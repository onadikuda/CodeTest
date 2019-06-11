package com.test;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import com.test.util.Components;
import com.test.util.Utilities;



public class Test_Registration extends Components {
	
		//AllTests AT = new AllTests();
		
		/**
		 * @regData - This method provides data for
		 * registering new users
		 */
		@DataProvider(name = "RegData")
		public Object[][] regData() throws Exception{
	        Object[][] retObjArr=Utilities.getTableArray(Utilities.FILE_NAME, "RegData", "RegData");
	        return(retObjArr);
	    }

		 /**
		  *@testRegistration method that receives data from this DataProvider 
		  *
		  * @param Uname username of the user
		  * @param fullname Full name of the user
		  * @param age Age of the user
		  * @param pwd Desired password required for login
		  * @param email E-mail that is required for registration
		  * @exception throws an exception if any field is not entered
		 */
	    @Test(dataProvider="RegData", groups = {"Data-Driven Tests"}, description = "Registering New User on AN")
		public void testRegistration(String Uname, String pwd, String email,String dob,WebDriver selenium) throws Exception {
			//getPage("ANHomePage", selenium);
			System.out.println("Register Prakash");
			wait(3000);
			enterValue("RegUserName", Uname,selenium);
			enterValue("RegPassword", pwd,selenium);
			enterValue("Email", email,selenium);
			//select("RegDOBYear",dob.split("-")[0],selenium);
			//select("RegDOBMonth","value="+dob.split("-")[1],selenium);
			//select("RegDOBDay",dob.split("-")[2],selenium);
			//click("Register",selenium);		
			
			clickAndWait("RegisterButton",selenium);
			//verifyTrue(selenium.isTextPresent("You are registered successfully."));
		}
	    
	   @DataProvider(name = "Searching")
		public String[][] GetSearchData() throws Exception{
	    	try {
		   System.out.println("TestData location:" +Utilities.FILE_NAME);
		   String[][] retObjArr=Utilities.getTableArray(Utilities.FILE_NAME, "SearchList", "SearchList");
		   return(retObjArr);
	    }
	    
	    	catch(Exception e){
	    		e.printStackTrace();
	    		}
			return null;
	    	}

	    public Object SearchText(String comp, String checklist, WebDriver selenium) throws Exception {
	    	
	    	try {
	    	String[][] InpArr=this.GetSearchData();
	    	String[] OutputArray; 
	    	for (int i=0;i<InpArr.length;i++){	    		
	    		OutputArray = InpArr[i] ;
	    	enterSetOfValues("Search",OutputArray,selenium);
	    		}
	    	System.out.println("Retrieve Testdata From XLS completed");
	    	}
	    	
	    	catch (NullPointerException e) {
	    		System.out.println("Not able to retrieve data");
	    	}
	    	
	    	return null;
			
		}
	    
	}

