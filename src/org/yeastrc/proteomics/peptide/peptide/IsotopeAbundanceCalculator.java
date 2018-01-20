package org.yeastrc.proteomics.peptide.peptide;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.yeastrc.proteomics.peptide.aminoacid.AminoAcid;
import org.yeastrc.proteomics.peptide.aminoacid.AminoAcidUtils;
import org.yeastrc.proteomics.peptide.atom.Atom;
import org.yeastrc.proteomics.peptide.atom.AtomUtils;
import org.apache.commons.math3.util.ArithmeticUtils;

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
	public Collection< IsotopeMassShiftProbability > getIsotopMassShiftProbabilities( Peptide peptide, int limit ) throws Exception {
		
		System.out.println( "\tStarting getIsotopMassShiftProbabilities()." );
		
		Collection<IsotopeMassShiftProbability> massShiftProbabilities = new HashSet<>();
		
		// get the count for each atom for the entire peptide
		Map< Atom, Integer > atomCount = getAtomCountForPeptide( peptide );
		
		for( Atom a : atomCount.keySet() ) {
			System.out.println( "\t\t" + a.getSymbol() + ":" + atomCount.get( a ) );
		}
		
		
		// lock in an order for the atoms
		List<Atom> atomList = new ArrayList<>();
		atomList.addAll( atomCount.keySet() );
		
		
		processElementInList( atomList, atomCount, new Integer( 0 ), getBigDecimal( 1.0 ), getBigDecimal( 0.0 ), massShiftProbabilities, limit );
		
		System.out.println( "\tEnding getIsotopMassShiftProbabilities().\n\n" );

		
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
	private void processElementInList( 	List<Atom> atomList,
									Map< Atom, Integer > atomCount,
									Integer index,
									BigDecimal currentProbability,
									BigDecimal currentMassShift,
									Collection<IsotopeMassShiftProbability> massShiftProbabilities,
									int limit
								 ) {
		
		System.out.println( "\n\t\tStarting processElementInList()." );

		
		Atom atom = atomList.get( index );
		
		System.out.println( "\t\t\tatom: " + atom.getSymbol() );

		
		BigDecimal probabilitySumForElement = getTotalProbabilityForAllIsotopesForElement( _ATOMIC_MASS_SHIFT_PROBABILITIES.get( atom ) );
		System.out.println( "\t\t\tprobabilitySumForElement: " + probabilitySumForElement );

		
		// iterate over number of times this element exists in this peptide
		for( int numberOfAtomsWithMassDiff = 0; numberOfAtomsWithMassDiff <= atomCount.get( atom ); numberOfAtomsWithMassDiff++ ) {
						
			// get the exact probability of this number of atoms having any isotope mass shift
			double p = getProbability( numberOfAtomsWithMassDiff, atomCount.get( atom ), probabilitySumForElement.doubleValue() );
			
			//System.out.println( "\t\t\tp: " + p );
			
			BigDecimal probabilityOfAnyMassShiftForElement = getBigDecimal( p );
			BigDecimal probabilityOfNoMassShiftForElement = getBigDecimal( 1.0 ).subtract( probabilityOfAnyMassShiftForElement );
			
			//System.out.println( "\t\t\tprobabilityOfAnyMassShiftForElement: " + probabilityOfAnyMassShiftForElement );
			//System.out.println( "\t\t\tprobabilityOfNoMassShiftForElement: " + probabilityOfNoMassShiftForElement );

			
			BigDecimal cumulativeProbabilityForAnyMassShift = currentProbability.multiply( probabilityOfAnyMassShiftForElement );
			BigDecimal cumulativeProbabilityForNoMassShift = currentProbability.multiply( probabilityOfNoMassShiftForElement );

			//System.out.println( "\t\t\tcumulativeProbabilityForAnyMassShift: " + cumulativeProbabilityForAnyMassShift );
			//System.out.println( "\t\t\tcumulativeProbabilityForNoMassShift: " + cumulativeProbabilityForNoMassShift );

			
			
			if( numberOfAtomsWithMassDiff == 0 ) {
				
				// no need to go on: we're below the probability cutoff
				if( cumulativeProbabilityForNoMassShift.compareTo( _PROBABILITY_CUTOFF ) < 0 ) {
					continue;
				}
				
				// no need to continue this further if current list is full and minimum probability is greater than this
				if( massShiftProbabilities.size() >= limit && getCurrentLowestProbability( massShiftProbabilities ).compareTo( cumulativeProbabilityForNoMassShift ) > 0 )
					continue;
				
				// if we're at the end of the element list, we can add this to our master list
				if( index >= atomList.size() - 1 ) {
					addToMassShiftProbabilities( cumulativeProbabilityForNoMassShift, currentMassShift, massShiftProbabilities, limit );
					continue;
				}
				
				// process the next element
				processElementInList( atomList, atomCount, index + 1, cumulativeProbabilityForNoMassShift, currentMassShift, massShiftProbabilities, limit );
				continue;				
			}
			
			
			// no need to go on: we're below the probability cutoff
			if( cumulativeProbabilityForAnyMassShift.compareTo( _PROBABILITY_CUTOFF ) < 0 ) {
				continue;
			}
			
			// no need to continue this further if current list is full and minimum probability is greater than this
			if( massShiftProbabilities.size() >= limit && getCurrentLowestProbability( massShiftProbabilities ).compareTo( cumulativeProbabilityForAnyMassShift ) > 0 )
				continue;
			
			// process all combinations of mass shifts for this element and this number of mass shifts
			
			List<BigDecimal> massShiftsForElement = new ArrayList<>();
			massShiftsForElement.addAll( _ATOMIC_MASS_SHIFT_PROBABILITIES.get( atom ).keySet() );
			
			processElementMassShiftsForCount( numberOfAtomsWithMassDiff, 0, massShiftsForElement, atomList, atomCount, index, currentProbability, currentMassShift, massShiftProbabilities, limit);			
			

		}// end iterating over number of mass modifications for this elements' atoms
		
		System.out.println( "\t\tEnding processElementInList().\n\n" );

		
	}

	
	private void processElementMassShiftsForCount (
			int numberOfAtomsWithMassShift,
			int elementIsotopeIndex,
			List<BigDecimal> massShiftsForElement,
			List<Atom> atomList,
			Map< Atom, Integer > atomCount,
			Integer index,
			BigDecimal currentProbability,
			BigDecimal currentMassShift,
			Collection<IsotopeMassShiftProbability> massShiftProbabilities,
			int limit
		 ) {

		Atom thisElement = atomList.get( index );
		BigDecimal thisMassShift = massShiftsForElement.get( elementIsotopeIndex );
		BigDecimal thisIsotopeProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( thisElement ).get( thisMassShift );
		
		for( int count = 0; count <= numberOfAtomsWithMassShift; count++ ) {
			
			double p = getProbability( count, atomCount.get( thisElement ), thisIsotopeProbability.doubleValue() );
			BigDecimal probabilityOfCountShifts = getBigDecimal( p );

			BigDecimal cumulativeProbability = currentProbability.multiply( probabilityOfCountShifts );
			
			// no need to go on: we're below the probability cutoff
			if( cumulativeProbability.compareTo( _PROBABILITY_CUTOFF ) < 0 ) {
				continue;
			}
			
			// no need to continue this further if current list is full and minimum probability is greater than this
			if( massShiftProbabilities.size() >= limit && getCurrentLowestProbability( massShiftProbabilities ).compareTo( cumulativeProbability ) > 0 )
				continue;
			
			BigDecimal cumulativeMassShift = currentMassShift;
			System.out.println( cumulativeMassShift );

			if( count != 0 ) {
				System.out.println( cumulativeMassShift );
				
				cumulativeMassShift = cumulativeMassShift.multiply( getBigDecimal( (double)count ) );			
				System.out.println( cumulativeMassShift );

				
				cumulativeMassShift = cumulativeMassShift.add( thisMassShift );
				System.out.println( cumulativeMassShift );
			}


			
			// if we're on the last mass shift for this atom, add it to the list
			if( elementIsotopeIndex >= massShiftsForElement.size() - 1 ) {

				// we're also on the last element
				if( index >= atomList.size() - 1 ) {
					addToMassShiftProbabilities( cumulativeProbability, cumulativeMassShift, massShiftProbabilities, limit );
					continue;
				} else {
					// move on to the next element
					processElementInList( atomList, atomCount, index + 1, cumulativeProbability, cumulativeMassShift, massShiftProbabilities, limit );
					continue;
				}

			}
			
			processElementMassShiftsForCount( numberOfAtomsWithMassShift, elementIsotopeIndex + 1, massShiftsForElement, atomList, atomCount, index, cumulativeProbability, cumulativeMassShift, massShiftProbabilities, limit);			
		}		
	}
	
	
	
	
	BigDecimal getCurrentLowestProbability( Collection<IsotopeMassShiftProbability> massShiftProbabilities ) {
		BigDecimal min = null;
		for( IsotopeMassShiftProbability sp : massShiftProbabilities ) {
			if( min == null || sp.getProbability().compareTo( min ) < 0 )
				min = sp.getProbability();
		}
		return min;
	}
	
	/**
	 * Add the probability and mass shift to the collection of mass shift probabilities we're making
	 * 
	 * @param probability
	 * @param currentMassShift
	 * @param massShiftProbabilities
	 * @param limit
	 * @param currentLowestProbability
	 * @return
	 */
	private void addToMassShiftProbabilities( BigDecimal probability,
													BigDecimal currentMassShift,
													Collection<IsotopeMassShiftProbability> massShiftProbabilities,
													int limit ) {
		
		IsotopeMassShiftProbability imsp = new IsotopeMassShiftProbability( currentMassShift, probability );

		
			///System.out.println( "Calling addToMassShiftProbabilities() with: " );
			///System.out.println( "\tprobability: " + probability );
			///System.out.println( "\tcurrentMassShift: " + currentMassShift );
		
		
			BigDecimal currentLowestProbability = getCurrentLowestProbability( massShiftProbabilities );
		
			/*
			 * If the collection isn't full, add this to it and note the new lowest probability in
			 * the collection
			 */
			if( massShiftProbabilities.size() < limit ) {
				massShiftProbabilities.add( imsp );
				
				return;
			}
			
			
			// we know massShiftProbabilities is full at this point
			
			
			// if the lowest probability is greater than this one, then there is nothing to do.
			if( currentLowestProbability.compareTo( probability ) > 0 )
				return;
		

			// if the lowest probability and this probability are the same just add this one
			if( currentLowestProbability.compareTo( probability ) == 0 ) {
				massShiftProbabilities.add( imsp );
				return;
			}
			
			
			// add this to the master list
			massShiftProbabilities.add( imsp );
			
			// make a list of items to remove (items with the lowest currentLowestProbability)
			Collection<IsotopeMassShiftProbability> itemsToRemove = new HashSet<>();
			for( IsotopeMassShiftProbability sp : massShiftProbabilities ) {
				if( sp.getProbability().equals( currentLowestProbability ) )
					itemsToRemove.add( sp );
			}
			
			// if removing these items would shrink the list below the limit, don't remove them
			if( massShiftProbabilities.size() - itemsToRemove.size() < limit )
				return;
			
			// remove items
			for( IsotopeMassShiftProbability sp : itemsToRemove ) {
				massShiftProbabilities.remove( sp );
			}
	}
	
	
	
	/**
	 * Get the sum of all probabilities for isotope mass shifts for this element
	 * @param elementMap
	 * @return
	 */
	BigDecimal getTotalProbabilityForAllIsotopesForElement( Map<BigDecimal, BigDecimal> elementMap ) {
		
		//System.out.println( "\n\n\t\t\tStarting getTotalProbabilityForAllIsotopesForElement()." );

		
		BigDecimal sum = getBigDecimal( 0.0 );
		
		for( BigDecimal massShift : elementMap.keySet() ) {
			//System.out.println( "\t\t\tmassshift: " + massShift );
			//System.out.println( "\t\t\tprobability: " + elementMap.get( massShift )  );

			sum = sum.add( elementMap.get( massShift ) );
			//System.out.println( "\t\t\tsum: " + sum );
		}
		
		//System.out.println( "\n\n\t\t\tEnding getTotalProbabilityForAllIsotopesForElement().\n\n" );

		
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

		// remove a H2O for each bond between amino acids.
		atomCountMap.put( AtomUtils.ATOM_OXYGEN, atomCountMap.get( AtomUtils.ATOM_OXYGEN ) - ( s.length() - 1 ) );
		atomCountMap.put( AtomUtils.ATOM_HYDROGEN, atomCountMap.get( AtomUtils.ATOM_HYDROGEN ) - ( ( s.length() - 1 ) * 2 ) );

		
		return atomCountMap;
	}
	
	/**
	 * For comparison purposes, standardize how we get big decimals
	 * 
	 * @param d
	 * @return
	 */
	private static BigDecimal getBigDecimal( double d ) {
		return new BigDecimal( d, MathContext.DECIMAL64 );
	}
	
	
	private double getProbability( int successes, int trials, double probabilityOfEvent ) {
		    return Math.exp(ArithmeticUtils.binomialCoefficientLog(trials, successes) + 
		      (successes * Math.log(probabilityOfEvent)) + ((trials - successes) * Math.log(1 - probabilityOfEvent)));
		  }
	
}

