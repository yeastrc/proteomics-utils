package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Tyrosine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 163.06333;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 163.1760;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'Y';
	}

	@Override
	public String getAbbreviation() {
		return "Tyr";
	}

	@Override
	public String getName() {
		return "tyrosine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C9H11NO3";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 9 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 11 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 3 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}