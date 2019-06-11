package com.test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.test.util.HashMapReference;
//import com.test.util.NotOnBrowser.BrowserList;
import com.test.Login_Test;

//@NotOnBrowser(navigator = BrowserList.iexplorer, enabled = true)
public class Components {

	Date now = new Date();
	private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS ");
	public WebDriver selenium;
	SoftAssert sftAsst = new SoftAssert();

	/**
	 * @winExists method verifies whether a wondow exists or not
	 * 
	 * @param name is the name of the window that is checked
	 */
	public void winExists(String name, WebDriver selenium) {
		// verifyTrue(selenium.isTextPresent(name));
		// verifyTrue(selenium.findElement(By.tagName("body")).getText().contains(name));
		selenium.getPageSource().contains(name);
	}

	/**
	 * @getPage method gets the required page
	 * 
	 * @param name is the name of the page to be opened
	 */
	public void getPage(String name, String Title, WebDriver selenium) {
		selenium.get(HashMapReference.getObj(name));
		// waitForPageToLoad("5000",selenium);
		System.out.println("URL of basepage" + selenium.getCurrentUrl());
	}

	/**
	 * @click method clicks the object and opens the corresponding action
	 * 
	 * @param name is the value in the Object reference table
	 * @throws InterruptedException
	 */
	public void click(String name, WebDriver selenium) throws Exception {
		System.out.println("Time" + df.format(now) + "In Click" + selenium);
		new WebDriverWait(selenium, 20)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(HashMapReference.getObj(name)))).click();
	}

	public void SearchAndEnter(String name, String Text, WebDriver selenium) throws Exception {

		System.out.println("Time" + df.format(now) + "In Search And Enter" + selenium);
		selenium.findElement(By.xpath(HashMapReference.getObj(name))).sendKeys(Text + Keys.ENTER);
		System.out.println("Time" + df.format(now) + "In Search And Enter2" + selenium);

	}

	public static void takeSnapShot(WebDriver webdriver) throws IOException {
		String fileWithPath = "./WebContent/test-output/Screenshots/YoutubeWithHitachi.jpg";
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}

	/**
	 * @click method clicks the object and opens the corresponding action
	 * 
	 * @param name is the value in the Object reference table
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void clickAndWait(String name, WebDriver selenium) throws InterruptedException {

		System.out.println("Time" + df.format(now) + "In ClickAndWait" + selenium);
		new WebDriverWait(selenium, 20)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(HashMapReference.getObj(name)))).click();

		/*
		 * boolean returnVal = IsElementPresent(name); if(returnVal==false) {
		 * 
		 * selenium.findElement(By.xpath(HashMapReference.getObj(name))).click();
		 * System.out.println("ClickAndWait....."+HashMapReference.getObj(name) +
		 * returnVal); wait(7000); } else {
		 * System.out.println("Failed in Click And Wait since element not found" +
		 * name); }
		 */
	}

	public void clickAndNav(String name, String Text, WebDriver selenium) throws InterruptedException {
		System.out
				.println("Time" + df.format(now) + "Browserinfo in ClickandNav  and selenium instance info" + selenium);
		System.out.println("Time" + df.format(now) + "ClickandNav Text" + Text);
		/*
		 * if(selenium.toString().contains("InternetExplorerDriver")) {
		 * System.out.println("Time" + df.format(now)
		 * +"Control will be missing for IE. Hence skipping this steps"); } else {
		 * System.out.println("Time" + df.format(now) +"ClickAndNav..... "+ selenium);
		 */
		WebDriverWait ww = new WebDriverWait(selenium, 30);
		ww.until(ExpectedConditions.elementToBeClickable(By.xpath(HashMapReference.getObj(name)))).click();
		ww.until(ExpectedConditions.urlToBe(Text));
		// }
	}

