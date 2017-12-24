package com.codeforces.competitions.year2017.round451div2;

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
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String line = in.readLine();
			Map<String, Set<String>> map = new HashMap<>();

			n = Integer.parseInt(line);

			for (int i = 0; i < n; i++)
			{
				String[] tok = in.readLine().split(" ");
				String name = tok[0];
				int cnt = Integer.parseInt(tok[1]);
				Set<String> set;

				if (map.containsKey(name))
					set = map.get(name);
				else
				{
					set = new HashSet<>();
					map.put(name, set);
				}

				for (int j = 0; j < cnt; j++)
					set.add(tok[2 + j]);
			}

			for (Map.Entry<String, Set<String>> entry : map.entrySet())
			{
				Set<String> set = entry.getValue();
				Set<String> remove = new HashSet<>();

				for (String str : set)
				{
					for (String cmp : set)
					{
						if (str.equals(cmp))
							continue;

						if (cmp.endsWith(str))
						{
							remove.add(str);

							break;
						}
					}
				}

				for (String rem : remove)
					set.remove(rem);
			}

			out.println(map.size());

			for (Map.Entry<String, Set<String>> entry : map.entrySet())
			{
				StringBuilder ans = new StringBuilder(entry.getKey());
				Set<String> set = entry.getValue();

				ans.append(" ").append(set.size());

				for (String str : set)
					ans.append(" ").append(str);

				out.println(ans.toString());
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

