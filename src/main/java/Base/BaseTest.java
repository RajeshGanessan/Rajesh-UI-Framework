package Base;

import Enums.ConfigProperties;
import Pages.LoginPage;
import Utils.TestDataUtil.ExcelUtils;
import Utils.TestDataUtil.FakerData;
import Utils.PropertyUtils.ReadProperty;
import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import Utils.EmailUtil.MailTriggerAPI;

import java.net.MalformedURLException;

public class  BaseTest {

    public WebDriver driver;
    public Base base;
    public LoginPage loginPage;
    public MailTriggerAPI mailAPI;
    public  ExcelUtils getData;
    public FakerData faker;

    @Parameters("Browser")
    @BeforeClass
    public void setup(String browserName) {

        base = new Base();
        ReadProperty.setProperty("browser", browserName);
        driver = base.initDriver();
        loginPage = new LoginPage(driver);

    }

//    // Sending ExecutionReportMail
	@AfterSuite
	public void sendReportMail() throws MalformedURLException, EmailException {
        if(Boolean.parseBoolean(ReadProperty.getProperty(ConfigProperties.SENDREPORT))) {
            mailAPI = new MailTriggerAPI();
            mailAPI.sendEmail();
        }
	}

    // for quitting browser
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



