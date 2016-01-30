package com.codechef.practice.easy.year2015.todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class ChefAndCake
{
	static int n, k, q, logTable[];
	static long a, b, c, d, e, f, r, s, t, m, array[];
	static long l1, la, lc, lm, d1, da, dc, dm;
	static long right, min, sum, product;
	static long mod = 1000000007;
	static int sparseTable[][];
	static InputReader reader;
	static OutputWriter writer;
	
	// segment tree
	static long tree[];
	
	public static void main(String[] args)
	{
		ChefAndCake cake = new ChefAndCake();
		
		reader = cake.new InputReader(System.in);
		writer = cake.new OutputWriter(System.out);
		
		getAttributes2();
		
/*		System.out.println("started!!");
		double startTime = System.currentTimeMillis();
		
		for (long i = 0; i < 10000000000l; i++)
			;
		
		double endTime = System.currentTimeMillis();

		System.out.printf("finished in %.6f milliseconds",
				(endTime - startTime));*/

		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		n = reader.nextInt();
		k = reader.nextInt();
		q = reader.nextInt();
		
		array = new long[n];
		
		a = reader.nextLong();
		b = reader.nextLong();
		c = reader.nextLong();
		d = reader.nextLong();
		e = reader.nextLong();
		f = reader.nextLong();
		r = reader.nextLong();
		s = reader.nextLong();
		t = reader.nextLong();
		m = reader.nextLong();
		
		array[0] = reader.nextLong();
		
		l1 = reader.nextLong();
		la = reader.nextLong();
		lc = reader.nextLong();
		lm = reader.nextLong();
		d1 = reader.nextLong();
		da = reader.nextLong();
		dc = reader.nextLong();
		dm = reader.nextLong();
		
		long prevElementSquare;
		
		for (int i = 1; i < n; i++)
		{
			prevElementSquare = powModM(array[i - 1], 2);
			
			if (powModS(t, i) <= r)
				array[i] = ((a * prevElementSquare) % m + (b * array[i - 1]) % m + c) % m;
			else
				array[i] = ((d * prevElementSquare) % m + (e * array[i - 1]) % m + f) % m;
		}
		
		preCompute();
		
		sum = 0;
		product = 1;
		
		for (int i = 0; i < q; i++)
		{
			l1 = (la * l1 + lc) % lm;
			d1 = (da * d1 + dc) % dm;
			
			right = Math.min(l1 + k - 1 + d1, n);
			
			min = array[findMinPos((int) l1, (int) (right - 1))];
			
			sum += min;
			product = (product * min) % mod;
		}
		
		writer.println(sum + " " + product);
	}
	
	public static void getAttributes2()
	{
		n = reader.nextInt();
		k = reader.nextInt();
		q = reader.nextInt();
		
		array = new long[n];
		
		a = reader.nextLong();
		b = reader.nextLong();
		c = reader.nextLong();
		d = reader.nextLong();
		e = reader.nextLong();
		f = reader.nextLong();
		r = reader.nextLong();
		s = reader.nextLong();
		t = reader.nextLong();
		m = reader.nextLong();
		
		array[0] = reader.nextLong();
		
		l1 = reader.nextLong();
		la = reader.nextLong();
		lc = reader.nextLong();
		lm = reader.nextLong();
		d1 = reader.nextLong();
		da = reader.nextLong();
		dc = reader.nextLong();
		dm = reader.nextLong();
		
		long prevElementSquare;
		
		for (int i = 1; i < n; i++)
		{
			prevElementSquare = powModM(array[i - 1], 2);
			
			if (powModS(t, i) <= r)
				array[i] = ((a * prevElementSquare) % m + (b * array[i - 1]) % m + c) % m;
			else
				array[i] = ((d * prevElementSquare) % m + (e * array[i - 1]) % m + f) % m;
		}
		
		int log, treeSize;
		
		log = (int) Math.ceil(Math.log(n) / Math.log(2));
		treeSize = 2 * (int) Math.pow(2, log);
		
		tree = new long[treeSize];
		
		//preCompute();
		buildTree(1, 0, n - 1);
		
		sum = 0;
		product = 1;
		
		for (int i = 0; i < q; i++)
		{
			l1 = (la * l1 + lc) % lm;
			d1 = (da * d1 + dc) % dm;
			
			right = Math.min(l1 + k - 1 + d1, n);
			
			//min = array[findMinPos((int) l1, (int) (right - 1))];
			min = queryTree(1, 0, n - 1, (int) l1, (int) right - 1);
			
			//System.out.println("i : " + i + ", min : " + min);
			
			sum += min;
			product = (product * min) % mod;
		}
		
		writer.println(sum + " " + product);
	}
	
	static void buildTree(int currNode, int tSI, int tEI)
	{
		if (tSI == tEI)
		{
			tree[currNode] = array[tSI];
			
			return;
		}
		
		int mid = (tSI + tEI) / 2;
		
		buildTree(2 * currNode, tSI, mid);
		buildTree(2 * currNode + 1, mid + 1, tEI);
		
		tree[currNode] = Math.min(tree[2 * currNode], tree[2 * currNode + 1]);
	}
	
	static long queryTree(int currNode, int tSI, int tEI, int rSI, int rEI)
	{
		if (tSI > rEI || tEI < rSI)
			return Long.MIN_VALUE;
		
		if (tSI >= rSI && tEI <= rEI)
			return tree[currNode];
		
		int mid;
		long lCM, rCM;
		
		mid = (tSI + tEI) / 2;
		lCM = queryTree(2 * currNode, tSI, mid, rSI, rEI);
		rCM = queryTree(2 * currNode + 1, mid + 1, tEI, rSI, rEI);
		
		if (lCM == Long.MIN_VALUE && rCM == Long.MIN_VALUE)
			return Long.MIN_VALUE;
		
		if (lCM == Long.MIN_VALUE)
			return rCM;
		
		if (rCM == Long.MIN_VALUE)
			return lCM;
			
		
		return Math.min(lCM, rCM);
	}
	
	static void preCompute()
	{
		logTable = new int[n + 1];
		
		for (int i = 2; i <= n; i++)
			logTable[i] = logTable[i >> 1] + 1;
		
		sparseTable = new int[logTable[n] + 1][n];
		
		for (int i = 0; i < n; i++)
			sparseTable[0][i] = i;
		
		int x, y;
		
		for (int i = 1; (1 << i) < n; i++)
		{
			for (int j = 0; (1 << i) + j <= n; j++)
			{
				x = sparseTable[i - 1][j];
				y = sparseTable[i - 1][(1 << i - 1) + j];
				
				sparseTable[i][j] = array[x] <= array[y] ? x : y;
			}
		}
	}
	
	static int findMinPos(int rSI, int rEI)
	{
		int k, x, y;
		
		k = logTable[rEI - rSI];
		x = sparseTable[k][rSI];
		y = sparseTable[k][rEI - (1 << k) + 1];
		
		return array[x] <= array[y] ? x : y;
	}
	
	static long powModM(long a, long power)
	{
		if (power == 0)
			return 1;
		
		if (power % 2 == 0)
			return powModM((a * a) % m, power / 2) % m;
		else
			return ((powModM((a * a) % m, power / 2) % m) * a) % m;
	}
	
	public static long powModS(long a, long power)
	{
		if (power == 0)
			return 1;
		
		if (power % 2 == 0)
			return powModS((a * a) % s, power / 2) % s;
		else
			return ((powModS((a * a) % s, power / 2) % s) * a) % s;
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
