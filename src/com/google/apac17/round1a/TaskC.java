package com.google.apac17.round1a;

import java.io.*;

/**
 * Created by rahulkhairwar on 16/04/16.
 */
public class TaskC
{
	public static void main(String[] args)
	{
		BufferedReader in;

		try
		{
			in = new BufferedReader(new FileReader(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/google/apac17/round1a/inputC.txt"));

			OutputWriter out = new OutputWriter(System.out);
			Thread thread = new Thread(null, new Solver(in, out), "Solver", 1 << 28);

			thread.start();

			try
			{
				thread.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			out.flush();

			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	static class Solver implements Runnable
	{
		int t, m;
		BufferedReader in;
		OutputWriter out;

		public Solver(BufferedReader in, OutputWriter out)
		{
			this.in = in;
			this.out = out;
		}

		@Override public void run()
		{
			try
			{
				t = Integer.parseInt(in.readLine());

				outer:
				for (int i = 0; i < t; i++)
				{
					m = Integer.parseInt(in.readLine());

					double[] cost = new double[m + 1];
					String[] tokens = in.readLine().split(" ");

					double max = 0;

					for (int j = 0; j <= m; j++)
					{
						cost[j] = Integer.parseInt(tokens[j]);
						max = Math.max(max, cost[j]);
					}

/*					if (m == 2)
					{
						if (cost[0] != 0)
						{
							double dis = Math.sqrt(cost[1] * cost[1] + 4 * cost[0] * cost[2]);
							double r1, r2;

							r1 = cost[1] + dis;
							r1 /= (2 * cost[0]);
							r1 -= 1;
							r2 = cost[1] - dis;
							r2 /= (2 * cost[0]);
							r2 -= 1;

							double rate = Math.max(r1, r2);

//							System.out.println("r1 : " + r1 + ", r2 : " + r2);
							System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
						}
						else
						{
							double rate = -cost[2] / cost[1] - 1;
							System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
						}
					}
					else if (m == 1)
					{
						double rate = cost[1] / cost[0] - 1;

						System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
					}*/
//					else
					{
						double a, b, c;

//						b = -2 + 1e-9;
//						c = 2 - 1e-9;
						max++;
						b = -max;
						c = max;

						do
						{
							a = b;
							b = c;
							c = b - (b - a) / (f(b, cost) - f(a, cost)) * f(b, cost);

							if (f(c, cost) == 0)
							{
								c -= 1;
								if (c == -1)
									c += 1e-9;
								else if (c == 1)
									c -= 1e-9;

/*								if (i == 3)
									System.out.println("\t&&&& c : " + c);*/

								System.out.printf("Case #" + (i + 1) + ": %.12f\n", c);

								continue outer;
							}
						} while (Math.abs(c - b) >= 1e-12);

						c -= 1;
						System.out.printf("Case #" + (i + 1) + ": %.12f\n", (c == -1 ? (c + 1e-9) : (c - 1e-9)));
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		double f(double x, double[] cost)
		{
			double a = 0;
			double pow = x;

			for (int i = 0; i < m; i++)
			{
				a += cost[m - i] * pow;
				pow *= x;
			}

			a -= cost[0] * pow;

			return a;
		}

/*		@Override public void run()
		{
			try
			{
				t = Integer.parseInt(in.readLine());

				for (int i = 0; i < t; i++)
				{
					m = Integer.parseInt(in.readLine());

					double[] cost = new double[m + 1];
					String[] tokens = in.readLine().split(" ");

					for (int j = 0; j <= m; j++)
						cost[j] = Integer.parseInt(tokens[j]);

					if (m == 2)
					{
						if (cost[0] != 0)
						{
							double dis = Math.sqrt(cost[1] * cost[1] + 4 * cost[0] * cost[2]);
							double r1, r2;

							r1 = cost[1] + dis;
							r1 /= (2 * cost[0]);
							r1 -= 1;
							r2 = cost[1] - dis;
							r2 /= (2 * cost[0]);
							r2 -= 1;

							double rate = Math.max(r1, r2);

//							System.out.println("r1 : " + r1 + ", r2 : " + r2);
							System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
						}
						else
						{
							double rate = -cost[2] / cost[1] - 1;

							System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
						}
					}
					else
					{
						double rate = cost[1] / cost[0] - 1;

						System.out.printf("Case #" + (i + 1) + ": %.12f\n", rate);
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}*/

	}

	static class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
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

	}

}

/*

Case #1: 0.000000000000
Case #2: 0.618033988750
Case #3: -0.999968327184
Case #4: 0.999999996000
Case #5: 0.052658374921
Case #6: 0.637568139296
Case #7: 0.473040830470
Case #8: -0.243876161493
Case #9: -0.656973554078
Case #10: -0.188711862001
Case #11: 0.163969046798
Case #12: -0.260445504805
Case #13: -0.162592540273
Case #14: 0.523478890068
Case #15: 0.790569213013
Case #16: 0.259085825282
Case #17: -0.269130029025
Case #18: -0.610681994565
Case #19: -0.727414393899
Case #20: 0.704131965452
Case #21: -0.093201065411
Case #22: 0.038186044031
Case #23: 0.538768828802
Case #24: -0.776859062614
Case #25: 0.610988201909
Case #26: -0.049664742178
Case #27: -0.523395253269
Case #28: 0.576029436272
Case #29: -0.756630516696
Case #30: 0.187429984340
Case #31: 0.135819273584
Case #32: -0.145214302582
Case #33: 0.455773507603
Case #34: -0.318618071889
Case #35: -0.035964543664
Case #36: 0.363207710834
Case #37: -0.178119306510
Case #38: 0.090431951185
Case #39: 0.355250664157
Case #40: -0.387612495284
Case #41: -0.022095662175
Case #42: 0.484487537296
Case #43: 0.098002868056
Case #44: 0.470772132283
Case #45: -0.179570809885
Case #46: 0.427500515508
Case #47: -0.184599172926
Case #48: 0.188422694239
Case #49: 0.239176196839
Case #50: 0.675813227031
Case #51: -0.111216740262
Case #52: -0.628266929033
Case #53: -0.438272404939
Case #54: -0.747010273983
Case #55: -0.096409725731
Case #56: -0.444313577776
Case #57: -0.749880524381
Case #58: -0.605813304257
Case #59: -0.514258813573
Case #60: 0.271809878597
Case #61: 0.124752679333
Case #62: 0.006953383005
Case #63: 0.075044077059
Case #64: 0.552109761140
Case #65: 0.349935618643
Case #66: 0.500096048462
Case #67: -0.457945621732
Case #68: 0.479797815706
Case #69: -0.512303853815
Case #70: 0.617254431963
Case #71: -0.284008547752
Case #72: -0.685888736299
Case #73: -0.032404164941
Case #74: -0.068345891893
Case #75: -0.578467717074
Case #76: 0.050377815508
Case #77: -0.614958910271
Case #78: -0.491026106248
Case #79: -0.503534297363
Case #80: -0.009045094088
Case #81: -0.505799355646
Case #82: -0.147736209272
Case #83: -0.226102752074
Case #84: -0.315132535246
Case #85: 0.446035634672
Case #86: 0.449443356787
Case #87: -0.403366592893
Case #88: -0.128721411163
Case #89: 0.343510153424
Case #90: -0.036412687069
Case #91: 0.141416884831
Case #92: 0.795476373554
Case #93: -0.736621109623
Case #94: -0.774668490371
Case #95: 0.586563601780
Case #96: 0.168818235922
Case #97: 0.298236054778
Case #98: -0.336700228906
Case #99: -0.295231813511
Case #100: -0.765711591411

 */
