package com.test.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.KeyStroke;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.test.util.Constants;

public class Utilities {

	// public static String path = Constants.PROJECT_PATH;
	// Does work for Testng test
	public static String path = System.getProperty("user.dir");
	// public static final String FILE_NAME =
	// path+Utilities.getWebContent()+"\\ReferenceFiles\\Data\\DataFeeder.xls";
	// public static final String FILE_NAME =
	// path+Utilities.getWebContent()+"ReferenceFiles/Data/DataFeeder.xls";
	public static final String FILE_NAME = path + "\\WebContent\\ReferenceFiles\\Data\\DataFeeder.xls";
	public static final String TestStep_FILE_NAME = path + "\\WebContent\\ReferenceFiles\\Data\\TestSteps.xml";
	public static final String TestDet_FILE_NAME = path + "\\WebContent\\ReferenceFiles\\Data\\TestDetails.xml";
	// public static final String FILE_NAME = "E:/Performance/Automation
	// workspace/AngularWithJava/WebContent/ReferenceFiles/Data/DataFeeder.xls";
	public static final String PageObj_FILE_NAME = path + "\\WebContent\\ReferenceFiles\\Data\\ObjectReference.xml";
	public static final String TestExec_FILE_NAME = path + "\\WebContent\\ReferenceFiles\\Data\\TestsToExecute.xml";

	public static String getWebContent() {
		System.out.println("In Utilities" + System.getProperty("user.dir"));
		File fpath = new File(Constants.PROJECT_PATH + "/WebContent");
		/*
		 * Does work for Testng test File fpath=new File( path+"/WebContent");
		 * System.out.print(fpath);
		 */

		return fpath.exists() ? "WebContent/" : "";
		/*
		 * Does work for Testng test return fpath.exists()?"\\WebContent":"";
		 */

	}

