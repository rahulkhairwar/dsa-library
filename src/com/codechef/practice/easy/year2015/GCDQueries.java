package com.codechef.practice.easy.year2015;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class GCDQueries
{
	static int t, n, q, l, r, array[];
	static Node[] tree;
	static InputReader in;
	static OutputWriter out;

	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);
		
		getAttributes();
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void getAttributes()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			q = in.nextInt();
			
			array = new int[n];
			
			for (int i = 0; i < n; i++)
				array[i] = in.nextInt();
			
			int log = (int) Math.ceil(Math.log(n) / Math.log(2));
			int treeSize = 2 * (int) Math.pow(2, log);
			
//			System.out.println("n : " + n + ", log : " + log + ", treesize : " + treeSize);
			tree = new Node[treeSize];
			
			buildTree(1, 0, n - 1);
			
			for (int i = 0; i < q; i++)
			{
				l = in.nextInt() - 1;
				r = in.nextInt() - 1;
				
				if (l == 0)
				{
					int answer = queryTree(1, 0, n - 1, r + 1, n - 1);
					
					out.println(answer);
				}
				else if (r == n - 1)
				{
					int answer = queryTree(1, 0, n - 1, 0, l - 1);
					
					out.println(answer);
				}
				else
				{
					int left, right;
					
					left = queryTree(1, 0, n - 1, 0, l - 1);
					right = queryTree(1, 0, n - 1, r + 1, n - 1);
					
					out.println(gcd(left, right));
				}
			}
		}
	}
	
	static int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}
	
	static void buildTree(int cN, int tSI, int tEI)
	{
		if (tSI > tEI)
			return;
		
		if (tSI == tEI)
		{
//			System.out.println("cn : " + cN + ", tsi : " + tSI);
			tree[cN] = new Node(array[tSI]);
			
			return;
		}
		
		int mid, lGCD, rGCD;
		
		mid = tSI + (tEI - tSI) / 2;
		
		buildTree(2 * cN, tSI, mid);
		buildTree(2 * cN + 1, mid + 1, tEI);
		
		lGCD = tree[2 * cN].gcd;
		rGCD = tree[2 * cN + 1].gcd;
		
		tree[cN] = new Node(gcd(lGCD, rGCD));
	}
	
	static int queryTree(int cN, int tSI, int tEI, int rSI, int rEI)
	{
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return -1;
		
		if (tSI >= rSI && tEI <= rEI)
			return tree[cN].gcd;

		int mid, lGCD, rGCD;
		
		mid = tSI + (tEI - tSI) / 2;
		lGCD = queryTree(2 * cN, tSI, mid, rSI, rEI);
		rGCD = queryTree(2 * cN + 1, mid + 1, tEI, rSI, rEI);
		
		if (lGCD == -1)
			return rGCD;
		else if (rGCD == -1)
			return lGCD;
		
		return gcd(lGCD, rGCD);
	}
	
	static class Node
	{
		int gcd;
		boolean lazy;
		
		public Node(int gcd)
		{
			this.gcd = gcd;
			lazy = false;
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


/*

1
3 3
2 6 9
1 1
2 2
2 3

1
10 7
4 5 8 12 16 24 32 58 91 6
1 4
2 9
5 10
6 9
1 5
3 8
6 10

1
10 7
4 5 8 12 16 24 48 96 192 256
1 4
3 9
6 10
1 7
3 5
3 10
6 8

*/