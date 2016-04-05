package com.facebookhackercup.practice;

import java.io.*;
import java.util.Scanner;

/**
 * Created by rahulkhairwar on 04/04/16.
 */
public class Autocomplete
{
	public static void main(String[] args)
	{
		Scanner in = null;

		try
		{
			in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive " +
					"Programming/src/com/facebookhackercup/practice/autocomplete.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		OutputWriter out = new OutputWriter(System.out);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 28);

		thread.start();
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		out.flush();

		in.close();
		out.close();
	}

	static class Solver implements Runnable
	{
		int t, n, count;
		Node dictionary;
		Scanner in;
		OutputWriter out;

		public Solver(Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		Node insert(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				temp = new Node();

			if (index == length - 1)
			{
				temp.isWord = true;
				temp.wordCount++;
			}
			else
				temp = insert(temp, word, length, index + 1);

			node.next[pos] = temp;
			node.wordCount++;

			return node;
		}

		void check(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			count++;

			if (temp.wordCount == 1 || index == length - 1)
				return;

			check(temp, word, length, index + 1);
		}

		@Override
		public void run()
		{
			t = in.nextInt();

			for (int i = 0; i < t; i++)
			{
				n = in.nextInt();
				count = 0;

				dictionary = new Node();

				for (int j = 0; j < n; j++)
				{
					String word = in.next();

					dictionary = insert(dictionary, word, word.length(), 0);
					check(dictionary, word, word.length(), 0);
				}

				out.println("Case #" + (i + 1) + ": " + count);
			}
		}

		class Node
		{
			Node[] next;
			int wordCount;
			boolean isWord;

			public Node()
			{
				next = new Node[26];
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

	}

}

/*

1
5
hi
hello
lol
hills
hill

5
5
hi
hello
lol
hills
hill
5
a
aa
aaa
aaaa
aaaaa
5
aaaaa
aaaa
aaa
aa
a
6
to
be
or
not
two
bee
3
having
fun
yet

 */
