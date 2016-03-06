package com.ashwin;

/**
 * Created by rahulkhairwar on 06/03/16.
 */
public class SimpleSentence implements Unifiable
{
	private Unifiable[] terms;

	public SimpleSentence(Constant predicateName,
						  Unifiable... args)
	{
		this.terms = new Unifiable[args.length + 1];
		terms[0] = predicateName;
		System.arraycopy(args, 0, terms, 1,
				args.length);
	}

	private SimpleSentence(Unifiable... args)
	{
		terms = args;
	}

	public String toString()
	{
		String s = null;
		for (Unifiable p : terms)
			if (s == null)
				s = p.toString();
			else
				s += " " + p;
		if (s == null)
			return "null";
		return "(" + s + ")";
	}

	public int length()
	{
		return terms.length;
	}

	public Unifiable getTerm(int index)
	{
		return terms[index];
	}
//unify and other functions to be defined shortly.

	public SubstitutionSet unify(Unifiable p,
								 SubstitutionSet s)
	{
		if (p instanceof SimpleSentence)
		{
			SimpleSentence s2 = (SimpleSentence) p;
			if (this.length() != s2.length())
				return null;
			SubstitutionSet sNew = new SubstitutionSet(s);
			for (int i = 0; i < this.length(); i++)
			{
				sNew = this.getTerm(i).unify(s2.getTerm(i),
						sNew);
				if (sNew == null)
					return null;
			}
			return sNew;
		}
		if (p instanceof Variable)
			return p.unify(this, s);
		return null;
	}

	public PCExpression replaceVariables(SubstitutionSet s)
	{
		Unifiable[] newTerms = new
				Unifiable[terms.length];
		for(int i = 0; i < length(); i++)
			newTerms[i] =
					(Unifiable)terms[i].replaceVariables(s);
		return new SimpleSentence(newTerms);
	}
}
