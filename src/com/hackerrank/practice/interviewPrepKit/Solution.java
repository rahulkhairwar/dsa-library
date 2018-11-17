package com.hackerrank.practice.interviewPrepKit;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Solution
{
	static int n;
	static int[][] dp;

	// Complete the maxSubsetSum function below.
	static int maxSubsetSum(int[] arr)
	{
		n = arr.length;
		dp = new int[n][2];

		for (int i = 0; i < n; i++)
			dp[i][0] = dp[i][1] = -1;

/*		dp[0][1] = arr[0];

		for (int i = 1; i < n; i++)
		{
			dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
			dp[i][1] = dp[i - 1][0] + arr[i];
		}*/

		int x = find(arr, n - 1, 0);

//		return Math.max(dp[n - 1][0], dp[n - 1][1]);
		return x;
	}

	static int find(int[] arr, int ind, int nextTaken)
	{
		if (ind == -1)
			return 0;

		if (dp[ind][nextTaken] != -1)
			return dp[ind][nextTaken];

		if (nextTaken == 1)
			return dp[ind][nextTaken] = find(arr, ind - 1, 0);
		else
			return dp[ind][nextTaken] = Math.max(find(arr, ind - 1, 0), arr[ind] + find(arr, ind - 1, 1));
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int n = scanner.nextInt();
		int[] arr = new int[n];

//		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

//		String[] arrItems = scanner.nextLine().split(" ");

//		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

/*		for (int i = 0; i < n; i++)
		{
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}*/

		for (int i = 0; i < n; i++)
			arr[i] = scanner.nextInt();

		int res = maxSubsetSum(arr);

		bufferedWriter.write(String.valueOf(res));
		bufferedWriter.newLine();
		bufferedWriter.close();
		scanner.close();
	}

}

/*

5
3 7 4 6 5

*/
