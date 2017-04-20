package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Glycine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 57.021464;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 57.0519;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'G';
	}

	@Override
	public String getAbbreviation() {
		return "Gly";
	}

	@Override
	public String getName() {
		return "glycine";
	}

}