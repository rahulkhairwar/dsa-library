package com.dsa.datastructures.trees.intervaltree;

import java.util.List;

/**
 * 
 * @author Rahul Khairwar
 *
 * @param <Type> the type of elements in this node
 */
public class IntervalNode<Type>
{
	List<Type> intersectingIntervalsSortedByStartPoints;
	List<Type> intersectingIntervalsSortedByEndPoints;
	IntervalNode<Type> leftSubtree;
	IntervalNode<Type> rightSubtree;
	private int median;

	public IntervalNode(IntervalNode<Type> leftSubtree,
			IntervalNode<Type> rightSubtree)
	{
		this.leftSubtree = leftSubtree;
		this.rightSubtree = rightSubtree;
	}

	public void sortAccToStartPoints(List<Type> all)
	{

	}

}
