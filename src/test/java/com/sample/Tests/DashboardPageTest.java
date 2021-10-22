package com.sample.Tests;


import Enums.ConfigProperties;
import Listeners.ExtentReportManager;
import Pages.DashboardPage;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Constants.AppConstants;

public class DashboardPageTest extends BaseTest {

    DashboardPage dashboardPage;

    @BeforeClass
    public void DashboardSetup() {
        dashboardPage = loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL), ReadProperty.getProperty(ConfigProperties.PASSWORD));
    }

    @Test(priority = 1, description = "Checking VestingTableHeader")
    public void vestingTableCheck() {
        String dashboardPageHeader = dashboardPage.getDashboardPageHeader();
        Assert.assertEquals(dashboardPageHeader, AppConstants.DASHBOARD_PAGE_HEADER);
        System.out.println("Dashboard page header verified");
    }

    @Test(priority = 2,description = "Checking Dashboard Footer")
    public void dashboardCheck() throws InterruptedException {

        String footerText = dashboardPage.getFooterText();
        Assert.assertEquals(footerText, AppConstants.DASHBOARD_PAGE_FOOTER);
        System.out.println("Dashboard Footer verified");
    }
}
