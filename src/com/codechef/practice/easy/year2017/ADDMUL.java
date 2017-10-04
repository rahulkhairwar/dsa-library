package com.codechef.practice.easy.year2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class ADDMUL
{
	public static void main(String[] args)
	{
		new ADDMUL(System.in, System.out);
	}

	private static class Solver
	{
		// BufferedReader in;
		static final int mod = (int) 1e9 + 7;
		int n, q;
		long[] arr;
		Node[] tree;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			q = in.nextInt();
			arr = in.nextLongArray(n);
			tree = new Node[n << 2];

			build(1, 0, n - 1);

			while (q-- > 0)
			{
				int type = in.nextInt();

				if (type < 4)
				{
					int x = in.nextInt() - 1;
					int y = in.nextInt() - 1;
					int val = in.nextInt();

					switch (type)
					{
						case 1:
							add(1, 0, n - 1, x, y, val);
							break;
						case 2:
							multiply(1, 0, n - 1, x, y, val);
							break;
						case 3:
							replace(1, 0, n - 1, x, y, val);
							break;
					}
				}
				else
				{
					int x = in.nextInt() - 1;
					int y = in.nextInt() - 1;

					out.println(query(1, 0, n - 1, x, y));
				}
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = new Node(arr[treeStart]);

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			build(left, treeStart, mid);
			build(right, mid + 1, treeEnd);
			tree[node] = new Node(CMath.mod(tree[left].sum + tree[right].sum, mod));
		}

		void add(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, long val)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				Node x = tree[node];

				x.sum += (treeEnd - treeStart + 1) * val;
				x.sum = CMath.mod(x.sum, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].add = CMath.mod(tree[left].add + val, mod);
					tree[right].add = CMath.mod(tree[right].add + val, mod);
				}

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			add(left, treeStart, mid, rangeStart, rangeEnd, val);
			add(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
			tree[node].sum = CMath.mod(tree[left].sum + tree[right].sum, mod);
		}

		void multiply(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, long val)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				Node x = tree[node];

				x.sum = CMath.mod(x.sum * val, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].mul = CMath.mod(tree[left].mul * val, mod);
					tree[right].mul = CMath.mod(tree[right].mul * val, mod);
					tree[left].add = CMath.mod(tree[left].add * val, mod);
					tree[right].add = CMath.mod(tree[right].add * val, mod);
				}

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			multiply(left, treeStart, mid, rangeStart, rangeEnd, val);
			multiply(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
			tree[node].sum = CMath.mod(tree[left].sum + tree[right].sum, mod);
		}

		void replace(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, long val)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				Node x = tree[node];

				x.sum = (treeEnd - treeStart + 1) * val;
				x.sum = CMath.mod(x.sum, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].mul = tree[right].mul = 0;
					tree[left].add = tree[right].add = val;
				}

				x.add = 0;
				x.mul = 1;

				return;
			}

			int mid = treeStart + treeEnd >> 1;
			int left = node << 1;
			int right = left + 1;

			replace(left, treeStart, mid, rangeStart, rangeEnd, val);
			replace(right, mid + 1, treeEnd, rangeStart, rangeEnd, val);
			tree[node].sum = CMath.mod(tree[left].sum + tree[right].sum, mod);
		}

		long query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			push(node, treeStart, treeEnd);

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tree[node].sum;

			int mid = treeStart + treeEnd >> 1;

			return CMath.mod(query(node << 1, treeStart, mid, rangeStart, rangeEnd) + query((node << 1) + 1, mid + 1,
					treeEnd, rangeStart, rangeEnd), mod);
		}

		void push(int node, int treeStart, int treeEnd)
		{
			Node x = tree[node];

			if (x.mul == 0)
			{
				x.sum = (treeEnd - treeStart + 1) * x.add;
				x.sum = CMath.mod(x.sum, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].mul = tree[right].mul = 0;
					tree[left].add = tree[right].add = x.add;
				}

				x.add = 0;
				x.mul = 1;

				return;
			}

			if (x.mul != 1)
			{
				x.sum = CMath.mod(x.sum * x.mul, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].mul = CMath.mod(tree[left].mul * x.mul, mod);
					tree[right].mul = CMath.mod(tree[right].mul * x.mul, mod);
					tree[left].add = CMath.mod(tree[left].add * x.mul, mod);
					tree[right].add = CMath.mod(tree[right].add * x.mul, mod);
				}

				x.mul = 1;
			}

			if (x.add != 0)
			{
				x.sum += (treeEnd - treeStart + 1) * x.add;
				x.sum = CMath.mod(x.sum, mod);

				if (treeStart != treeEnd)
				{
					int left = node << 1;
					int right = left + 1;

					tree[left].add = CMath.mod(tree[left].add + x.add, mod);
					tree[right].add = CMath.mod(tree[right].add + x.add, mod);
				}

				x.add = 0;
			}
		}

		static class Node
		{
			long sum, add, mul;

			@Override public String toString()
			{
				return "sum : " + sum + ", add : " + add + ", mul : " + mul;
			}

			Node(long sum)
			{
				this.sum = sum;
				mul = 1;
			}

		}

		// uncomment below line to change to BufferedReader
		// public Solver(BufferedReader in, PrintWriter out)
		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
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

		int nextInt()
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

		long nextLong()
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

		long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
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

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	private static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

	public ADDMUL(InputStream inputStream, OutputStream outputStream)
	{
		// uncomment below line to change to BufferedReader
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(inputStream));
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
