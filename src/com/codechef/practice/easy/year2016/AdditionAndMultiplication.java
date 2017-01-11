package com.codechef.practice.easy.year2016;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class AdditionAndMultiplication
{
	static int n, q;
	static long[] arr;
	static Node[] tree;
	static long mod = (long) 1e9 + 7;
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		solve();

		out.flush();

		in.close();
		out.close();
	}

	static void solve()
	{
		n = in.nextInt();
	}

	static void buildTree(int node, int tSI, int tEI)
	{
		if (tSI > tEI)
			return;

		if (tSI == tEI)
		{
			tree[node] = new Node(arr[tSI], 0, 1, -1);

			return;
		}

		int mid = tSI + ((tEI - tSI) >> 1);

		buildTree(2 * node, tSI, mid);
		buildTree(2 * node + 1, mid + 1, tEI);

		long addValue = CMath.mod(tree[2 * node].value
				+ tree[2 * node + 1].value, mod);

		tree[node] = new Node(addValue, 0, 1, -1);
	}

	/**
	 * @param updateType 1 if addition, 2 if multiplication, 3 if initialization
	 */
	static void updateTree(int node, int tSI, int tEI, int rSI, int rEI,
			int updateType, int updateValue)
	{
		Node temp = tree[node];
		long range = tEI - tSI + 1;

		if (temp.reInitialize != -1)
		{
			temp.value = range * temp.reInitialize;
			temp.value = CMath.mod(temp.value, mod);

			if (tSI != tEI)
			{
				tree[2 * node].reInitialize = temp.reInitialize;
				tree[2 * node + 1].reInitialize = temp.reInitialize;
			}

			temp.reInitialize = -1;
		}
		else
		{
			if (temp.addValue != 0)
			{
				temp.value += CMath.mod(range * temp.addValue, mod);
				temp.value = CMath.mod(temp.value, mod);

				if (tSI != tEI)
				{
					tree[2 * node].addValue += temp.addValue;
					tree[2 * node].addValue = CMath.mod(
							tree[2 * node].addValue, mod);
					tree[2 * node + 1].addValue += temp.addValue;
					tree[2 * node + 1].addValue = CMath.mod(
							tree[2 * node + 1].addValue, mod);
				}

				temp.addValue = 0;
			}

			if (temp.mulValue != 1)
			{
				temp.value *= temp.mulValue;
				temp.value = CMath.mod(temp.value, mod);

				if (tSI != tEI)
				{

				}
			}
		}

		if (tSI > rEI || tEI < rSI)
			return;

		if (tSI >= rSI && tEI <= rEI)
		{
			if (updateType == 3)
			{
				temp.value = range * updateValue;
				temp.value = CMath.mod(temp.value, mod);

				if (tSI != tEI)
				{
					tree[2 * node].reInitialize = updateValue;
					tree[2 * node + 1].reInitialize = updateValue;
				}
				
				temp.addValue = 0;
				temp.mulValue = 1;

				return;
			}
			else if (updateType == 2)
			{
				temp.value *= updateValue;
				temp.value = CMath.mod(temp.value, mod);

				if (tSI != tEI)
				{
					tree[2 * node].value *= updateValue;
					tree[2 * node].addValue *= updateValue;
					tree[2 * node + 1].value *= updateValue;
					tree[2 * node + 1].addValue *= updateValue;
				}

				return;
			}
			else
			{
				temp.value += (range * updateValue);
				temp.value = CMath.mod(temp.value, mod);

				if (tSI != tEI)
				{
					tree[2 * node].addValue += updateValue;
					tree[2 * node].addValue = CMath.mod(
							tree[2 * node].addValue, mod);
					tree[2 * node + 1].addValue += updateType;
					tree[2 * node + 1].addValue = CMath.mod(
							tree[2 * node + 1].addValue, mod);
				}

				return;
			}
		}
	}

	static class Node
	{
		long value, addValue, mulValue, reInitialize;

		public Node(long value, long addValue, long mulValue, long reInitialize)
		{
			this.value = value;
			this.addValue = addValue;
			this.mulValue = mulValue;
			this.reInitialize = reInitialize;
		}
	}

	static class CMath
	{
		static long power(long number, int power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
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
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
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
