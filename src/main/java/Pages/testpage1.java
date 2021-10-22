package Pages;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class testpage1 {

    private final WebDriver driver;
    ElementUtils elementUtils;
    HelperComponents helperComponents;

    public testpage1(WebDriver driver) {
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperComponents = new HelperComponents(driver);
    }

    private final By vestingTableHeader = By.xpath("//div[@class='page_title']");
    private final By cancelMenuBtn = By.xpath("//tr/td[position()=7 and contains(text(),'Pending')]//following-sibling::td[contains(@class,'row-action')]//button");
    private final By cancelGrantBtn = By.xpath("//div[@role='presentation']//child::li[text()='Cancel Grant']");
    private final By cancellationModal = By.xpath("//div[@class='modal-title']");
    private final By confirmCancel = By.xpath("//button[@form='cancel_grant']");
    private final By cancelReasonField = By.xpath("//textarea[@name='cancelGrantReason']");
    private final By reportsMenu = By.xpath("//a[contains(text(),'Reports')]");
    private final By successMessage = By.xpath("//div[contains(text(),'Grant cancelled successfully')]");

    public String getPageHeader() {
        return elementUtils.doGetText(vestingTableHeader);
    }


    public testpage1 clickCancelGrants(){
        elementUtils.doClick(cancelMenuBtn);
        if(elementUtils.doIsDisplayed(cancelGrantBtn)){
            elementUtils.doClick(cancelGrantBtn);
        }
        return this;
    }

    public testpage1 enterCancellationReason(String cancellationReason){
        if(elementUtils.doIsDisplayed(cancellationModal)) {
            elementUtils.doSendKeys(cancelReasonField, cancellationReason);
        }
        return this;
    }

    public testpage1 confirmCancellation(){
       if (elementUtils.doIsDisplayed(confirmCancel))
           elementUtils.doClick(confirmCancel);
       return this;
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