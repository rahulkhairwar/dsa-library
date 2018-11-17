package com.a2onlinejudge.groupcontests.jul02_2018;

import java.io.*;
import java.util.*;

public final class MahmoudAndADictionary
{
	public static void main(String[] args)
	{
		new MahmoudAndADictionary(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n, m, q;
        Node[] nodes;
		Map<String, Integer> dictionary;
		BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			q = Integer.parseInt(tok[2]);
			tok = in.readLine().split(" ");
			nodes = new Node[n];
			dictionary = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node(i, tok[i]);
				dictionary.put(tok[i], i);
			}

			while (m-- > 0)
			{
				tok = in.readLine().split(" ");

				int type = Integer.parseInt(tok[0]);
				int a = dictionary.get(tok[1]);
				int b = dictionary.get(tok[2]);
				Node aParent = findParent(nodes[a]);
				Node bParent = findParent(nodes[b]);

				a = dictionary.get(aParent.word);
				b = dictionary.get(bParent.word);

//				System.out.println("******");
				if (type == 1)
				{
					if (aParent.opp.contains(b)/* || opp[b].contains(a)*/)
					{
						out.println("NO");

						continue;
					}

					union(aParent, bParent);
					out.println("YES");
				}
				else
				{
					if (aParent.key == bParent.key || areOfSameGroup(aParent.opp, bParent.opp))
					{
						out.println("NO");

						continue;
					}

					aParent.opp.add(b);
					bParent.opp.add(a);
					uniteTheOpposites(aParent, bParent);
					uniteTheOpposites(bParent, aParent);
//					System.out.println("a : " + a + ", b : " + b + ", opp[a] : " + nodes[a].opp + ", opp[b] : " +
//							nodes[b].opp);
					out.println("YES");
				}

/*				System.out.println("nodes :");

				for (int i = 0; i < n; i++)
				{
					System.out.println("i : " + i + ", w[i] : " + nodes[i].word + ", par.key : " + findParent
							(nodes[i]).key);
					System.out.println("\topp[i] : " + nodes[i].opp);
				}*/
			}

			while (q-- > 0)
			{
				tok = in.readLine().split(" ");

//				System.out.println("tok : " + Arrays.toString(tok));
				int a = dictionary.get(tok[0]);
				int b = dictionary.get(tok[1]);

				if (findParent(nodes[a]).key == findParent(nodes[b]).key)
					out.println(1);
				else
				{
					Node aa = findParent(nodes[a]);
					int x = findParent(nodes[b]).key;

					if (aa.opp.contains(x))
						out.println(2);
					else
						out.println(3);
				}
			}
		}

		boolean areOfSameGroup(Set<Integer> a, Set<Integer> b)
		{
			if (a.size() > b.size())
			{
				Set<Integer> temp = a;

				a = b;
				b = temp;
			}

			for (int x : a)
			{
				if (b.contains(x))
					return true;
			}

			return false;
		}

		void uniteTheOpposites(Node a, Node b)
		{
			for (int x : b.opp)
				union(a, nodes[x]);
		}

		Node findParent(Node node)
		{
			if (node.key == node.parent.key)
				return node;

			return node.parent = findParent(node.parent);
		}

		void union(Node one, Node two)
		{
			Node oneParent = findParent(one);
			Node twoParent = findParent(two);

			if (oneParent.key == twoParent.key)
				return;

			oneParent.size = twoParent.size = oneParent.size + twoParent.size;

			if (oneParent.height > twoParent.height)
			{
				twoParent.parent = one;
				oneParent.opp.addAll(twoParent.opp);
			}
			else if (oneParent.height < twoParent.height)
			{
				oneParent.parent = two;
				twoParent.opp.addAll(oneParent.opp);
			}
			else
			{
				twoParent.parent = one;
				oneParent.opp.addAll(twoParent.opp);
				oneParent.height++;
			}
		}

		class Node
		{
			int key, height, size;
			Set<Integer> opp;
			String word;
			Node parent;

			Node(int key, String word)
			{
				this.key = key;
				this.word = word;
				opp = new HashSet<>();
				size = 1;
				parent = this;
			}

		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	public MahmoudAndADictionary(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "MahmoudAndADictionary", 1 << 29);

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

/*

5 4 5
hello hi welcome ihateyou goaway
1 hello hi
1 hi welcome
2 ihateyou hi
2 goaway hi
welcome hello
ihateyou welcome
welcome goaway
goaway ihateyou
welcome hi

*/