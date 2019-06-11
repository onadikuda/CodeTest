package com.test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.util.Components;
import com.test.util.HashMapReference;
import com.test.util.XMLTransformation;

/*@Listeners({ com.test.util.TestAnnotationTransformerListener.class, org.uncommons.reportng.HTMLReporter.class,
		org.uncommons.reportng.JUnitXMLReporter.class, com.test.util.TestNGCustomReportListener.class })*/
//com.test.util.TestNGEmailableReport2.class

@Listeners({org.uncommons.reportng.HTMLReporter.class,
		org.uncommons.reportng.JUnitXMLReporter.class })
public class AllTest extends Components {

	public final static Logger LOGGER = LogManager.getLogger(com.test.AllTest.class);
	Date date = new Date();
	private static final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	Login_Test TL = new Login_Test();
	Test_Registration TR = new Test_Registration();
	public String Checklist = null;
	public static final String TIMEOUT = "120000";
	public String HostName;
	public int Port;
	public String URL;
	XMLTransformation XmlTrans = new XMLTransformation();
	public String Browser;
	// public static WebDriver seleniumFF;
	public static WebDriver seleniumIE;
	public static WebDriver seleniumGchrome;

	/*
	 * public Selenium seleniumFF; public Selenium seleniumSafari; public Selenium
	 * seleniumFF8;
	 */
	@Parameters({ "HostName", "Port", "Browser", "URL" })
	@BeforeSuite(groups = { "demo",
			"multiEnvironment" }, description = "Show off Selenium Grid the IE Web Browser on Window.")
	public void SetDriversAndLaunch(String HostName1, int Port1, String Browser1, String URL1) throws Exception {
		// LOGGER.info("Going to initialize Page Header.");
		HostName = HostName1;
		Port = Port1;
		URL = URL1;
		Browser = Browser1;
		System.out.println("HOSTNAME is: " + HostName + "PORT is: " + Port + "BROWSER SELECTED is: " + Browser);
		LOGGER.info("Launched TestNG Class");
		try {
			switch (Browser) {
			case "*iexplorer":
				File file = new File("E://Software//IEDriver//IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				seleniumIE = new InternetExplorerDriver();
				EventFiringWebDriver driver = new EventFiringWebDriver(seleniumIE);
				driver.manage().window().maximize();
				HashMapReference.loadObjs();
				seleniumIE.get(URL);
				new WebDriverWait(seleniumIE, 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
						.executeScript("return document.readyState").equals("complete"));
				break;
			case "*chrome":
				System.setProperty("webdriver.chrome.driver", "E:\\Software\\ChromeDriver\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				seleniumGchrome = new ChromeDriver(options);
				HashMapReference.loadObjs();
				seleniumGchrome.get(URL);
				new WebDriverWait(seleniumGchrome, 30)
						.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
								.executeScript("return document.readyState").equals("complete"));
				break;
			}
		}
		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	/**
	 * Provides user selected test cases or which are part of TesttoExecute.xml
	 */
	@DataProvider(name = "Tests")
	public static Object[][] TestCaseLoader() throws Exception {
		Object[][] retObjArr1 = XMLTransformation.parseTestsToExecute();
		return (retObjArr1);
	}

	@Test(dataProvider = "Tests", description = "Execution of testCases are handled using this function...")
	public void Init_WebAction(String Test) throws Exception {
		switch (Browser) {
		case "*iexplorer":
			ParseDetails(Test, seleniumIE);
			break;
		case "*chrome":
			ParseDetails(Test, seleniumGchrome);
			break;
		}
	}

	public void ParseDetails(String Test, WebDriver selenium) throws Exception {
		if (Test != null) {
			if (Browser.compareToIgnoreCase("*iexplorer") == 0) {
				System.out.println("Time:" + df.format(date) +"||"+ "To ParseDetails for IE" + selenium);
				//String[] TestsIE = null;
				// System.out.println("Content in TestsIE[] before ParseTestDetails for IE:" + Arrays.toString(TestsIE));
				String[] TestsIE = XmlTrans.parseTestDetails(Test);
				// System.out.println("Content in TestDetails for IE:" + Arrays.toString(TestsIE));
				// System.out.println("Time" + df.format(date) + "AfterParseDetails for IE" +
				// selenium);
				for (int s = 0; s < TestsIE.length; s++) {
					if (TestsIE[s] != null) {
						String StepsIE = TestsIE[s];
						// System.out.println("TestDetail to be send for ParseTestSteps" + StepsIE);
						String[][] TestDataIE = XMLTransformation.parseTestSteps(StepsIE);
						// System.out.println("Time" + df.format(date) + "AfterParseTestSteps for IE" +
						// selenium + "TestSteps which are parsed" +TestDataIE);
						ParseSteps(TestDataIE, seleniumIE);
					}
					TestsIE[s] = null;
				}
			}

			else if (Browser.compareToIgnoreCase("*chrome") == 0) {
				System.out.println("Time:" + df.format(date) +"||"+ "To ParseDetails for chrome" + selenium);
				//String[] TestsGC = null;
				String[] TestsGC = XmlTrans.parseTestDetails(Test);
				// System.out.println("Content in TestDetails for Chrome:" +
				// Arrays.toString(TestsGC));

				for (int s = 0; s < TestsGC.length; s++) {
					if (TestsGC[s] != null) {
						String StepsGC = TestsGC[s];
						// System.out.println("Time" + df.format(date) + "AfterParseDetails for chrome"
						// + selenium+ "TestSteps which are parsed from TestDetails" +
						// TestsGC.toString());
						String[][] TestDataGC = XMLTransformation.parseTestSteps(StepsGC);
						// System.out.println("Time" + df.format(date) + "AfterParseTestSteps for
						// chrome" + selenium + "TestSteps which are parsed" +TestDataGC.length);
						ParseSteps(TestDataGC, seleniumGchrome);
					}
					TestsGC[s] = null;
				}
			}
		}

		else {
			System.out.println("*******************NULLLLL*****************");
		}

	}

	public void ParseSteps(String[][] TestData, WebDriver selenium) throws Exception {
		System.out.println("Time:" + df.format(date) + "In ParseSteps For Browser" + selenium);
		String[][] TestDataFinal = new String[2000][2000];
		String value1 = null;
		String value2 = null;
		String value3 = null;
		int j = 0;
		for (int i = 0; i < TestData.length; i++) {

			if (TestData[i][0] != null) {
				value1 = TestData[i][0];
				System.out.println("Time:" + df.format(date) +"||" + "Value1:" + value1);
			}
			if (TestData[i][1] != null) {
				value2 = TestData[i][1];
				System.out.println("Time:" + df.format(date) +"||"+ "Value2:" + value2);
			}
			if (TestData[i][2] != null) {
				value3 = TestData[i][2];
				System.out.println("Time:" + df.format(date) +"||"+ "Value3:" + value3);
			}
			Integer i3value = ((i + 1) % 3);
			if (i3value.equals(0)) {
				TestDataFinal[j][0] = value1;
				TestDataFinal[j][1] = value2;
				TestDataFinal[j][2] = value3;
				j++;

				value1 = value2 = value3 = null;

			}

			TestData[i][0] = null;
			TestData[i][1] = null;
			TestData[i][2] = null;
		}

		if (Browser.compareToIgnoreCase("*iexplorer") == 0) {
			// System.out.println("Time" + df.format(date) + "ParseSteps for IE" +
			// selenium);
			String[][] TestDataFinalIE = new String[2000][2000];
			for (int k = 0; k < TestDataFinal.length; k++) {
				TestDataFinalIE[k][0] = TestDataFinal[k][0];
				TestDataFinalIE[k][1] = TestDataFinal[k][1];
				TestDataFinalIE[k][2] = TestDataFinal[k][2];

				TestDataFinal[k][0] = null;
				TestDataFinal[k][1] = null;
				TestDataFinal[k][2] = null;
			}

			for (int k = 0; k < TestDataFinalIE.length; k++) {
				String Component = TestDataFinalIE[k][0];
				String CompType = HashMapReference.getComp(Component);
				String Action = TestDataFinalIE[k][1];
				String Expected = TestDataFinalIE[k][2];
				ExecuteSteps(Component, CompType, Action, Expected, selenium);

				TestDataFinalIE[k][0] = null;
				TestDataFinalIE[k][1] = null;
				TestDataFinalIE[k][2] = null;

			}
		}

		if (Browser.compareToIgnoreCase("*chrome") == 0) {

//			System.out.println("Time" + df.format(date) + "ParseSteps for Chrome" + selenium);
			String[][] TestDataFinalGC = new String[2000][2000];
			for (int k = 0; k < TestDataFinal.length; k++) {
				TestDataFinalGC[k][0] = TestDataFinal[k][0];
				TestDataFinalGC[k][1] = TestDataFinal[k][1];
				TestDataFinalGC[k][2] = TestDataFinal[k][2];

				TestDataFinal[k][0] = null;
				TestDataFinal[k][1] = null;
				TestDataFinal[k][2] = null;
			}

			for (int k = 0; k < TestDataFinalGC.length; k++) {

				String Component = TestDataFinalGC[k][0];
				String CompType = HashMapReference.getComp(Component);
				String Action = TestDataFinalGC[k][1];
				String Expected = TestDataFinalGC[k][2];
				ExecuteSteps(Component, CompType, Action, Expected, selenium);

				TestDataFinalGC[k][0] = null;
				TestDataFinalGC[k][1] = null;
				TestDataFinalGC[k][2] = null;

			}
		}
	}

	public void ExecuteSteps(String Component, String CompType, String Action, String Expected, WebDriver selenium)
			throws Exception {

		// System.out.println("At Start of ExecuteSteps()" + selenium);

		/*
		 * String Component = TestDataFinal[k][0] ; String CompType =
		 * HashMapReference.getComp(Component); String Action = TestDataFinal[k][1];
		 * String Expected= TestDataFinal[k][2];
		 */
		if (Component != null && Action != null && Expected != null) {

			if ((CompType.compareToIgnoreCase("Window") == 0) && (Action.compareToIgnoreCase("Open") == 0)) {
				getPage(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("clickAndVerify") == 0)) {
				clickAndVerify(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("clickAndVerifyAlert") == 0)) {
				clickAndVerifyAlert(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("ClickAndWait") == 0)) {
				clickAndWait(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("ClickAndNav") == 0)) {
				clickAndNav(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("ButtonWithLink") == 0) && (Action.compareToIgnoreCase("ClickAndWait") == 0)) {
				CopyLink(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("Wait") == 0)) {
				wait(3000);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("MoveBack") == 0)) {
				NavigateBack(selenium);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("Paste") == 0)) {
				PastefromClipBoard(selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("clickAndSwitch") == 0)) {
				clickAndSwitch(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("Click") == 0)) {
				click(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("PauseButton") == 0) && (Action.compareToIgnoreCase("Pause") == 0)) {
				PauseVideo(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0) && (Action.compareToIgnoreCase("Enter") == 0)) {
				SearchAndEnter(Component, Expected, selenium);
				takeSnapShot(selenium);
			} else if ((CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("MouseOver") == 0)) {
				MouseOver(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0) && (Action.compareToIgnoreCase("Click") == 0)) {
				click(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0)	&& (Action.compareToIgnoreCase("EnterValue") == 0)) {
				enterValue(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0)	&& (Action.compareToIgnoreCase("EnterSearchDetails") == 0)) {
				enterDetails(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0)	&& (Action.compareToIgnoreCase("EnterDetails") == 0)) {
				enterValue(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0)	&& (Action.compareToIgnoreCase("EnterSetOfValues") == 0)) {
				TR.SearchText(Component, Checklist, selenium);
				
			} else if ((CompType.compareToIgnoreCase("TextField") == 0 || CompType.compareToIgnoreCase("Button") == 0) && (Action.compareToIgnoreCase("VerifyValue") == 0)) {
				verifyValue(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("VerifyText") == 0)) {
				verifyText(Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Text") == 0) && (Action.compareToIgnoreCase("VerifyText") == 0)) {
				verifyText(Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Text") == 0) && (Action.compareToIgnoreCase("Scroll") == 0)) {
				Scroll(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("VerifyElement") == 0)) {
				verifyElement(Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Checkbox") == 0) && (Action.compareToIgnoreCase("VerifyChecked") == 0)) {
				verifyChecked(Component, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Checkbox") == 0 || CompType.compareToIgnoreCase("Radiobutton") == 0)	&& (Action.compareToIgnoreCase("Check") == 0)) {
				check(Component, true, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Checkbox") == 0)	&& (Action.compareToIgnoreCase("Uncheck") == 0)) {
				uncheck(Component, false, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Dropdown") == 0) && (Action.compareToIgnoreCase("Select") == 0)) {
				select(Component, Expected, selenium);
				
			} else if ((CompType.compareToIgnoreCase("Alert") == 0) && (Action.compareToIgnoreCase("Activate") == 0)) {
				selectPopUp(Component);
				
			} else if ((CompType.compareToIgnoreCase("NA") == 0) && (Action.compareToIgnoreCase("close") == 0)) {
				close(selenium);
			}
		}
	}

	@AfterSuite(alwaysRun = true)
	public void stopSelenium() {
		if (Browser.compareToIgnoreCase("*iexplorer") == 0) {
			System.out.println(
					"In AfterSuite for IE Thread Id:- " + Thread.currentThread().getId() + "Time:" + df.format(date));
			seleniumIE.quit();
		}

		if (Browser.compareToIgnoreCase("*chrome") == 0) {
			System.out.println("In AfterSuite for Chrome Thread Id:- " + Thread.currentThread().getId() + "Time:"
					+ df.format(date));
			seleniumGchrome.quit();

		}
	}
}
