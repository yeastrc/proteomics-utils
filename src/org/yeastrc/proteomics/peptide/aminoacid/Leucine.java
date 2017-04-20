package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Leucine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 113.084064;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 113.1594;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'L';
	}

	@Override
	public String getAbbreviation() {
		return "Leu";
	}

	@Override
	public String getName() {
		return "leucine";
	}

}