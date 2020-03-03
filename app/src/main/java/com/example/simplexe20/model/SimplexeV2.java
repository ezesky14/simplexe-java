package com.example.simplexe20.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SimplexeV2 {
    ProgrameLineaire programeLineaire;
   public TableauList tableauList;
    private final boolean LOG_ENABLE = true;
    SimplexeLogger simplexeLogger = new SimplexeLogger();

      /* * - Les fonctions a creer
       Les fonctions serviront à alleger le code , eviter les repetitions et permmettons une bonne transparence de l'algorithme
       Pour cela nous creerons plusieurs fonctions telles que :
         - recupLignePivot(): Recupere les elements du tableau ou se trouve le pivot et les insere dans un tableau
         - calculerLigne() :Applique la formule du pivot de GAUSS L'=L  - k * Lp / P  tels que P =Pivot  , Lp=Ligne pivot
         - determinerPivot() : Cette fonction determinera la variable entrante VE , la variable sortante VS et le pivot puis affectera la ligne pivot au tableau prevu à cet effet .
         - ApliquerReglePivot() : fera les calculs pour appliquer les regles de pivotage pour le tableau suivant .
         - verifierTableau() : Verifiera que le tableau Z contient seulement que des valeurs negatives ou nuls
         - InterpreterSolutionOptimale() : Interpretera la solution optimale si seulement si les coefficients des variables hors base sont negatives ou nuls.
       */

    public SimplexeV2(ProgrameLineaire programeLineaire) {
        this.programeLineaire = programeLineaire;
        initFirstTable();
    }

    private void initFirstTable () {
        tableauList = new TableauList();
        TableauV2  firstTb = new TableauV2(programeLineaire.getAllEquationValues(),programeLineaire.getColumnsNumberPerLine());
        tableauList.add(firstTb);
        if(LOG_ENABLE){
            simplexeLogger.logTable(firstTb, "1er tableau sans R");
        }
    }
    public TableauV2 calculLigneProchainTableau(TableauV2 actualTb ){
        float pivot = actualTb.getPivot();
        float [] lignePivot = actualTb.getLignePivotValues();
        int indice = actualTb.getColvEntrante().getVhbPosition();
        String pivotVdbPos = actualTb.getIndice();
        Log.w("pivot ",pivot+"" );
        Log.w("pivot vdb ",pivotVdbPos );

        //Le nombre de colonnes peut varier 7 ou 8
        int colNumberPerLine = programeLineaire.getColumnsNumberPerLine();


        TableauV2 nextTb = new TableauV2();
        nextTb.setNumberTotalcolPerLine(colNumberPerLine);
        List<ColonneTableau> tb_e1prim = nextTb.getLine("e1");
        List<ColonneTableau> tb_e2prim = nextTb.getLine("e2");
        List<ColonneTableau> tb_e3prim = nextTb.getLine("e3");
        List<ColonneTableau> tb_Zprim = nextTb.getLine("Z");


        float[] tb_e1 = actualTb.getLineValues("e1");
        float[] tb_e2 = actualTb.getLineValues("e2");
        float[] tb_e3 = actualTb.getLineValues("e3");
        float[] tb_Z = actualTb.getLineValues("Z");

        if ("e1".equals(pivotVdbPos)){
            for (int i=0;i<colNumberPerLine-1;i++) {
                tb_e1prim.get(i).setValue(lignePivot[i] / pivot);
                tb_e2prim.get(i).setValue(tb_e2[i] - (tb_e2[indice] * lignePivot[i] /pivot));
                tb_e3prim.get(i).setValue(tb_e3[i] - (tb_e3[indice] * lignePivot[i] /pivot));
                tb_Zprim.get(i).setValue(tb_Z[i] - (tb_Z[indice] * lignePivot[i] /pivot));

            }
        }else if ("e2".equals(pivotVdbPos)){
            for (int i=0;i<colNumberPerLine-1;i++) {
                tb_e1prim.get(i).setValue(tb_e1[i] - (tb_e1[indice] * lignePivot[i] /pivot));
                tb_e2prim.get(i).setValue(lignePivot[i] / pivot);
                tb_e3prim.get(i).setValue(tb_e3[i] - (tb_e3[indice] * lignePivot[i] /pivot));
                tb_Zprim.get(i).setValue(tb_Z[i] - (tb_Z[indice] * lignePivot[i] /pivot));

            }
        }else if ("e3".equals(pivotVdbPos)){
            for (int i=0;i<colNumberPerLine-1;i++) {
                tb_e1prim.get(i).setValue(tb_e1[i] - (tb_e1[indice] * lignePivot[i] /pivot));
                tb_e2prim.get(i).setValue(tb_e2[i] - (tb_e2[indice] * lignePivot[i] /pivot));
                tb_e3prim.get(i).setValue(lignePivot[i] / pivot);
                tb_Zprim.get(i).setValue(tb_Z[i] - (tb_Z[indice] * lignePivot[i] /pivot));
            }
        }

        nextTb.setLine(tb_e1prim, "e1");
        nextTb.setLine(tb_e2prim, "e2");
        nextTb.setLine(tb_e3prim, "e3");
        nextTb.setLine(tb_Zprim, "Z");
        // On retourne le prochain tableau
        if(LOG_ENABLE){
            simplexeLogger.logTable(nextTb, "prochain tableau sans R");
        }
        return nextTb;
    }



    public ColonneTableau max(List<ColonneTableau> cols){
        ColonneTableau maxCol = new ColonneTableau();
        float max = 0;
        int i= 0;
        for (ColonneTableau colonne: cols) {
            if (colonne.getValue()>max) {
                max = colonne.getValue();
                colonne.setLignePosition(i);
                maxCol = colonne;
            }
            i++;
        }
        Log.i("Maximum",maxCol.getValue()+"");
        Log.i("Maximum",maxCol.getPositionValue()+"");
        return maxCol;
    }

    public float min(float nb1, float nb2, float nb3, int n) {
        float min = 0;
        if(n==3) {
            min=nb1;
            if (nb2<min){
                min=nb2;
            }
            if (nb3<min){
                min=nb3;
            }
        }

        if(n==2) {
            min=nb1;
            if (nb2<min){min=nb2;}
        }

        return min;
    }



    private TableauV2 calculerR(TableauV2 tb, ColonneTableau colVEntrante) {
        /*On calcule pour e1, e2 et e3

         */
        Log.i("Vhb position" , colVEntrante.getVhbValue()+" - "+ colVEntrante.getVhbPosition()+"");
        int i = programeLineaire.getColumnsNumberPerLine()-1;
        for (int a =0; a<3 ; a++) {
            if (colVEntrante.getValue()== 0) {
                tb.getCol(a, i).setValue(00000);
            } else {
                float bb = tb.getCol(a, colVEntrante.getVhbPosition()).getValue();
                float f = tb.getCol(a, i-1).getValue() / bb;
                tb.getCol(a, i).setValue(f);
            }
        }
        return tb;
    }
    public TableauV2 determinerPivot(TableauV2 tb) {
        int i = programeLineaire.getColumnsNumberPerLine()-1;
        //recherche de la variable entrante
        // float max = max(tb_Z[0], tb_Z[1], tb_Z[2], tb_Z[3], tb_Z[4], tb_Z[5]);
        ArrayList<ColonneTableau> cols = tb.getColonnes();
        ColonneTableau colV_entrant = max(tb.getLine("Z"));
        tb.setMax(colV_entrant.getValue());
        float V_sortant;
        float min;

        //On met a jour dans le tableau la valeur entrante
        tb.setPosition_v_entrante(colV_entrant.getPositionValue());


        //On calcule pour e1,e2 et e3 et on met a jour les valeurs des colonnes dans le tableau
        int realPosZ = colV_entrant.getId();
        Log.w("realPosZ",realPosZ+"");
        tb.setRealPosZ(realPosZ);
        tb = calculerR(tb, colV_entrant);
        tb.setColonnes(cols);

        if (LOG_ENABLE){
            simplexeLogger.logTable(tb,"tableau "+tableauList.size()+" actualiser");
        }


        //algo pour eviter que les nombres negatifs soient inclues dans le calcul de minimum
        if (tb.getVal("e1",i) <= 0) {
            min = min(tb.getVal("e2",i), tb.getVal("e3",i), 0, 2);
            tb.setMin(min);
            V_sortant = min;
            if (tb.getVal("e2",i) <= 0) {
                V_sortant = tb.getVal("e3",i);
            }
            if (tb.getVal("e3",i) <= 0) {
                V_sortant = tb.getVal("e2",i);

            }

        } else {
            min = min(tb.getVal("e1",i), tb.getVal("e2",i), tb.getVal("e3",i), 3);
            tb.setMin(min);
            V_sortant = min;
        }


        if (tb.getVal("e2",i) <= 0) {
            min = min(tb.getVal("e1",i), tb.getVal("e3",i), 0, 2);
            tb.setMin(min);
            V_sortant = min;
            if (tb.getVal("e3",i) <= 0) {
                V_sortant = tb.getVal("e1",i);
            }


        } else {
            min = min(tb.getVal("e1",i), tb.getVal("e2",i), tb.getVal("e3",i), 3);
            tb.setMin(min);
            V_sortant = min;
        }

        if (tb.getVal("e3",i) <= 0) {
            min = min(tb.getVal("e2",i), tb.getVal("e1",i), 0, 2);
            tb.setMin(min);
            V_sortant = min;
        }

        //RECHERCHE DE LA VALEURE SORTANTE ET DU PIVOT PUIS REMPLISSAGE DU TABLEAU ligne pivot
        String vdbIndice = null;
        if (V_sortant == tb.getVal("e1",i)) {
            vdbIndice = "e1";
        }

        if (V_sortant == tb.getVal("e2",i)) {
            vdbIndice = "e2";
        }

        if (V_sortant == tb.getVal("e3",i)) {
            vdbIndice = "e3";
        }

        tb.setPosition_v_sortante("r"+vdbIndice);
        tb.setPosition_pivot(vdbIndice+"x1");
        tb.setPivot(tb.getVal(vdbIndice, colV_entrant.getVhbPosition()));
        tb.setPivotColonnes(tb.getLine(vdbIndice));
        tb.setIndice(vdbIndice);
        tb.setColvEntrante(colV_entrant);
       // tb.setColvSortante();


        Log.w("vdb v entrant",colV_entrant.getVdbPosition()+"");
        Log.w("vdbIndice",vdbIndice);
        Log.w("pivot",tb.getPivot()+"");
        Log.w("pivot",tb.getPosition_pivot()+" - "+tb.getPivot()+"");
        Log.w("v entrant",tb.getPosition_v_entrante());
        Log.w("v_sortant",tb.getPosition_v_sortante());
        Log.w("Ligne pivot"," ");
        return tb;
    }


    private TableauV2 genererProchainTableau(TableauV2  tb) {
       return calculLigneProchainTableau(tb);
    }

    public TableauList genTables() {
        // On defini le premier tableau
        TableauV2  currentTb = tableauList.get(0);

        // On initialise les variables
        boolean isLast = false;
        int i = 0;
        while (!isLast){
            currentTb = determinerPivot(currentTb);
            //On met a jour le tableau dans la liste
            tableauList.set(i,currentTb);
            //On genere le prochain tableau
            currentTb = genererProchainTableau(currentTb);
            currentTb.verifTableau();
            isLast = currentTb.isLast();
            Log.i("Fin tableau ?",currentTb.isLast()+"");
            tableauList.add(currentTb);

            i++;
        }

        return  tableauList;
    }



}
