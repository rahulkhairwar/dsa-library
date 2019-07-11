package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.*;

public final class Problem14
{
	public static void main(String[] args)
	{
		new Problem14(System.in, System.out);
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

			int[] z = zFunction(s);
			int[] maxFromLeft = new int[n];
			int max = 0;

			for (int i = 1; i < n; i++)
				maxFromLeft[i] = Math.max(z[i], maxFromLeft[i - 1]);

			for (int i = n - 1; i > 0; i--)
			{
				if (z[i] <= max)
					continue;

				int end = i + z[i] - 1;

				if (end < n - 1)
					continue;

				if (maxFromLeft[i - 1] >= z[i])
					max = z[i];
			}

			if (max == 0)
				out.println("Just a legend");
			else
			{
				for (int i = 0; i < max; i++)
					out.print(s[i]);
			}
		}

		int[] zFunction(char[] s)
		{
			int len = s.length;
			int[] z = new int[len];

			for (int i = 1, l = 0, r = 0; i < len; i++)
			{
				if (i <= r)
					z[i] = Math.min(r - i + 1, z[i - l]);

				while (i + z[i] < len && s[z[i]] == s[i + z[i]])
					z[i]++;

				if (i + z[i] - 1 > r)
				{
					l = i;
					r = i + z[i] - 1;
				}
			}

			return z;
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

	public Problem14(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Problem14", 1 << 29);

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

/*

a

*/
