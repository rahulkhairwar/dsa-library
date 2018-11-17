package com.dsa.util;

import java.math.BigInteger;

/**
 * This class provides some methods to manipulate numbers.
 */
public class NumberUtils
{
	public String returnBinary(int num, int requiredLength)
	{
		String temp, binary;

		temp = "";
		binary = "";

		while (num != 0)
		{
			temp += (num % 2);
			num >>= 1;
		}

		if (temp.length() < requiredLength)
		{
			int bal = requiredLength - temp.length();

			for (int i = 0; i < bal; i++)
				temp += "0";
		}

		int len = temp.length();

		for (int i = len - 1; i >= 0; i--)
			binary += temp.charAt(i);

		return binary;
	}

	public BigInteger convertBinaryToBase(String binary, BigInteger base)
	{
		BigInteger basePower, converted;

		basePower = BigInteger.ONE;
		converted = BigInteger.ZERO;

		for (int i = binary.length() - 1; i >= 0; i--)
		{
			converted = converted.add(BigInteger.valueOf(binary.charAt(i) - '0').multiply(basePower));
			basePower = basePower.multiply(base);
		}

//			System.out.println(binary + " converted to base " + base + " is : " + converted.toString());

		return converted;
	}

}
