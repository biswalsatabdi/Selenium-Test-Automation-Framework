package com.orangehrm.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class TestListener implements ITestListener{
//Triggered when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.startTest(testName);
		ExtentManager.LogStep("Test started:"+testName);
		
	}
//Triggered when a test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		if(!result.getTestClass().getName().toLowerCase().contains("api")) {
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End:"+testName+" - ✔️Test Passed");
		}
		else {
			ExtentManager.logStepValidationForAPI("Test End:"+testName+" - ✔️Test Passed");
		}
		
	}
	//Triggered when a test fails
	@Override
	public void onTestFailure(ITestResult result) {

	    Object testClass = result.getInstance();

	    if (testClass instanceof BaseClass) {
	        WebDriver driver = ((BaseClass) testClass).getDriverSafely();

	        if (driver != null) {
	            // take screenshot
	        	 ExtentManager.takeScreenShot(driver, result.getName());
	        }
	    }
	}

	
//Triggered when a test skipped
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped:"+testName);
	}

	//Triggered when a suite starts
	@Override
	public void onStart(ITestContext context) {
		//initialize the extentreport
		ExtentManager.getReporter();
	}
	
//Triggered when the suite end
	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endTest();	
		
	}
	
	

}
