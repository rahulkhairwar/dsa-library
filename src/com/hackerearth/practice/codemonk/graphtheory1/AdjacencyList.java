package com.hackerearth.practice.codemonk.graphtheory1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjacencyList
{
	static Scanner scanner = new Scanner(System.in);
	static int nodes, edges;
	static List<Integer>[] list;
	
	public static void main(String[] args)
	{
		while (true)
		{
			System.out.println("\n1. Enter new graph.");
			System.out.println("2. Show adjacency list for current graph.");
			System.out.println("3. Exit.");
			System.out.print("\nEnter your choice : ");
			
			int ch;
			
			ch = scanner.nextInt();
			
			switch (ch)
			{
				case 1:
					createNewGraph();
					break;
				case 2:
					printGraph();
					break;
				case 3:
					return;
				default:
					System.out.println("Wrong choice.");
			}
		}
	}
	
	static void createNewGraph()
	{
		System.out.println("Enter the number of nodes : ");
		nodes = scanner.nextInt();
		System.out.println("Enter the number of edges : ");
		edges = scanner.nextInt();
		
		list = new ArrayList[nodes];
		
		for (int i = 0; i < edges; i++)
		{
			int from, to;
			
			System.out.println("Enter the vertex number where the edge starts : ");
			from = scanner.nextInt() - 1;
			System.out.println("Enter the vertex number where the edge ends : ");
			to = scanner.nextInt() - 1;
			
			if (list[from] == null)
				list[from] = new ArrayList<Integer>();
			
			list[from].add(to);
			
/*			if (list[to] == null)
				list[to] = new ArrayList<Integer>();
			
			list[to].add(from);*/
		}
	}
	
	static void printGraph()
	{
		if (nodes == 0)
		{
			System.out.println("You have not created a graph yet.");
			
			return;
		}
		
		System.out.println("The adjacency list of the created graph looks like : ");
		
		for (int i = 0; i < nodes; i++)
		{
			List<Integer> temp = list[i];
			int size = temp.size();
			
			System.out.print(i + 1 + " -> ");
			
			for (int j = 0; j < size - 1; j++)
				System.out.print((temp.get(j) + 1) + " --> ");
			
			System.out.print(temp.get(size - 1) + 1);
			
			System.out.println();
		}
	}

}
