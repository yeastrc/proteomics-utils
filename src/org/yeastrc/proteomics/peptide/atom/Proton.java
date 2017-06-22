package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Proton implements Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 1.0072765;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 1.0072765;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public char getSymbol() {
		return 'p';
	}

}
