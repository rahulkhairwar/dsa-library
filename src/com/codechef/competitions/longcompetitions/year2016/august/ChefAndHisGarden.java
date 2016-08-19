package com.codechef.competitions.longcompetitions.year2016.august;

import java.io.*;
import java.util.*;
import java.util.List;

class ChefAndHisGarden
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		int t, n;
		long[] heights, growth;
		long infinity = Long.MAX_VALUE - 1;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				heights = new long[n];
				growth = new long[n];

				for (int i = 0; i < n; i++)
				{
					heights[i] = in.nextInt();
					growth[i] = in.nextInt();
				}

				if (n == 1)
				{
					out.println("1\n0 Inf");

					continue;
				}

				Interval[] intervals = new Interval[n];

				for (int i = 1; i < n; i++)
					intervals[i] = createInterval(heights[i - 1], heights[i], growth[i - 1], growth[i]);

				boolean valid;
				boolean zeroSlope;

				if (intervals[0].type == 2)
					valid = false;
				else
					valid = true;

				if (intervals[0].type == 2)
					zeroSlope = true;
				else
					zeroSlope = false;

				long startOne, endOne, max;

				startOne = intervals[0].left;
				endOne = intervals[0].right;
				max = intervals[0].right;

				for (int i = 1; i < n - 1; i++)
				{
					if (intervals[i].type == 2 || intervals[i].type == intervals[i - 1].type)
					{
						valid = false;

						if (intervals[i].type == 2)
							zeroSlope = true;
					}

					startOne = Math.max(startOne, intervals[i].left);
					endOne = Math.min(endOne, intervals[i].right);
					max = Math.max(max, intervals[i].right);
				}

				if (valid)	// already in zig-zag sequence => so, 1 + (0 or 1) intervals possible
				{
					if (endOne == infinity && max == infinity)	// i.e., none of the slopes get inverted, so the same
					// formation remains forever.
					{
						out.println("1\n0 Inf");

						continue;
					}

					if (max == infinity)	// i.e., some slope(s) never get inverted, but some do. So, the end time
					// for zig-zag interval shall be the minimum time at which any slope gets inverted
					{
						out.println("1\n0 " + endOne);

						continue;
					}

					for (int i = 0; i < n; i++)
						heights[i] += (max + 1) * growth[i];

					System.out.println("max : " + max + ", heights : " + Arrays.toString(heights));

					boolean add = false;

					for (int i = 1; i < n; i++)
					{
						if (heights[i] == heights[i - 1])
							add = true;
					}

					if (add)
					{
						max++;

						for (int i = 0; i < n; i++)
							heights[i] += growth[i];
					}

					long startTwo, endTwo, maxTwo;

					startTwo = intervals[0].left;
					endTwo = intervals[0].right;
					maxTwo = intervals[0].right;
					valid = true;

					for (int i = 1; i < n - 1; i++)
					{
						if (intervals[i].type == 2 || intervals[i].type == intervals[i - 1].type)
						{
							valid = false;

							if (intervals[i].type == 2)
								zeroSlope = true;
						}

						startTwo = Math.max(startTwo, intervals[i].left);
						endTwo = Math.min(endTwo, intervals[i].right);
						maxTwo = Math.max(maxTwo, intervals[i].right);

//						startOne = Math.max(startOne, intervals[i].left);
//						endOne = Math.min(endOne, intervals[i].right);
//						max = Math.max(max, intervals[i].right);
					}

					// i.e., all slopes get inverted at some point of time, so we surely have 2 intervals of time
					// with zig-zag sequences.
					// so, the first interval will be [0, endOne], and the second shall be when all the slopes have
					// inverted to infinity, i.e., [max or max + 1, infinity]

					// ** have to change max actually I guess.

					if (endOne + 1 == max)
					{
						out.println("1\n0 Inf");

						continue;
					}

					out.println("2\n0 " + endOne + "\n" + max + " Inf");
				}
				else	// not already in zig-zag sequence => so, 0 or 1 intervals possible
				{
					if (endOne == infinity)	// i.e., minimum time for a slope to get inverted is infinity(impossible)
					{
						out.println("0");

						continue;
					}

					if (max == infinity)	// i.e., some intervals might get inverted at some point of time, but
					// some won't ever get inverted
					{
						out.println("0");

						continue;
					}


				}
			}
		}

		/**
		 * Information about the type of slope (small->big = 0 || big->small = 1 || 0-slope = 2), along with the
		 * starting and ending times of the slope.
		 * @param hOne
		 * @param hTwo
		 * @param rOne
		 * @param rTwo
		 * @return
		 */
		Interval createInterval(long hOne, long hTwo, long rOne, long rTwo)
		{
			if (hOne == hTwo)
			{
				if (rOne == rTwo)
					return new Interval(2, 0, infinity);
				else if (rOne > rTwo)
					return new Interval(1, 1, infinity);
				else
					return new Interval(0, 1, infinity);
//					return new Interval(2, 0, 0);
			}
			else if (hOne > hTwo)
			{
				if (rOne >= rTwo)
					return new Interval(1, 0, infinity);
				else
				{
					long end = (hTwo - hOne) / (rOne - rTwo) - 1;

					if ((hTwo - hOne) % (rOne - rTwo) != 0)
						end++;

					return new Interval(1, 0, end);
				}
			}
			else	// hOne < hTwo
			{
				if (rOne <= rTwo)
					return new Interval(0, 0, infinity);
				else
				{
					long end = (hTwo - hOne) / (rOne - rTwo) - 1;

					if ((hTwo - hOne) % (rOne - rTwo) != 0)
						end++;

					return new Interval(0, 0, end);
				}
			}
		}

		void solve3()
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				heights = new long[n];
				growth = new long[n];

				for (int i = 0; i < n; i++)
				{
					heights[i] = in.nextInt();
					growth[i] = in.nextInt();
				}

				if (n == 1)
				{
					out.println("1\n0 Inf");

					continue;
				}

				Temp[] temp = new Temp[n - 1];
				temp[0] = createTemp(heights[1], heights[0], growth[1], growth[0]);

				for (int i = 2; i < n; i++)
					temp[i - 1] = createTemp(heights[i], heights[i - 1], growth[i], growth[i - 1]);

				int prev = temp[0].type;
				boolean valid = true;
				Interval one = new Interval(temp[0].start, temp[0].end);
				one.max = temp[0].end;

