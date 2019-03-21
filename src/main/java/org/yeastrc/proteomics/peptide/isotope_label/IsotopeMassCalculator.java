package org.yeastrc.proteomics.peptide.isotope_label;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcid;
import org.yeastrc.proteomics.peptide.atom.Atom;

public class IsotopeMassCalculator {

	private IsotopeMassCalculator() { }
	private static final IsotopeMassCalculator _INSTANCE = new IsotopeMassCalculator();
	public static IsotopeMassCalculator getInstance() { return _INSTANCE; }
	
	/**
	 * Get the mass shift of the given residue caused by the given label
	 * 
	 * @param aminoAcid The particular amino acid we're looking at
	 * @param label The stable isotope label on that amino acid
	 * @param massType The mass type--monoisotopic or average
	 * @return
	 */
	public double getMassShiftOnAminoAcidWithLabel( AminoAcid aminoAcid, IsotopeLabel label, MassType massType ) {
		
		double mass = 0.0;
		
		Atom labeledAtom = label.getLabeledAtom();
		Integer atomCount = aminoAcid.getParsedAtomCount().get( labeledAtom );	// the number of labeled atoms
		
		// residue does not contained the labeled atom? weird.
		if( atomCount == null || atomCount == 0 )
			return 0.0;

		
		mass = (double)atomCount * label.getMassChange( massType );
		
		return mass;
		
	}
	
}
