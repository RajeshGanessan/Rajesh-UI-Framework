package Pages;

import Base.Base;
import Utils.DynamicXpath;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ExerciseOptionsPage extends Base {

    private WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;


    public ExerciseOptionsPage(WebDriver driver) {
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
    }

    private final By allGrantsTableRows = By.xpath("//tbody[@class='table-body']/tr");
    private final By pageHeader = By.xpath("//div[@class='page_title']");
    private final By grantDetailsAccordian = By.xpath("//span[text()='Grant Details']//parent::div");
    private final By exerciseOptionsPageButton = By.xpath("//button[text()='Exercise Options']");
    private final By optionsVestedList = By.xpath("//tbody[@class='table-body']/tr/td[position()=2]");
    private final By optionsToBeExercisedTextBox = By.xpath("//tbody[@class='table-body']/tr/td[position()=2]//following-sibling::td[4]/input");
    private final By exerciseDateBox = By.xpath("//tbody[@class='table-body']/tr/td[position()=2]//following-sibling::td[6]//input");
    private final By uploadFileBtn = By.xpath("//tbody[@class='table-body']/tr/td[position()=2]//following-sibling::td[7]/button");
    private final By exerciseBtn = By.xpath("button[type='submit']");
    private final By calenderTitle = By.xpath("//select[@class='flatpickr-monthDropdown-months']");
    String unitsVestedRowsXpath = "//tbody[@class='table-body']/tr[%replaceable%]/td[position()=5]";
    String employeeXpath ="//tbody[@class='table-body']/tr[%replaceable%]/td[1]";
    String selectEmployeeXpath = "//tbody[@class='table-body']//td[contains(text(),'%replaceable%')]";

    public String getPageTitle() {
        return elementUtils.doGetText(pageHeader);
    }

    //To get employee with vestedOptions
    private List<String> getEmpWithVestedOptions() {
        List<WebElement> rows = elementUtils.getListOfElements(allGrantsTableRows);
        ArrayList<String> employees = new ArrayList<>();
        for(int i=1;i<=rows.size();i++){
            List<WebElement> columns = elementUtils.getListOfElements(DynamicXpath.get(unitsVestedRowsXpath,i));
            for(WebElement vestedOption : columns){
                int optionsCount = Integer.parseInt(vestedOption.getText());
                if(optionsCount > 0 ){
                    WebElement person = elementUtils.getElement(DynamicXpath.get(employeeXpath,i));
                     employees.add(person.getText());
                }
            }
            if(!employees.isEmpty()) break;
        }
        return employees;
    }

    public boolean goToEmployeeDetails(){
        List<String> employees = getEmpWithVestedOptions();
        String employeeToSelect = employees.get(0);
        elementUtils.doClick(DynamicXpath.get(selectEmployeeXpath,employeeToSelect));
        return elementUtils.doIsDisplayed(pageHeader);
    }

    public ExerciseOptionsPage openGrantDetails(){
        elementUtils.doClick(grantDetailsAccordian);
        return this;
    }

    public boolean goToExerciseOptionsPage(){
            helperMethods.scrollIntoView(elementUtils.getElement(exerciseOptionsPageButton));
            elementUtils.doClick(exerciseOptionsPageButton);
            return elementUtils.doIsDisplayed(pageHeader);
    }


    private List<String> getValueFromTable(By rowLocator,String employeeXpath,String columnXpath,List<String> employeeList){
        List<WebElement> rows = elementUtils.getListOfElements(rowLocator);
        for(int i=1;i<=rows.size();i++){
            List<WebElement> columns = elementUtils.getListOfElements(DynamicXpath.get(columnXpath,i));
            for(WebElement vestedOption : columns){
                int optionsCount = Integer.parseInt(vestedOption.getText());
                if(optionsCount > 0 ){
                    WebElement person = elementUtils.getElement(DynamicXpath.get(employeeXpath,i));
                    employeeList.add(person.getText());
                }
            }
            if(!employeeList.isEmpty()) break;
        }
        return employeeList;

    }






}


