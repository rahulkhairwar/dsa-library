package com.codeforces.competitions.year2016.jantomarch.manthancodefest;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Good question. Trie with simple 1D DP.
 * <br />Question <a href="http://codeforces.com/contest/633/problem/C">link</a>
 */
public final class TaskC
{
	public static void main(String[] args) throws InterruptedException
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
		int n, m;
		String text, words[];
		ArrayList<Point>[] isWord;
		boolean[] checked;
		Node root;
		ArrayList<String> answer;
		InputReader in;
		OutputWriter out;

		void solve(int testNumber)
		{
			n = in.nextInt();
			text = in.next();
			m = in.nextInt();
			words = new String[m + 1];
			isWord = new ArrayList[n + 1];
			checked = new boolean[n + 1];
			root = new Node();
			answer = new ArrayList<>();

			for (int i = 1; i <= m; i++)
			{
				words[i] = in.next();
				root = insert(root, new StringBuilder(words[i].toLowerCase()).reverse().toString(), words[i].length(),
						0, i);
			}

			for (int i = 0; i <= n; i++)
				isWord[i] = new ArrayList<>();

			dfs(-1);

			int size = answer.size();

			for (int i = size - 1; i >= 0; i--)
				out.print(answer.get(i) + " ");
		}

		boolean dfs(int doneUpto)
		{
			traverse(root, text, n, doneUpto + 1, doneUpto + 1);
			int size = isWord[doneUpto + 1].size();

			for (int i = 0; i < size; i++)
			{
				String currWord = words[isWord[doneUpto + 1].get(i).y];
				int temp = doneUpto + currWord.length();

				if (temp == n - 1)
				{
					answer.add(currWord);

					return true;
				}

				if (checked[temp])
					continue;

				if (dfs(temp))
				{
					answer.add(currWord);

					return true;
				}
				else
					checked[temp] = true;
			}

			return false;
		}

		Node insert(Node node, String word, int length, int index, int wordIndex)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				temp = new Node();

			if (index == length - 1)
			{
				temp.wordIndex = wordIndex;
				temp.isWord = true;
			}
			else
				temp = insert(temp, word, length, index + 1, wordIndex);

			node.next[pos] = temp;

			return node;
		}

		void traverse(Node node, String word, int length, int from, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				return;

			if (temp.isWord)
				isWord[from].add(new Point(index, temp.wordIndex));

			if (index == length - 1)
				return;

			traverse(temp, word, length, from, index + 1);
		}

		class Node
		{
			Node[] next;
			int wordIndex;
			boolean isWord;

			public Node()
			{
				wordIndex = -1;
				next = new Node[26];
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

7
uhaluar
5
rahul
rah
ra
ahu
ul

7
uhaluar
5
rahul
rah
ahu
ul
raul

7
kirkirk
2
rik
krik

10
aaaaaaaaaa
2
a
A

10
aaaaaaaaaa
10
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
aaaaaaaaa
a

10
aaaaaaaaaa
3
aaaaaaaaa
aaa
aaaaa


*/
