package org.yeastrc.proteomics.digestion.protease.proteases;

import org.yeastrc.proteomics.digestion.protease.ProteaseCutSite;

import java.util.Collection;

public interface IProtease {

    Collection<ProteaseCutSite> getCutSites();

}
