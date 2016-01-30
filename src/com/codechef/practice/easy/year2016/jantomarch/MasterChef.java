package com.codechef.practice.easy.year2016.jantomarch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class MasterChef
{
	static int t, n, k, m, minCosts[];
	static long[] dishes;
	static Node[] tree;
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
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			k = in.nextInt();
			m = in.nextInt();
			
			//dishes = in.nextLongArray(n);
			
			dishes = new long[n];
			
			long totalRating = 0;
			
			for (int i = 0; i < n; i++)
			{
				dishes[i] = in.nextLong();
				totalRating += dishes[i];
			}
			
			int log, treeSize;
			
			log = (int) Math.ceil(Math.log(n) / Math.log(2));
			treeSize = (1 << log) * 2;
			
			tree = new Node[treeSize];
			
			for (int i = 0; i < treeSize; i++)
				tree[i] = new Node(1000, false);
			
			for (int i = 0; i < m; i++)
			{
				int from, to, cost;
				
				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
				cost = in.nextInt();
				
				updateTree(1, 0, n, from, to, cost);
			}
			
			minCosts = new int[n];
			
			for (int i = 0; i < n; i++)
				minCosts[i] = queryTree(1, 0, n, i);
			
/*			System.out.println("All dishes have the minimum cost stored now. In O(n * log n) time.");
			
			for (int i = 0; i < n; i++)
			{
				System.out.println("i : " + i + ", minCost : " + minCosts[i]);
			}*/
			
			int[] negatives = new int[n];
			int negativeCount = 0;
			
			/*for (int i = 0; i < n; i++)
			{
				if (dishes[i] < 0 && minCosts[i] <= k)
					negatives[negativeCount++] = i;
			}
			
			if (negativeCount == 0)
				out.println(totalRating);
			else
			{
				Select[][] dp = new Select[negativeCount + 1][k + 1];
				
				dp[0][0] = new Select(0, 0);
				
				for (int i = 1; i <= negativeCount; i++)
				{
					for (int j = 0; j <= k; j++)
					{
						if (dp[i - 1][j] != null)
						{
							if (dp[i][j] == null)
								dp[i][j] = new Select(dp[i - 1][j].totalRating,
										dp[i - 1][j].totalCost);
							else
								dp[i][j].totalRating = Math.min(
										dp[i - 1][j].totalRating,
										dp[i][j].totalRating);

							long tempCost, tempRating;

							tempCost = minCosts[negatives[i - 1]];
							tempRating = dishes[negatives[i - 1]];

							if (j + tempCost <= k)
								dp[i][(int) (j + tempCost)] = new Select(
										dp[i - 1][j].totalRating + tempRating,
										dp[i - 1][j].totalCost + tempCost);
						}
					}
				}*/
				
/*				System.out.println("The dp looks like :");
				
				for (int i = 0; i <= negativeCount; i++)
				{
					System.out.print(i + " : ");
					for (int j = 0; j <= k; j++)
					{
						Select temp = dp[i][j];
						
						if (temp == null)
							System.out.print("- ");
						else
							System.out.print(temp.totalRating + " ");
					}
					
					System.out.println();
				}*/
				
				/*long min = Long.MAX_VALUE;
				
				for (int i = 0; i <= k; i++)
				{
					Select temp = dp[negativeCount][i];
					
					if (temp == null)
						continue;
					
					if (temp.totalRating < min)
						min = temp.totalRating;
				}*/
				
				long min = knapsack();
				
				out.println(totalRating - min);
			//}
		}
	}
	
	static long knapsack()
	{
		long[][] dp = new long[n + 1][k + 1];

		// int knapSack(int W, int wt[], int val[], int n)
		// W = capacity = k, wt = weights = minCosts, val = value = ratings,
		// n = number of items = number of dishes

		// Build table K[][] in bottom up manner
		for (int i = 0; i <= n; i++)
		{
			for (int j = 0; j <= k; j++)
			{
				if (i == 0 || j == 0)
					dp[i][j] = 0;
				else if (minCosts[i - 1] <= j)
					dp[i][j] = Math.min(dishes[i - 1]
							+ dp[i - 1][j - minCosts[i - 1]], dp[i - 1][j]);
				else
					dp[i][j] = dp[i - 1][j];
			}
		}

		return dp[n][k];
	}
	
	static void updateTree(int node, int tSI, int tEI, int rSI, int rEI,
			int updateValue)
	{
		Node temp = tree[node];

		if (temp.lazy)
		{
			if (tSI != tEI)
			{
				tree[2 * node].cost = Math.min(tree[2 * node].cost, temp.cost);
				tree[2 * node].lazy = true;
				tree[2 * node + 1].cost = Math.min(tree[2 * node + 1].cost,
						temp.cost);
				tree[2 * node + 1].lazy = true;
			}

			temp.lazy = false;
		}
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;
		
		if (tSI >= rSI && tEI <= rEI)
		{
			if (temp.cost > updateValue)
			{
				temp.cost = updateValue;
				temp.lazy = true;
			}
			
			return;
		}
		
		int mid = tSI + ((tEI - tSI) >> 1);
		
		updateTree(2 * node, tSI, mid, rSI, rEI, updateValue);
		updateTree(2 * node + 1, mid + 1, tEI, rSI, rEI, updateValue);
	}
	
	static int queryTree(int node, int tSI, int tEI, int index)
	{
		Node temp = tree[node];

		if (temp.lazy)
		{
			if (tSI != tEI)
			{
				tree[2 * node].cost = Math.min(tree[2 * node].cost, temp.cost);
				tree[2 * node].lazy = true;
				tree[2 * node + 1].cost = Math.min(tree[2 * node + 1].cost,
						temp.cost);
				tree[2 * node + 1].lazy = true;
			}

			temp.lazy = false;
		}
		
		if (tSI > tEI || tSI > index || tEI < index)
			return 1000;
		
		if (tSI == index && tEI == index)
			return temp.cost;
		
		int mid, leftChild, rightChild;
		
		mid = tSI + ((tEI - tSI) >> 1);
		
		leftChild = queryTree(2 * node, tSI, mid, index);
		rightChild = queryTree(2 * node + 1, mid + 1, tEI, index);
		
		return Math.min(leftChild, rightChild);
	}
	
	static class Node
	{
		int cost;
		boolean lazy;
		
		public Node(int cost, boolean lazy)
		{
			this.cost = cost;
			this.lazy = lazy;
		}
		
	}
	
	static class Select
	{
		long totalRating, totalCost;

		public Select(long totalRating, long totalCost)
		{
			this.totalRating = totalRating;
			this.totalCost = totalCost;
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

/*

1
5 10 5
10 -2 -5 7 -10
1 1 5
2 4 10
4 4 12
3 4 10
1 5 15

1
10 5 10
50 -50 10 -8 -5 -15 4 36 51 -80
1 5 10
2 7 8
1 9 15
8 10 12
5 6 7
2 4 5
3 5 2
8 9 5
2 8 10
1 7 9

*/
