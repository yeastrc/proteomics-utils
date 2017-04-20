package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Arginine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 156.101111;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 156.1875;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'R';
	}

	@Override
	public String getAbbreviation() {
		return "Arg";
	}

	@Override
	public String getName() {
		return "arginine";
	}

}