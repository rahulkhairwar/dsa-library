package com.codeforces.practice.easy.year2015;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public final class CircularRMQ
{
	static int n, m;
	static long array[];
	static Node tree[];
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		CircularRMQ rmq = new CircularRMQ();
		
		writer = rmq.new OutputWriter(System.out);
		
		try
		{
			getAttributes();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		writer.flush();
		writer.close();
	}
	
	private static void getAttributes() throws IOException
	{
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String line[];
		int left, right;

		n = Integer.parseInt(bReader.readLine().split(" ")[0]);
		
		int log, treeSize;
		
		log = (int) Math.ceil(Math.log(n) / Math.log(2));
		treeSize = 2 * (int) Math.pow(2, log);
		
		array = new long[n];
		tree = new Node[treeSize];
		
		line = bReader.readLine().split(" ");
		
		for (int i = 0; i < n; i++)
			array[i] = Long.parseLong(line[i]);
		
		buildTree(1, 0, n - 1);
		
		m = Integer.parseInt(bReader.readLine().split(" ")[0]);
		
		for (int i = 0; i < m; i++)
		{
			line = bReader.readLine().split(" ");
			left = Integer.parseInt(line[0]);
			right = Integer.parseInt(line[1]);
			
			if (line.length == 2)
			{
				if (left > right)
					writer.println(Math.min(
							queryTree(1, 0, n - 1, left, n - 1),
							queryTree(1, 0, n - 1, 0, right)));
				else
					writer.println(queryTree(1, 0, n - 1, left, right));
			}
			else
			{
				long updateValue = Long.parseLong(line[2]);
				
				if (left > right)
				{
					updateTree(1, 0, n - 1, left, n - 1, updateValue);
					updateTree(1, 0, n - 1, 0, right, updateValue);
				}
				else
					updateTree(1, 0, n - 1, left, right, updateValue);
			}
		}
	}
	
	static void buildTree(int currNode, int tSI, int tEI)
	{
		if (tSI == tEI)
		{
			tree[currNode] = new Node(array[tSI], 0);

			return;
		}

		int mid = (tSI + tEI) / 2;

		buildTree(2 * currNode, tSI, mid);
		buildTree(2 * currNode + 1, mid + 1, tEI);

		tree[currNode] = new Node(Math.min(tree[2 * currNode].min,
				tree[2 * currNode + 1].min), 0);
	}
	
	static void updateTree(int currNode, int tSI, int tEI, int rSI, int rEI,
			long updateValue)
	{
		Node temp = tree[currNode];
		
		if (temp.update != 0)
		{
			temp.min += temp.update;

			if (tSI != tEI)
			{
				tree[2 * currNode].update += temp.update;
				tree[2 * currNode + 1].update += temp.update;
			}

			temp.update = 0;
		}

		if (tSI > rEI || tEI < rSI)
			return;

		if (tSI >= rSI && tEI <= rEI)
		{
			temp.min += updateValue;

			if (tSI != tEI)
			{
				tree[2 * currNode].update += updateValue;
				tree[2 * currNode + 1].update += updateValue;
			}

			return;
		}

		int mid = (tSI + tEI) / 2;

		updateTree(2 * currNode, tSI, mid, rSI, rEI, updateValue);
		updateTree(2 * currNode + 1, mid + 1, tEI, rSI, rEI, updateValue);

		temp.min = Math.min(tree[2 * currNode].min,
				tree[2 * currNode + 1].min);
	}
	
	static long queryTree(int currNode, int tSI, int tEI, int rSI, int rEI)
	{
		Node temp = tree[currNode];
		
		if (temp.update != 0)
		{
			temp.min += temp.update;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].update += temp.update;
				tree[2 * currNode + 1].update += temp.update;
			}
			
			temp.update = 0;
		}
		
		if (tSI > rEI || tEI < rSI)
			return Long.MAX_VALUE;
		
		if (tSI >= rSI && tEI <= rEI)
			return temp.min;
		
		int mid;
		long lCM, rCM;
		
		mid = (tSI + tEI) / 2;
		lCM = queryTree(2 * currNode, tSI, mid, rSI, rEI);
		rCM = queryTree(2 * currNode + 1, mid + 1, tEI, rSI, rEI);
		
		return Math.min(lCM, rCM);
	}
	
	static class Node
	{
		long min, update;
		
		public Node(long min, long update)
		{
			this.min = min;
			this.update = update;
		}
		
	}

	class InputReader
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

	class OutputWriter
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

2
-1 -1
10
0 0
0 0
0 0 1
0 0
1 1
0 0 -1
0 0 0
0 0 1
1 1 0
0 0 -1

2
-1 -1
5
0 0
0 0
0 0 1
0 0
1 1


*/

