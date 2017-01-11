package com.codeforces.competitions.year2016.round379div2;

import java.io.*;
import java.util.*;

public final class TaskD
{
	public static void main(String[] args)
	{
		try
		{
			OutputWriter out = new OutputWriter(System.out);
			Solver solver = new Solver(out);

			solver.solve();
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		int n, kx, ky;
		Piece[] pieces;
		BufferedReader in;
		OutputWriter out;

		void solve() throws IOException
		{
			in = new BufferedReader(new InputStreamReader(System.in));

			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			tok = in.readLine().split(" ");
			kx = Integer.parseInt(tok[0]);
			ky = Integer.parseInt(tok[1]);
			pieces = new Piece[n];

			Map<Pair, Integer> map = new HashMap<>();
			Set<Pair> valid = new HashSet<>();
			Pair left, right, top, bottom, topRight, topLeft, bottomRight, bottomLeft;

			left = new Pair(-1, 0);
			right = new Pair(1, 0);
			top = new Pair(0, 1);
			bottom = new Pair(0, -1);
			topRight = new Pair(1, 1);
			topLeft = new Pair(-1, 1);
			bottomRight = new Pair(1, -1);
			bottomLeft = new Pair(-1, -1);
			valid.add(left);
			valid.add(right);
			valid.add(top);
			valid.add(bottom);
			valid.add(topRight);
			valid.add(topLeft);
			valid.add(bottomRight);
			valid.add(bottomLeft);

			for (int i = 0; i < n; i++)
			{
				tok = in.readLine().split(" ");

				char ch = tok[0].charAt(0);
				int x, y;

				x = Integer.parseInt(tok[1]);
				y = Integer.parseInt(tok[2]);

				pieces[i] = new Piece(ch, x, y);

				if (!valid.contains(pieces[i].slope))
					continue;

				Integer temp = map.get(pieces[i].slope);

				if (temp == null)
					map.put(pieces[i].slope, i);
				else
				{
					if (pieces[temp].dist > pieces[i].dist)
						map.put(pieces[i].slope, i);
				}
			}

			Iterator<Map.Entry<Pair, Integer>> iterator = map.entrySet().iterator();
			boolean poss = false;

			while (iterator.hasNext())
			{
				Map.Entry<Pair, Integer> entry = iterator.next();
				Pair slope = entry.getKey();
				int pos = entry.getValue();

				if (slope.equals(right) || slope.equals(left) || slope.equals(top) || slope.equals(bottom))
				{
					if (pieces[pos].c == 'R' || pieces[pos].c == 'Q')
						poss = true;
				}

				if (slope.equals(topRight) || slope.equals(topLeft) || slope.equals(bottomRight) || slope.equals
						(bottomLeft))
				{
					if (pieces[pos].c == 'B' || pieces[pos].c == 'Q')
						poss = true;
				}
			}

			if (poss)
				out.println("YES");
			else
				out.println("NO");
		}

		class Pair
		{
			int x, y;

			public Pair(int x, int y)
			{
				this.x = x;
				this.y = y;
			}

			@Override public int hashCode()
			{
				return Objects.hash(x, y);
			}

			@Override public boolean equals(Object obj)
			{
				Pair pair = (Pair) obj;

				return x == pair.x && y == pair.y;
			}

		}

		class Piece
		{
			char c;
			int x, y;
			Pair slope;
			double dist;

			Piece(char c, int x, int y)
			{
				this.c = c;
				this.x = x;
				this.y = y;
				slope = new Pair(kx - x, ky - y);

				if (slope.y != 0)
				{
					int gcd = CMath.gcd(Math.abs(slope.x), Math.abs(slope.y));

					slope.x /= gcd;
					slope.y /= gcd;
				}
				else
				{
					if (slope.x >= 0)
						slope.x = 1;
					else
						slope.x = -1;
				}

				dist = Math.pow(kx - x, 2) + Math.pow(ky - y, 2);
			}

		}

		Solver(OutputWriter out)
		{
			this.out = out;
		}

	}

	private static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

	private static class CMath
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
				max = Math.min(max, arr[i]);

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
				max = Math.min(max, arr[i]);

			return max;
		}

	}

}
