package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils.MassType;

public class Oxygen extends Atom {

	@Override
	public double getMass( MassType massType ) {

		if( massType == MassType.MONOISOTOPIC )
			return 15.99491461956;
		
		if( massType == MassType.AVERAGE )
			return 15.9994;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "O";
	}

}
