package org.yeastrc.proteomics.peptide.aminoacid;

public class AminoAcidUtils {

	/**
	 * Get the amino acid object for the supplied amino acid symbol (e.g. Lysine for K)
	 * @param residue
	 * @return
	 * @throws Exception
	 */
	public static AminoAcid getAminoAcidBySymbol( char residue ) throws Exception {
		AminoAcid aa = null;
		
		switch( residue ) {
			case 'A' : aa = ALANINE; break;
			case 'R' : aa = ARGININE; break;
			case 'N' : aa = ASPARAGINE; break;
			case 'D' : aa = ASPARTIC_ACID; break;
			case 'C' : aa = CYSTEINE; break;
			case 'E' : aa = GLUTAMIC_ACID; break;
			case 'Q' : aa = GLUTAMINE; break;
			case 'G' : aa = GLYCINE; break;
			case 'H' : aa = HISTIDINE; break;
			case 'I' : aa = ISOLEUCINE; break;
			case 'L' : aa = LEUCINE; break;
			case 'K' : aa = LYSINE; break;
			case 'M' : aa = METHIONINE; break;
			case 'F' : aa = PHENYLALANINE; break;
			case 'P' : aa = PROLINE; break;
			case 'S' : aa = SERINE; break;
			case 'T' : aa = THREONINE; break;
			case 'U' : aa = SELENOCYSTEINE; break;
			case 'W' : aa = TRYPTOPHAN; break;
			case 'Y' : aa = TYROSINE; break;
			case 'V' : aa = VALINE; break;
			case 'O' : aa = PYRROLYSINE; break;
			default : throw new IllegalArgumentException( "Unknown amino acid residue." );
		}
		
		return aa;
	}
	
	/**
	 * Get the amino acid object for the supplied amino acid abbreviation ( e.g. Lysine for "Lys")
	 * Not case sensitive
	 * @param abbr
	 * @return
	 * @throws Exception
	 */
	public static AminoAcid getAminoAcidByAbbreviation( String abbr ) throws Exception {
		abbr = abbr.toLowerCase();
		AminoAcid aa = null;
		
		switch( abbr ) {
			case "ala" : aa = ALANINE; break;
			case "arg" : aa = ARGININE; break;
			case "asn" : aa = ASPARAGINE; break;
			case "asp" : aa = ASPARTIC_ACID; break;
			case "cys" : aa = CYSTEINE; break;
			case "glu" : aa = GLUTAMIC_ACID; break;
			case "gln" : aa = GLUTAMINE; break;
			case "gly" : aa = GLYCINE; break;
			case "his" : aa = HISTIDINE; break;
			case "ile" : aa = ISOLEUCINE; break;
			case "leu" : aa = LEUCINE; break;
			case "lys" : aa = LYSINE; break;
			case "met" : aa = METHIONINE; break;
			case "phe" : aa = PHENYLALANINE; break;
			case "pro" : aa = PROLINE; break;
			case "ser" : aa = SERINE; break;
			case "thr" : aa = THREONINE; break;
			case "seC" : aa = SELENOCYSTEINE; break;
			case "trp" : aa = TRYPTOPHAN; break;
			case "tyr" : aa = TYROSINE; break;
			case "val" : aa = VALINE; break;
			case "pyl" : aa = PYRROLYSINE; break;
			default : throw new IllegalArgumentException( "Unknown amino acid abbreviation: " + abbr );
		}
		
		return aa;
	}
	
	/**
	 * Get the amino acid object for the supplied amino acid name ( e.g. Lysine for "lysine")
	 * Not case sensitive.
	 * @param abbr
	 * @return
	 * @throws Exception
	 */
	public static AminoAcid getAminoAcidByName( String name ) throws Exception {
		AminoAcid aa = null;
		name = name.toLowerCase();
		
		switch( name ) {
			case "alanine" : aa = ALANINE; break;
			case "arginine" : aa = ARGININE; break;
			case "asparagine" : aa = ASPARAGINE; break;
			case "aspartic acid" : aa = ASPARTIC_ACID; break;
			case "cysteine" : aa = CYSTEINE; break;
			case "glutamic acid" : aa = GLUTAMIC_ACID; break;
			case "glutamine" : aa = GLUTAMINE; break;
			case "glycine" : aa = GLYCINE; break;
			case "histidine" : aa = HISTIDINE; break;
			case "isoleucine" : aa = ISOLEUCINE; break;
			case "leucine" : aa = LEUCINE; break;
			case "lysine" : aa = LYSINE; break;
			case "methionine" : aa = METHIONINE; break;
			case "phenylalanine" : aa = PHENYLALANINE; break;
			case "proline" : aa = PROLINE; break;
			case "serine" : aa = SERINE; break;
			case "threonine" : aa = THREONINE; break;
			case "selenocysteine" : aa = SELENOCYSTEINE; break;
			case "tryptophan" : aa = TRYPTOPHAN; break;
			case "tyrosine" : aa = TYROSINE; break;
			case "valine" : aa = VALINE; break;
			case "pyrrolysine" : aa = PYRROLYSINE; break;
			default : throw new IllegalArgumentException( "Unknown amino acid abbreviation." );
		}
		
		return aa;
	}
	
	public static final AminoAcid ALANINE = new Alanine();
	public static final AminoAcid ARGININE = new Arginine();
	public static final AminoAcid ASPARAGINE = new Asparagine();
	public static final AminoAcid ASPARTIC_ACID = new AsparticAcid();
	public static final AminoAcid CYSTEINE = new Cysteine();
	public static final AminoAcid GLUTAMIC_ACID = new GlutamicAcid();
	public static final AminoAcid GLUTAMINE = new Glutamine();
	public static final AminoAcid GLYCINE = new Glycine();
	public static final AminoAcid HISTIDINE = new Histidine();
	public static final AminoAcid ISOLEUCINE = new Isoleucine();
	public static final AminoAcid LEUCINE = new Leucine();
	public static final AminoAcid LYSINE = new Lysine();
	public static final AminoAcid METHIONINE = new Methionine();
	public static final AminoAcid PHENYLALANINE = new Phenylalanine();
	public static final AminoAcid PROLINE = new Proline();
	public static final AminoAcid SERINE = new Serine();
	public static final AminoAcid THREONINE = new Threonine();
	public static final AminoAcid SELENOCYSTEINE = new Selenocysteine();
	public static final AminoAcid TRYPTOPHAN = new Tryptophan();
	public static final AminoAcid TYROSINE = new Tyrosine();
	public static final AminoAcid VALINE = new Valine();
	public static final AminoAcid PYRROLYSINE = new Pyrrolysine();
	
}
