package com.testingUtil.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class with some functions to help with debugging.
 */
public class DebugUtils
{
	public static String getSetFromBitmask(int[] arr, int mask)
	{
		List<Integer> set = new ArrayList<>();
		StringBuilder ans = new StringBuilder("{");

		for (int i = 0; i < arr.length; i++)
			if ((mask & (1 << i)) > 0)
				set.add(arr[i]);

		if (set.size() == 0)
			return "{}";

		for (int i = 0; i < set.size() - 1; i++)
			ans.append(set.get(i)).append(", ");

		ans.append(set.get(set.size() - 1)).append("}");

		return ans.toString();
	}

	public static String getSetFromBitmask(List<Integer> list, int mask)
	{
		List<Integer> set = new ArrayList<>();
		StringBuilder ans = new StringBuilder("{");

		for (int i = 0; i < list.size(); i++)
			if ((mask & (1 << i)) > 0)
				set.add(list.get(i));

		if (set.size() == 0)
			return "{}";

		for (int i = 0; i < set.size() - 1; i++)
			ans.append(set.get(i)).append(", ");

		ans.append(set.get(set.size() - 1)).append("}");

		return ans.toString();
	}

	/**
	 * Method to print the path found(probably via Dijkstra's algorithm) from one node to another in reverse order(dest
	 * <- .... <- src).
	 * @param par the parent array to navigate from the destination to the source
	 * @param node current node
	 */
	public static void printPath(int[] par, int node)
	{
		// this is the start node.
		if (par[node] == -1)
		{
			System.out.println(0);

			return;
		}

		System.out.print(node + " <- ");
		printPath(par, par[node]);
	}

}
