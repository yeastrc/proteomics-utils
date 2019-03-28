package org.yeastrc.proteomics.digestion.protease;

import java.util.List;

/**
 * A cut site for a protease. Essentially a given list of
 * residues at which the protease can cut. All the residues
 * defined in a single ProteaseCutSite must have the same
 * required and prohibited suffixes and prefixes.
 */
public class ProteaseCutSite {

    public ProteaseCutSite(List<String> cutResidues, boolean cutAfter, List<String> requiredCTermSequences, List<String> requiredNTermSequences, List<String> prohibitedCTermSequences, List<String> prohibitedNTermSequences) {
        this.cutResidues = cutResidues;
        this.cutAfter = cutAfter;
        this.requiredCTermSequences = requiredCTermSequences;
        this.requiredNTermSequences = requiredNTermSequences;
        this.prohibitedCTermSequences = prohibitedCTermSequences;
        this.prohibitedNTermSequences = prohibitedNTermSequences;
    }

    public List<String> getCutResidues() {
        return cutResidues;
    }

    public boolean isCutAfter() {
        return cutAfter;
    }

    public List<String> getRequiredCTermSequences() {
        return requiredCTermSequences;
    }

    public List<String> getRequiredNTermSequences() {
        return requiredNTermSequences;
    }

    public List<String> getProhibitedCTermSequences() {
        return prohibitedCTermSequences;
    }

    public List<String> getProhibitedNTermSequences() {
        return prohibitedNTermSequences;
    }

    private List<String> cutResidues;
    private boolean cutAfter;
    private List<String> requiredCTermSequences;
    private List<String> requiredNTermSequences;
    private List<String> prohibitedCTermSequences;
    private List<String> prohibitedNTermSequences;

}
