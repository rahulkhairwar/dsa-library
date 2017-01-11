package com.icpc;

import java.awt.geom.*;
import java.io.*;
import java.util.*;
class Cookbook {
	static InputReader in;
	static class Dijkstra {
		static final int INFINITY = Integer.MAX_VALUE;
		static int t, nodes, edges, dist[];
		static List<Edge>[] adj;
		static void findShortestPaths(int start) {
			dist = new int[nodes];
			for (int i = 0; i < nodes; i++)
				dist[i] = INFINITY;
			TreeSet<Node> set = new TreeSet<>();
			dist[start] = 0;
			set.add(new Node(start, 0, -1));
			while (set.size() > 0) {
				Node curr = set.pollFirst();
				for (Edge edge : adj[curr.node]) {
					if (dist[curr.node] + edge.weight < dist[edge.to]) {
						set.remove(new Node(edge.to, dist[edge.to], -1));
						dist[edge.to] = dist[curr.node] + edge.weight;
						set.add(new Node(edge.to, dist[edge.to], curr.node));
					}
				}
			}
		}
		static class Edge {
			int to, weight;
			public Edge(int to, int weight) {
				this.to = to;
				this.weight = weight;
			}
		}
		static class Node implements Comparable<Node> {
			int node, dist, parent;
			public Node(int node, int dist, int parent) {
				this.node = node;
				this.dist = dist;
				this.parent = parent;
			}
			@Override public int compareTo(Node o) {
				if (dist == o.dist)
					return Integer.compare(node, o.node);
				return Integer.compare(dist, o.dist);
			}
		}
	}
	static class SCC {
		int v, e, currTime;
		Node[] nodes, reverse;
		List<List<Integer>> sCC;
		void solve() {
			v = in.nextInt();
			e = in.nextInt();
			currTime = 1;
			nodes = new Node[v];
			reverse = new Node[v];
			sCC = new ArrayList<>();

			for (int i = 0; i < v; i++) {
				nodes[i] = new Node(i);
				reverse[i] = new Node(i);
			}

			for (int i = 0; i < e; i++) {
				int from, to;
				from = in.nextInt();
				to = in.nextInt();
				nodes[from].adj.add(to);
				reverse[to].adj.add(from);
			}

			for (int i = 0; i < v; i++)
				if (!nodes[i].visited)
					dfs(i);

			Arrays.sort(nodes, new Comparator<Node>() {
				@Override public int compare(Node one, Node two) {
					return Integer.compare(two.leavingTime, one.leavingTime);
				}
			});
			for (int i = 0; i < v; i++) {
				int curr = nodes[i].index;

				if (!reverse[curr].visited) {
					List<Integer> list = new ArrayList<>();
					findSCC(curr, list);
					sCC.add(list);
				}
			}
			for (List<Integer> aSCC : sCC)
				System.out.println(aSCC);
		}

		void dfs(int node) {
			Node temp = nodes[node];
			temp.visited = true;
			temp.visitingTime = currTime++;
			for (int curr : temp.adj) {
				if (!nodes[curr].visited)
					dfs(curr);
			}
			temp.leavingTime = currTime++;
		}

		void findSCC(int node, List<Integer> list) {
			Node temp = reverse[node];
			temp.visited = true;
			list.add(node);
			for (int curr : temp.adj) {
				if (!reverse[curr].visited)
					findSCC(curr, list);
			}
		}

		class Node {
			int index, visitingTime, leavingTime;
			boolean visited;
			List<Integer> adj;

			public Node(int index) {
				this.index = index;
				this.adj = new ArrayList<>();
			}
		}
	}

	static class BellmanFord {
		static final int INFINITY = Integer.MAX_VALUE;
		static int nodes, edges;
		static int[] dist, par;
		static Graph graph;
		static List<Edge>[] list;

		void solve() {
			nodes = in.nextInt();
			edges = in.nextInt();
			createGraph();
			int start = in.nextInt() - 1;
			calculateShortestPathsUsingGraph(start);
			for (int i = 0; i < nodes; i++) {
				if (i != start) {
					if (dist[i] == INFINITY)
						System.out.print(-1 + " ");
					else
						System.out.print(dist[i] + " ");
				}
			}
		}

