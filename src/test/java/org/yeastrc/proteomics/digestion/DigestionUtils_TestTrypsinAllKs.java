package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestTrypsinAllKs {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();
    }


    @Test
    public void testAllKs() {

        Collection<DigestionProduct> products = new HashSet<>();

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( true );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 3 );


            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }
        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }


        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 2 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 2 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 2 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 3 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 3 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 4 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }


        assertEquals( products, DigestionUtils.digestProteinSequence( "KKKK", _TRYPSIN, _DIGESTION_PARAMETERS ) );
    }

}