package Utils.PageUtils;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ElementUtils {

    WebDriver driver;

    public ElementUtils(WebDriver driver){
        this.driver = driver;
    }

    //getting element
    public WebElement getElement(By locator){
        WebElement element = null;
        try{
            System.out.println("Locator is " + locator);
            element = driver.findElement(locator);
        } catch (Exception e){
            System.out.println("Some exception occured when getting the element");
        }
        return element;
    }

    //getting elements
    public List<WebElement> getListOfElements(By locator){
        List<WebElement> elements = null;
        try{
            elements = driver.findElements(locator);
        } catch (NoSuchElementException e){
            System.out.println("Some Exception occured when getting the list of elements");
        }
        return elements;
    }

    //Get Page header
    public String getPageHeader(By locator){
        return doGetText(locator);
    }

    public void doSendKeys(By locator,String dataToEnter){
        waitForElementPresence(locator,10);
        getElement(locator).sendKeys(dataToEnter);
    }

    //Click element
    public void doClick(By locator){
        waitForElementPresence(locator,10);
        waitForElementClickable(locator,10);
        getElement(locator).click();
    }

    //get text
    public String doGetText(By locator) {
        waitForElementPresence(locator, 10);
        return getElement(locator).getText();
    }

    //check element is displayed
    public boolean doIsDisplayed(By locator) {
        waitForElementVisibility(locator);
        return getElement(locator).isDisplayed();
    }

    //click Element By JS
    public void clickElementByJS(By locator) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
       WebElement element =  getElement(locator);
        js.executeScript("arguments[0].click();", element);

    }

    //getting title
    public String waitForTitleToBePresent(String title, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver,timeOut);
        wait.until(ExpectedConditions.titleContains(title));
        return driver.getTitle();
    }

    //staticWait
    public void staticWait(int timeOut)  {
        Uninterruptibles.sleepUninterruptibly(timeOut, TimeUnit.SECONDS);
    }

    //SwitchFrame
    public void switchToFrameWindow(By locator){
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    //Switching To Default Content
    public void switchToDefaultView(){
        driver.switchTo().defaultContent();
    }


    //wait till element is clickable
    public void waitForElementClickable(By locator,int timeOut) {
        try{
        WebDriverWait wait =  new WebDriverWait(driver,timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            System.out.println("Some error/exception while waiting for element to click -> " + locator.toString());
            e.printStackTrace();
        }
    }

    public void waitForElementPresence(By locator,int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,timeOut);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Some error/exception while waiting for locator -> " + locator.toString());
            e.printStackTrace();
        }
    }

    //wait till element is visible
    public void waitForElementVisibility(By locator) {
        try {
            WebDriverWait wait =  new WebDriverWait(driver,15);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Some error/exception while waiting for element Visibility -> " + locator.toString());
            e.printStackTrace();
        }
    }

    //wait till element is Invisible
    public void waitForElementInvisibility(By locator) {
        try {
            WebDriverWait wait =  new WebDriverWait(driver,15);
            wait.until(ExpectedConditions.invisibilityOf(getElement(locator)));
        } catch (Exception e) {
            System.out.println("Some error/exception while waiting for element Visibility -> " + locator.toString());
            e.printStackTrace();
        }
    }

    //Select Value Using SelectClass
    public void selectValueUsingText(By locator,String valueToSelect){
        WebElement element = getElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(valueToSelect);
    }


}
