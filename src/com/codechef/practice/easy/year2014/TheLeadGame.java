package com.codechef.practice.easy.year2014;

import java.util.Scanner;

class TheLeadGame 
{
	public static int n, w;
	public static long s = 0, t = 0, l;
	
	public static void main(String[] args) 
	{
		l = 0;
		
		start();
	}
	
	public static void start()
	{
		Scanner scanner = new Scanner(System.in);

		n = scanner.nextInt();

		for (int i = 0; i < n; i++)
		{
			s += (long) scanner.nextInt();
			t += (long) scanner.nextInt();

			if ((Math.max(s, t) == s) && ((s - t) > l))
			{
				w = 1;
				l = s - t;
			}

			else if (Math.max(s, t) == t && (t - s) > l)
			{
				w = 2;
				l = t - s;
			}
		}
		
		scanner.close();
		System.out.println(w + " " + l);
	}

}



/*
 * Test Cases
 * 
 * 
5
140 82
89 134
90 110
112 106
88 90

5
82 140
89 134
90 110
112 106
88 90

1
99 100
 */
