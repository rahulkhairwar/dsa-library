package com.spoj.practice.classic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * http://www.spoj.com/problems/CCOST/
 */
public class CalculateTheCost
{
    public static void main(String[] args) throws IOException
    {
        InputReader in = new InputReader();
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        Solver solver = new Solver(in, out);
        solver.solve();

        in.close();
        out.flush();
        out.close();
    }

    static class Solver
    {
        int t, n, r, max;
        int[] bit;
        Tree[] trees;
        Query[] queries;
		Point[] points;
        InputReader in;
        PrintWriter out;

		void solve() throws IOException
		{
			t = in.nextInt();

			while (t-- > 0)
			{
				n = in.nextInt();
				points = new Point[200005];

				int[] temp = new int[200005];
				int count = 0;

				for (int i = 0; i < n; i++)
				{
					int x, y, value;

					x = in.nextInt();
					y = in.nextInt();
					value = in.nextInt();
					points[i] = new Point(-1, 1, x, y, value);
					temp[count++] = x;
					temp[count++] = y;
				}

				r = in.nextInt();
				int queriesCount = n;

				for (int i = 0, j = 0; i < r; i++)
				{
					int x1, y1, x2, y2;

					x1 = in.nextInt();
					y1 = in.nextInt();
					x2 = in.nextInt();
					y2 = in.nextInt();
					points[queriesCount++] = new Point(j++, 2, x1, y1, 0);
					points[queriesCount++] = new Point(j++, 3, x2, y2, 0);
					temp[count++] = x1;
					temp[count++] = y1;
					temp[count++] = x2;
					temp[count++] = y2;
				}

				compress2(temp, count);

				Arrays.sort(points, new Comparator<Point>()
				{
					@Override public int compare(Point o1, Point o2)
					{
						if (o1.y == o2.y)
						{
							if (o1.type == 1)
							{
								if (o2.type == 2)
								{
									if (o1.x < o2.x)
										return -1;
									else
										return 1;
								}
								else if (o2.type == 3)
								{
//									if (o1.)
								}
							}
							else if (o2.type == 1)
							{
								if (o1.type == 2)
								{
									if (o2.x < o1.x)
										return 1;
									else
										return -1;
								}
							}
						}

						return Integer.compare(o1.y, o2.y);
					}
				});

				int[] answers = new int[r];

				for (int i = 0; i < queriesCount; i++)
				{
					if (points[i].type == 1)
						add(points[i].x, points[i].value);
					else if (points[i].type == 2)
					{

					}
				}
			}
		}

		void compress2(int[] arr, int len)
		{
			Arrays.sort(arr, 0, len);

			Map<Integer, Integer> map = new HashMap<>();
			int counter = 1;

			for (int i = 0; i < len; i++)
				if (!map.containsKey(arr[i]))
					map.put(arr[i], counter++);

			int m = n + r;

			for (int i = 0; i < m; i++)
			{
				points[i].x = map.get(points[i].x);
				points[i].y = map.get(points[i].y);
			}
		}

        void solve2() throws IOException
        {
            t = in.nextInt();

            while (t-- > 0)
            {
                n = in.nextInt();
                trees = new Tree[n];
                int[] temp1 = new int[n];

                for (int i = 0; i < n; i++)
                {
                    int[] arr = in.nextIntArray();
                    trees[i] = new Tree(arr[0], arr[1], arr[2]);
                    temp1[i] = arr[2];
                }

                r = in.nextInt();
                queries = new Query[r];
                int[] temp2 = new int[2 * r];

                for (int i = 0, j = 0; i < r; i++)
                {
                    int[] arr = in.nextIntArray();
                    queries[i] = new Query(i, arr[0], arr[1], arr[2], arr[3]);
                    temp2[j++] = arr[1];
                    temp2[j++] = arr[3];
                }

                compress();

                int tr, q;
                int[] answers = new int[r];
                bit = new int[max + 1];

                tr = q = 0;

                while (tr < n || q < r)
                {
                    if (tr < n)
                    {
                        if (q < r)
                        {
                            if (trees[tr].y < queries[q].y1)
                            {
                                add(trees[tr].x, trees[tr].value);
                                tr++;
                            }
                            else if (!queries[q].startUsed)
                            {
                                answers[queries[q].index] -= query(queries[q].x1 - 1);
                                queries[q].startUsed = true;
                            }
                        }
                        else
                            tr = n;
                    }

                    if (q < r)
                    {
                        if (tr < n)
                        {
                            if (trees[tr].y > queries[q].y2)
                            {
                                answers[queries[q].index] += query(queries[q].x2);
                                q++;
                            }
                            else
                            {
                                add(trees[tr].x, trees[tr].value);
                                tr++;
                            }
                        }
                        else
                        {
                            answers[queries[q].index] += query(queries[q].x2);
                            q++;
                        }
                    }
                }

                for (int i = 0; i < r; i++)
                    out.println(answers[i]);
            }
        }

