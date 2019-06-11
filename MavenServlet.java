package com.test.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.test.util.Constants;
import com.test.util.Utilities;

@WebServlet("/MavenServlet")
public class MavenServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1779150178532712406L;
//	public final static Logger LOGGER = LogManager.getLogger(com.test.servlets.MavenServlet.class);

	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	//	LOGGER.info("MyServlet's doGet() called");
		String url = request.getParameter("urlp");
		String[] br = request.getParameterValues("browser");
		try {
			out.println(
					"<script language=\"JavaScript\"> function SetAllCheckBoxes(FormName, FieldName, CheckValue){if(!document.forms[FormName])return;var objCheckBoxes=document.forms[FormName].elements[FieldName];if(!objCheckBoxes)return;var countCheckBoxes = objCheckBoxes.length;if(!countCheckBoxes)objCheckBoxes.checked = CheckValue;else for(var i = 0; i < countCheckBoxes; i++)objCheckBoxes[i].checked = CheckValue;}</script>");
			out.println("<font face=\"Trebuchet MS\" size=3 color=\"#2A0A0A\">");
			out.println(
					"<h1 align=\"center\"><img src=\"images/Hitachi-Logo.jpg\"><br><font face=\"Trebuchet MS\" size=5 color=\"#084B8A\">Experience the New & Easy Automation Framework</font></h1><hr></hr><h2 align=\"center\"><img src=\"images/Start.jpg\"></h2><hr></hr>");
			out.println("<table align=\"Center\"><tr><td>");

			UpdateMaven(br);

			for (int i = 0; i < br.length; i++) {
				String FileName = Constants.PROJECT_PATH + Utilities.getWebContent()
						+ "/ReferenceFiles/Data/TestNG_Inputs/" + br[i];

				updateFile(FileName, "parameter", "name", "URL", "value", url, out);

				if (br[i].compareToIgnoreCase("testngLocalhostIE.xml") == 0) {
					out.println("<br><br><b>Browser:</b>&nbsp&nbsp Internet Explorer");
					//Process p1 = Runtime.getRuntime().exec(PROJECT_PATH+"WebContent/ReferenceFiles/LaunchRCIE.bat > "+PROJECT_PATH+"WebContent/test-output/LaunchRCIE.txt");
					out.println("<br><a href=\"" + Constants.PROJECT_URL
							+ "WebContent/ReferenceFiles/Data/TestNG_Inputs/testngLocalhostIE.xml\" target=\"_blank\">testngLocalhostIE.xml</a> is updated Successfully &nbsp&nbsp");
					out.println("<br><a href=\"" + Constants.PROJECT_URL
							+ "WebContent/test-output/LaunchRCIE.txt\" target=\"_blank\">RemoteWebDriverfor IE</a> can be started on port 4444 &nbsp&nbsp");

				}
				if (br[i].compareToIgnoreCase("testngLocalhostChrome.xml") == 0) {
					out.println("<br><br><b>Browser:</b>&nbsp&nbsp Google Chrome");
					out.println("<br><a href=\"" + Constants.PROJECT_URL
							+ "WebContent/ReferenceFiles/Data/TestNG_Inputs/testngLocalhostChrome.xml\" target=\"_blank\">testngLocalhostChrome.xml</a> is updated Successfully &nbsp&nbsp");
					out.println("<br><a href=\"" + Constants.PROJECT_URL
							+ "WebContent/test-output/LaunchRCChrome.txt\" target=\"_blank\">RemoteWebDriver for Google Chrome</a> can be started on port 4454 &nbsp&nbsp");

				}
			}
		}

		catch (Exception e) {
		//	LOGGER.fatal(e + " Fatal Exception in project Loading");
			e.printStackTrace();
			//System.out.println(e);
		}
		try {
			String[][] arr = new MavenServlet().parseXmlFile(out);
			out.println("<form name=\"myForm\" method=\"Get\" action=\"./XMLServlet\">");
			out.println("<br><b>Test Cases:</b>");

			for (int i = 0; i < arr.length; i++) {
				out.println("<br>");

				out.println("<input type=\"checkbox\" name=\"tc\" id=\"tc\" value=\"" + arr[i][0] + "\">" + arr[i][0]);

			}
			out.println("<br>");
			out.println("<br>");
			out.println(
					"<input type=\"button\" onClick=\"SetAllCheckBoxes('myForm', 'tc', true)\" value=\"Select All\"/>&nbsp&nbsp&nbsp&nbsp&nbsp");
			out.println(
					"<input type=\"button\" onClick=\"SetAllCheckBoxes('myForm', 'tc', false)\" value=\"UnSelect All\"/><br/>&nbsp&nbsp&nbsp&nbsp");
			out.println("<br><input type=\"Submit\" value=\"Execute Tests\"/>");
			out.println("</form>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			out.close();
		}

	}

	DocumentBuilderFactory objDocumentBuilderFactory;
	DocumentBuilder objDocumentBuilder;
	Document objDocument;

	private void loadFile(String FileName, PrintWriter out) {

		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilderFactory.setIgnoringElementContentWhitespace(true);
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			objDocument = objDocumentBuilder.parse(new File(FileName));

		} catch (ParserConfigurationException ex) {
			System.out.println(ex.getStackTrace().toString());
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace().toString());
		} catch (SAXException ex) {
			System.out.println(ex.getStackTrace().toString());
		}
	}

	public void updateFile(String fileName, String tagName, String requiredIdentificationNode,
			String requiredIdentificationNodeValue, String changeRequiredNode, String changeRequiredNodeNewValue,
			PrintWriter out) {
		loadFile(fileName, out);
		NodeList tagsNodeList = objDocument.getElementsByTagName(tagName);
		for (int i = 0; i < tagsNodeList.getLength(); i++) {
			Node childNode = tagsNodeList.item(i);
			Node requiredNodeKey = childNode.getAttributes().getNamedItem(requiredIdentificationNode);
			if (requiredIdentificationNodeValue.contentEquals(requiredNodeKey.getNodeValue())) {
				Node requiredNode = childNode.getAttributes().getNamedItem(changeRequiredNode);
				requiredNode.setNodeValue(changeRequiredNodeNewValue);
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(objDocument);
		StreamResult result = null;
		try {
			result = new StreamResult(new FileOutputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	protected void UpdateMaven(String[] br) throws IOException {
		try {
			String BatchFileName = Constants.PROJECT_PATH + Utilities.getWebContent() + "ReferenceFiles/RunSuite.bat";
			File file = new File(BatchFileName);
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("pushd \"" + Constants.PROJECT_PATH + "\"");
			bw.newLine();
			String temp2 = "";
			for (int i = 0; i < br.length; i++) {
				if (temp2.length() > 0) {
					temp2 += ",";
				}
				temp2 += "WebContent/ReferenceFiles/Data/TestNG_Inputs/" + br[i];
			}
			bw.write("mvn clean test -Dfiles=" + temp2);
			bw.newLine();
			bw.flush();
			bw.close();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e + "Inside UpdateMaven Script");
		}
	}

	public String[][] parseXmlFile(PrintWriter out) throws SAXException, IOException, ParserConfigurationException {
		File file = new File(
				Constants.PROJECT_PATH + Utilities.getWebContent() + "/ReferenceFiles/Data/TestsToLoad.xml");
		System.out.print(file);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		NodeList nodeLst = doc.getElementsByTagName("Test");
		String[][] NewnodeList = new String[nodeLst.getLength()][];

		for (int s = 0; s < nodeLst.getLength(); s++) {

			NewnodeList[s] = new String[1];
			NewnodeList[s][0] = ((Node) nodeLst.item(s)).getTextContent();
		}
		return NewnodeList;

	}

}
