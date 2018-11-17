package com.codeforces.competitions.year2018.round464div2;

import java.io.*;
import java.util.*;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		char[] a, b;
		Node[] nodes;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			a = in.readLine().toCharArray();
			b = in.readLine().toCharArray();
			nodes = new Node[26];

			for (int i = 0; i < 26; i++)
				nodes[i] = new Node(i);

			for (int i = 0; i < n; i++)
			{
				if (a[i] != b[i])
					union(nodes[a[i] - 'a'], nodes[b[i] - 'a']);
			}

			int cnt = 0;
			Set<Integer> set = new HashSet<>();
			StringBuilder ans = new StringBuilder("");

			for (int i = 0; i < 26; i++)
				set.add(findParent(nodes[i]).key);

			for (int x : set)
			{
				cnt += nodes[x].size - 1;

				List<Integer> list = new ArrayList<>();

				for (int i = 0; i < 26; i++)
				{
					if (findParent(nodes[i]).key == x)
						list.add(i);
				}

				for (int i = 0; i < list.size() - 1; i++)
					ans.append((char) (list.get(i) + 'a')).append(" ").append((char) (list.get(i + 1) + 'a'))
							.append("\n");
			}

			out.println(cnt);
			out.println(ans.toString());
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

			parentOne.size = parentTwo.size = parentOne.size + parentTwo.size;

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

		class Node
		{
			int key, height, size;
			Node parent;

			public Node(int key)
			{
				this.key = key;
				height = 0;
				size = 1;
				parent = this;
			}

		}

		public Solver(BufferedReader in, PrintWriter out)
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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			out.flush();
			out.close();
		}
	}

}
