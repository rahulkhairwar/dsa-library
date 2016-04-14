package com.dsa.datastructures.trees.trie;

import net.egork.chelper.util.OutputWriter;

import java.util.Scanner;

/**
 * Created by rahulkhairwar on 04/04/16.
 */
public class AlphabetTrie
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver();

		solver.solve(1, in, out);
	}

	static class Solver
	{
		Node root;
		Scanner in;
		OutputWriter out;

		public void solve(int testNumber, Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

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
			{
				if (temp.isWord)
					return true;

				return false;
			}
			else
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

	}

}
