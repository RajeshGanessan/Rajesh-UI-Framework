package ExtentReports;

import Utils.AppConstants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Objects;

public final class ExtentReporter {

    private static ExtentReports extent;

    public static void initReports(){
        if(Objects.nonNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(AppConstants.getReportPath());
            extent.attachReporter(sparkReporter);
            sparkReporter.config().setDocumentTitle("MSE automation");
            sparkReporter.config().setReportName("Test automation results");
            sparkReporter.config().setTheme(Theme.STANDARD);
        }
    }

    public static void flush(){
        if(Objects.nonNull(extent)) {
            ExtentManager.unload();
        }
    }

    public static void CreateTest(String testName){
        ExtentTest  test = extent.createTest(testName);
        ExtentManager.setTest(test);
    }

}
