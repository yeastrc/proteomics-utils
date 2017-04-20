package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Proline implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 97.052764;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 97.1167;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'P';
	}

	@Override
	public String getAbbreviation() {
		return "Pro";
	}

	@Override
	public String getName() {
		return "proline";
	}

}