package com.dsa.algorithms.others;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Prime Factorization in O(n log n).√
 */
public class PrimeFactorization
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Solver solver = new Solver(in, out);

		solver.solve();
		in.close();
		out.flush();
		out.close();
	}

	static class Solver
	{
		Scanner in;
		PrintWriter out;

		void solve()
		{
			System.out.println("here");
		}

		public Solver(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
