package com.test;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream
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

	public StringOutputStream()
	{
		output = new StringBuilder("");
	}

}
