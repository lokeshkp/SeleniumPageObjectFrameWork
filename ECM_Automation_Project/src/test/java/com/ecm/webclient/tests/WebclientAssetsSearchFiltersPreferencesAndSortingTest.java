package com.ecm.webclient.tests;

import java.awt.AWTException;
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
import org.openqa.selenium.remote.server.handler.SendKeys;
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

public class WebclientAssetsSearchFiltersPreferencesAndSortingTest extends ECMUtil {

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
			login_10DukeTelematicsWebclient();
	}
	
	
	
	
	// Test Script is to Verify filter functionality based on Asset,Driver and IMEI number------>REF-TESCTCASE:TELE02
	@Test(priority=2, dataProvider="FilterFunctionalityUsingAssetDriverIMEITestData", dataProviderClass=WebclientDataProviders.class)
	public void verifyAssetFilterUsingAssetDriverIMEIValues(Hashtable<String,String> data)throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{


		APP_LOGS.info("\n\n--------------------VerifyFilterisworkingfineforAssetsDriverMEMI--------------------\n\n");
		ExcelUtils.checkExecution("AssetSuite", "verifyAssetFilterUsingAssetDriverIMEIValues", data.get("RunMode"), excel);
		

		// Verifying asset filter  functionality is working fine
		enterTextToInputField(locators.assetFilterInputBox, data.get("filtervalue"));
		Thread.sleep(700);
		
		switch(data.get("Filtertype"))
		{

		// Verifying Asset filter functionality
		case "Asset": 
			
			int assetCount=locators.assetListSearchItems.size();
			if (data.get("Existindatabase").equalsIgnoreCase("YES"))
			{
			System.out.println("Filter search results count is: "+ assetCount);
			Assert.assertEquals(assetCount, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			for(WebElement assetNames: locators.assetNameHeaderOfSearchItems)
			{
				System.out.println(getobjStaticText(assetNames));
			}
			}
			
			if (data.get("Existindatabase").equalsIgnoreCase("NO"))
			{
				System.out.println("This Asset name is not exist in db, hence Filter search results count is: "+ assetCount);
				Assert.assertEquals(assetCount, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			}
			
			locators.assetFilterInputBox.clear();
			break;

			
		// Verifying Driver filter functionality  
		case "Driver":

			int assetsCount=locators.assetListSearchItems.size();
			System.out.println("Filter search results count is: "+ assetsCount);
			if (data.get("Existindatabase").equalsIgnoreCase("YES"))
			{
			Assert.assertEquals(assetsCount, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			for(WebElement assetNames: locators.assetNameHeaderOfSearchItems)
			{
				System.out.println(getobjStaticText(assetNames));
			}
			}
			
			if (data.get("Existindatabase").equalsIgnoreCase("NO"))
			{
				System.out.println("This Driver name is not exist in db, hence Filter search results count is: "+ assetsCount);
				Assert.assertEquals(assetsCount, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			}
			
			locators.assetFilterInputBox.clear();
			break;

			// Verifying IMEI filter functionality 
		case "IMEI":

			int assetCounts=locators.assetListSearchItems.size();
			System.out.println("Filter search results count is: "+ assetCounts);
			if (data.get("Existindatabase").equalsIgnoreCase("YES"))
			{
			Assert.assertEquals(assetCounts, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			for(WebElement assetNames: locators.assetNameHeaderOfSearchItems)
			{
				System.out.println(getobjStaticText(assetNames));
			}
			}
			
			if (data.get("Existindatabase").equalsIgnoreCase("NO"))
			{
				System.out.println("This IMEI is not exist in db, hence Filter search results count is: "+ assetCounts);
				Assert.assertEquals(assetCounts, Math.round(Float.parseFloat(data.get("SearchResults"))), "Filter results are matching");
			}
			
			locators.assetFilterInputBox.clear();
			break;
		default : 
			System.out.println("Invalid entry");
			APP_LOGS.info("FAIL: please provided correct filter type "); 
			locators.assetFilterInputBox.clear();
			break;
		} 
		refreshPage();
	}
	
	
	
	
	// Test Script is to verify Asset group filter functionality for individual options like " Assets / Trailers, Default, Haulage Group, London, Midlands Engineers, Personal Trackers, South East Engineers, South West Engineers" ------>REF-TESCTCASE:TELE03
	@Test(priority=3)
	public void verifyGroupFilterIsWorkingFineForIndividualSelectDeselect()throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
			String seldeseltxt="Assets / Trailers, Default, Haulage Group, London, Midlands Engineers, Personal Trackers, South East Engineers, South West Engineers";
			String[] seldeselvalue=seldeseltxt.split(",");
			APP_LOGS.info("\n\n--------------------Verify group filter is working fine for Individual Select Deselect options--------------------\n\n");
			clickBtn(locators.assetGroupsDropDownButton);
			
			//Verifying selectAll button functionality
			clickBtn(locators.assetSelectAllButton);
			clickBtn(locators.assetGroupsDropDownButton);
			Thread.sleep(700);
			Assert.assertEquals(locators.assetListItems.size(),92,"selectAll button functionality is Pass:All the groups are selected, hence all the assets are listed in the panel");
			
			//Verifying DeselectAll button functionality
			clickBtn(locators.assetGroupsDropDownButton);
			clickBtn(locators.assetDeSelectAllButton);
			clickBtn(locators.assetGroupsDropDownButton);
			Thread.sleep(700);
			Assert.assertEquals(locators.assetListSearchItems.size(),0,"DeselectAll button functionality is Pass:None of the groups are selected, hence No assets are listed in the panel");
			
			//Verifying Assetgroup search functionality
			clickBtn(locators.assetGroupsDropDownButton);
			for (String grpName : seldeselvalue) 
			{
				enterTextToInputField(locators.assetGroupsSearchInputbox, grpName.trim());
				pressEnterKey(locators.assetGroupsSearchInputbox);
				locators.assetGroupsSearchInputbox.clear();
			}
			clickBtn(locators.assetGroupsDropDownButton);
			Assert.assertEquals(locators.assetListItems.size(),92,"selectAll button functionality is Pass:All the groups are selected, hence all the assets are listed in the panel");
			refreshPage();
			Thread.sleep(1000);
			
			
			// Getting the list of asset group from db
			String query =properties.getProperty("queryforfeatchGrouplistoption");
			Statement stmt=SQLserverConnection();

			ArrayList<String> groupNameFromDB = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery(query);	

			while(rs.next())
			{
				groupNameFromDB.add(rs.getString(2));
			}
			System.out.println(" Group Filter list Options under data base :  " +  groupNameFromDB );
			
			
			//verifying the List of assets groups from DB with UI
			for(int j=0;j<(groupNameFromDB.size() & seldeselvalue.length);j++)
			{
				Assert.assertEquals(groupNameFromDB.get(j).trim(), seldeselvalue[j].trim(),"group names are matching between DB & UI");
				
			}
			

			// Getting the list of assets for the individual group from db  and also from UI & comapring both the results
			for(int i=0;i<(groupNameFromDB.size() | seldeselvalue.length);i++)
			{
				dbConn = getDBConnection();
				query =properties.getProperty("queryforfeatchAssetanGroupName");
				prestmt = dbConn.prepareStatement(query);
				prestmt.setString(1, groupNameFromDB.get(i));
				rs = prestmt.executeQuery();
				ArrayList<String> assetsNameFromDB = new ArrayList<String>();
				ArrayList<String> groupassociatedwithasset = new ArrayList<String>();

				while(rs.next())
				{
					assetsNameFromDB.add(rs.getString(1));
					groupassociatedwithasset.add(rs.getString(2));
				} 
				System.out.println("Name of the assets associated for the group:  " + seldeselvalue[i]+ ": from DB is=>  " + assetsNameFromDB );
				
				
				ArrayList<String> assetsNameFromUI = new ArrayList<String>();
				clickBtn(locators.assetGroupsDropDownButton);
				clickBtn(locators.assetDeSelectAllButton);
				Thread.sleep(1000);
				enterTextToInputField(locators.assetGroupsSearchInputbox, seldeselvalue[i].trim());
				pressEnterKey(locators.assetGroupsSearchInputbox);
				locators.assetGroupsSearchInputbox.clear();
				clickBtn(locators.assetGroupsDropDownButton);
				for (WebElement we : locators.assetNameHeaderOfSearchItems) 
				{
					hoverOverElement(we);
					assetsNameFromUI.add(getobjStaticText(we));
				}
				System.out.println("Name of the assets associated for the group:  " + seldeselvalue[i]+ ": from UI is=>  " + assetsNameFromUI );
				Assert.assertEquals(assetsNameFromDB.size(), assetsNameFromUI.size(),"Pass:Results are matching");
				
				
				for(int k=0;k<(assetsNameFromDB.size() & assetsNameFromUI.size());k++)
				{
					Assert.assertEquals(assetsNameFromDB.get(k).trim(), assetsNameFromUI.get(k).trim(),"Asset names are matching between DB & UI");
					
				}
				
				refreshPage();
				
	} 
			
	}
	
	
	
	
	
	
	// Test Script is to verify Preferences functionality for Assets using options like Location,Speed and limit,Odometer,Driver,Last update,Activity bar------>REF-TESCTCASE:TELE04
	@Test(priority=4,dataProvider="PreferencesOptionTestData",dataProviderClass=WebclientDataProviders.class)
	public void verifyPreferencesOptions(Hashtable<String,String> data) throws InterruptedException, AWTException
	{
			APP_LOGS.info("-----------------------verifyingPreferencesOptions-----------------");
			APP_LOGS.info("-----------------Field option "+ data.get("PreferencesOptionsForAsset") + " and check box " + data.get("Checkbox") + "----------------");
			ExcelUtils.checkExecution("AssetSuite", "verifyPreferencesOptions", data.get("RunMode"), excel);
			clickBtn(locators.assetPreferencesButton);
			Thread.sleep(1000);
			

			switch(data.get("PreferencesOptionsForAsset"))
			{


			case "Location":

				APP_LOGS.info("Asset Panel Preference Option selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON")) 
				{
					clickBtn(locators.locationOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					
					for (int i = 0; i < (locators.assetListForLocationOption.size() & locators.allAssetsName.size()); i++) 
					{
						hoverOverElement(locators.assetListForLocationOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						String locationName=getobjStaticText(locators.assetListForLocationOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Location Name=> " +locationName);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.locationOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Location option Uncheck functionality is working fine");
				}
				clickBtn(locators.assetPreferencesButton);
				break;
				

			case "Speed and limit":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON"))  
				{
					clickBtn(locators.speedAndLimitOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					for (int i = 0; i < (locators.assetListForLimitOption.size() & locators.assetListForSpeedOption.size() & locators.allAssetsName.size()); i++)
					{
						hoverOverElement(locators.assetListForLimitOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						String limitValue= getobjStaticText(locators.assetListForLimitOption.get(i));
						String speedValue=getobjStaticText(locators.assetListForSpeedOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Speed and limit values are=> " +limitValue +" : "+speedValue);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.speedAndLimitOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Speed and limit option Uncheck functionality is working fine");
				}
				clickBtn(locators.assetPreferencesButton);
				break;
				

			case "Odometer":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON")) 
				{
					clickBtn(locators.odometerOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					for (int i = 0; i < (locators.assetListForOdometerOption.size() & locators.allAssetsName.size()); i++)
					{
						hoverOverElement(locators.assetListForOdometerOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						String odometerValue=getobjStaticText(locators.assetListForOdometerOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Odometer Value=> " +odometerValue);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.odometerOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Speed and limit option Uncheck functionality is working fine");
				}
				clickBtn(locators.assetPreferencesButton);
				break;
				

			case "Driver":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON"))  
				{
					clickBtn(locators.driverOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					for (int i = 0; i < (locators.assetListForDriverOption.size() & locators.allAssetsName.size()); i++)
					{
						hoverOverElement(locators.assetListForDriverOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						String driverName=getobjStaticText(locators.assetListForDriverOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Driver Name=> " +driverName);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.driverOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Driver option Uncheck functionality is working fine");
				}

				clickBtn(locators.assetPreferencesButton);
				break;
				

			case "Last Update":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON"))  
				{
					clickBtn(locators.lastUpdateOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					for (int i = 0; i < (locators.assetListForLastUpdateOption.size() & locators.allAssetsName.size()); i++)
					{
						hoverOverElement(locators.assetListForLastUpdateOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						String lastUpdateTime=getobjStaticText(locators.assetListForLastUpdateOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Last Update Time=> " +lastUpdateTime);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.lastUpdateOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Last Update option Uncheck functionality is working fine");
				}

				clickBtn(locators.assetPreferencesButton);
				break;
				

			case "Activity Bar":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				if (data.get("Checkbox").equalsIgnoreCase("ON"))  
				{
					clickBtn(locators.activityOption);
					Thread.sleep(500);
					clickBtn(locators.assetPreferencesButton);
					Thread.sleep(500);
					for (int i = 0; i < (locators.assetListForSummaryActivityOption.size() & locators.allAssetsName.size()); i++)
					{
						hoverOverElement(locators.assetListForSummaryActivityOption.get(i));
						String assetName= getobjStaticText(locators.allAssetsName.get(i));
						boolean activityBar= isElementExist(locators.assetListForSummaryActivityOption.get(i));
						System.out.println("Asset Name=> "+ assetName+ ", Activity Bar=> " +activityBar);
					}
				}
				else 
				{
					clickBtn(locators.assetPreferencesButton);
					clickBtn(locators.activityOption);
					Assert.assertEquals(locators.assetListItems.size(),92,"Pass:Activity Bar option Uncheck functionality is working fine");
				}
				clickBtn(locators.assetPreferencesButton);
				break;

			default:

				APP_LOGS.info("FAIL: Filed option " + data.get("PreferencesOptionsForAsset") + "   is not valid field option");
				Assert.assertTrue(false, "FAIL: Please Provide valid field option");

			}

	}

	
	
	
	// Test Script is to verify Sort functionality for Assets using options like Asset Name,Driver Name,Last update (recent first),Last update (oldest first)------>REF-TESCTCASE:TELE04
		@Test(priority=5, dataProvider="SortAssetPanelByOptionTestData",dataProviderClass=WebclientDataProviders.class)
		public void verifySortOperationIsWorkingForAvailableOption(Hashtable<String,String> data) throws InterruptedException, ClassNotFoundException, SQLException
		{
			
				APP_LOGS.info("\n\n--------------------verifyingSortOperationIsWorkingForAvailableOption--------------------\n\n");

				ExcelUtils.checkExecution("AssetSuite", "verifySortOperationIsWorkingForAvailableOption", data.get("RunMode"), excel);
				
				Thread.sleep(2000);
				int i=0;
				clickBtn(locators.assetSortButton);
				for(WebElement sortOpt: locators.assetSortOptions)
				{
					String optiontext=getobjStaticText(sortOpt).trim();

					if(optiontext.equals(data.get("SortOption")))
						
					{
						Thread.sleep(1000);
						clickBtn(sortOpt);
						APP_LOGS.info("PASS : " + data.get("SortOption") + " Option radio button is selected");
						
						
						switch(data.get("SortOption"))
						{
						
						case  "Asset Name":

							APP_LOGS.info("Sort Option selected is : "+ data.get("SortOption"));
							ArrayList<String> assetsNameFromUI = new ArrayList<String>();
							
							for (WebElement we : locators.assetsListAfterSorting) 
							{
								hoverOverElement(we);
								assetsNameFromUI.add(getobjStaticText(we));
							}
							System.out.println("Asset Names from UI: "+assetsNameFromUI);
							
							
							String query =properties.getProperty("queryforFetchlistofAssetsAndDrivers");
							Statement stmt=SQLserverConnection();
							ArrayList<String> assetsNameFromDB = new ArrayList<String>();
							ResultSet rs = stmt.executeQuery(query);	
							
							while(rs.next())
							{
								assetsNameFromDB.add(rs.getString(1));
							} 
							
							System.out.println("Asset Names from DB: "+assetsNameFromDB);
							
							for(int k=0;k<(assetsNameFromDB.size() & assetsNameFromUI.size());k++)
							{
								Assert.assertEquals(assetsNameFromDB.get(k).trim(), assetsNameFromUI.get(k).trim(),"Pass:Sorting option is working fine for Asset Name as Assets are matching between DB & UI");
								
							}
							break;

						
						case "Driver Name":
							APP_LOGS.info("Sort Option selected is : "+ data.get("SortOption"));
							ArrayList<String> assetsNameFromUIForDriversSelection = new ArrayList<String>();
							
							for (WebElement we : locators.assetsListAfterSorting) 
							{
								hoverOverElement(we);
								assetsNameFromUIForDriversSelection.add(getobjStaticText(we));
							}
							System.out.println("Asset Names from UI: "+assetsNameFromUIForDriversSelection);
							
							
							String query1 =properties.getProperty("queryforFetchlistofAssetsAndDriversBasedOnDriverName");
							Statement stmt1=SQLserverConnection();
							ArrayList<String> assetsNameFromDBForDriversSelection = new ArrayList<String>();
							ResultSet rs1 = stmt1.executeQuery(query1);	
							
							while(rs1.next())
							{
								assetsNameFromDBForDriversSelection.add(rs1.getString(1));
							} 
							
							System.out.println("Asset Names from DB: "+assetsNameFromDBForDriversSelection);
							
							for(int k=0;k<(assetsNameFromDBForDriversSelection.size() & assetsNameFromUIForDriversSelection.size());k++)
							{
								//Assert.assertEquals(assetsNameFromDBForDriversSelection.get(k).trim(), assetsNameFromUIForDriversSelection.get(k).trim(),"Pass:Sorting option is working fine for Driver name as assets are matching between DB & UI");
							}
							break;


						case "Last Update Ascending":

							APP_LOGS.info("Sort Option selected is : "+data.get("SortOption"));
							ArrayList<String> listValueFromUI=new ArrayList<String>();
							ArrayList<String> listSortValueFromDB=new ArrayList<String>();
							String query2 =properties.getProperty("queryforfeatchAssetSortbyLastupdatefirst");
							Statement stmt2=SQLserverConnection();
							ResultSet rs2 = stmt2.executeQuery(query2);	
							while(rs2.next()){

								listSortValueFromDB.add(rs2.getString(2));
							}
							
							System.out.println("Asset Names from DB: "+listSortValueFromDB);
							
							
							for (WebElement we : locators.assetsListAfterSorting) 
							{
								hoverOverElement(we);
								listValueFromUI.add(getobjStaticText(we));
							}
							System.out.println("Asset Names from UI: "+listValueFromUI);
							
							for(int k=0;k<(listSortValueFromDB.size() & listValueFromUI.size());k++)
							{
								//Assert.assertEquals(listSortValueFromDB.get(k).trim(), listValueFromUI.get(k).trim(),"Pass:Sorting option is working fine for Last Update Ascending as assets are matching between DB & UI");
								
							}
							
							break;
							

						case  "Last Update Descending":
							
							APP_LOGS.info("Sort Option selected is : "+data.get("SortOption"));
							ArrayList<String> listValuesFromUI=new ArrayList<String>();
							ArrayList<String> listSortValuesFromDB=new ArrayList<String>();
							query =properties.getProperty("queryforfeatchAssetSortbyLastupdateLast");
							stmt=SQLserverConnection();
							ResultSet rs3 = stmt.executeQuery(query);	
							while(rs3.next()){
								listSortValuesFromDB.add(rs3.getString(2));
							}
							
							System.out.println("Asset Names from DB: "+listSortValuesFromDB);
							
							for (WebElement we : locators.assetsListAfterSorting) 
							{
								hoverOverElement(we);
								listValuesFromUI.add(getobjStaticText(we));
							}
							System.out.println("Asset Names from UI: "+listValuesFromUI);
							
							for(int k=0;k<(listSortValuesFromDB.size() & listValuesFromUI.size());k++)
							{
								//Assert.assertEquals(listSortValuesFromDB.get(k).trim(), listValuesFromUI.get(k).trim(),"Pass:Sorting option is working fine for Last Update Ascending as assets are matching between DB & UI");
								
							}
							
							break;

						default : 
							APP_LOGS.info("FAIL: Please enter correct option for Sorting");
						}
						break;
					}

					i++;
				}
		}



	@AfterClass		
	public void Closebrowser()
	{
		//cleanUp();
	}	  

}
