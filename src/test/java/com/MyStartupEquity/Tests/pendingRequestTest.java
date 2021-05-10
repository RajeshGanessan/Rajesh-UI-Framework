package com.MyStartupEquity.Tests;
import Pages.GrantOptionPage;
import Pages.PendingRequestPage;
import Utils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Utils.AppConstants;

public class pendingRequestTest extends BaseTest {

    GrantOptionPage grantOptionPage;
    PendingRequestPage pendingRequestPage;

	@BeforeClass
	public void Setup() {
		loginPage.doLogin(ReadProperty.getProperty("email"),ReadProperty.getProperty("password"));
		grantOptionPage = new GrantOptionPage(driver);
		pendingRequestPage = grantOptionPage.goToPendingRequestPage();
	}
//
	@Test(priority=1,description="Checking Pending Request Page header")
	public void checkPendingRequestHeader() {
		String pendingRequestTitle = pendingRequestPage.getPageTitle();
		Assert.assertEquals(pendingRequestTitle, AppConstants.PENDING_REQUEST_HEADER);
	}

	@Test(priority = 2,description = "Approving GrantLetter with SignEasy")
	public void SigningGrantLetter() {
		pendingRequestPage.navigateToSignEasyPage();
		boolean isSignatureDone = pendingRequestPage.initiateSignature();
		Assert.assertTrue(isSignatureDone,"Signing Grant letter failed");
		boolean isSignComplete = pendingRequestPage.confirmSignature();
		Assert.assertTrue(isSignComplete,"Signing Grant letter Failed");

	}
}
