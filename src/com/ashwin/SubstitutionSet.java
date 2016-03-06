package com.ashwin;

import java.util.HashMap;

/**
 * Created by rahulkhairwar on 06/03/16.
 */
public class SubstitutionSet
{
	private HashMap<Variable, Unifiable> bindings =
			new HashMap<Variable, Unifiable>();

	public SubstitutionSet()
	{
	}

	public SubstitutionSet(SubstitutionSet s)
	{
		this.bindings =
				new HashMap<Variable, Unifiable>(s.bindings);
	}

	public void clear()
	{
		bindings.clear();
	}

	public void add(Variable v, Unifiable exp)
	{
		bindings.put(v, exp);
	}

	public Unifiable getBinding(Variable v)
	{
		return (Unifiable) bindings.get(v);
	}

	public boolean isBound(Variable v)
	{
		return bindings.get(v) != null;
	}

	public String toString()
	{
		return "Bindings:[" + bindings + "]";
	}
}
