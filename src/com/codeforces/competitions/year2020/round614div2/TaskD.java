package com.codeforces.competitions.year2020.round614div2;

import java.io.*;
import java.util.*;

public class TaskD {

  public static void main(String[] args) {
    new TaskD(System.in, System.out);
  }

  static class Solver implements Runnable {

    long x0, y0, ax, ay, bx, by, xs, ys, t;
    InputReader in;
    PrintWriter out;

    void solve() {
      x0 = in.nextLong();
      y0 = in.nextLong();
      ax = in.nextLong();
      ay = in.nextLong();
      bx = in.nextLong();
      by = in.nextLong();
      xs = in.nextLong();
      ys = in.nextLong();
      t = in.nextLong();

      long prevX = x0;
      long prevY = y0;

      List<Node> nodes = new ArrayList<>();

      nodes.add(new Node(x0, y0));

      for (int i = 1; i < 63; i++) {
        long xx = ax * prevX + bx;
        long yy = ay * prevY + by;

        nodes.add(new Node(xx, yy));
        prevX = xx;
        prevY = yy;

        if (prevX > 1e16 || prevY > 1e16) {
          break;
        }
      }

      nodes.sort(Comparator.comparingLong(o -> o.x + o.y));

      int n = nodes.size();
      int max = 0;
      int startIndex = -1;

      for (int i = 0; i < nodes.size(); i++) {
        Node node = nodes.get(i);

        if (node.x == xs && node.y == ys) {
          startIndex = i;

          break;
        }
      }

      for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
          int ans = 0;

          if (startIndex >= 0 && (startIndex < i || startIndex > j)) {
            ans = 1;
            max = Math.max(max, ans);
          }

          prevX = xs;
          prevY = ys;

          long time = 0;

          for (int k = i; k <= j; k++) {
            long dist = Math.abs(prevX - nodes.get(k).x) + Math.abs(prevY - nodes.get(k).y);

            time += dist;

            if (time <= t) {
              ans++;
              max = Math.max(max, ans);
            }

            prevX = nodes.get(k).x;
            prevY = nodes.get(k).y;
          }

          prevX = xs;
          prevY = ys;
          time = 0;

          if (startIndex >= 0 && (startIndex < i || startIndex > j)) {
            ans = 1;
          } else {
            ans = 0;
          }

          for (int k = j; k >= i; k--) {
            long dist = Math.abs(prevX - nodes.get(k).x) + Math.abs(prevY - nodes.get(k).y);

            time += dist;

            if (time <= t) {
              ans++;
              max = Math.max(max, ans);
            }

            prevX = nodes.get(k).x;
            prevY = nodes.get(k).y;
          }
        }
      }

      out.println(max);
    }

    static class Node {

      long x, y;

      Node(long x, long y) {
        this.x = x;
        this.y = y;
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

  public TaskD(InputStream inputStream, OutputStream outputStream) {
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Thread thread = new Thread(null, new Solver(in, out), "TaskD", 1 << 29);

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

