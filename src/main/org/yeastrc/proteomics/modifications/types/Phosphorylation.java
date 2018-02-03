package org.yeastrc.proteomics.modifications.types;

import org.yeastrc.proteomics.mass.MassUtils;

public class Phosphorylation extends Modification {

	@Override
	public String getName() {
		return "phosphorylation";
	}

	@Override
	public double getMass(int type) throws Exception {

		// info taken from taken from http://www.unimod.org/modifications_view.php?editid1=21
		
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 79.966331;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 79.9799;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );		
	}


	@Override
	public Integer getUnimodAccession() {
		return 21;
	}

	@Override
	public char[] getModifiedResidues() {
		return new char[]{ 'S', 'T', 'Y' };
	}

}
