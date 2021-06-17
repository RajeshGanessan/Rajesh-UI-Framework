package Base;

import Enums.ConfigProperties;
import Utils.TestDataUtil.ExcelUtils;
import Utils.PropertyUtils.ReadProperty;
import Utils.browserOptionsUtil.OptionsManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class Base {

         OptionsManager optionsManager;
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

            optionsManager = new OptionsManager();
            switch (browserName){
                case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
                WebDriverManager.chromedriver().setup();
                if(Boolean.parseBoolean(ReadProperty.getProperty("remote")) || System.getProperty("remote")!=null){
                    initRemoteWebdriver(browserName);
                } else {
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                }
                break;
                case "firefox":
                WebDriverManager.firefoxdriver().setup();
                if(Boolean.parseBoolean(ReadProperty.getProperty("remote")) || System.getProperty("remote")!=null){
                    initRemoteWebdriver(browserName);
                } else {
                    tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
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

            getDriver().get(ReadProperty.getProperty(ConfigProperties.URL));

            return getDriver();
        }

        //Setting Remote Webdriver
    private void initRemoteWebdriver(String browserName) {
        String remoteURL = "http://"+ System.getProperty("HUB_HOST")+":4444/wd/hub";
        if(browserName.equalsIgnoreCase("chrome")){
                DesiredCapabilities capChrome = DesiredCapabilities.chrome();
                capChrome.setCapability("browserName","chrome");
                 capChrome.setCapability("enableVNC",true);
                capChrome.setCapability(ChromeOptions.CAPABILITY,optionsManager.getChromeOptions());
                try{
                    if(System.getProperty("HUB_HOST")!=null){
                        tlDriver.set(new RemoteWebDriver(new URL(remoteURL),capChrome));
                    } else {
                        tlDriver.set(new RemoteWebDriver(new URL(ReadProperty.getProperty("hubhost")),capChrome));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            else if(browserName.equalsIgnoreCase("firefox")){
                DesiredCapabilities capFirefox = DesiredCapabilities.firefox();
                capFirefox.setBrowserName(BrowserType.FIREFOX);
                capFirefox.setCapability(FirefoxOptions.FIREFOX_OPTIONS,optionsManager.getFirefoxOptions());
                try{
                    if(System.getProperty("HUB_HOST")!=null){
                        tlDriver.set(new RemoteWebDriver(new URL(remoteURL),capFirefox));
                    } else {
                        tlDriver.set(new RemoteWebDriver(new URL(ReadProperty.getProperty("hubhost")), capFirefox));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
    }







}
