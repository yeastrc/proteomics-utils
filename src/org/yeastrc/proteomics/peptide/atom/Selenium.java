package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Selenium extends Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 79.916519;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 78.960;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "Se";
	}

}
