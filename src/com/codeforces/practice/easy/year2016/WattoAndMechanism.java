package com.codeforces.practice.easy.year2016;

import java.util.*;
import java.io.*;

public class WattoAndMechanism
{
	private static void solve(FastScanner in, PrintWriter out)
	{
		int n, m;
		Vector<String>[] list = new Vector[(int) (6 * 1e5 + 5)];

		for (int i = 0; i < 6 * 1e5 + 5; i++)
			list[i] = new Vector<>();

		n = in.nextInt();
		m = in.nextInt();

		while (n-- > 0)
		{
			String curr = in.nextLine();
			list[curr.length()].add(curr);
		}

		HashMap<String, Integer> foundValues = new HashMap<>(2 * m);

		while (m-- > 0)
		{
			String curr = in.nextLine();
			Integer value = foundValues.get(curr);

			if (value == null)
			{
				int counter = 0;
				int len = curr.length();
				int sizee = list[len].size();

				for (int i = 0; i < sizee; i++)
				{
					counter = 0;

					for (int j = 0; j < len; j++)
					{
						if (list[len].get(i).charAt(j) != curr.charAt(j))
							counter++;

						if (counter > 1)
							break;
					}

					if (counter == 1)
					{
						out.println("YES");
						foundValues.put(curr, 1);

						break;
					}
				}
				if (counter != 1)
				{
					out.println("NO");
					foundValues.put(curr, 2);
				}
			}
			else if (value == 1)
				out.println("YES");
			else if (value == 2)
				out.println("NO");
		}
	}

	public static void main(String[] args) throws IOException
	{
		FastScanner in = new FastScanner(System.in);
		PrintWriter out =
				new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), false);
		solve(in, out);
		in.close();
		out.close();
	}

	static class FastScanner
	{
		BufferedReader reader;
		StringTokenizer st;

		FastScanner(InputStream stream)
		{
			reader = new BufferedReader(new InputStreamReader(stream));
			st = null;
		}

		String next()
		{
			while (st == null || !st.hasMoreTokens())
			{
				try
				{
					String line = reader.readLine();
					if (line == null)
					{
						return null;
					}
					st = new StringTokenizer(line);
				}
				catch (Exception e)
				{
					throw new RuntimeException();
				}
			}
			return st.nextToken();
		}

		String nextLine()
		{
			String s = null;
			try
			{
				s = reader.readLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return s;
		}

		int nextInt()
		{
			return Integer.parseInt(next());
		}

		long nextLong()
		{
			return Long.parseLong(next());
		}

		double nextDouble()
		{
			return Double.parseDouble(next());
		}

		char nextChar()
		{
			return next().charAt(0);
		}

		int[] nextIntArray(int n)
		{
			int[] arr = new int[n];
			int i = 0;
			while (i < n)
			{
				arr[i++] = nextInt();
			}
			return arr;
		}

		long[] nextLongArray(int n)
		{
			long[] arr = new long[n];
			int i = 0;
			while (i < n)
			{
				arr[i++] = nextLong();
			}
			return arr;
		}

		int[] nextIntArrayOneBased(int n)
		{
			int[] arr = new int[n + 1];
			int i = 1;
			while (i <= n)
			{
				arr[i++] = nextInt();
			}
			return arr;
		}

		long[] nextLongArrayOneBased(int n)
		{
			long[] arr = new long[n + 1];
			int i = 1;
			while (i <= n)
			{
				arr[i++] = nextLong();
			}
			return arr;
		}

		void close()
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
