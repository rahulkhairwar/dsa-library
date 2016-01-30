package com.codechef.competitions.shortcompetitions.year2015.december.cookoff;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class Q2
{
	static long q, s1, a, b;
	static int[] size;
	static LinkedList[] list, first, last;
	static long mod;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);

		solve();
//		test();
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void solve()
	{
		q = in.nextInt();
		s1 = in.nextInt();
		a = in.nextInt();
		b = in.nextInt();
		
		mod = (long) Math.pow(2, 32);
		
		System.out.println("mod is : " + mod);
		
/*		int mapCapacity = (int) ((mod / 100) / 0.75 + 1);
		int setCapacity = (int) (10000 / 0.75 + 1);*/
		
		list = new LinkedList[(int) (mod / 100l) + 10];
		first = new LinkedList[(int) (mod / 100l) + 10];
		last = new LinkedList[(int) (mod / 100l) + 10];
		size = new int[(int) (mod / 100l) + 10];
		
//		System.out.println("first[5018590] == null ? : " + (first[5018590] == null));
		
		long num;
		int pos;
//		long sum = 0;
		
		if (s1 % 2l == 1)
		{
			num = s1 / 2l;
			pos = (int) (num / 100l);
			
			LinkedList temp = new LinkedList();
			
			temp.add(num, pos);

			list[pos] = temp;
			size[pos]++;
//			sum += num;
		}
		
		long si = s1;
		
		for (int i = 1; i < q; i++)
		{
			si = ((a * si) % mod + b) % mod;
			
			num = si / 2l;
			pos = (int) (num / 100l);
			
			if (si % 2l == 1)
			{
				if (size[pos] == 0)
				{
					LinkedList temp = new LinkedList();
					temp.add(num, pos);
					
					list[pos] = temp;
					size[pos]++;
//					sum += num;
				}
				else
				{
					LinkedList temp = list[pos];
					
					if (temp.contains(num, pos))
						continue;
					else
					{
						temp.add(num, pos);
						size[pos]++;
//						sum += num;
					}
				}
			}
			else
			{
				if (size[pos] == 0)
					continue;
				else
				{
					LinkedList temp = list[pos];
					
					//if (temp.contains(num))
					{
						int result = temp.delete(num, pos);
						
						if (result == 1)
						{
							size[pos]--;
//							sum -= num;
						}
					}
				}
			}
		}
		
		int limit = (int) (mod / 100l);
		long sum = 0;
		
		for (int i = 0; i < limit; i++)
		{
			if (size[i] > 0)
			{
				LinkedList temp = first[i];
				
				while (temp != null)
				{
					sum += temp.value;
					temp = temp.next;
				}
			}
		}
		
		out.println(sum);
	}
	
	static void test()
	{
		LinkedList[] temp = new LinkedList[1];
		
		list = new LinkedList[1];
		first = new LinkedList[1];
		last = new LinkedList[1];
		size = new int[1];
		
		temp[0] = new LinkedList();
		
		while (true)
		{
			System.out.println("1. Add.");
			System.out.println("2. Delete.");
			System.out.println("3. Print linked list.");
			System.out.println("4. Exit.");
			
			int option = in.nextInt();
			
			switch (option)
			{
				case 1:
					int number = in.nextInt();
					temp[0].add(number, 0);
					break;
				case 2:
					number = in.nextInt();
					temp[0].delete(number, 0);
					break;
				case 3:
					temp[0].printList(0);
					break;
				case 4:
					return;
			}
		}
	}
	
	static class LinkedList
	{
		long value;
		LinkedList next;
		static int count = 0;
		static int maxPosition = -1;
		
		public LinkedList()
		{
			this.next = null;
//			System.out.println("in constructor of LinkedList");
			
			count++;
		}

		public void add(long number, int position)
		{
			LinkedList temp = new LinkedList();
			temp.value = number;
			
			if (position > maxPosition)
				maxPosition = position;
			
			if (size[position] == 0)
			{
				first[position] = temp;
				last[position] = temp;
			}
			else
			{
				last[position].next = temp;
				last[position] = temp;
			}
			
			size[position]++;
		}
		
		public boolean contains(long number, int position)
		{
			if (size[position] == 0)
				return false;
			
			LinkedList temp = first[position];
			
			while (temp != null)
			{
				if (temp.value == number)
					return true;
				
				temp = temp.next;
			}
			
			return false;
		}
		
		public int delete(long number, int position)
		{
			if (size[position] == 0)
				return -1;
			
			if (first[position].value == number)
			{
				if (first[position].next == null)
				{
					first[position] = null;
					last[position] = null;
				}
				else
					first[position] = first[position].next;
				
				size[position]--;
				
				return 1;
			}
			
			LinkedList previous, curr;
			
			previous = first[position];
			curr = first[position].next;
			
			while (curr != null)
			{
				if (curr.value == number)
				{
					previous.next = curr.next;
					size[position]--;
					
					return 1;
				}
				
				previous = curr;
				curr = curr.next;
			}
			
			return -1;
		}
		
		public void printList(int position)
		{
			LinkedList temp = first[position];
			
			while (temp != null)
			{
				System.out.print(temp.value + " ");
				
				temp = temp.next;
			}
			
			System.out.println();
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
