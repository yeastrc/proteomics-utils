package org.yeastrc.proteomics.peptide.peptide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.proteomics.mass.MassUtils.MassType;
import org.yeastrc.proteomics.peptide.isotope_label.IsotopeLabel;
import org.yeastrc.proteomics.peptide.isotope_label.LabelFactory;

public class PeptideMassCalculatorTest {

	@Test
	public void testGetMassForPeptide() {
		
		String sequence = "GPAVLIMCFYWHKRQNEDST";

		// from https://web.expasy.org/peptide_mass/
		double preCalcMonoMass = 2394.12;
		double preCalcAvgMass = 2395.74;
		
		// 15N labeled masses from http://protcalc.sourceforge.net/
		double preCalcMonoMass15N = 2423.04;
		double preCalcAvgMass15N = 2424.52;
		
		double phosphoMass = 79.966331;
		
		Map<Integer, Double> mods = new HashMap<>();
		mods.put( 10, phosphoMass );
		mods.put( 18, phosphoMass );
		mods.put( 19, phosphoMass );

		IsotopeLabel label = LabelFactory.getInstance().getLabel( "15N" );
		
		
		// test sequence only
		{
			Peptide peptide = new Peptide( sequence );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass, calcMass, 0.01 );	// tested against https://web.expasy.org/peptide_mass/
		}
		{
			Peptide peptide = new Peptide( sequence );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass, calcMass, 0.01 );	// tested against https://web.expasy.org/peptide_mass/
		}
		
		
		
		// add in mods
		{
			Peptide peptide = new Peptide( sequence, mods );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass + ( 3.0 * phosphoMass ), calcMass, 0.01 );
		}
		{
			Peptide peptide = new Peptide( sequence, mods );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass + ( 3.0 * phosphoMass ), calcMass, 0.01 );
		}
		
		// add in labels
		{
			Peptide peptide = new Peptide( sequence, mods, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.MONOISOTOPIC );
			
			assertEquals( preCalcMonoMass15N + ( 3.0 * phosphoMass ), calcMass, 0.01 );
		}
		{
			Peptide peptide = new Peptide( sequence, mods, label );		
			double calcMass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassType.AVERAGE );
			
			assertEquals( preCalcAvgMass15N + ( 3.0 * phosphoMass ), calcMass, 0.1 );	// not getting exact agreement on average
		}
		
	}
	
}
