package Listeners;

import Base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class ExtentReporter extends Base {

    static LocalDateTime now = LocalDateTime.now();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MMM dd HH-mm-ss");
    static String time = dtf.format(now);


    private static ExtentReports extent;

    private static final String reportFileName = "MSE-Test-Automation-Report"+time+".html";
    private static final String reportDirectory = System.getProperty("user.dir")+ File.separator + "Build-Reports";
    private static final String reportFilePath = reportDirectory+ File.separator + reportFileName;

    //Create an extent report instance
    public static void initReports(){
        String filePath = getReportFileLocation();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("My StartUp Equity Automation Result");
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setReportName("My StartUp Equity Automation Result");
        extent = new ExtentReports();
        extent.setSystemInfo("OS",System.getProperty("os.name"));
        extent.setSystemInfo("Host Name",System.getProperty("user.name"));
        extent.setSystemInfo("Java Version",System.getProperty("java.version"));
        extent.attachReporter(sparkReporter);
    }

    public static void createTest(String testName,String description){
        ExtentTest test = extent.createTest(testName, description);
        ExtentReportManager.setExtent(test);
    }

    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
        ExtentReportManager.unload();
    }

    //getting reportFileLocation
    private static String getReportFileLocation () {
        createReportPath(reportDirectory);
        return reportFilePath;
    }

    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            try{
                if (testDirectory.mkdirs()) {
                    System.out.println("Directory: " + path + " is created!" );
                } else {
                    System.out.println("Failed to create directory: " + path);
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Some Problem when creating directory");
            }
        }
    }
    public static String takeScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
    }
}



