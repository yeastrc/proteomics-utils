package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class AsparticAcid implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 115.026943;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 115.0886;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'D';
	}

	@Override
	public String getAbbreviation() {
		return "Asp";
	}

	@Override
	public String getName() {
		return "aspartic acid";
	}

}