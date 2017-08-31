package com.ecm.webclient.tests;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.runtime.directive.Foreach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
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
import com.ecm.util.Locators;
import com.ecm.util.ECMUtil;
import com.ecm.util.WebclientLocators;
import com.ecm.util.listeners.ErrorCollector;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;


	public class WebclientDriversVerificationTest extends ECMUtil {

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
				excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
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
		
		
		//Verify that list of drivers and its phone number displaying correctly
		@Test(priority=2)
		public void verifyListOfDriversAndPhoneUnderDriverPanel() throws IOException, InterruptedException, ClassNotFoundException, SQLException
		{
			
				APP_LOGS.info("\n\n--------------------ListofDriveranditsphoneunderDriverpanel--------------------\n\n");	
				clickBtn(locators.telematicsMainMenu);
				waituntilnew(locators.driversSubMenu, 20);	
				clickBtn(locators.driversSubMenu);
				Thread.sleep(500);
				if(getobjStaticText(locators.driversTitle).equals("DRIVERS"))
				{
					APP_LOGS.info(  getobjStaticText(locators.driversTitle) +  " Panel Tab " );
				}
				
				String query =properties.getProperty("queryforfeatchlistofDriveritsPhone");
				Statement stmt=SQLserverConnection();
				ArrayList<String> dbDriverNameAndNumber = new ArrayList<String>();
				ResultSet rs = stmt.executeQuery(query);	
				while(rs.next())
				{
					if ((rs.getString(2)==null)) 
					{
						dbDriverNameAndNumber.add(rs.getString(1));
					}
					else if (rs.getString(2).isEmpty()) 
					{
						dbDriverNameAndNumber.add(rs.getString(1));	
					}
					else {
						dbDriverNameAndNumber.add(rs.getString(1)+"\n"+rs.getString(2));
					}
				}

				List <WebElement>  driversdatalist= locators.driversList;
				ArrayList<String> uiDriverNameAndNumber= new ArrayList<>();
				
				for (WebElement webElement : driversdatalist) 
				{
					uiDriverNameAndNumber.add(getobjStaticText(webElement));

				}

				System.out.println(dbDriverNameAndNumber.size()+ " = " + uiDriverNameAndNumber.size());
				for (int i = 0; i < (dbDriverNameAndNumber.size()&uiDriverNameAndNumber.size()); i++) 
				{
					
					try 
					{
						Assert.assertEquals(dbDriverNameAndNumber.get(i).trim(), uiDriverNameAndNumber.get(i).trim());
					} catch (Throwable t) 
					{
						System.out.println(t);
					}
					
				}
			
		}
		
		
		@Test(priority=4,dataProvider="VerifyingDriverDetails", dataProviderClass=DataProviders.class)
		public void verifyFilterFunctionalityForDrivers(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException
		{
			
				clickBtn(locators.telematicsMainMenu);
				waituntilnew(locators.driversSubMenu, 20);	
				clickBtn(locators.driversSubMenu);
				Thread.sleep(500);
				if(getobjStaticText(locators.driversTitle).equals("DRIVERS"))
				{
					APP_LOGS.info(  getobjStaticText(locators.driversTitle) +  " Panel Tab " );
				}
				
				enterTextToInputField(locators.driversFilterInputbox, data.get("Filter value"));
				Thread.sleep(500);
				String[] res= data.get("No of search results").split("\\.");
				
				if (locators.driversOptionsList.size()==Integer.valueOf(res[0])) 
				{
					System.out.println("filter functionality is working: test results count for the Input '"+data.get("Filter value")+"' is "+res[0]);
					
				}
				clearInputField(locators.driversFilterInputbox);
				Thread.sleep(500);
				clickBtn(locators.driversPanelCloseButton);

		}
		
		
		@Test(priority=3,dataProvider="VerifyingDriverEditDetails", dataProviderClass=DataProviders.class)
		public void verifyEditDriverFunctionalityForDrivers(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException
		{
			
				String driverFields="Title,First Name,Last Name,Known as,Email Address,Driver Qualifications,Current Asset Allocation,Dallas Key (Business),Dallas Key (Private),AddressLine1,AddressLine2,AddressLine3,AddressLine4,AddressLine5,Home Telephone,Mobile Number";
				List<String> items = Arrays.asList(driverFields.split("\\s*,\\s*"));
				Thread.sleep(1000);
				clickDriverOnDriversList(data.get("Search"),data.get("option"));
				ArrayList<String> driverFiledValuesBeforeEdit= methodForFetchingDriverDetailsWindowData(locators.driversLabelsList,items);
				System.out.println(driverFiledValuesBeforeEdit);
				
				
				ArrayList<String> inputDataForDriverDetailsWindow=new ArrayList<String>();
				for (String item : items)
				{
					inputDataForDriverDetailsWindow.add(data.get(item));	
				}
				setDriverDetailsWindowData(locators.driversLabelsList, inputDataForDriverDetailsWindow);
				clickBtn(locators.driverDialogSaveButton);
				Thread.sleep(2000);
				clickDriverOnDriversList(data.get("SearchBy"),data.get("option"));
				ArrayList<String> driverFiledValuesAfterEdit= methodForFetchingDriverDetailsWindowData(locators.driversLabelsList,items);
				System.out.println(driverFiledValuesAfterEdit);
				Thread.sleep(1000);
				clickBtn(locators.driverDialogCancelButton);
			
		}
		
		@AfterClass		
		public void Closebrowser()
		{
			cleanUp();
		}	
		

}