//@NotOnBrowser()
	// Mechanism to skip test step based on browser since user knows the control is
	// not available
	public void CopyLink(String name, WebDriver selenium) throws Exception {

		System.out.println("Time" + df.format(now) + "CopyLink and selenium instance info" + selenium);
		/*
		 * if(selenium.toString().contains("InternetExplorerDriver")) {
		 * System.out.println("Time" + df.format(now) +
		 * "Cannot copy as previous step is skipped"); } else {
		 */
		new WebDriverWait(selenium, 30)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(HashMapReference.getObj(name)))).click();
		// wait(10000);
		System.out.println("Time" + df.format(now) + "Copy link successful" + selenium);
		// }
	}

	public void clickAndSwitch(String name, WebDriver selenium) throws InterruptedException {

		String parentWindow = selenium.getWindowHandle();
		selenium.findElement(By.xpath(HashMapReference.getObj(name))).click();
		wait(5000);
		Set<String> allWindows = selenium.getWindowHandles();
		for (String curWindow : allWindows) {
			selenium.switchTo().window(curWindow);
			selenium.manage().window().maximize();
			wait(5000);
		}
		// selenium.switchTo().window(parentWindow);
	}

	public void PastefromClipBoard(WebDriver selenium) throws InterruptedException {

		Actions actions = new Actions(selenium);
		actions.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v")).build().perform();
		wait(3000);
	}

	public void NavigateBack(WebDriver selenium) throws InterruptedException {
		selenium.navigate().back();
		System.out.println("Going back with browser info" + selenium);
		// new WebDriverWait(selenium, 10).until(ExpectedConditions.urlContains(Text));
	}

	/**
	 * @clickCheck method clicks the object and verifies and make sure the page is
	 *             directed to right page
	 * 
	 * @param name  is the value in the Object reference table
	 * @param Title is the title name of the redirected page
	 */
	public void clickAndVerify(String name, String Title, WebDriver selenium) throws InterruptedException {
		selenium.findElement(By.className(HashMapReference.getObj(name))).click();
		waitForPageToLoad("20000", selenium);
		Assert.assertEquals(selenium.getTitle(), Title);
	}

	public void clickAndVerifyAlert(String name, String Text, WebDriver selenium) {
		selenium.findElement(By.className(HashMapReference.getObj(name))).click();
		// wait.until(ExpectedConditions.alertIsPresent());
		// assertEquals(selenium.getAlert(), Text);
		// Assert.assertEquals(selenium.switchTo().alert(), arg1);
		WebDriverWait wait = new WebDriverWait(selenium, 2);
		wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertEquals(selenium.switchTo().alert(), Text);
	}

	/**
	 * @enterValue enters the value in the text field based on the object
	 * 
	 * @param name is the name value in the Object Reference Table
	 * @param Text is the text that is entered into the Text Field
	 * @throws InterruptedException
	 * 
	 */
	public void enterValue(String name, String Text, WebDriver selenium) throws InterruptedException {
		if (Text.equals("NA")) {
			selenium.findElement(By.xpath(HashMapReference.getObj(name))).sendKeys("No Text received to enter");
		} else {
			selenium.findElement(By.xpath(HashMapReference.getObj(name))).sendKeys(Text);
			wait(10000);
		}
	}

	// This method is used when user tries to enter multiple values in the given
	// text field
	public void enterSetOfValues(String name, String[] Text, WebDriver selenium) throws Exception {
		System.out.println("Time" + df.format(now) + "In EnterSetOfValues()" + selenium);
		selenium.findElement(By.xpath(HashMapReference.getObj(name))).sendKeys(Text);
		selenium.findElement(By.xpath(HashMapReference.getObj(name))).clear();
	}

	public void enterDetails(String name, String Text, WebDriver selenium) throws Exception {

		name = "omprakash";
		Text = "5thgbds";

		Login_Test TL = new Login_Test();
		TL.testLogin("name", "Text", selenium);
	}

	/**
	 * @verifyValue Verifies the value in the text field is equal to the actual
	 *              value
	 * 
	 * @param name is the name value in the Object Reference Table
	 * @param Text is the text that is entered into the Text Field
	 */

	public void verifyValue(String name, String Text, WebDriver selenium) {
		// assertEquals(selenium.getValue(HashMapReference.getObj(name)),Text);
		// Assert.assertEquals(selenium.equals(HashMapReference.getObj(name)),Text);

	}

	/**
	 * @wait method waits for the page to load for a given time
	 * 
	 * @param Time is the time the page waits for
	 */
	public void waitForPageToLoad(String Time, WebDriver selenium) {

		// selenium.waitForPageToLoad(Time);
		// selenium.waitForPageToLoad("20000",selenium);
	}

	/**
	 * @wait method waits for the page to load for a given time
	 * 
	 * @param component is the time the page waits for
	 * @throws InterruptedException
	 */
	public void wait(int Time) throws InterruptedException {
		Thread.sleep(Time);
	}

	/**
	 * @verifyText method verifies whether the text is entered or not
	 * 
	 * @param Text The text to be entered
	 */
	public void verifyText(String Text, WebDriver selenium) {

		// verifyTrue(selenium.isTextPresent(Text));
		selenium.getPageSource().contains(Text);

		// verifyEquals(SeleniumDriver.selenium.getText(HashMapTable.getObj(Text)),Text);
		// checkForVerificationErrors();

	}

	/*
	 * public boolean isElementPresent(WebDriver driver, By by){ try{
	 * driver.findElements(by); return true; }catch(NoSuchElementException e){
	 * return false; } }
	 */
	/**
	 * @verifyElement method verifies whether the text is entered or not
	 * 
	 * @param Text The text to be entered
	 */

	public void verifyElement(String Text, WebDriver selenium) {

		Assert.assertTrue(selenium.findElement(By.name(Text)).isDisplayed());
		// checkForVerificationErrors();
	}

	public void check(String name, boolean Expected, WebDriver selenium) throws InterruptedException {

		// boolean isChecked = selenium.isChecked(HashMapReference.getObj(name));

		boolean isChecked = selenium.findElement(By.name(HashMapReference.getObj(name))).isSelected();

		if (isChecked != Expected) {
			selenium.findElement(By.id(HashMapReference.getObj(name))).click();
			wait(3000);
		}
	}

	public void uncheck(String name, boolean Expected, WebDriver selenium) {

		boolean isChecked = selenium.findElement(By.name(HashMapReference.getObj(name))).isSelected();

		if (isChecked != Expected) {
			// selenium.click(HashMapReference.getObj(name));
			selenium.findElement(By.id(HashMapReference.getObj(name))).click();
		}
	}

	public void verifyChecked(String name, WebDriver selenium) {

		// verifyTrue(selenium.isChecked(HashMapReference.getObj(name)));
		Assert.assertTrue(selenium.findElement(By.name(HashMapReference.getObj(name))).isSelected());

	}

	public void MouseOver(String name, WebDriver selenium) {
		System.out.println("Time" + df.format(now) + "In MouseOver" + selenium);
		WebElement ele = selenium.findElement(By.xpath(HashMapReference.getObj(name)));
		Actions action = new Actions(selenium);
		action.moveToElement(ele).build().perform();
		String ToolTipText = ele.getAttribute("title");
	}

	public void Scroll(String name, WebDriver selenium) {

		System.out.println("Time" + df.format(now) + "In Scroll" + selenium);
		JavascriptExecutor js = (JavascriptExecutor) selenium;
		WebElement element = new WebDriverWait(selenium, 10)
				.until(ExpectedConditions.elementToBeClickable(By.xpath(HashMapReference.getObj(name))));
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	public void close(WebDriver selenium) {
		selenium.quit();
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openWindow(String URL, String name) {
		// selenium.openWindow(URL, HashMapReference.getObj(name));
	}

	public void refresh() {
		selenium.navigate().refresh();
	}

	public void goBack() {
		selenium.navigate().back();
	}

	public void select(String selectLocator, String optionLocator, WebDriver selenium) throws InterruptedException {
		Select dropdown = new Select(selenium.findElement(By.xpath(HashMapReference.getObj(selectLocator))));
		// , optionLocator)).click();
		dropdown.selectByValue(optionLocator);
		System.out.println("In dropdown");
		wait(5000);
	}

	public void getSelectedValues(String selectLocator) {
		// selenium.getSelectedValues(HashMapReference.getObj(selectLocator));
	}

	public void selectFrame(String locator) {
		// selenium.selectFrame(HashMapReference.getObj(locator));
		WebElement fr = selenium.findElement(By.id("theIframe"));

		selenium.switchTo().frame(fr);

		// driver.switchTo().defaultContent();

	}

	public void selectPopUp(String windowID) {
		// selenium.selectPopUp(HashMapReference.getObj(windowID));
	}

	public void selectWindow(String windowID) {
		// selenium.selectWindow(HashMapReference.getObj(windowID));
	}

	public void setBrowserLogLevel(String logLevel) {
		// selenium.setBrowserLogLevel(logLevel);
	}

	public void submit(String formLocator) {
		// selenium.submit(HashMapReference.getObj(formLocator));
	}

	public void waitForPopUp(String windowID, String timeout) {
		// selenium.waitForPopUp(HashMapReference.getObj(windowID), timeout);
	}

	public void windowFocus() {
		// selenium.windowFocus();
	}

	public void windowMaximize() {
		// Works only for IE and Firefox

		selenium.manage().window().maximize();
	}

	public void storeChecked(String locator, String variableName) {

	}

	public boolean IsElementPresent(String name) {

		selenium.findElement(By.xpath(HashMapReference.getObj(name)));
		System.out.println("In IsElementPreset Block- String to Search:" + name);
		return false;

	}

	public void PauseVideo(String name, WebDriver selenium) throws InterruptedException, IOException {
		wait(10000);
		System.out.println("In PauseVideo");
		JavascriptExecutor executor = (JavascriptExecutor) selenium;
		executor.executeScript("document.getElementById(\"ytd-player\").pause()");
		takeSnapShot(selenium);
		wait(10000);
		executor.executeScript("document.getElementById(\"ytd-player\").play()");
		wait(10000);
		// Object player_status =
		// executor.executeScript("document.getElementById(\"ytd-player\").getPlayerState()");
	}
}
