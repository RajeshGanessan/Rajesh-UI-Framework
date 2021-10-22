package com.sample.Tests;

import Enums.ConfigProperties;
import Pages.GrantOptionPage;
import Utils.PropertyUtils.ReadProperty;
import org.testng.IMethodInterceptor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.EmployeesPage;
import Constants.AppConstants;
import junit.framework.Assert;

public class grantOptionTest extends BaseTest {

	EmployeesPage employeesPage;
	GrantOptionPage grantOptionsPage;

	@BeforeClass
	public void setup() {
//		getData = new ExcelUtils("GrantOptions");
		loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
		employeesPage = new EmployeesPage(driver);
		grantOptionsPage = employeesPage.goToGrantOptionsPage();
	}

	@Test(priority = 1, description = "Checking grant Options page header")
	public void checkGrantOptionsHeader() {
		String grantOptionsHeader = grantOptionsPage.getPageHeader();
		Assert.assertEquals(AppConstants.GRANT_OPTIONS_HEADER,grantOptionsHeader);
	}

	@Test(priority = 2,description ="Creating sample 1")
	public void createExistingGrant() {
		grantOptionsPage.selectEmployeeFromList("success")
		.selectEquityScheme("ESOP 2021")
		.setSelectVestingField("Vesting annual")
		.selectTypeOfGrant(GrantOptionPage.GrantType.EXISTINGGRANT)
		.uploadGrantLetter()
		.selectGrantDate("Existing")
		.enterOptionsGranted()
		.enterExercisePrice()
		.confirmGrant();
		Assert.assertTrue("Existing Grant Creation Failed", grantOptionsPage.checkSuccessMessage());
	}

	@Test(priority = 3,description="Creating sample 2",invocationCount = 2)
	public void createFreshGrant() {
		grantOptionsPage.clearAndRefresh()
		.selectEmployeeFromList("success")
		.selectEquityScheme("ESOP 2021")
		.setSelectVestingField("Vesting annual")
		.selectTypeOfGrant(GrantOptionPage.GrantType.FRESHGRANT)
		.selectGrantDate("Fresh")
		.enterOptionsGranted()
		.enterExercisePrice()
		.confirmGrant();
		Assert.assertTrue("Fresh Grant Creation Failed", grantOptionsPage.checkSuccessMessage());
	}
}
