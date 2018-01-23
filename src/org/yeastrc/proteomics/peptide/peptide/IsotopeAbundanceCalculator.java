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
	private static final Map< Atom, List<BigDecimal>> _ATOMIC_MASS_SHIFT_PROBABILITIES;
	private static final Map< Atom, List<BigDecimal>> _ATOMIC_MASS_SHIFT_MASS_CHANGES;
	
	static {
	
		// from https://chemistry.sciences.ncsu.edu/msf/pdf/IsotopicMass_NaturalAbundance.pdf
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES = new HashMap<>();
		_ATOMIC_MASS_SHIFT_MASS_CHANGES = new HashMap<>();
		
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_CARBON, new ArrayList<>() );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.put( AtomUtils.ATOM_CARBON, new ArrayList<>() );
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_HYDROGEN, new ArrayList<>() );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.put( AtomUtils.ATOM_HYDROGEN, new ArrayList<>() );

		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_NITROGEN, new ArrayList<>() );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.put( AtomUtils.ATOM_NITROGEN, new ArrayList<>() );
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_OXYGEN, new ArrayList<>() );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.put( AtomUtils.ATOM_OXYGEN, new ArrayList<>() );
		
		_ATOMIC_MASS_SHIFT_PROBABILITIES.put( AtomUtils.ATOM_SULFUR, new ArrayList<>() );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.put( AtomUtils.ATOM_SULFUR, new ArrayList<>() );
		
		
		/* 13C */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).add( getBigDecimal( 0.0107 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_CARBON ).add( getBigDecimal( 1.003355 ) );
		
		/* 2H */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).add( getBigDecimal( 0.000115 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_HYDROGEN ).add( getBigDecimal( 1.006277 ) );
		
		/* 15N */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_NITROGEN ).add( getBigDecimal( 0.00368 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_NITROGEN ).add( getBigDecimal( 0.997035 ) );

		/* 17O */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 0.00038 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 1.004217 ) );

		/* 18O */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 0.00205 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 2.004245 ) );

		/* 33S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 0.0076 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 0.999387 ) );

		/* 34S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 0.0429 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 1.995796) );

		/* 36S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 0.0002 ) );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 3.99501 ) );
				
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
		
		
		// start with carbon
		processCarbon( atomCount, massShiftProbabilities );		

		
		System.out.println( "\tEnding getIsotopMassShiftProbabilities().\n\n" );

		
		return massShiftProbabilities;
	}

	
	
	
	private void processCarbon( Map< Atom, Integer > atomCount, Collection<IsotopeMassShiftProbability> massShiftProbabilities ) {
		
		BigDecimal carbonMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_CARBON ).get( 0 );
		BigDecimal carbonMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).get( 0 );		
			
		for( int carbonAtomIsotopeShifts = 0; carbonAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_CARBON ); carbonAtomIsotopeShifts++ ) {
			
			BigDecimal carbonProbability = getBigDecimal( getProbability(	carbonAtomIsotopeShifts,
																				atomCount.get( AtomUtils.ATOM_CARBON ),
																				carbonMassShiftProbability.doubleValue()
																			));
				
			BigDecimal totalCarbonMassShift = carbonMassShift.multiply( getBigDecimal( (double)carbonAtomIsotopeShifts) );
				
				
			if( !passesCutoffTest( carbonProbability ) ) continue;		// no need to keep processing if we don't pass the cutoff
				
			// now process Hydrogen
			processHydrogen( carbonProbability, totalCarbonMassShift, atomCount, massShiftProbabilities );
				
		}
	}
	
	private void processHydrogen( BigDecimal currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Collection<IsotopeMassShiftProbability> massShiftProbabilities ) {
		
		BigDecimal hydrogenMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_HYDROGEN ).get( 0 );
		BigDecimal hydrogenMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).get( 0 );
			
		for( int hydrogenAtomIsotopeShifts = 0; hydrogenAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_HYDROGEN ); hydrogenAtomIsotopeShifts++ ) {
			
			BigDecimal hydrogenProbability = getBigDecimal( getProbability(	hydrogenAtomIsotopeShifts,
																				atomCount.get( AtomUtils.ATOM_HYDROGEN ),
																				hydrogenMassShiftProbability.doubleValue()
																			));
				
			BigDecimal totalHydrogenMassShift = hydrogenMassShift.multiply( getBigDecimal( (double)hydrogenAtomIsotopeShifts) );
			BigDecimal totalMassShift = totalHydrogenMassShift.add( currentMassShift );
				
			hydrogenProbability = hydrogenProbability.multiply( currentProbability );
				
			if( !passesCutoffTest( hydrogenProbability ) ) continue;		// no need to keep processing if we don't pass the cutoff
				
			// now process Nitrogen
			processNitrogen( hydrogenProbability, totalMassShift, atomCount, massShiftProbabilities );
		}
	}
	
	
	private void processNitrogen( BigDecimal currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Collection<IsotopeMassShiftProbability> massShiftProbabilities ) {
		
		BigDecimal nitrogenMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_NITROGEN ).get( 0 );
		BigDecimal nitrogenMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_NITROGEN ).get( 0 );
			
		for( int nitrogenAtomIsotopeShifts = 0; nitrogenAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_NITROGEN ); nitrogenAtomIsotopeShifts++ ) {
			
			BigDecimal nitrogenProbability = getBigDecimal( getProbability(	nitrogenAtomIsotopeShifts,
																				atomCount.get( AtomUtils.ATOM_NITROGEN ),
																				nitrogenMassShiftProbability.doubleValue()
																			));
				
			BigDecimal totalNitrogenMassShift = nitrogenMassShift.multiply( getBigDecimal( (double)nitrogenAtomIsotopeShifts) );
			BigDecimal totalMassShift = totalNitrogenMassShift.add( currentMassShift );
				
			nitrogenProbability = nitrogenProbability.multiply( currentProbability );
				
			if( !passesCutoffTest( nitrogenProbability ) ) continue;		// no need to keep processing if we don't pass the cutoff
				
			// now process Oxygen
			processOxygen( nitrogenProbability, totalMassShift, atomCount, massShiftProbabilities );
				
		}
	}
	
	
	private void processOxygen( BigDecimal currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Collection<IsotopeMassShiftProbability> massShiftProbabilities ) {
		
			BigDecimal oxygenMassShift1 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).get( 0 );
			BigDecimal oxygenMassShiftProbability1 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).get( 0 );
			
			BigDecimal oxygenMassShift2 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).get( 1 );
			BigDecimal oxygenMassShiftProbability2 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).get( 1 );
			
			
			for( int oxygenAtomIsotopeShifts1 = 0; oxygenAtomIsotopeShifts1 <= atomCount.get( AtomUtils.ATOM_OXYGEN ); oxygenAtomIsotopeShifts1++ ) {
			
				BigDecimal oxygenProbability1 = getBigDecimal( getProbability(	oxygenAtomIsotopeShifts1,
																				atomCount.get( AtomUtils.ATOM_OXYGEN ),
																				oxygenMassShiftProbability1.doubleValue()
																			));
				
				BigDecimal totalOxygenMassShift1 = oxygenMassShift1.multiply( getBigDecimal( (double)oxygenAtomIsotopeShifts1) );
				BigDecimal totalMassShift = totalOxygenMassShift1.add( currentMassShift );
								
				BigDecimal totalProbability = oxygenProbability1.multiply( currentProbability );
				
				if( !passesCutoffTest( totalProbability ) ) continue;		// no need to keep processing if we don't pass the cutoff

				
				for( int oxygenAtomIsotopeShifts2 = 0; oxygenAtomIsotopeShifts2 <= atomCount.get( AtomUtils.ATOM_OXYGEN ) - oxygenAtomIsotopeShifts1; oxygenAtomIsotopeShifts2++ ) {

					
					
					BigDecimal oxygenProbability2 = getBigDecimal( getProbability(	oxygenAtomIsotopeShifts2,
							atomCount.get( AtomUtils.ATOM_OXYGEN ),
							oxygenMassShiftProbability2.doubleValue()
						));

					BigDecimal totalOxygenMassShift2 = oxygenMassShift2.multiply( getBigDecimal( (double)oxygenAtomIsotopeShifts2) );
					totalMassShift = totalOxygenMassShift2.add( totalMassShift );

					totalProbability = totalProbability.multiply( oxygenProbability2 );

					if( !passesCutoffTest( totalProbability ) ) continue;		// no need to keep processing if we don't pass the cutoff
					
					
					// now process Oxygen
					//processSulfur( oxygenProbability1, totalMassShift, atomCount, massShiftProbabilities );
					
					
					// add to massShiftProbabilities
					//addToMassShiftProbabilities( totalProbability, totalMassShift, massShiftProbabilities, 30 );

				}
				
				

			}
		}
	
	
	
	
	
	
	private boolean passesCutoffTest( BigDecimal probability ) {
		if( probability.compareTo( _PROBABILITY_CUTOFF  ) < 0 )
			return false;
		
		return true;
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

