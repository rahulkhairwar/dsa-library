package com.codeforces.competitions.year2016.intel.elimination;

import java.io.*;
import java.util.*;

public final class TaskB
{
    public static void main(String[] args)
    {
        OutputWriter out = new OutputWriter(System.out);
		Solver solver = new Solver(out);

		try
		{
			solver.solve();
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
        int n, arr[];
        OutputWriter out;

		void solve() throws IOException
		{
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

			n = Integer.parseInt(bf.readLine());
			arr = new int[n];

			String[] t = bf.readLine().split(" ");

			for (int i = 0; i < n; i++)
				arr[i] = Integer.parseInt(t[i]);

			String[][] tok = new String[n][];

			for (int i = 0; i < n; i++)
				tok[i] = bf.readLine().split(" ");

			boolean poss = true;

			for (int i = 0; i < n; i++)
			{
				int len = tok[i].length;
				int parts = 0;

				for (int j = 0; j < len; j++)
					parts += find(tok[i][j]);

				if (parts != arr[i])
					poss = false;
			}

			if (poss)
				out.println("YES");
			else
				out.println("NO");
		}

		int find(String word)
		{
			int[] count = new int[26];
			int len = word.length();

			for (int i = 0; i < len; i++)
				count[word.charAt(i) - 'a']++;

			return count[0] + count['e' - 'a'] + count['i' - 'a'] + count['o' - 'a'] + count['u' - 'a'] +
					count['y' - 'a'];
		}

        public Solver(OutputWriter out)
        {
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
