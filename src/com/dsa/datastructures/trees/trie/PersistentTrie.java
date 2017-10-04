package com.dsa.datastructures.trees.trie;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class PersistentTrie
{
	private final static int LOGN = 20;
	private final static int BITS = 31;
	private int idx;
	private ArrayList<Integer>[] graph;
	private int[] key;
	private int[] trie;
	private int[][] to;
	private int[] root;
	private int[] level;
	private int[][] pt;

	public static void main(String[] args) throws IOException
	{
		BufferedWriter out =
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileDescriptor.out), "ASCII"), 512);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.valueOf(st.nextToken());
		int queries = Integer.valueOf(st.nextToken());
		int[] key = new int[n + 1];
		ArrayList<Integer>[] adj = new ArrayList[n + 1];
		Map<Integer, Integer> map = new HashMap<>();

		st = new StringTokenizer(br.readLine(), " ");

		int idx = 1;
		int root = Integer.valueOf(st.nextToken());
		int rootKey = Integer.valueOf(st.nextToken());

		map.put(root, idx);
		key[idx] = rootKey;
		idx++;

		for (int i = 1; i < adj.length; i++)
			adj[i] = new ArrayList<>();

		for (int i = 1; i < n; i++)
		{
			st = new StringTokenizer(br.readLine(), " ");

			int u = Integer.valueOf(st.nextToken());
			int v = Integer.valueOf(st.nextToken());
			int k = Integer.valueOf(st.nextToken());

			key[idx] = k;	// node key[idx] has encryption key k.
			map.put(u, idx);	// node key[idx] has label u.

			Integer v1 = map.get(v);

			// creating a link between nodes u and v(idx and v1 denote their index in the key[] array).
			adj[idx].add(v1);
			adj[v1].add(idx);
			idx++;
		}

//		long start = System.nanoTime();
		PersistentTrie pt = new PersistentTrie(adj, key);
		int ans = 0;

		for (int i = 1; i <= queries; ++i)
		{
			st = new StringTokenizer(br.readLine(), " ");

			int t = Integer.valueOf(st.nextToken()) ^ ans;

			if (t == 0)
			{
				int u = Integer.valueOf(st.nextToken()) ^ ans;
				int v = Integer.valueOf(st.nextToken()) ^ ans;
				int k = Integer.valueOf(st.nextToken()) ^ ans;

				map.put(v, idx);
				pt.addNew(idx, map.get(u), k);
				idx++;
			}
			else
			{
				int v = Integer.valueOf(st.nextToken()) ^ ans;
				int k = Integer.valueOf(st.nextToken()) ^ ans;
				int[] res = pt.query(map.get(v), k);

				out.write(res[0] + " " + res[1] + "\n");
				ans = res[0] ^ res[1];
			}
		}

		//System.out.println((System.nanoTime()-start)/1000000);
		br.close();
		out.flush();
		out.close();
	}

	private int queryMin(int num, int lev, int root)
	{
		int cur = root;
		int ret = 0;

		for (int i = BITS - 1; i >= 0; i--)
		{
			int bit = (num >> i) & 1;

			if (to[cur][bit] > 0 && trie[to[cur][bit]] >= lev)
			{
				ret = ret * 2;
				cur = to[cur][bit];
			}
			else
			{
				ret = ret * 2 + 1;
				cur = to[cur][bit ^ 1];
			}
		}

		return ret;
	}

	private int queryMax(int num, int lev, int root)
	{
		int cur = root;
		int ret = 0;

		for (int i = BITS - 1; i >= 0; i--)
		{
			int bit = (num >> i) & 1;

			if (to[cur][bit ^ 1] > 0 && trie[to[cur][bit ^ 1]] >= lev)
			{
				ret = ret * 2 + 1;
				cur = to[cur][bit ^ 1];
			}
			else
			{
				ret = ret * 2;
				cur = to[cur][bit];
			}
		}

		return ret;
	}

	private int insert(int num, int lev, int root)
	{
		int newRoot = ++idx;
		int cur = newRoot;

		for (int i = BITS - 1; i >= 0; i--)
		{
			int bit = (num >> i) & 1;

			// to = next node. This step is to assign the similar sub-trie to the new root.
			to[cur][bit ^ 1] = to[root][bit ^ 1];
			// Assigning a new node to the new root for the non-similar sub-trie.
			to[cur][bit] = ++idx;
			// changing old root to the non-similar sub-trie, for iteration.
			root = to[root][bit];
			// changing new root to the non-similar sub-trie, for iteration.
			cur = to[cur][bit];
			// ?
			trie[cur] = lev;
		}

		return newRoot;
	}

	private void addNew(int u, int v, int k)
	{
		pt[0][u] = v;

		for (int i = 1; i < LOGN; i++)
			pt[i][u] = pt[i - 1][pt[i - 1][u]];

		level[u] = level[v] + 1;
		root[u] = insert(k, level[u], root[v]);
	}

	private void dfs(int u, int p)
	{
		pt[0][u] = p;

		for (int i = 1; i < LOGN; i++)
			pt[i][u] = pt[i - 1][pt[i - 1][u]];

		root[u] = insert(key[u], level[u], root[p]);

		for (int i = 0; i < graph[u].size(); i++)
		{
			if (graph[u].get(i) != p)
			{
				level[graph[u].get(i)] = level[u] + 1;
				dfs(graph[u].get(i), u);
			}
		}
/*
		for (int x : graph[u])
		{
			if (x != p)
			{
				level[x] = level[u] + 1;
				dfs(x, u);
			}
		}*/
	}

	public int[] query(int v, int k)
	{
		int min = queryMin(k, 0, root[v]);
		int max = queryMax(k, 0, root[v]);

		return new int[]{min, max};
	}

	private PersistentTrie(ArrayList<Integer>[] tree, int[] key)
	{
		graph = tree;
		this.key = key;

		int n = 200100;

		trie = new int[n * BITS];
		to = new int[n * BITS][2];
		root = new int[n];
		level = new int[n];
		pt = new int[LOGN][n];
		dfs(1, 0);
	}

}

/*

6 4
1 2
5 1 3
2 1 4
3 2 5
4 2 1
6 3 3
1 4 2
6 0 12 0
7 12 7
4 0 7

*/
