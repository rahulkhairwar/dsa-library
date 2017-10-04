package com.codeforces.competitions.year2017.round413div1_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver
	{
		int n, t, k, d;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			t = in.nextInt();
			k = in.nextInt();
			d = in.nextInt();

			int x;
			int min = x = (int) (Math.ceil((double) n / k) * t);
			int carrots = 0;
			int t1 = d;
			int t2 = 0;

			for (int i = 0; ; i++)
			{
				if (i == d)
					t1++;

				if (i > d)
				{
					if (t1 % t == 0)
						carrots += k;

					t1++;
				}

				if (t2 > 0 && t2 % t == 0)
					carrots += k;

				t2++;

				if (carrots >= n)
				{
					min = Math.min(min, i);

					break;
				}

			}

			if (min == x)
				out.println("NO");
			else
				out.println("YES");
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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
//		uncomment below line to change to BufferedReader
//		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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

