package com.common.implementation;

import java.util.Scanner;

/**
 * Prime Factorization in O()
 *
 * will complete soon.
 */
public class PrimeFactorization
{
	static Scanner in;

	public static void main(String[] args)
	{
		in = new Scanner(System.in);

//		solve();
		test();
	}

	static void solve()
	{
		int count = 0;

		for (int i = 1; i <= 1000; i++)
		{
			if (gcd(i, 1000) == 5)
				count++;
		}

		System.out.println("Count : " + count);
	}

	static int gcd(int a, int b)
	{
		if (b == 0)
			return a;

		return gcd(b, a % b);
	}

	static void test()
	{
		Node first = new Node();

		first = insert(first, 10);
		System.out.println(first.toString());
		first = insert(first, 20);
		System.out.println(first.toString());
		first = insert(first, 30);
		System.out.println(first.toString());

		Node second = null;

		second = insert(second, 10);
		System.out.println("after inserting 10 : " + second.toString());
		second = insert(second, 20);
		System.out.println(second.toString());
		second = insert(second, 30);
		System.out.println(second.toString());
	}

	static Node insert(Node head, int data)
	{
		Node temp = new Node(data);

		if (head == null)
			head = temp;
		else
		{
			temp.next = head;
			head = temp;
		}

		return head;
	}

}

class Node
{
	int data;
	Node next;

	public Node()
	{}

	public Node(int data)
	{
		this.data = data;
//		next = null;
	}

	public String toString()
	{
		String s = "";

		Node temp = this;

		while (temp != null)
		{
			s += (temp.data + " ");
			temp = temp.next;
		}

		return s;
	}

}
