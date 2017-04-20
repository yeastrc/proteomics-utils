package org.yeastrc.proteomics.peptide.peptide;

import java.util.Collection;
import java.util.Map;

import org.yeastrc.proteomics.modifications.types.Modification;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcidUtils;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

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
		    mass += AminoAcidUtils.getAminoAcidBySymbol( s.charAt(i) ).getMass( massType );
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
	
	private final String sequence;
	private Map<Integer, Collection<Modification>> modifications;
	
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
