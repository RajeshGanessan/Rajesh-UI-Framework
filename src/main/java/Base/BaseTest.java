package Base;

import java.net.MalformedURLException;

import Pages.LoginPage;
import Utils.DataUtil.ExcelUtils;
import Utils.DataUtil.FakerData;
import Utils.PageUtils.HelperMethods;
import Utils.ReadProperty;
import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import Listeners.MailTriggerAPI;

public class  BaseTest {

    public WebDriver driver;
    public Base base;
    public LoginPage loginPage;
    public HelperMethods helperMethods;
    public MailTriggerAPI mailAPI;
    public  ExcelUtils getData;
    public FakerData faker;

    @Parameters("Browser")
    @BeforeClass
    public void setup(String browserName) {

        base = new Base();
//        prop = base.initProp();
        ReadProperty.setProperty("browser", browserName);
        driver = base.initDriver();
        helperMethods = new HelperMethods(driver);
        loginPage = new LoginPage(driver);

    }

//    // Sending ExecutionReportMail
//	@AfterSuite
//	public void SendReportMail() throws MalformedURLException, EmailException {
//        mailAPI = new MailTriggerAPI();
//        mailAPI.SendEmail();
//	}

    // for quitting browser
    @AfterClass
    public void TearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



