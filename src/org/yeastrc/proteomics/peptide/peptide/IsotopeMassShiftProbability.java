package org.yeastrc.proteomics.peptide.peptide;

import java.math.BigDecimal;

/**
 * A isotopic shift in mass (relative to monoisotopic mass) and its associated probability
 * @author mriffle
 *
 */
public class IsotopeMassShiftProbability {
	

	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((massShift == null) ? 0 : massShift.hashCode());
		result = prime * result + ((probability == null) ? 0 : probability.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IsotopeMassShiftProbability other = (IsotopeMassShiftProbability) obj;
		if (massShift == null) {
			if (other.massShift != null)
				return false;
		} else if (!massShift.equals(other.massShift))
			return false;
		if (probability == null) {
			if (other.probability != null)
				return false;
		} else if (!probability.equals(other.probability))
			return false;
		return true;
	}
	
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
