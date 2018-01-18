package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label15N implements IsotopeLabel {

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
	public double getMassChange() throws Exception {
		return 0.99703497;
	}

}
