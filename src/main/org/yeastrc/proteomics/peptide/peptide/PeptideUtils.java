package org.yeastrc.proteomics.peptide.peptide;

import org.yeastrc.proteomics.mass.MassUtils;
import org.yeastrc.proteomics.peptide.aminoacid.InvalidAminoAcidException;

public class PeptideUtils {

	public static boolean isValidPeptideSequence( String sequence ) throws Exception {
		
		Peptide peptide = new Peptide( sequence );
		
		try {
			peptide.getMass( MassUtils.MASS_TYPE_MONOISOTOPIC );
		} catch( InvalidAminoAcidException e ) {
			return false;
		}
		
		return true;
		
	}
	
}
