package com.codechef.competitions.longcompetitions.year2016.october;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BruteSolver
{
	static int t, n;
	static String a, b, c;

	public static void main(String[] args)
	{
		try
		{
			solve();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static void solve() throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/codechef/competitions/longcompetitions/year2016/october/input.txt"));
		PrintWriter out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA "
				+ "Workspace/Competitive Programming/src/com/codechef/competitions/longcompetitions/year2016"
				+ "/october/bruteOutput.txt"));

		t = in.nextInt();

		while (t-- > 0)
		{
			a = in.next();
			b = in.next();
			c = in.next();
			n = in.nextInt();

			out.println(find());
		}

		out.flush();
		out.close();
	}

	static int find()
	{
		StringBuilder l = new StringBuilder(a);

		for (int i = 0; i < n; i++)
			l.append(b);

		l.append(c);

		return count(l);
	}

	static int count(StringBuilder l)
	{
		int len = l.length();
		int i = len - 1;

		while (i >= 0 && l.charAt(i) == '1')
			i--;

		if (i == -1)
			return 1;

		while (i >= 0 && l.charAt(i) == '0')
			i--;

		if (i == -1)
			return 1;

		l.setCharAt(i, '0');

		for (int j = i + 1; j < len; j++)
			if (l.charAt(j) == '0')
				l.setCharAt(j, '1');

		return 1 + count(l);
	}

}
