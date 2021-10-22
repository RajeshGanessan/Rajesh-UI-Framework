package com.sample.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.EquitySchemePage;
import Pages.VestingSchedulePage;
import Utils.TestDataUtil.ExcelUtils;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Constants.AppConstants;

public class VestingSchedulePageTest extends BaseTest {

    EquitySchemePage equitySchemePage;
    VestingSchedulePage vestingSchedulePage;

	@BeforeClass
	public void setup() {
		getData = new ExcelUtils("VestingSchedules");
		loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
		equitySchemePage = new EquitySchemePage(driver);
		vestingSchedulePage = equitySchemePage.goToVestingSchedulePage();
	}

	@Test(priority = 1, description = "Checking Vesting schedule header")
	public void checkVestingScheduleHeader() {
		String VHeader = vestingSchedulePage.getVestingSchedulePageHeader();
		Assert.assertEquals(VHeader, AppConstants.VESTING_SCHEDULE_HEADER);
	}

	@Test(priority = 2,description = "Creating new Vesting Schedule")
	public void createNewVestingSchedule() {
		boolean isVestingScheduleAdded = vestingSchedulePage.createVestingSchedule(getData.getCellData(1,0),
                                        getData.getCellData(1,1),getData.getCellData(1,2),
                                        getData.getCellData(1,3));
		Assert.assertTrue(isVestingScheduleAdded);
	}

	@Test(priority = 3, description = "Creating Vesting schedule with Custom vesting Options")
	public void CreateVestingScheduleWithCustomVesting() {
        boolean isCustomVestingAdded = vestingSchedulePage.createVestingSchedule("vcustomm","36",
                                "Annually","12","20","30","50");
		Assert.assertTrue(isCustomVestingAdded);
//		test.get().info("Created New Vesting schedule with custom Vesting Options successfully");
	}

}
