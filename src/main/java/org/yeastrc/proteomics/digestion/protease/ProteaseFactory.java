package org.yeastrc.proteomics.digestion.protease;

import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.proteases.Thermolysin;
import org.yeastrc.proteomics.digestion.protease.proteases.Trypsin;

import java.util.HashMap;
import java.util.Map;

public class ProteaseFactory {

    private static final IProtease PROTEASE_TRYPSIN = Trypsin.getInstance();
    private static final IProtease PROTEASE_THERMOLYSIN = Thermolysin.getInstance();

    private static final Map<String, IProtease> ProteaseLookupStrings;

    static {

        ProteaseLookupStrings = new HashMap<>();

        ProteaseLookupStrings.put( "trypsin", PROTEASE_TRYPSIN);
        ProteaseLookupStrings.put( "thermolysin", PROTEASE_THERMOLYSIN);
    }

    public static IProtease getProteaseByName( String name ) {

        if( ProteaseLookupStrings.containsKey( name ) ) {
            return ProteaseLookupStrings.get( name );
        }

        return null;
    }

}

