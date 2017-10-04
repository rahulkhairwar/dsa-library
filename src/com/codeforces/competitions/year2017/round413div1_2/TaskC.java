package com.codeforces.competitions.year2017.round413div1_2;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.TreeSet;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver
	{
		BufferedReader in;
		int n, c, d;
		Fountain[] fountains;
		int[] beauty, price;
		boolean[] throughCoins;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			c = Integer.parseInt(tok[1]);
			d = Integer.parseInt(tok[2]);
			fountains = new Fountain[n];
			beauty = new int[n];
			price = new int[n];
			throughCoins = new boolean[n];

			int coinFountainCnt = 0;
			int diamondFountainCnt = 0;

			for (int i = 0; i < n; i++)
			{
				tok = in.readLine().split(" ");

				fountains[i] =
						new Fountain(i, Integer.parseInt(tok[0]), Integer.parseInt(tok[1]), tok[2].charAt(0) == 'C');

				if (fountains[i].throughCoins)
					coinFountainCnt++;
				else
					diamondFountainCnt++;
			}

			out.println(CMath.max(combined(), coins(coinFountainCnt), diamonds(diamondFountainCnt)));
		}

		int coins(int coinFountainCnt)
		{
			if (coinFountainCnt == 0)
				return 0;

			Fountain[] coinFountains = new Fountain[coinFountainCnt];

			for (int i = 0, j = 0; i < n; i++)
			{
				if (fountains[i].throughCoins)
					coinFountains[j++] = fountains[i];
			}

			Comparator<Fountain> comparator = new Comparator<Fountain>()
			{
				@Override public int compare(Fountain o1, Fountain o2)
				{
					if (o1.price == o2.price)
					{
						if (o1.beauty == o2.beauty)
							return Integer.compare(o1.ind, o2.ind);

						return Integer.compare(o2.beauty, o1.beauty);
					}

					return Integer.compare(o1.price, o2.price);
				}
			};

			Comparator<Fountain> beautyComparator = new Comparator<Fountain>()
			{
				@Override public int compare(Fountain o1, Fountain o2)
				{
					if (o1.beauty == o2.beauty)
					{
						if (o1.price == o2.price)
							return Integer.compare(o1.ind, o2.ind);

						return Integer.compare(o1.price, o2.price);
					}

					return Integer.compare(o2.beauty, o1.beauty);
				}
			};

			Arrays.sort(coinFountains, comparator);

			if (coinFountains[0].price > c)
				return 0;

			int max = 0;
			int right = 1;
			TreeSet<Fountain> set = new TreeSet<>(beautyComparator);

			while (right < coinFountainCnt && coinFountains[0].price + coinFountains[right].price <= c)
				set.add(coinFountains[right++]);

			if (set.size() == 0)
				return 0;

			Fountain fountain = set.first();

			if (fountain != null)
				max = Math.max(max, coinFountains[0].beauty + fountain.beauty);

			right--;

			for (int i = 1; i < coinFountainCnt; i++)
			{
				set.remove(coinFountains[i]);

				while (coinFountains[i].price + coinFountains[right].price > c)
				{
					if (right == i)
						break;

					set.remove(coinFountains[right]);
					right--;
				}

				if (i == right)
					break;

				max = Math.max(max, coinFountains[i].beauty + set.first().beauty);
			}

			return max;
		}

		int diamonds(int diamondFountainCnt)
		{
			if (diamondFountainCnt == 0)
				return 0;

			Fountain[] diamondFountains = new Fountain[diamondFountainCnt];

			for (int i = 0, j = 0; i < n; i++)
			{
				if (!fountains[i].throughCoins)
					diamondFountains[j++] = fountains[i];
			}

			Comparator<Fountain> comparator = new Comparator<Fountain>()
			{
				@Override public int compare(Fountain o1, Fountain o2)
				{
					if (o1.price == o2.price)
					{
						if (o1.beauty == o2.beauty)
							return Integer.compare(o1.ind, o2.ind);

						return Integer.compare(o2.beauty, o1.beauty);
					}

					return Integer.compare(o1.price, o2.price);
				}
			};

			Comparator<Fountain> beautyComparator = new Comparator<Fountain>()
			{
				@Override public int compare(Fountain o1, Fountain o2)
				{
					if (o1.beauty == o2.beauty)
					{
						if (o1.price == o2.price)
							return Integer.compare(o1.ind, o2.ind);

						return Integer.compare(o1.price, o2.price);
					}

					return Integer.compare(o2.beauty, o1.beauty);
				}
			};

			Arrays.sort(diamondFountains, comparator);

			if (diamondFountains[0].price > d)
				return 0;

			int max = 0;
			int right = 1;
			TreeSet<Fountain> set = new TreeSet<>(beautyComparator);

			while (right < diamondFountainCnt && diamondFountains[0].price + diamondFountains[right].price <= d)
				set.add(diamondFountains[right++]);

			if (set.size() == 0)
				return 0;

			Fountain fountain = set.first();

			if (fountain != null)
				max = Math.max(max, diamondFountains[0].beauty + fountain.beauty);

			right--;

			for (int i = 1; i < diamondFountainCnt; i++)
			{
				set.remove(diamondFountains[i]);

				while (diamondFountains[i].price + diamondFountains[right].price > d)
				{
					if (right == i)
						break;

					set.remove(diamondFountains[right]);
					right--;
				}

				if (i == right)
					break;

				max = Math.max(max, diamondFountains[i].beauty + set.first().beauty);
			}

			return max;
		}

		int combined()
		{
			int coinsMax = 0;
			int diamondsMax = 0;

			for (int i = 0; i < n; i++)
			{
				if (fountains[i].throughCoins && fountains[i].price <= c)
					coinsMax = Math.max(coinsMax, fountains[i].beauty);

				if (!fountains[i].throughCoins && fountains[i].price <= d)
					diamondsMax = Math.max(diamondsMax, fountains[i].beauty);
			}

			if (coinsMax == 0 || diamondsMax == 0)
				return 0;

			return coinsMax + diamondsMax;
		}

		class Fountain
		{
			int ind, beauty, price;
			boolean throughCoins;

			public Fountain(int ind, int beauty, int price, boolean throughCoins)
			{
				this.ind = ind;
				this.beauty = beauty;
				this.price = price;
				this.throughCoins = throughCoins;
			}

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

	static class CMath
	{
		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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

