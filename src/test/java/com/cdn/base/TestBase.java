package com.cdn.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import com.cdn.listeners.CustomListeners;
import com.cdn.utilities.ExcelReader;
import com.cdn.utilities.ExtentReportManager;
import com.cdn.utilities.TestUtil;

public class TestBase {

	/*
	 * Webdriver properties logs--log4j jar, .log, log4j.properties file reportng,
	 * extentreport DB excel mail
	 */

	public static WebDriver driver;

	// properties
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;

	// logs
	public static Logger logger = LogManager.getLogger(TestBase.class.getName());

	// excel
	public static ExcelReader excel = new ExcelReader(
			"D:\\Selenium Projects\\DataDrivenProject\\src\\test\\resources\\excel\\Testdata.xlsx");
	public static WebDriverWait wait;

	//extentreport
	public ExtentReports rep = ExtentReportManager
			.createInstence(System.getProperty("user.dir") + "\\test-output\\Reports\\" + "Extent.html");
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	@BeforeSuite
	public void setUp() {

		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				logger.info("Config file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				logger.info("OR file loaded!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (config.getProperty("browser").equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (config.getProperty("browser").equals("chrome")) {
			driver = new ChromeDriver();
			logger.info("Browser lunched!!!");
		}
		if (config.getProperty("browser").equals("ie")) {
			driver = new InternetExplorerDriver();
		}
		driver.get(config.getProperty("testurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicitWait"))));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} 
		else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} 
		else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		CustomListeners.test.get().info("Clicking on: " + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		CustomListeners.test.get().info("Typing in: " + locator + " " + "Enter value as: " + value);
	}

	public static WebElement dropdown;
	
	public void select(String locator, String value) {
		if (locator.endsWith("_CSS")) {
			dropdown= driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown= driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown= driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select= new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.get().info("Dropdow locator is: " + locator + " " + "value as: " + value);
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public void assertEqual(String actual, String expacted) throws IOException {
		try {
			Assert.assertEquals(actual, expacted);
		}
		catch(Throwable t) {
			TestUtil.captureScreenshot();
			Reporter.log("Verify failure: "+t.getMessage());
			Reporter.log("</br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img height=200 width=200 src="+TestUtil.screenshotName+"></img></a>");
			Reporter.log("</br>");
			
			test.get().fail("Verify failure mesg: "+t.getMessage());
			test.get().fail("Screenshot for failure", MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\test-output\\html\\"+TestUtil.screenshotName).build());
		}
	}

	
	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		logger.info("Browser Closed!!!");
	}
}
