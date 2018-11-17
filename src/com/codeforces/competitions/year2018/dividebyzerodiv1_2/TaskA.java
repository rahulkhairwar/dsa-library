package com.codeforces.competitions.year2018.dividebyzerodiv1_2;

import java.io.*;
import java.util.*;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			char[] s = in.readLine().toCharArray();
			int n = s.length;
			int a = 0;
			int b = 0;
			int c = 0;
			int i = 0;

			while (i < n && s[i] == 'a')
			{
				i++;
				a++;
			}

			while (i < n && s[i] == 'b')
			{
				i++;
				b++;
			}

			while (i < n && s[i] == 'c')
			{
				i++;
				c++;
			}

			if (a == 0 || b == 0 || c == 0 || (a + b + c != n))
			{
				out.println("NO");

				return;
			}

			if (c == a || c == b)
				out.println("YES");
			else
				out.println("NO");
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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

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
