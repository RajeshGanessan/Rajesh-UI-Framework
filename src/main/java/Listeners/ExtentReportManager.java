package Listeners;

import Base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Platform;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class ExtentReportManager extends Base {

    static Calendar instance = Calendar.getInstance();
    static Date time = instance.getTime();

    private static ExtentReports extent;

    private static Platform platform;
    private static final String reportFileName = "MSE-Test-Automation-Report"+time+".html";
    private static final String linuxPath = System.getProperty("user.dir")+ "/Build-Reports";
    private static final String macPath = System.getProperty("user.dir")+ "/Build-Reports";
    private static final String macReportFileLoc = macPath + "/" + reportFileName;
    private static final String linuxReportFileLoc = linuxPath + "/" + reportFileName;

    //Create an extent report instance
    public static ExtentReports createInstance(){
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.setAppendExisting(false);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("My StartUp Equity Automation Result");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("My StartUp Equity Automation Result");

        extent = new ExtentReports();

        extent.setSystemInfo("OS",System.getProperty("os.name"));
        extent.setSystemInfo("Host Name",System.getProperty("user.name"));
        extent.setSystemInfo("Java Version",System.getProperty("java.version"));
        extent.attachReporter(htmlReporter);
        return extent;
    }

    //Select the extent report file location based on platform
    private static String getReportFileLocation (Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case LINUX:
                reportFileLocation = linuxReportFileLoc;
                createReportPath(linuxPath);
                System.out.println("ExtentReport Path for Linux: " + linuxPath + "\n");
                break;
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for Remote: " + macPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
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

    //Get current platform
    private static Platform getCurrentPlatform () {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }


}



