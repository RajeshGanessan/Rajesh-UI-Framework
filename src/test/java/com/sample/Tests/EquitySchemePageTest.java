package com.sample.Tests;

import java.io.IOException;

import Enums.ConfigProperties;
import Pages.DashboardPage;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.EquitySchemePage;
import Constants.AppConstants;
import Utils.TestDataUtil.ExcelUtils;

public class EquitySchemePageTest extends BaseTest {

    DashboardPage dashboardPage;
    EquitySchemePage equitySchemePage;

	@BeforeClass
	public void setup()  {
		getData = new ExcelUtils("ESOPscheme");
		dashboardPage = loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL), ReadProperty.getProperty(ConfigProperties.PASSWORD));
		equitySchemePage = dashboardPage.goToEquitySchemePage();
	}


	@Test(priority = 1)
	public void verifySchemePageHeader() {
	    String schemePageHeader = equitySchemePage.getEquityPageHeader();
		Assert.assertEquals(schemePageHeader, AppConstants.ESOP_HEADER);
	}

	@Test(priority = 2)
	public void CreateEquityScheme() throws IOException {

	    equitySchemePage.selectSchemeType(EquitySchemePage.SchemeTypes.ESOP);
	   boolean isSchemeCreated =  equitySchemePage.
			   fillingSchemeDetails(getData.getCellData(1,0),getData.getCellData(1,1),"400");
	   Assert.assertTrue(isSchemeCreated,"Scheme Creation Failed!!!");
	}

	@Test(priority=3,enabled = false)
	public void verifyAddedSchemeDetails() {
		boolean isSchemeVerified  = equitySchemePage.checkExpandedView();
		Assert.assertTrue(isSchemeVerified,"Added schemeDetails verification failed!!");
	}

//	@DataProvider()
}
