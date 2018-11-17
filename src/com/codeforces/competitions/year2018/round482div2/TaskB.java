package com.codeforces.competitions.year2018.round482div2;

import java.io.*;
import java.util.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		BufferedReader in;
//		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());

			String[] s = new String[3];

			for (int i = 0; i < 3; i++)
				s[i] = in.readLine();

			int[] ans = new int[3];

			for (int i1 = 0; i1 < s.length; i1++)
			{
				String ss = s[i1];
				int[] cnt = new int[256];

				for (char ch : ss.toCharArray())
					cnt[ch]++;

				Set<Integer> set = new HashSet<>();

				for (int i = 0; i < 256; i++)
					set.add(cnt[i]);

				set.remove(0);

				int max = 0;

				for (int i = 0; i < 256; i++)
					max = Math.max(max, cnt[i]);

				int len = ss.length();
				int rem = len - max;

				if (rem == n)
					ans[i1] = len;
				else if (rem > n)
					ans[i1] = max + n;
				else
				{
					int stepsLeft = n - max - rem;

					if (stepsLeft % 2 == 0)
						ans[i1] = len;
					else
						ans[i1] = len - 1;
				}
			}

			int[] temp = new int[3];

			System.arraycopy(ans, 0, temp, 0, 3);
			Arrays.sort(ans);

			if (ans[1] == ans[2])
			{
				out.println("Draw");

				return;
			}

			int max = 0;

			for (int i = 0; i < 3; i++)
				max = Math.max(max, temp[i]);

			if (temp[0] == max)
				out.println("Kuro");
			else if (temp[1] == max)
				out.println("Shiro");
			else
				out.println("Katie");
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

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskB", 1 << 29);

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
