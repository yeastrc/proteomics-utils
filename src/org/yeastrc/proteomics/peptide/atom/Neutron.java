package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils;

public class Neutron extends Atom {

	@Override
	public double getMass(int massType) throws Exception {

		if( massType == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 1.008665;
		
		if( massType == MassUtils.MASS_TYPE_AVERAGE )
			return 1.008665;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "n";
	}

}
