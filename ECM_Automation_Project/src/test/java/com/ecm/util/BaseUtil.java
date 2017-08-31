package com.ecm.util;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.write.DateTime;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.jam.mutable.MInvokable;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;





import com.google.common.base.Function;

public class BaseUtil extends CommonConfig {
	private static int veryShortTime = 2;
	private static int shortTime = 5;
	private static int mediumTime = 25;
	private static int LongTime = 45;
	
	public static WebDriverWait webdriverWait;
	


	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public Statement SQLserverConnection() throws ClassNotFoundException,SQLException {		
		//String dbUrl = "jdbc:sqlserver://192.168.0.122;databaseName=Phoenix";
		String dbUrl = "jdbc:mysql://localhost:3306/ecmtestdb";		
		String Username = "root";
		String Password = "root";

		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection connection = DriverManager.getConnection(dbUrl, Username,Password);

		Statement stmt = connection.createStatement();
		return stmt;
	}

	public Connection getDBConnection() throws ClassNotFoundException,SQLException {
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Class.forName("com.mysql.jdbc.Driver");
		//String dbUrl = "jdbc:sqlserver://192.168.0.122;databaseName=";
		//String dbname = "Phoenix";
		//String Username = "root";
		//String Password = "root";
		String dbUrl = "jdbc:mysql://localhost:3306/;databaseName=";
		String dbname = "ecmtestdb";
		String Username = "root";
		String Password = "root";
		Connection dbConn = DriverManager.getConnection(dbUrl + dbname,Username, Password);
		return dbConn;
	}

	public void Maximize() {
		getDriver().manage().window().maximize();
	}

	public String getTitle() {
		return getDriver().getTitle();
	}

	public WebElement getElement(String selector) {
		return getDriver().findElement(By.cssSelector(selector));
	}

	public void navigateToPreviousPage() throws InterruptedException {
		getDriver().navigate().back();
		Thread.sleep(6000);
	}

