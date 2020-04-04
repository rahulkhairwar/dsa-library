package com.a2oj.ladder.CodeforcesDiv2D;

import java.io.*;

public class Problem17 {

  public static void main(String[] args) {
    try {
      new Problem17(System.in, System.out);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static class Solver implements Runnable {

    char[] s;
    int cntA, cntB;
    long totalA, totalB, evenA, evenB;
    long[] occA, occB;
    BufferedReader in;
    PrintWriter out;

    void solve() throws IOException {
      s = in.readLine().toCharArray();
      occA = new long[2];
      occB = new long[2];

      for (int i = 0; i < s.length; i++) {
        if (s[i] == 'a') {
          cntA++;
          totalA += cntA;
          occA[i % 2]++;
          evenA += occA[(i % 2) ^ 1];
        } else {
          cntB++;
          totalB += cntB;
          occB[i % 2]++;
          evenB += occB[(i % 2) ^ 1];
        }
      }

      out.printf("%d %d", evenA + evenB, (totalA + totalB - evenA - evenB));
    }

    public Solver(BufferedReader in, PrintWriter out) {
      this.in = in;
      this.out = out;
    }

    @Override
    public void run() {
      try {
        solve();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  private Problem17(InputStream inputStream, OutputStream outputStream) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
    PrintWriter out = new PrintWriter(outputStream);
    Thread thread = new Thread(null, new Solver(in, out), "Problem17", 1 << 29);

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
