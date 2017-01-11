package com.codechef.competitions.longcompetitions.year2016.december;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class KTHMAX
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
		int t, n, q;
		int[] arr, tree;
		long[] count, cum;
		Map<Integer, Long> map;
		Map<Integer, Point> pos;
		Map<Integer, List<Integer>> positions;
		TreeSet<Integer> set;
		InputReader in;
		OutputWriter out;

		void solve()
		{
			t = in.nextInt();

			arr = new int[(int) 1e5 + 5];
			tree = new int[(int) 4e5 + 5];
			count = new long[(int) 1e5 + 5];

			while (t-- > 0)
			{
				n = in.nextInt();
				q = in.nextInt();
				map = new HashMap<>();
				pos = new HashMap<>();
				positions = new HashMap<>();
				set = new TreeSet<>(Collections.reverseOrder());

				for (int i = 0; i < n; i++)
				{
					arr[i] = in.nextInt();
					map.put(arr[i], 0L);
					set.add(arr[i]);

					List<Integer> list = positions.get(arr[i]);

					if (list == null)
					{
						list = new ArrayList<>();
						positions.put(arr[i], list);
					}

					list.add(i);
				}

				Arrays.fill(tree, 0);
				build(1, 0, n - 1);
				Arrays.fill(count, 1);

				for (int i = 0; i < n; i++)
				{
					long lt, rt;
					int fl, fr;

					lt = rt = 0;
					fl = fr = 0;

					if (i > 0)
					{
						fl = findLeft(i);
						lt = i - fl;
					}

					if (i < n - 1)
					{
						fr = findRight(i);
						rt = fr - i;
						count[i] += rt;
					}

					Point point = pos.get(arr[i]);

					if (point == null || point.y < fl)
					{
						count[i] += lt;
						count[i] += lt * rt;
						point = new Point(i, fr);
						pos.put(arr[i], point);
					}
					else
					{
						Long cnt = map.get(arr[i]);
						int index = Collections.binarySearch(positions.get(arr[i]), i);

						if (index > 0)
						{
							int prevPos = positions.get(arr[i]).get(index - 1);

							fl = prevPos + 1;
							lt = i - fl;
						}

						count[i] += lt;
						count[i] += lt * rt;
						map.put(arr[i], cnt + count[i]);
						point = new Point(i, fr);
						pos.put(arr[i], point);

						continue;
					}

					Long cnt = map.get(arr[i]);

					map.put(arr[i], cnt + count[i]);
				}

				List<Integer> list = new ArrayList<>();

				list.addAll(set);

				int size = list.size();

				cum = new long[size];

				for (int i = 0; i < size; i++)
					cum[i] = map.get(list.get(i));

				for (int i = 1; i < size; i++)
					cum[i] += cum[i - 1];

				while (q-- > 0)
				{
					long p = in.nextLong();
					int low, mid, high, ans;

					low = ans = 0;
					high = size - 1;

					while (low <= high)
					{
						mid = low + high >> 1;

						if (cum[mid] >= p)
						{
							if (mid == 0 || cum[mid - 1] < p)
							{
								ans = mid;

								break;
							}

							high = mid - 1;
						}
						else
							low = mid + 1;
					}

					out.println(list.get(ans));
				}
			}
		}

		int findLeft(int ind)
		{
			int low, high, mid;

			low = 0;
			high = ind;

			while (low <= high)
			{
				mid = low + high >> 1;

				int max = query(1, 0, n - 1, mid, ind);

				if (max > arr[ind])
					low = mid + 1;
				else
				{
					if (mid == 0 || query(1, 0, n - 1, mid - 1, ind) > arr[ind])
						return mid;

					high = mid - 1;
				}
			}

			return low;
		}

		int findRight(int ind)
		{
			int low, high, mid;

			low = ind;
			high = n - 1;

			while (low <= high)
			{
				mid = low + high >> 1;

				int max = query(1, 0, n - 1, ind, mid);

				if (max > arr[ind])
					high = mid - 1;
				else
				{
					if (mid == n - 1 || query(1, 0, n - 1, ind, mid + 1) > arr[ind])
						return mid;

					low = mid + 1;
				}
			}

			return low;
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = arr[treeStart];

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			build(node << 1, treeStart, mid);
			build((node << 1) + 1, mid + 1, treeEnd);

			tree[node] = Math.max(tree[node << 1], tree[(node << 1) + 1]);
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node];

			int mid, left, right;

			mid = treeStart + treeEnd >> 1;
			left = query(node << 1, treeStart, mid, rangeStart, rangeEnd);
			right = query((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return Math.max(left, right);
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
