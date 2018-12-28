package com.codeforces.competitions.year2018.round529div3;

import java.io.*;
import java.util.*;

public class TaskE
{
	public static void main(String[] args)
	{
		new TaskE(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		char[] s;
		Count[] cnt;
		boolean[] isValid;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());
			s = in.readLine().toCharArray();
			cnt = new Count[n];
			isValid = new boolean[n];

			if (s[0] == ')' && s[n - 1] == '(')
			{
				out.println(0);

				return;
			}

			int opening = 0;
			int closing = 0;
			boolean poss = true;

			for (int i = 0; i < n; i++)
			{
				char ch = s[i];

				if (ch == '(')
					opening++;
				else
					closing++;

				if (closing - opening > 2)
				{
					poss = false;

					break;
				}
			}

			if (!poss)
			{
				out.println(0);

				return;
			}

			Stack<Character> stack = new Stack<>();

			opening = closing = 0;

			for (int i = n - 1; i >= 0; i--)
			{
				char ch = s[i];

				if (ch == '(')
				{
					opening++;

					if (!stack.isEmpty())
					{
						stack.pop();

						if (stack.size() == 0)
							isValid[i] = true;
					}
				}
				else
				{
					closing++;
					stack.push(ch);
				}

				cnt[i] = new Count(opening, closing);
			}

			if (opening == closing + 2)    // change 1 ( to )
			{
			}
			else if (opening + 2 == closing)    // change 1 ) to (
			{
				int ind = -1;

				for (int i = 0; i + 2 < n; i++)
				{
					if (s[i] == ')' && s[i + 1] == ')' && s[i + 2] == ')')
					{
						if (ind != -1)
						{
							poss = false;

							break;
						}

						ind = i;
					}
				}

				if (!poss)
				{
					out.println("NO");

					return;
				}

				s[ind] = '(';

				if (possible(s))
				{
					out.println(1);

					return;
				}

				s[ind] = ')';
				s[ind + 1] = '(';

				if (possible(s))
				{
					out.println(1);

					return;
				}

				s[ind + 1] = ')';
				s[ind + 2] = '(';

				if (possible(s))
				{
					out.println(1);

					return;
				}

				out.println(0);
			}
			else
			{
				out.println(0);
			}
		}

		boolean possible(char[] s)
		{
			return false;
		}

		class Count
		{
			int opening, closing;

			Count(int opening, int closing)
			{
				this.opening = opening;
				this.closing = closing;
			}

		}

		void debug(Object... o)
		{
			System.err.println(Arrays.deepToString(o));
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

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;
			else
				return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

	}

	static class Utils
	{
		static boolean nextPermutation(int[] arr)
		{
			for (int a = arr.length - 2; a >= 0; --a)
			{
				if (arr[a] < arr[a + 1])
				{
					for (int b = arr.length - 1; ; --b)
					{
						if (arr[b] > arr[a])
						{
							int t = arr[a];

							arr[a] = arr[b];
							arr[b] = t;

							for (++a, b = arr.length - 1; a < b; ++a, --b)
							{
								t = arr[a];
								arr[a] = arr[b];
								arr[b] = t;
							}

							return true;
						}
					}
				}
			}

			return false;
		}

	}

	public TaskE(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskE", 1 << 29);

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

