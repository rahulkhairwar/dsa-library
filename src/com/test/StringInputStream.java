package com.test;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream
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

	public StringInputStream(String string)
	{
		this.string = string;
	}

}
