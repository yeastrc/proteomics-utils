package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Methionine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 131.040485;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 131.1986;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'M';
	}

	@Override
	public String getAbbreviation() {
		return "Met";
	}

	@Override
	public String getName() {
		return "methionine";
	}

}