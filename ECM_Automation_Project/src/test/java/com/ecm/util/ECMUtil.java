package com.ecm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;

public class ECMUtil extends BaseUtil {

	public ECMUtil() {
		// TODO Auto-generated constructor stub
	}

	public static WebDriverWait wait = null;

	Locators locators = PageFactory.initElements(getDriver(), Locators.class);
	WebclientLocators webLocators = PageFactory.initElements(getDriver(), WebclientLocators.class);

	public void login_10duke() throws InterruptedException {
		wait = new WebDriverWait(getDriver(), 20);
		getDriver().get(ApplicationConstants.TELEMATICS_LOGIN_URL.getValue());
		Thread.sleep(10000);
		String dukeurl = getDriver().getCurrentUrl();
		waitForPageLoaded();
		
		System.out.println(dukeurl);

		if (dukeurl.contains("causeway-idp-dev.10duke.com")) {
			APP_LOGS.info("Log in via 10 duke");
			enterTextToInputField(Locators.userName,ApplicationConstants.TELEMATICS_USERNAME.getValue());
			enterTextToInputField(Locators.password,ApplicationConstants.TELEMATICS_PASSWORD.getValue());
			clickBtn(Locators.signInButton);
			waituntilnew(locators.telematicsassetlist, 50);
			APP_LOGS.info("Login to Telematics application login Using 10duke is succesful");
		} else {
			APP_LOGS.info("Log in without 10 duke");

		}
	}
	
	//Login function for Telematics Webclient
	public void login_TelematicsWebclient() throws InterruptedException {
		wait = new WebDriverWait(getDriver(), 20);
		getDriver().get(ApplicationConstants.TELEMATICS_WEBCLIENT_LOGIN_URL.getValue());
		Thread.sleep(10000);
		String webclienturl = getDriver().getCurrentUrl();
		waitForPageLoaded();
		
		System.out.println(webclienturl);

		if (webclienturl.contains("telematics.staging.causeway.com")) {
			APP_LOGS.info("Log in page is correct for webclient");
			enterTextToInputField(webLocators.webclientuserName,ApplicationConstants.TELEMATICS_WEBCLIENT_USERNAME.getValue());
			enterTextToInputField(webLocators.webclientPassword,ApplicationConstants.TELEMATICS_WEBCLIENT_PASSWORD.getValue());
			clickBtn(webLocators.normalLoginInButton);
			waituntilnew(webLocators.telematicsassetlist, 50);
			APP_LOGS.info("Login to Telematics Webclinet application is succesful");
		} else {
			APP_LOGS.info("Log in failed");

		}
	}
	
	
	//Login function for Telematics Webclient
		public void login_10DukeTelematicsWebclient() throws InterruptedException {
			wait = new WebDriverWait(getDriver(), 20);
			getDriver().get(ApplicationConstants.TELEMATICS_WEBCLIENT_LOGIN_URL.getValue());
			Thread.sleep(10000);
			clickBtn(webLocators.login10DukeButton);
			Thread.sleep(500);
			String webclienturl = getDriver().getCurrentUrl();
			waitForPageLoaded();
			
			System.out.println(webclienturl);

			if (webclienturl.contains("causeway-idp-dev.10duke.com")) {
				APP_LOGS.info("Log in via 10 duke");
				enterTextToInputField(webLocators.userName,ApplicationConstants.TELEMATICS_USERNAME.getValue());
				enterTextToInputField(webLocators.password,ApplicationConstants.TELEMATICS_PASSWORD.getValue());
				clickBtn(webLocators.signInButton);
				waituntilnew(webLocators.telematicsassetlist, 60);
				APP_LOGS.info("Login to Telematics Webclinet application Using 10duke is succesful");
			} else {
				APP_LOGS.info("Log in without 10 duke");

			}
		}
	
	public void login_ReportsWebClient() throws InterruptedException {
		wait = new WebDriverWait(getDriver(), 20);
		getDriver().get(ApplicationConstants.TELEMATICS_REPORT_URL.getValue());
		Thread.sleep(5000);
		String reportUrl = getDriver().getCurrentUrl();
		waitForPageLoaded();
		
		System.out.println(reportUrl);

		if (reportUrl.contains("reports.causewaytms.com")) {
			APP_LOGS.info("Log in to Reports");
			enterTextToInputField(Locators.reportsUserName,ApplicationConstants.REPORTS_USERNAME.getValue());
			enterTextToInputField(Locators.reportsPassword,ApplicationConstants.REPORTS_PASSWORD.getValue());
			clickBtn(Locators.reportsLogin);
			waituntilnew(locators.reportsHeader, 50);
		} else {
			APP_LOGS.info("Log in Failed");

		}
	}
	
