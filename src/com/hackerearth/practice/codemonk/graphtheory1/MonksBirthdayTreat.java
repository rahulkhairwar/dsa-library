package com.hackerearth.practice.codemonk.graphtheory1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

class MonksBirthdayTreat
{
	static int n, d;
	static List<Integer>[] list;
	static boolean[] visited;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

//		Method 1 : By DFS.
//		solve();

//		Method 2 : By Disjoint sets.
		Solver solver = new Solver(in, out);
		solver.solve(0);
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void solve()
	{
		n = in.nextInt();
		d = in.nextInt();
		
		createGraph(n, d);
		
		int min = (int) 1e5;
		
//		System.out.println(min);
		
		for (int i = 0; i < n; i++)
		{
			int curr = countMin(i);
			
			if (curr < min)
				min = curr;
			
			if (min == 1)
				break;
		}
		
		out.println(min);
	}
	
	static void createGraph(int nodes, int edges)
	{
		list = new ArrayList[nodes];
		visited = new boolean[nodes];
		
		for (int i = 0; i < edges; i++)
		{
			int from, to;
			
			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			
			if (list[from] == null)
				list[from] = new ArrayList<Integer>();
			
			list[from].add(to);
		}
	}
	
	static int countMin(int node)
	{
		visited = new boolean[n];
		
		if (list[node] == null)
			return 1;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		visited[node] = true;
		stack.push(node);
		
		int size, total;
		
		size = list[node].size();
		total = 1;
		
		if (size > 0)
		{
			Iterator<Integer> iterator = list[node].iterator();
			
			while (iterator.hasNext())
			{
				int curr = iterator.next();
				
				if (!visited[curr])
					stack.push(curr);
			}
		}
		
		while (stack.size() > 1)
		{
			int top = stack.pop();
			
			if (visited[top])
				continue;
			
			visited[top] = true;
			total++;
			
			if (list[top] == null)
				continue;
			
			size = list[top].size();
			
			if (size > 0)
			{
				Iterator<Integer> iterator = list[top].iterator();
				
				while (iterator.hasNext())
				{
					int curr = iterator.next();
					
					if (!visited[curr])
						stack.push(curr);
				}
			}
		}
		
		stack.pop();
		
		return total;
	}

	static class Solver
	{
		int n, d;
		List<Integer>[] adj;
		boolean[] visited;
		HashMap<Integer, Node> disjointSets;
		InputReader in;
		OutputWriter out;

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		void solve(int testNumber)
		{
			n = in.nextInt();
			d = in.nextInt();

			getRelations();

			int answer = Integer.MAX_VALUE;

			for (int i = 1; i <= n; i++)
			{
				makeSet();

				visited = new boolean[n + 1];
				find(i);
				answer = Math.min(answer, disjointSets.get(i).size);
			}

			out.println(answer);
		}

		void getRelations()
		{
			adj = new List[n + 1];

			for (int i = 0; i < d; i++)
			{
				int from, to;

				from = in.nextInt();
				to = in.nextInt();

				if (adj[from] == null)
					adj[from] = new ArrayList<>();

				adj[from].add(to);
			}
		}

		void find(int node)
		{
			if (visited[node])
				return;

			visited[node] = true;

			if (adj[node] == null)
				return;

			Iterator<Integer> iterator = adj[node].iterator();

			while (iterator.hasNext())
			{
				int next = iterator.next();

				union(node, next);
				find(next);
			}

			return;
		}

		void makeSet()
		{
			disjointSets = new HashMap<>();

			for (int i = 1; i <= n; i++)
			{
				Node temp = new Node(i);

				disjointSets.put(i, temp);
			}
		}

		void union(int one, int two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne.key == parentTwo.key)
				return;

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.size += parentOne.size;
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.size += parentTwo.size;
				parentOne.height++;
			}
		}

		Node findParent(int key)
		{
			Node temp = disjointSets.get(key);

			if (temp.key == temp.parent.key)
				return temp;
			else
			{
				Node parent = findParent(temp.parent.key);
 				temp.parent = parent.parent;

				return parent;
			}
		}

		class Node
		{
			int key, height, size;
			Node parent;

			public Node(int key)
			{
				this.key = key;
				height = 0;
				size = 1;
				parent = this;
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
