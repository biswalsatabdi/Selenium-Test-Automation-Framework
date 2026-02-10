package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {
	private LoginPage loginpage;
	private HomePage homepage;
	@BeforeMethod
	public void setupPages() {
		LoginPage loginPage = new LoginPage(BaseClass.getActionDriver());

		homepage=new HomePage(getDriver());
	}
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {

	    ExtentManager.startTest("Valid login test");
	    System.out.println("Running testmethod1 on thread:" + Thread.currentThread().threadId());

	    ExtentManager.LogStep("Entering username and password");
	    loginpage.login(username, password);

	    ExtentManager.LogStep("Verifying admin tab visibility");
	    Assert.assertTrue(
	        homepage.isAdminTabVisible(),
	        "Admin tab should be visible after successful login"
	    );

	    ExtentManager.LogStep("Validation successful");
	    homepage.logout();
	    ExtentManager.LogStep("Logged out successfully");

	    staticWait(4);
	}

	@Test(dataProvider = "inValidLoginData", dataProviderClass = DataProviders.class)
	public void invalidLoginTest(String username, String password) {

	    System.out.println("Running testmethod2 on thread:" + Thread.currentThread().threadId());

	    ExtentManager.LogStep("Entering invalid username and password");
	    loginpage.login(username, password);

	    String expectedErrorMessage = "Invalid credentials";
	    Assert.assertTrue(
	    	    loginpage.verifyErrorMessage(expectedErrorMessage),
	    	    "Test failed: error message mismatch"
	    	);


	    ExtentManager.LogStep("Validation successful");
	}
}

