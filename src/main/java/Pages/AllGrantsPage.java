package Pages;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AllGrantsPage {

    private final WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;

    public AllGrantsPage(WebDriver driver) {
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
    }

    private By vestingTableHeader = By.xpath("//div[@class='page_title']");
    private By searchBar = By.cssSelector("input[name$='vesting_search_query']");
    private By searchBtn = By.xpath("//button[text()='Search']");
    private By menuButton = By.xpath("//tr[1]//button[@aria-controls='long-menu']");
    private By cancelGrantBtn = By.xpath("//div[@role='presentation'][1]//child::li");
    private By employeeFromList = By.xpath("//tr[1]/td[contains(@class,'employee_name')]");
    private By cancellationModal = By.xpath("//div[@class='modal-title']");
    private By confirmCancel = By.xpath("//button[@form='cancel_grant']");
    private By cancelReasonField = By.xpath("//textarea[@name='cancelGrantReason']");
    private By reportsMenu = By.xpath("//a[contains(text(),'Reports')]");
    private By successMessage = By.xpath("//div[contains(text(),'Grant cancelled successfully')]");


    public String getPageHeader() {
        return elementUtils.doGetText(vestingTableHeader);
    }

    public void clickCancelGrant() {
        elementUtils.doClick(menuButton);
        elementUtils.waitForElementVisibility(cancelGrantBtn);
        elementUtils.doClick(cancelGrantBtn);
    }

    public void enterCancellationReason(String cancellationReason){
        if(elementUtils.doIsDisplayed(cancellationModal)) {
            elementUtils.doSendKeys(cancelReasonField, cancellationReason);
        }
    }

    public void confirmCancellation(){
       if (elementUtils.doIsDisplayed(confirmCancel))
           elementUtils.doClick(confirmCancel);
    }

    public Boolean checkSuccessMessage(){
        return elementUtils.doIsDisplayed(successMessage);
    }

    //Navigating to Reports Page
    public ReportsPage goToReportsPage(){
        elementUtils.clickElementByJS(reportsMenu);
        return new ReportsPage(driver);
    }
}