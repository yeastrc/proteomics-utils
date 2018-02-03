package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Selenocysteine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 150.95364;
		
		if( type == MassType.AVERAGE )
			return 150.0388;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'U';
	}

	@Override
	public String getAbbreviation() {
		return "SeC";
	}

	@Override
	public String getName() {
		return "selenocysteine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C3H7NO2Se";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 3 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 7 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 2 );
		atomCount.put( AtomUtils.ATOM_SELENIUM, 1 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}