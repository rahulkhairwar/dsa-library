package com.campusplacements.directi.round1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class First
{
	static int t, n;
	static InputReader in = new InputReader();
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			String a, b;
			
			a = in.in.readLine();
			b = in.in.readLine();
			
			int min = 10000;
			
			for (int i = 0; i <= (1 << n); i++)
			{
				String bin = Integer.toBinaryString(i);
				
				min = Math.min(min, find(a, b, bin));
			}
			
			System.out.println(min);
		}

		in.in.close();
	}
	
	static int find(String a, String b, String bin)
	{
		int len = bin.length();
		StringBuilder x, y;
		
		x = new StringBuilder(a);
		y = new StringBuilder(b);
		
		for (int i = 0; i < len; i++)
		{
			if (bin.charAt(i) == '1')
			{
				 char temp = x.charAt(i);
				 
				 x.setCharAt(i, y.charAt(i));
				 y.setCharAt(i, temp);
			}
		}
		
		return Math.max(counter(x.toString()), counter(y.toString()));
	}
	
	static int counter(String s)
	{
		int len = s.length();
		int[] count = new int[26];
		
		int x = 0;
		
		for (int i = 0; i < len; i++)
		{
			count[s.charAt(i) - 'a']++;

			if (count[s.charAt(i) - 'a'] == 1)
				x++;
		}
		
		return x;
	}

	static class InputReader
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int nextInt() throws NumberFormatException, IOException
		{
			return Integer.parseInt(in.readLine());
		}
		
		int nextInt(String s)
		{
			return Integer.parseInt(s);
		}
		
		long nextLong(String s)
		{
			return Long.parseLong(s);
		}
		
	}

}
