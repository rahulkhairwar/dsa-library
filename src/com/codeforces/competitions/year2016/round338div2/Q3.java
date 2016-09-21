package com.codeforces.competitions.year2016.round338div2;

import java.awt.*;
import java.io.*;
import java.util.InputMismatchException;

public final class Q3
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);

		solver.solve(1);

		out.flush();

		in.close();
		out.close();
	}

	static class Solver
	{
		String s, t;
		Node tree, reverseTree;
		int endIndex;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			s = in.next();
			t = in.next();

			int sLength, tLength;

			sLength = s.length();
			tLength = t.length();

			int[] countS, countT;

			countS = new int[26];
			countT = new int[26];

			for (int i = 0; i < sLength; i++)
				countS[s.charAt(i) - 'a']++;

			for (int i = 0; i < tLength; i++)
				countT[t.charAt(i) - 'a']++;

			for (int i = 0; i < 26; i++)
				if (countT[i] > 0 && countS[i] == 0)
				{
					out.println(-1);

					return;
				}

			String reverse = new StringBuilder(s).reverse().toString();
			Point[] answer = new Point[tLength];
			int count, doneUpto;

			count = 0;
			doneUpto = -1;

			for (int i = 0; i < tLength; i++)
			{
				if (doneUpto >= tLength - 1)
					break;

				int len, revLen, tempOne, tempTwo;

				len = matchesUpto(s, t, doneUpto + 1);
				tempOne = endIndex;

				endIndex = -1;
				revLen = matchesUpto(reverse, t, doneUpto + 1);

				if (len >= revLen)
				{
					answer[count++] = new Point(tempOne - len + 1, tempOne);
					doneUpto += len;
				}
				else
				{
					answer[count++] = new Point(sLength - (endIndex - revLen + 1) + 1, sLength - endIndex + 1);
					doneUpto += revLen;
				}
			}

			out.println(count);

			for (int i = 0; i < count; i++)
				out.println(answer[i].x + " " + answer[i].y);
		}

		int matchesUpto(String word, String check, int checkFrom)
		{
			int wordLength, checkLength, max;

			wordLength = word.length();
			checkLength = check.length();
			max = 0;

			for (int i = 0, j, k; i < wordLength; i++)
			{
				j = checkFrom;

				while (i < wordLength && word.charAt(i) != check.charAt(j))
					i++;

				if (i == wordLength)
					break;

				k = i;

				while (k < wordLength && j < checkLength && word.charAt(k) == check.charAt(j))
				{
					k++;
					j++;
				}

				int currLength = k - i;

				if (currLength > max)
				{
					max = currLength;
					endIndex = k;
				}

				if (k == wordLength || j == checkLength)
					break;
			}

			return max;
		}

		/**
		 * Solution using Trie, but it's too slow (getting TLE on test #20 :( ).
		 */
		void solveWithTrie(int testNumber)
		{
			s = in.next();
			t = in.next();
			tree = new Node(0);
			reverseTree = new Node(0);

			int sLength, tLength;

			sLength = s.length();
			tLength = t.length();

			int[] countS, countT;

			countS = new int[26];
			countT = new int[26];

			for (int i = 0; i < sLength; i++)
				countS[s.charAt(i) - 'a']++;

			for (int i = 0; i < tLength; i++)
				countT[t.charAt(i) - 'a']++;

			for (int i = 0; i < 26; i++)
				if (countT[i] > 0 && countS[i] == 0)
				{
					out.println(-1);

					return;
				}

			for (int i = 0; i < sLength; i++)
			{
				String temp = s.substring(i, sLength);
				int tempLength = temp.length();

				if (!contains(tree, temp, tempLength, 0))
					tree = insert(tree, temp, tempLength, 0, i);
			}

			String reverse = new StringBuilder(s).reverse().toString();

			for (int i = 0; i < sLength; i++)
			{
				String temp = reverse.substring(i, sLength);

				reverseTree = insert(reverseTree, temp, temp.length(), 0, i);
			}

			Point[] answer = new Point[tLength];
			int count, doneUpto;

			count = 0;
			doneUpto = -1;

			for (int i = 0; i < tLength; i++)
			{
				if (doneUpto >= tLength - 1)
					break;

				Point one, two;
				int x, y;
				int oneLength, twoLength;

				x = matchUpto(tree, t, tLength, doneUpto + 1);
				oneLength = x - doneUpto;
				one = new Point(endIndex - oneLength + 2, endIndex + 1);

				endIndex = -1;
				y = matchUpto(reverseTree, t, tLength, doneUpto + 1);
				twoLength = y - doneUpto;
				two = new Point(sLength - (endIndex - twoLength + 1), sLength - endIndex);

				if (oneLength >= twoLength)
				{
					answer[count++] = new Point(one.x, one.y);
					doneUpto += oneLength;
				}
				else
				{
					answer[count++] = new Point(two.x, two.y);
					doneUpto += twoLength;
				}
			}

			out.println(count);

			for (int i = 0; i < count; i++)
				out.println(answer[i].x + " " + answer[i].y);
		}

		Node insert(Node node, String word, int length, int index, int wordIndex)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				temp = new Node(wordIndex);

			if (index < length - 1)
				temp = insert(temp, word, length, index + 1, wordIndex + 1);

			node.next[pos] = temp;

			return node;
		}

		int matchUpto(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				return index - 1;

			if (index == length - 1)
			{
				endIndex = temp.wordIndex;

				return index;
			}
			else
			{
				if (temp.next[word.charAt(index + 1) - 'a'] == null)
				{
					endIndex = temp.wordIndex;

					return index;
				}

				return matchUpto(temp, word, length, index + 1);
			}
		}

		boolean contains(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				return false;

			if (index == length - 1)
				return true;
			else
				return contains(temp, word, length, index + 1);
		}

		class Node
		{
			Node[] next;
			int wordIndex;

			public Node(int wordIndex)
			{
				next = new Node[26];
				this.wordIndex = wordIndex;
			}

		}

		public Solver(InputReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
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

/*

aaabrytaaa
ayrat

aaabrytaab
ayrat

*/
