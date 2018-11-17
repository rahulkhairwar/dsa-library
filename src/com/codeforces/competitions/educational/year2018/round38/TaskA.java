package com.codeforces.competitions.educational.year2018.round38;

import java.io.*;
import java.util.*;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		String s;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			s = in.readLine();

			Set<Character> vowels = new HashSet<>();

			vowels.add('a');
			vowels.add('e');
			vowels.add('i');
			vowels.add('o');
			vowels.add('u');
			vowels.add('y');

			while (true)
			{
				for (int i = 0; i < s.length() - 1; i++)
				{
					if (vowels.contains(s.charAt(i)) && vowels.contains(s.charAt(i + 1)))
						s = s.substring(0, i + 1) + s.substring(i + 2, s.length());
				}

				boolean vo = false;

				for (int i = 1; i < s.length(); i++)
				{
					if (vowels.contains(s.charAt(i - 1)) && vowels.contains(s.charAt(i)))
					{
						vo = true;

						break;
					}
				}

				if (!vo)
					break;
			}

			out.println(s);
		}

		public Solver(BufferedReader in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override
		public void run()
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

	public TaskA(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

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

