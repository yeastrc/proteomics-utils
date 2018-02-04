package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Pyrrolysine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 237.14773;
		
		if( type == MassType.AVERAGE )
			return 237.2982;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'O';
	}

	@Override
	public String getAbbreviation() {
		return "Pyl";
	}

	@Override
	public String getName() {
		return "pyrrolysine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C12H21N3O3";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 12 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 21 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 3 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 3 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}