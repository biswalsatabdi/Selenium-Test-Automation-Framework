package com.orangehrm.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;

public class ActionDriver {
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger=BaseClass.logger;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		int explicitwait=Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitwait));
	}

//method to click an element
	public void click(By by) {
		try {
			applyBorder(by,"green");
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
			ExtentManager.LogStep("clicked an element");
			logger.info("clicked an element");
		} catch (Exception e) {
			applyBorder(by,"red");
			logger.info("Unable to click the element:" + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "unable to click element:","unable to click");
			logger.error("unable to click an element");
		} 
	}

//method to enter text into an input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			logger.info("Entered text:"+value);
		} catch (Exception e) {
			applyBorder(by,"red");
			logger.error("unable to enter the value in input box:" + e.getMessage());
		}
	}

//method to get text from an input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			return driver.findElement(by).getText();
		} catch (Exception e) {
			applyBorder(by,"red");
			logger.error("unable to get the text:" + e.getMessage());
			return "";
		}
	}

//method to compare two texts
	public boolean compareText(By by, String expectedText) {
	    try {
	        waitForElementToBeVisible(by);

	        String actualText = driver.findElement(by).getText();

	        // Debug logs
	        logger.info("EXPECTED TEXT => [" + expectedText + "]");
	        logger.info("ACTUAL TEXT   => [" + actualText + "]");

	        String expected = expectedText.trim().toLowerCase();
	        String actual = actualText.trim().toLowerCase();

	        if (actual.equals(expected)) {
	            applyBorder(by, "green");
	            logger.info("Text are matching: " + actual + " equals " + expected);
	            ExtentManager.logStepWithScreenshot(
	                BaseClass.getDriver(),
	                "compareText",
	                "Text verified successfully! " + actual + " equals " + expected
	            );
	            return true;
	        } else {
	            applyBorder(by, "red");
	            logger.info("Text are not matching: " + actual + " not equals " + expected);
	            ExtentManager.logFailure(
	                BaseClass.getDriver(),
	                "Text comparison failed!",
	                "Expected: " + expected + " | Actual: " + actual
	            );
	            return false;
	        }
	    } catch (Exception e) {
	        applyBorder(by, "red");
	        logger.error("unable to compare texts:", e);
	        return false;
	    }
	}


//method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if (isDisplayed) {
				System.out.println("Element is visible");
				return isDisplayed;
			} else {
				return isDisplayed;
			}
		} catch (Exception e) {
			applyBorder(by,"red");
			logger.error("Element is not displayed:"+e.getMessage());
			return false;
		}
	}
	//wait for the page to load
	public void waitForPageLoad(int timeOutInSec) {
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec))
			.until(webDriver -> 
			    ((JavascriptExecutor) webDriver)
			        .executeScript("return document.readyState")
			        .equals("complete")
			);
			logger.info("page loaded successfully");
		} catch (Exception e) {
			logger.error("page did not load within"+ timeOutInSec +"seconds.Exception:"+e.getMessage());
		}
	}
//scroll to an element
	public void scrollToElement(By by) {
		try {
			applyBorder(by,"green");
			JavascriptExecutor js=(JavascriptExecutor) driver;
			WebElement element=driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true)",element);
		} catch (Exception e) {
			applyBorder(by,"red");
			logger.error("unable to locate element:"+e.getMessage());
		}
	}

//wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("element is not clickable:" + e.getMessage());
		}
	}

//wait for element to be visible
	public void waitForElementToBeVisible(By by) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	//utility method to border an element
	public void applyBorder(By by, String color) {
	    try {
	        WebElement element = driver.findElement(by);

	        String script = "arguments[0].style.border='3px solid " + color + "'";
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript(script, element);

	        logger.info("Applied the border with color " + color +
	                    " to element: " + getElementDescription(by));
	    } catch (Exception e) {
	        logger.warn("failed to apply the border to an element: " +
	                    getElementDescription(by), e);
	    }
	}

//Method to get the description of an element using by locator
	private String getElementDescription(By locator) {
		//check for null driver or locator to avoid NullPointerException
		if(driver==null) {
			return "driver is not initialized.";	
		}
		if(locator==null) {
			return"locator is null";
		}
//		//find the element using the locator
		try {
			WebElement element=driver.findElement(locator);
//	//get element attributes
			String name=element.getDomProperty("name");
			String id=element.getDomProperty("id");
			String text=element.getText();
			String className=element.getDomProperty("class");
			String placeholder=element.getDomProperty("placeholder");
			//return a description based on available attributes
			if(isNotEmpty(name)) {
				return "Element with name:"+ name;
			}
			else if(isNotEmpty(id)){
				return "Element with id:"+ id;
			}
			else if(isNotEmpty(text)){
				return "Element with text:"+ truncate(text,50);
}
			else if(isNotEmpty(className)){
				return "Element with class:"+ className;	
			}
			else if(isNotEmpty(placeholder)){
				return "Element with placeholder:"+ placeholder;
			}
			else {
				return "Element locate using:"+locator.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "unable to describe element due to error:"+e.getMessage();
		}
		}

private String truncate(String text, int i) {
	// TODO Auto-generated method stub
	return null;
}

private boolean isNotEmpty(String name) {
	// TODO Auto-generated method stub
	return false;
}
	

}
