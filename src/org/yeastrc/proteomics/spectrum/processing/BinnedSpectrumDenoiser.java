package org.yeastrc.proteomics.spectrum.processing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.yeastrc.proteomics.spectrum.object.Peak;
import org.yeastrc.proteomics.spectrum.object.Spectrum;

/**
 * Separates input spectrum into bins with width of binWidth and keeps
 * the most intense numPeaks peak per bin. Bins will always begin at the
 * lowest multiple of binWidth that will contain the first peak and always
 * end so that the final bin contains the final peak. E.g., the
 * first bin will be mz >= 100 < 200 if the first peak is 136 and the binWidth
 * is 100. If the binWidth is 25, the first bin would be mz >= 125 and < 150
 * 
 * @author Michael Riffle
 *
 */
public class BinnedSpectrumDenoiser implements SpectrumProcessor {

	@Override
	public Spectrum processSpectrum(Spectrum spectrum) throws Exception {
		
		List<SpectrumBin> bins = this.getBins( spectrum );
		List<Peak> newPeaks = new ArrayList<Peak>();		// the new list of peaks we're building for the new spectrum
		
		// loop over each of the bins, then loop over and find peaks that fit in that bin and determine which of those to keep
		// in the peak list for this specific bin. Then add the peak list for the bin to the new list of peaks we're building.
		for( SpectrumBin bin : bins ) {
			Collection<Peak> binPeaks = new TreeSet<Peak>();
			
			for( Peak p : spectrum.getPeaks() ) {
				
				// only examine peaks with a m/z in the range of the current bin
				if( p.getMassToCharge() >= bin.start && p.getMassToCharge() < bin.end ) {
					
					if( binPeaks.size() < this.numPeaks ) {
						binPeaks.add( p );
					} else {
						
						// iterate over peaks chosen so far, determine which one (if any) should be removed in favor of the current peak
						Peak peakToRemove = null;
						for( Peak binPeak : binPeaks ) {
							if( p.getIntensity() > binPeak.getIntensity() ) {
								if( peakToRemove == null || binPeak.getIntensity() < peakToRemove.getIntensity() ) {
									peakToRemove = binPeak;
								}
							}
						}
						
						if( peakToRemove != null ) {
							binPeaks.remove( peakToRemove );
							binPeaks.add( p );
						}
						
					}
					
				}
				
			}//end iteration over peaks
			
			// add all the peaks for this bin to the new peak list
			newPeaks.addAll( binPeaks );
			
		}//end iteration over bins
		
		return new Spectrum( newPeaks );		
	}	
	
	/**
	 * Get the bins calculated for this BinnedSpectrumDenoiser given the
	 * binWidth and numPeaks defined.
	 * @param spectrum
	 * @return
	 * @throws Exception
	 */
	public List<SpectrumBin> getBins( Spectrum spectrum ) throws Exception {
		
		if( spectrum.getPeaks() == null || spectrum.getPeaks().size() < 1 )
			throw new Exception( "Spectrum has no peaks." );
		
		List<SpectrumBin> bins = new ArrayList<SpectrumBin>();
		
		if( this.binWidth <= 0 || this.numPeaks < 1 )
			throw new Exception( "Bin width and/or num peaks haven't been defined." );
			

		double minMz = 0.0;
		double maxMz = 0.0;
				
		// find min and max peaks--it is not assumed that the peaks are in order of m/z,
		// though such an assumption could possibly be made. Review in future.
		for (Peak p : spectrum.getPeaks()) {
			if (minMz == 0.0 || p.getMassToCharge() < minMz)
				minMz = p.getMassToCharge();

			if (maxMz == 0.0 || p.getMassToCharge() > maxMz)
				maxMz = p.getMassToCharge();
		}

		int firstStart = getPreviousMultiple(this.binWidth, minMz);
		int lastStart = getPreviousMultiple(this.binWidth, maxMz);

		for (int i = firstStart; i <= lastStart; i += this.binWidth) {
			SpectrumBin bin = new SpectrumBin( i, i + this.binWidth );
			bins.add(bin);
		}

		return bins;
	}
	
	/**
	 * Get the previous multiple of "range" from the starting value.
	 * E.g., if range is 50, this method would return 150 for a value
	 * of 150-199, or a value of 500 for a value of 500-549.
	 * @param range
	 * @param value
	 * @return
	 */
	protected static int getPreviousMultiple( int range, double value ) {
		
		int startingValue = (int)value;
		int diff = startingValue % range;
		
		return startingValue - diff;
	}
	
	/**
	 * Get the next multiple of "range" from the starting value.
	 * E.g., if range is 50, this method would return 200 for a value
	 * of 151-200, or a value of 500 for a value of 451-500.
	 * @param range
	 * @param value
	 * @return
	 */
	protected static int getNextMultiple( int range, double value ) {
		
		int startingValue = (int)value;
		int diff = startingValue % range;
		
		return (startingValue - diff) + range;
	}
	
	
	public static BinnedSpectrumDenoiser getInstance( int binWidth, int numPeaks ) {
		return new BinnedSpectrumDenoiser( binWidth, numPeaks );
	}

	public BinnedSpectrumDenoiser( int binWidth, int numPeaks ) {
		this.binWidth = binWidth;
		this.numPeaks = numPeaks;
	}
	
	public int getBinWidth() {
		return binWidth;
	}
	public int getNumPeaks() {
		return numPeaks;
	}


	private final int binWidth;
	private final int numPeaks;
	
}
