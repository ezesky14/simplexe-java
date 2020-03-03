package com.example.simplexe20.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseWrapper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simplexe.db";
    private static final int DATABASE_VERSION = 1;

    /*Declaration des tables*/
    public static String PROGRAMMELINEAIRE = "programme_lineaire";
    public static String INTERPRETATION = "interpretation";
    public static String TABLEAU = "tableau";
    private static final String DATABASE_CREATE_DONNEES_3V = "CREATE TABLE "+PROGRAMMELINEAIRE+" ("
            + "`id`	TEXT NOT NULL,"
            + "`e1x1`	REAL,"
            + "`e1x2`	REAL,"
            + "`e1x3`	REAL,"
            + "`be1`	REAL,"
            + "`e2x1`	REAL,"
            + "`e2x2`	REAL,"
            + "`e2x3`	REAL,"
            + "`be2`	REAL,"
            + "`e3x1`	REAL,"
            + "`e3x2`	REAL,"
            + "`e3x3`	REAL,"
            + "`be3`	REAL,"
            + "`zx1`	REAL,"
            + "`zx2`	REAL,"
            + "`zx3`	REAL,"


            + "`type_simplexe`	TEXT NOT NULL,"
            + "`nb_tableau`	INTEGER,"
            + "PRIMARY KEY(id))";

    private static final String DATABASE_CREATE_INTERPRETATION = " CREATE TABLE interpretation ("
            + "  `id`	INTEGER NOT NULL,"
            + "`zmax`	REAL NOT NULL,"
            + "`e1`	REAL NOT NULL,"
            + "`e2`	REAL NOT NULL,"
            + "`e3`	REAL NOT NULL,"
            + "`x1`	REAL NOT NULL,"
            + "`x2`	REAL NOT NULL,"
            + "`x3`	REAL NOT NULL,"
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
            + "`programme_lineaireId`	TEXT NOT NULL,"
            + "PRIMARY KEY(id))";

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_DONNEES_3V);
        db.execSQL(DATABASE_CREATE_TABLEAU);
        db.execSQL(DATABASE_CREATE_INTERPRETATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROGRAMMELINEAIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLEAU);
        db.execSQL("DROP TABLE IF EXISTS " + INTERPRETATION);
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
            List<Float> values = new ArrayList<>();
            for (int a = 1; a < 16; a++) {
                values.add(cursor.getFloat(a));
            }
            prog.setAllEquationValues(values);
        }

        db.close();
        return prog;

    }

    public List<ProgrameLineaire> getAllProgrammeLineaire() {
        ArrayList<ProgrameLineaire> list = new ArrayList();
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

    public TableauV2 getTableau(String id) {
        TableauV2 tab = new TableauV2();

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
        TableauV2 tab = new TableauV2();

        String req = "SELECT *  FROM " + TABLEAU + " "+
                "INNER JOIN "+PROGRAMMELINEAIRE+" "+
                "ON "+PROGRAMMELINEAIRE+".id = "+TABLEAU+".programme_lineaireId " +
                "WHERE  programme_lineaireId = ? ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(req, new String[]{idProgLineaire});

        if (cursor != null) {
            do {
            tab.setId(cursor.getString(0));
            List<Float> values = new ArrayList<>();
            for (int a = 1; a < 16; a++) {
                values.add(cursor.getFloat(a));
            }
            tab.initTableau(values);
            } while (cursor.moveToNext());
        }

        db.close();
        return list;
    }

    public Interpretation getInterPretationProgLineaire(String idProgLineaire) {
        Interpretation interpretation = null;
        return interpretation;
    }

    private String genId(String start){
        return start+"-"+new Random().nextLong();

    }

    public String insertProgLineaire(ProgrameLineaire programeLineaire,String type) {
        List<ColonneTableau> p = programeLineaire.getColList();
        SQLiteDatabase db = this.getWritableDatabase();
        // Les valeurs à inserer
        ContentValues valeur = new ContentValues();
        //Ajout de l id
        String id = genId("PL");
        valeur.put("i"+0, id);
        int i = 1;
        for (ColonneTableau n : p) {
            valeur.put(n.getPositionValue(), n.getValue());
            i++;
        }
        //Ajout du type
        valeur.put("type_simplexe", type);
        //Ajout du nombre de tableaux
        valeur.put("nb_tableau", programeLineaire.getNb_tableau());

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
        valeur.put("id", genId("PL"));
        for (ColonneTableau n : tab.getColonnes()) {
            valeur.put(n.getPositionValue(), n.getValue());
        }


        //Ajout des autres elements
        valeur.put("pivot", tab.getPosition_pivot());
        valeur.put("v_entrante", tab.getPosition_v_entrante());
        valeur.put("v_sortante", tab.getPosition_v_sortante());
        valeur.put("max", tab.getMax());
        valeur.put("min", tab.getMin());
        valeur.put("isLast", tab.isLast());
        valeur.put("programme_lineaireId", programme_lineaireId);
        // insertion
        db.insert(TABLEAU, null, valeur);
        db.close();
    }

    public void insertAllTableau(TableauList tabList, String programme_lineaireId ) {
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
        String id_toString = "";
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

        } while (arreter != true);
        return id;
    }


    public void modifierDonnees(String table, String id, String position_pivot, String position_v_entrante, String position_v_sortante) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + table + " set pivot='" + position_pivot + "', v_entrante='" + position_v_entrante + "', v_sortante='" + position_v_sortante + "'  WHERE id=" + id);
        db.close();
    }


    public List<String> trier_champ(String champ) {
        String champ_a_analyser = champ + " - @";
        List<String> resultat = new ArrayList<String>();
        int indice = 0;
        int indice_tb = 0;
        String qu = "";

        //ANLYSE DU CHAMP
        while (champ_a_analyser.charAt(indice) != '@') {
            qu = "";
            while (!" - ".equals(champ_a_analyser.substring(indice, indice + 1) + champ_a_analyser.substring(indice + 1, indice + 2) + champ_a_analyser.substring(indice + 2, indice + 3))) {
                qu += champ_a_analyser.charAt(indice);
                indice += 1;
            }
            System.out.println(indice_tb + " " + qu);
            resultat.add(qu);
            indice_tb += 1;
            indice += 3;
        }
        return resultat;
    }


    public Donnees_3v Search_Donnees(String id) {

        Donnees_3v tb1 = new Donnees_3v();
        try {
            // declaration de la requete
            String selectQuery = "SELECT  *  FROM " + PROGRAMMELINEAIRE + "" +
                    " WHERE id = ? ";


            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

            // looping through all rows and adding to list
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
                tb1.setTYPE_SIMPLEXE(cursor.getString(16));
                tb1.setNB_TABLEAU(cursor.getInt(17));
            }
            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());
        }
        return tb1;
    }


    public List<Donnees_3v> Recup_all_id() {
        List<Donnees_3v> donnees_3v = new ArrayList<Donnees_3v>();
        try {
            String req = "SELECT id,type_simplexe,nb_tableau FROM donnees_3v ";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(req, null);

            if (cursor.moveToFirst()) {
                do {
                    System.out.println("0000 = " + cursor.getString(0));
                    Donnees_3v d = new Donnees_3v();
                    d.setId(Integer.parseInt(cursor.getString(0)));
                    d.setNB_TABLEAU(Integer.parseInt(cursor.getString(2)));


                    if (cursor.getString(1).equals("maxi_3v")) {
                        d.setTYPE_SIMPLEXE("de type maximisation à trois variables");
                    } else if (cursor.getString(1).equals("maxi_2v")) {
                        d.setTYPE_SIMPLEXE("de type maximisation à deux variables");
                    } else if (cursor.getString(1).equals("mini_3v")) {
                        d.setTYPE_SIMPLEXE("de type minimisation à trois variables");
                    }

                    donnees_3v.add(d);
                } while (cursor.moveToNext());
            }

            db.close();
        } catch (Exception e) {
            Log.e("erreur", e.getLocalizedMessage());

        }
        return donnees_3v;
    }

    public String Recup_type_simplexe(String id) {
        String type = "";
        try {
            String req = "SELECT type_simplexe  FROM donnees_3v WHERE  id = ?";
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
            // declaration de la requete
            String selectQuery = "SELECT  *  FROM " + TABLEAU + "" +
                    " WHERE id = ? ";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

            // looping through all rows and adding to list
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
            String selectQuery = "SELECT type_simplexe FROM donnees_3v WHERE id = ? ";
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




    	

    
    


