package com.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

class FileUtils
{
	private static String directory;
	private static String[] fileNames, categories;
	private static Map<String, String> map;
	private static PrintWriter out;

	static void parse() throws FileNotFoundException
	{
		init();
		work();
		finish();
	}

	static void init2() throws FileNotFoundException
	{
		int numberOfFiles = 500;

		directory = "/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/";
		fileNames = new String[numberOfFiles];
		categories = new String[numberOfFiles];
		map = new HashMap<>();
		out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/output.txt"));
	}

	static void work2() throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/cats.txt"));
		int ctr = 0;

		while (in.hasNext())
		{
			String[] tok = in.nextLine().split(" ");

			if (tok.length < 2)
				continue;

			System.out.println("tok : " + Arrays.toString(tok) + ", len : " + tok.length);

			String a = tok[0].trim();
			String b = tok[1].trim();

			fileNames[ctr] = a;
			categories[ctr] = b;
			map.put(a, b);
			ctr++;
		}

//			System.out.println("map :");

//			for (Map.Entry<String, String> entry : map.entrySet())
//				System.out.println("file : " + entry.getKey() + ", cat : " + entry.getValue());

		System.out.println("Number of files : " + map.size());
//		Map<String, String> news = getContentOfCategory("news");
		List<String[]> news = getContentOfCategory("news");

//		for (Map.Entry<String, String> entry : news.entrySet())
//			out.println(entry);

		for (String[] s : news)
			out.println(s[0] + " -> " + s[1]);

		out.flush();
	}

	static void init() throws FileNotFoundException
	{
		int numberOfFiles = 500;

		directory = "/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/brown/";
		fileNames = new String[numberOfFiles];
		categories = new String[numberOfFiles];
		map = new HashMap<>();
		out = new PrintWriter(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/output.txt"));
	}

	static void work() throws FileNotFoundException
	{
		File cat = new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
				+ "Programming/src/com/nlp/brown/cats.txt");
		Scanner in = new Scanner(cat);
		int ctr = 0;

		while (in.hasNext())
		{
			String[] tok = in.nextLine().split(" ");
			String a = tok[0].trim();
			String b = tok[1].trim();

			fileNames[ctr] = a;
			categories[ctr] = b;
			map.put(a, b);
			ctr++;
		}

//			System.out.println("map :");

//			for (Map.Entry<String, String> entry : map.entrySet())
//				System.out.println("file : " + entry.getKey() + ", cat : " + entry.getValue());

		System.out.println("Number of files : " + map.size());
/*		Map<String, String> news = getContentOfCategory("news");

		for (Map.Entry<String, String> entry : news.entrySet())
			out.println(entry);*/
	}

	static void finish()
	{
		out.flush();
		out.close();
	}

//	static Map<String, String> getContentOfCategory(String category) throws FileNotFoundException
	static List<String[]> getContentOfCategory(String category) throws FileNotFoundException
	{
//		Map<String, String> content = new HashMap<>();
		List<String[]> content = new ArrayList<>();

		if (map == null)
		{
			init();
			work();
		}

		for (Map.Entry<String, String> entry : map.entrySet())
		{
			if (entry.getValue().equals(category))
				appendContent(entry.getKey(), content);
		}

		return content;
	}

	static int getNumberOfWords(String category) throws FileNotFoundException
	{
		int cnt = 0;

		if (map == null)
		{
			init();
			work();
		}

		for (Map.Entry<String, String> entry : map.entrySet())
		{
			if (entry.getValue().equals(category))
				cnt += countWords(entry.getKey());
		}

		return cnt;
	}

	static int countWords(String file) throws FileNotFoundException
	{
		List<String> list = new ArrayList<>();
		Scanner in = new Scanner(new File(directory + file));
//		Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
//				+ "Programming/src/com/nlp/" + file));

		while (in.hasNext())
		{
			String line = in.nextLine();
			String[] tok = line.split(" ");

			if (tok.length > 0)
			{
				for (String x : tok)
				{
					x = x.trim();

					if (x.length() > 0)
						list.add(x);
				}
			}
		}

		return list.size();
	}

	static int tot = 0;

//	static void appendContent(String file, Map<String, String> content) throws FileNotFoundException
	static void appendContent(String file, List<String[]> content) throws FileNotFoundException
	{
		System.out.println("\twill append from file : " + file);
		List<String> list = new ArrayList<>();
		Scanner in = new Scanner(new File(directory + file));
//		Scanner in = new Scanner(new File("/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
//				+ "Programming/src/com/nlp/" + file));

		while (in.hasNext())
		{
			String line = in.nextLine();
			String[] tok = line.split(" ");

			if (tok.length > 0)
			{
				for (String x : tok)
				{
					x = x.trim();

					if (x.length() > 0)
						list.add(x);
				}
			}
		}

		tot += list.size();
		System.out.println("file : " + file + ", no. of words : " + list.size() + ", tot : " + tot + ", prev. "
				+ "content size : " + content.size());

		for (String x : list)
		{
			String[] tok = x.split("/");

			if (tok.length == 2)
				content.add(new String[]{tok[0], tok[1]});
			else if (tok.length == 3)
				content.add(new String[]{tok[0] + "/" + tok[1], tok[2]});
		}

		System.out.println("\tcontent size while leaving : " + content.size());
	}

}
