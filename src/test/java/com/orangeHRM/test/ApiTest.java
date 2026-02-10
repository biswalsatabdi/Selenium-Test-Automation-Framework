package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.ApiUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;


public class ApiTest {

    @Test(retryAnalyzer=RetryAnalyzer.class)
    public void verifyGetUserAPI() {
    	SoftAssert softAssert=new SoftAssert();

        String endpoint = "https://jsonplaceholder.typicode.com/todos/1";
        ExtentManager.LogStep("API endpoint: " + endpoint);

        Response response = ApiUtility.sendGetRequest(endpoint);

        // 🔹 Status code validation
        int actualStatusCode = response.getStatusCode();
        ExtentManager.LogStep("Actual status code: " + actualStatusCode);
        ExtentManager.LogStep("Validating status code");
        softAssert.assertEquals(actualStatusCode, 200, "Unexpected status code");


        // 🔹 userId validation (INT comparison)
        int actualUserId = response.jsonPath().getInt("userId");
        softAssert.assertEquals(actualUserId, 1, "UserId is not valid");
        ExtentManager.logStepValidationForAPI("UserId validation passed");

        // 🔹 title validation
        String actualTitle = response.jsonPath().getString("title");
        softAssert.assertEquals(actualTitle, "delectus aut autem", "Title is not valid");
        ExtentManager.logStepValidationForAPI("Title validation passed");
        
        softAssert.assertAll();

    }
   
}

