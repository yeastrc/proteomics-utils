package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils.MassType;

public class Nitrogen extends Atom {

	@Override
	public double getMass( MassType massType ) {

		if( massType == MassType.MONOISOTOPIC )
			return 14.0030740048;
		
		if( massType == MassType.AVERAGE )
			return 14.0067;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "N";
	}

}
