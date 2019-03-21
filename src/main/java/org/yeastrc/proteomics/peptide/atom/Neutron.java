package org.yeastrc.proteomics.peptide.atom;

import org.yeastrc.proteomics.mass.MassUtils.MassType;

public class Neutron extends Atom {

	@Override
	public double getMass( MassType massType ) {

		if( massType == MassType.MONOISOTOPIC || massType == MassType.AVERAGE )
			return 1.008665;
		
		throw new IllegalArgumentException( "Invalid mass type." );
	}

	@Override
	public String getSymbol() {
		return "n";
	}

}
