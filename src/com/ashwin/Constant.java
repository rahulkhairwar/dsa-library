package com.ashwin;

/**
 * Created by rahulkhairwar on 06/03/16.
 */
public class Constant implements Unifiable
{
	private String printName = null;
	private static int nextId = 1;
	private int id;
	public Constant()
	{
		this.id = nextId++;
	}
	public Constant(String printName)
	{
		this();

		this. printName= printName;
	}
	public String toString()
	{
		if (printName!= null)
			return printName;
		return "constant_" + id;
	}
//unify and other functions to be defined shortly.

	public SubstitutionSet unify(Unifiable exp,
								 SubstitutionSet s)
	{
		if (this == exp)
			return new SubstitutionSet(s);
		if (exp instanceof Variable)
			return exp.unify(this, s);
		return null;
	}

	@Override
	public PCExpression replaceVariables(SubstitutionSet s)
	{
		return this;
	}
}
