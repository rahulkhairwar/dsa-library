package com.codeforces.competitions.educational.year2017.round33;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m;
		Node[] nodes;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			nodes = new Node[n];

			for (int i = 0; i < n; i++)
				nodes[i] = new Node(i, in.nextInt());

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				union(nodes[u], nodes[v]);
			}

			long tot = 0;
			Set<Integer> set = new HashSet<>();

			for (int i = 0; i < n; i++)
			{
				Node node = findParent(nodes[i]);

				if (!set.contains(node.key))
				{
					set.add(node.key);
					tot += node.minCost;
				}
			}

			out.println(tot);
		}

		Node findParent(Node node)
		{
			if (node.key == node.parent.key)
				return node;

			return node.parent = findParent(node.parent);
		}

		void union(Node one, Node two)
		{
			Node parentOne, parentTwo;
			parentOne = findParent(one);
			parentTwo = findParent(two);

			if (parentOne.parent.key == parentTwo.parent.key)
				return;

			if (parentOne.height > parentTwo.height)
			{
				parentTwo.parent = parentOne;
				parentOne.minCost = Math.min(parentOne.minCost, parentTwo.minCost);
			}
			else if (parentOne.height < parentTwo.height)
			{
				parentOne.parent = parentTwo;
				parentTwo.minCost = Math.min(parentOne.minCost, parentTwo.minCost);
			}
			else
			{
				parentTwo.parent = parentOne;
				parentOne.minCost = Math.min(parentOne.minCost, parentTwo.minCost);
				parentOne.height++;
			}
		}

		class Node
		{
			long minCost;
			int key, height;
			Node parent;

			public Node(int key, long minCost)
			{
				this.key = key;
				this.minCost = minCost;
				height = 0;
				parent = this;
			}

		}

		public Solver(InputReader in, PrintWriter out)
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

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
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

		public int nextInt()
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

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public void close()
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			in.close();
			out.flush();
			out.close();
		}
	}

}

