package com.codechef.competitions.shortcompetitions.year2015.may;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Second
{
	private static int t, charC[];
	private static String s;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Second second = new Second();
		
		reader = second.new InputReader(System.in);
		writer = second.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			//writer.println("************************** i : " + i);
			s = reader.next();
			
			int len, max;
			
			len = s.length();
			max = 0;
			charC = new int[26];
			
			if (len % 2 == 0)
			{
				//writer.println("even");
				
				for (int j = 0; j < len; j++)
				{
					charC[s.charAt(j) - 97]++;
					
					if (charC[s.charAt(j) - 97] > max)
						max = charC[s.charAt(j) - 97];
				}
				
				// Arrays.sort(charC);
				
				if (max > len / 2)
				{
					writer.println(-1);
					
					continue;
				}
				
				String ans, t1, t2;
				int ansLen = 0;
				
				ans = "";
				
				for (int j = 0; j < 26; j++)
				{
					if (charC[j] > 0)
					{
						ans += (char) (j + 97);
						
						charC[j]--;
						ansLen++;
					}
				}
				
				// System.out.println("ans here : " + ans);
				max--;
				
				int ind = -1;
				int count = 0;
				
				boolean canDo = true;
				
				while (count < 26)
				{
					max = 100000;
					
					for (int j = 0; j < 26; j++)
						if (charC[j] < max)
						{
							max = charC[j];
							ind = j;
						}
					
					// System.out.println("entered, max : " + max + ", ind : " + ind);
/*					for (int j = 0; j < 26; j++)
					{
						if (charC[j] == max)
							ind = j;
					}*/
					
					int k = 1;
					char c = (char) (ind + 97);
					
					// System.out.println(ind);
					
					while (charC[ind] > 0 && k < ansLen)
					{
						// System.out.println("ans : " + ans);
						
						if (ans.charAt(k - 1) != c && ans.charAt(k) != c)
						{
							t1 = ans.substring(0, k);
							t2 = ans.substring(k, ansLen);
							
							ans = t1 + c + t2;
							ansLen++;
							charC[ind]--;
						}
						
						if (ans.charAt(ansLen - 1) != c && charC[ind] > 0)
						{
							ans += c;
							charC[ind]--;
							ansLen++;
						}
						
						k++;
					}
					
					if (charC[ind] > 0)
					{
						writer.println(-1);
						
						canDo = false;
					}
					
					count++;
				}
				
				if (canDo)
					writer.println(ans);
			}
			else
			{
				//writer.println("odd");
				
				for (int j = 0; j < len; j++)
				{
					charC[s.charAt(j) - 97]++;
					
					if (charC[s.charAt(j) - 97] > max)
						max = charC[s.charAt(j) - 97];
				}
				
				// Arrays.sort(charC);
				
				if (max > len / 2 + 1)
				{
					writer.println(-1);
					
					continue;
				}
				
				String ans, t1, t2;
				int ansLen = 0;
				
				ans = "";
				
				for (int j = 0; j < 26; j++)
				{
					if (charC[j] > 0)
					{
						ans += (char) (j + 97);
						
						charC[j]--;
						ansLen++;
						
						// System.out.println("curr ans : " + ans);
					}
				}
				
				// System.out.println("out");
				
				max--;	// 
				
				int ind = -1;
				int count = 0;
				
				max = -1;
				
				boolean canDo = true;
				
				while (count < 26)
				{
					max = 100000;
					// max = -1
					
					for (int j = 0; j < 26; j++)
						if (charC[j] < max)
						{
							max = charC[j];
							ind = j;
						}
					
/*					for (int j = 0; j < 26; j++)
					{
						if (charC[j] == max)
							ind = j;
					}*/
					
					int k = 1;
					char c = (char) (ind + 97);
					
					// System.out.println("max : " + max + ", ind : " + ind);
					
					while (charC[ind] > 0 && k < ansLen)
					{
						System.out.println("entered");
						if (ans.charAt(k - 1) != c && ans.charAt(k) != c)
						{
							t1 = ans.substring(0, k);
							t2 = ans.substring(k, ansLen);
							
							ans = t1 + c + t2;
							ansLen++;
							charC[ind]--;
						}
						
						if (ans.charAt(ansLen - 1) != c && charC[ind] > 0)
						{
							ans += c;
							charC[ind]--;
							ansLen++;
						}
						
						k++;
					}
					
					if (charC[ind] > 0)
					{
						writer.println(-1);
						
						canDo = false;
					}
					
					count++;
				}
				
				if (canDo)
					writer.println(ans);
			}
			
			// System.out.println("leaving i : " + i);
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
