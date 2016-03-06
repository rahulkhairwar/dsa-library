package com.ashwin;

/**
 * Created by rahulkhairwar on 06/03/16.
 */
public class Variable implements Unifiable
{
	private String printName = null;
	private static int nextId = 1;
	private int id;

	public Variable()
	{
		this.id = nextId++;
	}

	public Variable(String printName)
	{
		this();
		this.printName = printName;
	}

	public String toString()
	{
		if (printName != null)
			return printName + "_" + id;
		return "V" + id;
	}

	//unify and other functions to be defined shortly.
	public SubstitutionSet unify(Unifiable p,
								 SubstitutionSet s)
	{
		if (this == p) return s;
		if (s.isBound(this))
			return s.getBinding(this).unify(p, s);
		SubstitutionSet sNew = new SubstitutionSet(s);
		sNew.add(this, p);
		return sNew;
	}

	public PCExpression replaceVariables(
			SubstitutionSet s)
	{
		if(s.isBound(this))
			return
					s.getBinding(this).replaceVariables(s);
		else
			return this;
	}
}