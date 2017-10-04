package com.codeforces.competitions.year2017.round413div1_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver
	{
		static final int INF = (int) 1e9 + 7;
		int n;
		int[] prices, a, b;
		Shirt[] shirts;
		TreeSet<Shirt>[] sets;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			prices = in.nextIntArray(n);
			a = in.nextIntArray(n);
			b = in.nextIntArray(n);
			shirts = new Shirt[n];
			sets = new TreeSet[4];

			for (int i = 0; i < n; i++)
				shirts[i] = new Shirt(prices[i], a[i], b[i]);

			Comparator<Shirt> comparator = new Comparator<Shirt>()
			{
				@Override public int compare(Shirt o1, Shirt o2)
				{
					return Integer.compare(o1.price, o2.price);
				}
			};

			for (int i = 1; i < 4; i++)
				sets[i] = new TreeSet<>(comparator);

			for (int i = 0; i < n; i++)
			{
				sets[shirts[i].front].add(shirts[i]);
				sets[shirts[i].back].add(shirts[i]);
			}

			int m = in.nextInt();

			for (int i = 0; i < m; i++)
			{
				int col = in.nextInt();
				Shirt shirt = sets[col].pollFirst();

				if (shirt == null)
				{
					out.print(-1 + " ");

					continue;
				}

				while (shirt != null && shirt.price == INF)
					shirt = sets[col].pollFirst();

				if (shirt == null || shirt.price == INF)
					out.print(-1 + " ");
				else
				{
					out.print(shirt.price + " ");
					shirt.price = INF;
				}
			}
		}

		class Shirt
		{
			int price, front, back;

			public Shirt(int price, int front, int back)
			{
				this.price = price;
				this.front = front;
				this.back = back;
			}

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

	public TaskB(InputStream inputStream, OutputStream outputStream)
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

