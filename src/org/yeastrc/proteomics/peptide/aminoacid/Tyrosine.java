package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Tyrosine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 163.06333;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 163.1760;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'Y';
	}

	@Override
	public String getAbbreviation() {
		return "Tyr";
	}

	@Override
	public String getName() {
		return "tyrosine";
	}

}