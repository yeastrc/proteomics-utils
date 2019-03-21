package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label15N extends IsotopeLabel {

	@Override
	public String getName() {
		return "15N";
	}

	@Override
	public Atom getLabeledAtom() {
		// TODO Auto-generated method stub
		return AtomUtils.ATOM_NITROGEN;
	}

	@Override
	public double getMassChange( MassType type ) {
		
		if( type == null )
			throw new IllegalArgumentException( "type cannot be null" );
		
		if( type == MassType.MONOISOTOPIC )
			return 0.99703497;
		else if( type == MassType.AVERAGE )
			return 0.993409;
		else
			throw new IllegalArgumentException( "invalid mass type: " + type );
	}

}
