package com.codeforces.competitions.year2018.round527div3;

import java.io.*;
import java.util.*;

public final class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n, cnt;
        String[] strings, temp;
		BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			cnt = 2 * n - 2;
			strings = new String[cnt];
			temp = new String[cnt];

			for (int i = 0; i < cnt; i++)
			{
				strings[i] = in.readLine();
				temp[i] = strings[i];
			}

			Arrays.sort(strings, Comparator.comparingInt(String::length));

			String last = strings[cnt - 1];
			String secondLast = strings[cnt - 2];
			String ans;

			if ((ans = poss(last, secondLast)) != null)
				out.println(ans);
			else
				out.println(poss(secondLast, last));
		}

		String poss(String pref, String suff)
		{
			String[][] all = new String[n][2];
			boolean[][] used = new boolean[n][2];
			char[][] what = new char[n][2];
			int[] c = new int[n];

			for (int i = 0; i < cnt; i++)
			{
				int len = temp[i].length();

				all[len][c[len]++] = temp[i];
			}

			Arrays.fill(what[0], 'S');
			Arrays.fill(what[1], 'S');

			for (int i = 1; i < n; i++)
			{
				String pf = pref.substring(0, i);

				if (all[i][0].equals(pf) && !used[i][0])
				{
					used[i][0] = true;
					what[i][0] = 'P';
					used[i][1] = true;
					what[i][1] = 'S';
				}
				else if (all[i][1].equals(pf))
				{
					used[i][1] = true;
					what[i][1] = 'P';
					used[i][0] = true;
					what[i][0] = 'S';
				}
				else
					return null;
			}

			String revSuff = new StringBuilder(suff).reverse().toString();

			for (int i = 1; i < n; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					String rev = new StringBuilder(all[i][j]).reverse().toString();

					if (what[i][j] == 'S' && !(revSuff.startsWith(rev)))
						return null;
				}
			}

			char[] ans = new char[cnt];

			for (int i = 1; i < n; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					char ch = what[i][j];

					for (int k = 0; k < cnt; k++)
					{
						if (temp[k].equals(all[i][j]))
						{
							temp[k] = "___";
							ans[k] = ch;

							break;
						}
					}
				}
			}

			return new String(ans);
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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
