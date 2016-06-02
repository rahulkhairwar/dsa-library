package com.hackerearth.practice.codemonk.graphtheory1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class DFS
{
	static Scanner scanner = new Scanner(System.in);
	static int nodes, edges;
	static List<Integer>[] list;
	static boolean[] visited;
	
	public static void main(String[] args)
	{
		while (true)
		{
			System.out.println("\n1. Enter new graph.");
			System.out.println("2. Show adjacency list for current graph.");
			System.out.println("3. Perform DFS traversal on the current graph.");
			System.out.println("4. Exit.");
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
					performDFS();
					break;
				case 4:
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
	
	static void performDFS()
	{
		visited = new boolean[nodes];
		
		System.out.println("Enter the vertex number from which you want the traversal to start : ");
		
		int start = scanner.nextInt() - 1;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		System.out.println("The DFS traversal of the graph is : ");
		System.out.print((start + 1) + " -> ");
		
		visited[start] = true;
		stack.push(start);
		
		int size;
		
		if (list[start] == null)
			size = 0;
		else
			size = list[start].size();
		
		if (size > 0)
		{
			List<Integer> temp = list[start];
			
			for (int i = 0; i < size; i++)
			{
				int curr = temp.get(i);
				
				if (!visited[curr])
					stack.push(curr);
			}
		}
		
		while (stack.size() > 1)
		{
			int top = stack.pop();
			
			if (visited[top])
				continue;
			
			System.out.print((top + 1) + " -> ");
			
			visited[top] = true;
			
			size = list[top].size();
			
			if (size > 0)
			{
				List<Integer> temp = list[top];
				
				for (int i = 0; i < size; i++)
				{
					int curr = temp.get(i);
					
					if (!visited[curr])
						stack.push(temp.get(i));
				}
			}
		}
		
		stack.pop();
	}
	
}

/*

4
5
1 2
2 4
3 1
3 4
4 2

4
6
1 2
2 4
3 1
3 4
4 2
2 1

*/
