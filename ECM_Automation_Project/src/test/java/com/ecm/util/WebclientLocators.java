package com.ecm.util;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class WebclientLocators {


	//Login page

	@FindBy(how = How.XPATH, using = "//input[@name='Username']")
	public static WebElement webclientuserName;

	@FindBy(how = How.XPATH, using = "//input[@name='Password']")
	public static WebElement webclientPassword;

	@FindBy(how = How.XPATH, using = "//input[@type='SUBMIT']")
	public static WebElement normalLoginInButton;
	
	@FindBy(how = How.XPATH, using = "//button[@data-cid='login10Duke']")
	public static WebElement login10DukeButton;
	

	@FindBy(how = How.ID, using = "Username")
	public static WebElement userName;

	@FindBy(how = How.ID, using = "Password")
	public static WebElement password;

	@FindBy(how = How.ID, using ="loginbutton")
	public static WebElement signInButton;
		
	
	@FindBy(how = How.XPATH, using = "//header/atlas-tabs/ul/li/button")
	public static WebElement leftpanel;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost/descendant::button[@title='Close']")
	public static WebElement closepanel;
	
	
	//Reports page

	@FindBy(how = How.ID, using = "inLoginName")
	public static WebElement reportsUserName;

	@FindBy(how = How.ID, using = "inPassword")
	public static WebElement  reportsPassword;

	@FindBy(how = How.ID, using = "lbLogin")
	public static WebElement  reportsLogin;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Telematics Reports')]")
	public static WebElement reportsHeader;
	
	@FindBy(how = How.XPATH, using = "//a[@href='home.aspx']")
	public static WebElement homeHeader;

	@FindAll(@FindBy(how = How.XPATH, using = "//tr[@class='row']/td/a"))
	public static List<WebElement> reportsList;

	@FindBy(how = How.XPATH, using = "//select[contains(@id,'AccountSelector')]")
	public static WebElement reportsAccountSelector;

	@FindBy(how = How.XPATH, using = "//select[contains(@id,'DateRangeSelector')]")
	public static WebElement reportsDateRange;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'FromDate')]")
	public static WebElement reportsFromdate;

	@FindBy(how = How.XPATH, using = "//input[contains(@id,'ToDate')]")
	public static WebElement reportsTodate;

	@FindBy(how = How.XPATH, using = "//input[@value='View']")
	public static WebElement reportsView;

	@FindBy(how = How.XPATH, using = "//tr[@valign='top']//table[@lang='en-US']")
	public static WebElement reportsViewHeader;

	@FindBy(how = How.XPATH, using = "//span[@id='content_lMessage']")
	public static WebElement reportsEventTypeMessage;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//ul[contains(@id,'EventTypeSelector')]/li//input[@type='checkbox']"))
	public static List<WebElement> reportsEventTypes;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Event types')]")
	public static WebElement reportsEventOption;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@value,'SingleAllDay')]")
	public static WebElement reportsTimeRange;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//table[@style='border-collapse:collapse;']/tbody/tr[position()>1]"))
	public static List<WebElement> reportsColumns;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary (Non-CAN)')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]"))
	public static List<WebElement> reportsTrColumns;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary (Non-CAN)')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]/td[15]"))
	public static List<WebElement> reportsDriverPerformanceSummaryNonCANTdColumn;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary (by date)')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]/td[16]"))
	public static List<WebElement> reportsDriverPerformanceSummaryByDateTdColumn;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//tr[@valign='top'][position()>1]/td[16]"))
	public static List<WebElement> reportsDriverPerformanceSummaryByDateTdColumnNextPages;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//tr[@valign='top'][position()>1]/td[15]"))
	public static List<WebElement> reportsDriverPerformanceSummaryNonCANTdColumnNextPages;
	
	@FindBy(how = How.XPATH, using = "//a[@href='/pages/editreport.aspx?reportid=40']")
	public static WebElement reportsDriverPerformanceSummaryNonCAN;
	
	@FindBy(how = How.XPATH, using = "//a[@href='/pages/editreport.aspx?reportid=29']")
	public static WebElement reportsDriverPerformanceSummaryByDate;
	
	@FindBy(how = How.XPATH, using = "//span[contains(@id,'TotalPages')]")
	public static WebElement reportsTotalPages;
	
	@FindBy(how = How.XPATH, using = "//div[@id='rvReport_ctl06_ctl00_Next']")
	public static WebElement reportsNextButton;
	
	@FindBy(how = How.XPATH, using = "//div[@id='rvReport_ctl06_ctl00_Last_ctl00_ctl00']")
	public static WebElement reportsLastPageButton;
	
	@FindBy(how = How.XPATH, using = "//a[@href='/pages/editreport.aspx?reportid=41']")
	public static WebElement reportsDriverPerformanceSummary;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]/td[9]"))
	public static List<WebElement> reportsDriverPerformanceSummaryFirstPage;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary - by group')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]/td[10]"))
	public static List<WebElement> reportsDriverPerformanceSummarySecondPage;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//*[contains(text(),'Driver Performance Summary - by driver, asset, and group')]/../../../../../../following-sibling::tr[4]/td/table/tbody/tr[position()>2]/td[12]"))
	public static List<WebElement> reportsDriverPerformanceSummaryThirdPage;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//tr[@valign='top'][position()>1]/td[12]"))
	public static List<WebElement> reportsDriverPerformanceSummaryTdColumnNextPages;
	
	
	
	
	
	
	


	// Main Menu 

	@FindAll(@FindBy(how = How.XPATH, using = "//ul[@id='atlas-mainmenu']/li"))
	public static List<WebElement> mainmenu;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-menupane/ul/li"))
	public static List<WebElement> mainmenupane;

	@FindBy(how = How.XPATH, using = "//nav[@class='navbar navbar-default']//a[contains(text(),'Telematics')]")
	public static WebElement telematicsMainMenu;
     
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Scheduling')]")
    public static WebElement schedulingMainMenu;

    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Debug')]")
    public static WebElement debugMainMenu;
    
    @FindBy(how = How.ID, using = "atlas-profile-name")
    public static WebElement userid;
    
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Map')]")
	public static WebElement mapSubMenu;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Directions')]")
	public static WebElement directionsSubMenu;
	
	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Directions')]")
    public static WebElement directionHeading;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Asset Groups')]")
	public static WebElement assetGroupsSubMenu;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Drivers')]")
	public static WebElement driversSubMenu;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'POIs')]")
	public static WebElement poisSubMenu;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Account Reminders')]")
	public static WebElement accountRemindersSubMenu;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Timeline')]")
	public static WebElement timelineSubMenu;
	
	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Jeopardy')]")
	public static WebElement jeopardySubMenu;
	
	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Assets')]")
    public static WebElement assetHeading;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Asset Groups')]")
    public static WebElement assetGroupHeading;

    @FindBy(how = How.XPATH, using = "//h1[contains(text(),'Account Reminders')]")
    public static WebElement accountRemindersTitle;
    
    @FindBy(how = How.XPATH, using = "//h1[contains(text(),'Scheduling Timeline')]")
    public static WebElement timelineHeading;
    
    @FindBy(how = How.XPATH, using = "//h1[contains(text(),'Tasks in jeopardy')]")
    public static WebElement jeopardyHeading;

    
    
    //Telematics webclient main menu
    
    @FindBy(how = How.XPATH, using = "//li[@data-menu-name='view']")
    public static WebElement viewMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='view-assets']")
    public static WebElement assetsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Assets')]")
    public static WebElement assetsTitle;
    
    @FindBy(how = How.XPATH, using = "//a[@class='view-show-alerts']")
    public static WebElement showAlertsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Alerts')]")
    public static WebElement alertsTitle;
    
    @FindBy(how = How.XPATH, using = "//a[@class='view-drivers']")
    public static WebElement showDriversSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Drivers')]")
    public static WebElement driversTitle;
    
    @FindBy(how = How.XPATH, using = "//a[@class='view-drivers-performance']")
    public static WebElement driverPerformanceSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Driver Performance')]")
    public static WebElement driverPerformanceTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Driver Performance')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement driverPerformanceWindowCloseButton;
    
    
    
    @FindBy(how = How.XPATH, using = "//li[@data-menu-name='view']/following-sibling::li//a[contains(text(),'Reports')]")
    public static WebElement reportsMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='reports-online-reports']")
    public static WebElement onlineReportsSubMenu;
    
    
    
    @FindBy(how = How.XPATH, using = "//li[@class='poi-menu']")
    public static WebElement poiMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='view-poi']")
    public static WebElement showPOISubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'POIs')]")
    public static WebElement poiTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'POIs')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement poisWindowCloseButton;
  
    @FindBy(how = How.XPATH, using = "//a[@class='admin-poi-groups']")
    public static WebElement showPOIGroupsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'POI Groups')]")
    public static WebElement poiGroupsTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'POI Groups')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement poiGroupsWindowCloseButton;
  
    @FindBy(how = How.XPATH, using = "//a[@class='admin-poi-upload']")
    public static WebElement uploadPOIFileSubMenu;
    
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'POI File Upload')]")
    public static WebElement uploadPOITitle;
    
    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Cancel')]")
    public static WebElement uploadPOICloseButton;
    
    @FindBy(how = How.XPATH, using = "//span[contains(text(),'To Add New POI Right Click on Map.')]")
    public static WebElement addNewPOISubMenu;
    
    
  
    @FindBy(how = How.XPATH, using = "//li[@class='admin-menu']")
    public static WebElement adminMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='admin-reminders']")
    public static WebElement adminRemindersSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Reminders')]")
    public static WebElement remindersTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Reminders')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement remindersWindowCloseButton;
    
    @FindBy(how = How.XPATH, using = "//a[@class='admin-profiles']")
    public static WebElement adminProfilesSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Speed Profiles')]")
    public static WebElement speedProfilesTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Speed Profiles')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement speedProfilesWindowCloseButton;
    
    @FindBy(how = How.XPATH, using = "//a[@class='admin-assetgroups']")
    public static WebElement adminAssetgroupsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Asset Groups')]")
    public static WebElement assetGroupsTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Asset Groups')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement assetGroupsWindowCloseButton;
    
    
    
    @FindBy(how = How.XPATH, using = "//li[@class='workforce-menu']")
    public static WebElement workforceMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='workforce-show-jobs']")
    public static WebElement workforceShowJobsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Jobs')]")
    public static WebElement jobsTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Jobs')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement jobsWindowCloseButton;
    
    @FindBy(how = How.XPATH, using = "//a[@class='workforce-show-job-events']")
    public static WebElement workforceShowJobEventsSubMenu;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Job events')]")
    public static WebElement jobEventsTitle;
    
    @FindBy(how = How.XPATH, using = "//div[contains(text(),'Job events')]/preceding-sibling::div//button[@title='Close']")
    public static WebElement jobEventsWindowCloseButton;
    
    
    
    @FindBy(how = How.XPATH, using = "//li[@data-cid='map']")
    public static WebElement mapMainMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Road')]")
    public static WebElement roadSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Terrain')]")
    public static WebElement terrainSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Satellite')]")
    public static WebElement satelliteSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Hybrid')]")
    public static WebElement hybridSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Traffic')]")
    public static WebElement trafficSubMenu;
    
    
    
    @FindBy(how = How.XPATH, using = "//li[@data-menu-name='view']/following-sibling::li//a[contains(text(),'Options')]")
    public static WebElement optionsMainMenu;   
    
    @FindBy(how = How.XPATH, using = "//a[@class='options-user-preferences']")
    public static WebElement optionsUserPreferencesSubMenu;
    
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'User Preferences')]")
    public static WebElement userPreferencesTitle;
    
    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-text cancel-button']")
    public static WebElement userPreferencesWindowCloseButton;
  
    
  
    @FindBy(how = How.XPATH, using = "//li[@class='support-menu']")
    public static WebElement supportMainMenu;
    
    @FindBy(how = How.XPATH, using = "//h4[contains(text(),'Causeway Telematics - Support Portal')]")
    public static WebElement supportPortalTitle;
    
    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-text closebtn']")
    public static WebElement supportPortalWindowCloseButton;
    
  
    
    @FindBy(how = How.XPATH, using = "//li[@data-menu-name='view']/following-sibling::li//a[contains(text(),'Help')]")
    public static WebElement helpMainMenu;
  
    @FindBy(how = How.XPATH, using = "//a[@class='help-report-problem']")
    public static WebElement helpReportProblemSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[@class='help-about']")
    public static WebElement helpAboutSubMenu;
    
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Causeway Privacy Policy')]")
    public static WebElement helpAboutTitle;
    
    @FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary']")
    public static WebElement helpAboutWindowCloseButton;
    
    
    

	// Asset Tab 
    
    @FindBy(how = How.XPATH, using = "//button[@class='btn dropdown-toggle selectpicker btn-default']")
	public static WebElement assetGroupsDropDownButton;
    
    @FindBy(how = How.XPATH, using = "//input[@class='input-block-level form-control']")
   	public static WebElement assetGroupsSearchInputbox;
    
    @FindBy(how = How.XPATH, using = "//button[@class='actions-btn bs-select-all btn btn-sm btn-default']")
   	public static WebElement assetSelectAllButton;
    
    @FindBy(how = How.XPATH, using = "//button[@class='actions-btn bs-deselect-all btn btn-sm btn-default']")
   	public static WebElement assetDeSelectAllButton;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='0']//span[contains(text(),'Assets / Trailers')]")
   	public static WebElement assetsTrailersCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='1']//span[contains(text(),'Default')]")
   	public static WebElement defaultCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='2']//span[contains(text(),'Haulage Group')]")
   	public static WebElement haulageGroupCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='3']//span[contains(text(),'London')]")
   	public static WebElement londonCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='4']//span[contains(text(),'Midlands Engineers')]")
   	public static WebElement midlandsEngineersCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='5']//span[contains(text(),'Personal Trackers')]")
   	public static WebElement personalTrackersCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='6']//span[contains(text(),'South East Engineers')]")
   	public static WebElement southEastEngineersCheckbox;
    
    @FindBy(how = How.XPATH, using = "//li[@data-original-index='7']//span[contains(text(),'South West Engineers')]")
   	public static WebElement southWestEngineersCheckbox;
  
    
    
    @FindBy(how = How.XPATH, using = "//button[@class='fa fa-fw fa-sort']")
	public static WebElement assetSortButton;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//div[@data-cid='sortOptions']//label//span"))
    public static List<WebElement> assetSortOptions;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable']//li//div[@class='name']"))
    public static List<WebElement> assetsListAfterSorting;
  
  
    
    @FindBy(how = How.XPATH, using = "//input[@data-sortby='asset-name']")
	public static WebElement sortbyAssetNameButton;
    
    @FindBy(how = How.XPATH, using = "//input[@data-sortby='driver-name']")
   	public static WebElement sortbyDriverNameButton;
    
    @FindBy(how = How.XPATH, using = "//input[@data-sortby='last-update-asc']")
   	public static WebElement sortbyLastUpdateAscButton;
    
    @FindBy(how = How.XPATH, using = "//input[@data-sortby='last-update-des']")
   	public static WebElement sortbyLastUpdateDesButton;
    
  
    
    @FindBy(how = How.XPATH, using = "//button[@class='fa fa-fw fa-bars']")
	public static WebElement assetPreferencesButton;
  
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-location']")
	public static WebElement locationOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-location']//li//div[@class='asset-item']//div[@class='status location']"))
    public static List<WebElement> assetListForLocationOption;
    
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-speed-and-limit']")
	public static WebElement speedAndLimitOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-speed-and-limit']/li"))
    public static List<WebElement> assetListForSpeedAndLimitOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = " //ul[@class='asset-list-assets cards-list cards-list-selectable summary-speed-and-limit']//li//div[@class='asset-item']//div[@class='speed-and-limit']//span[@class='limit']"))
    public static List<WebElement> assetListForLimitOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = " //ul[@class='asset-list-assets cards-list cards-list-selectable summary-speed-and-limit']//li//div[@class='asset-item']//div[@class='speed-and-limit']//span[@class='speed']"))
    public static List<WebElement> assetListForSpeedOption;
   
    
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-odometer']")
	public static WebElement odometerOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-odometer']//li//div[@class='asset-item']//div[@class='odometer']"))
    public static List<WebElement> assetListForOdometerOption;
    
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-driver']")
	public static WebElement driverOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-driver']//li//div[@class='asset-item']//div[@class='driver']"))
    public static List<WebElement> assetListForDriverOption;
    
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-last-update']")
	public static WebElement lastUpdateOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-last-update']//li//div[@class='asset-item']//div[@class='time']"))
    public static List<WebElement> assetListForLastUpdateOption;
    
    @FindBy(how = How.XPATH, using = "//input[@data-toggleclass='summary-activity']")
	public static WebElement activityOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable summary-activity']//li//div[@class='asset-item']//div[@class='status activity']"))
    public static List<WebElement> assetListForSummaryActivityOption;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[contains(@class,'asset-list-assets cards-list cards-list-selectable')]//li//div[@class='asset-item']//div[@class='head']//div[@class='name']"))
    public static List<WebElement> allAssetsName;
    
    /*@FindBy(how = How.XPATH, using = "//ul[contains(@class,'asset-list-assets cards-list cards-list-selectable')]")
   	public static WebElement assetsListPanel;*/
   
    
    @FindBy(how = How.XPATH, using = "//input[@class='asset-list-filter form-control']")
	public static WebElement assetFilterInputBox;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable']/li"))
    public static List<WebElement> assetListItems;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable']/li//div[@class='name']"))
    public static List<WebElement> assetNameHeader;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable']/li[not(contains(@style, 'display: none;'))]"))
    public static List<WebElement> assetListSearchItems;
    
    @FindAll(@FindBy(how = How.XPATH, using = "//ul[@class='asset-list-assets cards-list cards-list-selectable']/li[@style= 'display: list-item;']//div[@class='name']"))
    public static List<WebElement> assetNameHeaderOfSearchItems;
    
  
    
    
  
    
    
    
    
    
  
    
  

	@FindBy(how = How.XPATH, using = "//atlas-paneltab//button[contains(text(),'Assets')]")
	public static WebElement assetButton;

	@FindBy(how = How.CLASS_NAME, using = "asset-list")
	public static WebElement telematicsassetlist;

	@FindBy(how = How.TAG_NAME, using = "telematics-assetitem")
	public static WebElement telematicsassetitem;

	@FindBy(how = How.CSS, using = ".btn-link")
	public static WebElement assetitem_button;

	@FindBy(how = How.TAG_NAME, using = "atlas-menupane")
	public static WebElement assetoptionmenupane;

	@FindBy(how = How.TAG_NAME, using = "telematics-assetspanel")
	public static WebElement telematicsassetfilter;

	
	// Asset details dialog Tabs

	@FindBy(how = How.XPATH, using = "//atlas-tabs//button[contains(text(),'Admin')]")
	public static WebElement assetMaintainDetailsAdminTab;

	@FindBy(how = How.XPATH, using = "//telematics-assetdialog//atlas-tabs//li/button[contains(text(),'Information')]")
	public static WebElement assetMaintainDetailsInformationTab;

	@FindBy(how = How.ID, using = "react-tabs-26")
	public static WebElement assetMaintainDetailsNotesTab;

	@FindBy(how = How.XPATH, using = "//atlas-tabs//button[contains(text(),'Virtual Accounts')]")
	public static WebElement assetMaintainDetailsVirtualAccountsTab;

	@FindBy(how = How.XPATH, using = "//atlas-tabs//button[contains(text(),'Reminders')]")
	public static WebElement assetMaintainDetailsRemindersTab;


	// Asset detail dialog window

	@FindBy(how = How.XPATH, using = "//atlas-standarddialog/header")
	public static WebElement assetMaintaindetailsdialog;

	@FindBy(how = How.TAG_NAME, using = "header")
	public static WebElement assetMaintaindetailsdialogheader;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='form-row clearfix']//div[@class='col-xs-9']//div[@class='col-xs-6 form-group']/label"))
	public static  List<WebElement> assetDetailsDialogToplabels;

	@FindBy(how = How.CLASS_NAME, using = "form-row clearfix")
	public static WebElement assetdetailsdialogtopform;

	@FindBy(how = How.XPATH, using = "//label/col-xs-9")
	public static WebElement assetdetailsdialoglabelcoloum;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-xs-9']//select/.."))
	public static List<WebElement> assetdetailsdialogfield;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-xs-6 form-group']"))
	public static List<WebElement> assetDetailsDialogField;

	@FindBy(how = How.XPATH, using = "//div[@class='col-xs-6 form-group']")
	public static WebElement assetsdetailsdialogfield;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-md-4 form-group']//select[@class='form-control']/.."))
	public static List<WebElement> assetdetailsdialogbottomfield;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-tabs//div[@class='col-md-4 form-group']"))
	public static List<WebElement> assetDetailsDialogBottomField;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-md-4 form-group']//div[@class='input-group-addon']/select/../../.."))
	public static List<WebElement> assetDetailsDialogBottomFields;

	@FindBy(how = How.XPATH, using = "//textarea")
	public static WebElement notesTabTextArea;

	@FindBy(how = How.CLASS_NAME, using = "ReactTabs__TabList")
	public static WebElement assetMaintaindetailsdialogTab;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetdialog//atlas-tabs//li"))
	public static List<WebElement> assetMaintainDetailsDialogTab;

	@FindAll(@FindBy(how = How.CLASS_NAME, using = "ReactTabs__TabPanel--selected"))
	public static List<WebElement> assetMaintaindetailsdialogTabLabels;

	@FindBy(how = How.XPATH, using = "//telematics-assetdialog//atlas-tabs//li[@class='selected']")
	public static WebElement assetMaintaindetailsdialogTabLabel;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-tabs//div[@class='row']//div[contains(@class,'col-md-4')]/label"))
	public static List<WebElement> assetMaintaindetailsdialogInformationTabLabels;

	@FindBy(how = How.CLASS_NAME, using = "input-group-addon")
	public static WebElement assetdetailsdialogotherfield;

	@FindBy(how = How.XPATH, using = "//div[@class='col-md-4 form-group']/div[@class='input-group']/div[@class='input-group-addon']")
	public static WebElement assetdetailsdialogoco2field;

	@FindBy(how = How.XPATH, using = "//div[@class='input-group']/div[@class='input-group-addon']/select/option")
	public static WebElement assetdetailsdialogofuelfield;

	@FindBy(how = How.XPATH, using = "//atlas-standarddialog/footer/button[@class='btn btn-default']")
	public static WebElement assetdetailsdialogcancelbutton;

	@FindBy(how = How.XPATH, using = "//atlas-dialog//button[contains(text(),'Save')]")
	public static WebElement assetDetailsDialogSaveButton;

	@FindBy(how = How.CLASS_NAME, using = "bg-danger")
	public static WebElement assetdetailsdialogerrormessage;

	@FindBy(how = How.CLASS_NAME, using = "ReactTabs__TabPanel--selected")
	public static WebElement assetdetailsdialogodometermile;

	@FindBy(how = How.CLASS_NAME, using = "form-group")
	public static WebElement assetdetailsdialogofieldlabel;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-tabs/ul/li"))
	public static List<WebElement> assetTabs;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Filter')]")
	public static WebElement filterTab;

	@FindBy(how = How.XPATH, using = "//div[@class='atlas-icon-input']//input[@type='text']")
	public static WebElement filterInputbox;


	//Admin Tab

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='form-group']/label"))
	public static List<WebElement> assetMaintainDetailsAdminTabLabels;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group']//input[@type='number']")
	public static WebElement adminTabFcal;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group']//input[@type='checkbox']")
	public static WebElement adminTabIdleEvent;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group']//select[@name='canType']")
	public static WebElement adminTabCanType;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group']//label[contains(text(),'Last CAN date')]")
	public static WebElement adminTabLatCanDate;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group']//textarea")
	public static WebElement adminTabNotes;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='form-group']//label/following-sibling::*"))
	public static List<WebElement> assetMaintainDetailsAdminFields;


	//virtual accounts Tab

	@FindAll(@FindBy(how = How.XPATH, using = "//input[@class='alloc-check-box checkbox']/../.."))
	public static List<WebElement> assetMaintainDetailsVirtualAccList;

	@FindAll(@FindBy(how = How.XPATH, using = "//input[@class='alloc-check-box checkbox']"))
	public static List<WebElement> assetMaintainDetailsVirtualAccCheckboxList;


	//group filter

	@FindBy(how = How.CLASS_NAME, using = "atlas-icon-input-control")
	public static WebElement groupFilter;

	@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']//div[@class='fa fa-fw fa-caret-down']")
	public static WebElement groupfiltericon;

	@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']")
	public static WebElement groupfilterselectdeselect;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']//ul/li"))
	public static List<WebElement> groupfilterselectdeselectbtnlink;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']/descendant::li[@class='selected']"))
	public static List<WebElement> groupfilterlistoptions;

	@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']//ul//li[@class='select-all']")
	public static WebElement groupfilterdeselectbtnlinkbtn;

	@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']/descendant::button[position()=2]")
	public static WebElement groupfilterselectbtnlinkbtn;

	@FindBy(how = How.XPATH, using = "//div/ul/li")
	public static WebElement groupfilterwhenitemdeselect;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-select[@class='atlas-icon-input-control']/descendant::button[position()>2]/.."))
	public static List<WebElement> groupListEleMents;


	//asset dialog

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='asset-list']/ul/li"))
	public static List<WebElement> telematicsAssetList;

	@FindBy(how = How.XPATH, using = "//div[@class='asset-list']/ul/li//telematics-assetitem[@class='selected']//ul//button")
	public static WebElement telematicsassetitemselected;

	@FindBy(how = How.XPATH, using = "//div[@class='asset-list']/ul/li//telematics-assetitem[@class='selected']//ul//img")
	public static WebElement telematicsAssetIconSelectedOnList;

	@FindBy(how = How.XPATH, using = "//div[@class='asset-icon-container']/img")
	public static WebElement assetIconOnDialogWindow;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-menupane//button"))
	public static List<WebElement> optionButtonSubOptions;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Sort')]")
	public static WebElement sortIcon;

	@FindBy(how = How.XPATH, using = "//section[@class='sort-options']//div[@class='fa fa-fw fa-caret-down']")
	public static WebElement sortDropDownIcon;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-text iconpicker-btn']")
	public static WebElement assetIconPicker;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-asseticonpicker//div[@class='asset-icon-container']/img"))
	public static List<WebElement> assetIcons;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-asseticonpicker//div[@class='color-switches-container']/div"))
	public static List<WebElement> assetIconColors;


	//preferences

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Preferences')]")
	public static WebElement preferencesIcon;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div[@class='fa fa-fw fa-caret-down']")
	public static WebElement preferencesDropDownIcon;

	@FindAll(@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/li"))
	public static List<WebElement> preferencesOptions;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li[@class='select-all']")
	public static  WebElement preferencesSelectAllOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Location')]")
	public static  WebElement preferencesLocationOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Speed and limit')]")
	public static  WebElement preferencesSpeedAndLimitOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Odometer')]")
	public static  WebElement preferencesOdometerOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Driver')]")
	public static  WebElement preferencesDriverOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Last update')]")
	public static  WebElement preferencesLastUpdateOption;

	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Activity bar')]")
	public static  WebElement preferencesActivityBarOption;
	
	@FindBy(how = How.XPATH, using = "//section[@class='preference-options']//div/ul/descendant::li/button[contains(text(),'Timeline')]")
	public static  WebElement preferencesTimelineOption;

	@FindBy(how = How.XPATH, using = "//section[@class='sort-options']//div[@class='fa fa-fw fa-caret-down']")
	public static WebElement fieldsIcon;

	@FindAll(@FindBy(how = How.XPATH, using = "//section[@class='sort-options']//div/ul/li"))
	public static List<WebElement> sortOptions;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='field-options']/div"))
	public static List<WebElement> fieldsOptions;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Asset name')]")
	public static WebElement sortOptionDefault;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='location']"))
	public static List<WebElement> location;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-1']/div[@class='speed-and-limit']/div[@hidden]"))
	public static List<WebElement> speedLimitHidden;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-1']/div[@class='speed-and-limit']"))
	public static List<WebElement> speedLimit;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-1']/div[@class='odometer']/div[@hidden]"))
	public static List<WebElement> odometerHidden;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-1']/div[@class='odometer']"))
	public static List<WebElement> odometer;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-2']/div[@class='driver']/div[@class='hidden']"))
	public static List<WebElement> tele_DriverHidden;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-2']/div[@class='driver']"))
	public static List<WebElement> tele_Driver;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[@class='status-2']/div[@class='time']"))
	public static List<WebElement> lastUpdate;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[5]"))
	public static List<WebElement> activityBar;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem/div[4]"))
	public static List<WebElement> timeline;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/p")
	public static WebElement noJourneysFound;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/ul/li"))
	public static List<WebElement> journeyViewItemList;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/ul/li")
	public static WebElement journeyViewItem;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/header/div[@class='form']/div[@class='input-group']/input")
	public static WebElement journeyViewFindDate;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost/header/h1")
	public static WebElement assetPanelElement;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem//div[@class='driver'][//a[@title] or //span]"))
	public static List<WebElement> driverNamesList;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetitem//h1[@title]"))
	public static List<WebElement> assetNamesList;


	// POIS 

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-poiitem"))
	public static List<WebElement> pois;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//h1[contains(text(),'POIs')]")
	public static WebElement poisHeader;	

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-select//ul/descendant::li[position()>1]"))
	public static List<WebElement> poisGroups;
	
	@FindBy(how = How.XPATH, using = "//atlas-select//div[@class='fa fa-fw fa-caret-down']")
	public static WebElement poisDropDownButton;
	
	@FindBy(how = How.XPATH, using = "//telematics-poispanel//li[@class='select-all']")
	public static WebElement poisSelectAllCheckbox;
	
	@FindBy(how = How.XPATH, using = "//telematics-poispanel//input[@placeholder='Filter']")
	public static WebElement poisFilterInputbox;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-poiitem//button[@title='Options']"))
	public static List<WebElement>poisOptionsList;
	

	//Journey View

	@FindBy(how = How.XPATH, using = "//atlas-navpanel/../../../header")
	public static WebElement journeysPanelHeader;

	@FindAll(@FindBy(how = How.XPATH, using = "//atlas-navpanel//nav//button[@title]"))
	public static List<WebElement> journeyHeaderNavPanel;

	@FindBy(how = How.XPATH, using = "//atlas-splitcontainer/atlas-splititem[position()=3]/atlas-tabbedpanelhost/header/div")
	public static WebElement journeyViewTitle;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[@class='text']")
	public static WebElement optionText;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Journey view')]")
	public static WebElement journeyViewOption;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Full day view')]")
	public static WebElement fullDayViewOption;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/header/div[@class='form']/div[@class='input-group']/div[@class='input-group-addon']")
	public static WebElement journeyViewDateTab;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='dd/mm/yyyy']")
	public static WebElement journeyViewDateField;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Find')]")
	public static WebElement journeyViewFind;

	@FindBy(how = How.XPATH, using = "//telematics-journeyitem//header")
	public static WebElement fullDayViewJourneyItemHeader;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel/ul//li[1]//telematics-journeyitem//header")
	public static WebElement JourneyItemHeader;

	@FindBy(how = How.XPATH, using = "//telematics-journeyspanel//button[@title='Options']")
	public static WebElement journeyspanelOptionButton;

	@FindBy(how = How.XPATH, using = "//atlas-splititem/footer/ul/li[@class='active']/div")
	public static WebElement journeyViewFooterTab;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-journeyitem//li[@class='start']/div[2]"))
	public static List<WebElement> journeyItemsStart;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-journeyitem//li[@class='end']/div[2]"))
	public static List<WebElement> journeyItemsEnd;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-journeyitem//footer"))
	public static List<WebElement> journeyItemsFooter;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-journeyitem//button"))
	public static List<WebElement> journeyItemsPlayButtons;

	@FindBy(how = How.XPATH, using = "//telematics-journeyitem//button")
	public static WebElement journeyItemsPlayButtonForFullDayView;

	@FindBy(how = How.XPATH, using = "//telematics-journeyitem//footer")
	public static WebElement journeyItemsFooterForFullDayView;

	@FindBy(how = How.XPATH, using = "//atlas-navpanel//button[@title='Back']")
	public static WebElement journeyBackButton;

	@FindBy(how = How.XPATH, using = "//atlas-navpanel//button[@title='Home']")
	public static WebElement journeyHomeButton;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='header']")
	public static WebElement journeyPlaybackHeader;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='datetimerange']/span[1]")
	public static WebElement journeyPlaybackDate;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='datetimerange']/span[2]")
	public static WebElement journeyPlaybackTime;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//p[@class='currentlocation']")
	public static WebElement journeyPlaybackLocation;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='speed-and-limit']/span")
	public static WebElement journeyPlaybackSpeed;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='headingicon']")
	public static WebElement journeyPlaybackDirection;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='speed-and-limit']")
	public static WebElement journeyPlaybackSpeedLimitAndDirection;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='statustext']")
	public static WebElement journeyPlaybackStatusText;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='controls']//button[@title='Rewind']")
	public static WebElement journeyPlaybackRewindButton;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='controls']//button[@title='Play/Pause']")
	public static WebElement journeyPlaybackPlayPauseButton;

	@FindBy(how = How.XPATH, using = "//telematics-journeyplayback//div[@class='controls']//button[@title='Forward']")
	public static WebElement journeyPlaybackForwardButton;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//header//h1[contains(text(),'Journeys')]/..//button[@title='Close']")
	public static WebElement journeyPanelCloseButton;

	@FindBy(how = How.XPATH, using = "//div[@class='streetview']//img")
	public static WebElement journeyPlaybackStreetView;


	//Nearest assets

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//h1[contains(text(),'Nearest Assets')]")
	public static WebElement nearestAssetsPanelHeader;

	@FindBy(how = How.XPATH, using = "//telematics-nearestassetspanel//input[@type='text']")
	public static WebElement nearestAssetsPanelAssetNameField;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-nearestassetitem//header//div[@class='name']"))
	public static List<WebElement> nearestAssetsPanelAssetNames;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-nearestassetitem//header//div[@class='rank']"))
	public static List<WebElement> nearestAssetsPanelAssetRanks;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-nearestassetitem//div[@class='location']"))
	public static List<WebElement> nearestAssetsPanelAssetLocations;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-nearestassetitem//div[@class='geodesic-distance']"))
	public static List<WebElement> nearestAssetsPanelDistance;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//header//h1[contains(text(),'Nearest Assets')]/..//button[@title='Close']")
	public static WebElement nearestAssetsPanelCloseButton;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-nearestassetitem"))
	public static List<WebElement> nearestAssetItems;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//maps-directionspanel//div[@class='travel-modes']/div/button"))
	public static List<WebElement> travelModes;
	
	@FindBy(how = How.XPATH, using = "//maps-directionspanel//button[@class='btn btn-default']")
	public static WebElement directionsPanelRouteButton;
	
	@FindBy(how = How.XPATH, using = "//maps-directionspanel//p[@class='bg-info']")
	public static WebElement directionsPanelInfo;


	//Asset Groups

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//h1[contains(text(),'Asset Groups')]")
	public static WebElement assetGroupHeader;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetgroupitem"))
	public static List<WebElement> assetGroupsList;

	@FindBy(how = How.XPATH, using = "//telematics-assetgroupspanel//input[@placeholder='Filter']")
	public static WebElement assetGroupFilterInputbox;

	@FindBy(how = How.XPATH, using = "//telematics-assetgroupspanel//button[@class='btn btn-link']")
	public static WebElement createNewGroupButton;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-assetgroupspanel//button[@title='Options']"))
	public static List<WebElement> assetGroupsOptionsList;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Delete')]")
	public static WebElement assetGroupDeleteButton;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Edit')]")
	public static WebElement assetGroupEditButton;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//header//h1[contains(text(),'Asset Groups')]/..//button[@title='Close']")
	public static WebElement assetGroupsPanelCloseButton;

	//Account Reminders

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//h1[contains(text(),'Account Reminders')]")
	public static WebElement accountRemindersGroupHeader;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-accountreminderitem"))
	public static List<WebElement> accountRemindersList;

	@FindBy(how = How.XPATH, using = "//telematics-accountreminderspanel//input[@placeholder='Filter']")
	public static WebElement accountRemindersFilterInputbox;

	@FindBy(how = How.XPATH, using = "//telematics-accountreminderspanel//button[@class='btn btn-link']")
	public static WebElement createNewReminderTypeButton;

	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-accountreminderspanel//button[@title='Options']"))
	public static List<WebElement>accountRemindersOptionsList;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Delete')]")
	public static WebElement accountRemindersDeleteButton;

	@FindBy(how = How.XPATH, using = "//atlas-menupane//div[contains(text(),'Edit')]")
	public static WebElement accountRemindersEditButton;

	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//header//h1[contains(text(),'Account Reminders')]/..//button[@title='Close']")
	public static WebElement accountRemindersPanelCloseButton;


	//Drivers
	
	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-driveritem"))
	public static List<WebElement>driversList;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//telematics-driverspanel//button[@title='Options']"))
	public static List<WebElement>driversOptionsList;
	
	@FindBy(how = How.XPATH, using = "//telematics-driverspanel//input[@placeholder='Filter']")
	public static WebElement driversFilterInputbox;
	
	@FindBy(how = How.XPATH, using = "//atlas-tabbedpanelhost//header//h1[contains(text(),'Drivers')]/..//button[@title='Close']")
	public static WebElement driversPanelCloseButton;
	
	@FindBy(how = How.XPATH, using = "//telematics-driverspanel//span[@class='fa fa-fw fa-plus']")
	public static WebElement createNewDriverButton;

	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-xs-9']//div[@class='row']//*[@class='form-control selectpicker' or @class='form-control']"))
	public static List<WebElement>driversLabelsList;
	
	@FindAll(@FindBy(how = How.XPATH, using = "//div[@class='col-xs-9']//div[@class='row']//label"))
	public static List<WebElement>driversFieldsList;
	
	@FindBy(how = How.XPATH, using = "//atlas-dialog//button[@class='btn btn-primary']")
	public static WebElement driverDialogSaveButton;
	
	@FindBy(how = How.XPATH, using = "//atlas-dialog//button[@class='btn btn-default']")
	public static WebElement driverDialogCancelButton;
	
	@FindBy(how = How.XPATH, using = "//telematics-driveritem//img")
	public static WebElement driverImageOnDriversPanel;
	
	@FindBy(how = How.XPATH, using = "//div[@class='col-xs-3 form-group image-container']/img")
	public static WebElement driverImageOnDriversDetailsDialog;
	



}
