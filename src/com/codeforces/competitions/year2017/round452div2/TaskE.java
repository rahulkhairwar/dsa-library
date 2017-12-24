package com.codeforces.competitions.year2017.round452div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n;
		boolean[] used;
		Node[] nodes;
		TreeSet<Integer> treeSet;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			used = new boolean[n];
			nodes = new Node[n];
			treeSet = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					Node parentOne = findParent(nodes[o1]);
					Node parentTwo = findParent(nodes[o2]);

					if (parentOne.size == parentTwo.size)
						return Integer.compare(parentOne.key, parentTwo.key);

					return Integer.compare(parentTwo.size, parentOne.size);
				}
			});

			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node(in.nextInt(), i);
				treeSet.add(nodes[i].key);
			}

			for (int i = 1; i < n; i++)
				if (nodes[i].val == nodes[i - 1].val)
					union(nodes[i - 1], nodes[i]);

			int cnt = 0;

			while (treeSet.size() > 0)
			{
				int ind = treeSet.first();

				deleteSubarray(findParent(nodes[ind]));
				cnt++;
			}

			out.println(cnt);
		}

		void union(Node one, Node two)
		{
			Node oneParent = findParent(one);
			Node twoParent = findParent(two);

			if (oneParent.key == twoParent.key)
				return;

			if (oneParent.height > twoParent.height)
			{
				treeSet.remove(oneParent.key);
				treeSet.remove(twoParent.key);
				twoParent.parent = oneParent;
				oneParent.size += twoParent.size;
				oneParent.end = twoParent.end;
				treeSet.add(oneParent.key);
			}
			else if (oneParent.height < twoParent.height)
			{
				treeSet.remove(oneParent.key);
				treeSet.remove(twoParent.key);
				oneParent.parent = twoParent;
				twoParent.size += oneParent.size;
				twoParent.start = oneParent.start;
				treeSet.add(twoParent.key);
			}
			else
			{
				treeSet.remove(oneParent.key);
				treeSet.remove(twoParent.key);
				twoParent.parent = oneParent;
				oneParent.size += twoParent.size;
				oneParent.height++;
				oneParent.end = twoParent.end;
				treeSet.add(oneParent.key);
			}
		}

		void deleteSubarray(Node node)
		{
			treeSet.remove(node.key);
			used[node.key] = true;

			int prev = node.start - 1;
			int next = node.end + 1;

			if (prev != -1)
				findParent(nodes[prev]).end = node.end;

			if (next != n)
				findParent(nodes[next]).start = node.start;

			if (prev != -1 && next != n && !used[findParent(nodes[prev]).key] && !used[findParent(nodes[next]).key])
				if (nodes[prev].val == nodes[next].val)
					union(nodes[prev], nodes[next]);
		}

		Node findParent(Node node)
		{
			if (node.key == node.parent.key)
				return node;

			return node.parent = findParent(node.parent);
		}

		static class Node
		{
			int val, key, height, size, start, end;
			Node parent;

			Node(int val, int key)
			{
				this.val = val;
				this.key = key;
				parent = this;
				height = 0;
				size = 1;
				start = end = key;
			}

		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
