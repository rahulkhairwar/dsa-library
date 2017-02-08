package com.coursera.algorithms_divide_and_conquer;

import java.io.File;
import java.util.*;

/**
 * The file contains the adjacency list representation of a simple undirected
 * graph. There are 200 vertices labeled 1 to 200. The first column in the file
 * represents the vertex label, and the particular row (other entries except the
 * first column) tells all the vertices that the vertex is adjacent to. So for
 * example, the 6th row looks like : "6 155 56 52 120 ......". This just means
 * that the vertex with label 6 is adjacent to (i.e., shares an edge with) the
 * vertices with labels 155,56,52,120,......,etc
 * <p>
 * Your task is to code up and run the randomized contraction algorithm for the
 * min cut problem and use it on the above graph to compute the min cut. (HINT:
 * Note that you'll have to figure out an implementation of edge contractions.
 * Initially, you might want to do this naively, creating a new graph from the
 * old every time there's an edge contraction. But you should also think about
 * more efficient implementations.) (WARNING: As per the video lectures, please
 * make sure to run the algorithm many times with different random seeds, and
 * remember the smallest cut that you ever find.) Write your numeric answer in
 * the space provided. So e.g., if your answer is 5, just type 5 in the space
 * provided.
 * <p>
 * Answer 17
 *
 * @author Narayanan
 */
public class MinCut
{
	Map<Integer, List<Integer>> adjList;
	List<Integer> vList;
	int size;
	int mincut = Integer.MAX_VALUE;
	Random r = new Random();

	public MinCut()
	{
		init();
	}

	public static void main(String args[])
	{
		long start = System.currentTimeMillis();
		MinCut mincut = new MinCut();
		//Probability of finding a mincut is between 1/n^2,1/nlogn
		for (int i = 1; i <= mincut.size * mincut.size; i++)
		{
			mincut.init();
			mincut.calculate();

			if (i % 1000 == 0)
				System.out.println("Iteration " + i + " " + mincut.mincut);
		}

		System.out.println(mincut.mincut);
		System.out.println("Time taken (s) : " + ((System.currentTimeMillis() - start) / 1000.0));
	}

	void init()
	{
		adjList = new HashMap<>();
		vList = new ArrayList<>();
		try
		{
			Scanner scanner = new Scanner(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA " + "Workspace/Competitive "
							+ "Programming/src/com/coursera/algorithms_divide_and_conquer/minCut.txt"));

			while (scanner.hasNextLine())
			{
				String s = scanner.nextLine();
				String num[] = s.split("\\t");
				List<Integer> adj = new ArrayList<>();

				for (int i = 1; i < num.length; i++)
					adj.add(Integer.parseInt(num[i]));

				adjList.put(Integer.parseInt(num[0]), adj);
				vList.add(Integer.parseInt(num[0]));
			}

			size = vList.size();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	void calculate()
	{
		while (adjList.size() > 2)
		{
			//Find the start_vertex of a random edge
			int vIndex = vList.get(r.nextInt(vList.size()));
			List<Integer> v = adjList.get(vIndex);
			//Find the end_vertex of a random edge
			int dIndex = v.get(r.nextInt(v.size()));
			List<Integer> d = adjList.get(dIndex);

			/*
			 * start_vertex = start_vertex U end_vertex
			 * 1. Find all vertices connected to end_vertex as aux vertices
			 * 2. Remove the end_vertex from the vertex list for each aux vertex
			 * 3. Add the start_vertex from the vertex list for each aux vertex
			 * 4. Add the aux vertices to the start_vertex list
			 * 5. Remove the end_vertex list from adjList
			 * 6. Remove self loops in start_vertex
			 */
			for (Iterator<Integer> iterator = d.iterator(); iterator.hasNext(); )
			{
				int auxIndex = iterator.next();
				List<Integer> aux = adjList.get(auxIndex);

				//2. Remove the end_vertex from the vertex list for each aux vertex
				while (aux.remove(new Integer(dIndex)))
					;

				//3. Add the start_vertex from the vertex list for each aux vertex
				aux.add(new Integer(vIndex));
				//4. Add the aux vertices to the start_vertex list
				v.add(auxIndex);
			}

			//5. Remove the end_vertex list from adjList
			adjList.remove(dIndex);
			vList.remove(new Integer(dIndex));

			//6. Remove self loops in start_vertex
			while (v.remove(new Integer(vIndex)))
				;
		}
		mincut = Math.min(mincut, adjList.get(vList.get(0)).size());
	}

	void display()
	{
		List<Integer> sortedKeys = new ArrayList<>(adjList.keySet());

		for (Iterator<Integer> iterator = sortedKeys.iterator(); iterator.hasNext(); )
		{
			int i = iterator.next();

			System.out.print(i + " ");
			List<Integer> list = adjList.get(i);

			for (Iterator<Integer> iterator2 = list.iterator(); iterator2.hasNext(); )
				System.out.print(iterator2.next() + " ");

			System.out.println();
		}

		System.out.println();
	}
}
