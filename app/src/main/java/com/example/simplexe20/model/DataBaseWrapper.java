package com.example.simplexe20.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.simplexe20.model_v1.Donnees3v;
import com.example.simplexe20.model_v1.Tableau;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DataBaseWrapper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplexe.db";
    private static final int DATABASE_VERSION = 1;

    /*Declaration des tables*/
    public static final String PROGRAMMELINEAIRE = "programme_lineaire";
    public static final String INTERPRETATION = "interpretation";
    public static final String TABLEAU = "tableau";
    public static final String COLONNES_TABLEAU = "colonne";
    private static final String DATABASE_CREATE_DONNEES_3V = "CREATE TABLE " + PROGRAMMELINEAIRE + " ("
            + "`id`	TEXT NOT NULL,"
            + "`e1x1`	REAL,"
            + "`e1x2`	REAL,"
            + "`e1x3`	REAL,"
            + "`e1b`	REAL,"
            + "`e2x1`	REAL,"
            + "`e2x2`	REAL,"
            + "`e2x3`	REAL,"
            + "`e2b`	REAL,"
            + "`e3x1`	REAL,"
            + "`e3x2`	REAL,"
            + "`e3x3`	REAL,"
            + "`e3b`	REAL,"
            + "`zx1`	REAL,"
            + "`zx2`	REAL,"
            + "`zx3`	REAL,"
            + "`type_simplexe`	TEXT NOT NULL,"
            + "`nb_tableau`	INTEGER,"
            + "PRIMARY KEY(id))";

    private static final String DATABASE_CREATE_INTERPRETATION = " CREATE TABLE interpretation ("
            + "  `id` TEXT NOT NULL,"
            + "`zmax` REAL NOT NULL,"
            + "`e1`	REAL NOT NULL,"
            + "`e2`	REAL NOT NULL,"
            + "`e3`	REAL NOT NULL,"
            + "`x1`	REAL NOT NULL,"
            + "`x2`	REAL NOT NULL,"
            + "`x3`	REAL NOT NULL,"
            + "`programme_lineaireId`	TEXT NOT NULL,"
            + "PRIMARY KEY(id))";


    /*declaration du nom de la base sqlite et de sa version*/
    private static final String DATABASE_CREATE_TABLEAU = "CREATE TABLE `tableau` ("
            + "`id`	TEXT NOT NULL,"

            + "`e1x1`	REAL,"
            + "`e1x2`	REAL,"
            + "`e1x3`	REAL,"
            + "`e1p1`	REAL,"
            + "`e1p2`	REAL,"
            + "`e1p3`	REAL,"
            + "`e1b`	REAL,"
            + "`e1r`	REAL,"

            + "`e2x1`	REAL,"
            + "`e2x2`	REAL,"
            + "`e2x3`	REAL,"
            + "`e2p1`	REAL,"
            + "`e2p2`	REAL,"
            + "`e2p3`	REAL,"
            + "`e2b`	REAL,"
            + "`e2r`	REAL,"

            + "`e3x1`	REAL,"
            + "`e3x2`	REAL,"
            + "`e3x3`	REAL,"
            + "`e3p1`	REAL,"
            + "`e3p2`	REAL,"
            + "`e3p3`	REAL,"
            + "`e3b`	REAL,"
            + "`e3r`	REAL,"


            + "`zx1`	REAL,"
            + "`zx2`	REAL,"
            + "`zx3`	REAL,"
            + "`zp1`	REAL,"
            + "`zp2`	REAL,"
            + "`zp3`	REAL,"
            + "`zb`	REAL,"

            + "`pivot`	TEXT,"
            + "`v_entrante`	TEXT,"
            + "`v_sortante`	TEXT,"
            + "`max`	TEXT,"
            + "`min`	TEXT,"
            + "`isLast`	TEXT,"
            + "`vdbLabels`	TEXT,"
            + "`vhbLabels`	TEXT,"
            + "`programme_lineaireId`	TEXT NOT NULL,"
            + "PRIMARY KEY(id))";

    private static final String DATABASE_CREATE_COLONNES_TABLEAU = "CREATE TABLE `colonne` ("
            + "`id`	TEXT NOT NULL,"
            + "`vhbValue`	TEXT,"
            + "`vdbValue`	TEXT,"
            + "`vhbPosition`	REAL,"
            + "`vdbPosition`	REAL,"
            + "`positionValue`	TEXT,"
            + "`value`	REAL,"
            + "`lignePosition`	REAL,"
            + "`tableauId`	TEXT NOT NULL,"
            + "PRIMARY KEY(id))";


    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_DONNEES_3V);
        db.execSQL(DATABASE_CREATE_TABLEAU);
        db.execSQL(DATABASE_CREATE_COLONNES_TABLEAU);
        db.execSQL(DATABASE_CREATE_INTERPRETATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROGRAMMELINEAIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLEAU);
        db.execSQL("DROP TABLE IF EXISTS " + INTERPRETATION);
        db.execSQL("DROP TABLE IF EXISTS " + COLONNES_TABLEAU);
        onCreate(db);
    }

    public ProgrameLineaire getProgrammeLineaire(String idProgLineaire) {
        ProgrameLineaire prog = new ProgrameLineaire();
        String req = "SELECT *  FROM " + PROGRAMMELINEAIRE + " WHERE  id = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{idProgLineaire});

        if (cursor != null) {
            cursor.moveToFirst();
            prog.setId(cursor.getString(0));
            prog.setType(cursor.getString(16));
            prog.setNbTableau(cursor.getInt(17));

            List<Float> values = new ArrayList<>();
            for (int a = 1; a < 16; a++) {
                Log.i("value", cursor.getFloat(a) + "");
                values.add(cursor.getFloat(a));
            }

            prog.setEquation1(values.get(0), values.get(1), values.get(2), values.get(3));
            prog.setEquation2(values.get(4), values.get(5), values.get(6), values.get(7));
            prog.setEquation3(values.get(8), values.get(9), values.get(10), values.get(11));
            prog.setzEquation(values.get(12), values.get(13), values.get(14));

            Log.i("type simplexe", cursor.getString(16));
        }
        db.close();
        return prog;
    }

    public List<ProgrameLineaire> getAllProgrammeLineaire() {
        ArrayList list = new ArrayList();
        String req = "SELECT *  FROM " + PROGRAMMELINEAIRE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, null);

        if (cursor != null) {
            do {
                ProgrameLineaire prog = new ProgrameLineaire();
                List<Float> values = new ArrayList<>();
                prog.setId(cursor.getString(0));
                for (int a = 1; a < 16; a++) {
                    values.add(cursor.getFloat(a));
                }
                prog.setEquationValuesWithoutPValues(values);
                list.add(prog);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;

    }

    public TableauV2 getTableau(String id, ProgrameLineaire programeLineaire) {
        TableauV2 tab = new TableauV2(programeLineaire);

        String req = "SELECT *  FROM " + TABLEAU + " WHERE  id = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{id});

        if (cursor != null) {
            cursor.moveToFirst();
            tab.setId(cursor.getString(0));
            List<Float> values = new ArrayList<>();
            for (int a = 1; a < 16; a++) {
                values.add(cursor.getFloat(a));
            }
            tab.initTableau(values);
        }

        db.close();
        return tab;
    }

    public ArrayList<TableauV2> getAllTableau(String idProgLineaire) {
        ArrayList<TableauV2> list = new ArrayList<>();
        String req = "SELECT *  FROM " + TABLEAU + " " +
                "INNER JOIN " + PROGRAMMELINEAIRE + " " +
                "ON " + PROGRAMMELINEAIRE + ".id = " + TABLEAU + ".programme_lineaireId " +
                "WHERE  programme_lineaireId = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{idProgLineaire});

        Log.i("cursor size", cursor.getCount() + "");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                List<Float> values = new ArrayList<>();
                for (int a = 1; a < 32; a++) {
                    values.add(cursor.getFloat(a));
                }
                values.add((float) 0);
                TableauV2 tab = new TableauV2(values, 8);
                tab.setId(cursor.getString(0));

                //Pivot
                String pivot = cursor.getString(32);
                Log.i("pivot", pivot + "");
                //tab.setPivot(Float.parseFloat(pivot));
                tab.setPositionPivot(pivot);


                //V entrante
                ColonneTableau colVEntrante = new ColonneTableau();
                String vEntrante = cursor.getString(33);
                Log.i("vEntrante", vEntrante + "");
                //colVEntrante.setValue(vEntrante);
                tab.setPositionVEntrante(vEntrante + "");
                tab.setColvEntrante(colVEntrante);

                //V sortante
                ColonneTableau colVSortante = new ColonneTableau();
                String vSortante = cursor.getString(34);
                Log.i("vSortante", vSortante + "");
                //colVEntrante.setValue(vSortante);
                tab.setPositionVSortante(vSortante + "");
                tab.setColvEntrante(colVSortante);


                tab.setMax(cursor.getFloat(35));
                tab.setMin(cursor.getFloat(36));

                Log.i("setisLast", cursor.getString(37));
                String isLast = cursor.getString(37);
                if (isLast.equals("0")) {
                    tab.setLast(false);
                } else {
                    tab.setLast(true);
                }


                // Recherche VdbLabels et vhbLabel
                String vdbLabels = cursor.getString(cursor.getColumnIndex("vdbLabels"));
                String vhbLabels = cursor.getString(cursor.getColumnIndex("vhbLabels"));

                tab.setVhbLabels(vhbLabels.split(","));
                tab.setVdbLabels(vdbLabels.split(","));

                list.add(tab);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    public Interpretation getInterPretationProgLineaire(String idProgLineaire) {
        Interpretation interpretation = new Interpretation();
        String req = "SELECT *  FROM " + INTERPRETATION + " " +
                "INNER JOIN " + PROGRAMMELINEAIRE + " " +
                "ON " + PROGRAMMELINEAIRE + ".id = " + INTERPRETATION + ".programme_lineaireId " +
                "WHERE  programme_lineaireId = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{idProgLineaire});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                interpretation.setId(cursor.getInt(0));
                interpretation.setZMAX(String.valueOf(cursor.getInt(1)));
                interpretation.setE1(String.valueOf(cursor.getInt(2)));
                interpretation.setE2(String.valueOf(cursor.getInt(3)));
                interpretation.setE3(String.valueOf(cursor.getInt(4)));
                interpretation.setX1(String.valueOf(cursor.getInt(5)));
                interpretation.setX2(String.valueOf(cursor.getInt(6)));
                interpretation.setX3(String.valueOf(cursor.getInt(7)));
                interpretation.setProgLineaireId(String.valueOf(cursor.getInt(8)));

            } while (cursor.moveToNext());
        }

        db.close();
        return interpretation;
    }

    @SuppressWarnings("UnusedReturnValue")
    public String insertInterPretation(Interpretation interpretation, String idProgLineaire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valeur = new ContentValues();
        String id = genId("INTERP");
        valeur.put("id", id);
        valeur.put("zmax", interpretation.getZMAX());
        valeur.put("e1", interpretation.getE1());
        valeur.put("e2", interpretation.getE2());
        valeur.put("e3", interpretation.getE3());
        valeur.put("x1", interpretation.getX1());
        valeur.put("x2", interpretation.getX2());
        valeur.put("x3", interpretation.getX3());
        valeur.put("programme_lineaireId", idProgLineaire);

        // insertion
        db.insert(INTERPRETATION, null, valeur);
        db.close();
        return id;
    }

    private String genId(String start) {
        return start + "-" + new Random().nextLong();
    }

    public String insertProgLineaire(ProgrameLineaire programeLineaire, String type) {
        List<ColonneTableau> p = programeLineaire.getColList();
        SQLiteDatabase db = this.getWritableDatabase();
        // Les valeurs à inserer
        ContentValues valeur = new ContentValues();
        //Ajout de l id
        String id = genId("PL");
        valeur.put("id", id);
        for (ColonneTableau n : p) {
            Log.i(n.getPositionValue(), n.getValue() + "");
            valeur.put(n.getPositionValue().toLowerCase(), n.getValue());
        }

        valeur.put("type_simplexe", type);
        valeur.put("nb_tableau", programeLineaire.getNbTableau());

        // insertion
        db.insert(PROGRAMMELINEAIRE, null, valeur);
        db.close();
        return id;
    }

    public void insertTableau(TableauV2 tab, String programme_lineaireId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Les valeurs à inserer
        ContentValues valeur = new ContentValues();
        //Ajout de l id
        valeur.put("id", genId("TB"));
        for (ColonneTableau n : tab.getColonnes()) {
            if (n.isCalculated()) {
                valeur.put(n.getPositionValue().toLowerCase(), n.getValue());
            }
        }

        //Ajout des autres elements
        valeur.put("pivot", tab.getPositionPivot());
        valeur.put("v_entrante", tab.getPositionVEntrante());
        valeur.put("v_sortante", tab.getPositionVSortante());
        valeur.put("max", tab.getMax());
        valeur.put("min", tab.getMin());
        valeur.put("isLast", tab.isLast());
        valeur.put("programme_lineaireId", programme_lineaireId);

        valeur.put("vhbLabels", tab.getLabelsToString(tab.getVhbLabels(), ","));
        valeur.put("vdbLabels", tab.getLabelsToString(tab.getVdbLabels(), ","));

        // insertion
        db.insert(TABLEAU, null, valeur);
        db.close();
    }

    public void insertAllTableau(ArrayList<TableauV2> tabList, String programme_lineaireId) {
        for (TableauV2 tb : tabList) {
            insertTableau(tb, programme_lineaireId);
        }
    }

    public void InsertData(List<String> donnees, String table, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Les valeurs à inserer
        ContentValues valeur = new ContentValues();
        int i = 1;
        for (String n : donnees) {
            valeur.put("i" + i, n);
            i++;
        }

        // insertion
        db.insert(table, null, valeur);

        // fermeture de la base
        db.close();
    }


    public void insertSQL(String requete) {
        SQLiteDatabase db = this.getWritableDatabase();
        // insertion
        db.execSQL(requete);
        // fermeture de la base
        db.close();
    }

    public void DeleteSQL(String requete) {
        SQLiteDatabase db = this.getWritableDatabase();
        // insertion
        db.execSQL(requete);
        // fermeture de la base
        db.close();
    }


    public String getNumberOfTable(String id) {
        String nb = "";
        String req = "SELECT nb_tableau  FROM donnees_3v WHERE  id = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{id});

        if (cursor != null) {
            cursor.moveToFirst();
            nb = String.valueOf(cursor.getInt(0));
        }

        db.close();
        return nb;
    }


    public int Veriier_Id_disponible() {
        int id = 1;
        String id_toString;
        boolean arreter;
        do {

            id_toString = id + "";
            // declaration de la requete
            String selectQuery = "SELECT  *  FROM donnees_3v WHERE id = ? ";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{(id_toString)});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                id++;
                arreter = false;
            } else {
                arreter = true;
            }

            db.close();

        } while (!arreter);
        return id;
    }


    public void modifierDonnees(String table, String id, String position_pivot, String position_v_entrante, String position_v_sortante) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + table + " set pivot='" + position_pivot + "', v_entrante='" + position_v_entrante + "', v_sortante='" + position_v_sortante + "'  WHERE id=" + id);
        db.close();
    }


    @SuppressWarnings("UnusedAssignment")
    public List<String> trier_champ(String champ) {
        String champ_a_analyser = champ + " - @";
        List<String> resultat = new ArrayList<>();
        int indice = 0;
        int indice_tb = 0;
        StringBuilder qu = new StringBuilder();

        //ANLYSE DU CHAMP
        while (champ_a_analyser.charAt(indice) != '@') {
            qu = new StringBuilder();
            while (!" - ".equals(champ_a_analyser.substring(indice, indice + 1) + champ_a_analyser.substring(indice + 1, indice + 2) + champ_a_analyser.substring(indice + 2, indice + 3))) {
                qu.append(champ_a_analyser.charAt(indice));
                indice += 1;
            }
            System.out.println(indice_tb + " " + qu);
            resultat.add(qu.toString());
            indice_tb += 1;
            indice += 3;
        }
        return resultat;
    }


    public Donnees3v getProgramLineaire(String id) {
        Donnees3v tb1 = new Donnees3v();
        try {
            // declaration de la requete
            String selectQuery = "SELECT  *  FROM " + PROGRAMMELINEAIRE + "" +
                    " WHERE id = ? ";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

            // looping through all rows and adding to list
            if (cursor != null) {
                cursor.moveToFirst();
                tb1.setId(cursor.getString(0));

                tb1.setE1X1(cursor.getString(1));
                tb1.setE1X2(cursor.getString(2));
                tb1.setE1X3(cursor.getString(3));
                tb1.setBE1(cursor.getString(4));

                tb1.setE2X1(cursor.getString(5));
                tb1.setE2X2(cursor.getString(6));
                tb1.setE2X3(cursor.getString(7));
                tb1.setBE2(cursor.getString(8));

                tb1.setE3X1(cursor.getString(9));
                tb1.setE3X2(cursor.getString(10));
                tb1.setE3X3(cursor.getString(11));

                tb1.setZX1(cursor.getString(12));
                tb1.setZX2(cursor.getString(13));
                tb1.setZX3(cursor.getString(14));
                tb1.setBE3(cursor.getString(15));

                tb1.setTYPE_SIMPLEXE(cursor.getString(16));
                tb1.setNB_TABLEAU(cursor.getInt(17));
            }
            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());
        }
        return tb1;
    }

    public List<Donnees3v> recupAllProgLineaire() {
        List<Donnees3v> donnees_3v = new ArrayList<Donnees3v>();
        try {
            String req = "SELECT id, type_simplexe, nb_tableau FROM " + PROGRAMMELINEAIRE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(req, null);

            if (cursor.moveToFirst()) {
                do {
                    Log.d("type", cursor.getString(1));
                    Donnees3v d = new Donnees3v();
                    d.setId(cursor.getString(0));
                    d.setNB_TABLEAU(Integer.parseInt(cursor.getString(2)));

                    switch (cursor.getString(1)) {
                        case "MAXI_TROIS_VARIABLES":
                            d.setTYPE_SIMPLEXE("de type maximisation à trois variables");
                            break;
                        case "MAXI_DEUX_VARIABLES":
                            d.setTYPE_SIMPLEXE("de type maximisation à deux variables");
                            break;
                        case "MINI_TROIS_VARIABLES":
                            d.setTYPE_SIMPLEXE("de type minimisation à trois variables");
                            break;
                    }

                    donnees_3v.add(d);
                } while (cursor.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());
            e.printStackTrace();

        }
        return donnees_3v;
    }

    @SuppressWarnings("ConstantConditions")
    public String getType_simplexe(String id) {
        String type = "";
        try {
            String req = "SELECT type_simplexe  FROM " + PROGRAMMELINEAIRE + " WHERE  id = ?";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(req, new String[]{id});
            if (cursor != null) {
                cursor.moveToFirst();
                type = String.valueOf(cursor.getString(0));
            }
            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());
        }
        return type;
    }

    public Tableau getTable(String id) {
        Tableau tb1 = new Tableau();
        try {
            String selectQuery = "SELECT  *  FROM " + TABLEAU + "" +
                    " WHERE id = ? ";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id});
            if (cursor != null) {
                cursor.moveToFirst();
                tb1.setId(cursor.getInt(0));
                tb1.setE1X1(cursor.getString(1));
                tb1.setE1X2(cursor.getString(2));
                tb1.setE1X3(cursor.getString(3));
                tb1.setE2X1(cursor.getString(4));
                tb1.setE2X2(cursor.getString(5));
                tb1.setE2X3(cursor.getString(6));
                tb1.setE3X1(cursor.getString(7));
                tb1.setE3X2(cursor.getString(8));
                tb1.setE3X3(cursor.getString(9));
                tb1.setZX1(cursor.getString(10));
                tb1.setZX2(cursor.getString(11));
                tb1.setZX3(cursor.getString(12));
                tb1.setBE1(cursor.getString(13));
                tb1.setBE2(cursor.getString(14));
                tb1.setBE3(cursor.getString(15));
                tb1.setP1E1(cursor.getString(16));
                tb1.setP1E2(cursor.getString(17));
                tb1.setP1E3(cursor.getString(18));
                tb1.setP2E1(cursor.getString(19));
                tb1.setP2E2(cursor.getString(20));
                tb1.setP2E3(cursor.getString(21));
                tb1.setP3E1(cursor.getString(22));
                tb1.setP3E2(cursor.getString(23));
                tb1.setP3E3(cursor.getString(24));
                tb1.setP1Z(cursor.getString(25));
                tb1.setP2Z(cursor.getString(26));
                tb1.setP3Z(cursor.getString(27));
                tb1.setBZ(cursor.getString(28));
                tb1.setPIVOT(cursor.getString(29));
                tb1.setV_ENTRANT(cursor.getString(30));
                tb1.setV_SORTANT(cursor.getString(31));
            }

            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());

        }
        return tb1;
    }


    /*  Drepreciated*/
    public String recuperer(String id) {
        String tb = "";
        try {
            String selectQuery = "SELECT type_simplexe FROM " + PROGRAMMELINEAIRE + " WHERE id = ? ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id});
            if (cursor != null) {
                cursor.moveToFirst();
                tb = cursor.getString(0);


            }

            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());
        }
        return tb;
    }

}




    	

    
    


