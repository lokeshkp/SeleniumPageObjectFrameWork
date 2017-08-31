package com.ecm.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.CaptureScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;












import com.ecm.util.*;
import com.ecm.util.listeners.ErrorCollector;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;

public class HomepageMenusAndSubmenusVerificationTest extends ECMUtil {

	Locators locators = null;
	Properties properties ;
	public ExcelReader excel;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTestInterruptedException testexception;
	
	
	@BeforeSuite
	public void beforeSuite() 
	{
		extent = new ExtentReports("src/test/resources/extentreport/extentreport.html", true); //Provide Desired Report Directory Location and Name
	}
	
	@BeforeMethod()
	public  void beforeMethod(Method method) 
	{
		test = extent.startTest( (this.getClass().getSimpleName() +" :: "+  method.getName()),method.getName()); //Test Case Start Here
		test.assignAuthor("Shivaprasad Nagaraju"); //Test Script Author Name
	}
	
	@AfterMethod()
	public void tearDown(ITestResult result,Method method) throws IOException
	{
	if(result.getStatus()==ITestResult.FAILURE)
	{
	 
	String screenshot_path=captureScreenshot(method.getName());
	String image= test.addScreenCapture(screenshot_path);
	test.log(LogStatus.FAIL,result.getThrowable());
	test.log(LogStatus.FAIL, "screenshot for failure: "+test.addScreenCapture(image));
	}
	 
	extent.endTest(test);
	extent.flush();
	}	

	@BeforeTest
	public void beforeTest() {
		try {
			properties=loadDriverAndProperties();
			locators = PageFactory.initElements(getDriver(), Locators.class);
		} catch (Exception e) {
		}
	}



	// Test Script is to verify Login functionality using 10duke
	@Test(priority=1)
	public void  launchBrowserAndLoginToTelematics() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
			login_10duke();

	}


	// Test Script is to verify Asset PanelTabs------>REF-TESCTCASE:TELE01
	@Test(priority=2)
	public void  VerifyHomepageMenus() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------Veryfyinhhomepage--------------------\n\n");
			Assert.assertTrue(isElementExist(locators.mapMainMenu), "mapmenu displayed");
			Assert.assertTrue(isElementExist(locators.telematicsMainMenu), "Telematics menu displayed");
			Assert.assertTrue(isElementExist(locators.schedulingMainMenu), "Scheduling menu displayed");
			Assert.assertTrue(isElementExist(locators.debugMainMenu), "Debug menu displayed");
			Assert.assertTrue(isElementExist(locators.userid), "Userid is displayed");
	}

	
	
	@Test(priority=3)
	public void  VerifyMapMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
			APP_LOGS.info("\n\n--------------------Verifyingmapoption--------------------\n\n");
			clickBtn(locators.mapMainMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.mapSubMenu), "mapmenu displayed");
			Assert.assertTrue(isElementExist(locators.directionsSubMenu), "Direction option displayed");
			clickBtn(locators.directionsSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.directionHeading), "Direction content is displayed");
	}

	
	
	@Test(priority=4)
	public void  VerifyTelematicsMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------Verifyingtelematicsmenuoptionstelematcs--------------------\n\n");
			clickBtn(locators.telematicsMainMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.assetsSubMenu), "Telematics menu displayed");
			Assert.assertTrue(isElementExist(locators.assetGroupsSubMenu), "Telematics asset option displayed");
			Assert.assertTrue(isElementExist(locators.driversSubMenu), "Telematics driver option menu displayed");
			Assert.assertTrue(isElementExist(locators.poisSubMenu), "Telematics POI option menu displayed");
			Assert.assertTrue(isElementExist(locators.accountRemindersSubMenu), "Telematics account reminder displayed");
			
			//click on assets
			clickBtn(locators.assetsSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.assetHeading), "asset content displayed");

			//Asset group
			clickBtn(locators.telematicsMainMenu);
			Thread.sleep(500);
			clickBtn(locators.assetGroupsSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.assetGroupHeading), "mapmenu displayed");
			
			//Drivers
			clickBtn(locators.telematicsMainMenu);
			Thread.sleep(500);
			clickBtn(locators.driversSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.driversTitle), "mapmenu displayed");

			//POI's
			clickBtn(locators.telematicsMainMenu);
			Thread.sleep(500);
			clickBtn(locators.poisSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.poiTitle), "mapmenu displayed");
			
			//Account reminders
			clickBtn(locators.telematicsMainMenu);
			clickBtn(locators.accountRemindersSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.accountRemindersTitle), "mapmenu displayed");
	}
	
	
	
	@Test(priority=5)
	public void  VerifySchedulingMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------verifyingAssetPanelTabs--------------------\n\n");
			clickBtn(locators.schedulingMainMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.timelineSubMenu), "Sub menuo option displayed");
			Assert.assertTrue(isElementExist(locators.jeopardySubMenu), "mapmenu displayed");
			
			//click on scheduling
			clickBtn(locators.timelineSubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.timelineHeading), "Sub menuo option displayed");

			//click on jeopardy
			clickBtn(locators.schedulingMainMenu);
			clickBtn(locators.jeopardySubMenu);
			Thread.sleep(500);
			Assert.assertTrue(isElementExist(locators.jeopardyHeading), "Sub menuo option displayed");
	}



	@AfterClass		
	public void Closebrowser()
	{
		cleanUp();
	}
}