	// Method for finding tab under panel
	public WebElement PanelTab(String paneltab, List<WebElement> e) {
		List<WebElement> poistabeles = e;

		for (WebElement poiele : poistabeles) {
			String tabtxt = poiele.getText().trim();
			if (tabtxt.equals(paneltab)) {
				return poiele;
			}
		}
		APP_LOGS.info("FAIL: Tab " + paneltab
				+ " is not displaying under panel");
		return null;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////

	// Method for selecting & clicking on Asset option
	public boolean assetElementAvailableOnAssetPanel(String assetforclick,
			List<WebElement> e) throws InterruptedException {
		List<WebElement> assetlist = e;
		APP_LOGS.info(assetlist.size());

		for (WebElement assetele : assetlist) {
			String assetdetailval = assetele.getText();

			String assetval[] = assetdetailval.split("\n");

			if (assetval[0].equals(assetforclick)) {
				APP_LOGS.info("PASS: Asset " + assetforclick
						+ " exist is Asset panel");
				clickBtn(assetele);
				Thread.sleep(1000);
			
				clickBtn(locators.telematicsassetitemselected);
				return true;
			}
		}
		return false;
	}
	
	
	public boolean driverElementAvailableOnDriversPanel(String driverForClick,
			List<WebElement> e) throws InterruptedException {
		List<WebElement> driverslist = e;
		APP_LOGS.info(driverslist.size());

		for (WebElement driverEle : driverslist) {
			String driverDetailVal = driverEle.getText();

			String driverVal[] = driverDetailVal.split("\n");

			if (driverVal[0].equals(driverForClick)) {
				APP_LOGS.info("PASS: Driver " + driverForClick
						+ " exist is Drivers panel");
				clickBtn(driverEle);
				Thread.sleep(1000);
				clickBtn(driverEle.findElement(By.xpath("//button[@title='Options']")));
				return true;
			}
		}
		return false;
	}
	
	
	
	

	// ///Method for verifying option button having options and click on it
	public String assetOptionButtonHavingOption(String optionclickval,
			WebElement e) {
		List<WebElement> pane = e.findElements(By.tagName("button"));
		String assetdetailsoptiontext = null;

		for (int i = 0; i < pane.size(); i++) {
			assetdetailsoptiontext = getobjStaticText(pane.get(i));
			if (assetdetailsoptiontext.equals(optionclickval)) {
				clickBtn(pane.get(i));
				return assetdetailsoptiontext;
			}
		}

		return assetdetailsoptiontext;
	}

	// method for Fetching Asset detail window data
	public ArrayList<String> methodforFetchingAssetdetailwindowdata(
			List<WebElement> e) throws InterruptedException {

		List<WebElement> Controlfield = e;

		ArrayList<String> assetvalueberforecancel = new ArrayList<String>();
		for (WebElement listele : Controlfield) {
			WebElement formcontrolele = listele.findElement(By
					.className("form-control"));
			String fieldabailable = formcontrolele.getTagName();

			if (fieldabailable.equals("select")) {
				String selval = getSelectedText(formcontrolele);

				assetvalueberforecancel.add(selval);
			} else if (fieldabailable.equals("input")) {

				String selval = formcontrolele.getAttribute("value");

				assetvalueberforecancel.add(selval);
			} else if (fieldabailable.equals("textarea")) {
				String selval = getobjStaticText(formcontrolele);
				assetvalueberforecancel.add(selval);

			}

		}

		return assetvalueberforecancel;

	}
	
	
	// method for Fetching Asset detail window data
	public ArrayList<String> methodForFetchingDriverDetailsWindowData(
				List<WebElement> e, List<String> items) throws InterruptedException {

			ArrayList<String> driverValueBerforeCancel = new ArrayList<String>();
			ArrayList<String> fieldsName = new ArrayList<String>();
			
			for (int i = 0; i < (e.size()|items.size()) ; i++) {
				
				String labelName= items.get(i);
				
				//System.out.println(labelName);
				
				String fieldabailable = e.get(i).getTagName();

				if (fieldabailable.equals("select")) {
					String selval = getAllSelectedText(e.get(i));

					driverValueBerforeCancel.add(labelName+"="+selval);
				} else if (fieldabailable.equals("input")) {

					String selval = e.get(i).getAttribute("value");

					driverValueBerforeCancel.add(labelName+"="+selval);
				} else if (fieldabailable.equals("textarea")) {
					String selval = getobjStaticText(e.get(i));
					driverValueBerforeCancel.add(labelName+"="+selval);

				}
				
			}
			
			return driverValueBerforeCancel;

		}
	
	
	
	
	
	// method for Fetching Asset detail Admin Tab data
	public ArrayList<String> methodForFetchingAssetdetailAdminTabData(
			List<WebElement> e) throws InterruptedException {

		List<WebElement> Controlfield = e;

		ArrayList<String> assetValueAfterSave = new ArrayList<String>();
		for (WebElement listele : Controlfield) {
			String fieldabailable = listele.getTagName();

				if (fieldabailable.equals("select")) {
					String selval = getSelectedText(listele);

					assetValueAfterSave.add(selval);
				} else if (fieldabailable.equals("input")) {

					String selval = listele.getAttribute("value");

					assetValueAfterSave.add(selval);
				} else if (fieldabailable.equals("textarea")) {
					String selval = getobjStaticText(listele);
					assetValueAfterSave.add(selval);

				}
				
		}

		return assetValueAfterSave;

	}
	
	
	
	public static double distance(double lat1, double lat2, double lon1,
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(lat2 - lat1);
	    Double lonDistance = Math.toRadians(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	
	
	

	// method for Set Asset detail window data
public void setAssetDetailwindowData(List<WebElement> e,
			ArrayList<String> inputdata) throws InterruptedException {
		int q = 0;
		int p = 0;
		List<WebElement> Controlfield = e;
		for (WebElement listele : Controlfield) {
			WebElement formcontrolele = listele.findElement(By
					.className("form-control"));
			String fieldabailable = formcontrolele.getTagName();

			if (fieldabailable.equals("select")) {
				selectDropDownByText(formcontrolele, inputdata.get(q));
				q++;
			} else if (fieldabailable.equals("input")) {
				formcontrolele.clear();
				enterTextToInputField(formcontrolele, inputdata.get(q));
				q++;
				String labelval = listele.findElement(By.tagName("label"))
						.getText();
				if (labelval.equals("Immobilisation timeout (minutes)")) {
					break;
				}
			} else if (fieldabailable.equals("textarea")) {
				formcontrolele.clear();
				enterTextToInputField(formcontrolele, inputdata.get(p));
			}

		}

	}
	
	
	
	// method for Set Driver details window data
public void setDriverDetailsWindowData(List<WebElement> e,
				ArrayList<String> inputdata) throws InterruptedException {
			int q = 0;
			int p = 0;
			List<WebElement> Controlfield = e;
			for (WebElement formcontrolele : Controlfield) {
				String fieldabailable = formcontrolele.getTagName();

				if (fieldabailable.equals("select")) {
					selectDropDownsByText(formcontrolele, inputdata.get(q));
					q++;
				} else if (fieldabailable.equals("input")) {
					if (formcontrolele.getAttribute("readonly") != null) 
					{
						
					}
					else {
						formcontrolele.clear();
						enterTextToInputField(formcontrolele, inputdata.get(q));
					}
					
					q++;
				} else if (fieldabailable.equals("textarea")) {
					formcontrolele.clear();
					enterTextToInputField(formcontrolele, inputdata.get(p));
				}

			}

		}
	
	
	
	
	
	
	

public WebElement returnmainmenuoptionelement(String mainmenu,
			String menuoption, List<WebElement> e) throws InterruptedException {
		List<WebElement> mainmenuele = e;
		int l = 0;
		int j = 1;
		for (WebElement menuele : mainmenuele) {
			if (j % 2 == 1) {
				String menutxt = getobjStaticText(menuele);

				if (menutxt.equals(mainmenu)) {
					APP_LOGS.info("PASS:" + mainmenu
							+ " Main Menu  displaying  ");
					clickBtn(menuele);
					List<WebElement> menuoptionele = e;

					Thread.sleep(2000);
					for (WebElement telematicsopt : menuoptionele) {
						String opttxt = getobjStaticText(telematicsopt);
						if (opttxt.equals(menuoption)) {
							APP_LOGS.info("PASS: Menu  " + menutxt
									+ " displaying option " + menuoption);
							return telematicsopt;
						}
					}
				}
			}
			j++;
		}
		return null;
	}

public boolean fieldsavailableoptioncheckuncheckisworkingfine(List<WebElement> e,String checkuncheck) {
		List<WebElement> assetsitem = e;
		boolean flg = true;
		
		if (checkuncheck.equalsIgnoreCase("OFF")) {
			for (WebElement item : assetsitem) {
				
					boolean speeddriver = isElementExist(item);
					if (speeddriver) {
						flg = false;
						return flg;
					}
				else {
					
					flg = true;
					return flg;
				}

			}
			return flg;
		} else if (checkuncheck.equalsIgnoreCase("ON")) {
			for (WebElement item : assetsitem) {
				boolean speeddriver = isElementExist(item);
				if (speeddriver) {
					flg = true;
					return flg;
				}
			else {
				
				flg = false;
				return flg;
			}

		}
		return flg;
		} else {
			APP_LOGS.info("FAIL: Enter correct value for check box i.e. ON/OF  ");
			return false;
		}
	}
	
public boolean preferencesAvailableOptionCheckUncheckIsWorkingFine(List<WebElement> e,List<WebElement> w,String checkuncheck) {
		List<WebElement> assetsitem = e;
		List<WebElement> assetsItem = w;
		boolean flg = true;
		
		if (checkuncheck.equalsIgnoreCase("OFF")) {
			for (WebElement item : assetsitem) {
				
					boolean speeddriver = isElementExist(item);
					if (speeddriver) {
						flg = false;
						return flg;
					}
				else {
					
					flg = true;
					return flg;
				}

			}
			return flg;
		} else if (checkuncheck.equalsIgnoreCase("ON")) {
			for (WebElement item : assetsItem) {
				boolean speeddriver = isElementExist(item);
				if (speeddriver) {
					flg = true;
					return flg;
				}
			else {
				
				flg = false;
				return flg;
			}

		}
		return flg;
		} else {
			APP_LOGS.info("FAIL: Enter correct value for check box i.e. ON/OF  ");
			return false;
		}
	}
	
public void clickDriverOnDriversList(String driver, String option) throws InterruptedException {
	
	clickBtn(locators.telematicsMainMenu);
	waituntilnew(locators.driversSubMenu, 20);	
	clickBtn(locators.driversSubMenu);
	Thread.sleep(500);
	if(getobjStaticText(locators.driversTitle).equals("DRIVERS"))
	{
		APP_LOGS.info(  getobjStaticText(locators.driversTitle) +  " Panel Tab " );
	}
		
		boolean driverTrue=driverElementAvailableOnDriversPanel( driver,  locators.driversList);
		Assert.assertTrue(driverTrue, " Driver " + driver + " not exist is drivers panel" );
		APP_LOGS.info("PASS: Driver " +driver + " exist is drivers panel and clicked on succesfully"  );

		//verifying option button having option  maintain Asset detail value and click on  
		String driverdetailsoptiontext= assetOptionButtonHavingOption(option, locators.assetoptionmenupane);
		Assert.assertEquals(driverdetailsoptiontext, option, "Driver option pop up window do not  having option " + option );
		APP_LOGS.info("PASS: Driver option pop up window having option "+ option);
		
		//verifying header of Asset detail dialog window
		WebElement driverDialogDetails= locators.assetMaintaindetailsdialog;
		String driverHeader=getobjStaticText(driverDialogDetails);

		if(driverHeader.equals("Driver"))
		{
			Assert.assertEquals(driverHeader, "Driver", "Verifying Driver dialog having heade Driver");
			APP_LOGS.info("PASS: Driver detail dialog having header " + "Driver");
		}
		else
		{
			APP_LOGS.info("FAIL: Driver detail dialog do not having header " + "Driver");
			Assert.assertEquals(driverHeader, "Driver", "Driver detail dialog do not having header Driver");
		}
		
	}
	
	
	
public void clickAssetOnAssetList(String asset, String option) throws InterruptedException {
		
		boolean assettrue=assetElementAvailableOnAssetPanel( asset,  locators.telematicsAssetList);
		Assert.assertTrue(assettrue, " Asset " + asset + " not exist is Asset panel" );
		APP_LOGS.info("PASS: Asset " +asset + " exist is Asset panel and clicked on succesfully"  );

		//verifying option button having option  maintain Asset detail value and click on  
		String assetdetailsoptiontext= assetOptionButtonHavingOption(option, locators.assetoptionmenupane);
		Assert.assertEquals(assetdetailsoptiontext, option, "Asset option pop up window do not  having option " + option );
		APP_LOGS.info("PASS: Asset option pop up window having option "+ option);
		
		//verifying header of Asset detail dialog window
		WebElement assetdialogMaintaindetails= locators.assetMaintaindetailsdialog;
		String assetheader=getobjStaticText(assetdialogMaintaindetails);

		if(assetheader.equals("Asset"))
		{
			Assert.assertEquals(assetheader, "Asset", "Verifying asset detail dialog having heade Asset");
			APP_LOGS.info("PASS: Asset detail dialog having header " + "Asset");
		}
		else
		{
			APP_LOGS.info("FAIL: Asset detail dialog do not having header " + "Asset");
			Assert.assertEquals(assetheader, "Asset", "Asset detail dialog do not having header Asset");
		}
		
	}
	
	
public int reportsVerificationBetweenReportsInHarshDrivingReports(WebElement l1,List<WebElement> l2,List<WebElement> l3,String s1,String s2,String s3) throws InterruptedException 
{
			int totalCount=0;
			String val;
			waituntilnew(l1, 60);
			clickBtn(l1);
			Thread.sleep(500);
			waituntilnew(locators.reportsAccountSelector, 20);
			selectDropDownByText(locators.reportsAccountSelector, s1);
			Thread.sleep(500);
			if (isElementExist(locators.reportsDateRange)) 
			{
				selectDropDownByText(locators.reportsDateRange, s2);
				Thread.sleep(500);
			} 
			clickBtn(locators.reportsView);
			if (isElementExist(locators.reportsEventTypeMessage)) 
			{
				System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

			}
			else 
			{
				waituntilnew(locators.reportsViewHeader, 1500);
				String reportName= getobjStaticText(locators.reportsViewHeader);
				System.out.println("Reports Header:  "+reportName);
				Assert.assertEquals(reportName,s3);
				Thread.sleep(1000);
				String totalPageCount=getobjStaticText(locators.reportsTotalPages);
				System.out.println("Total Page count is: "+totalPageCount);
				int harshAccleration = 0,harshBrake= 0;
				for (int i = 1; i <= Integer.valueOf(totalPageCount); i++) 
					
				{
					System.out.println("Page number: "+ i);
					waituntilnew(locators.reportsNextButton, 60);
					Thread.sleep(6000);
					if (i==1) 
					{
						for (WebElement webElement : l2) 
						{
							
							if (getobjStaticText(webElement).trim().equalsIgnoreCase("Harsh Acceleration")) 
							{
								harshAccleration++;
							} 
							else if (getobjStaticText(webElement).trim().equalsIgnoreCase("Harsh Brake")) 
							{
								harshBrake++;
							}
							else
							{
								System.out.println("Invalid entry: "+getobjStaticText(webElement).trim());
							}
							
						}
					}
					
					else 
					{
						for (WebElement webElement : l3) 
						{

							if (getobjStaticText(webElement).trim().equalsIgnoreCase("Harsh Acceleration")) 
							{
								harshAccleration++;
							} 
							else if (getobjStaticText(webElement).trim().equalsIgnoreCase("Harsh Brake")) 
							{
								harshBrake++;
							}
							else
							{
								System.out.println("Invalid entry: "+getobjStaticText(webElement).trim());
							}

						}
					}
					waituntilnewElementtobeClickable(locators.reportsNextButton, 100);
					retryingFindClick(locators.reportsNextButton);
					Thread.sleep(1000);
					
				}
				System.out.println("Harsh Acceleration count: "+harshAccleration);
				System.out.println("Harsh Brake count: "+harshBrake);
				totalCount=harshAccleration+harshBrake;
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
			}
			return totalCount;
	
}



public int reportsVerificationBetweenReportsInCANBusJourneySummaries(WebElement l1,List<WebElement> l2,List<WebElement> l3,List<WebElement> l4,List<WebElement> l5,String s1,String s2,String s3) throws InterruptedException 
{
			int totalCounting=0;
			String val;
			waituntilnew(l1, 60);
			clickBtn(l1);
			Thread.sleep(500);
			waituntilnew(locators.reportsAccountSelector, 20);
			selectDropDownByText(locators.reportsAccountSelector, s1);
			Thread.sleep(500);
			if (isElementExist(locators.reportsDateRange)) 
			{
				selectDropDownByText(locators.reportsDateRange, s2);
				Thread.sleep(500);
			} 
			clickBtn(locators.reportsView);
			if (isElementExist(locators.reportsEventTypeMessage)) 
			{
				System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

			}
			else 
			{
				waituntilnew(locators.reportsViewHeader, 1500);
				String reportName= getobjStaticText(locators.reportsViewHeader);
				System.out.println("Reports Header:  "+reportName);
				Assert.assertEquals(reportName,s3);
				Thread.sleep(1000);
				String totalPageCount=getobjStaticText(locators.reportsTotalPages);
				System.out.println("Total Page count is: "+totalPageCount);
				int harshAcclerationCount = 0,harshBrakeCount= 0;
				for (int i = 1; i <= Integer.valueOf(totalPageCount); i++) 
					
				{
					System.out.println("Page number: "+ i);
					waituntilnew(locators.reportsNextButton, 60);
					Thread.sleep(6000);
					if (i==1) 
					{
						for(int j=0;j<(l2.size() & l3.size());j++) 
						{
							
							if (!getobjStaticText(l2.get(j)).trim().isEmpty()|!getobjStaticText(l3.get(j)).trim().isEmpty()) 
							{
								harshAcclerationCount=Integer.parseInt(getobjStaticText(l2.get(j)))+harshAcclerationCount;
								harshBrakeCount=Integer.parseInt(getobjStaticText(l3.get(j)))+harshBrakeCount;
							} 
							else
							{
								System.out.println("Invalid entry for Harsh acceleration: "+getobjStaticText(l2.get(j)).trim()+" and Harsh Brake: "+getobjStaticText(l3.get(j)).trim());
							}
							
						}
					}
					
					else 
					{

						for(int j=0;j<(l4.size() & l5.size());j++) 
						{
							
							if ((!getobjStaticText(l4.get(j)).trim().isEmpty())|(!getobjStaticText(l5.get(j)).trim().isEmpty())) 
							{
								harshAcclerationCount=Integer.parseInt(getobjStaticText(l4.get(j)))+harshAcclerationCount;
								harshBrakeCount=Integer.parseInt(getobjStaticText(l5.get(j)))+harshBrakeCount;
							} 
							else
							{
								System.out.println("Invalid entry for Harsh acceleration: "+getobjStaticText(l2.get(j)).trim()+" and Harsh Brake: "+getobjStaticText(l3.get(j)).trim());
							}
							
						}
					
					}
					waituntilnewElementtobeClickable(locators.reportsNextButton, 100);
					retryingFindClick(locators.reportsNextButton);
					Thread.sleep(1000);
					
				}
				System.out.println("Harsh Acceleration count: "+harshAcclerationCount);
				System.out.println("Harsh Brake count: "+harshBrakeCount);
				totalCounting=harshAcclerationCount+harshBrakeCount;
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
			}
			return totalCounting;
	
}



public void reportsVerificationForNegativeValues(WebElement l1,List<WebElement> l2,List<WebElement> l3,String s1,String s2,String s3) throws InterruptedException 
{
		
			String val;
			waituntilnew(l1, 60);
			clickBtn(l1);
			Thread.sleep(500);
			waituntilnew(locators.reportsAccountSelector, 20);
			selectDropDownByText(locators.reportsAccountSelector, s1);
			Thread.sleep(500);
			if (isElementExist(locators.reportsDateRange)) 
			{
				selectDropDownByText(locators.reportsDateRange, s2);
				Thread.sleep(500);
			} 
			clickBtn(locators.reportsView);
			if (isElementExist(locators.reportsEventTypeMessage)) 
			{
				System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

			}
			else 
			{
				waituntilnew(locators.reportsViewHeader, 1500);
				String reportName= getobjStaticText(locators.reportsViewHeader);
				System.out.println("Reports Header:  "+reportName);
				Assert.assertEquals(reportName,s3);
				Thread.sleep(1000);
				String totalPageCount=getobjStaticText(locators.reportsTotalPages);
				System.out.println("Total Page count is: "+totalPageCount);
				int negativeCount=0;
				for (int i = 1; i <= Integer.valueOf(totalPageCount); i++) 
					
				{
					System.out.println("Page number: "+ i);
					waituntilnew(locators.reportsNextButton, 60);
					Thread.sleep(6000);
					if (i==1) 
					{
						for (WebElement element : l2) 
						{
							val=getobjStaticText(element);
							if (val.contains("-")) 
							{
								System.out.println("Failed at page number: "+ i);
								negativeCount++;
							} 
						}
					}
					
					else 
					{
						for (WebElement element : l3) 
						{
							val=getobjStaticText(element);
							if (val.contains("-")) 
							{
								negativeCount++;
								System.out.println("Failed at page number: "+ i);
							} 
						}
					}
					waituntilnewElementtobeClickable(locators.reportsNextButton, 100);
					retryingFindClick(locators.reportsNextButton);
					Thread.sleep(1000);
					
				}
				navigateToPreviousPage();
				Thread.sleep(700);
				clickBtn(locators.homeHeader);
				Thread.sleep(800);
				if (negativeCount>0) 
				{
					System.out.println("Negative values count is: "+negativeCount);
					Assert.fail("Negative values are present in the report for distance travelled");
				}
			}
	
}









	
public void reportsVerification(WebElement l1,List<WebElement> l2,List<WebElement> l3,String s1,String s2,String s3) throws InterruptedException 
	{
			
				String val;
				waituntilnew(l1, 60);
				clickBtn(l1);
				Thread.sleep(500);
				waituntilnew(locators.reportsAccountSelector, 20);
				selectDropDownByText(locators.reportsAccountSelector, s1);
				Thread.sleep(500);
				if (isElementExist(locators.reportsDateRange)) 
				{
					selectDropDownByText(locators.reportsDateRange, s2);
					Thread.sleep(500);
				} 
				clickBtn(locators.reportsView);
				if (isElementExist(locators.reportsEventTypeMessage)) 
				{
					System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

				}
				else 
				{
					waituntilnew(locators.reportsViewHeader, 1500);
					String reportName= getobjStaticText(locators.reportsViewHeader);
					System.out.println("Reports Header:  "+reportName);
					Assert.assertEquals(reportName,s3);
					Thread.sleep(1000);
					String totalPageCount=getobjStaticText(locators.reportsTotalPages);
					System.out.println("Total Page count is: "+totalPageCount);
					for (int i = 1; i <= Integer.valueOf(totalPageCount); i++) 
						
					{
						System.out.println("Page number: "+ i);
						waituntilnew(locators.reportsNextButton, 60);
						Thread.sleep(6000);
						if (i==1) 
						{
							for (WebElement element : l2) 
							{
								val=getobjStaticText(element);
								if (val.contains("%")) 
								{
									String[] cellVal=val.split("%");
									
									System.out.println(cellVal[0]);
									if (Float.parseFloat(cellVal[0])>100.0)
									{
										Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
									}
									
								} else 
								{
									System.out.println("Results are N/A or empty: "+val);
								}
							}
						}
						
						else 
						{
							for (WebElement element : l3) 
							{
								val=getobjStaticText(element);
								if (val.contains("%")) 
								{
									String[] cellVal=val.split("%");
									
									System.out.println(cellVal[0]);
									try 
									{
										if (Float.parseFloat(cellVal[0])>100.0)
										{
											
											Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
												
										}
									} catch (Exception e) 
									{
										Assert.fail("Idle value is "+ cellVal[0] +", Which is more than hundred at page number: "+ i);
									}
									
								} 
								else 
								{
									System.out.println("Results are N/A or empty: "+val);
								}
							}
							
						}
						waituntilnewElementtobeClickable(locators.reportsNextButton, 100);
						retryingFindClick(locators.reportsNextButton);
						Thread.sleep(1000);
						
					}
					navigateToPreviousPage();
					Thread.sleep(700);
					clickBtn(locators.homeHeader);
					Thread.sleep(800);
				}
		
	}



public void summaryReportsVerification(WebElement w1,List<WebElement> l1,List<WebElement> l2,List<WebElement> l3,List<WebElement> l4,String s1,String s2,String s3) throws InterruptedException 
{
	String val;
	waituntilnew(w1, 60);
	clickBtn(w1);
	Thread.sleep(500);
	waituntilnew(locators.reportsAccountSelector, 20);
	selectDropDownByText(locators.reportsAccountSelector, s1);
	Thread.sleep(500);
	if (isElementExist(locators.reportsDateRange)) 
	{
		selectDropDownByText(locators.reportsDateRange, s2);
		Thread.sleep(500);
	} 
	clickBtn(locators.reportsView);
	if (isElementExist(locators.reportsEventTypeMessage)) 
	{
		System.out.println("Mandatory field is missing==> Error message is: "+getobjStaticText(locators.reportsEventTypeMessage));

	}
	else 
	{
		waituntilnew(locators.reportsViewHeader, 1500);
		String reportName= getobjStaticText(locators.reportsViewHeader);
		System.out.println("Reports Header:  "+reportName);
		Assert.assertEquals(reportName,s3);
		Thread.sleep(1000);
		String totalPageCount=getobjStaticText(locators.reportsTotalPages);
		System.out.println(totalPageCount);
		for (int i = 1; i <= Integer.valueOf(totalPageCount); i++) 
			
		{
			System.out.println("Page number: "+ i);
			waituntilnew(locators.reportsNextButton, 40);
			if (i==1) 
			{
				for (WebElement element : l1) 
				{
					val=getobjStaticText(element);
					if (val.contains("%")) 
					{
						String[] cellVal=val.split("%");
						
						System.out.println(cellVal[0]);
						if (Float.parseFloat(cellVal[0])>100.0)
						{
							Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
						}
						
					} else 
					{
						System.out.println("Results are N/A or empty: "+val);
					}
				}
			}
			
			else if (i==2) 
			{
				for (WebElement element : l2) 
				{
					val=getobjStaticText(element);
					if (val.contains("%")) 
					{
						String[] cellVal=val.split("%");
						
						System.out.println(cellVal[0]);
						if (Float.parseFloat(cellVal[0])>100.0)
						{
							Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
						}
						
					} else 
					{
						System.out.println("Results are N/A or empty: "+val);
					}
				}
			}
			
			
			else if (i==3) 
			{
				for (WebElement element : l3) 
				{
					val=getobjStaticText(element);
					if (val.contains("%")) 
					{
						String[] cellVal=val.split("%");
						
						System.out.println(cellVal[0]);
						if (Float.parseFloat(cellVal[0])>100.0)
						{
							Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
						}
						
					} else 
					{
						System.out.println("Results are N/A or empty: "+val);
					}
				}
			}
			
			else 
			{
				for (WebElement element : l4) 
				{
					val=getobjStaticText(element);
					if (val.contains("%")) 
					{
						String[] cellVal=val.split("%");
						
						System.out.println(cellVal[0]);
						try 
						{
							if (Float.parseFloat(cellVal[0])>100.0)
							{
								
								Assert.fail("Idle value for "+s3 + " report is "+ cellVal[0] +", Which is more than 100% at page number: "+ i);
									
							}
						} catch (Exception e) 
						{
							Assert.fail("Idle value is "+ cellVal[0] +", Which is more than hundred at page number: "+ i);
						}
						
					} 
					else 
					{
						System.out.println("Results are N/A or empty: "+val);
					}
				}
				
			}
			waituntilnewElementtobeClickable(locators.reportsNextButton, 20);
			retryingFindClick(locators.reportsNextButton);
			Thread.sleep(1000);
		}
		navigateToPreviousPage();
		Thread.sleep(700);
		clickBtn(locators.homeHeader);
		Thread.sleep(800);
	}
	
}
	
	
	
public void clickAssetOnAssetListAndShowNearestAssetsOption(String asset, String option) throws InterruptedException {
		
		boolean assettrue=assetElementAvailableOnAssetPanel( asset,  locators.telematicsAssetList);
		Assert.assertTrue(assettrue, " Asset " + asset + " not exist is Asset panel" );
		APP_LOGS.info("PASS: Asset " +asset + " exist is Asset panel and clicked on succesfully"  );
		Thread.sleep(1000);
		//verifying option button having option  maintain Asset detail value and click on  
		String assetdetailsoptiontext= assetOptionButtonHavingOption(option, locators.assetoptionmenupane);
		Assert.assertEquals(assetdetailsoptiontext, option, "Asset option pop up window do not  having option " + option );
		APP_LOGS.info("PASS: Asset option pop up window having option "+ option);
		
		//verifying header of Asset detail dialog window
		WebElement nearestAssetsPanelHeader= locators.nearestAssetsPanelHeader;
		String nearestAssetsHeader=getobjStaticText(nearestAssetsPanelHeader);

		if(nearestAssetsHeader.equals("NEAREST ASSETS"))
		{
			Assert.assertEquals(nearestAssetsHeader, "NEAREST ASSETS", "Verifying Nearest Assets panel");
			APP_LOGS.info("PASS: Nearest Assets panel having header " + "NEAREST ASSETS");
		}
		else
		{
			APP_LOGS.info("FAIL: Nearest Assets panel do not having header " + "NEAREST ASSETS");
			Assert.assertEquals(nearestAssetsHeader, "NEAREST ASSETS", "Nearest Assets panel do not having header");
		}
		
	}
	
	
public void clickAssetOnAssetListAndShowJourneyOption(String asset, String option) throws InterruptedException {
	
	boolean assettrue=assetElementAvailableOnAssetPanel( asset,  locators.telematicsAssetList);
	Assert.assertTrue(assettrue, " Asset " + asset + " not exist is Asset panel" );
	APP_LOGS.info("PASS: Asset " +asset + " exist is Asset panel and clicked on succesfully"  );
	Thread.sleep(1000);
	//verifying option button having option  maintain Asset detail value and click on  
	String assetdetailsoptiontext= assetOptionButtonHavingOption(option, locators.assetoptionmenupane);
	Assert.assertEquals(assetdetailsoptiontext, option, "Asset option pop up window do not  having option " + option );
	APP_LOGS.info("PASS: Asset option pop up window having option "+ option);
	
	//verifying header of Asset detail dialog window
	WebElement journeysPanelHeader= locators.journeysPanelHeader;
	String journeysHeader=getobjStaticText(journeysPanelHeader);

	if(journeysHeader.equals("JOURNEYS"))
	{
		Assert.assertEquals(journeysHeader, "JOURNEYS", "Verifying asset detail dialog having heade Asset");
		APP_LOGS.info("PASS: Journeys detail dialog having header " + "Journeys");
	}
	else
	{
		APP_LOGS.info("FAIL: Journeys panel do not having header " + "Asset");
		Assert.assertEquals(journeysHeader, "Journeys", "Journeys panel do not having header");
	}
	
}
	

public void closeDefaultOpenPanels(WebElement leftpanel,
			WebElement closepanel) 
	{
		Actions action = new Actions(getDriver());
		action.moveToElement(leftpanel);
		clickBtn(leftpanel);
		clickBtn(closepanel);
		clickBtn(closepanel);
		clickBtn(closepanel);
	}

	
	
public static String[]  geoCodeToLatLangConverter(String address) throws Exception 
	{
		int responseCode = 0;
		String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
		URL url = new URL(api);
		HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
		httpConnection.connect();
		responseCode = httpConnection.getResponseCode();
		if(responseCode == 200)
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
			Document document = builder.parse(httpConnection.getInputStream());
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();
			XPathExpression expr = xpath.compile("/GeocodeResponse/status");
			String status = (String)expr.evaluate(document, XPathConstants.STRING);
			if(status.equals("OK"))
			{
				expr = xpath.compile("//geometry/location/lat");
				String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
				expr = xpath.compile("//geometry/location/lng");
				String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
				return new String[] {latitude, longitude};
			}
			else
			{
				throw new Exception("Error from the API - response status: "+status);
			}
		}
		return null;
	}
		
		
		
		
}
	
	
	

