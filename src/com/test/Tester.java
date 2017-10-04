package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Tester
{
	public static void main(String[] args)
	{
		String input = "VKKKKKKKKKVVVVVVVVVK";
		StringInputStream inputStream = new StringInputStream(input);
		StringOutputStream outputStream = new StringOutputStream();

		System.out.println("input : " + inputStream.getString());

		new TaskA(inputStream, outputStream);

		try
		{
			inputStream.close();
			outputStream.flush();
			outputStream.close();

			System.out.println("input read till : " + inputStream.getPos());
			System.out.println("output is : " + outputStream.getOutput());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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

}
