package com.dsa.datastructures.trees.binaryindextree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * An implementation of Binary Index Tree(BIT) which does Range Sum Query and point updation(addition, and NOT replace).
 */
public class BIT
{
	static int size;
	static int[] arr, bit;
	static Scanner in;

	// I'll just leave this here.. :P
	static void test()
	{
		int a = 10;

		System.out.println("a : " + Integer.toBinaryString(a));
		System.out.println("-a : " + Integer.toBinaryString(-a));
		System.out.println("a & -a : " + Integer.toBinaryString(a & -a));
		System.out.println("a + (a & -a) : " + Integer.toBinaryString(a + (a & -a)));
		System.exit(1);
	}

	public static void main(String[] args)
	{
//		test();
		in = new Scanner(System.in);

		System.out.println("Enter the size of the array : ");
		size = in.nextInt();

		arr = new int[size + 1];
		bit = new int[size + 1];

		for (int i = 1; i <= size; i++)
			arr[i] = in.nextInt();

		buildBIT(1, size);
		System.out.println("bit : " + Arrays.toString(bit));
		int choice;

		do
		{
			System.out.println("1. Sum upto index (1-based).");
			System.out.println("2. Update value at an index (1-based).");
			System.out.println("3. Print the bit.");
			System.out.println("4. Exit.");
			System.out.print("========\nEnter your choice : ");
			choice = in.nextInt();

			int index;

			switch (choice)
			{
				case 1:
					System.out.print("\nEnter the index : ");
					index = in.nextInt();
					System.out.println(sum(index));
					break;
				case 2:
					System.out.print("\nEnter the index : ");
					index = in.nextInt();
					System.out.print("\nEnter the value to be added to the entered index : ");
					int addValue = in.nextInt();

					add(index, addValue);
					break;
				case 3:
					System.out.println("\nbit : " + Arrays.toString(bit));
					break;
				case 4:
					break;
			}
		} while (choice != 4);
	}

	static void buildBIT(int from, int to)
	{
		if (from == to)
		{
			if (bit[from] == 0)
				bit[from] = arr[from];

			return;
		}

		int temp = 1;

		while (from + temp - 1 <= to)
		{
			int i = from + temp - 1;
			int prev = from + (temp - 1) / 2;

			if (bit[i] == 0)
				bit[i] = bit[prev] + cum(prev + 1, i);

			temp <<= 1;
		}

		int mid = from + ((to - from) >> 1);

		buildBIT(from, mid);
		buildBIT(mid + 1, to);
	}

	static int sum(int index)
	{
		int answer = 0;

		while (index > 0)
		{
			answer += bit[index];
			index -= index & -index;
		}

		return answer;
	}

	static void add(int index, int addValue)
	{
		while (index <= size)
		{
			bit[index] += addValue;
			index += (index & -index);
		}
	}

	static int cum(int from, int to)
	{
		if (from >= to)
			return arr[to];

		int cum = 0;

		for (int i = from; i <= to; i++)
			cum += arr[i];

		return cum;
	}

}

/*

8
1 2 3 4 5 6 7 8
bit : 1 3 3 10 5 11 7 36

16
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
bit : 1 3 3 10 5 11 7 36 9 19 11 42 13 27 15 136

 */