        void compress()
        {
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

            Arrays.sort(trees, new Comparator<Tree>()
            {
                public int compare(Tree o1, Tree o2)
                {
                    return Integer.compare(o1.y, o2.y);
                }
            });

            Arrays.sort(queries, new Comparator<Query>()
            {
                public int compare(Query one, Query two)
                {
                    return Integer.compare(one.y2, two.y2);
                }
            });

            int total = 2 * n + 4 * r;
            int[] temp = new int[total];
            int counter = 0;

            for (int i = 0; i < n; i++)
            {
                temp[counter++] = trees[i].x;
                temp[counter++] = trees[i].y;
            }

            for (int i = 0; i < r; i++)
            {
                temp[counter++] = queries[i].x1;
                temp[counter++] = queries[i].y1;
                temp[counter++] = queries[i].x2;
                temp[counter++] = queries[i].y2;
            }

            Arrays.sort(temp);
            counter = 1;

            for (int i = 0; i < total; i++)
                map.put(temp[i], counter++);

            max = counter - 1;

            for (int i = 0; i < n; i++)
            {
                trees[i].x = map.get(trees[i].x);
                trees[i].y = map.get(trees[i].y);
            }

            for (int i = 0; i < r; i++)
            {
                queries[i].x1 = map.get(queries[i].x1);
                queries[i].y1 = map.get(queries[i].y1);
                queries[i].x2 = map.get(queries[i].x2);
                queries[i].y2 = map.get(queries[i].y2);
            }
        }

        void add(int index, int value)
        {
            while (index <= max)
            {
                bit[index] += value;
                index += index & -index;
            }
        }

        int query(int index)
        {
            int answer = 0;

            while (index > 0)
            {
                answer += bit[index];
                index -= index & -index;
            }

            return answer;
        }

        public Solver(InputReader in, PrintWriter out)
        {
            this.in = in;
            this.out = out;
        }

        class Tree
        {
            int x, y, value;

            public Tree(int x, int y, int value)
            {
                this.x = x;
                this.y = y;
                this.value = value;
            }

        }

        class Query
        {
            int index, x1, y1, x2, y2;
            boolean startUsed;

            public Query(int index, int x1, int y1, int x2, int y2)
            {
                this.index = index;
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
            }

        }

		class Point
		{
			int index, type, x, y, value;

			public Point(int index, int type, int x, int y, int value)
			{
				this.index = index;
				this.type = type;
				this.x = x;
				this.y = y;
				this.value = value;
			}

		}

    }

    static class InputReader
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int nextInt() throws NumberFormatException, IOException
        {
            return Integer.parseInt(in.readLine());
        }

        int nextInt(String s)
        {
            return Integer.parseInt(s);
        }

        int[] nextIntArray() throws IOException
        {
            String[] tok = in.readLine().split(" ");
            int[] arr = new int[tok.length];

            for (int i = 0; i < tok.length; i++)
                arr[i] = nextInt(tok[i]);

            return arr;
        }

        long nextLong(String s)
        {
            return Long.parseLong(s);
        }

        long[] nextLongArray() throws IOException
        {
            String[] tok = in.readLine().split(" ");
            long[] arr = new long[tok.length];

            for (int i = 0; i < tok.length; i++)
                arr[i] = nextLong(tok[i]);

            return arr;
        }

        void close() throws IOException
        {
            in.close();
        }

    }

}
