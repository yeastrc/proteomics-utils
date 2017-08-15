package org.yeastrc.proteomics.peptide.atom;

import java.util.Objects;

public abstract class Atom {

	public abstract double getMass( int massType ) throws Exception;
	
	public abstract String getSymbol();
	
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Atom)) {
            return false;
        }
        Atom atom = (Atom) o;
        return this.getSymbol().equals( atom.getSymbol() );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSymbol());
    }
	
}
