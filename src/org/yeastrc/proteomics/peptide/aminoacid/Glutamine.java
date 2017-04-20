package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Glutamine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 128.058578;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 128.1307;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'Q';
	}

	@Override
	public String getAbbreviation() {
		return "Gln";
	}

	@Override
	public String getName() {
		return "glutamine";
	}

}