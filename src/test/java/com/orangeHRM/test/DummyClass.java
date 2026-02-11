package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class DummyClass extends BaseClass {
	@Test
	public void DummyTest() {
//		ExtentManager.startTest("DummyTest1 Test");-----This has been implemented in TestListener
		//Test checking
		String title = getDriver().getTitle();
		ExtentManager.LogStep("verifing the title");
		Assert.assertEquals(title, "OrangeHRM", "Test Failed");
		System.out.println("Test Passed");
		ExtentManager.logSkip("This case is skipped");
		throw new SkipException("skipping the test as part of the testing");

	}
}
