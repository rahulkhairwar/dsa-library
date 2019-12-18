package com.codeforces.competitions.year2019.globalround6;

import java.io.*;
import java.util.*;

public class TaskD {

  public static void main(String[] args) {
    new TaskD(System.in, System.out);
  }

  static class Solver implements Runnable {

    int n, m;
    Edge[] edges;
    long[] tot;
    int[] component;
    boolean[] vis;
    List<Integer>[] adj;
    Map<Integer, TreeSet<Integer>> asc, desc;
    List<Edge> ans;
    InputReader in;
    PrintWriter out;

    void solve() {
      n = in.nextInt();
      m = in.nextInt();
      tot = new long[n];
      component = new int[n];
      vis = new boolean[n];
      adj = new List[n];
      edges = new Edge[m];
      asc = new HashMap<>();
      desc = new HashMap<>();

      for (int i = 0; i < n; i++) {
        adj[i] = new ArrayList<>();
      }

      for (int i = 0; i < m; i++) {
        int u = in.nextInt() - 1;
        int v = in.nextInt() - 1;
        long d = in.nextLong();

        adj[u].add(v);
        adj[v].add(u);
        edges[i] = new Edge(u, v, d);
        tot[u] -= d;
        tot[v] += d;
      }

      int counter = 0;
      Comparator<Integer> ascendingComp = (o1, o2) -> {
        if (tot[o1] == tot[o2]) {
          return Integer.compare(o1, o2);
        }

        return Long.compare(tot[o1], tot[o2]);
      };
      Comparator<Integer> descendingComp = (o1, o2) -> {
        if (tot[o1] == tot[o2]) {
          return Integer.compare(o2, o1);
        }

        return Long.compare(tot[o2], tot[o1]);
      };

      for (int i = 0; i < n; i++) {
        if (!vis[i]) {
          counter++;

          TreeSet<Integer> aset = new TreeSet<>(ascendingComp);
          TreeSet<Integer> dset = new TreeSet<>(descendingComp);

          asc.put(counter, aset);
          desc.put(counter, dset);
          vis[i] = true;
          dfs(i, counter, aset, dset);
        }
      }

      ans = new ArrayList<>();

      for (Map.Entry<Integer, TreeSet<Integer>> entry : asc.entrySet()) {
        find(entry.getKey());
      }

      out.println(ans.size());

      for (Edge edge : ans) {
        out.println(edge.from + 1 + " " + (edge.to + 1) + " " + edge.debt);
      }
    }

    void find(int comp) {
      TreeSet<Integer> aset = asc.get(comp);
      TreeSet<Integer> dset = desc.get(comp);

      while (true) {
        if (aset.size() == 0 || dset.size() == 0) {
          break;
        }

        int giver = aset.pollFirst();
        int taker = dset.pollFirst();

        if (tot[giver] >= 0) {
          break;
        }

        long abs = Math.abs(tot[giver]);

        if (abs <= tot[taker]) {
          tot[taker] -= abs;
          tot[giver] = 0;
          ans.add(new Edge(giver, taker, abs));

          if (tot[taker] > 0) {
            dset.add(taker);
          }
        } else {
          tot[giver] += tot[taker];
          ans.add(new Edge(giver, taker, tot[taker]));
          tot[taker] = 0;
          aset.add(giver);
        }
      }
    }

    void dfs(int node, int counter, TreeSet<Integer> aset, TreeSet<Integer> dset) {
      component[node] = counter;
      aset.add(node);
      dset.add(node);

      for (int x : adj[node]) {
        if (!vis[x]) {
          vis[x] = true;
          dfs(x, counter, aset, dset);
        }
      }
    }

    static class Edge {

      int from, to;
      long debt;

      Edge(int from, int to, long debt) {
        this.from = from;
        this.to = to;
        this.debt = debt;
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

