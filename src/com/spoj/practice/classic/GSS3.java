package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

class GSS3
{
	public static void main(String[] args)
	{
		IR in = new IR(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		static final int INF = (int) 1e8;
		int n, q, arr[];
		Nd[] tr;
		Nd inf = new Nd(-INF, -INF, -INF, -INF);
		IR in;
		PrintWriter out;

		void solve()
		{
			n = in.nI();
			arr = new int[n];
			tr = new Nd[n << 2];

			for (int i = 0; i < n; i++)
				arr[i] = in.nI();

			b(1, 0, n - 1);
			q = in.nI();

			while (q-- > 0)
			{
				int type = in.nI();

				if (type == 0)
				{
					int x, y;

					x = in.nI() - 1;
					y = in.nI();

					upd(1, 0, n - 1, x, x, y);
				}
				else
				{
					int x, y;

					x = in.nI() - 1;
					y = in.nI() - 1;

					out.println(qr(1, 0, n - 1, x, y).ans);
				}
			}
		}

		void b(int nd, int tS, int tE)
		{
			if (tS == tE)
			{
				tr[nd] = new Nd(arr[tS], arr[tS], arr[tS], arr[tS]);

				return;
			}

			int mid = tS + tE >> 1;

			b(nd << 1, tS, mid);
			b((nd << 1) + 1, mid + 1, tE);

			Nd left, right;

			left = tr[nd << 1];
			right = tr[(nd << 1) + 1];
			tr[nd] = merge(left, right);
		}

		Nd qr(int nd, int tS, int tE, int rS, int rE)
		{
			Nd temp = tr[nd];

			if (tS > rE || tE < rS)
				return inf;

			if (tS >= rS && tE <= rE)
				return temp;

			int mid = tS + tE >> 1;
			Nd left, right;

			left = qr(nd << 1, tS, mid, rS, rE);
			right = qr((nd << 1) + 1, mid + 1, tE, rS, rE);

			return merge(left, right);
		}

		void upd(int node, int treeStart, int treeEnd, int rangeStart, int rangeEnd, int val)
		{
			Nd temp = tr[node];

			if (treeStart > rangeEnd || treeEnd < rangeStart)
				return;

			if (treeStart >= rangeStart && treeEnd <= rangeEnd)
			{
				temp.ans = temp.sum = temp.pre = temp.suf = val;

				return;
			}

			int mid = treeStart + treeEnd >> 1;

			upd(node << 1, treeStart, mid, rangeStart, rangeEnd, val);
			upd((node << 1) + 1, mid + 1, treeEnd, rangeStart, rangeEnd, val);

			tr[node] = merge(tr[node << 1], tr[(node << 1) + 1]);
		}

		Nd merge(Nd left, Nd right)
		{
			long pre, suf, sum, ans;

			pre = Math.max(left.pre, left.sum + right.pre);
			suf = Math.max(right.suf, left.suf + right.sum);
			sum = left.sum + right.sum;
			ans = Math.max(left.ans, Math.max(right.ans, Math.max(pre, Math.max(suf, left.suf + right
					.pre))));

			return new Nd(ans, sum, pre, suf);
		}

		class Nd
		{
			long ans, sum, pre, suf;

			public Nd(long ans, long sum, long pre, long suf)
			{
				this.ans = ans;
				this.sum = sum;
				this.pre = pre;
				this.suf = suf;
			}

		}

		public Solver(IR in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class IR
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public IR(InputStream stream)
		{
			this.stream = stream;
		}

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

		public int nI()
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

		public boolean isNewLine(int c)
		{
			return c == '\n';
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

	}

}
