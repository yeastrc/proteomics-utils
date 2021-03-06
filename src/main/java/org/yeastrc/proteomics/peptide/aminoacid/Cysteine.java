package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Cysteine implements AminoAcid {

	/**
	 * Taken from http://proteomicsresource.washington.edu/protocols06/masses.php
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 103.009184505;
		
		if( type == MassType.AVERAGE )
			return 103.1429;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'C';
	}

	@Override
	public String getAbbreviation() {
		return "Cys";
	}

	@Override
	public String getName() {
		return "cysteine";
	}

	@Override
	public String getMolecularFormula() {
		return "C3H7NO2S";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 3 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 5 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 1 );
		atomCount.put( AtomUtils.ATOM_SULFUR, 1 );
		
		return Collections.unmodifiableMap( atomCount );
	}
	
}