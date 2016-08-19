package com.dsa.datastructures.trees.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * An implementation of Number Trie, which stores numbers in their binary representation.
 * <br />Complexity for adding a 32-bit integer to the trie : O(32), i.e., constant.
 * <br />Complexity for checking whether an integer exists in the trie : O(32), i.e., constant.
 * <br />Complexity for finding the maximum XOR of an integer with any integer present in the trie : O(32), i.e.,
 * constant.
 */
public class NumberTrie
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			Solver solver = new Solver(in);

			solver.solve();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		Node root;
		BufferedReader in;

		void solve() throws IOException
		{
			root = new Node();

			int choice;
			String zeroes = "00000000000000000000000000000000";
			root = insert(root, zeroes, 0);

			do
			{
				System.out.println("1. Insert an integer in the trie.");
				System.out.println("2. Remove an integer from the trie.");
				System.out.println("3. Check if an integer exists in the trie.");
				System.out.println("4. Find the maximum possible XOR of an integer with any of the integers present "
						+ "in the trie.");
				System.out.println("5. Exit.");
				System.out.print("\nEnter your choice : ");
				choice = Integer.parseInt(in.readLine());

				int x;
				String binary;
				int len;

				switch (choice)
				{
					case 1 :
						System.out.print("Enter an integer : ");
						x = Integer.parseInt(in.readLine());
						binary = Integer.toBinaryString(x);
						len = binary.length();
						root = insert(root, zeroes.substring(0, 32 - len) + binary, 0);

						System.out.print("The integer " + x + " has been added to the trie.");
						break;
					case 2 :
						System.out.print("Enter the integer you want to remove from the trie : ");
						x = Integer.parseInt(in.readLine());
						binary = Integer.toBinaryString(x);
						len = binary.length();

						if (x == 0)
						{
							System.out.print("You cannot delete 0 from the trie.");

							continue;
						}

						if (delete(root, zeroes.substring(0, 32 - len) + binary, 0))
							System.out.print("One occurrence of integer " + x + " has been deleted from the trie.");
						else
							System.out.print("Integer " + x + " didn't exist in the trie.");

						break;
					case 3 :
						System.out.print("Enter an integer : ");
						x = Integer.parseInt(in.readLine());
						binary = Integer.toBinaryString(x);
						len = binary.length();

						if (contains(root, zeroes.substring(0, 32 - len) + binary, 0))
							System.out.print("The integer " + x + " exists in the trie.");
						else
							System.out.print("The integer " + x + " does not exist in the trie.");

						break;
					case 4 :
						System.out.print("Enter the integer whose maximum possible xor you want to find : ");
						x = Integer.parseInt(in.readLine());
						binary = Integer.toBinaryString(x);
						len = binary.length();

						System.out.print("The maximum possible xor of " + x + " with any of the integers present in"
								+ " the trie is : " + maxXor(root, zeroes.substring(0, 32 - len) + binary, 0));
						break;
					case 5 :
						break;
				}

				System.out.println("\n");
			} while (choice != 5);
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

		boolean contains(Node node, String word, int index)
		{
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];

			if (temp == null)
				return false;

			if (index == 31)
				return temp.isWord > 0;

			return contains(temp, word, index + 1);
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

			// to check whether the number has been deleted from the trie if it existed, or not, if it didn't already
			// exist.
			if (delete(temp, word, index + 1))
			{
				temp.exists--;

				return true;
			}

			return false;
		}

		long maxXor(Node node, String word, int index)
		{
			int pos = word.charAt(index) - '0';
			int reqPos = pos == 1 ? 0 : 1;
			Node temp = node.next[pos];
			Node reqTemp = node.next[reqPos];

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
					return Math.max(maxXor(reqTemp, word, index + 1), maxXor(temp, word, index + 1));

				return maxXor(reqTemp, word, index + 1);
			}

			if (temp != null)
				return maxXor(temp, word, index + 1);

			return 0;
		}

		class Node
		{
			int exists, isWord;
			Node[] next;

			public Node()
			{
				next = new Node[2];
			}

		}

		public Solver(BufferedReader in)
		{
			this.in = in;
		}

	}

}
