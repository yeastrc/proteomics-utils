package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class AsparticAcid implements AminoAcid {

	/**
	 * Taken from http://proteomicsresource.washington.edu/protocols06/masses.php
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 115.026943065;
		
		if( type == MassType.AVERAGE )
			return 115.0874;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'D';
	}

	@Override
	public String getAbbreviation() {
		return "Asp";
	}

	@Override
	public String getName() {
		return "aspartic acid";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C4H7NO4";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 4 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 5 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 3 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}