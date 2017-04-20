package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Valine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 99.068414;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 99.1326;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'V';
	}

	@Override
	public String getAbbreviation() {
		return "Val";
	}

	@Override
	public String getName() {
		return "valine";
	}

}