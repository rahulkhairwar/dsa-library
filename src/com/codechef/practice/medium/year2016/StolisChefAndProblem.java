package com.codechef.practice.medium.year2016;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StolisChefAndProblem
{
	static int[] BIT;
	static int N;

	public static void main(String[] args) throws IOException
	{
		main(System.in);
	}

	public static void main(InputStream stream) throws IOException
	{
		InputReader reader = new InputReader(stream);
		int limit = 500;
		N = reader.readInt();
		int M = reader.readInt();
		int Q = reader.readInt();

		List<Integer>[] itemLists = new List[M + 1];

		for (int m = 0; m < itemLists.length; m++)
			itemLists[m] = new ArrayList<>();

		int[] A = new int[N];

		for (int n = 0; n < N; n++)
		{
			int growth = reader.readInt();

			A[n] = growth;
			itemLists[growth].add(n);
		}

		int largeCount = 0;
		int[][] items = new int[M + 1][];

		for (int m = 0; m < itemLists.length; m++)
		{
			List<Integer> list = itemLists[m];
			int size = list.size();

			if (size >= limit)
				largeCount++;
			else
			{
				int[] array = new int[list.size()];

				for (int i = 0; i < array.length; i++)
					array[i] = list.get(i);

				items[m] = array;
			}
		}

		int[][] minAfter = new int[largeCount][N];
		int[][] maxBefore = new int[largeCount][N];
		int index = 0;

		for (int m = 0; m <= M; m++)
		{
			if (items[m] == null)
			{
				for (int position : itemLists[m])
				{
					minAfter[index][position] = 1;
					maxBefore[index][position] = 1;
				}

				int before = -1;

				for (int n = 0; n < N; n++)
				{
					if (maxBefore[index][n] == 1)
					{
						before = n;
						maxBefore[index][n] = n;
					}
					else
						maxBefore[index][n] = before;
				}

				int after = -1;

				for (int n = N - 1; n >= 0; n--)
				{
					if (minAfter[index][n] == 1)
					{
						after = n;
						minAfter[index][n] = n;
					}
					else
						minAfter[index][n] = after;
				}

				index++;
			}
		}

		Query[] queries = new Query[Q];

		for (int q = 0; q < Q; q++)
		{
			Query query = new Query();

			query.L = reader.readInt() - 1;
			query.R = reader.readInt() - 1;
			query.index = q;
			queries[q] = query;
		}

		Arrays.sort(queries, new Comparator<Query>()
		{
			@Override
			public int compare(Query o1, Query o2)
			{
				return o2.L - o1.L;
			}
		});

		BIT = new int[N + 1];
		int lastInserted = N;
		int[] answers = new int[Q];

		for (Query query : queries)
		{
			int L = query.L;
			int R = query.R;

			while (lastInserted != L)
			{ // i.e. lastInserted > L
				lastInserted--;
				int growth = A[lastInserted];

				if (items[growth] != null)
					for (int position : items[growth])
						if (position > lastInserted)
							set(position + 1, position - lastInserted);
			}

			int answer = read(R + 1);

			for (int i = 0; i < largeCount; i++)
			{
				int after = minAfter[i][L];

				if (after != -1)
				{
					int before = maxBefore[i][R];

					if (before != -1)
						answer = Math.max(answer, before - after);
				}
			}

			answers[query.index] = answer;
		}

		PrintWriter output = new PrintWriter(System.out);

		for (int answer : answers)
			output.println(answer);

		output.close();
	}

	static void set(int index, int value)
	{
		while (index <= N)
		{
			BIT[index] = Math.max(value, BIT[index]);
			index += index & (-index);
		}
	}

	static int read(int index)
	{
		int result = 0;

		while (index > 0)
		{
			result = Math.max(result, BIT[index]);
			index -= index & (-index);
		}

		return result;
	}

	static final class Query
	{
		public int L;
		public int R;
		public int index;
	}

	static final class InputReader
	{
		private final InputStream stream;
		private final byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

		private int read() throws IOException
		{
			if (curChar >= numChars)
			{
				curChar = 0;
				numChars = stream.read(buf);
				if (numChars <= 0)
				{
					return -1;
				}
			}
			return buf[curChar++];
		}

		public final int readInt() throws IOException
		{
			return (int) readLong();
		}

		public final long readLong() throws IOException
		{
			int c = read();
			while (isSpaceChar(c))
			{
				c = read();
				if (c == -1) throw new IOException();
			}
			boolean negative = false;
			if (c == '-')
			{
				negative = true;
				c = read();
			}
			long res = 0;
			do
			{
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return negative ? -res : res;
		}

		public final String readString(int length) throws IOException
		{
			char[] A = new char[length];
			int c = read();
			while (isSpaceChar(c))
			{
				c = read();
				if (c == -1) throw new IOException();
			}
			A[0] = (char) c;
			for (int n = 1; n < length; n++)
			{
				A[n] = (char) read();
			}
			return new String(A);
		}

		public final int[] readIntArray(int size) throws IOException
		{
			int[] array = new int[size];
			for (int i = 0; i < size; i++)
			{
				array[i] = readInt();
			}
			return array;
		}

		public final long[] readLongArray(int size) throws IOException
		{
			long[] array = new long[size];
			for (int i = 0; i < size; i++)
			{
				array[i] = readLong();
			}
			return array;
		}

		private boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	}

}