package com.cdn.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cdn.base.TestBase;

public class BankMangerLoginTest extends TestBase {

	@Test
	public void loginTestForBankManager() throws IOException {

		assertEqual("abc", "xyz");
		
		logger.info("Inside login test!!!");

		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("AddcustBtn_CSS"))),
				"Add customer button is present");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("OpenAccBtn_CSS"))),
				"Open Account button is present");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("CustomerBtn_CSS"))),
				"Customers button is present");
		logger.info("Login succussfully excuted!!!");
		
		//Assert.fail("Login failed");
		
	}
}
