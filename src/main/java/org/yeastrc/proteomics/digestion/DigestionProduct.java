package org.yeastrc.proteomics.digestion;

import java.util.Objects;

public class DigestionProduct {
    @Override
    public String toString() {
        return "DigestionProduct{" +
                "proteinPosition=" + proteinPosition +
                ", peptideLength=" + peptideLength +
                ", isNTerminal=" + isNTerminal +
                ", isCTerminal=" + isCTerminal +
                ", missedCleavages=" + missedCleavages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigestionProduct that = (DigestionProduct) o;
        return proteinPosition == that.proteinPosition &&
                peptideLength == that.peptideLength &&
                isNTerminal == that.isNTerminal &&
                isCTerminal == that.isCTerminal &&
                missedCleavages == that.missedCleavages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteinPosition, peptideLength, isNTerminal, isCTerminal, missedCleavages);
    }

    public int getMissedCleavages() {
        return missedCleavages;
    }

    public void setMissedCleavages(int missedCleavages) {
        this.missedCleavages = missedCleavages;
    }

    public boolean isNTerminal() {
        return isNTerminal;
    }

    public void setNTerminal(boolean NTerminal) {
        isNTerminal = NTerminal;
    }

    public boolean isCTerminal() {
        return isCTerminal;
    }

    public void setCTerminal(boolean CTerminal) {
        isCTerminal = CTerminal;
    }

    /**
     * Position (inclusive) in the supplied protein that this peptide starts. First
     * position is 1.
     *
     * @return
     */
    public int getProteinPosition() {
        return proteinPosition;
    }

    /**
     * Set the position (inclusive) in the supplied protein this peptide starts. First
     * position is 1.
     *
     * @param proteinPosition
     */
    public void setProteinPosition(int proteinPosition) {
        this.proteinPosition = proteinPosition;
    }

    /**
     * The length of this peptide.
     *
     * @return
     */
    public int getPeptideLength() {
        return peptideLength;
    }

    /**
     * The length of this peptide.
     *
     * @param peptideLength
     */
    public void setPeptideLength(int peptideLength) {
        this.peptideLength = peptideLength;
    }

    private int proteinPosition;
    private int peptideLength;
    private boolean isNTerminal;
    private boolean isCTerminal;
    private int missedCleavages;

}
