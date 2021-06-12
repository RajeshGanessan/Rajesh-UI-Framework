package Pages;

import Utils.PageUtils.DynamicXpath;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class EmployeeExitPage {

    private WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;

    private LocalDate localDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

    public EmployeeExitPage(WebDriver driver) {
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
    }

    private final By exitPageHeader = By.xpath("//div[@class='page_title']");
    private final By selectEmployeeField = By.xpath("//div[contains(text(),'Select Employee')]/following-sibling::div//input");
    private final By selectExitReasonField = By.xpath("//label[contains(text(),'Exit Reason')]/parent::div//input");
    private final By selectLastWorkingDateField = By.xpath("//input[@placeholder='Select date']");
    private final By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
    private final By bufferTimeField =  By.xpath("//input[@name='bufferTime']");
    private final By startEmpExitBtn = By.xpath("//button[normalize-space()='Start Employee Exit']");
    private final By confirmModal = By.xpath("//div[contains(text(),'Are you sure ?')]");
    private final By confirmExitProcess = By.xpath("//button[normalize-space()='Confirm']");
    private final By threeDotMenu = By.xpath("//tbody[@class='table-body']/tr[1]//button");
    private final By closeSuccessMessage = By.xpath("//div[contains(@class,'Toastify')]//button");
    private final By exitCreatedMessage = By
            .xpath("//div[@role='alert' and contains(text(),'Created Successfully')]");
    private final By exitCancelledMessage = By
            .xpath("//div[@role='alert' and contains(text(),'Employee exit canceled successfully')]");
    private final By cancelExitBtn = By.xpath("//div[@id='long-menu']//child::li[text()='Cancel Exit']");
    private final By expandDetailsBtn = By.xpath("//td[normalize-space()='+']");
    private final By schemeNameLabel = By.xpath("//b[contains(normalize-space(),'Scheme Name')]");
    private final By optionsGrantedLabel = By.xpath("//b[contains(normalize-space(),'Options Granted')]");
    String dateXpath = "//span[@aria-label='%replaceable%']";


    public String getPageHeader(){
        return elementUtils.doGetText(exitPageHeader);
    }

    public void selectEmployeeFromList(String empToSelect){
        if(elementUtils.doIsDisplayed(exitPageHeader)) {
            elementUtils.doClick(startEmpExitBtn);
            helperMethods.selectRandomValueFromList(empToSelect, selectEmployeeField);
        }
    }

    public void selectExitReasonFromList(String exitReasonToSelect){
        helperMethods.selectFromDropdown(exitReasonToSelect,selectExitReasonField);
    }

    private String getLastWorkingDate(){
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return formatter.format(localDate);
    }

    public void selectLastWorkingDate(){
        elementUtils.doClick(selectLastWorkingDateField);
        String lastWorkingDate = getLastWorkingDate();
        if(elementUtils.doIsDisplayed(calenderTitle))
            driver.findElement(DynamicXpath.get(dateXpath,lastWorkingDate)).click();
    }

    public void enterBufferDate(String bufferTime){
        elementUtils.doSendKeys(bufferTimeField,bufferTime);
    }

    public boolean confirmExitProcess(){
        elementUtils.doClick(startEmpExitBtn);
        if(elementUtils.doIsDisplayed(confirmModal)){
            elementUtils.doClick(confirmExitProcess);
        }
        return verifySuccessMessage(exitCreatedMessage,closeSuccessMessage);
    }

    public boolean cancelExitProcess(){
        elementUtils.doClick(threeDotMenu);
        if(elementUtils.doIsDisplayed(cancelExitBtn)){
            elementUtils.doClick(cancelExitBtn);
            elementUtils.doClick(confirmExitProcess);
        }
        return verifySuccessMessage(exitCancelledMessage,closeSuccessMessage);
    }

    public EmployeeExitPage expandDetails(){
        elementUtils.doClick(expandDetailsBtn);
        return this;
    }

    public boolean verifyDetails(){
        if(elementUtils.doIsDisplayed(schemeNameLabel) &&
                elementUtils.doIsDisplayed(optionsGrantedLabel)){
            return true;
        } 
        return false;
    }

    private boolean verifySuccessMessage(By successMessage,By closeMessage){
        if(elementUtils.doIsDisplayed(successMessage)){
            elementUtils.doClick(closeMessage);
            return true;
        }
        return false;
    }





}
