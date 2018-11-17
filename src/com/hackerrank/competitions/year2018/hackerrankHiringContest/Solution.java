package com.hackerrank.competitions.year2018.hackerrankHiringContest;

import java.util.*;

public class Solution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int max = 0;
		String maxName = null;

		for(int i = 0; i < n; i++){
			String name = in.next();
			int d = in.nextInt();
			int j = in.nextInt();

			if (j - d > max)
			{
				max = j - d;
				maxName = name;
			}
		}

		System.out.println(maxName + " " + max);
		in.close();
	}
}

