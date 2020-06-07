package com.example.simplexe20.model;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableauV2Test {
    private ProgrameLineaire programeLineaire;


    private void maxi3v() {
        programeLineaire = new ProgrameLineaire(SimplexeType.MAXI_TROIS_VARIABLES);
        programeLineaire.setEquation1(
                Float.parseFloat("2"),
                Float.parseFloat("0.5"),
                Float.parseFloat("2"),
                Float.parseFloat("280")
        );
        programeLineaire.setEquation2(
                Float.parseFloat("0"),
                Float.parseFloat("1"),
                Float.parseFloat("4"),
                Float.parseFloat("400")
        );
        programeLineaire.setEquation3(
                Float.parseFloat("2"),
                Float.parseFloat("3"),
                Float.parseFloat("0"),
                Float.parseFloat("600")
        );
        programeLineaire.setzEquation(
                Float.parseFloat("350"),
                Float.parseFloat("280"),
                Float.parseFloat("300")
        );
    }

    private void maxi2v() {
        programeLineaire = new ProgrameLineaire(SimplexeType.MAXI_DEUX_VARIABLES);
        programeLineaire.setEquation1(
                Float.parseFloat("2"),
                Float.parseFloat("2"),
                0,
                Float.parseFloat("500")
        );
        programeLineaire.setEquation2(
                Float.parseFloat("6"),
                Float.parseFloat("4"),
                0,
                Float.parseFloat("1200")
        );
        programeLineaire.setEquation3(
                Float.parseFloat("10"),
                Float.parseFloat("2"),
                0,
                Float.parseFloat("1800")
        );
        programeLineaire.setzEquation(
                Float.parseFloat("2500"),
                Float.parseFloat("2000"),
                0
        );
    }

    @Before
    public void setUp() {
        maxi2v();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPivotColonnes() {
    }

    @Test
    public void getLignePivotValues() {
    }

    @Test
    public void setPivotColonnes() {
    }

    @Test
    public void getColonnes() {
    }

    @Test
    public void setColonnes() {
    }

    @Test
    public void getLine() {
        programeLineaire.setColList();
        SimplexeV2 simplexeV2 = new SimplexeV2(programeLineaire);
        TableauV2 firstTb = simplexeV2.getTableauList().get(0);

        //S'assurer qu il donne les valeurs pour chaque ligne sans erreur
        List<ColonneTableau> e1Vals = firstTb.getLine("Z");
        float[] e1ValsPrime = firstTb.getLineValues("Z");
        String[] expectedVals = {"2500", "2000", "0", "0", "0", "0", "0"};
        int a = 0;
        for (ColonneTableau col : e1Vals) {
            Log.i("col " + (a + 1), String.valueOf(col.getPositionValue()));
            assertEquals("", String.valueOf(Float.parseFloat(expectedVals[a])),
                    String.valueOf(col.getValue()));
            a++;

        }

        a = 0;
        for (float v : e1ValsPrime) {
            Log.i("col " + (a + 1), String.valueOf(v));
            assertEquals("", String.valueOf(Float.parseFloat(expectedVals[a])),
                    String.valueOf(v));
            a++;

        }


    }


    @Test
    public void getLine2() {
        TableauV2 firstTb = new TableauV2(programeLineaire);

        //S'assurer qu il donne les valeurs pour chaque ligne sans erreur
        List<ColonneTableau> e1Vals = firstTb.getLine("e1");
        List<ColonneTableau> e2Vals = firstTb.getLine("e2");
        List<ColonneTableau> e3Vals = firstTb.getLine("e3");
        List<ColonneTableau> ZVals = firstTb.getLine("Z");
        float[] e1ValsPrime = firstTb.getLineValues("Z");
        String[] expectedVals = {"Zx1", "Zx2", "Zp1", "Zp2", "Zp3", "ZB", "ZR"};
        int a = 0;
        for (ColonneTableau col : ZVals) {
            Log.i("col " + (a + 1), col.getPositionValue());
            assertEquals("", expectedVals[a],
                    col.getPositionValue());
            a++;

        }
    }

    @Test
    public void getLineValues() {

    }
}