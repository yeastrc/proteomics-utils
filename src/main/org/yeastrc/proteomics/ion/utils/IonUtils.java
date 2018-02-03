package org.yeastrc.proteomics.ion.utils;

import org.yeastrc.proteomics.ion.object.FragmentIon;
import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;
import org.yeastrc.proteomics.peptide.peptide.PeptideMassCalculator;
import org.yeastrc.proteomics.spectrum.ionmass.IonMassConstants;

public class IonUtils {

	public static final int MATCH_TYPE_PROXIMITY = 0;	// nearest peak
	public static final int MATCH_TYPE_INTENSITY = 1;	// most intense peak

	
	public static final int ION_TYPE_A = 0;
	public static final int ION_TYPE_B = 1;
	public static final int ION_TYPE_C = 2;
	public static final int ION_TYPE_X = 3;
	public static final int ION_TYPE_Y = 4;
	public static final int ION_TYPE_Z = 5;
	public static final int ION_TYPE_Z_DOT = 6;

	/**
	 * See: http://www.matrixscience.com/help/fragmentation_help.html
	 * 
	 * [N] is the molecular mass of the neutral N-terminal group, [C] is the molecular mass of the neutral C-terminal
	 * group, [M] is molecular mass of the neutral amino acid residues. To obtain m/z values, add or subtract protons
	 * as required to obtain the required charge and divide by the number of charges. For example, to get a+, add 1
	 * proton to the Mr value for a. To get a–, subtract 2 protons from the Mr value for a and divide by 2.
	 * 
	 * @param ion
	 * @param massType
	 * @return
	 * @throws Exception
	 */
	public static double calculateMass( FragmentIon ion, MassType massType ) throws Exception {
		
		// get mass of underlying residues
		double mass = PeptideMassCalculator.getInstance().getMassOfResiduesForPeptide(ion.getPeptide(), massType);
		
		// add mass of the protons, determined by the + charge
		if( ion.getCharge() > 0 )
			mass += ion.getCharge() * IonMassConstants.PROTON;
		else
			throw new Exception( "Negative charges are not supported." );
		
		// adjust mass based on the type of ion
		if( ion.getIonType() == IonUtils.ION_TYPE_B ) {
			
			// [N]+[M]-H
			return mass;
		}
		
		if( ion.getIonType() == IonUtils.ION_TYPE_Y ) {

			// [C]+[M]+H
			mass += AtomUtils.ATOM_OXYGEN.getMass( massType ) + ( 2 * AtomUtils.ATOM_HYDROGEN.getMass( massType ) );
			return mass;
		}		
		
		if( ion.getIonType() == IonUtils.ION_TYPE_A ) {
			
			// [N]+[M]-CHO
			mass -= ( AtomUtils.ATOM_CARBON.getMass( massType ) + AtomUtils.ATOM_OXYGEN.getMass( massType ) );
			return mass;
			
		}
		
		if( ion.getIonType() == IonUtils.ION_TYPE_C ) {
			
			// [N]+[M]+NH2
			mass += AtomUtils.ATOM_NITROGEN.getMass( massType ) + ( 3 * AtomUtils.ATOM_HYDROGEN.getMass( massType ) );
			return mass;
			
		}
		
		if( ion.getIonType() == IonUtils.ION_TYPE_X ) {
			
			// [C]+[M]+CO-H
			mass += AtomUtils.ATOM_CARBON.getMass( massType ) + ( 2 * AtomUtils.ATOM_OXYGEN.getMass( massType ) );
			return mass;
			
		}
		
		if( ion.getIonType() == IonUtils.ION_TYPE_Z ) {

			// [C]+[M]-NH2
			mass += AtomUtils.ATOM_OXYGEN.getMass( massType ) - AtomUtils.ATOM_NITROGEN.getMass( massType ) - AtomUtils.ATOM_HYDROGEN.getMass( massType );
			return mass;
		}
		
		if( ion.getIonType() == IonUtils.ION_TYPE_Z_DOT ) {

			// [C]+[M]-NH2
			mass += AtomUtils.ATOM_OXYGEN.getMass( massType ) - AtomUtils.ATOM_NITROGEN.getMass( massType );
			return mass;
		}
		
		// if we got here, this ion type was not supported
		throw new Exception( "Ion type is not supported." );

		
	}
	
	
	/**
	 * Compares two mass to charge ratios (m/z). A difference less than 0.000001 is assumed to be equal.
	 * Returns -1 if a < b, 0 if a == b, and 1 if a > b
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compareMassToCharge( double a, double b ) {
		double epsilon = 0.000001d;
		
		double diff = a - b;
		
		if( Math.abs(diff) < epsilon ) return 0;
		if( diff > 0 ) return 1;
		return -1;
	}
	
}
