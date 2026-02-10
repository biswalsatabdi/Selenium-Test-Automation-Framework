package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

    private LoginPage loginpage;
    private HomePage homepage;

    @BeforeMethod
    public void setupPages() {
    	LoginPage loginPage = new LoginPage(BaseClass.getActionDriver());
        homepage = new HomePage(getDriver());
    }

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void verifyOrangeHRMLogo(String username, String password) {

        ExtentManager.LogStep("Logging into OrangeHRM");
        loginpage.login(username, password);   // ✅ FIXED

        ExtentManager.LogStep("Verifying OrangeHRM logo");
        Assert.assertTrue(
            homepage.verifyOrangeHRMlogo(),
            "OrangeHRM logo should be visible after login"
        );

        ExtentManager.LogStep("Validation successful");
        homepage.logout();
        ExtentManager.LogStep("Logged out successfully");
    }
}
