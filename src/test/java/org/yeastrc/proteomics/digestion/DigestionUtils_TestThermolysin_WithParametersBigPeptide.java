package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestThermolysin_WithParametersBigPeptide {

    private IProtease _PROTEASE;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _PROTEASE = ProteaseFactory.getProteaseByName("thermolysin");
        _DIGESTION_PARAMETERS = new DigestionParameters();

        _DIGESTION_PARAMETERS.setNumMissedCleavages( 3 );
    }


    @Test
    public void test() {

        Collection<DigestionProduct> products = new HashSet<>();
        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 6 );
            dp.setMissedCleavages( 3 );

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
            dp.setCTerminal( false );
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
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 3 );
            dp.setPeptideLength( 5 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 4 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 4 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 4 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 4 );
            dp.setPeptideLength( 12 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 5 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 5 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 5 );
            dp.setPeptideLength( 11 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 5 );
            dp.setPeptideLength( 12 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 7 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 7 );
            dp.setPeptideLength( 9 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 7 );
            dp.setPeptideLength( 10 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 7 );
            dp.setPeptideLength( 13 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 8 );
            dp.setPeptideLength( 8 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 8 );
            dp.setPeptideLength( 9 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 8 );
            dp.setPeptideLength( 12 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 8 );
            dp.setPeptideLength( 13 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 5 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 7 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 17 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 17 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 17 );
            dp.setPeptideLength( 6 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 17 );
            dp.setPeptideLength( 7 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 20 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 20 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 20 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 21 );
            dp.setPeptideLength( 2 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 21 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 23 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        assertEquals( products, DigestionUtils.digestProteinSequence( "ETLILHILRKPYTSKLIKRLIRL", _PROTEASE, _DIGESTION_PARAMETERS ) );
    }

}