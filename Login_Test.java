package com.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.util.Components;
import com.test.util.Utilities;


public class Login_Test extends Components {

	
	@DataProvider(name = "Tests")
	/*public Object[][] loginData() throws Exception{
		Object[][] retObjArr=Utilities.getTableArray(Utilities.FILE_NAME,"LoginData", "LoginData");
        return(retObjArr);*/
	public Object[][] loginData() throws Exception{
		Object[][] retObjArr=Utilities.getTableArray("E:/Performance/Automation workspace/AngularWithJava/WebContent/ReferenceFiles/Data/DataFeeder.xls","LoginData", "LoginData");
        return(retObjArr);
    
	
    }

	
	
	//@Test(dataProvider = "LoginData", groups = {"Data-Driven Tests"}, description = "Login to Sensei web app")
	@Test(dataProvider = "Tests", groups = {"Tests to be executed"}, description = "Login to Sensei web app")
	public void testLogin(String Uname, String pwd, WebDriver selenium) throws Exception {
	
	{
		//getPage("ANHomePage",selenium);
		//enterValue("Username",Uname,selenium);
		//enterValue("Password",pwd,selenium);
		//clickAndVerify("LoginButton","<Page Title>",selenium);
		//clickAndWait("Signout",selenium);
		enterValue("Search",Uname,selenium);
		System.out.println("In Login_Test" +Uname);
		//enterValue("SenseiPassword",pwd,selenium);
		wait(3000);
	}
}
}
