package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Arginine implements AminoAcid {

	/**
	 * Taken from http://proteomicsresource.washington.edu/protocols06/masses.php
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 156.101111050;
		
		if( type == MassType.AVERAGE )
			return 156.18568;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'R';
	}

	@Override
	public String getAbbreviation() {
		return "Arg";
	}

	@Override
	public String getName() {
		return "arginine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C6H14N4O2";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 6 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 14 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 4 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 2 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}