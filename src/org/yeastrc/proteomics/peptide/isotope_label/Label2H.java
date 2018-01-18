package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Label2H implements IsotopeLabel {

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
	public double getMassChange() throws Exception {
		return 1.006276744;
	}

}
