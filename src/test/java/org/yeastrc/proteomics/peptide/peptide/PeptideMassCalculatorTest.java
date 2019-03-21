package org.yeastrc.proteomics.peptide.peptide;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.isotope_label.IsotopeLabel;
import org.yeastrc.proteomics.peptide.isotope_label.LabelFactory;

public class PeptideMassCalculatorTest {

	// peptide string we're testing--one of each amino acid
	static final String sequence = "GPAVLIMCFYWHKRQNEDST";
	
	// from http://proteomicsresource.washington.edu/cgi-bin/fragment.cgi
	static final double preCalcMonoMass = 2394.124907;
	static final double preCalcAvgMass = 2395.713660;
	
	// http://protcalc.sourceforge.net/
	static final double preCalcMonoMass15N = 2423.0354;
	static final double preCalcAvgMass15N = 2424.5161;
	
	// the label we're using for testing
	IsotopeLabel label = LabelFactory.getInstance().getLabel( "15N" );
	
	// the mod we're using for testing
	static final double phosphoMass = 79.966331;
	
	static final Map<Integer, Double> testMods = new HashMap<>();

	static {
		testMods.put( 10, phosphoMass );
		testMods.put( 18, phosphoMass );
		testMods.put( 19, phosphoMass );
	}
	
	@Test
	public void getMassForPeptideSequenceOnly() {

		// test monoisotopic
		{
			Peptide peptide = new Peptide( sequence );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass, calcMass, 0.0001 );
		}
		
		// test average
		{
			Peptide peptide = new Peptide( sequence );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass, calcMass, 0.0001 );	// not getting exact agreement on average
		}
		
	}
	
	@Test
	public void getMassForPeptideSequenceAndMods() {
		
		// test monoisotopic
		{
			Peptide peptide = new Peptide( sequence, testMods );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass + ( 3.0 * phosphoMass ), calcMass, 0.0001 );
		}
		
		// test average
		{
			Peptide peptide = new Peptide( sequence, testMods );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass + ( 3.0 * phosphoMass ), calcMass, 0.0001 ); // not getting exact agreement on average
		}
		
	}
	
	
	@Test
	public void getMassForPeptideSequenceAndLabel() {

		// test monoisotopic
		{
			Peptide peptide = new Peptide( sequence, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass15N, calcMass, 0.01 );
		}
		
		// test average
		{
			Peptide peptide = new Peptide( sequence, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass15N, calcMass, 0.1 );	// not getting exact agreement on average
		}
	}
	
	
	@Test
	public void getMassForPeptideSequenceAndModsAndLabel() {
		
		// test monoisotopic
		{
			Peptide peptide = new Peptide( sequence, testMods, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass15N + ( 3.0 * phosphoMass ), calcMass, 0.01 );
		}
		
		// test average
		{
			Peptide peptide = new Peptide( sequence, testMods, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass15N + ( 3.0 * phosphoMass ), calcMass, 0.1 );	// not getting exact agreement on average
		}
		
	}
	

}
