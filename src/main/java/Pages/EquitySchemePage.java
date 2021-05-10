package Pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Base.Base;
import Utils.*;
import Utils.DataUtil.ExcelUtils;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;


public class EquitySchemePage extends Base {

    WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;

	public EquitySchemePage(WebDriver driver) {
	    this.driver = driver;
	    elementUtils = new ElementUtils(driver);
	    helperMethods = new HelperMethods(driver);
	    getData = new ExcelUtils("ESOPscheme");
	}

	//Webpage locators
	private final By equityPageHeader = By.xpath("//div[@class='page_title']");
	private final By newSchemeBtn = By.xpath("//button[contains(text(),'New Scheme')]");
	private final By selectSchemeType = By.xpath("//div[@class='css-1g6gooi']//input");
	private final By schemeName = By.xpath("//input[@name='scheme_name']");
	private final By schemeId = By.xpath("//input[@name='scheme_id']");
	private final By esopDate = By.xpath("//input[@placeholder='Select date']");
	private final By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
	private final By shareHolderDoc = By.xpath("//input[@name='shareholder_document']");
	private final By schemePoolSize = By.xpath("//input[@name='esop_pool_size']");
	private final By confirmationModal = By.xpath("//div[contains(text(),'Are you sure')]");
	private final By confirmButton = By.xpath("//button[normalize-space()='Confirm']");
	private final By arrowDown = By.xpath("//span[@class='arrowDown']");
	private final By esopSchemeDoc = By.xpath("//input[@name='esop_plan_document']");
	private final By createSchBtm = By.xpath("//button[contains(text(),'Create Scheme')]");
	private final By expandViewBtn = By.xpath("//td[text()='']//preceding-sibling::td");
	private final By vestingScheduleNav = By.xpath("//a[contains(text(),'Vesting Schedule')]");
	private final By docOfapproval = By.xpath("//tr[@class='content open']//b[contains(text(),'Document of')]");
	private final By sucessMessage = By.xpath("//div[@role='alert' and contains(text(),'ESOP Scheme saved successfully')]");

	//getting Scheme header
    public String getEquityPageHeader(){
		return elementUtils.doGetText(equityPageHeader);
	}

	public enum SchemeTypes {
		SAR,
		ESOP
	}
	//selecting Type of Scheme
	private void selectTypeOfScheme(SchemeTypes typeOfScheme){
		if(typeOfScheme == SchemeTypes.ESOP){
			helperMethods.selectFromDropdown("ESOP Scheme",selectSchemeType);
		} else if(typeOfScheme == SchemeTypes.SAR){
			helperMethods.selectFromDropdown("stock",selectSchemeType);
		} else {
			throw new InvalidArgumentException("Pls check the scheme type!!");
		}
	}

	// Creating New EsopScheme
	public void selectSchemeType(SchemeTypes schemeType) {
		try {
			elementUtils.waitForElementClickable(newSchemeBtn, 5);
			elementUtils.doClick(newSchemeBtn);
			selectTypeOfScheme(schemeType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Filling Details for scheme
	public boolean fillingSchemeDetails(String schemeNameData,String schemeID,String poolSize) {
    	boolean isEmpAdded;
		int randomNum = helperMethods.getRandomNumber();
		schemeNameData = schemeNameData + randomNum;
		schemeID = schemeID + randomNum;
		elementUtils.doSendKeys(schemeName, schemeNameData);
		elementUtils.doSendKeys(schemeId, schemeID);
		selectEquitySchemeDate();
		helperMethods.scrolltoBottomOfPage();
		elementUtils.doSendKeys(shareHolderDoc, helperMethods.getRandomFile());
		elementUtils.doSendKeys(schemePoolSize, poolSize);
		elementUtils.doSendKeys(esopSchemeDoc, helperMethods.getRandomFile());
		if (elementUtils.doIsDisplayed(createSchBtm)) {elementUtils.doClick(createSchBtm);}
		if(elementUtils.doIsDisplayed(confirmationModal)){elementUtils.doClick(confirmButton);}
		isEmpAdded = checkSuccessMessageValidation();
		if(isEmpAdded){
			//updating scheme Details in the sheet
			getData.setCellData(1, 0, schemeNameData);
			getData.setCellData(1, 1, schemeID );
		}
		return isEmpAdded;
	}
	// To check success Message
	private boolean checkSuccessMessageValidation() {
		return elementUtils.doIsDisplayed(sucessMessage);
	}

	// Selecting EsopSchemeDate
	public void selectEquitySchemeDate() {
    	helperMethods.selectDateFromCalender(arrowDown,esopDate,calenderTitle);
	}

	// checking expanded View
	public boolean checkExpandedView() {
		elementUtils.doClick(expandViewBtn);
		return elementUtils.doIsDisplayed(docOfapproval);
	}

	//returning nextPage object
	public VestingSchedulePage goToVestingSchedulePage() {
		elementUtils.clickElementByJS(vestingScheduleNav);
		return new VestingSchedulePage(driver);
	}

}
