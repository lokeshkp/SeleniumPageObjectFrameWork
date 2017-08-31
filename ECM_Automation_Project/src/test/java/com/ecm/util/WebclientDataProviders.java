package com.ecm.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class WebclientDataProviders {
	
	@DataProvider(name="webServicesList")
	public static Object[][] webServicesList(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBSERVICES_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="VerifyingDriverDetails")
	public static Object[][] VerifyingDriverDetails(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="VerifyListOfReports")
	public static Object[][] VerifyListOfReports(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="VerifyDriverPerformanceSummaryByDateReport")
	public static Object[][] VerifyDriverPerformanceSummaryByDateReport(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="VerifyDriverPerformanceSummaryNonCANReport")
	public static Object[][] VerifyDriverPerformanceSummaryNonCANReport(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="VerifyDriverPerformanceSummaryReport")
	public static Object[][] VerifyDriverPerformanceSummaryReport(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	
	@DataProvider(name="VerifyingDriverEditDetails")
	public static Object[][] VerifyingDriverEditDetails(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	@DataProvider(name="verifyingjourneyview")
	public static Object[][] verifyingjourneyview(Method m){
		
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
		
		String testcase = m.getName();
		
		return ExcelUtils.getData(testcase, excel);
		
	}
	
	 @DataProvider(name ="AssetPanelTabsData")
	    public static Object[][] AssetPanelTabsData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	 
	 @DataProvider(name ="NearestassetsTestData")
	    public static Object[][] NearestassetsTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	
	  @DataProvider(name ="AssetMaintainDetailsTestData")
	    public static Object[][] AssetMaintainDetailsTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="AssetMaintaindetailsFuelClassTestData")
	    public static Object[][] AssetMaintaindetailsFuelClassData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="AssetMaintainDetailsCancelButtonTestData")
	    public static Object[][] AssetMaintainDetailsCancelButtonTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="AssetMaintainDetailsSaveButtonTestData")
	    public static Object[][] AssetMaintainDetailsSaveButtonTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="AssetDetailsMandatoryFieldTestData")
	    public static Object[][] AssetDetailsMandatoryFieldTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="AssetOptionButtonsSubOptions")
	    public static Object[][] AssetOptionButtonsSubOptions(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  
	  @DataProvider(name ="AdminTabData")
	    public static Object[][] AdminTabData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  @DataProvider(name ="VirtualAccountsTabData")
	    public static Object[][] VirtualAccountsTabData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	 
	  @DataProvider(name ="AssetIconVerificationTestData")
	    public static Object[][] AssetIconVerificationTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  @DataProvider(name ="FilterFunctionalityForAssetGroupsTestData")
	    public static Object[][] FilterFunctionalityForAssetGroupsTestData (Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="FilterFunctionalityForAccountRemindersTestData")
	    public static Object[][] FilterFunctionalityForAccountRemindersTestData (Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	
	  @DataProvider(name ="FilterFunctionalityForPOISTestData")
	    public static Object[][] FilterFunctionalityForPOISTestData (Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="AssetIconPickerVerificationTestData")
	    public static Object[][] AssetIconPickerVerificationTestData (Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  
	  @DataProvider(name ="FilterFunctionalityUsingAssetDriverIMEITestData")
	    public static Object[][] FilterFunctionalityUsingAssetDriverIMEITestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	
	  @DataProvider(name ="GroupfilterSelectDeselectTestData")
	    public static Object[][] GroupfilterSelectDeselectData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="SortoptionsTestData")
	    public static Object[][] SortoptionsData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="SortAssetPanelByOptionTestData")
	    public static Object[][] SortAssetPanelByOptionTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="PreferencesOptionTestData")
	    public static Object[][] PreferencesOptionTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="FieldOptionForAssetPanelTestData")
	    public static Object[][] FieldOptionForAssetPanelTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  @DataProvider(name ="JourneysPanelTestData")
	    public static Object[][] JourneysPanelTestData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="JourneyViewTestData")
	    public static Object[][] JourneyViewTestData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="FullDayViewTestData")
	    public static Object[][] FullDayViewTestData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="JourneyPlaybackFunctionalityTestData")
	    public static Object[][] JourneyPlaybackFunctionalityTestData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_JOURNEYSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  
	  @DataProvider(name ="POISPanelTestData")
	    public static Object[][] POISPanelData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="MainMenuTestData")
	    public static Object[][] MainMenuTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  
	  @DataProvider(name ="TelematicsMenuOptionTestData")
	    public static Object[][] TelematicsMenuOptionTestData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="VixenMenuOptionTestData")
	    public static Object[][] VixenMenuoptionData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="MapMenuOptionTestData")
	    public static Object[][] MapMenuOptionTestData(Method m) throws Exception{
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
}
	  @DataProvider(name ="SchedulingMenuOptionTestData")
	  public static Object[][] SchedulingMenuoptionData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
	  }
	  @DataProvider(name ="DebugMenuOptionTestData")
	  public static Object[][] DebugMenuoptionData(Method m) throws Exception{
	
		  System.out.println(m.getName());
			ExcelReader excel = new ExcelReader(ExcelConstants.WEBCLIENT_ASSETSUITE_XL_PATH);
			
			String testcase = m.getName();
			
			return ExcelUtils.getData(testcase, excel);
	  }

}
