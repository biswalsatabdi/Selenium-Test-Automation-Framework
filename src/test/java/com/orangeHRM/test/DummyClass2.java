package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
//checking just
public class DummyClass2 extends BaseClass {
	@Test
	public void DummyTest2() {
//		ExtentManager.startTest("DummyTest2 Test");----This has been implemented in TestListener
		String title = getDriver().getTitle();
		ExtentManager.LogStep("verifing the title");
		Assert.assertEquals(title, "OrangeHRM", "Test Failed");
		System.out.println("Test Passed");
		ExtentManager.LogStep("validation successful");
	}
}
