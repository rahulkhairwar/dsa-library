package com.codeforces.competitions.year2018.round486div3;

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
		String[] arr;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			arr = new String[n];

			for (int i = 0; i < n; i++)
				arr[i] = in.readLine();

			Arrays.sort(arr, Comparator.comparingInt(String::length));

			boolean poss = true;

			for (int i = 0; i < n; i++)
			{
				for (int j = i + 1; j < n; j++)
				{
					if (!arr[j].contains(arr[i]))
					{
						poss = false;

						break;
					}
				}
			}

			if (!poss)
				out.println("NO");
			else
			{
				out.println("YES");

				for (String s : arr)
					out.println(s);
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
