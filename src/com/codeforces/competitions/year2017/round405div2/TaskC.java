package com.codeforces.competitions.year2017.round405div2;

import java.io.*;
import java.util.*;

public final class TaskC
{
    public static void main(String[] args)
    {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		try
		{
			solver.solve();
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

        out.flush();
        out.close();
    }

    static class Solver
    {
        int n, k;
        String[] verdict, names;
        BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			String[] s = in.readLine().split(" ");

			n = Integer.parseInt(s[0]);
			k = Integer.parseInt(s[1]);
			verdict = new String[n - k + 1];
			names = new String[52];

			for (int i = 0; i < 26; i++)
				names[i] = "" + (char) ('A' + i);

			for (int i = 26; i < 52; i++)
				names[i] = "" + (char) ('A' + i - 26) + (char) ('a' + i - 26);

			String[] tok = in.readLine().split(" ");
			System.arraycopy(tok, 0, verdict, 0, n - k + 1);

			for (int i = n - k; i >= 0; i--)
				if (verdict[i].equals("NO"))
					names[i] = names[i + k - 1];

			for (int i = 0; i < n; i++)
				out.print(names[i] + " ");
		}

        public Solver(BufferedReader in, PrintWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

	}

}
