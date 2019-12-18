package com.codeforces.competitions.year2019.globalround6;

import java.io.*;
import java.util.*;

public class TaskB {

  public static void main(String[] args) {
    new TaskB(System.in, System.out);
  }

  static class Solver implements Runnable {

    int t;
    InputReader in;
    PrintWriter out;

    void solve() {
      t = in.nextInt();

      outer:
      while (t-- > 0) {
        long x = in.nextLong();
        long n = (x - 21) / 14;
        Set<Long> set = new HashSet<>();

        set.add((long) 21 - 1);
        set.add((long) 21 - 2);
        set.add((long) 21 - 3);
        set.add((long) 21 - 4);
        set.add((long) 21 - 5);
        set.add((long) 21 - 6);

        for (long i = Math.max(0, n - 2); i <= n + 2; i++) {
          long rem = x - 14 * i;

          if (rem < 21 && set.contains(rem)) {
            out.println("YES");

            continue outer;
          }
        }

        out.println("NO");
      }
    }

    Solver(InputReader in, PrintWriter out) {
      this.in = in;
      this.out = out;
    }

    @Override
    public void run() {
			solve();
		}

  }

  static class InputReader {

    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;

    public int read() {
			if (numChars == -1) {
				throw new InputMismatchException();
			}

      if (curChar >= numChars) {
        curChar = 0;
        try {
          numChars = stream.read(buf);
        } catch (IOException e) {
          throw new InputMismatchException();
        }
				if (numChars <= 0) {
					return -1;
				}
      }

      return buf[curChar++];
    }

    public int nextInt() {
      int c = read();

			while (isSpaceChar(c)) {
				c = read();
			}

      int sgn = 1;

      if (c == '-') {
        sgn = -1;
        c = read();
      }

      int res = 0;

      do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}

        res *= 10;
        res += c & 15;

        c = read();
      } while (!isSpaceChar(c));

      return res * sgn;
    }

    public long nextLong() {
      int c = read();

			while (isSpaceChar(c)) {
				c = read();
			}

      int sign = 1;

      if (c == '-') {
        sign = -1;

        c = read();
      }

      long result = 0;

      do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}

        result *= 10;
        result += c & 15;

        c = read();
      } while (!isSpaceChar(c));

      return result * sign;
    }

    public boolean isSpaceChar(int c) {
      return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public void close() {
      try {
        stream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    InputReader(InputStream stream) {
      this.stream = stream;
    }

  }

  public TaskB(InputStream inputStream, OutputStream outputStream) {
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Thread thread = new Thread(null, new Solver(in, out), "TaskB", 1 << 29);

    try {
      thread.start();
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      in.close();
      out.flush();
      out.close();
    }
  }

}

