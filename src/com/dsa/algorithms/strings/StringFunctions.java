package com.dsa.algorithms.strings;

public class StringFunctions
{
	/**
	 * A function that computes the <a href="https://cp-algorithms.com/string/z-function.html">Z-Function</a> of a
	 * string.
	 * @param s the string, passed as a character-array.
	 * @return the z-function of the string.
	 */
	public static int[] zFunction(char[] s)
	{
		int len = s.length;
		int[] z = new int[len];

		for (int i = 1, l = 0, r = 0; i < len; i++)
		{
			if (i <= r)
				z[i] = Math.min(r - i + 1, z[i - l]);

			while (i + z[i] < len && s[z[i]] == s[i + z[i]])
				z[i]++;

			if (i + z[i] - 1 > r)
			{
				l = i;
				r = i + z[i] - 1;
			}
		}

		return z;
	}

	public static int[] prefixFunction(char[] s)
	{
		int len = s.length;
		int[] pre = new int[len];
		int i = 1, j = 0;

		while (i < len)
		{
			if (s[i] == s[j])
			{
				pre[i] = j + 1;
				i++;
				j++;
			}
			else
			{
				if (j > 0)
					j = pre[j - 1];
				else
					i++;
			}
		}

		return pre;
	}

	/**
	 * A function that computes the Longest Common Subsequence(LCS) between 2 strings using Dynamic Programming.
	 * <br />Time Complexity : O(n * m), where n and m are the lengths of the 2 strings.
	 * @param a the first string, passed as a character-array.
	 * @param b the second string, passed as a character-array.
	 * @return the LCS, as a string.
	 */
	public static String lCS(char[] a, char[] b)
	{
		int aLength, bLength;

		aLength = a.length;
		bLength = b.length;

		int[][] dp = new int[aLength + 1][bLength + 1];

		for (int i = 1; i <= aLength; i++)
		{
			for (int j = 1; j <= bLength; j++)
			{
				if (a[i - 1] == b[j - 1])
					dp[i][j] = 1 + dp[i - 1][j - 1];
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
			}
		}

		StringBuilder lcs = new StringBuilder();

		for (int i = aLength; i > 0;)
		{
			for (int j = bLength; j > 0;)
			{
				if (i == 0)
					break;

				if (dp[i][j] != dp[i - 1][j] && dp[i][j] != dp[i][j - 1])
				{
					i--;
					j--;
					lcs.append(a[i]);
				}
				else if (dp[i][j] == dp[i - 1][j])
					i--;
				else
					j--;
			}
		}

		return lcs.reverse().toString();
	}

}
