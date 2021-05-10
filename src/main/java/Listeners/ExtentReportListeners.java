package Listeners;

import Base.Base;
import Utils.PageUtils.HelperMethods;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ExtentReportListeners  extends Base implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite has Started");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite has completed");
        extent.flush();
        test.remove();
//        try{
//        Desktop.getDesktop().browse(new File(HelperMethods).toURI());} catch (IOException e){
//            e.printStackTrace();
//        }

    }
    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Passed!!");
        test.get().pass("Test Passed");
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Failed!!");
        try{
            test.get().log(Status.FAIL, MarkupHelper.createLabel("Test case FAILED due to issue",
                    ExtentColor.RED));
            test.get().info("Failed URL ---> " + getDriver().getCurrentUrl());
            test.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath("." + takeScreenshot()).build());
//            test.get().log(Status.FAIL, (Markup) test.get().addScreenCaptureFromPath(takeScreenshot()));

        } catch (IOException e) {
            System.err.println("Exception thrown while updating test fail status " + Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Skipped!!");
        try{
            test.get().log(Status.SKIP, MarkupHelper.createLabel("Test case SKIPPED due to issue",
                    ExtentColor.YELLOW));
            test.get().skip(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        } catch (IOException e) {
            System.err.println("Exception thrown while updating test Skip status " + Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }


}
