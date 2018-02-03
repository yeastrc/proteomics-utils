package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Tryptophan implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 186.079313;
		
		if( type == MassType.AVERAGE )
			return 186.2132;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'W';
	}

	@Override
	public String getAbbreviation() {
		return "Trp";
	}

	@Override
	public String getName() {
		return "tryptophan";
	}

	@Override
	public String getMolecularFormula() {
		return "C11H12N2O2";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 11 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 12 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 2 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 2 );
		
		return Collections.unmodifiableMap( atomCount );
	}
	
}