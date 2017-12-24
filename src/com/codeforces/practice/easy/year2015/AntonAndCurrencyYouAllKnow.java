package com.codeforces.practice.easy.year2015;

import java.io.*;
import java.util.*;

public final class AntonAndCurrencyYouAllKnow
{
	public static void main(String[] args)
	{
		new AntonAndCurrencyYouAllKnow(System.in, System.out);
	}

	static class Solver
	{
		char[] num;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			num = in.readLine().toCharArray();

			int n = num.length;
			int lastDig = num[n - 1] - '0';

			for (int i = 0; i < n; i++)
			{
				int dig = num[i] - '0';

				if (dig % 2 == 0 && dig < lastDig)
				{
					char temp = num[i];

					num[i] = num[n - 1];
					num[n - 1] = temp;
					out.println(new String(num));

					return;
				}
			}

			for (int i = n - 1; i >= 0; i--)
			{
				int dig = num[i] - '0';

				if (dig % 2 == 0)
				{
					char temp = num[i];

					num[i] = num[n - 1];
					num[n - 1] = temp;
					out.println(new String(num));

					return;
				}
			}

			out.println(-1);
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private AntonAndCurrencyYouAllKnow(InputStream inputStream, OutputStream outputStream)
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
