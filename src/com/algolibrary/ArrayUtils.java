package com.algolibrary;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by rahulkhairwar on 23/02/16.
 */
public class ArrayUtils
{
	public static <T> T[] reverse(T[] array)
	{
		Collections.reverse(Arrays.asList(array));

		return array;
	}

	/**
	 * Reverses the elements of the array in the specified range.
	 *
	 * @param array the array whose elements are to be reversed.
	 * @param from  range starting index.
	 * @param to    range ending index.
	 * @param <T>   the type of the array.
	 * @return the array with the reversed elements.
	 */
	public static <T> T[] reverseRange(T[] array, int from, int to)
	{
		int size = to - from + 1;

		for (int i = from, j = to; i < from + size / 2; i++, j--)
		{
			T temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

		return array;
	}


}