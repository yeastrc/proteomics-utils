package org.yeastrc.proteomics.peptide.atom;

public class AtomUtils {

	public static final Atom ATOM_CARBON = new Carbon();
	public static final Atom ATOM_HYDROGEN = new Hydrogen();
	public static final Atom ATOM_NITROGEN = new Nitrogen();
	public static final Atom ATOM_OXYGEN = new Oxygen();
	
	/**
	 * Get the atom corresponding to the supplied symbol
	 * @param symbol
	 * @return
	 * @throws Exception
	 */
	public static Atom getAtom( char symbol ) throws Exception {
		Atom retAtom = null;
		
		switch( symbol ) {
			case 'C' : retAtom = ATOM_CARBON; break;
			case 'H' : retAtom = ATOM_HYDROGEN; break;
			case 'N' : retAtom = ATOM_NITROGEN; break;
			case 'O' : retAtom = ATOM_OXYGEN; break;
			default: throw new IllegalArgumentException( "Unsupported atomic symbol: " + symbol );
		}
		
		return retAtom;
	}
	
}
