package com.MyStartupEquity.Tests;

import Base.BaseTest;
import Pages.ExerciseOptionsPage;
import Pages.GrantOptionPage;
import Pages.ReportsPage;
import Utils.AppConstants;
import Utils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExerciseOptionPageTest extends BaseTest {

    ReportsPage reportsPage;
    ExerciseOptionsPage exerciseOptionsPage;

    @BeforeClass
    public void Setup() {
        loginPage.doLogin(ReadProperty.getProperty("email"),ReadProperty.getProperty("password"));
        reportsPage = new ReportsPage(driver);
        exerciseOptionsPage = reportsPage.goToAllGrantsPage();
    }

    @Test(priority = 1,description = "All grants Page Header")
    public void verifyAllGrantsPageHeader(){
        String allGrants = exerciseOptionsPage.getPageTitle();
        Assert.assertEquals(allGrants, AppConstants.ALL_GRANTS_HEADER);
    }

    @Test(priority = 2,description = " getting employees")
    public void NavigatingToExerciseOptions(){
        boolean isNavigatedToEmployeeDetails = exerciseOptionsPage.goToEmployeeDetails();
        Assert.assertTrue(isNavigatedToEmployeeDetails);
       boolean isNavigatedToExerciseOptions =  exerciseOptionsPage.openGrantDetails()
                .goToExerciseOptionsPage();
       Assert.assertTrue(isNavigatedToExerciseOptions);

    }



//
}
