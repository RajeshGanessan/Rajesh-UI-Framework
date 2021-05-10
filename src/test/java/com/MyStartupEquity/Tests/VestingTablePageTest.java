package com.MyStartupEquity.Tests;

import Base.BaseTest;
import Pages.AllGrantsPage;
import Pages.PendingRequestPage;
import Utils.AppConstants;
import Utils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VestingTablePageTest extends BaseTest {

    PendingRequestPage pendingRequestPage;
    AllGrantsPage allGrantsPage;

    @BeforeClass
    public void setup(){
        loginPage.doLogin(ReadProperty.getProperty("email"),ReadProperty.getProperty("password"));
        pendingRequestPage = new PendingRequestPage(driver);
        allGrantsPage = pendingRequestPage.goToVestingTablePage();
    }

    @Test(priority = 1,description = "verfiying the vestingPage title")
    public void verifyVestingTablePageTitle(){
        String pageTitle = allGrantsPage.getPageHeader();
        Assert.assertEquals(pageTitle,AppConstants.ALL_GRANTS_HEADER);
    }

    @Test(priority = 2,description = "Cancelling a grant")
    public void verifyGrantCancellation(){
        allGrantsPage.clickCancelGrant();
        allGrantsPage.enterCancellationReason("Grant is cancelled");
        allGrantsPage.confirmCancellation();
        allGrantsPage.checkSuccessMessage();
    }

}
