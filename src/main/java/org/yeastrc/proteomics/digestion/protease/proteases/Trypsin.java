package org.yeastrc.proteomics.digestion.protease.proteases;

import org.yeastrc.proteomics.digestion.protease.ProteaseCutSite;
import org.yeastrc.proteomics.digestion.protease.ProteaseCutSiteBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Trypsin implements IProtease {

    private static final Trypsin _INSTANCE = new Trypsin();
    public static Trypsin getInstance() { return _INSTANCE; }

    private Trypsin() {

        this.cutSites = new HashSet<>();

        ProteaseCutSiteBuilder cutSiteBuilder = new ProteaseCutSiteBuilder();

        cutSiteBuilder.setCutAfter( true );

        List<String> cutResidues = new ArrayList<>( 2 );
        cutResidues.add( "K" );
        cutResidues.add( "R" );
        cutSiteBuilder.setCutResidues( cutResidues );

        List<String> prohibitedCTermResidues = new ArrayList<>( 1 );
        prohibitedCTermResidues.add( "P" );
        cutSiteBuilder.setProhibitedCTermSequences( prohibitedCTermResidues );

        this.cutSites.add( cutSiteBuilder.createProteaseCutSite() );
    }

    public Collection<ProteaseCutSite> getCutSites() { return this.cutSites; }

    private Collection<ProteaseCutSite> cutSites;

}
