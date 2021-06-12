package com.MyStartupEquity.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.EmployeeHomePage;
import Constants.AppConstants;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EmployeeHomePageTest extends BaseTest {

    EmployeeHomePage employeeHomePage;

    @BeforeClass
    public void setup() {
//		getData = new ExcelUtils("GrantOptions");
       employeeHomePage =  loginPage.doLoginToEmployeeHomePage(ReadProperty.getProperty(ConfigProperties.EMPEMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
    }

    @Test(priority = 1,description = "Checking employee dashboard header")
    public void verifyEmpDashboardHeader(){
        String empDashboardPageHeader = employeeHomePage.getHomePageHeader();
        Assert.assertEquals(empDashboardPageHeader, AppConstants.DASHBOARD_PAGE_HEADER);
    }

    @Test(priority=2,description = "Calculate AmountToBePaid for Exercise")
    public void verifyAmountToBePaid(){
        String grantOptionsHeader = employeeHomePage.getGrantOptionsHeader();
        Assert.assertEquals(grantOptionsHeader,AppConstants.GRANTED_OPTIONS_HEADER);
        double expectedAmountPaid = employeeHomePage.getAmountToBePaidValue();
        double actualAmountPaid = employeeHomePage.calculateAmountToBePaid();
        Assert.assertEquals(actualAmountPaid,expectedAmountPaid," Actual amount paid is not equal to expected");
    }

    @Test(priority = 4,description = "Verifying submit exercise options")
    public void verifySubmitExerciseOptions(){
        boolean isExerciseSubmitted = employeeHomePage.goToExercisePopup()
                .enterOptionsToExercise()
                .submitAndVerifyExerciseOptions();
        Assert.assertTrue(isExerciseSubmitted,"Submitting Exercise options failed");
    }

    @Test(priority = 3, description = "DownloadingDashboardReport")
    public void verifyDashboardReportDownload(){
        boolean isDownloaded = employeeHomePage.verifyFileDownload(AppConstants.FILEDOWNLOADPATH);
        Assert.assertTrue(isDownloaded);
    }

    @AfterTest
    public void deleteReportFile(){
        employeeHomePage.isFileDeleted();
    }


}