		static void createGraph() {
			graph = new Graph(nodes, edges);
			for (int i = 0; i < 2 * edges; i += 2) {
				int from, to, weight;
				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
				weight = in.nextInt();
				graph.edges[i].from = from;
				graph.edges[i].to = to;
				graph.edges[i].weight = weight;
				graph.edges[i + 1].from = to;
				graph.edges[i + 1].to = from;
				graph.edges[i + 1].weight = weight;
			}
		}
		static void calculateShortestPathsUsingGraph(int start) {
			dist = new int[nodes];
			Arrays.fill(dist, INFINITY);
			dist[start] = 0;
			for (int i = 1; i < nodes; i++) {
				for (int j = 0; j < 2 * edges; j++) {
					if (dist[graph.edges[j].from] < INFINITY
							&& dist[graph.edges[j].from] + graph.edges[j].weight < dist[graph.edges[j].to]) {
						dist[graph.edges[j].to] = dist[graph.edges[j].from] + graph.edges[j].weight;
						graph.parent[graph.edges[j].to] = graph.edges[j].from;
					}
				}
			}
		}
		static class Graph {
			int v, e;
			int[] parent;
			Edge[] edges;
			public Graph(int v, int e) {
				this.v = v;
				this.e = e;
				parent = new int[v];
				edges = new Edge[2 * e];
				for (int i = 0; i < v; i++)
					parent[i] = -1;
				for (int i = 0; i < 2 * e; i++)
					edges[i] = new Edge();
			}
			class Edge {
				int from, to, weight;
				public Edge() {
					from = to = weight = -1;
				}
			}
		}
		static class Edge {
			int toNode, weight;
			public Edge(int toNode, int weight) {
				this.toNode = toNode;
				this.weight = weight;
			}
		}
	}
	// Articulation Points and Bridges.
	static class ArticulationPoints {
		static int t, n, m, k, articulationPoints, currTime;
		static int[] parent, visitingTime, low;
		static List<Integer>[] adj;
		static boolean[] visited;
		void solve() {
			articulationPoints = 0;
			currTime = 1;// then, initialize parent, visitingTime, low, visited
			parent[0] = -1;// then, create graph, call dfs(0)
		}
		void dfs(int node) {
			visited[node] = true;
			visitingTime[node] = low[node] = currTime++;
			boolean isArticulationPoint = false;

			for (int curr : adj[node]) {
				if (!visited[curr]) {
					parent[curr] = node;
					dfs(curr);
					low[node] = Math.min(low[node], low[curr]);

					if (visitingTime[node] <= low[curr])    // condition becomes < for bridges.
						isArticulationPoint = true;
				} else if (curr != parent[node])
					low[node] = Math.min(low[node], visitingTime[curr]);
			}

			if (isArticulationPoint)
				articulationPoints++;
		}
	}

	static class DisjointSet {
		Node findParent(Node node) {
			if (node.key == node.parent.key)
				return node;
			return node.parent = findParent(node.parent);
		}
		void union(Node one, Node two) {
			Node parentOne, parentTwo;
			parentOne = findParent(one);
			parentTwo = findParent(two);
			// already connected.
			if (parentOne.parent.key == parentTwo.parent.key)
				return;
			if (parentOne.height > parentTwo.height)
				parentTwo.parent = parentOne;
			else if (parentOne.height < parentTwo.height)
				parentOne.parent = parentTwo;
			else {
				parentTwo.parent = parentOne;
				parentOne.height++;
			}
		}
		class Node {
			int key, height;
			Node parent;

			public Node(int key) {
				this.key = key;
				height = 0;
				parent = this;
			}

		}
	}
	static class LazySegmentTree {
		int data[];
		Node segmentTree[];

		void buildTree(int node, int treeStart, int treeEnd) {
			if (treeStart > treeEnd)
				return;
			if (treeStart == treeEnd) {
				segmentTree[node] = new Node(data[treeStart]);
				return;
			}

			int mid = (treeStart + treeEnd) / 2;
			buildTree(2 * node, treeStart, mid);
			buildTree(2 * node + 1, mid + 1, treeEnd);
			segmentTree[node] = new Node(Math.min(segmentTree[2 * node].minimum, segmentTree[2 * node + 1].minimum));
		}

