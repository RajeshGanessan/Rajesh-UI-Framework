package Pages;

import Base.Base;
import Utils.TestDataUtil.ExcelUtils;
import Utils.PageUtils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Utils.PageUtils.HelperMethods;

public class VestingSchedulePage extends Base {

    WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;

    public VestingSchedulePage(WebDriver driver){
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
		getData = new ExcelUtils("VestingSchedules");

	}

	private final By vestingTableHeader = By.xpath("//div[@class='page_title']");
	private final By createVestingScheduleBtn = By.xpath("//button[contains(text(),'Create Vesting Schedule')]");
	private final By createVestingPageHeader = By.xpath("//div[contains(text(),'Create New Vesting Schedule')]");
	private final By scheduleName = By.name("schedule_name");
	private final By vestingSchedulePeriod = By.name("vesting_period");
	private final By vestingFrequency = By.xpath("//div[@class='css-1g6gooi']//input");
	private final By customVestingToggle = By.xpath("//label[@for='custom_vesting']/span");
	private final By createScheduleBtn = By.xpath("//button[text()= 'Create Schedule']");
	private final By vestingOptionFields = By.xpath("//input[contains(@name,'custom_input')]");
	private final By vestingOption1 = By.xpath("//input[@name='custom_input_1']");
	private final By vestingOption2 = By.xpath("//input[@name='custom_input_2']");
	private final By vestingOption3 = By.xpath("//input[@name='custom_input_3']");
	private final By employeesNavBar = By.xpath("//a[contains(text(),'Employees')]");
	private final By cliffPeriodField = By.xpath("//input[@name='cliff_period']");
	private final By successMessage = By
			.xpath("//div[contains(text(),'ESOP Vesting Schedule saved successfully')]");

	public String getVestingSchedulePageHeader(){
		return elementUtils.doGetText(vestingTableHeader);
	}

	//Navigating to CreateVestingschedulePage
	private boolean goToCreatePage(){
		elementUtils.doClick(createVestingScheduleBtn);
		 return elementUtils.doIsDisplayed(createVestingPageHeader);
	}

	//Filling vestingForm Details
	private void fillVestingDetails(String vestingName,String vestingPeriod,String frequency,String cliffPeriod) throws Exception {
		int randomDigit = helperMethods.getRandomNumber();
		if (goToCreatePage()) {
			elementUtils.doSendKeys(scheduleName, vestingName + randomDigit);
			elementUtils.doSendKeys(vestingSchedulePeriod, vestingPeriod );
			helperMethods.selectFromDropdown(frequency,vestingFrequency);
			elementUtils.doSendKeys(cliffPeriodField, cliffPeriod);
		} else {
			throw new Exception("Cant able to fill the vesting details");
		}
	}

	// Creating new Vesting schedulee
	public boolean createVestingSchedule(String vestingName,String vestingPeriod,String frequency,String cliffPeriod){
		try{
			boolean isCreated;
			fillVestingDetails(vestingName,vestingPeriod,frequency,cliffPeriod);
			elementUtils.doClick(createScheduleBtn);
			isCreated = checkSuccessMessage();
			if(isCreated)
				getData.setCellData(0,1,vestingName);
//			getData.setCellData(1,0,vest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkSuccessMessage();
	}

	// Creating new Vesting schedule with Custom Vesting options
	public boolean createVestingSchedule(String vestingName, String vestingPeriod,
			String vestingFrequency, String cliffPeriod, String option1, String option2,
			String option3) {
		try {
			fillVestingDetails(vestingName,vestingPeriod,vestingFrequency,cliffPeriod);
			elementUtils.doClick(customVestingToggle);
				if (elementUtils.doIsDisplayed(vestingOptionFields)) {
					//Entering custom vesting options
					enterCustomVestingOptions(option1, option2, option3);
				}
			elementUtils.doClick(createScheduleBtn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkSuccessMessage();
	}

	// checking successMessage
	private boolean checkSuccessMessage() {
		return elementUtils.doIsDisplayed(successMessage);
	}
	// entering CustomVestingOptions
	private void enterCustomVestingOptions(String option1, String option2, String option3) {
		try {
			elementUtils.doSendKeys(vestingOption1, option1);
			elementUtils.doSendKeys(vestingOption2, option2);
			elementUtils.doSendKeys(vestingOption3, option3);
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	 //Navigating to Employees page
	public EmployeesPage goToEmployeesPage() {
		elementUtils.clickElementByJS(employeesNavBar);
		return new EmployeesPage(driver);
	}

}
