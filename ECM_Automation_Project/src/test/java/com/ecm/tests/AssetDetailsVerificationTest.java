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



public class AssetDetailsVerificationTest extends ECMUtil {
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
		test.assignAuthor("Shivaprasad Nagaraju"); 
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
	@Test(priority=3)
	public void verifyListOfAssetNameAndAssociatedDriversDisplayingUnderAssetPanelTab() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		
			APP_LOGS.info("\n\n--------------------VerifylistofAssetnameandassociatedDriverisdispayingunderassetpaneltab--------------------\n\n");	
			String query =properties.getProperty("queryforFetchlistofAssetsAndDrivers");
			Statement stmt=SQLserverConnection();

			ArrayList<String> Asset_Name = new ArrayList<String>();
			ArrayList<String> Driver_Name = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery(query);	

			while(rs.next()){
				Asset_Name.add(rs.getString(1));
				if (rs.getString(2)==null) {
					Driver_Name.add("[Unknown driver]");
					System.out.println("driver is not available for this asset: " + rs.getString(1) );
					
				} else {
					Driver_Name.add(rs.getString(2));
				}
				
			}

			APP_LOGS.info( " value asset name : " + Asset_Name);
			APP_LOGS.info( " value  Driver  Name: " + Driver_Name);
			Thread.sleep(1000);
			
			List <WebElement> assetdata= locators.assetNamesList;
			List <WebElement> driverdata= locators.driverNamesList;
			
			
			int i=0;
			for(WebElement asset: assetdata)
			{
				String optiontext=getobjStaticText(asset).trim();

				if(optiontext.equals(Asset_Name.get(i).trim()))
				{
					APP_LOGS.info("Pass: DB & UI Assets are matching: " +optiontext +" = "+ Asset_Name.get(i));
					System.out.println("Assets are matching: " +optiontext +" = "+ Asset_Name.get(i));	
				}
				else 
				{
					System.out.println("Assets doesnt  match:  "+optiontext +" = "+ Asset_Name.get(i));
					APP_LOGS.info("Fail: DB & UI Assets are not matching: " +optiontext +" = "+ Asset_Name.get(i));
				}
				
				i++;
			}
			
