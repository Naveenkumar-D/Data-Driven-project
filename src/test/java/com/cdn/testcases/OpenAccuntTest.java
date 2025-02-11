package com.cdn.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.cdn.base.TestBase;
import com.cdn.utilities.TestUtil;

public class OpenAccuntTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) {

		// Setting up run modes for test suites
		/*
		 * if(!TestUtil.isTestRunnable("openAccountTest", excel)) { 
		 * throw new SkipException("Skipping the test"+"openAccuntTest".toUpperCase()+"as the Runmode is No"); }
		 */

		if(!data.get("Runmode").equals("Y")) {
			throw new SkipException("Skipping the test as the Runmode is No");
		}
		
		click("OpenAccBtn_CSS");
		select("Customer_CSS", data.get("Customer"));
		select("Currency_CSS", data.get("Currency"));
		click("Process_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		// Assert.assertTrue(alert.getText().contains(alertText));

		alert.accept();
	}
}
