package com.codechef.competitions.longcompetitions.year2017.june;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class PRMQ
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
		int n, q;
		int[] arr;
		List<Integer>[] div;
		List<Pair>[] divisors, tree;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			pre();
			n = in.nextInt();
			arr = in.nextIntArray(n);
			tree = new List[n << 2];
			q = in.nextInt();
			build(1, 0, n - 1);
			cumulate(1, 0, n - 1);

			while (q-- > 0)
			{
				int l = in.nextInt() - 1;
				int r = in.nextInt() - 1;
				int x = in.nextInt();
				int y = in.nextInt();

				out.println(query(1, 0, n - 1, l, r, x, y));
			}
		}

		void cumulate(int node, int treeStart, int treeEnd)
		{
			int prev = 0;

			for (Pair pair : tree[node])
			{
				pair.y += prev;
				prev = pair.y;
			}

			if (treeStart == treeEnd)
				return;

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			cumulate(node << 1, treeStart, mid);
			cumulate((node << 1) + 1, mid + 1, treeEnd);
		}

		void pre()
		{
			int lim = (int) 1e6;

			div = new List[lim + 5];
			divisors = new List[lim + 5];

			for (int i = 2; i <= lim; i++)
				div[i] = new ArrayList<>();

			boolean[] isComposite = new boolean[lim + 5];
			int ctr = 2;

			isComposite[1] = true;
			div[2].add(2);

			while ((ctr << 1) <= lim)
			{
				isComposite[ctr << 1] = true;
				div[ctr << 1].add(2);
				ctr++;
			}

			for (int i = 3; i <= lim; i += 2)
			{
				if (isComposite[i])
					continue;

				div[i].add(i);
				ctr = i + i;

				while (ctr <= lim)
				{
					isComposite[ctr] = true;
					div[ctr].add(i);
					ctr += i;
				}
			}
		}

		void build(int node, int treeStart, int treeEnd)
		{
			if (treeStart == treeEnd)
			{
				tree[node] = createNode(arr[treeStart]);

				return;
			}

			int mid = treeStart + ((treeEnd - treeStart) >> 1);
			int left = node << 1;
			int right = left + 1;

			build(left, treeStart, mid);
			build(right, mid + 1, treeEnd);
			tree[node] = combine(tree[left], tree[right]);
		}

		void createTreeMap(int x)
		{
			divisors[x] = new ArrayList<>();

			for (int d : div[x])
			{
				int temp = x;
				int cnt = 0;

				while (temp % d == 0)
				{
					temp /= d;
					cnt++;
				}

				divisors[x].add(new Pair(d, cnt));
			}
		}

		List<Pair> createNode(int x)
		{
			List<Pair> list = new ArrayList<>();

			if (divisors[x] == null)
				createTreeMap(x);

			for (Pair pair : divisors[x])
				list.add(new Pair(pair.x, pair.y));

			return list;
		}

		List<Pair> combine(List<Pair> one, List<Pair> two)
		{
			List<Pair> list = new ArrayList<>();
			int oneSize = one.size();
			int twoSize = two.size();

			for (int i = 0, j = 0; i < oneSize || j < twoSize; )
			{
				Pair pair;

				if (i == oneSize || (j < twoSize && one.get(i).x > two.get(j).x))
				{
					pair = new Pair(two.get(j).x, two.get(j).y);
					j++;
				}
				else if (j == twoSize || (i < oneSize && one.get(i).x < two.get(j).x))
				{
					pair = new Pair(one.get(i).x, one.get(i).y);
					i++;
				}
				else
				{
					pair = new Pair(one.get(i).x, one.get(i).y + two.get(j).y);
					i++;
					j++;
				}

				list.add(pair);
			}

			return list;
		}

		int query(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int x, int y)
		{
			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return 0;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
				return count(tree[node], x, y);

			int mid = treeStart + ((treeEnd - treeStart) >> 1);

			return query(node << 1, treeStart, mid, rangeStart, rangeEnd, x, y) + query((node << 1) + 1, mid + 1,
					treeEnd, rangeStart, rangeEnd, x, y);
		}

		int count(List<Pair> list, int left, int right)
		{
			return findLessThanEqualTo(list, right) - findLessThanEqualTo(list, left - 1);
		}

		int findLessThanEqualTo(List<Pair> list, int x)
		{
			int low, high, mid;

			low = 0;
			high = list.size() - 1;

			while (low <= high)
			{
				mid = low + ((high - low) >> 1);

				if (list.get(mid).x <= x)
				{
					if (mid == list.size() - 1 || list.get(mid + 1).x > x)
						return list.get(mid).y;

					low = mid + 1;
				}
				else
				{
					if (mid == 0)
						return 0;

					high = mid - 1;
				}
			}

			return 0;
		}

		class Pair
		{
			int x, y;

			public Pair(int x, int y)
			{
				this.x = x;
				this.y = y;
			}

		}

		Solver(InputReader in, PrintWriter out)
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

}

/*

4
2 3 4 5
2
1 3 2 3
1 4 2 5

*/