	public boolean ifAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public WebElement getElementByJs(String jQuerySelector) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		return (WebElement) js.executeScript(jQuerySelector);
	}

	public List<WebElement> getElementsByJs(String jQuerySelector) {

		getDriver().manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		List<WebElement> executeScript = (List<WebElement>) js.executeScript(jQuerySelector);
		List<WebElement> script = executeScript;
		List<WebElement> elements = script;
		return elements;
	}

	public void executeJS(String code) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript(code, "");
	}

	public void clickOnElementUsingJS(WebElement e) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", e);
	}

	public Boolean isJQueryLoaded() {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		return (Boolean) js.executeScript("if(window.jQuery) {return true} else {return false}");
	}

	public boolean clickRadioBtn(WebElement e) {
		try {
			e.click();
			return true;

		} catch (NoSuchElementException s) {
			return false;
		}
	}

	public boolean clickBtn(WebElement e) {
		try {
			e.click();
			return true;

		} catch (NoSuchElementException s) {
			return false;
		}

	}

	public boolean clickOperation(WebElement element) {

		if (element == null) {

			return false;
		} else {

			if (element.isDisplayed()) {
				element.click();
				APP_LOGS.info("PASS: Element found and clicked successfully");
				return true;
			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed: Object Ident Type: ");
				return false;
			}

		}
	}

	public boolean clickLink(WebElement e) {
		try {
			e.click();
			return true;

		} catch (NoSuchElementException s) {
			return false;
		}
	}

	public void selectCheckbox(WebElement e) {
		if (!e.isSelected()) {
			e.click();
		}

	}
	
	public String  rgbToHex(int r,int g,int b) {
		String hex = String.format("#%02x%02x%02x",r, g, b);
		return hex;
	}
	

	public void deSelectCheckbox(WebElement e) {
		if (e.isSelected()) {
			e.click();
		}
	}
	
	public String dayLightSaving430Hrs(ArrayList<Time> Firstinstancetimestamp,int i) {
		Date datetime1 =Firstinstancetimestamp.get(i);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		APP_LOGS.info("instancet Data base Time  " + datetime1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime1);
		cal.add(Calendar.HOUR, 4);
		cal.add(Calendar.MINUTE, 30);
		String newTime1 = sdf.format(cal.getTime());
		return newTime1;
	
	}
	
	public String dayLightSaving530Hrs(ArrayList<Time> Firstinstancetimestamp,int i) {
		Date datetime1 =Firstinstancetimestamp.get(i);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		APP_LOGS.info("instancet Data base Time  " + datetime1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime1);
		cal.add(Calendar.HOUR, 5);
		cal.add(Calendar.MINUTE, 30);
		String newTime1 = sdf.format(cal.getTime());
		return newTime1;
		
		}

	public boolean enterTextToInputField(WebElement e, String text) throws InterruptedException {
		if (e == null) {

			return false;
		} else {
			if (e.isDisplayed()) {
				Thread.sleep(500);
				e.clear();
				Thread.sleep(500);
				e.sendKeys(text);
				APP_LOGS.info("PASS: Element found and entered value successfully");
				return true;
			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed: Object Locator : "
						+ e);
				return false;
			}

		}

	}
	
	public boolean clearInputField(WebElement e) throws InterruptedException {
		if (e == null) {

			return false;
		} else {
			if (e.isDisplayed()) {
				Thread.sleep(500);
				e.clear();
				Thread.sleep(500);
				APP_LOGS.info("PASS: Element found and cleared the value successfully");
				return true;
			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed: Object Locator : "
						+ e);
				return false;
			}

		}

	}
	

	public boolean enterKeysToInputField(WebElement e, Keys operation) {
		if (e == null) {

			return false;
		} else {
			if (e.isDisplayed()) {
				e.clear();
				e.sendKeys(operation);
				APP_LOGS.info("PASS: Element found and entered value successfully");
				return true;
			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed: Object Locator : "
						+ e);
				return false;
			}

		}

	}

	public boolean hoverOverElement(WebElement el) {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(el).build().perform();
		return false;

	}
	
	public boolean hoverOverElementOnSubMenu(WebElement el, WebElement subel) 
	{
		if (el == null) 
		{
			return false;
		} else 
		{
			if (el.isDisplayed()) 
			{
				Actions actions = new Actions(getDriver());
				actions.moveToElement(el).moveToElement(subel).build().perform();
				APP_LOGS.info("PASS: Element found and displayed successfully");
				return true;
			} else 
			{
				APP_LOGS.info("FAILURE: Elements not found or displayed: "+ el +" and "+subel);
				return false;
			}

		}
		

	}

	public void hoverOverElementandSelectSubMenu(WebElement el, WebElement subel) {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(el).moveToElement(subel).click().perform();
	}

	public void switchToIframe(String frameLoc) {
		webdriverWait.ignoring(NoSuchElementException.class).until(
				ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLoc));
	}

	public String getCurrentTimeStamp() {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(new Date()).toString();
	}

	public void selectDropDownByValue(WebElement e, String value) {

		Select dropdown = new Select(e);
		dropdown.selectByValue(value);
	}

	public void selectDropDownByText(WebElement e, String text) {

		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(text);
	}
	
	public void selectDropDownsByText(WebElement e, String text) {
		Select dropdown = new Select(e);
	if (dropdown.isMultiple()) 
	{
		dropdown.deselectAll();
		dropdown.selectByVisibleText(text);
	
	}
	else 
	{
		dropdown.selectByVisibleText(text);
	}
	
	}
	
	
	public void selectDropDownValueByIndex(WebElement e, int index) {

		Select dropdown = new Select(e);
		dropdown.selectByIndex(index);
	}

	public String getSelectedDropDownOptionText(WebElement e) {
		Select dropdown = new Select(e);
		return dropdown.getFirstSelectedOption().getText();
	}

	public String getSelectedDropDownOptionValue(WebElement e) {
		Select dropdown = new Select(e);
		return dropdown.getFirstSelectedOption().getAttribute("value");
	}
	
	public String currentDate() {
		
		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy/MM/dd").format(date);
		return modifiedDate;
	}
	
