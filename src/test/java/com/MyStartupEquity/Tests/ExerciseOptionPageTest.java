package com.MyStartupEquity.Tests;

import Base.BaseTest;
import Enums.ConfigProperties;
import Pages.ExerciseOptionsPage;
import Pages.ReportsPage;
import Constants.AppConstants;
import Utils.PropertyUtils.ReadProperty;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExerciseOptionPageTest extends BaseTest {

    ReportsPage reportsPage;
    ExerciseOptionsPage exerciseOptionsPage;

    @BeforeClass
    public void Setup() {
        loginPage.doLogin(ReadProperty.getProperty(ConfigProperties.EMAIL),ReadProperty.getProperty(ConfigProperties.PASSWORD));
        reportsPage = new ReportsPage(driver);
        exerciseOptionsPage = reportsPage.goToAllGrantsPage();
    }

    @Test(priority = 1,description = "All grants Page Header")
    public void verifyAllGrantsPageHeader(){
        String allGrants = exerciseOptionsPage.getPageTitle();
        Assert.assertEquals(allGrants, AppConstants.ALL_GRANTS_HEADER);
    }

    @Test(priority = 2,description = " Getting employees")
    public void NavigatingToExerciseOptions(){
//        boolean isNavigatedToEmployeeDetails = exerciseOptionsPage.goToEmployeeDetails();
//        Assert.assertTrue(isNavigatedToEmployeeDetails);

       boolean isNavigatedToExerciseOptions =  exerciseOptionsPage
               .goToEmployeeDetails()
               .openGrantDetails()
                .goToExerciseOptionsPage();
       Assert.assertTrue(isNavigatedToExerciseOptions);
    }

    @Test(priority = 3, description = "Filling exercise Details")
    public void verifyExerciseOptions(){
        boolean isExerciseDetailsUpdated = exerciseOptionsPage
                .enterOptionsToBeExercised()
                .selectExerciseDate()
                .uploadExerciseLetter()
                .checkSuccessMessage();
        Assert.assertTrue(isExerciseDetailsUpdated);
    }



//
}
