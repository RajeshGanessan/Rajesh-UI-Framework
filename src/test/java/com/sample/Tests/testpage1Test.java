package com.sample.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.testpage1;
import Pages.PendingRequestPage;
import Constants.AppConstants;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class testpage1Test extends BaseTest {

    PendingRequestPage pendingRequestPage;
    testpage1 testpage1;

    @BeforeClass
    public void setup(){
        loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
        pendingRequestPage = new PendingRequestPage(driver);
        testpage1 = pendingRequestPage.goToVestingTablePage();
    }

    @Test(priority = 1,description = "verfiying the sample title")
    public void verifyVestingTablePageTitle(){
        String pageTitle = testpage1.getPageHeader();
        Assert.assertEquals(pageTitle,AppConstants.ALL_GRANTS_HEADER);
    }

    @Test(priority = 2,description = "Cancelling a sample")
    public void verifyGrantCancellation(){
      boolean isCancelled =   testpage1.clickCancelGrants()
        .enterCancellationReason("sample is cancelled")
        .confirmCancellation()
        .checkSuccessMessage();
      Assert.assertTrue(isCancelled,"Grant sample failed ");
    }

}
