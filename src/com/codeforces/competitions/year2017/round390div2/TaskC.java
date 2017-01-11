package com.codeforces.competitions.year2017.round390div2;

import java.io.*;
import java.util.*;

public final class TaskC
{
	public static void main(String[] args)
	{
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		int n, k;
		Point[] pts;
		Q[] qs;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			k = in.nextInt();
			pts = new Point[n];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(i, in.nextInt(), in.nextInt());

			Arrays.sort(pts, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.l == o2.l)
						return Integer.compare(o1.r, o2.r);

					return Integer.compare(o1.l, o2.l);
				}
			});

			PriorityQueue<Integer> queue = new PriorityQueue<>();

			for (int i = 0; i < k; i++)
				queue.add(pts[i].r);

			int max = queue.peek() - pts[k - 1].l + 1;
			int ml, mr;

			ml = pts[k - 1].l;
			mr = queue.peek();

			for (int i = k; i < n; i++)
			{
				queue.poll();
				queue.add(pts[i].r);

				int curr = queue.peek() - pts[i].l + 1;

				if (curr > max)
				{
					max = curr;
					ml = pts[i].l;
					mr = queue.peek();
				}
			}

			if (max <= 0)
			{
				out.println(0);

				for (int i = 1; i <= k; i++)
					out.print(i + " ");
			}
			else
			{
				out.println(max);

				for (int i = 0; i < n && k > 0; i++)
				{
					if (pts[i].l <= ml && pts[i].r >= mr)
					{
						out.print(pts[i].id + 1 + " ");
						k--;
					}
				}
			}
		}

		void solve2()
		{
			n = in.nextInt();
			k = in.nextInt();
			pts = new Point[n];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(i, in.nextInt(), in.nextInt());

			PriorityQueue<Integer> lefts = new PriorityQueue<>(Collections.reverseOrder());
			int ctr = 0;

			qs = new Q[2 * n];

			for (int i = 0; i < n; i++)
			{
				qs[ctr++] = new Q(i, pts[i].l, 'l', pts[i].r);
				qs[ctr++] = new Q(i, pts[i].r, 'r', pts[i].l);
			}

			Arrays.sort(qs, new Comparator<Q>()
			{
				@Override public int compare(Q o1, Q o2)
				{
					if (o1.x == o2.x)
						return Character.compare(o1.type, o2.type);

					return Integer.compare(o1.x, o2.x);
				}
			});

			int ans, ml, mr;

			ans = ml = mr = 0;

			for (int i = 0; i < n << 1; i++)
			{
				if (qs[i].type == 'l')
					lefts.add(qs[i].x);
				else
				{
					if (lefts.size() >= k && qs[i].x - lefts.peek() + 1 > ans)
					{
						ans = qs[i].x - lefts.peek() + 1;
						ml = lefts.peek();
						mr = qs[i].x;
					}

					lefts.remove(qs[i].other);
				}
			}

			List<Integer> list = new ArrayList<>(k);

			for (int i = 0; i < n; i++)
			{
				if (pts[i].l <= ml && pts[i].r >= mr)
				{
					list.add(pts[i].id);

					if (list.size() == k)
						break;
				}
			}

//			System.out.println("ml : " + ml + ", mr : " + mr);

			if (list.size() < k)
			{
				out.println(0);

				for (int i = 1; i <= k; i++)
					out.print(i + " ");
			}
			else
			{
				out.println(ans);

				for (int x : list)
					out.print(x + 1 + " ");
			}
		}

		class Point
		{
			int id, l, r;

			public Point(int id, int l, int r)
			{
				this.id = id;
				this.l = l;
				this.r = r;
			}

			@Override public String toString()
			{
				return String.format("%d -> (%d, %d)", id, l, r);
			}

		}

		class Q
		{
			int id, x, other;
			char type;

			public Q(int id, int x, char type, int other)
			{
				this.id = id;
				this.x = x;
				this.type = type;
				this.other = other;
			}

		}

