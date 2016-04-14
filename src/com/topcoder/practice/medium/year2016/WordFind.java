package com.topcoder.practice.medium.year2016;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by rahulkhairwar on 06/04/16.
 */
public class WordFind
{
	static int rows, columns;
	static char[][] box;
	static Node root;
	static Point foundPosition;
	static String[] answer;

	public static void main(String[] args)
	{
		String[] grid = {"rahul", "abort", "hello", "urock", "lions", "sucks"};
		String[] wordList = {"rahul", "he", "ock", "ion", "rahuls", "hel", "rlcn", "ltokss", "lu", "aolk", "l",
				"rahool"};

		findWords(grid, wordList);
	}

	static String[] findWords(String[] grid, String[] wordList)
	{
		rows = grid.length;
		columns = grid[0].length();
		box = new char[rows][];
		root = new Node(new HashMap<>());

		for (int i = 0; i < rows; i++)
			box[i] = grid[i].toCharArray();

		for (int i = 0; i < rows; i++)
		{
			String curr = "";

			for (int j = columns - 1; j >= 0; j--)
			{
				curr = "" + box[i][j] + curr;
//				System.out.println("**** calling insert on word : " + curr);
				root = insertWord(root, curr, curr.length(), 0, i, j, 2);
			}
		}

		for (int i = 0; i < columns; i++)
		{
			String curr = "";

			for (int j = rows - 1; j >= 0; j--)
			{
				curr = "" + box[j][i] + curr;
//				System.out.println("$$$$ calling insert on word : " + curr);
				root = insertWord(root, curr, curr.length(), 0, j, i, 1);
			}
		}

		int j, k;

		for (int i = 0; i < columns; i++)
		{
			String curr = "";

			for (j = i, k = rows - 1; j >= 0 && k >= 0; j--, k--)
			{
				curr = "" + box[k][j] + curr;

//				System.out.println("@@@@ calling insert on word : " + curr);
				root = insertWord(root, curr, curr.length(), 0, k, j, 3);
			}
		}

		for (int i = 0; i < rows - 1; i++)
		{
			String curr = "";

			for (j = rows - 2 - i, k = columns - 1; j >= 0 && k >= 0; j--, k--)
			{
				curr = "" + box[j][k] + curr;
//				System.out.println("%%%% calling insert on word : " + curr);
				root = insertWord(root, curr, curr.length(), 0, j, k, 3);
			}
		}

		int len = wordList.length;
		answer = new String[len];

		for (int i = 0; i < len; i++)
		{
			answer[i] = "";
			if (contains(root, wordList[i], wordList[i].length(), 0))
			{
				if (foundPosition == null)
					System.out.println("******** " + wordList[i] + " found but null!!");
				else
					answer[i] = "" + foundPosition.x + " " + foundPosition.y;
			}
			else
				answer[i] = "";

			System.out.println("i : " + i + ", word : " + wordList[i] + ", answer[i] : " + answer[i]);
		}

		return answer;
	}

	static boolean contains(Node node, String word, int length, int index)
	{
		int pos = word.charAt(index) - 'a';
		Node temp = node.next[pos];

		if (index == length - 1 && temp.isWord)
			return true;

		if (temp == null)
			return false;
		else
		{
			boolean containsWord = contains(temp, word, length, index + 1);

			if (containsWord)
			{
//				System.out.println("word : " + word + ", index : " + index);
				if (index == 0)
					foundPosition = temp.positionsAt.get(word);

				return true;
			}
			else
				return false;
		}
	}

	/**
	 * @param node the node into which the word has to be added
	 * @param word the word to be added to the trie
	 * @param length the length of the word
	 * @param index the index of the current letter to be added
	 * @param row the row where the current letter occurs
	 * @param column the column where the current letter occurs
	 * @param type   <br />1 => row<br /> 2 => column<br /> 3 => diagonal
	 * @return the changed node
	 */
	static Node insertWord(Node node, String word, int length, int index, int row, int column, int type)
	{
//		System.out.println("word : " + word + ", row : " + row + ", col : " + column);
		int pos = word.charAt(index) - 'a';
		Node temp = node.next[pos];

		if (temp == null)
			temp = new Node(new HashMap<>());

		temp.isWord = true;

		Point prevPos = temp.positionsAt.get(word);

		if (prevPos == null)
			temp.positionsAt.put(word, new Point(row, column));
		else
		{
			if (row == prevPos.x)
			{
				if (column < prevPos.y)
					temp.positionsAt.put(word, new Point(row, column));
			}
			else if (row < prevPos.x)
				temp.positionsAt.put(word, new Point(row, column));
		}

		if (index < length - 1)
		{
			if (type == 1) // column
				temp = insertWord(temp, word, length, index + 1, row + 1, column, 1);
			else if (type == 2) // row
				temp = insertWord(temp, word, length, index + 1, row, column + 1, 2);
			else // diagonal
				temp = insertWord(temp, word, length, index + 1, row + 1, column + 1, 3);
		}

		node.next[pos] = temp;

		return node;
	}

	static class Node
	{
		Node[] next;
		boolean isWord;
		HashMap<String, Point> positionsAt;

		public Node(HashMap<String, Point> positionsAt)
		{
			this.positionsAt = positionsAt;
			next = new Node[26];
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
