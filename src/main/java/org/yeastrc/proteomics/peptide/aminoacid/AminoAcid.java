package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;

public interface AminoAcid {

	/**
	 * Get the mass of this amino acid
	 * @param type Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return The mass in daltons
	 * @throws Exception If the mass type is invalid
	 */
	public double getMass( MassType type );

	
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
	
	/**
	 * Get the molecular formula of this amino acid.
	 * @return
	 */
	public String getMolecularFormula();
	
	/**
	 * Get a map of each distinct atom and count of those atoms in the
	 * molecular formula for this amino acid.
	 * 
	 * @return
	 */
	public Map<Atom, Integer> getParsedAtomCount();
	
}
