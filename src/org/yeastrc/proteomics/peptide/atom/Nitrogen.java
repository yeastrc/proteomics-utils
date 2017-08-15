package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Nitrogen extends Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 14.0030740048;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 14.0067;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "N";
	}

}
