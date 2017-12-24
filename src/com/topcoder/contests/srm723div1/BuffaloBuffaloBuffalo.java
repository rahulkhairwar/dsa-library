package com.topcoder.contests.srm723div1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BuffaloBuffaloBuffalo
{
	private static final int MOD = (int) 1e9 + 7;
	private int n, cnt;
	private char[] s;
	private long[][][][][][] dp;
	private Set<Character> set;
	private boolean valid;

	public int count(String pattern)
	{
		s = pattern.toCharArray();
		n = s.length;

		if (n % 7 > 0)
			return 0;

		cnt = n / 7;
		dp = new long[cnt + 1][cnt + 1][2 * cnt + 1][cnt + 1][cnt + 1][cnt + 1];
		set = new HashSet<>();
		set.add('b');
		set.add('u');
		set.add('f');
		set.add('a');
		set.add('l');
		set.add('o');
		set.add('?');
		valid = true;

		for (int i = 0; i <= cnt; i++)
			for (int j = 0; j <= cnt; j++)
				for (int k = 0; k <= 2 * cnt; k++)
					for (int l = 0; l <= cnt; l++)
						for (int m = 0; m <= cnt; m++)
							Arrays.fill(dp[i][j][k][l][m], -1);

		int x = (int) find(cnt, cnt, cnt << 1, cnt, cnt, cnt);

		if (!valid)
			return 0;

		return x;
	}

	public long find(int b, int u, int f, int a, int l, int o)
	{
		int pos = n - (b + u + f + a + l + o);
		int uUsed = cnt - u;
		int fUsed = 2 * cnt - f;
		int aUsed = cnt - a;

		if (uUsed <= fUsed / 2 && fUsed % 2 == 1)
			return dp[b][u][f][a][l][o] = 0;

		if (fUsed / 2 < aUsed && fUsed % 2 == 1)
			return dp[b][u][f][a][l][o] = 0;

		if (pos == n)
			return 1;

		if (!set.contains(s[pos]))
		{
			valid = false;

			return dp[b][u][f][a][l][o] = 0;
		}

		if (dp[b][u][f][a][l][o] != -1)
			return dp[b][u][f][a][l][o];

		switch (s[pos])
		{
			case 'b':
				return dp[b][u][f][a][l][o] = b > 0 ? find(b - 1, u, f, a, l, o) : 0;
			case 'u':
				return dp[b][u][f][a][l][o] = u > 0 && u > b ? find(b, u - 1, f, a, l, o) : 0;
			case 'f':
				return dp[b][u][f][a][l][o] = f > 0 ? find(b, u, f - 1, a, l, o) : 0;
			case 'a':
				return dp[b][u][f][a][l][o] = a > 0 ? find(b, u, f, a - 1, l, o) : 0;
			case 'l':
				return dp[b][u][f][a][l][o] = l > 0 && l > a ? find(b, u, f, a, l - 1, o) : 0;
			case 'o':
				return dp[b][u][f][a][l][o] = o > 0 && o > l ? find(b, u, f, a, l, o - 1) : 0;
			default:
				break;
		}

		long ans = 0;

		if (b > 0)
			ans = CMath.mod(ans + find(b - 1, u, f, a, l, o), MOD);

		if (u > 0 && u > b)
			ans = CMath.mod(ans + find(b, u - 1, f, a, l, o), MOD);

		if (f > 0 && f >= u * 2)
			ans = CMath.mod(ans + find(b, u, f - 1, a, l, o), MOD);

		if (a > 0)
			ans = CMath.mod(ans + find(b, u, f, a - 1, l, o), MOD);

		if (l > 0 && l > a)
			ans = CMath.mod(ans + find(b, u, f, a, l - 1, o), MOD);

		if (o > 0 && o > l)
			ans = CMath.mod(ans + find(b, u, f, a, l, o - 1), MOD);

		return dp[b][u][f][a][l][o] = ans;
	}

	static class CMath
	{
		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

	}

}
