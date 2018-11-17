package com.codeforces.competitions.year2017.goodbye2017;

import java.io.*;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		char[] s;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			s = in.readLine().toCharArray();

			int cnt = 0;

			for (char c : s)
			{
				if (c >= '0' && c <= '9')
				{
					if ((c - '0') % 2 == 1)
						cnt++;
				}
				else if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
						cnt++;
			}

			out.println(cnt);
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

