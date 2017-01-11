package com.spoj.practice.classic;

import java.io.*;
import java.util.*;

class MediumFactorization
{
    public static void main(String[] args)
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        OutputWriter out = new OutputWriter(System.out);
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
        int[] sp;
		BufferedReader in;
        OutputWriter out;

		void solve() throws IOException
		{
			pre();
			String line;

			while ((line = in.readLine()) != null)
			{
				n = Integer.parseInt(line);
				out.print("1");

				while (n > 1)
				{
					out.print(" x " + sp[n]);
					n /= sp[n];
				}

				out.println();
			}
		}

		void pre()
		{
			sp = new int[(int) 1e7 + 5];

			int curr = 2;

			while (curr <= 1e7)
			{
				sp[curr] = 2;
				curr += 2;
			}

			for (int i = 3; i <= 1e7; i += 2)
			{
				if (sp[i] != 0)
					continue;

				curr = i;

				while (curr <= 1e7)
				{
					if (sp[curr] == 0)
						sp[curr] = i;

					curr += i;
				}
			}
		}

        public Solver(BufferedReader in, OutputWriter out)
        {
        	this.in = in;
        	this.out = out;
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

		public void println(char x)
		{
			writer.println(x);
		}

		public void print(char x)
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

		public void printf(String format, Object args)
		{
			writer.printf(format, args);
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
