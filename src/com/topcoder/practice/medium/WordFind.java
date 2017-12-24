package com.topcoder.practice.medium;

public class WordFind
{
	private static Node root;

	public static String[] findWords(String[] grid, String[] wordList)
	{
		insertWords(grid);

		int len = wordList.length;
		Triplet[] triplets = new Triplet[len];

		for (int i = 0; i < len; i++)
			triplets[i] = find(root, wordList[i], wordList[i].length(), 0);

		String[] answers = new String[len];

		for (int i = 0; i < len; i++)
		{
			if (triplets[i].type == 1)
				answers[i] = "" + triplets[i].row + " " + (triplets[i].col - wordList[i].length() + 1);
			else if (triplets[i].type == 2)
				answers[i] = "" + (triplets[i].row - wordList[i].length() + 1) + " " + triplets[i].col;
			else if (triplets[i].type == 3)
				answers[i] = "" + (triplets[i].row - wordList[i].length() + 1) + " " + (
						triplets[i].col - wordList[i].length() + 1);
			else
				answers[i] = "";
		}

		return answers;
	}

	private static void insertWords(String[] grid)
	{
		int n = grid.length;
		int len = grid[0].length();

		root = new Node();

		// right
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < len; j++)
				root = insert(root, grid[i].substring(j, len), len - j, 0, 1, i, j);
		}

		// down
		for (int i = 0; i < len; i++)
		{
			String curr = "";

			for (int j = 0; j < n; j++)
				curr += grid[j].charAt(i);

			for (int j = 0; j < n; j++)
				root = insert(root, curr.substring(j, n), n - j, 0, 2, j, i);
		}

		// down-right
		for (int i = 0; i < n; i++)
		{
			String curr = "";

			for (int j = i, k = 0; j < n && k < len; j++, k++)
				curr += grid[j].charAt(k);

			int length = curr.length();

			for (int j = 0; j < length; j++)
				root = insert(root, curr.substring(j, length), length - j, 0, 3, i + j, j);
		}

		for (int i = 1; i < len; i++)
		{
			String curr = "";

			for (int j = 0, k = i; j < n && k < len; j++, k++)
				curr += grid[j].charAt(k);

			if (curr.length() > 0)
			{
				int length = curr.length();

				for (int j = 0; j < length; j++)
					root = insert(root, curr.substring(j, length), length - j, 0, 3, j, i + j);
			}
		}
	}

	// types :
	// 1 = right
	// 2 = down
	// 3 = down-right
	private static Node insert(Node node, String word, int length, int index, int type, int row, int col)
	{
		int pos = word.charAt(index) - 'A';
		Node temp = node.next[pos];

		if (temp == null)
		{
			temp = new Node();
			temp.isWord = true;
			temp.row = row;
			temp.col = col;
			temp.type = type;
		}
		else
		{
			int prevStartRow = temp.row;
			int prevStartCol = temp.col;
			int currStartRow = row;
			int currStartCol = col;

			if (temp.type == 1)
				prevStartCol -= index;
			else if (temp.type == 2)
				prevStartRow -= index;
			else
			{
				prevStartRow -= index;
				prevStartCol -= index;
			}

			if (type == 1)
				currStartCol -= index;
			else if (type == 2)
				currStartRow -= index;
			else
			{
				currStartRow -= index;
				currStartCol -= index;
			}

			if (prevStartRow > currStartRow || (prevStartRow == currStartRow && prevStartCol > currStartCol))
			{
				temp.row = row;
				temp.col = col;
				temp.type = type;
			}
		}

		if (type == 1)
			col++;
		else if (type == 2)
			row++;
		else
		{
			row++;
			col++;
		}

		if (index < length - 1)
			temp = insert(temp, word, length, index + 1, type, row, col);

		node.next[pos] = temp;

		return node;
	}

	private static Triplet find(Node node, String word, int length, int index)
	{
		int pos = word.charAt(index) - 'A';
		Node temp = node.next[pos];

		if (temp == null)
			return new Triplet(-1, -1, -1);

		if (index == length - 1 && temp.isWord)
			return new Triplet(temp.type, temp.row, temp.col);

		return find(temp, word, length, index + 1);
	}

	static class Node
	{
		boolean isWord;
		int row, col, type;
		Node[] next;

		Node()
		{
			next = new Node[26];
		}

	}

	static class Triplet
	{
		int type, row, col;

		Triplet(int type, int row, int col)
		{
			this.type = type;
			this.row = row;
			this.col = col;
		}

	}

}
