package Pages;

import Base.Base;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class PendingRequestPage extends Base {

    private  WebDriver driver;
    ElementUtils elementUtils;
    HelperComponents helperComponents;

	public PendingRequestPage(WebDriver driver) {
	    this.driver = driver;
	    elementUtils = new ElementUtils(driver);
	    helperComponents = new HelperComponents(driver);
	}

	private final By pageTitle = By.xpath("//div[@class='page_title']");
	private final By approveBtn = By.xpath("//tr[1]//button[@class='esop-button blue-outline ']");
	private final By finishBtn = By.xpath("//button[contains(text(),'Finish')]");
	private final By signeasyFinishLoaded = By.xpath("//div[@class='documents-signed-text']");
	private final By signEasyFinishLoader =  By.xpath("//se-finalizing-dialog[@class='ng-scope']//div[@class='download-progress-dialog-content']//div");
	private final By SuccessMessage = By.xpath("//div[@role='alert' and contains(text(),'Grant Accepted Successfully')]");
	private final By signatureType = By.xpath("//button[normalize-space()='Type']");
	private final By signatureField = By.xpath("//button[contains(@class,'Annotation-Widget-Signature')]");
	private final By signaturePopUp = By.xpath("//div[@class='signature-modal']//h4");
	private final By grantRequestTab = By.xpath("//button[@id='simple-tab-0']");
	private final By submitSignatureBtn = By.xpath("//button[contains(text(),'Use')]");
	private final By signatureTemplate = By.xpath("//div[contains(@class,'signature-wrapper')]//tr[1]//td[1]");
	private final By allGrantsNav = By.xpath("//a[contains(text(),'All Grants')]");
	private final By signEasyFrame = By.xpath("//div[@class='PSPDFKit-Container']//iframe");
	private final By SignConfirm = By.xpath("//div[@class='all-done']/span");
    private  String currentWindow = "";


    public String getPageTitle(){
    	return elementUtils.doGetText(pageTitle);
	}

	//Navigating to Signeasy Page
    public void navigateToSignEasyPage(){
	    elementUtils.doClick(approveBtn);
	   boolean isSwitched = switchToSignEasyWindow();
	   if(isSwitched){
	       elementUtils.waitForElementPresence(finishBtn,20);
	       elementUtils.waitForElementClickable(finishBtn,5);
       }
    }

    public boolean initiateSignature(){
		boolean isSigned = false;
		elementUtils.switchToFrameWindow(signEasyFrame);
		elementUtils.waitForElementClickable(signatureField,10);
		elementUtils.doClick(signatureField);
		elementUtils.switchToDefaultView();
		if(elementUtils.doIsDisplayed(signaturePopUp)){
			elementUtils.doClick(signatureType);
			elementUtils.doClick(signatureTemplate);
			elementUtils.doClick(submitSignatureBtn);
			isSigned = true;
		}
		return isSigned;
	}

	public boolean confirmSignature(){
		elementUtils.waitForElementVisibility(SignConfirm);
		elementUtils.doClick(finishBtn);
		if(checkSuccessMessage()){
			switchToMainWindow();
			return true;
		}
		return false;
	}

    // Switching to SignEasy Window
    private boolean switchToSignEasyWindow() {
        boolean isSwitched = false;
        currentWindow = driver.getWindowHandle();
        try {
            Set<String> allWindows = driver.getWindowHandles();
            for (String actual : allWindows) {
                if (!actual.equalsIgnoreCase(currentWindow))
                    driver.switchTo().window(actual);{
                    isSwitched = true;}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSwitched;
    }

	// Switching back to parent Window
	private void switchToMainWindow() {
		try {
			driver.switchTo().window(currentWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    // checkSuccessMessage
	private boolean checkSuccessMessage() {
		return elementUtils.doIsDisplayed(signeasyFinishLoaded);
	}

	//Navigate to VestingTable Page
	public testpage1 goToVestingTablePage(){
    	elementUtils.clickElementByJS(allGrantsNav);
    	return new testpage1(driver);
	}
}


