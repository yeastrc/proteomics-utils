package org.yeastrc.proteomics.modifications.types;


public abstract class Modification {

	/**
	 * The name of this modification (e.g. "phosphorylation")
	 * @return
	 */
	abstract public String getName();

	/**
	 * Get the mass of the modification
	 * @param type Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return
	 * @throws Exception If invalid type is passed in
	 */
	abstract public double getMass( int type ) throws Exception;
	
	/**
	 * Get the Unimod (http://www.unimod.org) accession number for this modification
	 * @return The unimod accession, or null if none was found
	 */
	abstract public Integer getUnimodAccession();
	
	/**
	 * An array of which residues can have this modification in a peptide sequence
	 * E.g., ['S','T','Y'] for phosphorylation
	 * @return
	 */
	abstract public char[] getModifiedResidues();
	
	@Override
	public int hashCode() {
		return ( this.getName() + this.getUnimodAccession() ).hashCode();
	}
	
	/**
	 * Determines equality based on the name + unimod accession of the modification
	 */
	@Override
	public boolean equals( Object o ) {
		if (!(o instanceof Modification)) return false;
		
		return ( this.getName() + this.getUnimodAccession() ).equals( ((Modification)o).getName() + ((Modification)o).getUnimodAccession() );
	}
	
}
