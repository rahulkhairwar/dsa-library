package com.a2onlinejudge.ladder.CodeforcesDiv2D;

import java.io.*;
import java.util.*;

public class Problem7
{
	public static void main(String[] args)
	{
		new Problem7(System.in, System.out);
	}

	private static class Solver implements Runnable
	{
		int n, m;
		Node[] nodes;
		int[][] union;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			nodes = new Node[n];
			union = new int[n][n];

			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node();
				Arrays.fill(union[i], -1);
			}

			for (int i = 0; i < m; i++)
			{
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;

				nodes[u].toSet.add(v);
				nodes[v].fromSet.add(u);
				nodes[v].from.add(u);
			}

			int ans = 0;

			for (int i = 0; i < n; i++)
			{
				if (nodes[i].from.size() < 2)
					continue;

				for (int j = 0; j < nodes[i].from.size(); j++)
				{
					for (int k = j + 1; k < nodes[i].from.size(); k++)
					{
						int a = nodes[i].from.get(j);
						int b = nodes[i].from.get(k);

						ans += getParentUnion(a, b);

						if (nodes[a].fromSet.contains(i) && nodes[b].fromSet.contains(i))
							ans--;
					}
				}
			}

			out.println(ans);
		}

		int getParentUnion(int a, int b)
		{
			if (union[a][b] != -1)
				return union[a][b];

			if (nodes[a].fromSet.size() > nodes[b].fromSet.size())
			{
				int temp = a;

				a = b;
				b = temp;
			}

			int cnt = 0;

			for (int x : nodes[a].fromSet)
			{
				if (nodes[b].fromSet.contains(x))
					cnt++;
			}

			return union[a][b] = union[b][a] = cnt;
		}

		static class Node
		{
			Set<Integer> toSet, fromSet;
			List<Integer> from;

			Node()
			{
				toSet = new HashSet<>();
				fromSet = new HashSet<>();
				from = new ArrayList<>();
			}

		}

		Solver(InputReader in, PrintWriter out)
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

	private Problem7(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		in.close();
		out.flush();
		out.close();
	}

}
