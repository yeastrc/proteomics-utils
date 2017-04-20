package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Alanine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 71.037114;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 71.0788;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'A';
	}

	@Override
	public String getAbbreviation() {
		return "Ala";
	}

	@Override
	public String getName() {
		return "alanine";
	}

}
