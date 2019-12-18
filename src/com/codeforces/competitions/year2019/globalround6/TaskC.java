package com.codeforces.competitions.year2019.globalround6;

import java.io.*;
import java.util.*;

public class TaskC {

  public static void main(String[] args) {
    new TaskC(System.in, System.out);
  }

  static class Solver implements Runnable {

    int r, c;
    InputReader in;
    PrintWriter out;

    void solve() {
      r = in.nextInt();
      c = in.nextInt();

      if (r == 1 && c == 1) {
        out.println(0);

        return;
      }

      boolean invert = false;

      if (r > c) {
        int temp = r;

        r = c;
        c = temp;
        invert = true;
      }

      int[][] mat = new int[r][c];
      int i = 1;
      int j = 1;
      int counter = 3;
      int turn = 0;

      mat[0][0] = 2;

      while (i < r || j < c) {
        if (turn == 0) {
//					row.
          if (j == c) {
            turn ^= 1;
            continue;
          }

          mat[0][j] = counter;
          j++;
        } else {
//					column.
          if (i == r) {
            turn ^= 1;
            continue;
          }

          mat[i][0] = counter;
          i++;
        }

        counter++;
        turn ^= 1;
      }

      for (i = 1; i < r; i++) {
        for (j = 1; j < c; j++) {
          mat[i][j] = mat[i][0] * mat[0][j];
        }
      }

      if (!invert) {
        for (i = 0; i < r; i++, out.println()) {
          for (j = 0; j < c; j++) {
            out.print(mat[i][j] + " ");
          }
        }
      } else {
        for (i = 0; i < c; i++, out.println()) {
          for (j = 0; j < r; j++) {
            out.print(mat[j][i] + " ");
          }
        }
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

  public TaskC(InputStream inputStream, OutputStream outputStream) {
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Thread thread = new Thread(null, new Solver(in, out), "TaskC", 1 << 29);

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

