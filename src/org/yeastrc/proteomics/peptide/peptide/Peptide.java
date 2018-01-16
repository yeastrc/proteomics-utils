package org.yeastrc.proteomics.peptide.peptide;

import java.util.Collection;
import java.util.Map;

import org.yeastrc.proteomics.modifications.types.Modification;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcid;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcidUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;
import org.yeastrc.proteomics.peptide.isotope_label.IsotopeLabel;

/**
 * A Peptide is a peptide sequence and modifications
 * @author Michael Riffle
 *
 */
public class Peptide {

	/**
	 * Get the mass of this peptide in daltons, including mods
	 * @param massType Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return
	 * @throws Exception
	 */
	public double getMass( int massType ) throws Exception {

		double mass = this.getMassofResidues( massType );
		
		// add mass of H2O
		mass += AtomUtils.ATOM_OXYGEN.getMass( massType );
		mass += 2 * AtomUtils.ATOM_HYDROGEN.getMass( massType );
		
		// if H or O is labeled, include that
		if( this.getLabel() != null ) {
			
			if( this.getLabel().getLabeledAtom().equals( AtomUtils.ATOM_HYDROGEN ) )
				mass += 2 * this.getLabel().getMassChange();
			
			else if( this.getLabel().getLabeledAtom().equals( AtomUtils.ATOM_OXYGEN ) )
				mass += this.getLabel().getMassChange();
			
		}
		
		return mass;
	}
	
	/**
	 * Get the mass of the residues, including mods (same as peptide mass - mass of water)
	 * @param massType Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return
	 * @throws Exception
	 */
	public double getMassofResidues( int massType ) throws Exception {
		double mass = 0.0;
		String s = this.getSequence();
		
		for (int i = 0; i < s.length(); i++){
			
			AminoAcid residue = AminoAcidUtils.getAminoAcidBySymbol( s.charAt(i) );
			
		    mass += residue.getMass( massType );
		    
		    // adjust mass for label if it is present
		    if( this.getLabel() != null )
		    	mass += this.getLabelMassModForResidue( residue, this.getLabel() );
		}
		
		// add the mass of any modifications to this mass
		if( this.getModifications() != null ) {
			for( int p : this.getModifications().keySet() ) {
				for( Modification mod : this.getModifications().get( p ) ) {
					mass += mod.getMass( massType );
				}
			}			
		}

		return mass;
	}

	/**
	 * Get the mass shift caused by the presence of a stable isotope label on one of the atoms
	 * composing this residue.
	 * 
	 * @param residue
	 * @param label
	 * @return
	 * @throws Exception
	 */
	private double getLabelMassModForResidue( AminoAcid residue, IsotopeLabel label ) throws Exception {
	
		double mass = 0.0;
		
		Atom labeledAtom = label.getLabeledAtom();
		Integer atomCount = residue.getParsedAtomCount().get( labeledAtom );	// the number of labeled atoms
		
		// residue does not contained the labeled atom? weird.
		if( atomCount == null || atomCount == 0 )
			return 0.0;

		
		
		mass = (double)atomCount * label.getMassChange();
		
		return mass;
	}
	
	/**
	 * The sequence of this peptide
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * A map of position to a collection of modifications at that position.
	 * 0 is the N terminus, sequence.length + 1 is the C terminus, first residue is 1
	 * and last residue is sequence.length.
	 * @return
	 */
	public Map<Integer, Collection<Modification>> getModifications() {
		return modifications;
	}


	/**
	 * Set the modifications on this peptide
	 * @param mods
	 */
	public void setModification( Map<Integer, Collection<Modification>> mods ) {
		this.modifications = mods;
	}
		
	public IsotopeLabel getLabel() {
		return label;
	}

	public void setLabel(IsotopeLabel label) {
		this.label = label;
	}

	private final String sequence;
	private Map<Integer, Collection<Modification>> modifications;
	private IsotopeLabel label;
	
	/**
	 * Get an instance of a peptide with the supplied attributes
	 * @param sequence
	 * @param mods
	 */
	public Peptide( String sequence, Map<Integer, Collection<Modification>> mods ) {
		this.sequence = sequence;
		this.modifications = mods;
	}
	
	/**
	 * Get an instance of a peptide with the supplied attributes
	 * @param sequence
	 * @param mods
	 */
	public Peptide( String sequence ) {
		this.sequence = sequence;
	}
	
}
