package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils.MassType;

public class Selenium extends Atom {

	@Override
	public double getMass( MassType massType ) {

		if( massType == MassType.MONOISOTOPIC )
			return 79.916519;
		
		if( massType == MassType.AVERAGE )
			return 78.960;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "Se";
	}

}
