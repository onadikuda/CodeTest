package com.test.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.test.util.Constants;
import com.test.util.Utilities;

/**
 * Servlet implementation class XMLServlet
 */
@WebServlet("/XMLServlet")

public class XMLServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		Date date = new Date();

		out.println(
				"<h1 align=\"center\"><img src=\"images/Hitachi-Logo.jpg\"><br><font face=\"Trebuchet MS\" size=5 color=\"#084B8A\">End of your TestExecutions</h1><hr></hr><h2 align=\"center\"><img src=\"images/End.jpg\"></h2><hr></hr>");
		out.println("<font face=\"Trebuchet MS\" size=3 color=\"#2A0A0A\">");
		out.println("<table align=\"Center\"><tr><td>");
		String[] tests = req.getParameterValues("tc");

		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder2 = builderFactory.newDocumentBuilder();
			Document doc2 = docBuilder2.newDocument();

			createXmlTreeTwo(doc2, tests);

			out.println("<br><a href=\"" + Constants.PROJECT_URL
					+ "ReferenceFiles/Data/TestsToExecute.xml\" target=\"_blank\">Tests.xml</a> is created Successfully &nbsp&nbsp");

			out.println("<br><br><b>The Selected Test Cases are:</b><br>");
			for (int j = 0; j < tests.length; j++) {
				out.println(tests[j]);
				out.println("<br>");
			}
			System.out.println("LogFile Path" + "\"" + Utilities.getTestOutputFolderPath() + "\""
					+ "//AutomationResult.txt");
			Process p = Runtime.getRuntime()
					.exec(Constants.PROJECT_PATH + Utilities.getWebContent() + "ReferenceFiles/RunSuite.bat >" + "\""
							+ Utilities.getTestOutputFolderPath() + "\"" + "/AutomationResult.txt");

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.print(line + "Maven launched successful" + sdf.format(date));
			}
			p.waitFor();
			System.out.print("Maven process closed successfully @: " + sdf.format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("<br><a href=\"" + Constants.PROJECT_URL
				+ "test-output/surefire-reports/html/index.html\" target=\"_blank\">Click to find ReportOverview</a>");
		
		out.println("<br><a href=\"" + Constants.PROJECT_URL +
		 "test-output/AutomationResult.txt\" target2=\"_blank\">Are you interested in LogFile?</a>");
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		processRequest(req, res);
	}

	public void createXmlTreeTwo(Document doc, String[] tests) throws Exception {

		Element root = doc.createElement("TestCases");
		// adding a node after the last child node of the specified node.
		doc.appendChild(root);

		for (int i = 0; i < tests.length; i++) {
			Element child1 = doc.createElement("Test");
			root.appendChild(child1);
			child1.appendChild(doc.createTextNode(tests[i]));
		}

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer1 = factory.newTransformer();
		transformer1.setOutputProperty(OutputKeys.INDENT, "yes");

		// create string from xml tree

		StringWriter sw1 = new StringWriter();
		StreamResult result1 = new StreamResult(sw1);
		DOMSource source1 = new DOMSource(doc);
		transformer1.transform(source1, result1);
		String xmlString1 = sw1.toString();

		File file1 = new File(
				Constants.PROJECT_PATH + Utilities.getWebContent() + "/ReferenceFiles/Data/TestsToExecute.xml");
		System.out.print(file1);

		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1)));

		bw1.write(xmlString1);
		bw1.flush();
		bw1.close();

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
			ex.printStackTrace();
			System.out.println(ex.getStackTrace().toString());
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println(ex.getStackTrace().toString());
		} catch (SAXException ex) {
			ex.printStackTrace();
			System.out.println(ex.getStackTrace().toString());
		}
	}

	public void appendNode(String fileName, String tagName, String tfd, PrintWriter out) {
		loadFile(fileName, out);
		NodeList tagsNodeList = objDocument.getElementsByTagName(tagName);
		for (int i = 0; i < tagsNodeList.getLength(); i++) {
			Node childNode = tagsNodeList.item(i);

			Element e = objDocument.createElement("Test");
			if (tfd != null) {

				e.setTextContent(tfd);
				childNode.appendChild(e);
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (javax.xml.transform.TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DOMSource source = new DOMSource(objDocument);
		StreamResult result = null;
		try {
			result = new StreamResult(new FileOutputStream(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
