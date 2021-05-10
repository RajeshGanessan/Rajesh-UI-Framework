package Pages;

import Base.Base;
import Utils.AppConstants;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeHomePage extends Base {

    private WebDriver driver;
    ElementUtils elementUtils;
    private Random rand;
    HelperMethods helperMethods;

    public EmployeeHomePage(WebDriver driver){
        this.driver = driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
    }

    private final By pageHeader = By.xpath("//div[normalize-space()='ESOP Overview']");
    private final By grantedOptionsHeader = By.xpath("//div[@class='esop-vesting']//child::div[@class='page_title']");
    private final By downloadGrantBtn = By.xpath("//span[contains(text(),'Download my grants')]");
    private final By fetchingFile = By.xpath("//div[@role='alert' and (contains(text(),'Fetching'))]");
    private final By exerciseOptionsHeader = By.xpath("//div[contains(@class,'basic_info_panel')][1]//div[contains(text(),'Exercise Options')]");
    private final By amountOnExercise = By.xpath("//div[contains(@class,'basic_info_panel')][1]//tbody[@class='exercise-section']" +
            "//div[contains(text(),'Amount')]//parent::td//following-sibling::td/span");
    private final By vestedOptionsCount = By.xpath("//div[contains(@class,'basic_info_panel')][1]//table[@class='grant_side_details']//b[contains(text(),'Vested')]//parent::td/following-sibling::td");
    private final By exercisePrice = By.xpath("//div[contains(@class,'basic_info_panel')][1]//table[@class='grant_side_details']//b[contains(text(),'Exercise')]//parent::td/following-sibling::td");
    private final By exercisedOptionsCount = By.xpath("//div[contains(@class,'basic_info_panel')][1]//table[@class='grant_side_details']//b[contains(text(),'Exercised')]//parent::td/following-sibling::td");
    private final By exerciseOptionsBtn = By.xpath("//div[contains(@class,'basic_info_panel')][1]//button");
    private final By exerciseModalHeader = By.xpath("//div[@class='modal-title']/div");
    private final By textbox_OptionsToEnter = By.xpath("//input[@name='exerciseOptions']");
    private final By submitRequestBtn = By.xpath("//button[contains(.,'Request')]");
    private final By successMessage = By.xpath("//div[@role='alert' and contains(text(),'Email sent to Admin')]");



    public String getHomePageHeader(){
        return elementUtils.getPageHeader(pageHeader);
    }

    public String getGrantOptionsHeader(){
        return elementUtils.getPageHeader(grantedOptionsHeader);
    }

    private int getVestedOptions(){
        helperMethods.scrollIntoView(elementUtils.getElement(vestedOptionsCount));
        return Integer.parseInt(elementUtils.doGetText(vestedOptionsCount));
    }

    private double getExercisePrice(){
        helperMethods.scrollIntoView(elementUtils.getElement(vestedOptionsCount));
        return getNumericValue(elementUtils.doGetText(exercisePrice));
    }

    public double getAmountToBePaidValue(){
        helperMethods.scrollIntoView(elementUtils.getElement(amountOnExercise));
        return getNumericValue(elementUtils.doGetText(amountOnExercise));
    }

    public double calculateAmountToBePaid(){
        return (getVestedOptions() * getExercisePrice());
    }

    private double getNumericValue(String textToConvert){

        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(textToConvert);
        String text = null;
        while(m.find()){
             text = m.group();
        }
        return Double.parseDouble(text);
    }

    public EmployeeHomePage goToExercisePopup(){
        elementUtils.doClick(exerciseOptionsBtn);
        return this;
    }

    public EmployeeHomePage enterOptionsToExercise() {
        if (elementUtils.doIsDisplayed(exerciseModalHeader)) {
            elementUtils.waitForElementVisibility(textbox_OptionsToEnter);
            int vestedOptions = getVestedOptions();
            String optionsToEnter = String.valueOf(generateVestedOptions(vestedOptions));
            elementUtils.doSendKeys(textbox_OptionsToEnter,optionsToEnter);
        }
        return this;
    }

    public boolean submitAndVerifyExerciseOptions(){
        elementUtils.doClick(submitRequestBtn);
        return elementUtils.doIsDisplayed(successMessage);
    }

    private int generateVestedOptions(int bound){
         rand = new Random();
       return rand.nextInt(bound);
    }

    //verifying File downloading
    public boolean verifyFileDownload(String downloadPath){
        helperMethods.scrollIntoView(elementUtils.getElement(downloadGrantBtn));
        elementUtils.clickElementByJS(downloadGrantBtn);
        elementUtils.waitForElementInvisibility(fetchingFile);
        elementUtils.staticWait(2);
        return isFileDownloadedExt(downloadPath,".xlsx");
    }

    //delete the DownloadedReportFile
    public void isFileDeleted(){
        File dir = new File(AppConstants.fileDownloadPath);
        File files[] = dir.listFiles();
        if(files.length==0) {
            System.out.println("No Files to delete");
        }
        for (File file: files)
            file.delete();
    }

    //Checking whether FILE is present in the downloaded Directory
    private boolean isFileDownloadedExt (String dirPath,String ext){

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        System.out.println("Number of files -> " + files.length);
        return Arrays.stream(files).anyMatch(s -> s.getName().contains(ext));
    }


}
