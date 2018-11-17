package com.codeforces.practice.medium;

import java.io.*;
import java.util.*;

public final class C388Div2
{
	public static void main(String[] args)
	{
		new C388Div2(System.in, System.out);
	}

	static class Solver implements Runnable
	{
        int n;
        char[] s;
		BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			s = in.readLine().toCharArray();

			TreeSet<Integer> all = new TreeSet<>(Integer::compareTo);
			TreeSet<Integer> dSet = new TreeSet<>(Integer::compareTo);
			TreeSet<Integer> rSet = new TreeSet<>(Integer::compareTo);

			for (int i = 0; i < n; i++)
			{
				all.add(i);

				if (s[i] == 'D')
					dSet.add(i);
				else
					rSet.add(i);
			}

			boolean[] removed = new boolean[n];

			while (dSet.size() > 0 && rSet.size() > 0)
			{
				List<Integer> toRemove = new ArrayList<>();

				for (int pos : all)
				{
					if (removed[pos])
						continue;

					TreeSet<Integer> curr, other;

					if (s[pos] == 'D')
					{
						curr = dSet;
						other = rSet;
					}
					else
					{
						curr = rSet;
						other = dSet;
					}

					if (curr.size() == 0 || other.size() == 0)
						break;

					Integer higher = other.higher(pos);
					int rem;

					if (higher == null)
						rem = other.pollFirst();
					else
					{
						rem = higher;
						other.remove(higher);
					}

					removed[rem] = true;
					toRemove.add(rem);
				}

				for (int rem : toRemove)
					all.remove(rem);
			}

			if (dSet.size() == 0)
				out.println("R");
			else
				out.println("D");
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

	private C388Div2(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "C388Div2", 1 << 29);

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
