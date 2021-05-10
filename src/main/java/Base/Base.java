package Base;

import Utils.DataUtil.ExcelUtils;
import Utils.ReadProperty;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import Utils.optionsManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;

public class Base {

        public optionsManager optionsManager;
        public ExcelUtils getData;

        public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

        public static synchronized WebDriver getDriver(){
            return tlDriver.get();
        }


        public WebDriver initDriver() {

            String browserName = null;

            if(System.getProperty("browser") == null){
                browserName = ReadProperty.getProperty("browser");
            } else{
                browserName = System.getProperty("browser");
            }
            System.out.println("Running on ---> " + browserName + " browser");

            optionsManager op = new optionsManager();
            switch (browserName){
                case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
                WebDriverManager.chromedriver().setup();
                if(Boolean.parseBoolean(ReadProperty.getProperty("remote"))){
                    initRemoteWebdriver(browserName);
                } else {
                    tlDriver.set(new ChromeDriver(op.getChromeOptions()));
                }
                break;
                case "firefox":
                WebDriverManager.firefoxdriver().setup();
                if(Boolean.parseBoolean(ReadProperty.getProperty("remote"))){
                    initRemoteWebdriver(browserName);
                } else {
                    tlDriver.set(new FirefoxDriver(op.getFirefoxOptions()));
                }
                break;
                case "safari":
                WebDriverManager.getInstance(SafariDriver.class).setup();
                tlDriver.set(new SafariDriver());
                break;
                default:
                    System.out.println("This Browser is currently not supported for execution");
                    break;
                }

            getDriver().manage().window().maximize();
            getDriver().manage().deleteAllCookies();

            getDriver().get(ReadProperty.getProperty("TestUrl"));

            return getDriver();
        }

        //Setting Remote Webdriver
    private void initRemoteWebdriver(String browserName) {

            if(browserName.equalsIgnoreCase("chrome")){

                DesiredCapabilities capChrome = DesiredCapabilities.chrome();
                capChrome.setCapability(ChromeOptions.CAPABILITY,optionsManager.getChromeOptions());

                try{
                    tlDriver.set(new RemoteWebDriver(new URL(ReadProperty.getProperty("hubHost")),capChrome));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            else if(browserName.equalsIgnoreCase("firefox")){

                DesiredCapabilities capFirefox = DesiredCapabilities.firefox();
                capFirefox.setCapability(FirefoxOptions.FIREFOX_OPTIONS,capFirefox);

                try{
                    tlDriver.set(new RemoteWebDriver(new URL(ReadProperty.getProperty("hubHost")),capFirefox));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
    }

    //Initializing the properties
//    public Properties initProp(){
//
//            prop = new Properties();
//            String path = null;
//            String env = null;
//
//            try{
//                env = System.getProperty("env");
//                if(env == null){
//                    path = System.getProperty("user.dir") + "/resources/configuration/Data.properties";
//                } else{
//                    switch (env){
//                        case "staging":
//                            path = System.getProperty("user.dir") + "/resources/configuration/Qa.Data.properties";
//                            break;
//
//                        case "Production":
//                            path = System.getProperty("user.dir") + "/resources/configuration/Prod.Data.properties";
//                            break;
//                        default:
//                            System.out.println("Please pass the correct env value----> " + env);
//                            break;
//                    }
//                }
//                FileInputStream ip = new FileInputStream(path);
//                prop.load(ip);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        return prop;
//    }

    public String takeScreenshot() throws IOException {
        String currentTime = LocalDateTime.now().toString();
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = "./Build-Reports/FailureScreenshots/" + currentTime + ".png";
        File destination = new File(path);
        FileUtils.copyFile(screenshot, destination);
        return path;
    }




}
