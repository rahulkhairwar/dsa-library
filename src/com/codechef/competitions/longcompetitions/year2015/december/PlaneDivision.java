package com.codechef.competitions.longcompetitions.year2015.december;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class PlaneDivision
{
	static int t, n;
	static int a, b, c;
	static Line[] allLines, bZero;
	static BigLine[] bigLines, bigBZero;
	static Factorize[] fact;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);
		
		//System.out.println(10e4);
		//solve5();
		new Primes();
		factorizeSolve();
		
		out.flush();
		
		in.close();
		out.close();
	}
	
	static void solve2()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			allLines = new Line[n];
			bZero = new Line[n];

			for (int i = 0; i < n; i++)
				allLines[i] = new Line(in.nextDouble(), in.nextDouble(),
						in.nextDouble());

			Line.sortSlope(allLines, 0, n);
			
/*			System.out.println("after sorting the slopes, the lines look like :");
			
			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", slope : "
						+ allLines[i].slope + ", aByC : " + allLines[i].aByC);*/
			
			for (int i = 0; i < n; i++)
			{
				double currSlope = allLines[i].slope;
				
				int rightMost;
				
				if (i == n - 1)
					rightMost = -1;
				else
					rightMost = Line.searchRightMost(currSlope, i + 1);
				
				if (rightMost == -1)
					continue;
				else
				{
					Line.sortAByC(allLines, i, rightMost + 1);
					
					i = rightMost;
				}
			}
			
			System.out.println("after sorting aByC, the lines look like :");
			
			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", slope : "
						+ allLines[i].slope + ", aByC : " + allLines[i].aByC);
			
			int maxCount = 0;
			
			for (int i = 0; i < n;)
			{
				double currSlope, currAByC;
				
				currSlope = allLines[i].slope;
				currAByC = allLines[i].aByC;
				
				int count = 0;
				
				while (i < n && allLines[i].slope == currSlope)
				{
					count++;
					System.out.println("i : " + i + ", slope : " + allLines[i].slope + ", abyc : " + allLines[i].aByC + ", count : " + count);
					
					while (i < n && allLines[i].slope == currSlope && allLines[i].aByC == currAByC)
						i++;
					
					i++;
				}
				
				if (count > maxCount)
					maxCount = count;
			}
			
			out.println(maxCount);
		}
	}
	
	static void solve3()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			allLines = new Line[n];

			for (int i = 0; i < n; i++)
				allLines[i] = new Line(in.nextDouble(), in.nextDouble(),
						in.nextDouble());

			Line.sortSlope(allLines, 0, n);
			
/*			System.out.println("after sorting the slopes, the lines look like :");
			
			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", slope : "
						+ allLines[i].slope + ", aByC : " + allLines[i].aByC);*/
			
			for (int i = 0; i < n; i++)
			{
				double currSlope = allLines[i].slope;
				
				int rightMost;
				
				if (i == n - 1)
					rightMost = -1;
				else
					rightMost = Line.searchRightMost(currSlope, i + 1);
				
				if (rightMost == -1)
					continue;
				else
				{
					Line.sortAByC(allLines, i, rightMost + 1);
					
					i = rightMost;
				}
			}
			
/*			System.out.println("after sorting aByC, the lines look like :");
			
			for (int i = 0; i < n; i++)
				System.out.println("i : " + i + ", slope : "
						+ allLines[i].slope + ", aByC : " + allLines[i].aByC);*/

			Map<Double, HashMap<Double, Integer>> map = new HashMap<Double, HashMap<Double, Integer>>();
			int[] aZero, bZero;
			int aCount, bCount;
			
			aZero = new int[n];
			bZero = new int[n];
			
			aCount = bCount = 0;

			for (int i = 0; i < n; i++)
			{
/*				if (allLines[i].a == 0)
				{
					aZero[aCount++] = i;
					
					continue;
				}
				else if (allLines[i].b == 0)
				{
					bZero[bCount++] = i;
					
					continue;
				}*/
				
				double currKey = allLines[i].slope;
				
				if (map.containsKey(currKey))
				{
					double currAByC = allLines[i].aByC;
					
					HashMap<Double, Integer> temp = map.get(currKey);
					
					if (temp.containsKey(currAByC))
						//temp.put(currAByC, temp.get(currAByC) + 1);
						continue;
					else
						temp.put(currAByC, 1);
				}
				else
				{
					HashMap<Double, Integer> temp = new HashMap<Double, Integer>();
					
					temp.put(allLines[i].aByC, 1);
					map.put(currKey, temp);
				}
			}

			Iterator<Entry<Double, HashMap<Double, Integer>>> iterator = map
					.entrySet().iterator();
