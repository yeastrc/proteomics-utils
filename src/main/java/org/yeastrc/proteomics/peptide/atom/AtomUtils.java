package org.yeastrc.proteomics.peptide.atom;

public class AtomUtils {

	public static final Atom ATOM_CARBON = new Carbon();
	public static final Atom ATOM_HYDROGEN = new Hydrogen();
	public static final Atom ATOM_NITROGEN = new Nitrogen();
	public static final Atom ATOM_OXYGEN = new Oxygen();
	public static final Atom ATOM_SULFUR = new Sulfur();
	public static final Atom ATOM_SELENIUM = new Selenium();
	public static final Atom ATOM_PROTON = new Proton();
	public static final Atom ATOM_NEUTRON = new Neutron();
	
	/**
	 * Get the atom corresponding to the supplied symbol
	 * @param symbol
	 * @return
	 * @throws Exception
	 */
	public static Atom getAtom( String symbol ) throws Exception {
		Atom retAtom = null;
		
		switch( symbol ) {
			case "C" : retAtom = ATOM_CARBON; break;
			case "H" : retAtom = ATOM_HYDROGEN; break;
			case "N" : retAtom = ATOM_NITROGEN; break;
			case "O" : retAtom = ATOM_OXYGEN; break;
			case "S" : retAtom = ATOM_SULFUR; break;
			case "Se" : retAtom = ATOM_SELENIUM; break;
			case "p" : retAtom = ATOM_PROTON; break;
			case "n" : retAtom = ATOM_NEUTRON; break;
			
			default: throw new IllegalArgumentException( "Unsupported atomic symbol: " + symbol );
		}
		
		return retAtom;
	}
	
}
