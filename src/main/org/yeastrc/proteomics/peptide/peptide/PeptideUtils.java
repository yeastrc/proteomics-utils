package org.yeastrc.proteomics.peptide.peptide;

import java.util.Collection;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.aminoacid.InvalidAminoAcidException;

public class PeptideUtils {

	public static boolean isValidPeptideSequence( String sequence ) {
		
		if( sequence == null || sequence.length() < 1 )
			return false;
		
		Peptide peptide = new Peptide( sequence );
		
		try {
			PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
		} catch( InvalidAminoAcidException e ) {
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Check whether mod positions are legal--they cannot be less than 0 or greater than the length of the sequence
	 * (0 is an n-terminal mod, max length is c-terminal mod) If positions is empty or null, this will return true.
	 * @param sequence
	 * @param positions
	 * @return
	 */
	public static boolean isValidPeptideModPositions( String sequence, Collection<Integer> positions ) {
		
		if( positions == null ) return true;
		
		int l = sequence.length();
		
		for( int p : positions )
			if( p <0 || p > l ) return false;
		
		return true;
	}
	
}
