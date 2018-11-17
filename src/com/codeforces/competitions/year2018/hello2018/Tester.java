package com.codeforces.competitions.year2018.hello2018;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This code is automatically generated. Do not change it.
 */
public class Tester
{
	public static void main(String[] args)
	{
		List<Boolean> results = new ArrayList<>();
		List<Long> timeTaken = new ArrayList<>();
		List<TestCase> testCases = readTestCases("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive Programming/src/com/codeforces/competitions/year2018/hello2018/C.xml");

		for (int i = 0; i < testCases.size(); i++)
		{
			System.out.println("==========TestCase #" + (i + 1) + "==========");
			TestCase testCase = testCases.get(i);
			StringInputStream inputStream = new StringInputStream(testCase.getInput());
			StringOutputStream outputStream = new StringOutputStream();
			long start = System.currentTimeMillis();

			new TaskC(inputStream, outputStream);

			long time = System.currentTimeMillis() - start;

			try
			{
				inputStream.close();
				outputStream.flush();
				outputStream.close();

				boolean isMatching = testCase.getOutput().trim().equals(outputStream.getOutput().toString().trim());

				results.add(isMatching);
				timeTaken.add(time);
				System.out.print("Input :\n" + testCase.getInput() + "Output :\n" + outputStream.getOutput()
						+ "Expected Output :\n" + testCase.getOutput() + "Result : " + (isMatching ? "SUCCESS"
																								   : "WRONG "
																								+ "ANSWER") + "\nTime "
						+ "taken " + ": " + time + "ms\n");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("******************************************");

		for (int i = 0; i < results.size(); i++)
			System.out.println("TestCase #" + (i + 1) + ": " + (results.get(i) ? "SUCCESS" : "WRONG ANSWER") + " in "
					+ timeTaken.get(i) + "ms");
	}

	static List<TestCase> readTestCases(String xml)
	{
		List<TestCase> testCases = new ArrayList<>();
		String testCaseTag = "test";
		String inputTag = "input";
		String outputTag = "output";

		try
		{
			File xmlFile = new File(xml);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName(testCaseTag);

			for (int temp = 0; temp < nodeList.getLength(); temp++)
			{
				Node nNode = nodeList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) nNode;

					testCases.add(new TestCase(element.getElementsByTagName(inputTag).item(0).getTextContent(),
							element.getElementsByTagName(outputTag).item(0).getTextContent()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return testCases;
	}

	static class StringInputStream extends InputStream
	{
		String string;
		int pos;

		@Override public int read() throws IOException
		{
			if (pos >= string.length())
				return -1;

			return string.charAt(pos++);
		}

		public String getString()
		{
			return string;
		}

		public int getPos()
		{
			return pos;
		}

		StringInputStream(String string)
		{
			this.string = string;
		}

	}

	static class StringOutputStream extends OutputStream
	{
		StringBuilder output;

		@Override public void write(int b) throws IOException
		{
			output.appendCodePoint(b);
		}

		public StringBuilder getOutput()
		{
			return output;
		}

		StringOutputStream()
		{
			output = new StringBuilder("");
		}

	}

	static class TestCase
	{
		private String input, output;

		public String getInput()
		{
			return input;
		}

		public void setInput(String input)
		{
			this.input = input;
		}

		public String getOutput()
		{
			return output;
		}

		public void setOutput(String output)
		{
			this.output = output;
		}

		@Override public String toString()
		{
			return input + "=>\n" + output;
		}

		public TestCase(String input)
		{
			this.input = input;
		}

		public TestCase(String input, String output)
		{
			this.input = input;
			this.output = output;
		}

	}

}
