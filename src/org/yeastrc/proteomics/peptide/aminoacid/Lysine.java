package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Lysine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 128.094963;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 128.1741;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'K';
	}

	@Override
	public String getAbbreviation() {
		return "Lys";
	}

	@Override
	public String getName() {
		return "lysine";
	}

}