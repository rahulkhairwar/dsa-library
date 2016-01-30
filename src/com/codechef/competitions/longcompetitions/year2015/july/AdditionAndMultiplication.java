package com.codechef.competitions.longcompetitions.year2015.july;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class AdditionAndMultiplication
{
	static int arrayLength, numberOfQueries;
	static long array[];
	static Node tree[];
	static long mod;
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		AdditionAndMultiplication aAM = new AdditionAndMultiplication();
		
		reader = aAM.new InputReader(System.in);
		writer = aAM.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		mod = 1000000007;
		
		arrayLength = reader.nextInt();
		numberOfQueries = reader.nextInt();
		
		int treeSize, log;
		
		log = (int) (Math.ceil(Math.log(arrayLength) / Math.log(2))); 
		treeSize = (int) (2 * Math.pow(2, log));
		
		array = new long[arrayLength];
		//tree = new Node[treeSize];
		tree = new Node[3 * treeSize];
		
		for (int i = 0; i < arrayLength; i++)
			array[i] = reader.nextInt();
		
		buildTree(1, 0, arrayLength - 1);
		
/*		System.out.print("Tree after building : ");
		
		for (int i = 1; i < treeSize; i++)
			if (tree[i] != null)
				System.out.print(tree[i].sum + " ");
		
		System.out.println();*/
		
		int option, rSI, rEI, val;
		
		for (int i = 0; i < numberOfQueries; i++)
		{
			option = reader.nextInt();
			rSI = reader.nextInt() - 1;
			rEI = reader.nextInt() - 1;
			
			if (option != 4)
			{
				val = reader.nextInt();
				
				if (option == 1)
					addToRange(1, 0, arrayLength - 1, rSI, rEI, val);
				else if (option == 2)
					multiplyToRange(1, 0, arrayLength - 1, rSI, rEI, val);
				else
					initializeRange(1, 0, arrayLength - 1, rSI, rEI, val);

/*				if (option == 2)
				{
					System.out.println("Currently the leaves are : ");
					printLeafNodes(1, 0, arrayLength - 1);
					System.out.println();

					System.out.println("MUL tree : ");
					System.out.println("a m i s");

					for (int j = 1; j < treeSize; j++)
					{
						if (tree[j] != null)
						{
							System.out.println(tree[j].add + " " + tree[j].mul
									+ " " + tree[j].init + " " + tree[j].sum);
						}
					}

					System.out.println("\n**********");
				}*/
				
				//System.out.println("completed option : " + option);
				
				continue;
			}
/*			
			if (option == 4)
			{
				System.out.println("SUM tree : ");
				System.out.println("a m i s");
				
				for (int j = 1; j < treeSize; j++)
				{
					if (tree[j] != null)
					{
						System.out.println(tree[j].add + " " + tree[j].mul
								+ " " + tree[j].init + " " + tree[j].sum);
					}
				}

				System.out.println("\n**********");
			}*/
			
			writer.println(queryRangeSum(1, 0, arrayLength - 1, rSI, rEI));
		}
	}
	
	static void printLeafNodes(int currNode, int tSI, int tEI)	// perfect
	{
		if (tSI > tEI)
			return;
		
		long ans = (tree[currNode].sum * tree[currNode].mul + tree[currNode].add);
		
		if (tSI == tEI)
		{
			System.out.println("tSI : " + tSI + ", tEI : " + tEI + ", sum : " + ans);
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		printLeafNodes(2 * currNode, tSI, mid);
		printLeafNodes(2 * currNode + 1, mid + 1, tEI);
	}
	
	static void buildTree(int currNode, int tSI, int tEI)	// perfect
	{
		if (tSI > tEI)
			return;
		
		if (tSI == tEI)
		{
			tree[currNode] = new Node(0, 1, -1, array[tSI]);

			return;
		}
		
		int mid = (tSI + tEI) / 2;
		long lCS, rCS;
		
		buildTree(2 * currNode, tSI, mid);
		buildTree(2 * currNode + 1, mid + 1, tEI);
		
		lCS = tree[2 * currNode].sum;
		rCS = tree[2 * currNode + 1].sum;
		
		tree[currNode] = new Node(0, 1, -1, (lCS + rCS) % mod);
	}
	
	static void addToRange(int currNode, int tSI, int tEI, int rSI, int rEI,
			long addValue)	// fine, I guess
	{
		Node temp = tree[currNode];

		if (temp.add > 0 || temp.mul > 1)
		{
			temp.sum = (temp.sum * temp.mul) + ((tEI - tSI + 1) * temp.add);
			temp.sum %= mod;

			if (tSI != tEI)
			{
				tree[2 * currNode].add += temp.add;
				tree[2 * currNode].add %= mod;
				tree[2 * currNode].mul *= temp.mul;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode + 1].add += temp.add;
				tree[2 * currNode + 1].add %= mod;
				tree[2 * currNode + 1].mul *= temp.mul;
				tree[2 * currNode + 1].mul %= mod;
			}

			temp.add = 0;
			temp.mul = 1;
		}

		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;

		if (tSI >= rSI && tEI <= rEI)
		{
			temp.sum = temp.sum * temp.mul + ((tEI - tSI + 1) * addValue);
			temp.sum %= mod;

			if (tSI != tEI)
			{
				tree[2 * currNode].add += addValue;
				tree[2 * currNode].add %= mod;
				tree[2 * currNode].mul *= temp.mul;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode + 1].add += addValue;
				tree[2 * currNode + 1].add %= mod;
				tree[2 * currNode + 1].mul *= temp.mul;
				tree[2 * currNode + 1].mul %= mod;
			}
			
			temp.mul = 1;
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		addToRange(2 * currNode, tSI, mid, rSI, rEI, addValue);
		addToRange(2 * currNode + 1, mid + 1, tEI, rSI, rEI, addValue);
		
		temp.sum = tree[2 * currNode].sum + tree[2 * currNode + 1].sum;
		temp.sum %= mod;
	}
	
	static void multiplyToRange(int currNode, int tSI, int tEI, int rSI,
			int rEI, long mulValue)
	{
		Node temp = tree[currNode];
/*		
		System.out.println("MUL currNode : " + currNode + ", tSI : " + tSI
				+ ", tEI : " + tEI + ", sum : " + temp.sum + ", mul : " + temp.mul);
	*/	
		if (temp.mul > 1 || temp.add > 0)
		{
			temp.sum = temp.sum * temp.mul + temp.add;
			temp.sum %= mod;
			
			//System.out.println("mul done. sum : " + temp.sum);
			
			if (tSI != tEI)
			{
				tree[2 * currNode].mul *= temp.mul;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode].add *= temp.mul;
				tree[2 * currNode].add %= mod;
				tree[2 * currNode + 1].mul *= temp.mul;
				tree[2 * currNode + 1].mul %= mod;
				tree[2 * currNode + 1].add *= temp.mul;
				tree[2 * currNode + 1].add %= mod;
			}
			
			temp.mul = 1;
			temp.add = 0;
		}
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;
		
		if (tSI >= rSI && tEI <= rEI)
		{
			temp.sum = temp.sum * mulValue + temp.add * mulValue;
			temp.sum %= mod;
			
/*			System.out.println("fully inside, sum : " + temp.sum + ", tSI : "
					+ tSI + ", tEI : " + tEI);*/
	
			if (tSI != tEI)
			{
				tree[2 * currNode].mul *= mulValue;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode + 1].mul *= mulValue;
				tree[2 * currNode + 1].mul %= mod;
			}
			
			temp.add = 0;
			// not needed
			//temp.mul = 1;
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		multiplyToRange(2 * currNode, tSI, mid, rSI, rEI, mulValue);
		multiplyToRange(2 * currNode + 1, mid + 1, tEI, rSI, rEI, mulValue);
		
		temp.sum = tree[2 * currNode].sum + tree[2 * currNode + 1].sum;
		temp.sum %= mod;
	}
	

	static void initializeRange(int currNode, int tSI, int tEI, int rSI,
			int rEI, long initValue)
	{
		Node temp = tree[currNode];
		
		if (temp.init != -1)
		{
			temp.sum = ((tEI - tSI + 1) * temp.init);
			temp.sum %= mod;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].init = temp.init;
				tree[2 * currNode + 1].init = temp.init;
			}
			
			temp.init = -1;
			temp.add = 0;
			temp.mul = 1;
		}
		else if (temp.add > 0 || temp.mul > 1)
		{
			temp.sum = temp.sum * temp.mul + ((tEI - tSI + 1) * temp.add);
			temp.sum %= mod;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].add += temp.add;
				tree[2 * currNode].add %= mod;
				tree[2 * currNode + 1].add += temp.add;
				tree[2 * currNode + 1].add %= mod;
				tree[2 * currNode].mul *= temp.mul;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode + 1].mul *= temp.mul;
				tree[2 * currNode + 1].mul %= mod;
			}
			
			temp.init = -1;
			temp.add = 0;
			temp.mul = 1;
		}
		
