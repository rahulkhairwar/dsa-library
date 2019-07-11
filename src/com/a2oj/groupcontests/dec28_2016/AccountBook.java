package com.a2oj.groupcontests.dec28_2016;

import java.io.*;
import java.util.*;

/**
 * Question <a href="https://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=2932">link</a>.
 */
public class AccountBook
{
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
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

	static class Solver
	{
		int n, f;
		int[] arr;
		int[][] pos, neg;
		boolean[][] sign;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			arr = new int[41];
			pos = new int[41][(int) 16e3 + 5];
			neg = new int[41][(int) 16e3 + 5];
			sign = new boolean[2][41];

			while (true)
			{
				String[] tok = in.readLine().split(" ");

				n = Integer.parseInt(tok[0]);
				f = Integer.parseInt(tok[1]);

				if (n == 0 && f == 0)
					break;

				for (int i = 0; i < n; i++)
					arr[i] = Integer.parseInt(in.readLine());

				for (int i = 0; i < n; i++)
				{
					Arrays.fill(pos[i], -1);
					Arrays.fill(neg[i], -1);
				}

				Arrays.fill(sign[0], false);
				Arrays.fill(sign[1], false);

				int possible = possible(0, 0);

				if (possible == 0)
				{
					out.println("*");

					continue;
				}

				StringBuilder ans = new StringBuilder("");

				for (int i = 0; i < n; i++)
				{
					if (sign[0][i] && sign[1][i])
						ans.append("?");
					else if (sign[0][i])
						ans.append("-");
					else
						ans.append("+");
				}

				out.println(ans.toString());
			}
		}

		int possible(int ind, int currSum)
		{
			if (ind == n - 1)
			{
				int a, b;

				a = currSum + arr[ind];
				b = currSum - arr[ind];

				// adding this number to the sum.
				if (a == f)
				{
					if (a < 0)
						neg[ind][-a] = 1;
					else
						pos[ind][a] = 1;

					sign[1][ind] = true;

					return 1;
				}

				// subtracting this number from the sum.
				if (b == f)
				{
					if (b < 0)
						neg[ind][-b] = 1;
					else
						pos[ind][b] = 1;

					sign[0][ind] = true;

					return 1;
				}

				return 0;
			}

			if (currSum < -16e3 || currSum > 16e3)
				return 0;

			if (currSum < 0 && neg[ind][-currSum] != -1)
				return neg[ind][-currSum];

			if (currSum >= 0 && pos[ind][currSum] != -1)
				return pos[ind][currSum];

			int add, sub;

			add = possible(ind + 1, currSum + arr[ind]);
			sub = possible(ind + 1, currSum - arr[ind]);

			if (add == 1)
				sign[1][ind] = true;

			if (sub == 1)
				sign[0][ind] = true;

			if (add == 1 || sub == 1)
			{
				if (currSum < 0)
					neg[ind][-currSum] = 1;
				else
					pos[ind][currSum] = 1;

				return 1;
			}

			if (currSum < 0)
				neg[ind][-currSum] = 0;
			else
				pos[ind][currSum] = 0;

			return 0;
		}

		public Solver(BufferedReader in, PrintWriter out)
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

		public InputReader(InputStream stream)
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

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
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

/*

5 7
1
2
3
4
5
4 15
3
5
7
11
5 12
6
7
7
1
7
0 0

*/
