package com.nlp;

import java.io.FileNotFoundException;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			FileUtils.parse();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		ViterbiAlgorithm viterbiAlgorithm = new ViterbiAlgorithm();
		viterbiAlgorithm.viterbi();
	}

}

/*

1-1/2/cd

1-1/2=cd

*/
