package com.codeforces.competitions.year2017.goodbye2017;

import java.awt.*;
import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		double r;
		double[] x, heights;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			r = in.nextInt();
			x = new double[n];
			heights = new double[n];
			heights[0] = r;

			for (int i = 0; i < n; i++)
				x[i] = in.nextInt();

			for (int i = 1; i < n; i++)
			{
				double max = r;

				for (int j = 0; j < i; j++)
				{
					// check for ith one falling.
					if (willTouch(j, i))
						max = Math.max(max, getHeight(j, i));
				}

				heights[i] = max;
			}

			for (int i = 0; i < n; i++)
				out.printf("%.10f ", heights[i]);
		}

		boolean willTouch(int a, int b)
		{
			return Point.distance(x[a], 0, x[b], 0) <= 2 * r;
		}

		double getHeight(int a, int b)
		{
			return heights[a] + Math.sqrt(-Math.pow(x[b] - x[a], 2) + Math.pow(r + r, 2));
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

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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
