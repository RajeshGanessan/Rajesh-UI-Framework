package Listeners;

import Base.Base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Calendar;
import java.util.Date;

public class ExtentReportListeners  extends Base implements ITestListener {


    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentReporter.initReports();
        System.out.println("Test Suite has Started");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite has completed");
        ExtentReporter.flushReports();
    }
    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentReporter.createTest((result.getMethod().getMethodName()),result.getMethod().getDescription());
        ExtentReportManager.getTest().assignCategory(result.getTestContext().getSuite().getName());
        ExtentReportManager.getTest().assignCategory(className);
//        test.set(ExtentReportManager.getTest());
        ExtentReportManager.getTest().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Passed!!");
        ExtentReportManager.getTest().pass("Test Passed");
        ExtentReportManager.getTest().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Failed!!");
        ExtentReportManager.getTest().log(Status.FAIL, MarkupHelper.createLabel("Test case FAILED due to issue",
                ExtentColor.RED));
        ExtentReportManager.getTest().info("Failed URL ---> " + getDriver().getCurrentUrl());
        ExtentReportManager.getTest().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromBase64String(ExtentReporter.takeScreenshot()).build());
        //ExtentReportManager.getTest().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath("." + takeScreenshot()).build());
        ExtentReportManager.getTest().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Skipped!!");
        ExtentReportManager.getTest().log(Status.SKIP, MarkupHelper.createLabel("Test case SKIPPED due to issue",
                ExtentColor.YELLOW));
        ExtentReportManager.getTest().skip(result.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromPath(ExtentReporter.takeScreenshot()).build());
        ExtentReportManager.getTest().getModel().setEndTime(getTime(result.getEndMillis()));
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
