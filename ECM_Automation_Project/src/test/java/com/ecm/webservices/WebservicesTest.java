package com.ecm.webservices;

import static com.jayway.restassured.RestAssured.when;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

















import com.ecm.util.DataProviders;
import com.ecm.util.ExcelConstants;
import com.ecm.util.ExcelReader;
import com.ecm.util.ExcelUtils;
import com.ecm.util.Locators;
import com.ecm.util.ECMUtil;
import com.ecm.util.listeners.ErrorCollector;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;


public class WebservicesTest extends ECMUtil{
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTestInterruptedException testexception;
	Locators locators = null;
	Properties properties ;
	public ExcelReader excel;
	
	@BeforeSuite
	public void beforeSuite() 
	{
		extent = new ExtentReports("src/test/resources/extentreport/extentreport.html", true); //Provide Desired Report Directory Location and Name
		extent.loadConfig(new File("extent-config.xml"));
		extent.addSystemInfo("Environment","QA-Sanity"); //It will provide Execution Machine Information
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
			excel = new ExcelReader(ExcelConstants.WEBSERVICES_XL_PATH);
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
		
		test.log(LogStatus.PASS, "launchBrowserAndLoginToTelematics is success");
	}
	
	/*@Test(priority=2, dataProvider="webServicesList", dataProviderClass=DataProviders.class)
	public void webServicesSanityTest(Hashtable<String,String> data)
	{
		report.startTest("webServicesSanityTest");
		int code;
		String statusCode = null;
		Response resp = null;
		
		System.out.println("This Test is to cover Webservices Response Verification");
		ExcelUtils.checkExecution("WebServicesSuite", "webServicesSanityTest", data.get("RunMode"), excel);
		
		try 
		{
			 resp= when().
							get(data.get("Webservice"));
			 				code= resp.getStatusCode();
			 				statusCode= Integer.toString(code);
							System.out.println(resp.getStatusCode());
							Assert.assertEquals(resp.getStatusCode(), data.get("StatusCode"));
							System.out.println(resp.asString());
							System.out.println("Pass "+ statusCode+" "+ resp.asString());
		} 
		catch (Exception e) 
		{
			System.out.println("Fail "+statusCode+" "+resp.asString());
		}
		report.endTest();
	}*/
	
	@Test(priority=2)
	public void  failureTest() throws IllegalMonitorStateException, Exception
	{ 
		
			APP_LOGS.info("\n\n--------------------launchbrowsersetPropertiesLogFile--------------------\n\n");
			login_10duke();
			Assert.fail();
		
	}
	
	@Test(priority=3)
	public void  skipTest() throws IllegalMonitorStateException, Exception
	{ 
		Assert.fail();
		//test.log(LogStatus.SKIP, "skipped");
		//throw new SkipException("skipped");
		
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
	
	
	@AfterTest
	public void tearDown(){
		
		
	cleanUp();
		
	}
	@AfterSuite()
	protected void afterSuite() 
	{
		extent.close(); 
	}

}
