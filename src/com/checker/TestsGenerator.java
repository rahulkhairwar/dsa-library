package com.checker;

import com.checker.dto.Edge;
import com.checker.util.GeneratorUtils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class TestsGenerator
{
	public static void main(String[] args)
	{
		try
		{
			PrintWriter out = new PrintWriter(new File(
					"/Users/rahulkhairwar/Documents/IntelliJ IDEA Workspace/Competitive "
							+ "Programming/src/com/checker/input.txt"));
			Generator generator = new Generator(out);

			generator.generate();
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private static class Generator
	{
		PrintWriter out;

		void generate()
		{
			int v = (int) (Math.random() * 20) + 2;
			int e = Math.min((int) (Math.random() * v), v * (v - 1) / 2);

			out.println(v + " " + e);

//			System.out.println("will generate graph.");
			List<Edge> list = GeneratorUtils.generateRandomGraph(v, e, false);
//			System.out.println("graph generated.");

			for (Edge ed : list)
				out.println(ed.from + " " + ed.to);
		}

		Generator(PrintWriter out)
		{
			this.out = out;
		}
	}

	public static List<Point> getRandomTreeAsEdges(int v, boolean isZeroBased)
	{
		List<Point> edges = new ArrayList<>();

		for (int i = 1; i < v; i++)
			edges.add(new Point((int) (Math.random() * i), i));

		if (!isZeroBased)
		{
			for (Point pt : edges)
			{
				pt.x++;
				pt.y++;
			}
		}

		return edges;
	}

}
