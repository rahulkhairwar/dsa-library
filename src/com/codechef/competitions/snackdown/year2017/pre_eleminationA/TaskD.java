package com.codechef.competitions.snackdown.year2017.pre_eleminationA;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class TaskD
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);

/*		try
		{
			in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
					+ "Workspace/Competitive Programming/src/com/checker/input.txt")));
			out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/checker/output.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}*/

		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int t, n, l, a, b;
		Snake[] snakes;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				l = in.nextInt();
				a = in.nextInt();
				b = in.nextInt();
				snakes = new Snake[n];

				double mean = 0;

				for (int i = 0; i < n; i++)
				{
					snakes[i] = new Snake(in.nextInt());
					mean += snakes[i].start;
				}

				Arrays.sort(snakes, new Comparator<Snake>()
				{
					@Override public int compare(Snake o1, Snake o2)
					{
						return Integer.compare(o1.start, o2.start);
					}
				});

				mean /= n;

				System.out.println("mean : " + mean);

				int floor = (int) Math.floor(mean);
				int ceil = (int) Math.ceil(mean);

				out.println(Math.min(find(floor, snakes), find(ceil, snakes)));
			}
		}

		long find(int center, Snake[] org)
		{
			// center of snake line = center. So, start = center - (n * l) / 2.
			int start = center - (n * l) / 2;
			// or, start - 1, or start + 1

			return Math.min(helper(org, start), Math.min(helper(org, start - 1), helper(org, start + 1)));
		}

		long helper(Snake[] temp, int start)
		{
			Snake[] snakes = new Snake[temp.length];
			long cost = 0;

			for (int i = 0; i < n; i++)
				snakes[i] = new Snake(temp[i].start);

			for (int i = 0; i < n; i++)
			{
				snakes[i].moves = start - snakes[i].start;
				snakes[i].start = start;
				cost += Math.abs(snakes[i].moves);
				start += l;
			}

			start -= n * l;
//			System.out.println("center : " + center + ", cost : " + cost + ", start : " + start);

			if (start < a)
			{
				int extra = a - start;

				for (int i = 0; i < n; i++)
				{
					if (snakes[i].moves >= 0)
					{
						snakes[i].moves += extra;
						cost += extra;
					}
					else
					{
						snakes[i].moves += extra;
						cost += snakes[i].moves;
					}
				}
			}
			else if (start + n * l > b)
			{
				int extra = start + n * l - b;

				for (int i = 0; i < n; i++)
				{
					if (snakes[i].moves <= 0)
					{
						snakes[i].moves -= extra;
						cost += extra;
					}
					else
					{
						snakes[i].moves -= extra;
						cost += snakes[i].moves;
					}
				}
			}

			long x = 0;

			for (int i = 0; i < n; i++)
				x += Math.abs(snakes[i].moves);

			return x;
		}

		static class Snake
		{
			int start, moves;

			public Snake(int start)
			{
				this.start = start;
			}

			@Override public String toString()
			{
				return "st : " + start + ", mv : " + moves;
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

}

/*

2
3 4 11 23
10 11 30
3 4 11 40
10 11 30

1
6 5 20 93
44 39 41 1 21 21
: 38

1
5 1 1 25
9 6 16 43 9
: 39

*/
