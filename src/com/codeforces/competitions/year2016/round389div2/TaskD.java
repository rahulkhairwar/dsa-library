package com.codeforces.competitions.year2016.round389div2;

import java.io.*;
import java.util.*;

public final class TaskD
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int k, n;
		String[] s;
		int[] scores;
		HashMap<String, PriorityQueue<Integer>> pal, notPal;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			k = in.nextInt();
			n = in.nextInt();
			s = new String[k];
			scores = new int[k];
			pal = new HashMap<>();
			notPal = new HashMap<>();

			for (int i = 0; i < k; i++)
			{
				s[i] = in.next();
				scores[i] = in.nextInt();

				if (isPal(s[i]))
				{
					PriorityQueue<Integer> queue = pal.get(s[i]);

					if (queue == null)
					{
						queue = new PriorityQueue<>(Collections.reverseOrder());
						pal.put(s[i], queue);
					}

					queue.add(scores[i]);
				}
				else
				{
					PriorityQueue<Integer> queue = notPal.get(s[i]);

					if (queue == null)
					{
						queue = new PriorityQueue<>(Collections.reverseOrder());
						notPal.put(s[i], queue);
					}

					queue.add(scores[i]);
				}
			}

			int max = 0;
			int min = Integer.MAX_VALUE;

			for (Map.Entry<String, PriorityQueue<Integer>> entry : pal.entrySet())
			{
				int total = 0;
				PriorityQueue<Integer> queue = entry.getValue();

				while (queue.size() > 1)
				{
					int f, s;

					f = queue.poll();
					s = queue.poll();

					if (f < 0)
						break;

					if (s < 0)
					{
						if (f + s > 0)
						{
							min = Math.min(min, s);
							total += f + s;
						}
						else
							min = Math.min(min, f);
					}
					else
						total += f + s;
				}

/*				if (queue.size() == 1)
					min = Math.min(min, queue.peek());*/

				max += total;
			}

			int max2 = 0;

			for (Map.Entry<String, PriorityQueue<Integer>> entry : notPal.entrySet())
			{
				int total = 0;
				PriorityQueue<Integer> queue = entry.getValue();
				PriorityQueue<Integer> reverseQueue =
						notPal.get(new StringBuilder(entry.getKey()).reverse().toString());

				if (reverseQueue == null)
					continue;

				while (queue.size() > 0 && reverseQueue.size() > 0)
				{
					int val = queue.poll() + reverseQueue.poll();

					if (val > 0)
						total += val;
					else
						break;
				}

				max2 += total;
			}

			int max3 = 0;

			for (Map.Entry<String, PriorityQueue<Integer>> entry : pal.entrySet())
			{
				PriorityQueue<Integer> queue = entry.getValue();

				if (queue.size() == 0)
					continue;

				max3 = Math.max(max3, queue.poll());
			}

			if (min < 0)
				out.println(Math.max(max - min + max2, max + max2 + max3));
			else
				out.println(max + max2 + max3);
		}

		boolean isPal(String s)
		{
			int len = s.length();

			for (int i = 0, j = len - 1; i < j; i++, j--)
				if (s.charAt(i) != s.charAt(j))
					return false;

			return true;
		}

		public Solver(InputReader in, OutputWriter out)
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*

3 3
xxx 2
aaa 10
aaa -1

3 3
aba 5
aba -2
vvv 1

*/
