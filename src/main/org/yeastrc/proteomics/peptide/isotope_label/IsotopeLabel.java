package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;

public interface IsotopeLabel {

	/**
	 * Get the name of this isotope label (e.g., 15N)
	 * @return
	 */
	public String getName();

	/**
	 * Get the element that is labeled (e.g., nitrogen)
	 * @return
	 */
	public Atom getLabeledAtom();

	/**
	 * Get the mass change resulting from the label (e.g., mass of a neutron)
	 * @return
	 */
	public double getMassChange( MassType massType );
	
}
