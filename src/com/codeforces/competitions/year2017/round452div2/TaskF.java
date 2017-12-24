package com.codeforces.competitions.year2017.round452div2;

import java.io.*;
import java.util.*;

public class TaskF
{
	public static void main(String[] args)
	{
		new TaskF(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m;
		char[] s;
		int[] bit;
		TreeSet<Integer>[] small, caps, digits;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			s = in.readLine().toCharArray();
			bit = new int[n + 1];
			small = new TreeSet[26];
			caps = new TreeSet[26];
			digits = new TreeSet[10];

			for (int i = 0; i < 26; i++)
			{
				small[i] = new TreeSet<>(Integer::compareTo);
				caps[i] = new TreeSet<>(Integer::compareTo);
			}

			for (int i = 0; i < 10; i++)
				digits[i] = new TreeSet<>(Integer::compareTo);

			for (int i = 0; i < n; i++)
			{
				char x = s[i];

				if (x >= 'a' && x <= 'z')
					small[x - 'a'].add(i + 1);
				else if (x >= 'A' && x <= 'Z')
					caps[x - 'A'].add(i + 1);
				else
					digits[x - '0'].add(i + 1);

				add(i + 1, 1);
			}

			for (int i = 0; i < m; i++)
			{
				tok = in.readLine().split(" ");

				int l = Integer.parseInt(tok[0]);
				int r = Integer.parseInt(tok[1]);
				char ch = tok[2].charAt(0);

				if (ch >= 'a' && ch <= 'z')
					delete(small[ch - 'a'], l, r);
				else if (ch >= 'A' && ch <= 'Z')
					delete(caps[ch - 'A'], l, r);
				else
					delete(digits[ch - '0'], l, r);
			}

			StringBuilder ans = new StringBuilder("");

			for (int i = 1; i <= n; i++)
			{
				if (query(i) - (i > 1 ? query(i - 1) : 0) == 1)
					ans.append(s[i - 1]);
			}

			out.println(ans);
		}

		void add(int ind, int val)
		{
			while (ind <= n)
			{
				bit[ind] += val;
				ind += ind & -ind;
			}
		}

		int query(int ind)
		{
			int cnt = 0;

			while (ind > 0)
			{
				cnt += bit[ind];
				ind -= ind & -ind;
			}

			return cnt;
		}

		int getActualPosition(int p)
		{
			int low = 1;
			int high = n;

			while (low <= high)
			{
				int mid = low + high >> 1;

				if (query(mid) == p)
				{
					if (mid == 1 || query(mid - 1) < p)
						return mid;

					high = mid - 1;
				}
				else if (query(mid) < p)
					low = mid + 1;
				else
					high = mid - 1;
			}

			return -1;
		}

		void delete(TreeSet<Integer> treeSet, int l, int r)
		{
			int actualL = getActualPosition(l);
			int actualR = getActualPosition(r);
			NavigableSet<Integer> tailSet = treeSet.subSet(actualL, true, actualR, true);
			List<Integer> remove = new ArrayList<>();

			remove.addAll(tailSet);

			for (int x : remove)
			{
				treeSet.remove(x);
				add(x, -1);
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

	public TaskF(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskF", 1 << 29);

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
