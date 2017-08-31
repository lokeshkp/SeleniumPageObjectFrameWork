package com.ecm.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.StaleElementReferenceException;
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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;


	public class ECMReportsVerificationTest  extends ECMUtil {
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
				excel = new ExcelReader(ExcelConstants.JOURNEYSUITE_XL_PATH);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		
		
		// Test Script is to verify Login functionality using 10duke
		@Test(priority=1)
		public void  launchBrowserAndLoginToTelematics() throws IllegalMonitorStateException, Exception{ 
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
			login_ReportsWebClient();
		}
		
		
		//Verify list of Assets name and associated Driver is displaying under asset panel tab
		//@Test(priority=2,dataProvider="VerifyListOfReports", dataProviderClass=DataProviders.class)
		public void verifyListOfReports(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
			for (int j = 0; j < locators.reportsList.size(); j++){
				System.out.println(getobjStaticText(locators.reportsList.get(j)));
			}
				
				System.out.println("Report count: "+ locators.reportsList.size());
				
				/*for (int i = 0; i < locators.reportsList.size(); i++) 
				{
					try 
					{
						System.out.println("Report selected is: "+getobjStaticText(locators.reportsList.get(i)));
						clickBtn(locators.reportsList.get(i));
						Thread.sleep(500);
						waituntilnew(locators.reportsAccountSelector, 20);
						selectDropDownByText(locators.reportsAccountSelector, data.get("AccountSelector"));
						Thread.sleep(500);
						if (isElementExist(locators.reportsDateRange)) 
						{
							selectDropDownByText(locators.reportsDateRange, data.get("DateRange"));
							Thread.sleep(500);
						} 

						if (isElementExist(locators.reportsEventOption)) 
						{
							for (WebElement event : locators.reportsEventTypes) 
							{
								selectCheckbox(event);
							}
						}
						if (isElementExist(locators.reportsTimeRange)) 
						{
							clickBtn(locators.reportsTimeRange);
						}

						clickBtn(locators.reportsView);
						if (isElementExist(locators.reportsEventTypeMessage)) 
						{
							System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

						}
						else 
						{
							waituntilnew(locators.reportsViewHeader, 200);
							System.out.println("Reports Header:  "+getobjStaticText(locators.reportsViewHeader));
							Thread.sleep(1000);
							for (WebElement column : locators.reportsColumns) 
							{
								System.out.println(getobjStaticText(column)+" \n");
							}

							navigateToPreviousPage();
							Thread.sleep(500);
							navigateToPreviousPage();
							Thread.sleep(500);
							navigateToPreviousPage();
							Thread.sleep(500);
						}
					} 
					catch (Throwable t)
					{
						System.out.println(t);
					}

					//break;
				}*/

		}
			
			
		@Test(priority=5,dataProvider="VerifyDriverPerformanceSummaryNonCANReport", dataProviderClass=DataProviders.class)
		public void verifyDriverPerformanceSummaryNonCANReport(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				
			try{
				reportsVerification(locators.reportsDriverPerformanceSummaryNonCAN,locators.reportsDriverPerformanceSummaryNonCANTdColumn,locators.reportsDriverPerformanceSummaryNonCANTdColumnNextPages, data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName"));
			}catch (StaleElementReferenceException e) {
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
			}
				
		}
			
			
		@Test(priority=3,dataProvider="VerifyDriverPerformanceSummaryByDateReport", dataProviderClass=DataProviders.class)
		public void verifyDriverPerformanceSummaryByDateReport(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				
			try{	
			reportsVerification(locators.reportsDriverPerformanceSummaryByDate, locators.reportsDriverPerformanceSummaryByDateTdColumn,locators.reportsDriverPerformanceSummaryByDateTdColumnNextPages, data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName"));	
			}catch (StaleElementReferenceException e){
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
			}	
			
		}
			
			
		@Test(priority=4,dataProvider="VerifyDriverPerformanceSummaryReport", dataProviderClass=DataProviders.class)
		public void verifyDriverPerformanceSummaryReport(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				
			try {	
			summaryReportsVerification(locators.reportsDriverPerformanceSummary, locators.reportsDriverPerformanceSummaryFirstPage, locators.reportsDriverPerformanceSummarySecondPage, locators.reportsDriverPerformanceSummaryThirdPage, locators.reportsDriverPerformanceSummaryTdColumnNextPages,data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName"));
			} catch (StaleElementReferenceException e) {
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
			}	
				
		}
			
			
			@Test(priority=6,dataProvider="VerifyHarshBrakeAndAccelerationsReport", dataProviderClass=DataProviders.class)
			public void verifyHarshBrakeAndAccelerationsReport(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException
			{
				int harshAccAndBrakeCountFrmHarshDrivingReport=0, harshAccAndBrakeCountFrmCanBusJourneySummariesReport=0;

				try 
				{	
					harshAccAndBrakeCountFrmHarshDrivingReport=reportsVerificationBetweenReportsInHarshDrivingReports(locators.reportsHarshDrivingEvents, locators.reportsHarshDrivingFirstPage,locators.reportsHarshDrivingTdColumnNextPages, data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName1"));	
				} 
				catch (StaleElementReferenceException e) 
				{
					navigateToPreviousPage();
					Thread.sleep(700);
					clickBtn(locators.homeHeader);
					Thread.sleep(800);
				}	

				try 
				{	
					harshAccAndBrakeCountFrmCanBusJourneySummariesReport=reportsVerificationBetweenReportsInCANBusJourneySummaries(locators.reportsCANBusJourneySummariesReportBydate, locators.reportsHarshAccelerationFirstPage,locators.reportsHarshBrakingFirstPage,locators.reportsHarshAccelerationTdColumnNextPages,locators.reportsHarshBrakingTdColumnNextPages, data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName2"));	
				} 
				catch (StaleElementReferenceException e) 
				{
					navigateToPreviousPage();
					Thread.sleep(700);
					clickBtn(locators.homeHeader);
					Thread.sleep(800);
				}
				
				if (harshAccAndBrakeCountFrmHarshDrivingReport==harshAccAndBrakeCountFrmCanBusJourneySummariesReport)
				{
					System.out.println("Pass:Values are matching");
				} 
				else 
				{
					Assert.fail("Values are not matching");
					System.out.println("Fail:Values are not matching");
				}

			}
			
			
			@Test(priority=7,dataProvider="VerifyDriverDailySummary", dataProviderClass=DataProviders.class)
			public void verifyDriverDailySummary(Hashtable<String,String> data) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
				
				try{	
					reportsVerificationForNegativeValues(locators.reportsDriverDailySummary, locators.reportsDriverDailySummaryFirstPage,locators.reportsDriverDailySummaryTdColumnNextPages, data.get("AccountSelector"), data.get("DateRange"), data.get("ReportName"));	
				} 
				catch (StaleElementReferenceException e) {
					navigateToPreviousPage();
					Thread.sleep(700);
					clickBtn(locators.homeHeader);
					Thread.sleep(800);
				}	
			
			}
			
			
			@AfterClass		
			public void Closebrowser(){
				//cleanUp();
			}					
	}
