package com.test.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
import org.xml.sax.SAXException;

public class XMLTransformation {

	public static String[][] parseTestsToExecute() throws SAXException, IOException, ParserConfigurationException {
		File file = new File(Utilities.TestExec_FILE_NAME);
//		 File file = new File("E:/Performance/Automation workspace/AngularWithJava/WebContent/"+"/ReferenceFiles/Data/TestsToExecute.xml");
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

	private static String[] sortedArray = new String[2000];
	private int nodeCount = 0;

	public String[] parseTestDetails(String Testcase) throws SAXException, IOException, ParserConfigurationException {
		// File file = new File("E:/Performance/Automation
		// workspace/AngularWithJava/WebContent/"+"/ReferenceFiles/Data/TestDetails.xml");
		// In case of failure of previous TestSteps or TestCases the array holds the
		// previous TestSteps. Hence making to null
		sortedArray = new String[sortedArray.length];
		// System.out.println("SortedArray in TestDetails at start" +
		// Arrays.toString(sortedArray));
		System.out.println("Testcase Passed to TestDetails" + Testcase);
		File file = new File(Utilities.TestDet_FILE_NAME);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		Node root = doc.getDocumentElement();

		writeTestDetails(root, 0, Testcase, "");

		return sortedArray;
	}

	public final String getElementValue(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public void writeTestDetails(Node node, int indent, String nodeNameSelection, String nodeNameCurrent) {
		String nodeValue = getElementValue(node);
		// System.out.println("NodeValue in root of WriteTestDetails:" + nodeValue);
		NamedNodeMap attributes = node.getAttributes();
		// System.out.println("Attributes in root of WriteTestDetails:" +
		// attributes.toString());
		String attributeValue = "";

		if (nodeNameCurrent.equals(nodeNameSelection)) {
			sortedArray[nodeCount] = nodeValue;
			// System.out.println("nodeNameCurrent is equals to nodeNameSelection:" );
			nodeCount++;
		}
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			// System.out.println("Attribute while parsing in WriteTestDetails:" +
			// attribute);
			attributeValue = attribute.getNodeValue();
			// System.out.println("AttributeValue while parsing in WriteTestDetails:" +
			// attributeValue);
			if (attributeValue.equals(nodeNameSelection)) {
				// System.out.println("attributeValue is equals to nodeNameSelection:" );
			}
		}
		NodeList children = node.getChildNodes();
		// System.out.println("children while parsing in WriteTestDetails:" + children);
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			// System.out.println("child while parsing in WriteTestDetails:" + child);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				writeTestDetails(child, indent + 2, nodeNameSelection, attributeValue);
			} else {

				// System.out.println("Nothing to do in TestDetails:");
			}
		}
	}

	private static String[][] sortedArrayTwo = new String[2000][2000];
	private static int nodeCountTwo = 0;

	public static String[][] parseTestSteps(String Testcase)
			throws SAXException, IOException, ParserConfigurationException {
		System.out.println("Testcase Passed to ParseTestSteps" + Testcase);
		sortedArrayTwo = new String[sortedArrayTwo.length][sortedArrayTwo.length];
		File file = new File(Utilities.TestStep_FILE_NAME);
		// File file = new
		// File(Utilities.path+Utilities.getWebContent()+"/ReferenceFiles/Data/TestSteps.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		Node root = doc.getDocumentElement();
		// System.out.println("Root of ParseTestSteps:" + root);
		writeTestSteps(root, 0, Testcase, "");
		return sortedArrayTwo;

	}

	public static void writeTestSteps(Node node, int indent, String nodeNameSelection, String nodeNameCurrent) {
		NamedNodeMap attributes = node.getAttributes();
		// System.out.println("attributes while parsing in WriteTestSteps:" +
		// attributes);
		String attributeValue = "";
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			// System.out.println("attribute while parsing in Write TestSteps:" +
			// attribute);
			attributeValue = attribute.getNodeValue();
			// System.out.println("attributevalue while parsing in WriteTestSteps:" +
			// attributeValue);
			if (nodeNameCurrent.equals(nodeNameSelection)) {
				if (attribute.getNodeValue() != null && attribute.getNodeValue() != "") {
					sortedArrayTwo[nodeCountTwo][i] = attribute.getNodeValue();
					nodeCountTwo++;
				}

			}
		}

		NodeList children = node.getChildNodes();
		// System.out.println("children while parsing in WriteTestSteps:" + children);
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			// System.out.println("child while parsing in WriteTestSteps:" + child);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				writeTestSteps(child, indent + 2, nodeNameSelection, attributeValue);
			} else {

			}
		}
	}

	public final static String getElementValueTwo(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {
						return kid.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	private final static String[][] sortedArraythree = new String[2000][2000];
	private static int nodeCountThree = 0;

	public static String[][] parseObjectReference() throws SAXException, IOException, ParserConfigurationException {
		for (int i = 0; i < sortedArraythree.length; i++) {
			sortedArraythree[i][0] = null;
			sortedArraythree[i][1] = null;
			sortedArraythree[i][2] = null;
		}
		// File file = new File(Constants.PROJECT_PATH
		// +Utilities.getWebContent()+"/ReferenceFiles/Data/ObjectReference.xml");
		File file = new File(Utilities.PageObj_FILE_NAME);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		Node root = doc.getDocumentElement();
		System.out.println("Root of ParseObjectRef:" + root);
		writeDocumentToOutputThree(root, 0, "");
		return sortedArraythree;

	}

	public static void writeDocumentToOutputThree(Node node, int indent, String nodeNameCurrent) {
		NamedNodeMap attributes = node.getAttributes();
		// System.out.println("Attributes of ParseObjectRef:" + attributes);
		String attributeValue = "";
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			attributeValue = attribute.getNodeValue();
			if (attribute.getNodeValue() != null && attribute.getNodeValue() != "") {
				sortedArraythree[nodeCountThree][i] = attribute.getNodeValue();
				nodeCountThree++;
			}

		}
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				writeDocumentToOutputThree(child, indent + 2, attributeValue);
			} else {

			}
		}

	}

	public static String[][] retArray() throws SAXException, IOException, ParserConfigurationException {

		String[][] sortedArray1 = new String[2000][2000];

		for (int s = 0; s < sortedArray1.length; s++) {
			sortedArray1[s][0] = null;
			sortedArray1[s][1] = null;
			sortedArray1[s][2] = null;

		}

		XMLTransformation.parseObjectReference();

		String value1 = null;
		String value2 = null;
		String value3 = null;
		int j = 0;
		for (int i = 0; i < sortedArraythree.length; i++) {
			if (sortedArraythree[i][0] != null) {
				value1 = sortedArraythree[i][0];
			}
			if (sortedArraythree[i][1] != null) {
				value2 = sortedArraythree[i][1];
			}
			if (sortedArraythree[i][2] != null) {
				value3 = sortedArraythree[i][2];
			}
			Integer i3value = ((i + 1) % 3);
			if (i3value.equals(0)) {
				sortedArray1[j][0] = value1;
				sortedArray1[j][1] = value2;
				sortedArray1[j][2] = value3;
				j++;
				value1 = value2 = value3 = null;
			}

		}

		for (int k = 0; k < sortedArray1.length; k++) {
			String attr1value = sortedArray1[k][0];
			String attr2value = sortedArray1[k][1];
			String attr3value = sortedArray1[k][2];

			if (attr1value != null) {
			}
			if (attr2value != null) {

			}
			if (attr3value != null) {

			}
		}
		return sortedArray1;
	}

}
