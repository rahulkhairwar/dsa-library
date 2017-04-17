package com.nlp;

import java.io.FileNotFoundException;
import java.util.*;

class ViterbiAlgorithm
{
	private List<String> trainingListOfWords, trainingListOfTags, testingListOfWords, testingListOfTags;
	private List<Pair> outputListBaseline, outputList;
	private Map<String, Map<String, Double>> tempFollowMap, tempWordMap;
	private Map<String, List<Pair>> followMap, wordMap;
	private Map<String, Pair> wordTagBaselineMap;

	void viterbi()
	{
		try
		{
			testOnCategory("fiction");
//			testOnCategory("hobbies");
//			testOnFile("wsj_test.txt");
//			testOnFile("wsj_training.txt");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	void testOnFile(String file) throws FileNotFoundException
	{
		List<String[]> categoryMap = new ArrayList<>();
		FileUtils.appendContent(file, categoryMap);

		int numberOfCategoryWords = categoryMap.size();
		int errorCnt = 0;
		int baselineErrorCnt = 0;
		int ctr = 0;

		testingListOfWords = new ArrayList<>(numberOfCategoryWords);
		testingListOfTags = new ArrayList<>(numberOfCategoryWords);
		outputListBaseline = new ArrayList<>(numberOfCategoryWords);
		outputList = new ArrayList<>(numberOfCategoryWords);

		for (int i = 0; i < numberOfCategoryWords; i++)
			outputList.add(new Pair("", 0.0));

		for (String[] entry : categoryMap)
		{
			testingListOfWords.add(entry[0]);
			testingListOfTags.add(entry[1]);
			outputListBaseline.add(wordTagBaselineMap.getOrDefault(entry[0], new Pair("", 0.0)));

			if (outputListBaseline.get(ctr).word.equals(""))
				outputListBaseline.add(new Pair("NP", 1.0));

			if (!outputListBaseline.get(ctr).word.equals(testingListOfTags.get(ctr)))
				baselineErrorCnt++;

			List<Pair> transitionProbabilities, emissionProbabilities;

			if (ctr == 0)
				transitionProbabilities = followMap.getOrDefault(".", new ArrayList<>());
			else
				transitionProbabilities = followMap.getOrDefault(outputList.get(ctr - 1).word, new ArrayList<>());

			emissionProbabilities = wordMap.getOrDefault(testingListOfWords.get(ctr), new ArrayList<>());

			if (emissionProbabilities.size() == 0)
				outputList.set(ctr, new Pair("NP", 1.0));
			else
			{
				double maxProductProbability = 0.0;
				int transitionCtr = 0;
				int emissionCtr = 0;
				double productProbability;

				while (transitionCtr < transitionProbabilities.size() && emissionCtr < emissionProbabilities.size())
				{
					String transitionTag = transitionProbabilities.get(transitionCtr).word;
					String emissionTag = emissionProbabilities.get(emissionCtr).word;

					if (transitionTag.compareTo(emissionTag) < 0)
						transitionCtr++;
					else if (transitionTag.compareTo(emissionTag) > 0)
						emissionCtr++;
					else
					{
						productProbability = transitionProbabilities.get(transitionCtr).probability *
								emissionProbabilities.get(emissionCtr).probability;

						if (productProbability > maxProductProbability)
						{
							maxProductProbability = productProbability;
							outputList.set(ctr, new Pair(transitionTag, 1.0));
						}

						transitionCtr++;
						emissionCtr++;
					}
				}
			}

			if (outputList.get(ctr).word.length() == 0)
				outputList.set(ctr, getMax(emissionProbabilities));

			if (!outputList.get(ctr).word.equals(testingListOfTags.get(ctr)))
				errorCnt++;

			ctr++;
		}

		System.out.println("errorCount : " + errorCnt + ", baselineErrorCnt : " + baselineErrorCnt);
		System.out.printf("Fraction of errors (Baseline) : %.4f\n", ((double) baselineErrorCnt /
				numberOfCategoryWords));
		System.out.printf("Fraction of errors (Viterbi) : %.4f\n", ((double) errorCnt / numberOfCategoryWords));
		System.out.println("Tags suggested by Baseline Algorithm : " + outputListBaseline);
		System.out.println("Tags suggested by Viterbi Algorithm : " + outputList);
	}

	void testOnCategory(String category) throws FileNotFoundException
	{
		System.out.println("will test on " + category);
		List<String[]> categoryMap = FileUtils.getContentOfCategory(category);
		int numberOfCategoryWords = FileUtils.getNumberOfWords(category);
		int mapSize = categoryMap.size();
		int errorCnt = 0;
		int baselineErrorCnt = 0;
		int ctr = 0;

		testingListOfWords = new ArrayList<>(mapSize);
		testingListOfTags = new ArrayList<>(mapSize);
		outputListBaseline = new ArrayList<>(mapSize);
		outputList = new ArrayList<>(mapSize);

		for (int i = 0; i < mapSize; i++)
			outputList.add(new Pair("", 0.0));

		for (String[] entry : categoryMap)
		{
			testingListOfWords.add(entry[0]);
			testingListOfTags.add(entry[1]);
			outputListBaseline.add(wordTagBaselineMap.getOrDefault(entry[0], new Pair("", 0.0)));

			if (outputListBaseline.get(ctr).word.equals(""))
				outputListBaseline.add(new Pair("NP", 1.0));

			if (!outputListBaseline.get(ctr).word.equals(testingListOfTags.get(ctr)))
				baselineErrorCnt++;

			List<Pair> transitionProbabilities, emissionProbabilities;

			if (ctr == 0)
				transitionProbabilities = followMap.getOrDefault(".", new ArrayList<>());
			else
				transitionProbabilities = followMap.getOrDefault(outputList.get(ctr - 1).word, new ArrayList<>());

			emissionProbabilities = wordMap.getOrDefault(testingListOfWords.get(ctr), new ArrayList<>());

			if (emissionProbabilities.size() == 0)
				outputList.set(ctr, new Pair("NP", getMax(wordMap.get(testingListOfWords.get(ctr))).probability));
			else
			{
				double maxProductProbability = 0.0;
				int transitionCtr = 0;
				int emissionCtr = 0;
				double productProbability;

				while (transitionCtr < transitionProbabilities.size() && emissionCtr < emissionProbabilities.size())
				{
					String transitionTag = transitionProbabilities.get(transitionCtr).word;
					String emissionTag = emissionProbabilities.get(emissionCtr).word;

					if (transitionTag.compareTo(emissionTag) < 0)
						transitionCtr++;
					else if (transitionTag.compareTo(emissionTag) > 0)
						emissionCtr++;
					else
					{
						productProbability = transitionProbabilities.get(transitionCtr).probability *
								emissionProbabilities.get(emissionCtr).probability;

						if (productProbability > maxProductProbability)
						{
							maxProductProbability = productProbability;
							outputList.set(ctr, new Pair(transitionTag, maxProductProbability));
						}

						transitionCtr++;
						emissionCtr++;
					}
				}
			}

			if (outputList.get(ctr).word.length() == 0)
				outputList.set(ctr, getMax(emissionProbabilities));

			if (!outputList.get(ctr).word.equals(testingListOfTags.get(ctr)))
				errorCnt++;

			ctr++;
		}

		System.out.println("errorCount : " + errorCnt + ", baselineErrorCnt : " + baselineErrorCnt);

		System.out.printf("Fraction of errors (Baseline) : %.12f\n", ((double) baselineErrorCnt /
				numberOfCategoryWords));
		System.out.printf("Fraction of errors (Viterbi) : %.12f\n", ((double) errorCnt / numberOfCategoryWords));
//		System.out.println("Tags suggested by Baseline Algorithm : " + outputListBaseline);
//		System.out.println("Tags suggested by Viterbi Algorithm : " + outputList);
//		System.out.println("Correct tags : " + testingListOfTags);
	}

	private Double getSum(Collection<Double> values)
	{
		Double sum = 0.0;

		for (Double val : values)
			sum += val;

		return sum;
	}

	private void trainOnCategory(String category) throws FileNotFoundException
	{
		List<String[]> categoryMap = FileUtils.getContentOfCategory(category);
		int mapSize = categoryMap.size();

		for (String[] entry : categoryMap)
		{
			trainingListOfWords.add(entry[0]);
			trainingListOfTags.add(entry[1]);
		}

		for (int i = 1; i < mapSize; i++)
		{
			String tag = trainingListOfTags.get(i - 1);
			String followerTag = trainingListOfTags.get(i);

			tempFollowMap.put(tag, tempFollowMap.getOrDefault(tag, new HashMap<>()));

			Map<String, Double> temp = tempFollowMap.get(tag);
			Double cnt = temp.getOrDefault(followerTag, 0.0);

			temp.put(followerTag, cnt + 1);
		}

		for (int i = 0; i < mapSize; i++)
		{
			String word = trainingListOfWords.get(i);
			String tag = trainingListOfTags.get(i);

			tempWordMap.put(word, tempWordMap.getOrDefault(word, new HashMap<>()));

			Map<String, Double> temp = tempWordMap.get(word);
			Double cnt = temp.getOrDefault(tag, 0.0);

			temp.put(tag, cnt + 1);
		}

		// Adding the first word of the document as a follwer of ".".
		Map<String, Double> temp = tempFollowMap.getOrDefault(".", new HashMap<>());
		Double cnt = temp.getOrDefault(trainingListOfTags.get(0), 0.0);

		temp.put(trainingListOfTags.get(0), cnt + 1);
	}

	private void findProbabilities()
	{
		for (String tag : tempFollowMap.keySet())
		{
			Map<String, Double> followers = tempFollowMap.get(tag);
			Double sum = getSum(followers.values());
			List<Pair> list = new ArrayList<>();

			for (String key : followers.keySet())
			{
				followers.put(key, followers.get(key) / sum);
				list.add(new Pair(key, followers.get(key)));
			}

			list.sort(new Comparator<Pair>()
			{
				@Override public int compare(Pair o1, Pair o2)
				{
					return o1.word.compareTo(o2.word);
				}
			});

			followMap.put(tag, list);
		}

		for (String word : tempWordMap.keySet())
		{
			Map<String, Double> tags = tempWordMap.get(word);
			Double sum = getSum(tags.values());
			List<Pair> list = new ArrayList<>();

			wordTagBaselineMap.put(word, getMax(tags));

			for (String tag : tags.keySet())
			{
				tags.put(tag, tags.get(tag) / sum);
				list.add(new Pair(tag, tags.get(tag)));
			}

			list.sort(new Comparator<Pair>()
			{
				@Override public int compare(Pair o1, Pair o2)
				{
					return o1.word.compareTo(o2.word);
				}
			});

			wordMap.put(word, list);
		}
	}

	private Pair getMax(Map<String, Double> tags)
	{
		Pair max = new Pair("", 0.0);

		for (String key : tags.keySet())
		{
			if (tags.get(key) > max.probability)
			{
				max.word = key;
				max.probability = tags.get(key);
			}
		}

		return max;
	}

	private Pair getMax(List<Pair> list)
	{
		Pair max = new Pair("", 0.0);

		if (list == null)
			return max;

		for (Pair pair : list)
		{
			if (pair.probability > max.probability)
				max = pair;
		}

		return max;
	}

	static class Pair
	{
		String word;
		Double probability;

		@Override public String toString()
		{
			return String.format("%s => %.2f", word, probability);
		}

		public Pair(String word, Double probability)
		{
			this.word = word;
			this.probability = probability;
		}
	}

	ViterbiAlgorithm()
	{
		trainingListOfWords = new ArrayList<>();
		trainingListOfTags = new ArrayList<>();
		tempFollowMap = new HashMap<>();
		tempWordMap = new HashMap<>();
		followMap = new HashMap<>();
		wordMap = new HashMap<>();
		wordTagBaselineMap = new HashMap<>();

		try
		{
			trainOnCategory("learned");
			trainOnCategory("humor");
//			trainOnCategory("reviews");
//			trainOnCategory("romance");
			findProbabilities();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
