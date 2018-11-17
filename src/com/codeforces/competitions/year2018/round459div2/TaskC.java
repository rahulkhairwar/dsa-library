package com.codeforces.competitions.year2018.round459div2;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		char[] s;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			s = in.readLine().toCharArray();
			n = s.length;

			int ans = 0;

			for (int i = 0; i < n; i++)
			{
				int cnt = 0;
				int qsns = 0;

				for (int j = i; j < n; j++)
				{
					if (s[j] == '(')
						cnt++;
					else if (s[j] == ')')
						cnt--;
					else
						qsns++;

					if (cnt < 0)
						break;

					if (qsns > cnt)
					{
						qsns--;
						cnt++;
					}

					if ((j - i + 1) % 2 == 0 && cnt >= 0 && qsns >= cnt)
						ans++;
				}
			}

			out.println(ans);
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
