package Pages;

import Base.Base;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class DashboardPage extends Base {

	public WebDriver driver;
	ElementUtils elementUtils;
	HelperMethods helperMethods;

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		elementUtils = new ElementUtils(driver);
		helperMethods = new HelperMethods(driver);
	}


	private final By dashboardPageHeader = By.xpath("//div[contains(text(),'ESOP Overview')]");
	private final By equitySchemeNav = By.xpath("//a[contains(text(),'Equity Scheme')]");
	private final By footerText = By.xpath("//div[@class='footer-text']/div");


	//get dashboard Header
	public String getDashboardPageHeader(){
		return elementUtils.doGetText(dashboardPageHeader);
	}

	//functionMethods
	public String getFooterText() throws InterruptedException {
		elementUtils.staticWait(2000);
		helperMethods.scrollDownComplete();

		return elementUtils.doGetText(footerText);
	}

	public EquitySchemePage goToEquitySchemePage() {
	    elementUtils.clickElementByJS(equitySchemeNav);

	    return new EquitySchemePage(driver);
	}
}
