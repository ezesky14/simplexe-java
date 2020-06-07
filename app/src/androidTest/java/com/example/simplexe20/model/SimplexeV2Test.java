package com.example.simplexe20.model;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimplexeV2Test {
    private SimplexeV2 simplexe3VMaxi;
    private SimplexeV2 simplexe2VMaxi;

    @Before
    public void setUp() {
        maxi3v();
        maxi2v();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void calculateNextTableLine() {
    }

    @Test
    public void max() {
    }

    @Test
    public void min() {
    }

    @Test
    public void determinerPivot() {
    }

    @Test
    public void genTables() {
    }


    private void maxi3v() {
        ProgrameLineaire programeLineaire3VMaxi = new ProgrameLineaire(SimplexeType.MAXI_TROIS_VARIABLES);
        programeLineaire3VMaxi.setEquation1(
                Float.parseFloat("2"),
                Float.parseFloat("0.5"),
                Float.parseFloat("2"),
                Float.parseFloat("280")
        );
        programeLineaire3VMaxi.setEquation2(
                Float.parseFloat("0"),
                Float.parseFloat("1"),
                Float.parseFloat("4"),
                Float.parseFloat("400")
        );
        programeLineaire3VMaxi.setEquation3(
                Float.parseFloat("2"),
                Float.parseFloat("3"),
                Float.parseFloat("0"),
                Float.parseFloat("600")
        );
        programeLineaire3VMaxi.setzEquation(
                Float.parseFloat("350"),
                Float.parseFloat("280"),
                Float.parseFloat("300")
        );

        programeLineaire3VMaxi.setColList();
        simplexe3VMaxi = new SimplexeV2(programeLineaire3VMaxi);
        ArrayList<TableauV2> tableauList = simplexe3VMaxi.genTables();
        programeLineaire3VMaxi.setNbTableau(tableauList.size());
    }

    private void maxi2v() {
        ProgrameLineaire programeLineaire2VMaxi = new ProgrameLineaire(SimplexeType.MAXI_DEUX_VARIABLES);
        programeLineaire2VMaxi.setEquation1(
                Float.parseFloat("2"),
                Float.parseFloat("2"),
                0,
                Float.parseFloat("500")
        );
        programeLineaire2VMaxi.setEquation2(
                Float.parseFloat("6"),
                Float.parseFloat("4"),
                0,
                Float.parseFloat("1200")
        );
        programeLineaire2VMaxi.setEquation3(
                Float.parseFloat("10"),
                Float.parseFloat("2"),
                0,
                Float.parseFloat("1800")
        );
        programeLineaire2VMaxi.setzEquation(
                Float.parseFloat("2500"),
                Float.parseFloat("2000"),
                0
        );

        programeLineaire2VMaxi.setColList();
        simplexe2VMaxi = new SimplexeV2(programeLineaire2VMaxi);
        ArrayList<TableauV2> tableauList = simplexe2VMaxi.genTables();
        programeLineaire2VMaxi.setNbTableau(tableauList.size());
    }

    @Test
    public void applyReglePivot3vMaxi() {
        int position = 1;
        TableauV2 tb = simplexe3VMaxi.getTableauList().get(simplexe3VMaxi.getTableauList().size() - 1);
        String[] vhbLabels = tb.getVhbLabels();
        String[] vdbLabels = tb.getVdbLabels();

        assertArrayEquals("", new String[]{"x1", "e2", "e3"}, vdbLabels);
        assertArrayEquals("",
                new String[]{".", "x2", "x3", "e1", ".", "."}, vhbLabels);

    }


    @Test
    public void applyReglePivot2vMaxi() {
        int position = 1;
        TableauV2 tb = simplexe2VMaxi.getTableauList().get(simplexe2VMaxi.getTableauList().size() - 1);
        String[] vhbLabels = tb.getVhbLabels();
        String[] vdbLabels = tb.getVdbLabels();

        assertArrayEquals("", new String[]{"e3", "x2", "x1"}, vdbLabels);
        assertArrayEquals("",
                new String[]{".", ".", "e1", "e2", "."}, vhbLabels);

    }

    @Test
    public void interpreteSolu3vMaxi() {
        simplexe3VMaxi.interpreteSolu();
        assertEquals(true, true);
    }

    @Test
    public void interpreteSolu2vMaxi() {
        simplexe2VMaxi.interpreteSolu();
        assertEquals(true, true);
    }
}