package org.yeastrc.proteomics.digestion;

import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.ProteaseCutSite;
import org.yeastrc.proteomics.mass.MassUtils;
import org.yeastrc.proteomics.peptide.peptide.Peptide;
import org.yeastrc.proteomics.peptide.peptide.PeptideMassCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class DigestionUtils {

    /**
     * Get the cut sites in the protein sequence cut by this protease. Returns a list of cut positions
     * ordered from smallest to largest. The cut position indicates the position in the protein of the
     * residue immediately preceding the cut site (starting at 1). E.g. PE|PTIDE, the | would indicate
     * a cut position of 2.
     *
     * @param proteinSequence The protein sequence in the form of PEPTIDE, n->c terminus from left->right
     * @param protease The protease performing the digestion
     * @param excludedSites A collection of sites that may not be cleaved (eg, PTM sites). Sites are numbered
     *                      starting at 1 and represent the position of the residue to the left (n-terminus)
     *                      of the cut site. E.g., an excluded site of 2 would prevent cuts after K in PKPTIDE
     * @return A list of cut sites. An empty list if none are found.
     */
    public static List<Integer> getSortedCutSitesInProtein(String proteinSequence, IProtease protease, Collection<Integer> excludedSites ) {

        List<Integer> cutSites = new ArrayList<>();

        for (int zeroIndexPosition = 0; zeroIndexPosition < proteinSequence.length(); zeroIndexPosition++) {

            // index in protein sequence, starting at one
            int oneBasedIndexPosition = zeroIndexPosition + 1;

            // the position of the cut, which is the position of the residue the cut is AFTER
            Integer cutPosition = getValidCutSite( proteinSequence, protease, oneBasedIndexPosition );

            if( cutPosition != null && cutPosition != 0 && cutPosition < proteinSequence.length() ) {

                if( excludedSites == null || !excludedSites.contains( cutPosition ) ) {
                    cutSites.add(cutPosition);
                }
            }

        }

        return cutSites;
    }

    /**
     * Get the cut sites in the protein sequence cut by this protease. Returns a list of cut positions
     * ordered from smallest to largest. The cut position indicates the position in the protein of the
     * residue immediately preceding the cut site (starting at 1). E.g. PE|PTIDE, the | would indicate
     * a cut position of 2.
     *
     * @param proteinSequence The protein sequence in the form of PEPTIDE, n->c terminus from left->right
     * @param protease The protease performing the digestion
     * @return A list of cut sites. An empty list if none are found.
     */
    public static List<Integer> getSortedCutSitesInProtein(String proteinSequence, IProtease protease ) {

        return getSortedCutSitesInProtein( proteinSequence, protease, null );
    }

    /**
     * Digest the given protein sequence with the given protease and digestion parameters.
     *
     * @param proteinSequence The protein sequence to be digested
     * @param protease The protease doing the digesting
     * @param parameters The filter parameters to determining whether or not to include a digestion product
     * @return A collection of digestion products given the paramters. An empty collection if non are found.
     */
    public static Collection<DigestionProduct> digestProteinSequence( String proteinSequence,
                                                                      IProtease protease,
                                                                      DigestionParameters parameters ) {

        return digestProteinSequence( proteinSequence, protease, parameters, null, null );
    }

    /**
     * Digest the given protein sequence with the given protease and digestion parameters.
     *
     * @param proteinSequence The protein sequence to be digested
     * @param protease The protease doing the digesting
     * @param parameters The filter parameters to determining whether or not to include a digestion product
     * @param excludedSites A collection of sites that may not be cleaved (eg, PTM sites). Sites are numbered
     *                      starting at 1 and represent the position of the residue to the left (n-terminus)
     *                      of the cut site. E.g., an excluded site of 2 would prevent cuts after K in PKPTIDE
     *                      Set to null to have no excluded sites
     * @param requiredPositions A collection of protein positions (starting at 1) that the collection of returned
     *                          digestion products must contain. Set to null to have no required positions
     * @return A collection of digestion products given the paramters. An empty collection if non are found.
     */
    public static Collection<DigestionProduct> digestProteinSequence( String proteinSequence,
                                                                      IProtease protease,
                                                                      DigestionParameters parameters,
                                                                      Collection<Integer> excludedSites,
                                                                      Collection<Integer> requiredPositions ) {

        Collection<DigestionProduct> digestionProducts = new HashSet<>();

        // sorted arraylist of cut sites in this protein sequence for this protease
        List<Integer> sortedCutSites = getSortedCutSitesInProtein( proteinSequence, protease, excludedSites );

        // add n-terminal digestion products
        digestionProducts.addAll( getDigestionProductsForStartPosition( proteinSequence, sortedCutSites, parameters, 1, requiredPositions ) );

        for( int cutPosition : sortedCutSites ) {

            // the start position of the digestion product in the protein sequence
            int startPosition = cutPosition + 1;

            digestionProducts.addAll( getDigestionProductsForStartPosition(proteinSequence, sortedCutSites, parameters, startPosition, requiredPositions)) ;
        }

        return digestionProducts;
    }


    private static Collection<DigestionProduct> getDigestionProductsForStartPosition( String proteinSequence,
                                                                                      List<Integer> sortedCutSites,
                                                                                      DigestionParameters parameters,
                                                                                      int oneBasedStartPosition,
                                                                                      Collection<Integer> requiredPositions ) {

        Collection<DigestionProduct> digestionProducts = new HashSet<>();

        int numMissedCleavages = 0;

        for( int cutSitePosition : sortedCutSites ) {

            // skip the cut sites preceding our starting position
            if( cutSitePosition < oneBasedStartPosition ) {
                continue;
            }

            if( digestionProductMeetsParameters( proteinSequence, oneBasedStartPosition, cutSitePosition, parameters, requiredPositions ) ) {

                int length = cutSitePosition - oneBasedStartPosition + 1;
                DigestionProduct digestionProduct = getDigestionProduct( oneBasedStartPosition, length, numMissedCleavages, proteinSequence );

                digestionProducts.add( digestionProduct );
                //printJavaBuildCodeforDigestionProduct( digestionProduct );

            }

            numMissedCleavages++;

            // we've hit our maximum number of missed cleavages, stop
            if( parameters.getNumMissedCleavages() != null && numMissedCleavages > parameters.getNumMissedCleavages() ) {
                break;
            }

        }

        // add c-terminal fragment if necessary (not over the num of allowable missed cleavages)
        if( proteinSequence.length() > 0 && ( parameters.getNumMissedCleavages() == null || numMissedCleavages <= parameters.getNumMissedCleavages() ) ) {

            if( digestionProductMeetsParameters( proteinSequence, oneBasedStartPosition, proteinSequence.length(), parameters, requiredPositions ) ) {

                int length = proteinSequence.length() - oneBasedStartPosition + 1;
                DigestionProduct digestionProduct = getDigestionProduct( oneBasedStartPosition, length, numMissedCleavages, proteinSequence );

                digestionProducts.add(digestionProduct);
                //printJavaBuildCodeforDigestionProduct( digestionProduct );
            }
        }

        return digestionProducts;
    }


    private static DigestionProduct getDigestionProduct( int startPosition, int length, int missedCleavages, String proteinSequence ) {

        DigestionProduct dp = new DigestionProduct();

        dp.setProteinPosition( startPosition );
        dp.setPeptideLength( length );
        dp.setMissedCleavages( missedCleavages );

        if( startPosition == 1 ) {
            dp.setNTerminal( true );
        } else {
            dp.setCTerminal( false );
        }

        if( startPosition + length - 1 == proteinSequence.length() ) {
            dp.setCTerminal( true );
        } else {
            dp.setCTerminal( false );
        }

        return dp;
    }


    private static boolean digestionProductMeetsParameters( String proteinSequence,
                                                              int startPosition,
                                                              int endPosition,
                                                              DigestionParameters parameters,
                                                              Collection<Integer> requiredPositions) {

        int length = endPosition - startPosition + 1;

        if( requiredPositions != null && !digestionProductsContainsARequiredPosition( startPosition, endPosition, requiredPositions ) ) {
            return false;
        }

        if( parameters.getMaxPeptideLength() != null && length > parameters.getMaxPeptideLength() ) {
            return false;
        }

        if( parameters.getMinPeptideLength() != null && length < parameters.getMinPeptideLength() ) {
            return false;
        }

        if( parameters.getMaxPeptideMass() != null ||
            parameters.getMinPeptideMass() != null ) {

            String peptideSequence = proteinSequence.substring( startPosition - 1, startPosition - 1 + length );
            Peptide peptide = new Peptide( peptideSequence );
            double mass = PeptideMassCalculator.getInstance().getMassForPeptide( peptide, MassUtils.MassType.MONOISOTOPIC );

            if( parameters.getMaxPeptideMass() != null && mass > parameters.getMaxPeptideMass() ) {
                return false;
            }

            if( parameters.getMinPeptideMass() != null && mass < parameters.getMinPeptideMass() ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Return true if the supplied start/end positions define a range that includes at least
     * one required position.
     *
     * @param startPosition
     * @param endPosition
     * @param requiredPositions
     * @return
     */
    private static boolean digestionProductsContainsARequiredPosition( int startPosition,
                                                                       int endPosition,
                                                                       Collection<Integer> requiredPositions ) {

        for( int requiredPosition : requiredPositions ) {

            if( startPosition <= requiredPosition && requiredPosition <= endPosition ) {
                return true;
            }

        }

        return false;
    }


    private static Integer getValidCutSite( String proteinSequence, IProtease protease, int oneBasedPosition ) {

        for( ProteaseCutSite cutSite : protease.getCutSites() ) {

            // indicated position contains valid residue and passes prefix/suffix tests
            if (cutSite.getCutResidues().contains(proteinSequence.substring(oneBasedPosition - 1, oneBasedPosition)) &&
                    passesPrefixTests(proteinSequence, cutSite, oneBasedPosition) &&
                    passesSuffixTests(proteinSequence, cutSite, oneBasedPosition) ) {

                if( cutSite.isCutAfter() ) {
                    return oneBasedPosition;
                } else {
                    return oneBasedPosition - 1;
                }
            }
        }

        return null;
    }


    private static boolean passesPrefixTests( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if( position == 1 ) { return true; }
        if( position == proteinSequence.length() ) { return true; }

        if( !hasRequiredPrefixes( proteinSequence, cutSite, position ) ) {
            return false;
        }

        if( hasProhibitedPrefixes( proteinSequence, cutSite, position ) ) {
            return false;
        }

        return true;
    }

    private static boolean passesSuffixTests( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if( position == 1 ) { return true; }
        if( position == proteinSequence.length() ) { return true; }

        if( !hasRequiredSuffixes( proteinSequence, cutSite, position ) ) {
            return false;
        }

        if( hasProhibitedSuffixes( proteinSequence, cutSite, position ) ) {
            return false;
        }

        return true;
    }

    private static boolean proteinHasPrefixAtPosition( String proteinSequence, int position, String prefix ) {

        // prefix is longer than the # of residues n-terminal of the position
        if (prefix.length() >= position) {
            return false;
        }

        int prefixStart = position - 1 - prefix.length();

        // prefix is present
        return proteinSequence.substring(prefixStart, prefixStart + prefix.length()).equals(prefix);

    }

    private static boolean proteinHasSuffixAtPosition( String proteinSequence, int position, String suffix ) {

        // not enough residues at end of protein to contain this suffix, so nope
        if( position + suffix.length() > proteinSequence.length() ) {
            return false;
        }

        // suffix is present
        return proteinSequence.substring(position, position + suffix.length()).equals(suffix);

    }

    private static boolean hasRequiredPrefixes( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if (cutSite.getRequiredNTermSequences() != null) {

            for (String prefix : cutSite.getRequiredNTermSequences()) {

                if( proteinHasPrefixAtPosition( proteinSequence, position, prefix ) ) {
                    return true;
                }
            }

        } else {

            // there are no required prefixes, so it has the required prefixes
            return true;
        }

        // if here, no required prefixes were found
        return false;
    }

    private static boolean hasProhibitedPrefixes( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if( cutSite.getProhibitedNTermSequences() != null ) {

            for (String prefix : cutSite.getProhibitedNTermSequences()) {

                if( proteinHasPrefixAtPosition( proteinSequence, position, prefix ) ) {
                    return true;
                }
            }
        }

        // if here, then no prohibited prefixes were found
        return false;
    }

    private static boolean hasRequiredSuffixes( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if (cutSite.getRequiredCTermSequences() != null) {

            for (String prefix : cutSite.getRequiredCTermSequences()) {

                if( proteinHasSuffixAtPosition( proteinSequence, position, prefix ) ) {
                    return true;
                }
            }

        } else {

            // there are no required suffixes, so it has the required suffixes
            return true;
        }

        // if here, no required suffixes were found
        return false;
    }

    private static boolean hasProhibitedSuffixes( String proteinSequence, ProteaseCutSite cutSite, int position ) {

        if( cutSite.getProhibitedCTermSequences() != null ) {

            for (String prefix : cutSite.getProhibitedCTermSequences()) {

                if( proteinHasSuffixAtPosition( proteinSequence, position, prefix ) ) {
                    return true;
                }
            }
        }

        // if here, then no prohibited prefixes were found
        return false;
    }

    /**
     * Used for building tests.
     *
     * In the form of:
     *
     *         {
     *             DigestionProduct dp = new DigestionProduct();
     *             dp.setNTerminal( true );
     *             dp.setCTerminal( false );
     *             dp.setProteinPosition( 1 );
     *             dp.setPeptideLength( 9 );
     *             dp.setMissedCleavages( 0 );
     *
     *             products.add( dp );
     *         }
     *
     * @param dp The Digestion product to print
     */
    private static void printJavaBuildCodeforDigestionProduct( DigestionProduct dp ) {

        System.out.println( "{" );
        System.out.println( "\tDigestionProduct dp = new DigestionProduct();" );
        System.out.println( "\tdp.setNTerminal( " + dp.isNTerminal() + " );" );
        System.out.println( "\tdp.setCTerminal( " + dp.isCTerminal() + " );" );
        System.out.println( "\tdp.setProteinPosition( " + dp.getProteinPosition() + " );" );
        System.out.println( "\tdp.setPeptideLength( " + dp.getPeptideLength() + " );" );
        System.out.println( "\tdp.setMissedCleavages( " + dp.getMissedCleavages() + " );" );
        System.out.println();
        System.out.println( "\tproducts.add( dp );" );
        System.out.println( "}\n" );

    }


}
