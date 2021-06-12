package ExtentReports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

    private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();

     static ExtentTest getTest(){
        return extTest.get();
    }

     static void setTest(ExtentTest test){
        extTest.set(test);
    }

     static void unload(){
        extTest.remove();
    }
}
