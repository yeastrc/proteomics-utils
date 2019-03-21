package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label13C extends IsotopeLabel {

	@Override
	public String getName() {
		return "13C";
	}

	@Override
	public Atom getLabeledAtom() {
		// TODO Auto-generated method stub
		return AtomUtils.ATOM_CARBON;
	}

	@Override
	public double getMassChange( MassType type ) {
		
		if( type == null )
			throw new IllegalArgumentException( "type cannot be null" );
		
		if( type == MassType.MONOISOTOPIC )
			return 1.00335483;
		else if( type == MassType.AVERAGE )
			return 0.992655;
		else
			throw new IllegalArgumentException( "invalid mass type: " + type );
	}

}
