package org.yeastrc.proteomics.spectrum.ionmass;

public class IonMassUtils {

	/**
	 * Masses of this type are the calculated actual mass of an
	 * uncharged peptide (the sum of the masses of the amino acids)
	 */
	public final static int ION_MASS_TYPE_ACTUAL = 0;

	/**
	 * Masses of this type are the M+H+ (mass plus a proton) of
	 * a +1 charged peptide ion
	 */
	public final static int ION_MASS_TYPE_PLUS_PROTON = 0;


	
	/**
	 * Get the Mass object representing the mass represented by the given m/z and charge
	 * @param massToCharge Reported m/z value
	 * @param charge Reported charge
	 * @param precision Number of decimal places to store for mass
	 * @return
	 * @throws Exception
	 */
	public static IonMass getMassFromMassToCharge( double massToCharge, int charge, int precision ) throws Exception {
		if( charge < 1 )
			throw new RuntimeException( "Charge must be at least 1." );
		
		double cmass = ( massToCharge * charge ) - ( charge * IonMassConstants.PROTON );
		return new IonMass( cmass, IonMassUtils.ION_MASS_TYPE_ACTUAL, precision );
	}
	
	public static double getMassToCharge( IonMass mass, int charge, int precision ) throws Exception {

		// start with the M+H+ mass
		double mz = mass.getMassValue( ION_MASS_TYPE_PLUS_PROTON ).doubleValue();

		mz += ( charge - 1 ) * IonMassConstants.PROTON;				// add on the correct number of proton masses
		mz /= charge;												// divide by the charge
		
		return mz;
	}
	
}
