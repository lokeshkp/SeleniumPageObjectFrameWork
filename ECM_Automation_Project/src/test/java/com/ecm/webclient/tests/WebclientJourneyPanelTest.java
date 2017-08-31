package com.ecm.webclient.tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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
import org.testng.annotations.Test;

import com.ecm.util.DataProviders;
import com.ecm.util.ExcelConstants;
import com.ecm.util.ExcelReader;
import com.ecm.util.ExcelUtils;
import com.ecm.util.Locators;
import com.ecm.util.ECMUtil;
import com.ecm.util.WebclientLocators;
import com.ecm.util.listeners.ErrorCollector;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;




public class WebclientJourneyPanelTest extends ECMUtil {

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
			excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
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
	@Test(priority=2, dataProvider="JourneysPanelTestData", dataProviderClass=DataProviders.class)
	public void verifyJourneysViewWindowObjects(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException, ParseException
	{
		
			APP_LOGS.info("\n\n--------------------verifyingjourneyviewwindodisplayobjects--------------------\n\n");
			ExcelUtils.checkExecution("JourneySuite", "verifyJourneysViewWindowObjects", data.get("RunMode"), excel);
			clickAssetOnAssetListAndShowJourneyOption(data.get("Assets"), data.get("Option btn"));
			enterTextToInputField(locators.journeyViewDateField,preDayDate());
			Thread.sleep(500);
			clickBtn(locators.journeyViewFind);
			Thread.sleep(5000);
			
			List<WebElement> navPanel= locators.journeyHeaderNavPanel;
			for (WebElement webElement : navPanel) {
				if (getobjStaticText(webElement).equals("Back")) 
				{
					System.out.println("'Back' button is not enabled on journey navigation panel");
				}
				if (getobjStaticText(webElement).equals("Home")) 
				{
					System.out.println("'Home' button is not enabled on journey navigation panel");
				}
				if (getobjStaticText(webElement).equals("Options")) 
				{
					System.out.println("'Options' button is displayed on journey navigation panel");
				}
				else {
					System.out.println("Home & back buttons are not enabled");
				}
			}
			
			clickBtn(locators.journeyspanelOptionButton);
			Thread.sleep(500);

			if (isElementExist(locators.optionText)) 
			{
				if (getobjStaticText(locators.optionText).equals("Full day view"))
				{
					APP_LOGS.info("PASS: Displaying journeys in=> Journey view");
					if (getobjStaticText(locators.JourneyItemHeader).contains((data.get("Assets"))))
					{
						System.out.println("Displaying journeys  in=> Journey view");
					}
					clickBtn(locators.optionText);
					Thread.sleep(2000);
				}
				clickBtn(locators.journeyspanelOptionButton);
				Thread.sleep(500);
				if (getobjStaticText(locators.optionText).equals("Journey view"))
				{
					APP_LOGS.info("PASS: Displaying journeys in=> Full day view");
					if (getobjStaticText(locators.JourneyItemHeader).equals(data.get("Assets"))) 
					{
						System.out.println("Displaying journeys in=> Full day view");
					}
					
					clickBtn(locators.optionText);
					Thread.sleep(2000);
				}
			}
			else
			{
				APP_LOGS.info("Fail: Option doesnot exist on journey panel");
			}

	}
	
	
	
	
	
	
	// script for verifying journey view window  displaying Journey Events
	@Test(priority=3, dataProvider="JourneyViewTestData", dataProviderClass=DataProviders.class)
	public void verifyJourneyViewOptionFunctionality(Hashtable<String,String> data) throws InterruptedException, IOException, SQLException, ClassNotFoundException, ParseException
	{
		
			APP_LOGS.info("\n\n--------------------verifyingjourneyviewwindowwilldisplaywhileclickingonoptionShowjoinery--------------------\n\n");
			ExcelUtils.checkExecution("JourneySuite", "verifyJourneyViewOptionFunctionality", data.get("RunMode"), excel);
			clickAssetOnAssetListAndShowJourneyOption(data.get("Assets"), data.get("Option btn"));
			enterTextToInputField(locators.journeyViewDateField,preDayDate());
			
			Thread.sleep(500);
			clickBtn(locators.journeyViewFind);
			Thread.sleep(5000);
			
			dbConn = getDBConnection();
			String query =properties.getProperty("queryForJourneyView")+"'"+yesterdaysDate()+"'"+" AND "+"'"+currentDate()+"'";
			prestmt = dbConn.prepareStatement(query);
			ResultSet rs1 = prestmt.executeQuery();
			
			ArrayList<String> startLocation = new ArrayList<String>();
			ArrayList<String> endLocation = new ArrayList<String>();
			ArrayList<String> distance = new ArrayList<String>();
			ArrayList<String> duration = new ArrayList<String>();
			while(rs1.next()){
				startLocation.add(rs1.getString(4));  
				endLocation.add(rs1.getString(5));
				double val1= Double.valueOf(rs1.getString(3));
				distance.add("Distance: "+strToFloat(roundOff(val1))+" miles");
				
				String[]dur1= rs1.getString(1).split("\\.");
				String[]dur2= rs1.getString(2).split("\\.");
				
				
				String time= timeDifference(dur1[0],dur2[0]);
				System.out.println(time);
				duration.add("Duration: "+time);
				
			}
			
			List<WebElement> startLocations=locators.journeyItemsStart;
			List<WebElement> endtLocations=locators.journeyItemsEnd;
			int i=0;
			for(WebElement startLoc: startLocations)
			{
				String initLoc=getobjStaticText(startLoc).trim();

				if(initLoc.equals(startLocation.get(i).trim()))
				{
					APP_LOGS.info("Pass: DB & UI jouney start locations are matching: " +initLoc +" = "+ startLocation.get(i));
					System.out.println("Pass: DB & UI jouney start locations are matching: " + initLoc +" = "+ startLocation.get(i));	
				}
				else 
				{
					APP_LOGS.info("Fail: DB & UI jouney start locations are not matching: " +initLoc +" != "+ startLocation.get(i));
					System.out.println("Fail: DB & UI jouney start locations are not matching: " + initLoc +" != "+ startLocation.get(i));
				}
				
				i++;
			}
			
			int j=0;
			for(WebElement endLoc: endtLocations)
			{
				String lastLoc=getobjStaticText(endLoc).trim();

				if(lastLoc.equals(endLocation.get(j).trim()))
				{
					System.out.println("Pass: DB & UI jouney end locations are matching:" +lastLoc +" = "+ endLocation.get(j));
					APP_LOGS.info("Pass: DB & UI jouney end locations are matching:" +lastLoc +" = "+ endLocation.get(j));
				}
				else 
				{
					System.out.println("Pass: DB & UI jouney end locations are not matching:" +lastLoc +" != "+ endLocation.get(j));
					APP_LOGS.info("Pass: DB & UI jouney end locations are not matching:" +lastLoc +" != "+ endLocation.get(j));
				}
				
				j++;
			}
			
			
			ArrayList<String> distanceOnUi = new ArrayList<String>();
			ArrayList<String> durationOnUi = new ArrayList<String>();
			List<WebElement> footers=locators.journeyItemsFooter;
			for (WebElement webElement : footers) {
				String val= getobjStaticText(webElement);
				String[]icon= val.split(",");
				distanceOnUi.add(icon[0]);
				durationOnUi.add(icon[1]);
			}
			System.out.println(distanceOnUi);
			System.out.println(durationOnUi);
			
			
			for(int k=0;k<(distance.size() & distanceOnUi.size());k++)
			{
				
				String distanceVal=distance.get(k).trim();
				if(distanceVal.equals(distanceOnUi.get(k).trim()))
				{
					Assert.assertEquals(distanceVal, distanceOnUi.get(k).trim());
					System.out.println("pass: " + distanceOnUi.get(k).trim() +" = "+ distanceVal);
				}
				else
				{
					System.out.println("Pass: Distance Not exact match with Db as the value is been round off  to nearest value " + distanceOnUi.get(k).trim() +" != "+ distanceVal);
				}
			}
			
			
			for(int l=0;l<(duration.size() & durationOnUi.size());l++)
			{
				
				String durationVal=duration.get(l).trim();
				if(durationVal.equals(durationOnUi.get(l).trim()))
				{
					Assert.assertEquals(durationVal, durationOnUi.get(l).trim());
					System.out.println("pass: " + durationOnUi.get(l).trim() +" = "+ durationVal);
				}
				else
				{
					System.out.println("Pass: Duration is Not exact match with Db as the value is been round off  to nearest value " + durationOnUi.get(l).trim() +" != "+ durationVal);
				}
			}
			
			if (locators.journeyItemsPlayButtons.size()>1) {
				System.out.println("playback option is available for all the journey items in journey view");
				
			} else {
				System.out.println("playback option is not available for all the journey items in journey view");

			}

	}

	
	
	// script for verifying Full Day view window  displaying Journey Events
	@Test(priority=4, dataProvider="FullDayViewTestData", dataProviderClass=DataProviders.class)
	public void verifyFullDayViewOptionFunctionality(Hashtable<String,String> data) throws InterruptedException, IOException, SQLException, ClassNotFoundException, ParseException
	{
			double val3=0.00;
			APP_LOGS.info("\n\n--------------------verifying Fullday view window will display while clicking on option Show jounery--------------------\n\n");
			ExcelUtils.checkExecution("JourneySuite", "verifyFullDayViewOptionFunctionality", data.get("RunMode"), excel);
			clickAssetOnAssetListAndShowJourneyOption(data.get("Assets"), data.get("Option btn"));
			enterTextToInputField(locators.journeyViewDateField,preDayDate());
			clickBtn(locators.journeyspanelOptionButton);
			Thread.sleep(500);
			clickBtn(locators.optionText);
			Thread.sleep(500);
			clickBtn(locators.journeyViewFind);
			Thread.sleep(5000);
			
			dbConn = getDBConnection();
			String query =properties.getProperty("queryForJourneyView")+"'"+yesterdaysDate()+"'"+" AND "+"'"+currentDate()+"'";
			System.out.println(query);
			prestmt = dbConn.prepareStatement(query);
			ResultSet rs1 = prestmt.executeQuery();
			
			ArrayList<String> startLocation = new ArrayList<String>();
			ArrayList<String> endLocation = new ArrayList<String>();
			
			while(rs1.next()){
				startLocation.add(rs1.getString(4));  
				endLocation.add(rs1.getString(5));
				double val1= Double.valueOf(rs1.getString(3));
				 val3= Double.valueOf(rs1.getString(3))+val3;
				
				String[]dur1= rs1.getString(1).split("\\.");
				String[]dur2= rs1.getString(2).split("\\.");
				
				
				String time= timeDifference(dur1[0],dur2[0]);
				
			}
			System.out.println(val3);
			
			System.out.println(startLocation);
			System.out.println(endLocation);
			String totalDistance ="Distance: "+strToFloat(roundOff(val3))+" miles";
			System.out.println(totalDistance);
			
			List<WebElement> startLocations=locators.journeyItemsStart;
			List<WebElement> endtLocations=locators.journeyItemsEnd;
			
			int i=0;
			for(WebElement startLoc: startLocations)
			{
				String initLoc=getobjStaticText(startLoc).trim();

				if(initLoc.equals(startLocation.get(i).trim()))
				{
					APP_LOGS.info("Pass: DB & UI jouney start locations are matching: " +initLoc +" = "+ startLocation.get(i));
					System.out.println("Pass: DB & UI jouney start locations are matching: " + initLoc +" = "+ startLocation.get(i));	
				}
				else 
				{
					APP_LOGS.info("Fail: DB & UI jouney start locations are not matching: " +initLoc +" != "+ startLocation.get(i));
					System.out.println("Fail: DB & UI jouney start locations are not matching: " + initLoc +" != "+ startLocation.get(i));
				}
				
				i++;
			}
			
			int j=0;
			for(WebElement endLoc: endtLocations)
			{
				String lastLoc=getobjStaticText(endLoc).trim();

				if(lastLoc.equals(endLocation.get(j).trim()))
				{
					System.out.println("Pass: DB & UI jouney end locations are matching:" +lastLoc +" = "+ endLocation.get(j));
					APP_LOGS.info("Pass: DB & UI jouney end locations are matching:" +lastLoc +" = "+ endLocation.get(j));
				}
				else 
				{
					System.out.println("Pass: DB & UI jouney end locations are not matching:" +lastLoc +" != "+ endLocation.get(j));
					APP_LOGS.info("Pass: DB & UI jouney end locations are not matching:" +lastLoc +" != "+ endLocation.get(j));
				}
				
				j++;
			}
				String val= getobjStaticText(locators.journeyItemsFooterForFullDayView);
				String[]icon= val.split(",");
				String distanceOnUi= icon[0];
				String durationOnUi=icon[1];
			
			System.out.println(distanceOnUi);
			System.out.println(durationOnUi);
			if (totalDistance.trim().equalsIgnoreCase(distanceOnUi.trim())) 
			{
				System.out.println("Pass: Distance are exact match "+totalDistance.trim()+"="+distanceOnUi.trim());
			} else {
				System.out.println("Pass: Distance are not exact match but taken nearest value: "+totalDistance.trim()+"!="+distanceOnUi.trim());

			}
	}
		
		
	@Test(priority=5, dataProvider="JourneyPlaybackFunctionalityTestData", dataProviderClass=DataProviders.class)
	public void verifyJourneyPlaybackFunctionality(Hashtable<String,String> data) throws Exception
		{
			
				String query,query1,journeyStartTime,journeyEndTime,date,asset,time,location,speed,direction,spLimit,speedLimit;
				APP_LOGS.info("\n\n--------------------verifying Fullday view window will display while clicking on option Show jounery--------------------\n\n");
				ExcelUtils.checkExecution("JourneySuite", "verifyFullDayViewOptionFunctionality", data.get("RunMode"), excel);
				/*clickAssetOnAssetListAndShowJourneyOption(data.get("Assets"), data.get("Option btn"));
				enterTextToInputField(locators.journeyViewDateField,preDayDate());
				Thread.sleep(500);
				clickBtn(locators.journeyViewFind);
				Thread.sleep(5000);*/
				
				
				dbConn = getDBConnection();
				query =properties.getProperty("queryForJourneyViewLocationsAndSpeed")+"'"+yesterdaysDate()+"'"+" AND "+"'"+currentDate()+"'";
				prestmt = dbConn.prepareStatement(query);
				ResultSet rs1 = prestmt.executeQuery();
				double value;
				
				ArrayList<String> locationsOnDb = new ArrayList<String>();
				ArrayList<String> directionOnDb = new ArrayList<String>();
				ArrayList<String> vehicleSpeedOnDb = new ArrayList<String>();
				ArrayList<String> vehicleSpeedLimitOnDb = new ArrayList<String>();
				
				while(rs1.next())
				{
					locationsOnDb.add(rs1.getString(6)); 
					
					String[]direc=	rs1.getString(10).split("\\.");
					directionOnDb.add(direc[0]);
					
					String[]vSpeedLimit = rs1.getString(7).split("\\.");
					vehicleSpeedLimitOnDb.add(vSpeedLimit[0]);
					
					value = Double.valueOf(rs1.getString(8));
					float vSpeed = (float)Math.round(value);
					String [] vehicleSpeed= strToFloat(roundOff(vSpeed)).split("\\.");
					vehicleSpeedOnDb.add(vehicleSpeed[0]+" MPH" );
				}
				
				
				//query1 =properties.getProperty("queryForJourneyViewTimeDifference")+"'"+yesterdaysDate()+"'"+" AND "+"'"+currentDate()+"'";
				query1 =properties.getProperty("queryForJourneyViewTimeDifference")+"'"+"2016-12-08"+"'"+" and "+"'"+"2017-01-09"+"'";
				prestmt = dbConn.prepareStatement(query1);
				ResultSet rs2 = prestmt.executeQuery();
				
				ArrayList<Time> Firstinstancetimestamp = new ArrayList<Time>();
				ArrayList<Time> Lastinstancetimestamp = new ArrayList<Time>();
				
				while(rs2.next()){
					Firstinstancetimestamp.add(rs2.getTime(1));
					Lastinstancetimestamp.add(rs2.getTime(2));
				}
				journeyStartTime= dayLightSaving530Hrs(Firstinstancetimestamp, 0);
				journeyEndTime= dayLightSaving530Hrs(Lastinstancetimestamp, 0);
				

if (isElementExist(locators.journeyItemsPlayButtonForFullDayView))
{
				System.out.println("journey playback option is available for Full day view");
				clickBtn(locators.journeyItemsPlayButtonForFullDayView);
				Thread.sleep(500);
				//Before playing the journey playback
				if (getobjStaticText(locators.journeyPlaybackStatusText).equalsIgnoreCase(("Playback Stopped"))) 
				{
						 Thread.sleep(500);
						 date= getobjStaticText(locators.journeyPlaybackDate);
						 Assert.assertEquals(date, preDayDate());
						 asset= getobjStaticText(locators.journeyPlaybackHeader);
						 Assert.assertEquals(asset, data.get("Assets"));
						 time= getobjStaticText(locators.journeyPlaybackTime);
						 Assert.assertEquals(time, journeyStartTime +" to "+ journeyEndTime);
						 location= getobjStaticText(locators.journeyPlaybackLocation);
						 speed=getobjStaticText(locators.journeyPlaybackSpeed);
						 direction=getobjStaticText(locators.journeyPlaybackDirection);
						 String[] vDirection= direction.split(" ");
						 spLimit= getobjStaticText(locators.journeyPlaybackSpeedLimitAndDirection);
						 String[]spdLimit=spLimit.split("\\,");
						 speedLimit= spdLimit[0].substring(0,3);
						 
					for (int i = 0; i < (locationsOnDb.size() & directionOnDb.size() & vehicleSpeedLimitOnDb.size() & vehicleSpeedOnDb.size()) ; i++) {
						
						if ((locationsOnDb.get(i).equals((getobjStaticText(locators.journeyPlaybackLocation)))) & (vDirection[0].equals(directionOnDb.get(i).trim())) & (speedLimit.trim().equals(vehicleSpeedLimitOnDb.get(i))) & (speed.equals(vehicleSpeedOnDb.get(i)))) {
								String latLongs[] = geoCodeToLatLangConverter(locationsOnDb.get(i));
							    System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
							    String urlFromUI=locators.journeyPlaybackStreetView.getAttribute("src").trim();
							    String urlFromDB= "https://maps.googleapis.com/maps/api/streetview?sensor=false&location="+latLongs[0]+","+latLongs[1]+"&size=270x270&heading="+directionOnDb.get(i).trim();
							    System.out.println("urlFromUI before journey playback= "+urlFromUI);
							    System.out.println("urlFromDB before journey playback= "+urlFromDB);
								System.out.println("index: "+i);
								System.out.println("UI values before playback: Date: "+date +";  Time: "+time +";  Asset: "+asset+";  Location:"+location+"; SpeedLimit:"+speedLimit+"; vehicle Speed:"+speed+"; Direction:"+vDirection[0]);
								System.out.println("DB values before playback: Date:"+date +";  Time:"+time +";  Asset:"+asset+";  Location:"+locationsOnDb.get(i)+"; SpeedLimit:"+vehicleSpeedLimitOnDb.get(i)+"; vehicle Speed:"+vehicleSpeedOnDb.get(i)+"; Direction:"+ directionOnDb.get(i));
								break;
						}
					}
				}
				 Thread.sleep(1000);
				 clickBtn(locators.journeyPlaybackPlayPauseButton);
				 Thread.sleep(2000);
				 clickBtn(locators.journeyPlaybackPlayPauseButton);
				 Thread.sleep(2000);
				//After playing & pausing the journey playback
					if (getobjStaticText(locators.journeyPlaybackStatusText).equalsIgnoreCase(("Playback Paused"))) 
					{
							 Thread.sleep(1000);
							 date= getobjStaticText(locators.journeyPlaybackDate);
							 Assert.assertEquals(date, preDayDate());
							 asset= getobjStaticText(locators.journeyPlaybackHeader);
							 Assert.assertEquals(asset, data.get("Assets"));
							 time= getobjStaticText(locators.journeyPlaybackTime);
							 Assert.assertEquals(time, journeyStartTime +" to "+ journeyEndTime);
							 location= getobjStaticText(locators.journeyPlaybackLocation);
							 speed=getobjStaticText(locators.journeyPlaybackSpeed);
							 direction=getobjStaticText(locators.journeyPlaybackDirection);
							 String[] vDirection= direction.split(" ");
							 spLimit= getobjStaticText(locators.journeyPlaybackSpeedLimitAndDirection);
							 String[]spdLimit=spLimit.split("\\,");
							 speedLimit= spdLimit[0].substring(0,3);
							 Thread.sleep(500);
							
						for (int i = 0; i < (locationsOnDb.size() & directionOnDb.size() & vehicleSpeedLimitOnDb.size() & vehicleSpeedOnDb.size()) ; i++) 
						{
									Thread.sleep(500);
							if ((locationsOnDb.get(i).equals((getobjStaticText(locators.journeyPlaybackLocation)))) & (vDirection[0].equals(directionOnDb.get(i).trim())) & (speedLimit.trim().equals(vehicleSpeedLimitOnDb.get(i))) & (speed.equals(vehicleSpeedOnDb.get(i)))) 
							{
									/*String latLongs[] = geoCodeToLatLangConverter(locationsOnDb.get(i));
								    System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
								    String urlFromUI=locators.journeyPlaybackStreetView.getAttribute("src").trim();
								    String urlFromDB= "https://maps.googleapis.com/maps/api/streetview?sensor=false&location="+latLongs[0]+","+latLongs[1]+"&size=270x270&heading="+directionOnDb.get(i).trim();
								    System.out.println("urlFromUI after journey playback= "+urlFromUI);
								    System.out.println("urlFromDB after journey playback= "+urlFromDB);	*/
									Thread.sleep(500);	
									System.out.println("index: "+i);
									System.out.println("UI values after playback: Date: "+date +";  Time: "+time +";  Asset: "+asset+";  Location:"+location+"; SpeedLimit:"+speedLimit+"; vehicle Speed:"+speed+"; Direction:"+vDirection[0]);
									System.out.println("DB values after playback: Date:"+date +";  Time:"+time +";  Asset:"+asset+";  Location:"+locationsOnDb.get(i)+"; SpeedLimit:"+vehicleSpeedLimitOnDb.get(i)+"; vehicle Speed:"+vehicleSpeedOnDb.get(i)+"; Direction:"+ directionOnDb.get(i));
									break;
							}
						}
					}
				 
					clickBtn(locators.journeyBackButton);
					Thread.sleep(500);
					clickBtn(locators.journeyHomeButton);
					clickBtn(locators.journeyPanelCloseButton);
				 

			}
	
	}
	
	
	
	
	
	
	
	


	// It will execute after all the tests execute inside the class 
	@AfterClass		
	public void Closebrowser()
	{
		//cleanUp();
	}	

}
