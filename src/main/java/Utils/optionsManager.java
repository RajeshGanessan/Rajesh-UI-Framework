package Utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class optionsManager {

    public ChromeOptions chromeoptions;
    public FirefoxOptions firefoxOptions;
    public Properties prop;


    //To get chromeOptions
    public ChromeOptions getChromeOptions(){
        chromeoptions = new ChromeOptions();
        SetDownloadFileProperties();
        if(Boolean.parseBoolean(ReadProperty.getProperty("headless"))) chromeoptions.addArguments("--headless");
        if(Boolean.parseBoolean(ReadProperty.getProperty("incognito"))) chromeoptions.addArguments("incognito");

        return chromeoptions;
    }

    //To get FireFox options
    public FirefoxOptions getFirefoxOptions(){
        firefoxOptions = new FirefoxOptions();
        if(Boolean.parseBoolean(prop.getProperty("headless"))) firefoxOptions.addArguments("--headless");
        return firefoxOptions;
    }

    private void SetDownloadFileProperties(){
        Map<String,Object> prefs = new HashMap<>();

        prefs.put("profile.default_content_settings.popups",0);
        prefs.put("download.default_directory",AppConstants.fileDownloadPath);

        chromeoptions.setExperimentalOption("prefs",prefs);
    }

}
