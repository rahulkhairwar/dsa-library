package com.campusplacements.directi.round1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Second
{
	static int t, n, m, q, parent[];
	static long[] add, addUp, sub;
	static InputReader in = new InputReader();
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		t = in.nextInt();
		
		long mod = (long) (1e9 + 7);
		
		while (t-- > 0)
		{
			String[] tokens = in.in.readLine().split(" ");
			
			n = in.nextInt(tokens[0]);
			m = in.nextInt(tokens[1]);
			q = in.nextInt(tokens[2]);
			parent = new int[n + 1];
			add = new long[n + 1];
			addUp = new long[n + 1];
			sub = new long[n + 1];
			
			for (int i = 1; i <= n; i++)
				parent[i] = in.nextInt();

			for (int i = 0; i < m; i++)
			{
				tokens = in.in.readLine().split(" ");

				int x, y;
				
				x = in.nextInt(tokens[1]);
				y = in.nextInt(tokens[2]);
				
				if (tokens[0].length() == 3)
					add[x] += y;
				else
					addUp[x] += y;
			}
			
			for (int i = n; i > 0; i--)
			{
				sub[i] += add[i];

				long temp = addUp[i];
				int par = parent[i];
				
				sub[i] += temp;
				sub[par] += sub[i];
				
				if (temp == 0)
					continue;
				
				add[i] += temp;
				addUp[i] = 0;
				addUp[par] += temp;
			}
			
			for (int i = 0; i < q; i++)
			{
				tokens = in.in.readLine().split(" ");
				
				int x = in.nextInt(tokens[1]);
				
				if (tokens[0].length() == 3)
					System.out.println(add[x] % mod);
				else
					System.out.println(sub[x] % mod);
			}
		}
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
		
	}

}
