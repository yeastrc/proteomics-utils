package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Alanine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 71.037114;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 71.0788;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'A';
	}

	@Override
	public String getAbbreviation() {
		return "Ala";
	}

	@Override
	public String getName() {
		return "alanine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C3H7NO2";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 3 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 7 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 2 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}