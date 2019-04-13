package com.codeforces.competitions.year2019.hello2019;

import java.io.*;
import java.util.*;

public class TaskC
{
	public static void main(String[] args)
	{
		new TaskC(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		List<Sequence> valid, invalid, prefixes, suffixes;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			valid = new ArrayList<>();
			invalid = new ArrayList<>();
			prefixes = new ArrayList<>();
			suffixes = new ArrayList<>();

			for (int i = 0; i < n; i++)
			{
				char[] s = in.readLine().toCharArray();
				Sequence seq = new Sequence(s);

				if (isValid(seq))
					valid.add(seq);
				else
				{
					if (checkPref(seq))
						prefixes.add(seq);
					else if (checkSuff(seq))
						suffixes.add(seq);
				}
			}

			TreeMap<Integer, List<Sequence>> map = new TreeMap<>(Integer::compareTo);

			for (Sequence seq : prefixes)
			{
				List<Sequence> list = map.computeIfAbsent(seq.score, k -> new ArrayList<>());

				list.add(seq);
			}

			int ans = valid.size() / 2;

			for (Sequence seq : suffixes)
			{
				List<Sequence> list = map.get(-seq.score);

				if (list == null || list.size() == 0)
					continue;

				list.remove(list.size() - 1);
				ans++;
			}

			out.println(ans);
		}

		boolean isValid(Sequence sequence)
		{
			Stack<Character> stack = new Stack<>();

			for (char ch : sequence.s)
			{
				if (ch == '(')
					stack.add(ch);
				else
				{
					if (stack.size() == 0)
						return false;

					stack.pop();
				}
			}

			return stack.size() == 0;
		}

		boolean checkPref(Sequence sequence)
		{
			Stack<Character> stack = new Stack<>();

			for (char ch : sequence.s)
			{
				if (ch == '(')
					stack.add(ch);
				else
				{
					if (stack.size() == 0)
						return false;

					stack.pop();
				}
			}

			return true;
		}

		boolean checkSuff(Sequence sequence)
		{
			Stack<Character> stack = new Stack<>();

			for (int i = sequence.s.length - 1; i >= 0; i--)
			{
				char ch = sequence.s[i];

				if (ch == ')')
					stack.add(ch);
				else
				{
					if (stack.size() == 0)
						return false;

					stack.pop();
				}
			}

			return true;
		}

		class Sequence
		{
			char[] s;
			int score;

			Sequence(char[] s)
			{
				this.s = s;

				for (char ch : s)
				{
					if (ch == '(')
						score++;
					else
						score--;
				}
			}

		}

		Solver(BufferedReader in, PrintWriter out)
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

	public TaskC(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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

