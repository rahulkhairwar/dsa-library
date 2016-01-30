package com.codechef.competitions.longcompetitions.year2015.october;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class WhoDaresToBeAMillionaire
{
	static int t, n;
	static String correct, chef;
	static int winnings[];
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args)
	{
		try
		{
			getAttributes();
		}
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	static void getAttributes() throws NumberFormatException, IOException
	{
		t = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < t; i++)
		{
			n = Integer.parseInt(reader.readLine());
			
			correct = reader.readLine();
			chef = reader.readLine();
			
			winnings = new int[n + 1];
			
			String tok[] = reader.readLine().split(" ");
			
			for (int j = 0; j <= n; j++)
				winnings[j] = Integer.parseInt(tok[j]);
			
			int count = 0;
			
			for (int j = 0; j < n; j++)
				if (correct.charAt(j) == chef.charAt(j))
					count++;
			
			if (count == n)
				System.out.println(winnings[n]);
			else
			{
				int max = winnings[0];
				
				for (int j = 1; j <= count; j++)
					if (winnings[j] > max)
						max = winnings[j];
				
				System.out.println(max);
			}
		}
	}

}

/*

3
5
ABCDE
EBCDA
0 10 20 30 40 50
4
CHEF
QUIZ
4 3 2 1 0
8
ABBABAAB
ABABABAB
100 100 100 100 100 100 100 100 100

1
5
ABHGA
CBGGA
70 0 19 30 50 10

1
5
ABCDE
ABCDE
10 20 30 40 50 60

*/
