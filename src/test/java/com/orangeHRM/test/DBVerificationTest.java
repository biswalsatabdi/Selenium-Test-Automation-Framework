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
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class DBVerificationTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    @BeforeMethod
    public void setupPages() {
    	loginpage = new LoginPage(getDriver());
		homepage  = new HomePage(getDriver());
    }
	
	@Test(dataProvider="emplVerification", dataProviderClass = DataProviders.class)
	public void verifyEmployeeNameVerificationFromDB(String emplID, String empName) {
		
		SoftAssert softAssert = getSoftAssert();
		
		ExtentManager.LogStep("Logging with Admin Credentails");
		loginpage.login(prop.getProperty("username"), prop.getProperty("password"));
		
		ExtentManager.LogStep("click on PIM tab");
		homepage.clickOnPIMTab();
		
		ExtentManager.LogStep("Search for Employee");
		homepage.employeeSearch(empName);
		staticWait(1);
		
		ExtentManager.LogStep("Get the Employee Name from DB");
		String employee_id=emplID;
		
		//Fetch the data into a map
		
		Map<String,String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
		
		String emplFirstName = employeeDetails.get("firstName");
		String emplMiddleName = employeeDetails.get("middleName");
		String emplLastName = employeeDetails.get("lastName");
		
		String emplFirstAndMiddleName =(emplFirstName+" "+emplMiddleName).trim();
		
		//Validation for first and middle name
		ExtentManager.LogStep("Verify the employee first and middle name");
		softAssert.assertTrue(homepage.verifyEmployeeFirstAndMiddleName(emplFirstAndMiddleName),"First and Middle name are not Matching");
		
		//validation for last name
		ExtentManager.LogStep("Verify the employee last name");
		softAssert.assertTrue(homepage.verifyEmployeeLastName(emplLastName));
		
		ExtentManager.LogStep("DB Validation Completed");
		
		softAssert.assertAll();

	}

}