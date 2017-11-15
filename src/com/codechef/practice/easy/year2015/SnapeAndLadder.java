package com.codechef.practice.easy.year2015;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.InputMismatchException;

class SnapeAndLadder
{
	private static int t, b, ls;
	private static InputReader reader;
	private static OutputWriter writer;
	
	public static void main(String[] args)
	{
		SnapeAndLadder ladder = new SnapeAndLadder();
		
		reader = ladder.new InputReader(System.in);
		writer = ladder.new OutputWriter(System.out);
		
		getAttributes();
		
		writer.flush();
		
		reader.close();
		writer.close();
	}
	
	public static void getAttributes()
	{
		double answer1, answer2;
		String finalAnswer;
		
		t = reader.nextInt();
		
		for (int i = 0; i < t; i++)
		{
			b = reader.nextInt();
			ls = reader.nextInt();
			
			finalAnswer = "";
			
			answer1 = Math.sqrt(square(ls) + square(b));
			answer2 = Math.sqrt(square(ls) - square(b));
			
			finalAnswer += answer2 + " " + answer1;
			
			writer.println(finalAnswer);
		}
	}
	
	public static int square(int number)
	{
		return number * number;
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
        
        public void println(long x)
        {
            writer.println(x);
        }
 
        public void print(int x)
        {
            writer.print(x);
        }

        public void print(long x)
        {
            writer.println(x);
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
