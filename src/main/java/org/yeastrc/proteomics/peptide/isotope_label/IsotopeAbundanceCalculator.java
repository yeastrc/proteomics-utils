package org.yeastrc.proteomics.peptide.isotope_label;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import org.yeastrc.proteomics.peptide.peptide.Peptide;
import org.apache.commons.math3.util.ArithmeticUtils;

/**
 * A tool to get the predicted isotope distribution for one or more peptides. Should probably
 * be written using recursion instead of a chain of calls to separate methods for each
 * element... But writing it this way seemed less error prone.
 * 
 * @author mriffle
 *
 */
public class IsotopeAbundanceCalculator {

	private static final IsotopeAbundanceCalculator _INSTANCE = new IsotopeAbundanceCalculator();
	private IsotopeAbundanceCalculator() { }
	public static IsotopeAbundanceCalculator getInstance() { return _INSTANCE; }
		
	/**
	 * The probability below which no isotope mass shifts will be considered
	 */
	private static final double _DEFAULT_PROBABILITY_CUTOFF = 1E-5;
	
	
	/**
	 * A map of atom => mass shift (vs/ monoisotopic) => probability of observing this mass shift
	 */
	private static final Map< Atom, List<Double>> _ATOMIC_MASS_SHIFT_PROBABILITIES;
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
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).add(  0.0107 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_CARBON ).add( getBigDecimal( 1.003355 ) );
		
		/* 2H */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).add( 0.000115 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_HYDROGEN ).add( getBigDecimal( 1.006277 ) );
		
		/* 15N */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_NITROGEN ).add( 0.00368 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_NITROGEN ).add( getBigDecimal( 0.997035 ) );

		/* 17O */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).add( 0.00038 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 1.004217 ) );

		/* 18O */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).add( 0.00205 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).add( getBigDecimal( 2.004245 ) );

		/* 33S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( 0.0076 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 0.999387 ) );

		/* 34S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( 0.0429 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 1.995796) );

		/* 36S */
		_ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_SULFUR ).add( 0.0002 );
		_ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_SULFUR ).add( getBigDecimal( 3.99501 ) );
				
	}
	
	/**
	 * Get a list of neutral charge (not divided by charge) isotopic mass shifts (and associated probabilities) for these peptides, which are mass differences compared to the
	 * monoisotopic mass of this peptide. If the peptide contains a stable isotope label, no isotopics for the labeled element
	 * will be considered--the stable isotopic label is already used when calculating the monoisotopic mass for the peptide, and it
	 * is assumed that all atoms of the labeled element are labeled.
	 * 
	 * @param peptides A collection of peptides we are processing which should be part of the same ion (e.g. a pair of cross-linked peptides)
	 * @param charge The charge of the identified ion (so that we may add the appropriate number of extra Hydrogens into the calculation) Note
	 * 				 that the returned values are NOT divided by this value.
	 * @param probabilityCutoff The proability, below which, isotopes will not be considered. The higher this value, the faster this runs.
	 * @return
	 * @throws Exception
	 */
	public Map<BigDecimal, Double> getIsotopMassShiftProbabilities( Collection< Peptide > peptides, Integer charge, Double probabilityCutoff ) throws Exception {
		
		if( charge != null && charge < 0 ) {
			throw new Exception( "Charge must be >= 0." );
		}
		
		if( probabilityCutoff == null )
			probabilityCutoff = _DEFAULT_PROBABILITY_CUTOFF;
				
		Map<BigDecimal, Double> massShiftProbabilities = new HashMap<>();
		
		// get the count for each atom for the entire peptide
		Map< Atom, Integer > atomCount = getAtomCountForPeptides( peptides );

		if( charge != null ) {
			if( !atomCount.containsKey( AtomUtils.ATOM_HYDROGEN ) )
				atomCount.put( AtomUtils.ATOM_HYDROGEN, 0 );
			
			atomCount.put( AtomUtils.ATOM_HYDROGEN, atomCount.get( AtomUtils.ATOM_HYDROGEN ) + charge );
		}
		
		// start with carbon
		processCarbon( atomCount, massShiftProbabilities, probabilityCutoff );		
		
		return massShiftProbabilities;
	}
	
	public Map<BigDecimal, Double> getIsotopMassShiftProbabilities( Collection< Peptide > peptides, Integer charge ) throws Exception {
		return getIsotopMassShiftProbabilities( peptides, charge, null );
	}


	public Map<BigDecimal, Double> getIsotopMassShiftProbabilities( Peptide peptide, Integer charge, Double probabilityCutoff ) throws Exception {
		Collection< Peptide > peptides = new HashSet<>();
		peptides.add( peptide );
		
		return getIsotopMassShiftProbabilities( peptides, charge, probabilityCutoff );
	}
	
	public Map<BigDecimal, Double> getIsotopMassShiftProbabilities( Peptide peptide, Integer charge ) throws Exception {
		return getIsotopMassShiftProbabilities( peptide, charge, null );
	}
	
	
	private void processCarbon( Map< Atom, Integer > atomCount, Map<BigDecimal, Double> massShiftProbabilities, double probabilityCutoff) {
		
		if( atomCount.get( AtomUtils.ATOM_HYDROGEN ) == null || atomCount.get( AtomUtils.ATOM_HYDROGEN ) == 0 ) {
			processHydrogen( 1.0, getBigDecimal( (double)0.0 ), atomCount, massShiftProbabilities, probabilityCutoff );
			return;
		}
		
		BigDecimal carbonMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_CARBON ).get( 0 );
		double carbonMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_CARBON ).get( 0 );		
			
		for( int carbonAtomIsotopeShifts = 0; carbonAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_CARBON ); carbonAtomIsotopeShifts++ ) {
			
			double carbonProbability = getProbability(	carbonAtomIsotopeShifts, atomCount.get( AtomUtils.ATOM_CARBON ), carbonMassShiftProbability );
			
			if( carbonProbability < probabilityCutoff ) continue;
			
			BigDecimal totalCarbonMassShift = carbonMassShift.multiply( getBigDecimal( (double)carbonAtomIsotopeShifts) );				
				
			// now process Hydrogen
			processHydrogen( carbonProbability, totalCarbonMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
				
		}
	}
	
	private void processHydrogen( double currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount,  Map<BigDecimal, Double> massShiftProbabilities, double probabilityCutoff ) {
		
		if( atomCount.get( AtomUtils.ATOM_HYDROGEN ) == null || atomCount.get( AtomUtils.ATOM_HYDROGEN ) == 0 ) {
			processNitrogen( currentProbability, currentMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
			return;
		}
		
		BigDecimal hydrogenMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_HYDROGEN ).get( 0 );
		double hydrogenMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_HYDROGEN ).get( 0 );
			
		for( int hydrogenAtomIsotopeShifts = 0; hydrogenAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_HYDROGEN ); hydrogenAtomIsotopeShifts++ ) {
			
			double hydrogenProbability = getProbability( hydrogenAtomIsotopeShifts, atomCount.get( AtomUtils.ATOM_HYDROGEN ), hydrogenMassShiftProbability );
				
			BigDecimal totalHydrogenMassShift = hydrogenMassShift.multiply( getBigDecimal( (double)hydrogenAtomIsotopeShifts) );
			BigDecimal totalMassShift = totalHydrogenMassShift.add( currentMassShift );
				
			hydrogenProbability = hydrogenProbability * currentProbability;
			
			if( hydrogenProbability < probabilityCutoff ) continue;
			
			// now process Nitrogen
			processNitrogen( hydrogenProbability, totalMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
		}
	}
	
	
	private void processNitrogen( double currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Map<BigDecimal, Double> massShiftProbabilities, double probabilityCutoff ) {
		
		if( atomCount.get( AtomUtils.ATOM_NITROGEN ) == null || atomCount.get( AtomUtils.ATOM_NITROGEN ) == 0 ) {
			processOxygen( currentProbability, currentMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
			return;
		}
		
		BigDecimal nitrogenMassShift = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_NITROGEN ).get( 0 );
		double nitrogenMassShiftProbability = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_NITROGEN ).get( 0 );
			
		for( int nitrogenAtomIsotopeShifts = 0; nitrogenAtomIsotopeShifts <= atomCount.get( AtomUtils.ATOM_NITROGEN ); nitrogenAtomIsotopeShifts++ ) {
			
			double nitrogenProbability = getProbability(	nitrogenAtomIsotopeShifts, atomCount.get( AtomUtils.ATOM_NITROGEN ), nitrogenMassShiftProbability );
				
			BigDecimal totalNitrogenMassShift = nitrogenMassShift.multiply( getBigDecimal( (double)nitrogenAtomIsotopeShifts) );
			BigDecimal totalMassShift = totalNitrogenMassShift.add( currentMassShift );
				
			nitrogenProbability = nitrogenProbability * currentProbability;
			
			if( nitrogenProbability < probabilityCutoff ) continue;
			
			// now process Oxygen
			processOxygen( nitrogenProbability, totalMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
				
		}
	}
	
	
	private void processOxygen( double currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Map<BigDecimal, Double> massShiftProbabilities, double probabilityCutoff ) {
		
		if( atomCount.get( AtomUtils.ATOM_OXYGEN ) == null || atomCount.get( AtomUtils.ATOM_OXYGEN ) == 0 ) {
			processSulfur( currentProbability, currentMassShift, atomCount, massShiftProbabilities, probabilityCutoff );
			return;
		}
		
		BigDecimal oxygenMassShift1 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).get( 0 );
		double oxygenMassShiftProbability1 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).get( 0 );
			
		BigDecimal oxygenMassShift2 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( AtomUtils.ATOM_OXYGEN ).get( 1 );
		double oxygenMassShiftProbability2 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( AtomUtils.ATOM_OXYGEN ).get( 1 );
			
			
		for( int oxygenAtomIsotopeShifts1 = 0; oxygenAtomIsotopeShifts1 <= atomCount.get( AtomUtils.ATOM_OXYGEN ); oxygenAtomIsotopeShifts1++ ) {
			
			double oxygenProbability1 = getProbability(	oxygenAtomIsotopeShifts1, atomCount.get( AtomUtils.ATOM_OXYGEN ), oxygenMassShiftProbability1 );
				
			BigDecimal totalOxygenMassShift1 = oxygenMassShift1.multiply( getBigDecimal( (double)oxygenAtomIsotopeShifts1) );
			BigDecimal totalMassShift1 = totalOxygenMassShift1.add( currentMassShift );
								
			double totalProbability1 = oxygenProbability1 * currentProbability;

			if( totalProbability1 < probabilityCutoff ) continue;
			
			for( int oxygenAtomIsotopeShifts2 = 0; oxygenAtomIsotopeShifts2 <= atomCount.get( AtomUtils.ATOM_OXYGEN ) - oxygenAtomIsotopeShifts1; oxygenAtomIsotopeShifts2++ ) {		
					
				double oxygenProbability2 = getProbability(	oxygenAtomIsotopeShifts2, atomCount.get( AtomUtils.ATOM_OXYGEN ), oxygenMassShiftProbability2 );

				BigDecimal totalOxygenMassShift2 = oxygenMassShift2.multiply( getBigDecimal( (double)oxygenAtomIsotopeShifts2) );

				BigDecimal totalMassShift2 = totalOxygenMassShift2.add( totalMassShift1 );
				double totalProbability2 = totalProbability1 * oxygenProbability2;
				
				if( totalProbability2 < probabilityCutoff ) continue;
				
				// now process Sulfur
				processSulfur( totalProbability2, totalMassShift2, atomCount, massShiftProbabilities, probabilityCutoff );
					
			}
		}
	}
	
	
	private void processSulfur( double currentProbability, BigDecimal currentMassShift, Map< Atom, Integer > atomCount, Map<BigDecimal, Double> massShiftProbabilities, double probabilityCutoff ) {
		
		// the element we're processing
		Atom thisElement = AtomUtils.ATOM_SULFUR;
		
		
		if( atomCount.get( thisElement ) == null || atomCount.get( thisElement ) == 0 ) {
			// add to massShiftProbabilities
			addToMassShiftProbabilities( currentProbability, currentMassShift, massShiftProbabilities, 10 );
			return;
		}
			
		
		BigDecimal isotopicMassShift1 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( thisElement ).get( 0 );
		double isotopicMassShiftProbability1 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( thisElement ).get( 0 );
			
		BigDecimal isotopicMassShift2 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( thisElement ).get( 1 );
		double isotopicMassShiftProbability2 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( thisElement ).get( 1 );
		
		BigDecimal isotopicMassShift3 = _ATOMIC_MASS_SHIFT_MASS_CHANGES.get( thisElement ).get( 1 );
		double isotopicMassShiftProbability3 = _ATOMIC_MASS_SHIFT_PROBABILITIES.get( thisElement ).get( 1 );
		
			
		for( int sulfurAtomIsotopeShifts1 = 0; sulfurAtomIsotopeShifts1 <= atomCount.get( thisElement ); sulfurAtomIsotopeShifts1++ ) {
			
			double sulfurProbability1 = getProbability(	sulfurAtomIsotopeShifts1, atomCount.get( thisElement ), isotopicMassShiftProbability1 );
				
			BigDecimal totalSulfurMassShift1 = isotopicMassShift1.multiply( getBigDecimal( (double)sulfurAtomIsotopeShifts1) );
			BigDecimal totalMassShift1 = totalSulfurMassShift1.add( currentMassShift );
								
			double totalProbability1 = sulfurProbability1 * currentProbability;
				
			if( totalProbability1 < probabilityCutoff ) continue;
			
			for( int sulfurAtomIsotopeShifts2 = 0; sulfurAtomIsotopeShifts2 <= atomCount.get( thisElement ) - sulfurAtomIsotopeShifts1; sulfurAtomIsotopeShifts2++ ) {		
					
				double sulfurProbability2 = getProbability(	sulfurAtomIsotopeShifts2, atomCount.get( thisElement ), isotopicMassShiftProbability2 );

				BigDecimal totalSulfurMassShift2 = isotopicMassShift2.multiply( getBigDecimal( (double)sulfurAtomIsotopeShifts2) );

				BigDecimal totalMassShift2 = totalSulfurMassShift2.add( totalMassShift1 );
				double totalProbability2 = totalProbability1 * sulfurProbability2;
				
				if( totalProbability2 < probabilityCutoff ) continue;
				
				for( int sulfurAtomIsotopeShifts3 = 0; sulfurAtomIsotopeShifts3 <= atomCount.get( thisElement ) - ( sulfurAtomIsotopeShifts1 + sulfurAtomIsotopeShifts2 ); sulfurAtomIsotopeShifts3++ ) {		
					
					double sulfurProbability3 = getProbability(	sulfurAtomIsotopeShifts3, atomCount.get( thisElement ), isotopicMassShiftProbability3 );

					BigDecimal totalSulfurMassShift3 = isotopicMassShift3.multiply( getBigDecimal( (double)sulfurAtomIsotopeShifts3 ) );

					BigDecimal totalMassShift3 = totalSulfurMassShift3.add( totalMassShift2 );
					double totalProbability3 = totalProbability2 * sulfurProbability3;

					if( totalProbability3 < probabilityCutoff ) continue;
					
					// add to massShiftProbabilities
					addToMassShiftProbabilities( totalProbability3, totalMassShift3, massShiftProbabilities, 10 );

				}
			}
		}
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
	private void addToMassShiftProbabilities( 		double probability,
													BigDecimal currentMassShift,
													Map<BigDecimal, Double> massShiftProbabilities,
													int limit ) {
					
			
			if( !massShiftProbabilities.containsKey( currentMassShift ) ) {
				massShiftProbabilities.put( currentMassShift, probability );
				return;
			}
			
			
			massShiftProbabilities.put(currentMassShift , massShiftProbabilities.get( currentMassShift ) + probability );

	}
	
	
	
	/**
	 * Get a map of the counts for each distinct atom in the supplied peptide
	 * 
	 * @param peptide
	 * @return
	 */
	private Map< Atom, Integer > getAtomCountForPeptides( Collection<Peptide> peptides ) throws Exception {
		
		Map< Atom, Integer > atomCountMap = new HashMap<>();
		
		for( Peptide peptide : peptides ) {
			String s = peptide.getSequence();
			
			for (int i = 0; i < s.length(); i++){
				
				AminoAcid residue = AminoAcidUtils.getAminoAcidBySymbol( s.charAt(i) );
				Map<Atom, Integer> residueAtomCount = residue.getParsedAtomCount();
				
				for( Atom atom : residueAtomCount.keySet() ) {
					
					// do not add atoms for labeled elements as these cannot be mass shifted
					if( peptide.getLabel() != null )
						if( peptide.getLabel().getLabeledAtom().equals( atom ) )
							continue;
					
					if( !atomCountMap.containsKey( atom ) )
						atomCountMap.put( atom, 0 );
					
					atomCountMap.put( atom, atomCountMap.get( atom ) + residueAtomCount.get( atom ) );
					
				}
			}
	
			// add H and OH onto atom counts N and C terms
			atomCountMap.put( AtomUtils.ATOM_OXYGEN, atomCountMap.get( AtomUtils.ATOM_OXYGEN ) + 1 );
			atomCountMap.put( AtomUtils.ATOM_HYDROGEN, atomCountMap.get( AtomUtils.ATOM_HYDROGEN ) + 2 );
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
		return new BigDecimal( d, MathContext.DECIMAL64 ).setScale( 6, RoundingMode.HALF_UP );
	}
	
	
	private double getProbability( int successes, int trials, double probabilityOfEvent ) {
		    return Math.exp(ArithmeticUtils.binomialCoefficientLog(trials, successes) + 
		      (successes * Math.log(probabilityOfEvent)) + ((trials - successes) * Math.log(1 - probabilityOfEvent)));
		  }
	
}

