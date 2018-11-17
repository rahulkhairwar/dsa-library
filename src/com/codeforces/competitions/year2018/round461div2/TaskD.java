package com.codeforces.competitions.year2018.round461div2;

import java.io.*;
import java.util.Arrays;

public class TaskD
{
	public static void main(String[] args)
	{
		new TaskD(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		Temp[] temps;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			temps = new Temp[n];

			for (int i = 0; i < n; i++)
			{
				String str = in.readLine();
				int s = 0;
				int h = 0;

				for (int j = 0; j < str.length(); j++)
				{
					if (str.charAt(j) == 's')
						s++;
					else
						h++;
				}

				temps[i] = new Temp(str, s, h);
			}

			Arrays.sort(temps, (o1, o2) -> {
				long a = o1.s * o2.h;
				long b = o2.s * o1.h;

				return Long.compare(b, a);
			});

			long cnt = 0;
			int s = 0;

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < temps[i].string.length(); j++)
				{
					if (temps[i].string.charAt(j) == 's')
						s++;
					else
						cnt += s;
				}
			}

			out.println(cnt);
		}

		class Temp
		{
			String string;
			long s, h;

			Temp(String string, long s, long h)
			{
				this.string = string;
				this.s = s;
				this.h = h;
			}

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

	public TaskD(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

