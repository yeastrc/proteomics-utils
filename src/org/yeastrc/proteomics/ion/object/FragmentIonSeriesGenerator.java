package org.yeastrc.proteomics.ion.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.yeastrc.proteomics.ion.utils.IonUtils;
import org.yeastrc.proteomics.modifications.types.Modification;
import org.yeastrc.proteomics.peptide.peptide.Peptide;
import org.yeastrc.proteomics.peptide.peptide.PeptideModificationUtils;
import org.yeastrc.proteomics.modifications.utils.*;

public class FragmentIonSeriesGenerator {

	/**
	 * Returns a list of FragmentIons corresponding to the ion series that would be generated from the
	 * supplied peptide, supplied charge, and supplied ion type (e.g. b-ions).
	 * @param peptide
	 * @param charge
	 * @param ionType
	 * @return
	 * @throws Exception
	 */
	public List<FragmentIon> getFragmentIonSeries( Peptide peptide, int charge, int ionType ) throws Exception {
		
		String sequence = peptide.getSequence();
		StringBuilder tmpSequence = new StringBuilder();
		
		List<FragmentIon> ionSeries = new ArrayList<FragmentIon>( sequence.length() - 1 );
		
		if( ionType == IonUtils.ION_TYPE_A || ionType == IonUtils.ION_TYPE_B || ionType == IonUtils.ION_TYPE_C ) {
			
			// ions start at N terminus and move to C
			for( int i = 0; i < sequence.length() - 1; i++ ) {
				tmpSequence.append( sequence.charAt( i ) );
				
				Peptide fragment = new Peptide( tmpSequence.toString() );
				
				// add all mods from parent peptide, starting at N terminus
				if( peptide.getModifications() != null ) {
					for( int k = 0; k <= i + 1; k++ ) {
						Collection<Modification> mods = peptide.getModifications().get( k );
						if( mods != null ) {
							for( Modification mod : mods ) {
								PeptideModificationUtils.modPosition( fragment, k, mod );
							}
						}
					}
				}
				
				ionSeries.add( FragmentIon.createInstance( fragment, charge, ionType, i + 1 ) );
			}
			
			
		} else if( ionType == IonUtils.ION_TYPE_X || ionType == IonUtils.ION_TYPE_Y || ionType == IonUtils.ION_TYPE_Z || ionType == IonUtils.ION_TYPE_Z_DOT ) {
			
			// ions start at C termins and move to N
			for( int i = 0; i < sequence.length() - 1; i++ ) {
				tmpSequence.insert(0, sequence.charAt( sequence.length() - 1 - i ) );
				
				Peptide fragment = new Peptide( tmpSequence.toString() );
				
				// add all mods from parent peptide, starting at C terminus
				if( peptide.getModifications() != null ) {
					for( int k = 0; k <= i + 1; k++ ) {
						Collection<Modification> mods = peptide.getModifications().get( peptide.getSequence().length() + 1 - k );
						if( mods != null ) {
							for( Modification mod : mods ) {
								PeptideModificationUtils.modPosition( fragment, fragment.getSequence().length() + 1 - k, mod );
							}
						}
					}
				}
				
				ionSeries.add( FragmentIon.createInstance( fragment, charge, ionType, i + 1 ) );
			}
			
		} else {
			throw new Exception( "Unknown or unsupported ion type." );
		}
		
		return ionSeries;
	}
	

	public static FragmentIonSeriesGenerator createInstance() {
		return new FragmentIonSeriesGenerator();
	}
	
	private FragmentIonSeriesGenerator() { }
	
}
