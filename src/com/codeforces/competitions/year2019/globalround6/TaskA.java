package com.codeforces.competitions.year2019.globalround6;

import java.io.*;

public class TaskA {

  public static void main(String[] args) {
    new TaskA(System.in, System.out);
  }

  static class Solver implements Runnable {

    int n;
    BufferedReader in;
    PrintWriter out;

    void solve() throws IOException {
      n = Integer.parseInt(in.readLine());

      while (n-- > 0) {
        char[] s = in.readLine().toCharArray();
        int zeroes = 0;
        boolean two = false;
        int sum = 0;

        for (char ch : s) {
          if (ch == '0' && zeroes == 0) {
            zeroes++;
            sum += (ch - '0');

            continue;
          }

          if ((ch - '0') % 2 == 0) {
            two = true;
          }

          sum += (ch - '0');
        }

        if (zeroes > 0 && two && sum % 3 == 0) {
          out.println("red");
        } else {
          out.println("cyan");
        }
      }
    }

    Solver(BufferedReader in, PrintWriter out) {
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

  public TaskA(InputStream inputStream, OutputStream outputStream) {
    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
    PrintWriter out = new PrintWriter(outputStream);
    Thread thread = new Thread(null, new Solver(in, out), "TaskA", 1 << 29);

    try {
      thread.start();
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      out.flush();
      out.close();
    }
  }

}

