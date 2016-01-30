package com.codechef.competitions.longcompetitions.year2015.september;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class NikitoshAndXor
{
	static int n, nMinusOne, maxFromLeft[], maxFromRight[];
	static Number array[];
	static Node root, root2;
	static InputReader reader;
	static OutputWriter writer;
	
	public static void main(String[] args)
	{
		NikitoshAndXor xor = new NikitoshAndXor();

		reader = xor.new InputReader(System.in);
		writer = xor.new OutputWriter(System.out);

		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}
	
	static void getAttributes()
	{
		n = reader.nextInt();
		nMinusOne = n - 1;
		
		if (n == 2)
		{
			writer.println(reader.nextInt() + reader.nextInt());
			
			return;
		}
		
		array = new Number[n];
		maxFromLeft = new int[n];
		maxFromRight = new int[n];
		
/*		for (int i = 0; i < n; i++)
		{
			array[i] = convertToBinary(reader.nextInt());
			
			if (array[i].length > maxLength)
				maxLength = array[i].length;
		}
		
		for (int i = 0; i < n; i++)
		{
			for (int j = array[i].length; j < maxLength; j++)
				array[i].binary[j] = 0;
			
			array[i].length = maxLength;
		}*/
		
		// creating 2 Tries, which already contain 0
		root = new Node(null, null);
		root2 = new Node(null, null);
		
		byte maxLength = 32;

		Number zero, ans, pre;

		zero = new Number(0, maxLength, new byte[32]);
		ans = new Number(0, maxLength, new byte[32]);
		pre = new Number(0, maxLength, new byte[32]);

		for (int i = 0; i < 32; i++)
			zero.binary[i] = 0;

		insert(root, zero, (byte) 0);
		insert(root2, zero, (byte) 0);

		for (int i = 0; i < nMinusOne; i++)
		{
			array[i] = convertToBinary(reader.nextInt());

			pre = xor(pre.binary, array[i].binary, array[i].length);

			insert(root, pre, (byte) 0);

			ans = max(
					ans,
					query(root, new Number(0, (byte) 0, new byte[32]), pre,
							(byte) 0));

			maxFromLeft[i] = ans.intValueFromBeg();
		}
		
		array[nMinusOne] = convertToBinary(reader.nextInt());
		
/*		for (int i = 0; i < nMinusOne; i++)
		{
			// pre has correct binary representation
			pre = xor(pre.binary, array[i].binary, array[i].length);

			// array[i] has inverted binary representation
			insert(root, pre, (byte) 0);
			
			// queried has correct binary representation
			Number queried = query(root, new Number(0, (byte) 0, new byte[32]),
					pre, (byte) 0);
			
			// ans has correct binary representation
			ans = max(ans, queried);
			
			maxFromLeft[i] = ans.intValueFromBeg();
		}*/
		
		ans = new Number(0, (byte) maxLength, new byte[32]);
		pre = new Number(0, (byte) maxLength, new byte[32]);
		
		int count, temp, maxSum;
		
		count = n - 2;
		maxSum = 0;
		
		for (int i = nMinusOne; i > 0; i--)
		{
			pre = xor(pre.binary, array[i].binary, array[i].length);
			
			insert(root2, pre, (byte) 0);
			
			ans = max(ans, query(root2,
					new Number(0, (byte) 0, new byte[32]), pre, (byte) 0));
			
			temp = maxFromLeft[count--] + ans.intValueFromBeg();
			
			if (temp > maxSum)
				maxSum = temp;
		}
		
		writer.println(maxSum);
	}
	
	/**
	 * Returns an object of Number, which contains the bitwise-xor of num1 and
	 * num2, where num1 is the correct binary representation, and num2 is the
	 * inverted binary representation and the length of the bitwise-xor of n1
	 * and n2.
	 * 
	 * @param num1
	 *            the binary representation of the first number.
	 * @param num2
	 *            the inverted binary representation of the second number.
	 * @param num2Length
	 *            the length of the binary representation of num2.
	 * @return an object of Number, which contains the binary representation of
	 *         n1 ^ n2.
	 */
	static Number xor(byte num1[], byte num2[], byte num2Length)
	{
		byte length, ans[];
		
		length = 0;
		ans = new byte[32];
		
		for (int i = 0, j = num2Length - 1; j >= 0; i++, j--)
		{
			if (num1[i] != num2[j])
				ans[length++] = 1;
			else
				ans[length++] = 0;
		}
		
		return new Number(0, length, ans);
	}
	
	/**
	 * Converts a decimal integer into it's binary representation, and
	 * stores(inverted) it in the binary[] array of the returned Number.
	 * 
	 * @param number
	 *            the number to be converted into it's binary representation.
	 * @return an object of Number, which stores the number in decimal form,
	 *         it's binary representation, and also the length of the binary
	 *         representation of the number.
	 */
	static Number convertToBinary(int number)
	{
		Number num = new Number(number, (byte) 0, new byte[32]);
		
		while (number != 0)
		{
			num.binary[num.length++] = (byte) (number % 2);
			
			number /= 2;
		}
		
		while (num.length < 32)
			num.binary[num.length++] = 0;
		
		return num;
	}
	
	/**
	 * Perfect!!
	 * 
	 * Inserts an integer into the Trie, using it's binary representation.
	 * 
	 * @param node
	 *            the current node of the Trie.
	 * @param number
	 *            the number to be inserted in the Trie.
	 * @param index
	 *            the index of the binary representation of the number, using
	 *            which we compare the current node's current bit.
	 */
	static void insert(Node node, Number number, byte index)
	{
		if (index == number.length)
			return;

		// current bit in the binary representation of the number is 0
		if (number.binary[index] == 0)
		{
			// currently, there doesn't exist any number which has a zero at
			// this place
			if (node.leftChild == null)
				node.leftChild = new Node(null, null);

			// inserting the rest of the number
			insert(node.leftChild, number, (byte) (index + 1));
		}
		else
		{
			if (node.rightChild == null)
				node.rightChild = new Node(null, null);

			insert(node.rightChild, number, (byte) (index + 1));
		}
	}

	static Number max(Number num1, Number num2)
	{
		int i = 0;

		while (i < num1.length && num1.binary[i] == num2.binary[i])
			i++;

		if (i < num1.length && num1.binary[i] == 1)
			return num1;
		else
			return num2;
	}
	
	static Number query(Node node, Number max, Number number,
			byte numberLength)
	{
/*		if (numberLength == -1)
			return max;*/
		
		if (node == null)
			return new Number(0, (byte) 1, new byte[32]);

		if (number.binary[numberLength] == 0)
		{
			if (node.rightChild == null)
			{
				max.binary[max.length++] = 0;

				if (numberLength < number.length - 1)
					return query(node.leftChild, max, number,
							(byte) (numberLength + 1));
				else
					return max;
			}
			else
			{
				max.binary[max.length++] = 1;

				if (numberLength < number.length - 1)
					if (node.rightChild != null)
						return query(node.rightChild, max, number,
								(byte) (numberLength + 1));
					else
						return max;
				else
					return max;
			}
		}
		else
		{
			if (node.leftChild == null)
			{
				max.binary[max.length++] = 0;

				if (numberLength < number.length - 1)
					return query(node.rightChild, max, number,
							(byte) (numberLength + 1));
				else
					return max;
			}
			else
			{
				max.binary[max.length++] = 1;

				if (numberLength < number.length - 1)
					return query(node.leftChild, max, number,
							(byte) (numberLength + 1));
				else
					return max;
			}
		}
	}
	
	/**
	 * Inorder traversal of the Trie.
	 * 
	 * @param node
	 *            the current node of the Trie.
	 */
	static void traversal(Node node)
	{
		if (node == null)
			return;
		
		traversal(node.leftChild);
		
		//System.out.print(node.bit + " ");
		
		traversal(node.rightChild);
	}
	
	static class Number
	{
		int number;
		byte length, binary[];
		
		public Number(int number, byte length, byte binary[])
		{
			this.number = number;
			this.length = length;
			this.binary = binary;
		}
		
		public String binaryRepresentation()
		{
			String bin = "";
			
			for (int i = length - 1; i >= 0; i--)
				bin += binary[i];
			
			return bin;
		}
		
		public String binaryRepresentationFromBeg()
		{
			String bin = "";
			
			for (int i = 0; i < length; i++)
				bin += binary[i];
			
			return bin;
		}
		
		public int intValue()
		{
			int value, counter;
			
			value = counter = 0;
			
			for (int i = 0; i < length; i++)
				value += (binary[i] * pow(2, counter++));
			
			return value;
		}
		
		public int intValueFromBeg()
		{
			int value, counter;
			
			value = counter = 0;
			
			for (int i = length - 1; i >= 0; i--)
				value += (binary[i] * Math.pow(2, counter++));
			
			return value;
		}
		
		public double pow(int number, int power)
		{
			if (power == 0)
				return 1;
			
			if (power == 1)
				return number;
			
			if (power % 2 == 0)
				return pow(number * number, power / 2);
			else
				return pow(number * number, power / 2) * number;
		}
		
	}
	
	static class Node
	{
		Node leftChild, rightChild;
		
		public Node(Node leftChild, Node rightChild)
		{
			this.leftChild = leftChild;
			this.rightChild = rightChild;
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

/*

2
1000000000 909009000
ans : 1909009000

3
1000000000 909009000 1000000000
ans : 2000000000

3
1000000000 909009000 800050307
ans : 1909009000

5
11 21 30 15 7
ans :

5
11 21 0 1 2
ans : 33 (11 ^ 21 ^ 0 ^ 1 + 2)		CORRECT ANSWER!!!!

3
2 7 4
ans : 11 (7 + 4) 	CORRECT ANSWER!!!!

4
2 4 3 7
ans : 14 (4 ^ 3 + 7)	CORRECT ANSWER!!!!

5
1 8 4 2 0
ans : 15 (1 ^ 8 ^ 4 ^ 2 + 0)	CORRECT ANSWER!!!!

6
1 8 4 2 0 4
ans : 19 (1 ^ 8 ^ 4 ^ 2 + 4)	CORRECT ANSWER!!!!

3
2 4 5
ans : 11 (2 ^ 4 + 5)	CORRECT ANSWER!!!!

3
1 8 4
ans : 13 (1 ^ 8 + 4 OR 1 + 8 ^ 4)	CORRECT ANSWER!!!!

2
6 4
ans : 10	CORRECT ANSWER!!!!

10
3 0 2 0 293829 193482927 1836 29 1928 18327
ans : 193778533

10
3 0 2 1 10 9 8 4 7 6
ans : 24

25
3 0 2 0 293829 193482927 1836 29 1928 18327 29734 293743 92759 2975432 29572 29529 9265923 9256932 9265 9265 295792365 926592 926539 29563 92345
ans : 492034287

30
30 28 8374 293829 193482927 1836 29 1928 18327 29734 293743 92759 2975432 29572 29529 9265923 9256932 9265 9265 295792365 926592 926539 29563 92345 23 2 5027 297452973 29754 29073
ans : 734712537

2^29+2^28+2^27+2^26+2^25+2^24+2^23+2^22+2^21+2^20+2^19+2^18+2^17+

*/
