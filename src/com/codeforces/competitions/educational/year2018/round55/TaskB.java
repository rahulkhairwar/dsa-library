package com.codeforces.competitions.educational.year2018.round55;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class TaskB
{
	public static void main(String[] args)
	{
		new TaskB(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		static final int GOLD = 1;
		static final int SILVER = 0;
		int n;
		char[] s;
		List<Point> subs;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			s = in.readLine().toCharArray();
			subs = new ArrayList<>();

			int left = 0;
			int right = 0;
			int max = 0;
			int totalGold = 0;

			for (char ch : s)
			{
				if (ch == 'G')
					totalGold++;
			}

			while (left < n)
			{
				char type = s[left];

				while (right < n && s[right] == type)
					right++;

				right--;
				subs.add(new Point(type == 'G' ? GOLD : SILVER, right - left + 1));

				if (type == 'G')
					max = Math.max(max, right - left + 1);

				right++;
				left = right;
			}

			int i;

			if (subs.get(0).x == GOLD)
				i = 0;
			else
				i = 1;

			for (int j = i + 2; i < subs.size() && j < subs.size(); i += 2, j += 2)
			{
				int leftSize = subs.get(i).y;
				int rightSize = subs.get(j).y;

				if (subs.get(i + 1).y == 1)
				{
					int extra = (totalGold - leftSize - rightSize);

					if (extra > 0)
						extra = 1;

					max = Math.max(max, leftSize + extra + rightSize);
				}
				else
				{
					max = Math.max(max, leftSize + 1);
					max = Math.max(max, rightSize + 1);
				}
			}

			out.println(max);
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

/*

10
GGGSSGGGGG

*/
