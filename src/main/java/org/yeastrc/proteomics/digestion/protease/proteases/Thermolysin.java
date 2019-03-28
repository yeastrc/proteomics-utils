package org.yeastrc.proteomics.digestion.protease.proteases;

import org.yeastrc.proteomics.digestion.protease.ProteaseCutSite;
import org.yeastrc.proteomics.digestion.protease.ProteaseCutSiteBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Thermolysin implements IProtease {

    private static final Thermolysin _INSTANCE = new Thermolysin();
    public static Thermolysin getInstance() { return _INSTANCE; }

    /**
     * Thermolysin:
     *
     * Cut at: N-terminal side of A, F, I, L, M, V
     * but not if D or E is N-term to A, F, I, L, M, V
     *
     * Source: https://web.expasy.org/peptide_mass/peptide-mass-doc.html#table1
     *
     */
    private Thermolysin() {

        this.cutSites = new HashSet<>();

        ProteaseCutSiteBuilder cutSiteBuilder = new ProteaseCutSiteBuilder();

        cutSiteBuilder.setCutAfter( false );

        List<String> cutResidues = new ArrayList<>( 6 );
        cutResidues.add( "A" );
        cutResidues.add( "F" );
        cutResidues.add( "I" );
        cutResidues.add( "L" );
        cutResidues.add( "M" );
        cutResidues.add( "V" );
        cutSiteBuilder.setCutResidues( cutResidues );

        List<String> prohibitedNTermResidues = new ArrayList<>( 2 );
        prohibitedNTermResidues.add( "D" );
        prohibitedNTermResidues.add( "E" );
        cutSiteBuilder.setProhibitedNTermSequences(prohibitedNTermResidues);

        this.cutSites.add( cutSiteBuilder.createProteaseCutSite() );
    }

    public Collection<ProteaseCutSite> getCutSites() { return this.cutSites; }

    private Collection<ProteaseCutSite> cutSites;

}
