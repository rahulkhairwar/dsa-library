package com.codeforces.competitions.year2019.hello2019;

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
			String table = in.readLine();
			String[] cards = in.readLine().split(" ");
			boolean canPlay = false;

			for (int i = 0; i < 5; i++)
			{
				if (table.charAt(0) == cards[i].charAt(0) || table.charAt(1) == cards[i].charAt(1))
					canPlay = true;
			}

			if (canPlay)
				out.println("YES");
			else
				out.println("NO");
		}

		Solver(BufferedReader in, PrintWriter out)
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

