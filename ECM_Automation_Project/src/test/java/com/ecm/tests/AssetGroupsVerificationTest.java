package com.ecm.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.util.Asserts;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ecm.util.DataProviders;
import com.ecm.util.ExcelConstants;
import com.ecm.util.ExcelReader;
import com.ecm.util.ExcelUtils;
import com.ecm.util.Locators;
import com.ecm.util.ECMUtil;
import com.ecm.util.listeners.ErrorCollector;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;



public class AssetGroupsVerificationTest  extends ECMUtil {
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
			excel = new ExcelReader(ExcelConstants.ASSETSUITE_XL_PATH);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	// Test Script is to verify Login functionality using 10duke
	@Test(priority=1)
	public void  launchBrowserAndLoginToTelematics() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
			login_10duke();
	}
	
	
	//Verify list of Assets name and associated Driver is displaying under asset panel tab
		@Test(priority=2)
		public void verifyListOfAssetGroupsOnPanel() throws IOException, InterruptedException, ClassNotFoundException, SQLException
		{
			
				APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
				clickBtn(locators.telematicsMainMenu);
				Thread.sleep(500);
				clickBtn(locators.assetGroupsSubMenu);
				waituntilnew(locators.assetGroupHeader, 20);
				String query =properties.getProperty("queryforfeatchGrouplistoption");
				Statement stmt=SQLserverConnection();

				ArrayList<String> Group_Name = new ArrayList<String>();
				ResultSet rs = stmt.executeQuery(query);	
				while(rs.next())
				{
					Group_Name.add(rs.getString(2));

				}
				APP_LOGS.info(" Group Filter list Options under data base :  " +  Group_Name);
				List<WebElement>assetGroups=locators.assetGroupsList;
				
				if (isElementExist(locators.assetGroupHeader)) 
				{
					
					for (int i = 0; i < (Group_Name.size() & assetGroups.size()); i++) 
					{
						String groupName= getobjStaticText(assetGroups.get(i));
						if (Group_Name.get(i).equals(groupName)) 
						{
							System.out.println("Asset Groups are matching from UI & DB: "+Group_Name.get(i)+"="+groupName);
						}
						
					}
				}
				clickBtn(locators.assetGroupsPanelCloseButton);
		}
		
		
		//Verify list of Assets name and associated Driver is displaying under asset panel tab
				@Test(priority=4,dataProvider="FilterFunctionalityForAssetGroupsTestData", dataProviderClass=DataProviders.class)
				public void verifyFilterFunctionalityForAssetGroups(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException
				{
					
						APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
						clickBtn(locators.telematicsMainMenu);
						Thread.sleep(500);
						clickBtn(locators.assetGroupsSubMenu);
						waituntilnew(locators.assetGroupHeader, 20);
						
						if (isElementExist(locators.createNewGroupButton )) 
						{
							Assert.assertEquals("Create new group", getobjStaticText(locators.createNewGroupButton));
						};
						
						enterTextToInputField(locators.assetGroupFilterInputbox, data.get("Filter value"));
						Thread.sleep(500);
						String[] res= data.get("No of search results").split("\\.");
						if (locators.assetGroupsOptionsList.size()==Integer.valueOf(res[0])) 
						{
							System.out.println("filter functionality is working: test results count for the Input '"+data.get("Filter value")+"' is "+res[0]);
							
						}
						
						clickBtn(locators.assetGroupsPanelCloseButton);
					
				}
				
				
				//Verify list of Assets name and associated Driver is displaying under asset panel tab
				@Test(priority=3)
				public void verifyEditAndDeleteFunctionalityForAssetGroups() throws IOException, InterruptedException, ClassNotFoundException, SQLException
				{
					
						APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
						clickBtn(locators.telematicsMainMenu);
						Thread.sleep(500);
						clickBtn(locators.assetGroupsSubMenu);
						waituntilnew(locators.assetGroupHeader, 20);
						
						List<WebElement> optionsList= locators.assetGroupsOptionsList;
						for (WebElement webElement : optionsList) 
						{
							clickBtn(webElement);
							if (isElementExist(locators.assetGroupDeleteButton)&isElementExist(locators.assetGroupEditButton)) 
							{
								Thread.sleep(500);
								clickBtn(locators.assetGroupDeleteButton);
								System.out.println("Edit & Delete Options Available for the group");
							} 
							else {
								Assert.assertFalse(false);
							}
							break;
						}
						
						clickBtn(locators.assetGroupsPanelCloseButton);
				}

				@AfterClass		
				public void Closebrowser()
				{
					cleanUp();
				}	  



}
