package org.yeastrc.proteomics.peptide.aminoacid;

import org.yeastrc.proteomics.mass.MassUtils;

public class Asparagine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 114.042927;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 114.1039;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'N';
	}

	@Override
	public String getAbbreviation() {
		return "Asn";
	}

	@Override
	public String getName() {
		return "asparagine";
	}

}