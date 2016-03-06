package com.ashwin;

/**
 * Created by rahulkhairwar on 06/03/16.
 */
public interface Unifiable extends PCExpression
{
	public SubstitutionSet unify(Unifiable p, SubstitutionSet s);
}
