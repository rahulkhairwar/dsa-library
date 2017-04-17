package com.coursera.algorithms.graphSearch_shortestPaths_dS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class TwoSum
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);

		try
		{
			in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/coursera/algorithms/graphSearch_shortestPaths_dS/twoSumInput.txt"));
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
		int n = (int) 1e6;
		Scanner in;
		PrintWriter out;

		void solve()
		{
			long cnt = 0;
			long[] numbers = new long[n];

			for (int i = 0; i < n; i++)
				numbers[i] = in.nextLong();

			for (int i = (int) -1e4; i <= 1e4; i++)
			{
				cnt += twoSum(numbers, i);

				if (i % 10 == 0)
					System.out.println(i + ", cnt : " + cnt);
			}

			System.out.println("cnt : " + cnt);
		}

		int twoSum2(long[] numbers, long target)
		{
			HashMap<Long, Integer> h = new HashMap<>();
			int cnt = 0;

			for (int i = 0; i < numbers.length; i++)
			{
				long j = target - numbers[i];

				if (j != numbers[i] && h.containsKey(j))
					cnt++;
				else
					h.put(numbers[i], i);
			}

			return cnt;
		}

		int twoSum(long[] nums, int target)
		{
			if (nums == null || nums.length < 2)
				return 0;

			HashMap<Long, Integer> map = new HashMap<>();
			int cnt = 0;

			for (int i = 0; i < nums.length; i++)
			{
				if (map.containsKey(nums[i]))
					cnt++;
				else
					map.put(target - nums[i], i);
			}

			return cnt;
		}

		public Solver(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
