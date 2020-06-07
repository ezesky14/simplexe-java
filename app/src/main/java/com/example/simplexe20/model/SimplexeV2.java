package com.example.simplexe20.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplexeV2 {
    private ProgrameLineaire programeLineaire;
    private ArrayList<TableauV2> tableauList;
    private final boolean LOG_ENABLE = true;
    private Interpretation interpretation;
    private final SimplexeLogger simplexeLogger = new SimplexeLogger();

      /* * - Les fonctions a creer
       Les fonctions serviront à alleger le code , eviter les repetitions et permmettrons une bonne transparence de l'algorithme
       Pour cela nous creerons plusieurs fonctions telles que :
         - recupLignePivot(): Recupere les elements du tableau ou se trouve le pivot et les insere dans un tableau
         - calculerLigne() :Applique la formule du pivot de GAUSS L'=L  - k * Lp / P  tels que P = Pivot  , L p= Ligne pivot
         - determinerPivot() : Cette fonction determinera la variable entrante VE , la variable sortante VS et le pivot puis affectera la ligne pivot au tableau prevu à cet effet .
         - ApliquerReglePivot() : fera les calculs pour appliquer les regles de pivotage pour le tableau suivant .
         - verifierTableau() : Verifiera que le tableau Z contient seulement que des valeurs negatives ou nuls
         - InterpreterSolutionOptimale() : Interpretera la solution optimale si seulement si les coefficients des variables hors base sont negatives ou nuls.
       */

    public SimplexeV2(ProgrameLineaire programeLineaire) {
        this.programeLineaire = programeLineaire;
        initFirstTable();
    }

    public ProgrameLineaire getProgrameLineaire() {
        return programeLineaire;
    }

    public void setProgrameLineaire(ProgrameLineaire programeLineaire) {
        this.programeLineaire = programeLineaire;
    }

    public ArrayList<TableauV2> getTableauList() {
        return tableauList;
    }

    public void setTableauList(ArrayList<TableauV2> tableauList) {
        this.tableauList = tableauList;
    }

    public Interpretation getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(Interpretation interpretation) {
        this.interpretation = interpretation;
    }

    private void initFirstTable() {
        tableauList = new ArrayList();
        TableauV2 firstTb = new TableauV2(getProgrameLineaire());
        tableauList.add(firstTb);
        if (LOG_ENABLE) {
            simplexeLogger.logTable(firstTb, "1er tableau sans R");
        }
    }

    public ColonneTableau max(List<ColonneTableau> cols) {
        ColonneTableau maxCol = new ColonneTableau();
        float max = 0;
        int i = 0;
        for (ColonneTableau colonne : cols) {
            if (colonne.getValue() > max) {
                max = colonne.getValue();
                colonne.setLignePosition(i);
                maxCol = colonne;
            }
            i++;
        }
        return maxCol;
    }


    public ColonneTableau min(List<ColonneTableau> cols) {
        ColonneTableau minCol = new ColonneTableau(programeLineaire.getRoundNumber());
        float min = 0;
        for (int i = 0; i < cols.size(); i++) {
            ColonneTableau colonne = cols.get(i);
            if (colonne.getValue() > 0) {
                if (min == 0) {
                    min = colonne.getValue();
                    colonne.setLignePosition(i);
                    minCol = colonne;
                } else {
                    if (colonne.getValue() < min) {
                        min = colonne.getValue();
                        colonne.setLignePosition(i);
                        minCol = colonne;
                    }
                }

            }
        }
        Log.i("[min] min ", min + "");
        return minCol;
    }

    private TableauV2 calculerR(TableauV2 tb, ColonneTableau colVEntrante) {
        /*On calcule pour e1, e2 et e3

         */
        int i = programeLineaire.getColumnsNumberPerLine() - 1;
        for (int a = 0; a < 3; a++) {
            if (colVEntrante.getValue() == 0) {
                tb.getCol(a, i).setValue(00000);
            } else {
                float bb = tb.getCol(a, colVEntrante.getVhbPosition()).getValue();
                float f = tb.getCol(a, (i - 1)).getValue() / bb;
                tb.getCol(a, i).setValue(f);
            }
        }
        return tb;
    }

    @SuppressWarnings("unchecked")
    private ColonneTableau getVsortante(TableauV2 tb, int rIndice) {
        Map<Integer, Integer> colQueriesMap = new HashMap();
        colQueriesMap.put(0, rIndice);
        colQueriesMap.put(1, rIndice);
        colQueriesMap.put(2, rIndice);

        ArrayList<ColonneTableau> colonnes = tb.getColonnes(colQueriesMap);
        return min(colonnes);
    }

    public TableauV2 determinerPivot(TableauV2 tb) {
        //recherche de la variable entrante
        // float max = max(tb_Z[0], tb_Z[1], tb_Z[2], tb_Z[3], tb_Z[4], tb_Z[5]);
        ArrayList<ColonneTableau> cols = tb.getColonnes();
        ColonneTableau colV_entrant = max(tb.getLine("Z"));
        tb.setColvEntrante(colV_entrant);
        tb.setPositionVEntrante(colV_entrant.getPositionValue());
        tb.setMax(colV_entrant.getValue());
        //On calcule pour e1,e2 et e3 et on met a jour les valeurs des colonnes dans le tableau
        tb.setRealPosZ(colV_entrant.getId());
        tb = calculerR(tb, colV_entrant);
        tb.setColonnes(cols);

        if (LOG_ENABLE) {
            simplexeLogger.logTable(tb, "tableau " + tableauList.size() + " actualiser");
        }

        //RECHERCHE DE LA VALEURE SORTANTE ET DU PIVOT PUIS REMPLISSAGE DU TABLEAU ligne pivot
        int rIndice = programeLineaire.getColumnsNumberPerLine() - 1;
        ColonneTableau colVSortante = getVsortante(tb, rIndice);
        tb.setColvSortante(colVSortante);
        tb.setMin(colVSortante.getValue());
        String vdbIndice = colVSortante.getVdbValue();
        tb.setPositionVSortante("r" + vdbIndice);

        tb.setPositionPivot(vdbIndice + colV_entrant.getVhbValue());
        tb.setPivot(tb.getValue(vdbIndice, colV_entrant.getVhbPosition()));
        tb.setPivotColonnes(tb.getLine(vdbIndice));
        tb.setIndice(vdbIndice);
        tb.setColPivot(tb.getCol(vdbIndice, colV_entrant.getVhbPosition()));

        return tb;
    }

    public TableauV2 calculateNextTableLine(TableauV2 actualTb) {
        float pivot = actualTb.getPivot();
        float[] lignePivot = actualTb.getLignePivotValues();
        int indiceVEntranteVhbPos = actualTb.getColvEntrante().getVhbPosition();
        String pivotVdbPos = actualTb.getIndice();

        //Le nombre de colonnes peut varier 7 ou 8
        int colNumberPerLine = programeLineaire.getColumnsNumberPerLine();

        TableauV2 nextTb = new TableauV2(programeLineaire);
        List<ColonneTableau> tb_e1prim = nextTb.getLine("e1");
        List<ColonneTableau> tb_e2prim = nextTb.getLine("e2");
        List<ColonneTableau> tb_e3prim = nextTb.getLine("e3");
        List<ColonneTableau> tb_Zprim = nextTb.getLine("Z");

        float[] tb_e1 = actualTb.getLineValues("e1");
        float[] tb_e2 = actualTb.getLineValues("e2");
        float[] tb_e3 = actualTb.getLineValues("e3");
        float[] tb_Z = actualTb.getLineValues("Z");


        for (int i = 0; i < colNumberPerLine - 1; i++) {
            tb_Zprim.get(i).setValue(tb_Z[i] - (tb_Z[indiceVEntranteVhbPos] * lignePivot[i] / pivot));

            if ("e1".equals(pivotVdbPos)) {
                tb_e1prim.get(i).setValue(lignePivot[i] / pivot);
            } else {
                tb_e1prim.get(i).setValue(tb_e1[i] - (tb_e1[indiceVEntranteVhbPos] * lignePivot[i] / pivot));
            }

            if ("e2".equals(pivotVdbPos)) {
                tb_e2prim.get(i).setValue(lignePivot[i] / pivot);
            } else {
                tb_e2prim.get(i).setValue(tb_e2[i] - (tb_e2[indiceVEntranteVhbPos] * lignePivot[i] / pivot));
            }

            if ("e3".equals(pivotVdbPos)) {
                tb_e3prim.get(i).setValue(lignePivot[i] / pivot);
            } else {
                tb_e3prim.get(i).setValue(tb_e3[i] - (tb_e3[indiceVEntranteVhbPos] * lignePivot[i] / pivot));
            }
        }

        nextTb.setLine(tb_e1prim, "e1");
        nextTb.setLine(tb_e2prim, "e2");
        nextTb.setLine(tb_e3prim, "e3");
        nextTb.setLine(tb_Zprim, "Z");

        // On retourne le prochain tableau
        if (LOG_ENABLE) {
            simplexeLogger.logTable(nextTb, "prochain tableau sans R");
        }
        return nextTb;
    }

    private TableauV2 genererProchainTableau(TableauV2 tb) {
        return calculateNextTableLine(tb);
    }

    public ArrayList<TableauV2> genTables() {
        // On defini le premier tableau
        TableauV2 currentTb = tableauList.get(0);

        // On initialise les variables
        boolean isLast = false;
        int i = 0;
        while (!isLast) {
            currentTb = determinerPivot(currentTb);

            //On met a jour le tableau dans la liste
            tableauList.set(i, currentTb);

            //On genere le prochain tableau
            currentTb = genererProchainTableau(currentTb);

            TableauV2 previousTb = tableauList.get(i);
            currentTb = applyReglePivot(currentTb, previousTb);

            currentTb.verifyTable();
            isLast = currentTb.isLast();
            tableauList.add(currentTb);
            i++;
        }
        programeLineaire.setNbTableau(tableauList.size());
        // Interpretation
        interpretation = interpreteSolu();
        return tableauList;
    }

    public TableauV2 applyReglePivot(TableauV2 actualTb, TableauV2 previousTb) {
        /* Vdb : e1=> 0 , e2=> 1, e3 =>2
        Vhb : (x1=> 0 , x2=> 1 , "." => 2 , 3 et 4)  ou  (x1=> 0 , x2=> 1 , x3=> 2 ,"." => 3 , 4 et 5)
        */
        actualTb.setVdbLabels(previousTb.getVdbLabels().clone());
        actualTb.setVhbLabels(previousTb.getVhbLabels().clone());

        String[] actualVhbLabels = actualTb.getVhbLabels().clone();
        String[] actualVdbLabels = actualTb.getVdbLabels().clone();

        ColonneTableau colPivot = previousTb.getColPivot();
        int colPerLine = actualTb.getNumberTotalcolPerLine();

        int vhbPosition = colPivot.getVhbPosition();
        int vdbPosition = colPivot.getVdbPosition();

        if (previousTb.isPValue(colPivot.getVhbValue())) {
            int indexP = vhbPosition - 3;
            if (colPerLine == 7) {
                indexP = vhbPosition - 2;
            }

            String tmp2 = actualVhbLabels[vhbPosition];
            actualVhbLabels[vhbPosition] = actualVhbLabels[indexP];
            actualVhbLabels[indexP] = tmp2;

            String tmp1 = actualVdbLabels[vdbPosition];
            actualVdbLabels[vdbPosition] = actualVhbLabels[indexP];
            actualVhbLabels[indexP] = tmp1;

        } else {
            String tmp1 = actualVdbLabels[vdbPosition];
            actualVdbLabels[vdbPosition] = actualVhbLabels[vhbPosition];
            actualVhbLabels[vhbPosition] = tmp1;

            int indexP = vdbPosition + 3;
            if (colPerLine == 7) {
                indexP = vdbPosition + 2;
            }
            String tmp2 = actualVhbLabels[indexP];
            actualVhbLabels[indexP] = actualVhbLabels[vhbPosition];
            actualVhbLabels[vhbPosition] = tmp2;
        }

        actualTb.setVdbLabels(actualVdbLabels);
        actualTb.setVhbLabels(actualVhbLabels);
        return actualTb;
    }

    public Interpretation interpreteSolu() {
        SimplexeType simplexeType = programeLineaire.getType();
        TableauV2 lastTb = tableauList.get(tableauList.size() - 1);
        Interpretation interpretation = new Interpretation();
            /*
             1 - On recherche dans les labels les valeurs x1 , x2 et x3
                    1.1 Dans Vdb si non trouver on recherche dans vhb
                    1.2 Si trouver dans vdb alors
                        1.2.1 On recupere l'index qui represente la ligne,
                              on recherche la valeur B a cette ligne .
                              Cette valeur est liee a la variable d activite recherchee plus haut

                         Si trouver dans vhb alors
                           1.2.1 On recupere l'index qui represente la ligne,
                              on recherche la valeur Z a cette ligne (Zx1 ou Zx2 ou Zx3).
                              Cette valeur est liee a la variable d activite recherchee plus haut

             */

        String[] lastVhbLabels = lastTb.getVhbLabels().clone();
        String[] lastVdbLabels = lastTb.getVdbLabels().clone();
        String[] variableActivite;
        if (simplexeType == SimplexeType.MAXI_DEUX_VARIABLES) {
            variableActivite = new String[]{"x1", "x2", "e1", "e2", "e3"};
        } else {
            variableActivite = new String[]{"x1", "x2", "x3", "e1", "e2", "e3"};
        }

        for (String v : variableActivite) {
            int position;
            boolean found = false;
            int index = 0;
            for (String vdbLabel : lastVdbLabels) {
                if (vdbLabel == v) {
                    found = true;
                    position = index;
                    ColonneTableau colonneTableau = lastTb.getCol(position, (lastTb.getNumberTotalcolPerLine() - 2));
                    interpretation.setValue(colonneTableau, v, false);

                    Log.i(v + " found", colonneTableau.getValue() + "");
                }
                index++;
            }

            //Si pas trouver dans vdbLabels on va dans vhbLabels
            if (!found) {
                index = 0;
                for (String vhbLabel : lastVhbLabels) {
                    if (vhbLabel == v) {
                            /* 1- Verifier si cette variable d activite est e1, e2 ou e3
                               2- Si oui verifier si la valeur liee a la variable d'activite trouvee a travers la colonne
                                  est inferieur a 0
                               3- Si inferieur alors cette variable d activite est egale a 0 */

                        position = index;
                        ColonneTableau colonneTableau = lastTb.getCol(3, position);
                        interpretation.setValue(colonneTableau, v, true);

                        if (colonneTableau.getValue() > 0) {
                            Log.i(v + " found", colonneTableau.getValue() + "");
                        } else {
                            Log.i(v + " found", "0");
                        }

                    }
                    index++;
                }
            }
        }
        interpretation.setZMAX((lastTb.getValue("Z", 6) * -1) + "");
        return interpretation;
    }
}
