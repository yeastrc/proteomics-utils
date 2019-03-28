package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestTrypsin_WithParametersZeroMissedCleavages {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();

        _DIGESTION_PARAMETERS.setMaxPeptideLength( 4 );
        _DIGESTION_PARAMETERS.setMinPeptideLength( 2 );

        _DIGESTION_PARAMETERS.setNumMissedCleavages( 0 );
    }


    @Test
    public void test1() {

        Collection<DigestionProduct> products = new HashSet<>();

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 11 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        // ETLILKPIKRLIRL
        // 12345678901234

        assertEquals( products, DigestionUtils.digestProteinSequence( "ETLILKPIKRLIRL", _TRYPSIN, _DIGESTION_PARAMETERS ) );
    }

    @Test
    public void test2() {

        Collection<DigestionProduct> products = new HashSet<>();


        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 7 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 11 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        // ETLILK LIK R LIR L
        // 123456 789 0 123 4

        assertEquals( products, DigestionUtils.digestProteinSequence( "ETLILKLIKRLIRL", _TRYPSIN, _DIGESTION_PARAMETERS ) );
    }

}