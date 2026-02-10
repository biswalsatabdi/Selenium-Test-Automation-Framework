package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.LoggerManager;
public class BaseClass {
	protected static Properties prop;
//	protected static WebDriver driver;
//	protected static ActionDriver actiondriver;
	public static ThreadLocal<WebDriver> driver=new ThreadLocal<>();
	public static ThreadLocal<ActionDriver> actiondriver=new ThreadLocal<>();
	public static final Logger logger=LoggerManager.getLogger(BaseClass.class);
	
	protected ThreadLocal<SoftAssert> softAssert=ThreadLocal.withInitial(SoftAssert::new);
	//getter method for softAssert
	public SoftAssert getSoftAssert() {
		return softAssert.get();
	}
	@BeforeSuite
	public void loadconfig() throws IOException {
		// Load the configuration file
				prop = new Properties();
				FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
				prop.load(fis);
				logger.info("config.properties file loaded");
				
				//Start the Extent report
//				ExtentManager.getReporter();-----This has been implemented in TestListener
	}
	@BeforeMethod(alwaysRun = true)
	public void setup() throws IOException {
	    System.out.println("setting up webDriver for: " + this.getClass().getSimpleName());
	    launchBrowser();
	    configureBrowser();
	    staticWait(2);

	    actiondriver.set(new ActionDriver(getDriver()));
	    logger.info("ActionDriver initialized for thread: " + Thread.currentThread().getId());
	}

	// Initialize the webdriver based on the browser defined in config.properties
	private void launchBrowser() {
	    String browser = prop.getProperty("browser");

	    if (browser.equalsIgnoreCase("chrome")) {
	        driver.set(new ChromeDriver());
	    } else if (browser.equalsIgnoreCase("firefox")) {
	        driver.set(new FirefoxDriver());
	    } else if (browser.equalsIgnoreCase("edge")) {
	        driver.set(new EdgeDriver());
	    } else {
	        throw new IllegalArgumentException("Browser not supported: " + browser);
	    }

	    ExtentManager.registerDriver(getDriver());
	    logger.info(browser + " driver initialized for thread: " + Thread.currentThread().getId());
	}

	//configure browser settings
	private void configureBrowser() {
		// Implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the driver
		getDriver().manage().window().maximize();

		// Navigate to url
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to navigate to the URL"+e.getMessage());
		}
	}
	

	@AfterMethod(alwaysRun = true)
	public void teardown() {
	    try {
	        if (driver.get() != null) {
	            driver.get().quit();
	        }
	    } catch (Exception e) {
	        logger.error("Unable to quit browser", e);
	    } finally {
	        driver.remove();
	        actiondriver.remove();
	    }

	    logger.info("WebDriver closed for thread: " + Thread.currentThread().getId());
	}

//getter method for prop
	public static Properties getProp() {
		return prop;
	}
//	//Driver getter method
//	public WebDriver getDriver() {
//		return driver;
	//}
	
	
	
	//Getter method for webdriver
	public static WebDriver getDriver() {
	    if (driver.get() == null) {
	        throw new SkipException("WebDriver not initialized for this thread");
	    }
	    return driver.get();
	}
	
	//Getter method for Actiondriver
		public static ActionDriver getActionDriver() {
			if(actiondriver.get()==null) {
				System.out.println("Actiondriver is not initialized");
				throw new IllegalStateException("Webdriver is not initialized");
			}
			return actiondriver.get();
		}
	//Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver=driver;
	}
	//static wait for pause
		public void staticWait(int seconds) {
			LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
		}
		//
		public WebDriver getDriverSafely() {
		    return driver.get(); // NO exception, may return null
		}

	}

