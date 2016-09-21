package com.codeforces.competitions.year2016.round357div2;

import java.io.*;
import java.util.*;

public final class TaskC
{
    public static void main(String[] args)
    {
        OutputWriter out = new OutputWriter(System.out);

		Solver solver = new Solver(out);
		try
		{
			solver.solve(1);
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        OutputWriter out;

        public Solver(OutputWriter out)
        {
        	this.out = out;
        }

		void solve(int test) throws IOException
		{
			n = Integer.parseInt(in.readLine());

			int count = 0;
			StringBuilder answer = new StringBuilder("");

			TreeSet<Integer> treeSet = new TreeSet<>(new Comparator<Integer>()
			{
				@Override public int compare(Integer o1, Integer o2)
				{
					return Integer.compare(o1, o2);
				}
			});

			HashMap<Integer, Integer> map = new HashMap<>();

			for (int i = 0; i < n; i++)
			{
				String line = in.readLine();
				String[] tokens = line.split(" ");

				if (tokens[0].equals("insert"))
				{
					int num = Integer.parseInt(tokens[1]);
					Integer temp = map.get(num);

					answer.append("insert " + num + "\n");

					if (temp == null)
					{
						map.put(num, 1);
						treeSet.add(num);
					}
					else
						map.put(num, temp + 1);
				}
				else if (tokens[0].equals("getMin"))
				{
					int num = Integer.parseInt(tokens[1]);

					if (map.size() == 0)
					{
						answer.append("insert " + num + "\ngetMin " + num + "\n");
						count++;
						map.put(num, 1);
						treeSet.add(num);
					}
					else
					{
						Integer first = treeSet.first();

						while (first < num)
						{
							answer.append("removeMin\n");
							count++;

							Integer temp = map.get(first);

							if (temp == 1)
							{
								map.remove(first);
								treeSet.remove(first);
							}
							else
								map.put(first, temp - 1);

							if (map.size() == 0)
								break;

							first = treeSet.first();
						}

						if (map.size() == 0)
						{
							answer.append("insert " + num + "\n");
							map.put(num, 1);
							treeSet.add(num);
							count++;
							answer.append("getMin " + num + "\n");

							continue;
						}

						if (first == num)
						{
							answer.append("getMin " + num + "\n");

							continue;
						}

						if (first > num)
						{
							answer.append("insert " + num + "\ngetMin " + num + "\n");
							map.put(num, 1);
							treeSet.add(num);
							count++;
						}
					}
				}
				else
				{
					if (map.size() == 0)
					{
						answer.append("insert 1\nremoveMin\n");
						count++;

						continue;
					}

					Integer first = treeSet.first();
					Integer temp = map.get(first);

					if (temp > 1)
						map.put(first, temp - 1);
					else
					{
						map.remove(first);
						treeSet.remove(first);
					}

					answer.append("removeMin\n");
				}
			}

			out.println(n + count);
			out.println(answer.toString());
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
