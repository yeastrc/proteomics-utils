package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Threonine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 101.047679;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 101.1051;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'T';
	}

	@Override
	public String getAbbreviation() {
		return "Thr";
	}

	@Override
	public String getName() {
		return "threonine";
	}

}