package com.ecm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;


public class CommonConfig {
	private static WebDriver driver;
	public FileInputStream inputStream;
	public static final int IMPLICIT_TIMEOUT = 5;
	public static final int EXPLICIT_TIMEOUT = 25;
	public Properties properties;
	public Connection dbConn;
	public PreparedStatement prestmt;

	public static Select Selectoption = null;
	public static int invalidImageCount = 0;
	public static final String accountID = "1200050";

	private static final String LOG_PATH = "src/test/resources/properties/log4j.properties";
	public File profile = new File("src/test/resources/properties/sql.properties");
	
	public static Logger APP_LOGS = null;
	
	
	public void loggerProperties() {
		APP_LOGS=Logger.getLogger(this.getClass().getName());
	       PropertyConfigurator.configure(LOG_PATH);
	}

	public Properties setPropertiesFile() throws FileNotFoundException {
		inputStream = new FileInputStream(profile);
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static WebDriverWait waitForElement() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		return wait;
	}

	public Properties loadDriverAndProperties() throws FileNotFoundException, MalformedURLException {
		loggerProperties();
		setPropertiesFile();
		driver = getDriverInstance(false);
		maximizeWindow();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(ApplicationConstants.SELENIUM_IMPLICIT_TIMEOUT.getValue()), TimeUnit.SECONDS);
		return properties;
	}

	public static void cleanUp() {

		if (getDriver() != null) {
			getDriver().close();
			getDriver().quit();
		}
	}

	public static void takeScreenShot(Throwable t) {
		try {

			String screenshotName = "snapshot_" + System.currentTimeMillis()
					+ ".jpg";
			File screenshot = ((TakesScreenshot) getDriver())
					.getScreenshotAs(OutputType.FILE);
			String pathName = System.getProperty("user.dir")
					+ "/src/test/resources/screenshots/" + screenshotName;
			FileUtils.copyFile(screenshot, new File(pathName));
			System.out.println("SnapshotName -> " + screenshotName);
			String targetPath = "../target/downloads/" + screenshotName;
			Reporter.log("<a href=" + targetPath + " target='_blank' >"
					+ screenshotName + "</a>");
			Reporter.log(t.getMessage());
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
	}

	public WebDriver getDriverInstance(boolean incognitoMode) throws MalformedURLException {
		DesiredCapabilities capability = null;
		WebDriver newDriver = null;
		String nodeUrl=null;
		switch (ApplicationConstants.BROWSER.getValue()) {
		case "Firefox":
			FirefoxBinary firefoxbinar = new FirefoxBinary(new File("C:/Program Files (x86)/Mozilla Firefox/firefox.exe"));
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setEnableNativeEvents(true);
			firefoxProfile.setPreference("browser.download.folderList", 2);
			firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf");
			firefoxProfile.setPreference("pdfjs.disabled", true);
			firefoxProfile.setPreference("browser.downloadmanager.showWhenStarting", false);
			firefoxProfile.setPreference("plugin.state.default", 2);
			firefoxProfile.setPreference("plugins.click_to_play", true);
			firefoxProfile.setPreference("dom.disable_open_during_load", false);
			newDriver = new FirefoxDriver(firefoxbinar, firefoxProfile);
			break;

		case "Chrome":
			/*System.setProperty("webdriver.chrome.driver",
					"src/test/resources/drivers/chromedriver.exe");
			newDriver = new ChromeDriver();*/
			
			
			nodeUrl= "http://192.168.78.11:5555/wd/hub";
			capability= DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");	
			capability.setPlatform(Platform.VISTA);
			newDriver= new RemoteWebDriver(new URL(nodeUrl),capability );
			newDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			break;

		case "IE":
			System.setProperty("webdriver.ie.driver",
					"src/test/resources/drivers/IEDriverServer.exe");
			/*
			 * capability = DesiredCapabilities.internetExplorer();
			 * capability.setCapability("EnableNativeEvents", false);
			 * capability.setCapability("ignoreZoomSetting", true);
			 * capability.setCapability(InternetExplorerDriver.
			 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			 * driver=new InternetExplorerDriver(capability);
			 */

			newDriver = new InternetExplorerDriver();
		default:
			/*
			 * cap=DesiredCapabilities.chrome(); driver=new RemoteWebDriver(new
			 * URL("http://localhost:4444/wd/hub"), cap);
			 */
			APP_LOGS.info("no browser provided. Shutting down the test");
			System.exit(1);
			break;
		}
		//newDriver.get(ApplicationConstants.TELEMATICS_LOGIN_URL.getValue());
		return newDriver;
	}

	public void refreshPage() {
		getDriver().navigate().refresh();
	}

	public void maximizeWindow() {
		getDriver().manage().window().maximize();
	}

	public void deleteAllCookies() {
		getDriver().manage().deleteAllCookies();
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void startTestSuite(String sTestSuiteName) {
		APP_LOGS.info("\n\n--------------------" + sTestSuiteName+"--------------------\n\n");

		APP_LOGS.info("$$$$$$$$$$$$$$$$$$$$$$                "
				+ sTestSuiteName
				+ " suite    S---T---A---R---T---E---D                   $$$$$$$$$$$$$$$$$$$$$$");

	}

	public static void endTestSuite(String sTestSuiteName) {
		APP_LOGS.info("XXXXXXXXXXXXXXXXXXXXX                "
				+ sTestSuiteName
				+ " suite       -E---N---D---E---D-                    XXXXXXXXXXXXXXXXXXXXXXXX");

	}

	public static void startTestCase(String sTestCaseName) {

		APP_LOGS.info("######################               "
				+ sTestCaseName
				+ "     S---T---A---R---T---E---D                ########################");

	}

	public static void endTestCase(String sTestCaseName) {
		APP_LOGS.info("---------------------               "
				+ sTestCaseName
				+ "   -E---N---D---E---D-                     -------------------------");

	}

	public void clearCache() {
		getDriver().manage().deleteAllCookies();
	}

	protected static void printStackTrace(Exception e) {
		takeScreenShot(e);
		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("|       Cause     |  " + e.getCause());
		System.out.println("|       Class     |  "
				+ e.getClass().getSimpleName());
		System.out.println("|       Message   |  "
				+ WordUtils.wrap(e.getMessage(), 70));
		System.out
				.println("-----------------------------------------------------------------------");
		StackTraceElement[] elementList = e.getStackTrace();
		System.out
				.println("ATTENTION ! Below are the lines of code where the test fails");
		System.out
				.println("------------------------------------------------------------------------");
		for (int j = 0; j < elementList.length; j++) {
			if (elementList[j].getClassName().contains("com.mg")) {
				System.out.println(elementList[j]);
			}
		}
		System.out
				.println("------------------------------------------------------------------------");

		Assert.fail("Oops !!!! Some exception occurred.");
	}

	protected static void printStackTrace(Throwable t) {
		takeScreenShot(t);
		System.out
				.println("-----------------------------------------------------------------------");
		System.out.println("|       cause     |  " + t.getCause());
		System.out.println("|       Message   |  "
				+ WordUtils.wrap(t.getMessage(), 70));
		System.out
				.println("-----------------------------------------------------------------------");
		if (t instanceof SkipException) {
			throw new SkipException("Test Skipped.");
		}
		StackTraceElement[] elementList = t.getStackTrace();
		System.out
				.println("ATTENTION ! Below are the lines of code where the test fails");
		System.out
				.println("------------------------------------------------------------------------");
		for (int j = 0; j < elementList.length; j++) {
			if (elementList[j].getClassName().contains("com.mg")) {
				System.out.println(elementList[j]);
			}
		}
		System.out
				.println("------------------------------------------------------------------------");
		Assert.fail("Some Exception Occurred ..... Please check the error messages.");

	}

}
