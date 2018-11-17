package com.codeforces.competitions.year2017.round451div2;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TaskF
{
	public static void main(String[] args)
	{
		new TaskF(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n;
		String s;
		char[] t;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			s = in.readLine();
			t = s.toCharArray();
			n = s.length();

			// len[a] = len[b] = len[c].
			int len = n / 3;

//			System.out.println("n : " + n + ", len : " + len);

			if (3 * len == n)
			{
				int cStart = len << 1;
				BigInteger left = new BigInteger("" + s.charAt(0));
				BigInteger right = new BigInteger("" + s.charAt(cStart - 1));
				BigInteger tot = new BigInteger(s.substring(cStart, n));
				BigInteger powers = BigInteger.TEN;

				for (int i = 1; i < cStart - 1; i++)
					left = left.multiply(BigInteger.TEN).add(new BigInteger("" + s.charAt(i)));

//				System.out.println("left : " + left + ", right : " + right);

				if (left.add(right).equals(tot))
				{
					out.println(left + "+" + right + "=" + tot);

					return;
				}

				for (int i = cStart - 2; i >= 0; i--)
				{
//					System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);
					if (left.add(right).equals(tot))
					{
//						print(i, cStart);
						out.print(left + "+" + right + "=" + tot);

						return;
					}

					left = left.divide(BigInteger.TEN);
					right = powers.multiply(new BigInteger("" + s.charAt(i))).add(right);
					powers = powers.multiply(BigInteger.TEN);
				}

				cStart--;
				left = new BigInteger("" + s.charAt(0));
				right = new BigInteger("" + s.charAt(cStart - 1));
				tot = new BigInteger(s.substring(cStart, n));
				powers = BigInteger.TEN;

				for (int i = 1; i < cStart - 1; i++)
					left = left.multiply(BigInteger.TEN).add(new BigInteger("" + s.charAt(i)));

//				System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);

				if (left.add(right).equals(tot))
				{
					out.println(left + "+" + right + "=" + tot);

					return;
				}

//				System.out.println("cS : " + cStart);
				for (int i = cStart - 2; i >= 0; i--)
				{
//					System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);
					if (left.add(right).equals(tot))
					{
//						print(i, cStart);
						out.print(left + "+" + right + "=" + tot);

						return;
					}

					left = left.divide(BigInteger.TEN);
					right = powers.multiply(new BigInteger("" + s.charAt(i))).add(right);
					powers = powers.multiply(BigInteger.TEN);
				}
			}
			else
			{
				int cStart = n - (len + 1);
				BigInteger left = new BigInteger("" + s.charAt(0));
				BigInteger right = new BigInteger("" + s.charAt(cStart - 1));
				BigInteger tot = new BigInteger(s.substring(cStart, n));
				BigInteger powers = BigInteger.TEN;

				for (int i = 1; i < cStart - 1; i++)
					left = left.multiply(BigInteger.TEN).add(new BigInteger("" + s.charAt(i)));

//				System.out.println("cStart : " + cStart);
//				System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);

				if (left.add(right).equals(tot))
				{
					out.print(left + "+" + right + "=" + tot);

					return;
				}

				for (int i = cStart - 2; i >= 0; i--)
				{
//					System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);
					if (left.add(right).equals(tot))
					{
//						print(i + 1, cStart);
						out.print(left + "+" + right + "=" + tot);

						return;
					}

					left = left.divide(BigInteger.TEN);
					right = powers.multiply(new BigInteger("" + s.charAt(i))).add(right);
					powers = powers.multiply(BigInteger.TEN);
				}

				cStart--;
				left = new BigInteger("" + s.charAt(0));
				right = new BigInteger("" + s.charAt(cStart - 1));
				tot = new BigInteger(s.substring(cStart, n));
				powers = BigInteger.TEN;

				for (int i = 1; i < cStart - 1; i++)
					left = left.multiply(BigInteger.TEN).add(new BigInteger("" + s.charAt(i)));

				System.out.println("cStart : " + cStart);
//				System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);

				if (left.add(right).equals(tot))
				{
					out.print(left + "+" + right + "=" + tot);

					return;
				}

				for (int i = cStart - 2; i >= 0; i--)
				{
					System.out.println("left : " + left + ", right : " + right + ", tot : " + tot);
					if (left.add(right).equals(tot))
					{
//						print(i + 1, cStart);
						out.print(left + "+" + right + "=" + tot);

						return;
					}

					left = left.divide(BigInteger.TEN);
					right = powers.multiply(new BigInteger("" + s.charAt(i))).add(right);
					powers = powers.multiply(BigInteger.TEN);
				}
			}

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

	public TaskF(InputStream inputStream, OutputStream outputStream)
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);
		Thread thread = new Thread(null, new Solver(in, out), "TaskF", 1 << 29);

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

8917

19999999999999999999991000000000000000000000

*/
