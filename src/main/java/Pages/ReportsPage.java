package Pages;

import Base.Base;
import Utils.AppConstants;
import Utils.DynamicXpath;
import Utils.PageUtils.ElementUtils;
import Utils.PageUtils.HelperMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ReportsPage extends Base {

    private WebDriver driver;
    ElementUtils elementUtils;
    HelperMethods helperMethods;


    public ReportsPage(WebDriver driver){
        this.driver =  driver;
        elementUtils = new ElementUtils(driver);
        helperMethods = new HelperMethods(driver);
    }

    private final By pageTitle = By.xpath("//div[text()='Reports']");
    private final By reportType = By.xpath("//div[@class='css-1g6gooi']//input");
    private final By transactionType = By.xpath("(//div[@class='css-1g6gooi']//input)[last()]");
    private final By accountingMethod = By.xpath("(//div[@class='css-1g6gooi']//input)[2]");
    private final By financialYear = By.xpath("(//div[@class='css-1g6gooi']//input)[3]");
    private final By fromDate = By.xpath("//input[@labelfor='startDate']");
    private final By selectMonthDropdown = By.xpath("(//select[@aria-label='Month'])[position()=1]");
    private final By endDate = By.xpath("//input[@labelfor='endDate']");
    private final By calenderTitle = By.xpath("(//select[@class='flatpickr-monthDropdown-months'])[position()=2]");
    private final By fetchingFile = By.xpath("//div[@role='alert' and (contains(text(),'Fetching'))]");
    private final By downloadReportBtn = By.xpath("//button[contains(normalize-space(),'Download')]");
    private final By allGrantsMenu = By.xpath("//a[contains(text(),'All Grants')]");
    String dateXpath = "//span[@aria-label='%replaceable%']";

    //Selecting Report Type
    public void SelectReportType(String reportName){
        helperMethods.selectFromDropdown(reportName,reportType);
    }

    //getting PageHeader
    public String getReportPageHeader(){
        return elementUtils.getPageHeader(pageTitle);
    }

    //Selecting report with Transaction Type
    public void SelectReportType(String reportName,String TransactionType){
        helperMethods.selectFromDropdown(reportName,reportType);
        helperMethods.selectFromDropdown(TransactionType,transactionType);
    }

    //select FromDate
    public void selectStartDate(String month,String day){
        elementUtils.doClick(fromDate);
        selectMonthAndDay(month,day);
    }

    public void selectEndDate(){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String dateToSelect =  formatter.format(LocalDate.now());
        elementUtils.doClick(endDate);
        if(elementUtils.doIsDisplayed(calenderTitle))
            driver.findElement(DynamicXpath.get(dateXpath,dateToSelect)).click();
    }

    //Selecting Month and Day
    private void selectMonthAndDay(String monthToSelect,String day){
        elementUtils.selectValueUsingText(selectMonthDropdown,monthToSelect);
        By date = By.xpath("(//span[text()='"+day+"'])[position()=1]");
        elementUtils.doClick(date);
    }

    //verifying File downloading
    public boolean verifyFileDownload(String downloadPath){
            elementUtils.doClick(downloadReportBtn);
            elementUtils.waitForElementInvisibility(fetchingFile);
             elementUtils.staticWait(2);
            return isFileDownloaded_Ext(downloadPath,".xlsx");
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
    private boolean isFileDownloaded_Ext (String dirPath,String ext){

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        System.out.println("Number of files -> " + files.length);
       return Arrays.stream(files).anyMatch(s -> s.getName().contains(ext));
    }

    public  ExerciseOptionsPage goToAllGrantsPage(){
        elementUtils.clickElementByJS(allGrantsMenu);
        return new ExerciseOptionsPage(driver);
    }







    


}
