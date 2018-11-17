package com.codeforces.competitions.educational.year2018.round42;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static long INF = (long) 1e15;
		int n;
		List<Integer> bite, ber, common;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			bite = new ArrayList<>();
			ber = new ArrayList<>();
			common = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				tok = in.readLine().split(" ");

				if (tok[1].charAt(0) == 'B')
					bite.add(Integer.parseInt(tok[0]));
				else if (tok[1].charAt(0) == 'R')
					ber.add(Integer.parseInt(tok[0]));
				else
					common.add(Integer.parseInt(tok[0]));
			}

/*			Collections.sort(bite);
			Collections.sort(ber);
			Collections.sort(common);*/


			long cost = 0;
			int biteSize = bite.size();
			int berSize = ber.size();
			int commonSize = common.size();
			Point[] pts = new Point[biteSize + commonSize];
			int ctr = 0;

			for (int i = 0; i < biteSize; i++)
				pts[ctr++] = new Point(bite.get(i), 1);

			for (int i = 0; i < commonSize; i++)
				pts[ctr++] = new Point(common.get(i), 2);

			Arrays.sort(pts, Comparator.comparingInt(a -> a.x));

			cost += closestCommon(pts);
			pts = new Point[berSize + commonSize];
			ctr = 0;

			for (int i = 0; i < berSize; i++)
				pts[ctr++] = new Point(ber.get(i), 1);

			for (int i = 0; i < commonSize; i++)
				pts[ctr++] = new Point(common.get(i), 2);

			Arrays.sort(pts, Comparator.comparingInt(a -> a.x));

			cost += closestCommon(pts);

			out.println(cost);

/*			if (commonSize > 1)
			{
				int lastCommon = common.get(commonSize - 1);

				cost += lastCommon - common.get(0);

				if (biteSize > 0)
				{
					int firstBite = bite.get(0);
					int lastBite = bite.get(biteSize - 1);

					if (firstBite < common.get(0))
						cost += common.get(0) - firstBite;

					if (lastBite > lastCommon)
						cost += lastBite - lastCommon;
				}

				if (berSize > 0)
				{
					int firstBer = ber.get(0);
					int lastBer = ber.get(berSize - 1);

					if (firstBer < common.get(0))
						cost += common.get(0) - firstBer;

					if (lastBer > lastCommon)
						cost += lastBer - lastCommon;
				}
			}
			else
			{
				if (biteSize > 1)
					cost += bite.get(biteSize - 1) - bite.get(0);

				if (berSize > 1)
					cost += ber.get(berSize - 1) - ber.get(0);

				out.println(cost);
			}*/
		}

		long closestCommon(Point[] pts)
		{
			int len = pts.length;
			long min = INF;

			for (int i = 0; i < len; i++)
			{
				if (pts[i].y == 2)
				{
					long left = (i > 0 ? pts[i - 1].x : -INF);
					long right = (i < len - 1 ? pts[i + 1].x : INF);

					System.out.println("common at x : " + pts[i].x + ", lt : " + left + ", rt : " + right);

					min = CMath.min(min, pts[i].x - left, right - pts[i].x);
				}
			}

			System.out.println("min : " + min);

			if (min < 1e11)
				return min;

			return 0;
		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
		}

		public Solver(BufferedReader in, PrintWriter out)
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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
			try
			{
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

}

