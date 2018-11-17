package com.codeforces.competitions.year2018.round486div3;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int INFINITY = (int) 1e9;
		long n;
		char[] s;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextLong();
			s = ("" + n).toCharArray();

			if (s.length < 2)
			{
				out.println(-1);

				return;
			}

			int a = find("00".toCharArray());
			int b = find("25".toCharArray());
			int c = find("50".toCharArray());
			int d = find("75".toCharArray());
			int min = CMath.min(a, b, c, d);

			if (min == INFINITY)
				out.println(-1);
			else
				out.println(min);
		}

		int find(char[] dig)
		{
			int len = s.length;
			char[] ss = new char[len];
			int pos = -1;

			System.arraycopy(s, 0, ss, 0, s.length);

			for (int i = len - 1; i >= 0; i--)
			{
				if (ss[i] == dig[1])
				{
					pos = i;

					break;
				}
			}

			if (pos == -1)
				return INFINITY;

			int swaps = 0;
			int ones = pos;
			boolean addExtra = false;

			for (int i = pos; i < len - 1; i++)
			{
				char temp = ss[i];

				ss[i] = ss[i + 1];
				ss[i + 1] = temp;
				swaps++;

				if (ss[0] == '0')
					addExtra = true;
			}

			pos = -1;

			for (int i = len - 2; i >= 0; i--)
			{
				if (ss[i] == dig[0])
				{
					pos = i;

					break;
				}
			}

			if (pos == -1)
				return INFINITY;

			int tens = pos;

			for (int i = pos; i < len - 2; i++)
			{
				char temp = ss[i];

				ss[i] = ss[i + 1];
				ss[i + 1] = temp;
				swaps++;

				if (ss[0] == '0')
					addExtra = true;
			}

			if (ss[0] == '0')
			{
				pos = -1;

				for (int i = 0; i < len - 2; i++)
				{
					if (ss[i] != '0')
					{
						pos = i;

						break;
					}
				}

				if (pos == -1)
					return INFINITY;

				for (int i = pos; i > 0; i--)
				{
					char temp = ss[i];

					ss[i] = ss[i - 1];
					ss[i - 1] = temp;
					swaps++;
				}
			}
			else if (addExtra)
			{
				int other = -1;

				for (int i = 0; i < len; i++)
				{
					if (i == ones || i == tens)
						continue;

					if (s[i] != '0')
					{
						other = i;

						break;
					}
				}

				if (other == -1)
					return INFINITY;

				for (int i = other; i > 0; i--)
				{
					char temp = ss[i];

					ss[i] = ss[i - 1];
					ss[i - 1] = temp;
					swaps++;
				}
			}

			if (ss[0] == '0')
				return INFINITY;

			return swaps;
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
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

	static class CMath
	{
		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

	}

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}
