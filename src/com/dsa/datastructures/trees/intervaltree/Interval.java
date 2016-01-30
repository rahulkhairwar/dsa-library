package com.dsa.datastructures.trees.intervaltree;

/**
 * 
 * @author rahulkhairwar
 *
 * @param <Type>
 * @param <DataType> the type of data stored in the interval
 */

public class Interval<Type, DataType> implements Comparable<Interval<Type, DataType>>
{
	private int start;
	private int end;
	private DataType data;
	
	public Interval(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	@Override
	public int compareTo(Interval<Type, DataType> o)
	{
		return 0;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

}
