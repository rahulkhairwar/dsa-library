package com.codeforces.competitions.year2017.round451div2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		long[] arr;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			arr = in.nextLongArray(n);

			TreeSet<Long> set = new TreeSet<>(Long::compareTo);

			for (int i = 0; i <= 1e5; i++)
				set.add((long) i * i);

			int sq = 0;
			int nSq = 0;
			boolean[] isSquare = new boolean[n];

			for (int i = 0; i < n; i++)
			{
				long sqrt = (long) Math.sqrt(arr[i]);

				if (sqrt * sqrt == arr[i])
				{
					sq++;
					isSquare[i] = true;
				}
				else
					nSq++;
			}

			if (sq == nSq)
				out.println(0);
			else
			{
				if (nSq < sq)
				{
					List<Long> list = new ArrayList<>();
					long moves = 0;

					for (int i = 0; i < n; i++)
					{
						if (isSquare[i])
							list.add(arr[i]);
					}

					Collections.sort(list);

					int curr = list.size() - 1;

					while (nSq < sq)
					{
						if (list.get(curr) > 0)
							moves++;
						else
							moves += 2;

						nSq++;
						sq--;
						curr--;
					}

					out.println(moves);

					return;
				}

				long moves = 0;
				List<Long> list = new ArrayList<>();

				for (int i = 0; i < n; i++)
				{
					if (isSquare[i])
						continue;

					list.add(Math.min(arr[i] - set.headSet(arr[i]).last(), set.tailSet(arr[i]).first() - arr[i]));
				}

				Collections.sort(list);

				int curr = 0;

				while (sq < nSq)
				{
					moves += list.get(curr);
					sq++;
					nSq--;
					curr++;
				}

				out.println(moves);
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

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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

