package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Pyrrolysine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 255.15829;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 255.3172;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'O';
	}

	@Override
	public String getAbbreviation() {
		return "Pyl";
	}

	@Override
	public String getName() {
		return "pyrrolysine";
	}

}