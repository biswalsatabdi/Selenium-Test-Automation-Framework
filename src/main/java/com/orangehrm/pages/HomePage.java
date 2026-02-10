package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;

public class HomePage {
	private ActionDriver actiondriver;
	// Define locators by using class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIdButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text()='Logout']");
	private By orangeHrmLogo = By.xpath("//div[@class='oxd-brand-banner']//img");
	private By pimTab=By.xpath("//span[text()=\"PIM\"]");
	private By employeeSearch=By.xpath("//label[text()=\"Employee Name\"]/parent::div/following-sibling::div/div/div/input");
	private By searchButton=By.xpath("//button[@type=\"submit\"]");
	private By emplFirstAndMiddleName=By.xpath("//div[@class=\"oxd-table-card\"]/div/div[3]");
	private By emplLastName=By.xpath("//div[@class=\"oxd-table-card\"]/div/div[4]");
	private By employeeId = By.xpath("//div[@class='oxd-table-card']/div/div[2]");


// initialize the Actiondriver object by passing webdriver instance
//	public HomePage(WebDriver driver) {
//		this.actiondriver = new ActionDriver(driver);
//	}
	// initialize the Actiondriver object by passing webdriver instance
	public HomePage(WebDriver driver) {
	    this.actiondriver = new ActionDriver(driver);
	}
//method to navigate to pim tab
	public void clickOnPimTab() {
		actiondriver.click(pimTab);
	}
	//method to employee search
	public void employeeSearch(String value) {
		actiondriver.enterText(employeeSearch, value);
		actiondriver.click(searchButton);
		actiondriver.scrollToElement(emplFirstAndMiddleName);
	}
//verify employee first and middle name
	public boolean verifyEmployeeFirstNameAndMiddleName(String emplFirstAndMiddleNameFromDB) {
		return actiondriver.compareText(emplFirstAndMiddleName, emplFirstAndMiddleNameFromDB);
	}

	//verify employee Lastname.
	public boolean verifyEmployeeLastName(String emplLastNameFromDB) {
		return actiondriver.compareText(emplLastName, emplLastNameFromDB);
	}
	
	// Mwthod to verify if admin tab is visible
	public boolean isAdminTabVisible() {
		return actiondriver.isDisplayed(adminTab);
	}
	

	public boolean verifyOrangeHRMlogo() {
		return actiondriver.isDisplayed(orangeHrmLogo);
	}

	// method to perform logout operation
	public void logout() {
		actiondriver.click(userIdButton);
		actiondriver.click(logoutButton);
	}
	public String getEmployeeIdFromUI() {
	    actiondriver.waitForElementToBeVisible(employeeId);
	    String empId = actiondriver.getText(employeeId).trim();
	    return empId;
	}



	}


