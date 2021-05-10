package Pages;

import Base.Base;
import Utils.AppConstants;
import Utils.PageUtils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends Base {

	private WebDriver driver;
	ElementUtils elementUtils;

	private final By userEmail = By.cssSelector("input[name='email']");
	private final By userPassword = By.cssSelector("input[name='password']");
	private final By sign_InButton = By.xpath("//div[@class='login_container']//button[@type='submit']");
	private final By pageHeader= By.xpath("//div[contains(text(),'Use your email')]");
	private final By errorMessage = By.xpath("//div[@class='Toastify__toast-body']");
	private final By googleSignIn = By.xpath("//div[text()='Sign in with Google']");
	private final By esopManagementTab = By.xpath("//button[text()='ESOP Management']");

	public LoginPage(WebDriver driver){
		this.driver = driver;
		elementUtils = new ElementUtils(this.driver);
	}

	public WebElement getPageHeader() {
		return elementUtils.getElement(pageHeader);
	}
	//get loginPage title
	public String getLoginPageTitle(){
		return elementUtils.waitForTitleToBePresent(AppConstants.LOGINPAGE_TITLE,10);
	}
	//Setting userEmail
	public void setUserEmail(String Email){
		elementUtils.doSendKeys(userEmail,Email);
	}

	//Setting userPassword
	public void setUserPassword(String password){

		elementUtils.doSendKeys(userPassword,password);
	}

	//checkg GoogleSignIn
	public boolean checkGoogleSignIn(){
		return elementUtils.getElement(googleSignIn).isDisplayed();
	}


	public boolean InvalidLoginCheck(String username,String password) {
		setUserEmail(username);
		setUserPassword(password);
		elementUtils.doClick(sign_InButton);

		return elementUtils.getElement(errorMessage).isDisplayed();
	}

	//FunctionMethods
	public DashboardPage doLogin(String username,String password) {

		setUserEmail(username);
		setUserPassword(password);
		elementUtils.doClick(sign_InButton);
		goToEsopManagement();
		return new DashboardPage(driver);
	}

	private void goToEsopManagement(){
		elementUtils.doClick(esopManagementTab);
	}

	//For Employee login
	public EmployeeHomePage doLoginToEmployeeHomePage(String userName,String password){
		setUserEmail(userName);
		setUserPassword(password);
		elementUtils.doClick(sign_InButton);
		return  new EmployeeHomePage(driver);
	}


//
//	//Validating csrfToken
//	public String getTokenFromLocalStorage(String key) {
//
//		jsExecutor = (JavascriptExecutor)driver;
//
//		String csrfToken = (String) jsExecutor.executeScript(String.format("return window."
//				+ "localStorage.getItem('%s');", key));
//		System.out.println(csrfToken);
//
//		return csrfToken;
//	}

}
