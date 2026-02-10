package com.orangeHRM.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.ExtentManager;

public class DBVerificationTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    @BeforeMethod
    public void setupPages() {
    	LoginPage loginPage = new LoginPage(BaseClass.getActionDriver());
        homepage = new HomePage(getDriver());
    }

    @Test
    public void verifyEmployeeNameFromDB() {
    	SoftAssert softAssert=getSoftAssert();

        ExtentManager.LogStep("Logging in with Admin credentials");
        loginpage.login(prop.getProperty("username"), prop.getProperty("password"));

        ExtentManager.LogStep("Click on PIM tab");
        homepage.clickOnPimTab();

        ExtentManager.LogStep("Search for employee in UI");
        homepage.employeeSearch("Satabdi");

        ExtentManager.LogStep("Fetch Employee ID dynamically from UI");
        String employee_id = homepage.getEmployeeIdFromUI();

        ExtentManager.LogStep("Fetch employee details from DB using Employee ID: " + employee_id);
        Map<String, String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);

        String emplFirstName = employeeDetails.get("firstName");
        String emplMiddleName = employeeDetails.get("middleName");
        String emplLastName = employeeDetails.get("lastName");

        String emplFirstAndMiddleName =
                (emplFirstName + " " + emplMiddleName).trim();

        ExtentManager.LogStep("Verify employee First and Middle name");
        softAssert.assertTrue(
                homepage.verifyEmployeeFirstNameAndMiddleName(emplFirstAndMiddleName),
                "First and Middle name are not matching"
        );

        ExtentManager.LogStep("Verify employee Last name");
        softAssert.assertTrue(
                homepage.verifyEmployeeLastName(emplLastName),
                "Last name is not matching"
        );

        ExtentManager.LogStep("DB verification completed successfully");
        softAssert.assertAll();
    }
}
