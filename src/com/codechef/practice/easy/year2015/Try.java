package com.codechef.practice.easy.year2015;

class Try
{
	
	public static void main(String[] args)
	{
		Try t = new Try();
		MyArray oldArray, currentArray;
		
		oldArray = t.new MyArray(10);
		currentArray = t.new MyArray(10);
		
		oldArray.a[0] = 0;
		oldArray.a[1] = 1;
		oldArray.a[2] = 2;
		oldArray.a[3] = 3;
		oldArray.a[4] = 4;
		oldArray.a[5] = 5;
		oldArray.a[6] = 6;
		oldArray.a[7] = 7;
		oldArray.a[8] = 8;
		oldArray.a[9] = 9;
		
		System.out.println("currentArray before changing any values of it and also the oldArray");
		for (int i = 0; i < 10; i++)
			System.out.print(currentArray.a[i] + " ");
		
		System.out.println();
		for (int i = 0; i < 10; i++)
			System.out.print(oldArray.a[i] + " ");
		
		System.out.println();
		for (int i = 0; i < 10; i++)
			System.out.print(currentArray.a[i] + " ");
		
		currentArray.a = oldArray.a;
		
		System.out.println();
		for (int i = 0; i < 10; i++)
			System.out.print(currentArray.a[i] + " ");
	}
	
	class MyArray
	{
		int a[];
		
		public MyArray(int size)
		{
			a = new int[size];
		}
		
		public void printA()
		{
			for (int i = 0; i < 10; i++)
				System.out.print(a[i] + " ");
		}
	}

}

