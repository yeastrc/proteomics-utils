package org.yeastrc.proteomics.spectrum.ionmass;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IonMass implements Comparable<IonMass> {
	
	/**
	 * Create an instance of Mass
	 * @param massValue The value (in daltons) you're using to set the mass, can either be uncharged mass or M+H+ (charge 1) mass
	 * @param massType
	 * @throws Exception
	 */
	public IonMass( double massValue, int massType, int precision ) throws Exception {
		
		BigDecimal bdm = new BigDecimal( massValue );
		bdm = bdm.setScale(precision, RoundingMode.HALF_UP);
		this.mass = bdm;
		
		if( massType == IonMassUtils.ION_MASS_TYPE_PLUS_PROTON ) {
			
			this.protonMass = new BigDecimal( IonMassConstants.PROTON );
			this.protonMass.setScale( precision, RoundingMode.HALF_UP );
			
			this.mass = bdm.subtract( protonMass );

		} else if( massType != IonMassUtils.ION_MASS_TYPE_ACTUAL ) {
			throw new RuntimeException( "Unknown mass type." );
		}
		
	}

	
	/**
	 * Create an instance of Mass
	 * @param massValue The value you're using to set the mass, can either be uncharged mass or M+H+ (charge 1) mass
	 * @param massType
	 * @throws Exception
	 */
	public IonMass( String massValue, int massType, int precision ) throws Exception {
		
		BigDecimal bdm = new BigDecimal( massValue );
		bdm = bdm.setScale(precision, RoundingMode.HALF_UP);
		this.mass = bdm;
		
		if( massType == IonMassUtils.ION_MASS_TYPE_PLUS_PROTON ) {
			
			this.protonMass = new BigDecimal( IonMassConstants.PROTON );
			this.protonMass.setScale( precision, RoundingMode.HALF_UP );
			
			this.mass = bdm.subtract( protonMass );

		} else if( massType != IonMassUtils.ION_MASS_TYPE_ACTUAL ) {
			throw new RuntimeException( "Unknown mass type." );
		}
		
	}
	
	/**
	 * Get the value for mass stored in this Mass object (in daltons)
	 * @param massType
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getMassValue( int massType ) throws Exception {
		if( massType == IonMassUtils.ION_MASS_TYPE_ACTUAL ) {
			return this.mass;
		} else if( massType == IonMassUtils.ION_MASS_TYPE_PLUS_PROTON ) {
			return this.mass.add( this.protonMass );
		} else {
			throw new RuntimeException( "Unknown mass type." );
		}
	}
	
	// actual mass (in daltons) represented by this object
	private BigDecimal mass;
	

	// mass of a proton using the precision passed in
	private BigDecimal protonMass = null;

	@Override
	public int hashCode() {
		return this.mass.hashCode();
	}
	
	/**
	 * Will return true if the two masses are exactly the same number, even if the two
	 * masses have different precision (e.g., 2.000 == 2.0 == 2 )
	 * @param mo
	 * @return
	 */
	@Override
	public boolean equals( Object mo ) {
		return this.compareTo( (IonMass)mo ) == 0;
	}
	
	@Override
	public int compareTo( IonMass mo ) {
		return this.mass.compareTo( mo.mass );	
	}
	

}
