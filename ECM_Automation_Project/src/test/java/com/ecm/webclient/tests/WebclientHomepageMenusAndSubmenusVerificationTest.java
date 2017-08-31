package com.ecm.webclient.tests;

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

public class WebclientHomepageMenusAndSubmenusVerificationTest extends ECMUtil {

	WebclientLocators locators = null;
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
			locators = PageFactory.initElements(getDriver(), WebclientLocators.class);
		} catch (Exception e) {
		}
	}



	// Test Script is to verify Login functionality using 10duke
	@Test(priority=1)
	public void  launchBrowserAndLoginToTelematics() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
		//	login_10DukeTelematicsWebclient();
			login_TelematicsWebclient();

	}


	// Test Script is to verify Home Page Menus---->REF-TESCTCASE:TELE01
	@Test(priority=2)
	public void  verifyHomepageMenus() throws IllegalMonitorStateException, Exception
	{ 
			APP_LOGS.info("\n\n--------------------Veryfyinhhomepage--------------------\n\n");
			
			Assert.assertTrue(isElementExist(locators.viewMainMenu), "View menu is displayed");
			Assert.assertTrue(isElementExist(locators.reportsMainMenu), "Reports menu is displayed");
			Assert.assertTrue(isElementExist(locators.poiMainMenu), "POI menu is displayed");
			Assert.assertTrue(isElementExist(locators.adminMainMenu), "Admin menu is displayed");
			Assert.assertTrue(isElementExist(locators.workforceMainMenu), "Workforce menu is displayed");
			Assert.assertTrue(isElementExist(locators.mapMainMenu), "Map menu is displayed");
			Assert.assertTrue(isElementExist(locators.optionsMainMenu), "Options menu is displayed");
			Assert.assertTrue(isElementExist(locators.supportMainMenu), "Support menu is displayed");
			Assert.assertTrue(isElementExist(locators.helpMainMenu), "Support menu is displayed");
	}

	
	// Test Script is to verify View and reports Menu and Submenus---->REF-TESCTCASE:TELE01
	@Test(priority=3)
	public void  verifyViewAndReportsMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
			APP_LOGS.info("\n\n--------------------verify View And Reports Menu And Submenus--------------------\n\n");
			
			hoverOverElementandSelectSubMenu(locators.viewMainMenu, locators.assetsSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.assetsTitle), "Assets page is displayed");
			
			hoverOverElementandSelectSubMenu(locators.viewMainMenu, locators.showAlertsSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.alertsTitle), "Alerts window is displayed");
			
			hoverOverElementandSelectSubMenu(locators.viewMainMenu, locators.showDriversSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.driversTitle), "Drivers window is displayed");
			
			hoverOverElementandSelectSubMenu(locators.viewMainMenu, locators.driverPerformanceSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.driverPerformanceTitle), "Driver Performance window is displayed");
			clickBtn(locators.driverPerformanceWindowCloseButton);
			Thread.sleep(500);
			
			Assert.assertTrue(hoverOverElementOnSubMenu(locators.reportsMainMenu, locators.onlineReportsSubMenu), "Online reports submenu displayed");;
			Thread.sleep(600);
	}

	
	// Test Script is to verify POIs Menu and Submenus---->REF-TESCTCASE:TELE01
	@Test(priority=4)
	public void  verifyPOIMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------Verify POI Menu And Submenus--------------------\n\n");
			
			hoverOverElementandSelectSubMenu(locators.poiMainMenu, locators.showPOISubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.poiTitle), "POI Window is displayed");
			clickBtn(locators.poisWindowCloseButton);
			
			hoverOverElementandSelectSubMenu(locators.poiMainMenu, locators.showPOIGroupsSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.poiGroupsTitle), "poi Groups window is displayed");
			clickBtn(locators.poiGroupsWindowCloseButton);
			Thread.sleep(700);
			
			hoverOverElementandSelectSubMenu(locators.poiMainMenu, locators.uploadPOIFileSubMenu);
			Thread.sleep(600);
			Assert.assertTrue(isElementExist(locators.uploadPOITitle), "upload POI File window is displayed");
			clickBtn(locators.uploadPOICloseButton);
			Thread.sleep(700);
			
			Assert.assertTrue(hoverOverElementOnSubMenu(locators.poiMainMenu, locators.addNewPOISubMenu), "Online reports submenu displayed");;
			Thread.sleep(600);
	}
	
	
	// Test Script is to verify Admin Menu and Submenus---->REF-TESCTCASE:TELE01
	@Test(priority=5)
	public void  verifyAdminMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
			APP_LOGS.info("\n\n--------------------verify Admin Menu And Sub menus--------------------\n\n");
			
			hoverOverElementandSelectSubMenu(locators.adminMainMenu, locators.adminRemindersSubMenu);
			Thread.sleep(700);
			Assert.assertTrue(isElementExist(locators.remindersTitle), "Reminders Window is displayed");
			clickBtn(locators.remindersWindowCloseButton);
			Thread.sleep(700);
			
			hoverOverElementandSelectSubMenu(locators.adminMainMenu, locators.adminProfilesSubMenu);
			Thread.sleep(700);
			Assert.assertTrue(isElementExist(locators.speedProfilesTitle), "Speed Profiles window is displayed");
			clickBtn(locators.speedProfilesWindowCloseButton);
			Thread.sleep(700);
			
			hoverOverElementandSelectSubMenu(locators.adminMainMenu, locators.adminAssetgroupsSubMenu);
			Thread.sleep(700);
			Assert.assertTrue(isElementExist(locators.assetGroupsTitle), "Asset Groups window is displayed");
			clickBtn(locators.assetGroupsWindowCloseButton);
	}
	
	
	// Test Script is to verify Workforce Menu and Submenus---->REF-TESCTCASE:TELE01
	@Test(priority=6)
	public void  verifyWorkforceMenuAndSubmenus() throws IllegalMonitorStateException, Exception
	{ 
			APP_LOGS.info("\n\n--------------------verify Workforce Menu And Sub menus--------------------\n\n");
			
			hoverOverElementandSelectSubMenu(locators.workforceMainMenu, locators.workforceShowJobsSubMenu);
			Thread.sleep(700);
			Assert.assertTrue(isElementExist(locators.jobsTitle), "JObs Window is displayed");
			clickBtn(locators.jobsWindowCloseButton);
			Thread.sleep(700);
			
			hoverOverElementandSelectSubMenu(locators.workforceMainMenu, locators.workforceShowJobEventsSubMenu);
			Thread.sleep(700);
			Assert.assertTrue(isElementExist(locators.jobEventsTitle), "Speed Profiles window is displayed");
			clickBtn(locators.jobEventsWindowCloseButton);
			Thread.sleep(700);
			
	}
	
	
	// Test Script is to verify Map,Options,Support & Help Menu and Submenus---->REF-TESCTCASE:TELE01
		@Test(priority=7)
		public void  verifyMapOptionsSupportAndHelpMenuAndSubmenus() throws IllegalMonitorStateException, Exception
		{ 
				APP_LOGS.info("\n\n--------------------verify Map,Options,Support & Help Menu and Submenus--------------------\n\n");
				
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.mapMainMenu, locators.roadSubMenu), "Road submenu displayed");
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.roadSubMenu);
				Thread.sleep(700);
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.mapMainMenu, locators.terrainSubMenu), "Terrain submenu displayed");
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.terrainSubMenu);
				Thread.sleep(700);
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.mapMainMenu, locators.satelliteSubMenu), "Satellite submenu displayed");
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.satelliteSubMenu);
				Thread.sleep(700);
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.mapMainMenu, locators.hybridSubMenu), "Hybrid SubMenu displayed");
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.hybridSubMenu);
				Thread.sleep(700);
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.mapMainMenu, locators.trafficSubMenu), "Traffic SubMenu displayed");
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.trafficSubMenu);
				Thread.sleep(700);
				hoverOverElementandSelectSubMenu(locators.mapMainMenu, locators.roadSubMenu);
				Thread.sleep(700);
				
				hoverOverElementandSelectSubMenu(locators.optionsMainMenu, locators.optionsUserPreferencesSubMenu);
				Thread.sleep(700);
				Assert.assertTrue(isElementExist(locators.userPreferencesTitle), "User Preferences Title Window is displayed");
				clickBtn(locators.userPreferencesWindowCloseButton);
				Thread.sleep(700);
				
				clickBtn(locators.supportMainMenu);
				Thread.sleep(5000);
				waituntilnew(locators.supportPortalTitle, 40);
				Assert.assertTrue(isElementExist(locators.supportPortalTitle), "Support Portal window is displayed");
				clickBtn(locators.supportPortalWindowCloseButton);
				Thread.sleep(700);
				
				Assert.assertTrue(hoverOverElementOnSubMenu(locators.helpMainMenu, locators.helpReportProblemSubMenu));
				Thread.sleep(700);
				hoverOverElementandSelectSubMenu(locators.helpMainMenu, locators.helpAboutSubMenu);
				Thread.sleep(800);
				Assert.assertTrue(isElementExist(locators.helpAboutTitle), "Help About window is displayed");
				clickBtn(locators.helpAboutWindowCloseButton);
				Thread.sleep(700);
				
		}
	

	@AfterClass		
	public void Closebrowser()
	{
		cleanUp();
	}
}





