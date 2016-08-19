package com.codeforces.competitions.year2016.julytoseptember.round367div2;

import java.io.*;

/**
 * Good question on Trie(Number-Trie).
 * <br />Question <a href="http://codeforces.com/contest/706/problem/D">link</a>
 */
public final class TaskD
{
	public static void main(String[] args)
	{
		try
		{
			OutputWriter out = new OutputWriter(System.out);
			Solver solver = new Solver(out);
			solver.solve();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int q;
		Node root;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		OutputWriter out;

		void solve() throws IOException
		{
			String zeroes = "00000000000000000000000000000000";
			root = new Node();
			root = insert(root, zeroes, 0);
			q = Integer.parseInt(in.readLine());

			while (q-- > 0)
			{
				String[] tokens = in.readLine().split(" ");
				int x = Integer.parseInt(tokens[1]);
				String binary = Integer.toBinaryString(x);
				int len = binary.length();

				if (tokens[0].charAt(0) == '+')
					root = insert(root, zeroes.substring(0, 32 - len) + binary, 0);
				else if (tokens[0].charAt(0) == '-')
					delete(root, zeroes.substring(0, 32 - len) + binary, 0);
				else
					out.println(maxXor(root, zeroes.substring(0, 32 - len) + binary, 0));
			}
		}

		Node insert(Node node, String word, int index)
		{
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];

			if (temp == null)
				temp = new Node();

			if (index == 31)
				temp.isWord++;
			else
				temp = insert(temp, word, index + 1);

			temp.exists++;
			node.next[pos] = temp;

			return node;
		}

		long maxXor(Node node, String word, int index)
		{
			int pos = word.charAt(index) - '0';
			int req = pos == 1 ? 0 : 1;
			Node temp = node.next[pos];
			Node reqTemp = node.next[req];

			if (index == 31)
			{
				if (reqTemp != null && reqTemp.isWord > 0)
					return 1;

				return 0;
			}

			if (reqTemp != null)
			{
				if (reqTemp.exists > 0)
					return (1 << (31 - index)) + maxXor(reqTemp, word, index + 1);

				if (temp != null)
					return Math.max(maxXor(temp, word, index + 1), maxXor(reqTemp, word, index + 1));

				return maxXor(reqTemp, word, index + 1);

			}

			if (temp != null)
				return maxXor(temp, word, index + 1);

			return 0;
		}


		boolean delete(Node node, String word, int index)
		{
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];

			if (temp == null)
				return false;

			if (index == 31)
			{
				if (temp.isWord > 0)
				{
					temp.isWord--;

					return true;
				}

				return false;
			}

			if (delete(temp, word, index + 1))
			{
				temp.exists--;

				return true;
			}

			return false;
		}

		class Node
		{
			int isWord, exists;
			Node[] next;

			public Node()
			{
				next = new Node[2];
			}

		}

		public Solver(OutputWriter out)
		{
			this.out = out;
		}

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
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

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

}

/*

12
? 1
+ 1
+ 7
? 2
+ 3
? 1
? 6
+ 4
+ 6
+ 8
- 8
? 3

10
? 1
+ 1
+ 7
? 2
+ 3
? 1
? 6
+ 4
+ 6
? 3

*/
