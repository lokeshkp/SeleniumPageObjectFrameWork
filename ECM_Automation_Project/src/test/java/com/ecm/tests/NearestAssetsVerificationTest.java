package com.ecm.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
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


	public class NearestAssetsVerificationTest extends ECMUtil {

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


		// script for verifying journey view window objects
		@Test(priority=2, dataProvider="NearestassetsTestData", dataProviderClass=DataProviders.class)
		public void verifyNearestAssetsWindowObjects(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException, ParseException
		{
			
				APP_LOGS.info("\n\n--------------------verifyNearestAssetsWindowObjects--------------------\n\n");
				clickAssetOnAssetListAndShowNearestAssetsOption(data.get("Assets"), data.get("Option btn"));
				String query =properties.getProperty("queryForNearestAssets");
				Statement stmt=SQLserverConnection();

				ArrayList<String> distance = new ArrayList<String>();
				ArrayList<String> assets = new ArrayList<String>();
				ArrayList<String> locations = new ArrayList<String>();
				ArrayList<String> rank = new ArrayList<String>();
				ResultSet rs = stmt.executeQuery(query);
				int i=1;
				while(rs.next())
				{
					if (rs.getString(6) != null)
					{
						distance.add(rs.getString(6));
						assets.add(rs.getString(1));
						locations.add(rs.getString(4));
						rank.add(Integer.toString(i));
						i++;
					}
				}
				
				for (int j = 0; j < (rank.size() & assets.size() & locations.size() & distance.size() ) ; j++) 
				{
					
					System.out.println("Rank: "+rank.get(j)+"\n"+"Asset Name: "+assets.get(j)+"\n"+"Location: "+locations.get(j)+"\n"+"Distance: "+distance.get(j)+" miles"+"\n");
					
				}
				
				for (WebElement element : locators.nearestAssetItems) 
				{
					System.out.println("Rank: "+getobjStaticText(element.findElement(By.className("rank"))));
					System.out.println("Asset Name: "+getobjStaticText(element.findElement(By.className("name"))));
					System.out.println("Location: "+getobjStaticText(element.findElement(By.className("location"))));
					System.out.println("Distance: "+getobjStaticText(element.findElement(By.className("geodesic-distance")))+"\n");
					
				}
				
				for (WebElement element : locators.nearestAssetItems) 
				{
					clickBtn(element.findElement(By.className("directions")));
					for (WebElement ele : locators.travelModes) 
					{
						clickBtn(ele);
						Thread.sleep(1000);
						System.out.println("Travelling mode selected is: "+ele.getAttribute("title"));
						clickBtn(locators.directionsPanelRouteButton);
						Thread.sleep(1000);
						System.out.println("Travelling mode selected is: "+ele.getAttribute("title")+" & the Info is==> "+getobjStaticText(locators.directionsPanelInfo));
					}
					
					break;
				}
			
		}		
	
		@AfterClass		
		public void Closebrowser()
		{
			cleanUp();
		}	  

		
		
}
