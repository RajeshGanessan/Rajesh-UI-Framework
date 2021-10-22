package com.sample.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.AllGrantsPage;
import Pages.ReportsPage;
import Constants.AppConstants;
import Utils.TestDataUtil.DataProviderManager;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReportsPageTest extends BaseTest {

    AllGrantsPage allGrantsPage;
    ReportsPage reportsPage;

    @BeforeClass
    public void setup(){
        loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
        allGrantsPage = new AllGrantsPage(driver);
        reportsPage = allGrantsPage.goToReportsPage();
    }

    @Test(priority = 1, description = "verifying sample header")
    public void verifyReportsPageHeader(){
      String reportPageHeader = reportsPage.getReportPageHeader();
      Assert.assertEquals(reportPageHeader,AppConstants.REPORTS_PAGE_HEADER,"Page header verification failed");
    }

    @Test(priority = 2,description = "verify sample-Report")
    public void verifyGrantReport(){
        reportsPage.selectReportType("Grant Report")
        .selectStartDate("January","1")
        .selectEndDate();
         boolean isFileDownloaded = reportsPage.verifyFileDownload();
         Assert.assertTrue(isFileDownloaded);
    }

    @Test(priority = 3,description = "verify sample ",dataProvider = "TransactionTypes",dataProviderClass = DataProviderManager.class)
    public void verifyTransactionReport(String transactionType){
        reportsPage.selectReportType("Transactions Report",transactionType)
        .selectStartDate("January","1")
        .selectEndDate();
        boolean isFileDownloaded = reportsPage.verifyFileDownload();
        Assert.assertTrue(isFileDownloaded);
    }

    @AfterMethod(description = "Deleting the Downloaded Reports")
    public void deleteReportFile(){
        reportsPage.isFileDeleted();
    }
}
