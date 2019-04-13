package com.codeforces.competitions.year2019.hello2019;

import com.testingUtil.util.InputReader;

import java.io.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		int[] arr;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = in.nextInt();
			arr = in.nextIntArray(n);

			for (int i = 0; i < (1 << n); i++)
			{
				if (poss(i))
				{
					out.println("YES");

					return;
				}
			}

			out.println("NO");
		}

		boolean poss(int mask)
		{
			int a = 0;
			int b = 0;

			for (int i = 0; i < n; i++)
			{
				if ((mask & (1 << i)) > 0)
					a += arr[i];
				else
					b += arr[i];
			}

			a %= 360;
			b %= 360;

			return a == b;
		}

		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
		{
			solve();
		}

	}

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
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
			in.close();
			out.flush();
			out.close();
		}
	}

}

