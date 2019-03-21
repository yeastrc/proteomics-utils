package org.yeastrc.proteomics.spectrum.object;

import java.util.List;

public class Spectrum {

	/**
	 * Returns a sum of all the intensities of all the peaks
	 * @return
	 */
	public double getTotalIntensity() {
		
		if( this.totalIntensity == 0.0 ) {
			for( Peak p : this.peaks ) {
				this.totalIntensity += p.getIntensity();
			}
		}
		
		return this.totalIntensity;
	}
	
	
	public List<Peak> getPeaks() {
		return peaks;
	}

	private final List<Peak> peaks;
	private double totalIntensity = 0.0;
	
	
	public Spectrum( List<Peak> peaks ) {
		this.peaks = peaks;
	}
	
}
