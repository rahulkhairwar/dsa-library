package com.common.tricks;

import java.util.Scanner;

/**
 * Created by rahulkhairwar on 10/02/16.
 */
public class IncreaseStackSize implements Runnable
{
	static Scanner in;

	public static void main(String[] args) throws InterruptedException
	{
		in = new Scanner(System.in);

		// here, 1 << 28 (i.e., 2 ^ 28, is the new size of stack that we want.
		Thread thread = new Thread(null, new IncreaseStackSize(), "IncreaseStackSize", 1 << 28);

		thread.start();
		thread.join();

		in.close();
	}

	public IncreaseStackSize()
	{
		// take the input here ..
	}

	@Override
	public void run()
	{
		// your code here ..
	}

}
