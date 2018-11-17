package com.dsa.datastructures.trees.segmenttree;

//package dsa;

import java.util.Scanner;

/**
 * An implementation of Persistent Segment Tree, which does range-sum updates and queries.
 */
public final class PersistentSegmentTree
{
	int cnt, n, versions;
	int[] arr;
	Node[] roots;
	Node[] nodes;
	Scanner in;

	public static void main(String[] args)
	{
		PersistentSegmentTree tree = new PersistentSegmentTree();

		tree.solve();
	}

	void solve()
	{
		in = new Scanner(System.in);
		n = in.nextInt();
		arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = in.nextInt();

		nodes = new Node[n << 3];
		roots = new Node[n << 2];
		cnt = 1;
		nodes[cnt++] = new Node(0, -1, -1);
		buildFirst(nodes[1], 0, n - 1);
		roots[versions++] = nodes[1];   // root[0th version] = nodes[1].
		System.out.println("Version 0 :");
		printTree(roots[0], 0, n - 1);

		int updates = in.nextInt();
		int queries = in.nextInt();

		// for every update operation, create a new node, add it to the roots with a new version, then call update on
		// that root along with the previous version root. Eg. :
		for (int i = 0; i < updates; i++)
		{
			int ind = in.nextInt() - 1;
			int val = in.nextInt();

			roots[versions] = new Node(0, -1, -1);
			update(roots[versions - 1], roots[versions], 0, n - 1, ind, val);
			System.out.println("Version " + versions + " :");
			printTree(roots[versions], 0, n - 1);
			versions++;
		}

		for (int i = 0; i < queries; i++)
		{
			int version = in.nextInt() - 1;
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;

			System.out.println("In version " + version + ", query(" + l + ", " + r + ") : " + query(roots[version],
					0, n - 1, l, r));
		}
	}

	void printTree(Node node, int treeStart, int treeEnd)
	{
		System.out.println("[" + treeStart + ", " + treeEnd + "] : " + node.sum);

		if (treeStart == treeEnd)
			return;

		int mid = treeStart + treeEnd >> 1;

		printTree(nodes[node.leftChild], treeStart, mid);
		printTree(nodes[node.rightChild], mid + 1, treeEnd);
	}

	void buildFirst(Node node, int treeStart, int treeEnd)
	{
		if (treeStart == treeEnd)
		{
			node.sum = arr[treeStart];

			return;
		}

		int mid = treeStart + treeEnd >> 1;
		int left = cnt++;
		int right = cnt++;

		node.setChildren(left, right);
		buildFirst(nodes[left] = new Node(0, -1, -1), treeStart, mid);
		buildFirst(nodes[right] = new Node(0, -1, -1), mid + 1, treeEnd);
		node.sum = nodes[left].sum + nodes[right].sum;
	}

	void update(Node prev, Node curr, int treeStart, int treeEnd, int ind, int val)
	{
		if (ind < treeStart || treeEnd < ind)
			return;

		if (treeStart == treeEnd)
		{
			curr.sum = val;

			return;
		}

		int mid = treeStart + treeEnd >> 1;

		if (ind <= mid)
		{
			curr.rightChild = prev.rightChild;
			curr.leftChild = cnt;
			nodes[cnt++] = new Node(0, -1, -1);
			update(nodes[prev.leftChild], nodes[curr.leftChild], treeStart, mid, ind, val);
		}
		else
		{
			curr.leftChild = prev.leftChild;
			curr.rightChild = cnt;
			nodes[cnt++] = new Node(0, -1, -1);
			update(nodes[prev.rightChild], nodes[curr.rightChild], mid + 1, treeEnd, ind, val);
		}

		curr.sum = nodes[curr.leftChild].sum + nodes[curr.rightChild].sum;
	}

	long query(Node node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
	{
		if (treeStart > rangeEnd || treeEnd < rangeStart)
			return 0;

		if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			return node.sum;

		int mid = treeStart + treeEnd >> 1;
		long lSum = query(nodes[node.leftChild], treeStart, mid, rangeStart, rangeEnd);
		long rSum = query(nodes[node.rightChild], mid + 1, treeEnd, rangeStart, rangeEnd);

		return lSum + rSum;
	}

	class Node
	{
		int leftChild, rightChild;
		long sum;

		Node(long sum, int leftChild, int rightChild)
		{
			this.sum = sum;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		void setChildren(int leftChild, int rightChild)
		{
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		@Override
		public String toString()
		{
			return "lC : " + leftChild + ", rC : " + rightChild + ", sum : " + sum;
		}

	}

}

/*

5
1 2 3 4 5
2 3
5 1
3 10
2 1 5
3 4 5
1 1 4

*/

