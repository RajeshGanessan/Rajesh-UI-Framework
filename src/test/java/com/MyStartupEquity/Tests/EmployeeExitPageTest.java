package com.MyStartupEquity.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.EmployeeExitPage;
import Pages.ExerciseOptionsPage;
import Constants.AppConstants;
import Utils.TestDataUtil.DataProviderManager;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EmployeeExitPageTest extends BaseTest {
    ExerciseOptionsPage exerciseOptionsPage;
    EmployeeExitPage employeeExitPage;

    @BeforeClass
    public void setup(){
        loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
        exerciseOptionsPage = new ExerciseOptionsPage(driver);
        employeeExitPage = exerciseOptionsPage.goToEmployeeExitPage();
    }

    @Test(priority = 2,description = "Starting Exit Progress",dataProvider = "ExitProcessTypes",dataProviderClass = DataProviderManager.class)
    public void verifyStartingExitProcess(String ExitReason){
        employeeExitPage.selectEmployeeFromList("success");
        employeeExitPage.selectExitReasonFromList(ExitReason);
        employeeExitPage.selectLastWorkingDate();
        employeeExitPage.enterBufferDate("0");
        boolean isStarted = employeeExitPage.confirmExitProcess();
        Assert.assertTrue(isStarted);
        boolean isDetailsVerified = employeeExitPage.expandDetails()
                .verifyDetails();
        Assert.assertTrue(isDetailsVerified);

    }

    @AfterMethod
    public void cancelExits(){
        employeeExitPage.cancelExitProcess();
    }
}
