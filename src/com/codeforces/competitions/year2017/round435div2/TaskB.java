package com.codeforces.competitions.year2017.round435div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver
	{
		int n, oneCnt, twoCnt;
		int[] col;
		int[][] to;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			adj = new List[n];
			col = new int[n];
			to = new int[n][3];

			for (int i = 0; i < n; i++)
				adj[i] = new ArrayList<>();

			for (int i = 1; i < n; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				adj[u].add(v);
				adj[v].add(u);
			}

			dfs(0, 1);

			long ans = 0;

			for (int i = 0; i < n; i++)
			{
				if (col[i] == 1)
					ans += (twoCnt - to[i][2]);
				else
					ans += (oneCnt - to[i][1]);
			}

			out.println(ans / 2);
		}

		void dfs(int node, int colour)
		{
			if (col[node] > 0)
				return;

			col[node] = colour;

			if (colour == 1)
				oneCnt++;
			else
				twoCnt++;

			for (int x : adj[node])
			{
				dfs(x, colour == 1 ? 2 : 1);
				to[node][colour == 1 ? 2 : 1]++;
			}
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
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

		public int nextInt()
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

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public void close()
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

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
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

