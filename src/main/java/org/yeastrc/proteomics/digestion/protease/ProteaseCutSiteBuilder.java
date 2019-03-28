package org.yeastrc.proteomics.digestion.protease;

import java.util.List;

public class ProteaseCutSiteBuilder {
    private List<String> cutResidues;
    private boolean cutAfter;
    private List<String> requiredCTermSequences;
    private List<String> requiredNTermSequences;
    private List<String> prohibitedCTermSequences;
    private List<String> prohibitedNTermSequences;

    public ProteaseCutSiteBuilder setCutResidues(List<String> cutResidues) {
        this.cutResidues = cutResidues;
        return this;
    }

    public ProteaseCutSiteBuilder setCutAfter(boolean cutAfter) {
        this.cutAfter = cutAfter;
        return this;
    }

    public ProteaseCutSiteBuilder setRequiredCTermSequences(List<String> requiredCTermSequences) {
        this.requiredCTermSequences = requiredCTermSequences;
        return this;
    }

    public ProteaseCutSiteBuilder setRequiredNTermSequences(List<String> requiredNTermSequences) {
        this.requiredNTermSequences = requiredNTermSequences;
        return this;
    }

    public ProteaseCutSiteBuilder setProhibitedCTermSequences(List<String> prohibitedCTermSequences) {
        this.prohibitedCTermSequences = prohibitedCTermSequences;
        return this;
    }

    public ProteaseCutSiteBuilder setProhibitedNTermSequences(List<String> prohibitedNTermSequences) {
        this.prohibitedNTermSequences = prohibitedNTermSequences;
        return this;
    }

    public ProteaseCutSite createProteaseCutSite() {
        return new ProteaseCutSite(cutResidues, cutAfter, requiredCTermSequences, requiredNTermSequences, prohibitedCTermSequences, prohibitedNTermSequences);
    }
}