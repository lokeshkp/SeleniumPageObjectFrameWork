package com.ecm.util;

import java.util.Hashtable;

import org.testng.SkipException;

public class ExcelUtils {
	
	
	
	public static boolean isTestExecutable(String testCaseName, ExcelReader excel){
		
		String sheetName  = ExcelConstants.TEST_CASE_SHEET;
		
		int rows = excel.getRowCount(sheetName);
		
		for(int rowNum=2; rowNum<=rows; rowNum++){
			
			String testCase = excel.getCellData(sheetName, ExcelConstants.TEST_CASE_COL, rowNum);
			
			if(testCase.equalsIgnoreCase(testCaseName)){
				
				String runMode = excel.getCellData(sheetName, ExcelConstants.TEST_RUNMODE_COL, rowNum);
				if(runMode.equalsIgnoreCase(ExcelConstants.RUNMODE_YES)){
					
					return true;
				}else{
					
					return false;
				}
				
			}
			
			
		}
		return false;

		
		
	}
	
	public static boolean isSuiteExecutable(String suiteName){
		
		ExcelReader excel = new ExcelReader(ExcelConstants.TEST_SUITE_XLS_PATH);
		String sheetName = ExcelConstants.TEST_SUITE_SHEET;
		int rows = excel.getRowCount(sheetName);
		
		for(int rowNum=2; rowNum<=rows; rowNum++){
			
			String testSuiteName = excel.getCellData(sheetName, ExcelConstants.TEST_SUITE_COL, rowNum);
			
			if(testSuiteName.equalsIgnoreCase(suiteName)){
				
				String runMode = excel.getCellData(sheetName, ExcelConstants.SUITE_RUNMODE_COL, rowNum);
				if(runMode.equalsIgnoreCase(ExcelConstants.RUNMODE_YES)){
					
					return true;
				}else{
					
					return false;
				}
				
			}
			
			
		}
		return false;
	}
	
	
	public static void checkExecution(String testSuiteName, String testCaseName, String dataRunMode, ExcelReader excel){
		
		if(!isSuiteExecutable(testSuiteName)){			
			throw new SkipException("Skipping the Test : "+testCaseName+" as the Runmode of Test Suite "+testSuiteName+" is No");
		}
		
		if(!isTestExecutable(testCaseName,excel)){			
			throw new SkipException("Skipping the Test : "+testCaseName+" as the Runmode of Test Case "+testCaseName+" is No");
		}
		
		if(dataRunMode.equalsIgnoreCase("N")){			
			throw new SkipException("Skipping the Test : "+testCaseName+" as the Runmode of Test Data is No");
		}		
	}
	
	public static Object[][] getData(String testcase, ExcelReader excel){
		
			String sheetName = "TestData";
			String testCase = testcase;
			//Test case start from
			
			int testCaseRowNum=1;
			
			while(!excel.getCellData(sheetName, 0, testCaseRowNum).equalsIgnoreCase(testCase)){				
				
				testCaseRowNum++;
			}
			
			System.out.println("Test case starts from : "+testCaseRowNum);			
			
			//total cols & rows - cols starts and test data starts from			
			
			int colsStartRowNum=testCaseRowNum+1;
			int dataStartRowNum=colsStartRowNum+1;			
			
			//total cols in test are			
			int cols=0;
			
			while(!excel.getCellData(sheetName, cols, colsStartRowNum).trim().equals("")){				
				cols++;
				
			}
			
			System.out.println("Total cols in a test are : "+cols);			
			
			//total rows in a test are
			
			int rows=0;
			
			while(!excel.getCellData(sheetName, 0, dataStartRowNum+rows).trim().equals("")){
				
				rows++;
				
			}

			System.out.println("Total rows are : "+rows);
			
			//printing the test data
			Object[][] data = new Object[rows][1];
			int i=0;
			for(int row=dataStartRowNum; row<dataStartRowNum+rows;row++){
				Hashtable<String, String> table =new Hashtable<String,String>();
				for(int col=0;col<cols;col++){					
					
					//System.out.println(excel.getCellData(sheetName, col, row));
					String Testdata = excel.getCellData(sheetName, col, row);
					String colName = excel.getCellData(sheetName, col, colsStartRowNum);
					table.put(colName, Testdata);	
				
				}
				
				data[i][0] = table;
				i++;
			}
			
			return data;
	}

}
