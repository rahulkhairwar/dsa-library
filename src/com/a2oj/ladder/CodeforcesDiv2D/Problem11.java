package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.*;

public final class Problem11
{
	public static void main(String[] args)
	{
		new Problem11(System.in, System.out);
	}

	static class Solver
	{
		char[] a, b;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			a = in.readLine().toCharArray();
			b = in.readLine().toCharArray();

			if (a.length > b.length)
			{
				char[] temp = a;

				a = b;
				b = temp;
			}

			int cnt = 0;

			for (int i = 1; i <= a.length; i++)
			{
				if (a.length % i == 0 && b.length % i == 0)
				{
					if (isDivisor(a, a, i) && isDivisor(b, a, i))
						cnt++;
				}
			}

			out.println(cnt);
		}

		boolean isDivisor(char[] arr, char[] comp, int len)
		{
			for (int i = 0, j = 0; j < arr.length; i = (i + 1) % len, j++)
				if (comp[i] != arr[j])
					return false;

			return true;
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	public Problem11(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
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
