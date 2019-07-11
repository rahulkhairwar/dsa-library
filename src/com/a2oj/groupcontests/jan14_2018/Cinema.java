package com.a2oj.groupcontests.jan14_2018;

import java.io.*;
import java.util.*;

public class Cinema
{
	public static void main(String[] args)
	{
		new Cinema(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int m, k, n;
		Set<Integer> favourites;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			m = Integer.parseInt(tok[0]);
			k = Integer.parseInt(tok[1]);
			favourites = new HashSet<>(m << 1);
			tok = in.readLine().split(" ");

			for (int i = 0; i < k; i++)
				favourites.add(Integer.parseInt(tok[i]));

			n = Integer.parseInt(in.readLine());

			int[] favCnt = new int[n];
			int[] unknownCnt = new int[n];

			for (int i = 0; i < n; i++)
			{
				char[] name = in.readLine().toCharArray();
				int actorsCnt = Integer.parseInt(in.readLine());

				tok = in.readLine().split(" ");

				for (int j = 0; j < actorsCnt; j++)
				{
					int x = Integer.parseInt(tok[j]);

					if (x == 0)
						unknownCnt[i]++;
					else if (favourites.contains(x))
						favCnt[i] = Math.min(favCnt[i] + 1, k);
				}
			}

			int maxx = 0;

			for (int i = 0; i < n; i++)
				maxx = Math.max(maxx, Math.min(favCnt[i] + unknownCnt[i], k));

			for (int i = 0; i < n; i++)
			{
				if (favCnt[i] == maxx)
					out.println(0);
				else if (favCnt[i] + unknownCnt[i] >= k)
					out.println(2);
				else
					out.println(1);
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

	private Cinema(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

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
