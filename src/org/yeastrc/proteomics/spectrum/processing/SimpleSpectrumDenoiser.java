package org.yeastrc.proteomics.spectrum.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.yeastrc.proteomics.spectrum.object.Peak;
import org.yeastrc.proteomics.spectrum.object.Spectrum;

/**
 * A simple spectrum denoiser. Will remove peaks from a MSn (e.g. MS2) spectrum
 * by keeping the X most intense peaks, or only the peaks with an intensity that
 * is at least some fraction of the total intensity of the spectrum.
 * 
 * @author Michael Riffle
 * @version 1/0
 *
 */
public class SimpleSpectrumDenoiser implements SpectrumProcessor {

	/**
	 * Process the input spectrum and return a new spectrum which has been "denoised" according
	 * to the values set in this processor.
	 * @param inputSpectrum The input spectrum
	 * @return A new spectrum, which has been processed (peaks removed, added or changed)
	 */
	@Override
	public Spectrum processSpectrum( Spectrum inputSpectrum ) {
				
		List<Peak> tmpPeaks = new ArrayList<Peak>();
		List<Peak> newPeaks = null;

		// filter by fraction of total intensity
		if( this.getFractionTotalIntensity() != 0.0 ) {
			double cutoff = this.getFractionTotalIntensity() * inputSpectrum.getTotalIntensity();
			for( Peak p : inputSpectrum.getPeaks() ) {
				if( p.getIntensity() >= cutoff )
					tmpPeaks.add( p );
			}
		} else {
			tmpPeaks.addAll( inputSpectrum.getPeaks() );
		}
		
		
		// we are filtering out all but the top N peaks
		if( this.getMaxPeaks() > 0 && tmpPeaks.size() > this.getMaxPeaks() ) {
			
			double[] sortedIntensities = new double[ tmpPeaks.size() ];
			for( int i = 0; i < tmpPeaks.size(); i++ )
				sortedIntensities[ i ] = tmpPeaks.get( i ).getIntensity();
			
			Arrays.sort( sortedIntensities );
			
			// get the intensity to use as a filter
			double intensityFilter = sortedIntensities[ sortedIntensities.length - this.getMaxPeaks() ];
			
						
			// find the number of peaks that have this intensity value that we should include
			// e.g., we have 6 peaks w/ the same intensity value which is the same as our intensityFilter cutoff
			// however, we only have room to include 5 of them in our final list of N peaks
			int lastPlaceCount = 0;
			for( int i = (sortedIntensities.length - this.getMaxPeaks()); i < sortedIntensities.length; i++ ) {
				
				if( sortedIntensities[ i ] == intensityFilter )
					lastPlaceCount++;
				else
					break;
			}
						
			// create a new list of peaks that only include the # of peaks requested
			newPeaks = new ArrayList<Peak>( this.getMaxPeaks() );
			int lastPlacesIncluded = 0;
			for( Peak p : tmpPeaks ) {
				if( p.getIntensity() > intensityFilter )
					newPeaks.add( p );
				else if( p.getIntensity() == intensityFilter ) {
					if( lastPlacesIncluded < lastPlaceCount ) {
						lastPlacesIncluded++;
						newPeaks.add( p );
					}
				}
			}
			
			
			if( newPeaks.size() != this.getMaxPeaks() ) {
				throw new RuntimeException( "Got wrong number of filtered peaks. Expected " + this.getMaxPeaks() +" Got " + newPeaks.size() );
			}
		} else {
			newPeaks = tmpPeaks;
		}

		
		return new Spectrum( newPeaks );
	}
	
	/**
	 * Get the number of max peaks that will be included for any spectrum processed by this denoiser.
	 * @return
	 */
	public int getMaxPeaks() {
		return maxPeaks;
	}
	
	/**
	 * Set the number of max peaks that will be included for any spectrum processed by this denoiser.
	 * @param maxPeaks
	 */
	private void setMaxPeaks(int maxPeaks) {
		this.maxPeaks = maxPeaks;
	}

	/**
	 * Get the fraction of the total ion intensity for a spectrum that a given peak must have to be
	 * included after denoising.
	 * @return
	 */
	public double getFractionTotalIntensity() {
		return fractionTotalIntensity;
	}
	
	/**
	 * Get the fraction of the total ion intensity for a spectrum that a given peak must have to be
	 * included after denoising.
	 * @param fractionTotalIntensity
	 */
	private void setFractionTotalIntensity(double fractionTotalIntensity) {
		this.fractionTotalIntensity = fractionTotalIntensity;
	}
	
	private int maxPeaks = -1;
	private double fractionTotalIntensity = 0.0;
	
	private SimpleSpectrumDenoiser() { }
	
	/**
	 * Get an instance of a SimpleSpectrumDenoiser with the supplied parameters
	 * @param maxPeaks Keep at most maxPeaks of the most intense peaks.
	 * @param fractionTotalIntensity Any peak with an intensity < the total intensity for the spectrum
	 *        multiplied by fractionTotalIntensity will be removed.
	 * @return
	 * @throws Exception
	 */
	public static SimpleSpectrumDenoiser createInstance( int maxPeaks, double fractionTotalIntensity ) throws Exception {
		
		if( maxPeaks <= 0 )
			throw new IllegalArgumentException( "maxPeaks must be > 0 " );
		
		if( fractionTotalIntensity < 0.0 || fractionTotalIntensity > 1.0 )
			throw new IllegalArgumentException( "fractionTotalIntensity must be between 0 and 1." );
		
		SimpleSpectrumDenoiser ssd = new SimpleSpectrumDenoiser();
		ssd.setMaxPeaks( maxPeaks );
		ssd.setFractionTotalIntensity( fractionTotalIntensity );
		
		return ssd;
	}
	
	/**
	 * Get an instance of a SimpleSpectrumDenoiser with the supplied parameters
	 * @param maxPeaks Keep at most maxPeaks of the most intense peaks. Defaults to 100
	 * @return
	 * @throws Exception
	 */
	public static SimpleSpectrumDenoiser createInstance( int maxPeaks ) throws Exception {
		
		if( maxPeaks <= 0 )
			throw new IllegalArgumentException( "maxPeaks must be > 0 " );
		
		SimpleSpectrumDenoiser ssd = new SimpleSpectrumDenoiser();
		ssd.setMaxPeaks( maxPeaks );
		
		return ssd;
	}
	
	/**
	 * Get an instance of a SimpleSpectrumDenoiser with the supplied parameters
	 * @param fractionTotalIntensity Any peak with an intensity < the total  intensity for the spectrum
	 *        multiplied by fractionTotalIntensity will be removed.
	 * @return
	 * @throws Exception
	 */
	public static SimpleSpectrumDenoiser createInstance( double fractionTotalIntensity ) throws Exception {
		
		if( fractionTotalIntensity < 0.0 || fractionTotalIntensity > 1.0 )
			throw new IllegalArgumentException( "fractionTotalIntensity must be between 0 and 1." );
		
		SimpleSpectrumDenoiser ssd = new SimpleSpectrumDenoiser();
		ssd.setFractionTotalIntensity( fractionTotalIntensity );
		
		return ssd;
	}
}
