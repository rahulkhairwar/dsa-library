package com.codechef.competitions.longcompetitions.year2015.september;

import java.util.Scanner;

public class Sumit
{
	public static void main(String[] args)
	{
		int n;
		
		Scanner s = new Scanner(System.in);
		
		n = s.nextInt();
		
		int input[] = new int[n];
		
		for (int i = 0 ;i < n; i++)
		{
			input[i] = s.nextInt();
		}
		
		int ans = max(input, n);
		
		System.out.println(ans);
	}
	
	static int max(int inp[], int n)
	{
		int a[], b[], c[], d[];
		
		a = new int[32];
		b = new int[32];
		c = new int[n];
		d  = new int[n];
		
		int max, pre = 0;
		
		Tree t;
		
		t = null;
		
		convBin(a, 0);
		System.out.println("jha3");
		
		t = insert(t, a);
		
		for (int i = 0; i < n; i++)
		{
			pre = pre ^ inp[i];
			System.out.println("gh");
			convBin(a, pre);
			System.out.println("land");
			t = insert(t, a);
			query(t, a, b);
			c[i] = convDec(b);
		}
		
		pre = 0;
		t = null;
		
		convBin(a, 0);
		//System.out.println("jha3");
		
		t = insert(t, a);
		
		for (int i = 0; i < n; i++)
		{
			pre = pre ^ inp[n - i - 1];
			//System.out.println("gh");
			convBin(a, pre);
			//System.out.println("land");
			t = insert(t, a);
			query(t, a, b);
			d[i] = convDec(b);
		}
		
		max = 0;
		
		for (int i = 0; i < n - 1; i++)
		{
			if (c[i] + d[i + 1] > max)
				max = c[i] + d[i + 1];
		}
		
		return max;
	}
	
	static void query(Tree t, int a[], int b[])
	{
		Tree p;
		p = t;
		
		for (int i = 0; i < 32; i++)
		{
			if (a[i] == 0 && p.right != null)
			{
				b[i] = 1;
				p = p.right;
			}
			else if(a[i] == 0)
			{
				b[i] = 0;
				p = p.left;
			}
			else if (a[i] == 1 && p.left != null)
			{
				b[i] = 1;
				p = p.left;
			}
			else
			{
				b[i] = 0;
				p = p.right;
			}
		}
	}
	
	static void convBin(int a[], int x)
	{
		int l = 0;
		
		while (x != 0)
		{
			a[l++] = x % 2;
			
			x /= 2;
		}
	}
	
	static int convDec(int a[])
	{
		int value, counter;
		
		value = counter = 0;
		
		for (int i = 31; i >= 0; i--)
			value += (a[i] * Math.pow(2, counter++));
		
		return value;
	}
	
	static Tree mkNode(int x)
	{
		return new Tree(x,  null, null);
	}
	
	static Tree insert(Tree t, int a[])
	{
		Tree p;

		if (t == null)
		{
			t = mkNode(-1);
			
			System.out.println("jhat");
			
			p = t;
			
			System.out.println("sir2");
			
			for (int j = 0; j < 32; j++)
			{
				if (a[j] == 0)
				{
					p.left = mkNode(0);
					p = p.left;
				}
				else
				{
					p.right = mkNode(1);
					p = p.right;
				}
			}
			
			System.out.println("sir");
		}
		else
		{
			System.out.println("in else");
			
			p = t;
			
			for (int i = 0; i < 32; i++)
			{
				if (a[i] == 0 && p.left != null)
				{
					p= p.left;
				}
				else if(a[i] == 0)
				{
					p.left = mkNode(0);
					p = p.left;
				}
				else if(a[i] == 1 && p.right != null)
				{
					p = p.right;
				}
				else
				{
					p.right = mkNode(1);
					p = p.right;
				}
			}
			System.out.println("shani");
		}
		
		System.out.println("land");
		return t;
	}
	
	static class Tree
	{
		int val;
		Tree left, right;
		
		public Tree(int val, Tree left, Tree right)
		{
			super();
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

}
