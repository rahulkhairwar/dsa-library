package com.dsa.datastructures.sets;

import java.io.*;
import java.util.*;

/**
 * An implementation of Disjoint Set Union data structure.
 */
public class DisjointSet
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver();

		solver.solve(in, out);
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		HashMap<Integer, Node> disjointSets;
		Scanner in;
		OutputWriter out;

		void solve(Scanner in, OutputWriter out)
		{
			this.in = in;
			this.out = out;

			disjointSets = new HashMap<>();

			while (true)
			{
				int choice;

				System.out.println("Menu :");
				System.out.println("1. Make set.");
				System.out.println("2. Join 2 sets.");
				System.out.println("3. Find parent of an item.");
				System.out.println("4. Exit.");
				System.out.print("Enter your choice : ");

				choice = in.nextInt();

				System.out.println();

				switch (choice)
				{
					case 1:
						System.out.print("Enter a key : ");
						int key = in.nextInt();
						System.out.println();
						makeSet(key);
						break;

					case 2:
						System.out.print("Enter a key : ");
						key = in.nextInt();

						System.out.println("\nParent of " + key + " is : " + findParent(key).key);
						break;

					case 3:
						System.out.print("Enter 2 keys : ");
						int one, two;

						one = in.nextInt();
						two = in.nextInt();
						System.out.println();
						union(one, two);
						break;

					case 4:
						return;
				}
			}
		}

		/**
		 * Function to create a new disjoint set with 1 item.
		 *
		 * @param key key of the item.
		 */
		void makeSet(int key)
		{
			Node temp = new Node(key);

			if (disjointSets.containsKey(key))
				return;

			disjointSets.put(key, temp);
		}

		/**
		 * Function to do union of 2 disjoint set forests, by Rank(height of forest).
		 *
		 * @param one key of first disjoint set forest.
		 * @param two key of second disjoint set forest.
		 */
		void union(int one, int two)
		{
			Node parentOne, parentTwo;

			parentOne = findParent(one);
			parentTwo = findParent(two);

			// already connected.
			if (parentOne.parent.key == parentTwo.parent.key)
				return;

			if (parentOne.height > parentTwo.height)
				parentTwo.parent = parentOne;
			else if (parentOne.height < parentTwo.height)
				parentOne.parent = parentTwo;
			else
			{
				parentTwo.parent = parentOne;
				parentOne.height++;
			}
		}

		/**
		 * Function to find the parent(disjoint set representative, i.e., highest ancestor) of an item, given it's
		 * key. Also, while finding the parent, this function does path compression for all items on the way to the
		 * parent.
		 *
		 * @param key key of the item whose parent is to be found.
		 * @return the found parent of the item.
		 */
		Node findParent(int key)
		{
			Node temp = disjointSets.get(key);

			if (temp.key == temp.parent.key)
				return temp;
			else
			{
				Node parent = findParent(temp.parent.key);
				temp.parent = parent;

				return parent;
			}
		}

		Node findParent(Node node)
		{
			if (node.parent == node)
				return node;
			else
				return node.parent = findParent(node.parent);
		}

		class Node
		{
			int key, height;
			Node parent;

			public Node(int key)
			{
				this.key = key;
				height = 0;
				parent = this;
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
