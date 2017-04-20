package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Oxygen implements Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 15.99491461956;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 15.9994;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public char getSymbol() {
		return 'O';
	}

}
