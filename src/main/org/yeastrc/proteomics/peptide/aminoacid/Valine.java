package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Valine implements AminoAcid {

	/**
	 * Taken from http://proteomicsresource.washington.edu/protocols06/masses.php
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 99.068413945;
		
		if( type == MassType.AVERAGE )
			return 99.13106;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'V';
	}

	@Override
	public String getAbbreviation() {
		return "Val";
	}

	@Override
	public String getName() {
		return "valine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C5H11NO2";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 5 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 11 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 2 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}