//			int count = 0;
			int max = 0;

			while (iterator.hasNext())
			{
				Entry<Double, HashMap<Double, Integer>> slope = iterator.next();
				int size = slope.getValue().size();
				
				if (size > max)
					max = size;
				
//				System.out.println("count : " + count++ + ", slope : " + slope.getKey() + ", size : " + size);
			}
			
			out.println(max);
		}
	}
	
	static void solve4()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			Line.bZeroCount = Line.bNotZeroCount = 0;
			
			allLines = new Line[n];
			bZero = new Line[n];

			for (int i = 0; i < n; i++)
			{
				double a, b, c;
				
				a = in.nextDouble();
				b = in.nextDouble();
				c = in.nextDouble();
				
				if (b == 0)
					bZero[Line.bZeroCount++] = new Line(a, c);
				else
					allLines[Line.bNotZeroCount++] = new Line(a, b, c);
			}

			Line.sortSlope(allLines, 0, Line.bNotZeroCount);
			
			for (int i = 0; i < Line.bNotZeroCount; i++)
			{
				double currSlope = allLines[i].slope;
				
				int rightMost;
				
				if (i == Line.bNotZeroCount - 1)
					rightMost = -1;
				else
					rightMost = Line.searchRightMost(currSlope, i + 1);
				
				if (rightMost == -1)
					continue;
				else
				{
					Line.sortAByC(allLines, i, rightMost + 1);
					
					i = rightMost;
				}
			}
			
			Map<Double, Set<Double>> map = new HashMap<Double, Set<Double>>();
			
			for (int i = 0; i < Line.bNotZeroCount; i++)
			{
				double currSlope, currAByC;
				
				currSlope = allLines[i].slope;
				currAByC = allLines[i].aByC;
				
				if (map.containsKey(currSlope))
				{
					Set<Double> temp = map.get(currSlope);
					
					temp.add(currAByC);
				}
				else
				{
					Set<Double> temp = new HashSet<Double>();
					
					temp.add(currAByC);
					
					map.put(currSlope, temp);
				}
			}
			
			int max = 0;
			
			Iterator<Entry<Double, Set<Double>>> iterator = map.entrySet().iterator();
			
			while (iterator.hasNext())
			{
				Entry<Double, Set<Double>> line = iterator.next();
				
				int size = line.getValue().size();
				
				if (size > max)
					max = size;
			}
			
			Set<Double> bZeroSet = new HashSet<Double>();
			
			for (int i = 0; i < Line.bZeroCount; i++)
			{
				bZeroSet.add(bZero[i].aByC);
			}
			
			if (bZeroSet.size() > max)
				max = bZeroSet.size();
			
			out.println(max);
		}
	}
	
	static void solve5()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			BigLine.bZeroCount = BigLine.bNotZeroCount = 0;
			
			bigLines = new BigLine[n];
			bigBZero = new BigLine[n];
