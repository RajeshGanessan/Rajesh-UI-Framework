package Utils.browserOptionsUtil;

import Constants.AppConstants;
import Utils.PropertyUtils.ReadProperty;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

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
        firefoxOptions.setProfile(setDownloadFilePropertiesFirefox());
        if(Boolean.parseBoolean(ReadProperty.getProperty("headless"))) firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }

    //Setting Custom download path for chrome
    private void setDownloadFilePropertiesChrome(){
        Map<String,Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups",0);
        prefs.put("download.default_directory", AppConstants.FILEDOWNLOADPATH_CHROME);
        chromeoptions.setExperimentalOption("prefs",prefs);
    }

    //Setting custom download path for firefox
    private FirefoxProfile setDownloadFilePropertiesFirefox(){
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir",AppConstants.FILEDOWNLOADPATH_FIREFOX);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return profile;
    }

}
