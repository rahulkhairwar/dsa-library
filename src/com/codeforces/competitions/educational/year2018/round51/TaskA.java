package com.codeforces.competitions.educational.year2018.round51;

import java.io.*;

public class TaskA
{
	public static void main(String[] args)
	{
		new TaskA(System.in, System.out);
	}

	static class Solver implements Runnable
	{
		int n, len;
		char[] s;
		BufferedReader in;
		PrintWriter out;

		void solve() throws IOException
		{
			n = Integer.parseInt(in.readLine());

			while (n-- > 0)
			{
				s = in.readLine().toCharArray();
				len = s.length;

				int small = 0;
				int capital = 0;
				int digit = 0;

				for (char ch : s)
				{
					if (ch >= 'a' && ch <= 'z')
						small++;
					else if (ch >= 'A' && ch <= 'Z')
						capital++;
					else
						digit++;
				}

				if (small > 0 && capital > 0 && digit > 0)
				{
					out.println(new String(s));

					continue;
				}
				else if (small > 0 && capital > 0)
				{
					if (small == 1)
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= 'A' && s[i] <= 'Z')
							{
								s[i] = '4';

								break;
							}
						}
					}
					else
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= 'a' && s[i] <= 'z')
							{
								s[i] = '4';

								break;
							}
						}
					}
				}
				else if (small > 0 && digit > 0)
				{
					if (small == 1)
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= '0' && s[i] <= '9')
							{
								s[i] = 'D';

								break;
							}
						}
					}
					else
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= 'a' && s[i] <= 'z')
							{
								s[i] = 'D';

								break;
							}
						}
					}
				}
				else if (capital > 0 && digit > 0)
				{
					if (capital == 1)
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= '0' && s[i] <= '9')
							{
								s[i] = 'd';

								break;
							}
						}
					}
					else
					{
						for (int i = 0; i < len; i++)
						{
							if (s[i] >= 'A' && s[i] <= 'Z')
							{
								s[i] = 'd';

								break;
							}
						}
					}
				}
				else if (small > 0)
				{
					s[len - 2] = 'D';
					s[len - 1] = '4';
				}
				else if (capital > 0)
				{
					s[len - 2] = 'd';
					s[len - 1] = '4';
				}
				else
				{
					s[len - 2] = 'd';
					s[len - 1] = 'D';
				}

				out.println(new String(s));
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
