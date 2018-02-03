package org.yeastrc.proteomics.peptide.peptide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.yeastrc.proteomics.peptide.isotope_label.IsotopeLabel;
import org.yeastrc.proteomics.peptide.isotope_label.LabelFactory;


public class PeptideTest {

	@Test
	public void testCreatePeptideFromSequenceOnly() {
		
		String sequence = "PPEPPTIDE";
		
		Peptide peptide = new Peptide( sequence );

		assertEquals( sequence, peptide.getSequence() );
		assertNull( peptide.getLabel() );
		assertNull( peptide.getModificationMasses() );
	}
	
	@Test
	public void testCreatePeptideFromSequencePlusLabel() {
		
		String sequence = "PPEPPTIDE";
		IsotopeLabel label = LabelFactory.getInstance().getLabel( "15N" );
		
		Peptide peptide = new Peptide( sequence, label );

		assertEquals( sequence, peptide.getSequence() );
		assertEquals( LabelFactory.getInstance().getLabel( "15N" ), peptide.getLabel() );
		assertNull( peptide.getModificationMasses() );
	}
	
	
	@Test
	public void testCreatePeptideFromSequencePlusMods() {
		
		String sequence = "PPEPPTIDE";
		
		Map<Integer, Double> mods = new HashMap<>();
		
		mods.put( 1, 44.55 );
		mods.put( 3, 23.56 );
		mods.put( 8, 334.22 );
		mods.put( 9, -333.45 );
		
		
		Peptide peptide = new Peptide( sequence, mods );

		assertEquals( sequence, peptide.getSequence() );
		assertNull( peptide.getLabel() );

		assertEquals( 4, peptide.getModificationMasses().size() );
		assertEquals( 44.55, peptide.getModificationMasses().get( 1 ).doubleValue(), 0.00001 );
		assertEquals( 23.56, peptide.getModificationMasses().get( 3 ).doubleValue(), 0.00001 );
		assertEquals( 334.22, peptide.getModificationMasses().get( 8 ).doubleValue(), 0.00001 );
		assertEquals( -333.45, peptide.getModificationMasses().get( 9 ).doubleValue(), 0.00001 );
	}
	
	@Test
	public void testCreatePeptideFromIllegalModPosition() {
		
		String sequence = "PPEPPTIDE";

		// put mod before start of peptide
		{
			Map<Integer, Double> mods = new HashMap<>();
			mods.put( -1, 44.55 );
		
			try {	
				new Peptide( sequence, mods );
				fail();
			} catch( IllegalArgumentException e ) {
			}
		}
		
		// put mod passed end of peptide
		{
			Map<Integer, Double> mods = new HashMap<>();
			mods.put( 11, 44.55 );
		
			try {	
				new Peptide( sequence, mods );
				fail();
			} catch( IllegalArgumentException e ) {
			}
		}
			

	}
	
}