	public static String[][] getTableArray(String xlFilePath, String sheetName, String tableName) throws Exception {
		String[][] tabArray = null;

		try {
			// System.out.println("file name is..."+xlFilePath);
			// System.out.println("Sheet name..."+sheetName);
			// System.out.println("Table name..."+tableName);
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			// System.out.println("workbook..."+workbook);
			Sheet sheet = workbook.getSheet(sheetName);
			// System.out.println("sheet..."+sheet);
			int startRow, startCol, endRow, endCol, ci, cj;
			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			// System.out.println("startRow is.."+startRow);
			startCol = tableStart.getColumn();
			// System.out.println("startCol is.."+startCol);
			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);
			endRow = tableEnd.getRow();
			// System.out.println("End row is..."+endRow);
			endCol = tableEnd.getColumn();
			/*
			 * System.out.println("startRow="+startRow+", endRow="+endRow+", " +
			 * "startCol="+startCol+", endCol="+endCol);
			 */
			tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
					// System.out.println("Data in return of tab Array" + tabArray[ci][cj]) ;
				}
			}
		} catch (FileNotFoundException e) {

			System.out.println("Error in Retrieving TableData");

		}
		return (tabArray);

	}

	/**
	 * @putTableData method writes the data onto the excel sheet
	 * 
	 * @param fileName  Name of the file where the data is to be written
	 * @param sheetName Name of the sheet in the excel on which data is to be
	 *                  written
	 */
	/*
	 * public static String[][] putTableData(String fileName, String sheetName){ //
	 * create WorkbookSettings object WorkbookSettings ws = new WorkbookSettings();
	 * 
	 * try { //create work book WritableWorkbook workbook =
	 * Workbook.createWorkbook(new File(fileName), ws);
	 * 
	 * //create work sheet WritableSheet workSheet = null; workSheet =
	 * workbook.createSheet(sheetName ,0); //SheetSettings sh =
	 * workSheet.getSettings();
	 * 
	 * //Creating Writable font to be used in the report WritableFont normalFont =
	 * new WritableFont(WritableFont.createFont("MS Sans Serif"),
	 * WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
	 * UnderlineStyle.NO_UNDERLINE);
	 * 
	 * //creating plain format to write data in excel sheet WritableCellFormat
	 * normalFormat = new WritableCellFormat(normalFont);
	 * normalFormat.setWrap(true);
	 * normalFormat.setAlignment(jxl.format.Alignment.CENTRE);
	 * normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	 * normalFormat.setWrap(true); normalFormat.setBorder(jxl.format.Border.ALL,
	 * jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
	 * 
	 * //write to datasheet
	 * 
	 * workSheet.addCell(new jxl.write.Label(0,1,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(0,2,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(0,3,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(1,1,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(1,2,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(1,3,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(2,1,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(2,2,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(2,3,"test",normalFormat));
	 * workSheet.addCell(new jxl.write.Label(3,1,"test",normalFormat));
	 * 
	 * 
	 * //write to the excel sheet workbook.write();
	 * 
	 * //close the workbook workbook.close();
	 * 
	 * }catch (Exception e){ e.printStackTrace (); } return null; }
	 * 
	 */
	// File Upload
	// FileChooserThread
	public class FileChooserThread extends Thread {
		public FileChooserThread(String file) {
			super(new FileRunner(file));
		}
	}

	/**
	 * This class identifies the browser button and the file chooser menu as the
	 * selenium doesn't recognise the browser button in web pages
	 */
	class FileRunner implements Runnable {

		private String fullName;

		public FileRunner(String fileName) {
			this.fullName = fileName;
		}

		/**
		 * @run() this method starts running the thread
		 */
		public void run() {
			try {
				Thread.sleep(1000);
				Robot robot = new Robot(); // input simulation class
				for (char c : fullName.toCharArray()) {
					if (c == ':') {
						robot.keyPress(KeyEvent.VK_SHIFT);
						robot.keyPress(KeyEvent.VK_SEMICOLON);
						robot.keyRelease(KeyEvent.VK_SHIFT);
					} else if (c == '/') {
						robot.keyPress(KeyEvent.VK_BACK_SLASH);
					} else {
						robot.keyPress(KeyStroke.getKeyStroke(Character.toUpperCase(c), 0).getKeyCode());
					}
				}
				robot.keyPress(KeyEvent.VK_ENTER);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @chooseFile method is used to choose a file from the file chooser dialog on
	 *             clicking the Browse button in the Application
	 * 
	 * @param element  Element that you select
	 * @param fileName name of the file you select
	 * @return the selected fileName
	 * @throws Exception throws an exception if file is not chosen and attempting to
	 *                   open
	 */
	public String chooseFile(String element, String fileName) throws Exception {

		// Number positionLeft =
		// ThreadSafeSeleniumSessionStorage.session().getElementPositionLeft(element);
		// Number positionTop =
		// ThreadSafeSeleniumSessionStorage.session().getElementPositionTop(element);
		new FileChooserThread(fileName).start(); // launch input thread.
		// this method will held current thread while FileChooser gives the file name
		// ThreadSafeSeleniumSessionStorage.session().clickAt("content",positionLeft +
		// "," + positionTop);
		return fileName;
	}

	/**
	 * @getRandomString method generates a unique random string
	 * @return
	 */
	public static String GetRandomString() {
		String RandomString = null;
		Calendar objCalendar = new GregorianCalendar();
		RandomString = Integer.toString(objCalendar.get(Calendar.MONTH) + 1)
				+ Integer.toString(objCalendar.get(Calendar.DATE)) + Integer.toString(objCalendar.get(Calendar.YEAR))
				+ Integer.toString(objCalendar.get(Calendar.HOUR)) + Integer.toString(objCalendar.get(Calendar.MINUTE))
				+ Integer.toString(objCalendar.get(Calendar.SECOND));
		return RandomString;
	}

	public static String getTestOutputFolderPath() {
		File fpath = new File(Constants.PROJECT_PATH + "/WebContent/test-output");

		System.out.println(fpath);

		File fpathInWar = new File(Constants.PROJECT_PATH + "/test-output");

		// File fpath=new File("E://Performance//Automation
		// workspace//AngularWithJava//WebContent//test-output");

		// File fpathInWar=new File("E://Performance//Automation
		// workspace//AngularWithJava//WebContent//test-output");
		String i = fpath.exists() ? fpath.getAbsolutePath() : fpath.getAbsolutePath();
		System.out.println("In getTestoutputFolderPath" + i);
		return fpath.exists() ? fpath.getAbsolutePath() : fpath.getAbsolutePath();

	}

	static String searchFolder(File curDir, String file) {
		if (curDir.getName().equals(file)) {
			return curDir.getAbsolutePath();
		} else {
			if (curDir.isDirectory()) {
				File[] subList = curDir.listFiles();
				for (int i = 0; i < subList.length; i++) {
					System.out.println("dir:  " + subList[i].getName());
					// System.out.println("file: "+file);
					if (subList[i].getName().equals(file)) {
						return subList[i].getAbsolutePath();
					} else {
						if (subList[i].isDirectory()) {
							searchFolder(subList[i], file);
						}
					}
				}
			}
		}

		return "";
	}

	public static void main(String args[]) {
		System.out.println(getTestOutputFolderPath());

	}

	public static String getCurrentDateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
		return formatter.format(currentDate.getTime());
	}

	/*
	 * public static String formatTimeInLocalOrSpecifiedTimeZone( long
	 * timeInMilliSeconds, String format) { SimpleDateFormat sdf = new
	 * SimpleDateFormat(format); TimeZone timeZone = RuntimeBehavior.getTimeZone();
	 * sdf.setTimeZone(timeZone); return sdf.format(timeInMilliSeconds); }
	 */
}
