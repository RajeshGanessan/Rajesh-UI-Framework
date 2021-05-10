//package com.MyStartupEquity.Tests;
//
//import static org.testng.Assert.assertEquals;
//
//import java.io.IOException;
//
//import Pages.LoginPage;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import Base.BaseTest;
//import Pages.manageValuationPage;
//import Pages.pendingRequestPage;
//import Utils.AppConstants;
//
//public class fundingRoundTest extends BaseTest {
//
//	public fundingRoundTest() throws IOException {
//		super();
//	}
//
//
//	@BeforeClass
//	public void setup() throws InterruptedException {
//		page.getInstance(LoginPage.class).doLogin(AppConstants.LOGIN_EMAIL, AppConstants.LOGIN_PASSWORD);
//		page.getInstance(pendingRequestPage.class).goToFundingRoundPage();
//	}
//
//	@Test(priority=1,description="Checking Manage Valuation page Header")
//	public void checkValuationPageheader() {
//		String header = page.getInstance(manageValuationPage.class).getPageTitle();
//		Assert.assertEquals(header, "Manage Valuations");
//	}
//
//	@Test(description ="Adding valuation",dependsOnMethods = "checkValuationPageheader")
//	public void addNewValuation() {
//		boolean addNewValuation = page.getInstance(manageValuationPage.class).addNewValuation(faker.getFundingRoundName(),
//				faker.getFundingShares(), faker.getPostMoney(), false);
//		Assert.assertTrue(addNewValuation);
//
//	}
//
//	@Test(priority=3,description = "Adding valuation with postMoney option2",dependsOnMethods = "checkValuationPageheader")
//	public void addNewValuationWithPostMoneyOption() {
//
//		boolean addNewValuation = page.getInstance(manageValuationPage.class).addNewValuation(faker.getFundingRoundName(),
//				faker.getFundingShares(), faker.getPostMoney(), true);
//		Assert.assertTrue(addNewValuation);
//	}
//
//}
