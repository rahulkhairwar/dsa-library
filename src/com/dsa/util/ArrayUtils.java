package com.dsa.util;

import java.util.Random;

/**
 * This class provides some methods to manipulate arrays.
 */
public final class ArrayUtils
{
	/**
	 * Don't let anyone instantiate this class.
	 */
	private ArrayUtils()
	{
	}

	/**
	 * Reverses an array of type T.
	 *
	 * @param arr the arr whose elements are to be reversed.
	 * @param <T>   the type of the arr.
	 * @return the reversed array.
	 */
	public static <T> T[] reverse(T[] arr)
	{
		for (int i = 0, j = arr.length - 1; i < arr.length / 2; i++, j--)
		{
			T temp = arr[i];

			arr[i] = arr[j];
			arr[j] = temp;
		}

		return arr;
	}

	/**
	 * Reverses the elements of the array in the specified range.
	 *
	 * @param array the array whose elements are to be reversed.
	 * @param from  range starting index.
	 * @param to    range ending index.
	 * @return the array with the reversed elements.
	 */
	public static double[] reverseRange(double[] array, int from, int to)
	{
		int size = to - from + 1;

		for (int i = from, j = to; i < from + size / 2; i++, j--)
		{
			double temp = array[i];

			array[i] = array[j];
			array[j] = temp;
		}

		return array;
	}

	/**
	 * Reverses the elements of the array in the specified range.
	 *
	 * @param array the array whose elements are to be reversed.
	 * @param from  range starting index.
	 * @param to    range ending index.
	 * @return the array with the reversed elements.
	 */
	public static long[] reverseRange(long[] array, int from, int to)
	{
		int size = to - from + 1;

		for (int i = from, j = to; i < from + size / 2; i++, j--)
		{
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

		return array;
	}

	/**
	 * Reverses the elements of the array in the specified range.
	 *
	 * @param array the array whose elements are to be reversed.
	 * @param from  range starting index.
	 * @param to    range ending index.
	 * @return the array with the reversed elements.
	 */
	public static int[] reverseRange(int[] array, int from, int to)
	{
		int size = to - from + 1;

		for (int i = from, j = to; i < from + size / 2; i++, j--)
		{
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

		return array;
	}

	/**
	 * Reverses the elements of the array in the specified range.
	 *
	 * @param array the array whose elements are to be reversed.
	 * @param from  range starting index.
	 * @param to    range ending index.
	 * @return the array with the reversed elements.
	 */
	public static boolean[] reverseRange(boolean[] array, int from, int to)
	{
		int size = to - from + 1;

		for (int i = from, j = to; i < from + size / 2; i++, j--)
		{
			boolean temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}

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

	public static void shuffleArray(double[] arr)
	{
		int len = arr.length;
		Random random = new Random();

		for (int i = 0; i < len; i++)
		{
			double temp = arr[i];
			int randomPosition = i + random.nextInt(len - i);

			arr[i] = arr[randomPosition];
			arr[randomPosition] = temp;
		}
	}

	public static void shuffleArray(long[] arr)
	{
		int len = arr.length;
		Random random = new Random();

		for (int i = 0; i < len; i++)
		{
			long temp = arr[i];
			int randomPosition = i + random.nextInt(len - i);

			arr[i] = arr[randomPosition];
			arr[randomPosition] = temp;
		}
	}

	public static void shuffleArray(int[] arr)
	{
		int len = arr.length;
		Random random = new Random();

		for (int i = 0; i < len; i++)
		{
			int temp = arr[i];
			int randomPosition = i + random.nextInt(len - i);

			arr[i] = arr[randomPosition];
			arr[randomPosition] = temp;
		}
	}

	public static void shuffleArray(boolean[] arr)
	{
		int len = arr.length;
		Random random = new Random();

		for (int i = 0; i < len; i++)
		{
			boolean temp = arr[i];
			int randomPosition = i + random.nextInt(len - i);

			arr[i] = arr[randomPosition];
			arr[randomPosition] = temp;
		}
	}

	public static void shuffleArray(Object[] arr)
	{
		int len = arr.length;
		Random random = new Random();

		for (int i = 0; i < len; i++)
		{
			Object temp = arr[i];
			int randomPosition = i + random.nextInt(len - i);

			arr[i] = arr[randomPosition];
			arr[randomPosition] = temp;
		}
	}

}