package com.cdn.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.cdn.base.TestBase;
import com.cdn.utilities.TestUtil;

public class AddCustomersTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) {
		// Setting up run modes for test data and implementing parameterization
		
		if(!data.get("Runmode").equals("Y")) {
			throw new SkipException("Skipping the test as the Runmode is No");
		}
		
		// driver.findElement(By.cssSelector(OR.getProperty("AddcustBtn"))).click();
		logger.info("Clicking add customer button");
		click("AddcustBtn_CSS");
		// driver.findElement(By.cssSelector(OR.getProperty("Firstname"))).sendKeys(firstName);
		logger.info("Enter first value");
		type("Firstname_CSS", data.get("firstname"));
		// driver.findElement(By.cssSelector(OR.getProperty("Lastname"))).sendKeys(lastName);
		logger.info("Enter last value");
		type("Lastname_CSS", data.get("lastname"));
		// driver.findElement(By.cssSelector(OR.getProperty("Postcode"))).sendKeys(postCode);
		logger.info("Enter postCode value");
		type("Postcode_CSS", data.get("postcode"));
		// driver.findElement(By.cssSelector(OR.getProperty("AddBtn"))).click();
		logger.info("Clicking Add customer");
		click("AddBtn_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alertText")));

		alert.accept();

	}

}
