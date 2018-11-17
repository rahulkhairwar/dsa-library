package com.codeforces.competitions.educational.year2018.round42;

import java.io.*;
import java.util.*;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, a, b;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			String[] tok = in.readLine().split(" ");

			n = Integer.parseInt(tok[0]);
			a = Integer.parseInt(tok[1]);
			b = Integer.parseInt(tok[2]);

			String[] input = in.readLine().split("\\*");
			int tot = a + b;
			PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());

			for (int i = 0; i < input.length; i++)
			{
				if (input[i].length() == 0)
					continue;

				queue.add(input[i].length());
			}

			while (queue.size() > 0)
			{
				int first = queue.poll();

				if (a > b)
				{
					int temp = a;

					a = b;
					b = temp;
				}

				int half = first / 2;

				if (first % 2 == 1)
				{
					if (a >= half)
					{
						a -= half;
						b -= half;

						if (b > 0)
							b--;
						else
							break;
					}
					else
					{
						b -= a;
						first -= a;
						first -= a;
						first--;
						a = 0;

						if (b > 0)
							b--;
						else
							break;

						if (first > 1)
						{
							while (first > 0 && b > 0)
							{
								first -= 2;
								b--;
							}
						}
					}
				}
				else
				{
					if (a >= half)
					{
						a -= half;
						b -= half;
					}
					else
					{
						b -= a;
						first -= a;
						first -= a;
						a = 0;

						if (first > 1)
						{
							while (first > 0 && b > 0)
							{
								first -= 2;
								b--;
							}
						}
					}
				}
			}

			out.println(tot - (a + b));
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				solve();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	public TaskB(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskB", 1 << 29);

		try
		{
			thread.start();
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			out.flush();
			out.close();
		}
	}

}

