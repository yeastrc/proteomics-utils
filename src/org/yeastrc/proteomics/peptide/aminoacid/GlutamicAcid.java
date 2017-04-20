package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class GlutamicAcid implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 129.042593;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 129.1155;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'E';
	}

	@Override
	public String getAbbreviation() {
		return "Glu";
	}

	@Override
	public String getName() {
		return "glutamic acid";
	}

}