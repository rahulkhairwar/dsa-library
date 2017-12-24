package com.codeforces.competitions.year2017.round453div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int h;
		int[] hts;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			h = in.nextInt();
			hts = in.nextIntArray(h + 1);

			int pos = -1;

			for (int i = 1; i <= h; i++)
			{
				if (hts[i - 1] > 1 && hts[i] > 1)
				{
					pos = i - 1;

					break;
				}
			}

			if (pos == -1)
			{
				out.println("perfect");

				return;
			}

			out.println("ambiguous");

			List<Integer> list = new ArrayList<>();
			List<Integer> temp = new ArrayList<>();
			int ctr = 1;

			list.add(0);

			for (int i = 0; i <= h; i++)
			{
				for (int j = 0; j < hts[i]; j++)
				{
					out.print(list.get(0) + " ");
					temp.add(ctr++);
				}

				list = temp;
				temp = new ArrayList<>();
			}

			out.println();
			list = new ArrayList<>();
			temp = new ArrayList<>();
			list.add(0);
			ctr = 1;

			for (int i = 0; i <= pos; i++)
			{
				for (int j = 0; j < hts[i]; j++)
				{
					out.print(list.get(0) + " ");
					temp.add(ctr++);
				}

				list = temp;
				temp = new ArrayList<>();
			}

			out.print(list.get(0) + " " + list.get(1) + " ");
			temp.add(ctr++);
			temp.add(ctr++);

			for (int i = pos + 1; i <= h; i++)
			{
				for (int j = (i == pos + 1 ? 2 : 0); j < hts[i]; j++)
				{
					out.print(list.get(0) + " ");
					temp.add(ctr++);
				}

				list = temp;
				temp = new ArrayList<>();
			}
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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
