package com.MyStartupEquity.Tests;

import Enums.ConfigProperties;
import Pages.VestingSchedulePage;
import Utils.TestDataUtil.FakerData;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.EmployeesPage;
import Constants.AppConstants;

public class EmployeesPageTest extends BaseTest {

    VestingSchedulePage vestingSchedulePage;
    EmployeesPage employeesPage;

	@BeforeClass
	public void setup() {
		faker = new FakerData();
        loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
        vestingSchedulePage = new VestingSchedulePage(driver);
        employeesPage =  vestingSchedulePage.goToEmployeesPage();

	}

	@Test(priority = 1, description = "Checking Employees page header")
	public void CheckingEmployeeBookHeader() {

		String EmpPageHeader = employeesPage.getEmployeesPageHeader();
		Assert.assertEquals(EmpPageHeader, AppConstants.EMPLOYEES_HEADER);
	}

	@Test(description = "Adding New Employee",priority = 2)
	public void addNewEmployee() {
		Assert.assertTrue(employeesPage.GoToAddEmployeePage());
		employeesPage.setEmployeeName(faker.getEmpName())
				     .setEmployeeId(faker.generateId())
				     .setEmpEmail(faker.getEmailAddress())
		             .setEmployeePhoneNumber(faker.getPhoneNumber());
		employeesPage.setEmployeeAddress(faker.getAddress());
		employeesPage.selectNationality("India");
		employeesPage.selectJoiningDate();
		employeesPage.setPanNumber("ASDFG3456K");
		employeesPage.setEmployeeDesignation("Product Engineer");
		Assert.assertTrue(employeesPage.checkSuccessMessage(),"Adding Employee Failed");
	}

// Sear
//	@Test(description = "Searching for an Employee",dependsOnMethods = "addNewEmployee")
//	public void SearchEmployee() {
//		boolean employeeSearch = employeesPage.searchEmployee("suhail");
//		Assert.assertTrue(employeeSearch,"Employee Search Failed! ");
//	}

}
