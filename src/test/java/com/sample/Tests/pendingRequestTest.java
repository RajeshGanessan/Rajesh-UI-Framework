package com.sample.Tests;
import Enums.ConfigProperties;
import Pages.GrantOptionPage;
import Pages.PendingRequestPage;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Constants.AppConstants;

public class pendingRequestTest extends BaseTest {

    GrantOptionPage grantOptionPage;
    PendingRequestPage pendingRequestPage;

	@BeforeClass
	public void Setup() {
		loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
		grantOptionPage = new GrantOptionPage(driver);
		pendingRequestPage = grantOptionPage.goToPendingRequestPage();
	}
//
	@Test(priority=1,description="Checking sample header")
	public void checkPendingRequestHeader() {
		String pendingRequestTitle = pendingRequestPage.getPageTitle();
		Assert.assertEquals(pendingRequestTitle, AppConstants.PENDING_REQUEST_HEADER);
	}

	@Test(priority = 2,description = "Approving sample SignEasy")
	public void SigningGrantLetter() {
		pendingRequestPage.navigateToSignEasyPage();
		boolean isSignatureDone = pendingRequestPage.initiateSignature();
		Assert.assertTrue(isSignatureDone,"Signing Grant letter failed");
		boolean isSignComplete = pendingRequestPage.confirmSignature();
		Assert.assertTrue(isSignComplete,"Signing Grant letter Failed");

	}
}
