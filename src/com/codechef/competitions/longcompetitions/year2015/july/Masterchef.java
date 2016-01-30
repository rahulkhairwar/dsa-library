package com.codechef.competitions.longcompetitions.year2015.july;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

class Masterchef
{
	private static int t, numberOfDishes, budget, numberOfJudges;
	private static Node root;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		Masterchef chef = new Masterchef();

		reader = chef.new InputReader(System.in);
		writer = chef.new OutputWriter(System.out);
		
		getAttributes();

		writer.flush();

		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		int countNegative;
		long currentRating, sum;
		t = reader.nextInt();

		for (int i = 0; i < t; i++)
		{
			numberOfDishes = reader.nextInt();
			budget = reader.nextInt();
			numberOfJudges = reader.nextInt();
			
			root = new Node(null, null);	// will create a fresh tree for each testcase

			countNegative = 0;
			sum = 0;

			Dish[] dishes = new Dish[numberOfDishes];
			
			List<Judge> judges = new ArrayList<Judge>();

//			 creating all dish objects with their ratings, and
//			 initial minimum cost 1000, which will be changed later.
//			 Initial cost as 1000, since maximum possible cost is 500
			for (int j = 0; j < numberOfDishes; j++)
			{
				currentRating = reader.nextLong();
				dishes[j] = new Dish(currentRating, 1000);

				if (currentRating < 0)
					countNegative++;

				sum += currentRating;
			}

			for (int j = 0; j < numberOfJudges; j++)
				judges.add(new Judge(reader.nextInt(), reader.nextInt(),
						reader.nextInt()));

			Dish[] dishesWithNegativeRating = new Dish[countNegative];
			
			// will use this sorted list in the whole createTree method
			Judge.sortAccordingToRight(judges);
			
			root = createTree(root, judges);
			
			int length = dishes.length;
			int tempCount = 0;
			Dish temp;
			int totalCost = 0;
			int totalNegativeRating = 0;
			
			for (int j = 0; j < length && tempCount < countNegative; j++)
			{
				temp = dishes[j];
				
				if (temp.rating < 0)
				{
					temp.cost = queryTree(root, j + 1, 1000);
					
					if (temp.cost <= budget)
					{
						dishesWithNegativeRating[tempCount++] = temp;
						totalCost += temp.cost;
						totalNegativeRating += temp.rating;
					}
				}
			}
			
			if (totalCost <= budget)
			{
				writer.println(sum - totalNegativeRating);
				
				continue;
			}
			
			countNegative = tempCount;

			Select[][] dp = new Select[countNegative + 1][budget + 1];

			dp[0][0] = new Select(true, 0);

			for (int j = 1; j <= countNegative; j++)
			{
				for (int k = 0; k <= budget; k++)
				{
					if (dp[j - 1][k] != null)
					{
						if (dp[j][k] == null)
							dp[j][k] = new Select(true, dp[j - 1][k].minSum);
						else
							dp[j][k].minSum = (long) Math.min(dp[j][k].minSum,
									dp[j - 1][k].minSum);

						Dish tempDish = dishesWithNegativeRating[j - 1];

						if (k + tempDish.cost <= budget)
							dp[j][k + tempDish.cost] = new Select(true,
									tempDish.rating + dp[j - 1][k].minSum);
					}
				}
			}
			
			long minSumInDp = dp[countNegative][0].minSum;

			// finding the minimum possible sum to subtract from the total sum
			for (int j = 1; j <= budget; j++)
				if (dp[countNegative][j] != null && dp[countNegative][j].minSum < minSumInDp)
					minSumInDp = dp[countNegative][j].minSum;

			writer.println(sum - minSumInDp);
		}
	}
	
	// the list of judges should be sorted according to
	// their end points
	// for the first time, the root comes in as the node parameter
	public static Node createTree(Node node, List<Judge> allJudges)
	{
		/**
		 * From the sorted(acc. to end) list of judges, find the median
		 * endpoint and we'll separate the judges based on that median.
		 * All intervals which end before this median will be added to
		 * intervals which start after this median will be added to
		 * listForLeftNode, and all listForRightNode. Rest of the judges
		 * will remain in the list of intervals of the current node, as
		 * all of them will intersect the median.
		 */
		
		int size = allJudges.size();
		
		if (node == null || size == 0)
			return null;

		int median = allJudges.get(size / 2).interval.right;

		List<Judge> listForLeftSubtree, listForRightSubtree;

		listForLeftSubtree = new ArrayList<Judge>();
		listForRightSubtree = new ArrayList<Judge>();
		node.intersectingIntervalsSortedByStartPoints = new ArrayList<Judge>();
		node.intersectingIntervalsSortedByEndPoints = new ArrayList<Judge>();
		// ^ will store the list of intersecting intervals in the node itself, 
		// along with the median

		Judge tempJudge;
		
		for (int i = 0; i < size; i++)
		{
			tempJudge = allJudges.get(i);
			
			if (tempJudge.interval.right < median)
				listForLeftSubtree.add(tempJudge);
			else if (tempJudge.interval.left > median)
				listForRightSubtree.add(tempJudge);
			else
				node.intersectingIntervalsSortedByEndPoints.add(tempJudge);
		}
		
		node.middlePoint = median;

		// adding all the intervals in the list to be sorted acc. to start points
		node.intersectingIntervalsSortedByStartPoints
				.addAll(node.intersectingIntervalsSortedByEndPoints);

		// sorting this list sorted according to the start points
		Node.sortAccToStartPoints(node.intersectingIntervalsSortedByStartPoints);
		
		if (listForLeftSubtree.size() > 0)
		{
			node.leftSubtree = new Node(null, null);
			node.leftSubtree = createTree(node.leftSubtree, listForLeftSubtree);
		}
		
		if (listForRightSubtree.size() > 0)
		{
			node.rightSubtree = new Node(null, null);
			node.rightSubtree = createTree(node.rightSubtree, listForRightSubtree);
		}
		
		return node;
	}

	public static int queryTree(Node node, int searchValue, int currMinCost)
	{
		if (node == null)
			return 1000;
		
		Judge tempJudge;
		int size;

		if (searchValue < node.middlePoint)
		{
			size = node.intersectingIntervalsSortedByStartPoints.size();
			
			for (int i = 0; i < size; i++)
			{
				tempJudge = node.intersectingIntervalsSortedByStartPoints
						.get(i);
				
				if (tempJudge.interval.left > searchValue)
					break;

				if (tempJudge.cost < currMinCost)
					currMinCost = tempJudge.cost;
			}
		}
		else
		{
			size = node.intersectingIntervalsSortedByEndPoints.size();
			
			for (int i = size - 1; i >= 0; i--)
			{
				tempJudge = node.intersectingIntervalsSortedByEndPoints.get(i);
				
				if (tempJudge.interval.right < searchValue)
					break;
				
				if (tempJudge.cost < currMinCost)
					currMinCost = tempJudge.cost;
			}
		}
		
		int leftMin, rightMin;
		
		leftMin = rightMin = 1000;

		if (searchValue < node.middlePoint)
			leftMin = queryTree(node.leftSubtree, searchValue, currMinCost);
		else
			rightMin = queryTree(node.rightSubtree, searchValue, currMinCost);

		return Math.min(currMinCost, Math.min(leftMin, rightMin));
	}

	// class to store the ratings and minimum cost of removing them
	public static class Dish
	{
		long rating;
		int cost;

		public Dish(long rating, int cost)
		{
			this.rating = rating;
			this.cost = cost;
		}

	}
	
	// class to store range and cost of all judges,
	// and then sort according to the cost
	public static class Judge
	{
		Interval interval;
		int cost;

		public Judge(int left, int right, int cost)
		{
			interval = new Interval(left, right);
			this.cost = cost;
		}

		// sorts acc. to the start points
		public static void sortAccordingToLeft(Judge allJudges[])
		{
			Arrays.sort(allJudges, new Comparator<Judge>()
			{
				@Override
				public int compare(Judge judge1, Judge judge2)
				{
					return judge1.interval.left - judge2.interval.left;
				}
			});
		}
		
		// sorts acc. to the end points
		public static void sortAccordingToRight(List<Judge> allJudges)
		{
			allJudges.sort(new Comparator<Judge>()
			{
				@Override
				public int compare(Judge judge1, Judge judge2)
				{
					return judge1.interval.right - judge2.interval.right;
				}
			});
		}
		
		// sorts acc. to the costs
		public static void sortAccordingToCost(Judge allJudges[])
		{
			Arrays.sort(allJudges, new Comparator<Judge>()
			{
				@Override
				public int compare(Judge judge1, Judge judge2)
				{
					return judge1.cost - judge2.cost;
				}
			});
		}

	}
	
	// wrapper class to store an interval
	public static class Interval
	{
		int left;
		int right;
		
		public Interval(int left, int right)
		{
			this.left = left;
			this.right = right;
		}
		
	}
	
	// Judge class acts as the interval class here
	public static class Node
	{
		List<Judge> intersectingIntervalsSortedByStartPoints;
		List<Judge> intersectingIntervalsSortedByEndPoints;
		Node leftSubtree;
		Node rightSubtree;
		int middlePoint;	// the median of endpoints
		
		public Node(Node leftSubtree, Node rightSubtree)
		{
			this.leftSubtree = leftSubtree;
			this.rightSubtree = rightSubtree;
		}
		
		public static void sortAccToStartPoints(List<Judge> allJudges)
		{
			allJudges.sort(new Comparator<Judge>()
			{
				@Override
				public int compare(Judge judge1, Judge judge2)
				{
					return judge1.interval.left - judge2.interval.left;
				}
			});
		}
		
	}

	// class to use in the dp[][] array
	public static class Select
	{
		boolean doKeep;
		long minSum;

		public Select(boolean doKeep, long minSum)
		{
			this.doKeep = doKeep;
			this.minSum = minSum;
		}

	}

	class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

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

		public float nextFloat() // problematic
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

		public double nextDouble() // not completely accurate
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

	}

	class OutputWriter
	{
		private PrintWriter writer;

		public OutputWriter(OutputStream stream)
		{
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					stream)));
		}

		public OutputWriter(Writer writer)
		{
			this.writer = new PrintWriter(writer);
		}

		public void println(int x)
		{
			writer.println(x);
		}

		public void print(int x)
		{
			writer.print(x);
		}

		public void println(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(int array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i] + " ");
		}

		public void println(long x)
		{
			writer.println(x);
		}

		public void print(long x)
		{
			writer.print(x);
		}

		public void println(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				println(array[i]);
		}

		public void print(long array[], int size)
		{
			for (int i = 0; i < size; i++)
				print(array[i]);
		}

		public void println(float num)
		{
			writer.println(num);
		}

		public void print(float num)
		{
			writer.print(num);
		}

		public void println(double num)
		{
			writer.println(num);
		}

		public void print(double num)
		{
			writer.print(num);
		}

		public void println(String s)
		{
			writer.println(s);
		}

		public void print(String s)
		{
			writer.print(s);
		}

		public void println()
		{
			writer.println();
		}

		public void printSpace()
		{
			writer.print(" ");
		}

		public void flush()
		{
			writer.flush();
		}

		public void close()
		{
			writer.close();
		}

	}

}

/*

6
6 2 1
-5 8 2 -6 3 -8
1 6 1
6 3 1
-5 8 2 -6 3 -8
1 6 1
8 4 1
-8 -19 3 -3 -5 5 -4 -2
1 8 1
8 4 1
-8 -19 3 -3 -5 5 -4 -20
1 8 1
5 10 5
10 -2 -5 7 -10
1 1 5
2 4 10
4 4 12
3 4 10
1 5 15
5 10 6
10 -2 -5 7 -10
1 1 5
2 4 10
4 4 12
3 4 10
1 5 15
5 5 2

answers : 8, 13, 3, 1, 5, 10

1
5 10 5
10 -2 -5 7 -10
1 1 5
2 4 10
4 4 12
3 4 10
1 5 15

1
5 10 6
10 -2 -5 7 -10
1 1 5
2 4 10
4 4 12
3 4 10
1 5 15
5 5 2

*/
