package com.test;

import java.util.ArrayList;

/**
 * Created by rahulkhairwar on 12/03/16.
 */
public class AppliedCS
{
	public static void main(String[] args)
	{
//		alphabeticalOrder("rahul");
		getAnagramsWithOneMoreLetter("abcde");
	}

	static String alphabeticalOrder(String word)
	{
		int[] count = new int[26];
		int len = word.length();

		for (int i = 0; i < len; i++)
			count[word.charAt(i) - 'a']++;

		String sorted = "";

		for (int i = 0; i < 26; i++)
		{
			while (count[i] > 0)
			{
				sorted += (char) (i + 'a');
				count[i]--;
			}
		}

		System.out.println("sorted : " + sorted);

		return sorted;
	}

	static ArrayList<String> getAnagramsWithOneMoreLetter(String word)
	{
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < 26; i++)
		{
			char curr = (char) (i + 'a');

//			System.out.println("i : " + curr);

			int len = word.length();

			for (int j = 1; j < len; j++)
			{
				String newWord = word.substring(0, j) + curr + word.substring(j, len);

//				System.out.println("newWord : " + newWord);

				result.add(newWord);
			}
		}

		return result;
	}

}
