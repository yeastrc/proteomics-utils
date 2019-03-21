package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;

public abstract class IsotopeLabel {

	/**
	 * Get the name of this isotope label (e.g., 15N)
	 * @return
	 */
	public abstract String getName();

	/**
	 * Get the element that is labeled (e.g., nitrogen)
	 * @return
	 */
	public abstract Atom getLabeledAtom();

	/**
	 * Get the mass change resulting from the label (e.g., mass of a neutron)
	 * @return
	 */
	public abstract double getMassChange( MassType massType );

	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	/**
	 * Whether or not these IsotopeLabels are equal. Uses only the name of the label.
	 */
	@Override
	public boolean equals( Object o ) {
		
		if( o == null ) return false;
		
		if (getClass() != o.getClass())
			return false;
				
		return getName().equals(( ((IsotopeLabel)o).getName()) );
	}
	
	
}
