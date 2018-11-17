package com.codechef.competitions.longcompetitions.year2018.july;

import java.io.*;
import java.util.*;

public class GEARS
{
	public static void main(String[] args)
	{
		new GEARS(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, m, currTime;
		int[] arr;
		Node[] nodes;
		int[] visCnt;
		List<Integer>[] adj;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			m = in.nextInt();
			currTime = 1;
			arr = in.nextIntArray(n);
			nodes = new Node[n];
			visCnt = new int[n];
			adj = new List[n];

			for (int i = 0; i < n; i++)
			{
				nodes[i] = new Node(i, -1);
				adj[i] = new ArrayList<>();
			}

			while (m-- > 0)
			{
				int type = in.nextInt();

				switch (type)
				{
					case 1:
						changeTeeth();

						break;
					case 2:
						connect();

						break;
					case 3:
						findSpeed();

						break;
					default:
						break;
				}
			}
		}

		void changeTeeth()
		{
			int x = in.nextInt() - 1;
			int t = in.nextInt();

			arr[x] = t;
		}

		void connect()
		{
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			int xCol = nodes[x].col;
			int yCol = nodes[y].col;

			if (xCol == -1)
			{
				if (yCol == -1)
				{
					xCol = 0;
					yCol = 1;
				}
				else
					xCol = (yCol + 1) % 2;

				union(nodes[x], nodes[y]);
			}
			else
			{
				if (yCol == -1)
				{
					yCol = (xCol + 1) % 2;
					union(nodes[x], nodes[y]);
				}
				else
				{
					Node xParent = findParent(nodes[x]);
					Node yParent = findParent(nodes[y]);
					int xx = xParent.key;
					int yy = yParent.key;

					if (xParent.isBlocked || yParent.isBlocked)
						nodes[x].isBlocked = nodes[y].isBlocked = true;
					else if (xCol == yCol)
					{
						if (xx == yy)
						{
							xParent.isBlocked = yParent.isBlocked = true;

							return;
						}

						currTime++;

						if (xParent.size > yParent.size)
						{
							changeColor(y);
							yCol = (yCol + 1) % 2;
						}
						else
						{
							changeColor(x);
							xCol = (xCol + 1) % 2;
						}
					}

					union(nodes[x], nodes[y]);
				}
			}

			nodes[x].col = xCol;
			nodes[y].col = yCol;
		}

		void addEdge(int x, int y)
		{
			adj[x].add(y);
			adj[y].add(x);
		}

		void changeColor(int node)
		{
			visCnt[node] = currTime;
			nodes[node].col = (nodes[node].col + 1) % 2;

			for (int x : adj[node])
			{
				if (visCnt[x] < visCnt[node])
					changeColor(x);
			}
		}

		void findSpeed()
		{
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			int v = in.nextInt();
			Node xParent = findParent(nodes[x]);
			Node yParent = findParent(nodes[y]);
			int xx = xParent.key;
			int yy = yParent.key;

			if (xx != yy || xParent.isBlocked || nodes[x].isBlocked || nodes[y].isBlocked)
				out.println(0);
			else
			{
				long num = (long) v * arr[x];
				long den = arr[y];
				long gcd = CMath.gcd(num, den);
				int sign = 1;

				num /= gcd;
				den /= gcd;

				if (nodes[x].col != nodes[y].col)
					sign = -1;

				out.println(sign * num + "/" + den);
			}
		}

		Node findParent(Node node)
		{
			if (node.key == node.par.key)
				return node;

			return node.par = findParent(node.par);
		}

		void union(Node a, Node b)
		{
			Node aParent = findParent(a);
			Node bParent = findParent(b);

			if (aParent.key == bParent.key)
				return;

			aParent.size = bParent.size = (aParent.size + bParent.size);
			aParent.isBlocked = bParent.isBlocked = (aParent.isBlocked || bParent.isBlocked);
			addEdge(a.key, b.key);

			if (aParent.height > bParent.height)
				bParent.par = aParent;
			else if (aParent.height < bParent.height)
				aParent.par = bParent;
			else
			{
				bParent.par = aParent;
				aParent.height++;
			}
		}

		class Node
		{
			int key, col, height, size;
			boolean isBlocked;
			Node par;

			Node(int key, int col)
			{
				this.key = key;
				this.col = col;
				size = 1;
				par = this;
			}

		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
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

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
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

	static class CMath
	{
		static long gcd(long a, long b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

	}

	public GEARS(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "GEARS", 1 << 29);

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
			in.close();
			out.flush();
			out.close();
		}
	}

}
