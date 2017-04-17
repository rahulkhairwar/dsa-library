package com.codeforces.competitions.year2017.round400div1_2;

import java.io.*;

public final class TaskA
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
        int n;
		BufferedReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			String[] s = in.readLine().split(" ");
			n = Integer.parseInt(in.readLine());

			for (int i = 0; i < n; i++)
			{
				out.println(s[0] + " " + s[1]);

				String[] tok = in.readLine().split(" ");

				if (s[0].equals(tok[0]))
					s[0] = tok[1];
				else
					s[1] = tok[1];
			}

			out.println(s[0] + " " + s[1]);
		}

        public Solver(BufferedReader in, PrintWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

	}

}