//				System.out.println("temp[0] => type : " + temp[0].type + ", start : " + temp[0].start + ", end : "
//					+ temp[0].end);

				for (int i = 1; i < n - 1; i++)
				{
					if (temp[i].type == prev || temp[i].type == 2)
						valid = false;

					one.left = Math.max(one.left, temp[i].start);
					one.right = Math.min(one.right, temp[i].end);
					one.max = Math.max(one.max, temp[i].end);

//					System.out.println("i : " + i + ", temp[i] => type : " + temp[i].type + ", start : " + temp[i]
//							.start + ", end : " + temp[i].end);

					if (prev == 0)
						prev = 1;
					else if (prev == 1)
						prev = 0;
				}

				if (!valid)
				{
//					System.out.println("valid : " + valid + ", one : " + one.toString() + ", max : " + one.max);
					if (one.right == infinity)
					{
						out.println("0");

						continue;
					}

					boolean add = false;
					heights[0] += (one.right + 1) * growth[0];

					for (int i = 1; i < n; i++)
					{
						heights[i] += (one.right + 1) * growth[i];

						if (heights[i] == heights[i - 1])
							add = true;
					}

					long time = one.right + 1;

					if (add)
					{
						time++;

						for (int i = 0; i < n; i++)
							heights[i] += growth[i];
					}

//					System.out.println("heights : " + Arrays.toString(heights));

					temp[0] = createTemp(heights[1], heights[0], growth[1], growth[0]);
					prev = temp[0].type;

					for (int i = 2; i < n; i++)
						temp[i - 1] = createTemp(heights[i], heights[i - 1], growth[i], growth[i - 1]);

					valid = true;

					one = new Interval(0, infinity);
					one.max = temp[0].end;

					for (int i = 1; i < n - 1; i++)
					{
						if (temp[i].type == prev || temp[i].type == 2)
							valid = false;

						one.left = Math.max(one.left, temp[i].start);
						one.right = Math.min(one.right, temp[i].end);
						one.max = Math.max(one.max, temp[i].end);

						if (prev == 0)
							prev = 1;
						else if (prev == 1)
							prev = 0;
					}

					if (!valid)
						out.println(0);
					else
						out.println("1\n" + time + " " + (one.right == infinity ? "Inf" : one.right));
				}
				else
				{
					if (one.right == infinity)
					{
						out.println("1\n0 Inf");

						continue;
					}

					if (one.max == infinity)
					{
						out.println("1\n0 " + one.right);

						continue;
					}

					Interval two;
					boolean add = false;
					heights[0] += (one.max + 1) * growth[0];

					for (int i = 1; i < n; i++)
					{
						heights[i] += (one.max + 1) * growth[i];

						if (heights[i] == heights[i - 1])
							add = true;
					}

					long time = one.max + 1;

					if (add)
					{
						time++;

						for (int i = 0; i < n; i++)
							heights[i] += growth[i];
					}

					temp[0] = createTemp(heights[1], heights[0], growth[1], growth[0]);
					prev = temp[0].type;
					one.max = temp[0].end;

					for (int i = 2; i < n; i++)
						temp[i - 1] = createTemp(heights[i], heights[i - 1], growth[i], growth[i - 1]);

					for (int i = 1; i < n - 1; i++)
					{
						if (temp[i].type == prev || temp[i].type == 2)
							valid = false;

						one.left = Math.max(one.left, temp[i].start);
						one.right = Math.min(one.right, temp[i].end);
						one.max = Math.max(one.max, temp[i].end);

						if (prev == 0)
							prev = 1;
						else if (prev == 1)
							prev = 0;
					}

					if (!valid)
						out.println("1\n" + 0 + " " + (one.right == infinity ? "Inf" : one.right));
					else
					{
						two = new Interval(time, infinity);

						if (two.left - 1 == one.right)
							out.println("1\n" + 0 + " Inf");
						else
							out.println("2\n" + 0 + " " + one.right + "\n" + time + " " + (two.right == infinity ?
																						   "Inf" : two.right));
					}
				}
			}
		}

		/**
		 * type =>
		 * 0 : small-big
		 * 1 : big-small
		 * 2 : level
		 */
		Temp createTemp(long hOne, long hTwo, long rOne, long rTwo)
		{
			Temp temp;

			if (hOne < hTwo)
			{
				long start = 0;
				long end;

				if (rOne <= rTwo)
					end = infinity;
				else
				{
					end = (hOne - hTwo) / (rTwo - rOne) - 1;

					if ((hOne - hTwo) % (rTwo - rOne) != 0)
						end++;
				}

				temp = new Temp(start, end, 0);
			}
			else if (hOne > hTwo)
			{
				long start = 0;
				long end;

				if (rOne >= rTwo)
					end = infinity;
				else
				{
					end = (hOne - hTwo) / (rTwo - rOne) - 1;

					if ((hOne - hTwo) % (rTwo - rOne) != 0)
						end++;
				}

				temp = new Temp(start, end, 1);
			}
			else
			{
				long start = 0;
				long end;

				if (rOne == rTwo)
					end = infinity;
				else
					end = 0;

				temp = new Temp(start, end, 2);
			}

			return temp;
		}

		class Temp
		{
			long start, end;
			int type;

			public Temp()
			{}

			public Temp(long start, long end, int type)
			{
				this.start = start;
				this.end = end;
				this.type = type;
			}

		}

		class Interval
		{
			int type;
			long left, right, max;

			public Interval(int type, long left, long right)
			{
				this.type = type;
				this.left = left;
				this.right = right;
			}

			public Interval(long left, long right)
			{
				this.left = left;
				this.right = right;
			}

			@Override public String toString()
			{
				if (right == infinity)
					return left + " Inf\n";
				else
					return left + " " + right + "\n";
			}

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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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

1
7
2 4
4 2
3 3
5 1
2 4
4 2
1 5
: 2 => [0, 0], [2, Inf]

1
7
2 4
4 2
3 3
5 1
2 4
4 6
1 9
: 1 => [0, 0]

3
3
0 1
2 2
0 3
2
2 1
1 2
3
1 1
2 2
3 3
: sample test case

1
3
0 1
2 2
0 3

3
3
4 2
4 3
4 2
3
4 2
4 1
4 2
3
4 2
4 2
4 2
: 1 => [1, Inf]
: 1 => [1, Inf]
: 0

1
3
4 2
4 3
4 2

1
3
4 2
4 2
4 2

3
3
0 1
2 2
0 3
2
2 1
1 2
3
1 1
2 2
3 3
: 1 => [0, 1]
: 2 => [0, 0], [2, Inf]
: 0

1
3
0 1
2 2
0 3

1
2
2 1
1 2

1
3
1 1
2 2
3 3

1
3
7 7
6 10
7 7
: 1 => [0, Inf]

1
2
1 3
2 2
: 2 => [0, 0], [2, Inf]

1
3
2 2
7 1
4 5
: 2 => [0, 0], [6, Inf]

2
4
4 1
2 1
3 2
4 0
3
4 3
4 2
4 3
: 1 => [1, Inf]
: 1 => [1, Inf]

1
4
4 1
2 1
3 2
4 0

1
4
4 0
3 2
2 1
4 1

*/
