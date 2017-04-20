package org.yeastrc.proteomics.peptide.atom;

public interface Atom {

	public double getMass( int massType ) throws Exception;
	
	public char getSymbol();
	
}
