package com.codeforces.competitions.educational.year2016.jantomarch.round5;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public final class Q3 implements Runnable
{
	static int t, n, m;
	static String[] map;
	static boolean[][] visited;
	static int[][] numbers;
	static Component[] components;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args) throws InterruptedException
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		Thread t = new Thread(null, new Q3(), "Q3", 1 << 29);

		t.start();
		t.join();

		out.flush();

		in.close();
		out.close();
	}
   
    public Q3()
    {
		n = in.nextInt();
		m = in.nextInt();
		
		map = new String[n];
		
		for (int i = 0; i < n; i++)
			map[i] = in.next();
	}
    
	@Override
	public void run()
	{
		createGraph();
		
		StringBuilder[] answer = new StringBuilder[n];
		
		for (int i = 0; i < n; i++)
		{
			answer[i] = new StringBuilder();
			
			for (int j = 0; j < m; j++)
			{
				if (map[i].charAt(j) == '*')
				{
					Set<Integer> set = new TreeSet<>();
					int x;

					if (i == 0 && j == 0)
					{
						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (i == 0 && j == m - 1)
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (i == n - 1 && j == 0)
					{
						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (i == n - 1 && j == m - 1)
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (i == 0)
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (i == n - 1)
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (j == 0)
					{
						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					else if (j == m - 1)
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					else
					{
						if (j > 0 && numbers[i][j - 1] != 0)
						{
							x = components[numbers[i][j - 1] - 1].number;

							set.add(x);
						}

						if (j < m - 1 && numbers[i][j + 1] != 0)
						{
							x = components[numbers[i][j + 1] - 1].number;

							set.add(x);
						}

						if (i > 0 && numbers[i - 1][j] != 0)
						{
							x = components[numbers[i - 1][j] - 1].number;

							set.add(x);
						}

						if (i < n - 1 && numbers[i + 1][j] != 0)
						{
							x = components[numbers[i + 1][j] - 1].number;

							set.add(x);
						}
					}
					
					Iterator<Integer> it = set.iterator();
					int total = 0;
					
					while (it.hasNext())
					{
						int next = it.next() - 1;
						
						total += components[next].size;
					}
					
					total++;
					total %= 10;
					
					answer[i].append(total);
				}
				else
					answer[i].append('.');
			}
			
			out.println(answer[i].toString());
		}
	}
	
	static void createGraph()
	{
		visited = new boolean[n][m];
		numbers = new int[n][m];
		components = new Component[n * m];
		
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < m; j++)
			{
				if (map[i].charAt(j) == '.' && !visited[i][j])
				{
					int num = Component.count;
					
					Component.count++;
					components[num] = new Component(dfs(num + 1, i, j));
				}
			}
		}
	}
	
	static int dfs(int compNum, int i, int j)
	{
		if (map[i].charAt(j) == '*')
			return 0;
		
		if (visited[i][j])
			return 0;
		
		visited[i][j] = true;
		
		int left, up, right, bottom, total;
		
		left = up = right = bottom = 0;
		total = 1;
		
		if (i == 0)
		{
			if (j == 0)
			{
				if (m > 1)
					right = dfs(compNum, i, j + 1);
				
				if (n > 1)
					bottom = dfs(compNum, i + 1, j);
				
				numbers[i][j] = compNum;
				
				return (right + bottom + total);
			}
			else if (j == m - 1)
			{
				left = dfs(compNum, i, j - 1);
				
				if (n > 1)
					bottom = dfs(compNum, i + 1, j);
				
				numbers[i][j] = compNum;
				
				return (left + bottom + total);
			}
			
			numbers[i][j] = compNum;
			
			left = dfs(compNum, i, j - 1);
			
			if (m > 1)
				right = dfs(compNum, i, j + 1);
			
			if (n > 1)
				bottom = dfs(compNum, i + 1, j);
			
			return (left + up + right + bottom + total);
		}
		else if (i == n - 1)
		{
			if (j == 0)
			{
				up = dfs(compNum, i - 1, j);
				
				if (m > 1)
					right = dfs(compNum, i, j + 1);
				
				numbers[i][j] = compNum;
				
				return (up + right + total);
			}
			else if (j == m - 1)
			{
				left = dfs(compNum, i, j - 1);
				up = dfs(compNum, i - 1, j);
				
				numbers[i][j] = compNum;
				
				return (left + up + total);
			}
			
			numbers[i][j] = compNum;
			
			left = dfs(compNum, i, j - 1);
			
			if (m > 1)
				right = dfs(compNum, i, j + 1);
			
			up = dfs(compNum, i - 1, j);
			
			return (left + up + right + bottom + total);
		}
		
		if (j == 0)
		{
			if (i == 0)
			{
				if (m > 1)
					right = dfs(compNum, i, j + 1);
				
				if (n > 1)
					bottom = dfs(compNum, i + 1, j);
				
				numbers[i][j] = compNum;
				
				return (right + bottom + total);
			}
			else if (i == n - 1)
			{
				up = dfs(compNum, i - 1, j);
				
				if (m > 1)
					right = dfs(compNum, i, j + 1);
				
				numbers[i][j] = compNum;
				
				return (right + up + total);
			}
			
			numbers[i][j] = compNum;
			
			up = dfs(compNum, i - 1, j);
			
			if (m > 1)
				right = dfs(compNum, i, j + 1);
			
			if (n > 1)
				bottom = dfs(compNum, i + 1, j);
			
			return (left + up + right + bottom + total);
		}
		else if (j == m - 1)
		{
			if (i == 0)
			{
				left = dfs(compNum, i, j - 1);
				
				if (n > 1)
					bottom = dfs(compNum, i + 1, j);
				
				numbers[i][j] = compNum;
				
				return (left + bottom + total);
			}
			else if (i == n - 1)
			{
				left = dfs(compNum, i, j - 1);
				up = dfs(compNum, i - 1, j);
				
				numbers[i][j] = compNum;
				
				return (left + up + total);
			}
			
			numbers[i][j] = compNum;
			
			left = dfs(compNum, i, j - 1);
			up = dfs(compNum, i - 1, j);
			
			if (n > 1)
				bottom = dfs(compNum, i + 1, j);
			
			return (left + up + right + bottom + total);
		}
		
		left = dfs(compNum, i, j - 1);
		
		if (m > 1)
			right = dfs(compNum, i, j + 1);
		
		up = dfs(compNum, i - 1, j);
		
		if (n > 1)
			bottom = dfs(compNum, i + 1, j);
		
		numbers[i][j] = compNum;
		
		return (left + up + right + bottom + total);
	}
	
	static class Component
	{
		static int count = 0;
		int number, size;

		public Component(int size)
		{
			number = count;
			this.size = size;
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
