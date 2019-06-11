package com.test.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {

	public static String PROJECT_PATH;
	public static String PROJECT_URL;
	public static String APPLICATION_TO_TEST;
	public static String AUTOMATION_LOG;
	// public static String IEDriver;
	static {
		try {

			Properties props = new Properties();
			System.out.println("In Constants" + System.getProperty("user.dir"));
			InputStream inputStream = Constants.class.getClassLoader().getResourceAsStream("config.properties");
			System.out.println(Constants.class.getClassLoader().getClass());
			if (inputStream != null) {
				props.load(inputStream);
				System.out.println("Not Null of load props");

			}
			PROJECT_PATH = props.getProperty("automation_project_path");
			System.out.println("Property File Loading Path:" + PROJECT_PATH);
			PROJECT_URL = props.getProperty("automation_project_url");
			APPLICATION_TO_TEST = props.getProperty("application_to_test");
			AUTOMATION_LOG = props.getProperty("test.log.file");
			// IEDriver=props.getProperty(IEDriver);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public String getdir() {

		Properties props = new Properties();
		InputStream inputStream;
		try {
			System.out.println("In Constants getdir" + System.getProperty("user.dir"));
			inputStream = Constants.class.getClassLoader().getResourceAsStream("/config.properties");
			props.load(inputStream);
		} catch (IOException e) {

			e.printStackTrace();
		}
		PROJECT_PATH = props.getProperty("automation_project_path");
		return PROJECT_PATH;

	}

	public static void main(String args[]) throws Exception {
	}

}