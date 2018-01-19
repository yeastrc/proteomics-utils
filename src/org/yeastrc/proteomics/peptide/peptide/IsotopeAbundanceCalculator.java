package org.yeastrc.proteomics.peptide.peptide;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yeastrc.proteomics.peptide.aminoacid.AminoAcid;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcidUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.MathUtils;

public class IsotopeAbundanceCalculator {

	private static final IsotopeAbundanceCalculator _INSTANCE = new IsotopeAbundanceCalculator();
	private IsotopeAbundanceCalculator() { }
	public static IsotopeAbundanceCalculator getInstance() { return _INSTANCE; }
		
	/**
	 * The probability below which no isotope mass shifts will be considered
	 */
	private static final BigDecimal _PROBABILITY_CUTOFF = getBigDecimal( 1E-6 );
	
	
	/**
	 * A map of atom => mass shift (vs/ monoisotopic) => probability of observing this mass shift
	 */
	private static final Map< Atom, Map<BigDecimal, BigDecimal>> _ATOMIC_MASS_SHIFT_PROBABILITIES;
	
	static {
	
		// from https://chemistry.sciences.ncsu.edu/msf/pdf/IsotopicMass_NaturalAbundance.pdf
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES = new HashMap<>();
		
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_CARBON, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).put( getBigDecimal( 1.003355 ), getBigDecimal( 0.0107 ) );	// 13C
		
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_HYDROGEN, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).put( getBigDecimal( 1.006277 ), getBigDecimal( 0.000115 ) );	// 2H
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_NITROGEN, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_NITROGEN ).put( getBigDecimal( 0.997035 ), getBigDecimal( 0.00368 ) );	// 15N

		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_OXYGEN, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).put( getBigDecimal( 1.004217 ), getBigDecimal( 0.00038 ) );	// 17O
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).put( getBigDecimal( 2.004245 ), getBigDecimal( 0.00205 ) );	// 18O

		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_SULFUR, new HashMap<>() );
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).put( getBigDecimal( 0.999387 ), getBigDecimal( 0.0076 ) );	// 33S
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).put( getBigDecimal( 1.995796 ), getBigDecimal( 0.0429 ) );	// 34S
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).put( getBigDecimal( 3.99501 ), getBigDecimal( 0.0002 ) );	// 36S
				
	}
	
	/**
	 * Get a list of mass shifts, in order of most likely to least likely, for the supplied peptide--limited
	 * to a total count of the limit supplied. Note that this method may return more than the limit if there
	 * is a tie for probabilities in last place.
	 * 
	 * @param peptide
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List< IsotopeMassShiftProbability > getIsotopMassShiftProbabilities( Peptide peptide, int limit ) throws Exception {
		
		List<IsotopeMassShiftProbability> massShiftProbabilities = new ArrayList<>(limit);
		BigDecimal currentLowestProbability = null;
		
		// get the count for each atom for the entire peptide
		Map< Atom, Integer > atomCount = getAtomCountForPeptide( peptide );
		
		// lock in an order for the atoms
		List<Atom> atomList = new ArrayList<>();
		atomList.addAll( atomCount.keySet() );
		
		
		processAtomList( atomList, atomCount, new Integer( 0 ), getBigDecimal( 1.0 ), massShiftProbabilities, currentLowestProbability, limit );
		
		return massShiftProbabilities;
	}
	
	/**
	 * Use recursion
	 * 
	 * @param atomList
	 * @param index
	 * @param massShiftProbabilities
	 * @param currentLowestProbability
	 */
	void processAtomList( List<Atom> atomList, Map< Atom, Integer > atomCount, Integer index, BigDecimal currentProbability, List<IsotopeMassShiftProbability> massShiftProbabilities, BigDecimal currentLowestProbability, int limit ) {
		
		// no need to process any possibilities if we're already below our cutoff
		if( currentProbability.compareTo( _PROBABILITY_CUTOFF ) < 0 )
			return;
		
		Atom atom = atomList.get( index );
		
		BigDecimal probabilitySumForElement = getTotalProbabilityForAllIsotopesForElement( _ATOMIC_MASS_SHIFT_PROBABILITIES.get( atom ) );
		
		
		// iterate over the number of possible isotopes of this particular element ( 0 .. # of atoms for this element )
		for( int count = 0; count <= atomCount.get( atom ); count++ ) {
			
			// get the exact probability of this number of atoms having any isotope mass shift
			double doubleProbability = getProbability( count, atomCount.get( atom ), probabilitySumForElement.doubleValue() );
			BigDecimal probability = getBigDecimal( doubleProbability );
			
			BigDecimal newCurrentProbability = currentProbability.multiply( probability );

			// no need to continue this further if it doesn't meet the cutoff
			if( newCurrentProbability.compareTo( _PROBABILITY_CUTOFF ) < 0 )
				continue;
			
			// no need to continue this further if current list is full and minimum probability is greater than this
			if( massShiftProbabilities.size() >= limit && currentLowestProbability.compareTo( newCurrentProbability ) > 0 )
				continue;
			
			
			
		}
		
		
		
	}

	
	/**
	 * Get the sum of all probabilities for isotope mass shifts for this element
	 * @param elementMap
	 * @return
	 */
	BigDecimal getTotalProbabilityForAllIsotopesForElement( Map<BigDecimal, BigDecimal> elementMap ) {
		
		BigDecimal sum = getBigDecimal( 0.0 );
		
		for( BigDecimal massShift : elementMap.keySet() ) {
			sum.add( elementMap.get( massShift ) );
		}
		
		return sum;
		
	}
	
	
	
	
	/**
	 * Get a map of the counts for each distinct atom in the supplied peptide
	 * 
	 * @param peptide
	 * @return
	 */
	private Map< Atom, Integer > getAtomCountForPeptide( Peptide peptide ) throws Exception {
		
		Map< Atom, Integer > atomCountMap = new HashMap<>();
		
		String s = peptide.getSequence();
		
		for (int i = 0; i < s.length(); i++){
			
			AminoAcid residue = AminoAcidUtils.getAminoAcidBySymbol( s.charAt(i) );
			Map<Atom, Integer> residueAtomCount = residue.getParsedAtomCount();
			
			for( Atom atom : residueAtomCount.keySet() ) {
				
				if( !atomCountMap.containsKey( atom ) )
					atomCountMap.put( atom, 0 );
				
				atomCountMap.put( atom, atomCountMap.get( atom ) + residueAtomCount.get( atom ) );
				
			}
		}

		return atomCountMap;
	}
	
	/**
	 * For comparison purposes, standardize how we get big decimals
	 * 
	 * @param d
	 * @return
	 */
	private static BigDecimal getBigDecimal( double d ) {
		return new BigDecimal( 1E-6, MathContext.DECIMAL64);
	}
	
	
	private double getProbability( int successes, int trials, double probabilityOfEvent ) {
		    return Math.exp(ArithmeticUtils.binomialCoefficientLog(trials, successes) + 
		      (successes * Math.log(probabilityOfEvent)) + ((trials - successes) * Math.log(1 - probabilityOfEvent)));
		  }
	
}

