package org.yeastrc.proteomics.digestion;

public class DigestionParameters {

    public Integer getNumMissedCleavages() {
        return numMissedCleavages;
    }

    public void setNumMissedCleavages(Integer numMissedCleavages) {
        this.numMissedCleavages = numMissedCleavages;
    }

    public Double getMaxPeptideMass() {
        return maxPeptideMass;
    }

    public void setMaxPeptideMass(Double maxPeptideMass) {
        this.maxPeptideMass = maxPeptideMass;
    }

    public Double getMinPeptideMass() {
        return minPeptideMass;
    }

    public void setMinPeptideMass(Double minPeptideMass) {
        this.minPeptideMass = minPeptideMass;
    }

    public Integer getMaxPeptideLength() {
        return maxPeptideLength;
    }

    public void setMaxPeptideLength(Integer maxPeptideLength) {
        this.maxPeptideLength = maxPeptideLength;
    }

    public Integer getMinPeptideLength() {
        return minPeptideLength;
    }

    public void setMinPeptideLength(Integer minPeptideLength) {
        this.minPeptideLength = minPeptideLength;
    }

    private Integer numMissedCleavages;
    private Double maxPeptideMass;
    private Double minPeptideMass;
    private Integer maxPeptideLength;
    private Integer minPeptideLength;

}
