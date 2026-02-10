package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class LoginPage {

    private ActionDriver actiondriver;

    public LoginPage(ActionDriver actiondriver) {
        this.actiondriver = actiondriver;
    }

//Define locators by using class
    private By userNameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

	// initialize the Actiondriver object by passing webdriver instance
//	public LoginPage(WebDriver driver) {
//		this.actiondriver = new ActionDriver(driver);
//	}
    public void login(String userName, String password) {
        actiondriver.enterText(userNameField, userName);
        actiondriver.enterText(passwordField, password);
        actiondriver.click(loginButton);
    }
	// method to check if error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actiondriver.isDisplayed(errorMessage);
	}

	// Method to get the text from error message
	public String getErrorMessageText() {
		return actiondriver.getText(errorMessage);
	}

	// verify if error is correct or not
	public boolean verifyErrorMessage(String expectedError) {
		 return actiondriver.compareText(errorMessage, expectedError);

	}
	
}
