package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label18O implements IsotopeLabel {

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
	public double getMassChange() throws Exception {
		return 2.00424567;
	}

}
