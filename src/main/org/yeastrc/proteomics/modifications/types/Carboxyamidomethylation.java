package org.yeastrc.proteomics.modifications.types;

import org.yeastrc.proteomics.mass.MassUtils;

public class Carboxyamidomethylation extends Modification {

	@Override
	public String getName() {
		return "carboxyamidomethylation";
	}

	@Override
	public double getMass(int type) throws Exception {
		// info taken from taken from http://www.unimod.org/modifications_view.php?editid1=4
		
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 57.021464;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 57.0513;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public Integer getUnimodAccession() {
		return 4;
	}

	@Override
	public char[] getModifiedResidues() {
		return new char[]{ 'C' };
	}

}
