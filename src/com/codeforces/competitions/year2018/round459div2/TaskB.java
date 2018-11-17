package com.codeforces.competitions.year2018.round459div2;

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
		int n, m;
		StringBuilder[] queries;
		Map<String, String> map;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			m = Integer.parseInt(tok[1]);
			queries = new StringBuilder[m];
			map = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				String line = in.readLine();

				tok = line.split(" ");
				map.put(tok[1], tok[0]);
			}

			for (int i = 0; i < m; i++)
			{
				String line = in.readLine();

				queries[i] = new StringBuilder(line);
				tok = line.split(" ");

				String name = map.get(tok[1].substring(0, tok[1].length() - 1));

				queries[i].append(" #").append(name);
				out.println(queries[i]);
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