/*		System.out.println("INIT cN : " + currNode + ", tSI : " + tSI
				+ ", tEI : " + tEI + ", rSI : " + rSI + ", rEI : " + rEI);*/
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return;
		
		if (tSI >= rSI && tEI <= rEI)
		{
			temp.sum = (tEI - tSI + 1) * initValue;
			temp.sum %= mod;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].init = initValue;
				tree[2 * currNode + 1].init = initValue;
			}
			
			temp.add = 0;
			temp.mul = 1;
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		initializeRange(2 * currNode, tSI, mid, rSI, rEI, initValue);
		initializeRange(2 * currNode + 1, mid + 1, tEI, rSI, rEI, initValue);
		
		tree[currNode].sum = tree[2 * currNode].sum + tree[2 * currNode + 1].sum;
		tree[currNode].sum %= mod;
	}
	
	static long queryRangeSum(int currNode, int tSI, int tEI, int rSI, int rEI)
	{
		Node temp = tree[currNode];
		
/*		System.out.println("Q-----currNode : " + currNode + ", tSI : " + tSI
				+ ", tEI : " + tEI + ", tree[cN].sum : " + tree[currNode].sum);*/
		
		if (temp.init != -1)
		{
			//System.out.println("currNode : " + currNode + ", in init");
			temp.sum = ((tEI - tSI + 1) * temp.init);
			temp.sum %= mod;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].init = temp.init;
				tree[2 * currNode + 1].init = temp.init;
			}
			
			temp.init = -1;
			temp.add = 0;
			temp.mul = 0;
		}
		else
		{
			temp.sum = (temp.sum * temp.mul) + ((tEI - tSI + 1) * temp.add);
			temp.sum %= mod;
			
			if (tSI != tEI)
			{
				tree[2 * currNode].add += temp.add;
				tree[2 * currNode].add %= mod;
				tree[2 * currNode].mul *= temp.mul;
				tree[2 * currNode].mul %= mod;
				tree[2 * currNode + 1].add += temp.add;
				tree[2 * currNode + 1].add %= mod;
				tree[2 * currNode + 1].mul *= temp.mul;
				tree[2 * currNode + 1].mul %= mod;
			}
			
			temp.add = 0;
			temp.mul = 1;
		}
		
		if (tSI > tEI || tSI > rEI || tEI < rSI)
			return -1;
		
		if (tSI >= rSI && tEI <= rEI)
			return temp.sum;
		
		int mid = (tSI + tEI) / 2;
		long lCS, rCS;
		
		lCS = queryRangeSum(2 * currNode, tSI, mid, rSI, rEI);
		rCS = queryRangeSum(2 * currNode + 1, mid + 1, tEI, rSI, rEI);
		
		if (lCS == -1)
			lCS = 0;
		
		if (rCS == -1)
			rCS = 0;
		
		tree[currNode].sum = tree[2 * currNode].sum + tree[2 * currNode + 1].sum;
		tree[currNode].sum %= mod;
		
		return (lCS + rCS) % mod;
	}
	
	static class Node
	{
		long add, mul, init, sum;
		
		public Node(long add, long mul, long init, long sum)
		{
			this.add = add;
			this.mul = mul;
			this.init = init;
			this.sum = sum;
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

/*

4 4
1 2 3 4
4 1 4
1 1 3 10
2 2 4 2
4 1 4

4 7
1 2 3 4
4 1 4
1 1 3 10
2 2 4 2
4 1 4
4 1 1
4 2 3
4 1 3

5 10
5 4 8 2 3
4 1 5
1 3 5 10
1 1 5 4 
1 2 3 101
4 1 5
3 1 4 20
4 1 5
2 3 5 10
3 1 2 50
4 1 5

*/
