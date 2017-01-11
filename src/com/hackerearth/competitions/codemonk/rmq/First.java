package com.hackerearth.competitions.codemonk.rmq;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

public class First
{
	static int n, q, nMinusOne;
	static char[] s;
	static Node tree[];
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		First first = new First();
		
		reader = first.new InputReader(System.in);
		writer = first.new OutputWriter(System.out);
		//System.out.println(-5 % 3);
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		
		s = new char[n];
		
		for (int i = 0; i < n; i++)
			s[i] = (char) reader.read();
		
		q = reader.nextInt();
		
		nMinusOne = n - 1;
		tree = new Node[4 * n];
		
		build(1, 0, nMinusOne);
		
/*		System.out.println("tree : ");
		for (int i = 1; i < 4 * n; i++)
		{
			if (tree[i] != null)
				System.out.println(i + " : " + tree[i].even + " " + tree[i].odd);
		}*/
		
		for (int i = 0; i < q; i++)
		{
			int ch = reader.nextInt();
			
			if (ch == 0)
			{
				int l = reader.nextInt();
				int r = reader.nextInt();
				
				int answer[] = query(1, 0, nMinusOne, l, r);
				int fin;
				
				if ((nMinusOne - r) % 2 == 0)
					fin = answer[0] - answer[1];
				else
					fin = answer[1] - answer[0];
				
				if ((fin %= 3) < 0)
					fin += 3;
				
				writer.println(fin % 3);
			}
			else
			{
				int l = reader.nextInt();
				
				if (s[l] == '0')
				{
					update(1, 0, nMinusOne, l, l);
					s[l] = '1';
				}
				
/*				System.out.println("tree : ");
				for (int j = 1; j < 4 * n; j++)
				{
					if (tree[j] != null)
						System.out.println(j + " : " + tree[j].even + " " + tree[j].odd);
				}*/
			}
		}
	}
	
	static void build(int cn, int tsi, int tei)
	{
		if (tsi > tei)
			return;
		
		if (tsi == tei)
		{
			//System.out.println("build : " + cn);
			tree[cn] = new Node(0, 0);
			
			if (s[tsi] == '1')
			{
				if ((nMinusOne - tsi) % 2 == 0)
					tree[cn].even++;
				else
					tree[cn].odd++;
			}
			
			return;
		}
		
		int mid = (tsi + tei) / 2;
		
		build(2 * cn, tsi, mid);
		build(2 * cn + 1, mid + 1, tei);
		
		tree[cn] = new Node(0, 0);
		
		tree[cn].odd = tree[2 * cn].odd + tree[2 * cn + 1].odd;
		tree[cn].even = tree[2 * cn].even + tree[2 * cn + 1].even;
	}
	
	static int update(int cn, int tsi, int tei, int rsi, int rei)
	{
		//System.out.println("cn : " + cn + ", tsi : " + tsi + ", tei : " + tei + ", rsi : " + rsi + ", rei : " + rei);
		if (tsi > tei || tsi > rei || tei < rsi)
		//if (tsi > tei)
			return -1;
		
		if (tsi == tei)
		{
			if ((nMinusOne - tsi) % 2 == 0)
			{
				//System.out.println("inside, cn : " + cn + ", even : " + tree[cn].even + ", odd : " + tree[cn].odd);
				tree[cn].even++;
				//tree[cn].odd--;
				
				return 0;
			}
			else
			{
				//System.out.println("inside, cn : " + cn + ", even : " + tree[cn].even + ", odd : " + tree[cn].odd);
				tree[cn].odd++;
				//tree[cn].even--;

				return 1;
			}
		}
		
		int mid, l, r;
		
		mid = (tsi + tei) / 2;
		l = update(2 * cn, tsi, mid, rsi, rei);
		r = update(2 * cn + 1, mid + 1, tei, rsi, rei);
		
		if (l == 0)
		{
			tree[cn].even++;
			
			return 0;
		}
		else if (l == 1)
		{
			tree[cn].odd++;
			
			return 1;
		}
		else if (r == 0)
		{
			tree[cn].even++;
			
			return 0;
		}
		else if (r == 1)
		{
			tree[cn].odd++;
			
			return 1;
		}
		
		return -1;
	}
	
	static int[] query(int cn, int tsi, int tei, int rsi, int rei)
	{
		if (tsi > tei || tsi > rei || tei < rsi)
			return new int[] {0, 0};
		
		if (tsi >= rsi && tei <= rei)
			return new int[] {tree[cn].even, tree[cn].odd};
		
		int mid, l[], r[];
		
		mid = (tsi + tei) / 2;
		l = query(2 * cn, tsi, mid, rsi, rei);
		r = query(2 * cn + 1, mid + 1, tei, rsi, rei);
		
		return new int[] {l[0] + r[0], l[1] + r[1]};
	}
	
	static class Node
	{
		int even, odd;
		
		public Node(int even, int odd)
		{
			this.even = even;
			this.odd = odd;
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

5
10010
6
0 2 4
0 2 3
1 1
0 0 4
1 1
0 0 3

6
101100
6
0 2 4
0 2 3
1 1
0 0 4
1 1
0 0 3

*/
