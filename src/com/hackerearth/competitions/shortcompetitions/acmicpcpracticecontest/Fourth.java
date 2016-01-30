package com.hackerearth.competitions.shortcompetitions.acmicpcpracticecontest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class Fourth
{
	static int n, m;
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Fourth fourth = new Fourth();
		
		reader = fourth.new InputReader(System.in);
		writer = fourth.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		m = reader.nextInt();
		
		Box boxes[] = new Box[n];
		
		int len;
		
		//List<Box> boxList = new ArrayList<Box>();
		// boxMap = new HashMap<Integer, Box>();
		
/*		TreeMap<Integer, Box> boxMap = new TreeMap<Integer, Box>(new Comparator<Box>()
		{
			
		});*/
		
		TreeMap<Integer, Box> boxTree = new TreeMap<Integer, Box>();
		
/*		TreeSet<Box> boxTree = new TreeSet<Box>(new Comparator<Box>()
		{
			@Override
			public int compare(Box o1, Box o2)
			{
				//System.out.println("box1.minEl : " + o1.minElement + ", box2.minEl : " + o2.minElement);
				return o1.minElement - o2.minElement;
			}
		});*/
		
		for (int i = 0; i < n; i++)
		{
			len = reader.nextInt();
			
			List<Integer> temp = new ArrayList<Integer>();
			
			for (int j = 0; j < len; j++)
				temp.add(reader.nextInt());
			
			int arr[] = findMaxSum(len, temp);
			
/*			System.out.println("box[" + i + "].sum : " + arr[0] + ", left : "
					+ arr[1] + ", right : " + arr[2]);*/
			
			boxes[i] = new Box(arr[0], len, arr[1], arr[2], temp);
			boxes[i].changeMin();
			
			System.out.println("adding into boxTree!! with i : " + i
					+ ", boxTree.size : " + boxTree.size());
			boxTree.put(i, boxes[i]);
			System.out.println("adding into boxTree!! with i : " + i
					+ ", boxTree.size : " + boxTree.size());
		}
		
		/**
		 * 
		 * Perfect till here!!
		 * 
		 * 
		 */
		
		System.out.println("****Sorted boxTree size : " + boxTree.size());
		
		Set<Integer> keys = boxTree.keySet();
		Iterator<Integer> iterator = keys.iterator();
		
		while (iterator.hasNext())
		{
			int index = iterator.next();
			Box temp = boxTree.get(index);
		}
		
		int totalLength = 0;
		//Collections tree = boxTree.
		//Iterator<Box> iterator = boxTree.iterator();
		
		while (iterator.hasNext())
		{
			int index = iterator.next();
			Box temp = boxTree.get(index);
			
			System.out.println("minElement : " + temp.minElement
					+ ", minElIndex : " + temp.minElementIndex + ", sum : "
					+ temp.sum);
		}
			
		keys = boxTree.keySet();
		iterator = keys.iterator();
		
		System.out.println("sorted boxTree : ");
		
		while (iterator.hasNext())
		{
			int index = iterator.next();
			Box temp = boxTree.get(index);
			
			//totalLength += (temp.right - temp.left + 1);
			
			System.out.println();
		}
		
		/*while (totalLength > m)
		{
			Box min = boxTree.firstEntry();
			
			boxTree.remove(min);
			
			if (min.isMinLeft)
			{
				min.sum -= min.array.get(min.left);
				min.boxLength--;
				min.left++;
				
				min.changeMin();
			}
			else
			{
				min.sum -= min.array.get(min.right);
				min.boxLength--;
				min.right--;
				
				min.changeMin();
			}
			
			boxTree.add(min);
			
			totalLength--;
		}
		
		long totalSum = 0;
		
		iterator = boxTree.iterator();
		Box temp;
		int counter = 0;
		
		while (iterator.hasNext())
		{
			temp = iterator.next();
			
			System.out.println("box[" + counter + "].sum : " + temp.sum + ", left : " + temp.left + ", right : " + temp.right);
			totalSum += temp.sum;
			counter++;
		}
		
		writer.println(totalSum);*/
	}
	
	static int[] findMaxSum(int size, List<Integer> array)
	{
		int value, currentSum, currentIndex, maxSum, maxSumLeftIndex, maxSumRightIndex;

		currentSum = 0;
		maxSum = Integer.MIN_VALUE;
		currentIndex = maxSumLeftIndex = maxSumRightIndex = -1;

		for (int i = 0; i < size; i++)
		{
			value = currentSum + array.get(i);

			if (value > 0)
			{
				if (currentSum == 0)
					currentIndex = i;

				currentSum = value;
			}
			else
				currentSum = 0;

			if (currentSum > maxSum)
			{
				maxSum = currentSum;
				maxSumLeftIndex = currentIndex;
				maxSumRightIndex = i;
			}
		}

		return new int[]
		{ maxSum, maxSumLeftIndex, maxSumRightIndex };
	}
	
	static class Box implements Comparable<Box>
	{
		long sum;
		int boxLength, left, right, minElement, minElementIndex;
		boolean isMinLeft;
		List<Integer> array;

		public Box(long sum, int boxLength, int left, int right, List<Integer> array)
		{
			this.sum = sum;
			this.boxLength = boxLength;
			this.left = left;
			this.right = right;
			this.array = array;
			
			//this.minElement = Math.min(array.get(left), array.get(right));
		}
		
		public void changeMin()
		{
			if (array.get(left) < array.get(right))
			{
				minElementIndex = left;
				minElement = array.get(left);
				isMinLeft = true;
			}
			else
			{
				minElementIndex = right;
				minElement = array.get(right);
				isMinLeft = false;
			}
		}

		@Override
		public int compareTo(Box o)
		{
			return this.minElement - o.minElement;
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
