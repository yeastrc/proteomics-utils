package org.yeastrc.proteomics.modifications.types;

import org.yeastrc.proteomics.mass.MassUtils;

public class Sulfation extends Modification {

	@Override
	public String getName() {
		return "sulfation";
	}

	@Override
	public double getMass(int type) throws Exception {

		// info taken from taken from http://www.unimod.org/modifications_view.php?editid1=21
		
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 94.967714;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 95.0778;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );		
	}


	@Override
	public Integer getUnimodAccession() {
		return 997;
	}

	@Override
	public char[] getModifiedResidues() {
		return new char[]{ 'Y' };
	}

}
