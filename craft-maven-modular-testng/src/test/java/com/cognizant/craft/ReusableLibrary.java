package com.cognizant.craft;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Status;
import com.cognizant.framework.selenium.CraftDriver;
import com.cognizant.framework.selenium.SeleniumReport;
import com.cognizant.framework.selenium.WebDriverUtil;

/**
 * Abstract base class for reusable libraries created by the user
 * 
 * @author Cognizant
 */
public abstract class ReusableLibrary {

	private int responseStatus;
	private HttpURLConnection httpURLConnect;

	/**
	 * The {@link CraftDataTable} object (passed from the test script)
	 */
	protected CraftDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link CraftDriver} object
	 */
	protected CraftDriver driver;

	protected WebDriverUtil driverUtil;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;

	protected Map<String, String> reusableHandle;

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public ReusableLibrary(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getcraftDriver();
		this.driverUtil = scriptHelper.getDriverUtil();
		this.reusableHandle = scriptHelper.getReusablehandle();

		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}

	/**
	 * Function to check the All Broken Links available in the Page
	 * 
	 */
	protected void validateAllLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					report.updateTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					report.updateTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}

	}

	/**
	 * Function to check the All Broken Image Links available in the Page
	 * 
	 */
	protected void validateAllImageLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("img"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					report.updateTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					report.updateTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				report.updateTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}

	}

}