/*		void solve2()
		{
			n = in.nextInt();
			k = in.nextInt();
			pts = new Point[n];

			for (int i = 0; i < n; i++)
				pts[i] = new Point(i, in.nextInt(), in.nextInt());

			shrink();
			bit = new int[max + 1];
			segmentTree = new Node[5 * max + 1];

			for (int i = 0; i < 5 * max; i++)
				segmentTree[i] = new Node(0);

			for (int i = 0; i < n; i++)
			{
				System.out.println("adding to [" + pts[i].sl + ", " + pts[i].sr);
				add(1, 0, max - 1, pts[i].sl, pts[i].sr, 1);
			}

			int ans = 0;
			int ml, mr;
			int currLeft;

			ml = mr = 0;

			System.out.println("query :");
			for (int i = 1; i < max; i++)
				System.out.println("i : " + i + ", query[i] : " + query(1, 0, max - 1, i, i));

			for (int i = 1; i < max; i++)
			{
				int currRight = currLeft = i;

//				if (query(1, 0, max - 1, i, i) < k)
//					continue;

				while (i < max && query(1, 0, max - 1, i, i) >= k)
				{
					currRight = i;
					i++;
				}

				Integer ll, rr;

				System.out.println("cL : " + currLeft + ", cr : " + currRight);
				ll = revMap.get(currLeft);
				rr = revMap.get(currRight);

				if (ll == null || rr == null)
					continue;

				System.out.println("\tll : " + ll + ", rr : " + rr + ", ans : " + ans);
				if (rr - ll + 1 > ans)
				{
					ans = rr - ll + 1;
					ml = ll;
					mr = rr;
				}
			}

			List<Integer> list = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				if (pts[i].l <= ml && pts[i].r >= mr)
				{
					list.add(i);

					if (list.size() == k)
						break;
				}
			}

			if (list.size() < k)
			{
				out.println(0);

				for (int i = 1; i <= k; i++)
					out.print(i + " ");
			}
			else
			{
				out.println(ans);

				for (int x : list)
					out.print(x + 1 + " ");
			}
		}

		void shrink()
		{
			List<Integer> list = new ArrayList<>(n << 1);

			for (int i = 0; i < n; i++)
			{
				list.add(pts[i].l);
				list.add(pts[i].r);
			}

			Collections.sort(list);
			map = new HashMap<>();
			revMap = new HashMap<>();

			int ctr = 1;

			for (int xx : list)
			{
				if (!map.containsKey(xx))
					map.put(xx, ctr++);
			}

			max = ctr + 1;

			for (int i = 0; i < n; i++)
			{
				pts[i].sl = map.get(pts[i].l);
				pts[i].sr = map.get(pts[i].r);
				revMap.put(pts[i].sl, pts[i].l);
				revMap.put(pts[i].sr, pts[i].r);
			}
//			System.out.println("hh");
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd)
		{
			Node tempNode = segmentTree[node];

			// i.e., lazy
			if (tempNode.update > 0)
			{
				tempNode.sum += tempNode.update;

				// not a leaf node
				if (treeStart != treeEnd)
				{
					int update = tempNode.update;

					segmentTree[2 * node].update = update;
					segmentTree[2 * node + 1].update = update;
				}

				tempNode.update = 0;
			}

			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return tempNode.sum;

			int mid, lSum, rSum;

			mid = (treeStart + treeEnd) / 2;
			lSum = query(2 * node, treeStart, mid, rangeStart, rangeEnd);
			rSum = query(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd);

			return lSum + rSum;
		}

		void add(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int addValue)
		{
			Node tempNode = segmentTree[node];

			if (tempNode.update > 0)
			{
				tempNode.sum += tempNode.update;

				if (treeStart != treeEnd)
				{
					segmentTree[2 * node].update += tempNode.update;
					segmentTree[2 * node + 1].update += tempNode.update;
				}

				tempNode.update = 0;
			}

			// if the update range is completely out of the range that this node
			// stores information of
			if (treeStart > treeEnd || treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			// if the range this node holds is completely inside of the update range
			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				tempNode.sum += addValue;

				// not a leaf node
				if (treeStart != treeEnd)
				{
					segmentTree[2 * node].update += addValue;
					segmentTree[2 * node + 1].update += addValue;
					System.out.println("setting lazy for ts : " + treeStart + ", te : " + treeEnd);
				}

				return;
			}

			int mid = (treeStart + treeEnd) / 2;

			add(2 * node, treeStart, mid, rangeStart, rangeEnd, addValue);
			add(2 * node + 1, mid + 1, treeEnd, rangeStart, rangeEnd, addValue);
			tempNode.sum = segmentTree[2 * node].sum + segmentTree[2 * node + 1].sum;
		}

		static class Node
		{
			int sum;
			int update; // if lazy, this will be != 0, else, 0

			public Node(int sum)
			{
				this.sum = sum;
				this.update = 0;
			}

		}

		void add(int l, int r)
	{
		put(r, 1);
		put(l - 1, -1);
	}

		void put(int ind, int val)
		{
			if (ind == 0)
				return;

			while (ind <= max)
			{
				bit[ind] += val;
				ind += ind & -ind;
			}
		}

		int query(int l, int r)
		{
			return ask(r) - ask(l - 1);
		}

		int ask(int ind)
		{
			if (ind == 0)
				return 0;

			int ans = 0;

			while (ind > 0)
			{
				ans += bit[ind];
				ind -= ind & -ind;
			}

			return ans;
		}

		class Point
		{
			int id, l, r, sl, sr;

			public Point(int id, int l, int r)
			{
				this.id = id;
				this.l = l;
				this.r = r;
			}

		}*/

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

	}

}
