//package Pages;
//
//import Base.BaseTest;
//import Base.basePage;
//import Utils.DynamicXpath;
//import Utils.PageUtils.HelperMethods;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.TemporalAdjusters;
//import java.util.List;
//
//import static Base.BaseTest.*;
//
//public class employeeExitsPage extends basePage {
//
//    private LocalDate localDate;
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
//
//    public employeeExitsPage(WebDriver driver) {
//        super(driver);
//    }
//
//    ///following-sibling::div//input
//    private By employeeExitHeader = By.xpath("//div[@class='page_title']");
//    private By EmpExitBtn = By.cssSelector("button[class*='esop-button']");
//    private By selectEmployeeField = By.xpath("//div[contains(text(),'Select')]");
//    private By selectExitReasonField =  By.xpath("//div[contains(text(),'Resignation')]/following-sibling::div//input");
//    private By resignationDate = By.xpath("//label[@for='resignationDate']/following-sibling::div//input");
//    private By lastWorkingDate = By.xpath("//label[@for='lastWorkingDate']/following-sibling::div//input");
//    private By bufferTimeField = By.xpath("//input[@name='bufferTime']");
//    private By overrideCheckBox = By.xpath("//input[@id='overrideCheckbox']");
//    private By startExitBtn = By.xpath("//button[contains(@class,'btn btn-primary esop-button primary')]");
//    private By employeeList = By.xpath("//div[contains(@class,'option')]");
//    private By editBtn = By.xpath("//button[@class='esop-button blue-outline']");
//    private By calenderTitle = By.xpath("//div[contains(@class,'open') and contains(@class,'flatpickr-calendar')]//select[@class='flatpickr-monthDropdown-months']");
//    private By areYouSureModal = By.xpath("//div[contains(text(),'Are you sure ?')]");
//    private By confirmBtn =  By.xpath("//button[contains(text(),'Confirm')]");
//    private By successMessage =  By.xpath("//div[@role='alert' and contains(text(),'Created Successfully')]");
//
//
//
//    String EmployeeNameXpath = "//div[contains(@class,'option') and contains(@id,'option-%replaceable%')]";
//    String ExitReasonXpath = "//div[contains(@class,'option') and contains(text(),'%replaceable%')]";
//
//    public String getEmployeeExitHeader() {
//        return getPageHeader(employeeExitHeader);
//    }
//
//    public WebElement getEmpExitBtn() {
//        return getElement(EmpExitBtn);
//    }
//
//    public WebElement getSelectEmployeeField() {
//        return getElement(selectEmployeeField);
//    }
//
//    public WebElement getSelectExitReasonField() {
//        return getElement(selectExitReasonField);
//    }
//
//    public WebElement getResignationDate() {
//        return getElement(resignationDate);
//    }
//
//    public WebElement getExitEditProcess(){
//        return getElement(editBtn);
//    }
//
//    public WebElement getCalenderTitle(){
//        return getElement(calenderTitle);
//    }
//
//    public WebElement getLastWorkingDateField() {
//        return getElement(lastWorkingDate);
//    }
//
//    public WebElement getBufferTimeField() {
//        return getElement(bufferTimeField);
//    }
//
//    public WebElement getOverrideCheckBox() {
//        return getElement(overrideCheckBox);
//    }
//
//    public WebElement getSuccessMessage(){
//        return getElementWithVisibility(successMessage);
//    }
//
//    public WebElement getStartExitBtn() {
//        return getElement(startExitBtn);
//    }
//
//    public WebElement getConfirmModal(){
//        return getElement(areYouSureModal);
//    }
//
//    public WebElement getConfirmBtn(){
//        return getElement(confirmBtn);
//    }
//
//    public List<WebElement> getEmployeeList(){
//        return getListElements(employeeList);
//    }
//
//
//    //clicking exit button
//    private void clickEmpExitBtn(){
//        getEmpExitBtn().click();
//        test.get().info("Clicked on the StartEmployeeExit button");
//    }
//
//    //clicking exit button
//    private void clickEmployeeField(){
//        waitForElementClickable(selectEmployeeField);
//        getSelectEmployeeField().click();
//    }
//
//    //selecting the employee
//    private int getSizeOfEmployeeList(){
//        int size =  getEmployeeList().size()-1;
//        if(size < 0){
//            size+=2;
//        }
//        return size;
//
//    }
//
//    // To get Resignation date
//    private String getResignDate() {
//
//        localDate = LocalDate.now();
//        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
//        return formatter.format(localDate);
//        }
//
//    // To get LastWorkingDate date
//    public String getLastWorkingDate() {
//
//        localDate = LocalDate.now();
//        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth());
//        return formatter.format(localDate.plusDays(8));
//    }
//    //Select Resignation date
//    public void selectResignationDate(){
//        String SelectDate = "//div[contains(@class,'open') and contains(@class,'flatpickr-calendar')]//span[@aria-label='%replaceable%']";
//
//        String resignationDate = getResignDate();
//
//        getResignationDate().click();
//
//        waitForElementPresence(calenderTitle);
//
//        if (getCalenderTitle().isDisplayed()) {
//
//            driver.findElement(DynamicXpath.get(SelectDate, resignationDate)).click();
//            BaseTest.test.get().info("Selected Resignation Date as " + resignationDate);
//        }
//    }
//
//    //select last working date
//    public void selectLastWorkingDate(){
//        String SelectDate = "//div[contains(@class,'open') and contains(@class,'flatpickr-calendar')]//span[@aria-label='%replaceable%']";
//
//        String lastWorkingDate = getLastWorkingDate();
//        getLastWorkingDateField().click();
//        waitForElementPresence(calenderTitle);
//
//        if (getCalenderTitle().isDisplayed()) {
//            driver.findElement(DynamicXpath.get(SelectDate, lastWorkingDate)).click();
//            BaseTest.test.get().info("Selected lastworking Date as " + lastWorkingDate);
//        }
//
//    }
//
//    // checking successMessage
//    public boolean checkSuccessMessage() {
//
//        if (getSuccessMessage().isDisplayed()) {
//            BaseTest.test.get().info("Success Message Displayed after Employee exit process is started ");
//
//        } else {
//            BaseTest.test.get().info("Failed to verify the SuccessMessage ");
//
//        }
//        return getSuccessMessage().isDisplayed();
//    }
//
//    //enum for ExitReason
//    public enum ExitReason{
//        DEATH,
//        RESIGNATION,
//        TERMINATION
//    }
//
//    //Selecting a random Employee from the List
//    public void selectEmployeeFromList(){
//
//        int value;
//        String optionToSelect;
//        try{
//            clickEmpExitBtn();
//            clickEmployeeField();
//            value = getSizeOfEmployeeList();
//            optionToSelect = HelperMethods.getRandomNumberValue(value);
//            HelperMethods.SelectDynamicValue(optionToSelect, EmployeeNameXpath);
//            test.get().info("Employee selected from the list");
//        } catch (Exception e){
//            BaseTest.test.get().fail("Failed to Select employee From the list");
//            e.printStackTrace();
//        }
//    }
//
//    //Start Employee Exit process:
//    public boolean startEmployeeExit( String bufferTime,ExitReason exitReason){
//        try {
//                selectEmployeeFromList();
//            //For Selecting Exit reason from DropDown
//                switch (exitReason){
//
//                    case DEATH:
//                        HelperMethods.getDynamicValue("Death",getSelectExitReasonField(),ExitReasonXpath);
//                        test.get().info("Selected Exit reason as Death");
//                        break;
//                    case RESIGNATION:
//                        HelperMethods.getDynamicValue("Resignation",getSelectExitReasonField(),ExitReasonXpath);
//                        test.get().info("Selected Exit reason as Death");
//                        break;
//                    case TERMINATION:
//                        HelperMethods.getDynamicValue("Termination",getSelectExitReasonField(),ExitReasonXpath);
//                        break;
//                }
//
//                //Selecting ResignationDate
//                 selectResignationDate();
//                //Selecting LastWorking Date
//                selectLastWorkingDate();
//
//                //Entering BufferTimes
//                getBufferTimeField().sendKeys(bufferTime);
//
//                getStartExitBtn().click();
//
//                if(getConfirmModal().isDisplayed()){
//
//                    getConfirmBtn().click();
//                }
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Something Went wrong when Starting EmployeeExit process");
//            test.get().fail("Starting EmployeeExit Process Failed!!");
//            return false;
//        }
//        return checkSuccessMessage();
//
//    }
//
//
//
//}