			int j=0;
			for(WebElement driver: driverdata)
			{
				String optiontext=getobjStaticText(driver).trim();

				if(optiontext.equals(Driver_Name.get(j).trim()))
				{
					System.out.println("Drivers are matching: " +optiontext +" = "+ Driver_Name.get(j));
					APP_LOGS.info("Pass: DB & UI Drivers are matching: " +optiontext +" = "+ Driver_Name.get(j));
				}
				else 
				{
					System.out.println("Drivers doesnt match:  "+optiontext +" = "+ Driver_Name.get(j));
					APP_LOGS.info("Fail: DB & UI Drivers are not matching: " +optiontext +" = "+ Driver_Name.get(j));
				}
				
				j++;
			}
		
	}


	//Verify Asset Icon On Assets List And Asset Dialog
	@Test(priority=12, dataProvider="AssetIconVerificationTestData", dataProviderClass=DataProviders.class)
	public void verifyAssetIconOnAssetsListAndAssetDialog(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			APP_LOGS.info("\n\n--------------------verify Asset Icon On Aseets List And Asset Dialog--------------------\n\n");

			//Click on Asset available on asset panel	 
			boolean assettrue=assetElementAvailableOnAssetPanel( data.get("Assets"), locators.telematicsAssetList);
			Assert.assertTrue(assettrue, " Asset " + data.get("Assets") + " not exist is Asset panel" );
			APP_LOGS.info("PASS: Asset " + data.get("Assets") + " exist is Asset panel and clicked on succesfully"  );
			
			//verifying asset icon on assets list
			String assetListIcon= locators.telematicsAssetIconSelectedOnList.getAttribute("src");
			System.out.println(assetListIcon);
			String[]icon= assetListIcon.split("\\=|&");
			
			//verifying option button having option  maintain Asset detail value and click on  
			String assetdetailsoptiontext= assetOptionButtonHavingOption(data.get("Option btn"), locators.assetoptionmenupane);
			Assert.assertEquals(assetdetailsoptiontext, data.get("Option btn"), "Asset option pop up window do not  having option " + data.get("Option btn") );
			APP_LOGS.info("PASS: Asset option pop up window having option "+ data.get("Option btn"));
			Thread.sleep(1000);
			clickBtn(locators.assetIconPicker);
			List<WebElement> assetImages= locators.assetIcons;
			List<WebElement> assetImageColors= locators.assetIconColors;
			for (int i = 0; i < (assetImages.size() & assetImageColors.size()); i++) 
			{
				
				Thread.sleep(1000);
				clickBtn(assetImages.get(i));
				Thread.sleep(1000);
				clickBtn(assetImageColors.get(i));
				
			}
			//verifying asset icon on asset dialog
			String assetDialogIcon= locators.assetIconOnDialogWindow.getAttribute("src");
			System.out.println(assetDialogIcon);
			if (!assetListIcon.equals(assetDialogIcon)) {
				
				System.out.println("changes are not saved to db after clicking on cancel");
				
			}
			clickBtn(locators.assetdetailsdialogcancelbutton);
			Thread.sleep(1000);
			//verifying asset icon on asset dialog
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			Thread.sleep(1000);
			String assetDialogIconAfterCanel= locators.assetIconOnDialogWindow.getAttribute("src");
			System.out.println(assetDialogIcon);
			
			System.out.println("Asset icon: "+ icon[1] +"\n"+ "Icon color: "+icon[3]);
			Assert.assertEquals(assetListIcon, assetDialogIconAfterCanel, " Pass: Assets icon on the assets list and asset dialog are matching");
			clickBtn(locators.assetdetailsdialogcancelbutton);
		
	}
	
	
	@Test(priority=2, dataProvider="AssetIconPickerVerificationTestData", dataProviderClass=DataProviders.class)
	public void verifyAssetIconPickerFunctionality(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			int r,g,b;
			ExcelUtils.checkExecution("AssetSuite", "verifyAssetIconPickerFunctionality", data.get("RunMode"), excel);
			APP_LOGS.info("\n\n--------------------verify Asset Icon On Aseets List And Asset Dialog--------------------\n\n");

			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			Thread.sleep(1000);
			
			clickBtn(locators.assetIconPicker);
			List<WebElement> assetImages= locators.assetIcons;
			List<WebElement> assetImageColors= locators.assetIconColors;
			Thread.sleep(1000);
			Statement stmt=SQLserverConnection();
			String query;
			ResultSet rs;
			ArrayList<String> urlList = new ArrayList<String>();
			for (int i = 0; i < (assetImages.size() & assetImageColors.size()); i++) 
			{
				String assetListIcon= assetImages.get(i).getAttribute("src");
				 String vehicle=assetListIcon.substring(59);
				String[]icon= vehicle.split("\\.|/");
				String assetIconColor= assetImageColors.get(i).getAttribute("style");
				String[]iconColors= assetIconColor.split("\\:|;");
				String col= iconColors[1].substring(4);
				String cols = col.replaceAll("\\p{P}","");
				String[]colors= cols.split(" ");
				r=Integer.valueOf(colors[0].trim());
				g=Integer.valueOf(colors[1].trim());
				b=Integer.valueOf(colors[2].trim());
				clickBtn(assetImages.get(i));
				Thread.sleep(1000);
				clickBtn(assetImageColors.get(i));
				System.out.println("The icon selected is: "+ icon[1] +"and color selected is: "+rgbToHex(r, g, b));
				clickBtn(locators.assetDetailsDialogSaveButton);
				Thread.sleep(1000);
				String urlFormedFromUI= ("http://be-tms-web.causeway.local/icons/api/v1/assetmarker/svg?motif="+icon[1]+"&colour=%23"+rgbToHex(r, g, b).substring(1)).trim();
				System.out.println("Url from UI: "+urlFormedFromUI);
				
				
				query =properties.getProperty("queryForAssetIconAndColorVerification")+data.get("Assets data");
				rs = stmt.executeQuery(query);	
				String colorCode = null,colorCode1 = null;
				String[] assetIconName = null;
				while(rs.next())
				{
					 colorCode=rs.getString(2).substring(1);
					 colorCode1=rs.getString(3).substring(20);
					 assetIconName=colorCode1.split("\\.");
				}
				String urlFormedFromDb= ("http://be-tms-web.causeway.local/icons/api/v1/assetmarker/svg?motif="+assetIconName[0]+"&colour=%23"+colorCode).trim();
				System.out.println("Url from DB: "+urlFormedFromDb);
				Assert.assertEquals(urlFormedFromUI, urlFormedFromDb);
				Thread.sleep(1000);
				clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
				Thread.sleep(1000);
				clickBtn(locators.assetIconPicker);
				Thread.sleep(1000);
				
			}
			clickBtn(locators.assetdetailsdialogcancelbutton);
		
		
	}	
	
	
	//verifying View asset details window displaying correct labels and fields
	@Test(priority=5,dataProvider="AssetMaintainDetailsTestData", dataProviderClass=DataProviders.class)
	public void verifyMaintainAssetDetailsWindowDisplayingCorrectLabelsAndFields(Hashtable<String,String> data) throws InterruptedException, IOException
	{
		
			APP_LOGS.info("\n\n--------------------verifyMaintainAssetDetailsWindowDisplayingCorrectLabelsAndFields--------------------\n\n");
			ExcelUtils.checkExecution("AssetSuite", "verifyMaintainAssetDetailsWindowDisplayingCorrectLabelsAndFields", data.get("RunMode"), excel);
			
			
			String labellist[]=data.get("Label").split(",");

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			//verifying header of Asset detail dialog window
			Thread.sleep(5000);
			WebElement assetdialogMaintaindetails= locators.assetMaintaindetailsdialog;
			String assetheader=getobjStaticText(assetdialogMaintaindetails);
			if(assetheader.equals(labellist[0].trim()))
			{
				Assert.assertEquals(assetheader, "Asset", "Verifying asset detail dialog having heade Asset");
				APP_LOGS.info("PASS: Asset detail dialog having header " + labellist[0].trim());
			}
			else
			{
				APP_LOGS.info("FAIL: Asset detail dialog do not having header " + labellist[0].trim());
				Assert.assertEquals(assetheader, "Asset", "Asset detail dialog do not having header Asset");
			}
			
			
			
			//verifying upper part of Asset detail dialog window
			List <WebElement> labeltextelement=locators.assetDetailsDialogToplabels;

			int i=0;
			for (WebElement topLabel : labeltextelement) {
				if (getobjStaticText(topLabel).trim().equalsIgnoreCase(labellist[i])) 
				{
					APP_LOGS.info("PASS: " + labellist[i] + " Label is displaying in Asset detail dialog window");
				} 
				else
				{
					APP_LOGS.info("FAIL: " + labellist[i] + " Label is not displaying in Asset detail dialog window");
				}
				i++;
			}
			

			//verifying tab part of Asset detail dialog window
			List <WebElement>  assetdetailtablist=locators.assetMaintainDetailsDialogTab;
			String tabList[]=data.get("AssetDialogTabs").split(",");
			int x=0;
			for (WebElement tab : assetdetailtablist) {
				if (getobjStaticText(tab).trim().equalsIgnoreCase(tabList[x])) 
				{
					clickBtn(tab);
					APP_LOGS.info("PASS: " + tabList[x] + " Tab is displaying in Asset detail dialog window");
					
				} else 
				{
					
					APP_LOGS.info("Fail: " + tabList[x] + " Tab is not displaying in Asset detail dialog window");

				}
				x++;
			}

			
			//verifying labels under information tab of Asset detail dialog window
			clickBtn(locators.assetMaintainDetailsInformationTab);
			List <WebElement> tabLabellist=locators.assetMaintaindetailsdialogInformationTabLabels;
			String labelList[]=data.get("Information Tab details").split(",");
			
			int y=0;
			for (WebElement tabLabel : tabLabellist) {
				if (getobjStaticText(tabLabel).trim().equalsIgnoreCase(labelList[y].trim())) 
				{
					APP_LOGS.info("PASS:  " + labelList[y] + " label is displaying in Information tab");
					
				} else 
				{
					
					APP_LOGS.info("Fail: " + labelList[y] + " label is not displaying in Information tab");

				}
				y++;
			}
			
			
			
			/*//verifying labels under Admin tab of Asset detail dialog window
					clickBtn(locators.assetMaintainDetailsAdminTab);
					List <WebElement> adminTabLabelList=locators.assetMaintainDetailsAdminTabLabels;
					String adminTabLabels[]=data.get("Admin Tab details").split(",");
					
					int z=0;
					for (WebElement tabLabel : adminTabLabelList) {
						if (getobjStaticText(tabLabel).trim().equalsIgnoreCase(adminTabLabels[z].trim())) 
						{
							APP_LOGS.info("PASS:  " + adminTabLabels[z] + " label is displaying in Admin tab");
							
						} else 
						{
							
							APP_LOGS.info("Fail: " + adminTabLabels[z] + " label is not displaying in Admin tab");

						}
						z++;
					}*/
			
					
			clickBtn(locators.assetdetailsdialogcancelbutton);
		
	}
	
	

	//verifying View asset details window displaying Listbox Having Correct List Values
	@Test( priority=6, dataProvider="AssetMaintaindetailsFuelClassTestData", dataProviderClass=DataProviders.class)
	public void verifyMaintainAssetDetailsWindowDisplayingListboxHavingCorrectListValues(Hashtable<String,String> data) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			APP_LOGS.info("\n\n--------------------verify Maintain Asset Details Window Displaying Listbox Having Correct List Values--------------------\n\n");
			ExcelUtils.checkExecution("AssetSuite", "verifyMaintainAssetDetailsWindowDisplayingCorrectLabelsAndFields", data.get("RunMode"), excel);
			
			String classlistvalue[]=data.get("Class").split(",");
			String fueltypevalue[]=data.get("Fuel Type").split(",");
			String fuelecounit[]=data.get("Fuel economy unit").split(",");
			String odometunit[]=data.get("Odometer").split(",");

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			
			//verifying List box value list under asset detail window    
			Statement stmt=SQLserverConnection();
			ArrayList<String> classlist = new ArrayList<String>();
			List <WebElement> Controlfield=locators.assetdetailsdialogfield;
			List <WebElement> infoFields =locators.assetDetailsDialogBottomFields;
			APP_LOGS.info("Verifying list  under list box");
			for(WebElement listele: Controlfield)
			{
				String labelName=listele.findElement(By.tagName("label")).getText();
				APP_LOGS.info("Case  " + labelName);
				List <WebElement> controlfieldlist=	listele.findElements(By.tagName("option"));;
				String query;
				ResultSet rs;
				switch(labelName)
				{

				case "Class":
					//List <WebElement> controlfieldlist=Controlfield.get(p).findElements(By.tagName("option"));
					for(int i=0;i<classlistvalue.length   ;i++)
					{
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						if(optionval.equals(classlistvalue[i].trim()))
						{
							Assert.assertEquals(optionval, classlistvalue[i].trim(), "Class list box having correct data");
							APP_LOGS.info("PASS: Class list having Contain data " + classlistvalue[i].trim());
						}
						else
						{
							APP_LOGS.info("FAIL: Class list do not having Contain data " + classlistvalue[i].trim());
							Assert.assertEquals(optionval, classlistvalue[i].trim(), " Class list box do not Contain data under list");
						}
					}	
					classlist.clear();		break;



				case "Group":	
					query =properties.getProperty("queryforfeatchGrouplist");
					rs = stmt.executeQuery(query);	
					while(rs.next()){
						classlist.add(rs.getString(1));
					}
					APP_LOGS.info( " Group list value under data base : " + classlist);
					APP_LOGS.info( " Group list value under data base : " + controlfieldlist.size() + "  " +  classlist.size());
					for(int i=0;i<controlfieldlist.size() & i< classlist.size()  ;i++)
					{

						//String optionval=controlfieldlist.get(i).getText().trim();
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();

						if(optionval.equals(classlist.get(i).trim()))
						{
							Assert.assertEquals(optionval, classlist.get(i).trim(), "Group list box having correct data");
							APP_LOGS.info("PASS: Group list having Contain data " + classlist.get(i).trim());
						}
						else
						{
							APP_LOGS.info("FAIL: Group list do not having Contain data " + classlist.get(i).trim());
							Assert.assertEquals(optionval, classlist.get(i).trim(), " Group list box do not Contain data under list");
						}
					}	
					classlist.clear();	

					break;
				case "Driver":	
					query =properties.getProperty("queryforfeatchDriverlist");
					rs = stmt.executeQuery(query);	
					while(rs.next()){
						classlist.add(rs.getString(1));
					}
					APP_LOGS.info( " Driver list value under data base : " + classlist);

					for(int i=0;i<(controlfieldlist.size() | classlist.size())  ;i++)
					{
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();

						if(optionval.equals(classlist.get(i).trim()))
						{
							Assert.assertEquals(optionval, classlist.get(i).trim(), " Driver list box Contain data");
							APP_LOGS.info("PASS: Driver list Contain data " + classlist.get(i).trim());
						}
						else
						{
							APP_LOGS.info("FAIL: Driver list do not  Contain correct data " + classlist.get(i).trim());
							Assert.assertEquals(optionval, classlist.get(i).trim(), "  Driver box do not Contain data under list");
						}
					}	
					classlist.clear();	
					break;

				default :
					break;
				}
			}
			
			
			Controlfield=locators.assetdetailsdialogbottomfield;
			APP_LOGS.info("Verifying list  under list box");

			for(WebElement listele: Controlfield)
			{
				String labelName=listele.findElement(By.tagName("label")).getText();
				APP_LOGS.info("Label Displaying........................."  + labelName);

				List <WebElement> controlfieldlist=	listele.findElements(By.tagName("option"));;
				String query;
				ResultSet rs;
				switch(labelName)
				{


			case "Make & model":	
					query =properties.getProperty("queryforfeatchMakeModellist");
					rs = stmt.executeQuery(query);	

					while(rs.next()){
						classlist.add(rs.getString(1));

					}
					APP_LOGS.info( " Make & Model list value under data base : " + classlist);
					boolean flg=false;	
					for(int i=0;i<(controlfieldlist.size() & classlist.size()) ;i++)
					{
						//String optionval=controlfieldlist.get(i).getText().trim();
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						int j;

						for( j=0;j<(controlfieldlist.size() & classlist.size());j++)
						{
							if(optionval.equalsIgnoreCase(classlist.get(j).trim()))
							{
								flg=true;

								break;
							}
						}
						//if(optionval.contains(classlist.get(i).trim()))

							if(flg)
							{

								Assert.assertEquals(optionval, classlist.get(j).trim(), " Make & Model list box Contain data");
								APP_LOGS.info("PASS: Make & Model list Contain data " + classlist.get(j).trim());
							}
							else if(j<classlist.size()-1)
							{
								APP_LOGS.info("FAIL: Make & Modelver list do not Contain data " + classlist.get(j).trim());
								Assert.assertEquals(optionval, classlist.get(j).trim(), " Make & Model list box do not Contain data under list");

							}else
							{
							}
					}	
					classlist.clear();		
					break;
					
				case "Fuel type":

					for(int i=0;i<fueltypevalue.length   ;i++)
					{
						//String optionval=controlfieldlist.get(i).getText().trim();
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						if(optionval.equals(fueltypevalue[i].trim()))
						{
							Assert.assertEquals(optionval, fueltypevalue[i].trim(), "Fuel Type  list box having correct data");
							APP_LOGS.info("PASS: Fuel Type list having Contain data " + fueltypevalue[i].trim());
						}
						else
						{
							APP_LOGS.info("FAIL: Fuel Type  list do not having Contain data " + fueltypevalue[i].trim());
							Assert.assertEquals(optionval, fueltypevalue[i].trim(), " Fuel Type  list box do not Contain data under list");
						}
					}	
					classlist.clear();
					break;

				case "Speeding profile":	

					query =properties.getProperty("queryforfeatchSpeedingprofilelist");
					rs = stmt.executeQuery(query);	

					while(rs.next()){
						classlist.add(rs.getString(1));
					}
					APP_LOGS.info( " Speeding profile  list value under data base : " + classlist);
					
					System.out.println("UI: "+controlfieldlist.size() +"  db: "+classlist.size());

					for(int i=0;i<(controlfieldlist.size() & classlist.size())  ;i++)
					{
						
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						if(optionval.equals(classlist.get(i).trim()))
						{
							Assert.assertEquals(optionval, classlist.get(i).trim(), "Speeding profilel list box Contain data");
							APP_LOGS.info("PASS: Speeding profilelistContain data " + classlist.get(i).trim());
							System.out.println("pass: Speeding profile list Contain data " + classlist.get(i).trim() +"position: "+ i + "  datas: "+ optionval);
						}
						else
						{
							System.out.println("FAIL: Speeding profile list do not Contain data " + classlist.get(i).trim() +"position: "+ i);
							APP_LOGS.info("FAIL: Speeding profile list do not Contain data " + classlist.get(i).trim());
							//Assert.assertEquals(optionval, classlist.get(i).trim(), " Speeding profile list box do not Contain data under list");
						}
					}
					classlist.clear();	
					break;
				default :
					break;
				}
			}
			
			
