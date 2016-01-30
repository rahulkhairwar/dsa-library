package com.codechef.competitions.longcompetitions.year2015.february;

import java.util.Scanner;

class LetUsPlayWithRankList
{
	private static int t, n, from, to, max;
	private static long s, sum[];

	public static void main(String[] args)
	{
		from = to = 0;
		sum = new long[100000];
		
		sum[0] = 1;

		getAttributes();
	}

	public static void getAttributes()
	{
		int searchSResult;
		Scanner scanner = new Scanner(System.in);

		t = scanner.nextInt();

		for (int i = 0; i < t; i++)
		{
			n = scanner.nextInt();
			s = scanner.nextLong();

			if (n > max)
				max = n;
			
			sum[n - 1] = (long) (n * (n + 1)) / 2;

			if (s == sum[n - 1])
			{
				System.out.println(0);

				continue;
			}

			if (s == n)
			{
				System.out.println(n - 1);

				continue;
			}

			if ((n - 1) > from)
			{
				to = n - 1;

				createSumArray();

				from = to;
			}
			
			searchSResult = searchS();

			System.out.println(n - searchSResult - 1);
		}

		scanner.close();
	}

	public static void createSumArray()
	{
		for (long i = from + 1; i <= to; i++)
			sum[(int) i] = ((i + 1) * (i + 2)) / 2;
	}

	public static int searchS()
	{
		int l = 0, r = n - 1;
		int middle, temp;

		while (l < r)
		{
			middle = ((r - l) / 2) + l;

			if (s < sum[middle])
			{
				temp = middle - 1;
				
				if (s > sum[temp])
				{
					if ((s - sum[temp]) < (n - temp - 1))
						return backwardSearch(temp - 1);
					else
						return temp;
				}
				else if (s == sum[temp])
					return backwardSearch(temp - 1);
				else
				{
					r = temp;

					continue;
				}
			}
			else if (s == sum[middle])
				return backwardSearch(middle - 1);
			else
			{
				temp = middle + 1;

				if (temp == 100000)
					return backwardSearch(99999);
				
				if (s <= sum[temp])
					return backwardSearch(middle);
				else
				{
					l = temp;

					continue;
				}
			}
		}

		return n - 2;
	}

	public static int backwardSearch(int fromIndex)
	{
		int i = fromIndex;

		while (i >= 0)
		{
			if ((s - sum[i]) < (long) (n - i - 1))
			{
				i--;

				continue;
			}
			else
				return i;
		}

		return 0;
	}

}

/*
 * test cases : edited

4 
1 1
3 6
3 5
3 3


11 
5 15
5 14
5 13
5 12
5 11
5 10
5 9
5 8
5 7
5 6
5 5


46
10 10
10 11
10 12
10 13
10 14
10 15
10 16
10 17
10 18
10 19
10 20
10 21
10 22
10 23
10 24
10 25
10 26
10 27
10 28
10 29
10 30
10 31
10 32
10 33
10 34
10 35
10 36
10 37
10 38
10 39
10 40
10 41
10 42
10 43
10 44
10 45
10 46
10 47
10 48
10 49
10 50
10 51
10 52
10 53
10 54
10 55


10
9858 47595000


10
100000 5000040065
100000 4999948579

102
 */
// (9858 - 102) * (same + 1) / 2 = 47594646
// (9858 - 101) * (same + 1) / 2 = 47604403
