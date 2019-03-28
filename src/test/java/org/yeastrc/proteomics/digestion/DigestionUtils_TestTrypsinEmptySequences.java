package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestTrypsinEmptySequences {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();
    }


    @Test
    public void testEmptySequence() {

        assertEquals( 0, DigestionUtils.digestProteinSequence( "", _TRYPSIN, _DIGESTION_PARAMETERS ).size() );

    }

    @Test
    public void testNullSequence() {

        try {
            DigestionUtils.digestProteinSequence(null, _TRYPSIN, _DIGESTION_PARAMETERS);
            fail();
        } catch( NullPointerException e ) { ; }

    }
}