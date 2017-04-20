package org.yeastrc.proteomics.peptide.peptide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.yeastrc.proteomics.modifications.types.Modification;

public class PeptideModificationUtils {

	/**
	 * Find the sites in the supplied peptide sequence N terminus is 0, first position is 1, etc.
	 * that may be modifified by this modification
	 * @param sequence The sequence of the peptide we're testing
	 * @param mod The Modification we're testing for
	 * @return An array of the sites (starting at 1 for the first residue) that may carry this modification
	 */
	public static List<Integer> findSites( String sequence, Modification mod ) {
		List<Integer> sites = new ArrayList<Integer>();
		
		for (int i = 0; i < sequence.length(); i++){
		    char c = sequence.charAt(i);        
		    
		    for( char t : mod.getModifiedResidues() ) {
		    	if( c == t ) sites.add( i + 1 );
		    }
		}
		
		return sites;
	}

	/**
	 * Add the requested mod to the requested position in the supplied peptide.
	 * @param peptide
	 * @param position
	 * @param mod
	 * @throws Exception if the supplied mod does not modify the residue at the requested position in the supplied peptide
	 */
	public static void modPosition( Peptide peptide, int position, Modification mod ) throws Exception {
		
		if( !(findSites( peptide.getSequence(), mod ) ).contains( position ) )
			throw new IllegalArgumentException( "Supplied mod does not modify the supplied sequence at the requested site." );
		
		if( peptide.getModifications() == null )
			peptide.setModification( new HashMap<Integer, Collection<Modification>>() );		
		
		Map<Integer, Collection<Modification>> mods = peptide.getModifications();
		
		if( mods.get( position ) == null )
			mods.put( position, new HashSet<Modification>() );
		
		mods.get( position ).add( mod );
	}

	
	
	/**
	 * Modify all possible sites in the supplied Peptide with the supplied modification
	 * This is useful for static modifications such as carboxyamidomethylation of all cysteines.
	 * Note: a particular modificaton may only appear once at a given position. 
	 * @param peptide
	 * @param mod
	 */
	public static void modAllPositions( Peptide peptide, Modification mod ) {
		
		if( peptide.getModifications() == null )
			peptide.setModification( new HashMap<Integer, Collection<Modification>>() );		
		
		Map<Integer, Collection<Modification>> mods = peptide.getModifications();

		List<Integer> sites = findSites(peptide.getSequence(), mod);
		for( int site : sites ) {
			
			if( mods.get( site ) == null )
				mods.put( site, new HashSet<Modification>() );
			
			Collection<Modification> localmods = mods.get( site );
			localmods.add( mod );			
		}
	}
	
	
	/**
	 * Creates a new copy of the supplied modificatoins
	 * @param mods
	 * @return
	 */
	public static Map<Integer, Collection<Modification>> copyMods( Map<Integer, Collection<Modification>> mods ) {
		if( mods == null ) return null;
		
		Map<Integer, Collection<Modification>> newMods = new HashMap<Integer, Collection<Modification>>();
		for( int i : mods.keySet() ) {
			Collection<Modification> newLocalMods = new HashSet<Modification>();
			for( Modification mod : mods.get( i ) ) {
				newLocalMods.add( mod );
			}
			newMods.put( i,  newLocalMods );
		}
		
		return newMods;
	}
	
}
