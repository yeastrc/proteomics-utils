package org.yeastrc.proteomics.peptide.peptide;

import java.util.Map;

import org.yeastrc.proteomics.peptide.isotope_label.IsotopeLabel;

/**
 * A Peptide is a peptide sequence and modifications
 * @author Michael Riffle
 *
 */
public class Peptide {
	
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
	public Map<Integer, Double> getModificationMasses() {
		return modificationMasses;
	}
		
	public IsotopeLabel getLabel() {
		return label;
	}


	private final String sequence;
	private Map<Integer, Double> modificationMasses;
	private IsotopeLabel label;
	
	/**
	 * Get an instance of a peptide with the supplied attributes
	 * @param sequence
	 * @param mods
	 */
	public Peptide( String sequence, Map<Integer, Double> mods, IsotopeLabel label ) {
		
		if( !PeptideUtils.isValidPeptideModPositions( sequence,  mods.keySet() ) )
			throw new IllegalArgumentException( "mod positions not in sequence" );
		
		this.sequence = sequence;
		this.modificationMasses = mods;
		this.label = label;
	}
	
	public Peptide( String sequence, IsotopeLabel label ) {
		
		this.sequence = sequence;
		this.label = label;
	}
	
	/**
	 * Get an instance of a peptide with the supplied attributes
	 * @param sequence
	 * @param mods
	 */
	public Peptide( String sequence, Map<Integer, Double> mods ) {
		
		if( !PeptideUtils.isValidPeptideModPositions( sequence,  mods.keySet() ) )
			throw new IllegalArgumentException( "mod positions not in sequence" );
		
		this.sequence = sequence;
		this.modificationMasses = mods;
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