public String tommorrowsDate() {
		
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DATE, 1);
	String tomoDate=(new SimpleDateFormat("yyyy/MM/dd").format(c.getTime()));
		return tomoDate;
	}

public String yesterdaysDate() {
	
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DATE, -1);
	String yestDate=(new SimpleDateFormat("yyyy/MM/dd").format(c.getTime()));
		return yestDate;
	}

public String preDayDate() {
	
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DATE, -1);
	String yestDate=(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
		return yestDate;
	}

public String TodaysDate() {
	
	Date date = new Date();
	String modifiedDate= new SimpleDateFormat("dd/MM/yyyy").format(date);
	return modifiedDate;
}
public double roundUp(double a) {
	double roundOff = Math.round(a * 100.0) / 100.0;
	return roundOff;
}

public String roundOff(Number n) {
DecimalFormat df = new DecimalFormat("#.#");
df.setRoundingMode(RoundingMode.CEILING);

    Double d = n.doubleValue();
   return(df.format(d));
}

public String strToFloat(String str) {
	Float fl= Float.valueOf(str);
   return(Float.toString(fl));
}

public Date roundOffDate(Date now) {
	Date nearestMinute = DateUtils.round(now, Calendar.MINUTE);
			return nearestMinute;
}

public String timeDifference(String startDate,String endDate) throws ParseException {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long second = 1000l;
    long minute = 60l * second;
    long hour = 60l * minute;

    // parsing input
    Date date1 = dateFormat.parse(startDate);
    Date date2 = dateFormat.parse(endDate);

    // calculation
    long diff = date2.getTime() - date1.getTime();
    // printing output
    return(String.format("%02d", diff / hour)+":"+String.format("%02d", (diff % hour) / minute)+":"+String.format("%02d", (diff % minute) / second));
}

