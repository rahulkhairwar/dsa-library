package com.codeforces.practice.hard.year2017;

import java.io.*;
import java.util.*;

public final class ChocolateBar
{
	public static void main(String[] args)
	{
		new ChocolateBar(System.in, System.out);
	}

	private static class Solver
	{
		// BufferedReader in;
		static final int INF = (int) 1e9;
		int t, n, m, k;
		int[][][] dp;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();
			dp = new int[31][31][51];

			for (int i = 0; i < 31; i++)
				for (int j = 0; j < 31; j++)
					Arrays.fill(dp[i][j], INF);

			while (t-- > 0)
			{
				n = in.nextInt();
				m = in.nextInt();
				k = in.nextInt();
				out.println(n <= m ? find(m, n, k) : find(n, m, k));
			}
		}

		int find(int len, int bre, int needed)
		{
//            System.out.println("len : " + len + ", bre : " + bre + ", needed : " + needed + ", (n/b) : " + (needed /
//                    bre) + ", (n/l) : " + (needed / len));

			if (needed == 0 || needed == len * bre)
				return 0;

			if (needed < bre)
				return bre * bre + 1;

			if (needed == bre)
				return bre * bre;

			if (dp[len][bre][needed] != INF)
				return dp[len][bre][needed];

			int subLen = (needed / len) * len;
			int subBre = (needed / bre) * bre;
			int a = len - (needed / bre);
			int b = bre - (needed / len);
//            int ans = Math.min(bre * bre + find(Math.max(a, bre), Math.min(a, bre), needed - subBre), len * len +
//                    find(Math.max(b, len), Math.min(b, len), needed - subLen));

			int ans;

			ans = bre * bre + find(Math.max(a, bre), Math.min(a, bre), needed - subBre);

			if (subLen > 0)
				ans = Math.min(ans, len * len + find(Math.max(b, len), Math.min(b, len), needed - subLen));

//            int ans = bre * bre + find(Math.max(a, bre), Math.min(a, bre), needed - subBre);

			return dp[len][bre][needed] = ans;

//            return dp[len][bre][needed] = bre * bre + find(Math.max(a, bre), Math.min(a, bre), needed - subBre);
		}

		// uncomment below line to change to BufferedReader
		// public Solver(BufferedReader in, PrintWriter out)
		Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	private static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public ChocolateBar(InputStream inputStream, OutputStream outputStream)
	{
		// uncomment below line to change to BufferedReader
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(inputStream));
		InputReader in = new InputReader(inputStream);
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

/*

4
2 2 1
2 2 3
2 2 2
2 2 4

1
2 2 3

1
3 3 2

*/