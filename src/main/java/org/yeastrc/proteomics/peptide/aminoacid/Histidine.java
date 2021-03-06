package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Histidine implements AminoAcid {

	/**
	 * Taken from http://proteomicsresource.washington.edu/protocols06/masses.php
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 137.058911875;
		
		if( type == MassType.AVERAGE )
			return 137.13928;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'H';
	}

	@Override
	public String getAbbreviation() {
		return "His";
	}

	@Override
	public String getName() {
		return "histidine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C6H9N3O2";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 6 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 7 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 3 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 1 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}