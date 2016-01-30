package com.codechef.competitions.longcompetitions.year2015.september;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BankRobbery
{
	static int t, m;
	static double p, amount;
	static BufferedReader reader;

	public static void main(String[] args)
	{
		reader = new BufferedReader(new InputStreamReader(System.in));

		/**
		 * 
		 * 
		 * Solved this question in C++, because of some problem with Java's double!!
		 * 	 The Solution
		 * 		|	|
		 * 		V	V
		 */
		
/*	    #include <iostream>
	    using namespace std;
	     
	    long double pow(long double number, int power);
	     
	    int t, m;
	    long double p, amount, x;

	    long double sumOfGP(long double r, int n);

	    int main()
	    {
	    	amount = 1000000000.0;
	     
	    	cin >> t;
	     
	    	for (int i = 0; i < t; i++)
	    	{
	    		cin >> m;
	    		cin >> p;
	     
	     		if (p == 1)
	     		{
	     			long double zero = 0;

	     			if (m % 2 == 0)
		     			printf("%.10Lf %.10Lf\n", zero, amount);
		     		else
		     			printf("%.10Lf %.10Lf\n", amount, zero);
		     	}
		     	else if (m == 1 || p == 0)
		     		printf("%.10Lf %.10Lf\n", amount, zero);
	    		else if (m % 2 == 0)
	    		{
	    			long double pIntoAmount, pSquare;
	    			
	    			pIntoAmount = p * amount;
	    			pSquare = p * p;
	    			
	    			if (m == 2)
	    				printf("%.10Lf %.10Lf\n", amount - pIntoAmount, pIntoAmount);
	    			else
	    			{
	    				x = p * pIntoAmount * sumOfGP(pSquare, (m - 1) / 2) - pIntoAmount * sumOfGP(pSquare, m / 2);

	    				printf("%.10Lf %.10Lf\n", amount + x, -x);
	    			}
	    		}
	    		else
	    		{
	    			if (m == 1)
		     			printf("%.10Lf %.10Lf\n", amount, zero);
		     		else
		     		{
	    				long double pIntoAmount, pSquare;
	    			
	    				pIntoAmount = p * amount;
	    				pSquare = p * p;
		    			
	    				x = pIntoAmount * sumOfGP(pSquare, m / 2) * (p - 1);

	    				printf("%.10Lf %.10Lf\n", amount + x, -x);
		     		}
	    		}
	    	}
	     
	    	return 0;
	    }

	    long double sumOfGP(long double r, int n)
	    {
	    	return (1 - pow(r, n)) / (1 - r);
	    }
	     
	    long double pow(long double number, int power)
	    {
	    	if (power == 0)
	    		return 1;
	     
	    	if (power == 1)
	    		return number;
	     
	    	if (power % 2 == 0)
	    		return pow(number * number, power / 2);
	    	else
	    		return pow(number * number, power / 2) * number;
	    }*/
		
		
		try
		{
			getAttributes();
			
			reader.close();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static void getAttributes() throws NumberFormatException, IOException
	{
		amount = 1000000000.0;
		
		t = Integer.parseInt(reader.readLine());
		
		String line[];
		double x;

		for (int i = 0; i < t; i++)
		{
			line = reader.readLine().split(" ");
			
			m = Integer.parseInt(line[0]);
			p = Double.parseDouble(line[1]);
			
			if (p == 1)
			{
				if (m % 2 == 0)
					System.out.printf("%.10f %.10f\n", 0d, amount);
				else
					System.out.printf("%.10f %.10f\n", amount, 0d);
			}
			else if (m == 1 || p == 0)
				System.out.printf("%.10f %.10f\n", amount, 0d);
			else if (m == 2)
				System.out.printf("%.10f %.10f\n", amount - p * amount, p * amount);
			else if (m % 2 == 0)
			{
				x = p * p * amount * sumOfGP(p * p, (m - 1) / 2) - p
						* amount * sumOfGP(p * p, m / 2);
				
				System.out.printf("%.10f %.10f\n", amount + x, -x);
			}
			else
			{
				x = p * amount * sumOfGP(p * p, m / 2) * (p - 1);
				
				System.out.printf("%.10f %.10f\n", amount + x, -x);
			}
		}
	}
	
	static double sumOfGP(double r, int n)
	{
		return (1 - power(r, n)) / (1 - r);
	}

	static double power(double number, int power)
	{
		if (power == 0)
			return 1;

		if (power == 1)
			return number;

		if (power % 2 == 0)
			return power(number * number, power / 2);
		else
			return power(number * number, power / 2) * number;
	}

}

/*

8
4 0.2
4 0.7
5 0.9
6 0.9
7 0.5
8 0.1938794578387
9 0.4274383756432
10 0.1000000000000

4
3 0.9
4 0.9
5 0.9
6 0.9

*/
