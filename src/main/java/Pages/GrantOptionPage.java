package Pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Base.Base;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import Utils.DynamicXpath;

public class GrantOptionPage extends Base {
    private WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;

	private final DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMMM d, yyyy");

	private By grantOptionsHeader = By.xpath("//div[@class='page_title']");
	private By selectEmployee = By.xpath("//div[contains(text(),'Select Employee')]/following-sibling::div//input");
	private By selectScheme = By.xpath("//div[contains(text(),'Select Equity Scheme')]/following-sibling::div//input");
	private By selectVestingField = By
			.xpath("//div[contains(text(),'Select Vesting Schedule')]/following-sibling::div//input");
	private By selectTypeOfGrantField = By
			.xpath("//div[contains(text(),'Fresh Grant')]/following-sibling::div//input");
	private By exercisePrice = By.xpath("//input[@name='grant_price']");
	private By exisitingGrantLetter = By.xpath("//input[@name='esop_grant_letter']");
	private By grantLetterLabel = By.xpath("//label[@for='esop_grant_letter']");
	private By grantDate = By.xpath("//input[@placeholder='Select date']");
	private By grantOptionField = By.xpath("//input[@name='grant_option']");
	private By grantOptionButton = By.xpath("//button[starts-with(text(),'Grant Options')]");
	private By arrowUp = By.xpath("//span[@class='arrowUp']");
	private By arrowDown = By.xpath("//span[@class='arrowDown']");
	private By grantConfirmButton = By.xpath("//div[contains(text(),'Confirm')]");
	private By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
	private By previewGrantOptionBox = By.xpath("//div[@class='modal-title']/div");
	private By pendingRequestNav = By.xpath("//a[contains(text(),'Pending Request')]");
	private By optionsGrantedBtn = By.xpath("//button[contains(text(),'OK')]");
	private By optionsGrantedModal = By.xpath("//div[@class='modal-title']/div");
	private By successMessage = By
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
		helperMethods.selectRandomValueFromList(empToSelect,selectEmployee);
	    }

    public void selectEquityScheme(String schemeToSelect){
		helperMethods.selectFromDropdown(schemeToSelect,selectScheme);
	}

    public void setSelectVestingField(String vestingToSelect){
		helperMethods.selectFromDropdown(vestingToSelect,selectVestingField);
	}

    public void selectTypeOfGrant(GrantType typeOfGrant){
			switch (typeOfGrant){
				case FRESHGRANT:
					helperMethods.selectFromDropdown("Fresh Grant",
							selectTypeOfGrantField);
					break;
				case EXISTINGGRANT:
					helperMethods.selectFromDropdown("Grant letter has been signed physically by the Employee",
							selectTypeOfGrantField);
					break;
		}
	}

	public void uploadGrantLetter(){
		if(elementUtils.doIsDisplayed(grantLetterLabel)) {
			elementUtils.doSendKeys(exisitingGrantLetter,helperMethods.getRandomFile());
		}
	}

	public void enterOptionsGranted(String optionsToEnter){
		helperMethods.scrollIntoView(elementUtils.getElement(grantOptionField));
		elementUtils.doSendKeys(grantOptionField,optionsToEnter);
	}

	public void enterExercisePrice(String exerPrice){
		elementUtils.doSendKeys(exercisePrice,exerPrice);
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
		String key = "grant_option_initial_value";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(String.format("window.localStorage.removeItem('%s');",key));
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
