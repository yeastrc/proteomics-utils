package org.yeastrc.proteomics.peptide.peptide;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

import static org.junit.Assert.*;

public class PeptideUtilsTest {

	@Test
	public void testIsValidPeptideSequence() {
		
		String sequence = "GPAVLIMCFYWHKRQNEDST";
		assertTrue( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );

		assertEquals( 20, sequence.length() );	// just make sure I have all 20 amino acids.
		
		sequence = "";
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = null;
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "ZGPAVLIMCFYWHKRQNEDST";
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "GPAVLIMCFYWHKRQNEDSZ";
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );

		sequence = "GPAVLIMCFYWBHKRQNEDST";	// B
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
	
		sequence = "ZGPAVLIMCFYJHKRQNEDST"; // J
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "ZGPAVLIMCFYXHKRQNEDST"; // X
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "ZGPAVLIMCFYZHKRQNEDST"; // Z
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "ZGPAVLIMCFYOHKRQNEDST"; // O
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
		
		sequence = "ZGPAVLIMCFY;HKRQNEDST"; // ;
		assertFalse( sequence, PeptideUtils.isValidPeptideSequence( sequence ) );
	}
	
	@Test
	public void testIsValidPeptideModPositions() {
		
		String sequence = "GPAVLIMCFYWHKRQNEDST";
		
		{
			Collection<Integer> positions = new HashSet<>();

			positions.add( 0 );
			positions.add( 1 );
			positions.add( 2 );
			positions.add( 3 );
			positions.add( 4 );
			positions.add( 5 );
			positions.add( 6 );
			positions.add( 7 );
			positions.add( 8 );
			positions.add( 9 );
			positions.add( 10 );
			positions.add( 11 );
			positions.add( 12 );
			positions.add( 13 );
			positions.add( 14 );
			positions.add( 15 );
			positions.add( 16 );
			positions.add( 17 );
			positions.add( 18 );
			positions.add( 19 );
			positions.add( 20 );

			assertTrue( PeptideUtils.isValidPeptideModPositions( sequence, positions ) );
		}
		
		{
			Collection<Integer> positions = new HashSet<>();

			positions.add( 0 );
			positions.add( 1 );
			positions.add( 2 );
			positions.add( 3 );
			positions.add( 4 );
			positions.add( 5 );
			positions.add( 6 );
			positions.add( 7 );
			positions.add( 8 );
			positions.add( 9 );
			positions.add( 10 );
			positions.add( 11 );
			positions.add( 12 );
			positions.add( 13 );
			positions.add( 14 );
			positions.add( 15 );
			positions.add( 16 );
			positions.add( 17 );
			positions.add( 18 );
			positions.add( 19 );
			positions.add( 20 );
			positions.add( 21 );

			assertFalse( PeptideUtils.isValidPeptideModPositions( sequence, positions ) );
		}
		
		{
			Collection<Integer> positions = new HashSet<>();

			positions.add( -1 );
			positions.add( 0 );
			positions.add( 1 );
			positions.add( 2 );
			positions.add( 3 );
			positions.add( 4 );
			positions.add( 5 );
			positions.add( 6 );
			positions.add( 7 );
			positions.add( 8 );
			positions.add( 9 );
			positions.add( 10 );
			positions.add( 11 );
			positions.add( 12 );
			positions.add( 13 );
			positions.add( 14 );
			positions.add( 15 );
			positions.add( 16 );
			positions.add( 17 );
			positions.add( 18 );
			positions.add( 19 );
			positions.add( 20 );

			assertFalse( PeptideUtils.isValidPeptideModPositions( sequence, positions ) );
		}
		
		{
			Collection<Integer> positions = new HashSet<>();

			assertTrue( PeptideUtils.isValidPeptideModPositions( sequence, positions ) );
		}
		
		{
			assertTrue( PeptideUtils.isValidPeptideModPositions( sequence, null ) );
		}
		
		
	}
	
}
