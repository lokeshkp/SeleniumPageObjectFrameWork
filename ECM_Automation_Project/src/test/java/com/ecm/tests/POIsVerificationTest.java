package com.ecm.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.runtime.directive.Foreach;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import com.ecm.util.DataProviders;
import com.ecm.util.ExcelConstants;
import com.ecm.util.ExcelReader;
import com.ecm.util.Locators;
import com.ecm.util.ECMUtil;
import com.ecm.util.listeners.ErrorCollector;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;

	
	public class POIsVerificationTest extends ECMUtil {
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
		public void verifyListOfPOIsGroupsAndPOIsNameOnPOIPanel() throws IOException, InterruptedException, ClassNotFoundException, SQLException
		{
			
				APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
				clickBtn(locators.telematicsMainMenu);
				Thread.sleep(500);
				clickBtn(locators.poisSubMenu);
				waituntilnew(locators.poisHeader, 20);
				clickBtn(locators.poisDropDownButton);
				Thread.sleep(500);
				String query =properties.getProperty("queryForPOISGroups");
				Statement stmt=SQLserverConnection();

				ArrayList<String> poisGroups = new ArrayList<String>();
				ResultSet rs = stmt.executeQuery(query);	

				while(rs.next()){
					poisGroups.add(rs.getString(1));
				}

				System.out.println(" pois Groups names : " + poisGroups);
				
				List <WebElement> poisGroupNames= locators.poisGroups;
				
				for (int i = 0; i < (poisGroupNames.size() & poisGroups.size()) ; i++) 
				{
					String poiGroup= getobjStaticText(poisGroupNames.get(i));
					if (poisGroups.get(i).trim().equalsIgnoreCase(poiGroup)) 
					{
						System.out.println("POI groups are matching: "+poisGroups.get(i).trim()+" = "+poiGroup);
					} else {
						System.out.println("POI groups are not matching: "+poisGroups.get(i).trim()+" != "+poiGroup);
					}
				}
				
				
				ArrayList<String> pois = new ArrayList<String>();
				String query1 =properties.getProperty("queryForPOISName");
				ResultSet rs1 = stmt.executeQuery(query1);	

				while(rs1.next()){
					pois.add(rs1.getString(1));
				}

				System.out.println(" pois names : " + pois);
				
				List <WebElement> poisNames= locators.pois;
				
				for (int i = 0; i < (poisNames.size() & pois.size()) ; i++) 
				{
					String poi= getobjStaticText(poisNames.get(i));
					if (pois.get(i).trim().equalsIgnoreCase(poi)) 
					{
						System.out.println("POI groups are matching: "+pois.get(i).trim()+" = "+poi);
					} else {
						System.out.println("POI groups are not matching: "+pois.get(i).trim()+" != "+poi);
					}
				}
		}
		
		
		
		
		//Verify list of POis group and associated POis name is displaying under POIS panel tab
				@Test(priority=3)
				public void verifyInvidualPOIsGroupsAndRelatedPOIsNameOnPOIPanel() throws IOException, InterruptedException, ClassNotFoundException, SQLException
				{
					
						APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
						clickBtn(locators.telematicsMainMenu);
						Thread.sleep(500);
						clickBtn(locators.poisSubMenu);
						waituntilnew(locators.poisHeader, 20);
						clickBtn(locators.poisDropDownButton);
						Thread.sleep(500);
						clickBtn(locators.poisSelectAllCheckbox);


						List <WebElement> poisOnPanel= locators.pois;
						if (poisOnPanel.size()==0) 
						{
							System.out.println("POI groups select all checkbox is working fine: ");
						} else {
							System.out.println("POI groups select all checkbox is not working fine:");
						}

						Thread.sleep(500);
						clickBtn(locators.poisSelectAllCheckbox);
						Thread.sleep(500);

						Statement stmt=SQLserverConnection();
						ArrayList<String> pois = new ArrayList<String>();
						String query1 =properties.getProperty("queryForPOISName");
						ResultSet rs1 = stmt.executeQuery(query1);	

						while(rs1.next()){
							pois.add(rs1.getString(1));
						}

						System.out.println(" pois names : " + pois);

						List <WebElement> poisNames= locators.pois;

						for (int i = 0; i < (poisNames.size() & pois.size()) ; i++) 
						{
							String poi= getobjStaticText(poisNames.get(i));
							if (pois.get(i).trim().equalsIgnoreCase(poi)) 
							{
								System.out.println("POI names are matching: "+pois.get(i).trim()+" = "+poi);
							} else {
								System.out.println("POI names are not matching: "+pois.get(i).trim()+" != "+poi);
							}
						}
						Thread.sleep(500);
						clickBtn(locators.poisSelectAllCheckbox);
						
						
						
						List <WebElement> poisGroupNames= locators.poisGroups;

						for (WebElement webElement : poisGroupNames) 
						{
							clickBtn(webElement);
							String group= getobjStaticText(webElement);
							System.out.println("The group selected is: "+group);
							
							if (group.equalsIgnoreCase("Engineer's Home Address")) 
							{
								ArrayList<String> poisNamesFromDb = new ArrayList<String>();
								String query =properties.getProperty("queryForIndividualPOISGroupResults1")+"and grp.GroupId=2"+properties.getProperty("queryForIndividualPOISGroupResults2");

								ResultSet rs = stmt.executeQuery(query);	
								while(rs.next()){
									poisNamesFromDb.add(rs.getString(1));
								}

								System.out.println(" pois names : " + poisNamesFromDb);

								List <WebElement> poisName= locators.pois;

								for (int i = 0; i < (poisName.size() & poisNamesFromDb.size()) ; i++) 
								{
									String poi= getobjStaticText(poisName.get(i));
									if (poisNamesFromDb.get(i).trim().equalsIgnoreCase(poi)) 
									{
										System.out.println("POI names are matching: "+poisNamesFromDb.get(i).trim()+" = "+poi);
									} else {
										System.out.println("POI names are not matching: "+pois.get(i).trim()+" != "+poi);
									}
								}
							}
							else 
							{
								ArrayList<String> poisNamesFromDb = new ArrayList<String>();
								String query =properties.getProperty("queryForIndividualPOISGroupResults1")+getobjStaticText(webElement)+properties.getProperty("queryForIndividualPOISGroupResults2");

								ResultSet rs = stmt.executeQuery(query);	
								while(rs.next()){
									poisNamesFromDb.add(rs.getString(1));
								}

								System.out.println(" pois names : " + poisNamesFromDb);

								List <WebElement> poisName= locators.pois;

								for (int i = 0; i < (poisName.size() & poisNamesFromDb.size()) ; i++) 
								{
									String poi= getobjStaticText(poisName.get(i));
									if (poisNamesFromDb.get(i).trim().equalsIgnoreCase(poi)) 
									{
										System.out.println("POI names are matching: "+poisNamesFromDb.get(i).trim()+" = "+poi);
									} else {
										System.out.println("POI names are not matching: "+pois.get(i).trim()+" != "+poi);
									}
								}
							}

							clickBtn(webElement);
						}

						Thread.sleep(500);
						clickBtn(locators.poisSelectAllCheckbox);

				}	
				
				//Verify list of Assets name and associated Driver is displaying under asset panel tab
				@Test(priority=4,dataProvider="FilterFunctionalityForPOISTestData", dataProviderClass=DataProviders.class)
				public void verifyFilterFunctionalityForPOIs(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException
				{
					
						APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
						clickBtn(locators.telematicsMainMenu);
						Thread.sleep(500);
						clickBtn(locators.poisSubMenu);
						waituntilnew(locators.poisHeader, 20);
						
						enterTextToInputField(locators.poisFilterInputbox, data.get("Filter value"));
						Thread.sleep(500);
						String[] res= data.get("No of search results").split("\\.");
						if (locators.poisOptionsList.size()==Integer.valueOf(res[0])) 
						{
							System.out.println("filter functionality is working: test results count for the Input '"+data.get("Filter value")+"' is "+res[0]);
							
						}
						
						clearInputField(locators.poisFilterInputbox);
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
				
				@AfterClass		
				public void Closebrowser()
				{
					cleanUp();
				}	  
			
				@AfterSuite()
				protected void afterSuite() 
				{
					extent.close();  // close the Test Suite
				}
				

}