//verifying fuel units & odometer units	
			for (WebElement webElement : infoFields) {
				
				String labelName=webElement.findElement(By.tagName("label")).getText();
				APP_LOGS.info("Label Displaying........................."  + labelName);

				List <WebElement> controlfieldlist=	webElement.findElements(By.tagName("option"));;
				
				switch(labelName)
				{

				case "Fuel economy":
					String fuelUnits="MPG,L/100km";
					String fuelUnitsList[]=fuelUnits.split(",");	
					
					for(int i=0;i<(controlfieldlist.size() & fuelUnitsList.length)  ;i++)
					{
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						if(optionval.equals(fuelUnitsList[i].trim()))
						{
							Assert.assertEquals(optionval, fuelUnitsList[i].trim(), "fuel unit matches");
							APP_LOGS.info("PASS: fuel unit matches " + fuelUnitsList[i].trim());
						}
						else
						{
							APP_LOGS.info("PASS: fuel unit doesnt matche " + fuelUnitsList[i].trim());
						}
					}
					break;
					
					
					
				case "Odometer":
					String odoUnits="mi,km";
					String odoUnitsList[]=odoUnits.split(",");	
					
					for(int i=0;i<(controlfieldlist.size() & odoUnitsList.length)  ;i++)
					{
						String optionval=getobjStaticText(controlfieldlist.get(i)).trim();
						if(optionval.equals(odoUnitsList[i].trim()))
						{
							Assert.assertEquals(optionval, odoUnitsList[i].trim(), "Odometer unit matches");
							APP_LOGS.info("PASS: Odometer unit matches " + odoUnitsList[i].trim());
						}
						else
						{
							APP_LOGS.info("PASS: Odometer unit doesnt matche " + odoUnitsList[i].trim());
						}
					}
					break;
					
				default :
					break;
				}
				
			}
			clickBtn(locators.assetdetailsdialogcancelbutton);
	}
		


	//verifying View asset details window cancel button is working 
	@Test(priority=7, dataProvider="AssetMaintainDetailsCancelButtonTestData", dataProviderClass=DataProviders.class)
	public void verifyMaintainAssetDetailsWindowCancelButton(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			ArrayList<String> inputdata=new ArrayList<String>();
			inputdata.add(data.get("name"));
			inputdata.add(data.get("Alternate Name"));
			inputdata.add(data.get("Class"));
			inputdata.add(data.get("Group"));
			inputdata.add(data.get("Driver"));
			inputdata.add(data.get("Make & model"));
			inputdata.add(data.get("Fuel type"));
			inputdata.add(data.get("CO2 emissions"));
			inputdata.add(data.get("Speeding profile"));
			inputdata.add(data.get("fuel economy"));
			inputdata.add(data.get("Odometer"));
			inputdata.add(data.get("Running Cost"));
			inputdata.add(data.get("Idle cost"));
			inputdata.add(data.get("Immobilisation"));		
			inputdata.add("010657000514039");
			inputdata.add(data.get("Installation date"));
			inputdata.add(data.get("Last communication / Message Type"));
			inputdata.add(data.get("Notes"));

			ArrayList<String> inputdataforupperpartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforinfopartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforNotepartwindow=new ArrayList<String>(); 		 
			for(int i=0;i<5;i++)
			{
				inputdataforupperpartwindow.add(inputdata.get(i));
			}
			for(int i=5;i<17;i++)
			{
				inputdataforinfopartwindow.add(inputdata.get(i));
			}
			inputdataforNotepartwindow.add(inputdata.get(17));
			APP_LOGS.info("\n\n--------------------Verifyingmaintainassetdetailswindowcancelisworking--------------------\n\n");

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			
			
			//Fetching   value from field under asset detail window 
			ArrayList<String> assetvalueberforecancel = new ArrayList<String>();
			assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogField));


			List <WebElement>  assetdetailtablist=locators.assetMaintainDetailsDialogTab;

			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				APP_LOGS.info("message    " + tabtext);
				if(tabtext.equals("Information"))
				{
					assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogBottomField));               	   }
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					Thread.sleep(3000);
					assetvalueberforecancel.add(getobjStaticText(locators.notesTabTextArea));
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}

			APP_LOGS.info(" fields  value under asset detail window  " + assetvalueberforecancel);	

			
			
			//entering value under fields available in asset detail window	   		    
			setAssetDetailwindowData(locators.assetDetailsDialogField, inputdataforupperpartwindow);

			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);

				if(tabtext.equals("Information"))
				{
					setAssetDetailwindowData(locators.assetDetailsDialogBottomField, inputdataforinfopartwindow);
				}
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					Thread.sleep(3000);
					enterTextToInputField(locators.notesTabTextArea, "notes");
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}

			clickBtn(locators.assetdetailsdialogcancelbutton);
			
			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			
			//Fetching   value from field under asset detail window after clicking on cancel
			ArrayList<String> assetvalueaftercancel = new ArrayList<String>();
			assetvalueaftercancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogField));


			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				APP_LOGS.info("message    " + tabtext);
				if(tabtext.equals("Information"))
				{
					assetvalueaftercancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogBottomField));
				}
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					Thread.sleep(3000);
					assetvalueaftercancel.add(getobjStaticText(locators.notesTabTextArea));
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}

			APP_LOGS.info(" fields  value under asset detail window  " + assetvalueaftercancel);	

			// verifying before and after cancel button asset detail window fields data are equal 
			APP_LOGS.info(" fields  value under asset detail window after cancle button  " + assetvalueaftercancel);
			if(assetvalueaftercancel.equals(assetvalueberforecancel))
			{
				Assert.assertEquals(assetvalueaftercancel, assetvalueberforecancel);
				APP_LOGS.info("PASS: Cancel button under View asset details window is working fine");
			}
			else
			{
				APP_LOGS.info("FAIL: Cancel button under View asset details window is not  working fine");
				Assert.assertEquals(assetvalueaftercancel, assetvalueberforecancel, "Cancel button under View asset details window is not  working fine");
			}
			clickBtn(locators.assetdetailsdialogcancelbutton);
	}

	
	
	//verifying View asset details window Save button is working
	@Test(priority=8, dataProvider="AssetMaintainDetailsSaveButtonTestData", dataProviderClass=DataProviders.class)
	public void verifyMaintainAssetDetailsWindowSaveButton(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			ArrayList<String> inputdata=new ArrayList<String>();
			inputdata.add(data.get("name"));
			inputdata.add(data.get("Alternate Name"));
			inputdata.add(data.get("Class"));
			inputdata.add(data.get("Group"));
			inputdata.add(data.get("Driver"));
			inputdata.add(data.get("Make & model"));
			inputdata.add(data.get("Fuel type"));
			inputdata.add(data.get("CO2 emissions"));
			inputdata.add(data.get("Speeding profile"));
			inputdata.add(data.get("fuel economy"));
			inputdata.add(data.get("Odometer"));
			inputdata.add(data.get("Running Cost"));
			inputdata.add(data.get("Idle cost"));
			inputdata.add(data.get("Immobilisation"));
			inputdata.add("010657000514039");
			inputdata.add(data.get("Installation date"));
			inputdata.add(data.get("Last communication / Message Type"));
			inputdata.add(data.get("Notes"));

			ArrayList<String> inputdataforupperpartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforinfopartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforNotepartwindow=new ArrayList<String>(); 		 
			for(int i=0;i<5;i++)
			{
				inputdataforupperpartwindow.add(inputdata.get(i));
			}
			for(int i=5;i<17;i++)
			{
				inputdataforinfopartwindow.add(inputdata.get(i));
			}
			inputdataforNotepartwindow.add(inputdata.get(17));
			APP_LOGS.info("\n\n--------------------verify Maintain Asset Details Window Save Button--------------------\n\n");

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
					
					//Fetching   value from field under asset detail window 
					ArrayList<String> assetvalueberforecancel = new ArrayList<String>();
					assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogField));


					List <WebElement>  assetdetailtablist=locators.assetMaintainDetailsDialogTab;

					for(WebElement assetdetailwindowtabele:assetdetailtablist)
					{
						String tabtext=getobjStaticText(assetdetailwindowtabele);
						APP_LOGS.info("message    " + tabtext);
						if(tabtext.equals("Information"))
						{
							assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogBottomField));               	   }
						if(tabtext.equals("Notes"))
						{
							clickBtn(assetdetailwindowtabele);
							assetvalueberforecancel.add(getobjStaticText(locators.notesTabTextArea));
						}
						if(tabtext.equals("Reminders"))
						{
							clickBtn(assetdetailwindowtabele);
						}
					}
					for(WebElement assetdetailwindowtabele:assetdetailtablist)
					{
						String tabtext=getobjStaticText(assetdetailwindowtabele);
						if(tabtext.equals("Information"))
						{
							clickBtn(assetdetailwindowtabele);
							break;
						}
					}

					APP_LOGS.info(" fields  value under asset detail window  " + assetvalueberforecancel);	
					Thread.sleep(3000);
					
					
					//entering value under fields available in asset detail window	   		    
					setAssetDetailwindowData(locators.assetDetailsDialogField, inputdataforupperpartwindow);

					for(WebElement assetdetailwindowtabele:assetdetailtablist)
					{
						String tabtext=getobjStaticText(assetdetailwindowtabele);

						if(tabtext.equals("Information"))
						{
							setAssetDetailwindowData(locators.assetDetailsDialogBottomField, inputdataforinfopartwindow);
							Thread.sleep(3000);
						}
						if(tabtext.equals("Notes"))
						{
							clickBtn(assetdetailwindowtabele);
							Thread.sleep(3000);
							enterTextToInputField(locators.notesTabTextArea, "notes");
						}
						if(tabtext.equals("Reminders"))
						{
							clickBtn(assetdetailwindowtabele);
						}
					}
					for(WebElement assetdetailwindowtabele:assetdetailtablist)
					{
						String tabtext=getobjStaticText(assetdetailwindowtabele);
						if(tabtext.equals("Information"))
						{
							clickBtn(assetdetailwindowtabele);
							break;
						}
					}

			//Clicking on Save button and again opening asset detail window and verifying data should  save   	    	    

			clickBtn(locators.assetDetailsDialogSaveButton); 
			Thread.sleep(3000);

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("name"), data.get("Option btn"));

			//Fetching   value from field under asset detail window 

			ArrayList<String> assetvalueaftersave = new ArrayList<String>();
			assetvalueaftersave.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogField));
			List <WebElement>  assetetailtablist=locators.assetMaintainDetailsDialogTab;

			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				APP_LOGS.info("message    " + tabtext);
				if(tabtext.equals("Information"))
				{
					assetvalueaftersave.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogBottomField));
				}
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					assetvalueaftersave.add(getobjStaticText(locators.notesTabTextArea));
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}

			// verifying before and after Save button asset detail window fields data are equal 
			Thread.sleep(3000);
			APP_LOGS.info(" fields  value under asset detail window after Save button.." + inputdata);
			System.out.println(inputdata);
			System.out.println(assetvalueaftersave);
			APP_LOGS.info(" fields  value under asset detail window after Save button  " + assetvalueaftersave);
			clickBtn(locators.assetdetailsdialogcancelbutton);
		
	}

	
	
	//verifying View asset details window Save button is working for mandatory value
	@Test(priority=9, dataProvider="AssetDetailsMandatoryFieldTestData", dataProviderClass=DataProviders.class)
	public void verifyMaintainAssetDetailsSaveIsWorkingForMandatoryValues(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			ArrayList<String> inputdata=new ArrayList<String>();
			inputdata.add(data.get("name"));
			inputdata.add(data.get("Alternate Name"));
			inputdata.add(data.get("Class"));
			inputdata.add(data.get("Group"));
			inputdata.add(data.get("Driver"));
			inputdata.add(data.get("Make & model"));
			inputdata.add(data.get("Fuel type"));
			inputdata.add(data.get("CO2 emissions"));
			inputdata.add(data.get("Speeding profile"));
			inputdata.add(data.get("fuel economy"));
			inputdata.add(data.get("Odometer"));
			inputdata.add(data.get("Running Cost"));
			inputdata.add(data.get("Idle cost"));
			inputdata.add(data.get("Immobilisation"));
			inputdata.add(data.get("Notes"));
			inputdata.add(data.get("Error Message"));
			
			APP_LOGS.info("\n\n--------------------verify Maintain Asset Details Save Is Working For Mandatory Values--------------------\n\n");
			ArrayList<String> inputdataforupperpartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforinfopartwindow=new ArrayList<String>(); 
			ArrayList<String> inputdataforNotepartwindow=new ArrayList<String>(); 		 
			for(int i=0;i<5;i++)
			{
				inputdataforupperpartwindow.add(inputdata.get(i));
			}
			for(int i=5;i<14;i++)
			{
				inputdataforinfopartwindow.add(inputdata.get(i));
			}

			APP_LOGS.info("\n\n--------------------Verify maintain asset details window cancel is working--------------------\n\n");
			System.out.println(inputdata);

			//Click on Asset available on asset panel	 
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			
			ArrayList<String> assetvalueberforecancel = new ArrayList<String>();
			assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogField));

			List <WebElement>  assetdetailtablist=locators.assetMaintainDetailsDialogTab;

			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				APP_LOGS.info("message    " + tabtext);
				if(tabtext.equals("Information"))
				{
					assetvalueberforecancel.addAll( methodforFetchingAssetdetailwindowdata(locators.assetDetailsDialogBottomField));               	   }
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					assetvalueberforecancel.add(getobjStaticText(locators.notesTabTextArea));
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}
			APP_LOGS.info(" fields  value under asset detail window  " + assetvalueberforecancel);	
			Thread.sleep(3000);
			
			//entering value under fields available in asset detail window	
			setAssetDetailwindowData(locators.assetDetailsDialogField, inputdataforupperpartwindow);

			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);

				if(tabtext.equals("Information"))
				{
					setAssetDetailwindowData(locators.assetDetailsDialogBottomField, inputdataforinfopartwindow);
					Thread.sleep(3000);
				}
				if(tabtext.equals("Notes"))
				{
					clickBtn(assetdetailwindowtabele);
					enterTextToInputField(locators.notesTabTextArea,inputdata.get(14));
				}
				if(tabtext.equals("Reminders"))
				{
					clickBtn(assetdetailwindowtabele);
				}
			}
			
			
			for(WebElement assetdetailwindowtabele:assetdetailtablist)
			{
				String tabtext=getobjStaticText(assetdetailwindowtabele);
				if(tabtext.equals("Information"))
				{
					clickBtn(assetdetailwindowtabele);
					break;
				}
			}

			//Clicking on Save button and again opening asset detail window and verifying data should  save   	    	    

			clickBtn(locators.assetDetailsDialogSaveButton); 
			Thread.sleep(3000);

			WebElement errmessageele=  	locators.assetdetailsdialogerrormessage;
			String errmsg= getobjStaticText(errmessageele).trim() ;  	
			System.out.println("error message displayed is-----> " + errmsg);
			APP_LOGS.info(errmsg + " Error message displaying");

			if(errmsg.equals(inputdata.get(15)))
			{

				Assert.assertEquals(errmsg, inputdata.get(15));
				APP_LOGS.info("PASS: Error message displaying correctly");
				clickBtn(locators.assetdetailsdialogcancelbutton);

			}else
			{
				APP_LOGS.info("FAIL: Error message not displaying correctly");
				clickBtn(locators.assetdetailsdialogcancelbutton);
				Assert.assertEquals(errmsg, inputdata.get(15), "Error message not displaying correctly");
			}
	}
	
	
	
	// script for verifying option button is displaying 5 Sub Options
	@Test(priority=4, dataProvider="AssetOptionButtonsSubOptions", dataProviderClass=DataProviders.class)
	public void verifyAssetOptionButtonsSubOptions(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			String optionvalues[]=data.get("Option").split(",");
			APP_LOGS.info("\n\n--------------------verify Asset Option Buttons SubOptions--------------------\n\n");

			//Click on Asset available on asset panel	 
			boolean assettrue=assetElementAvailableOnAssetPanel( data.get("Assets"), locators.telematicsAssetList);
			Assert.assertTrue(assettrue, " Asset " + data.get("Assets") + " not exist is Asset panel" );
			APP_LOGS.info("PASS: Asset " + data.get("Assets") + " exist is Asset panel and clicked on succesfully"  );
			/////verifying option button having option  maintain Asset detail value and click on  
			WebElement optionmenupane=locators.telematicsassetitemselected;

			List <WebElement> pane= locators.optionButtonSubOptions;
			String assetdetailsoptiontext=null;

			for(int i=0;i<pane.size();i++)
			{
				assetdetailsoptiontext=getobjStaticText(pane.get(i));
				if(assetdetailsoptiontext.equals(optionvalues[i].trim()))
				{

					Assert.assertEquals(assetdetailsoptiontext, optionvalues[i].trim());
					APP_LOGS.info("PASS: Asset option pop up window having option " + optionvalues[i].trim());

					if(i==pane.size()-1)
					{
						clickBtn(pane.get(i)); 
						Thread.sleep(3000);
					}

				}else
				{
					APP_LOGS.info("FAIL: Asset option pop up window do not  having option " + optionvalues[i].trim() );
					Assert.assertEquals(assetdetailsoptiontext, optionvalues[i].trim());
				}

			}
	}



	//verifying Admin Tab Data
	@Test(priority=10, dataProvider="AdminTabData", dataProviderClass=DataProviders.class)
	public void verifyAdminTabValues(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			clickBtn(locators.assetMaintainDetailsAdminTab);
			List<WebElement> adminLabelList=locators.assetMaintainDetailsAdminTabLabels;
			
			for(WebElement assetDetailAdminTabEle:adminLabelList)
			{
				String labelText=getobjStaticText(assetDetailAdminTabEle);

				if(labelText.equals("FCal"))
				{
					locators.adminTabFcal.clear();
					enterTextToInputField(locators.adminTabFcal, (data.get("FCal")));
				}
				if(labelText.equals("Sends Idle Events"))
				{
					selectCheckbox(locators.adminTabIdleEvent);
				}
				if(labelText.equals("Can Type"))
				{
					selectDropDownByText(locators.adminTabCanType, data.get("Can Type"));
				}
				if(labelText.equals("Last CAN date"))
				{
					System.out.println("Last CAN date is: Never ");
				}
				if(labelText.equals("Notes"))
				{
					enterTextToInputField(locators.adminTabNotes,data.get("notes"));
				}
			}
			
			Thread.sleep(2000);
			clickBtn(locators.assetDetailsDialogSaveButton);
			Thread.sleep(2000);
			clickBtn(locators.assetdetailsdialogcancelbutton);
			//verifying header of admin tab values with db Data
			Thread.sleep(2000);
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			clickBtn(locators.assetMaintainDetailsAdminTab);
			
			ArrayList<String> adminTabValueAfterSave = new ArrayList<String>();
			adminTabValueAfterSave.addAll( methodForFetchingAssetdetailAdminTabData(locators.assetMaintainDetailsAdminFields));
			System.out.println(adminTabValueAfterSave);
			
			String query;
			ResultSet rs;
			Statement stmt=SQLserverConnection();
			ArrayList<String> adminValuesList = new ArrayList<String>();
			query =(properties.getProperty("queryForFetchingAdminTabDetails"))+data.get("IMEI");
			rs = stmt.executeQuery(query);	

			while(rs.next())
			{
				adminValuesList.add(rs.getString(1));
				
				if (rs.getString(2).equalsIgnoreCase("1")) 
				{
					adminValuesList.add("on");
				}
				else 
				{
					adminValuesList.add("off");
				}
				
				adminValuesList.add(rs.getString(3));
				
				if (rs.getString(4) != null) 
				{
					
					adminValuesList.add("Never");
				}
				else {
					adminValuesList.add("Never");
				}
				
				adminValuesList.add(rs.getString(5));
			}
			System.out.println(adminValuesList);
			
			for(int i=0;i<(adminValuesList.size() & adminTabValueAfterSave.size())  ;i++)
			{
				
				String optionval=adminValuesList.get(i).trim();
				if(optionval.equals(adminTabValueAfterSave.get(i).trim()))
				{
					Assert.assertEquals(optionval, adminTabValueAfterSave.get(i).trim());
					System.out.println("pass: " + adminTabValueAfterSave.get(i).trim() +" = "+ optionval);
				}
				else
				{
					Float fl= Float.valueOf(adminTabValueAfterSave.get(i));
					String fcal = Float.toString(fl);
					if (optionval.equals(fcal)) 
					{
						System.out.println("Pass : Fcal values are matching-->" + fcal +" = "+ optionval);
					}
					else 
					{
						System.out.println("Fail : Fcal values are not matching--> " + fcal +" = "+ optionval);
					}
				}
			}
			clickBtn(locators.assetdetailsdialogcancelbutton);
			Thread.sleep(2000);
	}

	//verifying Virtual Accounts Tab Data
	@Test(priority=11, dataProvider="VirtualAccountsTabData", dataProviderClass=DataProviders.class)
	public void verifyVirtualAccountsTabValues(Hashtable<String,String> data ) throws InterruptedException, IOException, SQLException, ClassNotFoundException
	{
		
			clickAssetOnAssetList(data.get("Assets"), data.get("Option btn"));
			clickBtn(locators.assetMaintainDetailsVirtualAccountsTab);
			
			List<WebElement> virtualAccList=locators.assetMaintainDetailsVirtualAccList;
			List<WebElement> virtualAccStatusList=locators.assetMaintainDetailsVirtualAccCheckboxList;
			for (WebElement webElement : virtualAccStatusList) {
				System.out.println(webElement.getAttribute("value"));
				
			}
			
			
			String query;
			ResultSet rs;
			Statement stmt=SQLserverConnection();
			ArrayList<String> virtualAccountsList = new ArrayList<String>();
			ArrayList<String> checkboxList = new ArrayList<String>();
			query =properties.getProperty("queryForFetchingirtualTabDetails1")+data.get("Assets data")+properties.getProperty("queryForFetchingirtualTabDetails2");
			rs = stmt.executeQuery(query);	

			while(rs.next())
				
			{
				if (rs.getString(1).equals("1200055")) {
					virtualAccountsList.add("alltradesnorth");
				}
				if (rs.getString(1).equals("1200056")) {
					virtualAccountsList.add("alltradessouth");
				}
				if (rs.getString(1).equals("1300076")) {
					virtualAccountsList.add("alltradesvirt1");
				}
				if (rs.getString(1).equals("1300077")) {
					virtualAccountsList.add("alltradesvirt2");
				}
				if (rs.getString(1).equals("1300078")) {
					virtualAccountsList.add("alltradesvirt3");
				}
				
				if (rs.getString(3).equals("0")) {
					checkboxList.add("on");
					
				} else {
					
					checkboxList.add("off");

				}
			}
			System.out.println(virtualAccountsList);
			System.out.println(checkboxList);
			
			for(int i=0;i<(virtualAccountsList.size() & virtualAccList.size())  ;i++)
			{
				
				String optionval=getobjStaticText(virtualAccList.get(i)).trim();
				if(optionval.equals(virtualAccountsList.get(i).trim()))
				{
					Assert.assertEquals(optionval, virtualAccountsList.get(i).trim());
					System.out.println("pass: " + virtualAccountsList.get(i).trim() +" = "+ optionval);
				}
				else
				{
						System.out.println("Fail :--> " + virtualAccountsList.get(i).trim() +" = "+ optionval);
				}
			}
			
			for(int i=0;i<(checkboxList.size() & virtualAccStatusList.size())  ;i++)
			{
				
				String optionval=(virtualAccStatusList.get(i).getAttribute("value")).trim();
				if(optionval.equals(checkboxList.get(i).trim()))
				{
					Assert.assertEquals(optionval, checkboxList.get(i).trim());
					System.out.println("pass: " + checkboxList.get(i).trim() +" = "+ optionval);
				}
				else
				{
						System.out.println("Fail :--> " + checkboxList.get(i).trim() +" = "+ optionval);
				}
			}
			
			
			clickBtn(locators.assetdetailsdialogcancelbutton);
			Thread.sleep(2000);
	}
	
	@AfterClass		
	public void Closebrowser()
	{
		cleanUp();
	}	  

}

