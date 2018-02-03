package org.yeastrc.proteomics.ion.object;

import org.yeastrc.proteomics.ion.utils.IonUtils;
import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.spectrum.object.Peak;
import org.yeastrc.proteomics.spectrum.object.Spectrum;

public class FragmentIonSearcher {

	/**
	 * Find the matching peak corresponding to the supplied calculated fragment ion in the given spectrum.
	 * @param spectrum The spectrum to search.
	 * @param calculatedIon The calculated fragment ion.
	 * @return The matching peak, null if no peak was found within the m/z tolerance
	 * @throws Exception
	 */
	public Peak findPeak( Spectrum spectrum, FragmentIon calculatedIon ) throws Exception {
		Peak match = null;
		double limit = this.massTolerance / 2;
		double calculatedIonMZ = calculatedIon.getMassToCharge( massType );
		
		for( Peak peak : spectrum.getPeaks() ) {
			if( Math.abs( calculatedIonMZ - peak.getMassToCharge() ) <= limit ) {
				
				// this peak is within our m/z tolerance window
				if( match == null ) {
					match = peak;
				} else {
					
					if( matchType == IonUtils.MATCH_TYPE_INTENSITY ) {
						if( peak.getIntensity() > match.getIntensity() ) {
								match = peak;
						} else if( peak.getIntensity() == match.getIntensity() ) {
						
							// in the event that we're choosing by intensity, but two peaks in this range
							// have the same intensity, pick the closest one.
							if( IonUtils.compareMassToCharge( Math.abs( calculatedIonMZ - peak.getMassToCharge() ), Math.abs( calculatedIonMZ - match.getMassToCharge() ) ) < 0 )
								match = peak;
						}
						
					} else if( matchType == IonUtils.MATCH_TYPE_PROXIMITY ) {
						
						if( IonUtils.compareMassToCharge( Math.abs( calculatedIonMZ - peak.getMassToCharge() ), Math.abs( calculatedIonMZ - match.getMassToCharge() ) ) < 0 ) {
							match = peak;
						} else if( IonUtils.compareMassToCharge( Math.abs( calculatedIonMZ - peak.getMassToCharge() ), Math.abs( calculatedIonMZ - match.getMassToCharge() ) ) == 0 ) {
							
							// in the even that we're choosing by proximity, but different peaks are the same distance
							// from the calculated m/z, choose the most intense one of these
							if( peak.getIntensity() > match.getIntensity() )
								match = peak;
						}
						
					}
					
				}
				
			}
		}
		
		
		return match;
	}
	
	public double getMassTolerance() {
		return massTolerance;
	}

	private void setMassTolerance(double massTolerance) {
		this.massTolerance = massTolerance;
	}

	public int getMatchType() {
		return matchType;
	}

	private void setMatchType(int matchType) {
		this.matchType = matchType;
	}
	
	public MassType getMassType() {
		return massType;
	}

	/**
	 * Get a new fragment ion searcher with the specified search parameters.
	 * @param massTolerance Any matched peak will be within +/- massTolerance/2 m/z (inclusive) of the calculated ion for the peptide
	 * @param matchType Either IonUtils.MATCH_TYPE_PROXIMITY or IonUtils.MATCH_TYPE_INTENSITY for closest peak or
	 *                  most intense peak within the mass tolerance window.
	 * @param massType Either MassUtils.MASS_TYPE_MONOISOTOPIC or MassUtils.MASS_TYPE_AVERAGE
	 * @return The searcher
	 */
	public static FragmentIonSearcher getInstance( double massTolerance, int matchType, MassType massType ) {
		FragmentIonSearcher fis = new FragmentIonSearcher();
		
		fis.setMassTolerance( massTolerance);
		fis.setMatchType( matchType );
		
		return fis;
	}
	
	private FragmentIonSearcher() { }
	
	private double massTolerance;
	private int matchType;
	private MassType massType;
}
