package Utils.browserOptionsUtil;

import Constants.AppConstants;
import Utils.PropertyUtils.ReadProperty;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OptionsManager {

    public ChromeOptions chromeoptions;
    public FirefoxOptions firefoxOptions;


    //To get chromeOptions
    public ChromeOptions getChromeOptions(){
        chromeoptions = new ChromeOptions();
        setDownloadFilePropertiesChrome();
        if(Boolean.parseBoolean(ReadProperty.getProperty("headless"))) chromeoptions.addArguments("--headless");
        if(Boolean.parseBoolean(ReadProperty.getProperty("incognito"))) chromeoptions.addArguments("incognito");

        return chromeoptions;
    }

    //To get FireFox options
    public FirefoxOptions getFirefoxOptions(){
        firefoxOptions = new FirefoxOptions();
        if(Boolean.parseBoolean(ReadProperty.getProperty("headless"))) firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }

    private void setDownloadFilePropertiesChrome(){
        Map<String,Object> prefs = new HashMap<>();

        prefs.put("profile.default_content_settings.popups",0);
        prefs.put("download.default_directory", AppConstants.FILEDOWNLOADPATH);

        chromeoptions.setExperimentalOption("prefs",prefs);
    }

}
