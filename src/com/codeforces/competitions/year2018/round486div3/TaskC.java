package com.codeforces.competitions.year2018.round486div3;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int k;
		int[] lengths;
		long[] sums;
		List<Integer>[] sequences;
		Map<Long, Pair> map;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			k = in.nextInt();
			lengths = new int[k];
			sums = new long[k];
			map = new HashMap<>();
			sequences = new List[k];

			for (int i = 0; i < k; i++)
			{
				lengths[i] = in.nextInt();
				sequences[i] = new ArrayList<>();

				for (int j = 0; j < lengths[i]; j++)
				{
					int x = in.nextInt();

					sequences[i].add(x);
					sums[i] += x;
				}

				for (int j = 0; j < lengths[i]; j++)
				{
					long sum = sums[i] - sequences[i].get(j);
					Pair pair = new Pair(j, i, sum);

					if (map.containsKey(sum))
					{
						Pair other = map.get(sum);

						if (other.seqInd == i)
							continue;

						out.println("YES");
						out.println(pair.seqInd + 1 + " " + (pair.remInd + 1));
						out.println(other.seqInd + 1 + " " + (other.remInd + 1));

						return;
					}

					map.put(sum, pair);
				}
			}

			out.println("NO");
		}

		class Pair
		{
			int remInd, seqInd;
			long sum;

			public Pair(int remInd, int seqInd, long sum)
			{
				this.remInd = remInd;
				this.seqInd = seqInd;
				this.sum = sum;
			}

			@Override public boolean equals(Object o)
			{
				if (this == o)
					return true;
				if (!(o instanceof Pair))
					return false;

				Pair pair = (Pair) o;

				if (seqInd == pair.seqInd)
					return sum == pair.sum;
				return sum == pair.sum;
			}

			@Override public int hashCode()
			{
				return Objects.hash(sum, seqInd);
			}
		}

		public Solver(InputReader in, PrintWriter out)
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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
