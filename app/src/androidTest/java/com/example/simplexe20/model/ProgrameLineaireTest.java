package com.example.simplexe20.model;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProgrameLineaireTest {
    private ProgrameLineaire programeLineaire;

    @Before
    public void setUp() {
        maxi2v();
    }

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

    @After
    public void tearDown() throws Exception {
    }

    private String[] getColListMock() {
        String[] expectedList = new String[]{
                "2", "0.5", "2", "280",
                "0", "1", "4", "400",
                "2", "3", "0", "600",
                "350", "280", "300"
        };

        return expectedList;
    }

    private String[] getColListMock2V() {
        String[] expectedList = new String[]{
                "2", "2", "500",
                "6", "4", "1200",
                "10", "2", "1800",
                "2500", "2000"
        };

        return expectedList;
    }

    @Test
    public void testMaxi2vValuesResult() {
        programeLineaire.setColList();
        List<ColonneTableau> colonneTableaus = programeLineaire.getColList();
        String[] expectedList = getColListMock2V();
        //Verifier que le nombre de colonne est egale a 11
        assertEquals("Verifier que le nombre de colonne est egale a 11", colonneTableaus.size(), 11);

        //Verifier que les valeurs concordent bien
        int i = 0;
        for (String value : expectedList) {
            assertEquals("Verifier que les valeurs concordent bien pour " + value, String.valueOf(colonneTableaus.get(i).getValue()), String.valueOf(Float.parseFloat(value)));
            i++;
        }
        SimplexeV2 simplexeV2 = new SimplexeV2(programeLineaire);
        ArrayList<TableauV2> tableauList = simplexeV2.genTables();
        programeLineaire.setNbTableau(tableauList.size());
    }


    @Test
    public void setColList() {
        programeLineaire.setColList();
        List<ColonneTableau> colonneTableaus = programeLineaire.getColList();
        String[] expectedList = getColListMock();
        //Verifier que le nombre de colonne est egale a 15
        assertEquals("Verifier que le nombre de colonne est egale a 15", colonneTableaus.size(), 15);

        //Verifier que les valeurs concordent bien
        int i = 0;
        for (String value : expectedList) {
            assertEquals("Verifier que les valeurs concordent bien pour " + value, String.valueOf(colonneTableaus.get(i).getValue()), String.valueOf(Float.parseFloat(value)));
            i++;
        }

    }
}