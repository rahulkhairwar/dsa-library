package com.dsa.datastructures.trees.trie;

import java.io.*;
import java.util.Scanner;

/**
 * An implementation of Alphabet Trie.
 * <br />Complexity for adding a word : O(length(word)).
 * <br />Complexity for checking whether a word exists in the trie : O(length(word)).
 */
public class AlphabetTrie
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(in, out);
		solver.solve();
	}

	static class Solver
	{
		Node root;
		Scanner in;
		OutputWriter out;

		public void solve()
		{
			root = new Node();

			one:
			while (true)
			{
				System.out.println("MENU");
				System.out.println("1. Insert.");
				System.out.println("2. Check if the Trie contains a word.");
				System.out.println("3. Exit.");
				System.out.println("\nEnter your choice : ");
				System.out.println("Enter word : ");

				int choice = in.nextInt();

				switch (choice)
				{
					case 1:
						String word = in.next();
						root = insert(root, word, word.length(), 0);
						break;
					case 2:
						word = in.next();
						boolean result = contains(root, word, word.length(), 0);
						System.out.println("Trie contains " + word + " ? : " + result);
						break;
					case 3:
						break one;
				}
			}
		}

		public Node insert(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				temp = new Node();

			if (index == length - 1)
				temp.isWord = true;
			else
				temp = insert(temp, word, length, index + 1);

			node.next[pos] = temp;

			return node;
		}

		public boolean contains(Node node, String word, int length, int index)
		{
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];

			if (temp == null)
				return false;

			if (index == length - 1)
				return temp.isWord;

			return contains(temp, word, length, index + 1);
		}

		static class Node
		{
			Node[] next;
			boolean isWord;

			public Node()
			{
				next = new Node[26];
			}

		}

		public Solver(Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
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

}
