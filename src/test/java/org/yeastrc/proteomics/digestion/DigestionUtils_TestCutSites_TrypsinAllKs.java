package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestCutSites_TrypsinAllKs {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();
    }


    @Test
    public void test() {

        Collection<DigestionProduct> products = new HashSet<>();

        ArrayList list = new ArrayList( 2 );
        list.add( 1 );
        list.add( 2 );
        list.add( 3 );

        assertEquals( list, DigestionUtils.getSortedCutSitesInProtein( "KKKK", _TRYPSIN ) );
    }

}