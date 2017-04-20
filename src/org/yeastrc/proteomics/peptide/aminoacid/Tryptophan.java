package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Tryptophan implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 186.079313;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 186.2132;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'W';
	}

	@Override
	public String getAbbreviation() {
		return "Trp";
	}

	@Override
	public String getName() {
		return "tryptophan";
	}

}