package com.hackerrank.competitions.year2018.hackerrankHiringContest;

import java.util.*;

public class WinningLotteryTicket
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		String[] tickets = new String[n];

		for (int tickets_i = 0; tickets_i < n; tickets_i++)
			tickets[tickets_i] = in.next();

		long result = winningLotteryTicket(tickets);

		System.out.println(result);
		in.close();
	}

	static long winningLotteryTicket(String[] tickets)
	{
		int n = tickets.length;
		long[] cnt = new long[1 << 10];

		for (int i = 0; i < n; i++)
		{
			int mask = 0;

			for (char ch : tickets[i].toCharArray())
			{
				int dig = ch - '0';

				mask |= (1 << dig);
			}

			cnt[mask]++;
		}

		long ans = cnt[(1 << 10) - 1] * (cnt[(1 << 10) - 1] - 1) / 2;

		for (int i = 0; i < (1 << 10); i++)
		{
			if (cnt[i] == 0)
				continue;

			for (int j = i + 1; j < (1 << 10); j++)
			{
				if (cnt[j] == 0)
					continue;

				int mask = i | j;

				if (mask == (1 << 10) - 1)
					ans += cnt[i] * cnt[j];
			}
		}

		return ans;
	}

}
