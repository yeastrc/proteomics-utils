package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestCutSites_TrypsinWithExclusions {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;
    private Collection<Integer> _EXCLUDED_POSITIONS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();

        _EXCLUDED_POSITIONS = new HashSet<>();
        _EXCLUDED_POSITIONS.add( 3 );
        _EXCLUDED_POSITIONS.add( 12 );
    }


    @Test
    public void test() {

        Collection<DigestionProduct> products = new HashSet<>();

        ArrayList list = new ArrayList( 2 );
        list.add( 4 );
        list.add( 13 );

        assertEquals( list, DigestionUtils.getSortedCutSitesInProtein( "ETKRILRPYLIKRLI", _TRYPSIN, _EXCLUDED_POSITIONS ) );
    }

}