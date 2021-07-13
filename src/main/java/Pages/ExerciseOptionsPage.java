package Pages;

import Base.Base;
import Utils.PageUtils.DynamicXpath;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperComponents;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExerciseOptionsPage extends Base {

    private WebDriver driver;
    ElementUtils elementUtils;
    HelperComponents helperComponents;


    public ExerciseOptionsPage(WebDriver driver) {
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperComponents = new HelperComponents(driver);
    }

    private final By allGrantsTableRows = By.xpath("//tbody[@class='table-body']/tr");
    private final By pageHeader = By.xpath("//div[@class='page_title']");
    private final By grantDetailsAccordian = By.xpath("//span[text()='Grant Details']//parent::div");
    private final By exerciseOptionsPageButton = By.xpath("//button[text()='Exercise Options']");
    private final By arrowDown = By.xpath("//span[@class='arrowDown']");
    private final By exerciseFormBtn = By.xpath("//button[@form='exercise_form']");
    private final By optionsVestedCount = By.xpath("//tbody[@class='table-body']/tr[1]/td[position()=2]");
    private final By optionsToBeExercisedTextBox = By.xpath("//tbody[@class='table-body']/tr[1]/td[position()=2]//following-sibling::td[4]/input");
    private final By exerciseDateBox = By.xpath("//tbody[@class='table-body']/tr[1]/td[position()=2]//following-sibling::td[6]//input");
    private final By uploadFileBtn = By.xpath("//tbody[@class='table-body']/tr[1]/td[position()=2]//following-sibling::td[7]//input");
    private final By employeeExitNavBar = By.xpath("//a[contains(text(),'Employee Exits')]");
    private final By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
    private final By successMessage = By
            .xpath("//div[@role='alert' and contains(text(),'Exercise detail updated successfully')]");
    String unitsVestedRowsXpath = "//tbody[@class='table-body']/tr[%replaceable%]/td[position()=5]";
    String employeeXpath ="//tbody[@class='table-body']/tr[%replaceable%]/td[1]";
    String selectEmployeeXpath = "//tbody[@class='table-body']//td[contains(text(),'%replaceable%')]";

    public String getPageTitle() {
        return elementUtils.doGetText(pageHeader);
    }

    //To get employee with vestedOptions
    private String getEmpWithVestedOptions() {
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        String empToSelect = "";
        List<WebElement> rows = elementUtils.getListOfElements(allGrantsTableRows);
        for(int i=1;i<=rows.size();i++){
            WebElement vestedOption = elementUtils.getElement(DynamicXpath.get(unitsVestedRowsXpath,i));
                int optionsCount = Integer.parseInt(vestedOption.getText());
                if(optionsCount > 0 ){
                     empToSelect = elementUtils.getElement(DynamicXpath.get(employeeXpath,i)).getText();
                    System.out.println(empToSelect);
                    break;
                }
        }
        return empToSelect;
    }



    public ExerciseOptionsPage enterOptionsToBeExercised(){
        elementUtils.doSendKeys(optionsToBeExercisedTextBox,getOptionsToBeExercised());
        return this;
    }

    public ExerciseOptionsPage selectExerciseDate(){
        helperComponents.selectDateFromCalender(arrowDown,exerciseDateBox,calenderTitle);
        return this;
    }

    public ExerciseOptionsPage uploadExerciseLetter(){
        elementUtils.doSendKeys(uploadFileBtn, helperComponents.getRandomFile());
        return this;
    }

    public boolean checkSuccessMessage() {
            elementUtils.doClick(exerciseFormBtn);
             return elementUtils.doIsDisplayed(successMessage);
    }

    private String getOptionsToBeExercised(){
        int optionsVested = Integer.parseInt(elementUtils.doGetText(optionsVestedCount));
        return helperComponents.getRandomVestedOptionValue(optionsVested);
    }

    public ExerciseOptionsPage goToEmployeeDetails()  {
         String employee = getEmpWithVestedOptions();
        if(employee.isEmpty()){
            System.out.println("No Employees with vested Options found");
            throw new RuntimeException("No Employees with vested Options found");
        } else {
        System.out.println(employee);
        elementUtils.doClick(DynamicXpath.get(selectEmployeeXpath,employee));
         elementUtils.doIsDisplayed(pageHeader);
        }
        return this;
    }

    public ExerciseOptionsPage openGrantDetails(){
        elementUtils.doClick(grantDetailsAccordian);
        return this;
    }

    public boolean goToExerciseOptionsPage(){
        if(!elementUtils.doIsDisplayed(exerciseOptionsPageButton)) {
            helperComponents.scrollIntoView(elementUtils.getElement(exerciseOptionsPageButton));}
            elementUtils.doClick(exerciseOptionsPageButton);
            return elementUtils.doIsDisplayed(exerciseFormBtn);
    }

    //navigating to EmployeeExit page
    public EmployeeExitPage goToEmployeeExitPage(){
        helperComponents.scrollIntoView(elementUtils.getElement(employeeExitNavBar));
        elementUtils.clickElementByJS(employeeExitNavBar);
        return new EmployeeExitPage(driver);    }

}


