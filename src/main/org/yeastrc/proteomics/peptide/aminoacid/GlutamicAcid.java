package org.yeastrc.proteomics.peptide.aminoacid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.mass.MassUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class GlutamicAcid implements AminoAcid {

	/**
	 * Taken from http://en.wikipedia.org/wiki/Proteinogenic_amino_acid
	 */
	@Override
	public double getMass(int type) throws Exception {
		if( type == MassUtils.MASS_TYPE_MONOISOTOPIC )
			return 129.042593;
		
		if( type == MassUtils.MASS_TYPE_AVERAGE )
			return 129.1155;
		
		throw new IllegalArgumentException( "Did not get a valid mass type." );	
	}

	@Override
	public char getSymbol() {
		return 'E';
	}

	@Override
	public String getAbbreviation() {
		return "Glu";
	}

	@Override
	public String getName() {
		return "glutamic acid";
	}
	
	@Override
	public String getMolecularFormula() {
		return "C5H9NO4";
	}
	
	@Override
	public Map<Atom, Integer> getParsedAtomCount() {
		
		Map<Atom,Integer> atomCount = new HashMap<>();
		
		atomCount.put( AtomUtils.ATOM_CARBON, 5 );
		atomCount.put( AtomUtils.ATOM_HYDROGEN, 9 );
		atomCount.put( AtomUtils.ATOM_NITROGEN, 1 );
		atomCount.put( AtomUtils.ATOM_OXYGEN, 4 );
		
		return Collections.unmodifiableMap( atomCount );
	}

}