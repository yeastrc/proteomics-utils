package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Selenocysteine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 150.95364;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 150.0388;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'U';
	}

	@Override
	public String getAbbreviation() {
		return "SeC";
	}

	@Override
	public String getName() {
		return "selenocysteine";
	}

}