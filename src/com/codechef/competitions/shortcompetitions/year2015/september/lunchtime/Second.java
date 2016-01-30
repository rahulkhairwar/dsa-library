package com.codechef.competitions.shortcompetitions.year2015.september.lunchtime;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Set;

// Many lists
class Second
{
	static int n, m, l, r;
	static Set<Integer> allsets[];
	static Node tree[];
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Second second = new Second();
		
		reader = second.new InputReader(System.in);
		writer = second.new OutputWriter(System.out);
		
		getAttributes3();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		
		allsets = new HashSet[n];
		
		for (int i = 0; i < n; i++)
			allsets[i] = new HashSet<Integer>();
		
		int log, treeSize;
		
		log = (int) Math.ceil(Math.log(n) / Math.log(2));
		treeSize = 2 * (int) Math.pow(2, log);
		
		tree = new Node[treeSize];
		
		buildTree(1, 0, n - 1);
		
		for (int i = 0; i < m; i++)
		{
			int a = reader.nextInt();
			
			if (a == 0)
			{
				l = reader.nextInt() - 1;
				r = reader.nextInt() - 1;
				int x = reader.nextInt();
				
				add(1, 0, n - 1, l, r, x);
			}
			else
			{
				int q = reader.nextInt() - 1;
				
				writer.println(query(1, 0, n - 1, q, q));
			}
		}
	}
	
	static void getAttributes2()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		
		//Hashtable<Integer, HashSet<Integer>> table = new Hashtable<Integer, HashSet<Integer>>(1000000);
		Map<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(1000000);
		
		for (int i = 0; i < n; i++)
			map.put(i, new HashSet<Integer>());
		
		for (int i = 0; i < m; i++)
		{
			int a = reader.nextInt();
			
			if (a == 0)
			{
				l = reader.nextInt() - 1;
				r = reader.nextInt() - 1;
				int x = reader.nextInt();
				
				for (int j = l; j <= r; j++)
					map.get(j).add(x);
			}
			else
			{
				int q = reader.nextInt() - 1;
				
				writer.println(map.get(q).size());
			}
		}
	}
	
	static void getAttributes3()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		
		Hashtable<Integer, HashSet<Integer>> table = new Hashtable<Integer, HashSet<Integer>>(1000000);
		//Map<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(1000000);
		
		for (int i = 0; i < n; i++)
			table.put(i, new HashSet<Integer>());
		
		for (int i = 0; i < m; i++)
		{
			int a = reader.nextInt();
			
			if (a == 0)
			{
				l = reader.nextInt() - 1;
				r = reader.nextInt() - 1;
				int x = reader.nextInt();
				
				//add(1, 0, n - 1, l, r, x);
				for (int j = l; j <= r; j++)
					table.get(j).add(x);
			}
			else
			{
				int q = reader.nextInt() - 1;
				
				//writer.println(query(1, 0, n - 1, q, q));
				writer.println(table.get(q).size());
			}
		}
	}
	
	static void buildTree(int cN, int tSI, int tEI)
	{
		if (tSI > tEI)
			return;
		
		if (tSI == tEI)
		{
			tree[cN] = new Node(allsets[tSI], new HashSet<Integer>(), 0);
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		buildTree(2 * cN, tSI, mid);
		buildTree(2 * cN + 1, mid + 1, tEI);
		
		tree[cN] = new Node(new HashSet<Integer>(), new HashSet<Integer>(), 0);
	}
	
	static void add(int cN, int tSI, int tEI, int rSI, int rEI, int x)
	{
		Node temp = tree[cN];
		
		if (temp.update.size() != 0)
		{
			temp.set.addAll(temp.update);
			
			if (tSI != tEI)
			{
				tree[2 * cN].update.addAll(temp.update);
				tree[2 * cN + 1].update.addAll(temp.update);
			}
			
			temp.update = new HashSet<Integer>();
		}
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;
		
		if (tSI >= rSI && tEI <= rEI)
		{
			temp.set.add(x);
			
			if (tSI != tEI)
			{
				tree[2 * cN].update.add(x);
				tree[2 * cN + 1].update.add(x);
			}
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		add(2 * cN, tSI, mid, rSI, rEI, x);
		add(2 * cN + 1, mid + 1, tEI, rSI, rEI, x);
		
		temp.set = new HashSet<Integer>();
		temp.set.addAll(tree[2 * cN].set);
		temp.set.addAll(tree[2 * cN + 1].set);
	}
	
	static int query(int cN, int tSI, int tEI, int rSI, int rEI)
	{
		//System.out.println("in query, cn : " + cN + ", tsi : " + tSI + ", tei : " + tEI);
		Node temp = tree[cN];
		
		if (temp.update.size() != 0)
		{
			temp.set.addAll(temp.update);
			
			if (tSI != tEI)
			{
				tree[2 * cN].update.addAll(temp.update);
				tree[2 * cN + 1].update.addAll(temp.update);
			}
			
			temp.update = new HashSet<Integer>();
		}
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return 0;
		
		if (tSI >= rSI && tEI <= rEI)
			return temp.set.size();
		
		int mid, left, right;
		
		mid = (tSI + tEI) / 2;
		left = query(2 * cN, tSI, mid, rSI, rEI);
		right = query(2 * cN + 1, mid + 1, tEI, rSI, rEI);
				
		return left + right;
	}
	
	static class Node
	{
		Set<Integer> set, update;
		int size;
		
		public Node(Set<Integer> set, Set<Integer> update, int size)
		{
			this.set = set;
			this.update = update;
			this.size = size;
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
