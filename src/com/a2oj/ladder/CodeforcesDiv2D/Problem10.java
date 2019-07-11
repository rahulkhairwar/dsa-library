package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public final class Problem10
{
	public static void main(String[] args)
	{
		new Problem10(System.in, System.out);
	}

	static class Solver
	{
		long l, r;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			l = in.nextLong();
			r = in.nextLong();

			String temp = Long.toBinaryString(l);
			char[] binR = Long.toBinaryString(r).toCharArray();

			while (temp.length() < binR.length)
				temp = "0" + temp;

			char[] binL = temp.toCharArray();
			int n = binL.length;
			char[] a = new char[n];
			char[] b = new char[n];
			int pos = -1;

			for (int i = 0; i < n; i++)
			{
				if (binL[i] != binR[i])
				{
					pos = i;

					break;
				}
			}

			if (pos == -1)
			{
				out.println(0);

				return;
			}

			for (int i = 0; i <= pos; i++)
			{
				a[i] = binL[i];
				b[i] = binR[i];
			}

			for (int i = pos + 1; i < n; i++)
			{
				a[i] = '1';
				b[i] = '0';
			}

			out.println(Long.parseLong(new String(a), 2) ^ Long.parseLong(new String(b), 2));
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public Problem10(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		out.flush();
		out.close();
	}

}
