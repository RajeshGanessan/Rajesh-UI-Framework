package com.MyStartupEquity.Tests;


import Enums.ConfigProperties;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.Test;
import Base.BaseTest;
import Constants.AppConstants;

public class LoginTest extends BaseTest {


	@Test(priority = 1, description = "LoginPage Landing")
	public void verifyLoginPageTitle() {
		String title = loginPage.getLoginPageTitle();
		Assert.assertEquals(title,AppConstants.LOGINPAGE_TITLE,"login page title is not matched");
		}

//	@Test(priority = 2, description = "Verifying invalid Credentials",enabled =false)
//	public void verifyInvalidCredentials() {
//		boolean isFailed = loginPage.InvalidLoginCheck("hello@mail.com","Admin@123");
//		System.out.println(isFailed);
//		Assert.assertTrue(isFailed,"Invalid credentials failed");
//	}

	@Test(priority = 2, description = "CheckGoogleSignIn")
	public void checkGoogleSignIn(){
		boolean isGoogleSignIn = loginPage.checkGoogleSignIn();
		Assert.assertTrue(isGoogleSignIn,"GoogleSignInButton not displayed");
	}
	@Test(priority = 3,description = "Login flow")
	public void logging_In() {
		loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
	}

}
