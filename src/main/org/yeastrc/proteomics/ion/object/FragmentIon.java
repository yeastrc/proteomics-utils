package org.yeastrc.proteomics.ion.object;

import org.yeastrc.proteomics.ion.utils.IonUtils;
import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.peptide.Peptide;

public class FragmentIon {

	/**
	 * Get the m/z for this fragment ion
	 * @param massType
	 * @return
	 * @throws Exception
	 */
	public double getMassToCharge( MassType massType ) throws Exception {
		return IonUtils.calculateMass( this, massType ) / this.getCharge();
	}
	
	public Peptide getPeptide() {
		return peptide;
	}
	public int getCharge() {
		return charge;
	}
	public int getIonType() {
		return ionType;
	}
	public int getIndex() {
		return index;
	}

	/**
	 * Get an instance of this class
	 * @param peptide
	 * @param charge
	 * @param ionType
	 * @param index
	 * @return
	 */
	public static FragmentIon createInstance( Peptide peptide, int charge, int ionType, int index ) throws Exception {
		
		if( charge < 1 )
			throw new Exception( "Charge can not be less than 1." );
		
		FragmentIon fi = new FragmentIon();
		fi.peptide = peptide;
		fi.charge = charge;
		fi.index = index;
		fi.ionType = ionType;
		
		return fi;
	}
	
	private FragmentIon() { }

	private Peptide peptide;
	private int charge;
	private int ionType;
	private int index;
	
}