//			allLines = new Line[n];
//			bZero = new Line[n];

			for (int i = 0; i < n; i++)
			{
				double a, b, c;
				
				a = in.nextDouble();
				b = in.nextDouble();
				c = in.nextDouble();
				
//				System.out.println("i : " + i);
				if (b == 0)
					bigBZero[BigLine.bZeroCount++] = new BigLine(a, c);
				else
					bigLines[BigLine.bNotZeroCount++] = new BigLine(a, b, c);
			}

			BigLine.sortSlope(bigLines, 0, BigLine.bNotZeroCount);
			
			for (int i = 0; i < BigLine.bNotZeroCount; i++)
			{
				BigDecimal currSlope = bigLines[i].slope;
				
				int rightMost;
				
				if (i == BigLine.bNotZeroCount - 1)
					rightMost = -1;
				else
					rightMost = BigLine.searchRightMost(currSlope, i + 1);
				
				if (rightMost == -1)
					continue;
				else
				{
					BigLine.sortAByC(bigLines, i, rightMost + 1);
					
					i = rightMost;
				}
			}
			
			Map<BigDecimal, Set<BigDecimal>> map = new HashMap<BigDecimal, Set<BigDecimal>>();
			
			for (int i = 0; i < BigLine.bNotZeroCount; i++)
			{
				BigDecimal currSlope, currAByC;
				
				currSlope = bigLines[i].slope;
				currAByC = bigLines[i].aByC;
				
				if (map.containsKey(currSlope))
				{
					Set<BigDecimal> temp = map.get(currSlope);
					
					temp.add(currAByC);
				}
				else
				{
					Set<BigDecimal> temp = new HashSet<BigDecimal>();
					
					temp.add(currAByC);
					
					map.put(currSlope, temp);
				}
			}
			
			int max = 0;
			
			Iterator<Entry<BigDecimal, Set<BigDecimal>>> iterator = map.entrySet().iterator();
			
			while (iterator.hasNext())
			{
				Entry<BigDecimal, Set<BigDecimal>> line = iterator.next();
				
				int size = line.getValue().size();
				
				if (size > max)
					max = size;
			}
			
			Set<BigDecimal> bZeroSet = new HashSet<BigDecimal>();
			
			for (int i = 0; i < BigLine.bZeroCount; i++)
			{
				bZeroSet.add(bigBZero[i].aByC);
			}
			
			if (bZeroSet.size() > max)
				max = bZeroSet.size();
			
			out.println(max);
		}
	}
	
	static void factorizeSolve()
	{
		t = in.nextInt();
		
		while (t-- > 0)
		{
			n = in.nextInt();
			
			fact = new Factorize[n];
			
			for (int i = 0; i < n; i++)
			{
				a = in.nextInt();
				b = in.nextInt();
				c = in.nextInt();
				
				fact[i] = new Factorize(a, b, c);
			}
			
/*			System.out.println("lines after factorizing : ");

			for (int i = 0; i < n; i++)
			{
				System.out.println("i : " + i + ", a : " + fact[i].a + ", b : "
						+ fact[i].b + ", c : " + fact[i].c);
			}*/
			
			Map<Pair, Set<Integer>> slopes = new HashMap<Pair, Set<Integer>>();
			
			for (int i = 0; i < n; i++)
			{
				a = (int) fact[i].a;
				b = (int) fact[i].b;
				c = (int) fact[i].c;

//				System.out.println("i : " + i + " => " + fact[i].toString());
				
				Pair temp = new Pair(a, b);
				
				if (slopes.containsKey(temp))
				{
					Set<Integer> tempSet = slopes.get(temp);
					
					tempSet.add(c);
				}
				else
				{
					Set<Integer> tempSet = new HashSet<Integer>();
					
					tempSet.add(c);
					
					slopes.put(temp, tempSet);
				}
			}
			
			Iterator<Entry<Pair, Set<Integer>>> iterator = slopes.entrySet().iterator();
			int max = 0;
			
			int count = 0;
			
			while (iterator.hasNext())
			{
				Entry<Pair, Set<Integer>> temp = iterator.next();
				
/*				System.out.println("count : " + count + ", pair = "
						+ temp.getKey().toString() + ", set.size : "
						+ temp.getValue().size());
				System.out.println("pair's hashcode : "
						+ temp.getKey().hashCode());
*/
				
				count++;
				
				int curr = temp.getValue().size();
				
				if (curr > max)
					max = curr;
			}
			
			out.println(max);
		}
	}
	
	static class Line
	{
		public static int bZeroCount, bNotZeroCount;
		double a, b, c, slope, aByC;
		
		public Line(double a, double c)
		{
			this.a = a;
			this.b = 0d;
			this.c = c;
			
			if (c == 0)
				aByC = Double.MAX_VALUE * (a < 0 ? -1 : 1);
			else
				aByC = a / c;
		}
		
		public Line(double a, double b, double c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
			
			if (a == 0)
				slope = Double.MIN_VALUE;
			else
				slope = - a / b;
			
			if (c == 0)
				aByC = Double.MAX_VALUE * (a < 0 ? -1 : 1);
			else
				aByC = a / c;
		}
		
		public static void sortSlope(Line[] allLines, int from, int to)
		{
			Arrays.sort(allLines, from, to, new Comparator<Line>()
			{
				@Override
				public int compare(Line line1, Line line2)
				{
					return Double.compare(line1.slope, line2.slope);
				}
			});
		}
		
		public static int searchRightMost(double searchValue, int from)
		{
			int first, last, mid;
			
			first = from;
			last = bNotZeroCount;
			
			while (first <= last)
			{
				mid = first + (last - first) / 2;
				
				if (allLines[mid].slope == searchValue)
				{
					if (mid == bNotZeroCount - 1)
						return mid;
					else if (allLines[mid + 1].slope > searchValue)
						return mid;
					else
						first = mid + 1;
				}
				else
					last = mid - 1;
			}
			
			return -1;
		}
		
		public static void sortAByC(Line[] allLines, int from, int to)
		{
			Arrays.sort(allLines, from, to, new Comparator<Line>()
			{
				@Override
				public int compare(Line line1, Line line2)
				{
					return Double.compare(line1.aByC, line2.aByC);
				}
			});
		}
		
	}
	
	static class BigLine
	{
		public static int bZeroCount, bNotZeroCount;
		BigDecimal a, b, c, slope, aByC;
		
		public BigLine(double a, double c)
		{
			this.a = BigDecimal.valueOf(a);
			this.b = BigDecimal.ZERO;
			this.c = BigDecimal.valueOf(c);
			
			if (c == 0)
				aByC = BigDecimal.valueOf(Double.MAX_VALUE).multiply(
						a < 0 ? BigDecimal.valueOf(-1) : BigDecimal.valueOf(1));
			else
				aByC = this.a.divide(this.c, 100, RoundingMode.HALF_UP);
		}
		
		public BigLine(double a, double b, double c)
		{
			this.a = BigDecimal.valueOf(a);
			this.b = BigDecimal.valueOf(b);
			this.c = BigDecimal.valueOf(c);
			
			if (a == 0)
				slope = BigDecimal.valueOf(Double.MIN_VALUE);
			else
				slope = BigDecimal.valueOf(-1).multiply(this.a).divide(this.b, 100, RoundingMode.HALF_UP);
			
			if (c == 0)
				aByC = BigDecimal.valueOf(Double.MAX_VALUE).multiply(
						a < 0 ? BigDecimal.valueOf(-1) : BigDecimal.valueOf(1));
			else
				aByC = this.a.divide(this.c, 100, RoundingMode.HALF_UP);
		}
		
		public static void sortSlope(BigLine[] allLines, int from, int to)
		{
			Arrays.sort(allLines, from, to, new Comparator<BigLine>()
			{
				@Override
				public int compare(BigLine line1, BigLine line2)
				{
					//return Double.compare(line1.slope, line2.slope);
					return line1.slope.compareTo(line2.slope);
				}
			});
		}
		
		public static int searchRightMost(BigDecimal searchValue, int from)
		{
			int first, last, mid;
			
			first = from;
			last = bNotZeroCount;
			
			while (first <= last)
			{
				mid = first + (last - first) / 2;
				
				if (bigLines[mid].slope.compareTo(searchValue) == 0)
				{
					if (mid == bNotZeroCount - 1)
						return mid;
					else if (bigLines[mid + 1].slope.compareTo(searchValue) == 1)
						return mid;
					else
						first = mid + 1;
				}
				else
					last = mid - 1;
			}
			
			return -1;
		}
		
		public static void sortAByC(BigLine[] allLines, int from, int to)
		{
			Arrays.sort(allLines, from, to, new Comparator<BigLine>()
			{
				@Override
				public int compare(BigLine line1, BigLine line2)
				{
					return line1.aByC.compareTo(line2.aByC);
				}
			});
		}
		
	}

	static class Primes
	{
		static int primeCount, primes[];
		
		static
		{
			primes = new int[9600];
			boolean[] arr = new boolean[(int) 1e5 + 1];
			
			primes[0] = 2;
			primes[1] = 3;
			primes[2] = 5;
			
			primeCount = 3;
			
			arr[1] = true;
			arr[2] = false;
			arr[3] = false;
			arr[5] = false;
			
			for (int i = 7; i <= 1e5; i += 2)
			{
				if (arr[i])
					continue;
				else
				{
					primes[primeCount++] = i;

					int counter = 2;
					
					while (counter * i <= 1e5)
						arr[counter++ * i] = true;
				}
			}
			
//			System.out.println("total primes : " + primeCount);
		}
		
	}
	
	static class Factorize
	{
		double a, b, c;
		
		public Factorize(double a, double b, double c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
			
			factorize();
		}
		
		void factorize()
		{
			double absA, absB, absC, sqrt;
			
			absA = Math.abs(a);
			absB = Math.abs(b);
			absC = Math.abs(c);
			
			if (absA == 0)
			{
				if (absC == 0)
					sqrt = absB;
				else
					sqrt = (int) Math.min(absB, absC);
			}
			else if (absB == 0)
			{
				if (absC == 0)
					sqrt = absA;
				else
					sqrt = (int) Math.min(absA, absC);
			}
			else if (absC == 0)
				sqrt = (int) Math.min(absA, absB);
			else
				sqrt = (int) Math.min(absA, Math.min(absB, absC));
			
			for (int i = 0; i < Primes.primeCount && Primes.primes[i] <= sqrt; i++)
			{
				int currPrime = Primes.primes[i];

				while (a % currPrime == 0 && b % currPrime == 0
						&& c % currPrime == 0)
				{
					a /= currPrime;
					b /= currPrime;
					c /= currPrime;
				}
			}
		}
		
		@Override
		public String toString()
		{
			return "a : " + a + ", b : " + b + ", c : " + c;
		}
		
	}
	
	static class Pair
	{
		static int count = 0;
		int a, b;
		
		public Pair(int a, int b)
		{
			this.a = a;
			this.b = b;
		}
		
		@Override
		public int hashCode()
		{
			return a ^ b;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			Pair temp;
			
			if (obj instanceof Pair)
				temp = (Pair) obj;
			else
				return false;
			
			if (this.a == temp.a && this.b == temp.b)
				return true;
			else
				return false;
		}
		
		@Override
		public String toString()
		{
			return "a : " + a + ", b : " + b;
		}
		
	}
	
	static class InputReader
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

	}

	static class OutputWriter
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
