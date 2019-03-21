package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils.MassType;

public class Sulfur extends Atom {

	@Override
	public double getMass( MassType massType ) {

		if( massType == MassType.MONOISOTOPIC )
			return 31.972071;
		
		if( massType == MassType.AVERAGE )
			return 32.065;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "S";
	}

}
