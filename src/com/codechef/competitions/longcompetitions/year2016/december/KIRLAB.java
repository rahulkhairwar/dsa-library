package com.codechef.competitions.longcompetitions.year2016.december;

import java.io.*;
import java.util.*;

class KIRLAB
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
		int t, n;
		int[] arr, sp, depth;
		Node[] nodes;
		List<Integer> primes;
		List<Integer>[] primeFactors, pos;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			pre();

			t = in.nextInt();
			primeFactors = new ArrayList[(int) 1e7 + 5];
			pos = new List[(int) 1e7 + 5];
			nodes = new Node[(int) 1e5 + 5];

			int[] time = new int[(int) 1e7 + 5];
			int currTime = -1;

			while (t-- > 0)
			{
				n = in.nextInt();
				arr = new int[n];
				depth = new int[n];
				currTime++;

				List<Integer> used = new ArrayList<>();

				for (int i = 0; i < n; i++)
					nodes[i] = new Node();

				for (int i = 0; i < n; i++)
				{
					arr[i] = in.nextInt();

					if (primeFactors[arr[i]] == null)
						factorize(arr[i]);

					for (int x : primeFactors[arr[i]])
					{
						if (pos[x] == null || time[x] != currTime)
						{
							pos[x] = new ArrayList<>();
							time[x] = currTime;
							used.add(x);
						}

						pos[x].add(i);
					}
				}

				for (int x : used)
				{
					if (pos[x] == null)
						continue;

					int size = pos[x].size();

					for (int i = 1; i < size; i++)
						nodes[pos[x].get(i - 1)].adj.add(pos[x].get(i));
				}

				int ans = 0;

				for (int i = 0; i < n; i++)
					if (!nodes[i].visited)
						ans = Math.max(ans, dfs(i));

				out.println(Math.max(ans, 1));
			}
		}

		int dfs(int node)
		{
			nodes[node].visited = true;

			int max = 0;

			for (int x : nodes[node].adj)
			{
				if (nodes[x].visited)
					max = Math.max(max, nodes[x].depth);
				else
					max = Math.max(max, dfs(x));
			}

			nodes[node].depth = max + 1;

			return max + 1;
		}

		void factorize(int num)
		{
			primeFactors[num] = new ArrayList<>();

			int temp = num;

			while (temp > 1)
			{
				int x = sp[temp];

				primeFactors[num].add(sp[temp]);

				while (temp % x == 0)
					temp /= x;
			}
		}

		void pre()
		{
			int MAX = (int) 1e7 + 5;
			boolean[] v = new boolean[MAX];

			sp = new int[MAX];
			primes = new ArrayList<>();
			primes.add(2);

			for (int i = 2; i < MAX; i += 2)
				sp[i] = 2;

			for (int i = 3; i < MAX; i += 2)
			{
				if (!v[i])
				{
					primes.add(i);
					sp[i] = i;

					for (int j = i; (long) j * i < MAX; j += 2)
					{
						if (!v[j * i])
						{
							v[j * i] = true;
							sp[j * i] = i;
						}
					}
				}
			}
		}

		class Node
		{
			boolean visited;
			int depth;
			List<Integer> adj;

			public Node()
			{
				adj = new ArrayList<>();
			}

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
