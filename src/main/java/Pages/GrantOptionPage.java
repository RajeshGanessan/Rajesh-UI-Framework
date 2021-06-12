package Pages;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import Base.Base;
import Enums.ConfigProperties;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import Utils.PropertyUtils.ReadProperty;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import Utils.PageUtils.DynamicXpath;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GrantOptionPage extends Base {
    private WebDriver driver;
    private Random random;
    ElementUtils elementUtils;
    HelperMethods helperMethods;


	private final DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMMM d, yyyy");

	private final By grantOptionsHeader = By.xpath("//div[@class='page_title']");
	private final By selectEmployee = By.xpath("//div[contains(text(),'Select Employee')]/following-sibling::div//input");
	private final By selectScheme = By.xpath("//div[contains(text(),'Select Equity Scheme')]/following-sibling::div//input");
	private final By selectVestingField = By
			.xpath("//div[contains(text(),'Select Vesting Schedule')]/following-sibling::div//input");
	private final By selectTypeOfGrantField = By
			.xpath("//div[contains(text(),'Fresh Grant')]/following-sibling::div//input");
	private final By exercisePrice = By.xpath("//input[@name='grant_price']");
	private final By exisitingGrantLetter = By.xpath("//input[@name='esop_grant_letter']");
	private final By grantLetterLabel = By.xpath("//label[@for='esop_grant_letter']");
	private final By grantDate = By.xpath("//input[@placeholder='Select date']");
	private final By grantOptionField = By.xpath("//input[@name='grant_option']");
	private final By grantOptionButton = By.xpath("//button[starts-with(text(),'Grant Options')]");
	private final By arrowUp = By.xpath("//span[@class='arrowUp']");
	private final By arrowDown = By.xpath("//span[@class='arrowDown']");
	private final By grantConfirmButton = By.xpath("//div[contains(text(),'Confirm')]");
	private final By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
	private final By previewGrantOptionBox = By.xpath("//div[@class='modal-title']/div");
	private final By pendingRequestNav = By.xpath("//a[contains(text(),'Pending Request')]");
	private final By optionsGrantedBtn = By.xpath("//button[contains(text(),'OK')]");
	private final By optionsGrantedModal = By.xpath("//div[@class='modal-title']/div");
	private final By successMessage = By
			.xpath("//div[@role='alert' and contains(text(),'Grant information saved successfully')]");

	public GrantOptionPage(WebDriver driver){
	    this.driver =  driver;
	    elementUtils = new ElementUtils(driver);
	    helperMethods = new HelperMethods(driver);
	}

	public enum GrantType{
		EXISTINGGRANT,
		FRESHGRANT
	}

	public void selectEmployeeFromList(String empToSelect){
		if(elementUtils.doIsDisplayed(grantOptionsHeader))
		helperMethods.selectRandomValueFromList(empToSelect,selectEmployee);
	    }

    public void selectEquityScheme(String schemeToSelect){
		helperMethods.selectFromDropdown(schemeToSelect,selectScheme);
	}

    public void setSelectVestingField(String vestingToSelect){
		helperMethods.selectFromDropdown(vestingToSelect,selectVestingField);
	}

    public void selectTypeOfGrant(GrantType typeOfGrant){
			if (typeOfGrant.equals(GrantType.FRESHGRANT)) {
				helperMethods.selectFromDropdown("Fresh Grant",
						selectTypeOfGrantField);
			} else if(typeOfGrant.equals(GrantType.EXISTINGGRANT)) {
				helperMethods.selectFromDropdown("Grant letter has been signed physically by the Employee",
						selectTypeOfGrantField);
			} else throw new InvalidArgumentException("The given Grant type is not correct, Check once");
		}


	public void uploadGrantLetter(){
		if(elementUtils.doIsDisplayed(grantLetterLabel)) {
			if(Boolean.parseBoolean(ReadProperty.getProperty(ConfigProperties.REMOTE))){
				if(driver instanceof RemoteWebDriver){
					((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
				}
			}
			elementUtils.doSendKeys(exisitingGrantLetter,helperMethods.getRandomFile());
		}
	}

	public void enterOptionsGranted(){
		helperMethods.scrollIntoView(elementUtils.getElement(grantOptionField));
		elementUtils.doSendKeys(grantOptionField,getOptionsGranted());
	}

	public void enterExercisePrice(){
		elementUtils.doSendKeys(exercisePrice,getExercisePrice());
	}

	public void confirmGrant(){
		helperMethods.scrollIntoView(elementUtils.getElement(grantOptionButton));
		elementUtils.doClick(grantOptionButton);
		if(elementUtils.doIsDisplayed(previewGrantOptionBox)){
			elementUtils.doClick(grantConfirmButton);
		}
	}

	// Selecting year from dropdown
	private void selectYear(String typeOfGrant) {
		By arrow = typeOfGrant.contains("Fresh") ? arrowUp : arrowDown;
		helperMethods.moveToElement(arrow);
			 for(int i=0;i<3;i++){
			 	elementUtils.doClick(arrow);
			 }
	}

	// To get FreshGrantdate
	private String getFreshGrantDate() {
		return formatter.format(LocalDate.now().plusYears(3));
	}

	//To get existing Grant Date
	private String getExistingGrantDate(){
		return formatter.format(LocalDate.now().minusYears(3));
	}

	//check title
	public String getPageHeader(){
		return elementUtils.doGetText(grantOptionsHeader);
	}

	// Selecting date from calender
	public void selectGrantDate(String typeOfGrant) {
		String selectDate = "//span[@aria-label='%replaceable%']";
		String grantDateToSelect = typeOfGrant.contains("Fresh") ? getFreshGrantDate() : getExistingGrantDate();
		elementUtils.doClick(grantDate);
		if (elementUtils.doIsDisplayed(calenderTitle)) {
			selectYear(typeOfGrant);
			driver.findElement(DynamicXpath.get(selectDate, grantDateToSelect)).click();
		}
	}

	//removing localStorageVlue
	private void clearingLocalStorage(){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String key = "grant_option_initial_value";
		js.executeScript(String.format("window.localStorage.removeItem('%s');",key));
	}

	private String getOptionsGranted(){
		int min=20; int max = 60;
		random = new Random();
		return String.valueOf(random.nextInt(max-min) + min);
	}

	private String getExercisePrice(){
		double start = 20; double end = 50;
		DecimalFormat format = new DecimalFormat("0.00");
		double rand = new Random().nextDouble();
		return String.valueOf(format.format(start + (rand * (end - start))));
	}

	public void clearAndRefresh(){
		clearingLocalStorage();
		driver.navigate().refresh();
	}
	// checking successMessage after Grant
	public boolean checkSuccessMessage() {
		if(elementUtils.doIsDisplayed(optionsGrantedModal)){
			elementUtils.waitForElementVisibility(optionsGrantedBtn);
			elementUtils.doClick(optionsGrantedBtn);
		}
		return elementUtils.doIsDisplayed(successMessage);
	}

//	navigating to the Pending request page
	public PendingRequestPage goToPendingRequestPage() {
		helperMethods.scrollIntoView(elementUtils.getElement(pendingRequestNav));
		elementUtils.clickElementByJS(pendingRequestNav);
		return new PendingRequestPage(driver);
	}
}
