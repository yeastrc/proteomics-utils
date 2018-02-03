package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label13C implements IsotopeLabel {

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
	public double getMassChange() throws Exception {
		return 1.00335483;
	}

}
