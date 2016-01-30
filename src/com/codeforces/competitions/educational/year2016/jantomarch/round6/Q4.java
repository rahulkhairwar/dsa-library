package com.codeforces.competitions.educational.year2016.jantomarch.round6;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Q4
{
	static int n, m;
	static Node[] tree;
	static boolean[] visited;
	static List<Integer>[] paths;
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
		m = in.nextInt();
		
		tree = new Node[n];
		visited = new boolean[n];
		
		for (int i = 0; i < n; i++)
		{
			tree[i] = new Node(in.nextInt(), -1, null);
		}
		
		for (int i = n - 1; i >= 0; i--)
		{
			int from, to;
			
			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			
			if (tree[from].list == null)
				tree[from].list = new ArrayList<Integer>();
			
			tree[from].list.add(to);
			
			if (tree[to].list == null)
				tree[to].list = new ArrayList<Integer>();
			
			tree[to].list.add(from);
		}
		
		Set<Integer> root = dfs(0, -1);
		
		System.out.println("size of root set : " + root.size());

		for (int i = 0; i < n; i++)
		{
			System.out.println("node " + i + " => size : " + tree[i].set.size()
					+ ", parent : " + tree[i].parent);
		}
		
		for (int i = 0; i < m; i++)
		{
			int type = in.nextInt();
			int vertex = in.nextInt();
			
			if (type == 1)
			{
				int changeTo = in.nextInt();
				
				update(vertex, changeTo);
			}
			else
			{
				//out.println(tree[vertex].set.size());
			}
		}
	}
	
	static Set<Integer> dfs(int node, int parent)
	{
		Node temp = tree[node];
		
		if (visited[node])
			return temp.set;
		
		visited[node] = true;
		
		temp.parent = parent;
		
		if (temp.list == null)
			return temp.set;
		
		Iterator<Integer> iterator = temp.list.iterator();
		
		while (iterator.hasNext())
		{
			int curr = iterator.next();
			
			Set<Integer> tempSet = dfs(curr, node);
			
			temp.set.addAll(tempSet);
		}
		
		return temp.set;
	}
	
	static void update(int node, int colour)
	{
		Node temp = tree[node];
		Node t = temp;
		
		//int[] path = new int[n];
		
		if (paths[node] == null)
		{
			paths[node] = new ArrayList<Integer>();
			
			while (t.parent != -1)
			{
//				paths[pathCount++] = t.parent;
				paths[node].add(t.parent);
				t = tree[t.parent];
			}
		}
		
		for (int i = paths[node].size() - 1; i >= 0; i--)
		{
			t = tree[paths[i].get(i)];
			
			if (t.lazy != -1)
			{
				lazy(paths[i].get(i), t.lazy);
			}
		}
		
		temp.lazy = colour;
	}
	
	static void query(int node)
	{
		Node temp = tree[node];
		
		if (temp.lazy != -1)
			lazy(node, temp.lazy);
		
		
	}
	
	static void lazy(int node, int colour)
	{
		Node temp = tree[node];
		
		if (temp.colour == colour)
			return;
		
		temp.colour = colour;
		temp.set = new HashSet<Integer>();
		temp.set.add(colour);
		temp.lazy = -1;
		
		Iterator<Integer> iterator = temp.list.iterator();
		
		while (iterator.hasNext())
		{
			int curr = iterator.next();
			
			if (curr == temp.parent)
				continue;
			
			tree[curr].lazy = colour;
		}
	}
	
	static class Node
	{
		int colour, parent;
		List<Integer> list;
		Set<Integer> set;
		int lazy;
		
		public Node(int colour, int parent, List<Integer> list)
		{
			this.colour = colour;
			this.parent = parent;
			this.list = list;
			
			set = new HashSet<Integer>();
			set.add(colour);
			
			lazy = -1;
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

/*

13 3
1 2 1 4 3 4 1 3 2 4 2 3 2

70 3
402220 282419 650495 930659 650495 889316 305186 305186 402220 889316 282419 305186 402220 650495 930659 650495 624910 650495 305186 574693 889316 201793 201793 930659 156713 305186 624910 574693 402220 402220 305186 930659 650495 889316 650495 889316 402220 156713 624910 402220 889316 650495 305186 305186 650495 305186 201793 650495 624910 201793 282419 650495 930659 574693 402220 930659 889316 624910 930659 650495 402220 574693 201793 282419 624910 402220 201793 650495 624910 650495 402220

*/
