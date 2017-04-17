package com.codeforces.competitions.year2017.round404div2;

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

			n = Integer.parseInt(in.readLine());

			int tot = 0;

			for (int i = 0; i < n; i++)
			{
				String s = in.readLine();

				switch (s)
				{
					case "Tetrahedron":
						tot += 4;
						break;
					case "Cube":
						tot += 6;
						break;
					case "Octahedron":
						tot += 8;
						break;
					case "Dodecahedron":
						tot += 12;
						break;
					case "Icosahedron":
						tot += 20;
						break;
				}
			}

			out.println(tot);
		}

        public Solver(BufferedReader in, PrintWriter out)
        {
        	this.in = in;
        	this.out = out;
        }

	}

}
