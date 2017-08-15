package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Sulfur extends Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 31.972071;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 32.065;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "S";
	}

}
