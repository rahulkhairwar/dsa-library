package com.codechef.competitions.longcompetitions.year2015.february;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class ChefAndStrings
{
	static String p;
	static int q, l, r, length;
	static char a, b;
	static long[][] ch, ce, cf, he, hf, ef;
	static InputReader in;
	static OutputWriter out;
	
	public static void main(String[] args)
	{
		in = new InputReader(System.in);
		out = new OutputWriter(System.out);
		
		solve();
		
		out.flush();
		
		in.close();
		out.close();
	}

	static void solve()
	{
		p = in.next();
		length = p.length();
		
		pre();
		
		System.out.println("c->h");
		for (int i = 0; i < length; i++)
			System.out.print(ch[0][i] + " ");
		
		System.out.println("\nh->c");
		for (int i = 0; i < length; i++)
			System.out.print(ch[1][i] + " ");
		
		System.out.println("\nc->e");
		for (int i = 0; i < length; i++)
			System.out.print(ce[0][i] + " ");
		
		System.out.println("\ne->c");
		for (int i = 0; i < length; i++)
			System.out.print(ce[1][i] + " ");
		
		System.out.println("\nc->f");
		for (int i = 0; i < length; i++)
			System.out.print(cf[0][i] + " ");
		
		System.out.println("\nf->c");
		for (int i = 0; i < length; i++)
			System.out.print(cf[1][i] + " ");
		
		System.out.println("\nh->e");
		for (int i = 0; i < length; i++)
			System.out.print(he[0][i] + " ");
		
		System.out.println("\ne->h");
		for (int i = 0; i < length; i++)
			System.out.print(he[1][i] + " ");
		
		System.out.println("\nh->f");
		for (int i = 0; i < length; i++)
			System.out.print(hf[0][i] + " ");
		
		System.out.println("\nf->h");
		for (int i = 0; i < length; i++)
			System.out.print(hf[1][i] + " ");
		
		System.out.println("\ne->f");
		for (int i = 0; i < length; i++)
			System.out.print(ef[0][i] + " ");
		
		System.out.println("\nf->e");
		for (int i = 0; i < length; i++)
			System.out.print(ef[1][i] + " ");
		
		q = in.nextInt();
		
		for (int i = 0; i < q; i++)
		{
			a = (char) in.read();
			in.read();
			b = (char) in.read();
			l = in.nextInt() - 1;
			r = in.nextInt() - 1;
			
			if (a == 'c')
			{
				if (b == 'h')
					out.println(ch[0][r] - (l > 0 ? ch[0][l - 1] : 0));
				else if (b == 'e')
					out.println(ce[0][r] - (l > 0 ? ce[0][l - 1] : 0));
				else
					out.println(cf[0][r] - (l > 0 ? cf[0][l - 1] : 0));
			}
			else if (a == 'h')
			{
				if (b == 'c')
					out.println(ch[1][r] - (l > 0 ? ch[1][l - 1] : 0));
				else if (b == 'e')
					out.println(he[0][r] - (l > 0 ? he[0][l - 1] : 0));
				else
					out.println(hf[0][r] - (l > 0 ? hf[0][l - 1] : 0));
			}
			else if (a == 'e')
			{
				if (b == 'c')
					out.println(ce[1][r] - (l > 0 ? ce[1][l - 1] : 0));
				else if (b == 'h')
					out.println(ch[1][r] - (l > 0 ? ch[1][l - 1] : 0));
				else
					out.println(cf[0][r] - (l > 0 ? cf[0][l - 1] : 0));
			}
			else
			{
				if (b == 'c')
					out.println(cf[1][r] - (l > 0 ? cf[1][l - 1] : 0));
				else if (b == 'h')
					out.println(hf[1][r] - (l > 0 ? hf[1][l - 1] : 0));
				else
					out.println(ef[1][r] - (l > 0 ? ef[1][l - 1] : 0));
			}
		}
	}
	
	static void pre()
	{
		ch = new long[2][length];
		ce = new long[2][length];
		cf = new long[2][length];
		he = new long[2][length];
		hf = new long[2][length];
		ef = new long[2][length];
		
		int countC, countH, countE, countF;
		
		countC = countH = countE = countF = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'c')
				countC++;
			else if (p.charAt(i) == 'h')
				ch[0][i] = (i > 0 ? ch[0][i - 1] : 0) + countC;
		}
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'h')
				countH++;
			else if (p.charAt(i) == 'c')
				ch[1][i] = (i > 0 ? ch[1][i - 1] : 0) + countH;
		}
		
		countC = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'c')
				countC++;
			else if (p.charAt(i) == 'e')
				ce[0][i] = (i > 0 ? ce[0][i - 1] : 0) + countC;
		}
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'e')
				countE++;
			else if (p.charAt(i) == 'c')
				ce[1][i] = (i > 0 ? ce[1][i - 1] : 0) + countE;
		}
		
		countC = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'c')
				countC++;
			else if (p.charAt(i) == 'f')
				cf[0][i] = (i > 0 ? cf[0][i - 1] : 0) + countC;
		}
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'f')
				countF++;
			else if (p.charAt(i) == 'c')
				cf[1][i] = (i > 0 ? cf[1][i - 1] : 0) + countF;
		}
		
		countH = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'h')
				countH++;
			else if (p.charAt(i) == 'e')
				he[0][i] = (i > 0 ? he[0][i - 1] : 0) + countH;
		}
		
		countE = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'e')
				countE++;
			else if (p.charAt(i) == 'h')
				he[1][i] = (i > 0 ? he[1][i - 1] : 0) + countE;
		}
		
		countH = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'h')
				countH++;
			else if (p.charAt(i) == 'f')
				hf[0][i] = (i > 0 ? hf[0][i - 1] : 0) + countH;
		}
		
		countF = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'f')
				countF++;
			else if (p.charAt(i) == 'h')
				hf[1][i] = (i > 0 ? hf[1][i - 1] : 0) + countF;
		}
		
		countE = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'e')
				countE++;
			else if (p.charAt(i) == 'f')
				ef[0][i] = (i > 0 ? ef[0][i - 1] : 0) + countE;
		}
		
		countF = 0;
		
		for (int i = 0; i < length; i++)
		{
			if (p.charAt(i) == 'f')
				countF++;
			else if (p.charAt(i) == 'e')
				ef[1][i] = (i > 0 ? ef[1][i - 1] : 0) + countF;
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

// TLE!!

// make 4 arrays : for l, r, a, b and count the number in a single traversal for
// all the cases.
// Also, make some other arrays for getting the solution.
// Lastly, call the function just once.

/*
 * 
test cases :

checfcheff
5
c h 1 10
c f 1 10
e c 1 10
c f 1 5
c f 6 10

cheffehfehfehfehfccchhehehfehfccfehcfhcfhefchfhcefhcechfehfhefchfchfchefchchfchcfehchfehefchfeeeeeeeeeeeeeeeeeceeeeeeeeeheeeeeeeehhhhhhhhhhhcffffffffffffffc
20
c h 1 50
c f 25 152
e c 12 24
c f 23 55
c f 6 140
c h 2 129
c f 5 156
e c 5 60
c f 1 59
c f 61 69
c h 100 156
c f 103 145
e c 16 107
c f 45 127
c f 66 106
c h 18 108
c f 46 105
e c 1 156
c f 1 156
c f 6 150

chefchfehfcfhcfhecfhfhcfhcfhfhefhefhcfhcfhefcecccccccccehfchefchefhecfffffffehcfehhhhhhcfehhhhhhhhhhhcfffffffffffhecfehfchechfhecfhcefhcfhfhefhcefhechcechfc
30
c h 1 50
c f 25 152
e c 12 24
c f 23 55
c f 6 140
c h 2 129
c f 5 156
e c 5 60
c f 1 59
c f 61 69
c h 100 156
c f 103 145
e c 16 107
c f 45 127
c f 66 106
c h 18 108
c f 46 105
e c 1 156
c f 1 156
c f 6 150
c h 45 145
c f 28 142
e c 102 154
c f 23 156
c f 61 145
c h 21 120
c f 51 151
e c 45 120
c f 10 129
c f 61 139

*/
