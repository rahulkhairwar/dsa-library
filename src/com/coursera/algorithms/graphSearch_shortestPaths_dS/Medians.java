package com.coursera.algorithms.graphSearch_shortestPaths_dS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Medians
{
	public static void main(String[] args)
	{
		try
		{
			Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
					+ "Programming/src/com/coursera/algorithms/graphSearch_shortestPaths_dS/MediansInput.txt"));
			PrintWriter out = new PrintWriter(System.out);
			Solver solver = new Solver(in, out);

			solver.solve();
			in.close();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class Solver
	{
		Scanner in;
		PrintWriter out;

		void solve()
		{
			PriorityQueue<Integer> minHeap = new PriorityQueue<>();
			PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
			int ans = 0;

			for (int i = 0; i < 1e4; i++)
			{
				int x = in.nextInt();

				addElement(minHeap, maxHeap, x);
				ans += maxHeap.peek();
			}

			out.println(ans % 10000);
		}

		void addElement(PriorityQueue<Integer> minHeap, PriorityQueue<Integer> maxHeap, int x)
		{
			if (maxHeap.size() == 0)
			{
				maxHeap.add(x);

				return;
			}

			if (maxHeap.size() == minHeap.size())
			{
				if (x < minHeap.peek())
					maxHeap.add(x);
				else
				{
					maxHeap.add(minHeap.poll());
					minHeap.add(x);
				}

				return;
			}

			if (minHeap.size() == 0)
			{
				if (x > maxHeap.peek())
					minHeap.add(x);
				else
				{
					minHeap.add(maxHeap.poll());
					maxHeap.add(x);
				}

				return;
			}

			if (x > maxHeap.peek())
				minHeap.add(x);
			else
			{
				minHeap.add(maxHeap.poll());
				maxHeap.add(x);
			}
		}

		Solver(Scanner in, PrintWriter out)
		{
			this.in = in;
			this.out = out;
		}

	}

}
