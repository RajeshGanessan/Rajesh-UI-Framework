//package com.MyStartupEquity.Tests;
//
//import Base.BaseTest;
//import Pages.LoginPage;
//import Pages.employeeExitsPage;
//import Utils.AppConstants;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import Pages.manageExitProcessPage;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//
//public class  exitProcessTest extends BaseTest  {
//
//    public exitProcessTest() throws IOException {
//        super();
//    }
//
//
//    @BeforeMethod
//    public void setup() throws InterruptedException, IOException {
//
//        page.getInstance(LoginPage.class).doLogin(AppConstants.LOGIN_EMAIL, AppConstants.LOGIN_PASSWORD);
//        page.getInstance(manageExitProcessPage.class).goToEmployeeExitsPage();
//    }
//
//    @Test(priority = 1, description = "Checking the ExitProcess Header")
//    public void CheckExitProcessHeader(){
//
//        String exitPageHeader = page.getInstance(employeeExitsPage.class).getEmployeeExitHeader();
//    }
//    @Test
//    public void StartExitProcess(){
//
//      boolean ExitProcess  =  page.getInstance(employeeExitsPage.class).startEmployeeExit("7", employeeExitsPage.ExitReason.RESIGNATION);
//        Assert.assertTrue(ExitProcess);
//
//    }
//}
