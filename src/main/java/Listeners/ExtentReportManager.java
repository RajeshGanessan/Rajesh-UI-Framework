package Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportManager {

    private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();


    static void setExtent(ExtentTest test){
        extTest.set(test);
    }

    static ExtentTest getTest(){
        return extTest.get();
    }

    static void unload(){
        extTest.remove();
    }
}
