package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class SantaClausAndAPalindrome
{
	public static void main(String[] args)
	{
		new SantaClausAndAPalindrome(System.in, System.out);
	}

	static class Solver
	{
		int k, n;
		String[] words;
		int[] val;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			k = Integer.parseInt(tok[0]);
			n = Integer.parseInt(tok[1]);
			words = new String[k];
			val = new int[k];

			Map<String, List<Integer>> map = new HashMap<>();

			for (int i = 0; i < k; i++)
			{
				tok = in.readLine().split(" ");
				words[i] = tok[0];
				val[i] = Integer.parseInt(tok[1]);

				List<Integer> list = map.get(words[i]);

				if (list == null)
				{
					list = new ArrayList<>();
					map.put(words[i], list);
				}

				list.add(val[i]);
			}

			for (Map.Entry<String, List<Integer>> entry : map.entrySet())
				entry.getValue().sort(Collections.reverseOrder());

			out.println(find(map));
		}

		long find(Map<String, List<Integer>> map)
		{
			long tot = 0;
			int singleMax = 0;
			Set<String> set = new HashSet<>();

			for (Map.Entry<String, List<Integer>> entry : map.entrySet())
			{
				String s = entry.getKey();
				String rev = new StringBuilder(s).reverse().toString();
				List<Integer> list = entry.getValue();

				if (set.contains(rev))
					continue;

				set.add(s);

				if (isPalindrome(s))
				{
					for (int i = 0; i < list.size(); i += 2)
					{
						if (i + 1 < list.size())
						{
							int ff = list.get(i);
							int ss = list.get(i + 1);

							if (ff < 0)
								break;
							else if (ss < 0)
							{
								if (ff + ss > 0)
								{
									singleMax = Math.max(singleMax, -ss);
									tot += ff + ss;
								}
								else
									singleMax = Math.max(singleMax, ff);
							}
							else
								tot += ff + ss;
						}
						else
						{
							int x = list.get(i);

							if (x < 0)
								break;

							singleMax = Math.max(singleMax, x);
						}
					}
				}
				else
				{
					List<Integer> revList = map.get(rev);

					if (revList == null)
						continue;

					for (int i = 0, j = 0; i < list.size() && j < revList.size(); i++, j++)
					{
						if (list.get(i) + revList.get(j) < 0)
							break;
						else
							tot += list.get(i) + revList.get(j);
					}
				}
			}

			return tot + singleMax;
		}

		boolean isPalindrome(String s)
		{
			int len = s.length();

			for (int i = 0; i < len / 2; i++)
				if (s.charAt(i) != s.charAt(len - 1 - i))
					return false;

			return true;
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private SantaClausAndAPalindrome(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
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
