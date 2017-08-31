package com.ecm.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

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


	public class AccountRemindersVerificationTest  extends ECMUtil {
		Locators locators = null;
		Properties properties ;
		public ExcelReader excel;
		public static ExtentReports extent;
		public static ExtentTest test;
		public static ExtentTestInterruptedException testexception;
		
		@BeforeSuite
		public void beforeSuite(){
			extent = new ExtentReports("src/test/resources/extentreport/extentreport.html", true); //Provide Desired Report Directory Location and Name
		}
		
		@BeforeMethod()
		public  void beforeMethod(Method method){
			test = extent.startTest( (this.getClass().getSimpleName() +" :: "+  method.getName()),method.getName()); //Test Case Start Here
			test.assignAuthor("Shivaprasad Nagaraju"); //Test Script Author Name
		}
		
		@AfterMethod()
		public void tearDown(ITestResult result,Method method) throws IOException{
			if(result.getStatus()==ITestResult.FAILURE){			 
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
		public void  launchBrowserAndLoginToECM() throws IllegalMonitorStateException, Exception{ 
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
			System.out.println(properties.getProperty("queryforfeatchClasslist"));
			login_10duke();
		}
		
		
		//Verify list of Assets name and associated Driver is displaying under asset panel tab
			@Test(priority=2)
			public void verifyListOfAccountRemindersOnPanel() throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				
					APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
					clickBtn(locators.telematicsMainMenu);
					Thread.sleep(500);
					clickBtn(locators.accountRemindersSubMenu);
					waituntilnew(locators.accountRemindersGroupHeader, 20);
					String query =properties.getProperty("queryForAccountRemindersList");
					Statement stmt=SQLserverConnection();

					ArrayList<String> accountRemindersName = new ArrayList<String>();
					ResultSet rs = stmt.executeQuery(query);	
					while(rs.next()){
						accountRemindersName.add(rs.getString(1));
					}
					APP_LOGS.info(" Account Reminders are :  " +  accountRemindersName);
					List<WebElement>accountReminders=locators.accountRemindersOptionsList;
					
					if (isElementExist(locators.accountRemindersGroupHeader)){
						
						for (int i = 0; i < (accountRemindersName.size() & accountReminders.size()); i++){
							String accReminderName= getobjStaticText(accountReminders.get(i));
							if (accountRemindersName.get(i).equals(accReminderName)) {
								System.out.println("Account Reminders are matching from UI & DB: "+accountRemindersName.get(i)+"="+accReminderName);
							}
						}
					}
					
					clickBtn(locators.accountRemindersPanelCloseButton);
				
			}
			
			
			//Verify list of Assets name and associated Driver is displaying under asset panel tab
			@Test(priority=4,dataProvider="FilterFunctionalityForAccountRemindersTestData", dataProviderClass=DataProviders.class)
			public void verifyFilterFunctionalityForAccountReminders(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
				clickBtn(locators.telematicsMainMenu);
				Thread.sleep(500);
				clickBtn(locators.accountRemindersSubMenu);
				waituntilnew(locators.accountRemindersGroupHeader, 20);
							
				if (isElementExist(locators.createNewReminderTypeButton )) {
					Assert.assertEquals("Create new reminder type", getobjStaticText(locators.createNewReminderTypeButton));
				};
							
				enterTextToInputField(locators.accountRemindersFilterInputbox, data.get("Filter value"));
				Thread.sleep(500);
				String[] res= data.get("No of search results").split("\\.");
							
				if (locators.accountRemindersOptionsList.size()==Integer.valueOf(res[0])) {
					System.out.println("filter functionality is working: test results count for the Input '"+data.get("Filter value")+"' is "+res[0]);
				}
				clearInputField(locators.accountRemindersFilterInputbox);
				Thread.sleep(500);
				clickBtn(locators.accountRemindersPanelCloseButton);
			}
					
					
					//Verify list of Assets name and associated Driver is displaying under asset panel tab
			@Test(priority=3)
			public void verifyEditAndDeleteFunctionalityForAccountReminders() throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
				clickBtn(locators.telematicsMainMenu);
				Thread.sleep(500);
				clickBtn(locators.accountRemindersSubMenu);
				waituntilnew(locators.accountRemindersGroupHeader, 20);

				List<WebElement> optionsList= locators.accountRemindersOptionsList;
				for (WebElement webElement : optionsList){
					//clickBtn(webElement);
					if (isElementExist(locators.accountRemindersDeleteButton)&isElementExist(locators.accountRemindersEditButton)) {
						Thread.sleep(500);
						//clickBtn(locators.accountRemindersDeleteButton);
						System.out.println("Edit & Delete Options Available for the group");
						test.log(LogStatus.PASS, "verifyEditAndDeleteFunctionalityForAccountReminders successful");
					}else {
							Assert.assertFalse(false);
					}
					break;
				}

				clickBtn(locators.accountRemindersPanelCloseButton);
			}
					
		@AfterClass		
		public void Closebrowser(){
			cleanUp();
		}	
		
		@AfterSuite()
		protected void afterSuite() {
			extent.close(); 
		}

					
	}
