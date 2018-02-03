package org.yeastrc.proteomics.spectrum.processing;

public class SpectrumBin {
	
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	
	public SpectrumBin( int start, int end ) {
		this.start = start;
		this.end = end;
	}
	
	public final int start;
	public final int end;
	
}
