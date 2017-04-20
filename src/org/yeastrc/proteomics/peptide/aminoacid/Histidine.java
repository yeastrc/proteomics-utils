package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Histidine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 137.058912;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 137.1411;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'H';
	}

	@Override
	public String getAbbreviation() {
		return "His";
	}

	@Override
	public String getName() {
		return "histidine";
	}

}