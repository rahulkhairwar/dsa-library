package com.codeforces.competitions.year2016.round357div2;

import java.io.*;
import java.util.*;

public final class TaskA
{
    public static void main(String[] args)
    {
        OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(out);
		solver.solve(1);

        out.flush();
        out.close();
    }

    static class Solver
    {
        int n;
		Scanner in = new Scanner(System.in);
        OutputWriter out;

        public Solver(OutputWriter out)
        {
        	this.out = out;
        }

		void solve(int testNumber)
		{
			n = in.nextInt();

			boolean done = false;

			in.nextLine();
			for (int i = 0; i < n; i++)
			{
				String[] tokens = in.nextLine().split(" ");
				int before, after;

				before = Integer.parseInt(tokens[1]);
				after = Integer.parseInt(tokens[2]);

				if (before >= 2400 && after > before)
					done = true;
			}

			if (done)
				out.println("YES");
			else
				out.println("NO");
		}

	}

    static class OutputWriter
    {
        private PrintWriter writer;

        public OutputWriter(OutputStream stream)
        {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    stream)));
        }

        public OutputWriter(Writer writer)
        {
            this.writer = new PrintWriter(writer);
        }

        public void println(int x)
        {
            writer.println(x);
        }

        public void print(int x)
        {
            writer.print(x);
        }

        public void println(int array[], int size)
        {
            for (int i = 0; i < size; i++)
                println(array[i]);
        }

        public void print(int array[], int size)
        {
            for (int i = 0; i < size; i++)
                print(array[i] + " ");
        }

        public void println(long x)
        {
            writer.println(x);
        }

        public void print(long x)
        {
            writer.print(x);
        }

        public void println(long array[], int size)
        {
            for (int i = 0; i < size; i++)
                println(array[i]);
        }

        public void print(long array[], int size)
        {
            for (int i = 0; i < size; i++)
                print(array[i]);
        }

        public void println(float num)
        {
            writer.println(num);
        }

        public void print(float num)
        {
            writer.print(num);
        }

        public void println(double num)
        {
            writer.println(num);
        }

        public void print(double num)
        {
            writer.print(num);
        }

        public void println(String s)
        {
            writer.println(s);
        }

        public void print(String s)
        {
            writer.print(s);
        }

        public void println()
        {
            writer.println();
        }

        public void printSpace()
        {
            writer.print(" ");
        }

        public void flush()
        {
            writer.flush();
        }

        public void close()
        {
            writer.close();
        }

    }

}
