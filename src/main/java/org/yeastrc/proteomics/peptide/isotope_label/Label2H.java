package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label2H extends IsotopeLabel {

	@Override
	public String getName() {
		return "2H";
	}

	@Override
	public Atom getLabeledAtom() {
		// TODO Auto-generated method stub
		return AtomUtils.ATOM_HYDROGEN;
	}

	@Override
	public double getMassChange( MassType type ) {
		
		if( type == null )
			throw new IllegalArgumentException( "type cannot be null" );
		
		if( type == MassType.MONOISOTOPIC )
			return 1.006276744;
		else if( type == MassType.AVERAGE )
			return 1.006162;
		else
			throw new IllegalArgumentException( "invalid mass type: " + type );
	}

}
