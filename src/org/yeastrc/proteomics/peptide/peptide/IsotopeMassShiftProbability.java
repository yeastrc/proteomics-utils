package org.yeastrc.proteomics.peptide.peptide;

import java.math.BigDecimal;

/**
 * A isotopic shift in mass (relative to monoisotopic mass) and its associated probability
 * @author mriffle
 *
 */
public class IsotopeMassShiftProbability {

	public IsotopeMassShiftProbability( BigDecimal massShift, BigDecimal probability ) {
		this.massShift = massShift;
		this.probability = probability;
	}
	
	private BigDecimal massShift;		// shift relative to monoisotopic mass
	private BigDecimal probability;		// probability of shift (0-1)
	
	public BigDecimal getMassShift() {
		return massShift;
	}
	public BigDecimal getProbability() {
		return probability;
	}
	
}
