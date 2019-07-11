package com.a2oj.ladder.CodeforcesDiv2C;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class Problem27
{
	public static void main(String[] args)
	{
		try
		{
			InputReader in = new InputReader();
			PrintWriter out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();

			in.close();
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
		int n;
		InputReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = in.nextInt();
			Point[] points = new Point[n];

			for (int i = 0; i < n; i++)
			{
				String[] tokens = in.readLine().split(" ");

				points[i] = new Point(in.nextInt(tokens[0]), in.nextInt(tokens[1]));
			}

			Arrays.sort(points, new Comparator<Point>()
			{
				@Override public int compare(Point o1, Point o2)
				{
					if (o1.y == o2.y)
						return Integer.compare(o2.x, o1.x);

					return Integer.compare(o1.y, o2.y);
				}
			});

			int count = 0;
			int minX, maxY;

			minX = points[n - 1].x;
			maxY = points[n - 1].y;

			for (int i = n - 2; i >= 0; i--)
			{
				if (points[i].y < maxY && points[i].x > minX)
					count++;

				if (points[i].x < minX)
				{
					minX = points[i].x;
					maxY = points[i].y;
				}
			}

			out.println(count);
		}

		public Solver(InputReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

	static class InputReader
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String readLine() throws IOException
		{
			return in.readLine();
		}

		int nextInt() throws IOException
		{
			return Integer.parseInt(in.readLine());
		}

		int nextInt(String s) throws IOException
		{
			return Integer.parseInt(s);
		}

		long nextLong() throws IOException
		{
			return Long.parseLong(in.readLine());
		}

		long nextLong(String s) throws IOException
		{
			return Long.parseLong(s);
		}

		void close() throws IOException
		{
			in.close();
		}

	}

}

/*

4
1 5
2 4
10 15
11 14

*/
