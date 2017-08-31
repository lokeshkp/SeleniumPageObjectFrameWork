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

public class AssetsFilterTest extends ECMUtil {

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
	
	
	
	// Test Script is to verify Asset PanelTabs------>REF-TESCTCASE:TELE01
		@Test(priority=2,dataProvider="AssetPanelTabsData", dataProviderClass=DataProviders.class)
		public void  verifyAssetPanelTabs(Hashtable<String,String> data) throws IllegalMonitorStateException, Exception
		{ 
			
				APP_LOGS.info("\n\n--------------------verifyingAssetPanelTabs--------------------\n\n");
				ExcelUtils.checkExecution("AssetSuite", "verifyAssetPanelTabs", data.get("RunMode"), excel);
				List<WebElement>tabs= locators.assetTabs;
				int i=0;
				for (WebElement webElement : tabs) {
					if (webElement.getText().trim().equalsIgnoreCase((data.get("AssetPanelTabs")))) 
					{
						clickBtn(webElement);
						APP_LOGS.info("  Verification successful for the Tab: "+ data.get("AssetPanelTabs"));
						break;
					}
					else 
					{
						i++;
					}
				}
				APP_LOGS.info("Verifying AssetPanel Tabs operation succesful");
		}
	
	
	
	
	



	// Test Script is to Verify filter functionality based on Asset,Driver and IMEI number------>REF-TESCTCASE:TELE05
	@Test(priority=9, dataProvider="FilterFunctionalityUsingAssetDriverIMEITestData", dataProviderClass=DataProviders.class)
	public void verifyAssetFilterUsingAssetDriverIMEIValues(Hashtable<String,String> data)throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{

		
			APP_LOGS.info("\n\n--------------------VerifyFilterisworkingfineforAssetsDriverMEMI--------------------\n\n");
			ExcelUtils.checkExecution("AssetSuite", "verifyAssetFilterUsingAssetDriverIMEIValues", data.get("RunMode"), excel);
			clickBtn(locators.filterTab);
			if(data.get("Existindatabase").trim().equalsIgnoreCase("YES") | data.get("Existindatabase").trim().equalsIgnoreCase("No"))

			{
				
				if (data.get("AssetbelongtoTracker_IMEI").equals("AC123456")) {
					
					enterTextToInputField(locators.filterInputbox, "011104005168635");
					APP_LOGS.info("IMEI value entered is: 011104005168635");
				}
				else 
				{
				enterTextToInputField(locators.filterInputbox, data.get("filtervalue"));
				APP_LOGS.info("Filter value entered is: "+ data.get("filtervalue"));
				}
				WebElement assetpanell= Locators.telematicsassetfilter;

				List <WebElement> assetNames=	assetpanell.findElements(By.xpath("//div/ul/li[@hidden='false']"));


				switch(data.get("Filtertype")){

				// Verifying asset filter  functionality is working fine 

				case "Asset": 

					for(WebElement asset_ele: assetNames)
					{
						String assetvalue[]=asset_ele.getText().split("\\n");
						APP_LOGS.info("  text " +asset_ele.getText());
						APP_LOGS.info("  text " +asset_ele.getText().split("\\n") + "Size  " +assetvalue.length );
						if(asset_ele.isDisplayed())
						{

							if(data.get("Existindatabase").trim().equalsIgnoreCase("YES")  )
							{


								if(assetvalue[0].trim().equalsIgnoreCase(data.get("filtervalue").trim()))
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " exist is Asset List ");
									Assert.assertEquals(assetvalue[0].trim(), data.get("filtervalue")); 
								}
								else
								{
									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + " not exist is Asset List  ");
									Assert.assertEquals(assetvalue[0].trim(), data.get("filtervalue"), " Filter Value  Not exist is Asset List"); 

								}
								break;    

							}

							else if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should not exist in asset list");
								if(assetvalue[0].trim().equalsIgnoreCase(data.get("filtervalue").trim()))
								{ 

									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + "   exist is Asset List  ");;
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("filtervalue")); 
								}
								else
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " not  exist is Asset List ");
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("filtervalue")); 

								}

								break;
							}
							else{

							}


						}else{
							if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should not exist in asset list");
								if(assetvalue[0].trim().equalsIgnoreCase(data.get("filtervalue").trim()))
								{ 

									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + "   exist is Asset List  ");;
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("filtervalue")); 
								}
								else
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " not  exist is Asset List ");
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("filtervalue")); 

								} 

								break;
							}
							if(data.get("Existindatabase").trim().equalsIgnoreCase("Yes") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should  exist in asset list");


								APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue").trim()  + "   not exist is Asset List  ");;
								Assert.assertEquals(assetvalue[0].trim(), data.get("filtervalue")); 

								break;
							}
						}

					}
					APP_LOGS.info("Filter verification using Asset Name is successful");
					locators.filterInputbox.clear();
					break;

					// Verifying Driver filter functionality  is working fine or not  

				case "Driver":

					for(WebElement asset_ele: assetNames)
					{
						String assetvalue[]=asset_ele.getText().split("\\n");
						APP_LOGS.info("  text " +asset_ele.getText());
						APP_LOGS.info("  text " +asset_ele.getText().split("\\n") + "Size  " +assetvalue.length );

						if(asset_ele.isDisplayed())
						{

							// APP_LOGS.info(" Full value " + asset_ele.getText() + "....");

							if(data.get("Existindatabase").trim().equalsIgnoreCase("YES")  )
							{

								if(assetvalue[5].trim().equalsIgnoreCase(data.get("filtervalue").trim()))
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " exist is Asset List ");
									Assert.assertEquals(assetvalue[5].trim(), data.get("filtervalue")); 
								}
								else
								{
									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + " not exist is Asset List  ");
									Assert.assertEquals(assetvalue[5].trim(), data.get("filtervalue"), " Filter Value  Not exist is Asset List"); 

								}
								break;    

							}

							else if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info("As mentioned in test data Value Should not exist in asset list");
								if(assetvalue[5].trim().equalsIgnoreCase(data.get("filtervalue").trim()))
								{ 

									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + "   exist is Asset List  ");;
									Assert.assertNotEquals(assetvalue[5].trim(), data.get("filtervalue")); 
								}
								else
								{

									APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " not  exist is Asset List ");
									Assert.assertNotEquals(assetvalue[5].trim(), data.get("filtervalue")); 

								}

								break;
							}
							else{

							}


						}else{

							if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info("As mentioned in test data Value Should not exist in asset list");
								int len=assetvalue.length;

								APP_LOGS.info("PASS: Filter Value " + data.get("filtervalue")  + " not  exist is Asset List ");

								Assert.assertNotEquals(assetvalue[len-1].trim(), data.get("filtervalue")); 


								break;
							}
							if(data.get("Existindatabase").trim().equalsIgnoreCase("Yes") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should  exist in asset list");

								int len=assetvalue.length;
								APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue").trim()  + "   not exist is Asset List  ");;
								Assert.assertEquals(assetvalue[len-1].trim(), data.get("filtervalue")); 
								break;
							}
						}

					}
					locators.filterInputbox.clear();
					APP_LOGS.info("Filter verification using Driver name is successful");
					break;


					// Verifying IEMI filter functionality is working fine or not
				case "IMEI":

					for(WebElement asset_ele: assetNames)
					{

						String assetvalue[]=asset_ele.getText().split("\\n");
						APP_LOGS.info("  text " +asset_ele.getText());
						APP_LOGS.info("  text " +asset_ele.getText().split("\\n") + "Size  " +assetvalue.length );
						if(asset_ele.isDisplayed())
						{

							// APP_LOGS.info(" Full value " + asset_ele.getText() + "....");

							if(data.get("Existindatabase").trim().equalsIgnoreCase("YES")  )
							{


								if(assetvalue[0].trim().equalsIgnoreCase(data.get("AssetbelongtoTracker_IMEI").trim()))
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("AssetbelongtoTracker_IMEI")  + " exist is Asset List ");
									Assert.assertEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 
								}
								else
								{
									APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue")  + " not exist is Asset List  ");
									Assert.assertEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim(), " Filter Value  Not exist is Asset List"); 

								}
								break;    

							}

							else if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should not exist in asset list");
								if(assetvalue[0].trim().equalsIgnoreCase(data.get("AssetbelongtoTracker_IMEI").trim()))
								{ 

									APP_LOGS.info("FAIL: Filter Value " + data.get("AssetbelongtoTracker_IMEI").trim()  + "   exist is Asset List  ");;
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 
								}
								else
								{

									APP_LOGS.info("PASS: Filter Value " + data.get("AssetbelongtoTracker_IMEI").trim()  + " not  exist is Asset List ");
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 

								}

								break;
							}
							else{

							}

						}else{
							if(data.get("Existindatabase").trim().equalsIgnoreCase("NO") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should not exist in asset list");
								if(assetvalue[0].trim().equalsIgnoreCase(data.get("AssetbelongtoTracker_IMEI").trim()))
								{ 

									APP_LOGS.info("FAIL: Filter Value " + data.get("AssetbelongtoTracker_IMEI").trim()  + "   exist is Asset List  ");;
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 
								}
								else
								{
									APP_LOGS.info("PASS: Filter Value " + data.get("AssetbelongtoTracker_IMEI").trim()  + " not  exist is Asset List ");
									Assert.assertNotEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 
								} 

								break;
							}


							if(data.get("Existindatabase").trim().equalsIgnoreCase("Yes") )
							{
								APP_LOGS.info(" As mentioned in test data Value Should  exist in asset list");


								APP_LOGS.info("FAIL: Filter Value " + data.get("filtervalue").trim()  + "   not exist is Asset List  ");;
								Assert.assertEquals(assetvalue[0].trim(), data.get("AssetbelongtoTracker_IMEI").trim()); 


								break;
							}
						}
					}
					APP_LOGS.info("Filter verification using IMEI number is successful");
					locators.filterInputbox.clear();
					break;
				default : 
					APP_LOGS.info("FAIL: please  provided correct filter type "); 

					break;
				} 
			}else
			{
				APP_LOGS.info("FAIL: please  provided inpute for  value exist in data base or not i.e(Yes/No)? "); 
				Assert.assertEquals(true, false, "please  provided inpute for  value exist in data base or not i.e(Yes/No)?"); 
			}
	}
	
	
	
	
	
	




	// Test Script is to verify group filter functionality------>REF-TESCTCASE:TELE05
	@Test(priority=3)
	public void verifyGroupFilterHavingSelectDeselectBtnAndItsWorkingFine()throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			String seldeseltxt="Select all, Assets / Trailers, Default, Haulage Group, London, Midlands Engineers, Personal Trackers, South East Engineers, South West Engineers";
			String[] seldeselvalue=seldeseltxt.split(",");
			APP_LOGS.info("\n\n--------------------VerifygroupfilterhavingSelectDeselectbtnanditsworkingfine--------------------\n\n");
			clickBtn(locators.filterTab);
			//verifying group filter icon is displaying or not 
			boolean groupfiltericonexist=isElementExist(locators.groupfiltericon);
			if(groupfiltericonexist)
			{
				APP_LOGS.info("PASS: Group Filter icon  Displaying under Asset Panel ");
				Assert.assertEquals(true, true); 
			}
			else
			{
				APP_LOGS.info("FAIL: Group Filter icon not  Displaying under Asset Panel ");
				Assert.assertEquals(false, true, "Group Filter icon not  Displaying under Asset Panel"); 

			}

			//verifying  group filter list box is  displaying or not 

			WebElement groupfilter= locators.groupFilter;
			boolean groupfilterexist=isElementExist(groupfilter);
			if(groupfilterexist)
			{
				APP_LOGS.info("PASS: Group Filter list box Displaying under Asset Panel ");
				Assert.assertEquals(true, true); 
			}
			else
			{
				APP_LOGS.info("FAIL: Group Filter list box not  Displaying under Asset Panel ");
				Assert.assertEquals(false, true, "Group Filter field not  displaying under Asset Panel"); 
			}
			Thread.sleep(5000);
			clickBtn(groupfilter);

			//Verifying group filter list contain select all/ Deselect all link button	
			List <WebElement> selectdeselectbtnlink= locators.groupfilterselectdeselectbtnlink;
			int i=0;		     
			for(WebElement selbtnlink:selectdeselectbtnlink)
			{
				boolean selectdeselectbtnlinkexist=isElementExist(selbtnlink);

				if(selectdeselectbtnlinkexist)
				{



					String btnlinktext=getobjStaticText(selbtnlink);
					if(btnlinktext.trim().equals(seldeselvalue[i].trim()))
					{
						APP_LOGS.info("PASS: " + seldeselvalue[i].trim() + " link button displaying under Group Filter list ");
						Assert.assertEquals(btnlinktext.trim(), seldeselvalue[i].trim()); 
					}
					else
					{
						APP_LOGS.info("FAIL: link button " + seldeselvalue[i].trim() + " text not displaying under Group Filter list ");
						Assert.assertEquals(btnlinktext.trim(), seldeselvalue[i].trim(), "link button " + seldeselvalue[i].trim() + " text not displaying under Group Filter list ");   

					}
					i++;
				}
				else
				{
					APP_LOGS.info("FAIL: link button Select/Deselect not displaying under Group Filter list ");
					Assert.assertEquals(false, true, "link button Select/Deselect not displaying under Group Filter list "); 

				}

			}


			/// Verifying group list box contain correct list option

			String query =properties.getProperty("queryforfeatchGrouplistoption");
			Statement stmt=SQLserverConnection();

			ArrayList<String> Group_Name = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery(query);	



			while(rs.next()){
				Group_Name.add(rs.getString(2));


			}
			APP_LOGS.info(" Group Filter list Options under data base :  " +  Group_Name);
			Thread.sleep(5000);
			List <WebElement> grouplist= locators.groupfilterlistoptions;
			int listindx=0;

			for(WebElement grouplistvalele: grouplist)
			{
				String grouplistval=grouplistvalele.getText().trim();


				if(grouplistval.equals(Group_Name.get(listindx)))

				{

					APP_LOGS.info("PASS: " + Group_Name.get(listindx) + " list option displaying under Group Filter list ");
					Assert.assertEquals(grouplistval, Group_Name.get(listindx)); 
				}
				else
				{
					APP_LOGS.info("PASS: " + Group_Name.get(listindx) + " list option is not displaying under Group Filter list ");
					Assert.assertEquals(grouplistval, Group_Name.get(listindx), "list option is not displaying under Group Filter list");
				}

				listindx++;
			}


			// verifying  deselect link button is working or not 

			WebElement deselectbtn = locators.groupfilterdeselectbtnlinkbtn;
			
			clickBtn(deselectbtn );

			/// logics for verifying group list option is deselected  

			List <WebElement> grouplistele= locators.groupfilterlistoptions;;

			boolean flg=true;	   

			for(WebElement groupvalelel: grouplistele)
			{
				String attributeclass=groupvalelel.getAttribute("class");
				if(attributeclass.isEmpty()==true)

				{
					flg=true;
					APP_LOGS.info("pass: group list option is not get deselected under Group Filter list ");
					break;
				}

			}
			if(flg)
			{

				APP_LOGS.info("PASS: group list option get deselected under Group Filter list ");

			}

			Assert.assertTrue(flg, "group list option is not get deselected under Group Filter list");


			groupfilter= locators.groupFilter;
			clickBtn(groupfilter);

			/// logics for verifying asset list is not displaying under asset panel   
			List <WebElement> assetlist= locators.telematicsAssetList;
			flg=true;
			for(WebElement assetlistele: assetlist)
			{
				boolean assetexist= assetlistele.isDisplayed();

				if(assetexist)
				{
					flg=false;
					APP_LOGS.info("FAIL: Asset list is displaying  under asset panel");

					break;

				}

			}
			if(flg)
			{

				APP_LOGS.info("PASE: Asset lists are not displaying   under asset panel");
				APP_LOGS.info("PASE: Deselect All is working as expected");

			}

			Assert.assertTrue(flg, " Asset list is displaying  under asset panel but it should not  display as we Deselected ");

			clickBtn(groupfilter);

			// verifying  select link button is working or not 
			WebElement selectbtn= locators.groupfilterselectbtnlinkbtn;


			clickBtn(selectbtn );
			/// logics for verifying group list option is get selected  
			WebElement atlasselect= locators.groupfilterselectdeselect;

			grouplistele= atlasselect.findElements(By.xpath("div/ul/li"));

			flg=true;	   

			for(WebElement groupvalelel: grouplistele)
			{
				String attributeclass=groupvalelel.getAttribute("class");


				if(attributeclass.equals("selected"))

				{
					flg=true;
					APP_LOGS.info("FAIL: group list option not get selected under Group Filter list ");
					break;
				}

			}
			if(flg)
			{

				APP_LOGS.info("PASS: group list  option get  selected under Group Filter list ");

			}

			Assert.assertTrue(flg, "Group list option not get selected selected but it should get selected as we click on select all button.");

			groupfilter= locators.groupFilter;
			clickBtn(groupfilter);
			APP_LOGS.info("Group Filter Options working fine");

	}

	
	
	
	
	
	
	
	
	
	
	
	

	// Test Script is to verify group filter functionality for individual options like "Select all, Assets / Trailers, Default, Haulage Group, London, Midlands Engineers, Personal Trackers, South East Engineers, South West Engineers" ------>REF-TESCTCASE:TELE05
	@Test(priority=4)
	public void verifyGroupFilterIsWorkingFineForIndividualSelectDeselect()throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			String seldeseltxt="Select all, Assets / Trailers, Default, Haulage Group, London, Midlands Engineers, Personal Trackers, South East Engineers, South West Engineers";
			String[] seldeselvalue=seldeseltxt.split(",");
			APP_LOGS.info("\n\n--------------------VerifygroupfilterisworkingfineforIndividualSelectDeselect--------------------\n\n");
			clickBtn(locators.filterTab);
			WebElement groupfilter= locators.groupFilter;
			boolean groupfilterexist=isElementExist(groupfilter);

			if(groupfilterexist)
			{
				APP_LOGS.info("PASS: Group Filter list box Displaying under Asset Panel ");
				Assert.assertEquals(true, true); 
			}
			else
			{
				APP_LOGS.info("FAIL: Group Filter list box not  Displaying under Asset Panel ");
				Assert.assertEquals(false, true, "Group Filter field not  displaying under Asset Panel"); 
			}
			Thread.sleep(1000);
			clickBtn(groupfilter);
			waitForElement();
			/// Verifying group list box contain correct list option

			String query =properties.getProperty("queryforfeatchGrouplistoption");
			Statement stmt=SQLserverConnection();

			ArrayList<String> Group_Name = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery(query);	

			while(rs.next()){
				Group_Name.add(rs.getString(2));
			}

			APP_LOGS.info(" Group Filter list Options under data base :  " +  Group_Name );

			Thread.sleep(5000);
			List <WebElement> grouplist= locators.groupfilterlistoptions;
			int listindx=0;

			for(WebElement grouplistvalele: grouplist)
			{
				String grouplistval=grouplistvalele.getText().trim();


				if(grouplistval.equals(Group_Name.get(listindx)))

				{
					APP_LOGS.info("PASS: " + Group_Name.get(listindx) + " list option displaying under Group Filter list ");
					Assert.assertEquals(grouplistval, Group_Name.get(listindx)); 
				}
				else
				{
					APP_LOGS.info("PASS: " + Group_Name.get(listindx) + " list option is not displaying under Group Filter list ");
					Assert.assertEquals(grouplistval, Group_Name.get(listindx), "list option is not displaying under Group Filter list");
				}

				listindx++;
			}

			//verifying  deselect link button is working or not 

			WebElement deselectbtn = locators.groupfilterdeselectbtnlinkbtn;
			clickBtn(deselectbtn );
			waitForElement();
			/// logics for verifying group list option is deselected  
			WebElement atlasselect= locators.groupfilterselectdeselect;

			List <WebElement> grouplistele= atlasselect.findElements(By.xpath("div/ul/li"));

			boolean flg=true;	   

			for(WebElement groupvalelel: grouplistele)
			{
				String attributeclass=groupvalelel.getAttribute("class");
				if(attributeclass.isEmpty()==true)

				{
					flg=true;
					APP_LOGS.info("Pass: group list option is  get deselected under Group Filter list ");
					break;
				}
			}
			if(flg)
			{

				APP_LOGS.info("PASS: group list option get deselected under Group Filter list ");
			}

			Assert.assertTrue(flg, "group list option is get deselected under Group Filter list");
			groupfilter= locators.groupFilter;

			/// logics for verifying asset list is not displaying under asset panel   
			List <WebElement> assetlist= locators.telematicsAssetList;

			//  grouplist= locators("groupfilterlistoptions_objLocatortype"), obj.getProperty("groupfilterlistoptions_objLocatorValue"));   

			for(String groupname: Group_Name)
			{


				for(WebElement groupele: locators.groupListEleMents)
				{

					String grouplisttext=groupele.getText().trim();

					if(groupname.equals(grouplisttext))
					{

						APP_LOGS.info("Group filter option displaying under list need to select and verify is working  " + grouplisttext);
						clickBtn(groupele);
						String attributeclass=groupele.getAttribute("class");
						if(!attributeclass.equals("selected"))

						{
							flg=false;
							APP_LOGS.info("FAIL: group list option not get selected under Group Filter list ");
							Assert.assertEquals(attributeclass, "selected", "Group list option not get selected selected but it should get selected as we click on select all button.");

						}
						else
						{

							APP_LOGS.info("PASS: group list  option get  selected under Group Filter list ");  
							Assert.assertEquals(attributeclass, "selected", "Group list option not get selected selected but it should get selected as we click on select all button.");

							dbConn = getDBConnection();
							query =properties.getProperty("queryforfeatchAssetanGroupName");
							prestmt = dbConn.prepareStatement(query);
							prestmt.setString(1, groupname);
							rs = prestmt.executeQuery();
							ArrayList<String> asset_Name = new ArrayList<String>();
							ArrayList<String> groupassociatedwithasset = new ArrayList<String>();

							while(rs.next()){

								asset_Name.add(rs.getString(1));
								groupassociatedwithasset.add(rs.getString(2));
							} 

							APP_LOGS.info("Name of assets associated with group  " + asset_Name );  
							groupfilter= locators.groupFilter;
							clickBtn(groupfilter);
							waitForElement();
							assetlist= locators.telematicsAssetList;

							int i=0;
							flg=true;
							waitForElement();
							for(WebElement assetlistele: assetlist)
							{
								boolean assetexist= assetlistele.isDisplayed();

								String assettext=assetlistele.getText().trim();

								if(assetexist)
								{
									if(!assettext.contains(asset_Name.get(i)))
									{

										flg=false;
										APP_LOGS.info("FAIL: Expected asset is not displaying under Asset list ");

										break;
									}

									i++;

								}
							}
							APP_LOGS.info(" Number of asset displaying " + i);
							if(flg)
							{

								if(!asset_Name.isEmpty() & i==0)
								{
									flg=false;

									APP_LOGS.info("FAIL: Expected asset is not displaying under Asset list ");
									break;
								}

								APP_LOGS.info("PASS: Individual select  is   working for group " + groupname );

							}

							Assert.assertTrue(flg, "Individual select  is not working for group " + groupname);
							groupfilter= locators.groupFilter;

							clickBtn(groupfilter);

							WebElement atlasselectind= locators.groupfilterselectdeselect;

							List <WebElement> grouplisteleind= atlasselectind.findElements(By.xpath("div/ul/li"));

							for(WebElement groupvalelelind: grouplisteleind)
							{
								attributeclass=groupvalelelind.getAttribute("class");
								if(attributeclass.equals("selected"))

								{
									clickBtn(groupvalelelind);
									waitForElement();
									Thread.sleep(2000);
								}
							}

						}

						break;   
					}
				}
			}

			//verifying  select link button is working or not 
			WebElement selectbtn= locators.groupfilterselectbtnlinkbtn;
			clickBtn(selectbtn );
			waitForElement();
			groupfilter= locators.groupFilter;
			clickBtn(groupfilter);
			waitForElement();
	} 
	
	
	
	
	



	// Test Script is to verify Sort functionality for Assets------>REF-TESCTCASE:TELE04
	@Test(priority=5)
	public void verifySortBtnDisplayingCorrectListOfOption() throws InterruptedException
	{
		
			APP_LOGS.info("\n\n--------------------verifySortBtnDisplayingCorrectListOfOption--------------------\n\n");
			String Sortoptions="Asset name,Driver name,Last update (recent first),Last update (oldest last)";

			String[] sortoption=Sortoptions.split(",");
			ArrayList<String>  options=new ArrayList<String>();

			for(String option: sortoption)
			{
				options.add(option)	;
			}
			WebElement sorticon=locators.sortIcon;
			clickBtn(sorticon);
			Thread.sleep(5000);
			clickBtn(locators.fieldsIcon);
			List <WebElement> sortoptions=locators.sortOptions;
			int i=0;
			for(WebElement sortopt: sortoptions)
			{
				String optiontext=getobjStaticText(sortopt).trim();
				if(optiontext.equals(options.get(i).trim()))
				{
					APP_LOGS.info("pass: Sort option " + options.get(i) + " is displaying under Sort list ");
					i++;
				}
			}
			clickBtn(locators.fieldsIcon);
			APP_LOGS.info("PASS: Sort options  " + options + "  are  displaying under Sort list ");
	}

	
	
	
	
	
	

	// Test Script is to verify Sort functionality for Assets using options like Asset Name,Driver Name,Last update (recent first),Last update (oldest first)------>REF-TESCTCASE:TELE04
	@Test(priority=6, dataProvider="SortAssetPanelByOptionTestData",dataProviderClass=DataProviders.class)
	public void verifySortOperationIsWorkingForAvailableOption(Hashtable<String,String> data) throws InterruptedException, ClassNotFoundException, SQLException
	{
		
			APP_LOGS.info("\n\n--------------------verifyingSortOperationIsWorkingForAvailableOption--------------------\n\n");
			ExcelUtils.checkExecution("AssetSuite", "verifySortOperationIsWorkingForAvailableOption", data.get("RunMode"), excel);
			Thread.sleep(2000);
			ArrayList<String> listvalue=new ArrayList<String>();
			ArrayList<String> listsortvalue=new ArrayList<String>();

			clickBtn(locators.sortIcon);
			Thread.sleep(2000);
			clickBtn(locators.sortDropDownIcon);
			List <WebElement> sortoptions=locators.sortOptions;
			int i=0;

			for(WebElement sortopt: sortoptions)
			{
				String optiontext=getobjStaticText(sortopt).trim();

				if(optiontext.equals(data.get("SortOption")))
					
				{
					clickBtn(sortopt);
					APP_LOGS.info("PASS : " + data.get("SortOption") + " Option radio button  get selected");
					clickBtn(locators.sortIcon);
					boolean issorted=false;
					
					
					switch(data.get("SortOption"))
					{
					
					case  "Asset name":

						APP_LOGS.info("Sort Option selected is : "+ data.get("SortOption"));
						List <WebElement> assetlist=locators.telematicsAssetList;
						issorted=false;
						for(WebElement asset: assetlist)
						{			    		 
							String assetdetailval=asset.getText();
							String assetval[]=assetdetailval.split("\n");
							listvalue.add(assetval[0]);

						}

						APP_LOGS.info("List of assets fetch from asset panel list  :" + listvalue );
						listsortvalue.addAll(listvalue);
						Collections.sort(listsortvalue);
						APP_LOGS.info("List of assets after appling sort function  :" + listsortvalue); 

						issorted=listvalue.equals(listsortvalue);
						if(issorted)
						{

							APP_LOGS.info("PASS: Sort by " + data.get("SortOption") + " is  working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");

						}else
						{
							APP_LOGS.info("FAIL: Sort by " + data.get("SortOption") + " is not working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");
						}
						listvalue.clear();listsortvalue.clear();
						break;

					
					case "Driver name":
						APP_LOGS.info("Sort Option selected is : "+data.get("SortOption"));
						List <WebElement> driverlist=locators.telematicsAssetList;

						for(WebElement drivers: driverlist)
						{			    		 
							String driverdetailval=drivers.getText();
							String driverval[]=driverdetailval.split("\n");
							listvalue.add(driverval[5].trim());
						}

						APP_LOGS.info("List of driver fetch from asset panel list   :" + listvalue );
						listsortvalue.addAll(listvalue);
						Collections.sort(listsortvalue);
						APP_LOGS.info("List of driver after applying sort function  :" + listsortvalue); 

						issorted=listvalue.equals(listsortvalue);
						if(issorted)
						{

							APP_LOGS.info("PASS: Sort by " + data.get("SortOption") + " is  working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");

						}else
						{
							APP_LOGS.info("FAIL: Sort by " + data.get("SortOption") + " is not working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");
						}
						listvalue.clear();listsortvalue.clear();
						break;


					case "Last update (recent first)":

						APP_LOGS.info("Sort Option selected is : "+data.get("SortOption"));
						List <WebElement> updaterecentlist=locators.telematicsAssetList;
						issorted=false;
						String query =properties.getProperty("queryforfeatchAssetSortbyLastupdatefirst");
						Statement stmt=SQLserverConnection();
						ResultSet rs = stmt.executeQuery(query);	
						while(rs.next()){

							listsortvalue.add(rs.getString(2));
						}
						
					
						for(WebElement asset: updaterecentlist)
						{			    		 
							String assetdetailval=asset.getText();
							String assetval[]=assetdetailval.split("\n");
							if (assetval[0].contains("Sally")) {
								break;
							}
							else
							{
							listvalue.add(assetval[0]);
							}
						}
						
						APP_LOGS.info("List of Asset fetch from asset panel list   :" + listvalue );
						APP_LOGS.info(" List of Asset fetch from data base based on Last update recent first :  " +  listsortvalue );

						issorted=listvalue.equals(listsortvalue);
						if(issorted)
						{

							APP_LOGS.info("PASS: Sort by " + data.get("SortOption") + " is  working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");

						}else
						{
							APP_LOGS.info("FAIL: Sort by " + data.get("SortOption") + " is not working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");
						}
						listvalue.clear();
						listsortvalue.clear();
						updaterecentlist.clear();
						query=null;
						break;
						

					case  "Last update (oldest first)":
						
						APP_LOGS.info("Sort Option selected is : "+data.get("SortOption"));
						List <WebElement> updaterecentlistupdaterecentlist=locators.telematicsAssetList;
						issorted=false;
						query =properties.getProperty("queryforfeatchAssetSortbyLastupdateLast");
						stmt=SQLserverConnection();
						ResultSet rs1 = stmt.executeQuery(query);	
						while(rs1.next()){
							listsortvalue.add(rs1.getString(2));
						}

						for(WebElement asset: updaterecentlistupdaterecentlist)
						{			    		 
							String assetdetailval=asset.getText();
							String assetval[]=assetdetailval.split("\n");
							if (assetval[0].contains("Sally")) {
								System.out.println("Sally:This asset's instance says never");
							}
							else
							{
							listvalue.add(assetval[0]);
							}

						}

						APP_LOGS.info("List of Asset fetch from asset panel list   :" + listvalue );
						APP_LOGS.info(" List of Asset fetch from data base based on Last update oldest first :  " +  listsortvalue );

						issorted=listvalue.equals(listsortvalue);
						if(issorted)
						{

							APP_LOGS.info("PASS: Sort by " + data.get("SortOption") + " is  working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");

						}else
						{
							APP_LOGS.info("FAIL: Sort by " + data.get("SortOption") + " is not working as expected "); 
							Assert.assertTrue(issorted, "Sort by " + data.get("SortOption") + " is not working as expected ");
						}
						listvalue.clear();
						listsortvalue.clear();
						break;
					default : 
						APP_LOGS.info("FAIL: Please enter correct option for Sorting");
					}
					break;
				}

				i++;
			}
	}

	
	
	
	
	
	
	

	// Test Script is to verify Preferences functionality for Assets------>REF-TESCTCASE:TELE03 
	@Test(priority=7 )
	public void  verifyPreferenceOptionDisplayingCorrectListOfOptionAndCheck() throws InterruptedException
	{
		
			String fieldsOptions="Select all,Location,Speed and limit,Odometer,Driver,Last update,Activity bar,Timeline";
			APP_LOGS.info("Asset Panel Preference Options are : "+fieldsOptions);
			String[] fieldsoption=fieldsOptions.split(",");
			ArrayList<String>  options=new ArrayList<String>();
			
			for(String option: fieldsoption)
			{
				options.add(option)	;
			}
			
			clickBtn(locators.preferencesIcon);
			Thread.sleep(5000);
			APP_LOGS.info("Fileds Option " + options);
			WebElement fieldsicon=locators.preferencesDropDownIcon;
			boolean icon=isElementExist(fieldsicon);
			APP_LOGS.info("PASS: Fields icon displaying under Asset Panel ");


			clickBtn(fieldsicon);
			Thread.sleep(5000);
			List <WebElement> fieldsoptionsele=locators.preferencesOptions;
			int i=0;
			for(WebElement fieldopt: fieldsoptionsele)
			{
				String optiontext=getobjStaticText(fieldopt).trim();

				if(optiontext.equals(options.get(i).trim()))
				{
					clickBtn(fieldopt);	
				}
				
				i++;
			}
			APP_LOGS.info("PASS: Field options  " + options + "  are  displaying under Field list ");
			APP_LOGS.info("Asset Panel Preference Options verified");
			clickBtn(locators.preferencesSelectAllOption);
	}
	
	
	
	
	
	
	// Test Script is to verify Preferences functionality for Assets using options like Location,Speed and limit,Odometer,Driver,Last update,Activity bar------>REF-TESCTCASE:TELE03 
	@Test(priority=8,dataProvider="PreferencesOptionTestData",dataProviderClass=DataProviders.class)
	public void verifyPreferencesOptions(Hashtable<String,String> data) throws InterruptedException
	{

		
			APP_LOGS.info("-----------------------verifyingPreferencesOptions-----------------");
			APP_LOGS.info("-----------------Field option "+ data.get("PreferencesOptionsForAsset") + " and check box " + data.get("Checkbox") + "----------------");
			ExcelUtils.checkExecution("AssetSuite", "verifyPreferencesOptions", data.get("RunMode"), excel);
			clickBtn(locators.preferencesIcon);
			Thread.sleep(5000);
			WebElement fieldsicon=locators.preferencesDropDownIcon;
			clickBtn(fieldsicon);
			//clickBtn(locators.preferencesSelectAllOption);
			

			switch(data.get("PreferencesOptionsForAsset"))
			{


			case "Location":
				
				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesLocationOption);
				boolean flg=fieldsavailableoptioncheckuncheckisworkingfine(locators.location,  data.get("Checkbox"));

				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is not working fine for " +  data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option " + data.get("PreferencesOptionsForAsset") + " check box is not working fine for " +  data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " +  data.get("Checkbox"));

				break;

			case "Speed and limit":
				
				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesSpeedAndLimitOption);

				flg=preferencesAvailableOptionCheckUncheckIsWorkingFine(locators.speedLimitHidden,locators.speedLimit, data.get("Checkbox"));

				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));

				break;
				
			case "Odometer":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesOdometerOption);
				waitForElement();
				
					flg=preferencesAvailableOptionCheckUncheckIsWorkingFine(locators.odometerHidden,locators.odometer, data.get("Checkbox"));

				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));

				break;
				
			case "Driver":
				
				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesDriverOption);
				
					flg=preferencesAvailableOptionCheckUncheckIsWorkingFine(locators.tele_DriverHidden,locators.tele_Driver, data.get("Checkbox"));

				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + " check box is not working fine for " + data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));


				break;
				
			case "Last update":

				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesLastUpdateOption);
				flg=fieldsavailableoptioncheckuncheckisworkingfine(locators.lastUpdate, data.get("Checkbox"));

				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));

				break;
				
			case "Activity bar":
				
				APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
				clickBtn(locators.preferencesActivityBarOption);
				flg=fieldsavailableoptioncheckuncheckisworkingfine(locators.activityBar, data.get("Checkbox"));
				//clickBtn(locators.preferencesActivityBarOption);
				if(!flg)
				{
					APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + " check box is not working fine for " + data.get("Checkbox"));
				}				  							
				Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is not working fine for " + data.get("Checkbox")); 
				APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));
				break;
				
				
			case "Timeline":
				

				try {
					APP_LOGS.info("Asset Panel Preference Options selected is : "+data.get("PreferencesOptionsForAsset"));
					clickBtn(locators.preferencesTimelineOption);
					flg=fieldsavailableoptioncheckuncheckisworkingfine(locators.timeline, data.get("Checkbox"));
					//clickBtn(locators.preferencesActivityBarOption);
					if(!flg)
					{
						APP_LOGS.info("FAIL: Filed option  " + data.get("PreferencesOptionsForAsset") + " check box is not working fine for " + data.get("Checkbox"));
					}				  							
					Assert.assertTrue(flg,"Filed option  " + data.get("PreferencesOptionsForAsset") + "  check box is working fine for " + data.get("Checkbox")); 
					APP_LOGS.info("PASS: Filed option " + data.get("PreferencesOptionsForAsset") + " check box is working fine for " + data.get("Checkbox"));

					break;
				} catch (Throwable t) {
					System.out.println(t);
				}

			default:

				APP_LOGS.info("FAIL: Filed option " + data.get("PreferencesOptionsForAsset") + "   is not valid field option");
				Assert.assertTrue(false, "FAIL: Please Provide valid field option");

			}

	}



	@AfterClass		
	public void Closebrowser()
	{
		cleanUp();
	}	  

}
