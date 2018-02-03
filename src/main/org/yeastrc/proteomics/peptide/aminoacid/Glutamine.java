package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class Glutamine implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(MassType type) {
		if( type == MassType.MONOISOTOPIC )
			return 128.058578;
		
		if( type == MassType.AVERAGE )
			return 128.1307;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'Q';
	}

	@Override
	public String getAbbreviation() {
		return "Gln";
	}

	@Override
	public String getName() {
		return "glutamine";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C5H10N2O3";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 5 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 10 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 2 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 3 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}