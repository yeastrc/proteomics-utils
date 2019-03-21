package org.yeastrc.proteomics.spectrum.object;

public class Peak implements Comparable<Peak> {
	
	@Override
	public boolean equals( Object o ) {
		if( !(o instanceof Peak) )
			return false;
		
		if( ((Peak)o).getIntensity() == this.getIntensity() && ((Peak)o).getMassToCharge() == this.getMassToCharge() )
			return true;
		
		return false;
	}
	
	/**
	 * Define natural ordering for peaks. They are ordered by m/z values, and then
	 * by intensity if m/z are equal
	 * @param p
	 * @return
	 */
	@Override
	public int compareTo(Peak p) {
		if( this.getMassToCharge() < p.getMassToCharge() ) return -1;
		if( this.getMassToCharge() > p.getMassToCharge() ) return 1;
		
		if( this.getIntensity() < p.getIntensity() ) return -1;
		if( this.getIntensity() > p.getIntensity() ) return 1;
		
		return 0;
	}
	
	@Override
	public int hashCode() {
		return (new Double( this.massToCharge ) ).hashCode();
	}
	
	public double getMassToCharge() {
		return massToCharge;
	}
	public double getIntensity() {
		return intensity;
	}
	public Spectrum getSpectrum() {
		return spectrum;
	}
	
	private final double massToCharge;
	private final double intensity;
	private final Spectrum spectrum;
	
	public Peak( Spectrum spectrum, double massToCharge, double intensity ) {
		this.massToCharge = massToCharge;
		this.intensity = intensity;
		this.spectrum = spectrum;
	}


	
}
