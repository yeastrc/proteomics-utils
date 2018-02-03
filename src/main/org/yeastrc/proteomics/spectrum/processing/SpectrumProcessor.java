package org.yeastrc.proteomics.spectrum.processing;

import org.yeastrc.proteomics.spectrum.object.Spectrum;

public interface SpectrumProcessor {
	
	/**
	 * Process the input spectrum according to the implementing method and return
	 * a new spectrum (original spectrum is unchanged).
	 * @param inputSpectrum
	 * @return
	 */
	public Spectrum processSpectrum( Spectrum inputSpectrum ) throws Exception;

	
}