		int queryTree(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd) {
			Node tempNode = segmentTree[node];

			if (tempNode.update > 0) {
				tempNode.minimum += tempNode.update;

				if (treeStart != treeEnd) {
					int update = tempNode.update;

					segmentTree[2 * node].update = update;
					segmentTree[2 * node + 1].update = update;
				}

				tempNode.update = 0;
			}

			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return Integer.MAX_VALUE;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tempNode.minimum;

			int mid, leftChildMin, rightChildMin;
			mid = (treeStart + treeEnd) / 2;
			leftChildMin = queryTree(2 * node, treeStart, mid, rangeStart, rangeEnd);
			rightChildMin = queryTree(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return Math.min(leftChildMin, rightChildMin);
		}

		void updateTree(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int addValue) {
			Node tempNode = segmentTree[node];

			if (tempNode.update > 0)    // lazy
			{
				tempNode.minimum += tempNode.update;
				if (treeStart != treeEnd) {
					segmentTree[2 * node].update += tempNode.update;
					segmentTree[2 * node + 1].update += tempNode.update;
				}
				tempNode.update = 0;
			}
			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return;
			if (treeStart >= rangeStart && treeEnd <= rangeEnd) {
				tempNode.minimum += addValue;
				if (treeStart != treeEnd) {
					segmentTree[2 * node].update += addValue;
					segmentTree[2 * node + 1].update += addValue;
				}
				return;
			}

			int mid = (treeStart + treeEnd) / 2;
			updateTree(2 * node, treeStart, mid, rangeStart, rangeEnd, addValue);
			updateTree(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd, addValue);
			tempNode.minimum = Math.min(segmentTree[2 * node].minimum, segmentTree[2 * node + 1].minimum);
		}

		static class Node {
			int minimum;
			int update; // if lazy, this will be != 0, else, 0
			public Node(int minimum) {
				this.minimum = minimum;
				this.update = 0;
			}
		}
	}
	static class Trie {
		public Node insert(Node node, String word, int length, int index) {
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];
			if (temp == null)
				temp = new Node();
			if (index == length - 1)
				temp.isWord = true;
			else
				temp = insert(temp, word, length, index + 1);
			node.next[pos] = temp;
			return node;
		}
		public boolean contains(Node node, String word, int length, int index) {
			int pos = word.charAt(index) - 'a';
			Node temp = node.next[pos];
			if (temp == null)
				return false;
			if (index == length - 1)
				return temp.isWord;
			return contains(temp, word, length, index + 1);
		}
		static class Node {
			Node[] next;
			boolean isWord;
			public Node() {
				next = new Node[26];
			}
		}
	}
	static class NumberTrie {
		Node root;

		Node insert(Node node, String word, int index) {
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];
			if (temp == null)
				temp = new Node();
			if (index == 31)
				temp.isWord++;
			else
				temp = insert(temp, word, index + 1);
			temp.exists++;
			node.next[pos] = temp;
			return node;
		}

