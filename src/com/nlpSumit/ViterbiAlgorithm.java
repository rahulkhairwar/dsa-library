package com.nlpSumit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class ViterbiAlgorithm
{
	static int tot = 0;
	private static String directory;
	private static String[] fileNames;
	private static String[] categories;
	private static Map<String, String> map;
	private List<String> trainingListOfWords;
	private List<String> trainingListOfTags;
	private List<String> testWords;
	private List<String> testTags;
	private List<MyClass> baselineOutputList;
	private List<MyClass> outputList;
	private Map<String, Map<String, Double>> tempFollowMap;
	private Map<String, Map<String, Double>> tempWordMap;
	private Map<String, List<MyClass>> followMap;
	private Map<String, List<MyClass>> wordMap;
	private Map<String, MyClass> wordTagBaselineMap;

	static void initializeEverything() throws FileNotFoundException
	{
		int numberOfFiles = 500;

		directory = "/home/sumit/Desktop/nlp/brown/";

		CMath.gcd(100, 552);
		fileNames = new String[numberOfFiles];
		categories = new String[numberOfFiles];
		map = new HashMap<>();
	}

	// done.
	static int cntTheWrds(String file) throws FileNotFoundException
	{
		List<String> list = new ArrayList<>();
		File ff = new File(file);
		Scanner in = new Scanner(ff);

		while (in.hasNext())
		{
			String[] tok = in.nextLine().split(" ");

			if (tok.length > 0)
			{
				for (int i = 0; i < tok.length; i++)
				{
					String x = tok[i];

					x = x.trim();
					CMath.gcd(100, 2234);

					if (x.length() > 0)
						list.add(x);
				}
			}
		}

		return list.size();
	}

	// done.
	static String trim(String x)
	{
		int left = 0;

		while (left < x.length() && Character.isWhitespace(x.charAt(left)))
			left++;

		int right = x.length() - 1;

		while (right >= 0 && Character.isWhitespace(x.charAt(right)))
			right--;

//		if (left == -1 || right == -2)
			System.out.println("left : " + left + ", right : " + right + ", x : " + x);
		return x.substring(left, right + 1);
	}

	// done.
	static void parseContentAndAdd(String file, List<String[]> content) throws FileNotFoundException
	{
		List<String> list = new ArrayList<>();
		Scanner in = new Scanner(new File(directory + file));

		while (in.hasNext())
		{
			String line = in.nextLine();
			String[] tok = line.split(" ");

			if (tok.length > 0)
			{
				for (String x : tok)
				{
/*					if (!x.trim().equals(trim(x)))
						System.out.println("found!! inbuilt : " + x.trim() + ", my : " + trim(x));

					x = trim(x);*/

					x = x.trim();

					if (x.length() > 0)
						list.add(x);
				}
			}
		}

		tot = tot + list.size();

		for (String x : list)
		{
			String[] tok = x.split("/");

			switch (tok.length)
			{
				case 2:
					content.add(new String[]{tok[0], tok[1]});
					break;
				case 3:
					content.add(new String[]{tok[0] + "/" + tok[1], tok[2]});
					break;
			}
		}
	}

	// done.
	public static void main(String[] args) throws FileNotFoundException
	{
		ViterbiAlgorithm viterbiAlgorithm = new ViterbiAlgorithm();

		viterbiAlgorithm.viterbi();
		viterbiAlgorithm.parse();
	}

	void viterbi() throws FileNotFoundException
	{
		testing("humor");
	}

	// done.
	void testing(String cat) throws FileNotFoundException
	{
		List<String[]> catMap = getContentOfCategory(cat);
		int numberOfCatWrds, mapSize, errors, baselineErrors, ptr;

		numberOfCatWrds = numOfWrds(cat);
		mapSize = catMap.size();
		errors = baselineErrors = ptr = 0;
		initializeLists();

		CMath.gcd(100, 200);

		for (int i = 0; i < mapSize; i++)
			outputList.add(new MyClass("", 0.0));

		for (String[] entry : catMap)
		{
			testWords.add(entry[0]);
			testTags.add(entry[1]);

			MyClass myClass = wordTagBaselineMap.get(entry[0]);

			if (myClass == null)
				myClass = new MyClass("", 0.0);

			baselineOutputList.add(myClass);

			String wrd = baselineOutputList.get(ptr).word;

			if (wrd.equals(""))
				baselineOutputList.add(new MyClass("NP", 1.0));

			wrd = baselineOutputList.get(ptr).word;
			String temp = testTags.get(ptr);

			if (!wrd.equals(temp))
				baselineErrors++;

			List<MyClass> transProb;
			List<MyClass> emmPrb;

			if (ptr == 0)
			{
				transProb = followMap.get(".");

				if (transProb == null)
					transProb = new ArrayList<>();
			}
			else
			{
				transProb = followMap.get(outputList.get(ptr - 1).word);

				if (transProb == null)
					transProb = new ArrayList<>();
			}

			List<MyClass> myClasses = wordMap.get(testWords.get(ptr));

			if (myClasses == null)
				myClasses = new ArrayList<>();

			emmPrb = myClasses;

			if (emmPrb.size() == 0)
				outputList.set(ptr, new MyClass("NP", findTheMax(wordMap.get(testWords.get(ptr))).prob));
			else
			{
				double maxPrdPrb = 0.0;
				int transCtr = 0;
				int emissionCtr = 0;
				double maxPrb;

				while (emissionCtr < emmPrb.size() && transCtr < transProb.size())
				{
					String transTag = transProb.get(transCtr).word;
					String emmTag = emmPrb.get(emissionCtr).word;

					if (transTag.compareTo(emmTag) < 0)
						transCtr++;
					else if (transTag.compareTo(emmTag) > 0)
						emissionCtr++;
					else
					{
						maxPrb = transProb.get(transCtr).prob * emmPrb
								.get(emissionCtr).prob;

						if (maxPrb > maxPrdPrb)
						{
							maxPrdPrb = maxPrb;
							MyClass myClass1 = new MyClass(transTag, maxPrdPrb);
							outputList.set(ptr, myClass1);
						}

						transCtr++;
						emissionCtr++;
					}
				}
			}

			if (outputList.get(ptr).word.length() == 0)
				outputList.set(ptr, findTheMax(emmPrb));

			if (!outputList.get(ptr).word.equals(testTags.get(ptr)))
				errors++;

			ptr++;
		}

		System.out.println("errors = " + errors + " and baseline errors = " + baselineErrors);

		System.out.printf("Fraction of errors (Baseline) : %.4f\n", ((double) baselineErrors / numberOfCatWrds));
		System.out.printf("Fraction of errors (Viterbi) : %.4f\n", ((double) errors / numberOfCatWrds));
	}

	// done.
	private void initializeLists()
	{
		testWords = new ArrayList<>();
		testTags = new ArrayList<>();
		baselineOutputList = new ArrayList<>();
		outputList = new ArrayList<>();
	}

	// done.
	List<String[]> getContentOfCategory(String category) throws FileNotFoundException
	{
		List<String[]> content = new ArrayList<>();

		if (map == null)
		{
			initializeEverything();
			job();
		}

		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

		while (iterator.hasNext())
		{
			Map.Entry<String, String> entry = iterator.next();

			if (entry.getValue().equals(category))
				parseContentAndAdd(entry.getKey(), content);
		}

		return content;
	}

	// done.
	int numOfWrds(String category) throws FileNotFoundException
	{
		int cnt = 0;

		if (map == null)
		{
			initializeEverything();
			job();
		}

		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();

		while (iterator.hasNext())
		{
			Map.Entry<String, String> entry = iterator.next();

			if (entry.getValue().equals(category))
				cnt += cntTheWrds(directory + entry.getKey());
		}

		return cnt;
	}

	// done.
	void job() throws FileNotFoundException
	{
		File cat = new File("/home/sumit/Desktop/nlp/brown/cats.txt");
		Scanner in = new Scanner(cat);
		int ctr = 0;

		while (in.hasNext())
		{
			String[] tok = in.nextLine().split(" ");
			String a, b;

			a = tok[0].trim();
			b = tok[1].trim();

			fileNames[ctr] = a;
			categories[ctr] = b;
			map.put(a, b);
			ctr++;
		}
	}

	// done.
	private Double getSum(Collection<Double> values)
	{
		Double sum = 0.0;
		Iterator<Double> iterator = values.iterator();

		while (iterator.hasNext())
			sum += iterator.next();

		return sum;
	}

	// done.
	private void doTheTraining(String... categories) throws FileNotFoundException
	{
		for (String cat : categories)
		{
			List<String[]> categoryMap = getContentOfCategory(cat);
			int mapSize = categoryMap.size();

			for (String[] entry : categoryMap)
			{
				trainingListOfWords.add(entry[0]);
			}

			for (String[] entry : categoryMap)
			{
				trainingListOfTags.add(entry[1]);
			}

			for (int i = 1; i < mapSize; i++)
			{
				String tag = trainingListOfTags.get(i - 1);
				String followerTag = trainingListOfTags.get(i);
				Map<String, Double> temp = tempFollowMap.get(tag);

				if (temp == null)
					temp = new HashMap<>();

				tempFollowMap.put(tag, temp);
				temp = tempFollowMap.get(tag);

				Double cnt = temp.get(followerTag);

				if (cnt == null)
					cnt = 0.0;

				temp.put(followerTag, cnt + 1);
			}

			for (int i = 0; i < mapSize; i++)
			{
				String word = trainingListOfWords.get(i);
				String tag = trainingListOfTags.get(i);

				Map<String, Double> stringDoubleMap = tempWordMap.get(word);

				if (stringDoubleMap == null)
					stringDoubleMap = new HashMap<>();

				tempWordMap.put(word, stringDoubleMap);

				Map<String, Double> temp = tempWordMap.get(word);
				Double cnt = temp.get(tag);

				if (cnt == null)
					cnt = 0.0;

				temp.put(tag, cnt + 1);
			}

			Map<String, Double> stringDoubleMap = tempFollowMap.get(".");

			if (stringDoubleMap == null)
				stringDoubleMap = new HashMap<>();

			Map<String, Double> temp = stringDoubleMap;
			Double cnt = temp.get(trainingListOfTags.get(0));

			if (cnt == null)
				cnt = 0.0;

			temp.put(trainingListOfTags.get(0), cnt + 1);
		}
	}

	// done.
	private void prob()
	{
		Iterator<String> iterator = tempFollowMap.keySet().iterator();

		while (iterator.hasNext())
		{
			String tag = iterator.next();
			Map<String, Double> followers = tempFollowMap.get(tag);
			Double sum = getSum(followers.values());
			List<MyClass> list = new ArrayList<>();
			Iterator<String> iterator1 = followers.keySet().iterator();

			while (iterator1.hasNext())
			{
				String key = iterator1.next();
				followers.put(key, followers.get(key) / sum);
				list.add(new MyClass(key, followers.get(key)));
			}

			list.sort(new Comparator<MyClass>()
			{
				@Override public int compare(MyClass o1, MyClass o2)
				{
					return o1.word.compareTo(o2.word);
				}
			});

			followMap.put(tag, list);
		}

		iterator = tempWordMap.keySet().iterator();

		while (iterator.hasNext())
		{
			String word = iterator.next();
			Map<String, Double> tags = tempWordMap.get(word);
			Double sum = getSum(tags.values());
			List<MyClass> list = new ArrayList<>();
			Iterator<String> iterator1 = tags.keySet().iterator();

			wordTagBaselineMap.put(word, findTheMax(tags));

			while (iterator1.hasNext())
			{
				String tag = iterator1.next();

				tags.put(tag, tags.get(tag) / sum);

				MyClass my = new MyClass(tag, tags.get(tag));

				list.add(my);
			}

			Comparator<MyClass> comparator = new Comparator<MyClass>()
			{
				@Override public int compare(MyClass o1, MyClass o2)
				{
					return o1.word.compareTo(o2.word);
				}
			};

			CMath.gcd(1034, 200);
			list.sort(comparator);
			wordMap.put(word, list);
		}
	}

	// done.
	private MyClass findTheMax(Map<String, Double> tags)
	{
		MyClass max = new MyClass("", 0.0);
		Iterator<String> iterator = tags.keySet().iterator();

		while (iterator.hasNext())
		{
			String key = iterator.next();
			CMath.gcd(100, 259);

			if (tags.get(key) > max.prob)
			{
				max.word = key;
				max.prob = tags.get(key);
			}
		}

		return max;
	}

	// done.
	private MyClass findTheMax(List<MyClass> list)
	{
		MyClass max = new MyClass("", 0.0);

		if (list == null)
			return max;

		Iterator<MyClass> iterator = list.iterator();
		CMath.gcd(100, 2343);

		while (iterator.hasNext())
		{
			MyClass myClass = iterator.next();

			if (myClass.prob > max.prob)
				max = myClass;
		}

		return max;
	}

	void parse()
	{
		try
		{
			initializeEverything();
			job();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static class MyClass
	{
		String word;
		Double prob;

		MyClass(String word, Double prob)
		{
			this.word = word;
			this.prob = prob;
		}

	}

	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public int read()
		{
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars)
			{
				curChar = 0;
				try
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}

			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-')
			{
				sgn = -1;
				c = read();
			}

			int res = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				res *= 10;
				res += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public int[] nextIntArray(int arraySize)
		{
			int array[] = new int[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextInt();

			return array;
		}

		public long nextLong()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sign = 1;

			if (c == '-')
			{
				sign = -1;

				c = read();
			}

			long result = 0;

			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();

				result *= 10;
				result += c & 15;

				c = read();
			} while (!isSpaceChar(c));

			return result * sign;
		}

		public long[] nextLongArray(int arraySize)
		{
			long array[] = new long[arraySize];

			for (int i = 0; i < arraySize; i++)
				array[i] = nextLong();

			return array;
		}

		public float nextFloat()
		{
			float result, div;
			byte c;

			result = 0;
			div = 1;
			c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean isNegative = (c == '-');

			if (isNegative)
				c = (byte) read();

			do
			{
				result = result * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					result += (c - '0') / (div *= 10);

			if (isNegative)
				return -result;

			return result;
		}

		public double nextDouble()
		{
			double ret = 0, div = 1;
			byte c = (byte) read();

			while (c <= ' ')
				c = (byte) read();

			boolean neg = (c == '-');

			if (neg)
				c = (byte) read();

			do
			{
				ret = ret * 10 + c - '0';
			} while ((c = (byte) read()) >= '0' && c <= '9');

			if (c == '.')
				while ((c = (byte) read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);

			if (neg)
				return -ret;

			return ret;
		}

		public String next()
		{
			int c = read();

			while (isSpaceChar(c))
				c = read();

			StringBuilder res = new StringBuilder();

			do
			{
				res.appendCodePoint(c);

				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c)
		{
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String nextLine()
		{
			int c = read();

			StringBuilder result = new StringBuilder();

			do
			{
				result.appendCodePoint(c);

				c = read();
			} while (!isNewLine(c));

			return result.toString();
		}

		public boolean isNewLine(int c)
		{
			return c == '\n';
		}

		public void close()
		{
			try
			{
				stream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

	}

	static class CMath
	{
		static long power(long number, long power)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			if (power == 1)
				return number;

			if (power % 2 == 0)
				return power(number * number, power / 2);
			else
				return power(number * number, power / 2) * number;
		}

		static long modPower(long number, long power, long mod)
		{
			if (number == 1 || number == 0 || power == 0)
				return 1;

			number = mod(number, mod);

			if (power == 1)
				return number;

			long square = mod(number * number, mod);

			if (power % 2 == 0)
				return modPower(square, power / 2, mod);
			else
				return mod(modPower(square, power / 2, mod) * number, mod);
		}

		static long moduloInverse(long number, long mod)
		{
			return modPower(number, mod - 2, mod);
		}

		static long mod(long number, long mod)
		{
			return number - (number / mod) * mod;
		}

		static int gcd(int a, int b)
		{
			if (b == 0)
				return a;

			return gcd(b, a % b);
		}

		static long min(long... arr)
		{
			long min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static long max(long... arr)
		{
			long max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
		}

		static int min(int... arr)
		{
			int min = arr[0];

			for (int i = 1; i < arr.length; i++)
				min = Math.min(min, arr[i]);

			return min;
		}

		static int max(int... arr)
		{
			int max = arr[0];

			for (int i = 1; i < arr.length; i++)
				max = Math.max(max, arr[i]);

			return max;
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
			doTheTraining("adventure", "romance", "news");
			prob();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

}