public void roundOffToMin(String str) throws ParseException  {
	
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	Date date = sdf.parse(str);

	Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
	calendar.setTime(date);   // assigns calendar to given date 
	int hour = calendar.get(Calendar.HOUR);
	int minute=calendar.get(calendar.MINUTE);
	int second=calendar.get(calendar.SECOND);
	System.out.println(hour+minute+second);
	
	if (second>29||minute==59||hour==23) 
	{
		System.out.println("00:00:00");
			
		}
		minute +=1;
		if (minute==59 && hour <23) {
			hour+=1;
		}
		
	} 
	

	public String getselectNormalDropDownPostion(WebElement e) {
		Select dropdown = new Select(e);
		List<WebElement> list = dropdown.getOptions();
		int postion = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText()
					.equals(dropdown.getFirstSelectedOption().getText())) {
				System.out.println("The index of the selected option is: " + i);
				postion = i;
				break;
			}
		}
		return Integer.toString(postion);
	}

	public void selectDate(String cssPath, String date) {

		StringBuilder builder = new StringBuilder();
		builder.append("$('").append(cssPath).append("').val('").append(date)
				.append("')");
		executeJS(builder.toString());

	}

	public void CheckImage(WebElement imgelement) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) getDriver();

		Boolean ImagePresent = (Boolean) js
				.executeScript(
						"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
						imgelement);
		if (!ImagePresent) {
			System.out.println("Image not displayed.");
		} else {
			System.out.println("Image displayed.");
		}
	}

	public boolean verifyimageActive(WebElement imgElement) {

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			// verifying response code he HttpStatus should be 200 if not,
			// increment as invalid images count
			if (response.getStatusLine().getStatusCode() != 200) {

				invalidImageCount++;
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	// Method for getting the selected value under selected object
	public String getSelectedText(WebElement e) throws InterruptedException {

		if (e.isDisplayed()) {

			Selectoption = new Select(e);
			WebElement selelement = Selectoption.getFirstSelectedOption();
			String selvalue = selelement.getText().trim();

			return selvalue;
		} else {
			APP_LOGS.info("FAILURE: Element not found or displayed: " + e);
			return null;
		}
	}
	
	public String getAllSelectedText(WebElement e) throws InterruptedException {

		if (e.isDisplayed()) 
		{

			Selectoption = new Select(e);
			if (Selectoption.isMultiple()) 
			{
				List<WebElement> selelement = Selectoption.getAllSelectedOptions();
				String selectedValue="";
				String selvalue= null ;
				for (WebElement webElement : selelement) 
				{
					selvalue = webElement.getText().trim();
					selectedValue=selectedValue+selvalue+"   ";
				}
				return selectedValue;
				
			} else 
			{
				
				WebElement selelement = Selectoption.getFirstSelectedOption();
				String selvalue = selelement.getText().trim();
				return selvalue;
			}
			
		} 
		else 
		{
			APP_LOGS.info("FAILURE: Element not found or displayed: " + e);
			return null;
		}
	}
	
	
	
	

	public boolean isTextPresentInPage(String text) {
		String bodyText = getDriver().findElement(By.tagName("body")).getText();
		if (bodyText.contains(text))
			return true;
		else
			return false;
	}

	public String getobjStaticText(WebElement element) {

		if (element == null) {

			return null;
		} else {
			if (element.isDisplayed()) {

				return element.getText().trim();

			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed");

				return null;
			}
		}

	}

	public WebElement getElementWithDynamicXpath(String xpathValue,
			String... replaceValues) {
		int i = 1;
		for (String replaceValue : replaceValues) {
			xpathValue = xpathValue.replace("x" + i, replaceValue);
			i++;
		}
		List<WebElement> list = getDriver().findElements(By.xpath(xpathValue));
		if (list.size() == 0)
			return null;
		return list.get(0);
	}

	public List<WebElement> getElementsWithDynamicXpath(String xpathValue,
			List<String> replaceValues) {
		int i = 1;
		for (String replaceValue : replaceValues) {
			xpathValue = xpathValue.replace("x" + i, replaceValue);
			i++;
		}
		return getDriver().findElements(By.xpath(xpathValue));
	}

	public List<WebElement> getElementsWithDynamicXpathValue(String xpathValue,
			String... replaceValues) {
		int i = 1;
		for (String repValue : replaceValues) {
			xpathValue = xpathValue.replace("x" + i, repValue);
			i++;
		}
		return getDriver().findElements(By.xpath(xpathValue));
	}

	public WebElement getElementWithDynamicCSSpath(String xpathValue,
			String replaceValue) {
		return getDriver().findElement(
				By.cssSelector(xpathValue.replace("x1", replaceValue)));
	}

	public boolean isElementPresent(String csslocator) {
		if (getDriver().findElements(By.cssSelector(csslocator)).size() == 0)
			return false;
		else
			return true;
	}

	public boolean confirmWaitAlert() {
		try {

			webdriverWait.until(ExpectedConditions.alertIsPresent());
			getDriver().switchTo().alert().accept();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public Object navigate(String url, Class<?> className) {

		getDriver().navigate().to(url);
		return PageFactory.initElements(getDriver(), className);
	}

	public boolean isAlertPresent() {
		try {
			getDriver().switchTo().alert();
			return true;

		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public Alert getAlertbox() {

		try {
			return getDriver().switchTo().alert();

		} catch (NoAlertPresentException ex) {
			return null;
		}
	}

	public void acceptAllAlerts() {
		try {
			if (isAlertPresent()) {
				do {
					getDriver().switchTo().alert().accept();
				} while (isAlertPresent());
			}
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
		}
	}

	public void dismissAllAlerts() {
		if (isAlertPresent()) {
			do {
				getDriver().switchTo().alert().dismiss();
				;
			} while (isAlertPresent());
		}
	}

	public String getTextFromPopup() {
		return getDriver().switchTo().alert().getText();
	}

	public void waituntilnew(WebElement e, int timeinSeconds) {

		WebDriverWait wait = new WebDriverWait(getDriver(), timeinSeconds);
		wait.until(ExpectedConditions.visibilityOf(e));

	}

	public void waituntilnewElementtobeClickable(WebElement e, int timeinSeconds) {

		WebDriverWait wait = new WebDriverWait(getDriver(), timeinSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(e));

	}

	public void waituntilPageToBeLoaded(int timeinSeconds) {

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String command = "return document.readyState";

		// Check the readyState before doing any waits
		if (js.executeScript(command).toString().equals("complete")) {
			return;
		}

		for (int i = 0; i < timeinSeconds; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			if (js.executeScript(command).toString().equals("complete")) {
				break;
			}
		}

	}

	public WebElement waitUntil(ExpectedCondition<?> expectedCondition,
			Class<?>... classes) {
		FluentWait<WebDriver> wait = webdriverWait.pollingEvery(1,
				TimeUnit.SECONDS).withTimeout(20, TimeUnit.SECONDS);
		if (classes != null) {
			for (Class c : classes) {
				wait.ignoring(c);
			}
		}
		return (WebElement) wait.until(expectedCondition);
	}

	public int getNumberOfErrorMessagesOnPage() {
		return getDriver().findElements(By.cssSelector(".error")).size();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		(new Actions(getDriver())).dragAndDrop(source, target).perform();
	}

	public boolean isElementExist(WebElement e) {
		boolean isPresent = false;
		try {
			isPresent = e.isDisplayed();
		} catch (NoSuchElementException s) {
			isPresent = false;
		} catch (NullPointerException npe) {
			isPresent = false;
		}
		return isPresent;
	}

	public boolean isElementNull(WebElement e) {
		boolean isNull = false;
		if (e.getText() == null) {
			isNull = true;
		}
		return isNull;
	}
	
public static String captureScreenshot(String methodName) throws IOException{
		
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int sec = cal.get(Calendar.SECOND);
		int min = cal.get(Calendar.MINUTE);
		int date = cal.get(Calendar.DATE);
		int day = cal.get(Calendar.HOUR_OF_DAY);
		String mailscreenshotpath = null;
		
		
		File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	    try {
	    	 mailscreenshotpath = System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"+methodName+"_"+year+"_"+date+"_"+(month+1)+"_"+day+"_"+min+"_" +sec+".png";
			FileUtils.copyFile(scrFile, new File(mailscreenshotpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	return mailscreenshotpath;
	}
	
	
	
	
	
	public void outlookTextReader() throws AWTException, HeadlessException, UnsupportedFlavorException, IOException {
		Robot robots = new Robot();

		robots.keyPress(KeyEvent.VK_CONTROL);
		robots.keyPress(KeyEvent.VK_A);
		robots.keyRelease(KeyEvent.VK_A);
		robots.keyRelease(KeyEvent.VK_CONTROL);

		robots.keyPress(KeyEvent.VK_CONTROL);
		robots.keyPress(KeyEvent.VK_C);
		robots.keyRelease(KeyEvent.VK_C);
		robots.keyRelease(KeyEvent.VK_CONTROL);
		
		robots.keyPress(KeyEvent.VK_ESCAPE);
		robots.keyRelease(KeyEvent.VK_ESCAPE);
		
		robots.keyPress(KeyEvent.VK_TAB);
		robots.keyRelease(KeyEvent.VK_TAB);
		
		robots.keyPress(KeyEvent.VK_ENTER);
		robots.keyRelease(KeyEvent.VK_ENTER);
		
		String value= (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(value);
		
	}

	public void robotForFileUpload(String filename) throws AWTException,
			InterruptedException {
		StringSelection ss = new StringSelection(filename);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Thread.sleep(3000);

		Robot robots = new Robot();

		robots.keyPress(KeyEvent.VK_CONTROL);

		robots.keyPress(KeyEvent.VK_V);

		robots.keyRelease(KeyEvent.VK_V);

		robots.keyRelease(KeyEvent.VK_CONTROL);

		Thread.sleep(1000);

		robots.keyPress(KeyEvent.VK_ENTER);

		robots.keyRelease(KeyEvent.VK_ENTER);

	}
	
	public boolean pressDownArrowKey () throws AWTException, InterruptedException 
	{
				Thread.sleep(500);
				Robot robots = new Robot();
				robots.keyPress(KeyEvent.VK_DOWN);
				robots.keyRelease(KeyEvent.VK_DOWN);
				Thread.sleep(500);
				return true;
	}	
	
public boolean pressEnterKey(WebElement e) 
	
	{
		if (e == null) {

			return false;
		} else {
			if (e.isDisplayed()) 
			{
				e.sendKeys(Keys.ENTER);
				return true;

			} else {
				APP_LOGS.info("FAILURE: Element not found or displayed");

				return false;
			}
		}
		
	}

	public void multiselect(WebElement e1, WebElement e2, WebElement e3,
			WebElement e4, WebElement e5, WebElement e6) {

		Actions builder = new Actions(getDriver());

		builder.keyDown(Keys.CONTROL).click(e1).keyUp(Keys.CONTROL).perform();
		builder.keyDown(Keys.CONTROL).click(e2).keyUp(Keys.CONTROL).perform();
		builder.keyDown(Keys.CONTROL).click(e3).keyUp(Keys.CONTROL).perform();
		builder.keyDown(Keys.CONTROL).click(e4).keyUp(Keys.CONTROL).perform();
		builder.keyDown(Keys.CONTROL).click(e5).keyUp(Keys.CONTROL).perform();
		builder.keyDown(Keys.CONTROL).click(e6).keyUp(Keys.CONTROL).perform();

	}

	public WebElement getElementWithDynamicXpaths(String xpathValue,
			String replaceValue1, String replaceValue2) {
		return getDriver().findElement(
				By.xpath(xpathValue.replace("x1", replaceValue1).replace("x2",
						replaceValue2)));

	}

	public boolean retryingFindClick(WebElement w) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				w.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	// for handling Lazy Loading of the web elements in the page
	public void lazyLoading(WebElement e) {

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		boolean reachedbottom = Boolean
				.parseBoolean(js
						.executeScript(
								"return $(document).height() == ($(window).height() + $(window).scrollTop());")
						.toString());

		while (!reachedbottom) {

			((JavascriptExecutor) getDriver()).executeScript(
					"window.scrollBy(0,2000)", "");
			try {
				reachedbottom = Boolean
						.parseBoolean(js
								.executeScript(
										"return $(document).height() == ($(window).height() + $(window).scrollTop());")
								.toString());
				waituntilnewElementtobeClickable(e, 5);
				clickBtn(e);
				System.out.println("Lazy loading operation successful");
				break;
			} catch (Exception ex) {
				Logger.getLogger(Test.class.getName()).log(null, ex);
				System.out.println(ex.getMessage());
				break;
			}
		}
	}

	// for handling Lazy Loading of the page
	public void lazyLoadingTillPageEnd() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		for (int second = 0;; second++) {
			if (second >= 10) {
				break;
			}
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(3000);
		}
	}

	// for handling Lazy Loading of the page
	public void scrollUpTillPageStart() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		for (int second = 0;; second++) {
			if (second >= 10) {
				break;
			}
			js.executeScript("window.scrollBy(0,-2000)", "");
			Thread.sleep(3000);
		}
	}

	/*
	 * For selecting the web elements using robot class num= position of the web
	 * element
	 */
	public void robotSelectElementUsingTabButton(int num) throws AWTException,
			InterruptedException {
		Robot robot = new Robot();
		for (int i = 1; i <= num; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(1000);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public static Alert waitForAlert(WebDriver driver, int seconds) {
		Wait<WebDriver> wait = new WebDriverWait(getDriver(), seconds)
				.ignoring(NullPointerException.class);
		return wait.until(new AlertAvailable());
	}

	private static class AlertAvailable implements ExpectedCondition<Alert> {
		public Alert apply(WebDriver driver) {
			Alert alert = getDriver().switchTo().alert();
			System.out.println(alert.getText());
			return alert;
		}
	}

	public static int generateRandomNumber(int Min, int Max) {
		return Min + (int) (Math.random() * ((Max - Min) + 1));
	}

	public static String generateRandomPhoneNumber() {
		Random rand = new Random();
		int i1 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		int i2 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		int i3 = rand.nextInt(9) * 100 + rand.nextInt(9) * 10 + rand.nextInt(9);
		DecimalFormat dc = new DecimalFormat("000");
		return dc.format(i1) + "-" + dc.format(i2) + "-" + dc.format(i3);
	}

	public static Function<WebDriver, WebElement> presenceOfElementLocated(
			final By locator) {
		return new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		};
	}

	public static String getFutureDate(int days, String format) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format);
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, days); // Adds 15 days
		return df.format(c.getTime());
	}

	public static int getLongTime() {
		return LongTime;
	}

	public static int getShortTime() {
		return shortTime;
	}

	public static int getVeryShortTime() {
		return veryShortTime;
	}

	public static int getMediumTime() {
		return mediumTime;
	}

	public static String generateRandomString() {
		int randomStringLength = 10;
		String charList = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < randomStringLength; i++) {
			int number = generateRandomNumber(1, 60);
			char ch = charList.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * This method extract the string inside the bracket. Example - Cart(11)
	 * 
	 * @param String
	 *            pStringToExtractFrom
	 * @return String
	 */
	public static String getStringInsideBracket(String pStringToExtractFrom) {
		String valueInsideBracket = "";
		Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(
				pStringToExtractFrom);
		while (m.find()) {
			valueInsideBracket = m.group(1);
		}
		return valueInsideBracket;
	}

	/**
	 * This method splits the string on a delimiter defined as:
	 * 
	 * @param ids
	 *            and delimiter
	 * @return List<String>
	 */
	public static List<String> convertDelimiterSeparatedStringToListString(
			String ids, String delimiter) {
		List<String> listStrings = new ArrayList<String>();
		String[] split = ids.split(delimiter);
		for (String string : split) {
			listStrings.add(string.trim());
		}
		return listStrings;
	}

	/**
	 * This method removes the given string from other string value
	 * 
	 * @param value
	 * @param removeString
	 * @return
	 */
	public static String removePrecedingString(String value, String removeString) {
		return value.replaceAll(removeString, "");
	}

	/**
	 * Check whether string starts with given character.
	 * 
	 * @param stringToCheck
	 * @param lettsOrDigitOrDollor
	 * @return
	 */
	public static boolean isStringStartsWith(String stringToCheck, String lettsOrDigitOrDollor) {
		boolean matchesStartingLetter = false;
		if (lettsOrDigitOrDollor != null && !"".equals(lettsOrDigitOrDollor)) {
			boolean numberMatches = lettsOrDigitOrDollor.matches("[-+]?\\d*\\.?\\d+");
			boolean dollorMatches = lettsOrDigitOrDollor.contains("$");

			if (numberMatches) {
				matchesStartingLetter = stringToCheck.matches("^[0-9].*$");
			} else if (dollorMatches) {
				matchesStartingLetter = stringToCheck.matches("^[$].*$");
			} else {
				matchesStartingLetter = stringToCheck.matches("^[A-Z].*$");
			}
		}
		return matchesStartingLetter;
	}

	public boolean checkUnCheckcheckbox(WebElement e, String checkuncheck)throws InterruptedException {
		if (checkuncheck.equalsIgnoreCase("On")) {
			if (e.isSelected()) {
				APP_LOGS.info("PASS: Check box  is already checked");
				return true;
			} else {
				clickBtn(e);
				if (e.isSelected()) {
					APP_LOGS.info("PASS: After clicking on check box,  Checkbox get checked ");
					return true;
				} else {
					APP_LOGS.info("FAIL: After clicking on check box,  Checkbox get not checked ");
					return false;
				}
			}
		} else if (checkuncheck.equalsIgnoreCase("Off")) {
			if (e.isSelected()) {
				clickBtn(e);
				if (e.isSelected()) {
					APP_LOGS.info("FAIL: After clicking on check box,  Checkbox  not getting Unchecked");
					return false;
				} else {
					APP_LOGS.info("PASS: After clicking  on check box,  Checkbox getting unchecked ");
					return true;
				}
			} else {
				APP_LOGS.info("PASS: Check box is already Unchecked");
				return true;
			}
		} else {
			APP_LOGS.info("FAIL: Enter valid value for check box i.e. ON/OFF");
			return false;
		}

	}

}