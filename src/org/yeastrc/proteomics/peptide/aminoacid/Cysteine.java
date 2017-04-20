package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Cysteine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 103.009185;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 103.1388;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'C';
	}

	@Override
	public String getAbbreviation() {
		return "Cys";
	}

	@Override
	public String getName() {
		return "cysteine";
	}

}