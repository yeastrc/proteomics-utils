package org.yeastrc.proteomics.peptide.peptide;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcid;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcidUtils;
import org.yeastrc.proteomics.peptide.aminoacid.InvalidAminoAcidException;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;
import org.yeastrc.proteomics.peptide.isotope_label.IsotopeMassCalculator;

public class PeptideMassCalculator {

	private PeptideMassCalculator() { }
	private static final PeptideMassCalculator _INSTANCE = new PeptideMassCalculator();
	public static PeptideMassCalculator getInstance() { return _INSTANCE; }
	
	/**
	 * Get the mass of the supplied peptide, taking into account mods and isotope label
	 * 
	 * @param peptide The peptide
	 * @param massType The type of mass (monoisotopic or average)
	 * @return The total mass of htis peptide.
	 * @throws InvalidAminoAcidException
	 */
	public double getMassForPeptide( Peptide peptide, MassType massType ) throws InvalidAminoAcidException {
		
		double mass = getMassOfResiduesForPeptide( peptide, massType );
		
		// add mass of H2O
		mass += AtomUtils.ATOM_OXYGEN.getMass( massType );
		mass += 2 * AtomUtils.ATOM_HYDROGEN.getMass( massType );
		
		return mass;
		
	}
	
	/**
	 * Get the mass of the supplied peptide's residues, including any mass modifications
	 * or stable isotope labels. This is essentially the same as the mass of the peptide,
	 * minus a H and OH
	 * 
	 * @param peptide
	 * @param massType
	 * @return
	 * @throws InvalidAminoAcidException
	 */
	public double getMassOfResiduesForPeptide( Peptide peptide, MassType massType ) throws InvalidAminoAcidException {
		
		double mass = 0.0;
		String s = peptide.getSequence();
		
		for (int i = 0; i < s.length(); i++){
			
			AminoAcid aminoAcid = AminoAcidUtils.getAminoAcidBySymbol( s.charAt(i) );
			
		    mass += aminoAcid.getMass( massType );
		    
		    // adjust mass for label if it is present
		    if( peptide.getLabel() != null )
		    	mass += IsotopeMassCalculator.getInstance().getMassShiftOnAminoAcidWithLabel( aminoAcid, peptide.getLabel(), massType );
		}
		
		// add the mass of any modifications to this mass
		if( peptide.getModificationMasses() != null ) {
			for( int position : peptide.getModificationMasses().keySet() ) {
				mass += peptide.getModificationMasses().get( position );
			}			
		}

		return mass;
		
	}
	
}
