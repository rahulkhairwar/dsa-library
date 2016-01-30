package com.hackerrank.competitions.year2016.codeagon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Q4
{
	static int t, n, m;
	static List<Pair>[] list;
	static boolean[] visited;
	static int[] dp;
	static int maximumDifference;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String args[])
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
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			m = in.nextInt();

			createGraph(n, m);
			
			int start, end;
			
			start = in.nextInt() - 1;
			end = in.nextInt() - 1;
			
			boolean canReach = canReachDestination(start, end);
			
			if (canReach)
			{
				dp = new int[n];
				
				Arrays.fill(dp, 501);
				
				Iterator<Pair> iterator = list[end].iterator();
				
				while (iterator.hasNext())
				{
					Pair curr = iterator.next();

					dp[curr.node] = Math.min(dp[curr.node], Math.min(curr.cost, curr.taxi));
				}
				
				visited = new boolean[n];
				out.println(dfs(start, end) - maximumDifference);
				
/*				System.out.println("dp : ");
				
				for (int i = 0; i < n; i++)
					System.out.println("i : " + i + " " + dp[i]);*/
			}
			else
				out.println(-1);
		}
	}
	
	@SuppressWarnings("unchecked")
	static void createGraph(int nodes, int edges)
	{
		list = new ArrayList[nodes];
		visited = new boolean[nodes];
		
		for (int i = 0; i < edges; i++)
		{
			int from, to, regCost, taxiCost;
			
			from = in.nextInt() - 1;
			to = in.nextInt() - 1;
			regCost = in.nextInt();
			taxiCost = in.nextInt();
			
			if (list[from] == null)
				list[from] = new ArrayList<Pair>();
			
			list[from].add(new Pair(to, regCost, taxiCost));
			
			if (list[to] == null)
				list[to] = new ArrayList<Pair>();
			
			list[to].add(new Pair(from, regCost, taxiCost));
		}
	}
	
	static boolean canReachDestination(int from, int to)
	{
		if (from == to)
			return true;
		
		visited = new boolean[n];
		
		Stack<Pair> stack = new Stack<Pair>();
		visited[from] = true;
		stack.push(new Pair(from, 0, 0));
		
		Iterator<Pair> iterator = list[from].iterator();
		
		while (iterator.hasNext())
		{
			Pair curr = iterator.next();
			
			if (!visited[curr.node])
				stack.push(curr.clone());
		}
		
		while (stack.size() > 1)
		{
			Pair top = stack.pop();
			
			if (visited[top.node])
				continue;
			
			visited[top.node] = true;
			
			if (top.node == to)
				return true;
			
			if (list[top.node] == null)
				continue;

			iterator = list[top.node].iterator();
			
			while (iterator.hasNext())
			{
				Pair curr = iterator.next();
				
				if (!visited[curr.node])
					stack.push(curr.clone());
			}
		}
		
		return false;
	}
	
	static int dfs(int node, int to)
	{
		if (dp[node] < 501)
			return dp[node];
		
		if (list[node] == null)
			return Integer.MAX_VALUE;
		
		Iterator<Pair> iterator = list[node].iterator();
		
//		System.out.println("node : " + node);
//		in.nextInt();
		
		while (iterator.hasNext())
		{
			Pair curr = iterator.next();
			
			if (visited[curr.node])
				continue;
			
/*			System.out.println("curr.node : " + node);
			in.nextInt();*/
			visited[curr.node] = true;

			if (curr.node == to)
				return Math.min(curr.cost, curr.taxi);

			if (Math.abs(curr.cost - curr.taxi) > maximumDifference)
				maximumDifference = Math.abs(curr.cost - curr.taxi);

			dp[node] = Math.min(dp[node],
					list[node].get(0).cost + dfs(curr.node, to));
		}
		
		return dp[node];
	}
	
	static class Pair
	{
		int node, cost, taxi;

		public Pair(int node, int cost, int taxi)
		{
			this.node = node;
			this.cost = cost;
			this.taxi = taxi;
		}
		
		public Pair clone()
		{
			return new Pair(node, cost, taxi);
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
	
}
