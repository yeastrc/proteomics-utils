package org.yeastrc.proteomics.peptide.aminoacid;

public interface AminoAcid {

	/**
	 * Get the mass of this amino acid
	 * @param type Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return The mass in daltons
	 * @throws Exception If the mass type is invalid
	 */
	public double getMass( int type ) throws Exception;

	
	/**
	 * The symbol of this amino acid e.g., K for lysine
	 * @return
	 */
	public char getSymbol();
	
	/**
	 * The three letter abbreviation of the amino acid e.g. Lys for lysine
	 * @return
	 */
	public String getAbbreviation();
	
	/**
	 * Get the name of this amino acid e.g. lystine
	 * @return
	 */
	public String getName();
	
	
}
