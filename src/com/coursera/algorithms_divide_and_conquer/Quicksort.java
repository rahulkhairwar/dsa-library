package com.coursera.algorithms_divide_and_conquer;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Quicksort
{
	public static void main(String[] args)
	{
		try
		{
			InputReader in = new InputReader(new FileInputStream(new File("/Users/rahulkhairwar/Documents/IntelliJ "
					+ "IDEA Workspace/Competitive Programming/src/com/coursera/algorithms_divide_and_conquer"
					+ "/quicksort.txt")));
			PrintWriter out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int n, cnt;
		int[] arr;
		InputReader in;
		PrintWriter out;

		void solve()
		{
			n = (int) 1e4;
//			n = 100;
			arr = new int[n];

			for (int i = 0; i < n; i++)
				arr[i] = in.nextInt();

//			sort1(arr, 0, n - 1);
			sort2(arr, 0, n - 1);
//			sort3(arr, 0, n - 1);
			System.out.println("sorted array : " + Arrays.toString(arr) + ", total comparisons : " + cnt);
			System.out.println("total comparisons : " + cnt);
		}

		void sort1(int[] arr, int l, int r)
		{
			if (l >= r)
				return;

			cnt += r - l;
//			System.out.println("l : " + l + ", r : " + r);

			int i = l + 1;

			for (int j = i; j <= r; j++)
			{
				if (arr[j] < arr[l])
				{
					int temp = arr[j];

					arr[j] = arr[i];
					arr[i] = temp;
					i++;
				}
			}

			int temp = arr[l];

			arr[l] = arr[i - 1];
			arr[i - 1] = temp;
//			System.out.println("\ti : " + i + ", arr : " + Arrays.toString(arr));
			sort1(arr, l, i - 2);
			sort1(arr, i, r);
		}

		void sort2(int[] arr, int l, int r)
		{
			if (l >= r)
				return;

			cnt += r - l;

			int temp = arr[l];

			arr[l] = arr[r];
			arr[r] = temp;

			int i = l + 1;

			for (int j = i; j <= r; j++)
			{
				if (arr[j] < arr[l])
				{
					temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
					i++;
				}
			}

			temp = arr[l];
			arr[l] = arr[i - 1];
			arr[i - 1] = temp;
//			System.out.println("\ti : " + i + ", arr : " + Arrays.toString(arr));
			sort2(arr, l, i - 2);
			sort2(arr, i, r);
		}

		void sort3(int[] arr, int l, int r)
		{
			if (l >= r)
				return;

			cnt += r - l;

			/*int temp = arr[l];

			arr[l] = arr[r];
			arr[r] = temp;

			int i = l + 1;*/

			int mid = l + ((r - l) >> 1);
			int[] x = new int[]{arr[l], arr[mid], arr[r]};
			int temp;
			int i = l + 1;

			Arrays.sort(x);

			if (x[1] == arr[mid])
			{
				temp = arr[l];
				arr[l] = arr[mid];
				arr[mid] = temp;
			}
			else if (x[1] == arr[r])
			{
				temp = arr[l];
				arr[l] = arr[r];
				arr[r] = temp;
			}

			for (int j = i; j <= r; j++)
			{
				if (arr[j] < arr[l])
				{
					temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
					i++;
				}
			}

			temp = arr[l];
			arr[l] = arr[i - 1];
			arr[i - 1] = temp;
//			System.out.println("\ti : " + i + ", arr : " + Arrays.toString(arr));
			sort3(arr, l, i - 2);
			sort3(arr, i, r);
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
				} catch (IOException e)
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
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

}

/*

5 2 8 3 7 6 1 4 10 9

10 9 8 7 6 5 4 3 2 1

1 2 3 4 5 6 7 8 9 10

*/
