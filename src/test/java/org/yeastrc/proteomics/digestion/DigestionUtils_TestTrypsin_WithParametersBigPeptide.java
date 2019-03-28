package org.yeastrc.proteomics.digestion;

import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.digestion.protease.proteases.IProtease;
import org.yeastrc.proteomics.digestion.protease.ProteaseFactory;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class DigestionUtils_TestTrypsin_WithParametersBigPeptide {

    private IProtease _TRYPSIN;
    private DigestionParameters _DIGESTION_PARAMETERS;

    @Before
    public void setUp() {

        _TRYPSIN = ProteaseFactory.getProteaseByName("trypsin");
        _DIGESTION_PARAMETERS = new DigestionParameters();

        _DIGESTION_PARAMETERS.setNumMissedCleavages( 3 );
    }


    @Test
    public void test() {

        Collection<DigestionProduct> products = new HashSet<>();

        {   // 1107.6884
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 9 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {   // 1812.0741
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 15 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {   // 2166.3372
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 18 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {   // 2322.4383
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( true );
            dp.setCTerminal( false );
            dp.setProteinPosition( 1 );
            dp.setPeptideLength( 19 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {   // 723.4035
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 10 );
            dp.setPeptideLength( 6 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {   // 1077.6666
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 10 );
            dp.setPeptideLength( 9 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {   // 1233.7677
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 10 );
            dp.setPeptideLength( 10 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {   // 1616.0370
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 10 );
            dp.setPeptideLength( 13 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {   // 373.2809
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {   // 529.3820
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {   // 911.6512
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 7 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {   // 1024.7353
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 16 );
            dp.setPeptideLength( 8 );
            dp.setMissedCleavages( 3 );

            products.add( dp );
        }

        {   // 175.1189
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 19 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {   // 557.3882
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 19 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {   // 670.4722
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 19 );
            dp.setPeptideLength( 5 );
            dp.setMissedCleavages( 2 );

            products.add( dp );
        }

        {   // 401.2871
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( false );
            dp.setProteinPosition( 20 );
            dp.setPeptideLength( 3 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        {   // 514.3711
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 20 );
            dp.setPeptideLength( 4 );
            dp.setMissedCleavages( 1 );

            products.add( dp );
        }

        {   // 132.1019
            DigestionProduct dp = new DigestionProduct();
            dp.setNTerminal( false );
            dp.setCTerminal( true );
            dp.setProteinPosition( 23 );
            dp.setPeptideLength( 1 );
            dp.setMissedCleavages( 0 );

            products.add( dp );
        }

        assertEquals( products, DigestionUtils.digestProteinSequence( "ETLILHILRKPYTSKLIKRLIRL", _TRYPSIN, _DIGESTION_PARAMETERS ) );
    }

}