		boolean contains(Node node, String word, int index) {
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];
			if (temp == null)
				return false;
			if (index == 31)
				return temp.isWord > 0;
			return contains(temp, word, index + 1);
		}

		boolean delete(Node node, String word, int index) {
			int pos = word.charAt(index) - '0';
			Node temp = node.next[pos];
			if (temp == null)
				return false;
			if (index == 31) {
				if (temp.isWord > 0) {
					temp.isWord--;
					return true;
				}
				return false;
			}
			// to check whether the number has been deleted from the trie if it existed, or not, if it didn't already
			// exist.
			if (delete(temp, word, index + 1)) {
				temp.exists--;
				return true;
			}
			return false;
		}

		long maxXor(Node node, String word, int index) {
			int pos = word.charAt(index) - '0';
			int reqPos = pos == 1 ? 0 : 1;
			Node temp = node.next[pos];
			Node reqTemp = node.next[reqPos];
			if (index == 31) {
				if (reqTemp != null && reqTemp.isWord > 0)
					return 1;
				return 0;
			}
			if (reqTemp != null) {
				if (reqTemp.exists > 0)
					return (1 << (31 - index)) + maxXor(reqTemp, word, index + 1);
				if (temp != null)
					return Math.max(maxXor(reqTemp, word, index + 1), maxXor(temp, word, index + 1));
				return maxXor(reqTemp, word, index + 1);
			}
			if (temp != null)
				return maxXor(temp, word, index + 1);
			return 0;
		}

		class Node {
			int exists, isWord;
			Node[] next;
			public Node() {
				next = new Node[2];
			}
		}
	}
	static class EdmundKarpDirected {
		static final int INFINITY = (int) 2e9;
		int v, e, s, t, flow, maxFlow;
		Edge[] edges;
		List<Integer>[] adj;
		int[] par, parEdge;

		void solve() {
			v = in.nextInt();    // no. of vertices.
			e = in.nextInt();    // no. of edges.
			s = in.nextInt();    // source.
			t = in.nextInt();    // sink.
			maxFlow = 0;
			edges = new Edge[e << 1];
			adj = new ArrayList[v];

			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();
			int counter = 0;
			for (int i = 0; i < e; i++) {
				int from, to, weight;
				from = in.nextInt();
				to = in.nextInt();
				weight = in.nextInt();
				// forward edge from -> to with weight = weight
				edges[counter] = new Edge(to, weight);
				adj[from].add(counter++);
				// backward edge from <- to with weight = 0
				edges[counter] = new Edge(from, 0);
				adj[to].add(counter++);
			}
			while (true) {
				boolean[] visited = new boolean[v];
				Queue<Integer> queue = new LinkedList<>();
				flow = 0;
				par = new int[v];
				parEdge = new int[v];
				Arrays.fill(par, -1);
				Arrays.fill(parEdge, -1);
				visited[s] = true;
				queue.add(s);
				while (queue.size() > 0) {
					int curr = queue.poll();
					if (curr == t)
						break;    // breaking immediately if we reach the sink.
					for (int ed : adj[curr]) {
						int to = edges[ed].to;
						int weight = edges[ed].weight;
						if (weight > 0 && !visited[to]) {
							visited[to] = true;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}
				augment(t, INFINITY);
				if (flow == 0)
					break;    // no more augmenting paths can increase the flow.
				maxFlow += flow;
			}
			System.out.println("The Max Flow possible in the given graph is : " + maxFlow);
		}

		void augment(int node, int minEdge) {
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1) {
				int ed = parEdge[node];
				int weight = edges[ed].weight;
				augment(par[node], Math.min(minEdge, weight));
				edges[ed].weight -= flow;    // decreasing flow from the forward edge
				edges[ed ^ 1].weight += flow;    // increasing flow in the backward edge
			}
		}

		class Edge {
			int to, weight;
			public Edge(int to, int weight) {
				this.to = to;
				this.weight = weight;
			}
		}
	}
	static class EdmundKarpUndirected {
		static final int INFINITY = (int) 2e9;
		int v, e, s, t, flow, maxFlow;
		Edge[] edges;
		int[] par, parEdge;
		List<Integer>[] adj;

		void solve() {
			v = in.nextInt();
			s = in.nextInt() - 1;
			t = in.nextInt() - 1;
			e = in.nextInt();
			maxFlow = 0;
			edges = new Edge[e << 2];
			adj = new ArrayList[v];
			for (int i = 0; i < v; i++)
				adj[i] = new ArrayList<>();
			int counter = 0;
			for (int i = 0; i < e; i++) {
				int from, to, weight;
				from = in.nextInt() - 1;
				to = in.nextInt() - 1;
				weight = in.nextInt();
				edges[counter] = new Edge(to, weight, counter + 2);
				adj[from].add(counter++);
				edges[counter] = new Edge(from, 0, -1);
				adj[to].add(counter++);
				edges[counter] = new Edge(from, weight, counter - 2);
				adj[to].add(counter++);
				edges[counter] = new Edge(to, 0, -1);
				adj[from].add(counter++);
			}
			while (true) {
				int[] dist = new int[v];
				Queue<Integer> queue = new LinkedList<>();
				flow = 0;
				par = new int[v];
				parEdge = new int[v];
				Arrays.fill(dist, INFINITY);
				Arrays.fill(par, -1);
				Arrays.fill(parEdge, -1);
				dist[s] = 0;
				queue.add(s);
				while (queue.size() > 0) {
					int curr = queue.poll();
					if (curr == t)
						break;    // breaking immediately if reached the sink
					for (int ed : adj[curr]) {
						int to = edges[ed].to;
						int weight = edges[ed].weight;
						if (weight > 0 && dist[to] == INFINITY) {
							dist[to] = dist[curr] + 1;
							queue.add(to);
							par[to] = curr;
							parEdge[to] = ed;
						}
					}
				}
				augment(t, INFINITY);
				if (flow == 0)
					break;
				maxFlow += flow;
			}
		}

		void augment(int node, int minEdge) {
			if (node == s)
				flow = minEdge;
			else if (par[node] != -1) {
				int ed = parEdge[node];
				int weight = edges[ed].weight;
				int oppEdge = edges[ed].oppEdge;
				augment(par[node], Math.min(minEdge, weight));
				edges[ed].weight -= flow;
				edges[ed ^ 1].weight += flow;
				if (oppEdge >= 0)
					edges[oppEdge].weight -= flow;
			}
		}

		class Edge {
			int to, weight, oppEdge;

			public Edge(int to, int weight, int oppEdge) {
				this.to = to;
				this.weight = weight;
				this.oppEdge = oppEdge;
			}
		}
	}
	static class Kruskal {
		int n, e;
		Node[] nodes;
		Edge[] edges;
		InputReader in;

		void solve() {
			n = in.nextInt();
			e = in.nextInt();
			for (int i = 0; i < n; i++)
				nodes[i] = new Node(i);
			for (int i = 0; i < e; i++)
				edges[i] = new Edge(i, in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
			int q = in.nextInt();
			int[] cables = new int[q];
			for (int i = 0; i < q; i++)
				cables[i] = in.nextInt();
			Arrays.sort(cables);
			Arrays.sort(edges, new Comparator<Edge>() {
				@Override public int compare(Edge o1, Edge o2) {
					return Integer.compare(o1.weight, o2.weight);
				}
			});
			List<Integer> used = new ArrayList<>();
			for (int i = 0; i < e; i++) {
				Node a, b;
				a = findParent(nodes[edges[i].from]);
				b = findParent(nodes[edges[i].to]);
				if (!a.equals(b)) {
					union(a, b);
					used.add(edges[i].weight);
				}
			}
			long sum = 0;
			int counter = 0;
			Collections.reverse(used);
			for (int i = 0; i < n - 1; i++) {
				if (counter < q && cables[counter] < used.get(i))
					sum += cables[counter++];
				else
					sum += used.get(i);
			}
			System.out.println(sum);
		}

		Node findParent(Node node) {
			if (node.key == node.parent.key)
				return node;
			else
				return node.parent = findParent(node.parent);
		}

		void union(Node one, Node two) {
			Node parentOne, parentTwo;
			parentOne = findParent(one);
			parentTwo = findParent(two);
			if (parentOne.key == parentTwo.key)
				return;
			if (parentOne.height > parentTwo.height)
				parentTwo.parent = parentOne;
			else if (parentOne.height < parentTwo.height)
				parentOne.parent = parentTwo;
			else {
				parentTwo.parent = parentOne;
				parentOne.height++;
			}
		}

		class Node {
			int key, height;
			Node parent;
			public Node(int key) {
				this.key = key;
				this.height = 0;
				parent = this;
			}
		}

		class Edge {
			int ind, from, to, weight;
			public Edge(int ind, int from, int to, int weight) {
				this.ind = ind;
				this.from = from;
				this.to = to;
				this.weight = weight;
			}
		}
	}
	/**Algorithms.*/
	static class MatrixExponentiation {
		static final int mod = (int) 1e9 + 7;
		int n;

		long[][] multiply(long[][] a, long[][] b) {
			long[][] ans = new long[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					long sum = 0;
					for (int k = 0; k < n; k++)
						sum = CMath.mod(sum + CMath.mod(a[i][k] * b[k][j], mod), mod);
					ans[i][j] = sum;
				}
			}
			return ans;
		}
	}
	static class CMath {
		static long modPower(long number, long power, long mod) {
			if (number == 1 || power == 0)
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

		static long moduloInverse(long number, long mod) {
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod) {
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b) {
			if (b == 0)
				return a;

			return gcd(b, a % b);
		}
	}
	static class KMP {
		void solve() {
			char[] pattern = in.nextLine().toCharArray();
			char[] text = in.nextLine().toCharArray();
			int n, m;
			n = text.length;
			m = pattern.length;
			int[] pre = new int[m];
			int i = 1, j = 0;
			while (i < m) {
				if (pattern[i] == pattern[j]) {
					pre[i] = j + 1;
					i++;
					j++;
				} else {
					if (j > 0)
						j = pre[j - 1];
					else
						i++;
				}
			}
			i = j = 0;
			List<Integer> pos = new ArrayList<>();
			while (i < n) {
				if (text[i] == pattern[j]) {
					if (j == m - 1) {
						pos.add(i - m + 1);
						j = pre[j] - 1;
					}
					i++;
					j++;
				} else {
					if (j > 0)
						j = pre[j - 1];
					else
						i++;
				}
			}
			System.out.println("The pattern is present in the text at positions : " + pos);
		}
	}
	static class LCS {
		String a, b;
		void solve() {
			System.out.print("Enter the 1st string : ");
			a = in.next();
			System.out.print("\nEnter the 2nd string : ");
			b = in.next();
			int aLength, bLength;
			aLength = a.length();
			bLength = b.length();
			int[][] dp = new int[aLength + 1][bLength + 1];
			for (int i = 1; i <= aLength; i++) {
				for (int j = 1; j <= bLength; j++) {
					if (a.charAt(i - 1) == b.charAt(j - 1))
						dp[i][j] = 1 + dp[i - 1][j - 1];
					else
						dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
			StringBuilder lcs = new StringBuilder();
			for (int i = aLength; i > 0; ) {
				for (int j = bLength; j > 0; ) {
					if (i == 0)
						break;

					if (dp[i][j] != dp[i - 1][j] && dp[i][j] != dp[i][j - 1]) {
						i--;
						j--;
						lcs.append(a.charAt(i));
					} else if (dp[i][j] == dp[i - 1][j])
						i--;
					else
						j--;
				}
			}

			lcs = lcs.reverse();
			System.out.println("The LCS is : " + lcs.toString());
		}
	}
	static class SparseTable {
		static int array[], logTable[], rmq[][];

		public static void main(String[] args) {
			//int array[] = {1, 5, -2, 3};
			array = new int[]{1, 5, -2, 3, 1, 0, 8, 9, 4, 3};
			solve();
			int from, to, minPos;
			boolean doAsk = true;
			System.out.println("Range starting index(0-based) : ");
			from = in.nextInt();
			System.out.println("Range ending index(0-based) : ");
			to = in.nextInt();
			minPos = minPos(from, to);
		}

		static void solve() {
			int n = array.length;
			logTable = new int[n + 1];
			for (int i = 2; i <= n; i++)
				logTable[i] = logTable[i >> 1] + 1;
			// this 2-D array stores the array index of the smallest element in a range
			rmq = new int[logTable[n] + 1][n];
			// all elements will be the smallest in their own cell(range = 1 cell)
			for (int i = 0; i < n; i++)
				rmq[0][i] = i;
			for (int i = 1; (1 << i) < n; ++i) {
				for (int j = 0; (1 << i) + j <= n; j++) {
					int x, y;

					x = rmq[i - 1][j];
					// y = rmq[prev. row][2^(i - 1) + j]
					y = rmq[i - 1][(1 << (i - 1)) + j];
					rmq[i][j] = array[x] <= array[y] ? x : y;
				}
			}
		}

		static int minPos(int left, int right) {
			int k, x, y;
			// to find the size of the 2 intervals which completely cover the required interval
			k = logTable[right - left];
			x = rmq[k][left];
			y = rmq[k][right - (1 << k) + 1];    // y = rmq[k][right - 2^k + 1]
			return array[x] <= array[y] ? x : y;
		}
	}
	static class SparseTable2D {
		static int[] log;
		static int[][] arr, sum;
		static int[][][][] rmq;

		public static void main(String[] args) {
			arr = new int[][]{{1, 0, 3, 2, 6}, {10, 100, 12, 3, 0}, {12, 16, 15, 2, 4}, {8, 10, 50, 20, 1},
					{1, 2, 3, 4, 5}};
			buildSparseMatrix(arr.length, arr[0].length);
			while (true) {
				System.out.println("Enter the top-left & bottom-right points of the submatrix : ");
				int x1, y1, x2, y2;
				x1 = in.nextInt();
				y1 = in.nextInt();
				x2 = in.nextInt();
				y2 = in.nextInt();
				System.out.println(query(x1, y1, x2, y2));
				System.out.println("Do you want to ask any more queries ? (yes/no) : ");

				if (!in.next().equals("yes"))
					break;
			}
		}
		static void buildSparseMatrix(int n, int m) {
			log = new int[n + 1];
			for (int i = 2; i <= n; i++)
				log[i] = log[i >> 1] + 1;
			rmq = new int[n + 1][m + 1][log[n] + 1][log[m] + 1];
			for (int i = 0; (1 << i) <= n; i++) {
				for (int j = 0; (1 << j) <= m; j++) {
					for (int x = 0; x + (1 << i) - 1 < n; x++) {
						for (int y = 0; y + (1 << j) - 1 < m; y++) {
							if (i == 0 && j == 0)
								rmq[x][y][i][j] = arr[x][y];
							else if (i == 0)
								rmq[x][y][i][j] = max(rmq[x][y][i][j - 1], rmq[x][y + (1 << (j - 1))][i][j - 1]);
							else if (j == 0)
								rmq[x][y][i][j] = max(rmq[x][y][i - 1][j], rmq[x + (1 << (i - 1))][y][i - 1][j]);
							else
								rmq[x][y][i][j] = max(rmq[x][y][i - 1][j - 1], rmq[x + (1 << (i - 1))][y][i - 1][j -
												1],
										rmq[x][y + (1 << (j - 1))][i - 1][j - 1],
										rmq[x + (1 << (i - 1))][y + (1 << (j - 1))][i - 1][j - 1]);
						}
					}
				}
			}
		}

		static int query(int x, int y, int x1, int y1) {
			int k, l, ans;

			k = log[x1 - x + 1];
			l = log[y1 - y + 1];
			ans = max(rmq[x][y][k][l], rmq[x1 - (1 << k) + 1][y][k][l], rmq[x][y1 - (1 << l) + 1][k][l],
					rmq[x1 - (1 << k) + 1][y1 - (1 << l) + 1][k][l]);

			return ans;
		}

		static int max(int... integers) {
			int maxValue = -1;

			for (int i : integers)
				maxValue = Math.max(maxValue, i);

			return maxValue;
		}
	}
	static class Kandane {
		static void getAttributes() {
			int size, array[], answer[];
			size = in.nextInt();
			array = new int[size];
			for (int i = 0; i < size; i++)
				array[i] = in.nextInt();
			answer = findMaxSum(array, size);
			System.out.println(
					"The maximum sum of contiguous elements in the array is : " + answer[0] + " starting " + "at "
							+ "index " + answer[1] + " and ending at index " + answer[2]);
		}

		/**
		 * Finds the maximum sum of contiguous elements in the array using Kandane's
		 * Algorithm.
		 *
		 * @param array the array whose maximum sum of contiguous elements need to be
		 *              found.
		 * @param size  the size of the array.
		 * @return an array of size 3, having : <br/>
		 * maximum sum of contiguous elements at index 0,<br/>
		 * the left index of the contiguous sub-array at index 1, and<br/>
		 * the right index of the contiguous sub-array at index 2.
		 */
		static int[] findMaxSum(int array[], int size) {
			int value, currentSum, currentIndex, maxSum, maxSumLeftIndex, maxSumRightIndex;
			currentSum = maxSum = 0;
			currentIndex = maxSumLeftIndex = maxSumRightIndex = -1;
			for (int i = 0; i < size; i++) {
				value = currentSum + array[i];

				if (value > 0) {
					if (currentSum == 0)
						currentIndex = i;
					currentSum = value;
				} else
					currentSum = 0;
				if (currentSum > maxSum) {
					maxSum = currentSum;
					maxSumLeftIndex = currentIndex;
					maxSumRightIndex = i;
				}
			}
			return new int[]{maxSum, maxSumLeftIndex, maxSumRightIndex};
		}
	}
	static class PrimeFactorization {
		int[] sp;
		List<Integer> primes;
		void pre() {
			int MAX = (int) 1e7 + 5;
			boolean[] v = new boolean[MAX];
			sp = new int[MAX];
			primes = new ArrayList<>();
			primes.add(2);
			for (int i = 2; i < MAX; i += 2)
				sp[i] = 2;
			for (int i = 3; i < MAX; i += 2) {
				if (!v[i]) {
					primes.add(i);
					sp[i] = i;
					for (int j = i; (long) j * i < MAX; j += 2) {
						if (!v[j * i]) {
							v[j * i] = true;
							sp[j * i] = i;
						}
					}
				}
			}
		}

		void factorize(int num, List<Integer> primeFactors) {
			primeFactors = new ArrayList<>();

			int temp = num;

			while (temp > 1) {
				int x = sp[temp];
				primeFactors.add(sp[temp]);
				while (temp % x == 0)
					temp /= x;
			}
		}
	}

	// In this example, we read an input file containing three lines, each
// containing an even number of doubles, separated by commas.  The first two
// lines represent the coordinates of two polygons, given in counterclockwise
// (or clockwise) order, which we will call "A" and "B".  The last line
// contains a list of points, p[1], p[2], ...
//
// Our goal is to determine:
//   (1) whether B - A is a single closed shape (as opposed to multiple shapes)
//   (2) the area of B - A
//   (3) whether each p[i] is in the interior of B - A
//
// INPUT:
//   0 0 10 0 0 10
//   0 0 10 10 10 0
//   8 6
//   5 1
//
// OUTPUT:
//   The area is singular.
//   The area is 25.0
//   Point belongs to the area.
//   Point does not belong to the area.
	public class JavaGeometry {
		// make an array of doubles from a string
		double[] readPoints(String s) {
			String[] arr = s.trim().split("\\s++");
			double[] ret = new double[arr.length];
			for (int i = 0; i < arr.length; i++)
				ret[i] = Double.parseDouble(arr[i]);
			return ret;
		}

		// make an Area object from the coordinates of a polygon
		Area makeArea(double[] pts) {
			Path2D.Double p = new Path2D.Double();
			p.moveTo(pts[0], pts[1]);
			for (int i = 2; i < pts.length; i += 2)
				p.lineTo(pts[i], pts[i + 1]);
			p.closePath();
			return new Area(p);
		}

		// compute area of polygon
		double computePolygonArea(ArrayList<Point2D.Double> points) {
			Point2D.Double[] pts = points.toArray(new Point2D.Double[points.size()]);
			double area = 0;
			for (int i = 0; i < pts.length; i++) {
				int j = (i + 1) % pts.length;
				area += pts[i].x * pts[j].y - pts[j].x * pts[i].y;
			}
			return Math.abs(area) / 2;
		}

		// compute the area of an Area object containing several disjoint polygons
		double computeArea(Area area) {
			double totArea = 0;
			PathIterator iter = area.getPathIterator(null);
			ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
			while (!iter.isDone()) {
				double[] buffer = new double[6];
				switch (iter.currentSegment(buffer)) {
					case PathIterator.SEG_MOVETO:
					case PathIterator.SEG_LINETO:
						points.add(new Point2D.Double(buffer[0], buffer[1]));
						break;
					case PathIterator.SEG_CLOSE:
						totArea += computePolygonArea(points);
						points.clear();
						break;
				}
				iter.next();
			}
			return totArea;
		}

		// notice that the main() throws an Exception -- necessary to
		// avoid wrapping the Scanner object for file reading in a
		// try { ... } catch block.
		public void main(String args[]) throws Exception {
			Scanner scanner = new Scanner(new File("input.txt"));
			double[] pointsA = readPoints(scanner.nextLine());
			double[] pointsB = readPoints(scanner.nextLine());
			Area areaA = makeArea(pointsA);
			Area areaB = makeArea(pointsB);
			areaB.subtract(areaA);
			// also,
			//   areaB.exclusiveOr (areaA);
			//   areaB.add (areaA);
			//   areaB.intersect (areaA);

			// (1) determine whether B - A is a single closed shape (as
			//     opposed to multiple shapes)
			boolean isSingle = areaB.isSingular();
			// also,
			//   areaB.isEmpty();

			if (isSingle)
				System.out.println("The area is singular.");
			else
				System.out.println("The area is not singular.");

			// (2) compute the area of B - A
			System.out.println("The area is " + computeArea(areaB) + ".");

			// (3) determine whether each p[i] is in the interior of B - A
			while (scanner.hasNextDouble()) {
				double x = scanner.nextDouble();
				assert (scanner.hasNextDouble());
				double y = scanner.nextDouble();

				if (areaB.contains(x, y)) {
					System.out.println("Point belongs to the area.");
				} else {
					System.out.println("Point does not belong to the area.");
				}
			}

			// Finally, some useful things we didn't use in this example:
			//
			//   Ellipse2D.Double ellipse = new Ellipse2D.Double (double x, double y,
			//                                                    double w, double h);
			//
			//     creates an ellipse inscribed in box with bottom-left corner (x,y)
			//     and upper-right corner (x+y,w+h)
			//
			//   Rectangle2D.Double rect = new Rectangle2D.Double (double x, double y,
			//                                                     double w, double h);
			//
			//     creates a box with bottom-left corner (x,y) and upper-right
			//     corner (x+y,w+h)
			//
			// Each of these can be embedded in an Area object (e.g., new Area (rect)).

		}
	}

	/**
	 * No Eulerian Path <=> No of Odd Degree Vetrices >=2
	 * Eulerian Circuit No odd degree vertex
	 * Maximal Independent Set = Total_Nodes - Minimal_Vertex_Cover
	 * Directed Graph is Eulerian(having E.Cycle) iff in_degree=out_degree(of all vertices) and all vertices with non
	 * 0 degree belong to a single SCC.
	 * Maximum Bipartite Matching = Minimal Vertex Cover (Konigs Theorem)
	 */

	static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt() {
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-') {
				sgn = -1;
				c = read();
			}

			int res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public long nextLong() {
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-') {
				sign = -1;

				c = read();
			}

			long result = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public double nextDouble() {
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do {
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next() {
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do {
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine() {
			int c = read();

			StringBuilder result = new StringBuilder();

			do {
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c) {
			return c == '\n';
		}

		public void close() {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	static class JavaSolutionFormat {
		public static void main(String[] args) {
			InputReader in = new InputReader(System.in);
			PrintWriter out = new PrintWriter(System.out);
//			Solver solver = new Solver(in, out);

//			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
	}

}