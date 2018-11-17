package com.dsa.testingUtil.dto;

import java.util.Objects;

/**
 * Data Transfer Object class for storing weighted edges.
 */
public class Edge
{
	public int from, to, weight;

	public Edge(int from, int to, int weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	@Override public int hashCode()
	{
		return Objects.hash(Math.min(from, to), Math.max(from, to));
	}

	@Override public boolean equals(Object obj)
	{
		Edge edge = (Edge) obj;

		// checking only for connection between nodes, edge weight doesn't matter.
		return Math.min(from, to) == Math.min(edge.from, edge.to) && Math.max(from, to) == Math.max(edge.from, edge.to);

	}

	@Override public String toString()
	{
		return String.format("(%d -> %d) :: %d", from, to, weight);
	}

}
