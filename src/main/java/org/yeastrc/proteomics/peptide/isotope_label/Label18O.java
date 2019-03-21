package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label18O extends IsotopeLabel {

	@Override
	public String getName() {
		return "18O";
	}

	@Override
	public Atom getLabeledAtom() {
		// TODO Auto-generated method stub
		return AtomUtils.ATOM_OXYGEN;
	}

	@Override
	public double getMassChange( MassType type ) {
		
		if( type == null )
			throw new IllegalArgumentException( "type cannot be null" );
		
		if( type == MassType.MONOISOTOPIC )
			return 2.00424567;
		else if( type == MassType.AVERAGE )
			return 1.99976;
		else
			throw new IllegalArgumentException( "invalid mass type: " + type );
	}

}
