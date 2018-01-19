package org.yeastrc.proteomics.peptide.peptide;

import java.util.HashMap;
import java.util.Map;

import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;

public class IsotopeAbundanceCalculator {

	/**
	 * A map of atom => mass shift (vs/ monoisotopic) => probability of observing this mass shift
	 */
	private static final Map< Atom, Map<Double, Double>> _ATOMIC_MASS_SHIFT_PROBABILITIES;
	
	static {
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES = new HashMap<>();
		
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_CARBON, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).put( 0.0, 0.9893 );		// 12C
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).put( 1.003355, 0.0107 );	// 13C
		
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_HYDROGEN, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).put( 0.0, 0.999885  );		// 1H
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).put( 1.003355, 1.006277  );	// 2H
		
		
		
		
	}
	
}
