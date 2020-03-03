package com.example.simplexe20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model.ProgrameLineaire;
import com.example.simplexe20.model.SimplexeType;
import com.example.simplexe20.model.SimplexeV2;
import com.example.simplexe20.model.TableauList;
import com.example.simplexe20.model.TableauV2;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;

    String id_enreg;
    TextView lab_e1x1;
    EditText txt_e1x1;

    TextView lab_e1x2;
    EditText txt_e1x2;

    TextView lab_e1x3;
    EditText txt_e1x3;

    TextView lab_e2x1;
    EditText txt_e2x1;

    TextView lab_e2x2;
    EditText txt_e2x2;

    TextView lab_e2x3;
    EditText txt_e2x3;

    TextView lab_e3x1;
    EditText txt_e3x1;

    TextView lab_e3x2;
    EditText txt_e3x2;

    TextView lab_e3x3;
    EditText txt_e3x3;

    TextView lab_zx1;
    EditText txt_zx1;

    TextView lab_zx2;
    EditText txt_zx2;

    TextView lab_zx3;
    EditText txt_zx3;

    EditText txt_be1;
    EditText txt_be2;
    EditText txt_be3;

    TextView i1;
    TextView i2;
    TextView i3;


    DataBaseWrapper db = new DataBaseWrapper(this);
    Simplexe_3v simp_3v = new Simplexe_3v();
    NavigationViewModel nva = new NavigationViewModel();

    SimplexeV2 simplexeV2;
    TableauList tableauList;
    Context CONTEXT = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Maximisation à trois variables");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //DECLARATION DES CHAMPS  POUR LE TABLEAU A 3VARIABLES
        initialiserChamps();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            nva.setupNavigationDrawerContent(navigationView, MainActivity.this, drawerLayout, R.id.item_max_3v);
        }
        nva.setupNavigationDrawerContent(navigationView, MainActivity.this, drawerLayout, R.id.item_max_3v);
    }

    private void initialiserChamps() {
        lab_e1x1 = (TextView) findViewById(R.id.e1x1_);
        txt_e1x1 = (EditText) findViewById(R.id.txt_e1x1);

        lab_e1x2 = (TextView) findViewById(R.id.e1x2_);
        txt_e1x2 = (EditText) findViewById(R.id.txt_e1x2);

        lab_e1x3 = (TextView) findViewById(R.id.e1x3_);
        txt_e1x3 = (EditText) findViewById(R.id.txt_e1x3);

        lab_e2x1 = (TextView) findViewById(R.id.e2x1_);
        txt_e2x1 = (EditText) findViewById(R.id.txt_e2x1);

        lab_e2x2 = (TextView) findViewById(R.id.e2x2_);
        txt_e2x2 = (EditText) findViewById(R.id.txt_e2x2);

        lab_e2x3 = (TextView) findViewById(R.id.e2x3_);
        txt_e2x3 = (EditText) findViewById(R.id.txt_e2x3);

        lab_e3x1 = (TextView) findViewById(R.id.e3x1_);
        txt_e3x1 = (EditText) findViewById(R.id.txt_e3x1);

        lab_e3x2 = (TextView) findViewById(R.id.e3x2_);
        txt_e3x2 = (EditText) findViewById(R.id.txt_e3x2);

        lab_e3x3 = (TextView) findViewById(R.id.e3x3_);
        txt_e3x3 = (EditText) findViewById(R.id.txt_e3x3);

        lab_zx1 = (TextView) findViewById(R.id.x1_);
        txt_zx1 = (EditText) findViewById(R.id.txt_zx1);
        lab_zx2 = (TextView) findViewById(R.id.x2_);
        txt_zx2 = (EditText) findViewById(R.id.txt_zx2);


        txt_be1 = (EditText) findViewById(R.id.txt_be1);
        txt_be2 = (EditText) findViewById(R.id.txt_be2);
        txt_be3 = (EditText) findViewById(R.id.txt_be3);


        txt_zx3 = (EditText) findViewById(R.id.txt_zx3);


        i1 = (TextView) findViewById(R.id.i1);
        i2 = (TextView) findViewById(R.id.i2);
        i3 = (TextView) findViewById(R.id.i3);

        i1.setText("<=");
        i2.setText("<=");
        i3.setText("<=");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Valider_3v(View v) {
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty() || txt_e1x3.getText().toString().isEmpty() || txt_zx1.getText().toString().isEmpty()
                || txt_e2x1.getText().toString().isEmpty() || txt_e2x2.getText().toString().isEmpty() || txt_e2x3.getText().toString().isEmpty() || txt_zx2.getText().toString().isEmpty()
                || txt_e3x1.getText().toString().isEmpty() || txt_e3x2.getText().toString().isEmpty() || txt_e3x3.getText().toString().isEmpty() || txt_zx3.getText().toString().isEmpty()
                || txt_be1.getText().toString().isEmpty() || txt_be2.getText().toString().isEmpty() || txt_be3.getText().toString().isEmpty()) {

            Toast.makeText(this, "Veillez remplir les champs SVP", Toast.LENGTH_LONG).show();
        } else {
            procederAuCalcul();
        }
    }

    public void procederAuCalcul() {
        System.out.println("e1x1= " + txt_e1x1.getText());
        System.out.println("e1x2= " + txt_e1x2.getText());
        System.out.println("e1x3= " + txt_e1x3.getText());
        System.out.println("e2x1= " + txt_e2x1.getText());
        System.out.println("e2x2= " + txt_e2x2.getText());
        System.out.println("e2x3= " + txt_e2x3.getText());
        System.out.println("e3x1= " + txt_e3x1.getText());
        System.out.println("e3x2= " + txt_e3x2.getText());
        System.out.println("e3x3= " + txt_e3x3.getText());
        System.out.println("zx1= " + txt_zx1.getText());
        System.out.println("zx2= " + txt_zx2.getText());
        System.out.println("zx3= " + txt_zx3.getText());
        System.out.println("be1= " + txt_be1.getText());
        System.out.println("be2= " + txt_be2.getText());
        System.out.println("be3= " + txt_be3.getText());

        calculerSimplexeV2();
    }


    private void calculerSimplexeV2() {
        //On verifie si tout les champs ont été renseigner
        //dans le cas contraire on affiche un message afin de forcer l'utilisateur à remplir tout les champs
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty() || txt_e1x3.getText().toString().isEmpty()
                || txt_e2x1.getText().toString().isEmpty() || txt_e2x2.getText().toString().isEmpty() || txt_e2x3.getText().toString().isEmpty()
                || txt_e3x1.getText().toString().isEmpty() || txt_e3x2.getText().toString().isEmpty() || txt_e3x3.getText().toString().isEmpty()
                || txt_zx1.getText().toString().isEmpty() || txt_zx2.getText().toString().isEmpty() || txt_zx3.getText().toString().isEmpty()) {
            Toast.makeText(CONTEXT, "Remplissez bien les champs SVP", Toast.LENGTH_SHORT).show();

        } else {
            try {

                //Ajout des donnees dans le programme lineaire
                ProgrameLineaire programeLineaire = new ProgrameLineaire(SimplexeType.MAXI_TROIS_VARIABLES);
                programeLineaire.setEquation1(
                        Float.parseFloat(txt_e1x1.getText().toString()),
                        Float.parseFloat(txt_e1x2.getText().toString()),
                        Float.parseFloat(txt_e1x3.getText().toString()),
                        Float.parseFloat(txt_be1.getText().toString())
                );
                programeLineaire.setEquation2(
                        Float.parseFloat(txt_e2x1.getText().toString()),
                        Float.parseFloat(txt_e2x2.getText().toString()),
                        Float.parseFloat(txt_e2x3.getText().toString()),
                        Float.parseFloat(txt_be2.getText().toString())
                );
                programeLineaire.setEquation3(
                        Float.parseFloat(txt_e3x1.getText().toString()),
                        Float.parseFloat(txt_e3x2.getText().toString()),
                        Float.parseFloat(txt_e3x3.getText().toString()),
                        Float.parseFloat(txt_be3.getText().toString())
                );
                programeLineaire.setzEquation(
                        Float.parseFloat(txt_zx1.getText().toString()),
                        Float.parseFloat(txt_zx2.getText().toString()),
                        Float.parseFloat(txt_zx3.getText().toString())
                );
                programeLineaire.setColList();
                simplexeV2 = new SimplexeV2(programeLineaire);
                this.tableauList = simplexeV2.genTables();
                programeLineaire.setNb_tableau(this.tableauList.size());

                //Enregistrement dans la base de donnees
                id_enreg = db.insertProgLineaire(programeLineaire, SimplexeType.MAXI_TROIS_VARIABLES.toString());
                db.insertAllTableau(this.tableauList,id_enreg);
                //Redirection vers les resultats
                AlertDialog.Builder al = new AlertDialog.Builder(CONTEXT);
                al.setTitle("Enregistrement");
                al.setMessage("Enregistrement éffectué .");

                al.setPositiveButton("résultat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CONTEXT, Resultat3v_Tableau.class);
                        intent.putExtra("id", "" + id_enreg);
                        intent.putParcelableArrayListExtra("tbList", tableauList);
                        startActivity(intent);
                    }
                });
            } catch (SQLException ex) {
                Toast.makeText(CONTEXT, "Erreur détectée", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void calculerSimplexe() {
        //Verifier tout d'abord si tout les champs respectent les normes , afin d'eviter d'eventuelles erreurs

        int nb_tableau = 0;
        String req;


        //On verifie si tout les champs ont été renseigner
        //dans le cas contraire on affiche un message afin de forcer l'utilisateur à remplir tout les champs
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty() || txt_e1x3.toString().isEmpty() || txt_e2x1.toString().isEmpty() || txt_e2x2.toString().isEmpty() || txt_e2x3.toString().isEmpty() || txt_e3x1.toString().isEmpty() || txt_e3x2.toString().isEmpty() || txt_e3x3.getText().toString().isEmpty() || txt_zx1.toString().isEmpty() || txt_zx2.toString().isEmpty() || txt_zx3.toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Remplissez bien les champs SVP", Toast.LENGTH_SHORT).show();

        } else {

            int tb_indice = 1;

            try {


                //CALCUL DES TABLEAUX

                req = "insert into donnees_3v values (" + id_enreg
                        + "," + txt_e1x1.getText() + "," +
                        txt_e1x2.getText() + "," +
                        txt_e1x3.getText() + "," +
                        txt_e2x1.getText() + "," +
                        txt_e2x2.getText() + "," +
                        txt_e2x3.getText() + "," +
                        txt_e3x1.getText() + "," +
                        txt_e3x2.getText() + "," +
                        txt_e3x3.getText() + "," +
                        txt_zx1.getText() + "," +
                        txt_zx2.getText() + "," +
                        txt_zx3.getText() + "," +
                        txt_be1.getText() + "," +
                        txt_be2.getText() + "," +
                        txt_be3.getText() + ",'maxi_3v',0)";

                db.insertSQL(req);


                //On convertit les valeurs du champ en Float (valeur flotante)
                simp_3v.tb_e1[0] = Float.parseFloat(txt_e1x1.getText().toString());
                simp_3v.tb_e1[1] = Float.parseFloat(txt_e1x2.getText().toString());
                simp_3v.tb_e1[2] = Float.parseFloat(txt_e1x3.getText().toString());
                simp_3v.tb_e2[0] = Float.parseFloat(txt_e2x1.getText().toString());
                simp_3v.tb_e2[1] = Float.parseFloat(txt_e2x2.getText().toString());
                simp_3v.tb_e2[2] = Float.parseFloat(txt_e2x3.getText().toString());
                simp_3v.tb_e3[0] = Float.parseFloat(txt_e3x1.getText().toString());
                simp_3v.tb_e3[1] = Float.parseFloat(txt_e3x2.getText().toString());
                simp_3v.tb_e3[2] = Float.parseFloat(txt_e3x3.getText().toString());
                simp_3v.tb_Z[0] = Float.parseFloat(txt_zx1.getText().toString());
                simp_3v.tb_Z[1] = Float.parseFloat(txt_zx2.getText().toString());
                simp_3v.tb_Z[2] = Float.parseFloat(txt_zx3.getText().toString());
                simp_3v.tb_e1[6] = Float.parseFloat(txt_be1.getText().toString());
                simp_3v.tb_e2[6] = Float.parseFloat(txt_be2.getText().toString());
                simp_3v.tb_e3[6] = Float.parseFloat(txt_be3.getText().toString());
                simp_3v.tb_e1[3] = 1;
                simp_3v.tb_e2[3] = 0;
                simp_3v.tb_e3[3] = 0;
                simp_3v.tb_e1[4] = 0;
                simp_3v.tb_e2[4] = 1;
                simp_3v.tb_e3[4] = 0;
                simp_3v.tb_e1[5] = 0;
                simp_3v.tb_e2[5] = 0;
                simp_3v.tb_e3[5] = 1;
                simp_3v.tb_Z[3] = 0;
                simp_3v.tb_Z[4] = 0;
                simp_3v.tb_Z[5] = 0;
                simp_3v.tb_Z[6] = 0;

                //On enregistre les donnees dans la BD
                db.insertSQL(formatterRequeteInsertion(id_enreg + "", "tableau"));


                //1ER TABLEAU DEJA EFFECTUER ,ON PASSE DONC AUX CALCUL POUR LES PROCHAINS TABLEAUX
                //*On cherche le pivot
                int pre = 1;
                while (!"oui".equals(simp_3v.fin_tableau)) {
                    pre = tb_indice;

                    simp_3v.determinerPivot();
                    simp_3v.verifTableau();
                    System.out.println(simp_3v.fin_tableau);

                    tb_indice++;
                    nb_tableau++;

                    //ON AFFECTE LES COLONNES DEJA CALCULEES POUR UN AUTRE CALCUL DU PROCHAIN TABLEAU
                    for (int j = 0; j < 8; j++) {
                        simp_3v.tb_e1[j] = simp_3v.tb_e1prim[j];
                        simp_3v.tb_e2[j] = simp_3v.tb_e2prim[j];
                        simp_3v.tb_e3[j] = simp_3v.tb_e3prim[j];
                        simp_3v.tb_Z[j] = simp_3v.tb_Zprim[j];
                    }
                    //ON ENREGISTRE LES NOUVELLES DONNEES CALCULEES DU NOUVEAU TABLEAU
                    db.insertSQL(formatterRequeteInsertion(id_enreg + "", "tableau" + tb_indice));
                    //ON MODIFIE DES INFOS DU PRECEDENT TABLEAU
                    db.insertSQL(formatterRequeteUpdate(id_enreg + "", "tableau" + pre));


                    System.out.println("on passe au tableau" + String.valueOf(tb_indice - 1));
                }


                //Ajout DU NOMBRE DE TABLEUX DANS LA TABLE donnees_3v
                nb_tableau++;

                req = "UPDATE donnees_3v set nb_tableau ='" + nb_tableau + "'  WHERE id='" + id_enreg + "' ";
                db.insertSQL(req);


                AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
                al.setTitle("Enregistrement");
                al.setMessage("Enregistrement éffectué .");

                al.setPositiveButton("résultat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Resultat3v_Tableau.class);
                        intent.putExtra("id", "" + id_enreg);
                        startActivity(intent);
                    }
                });


            } catch (SQLException ex) {
                Toast.makeText(MainActivity.this, "Erreur détectée", Toast.LENGTH_LONG).show();
            }
        }

    }


    private String formatterRequeteInsertion(String id, String table) {
        String r = "insert into  " + table + "  values (" + id + ","
                + simp_3v.tb_e1[0] +
                "," + simp_3v.tb_e1[1] + "," +
                simp_3v.tb_e1[2] + "," +
                simp_3v.tb_e2[0] + "," +
                simp_3v.tb_e2[1] + "," +
                simp_3v.tb_e2[2] + "," +
                simp_3v.tb_e3[0] + "," +
                simp_3v.tb_e3[1] + "," +
                simp_3v.tb_e3[2] + "," +
                simp_3v.tb_Z[0] + "," +
                simp_3v.tb_Z[1] + "," +
                simp_3v.tb_Z[2] + "," +
                simp_3v.tb_e1[6] + "," +
                simp_3v.tb_e2[6] + "," +
                simp_3v.tb_e3[6] + "," +
                simp_3v.tb_e1[3] + "," +
                simp_3v.tb_e2[3] + "," +
                simp_3v.tb_e3[3] + "," +
                simp_3v.tb_e1[4] + "," +
                simp_3v.tb_e2[4] + "," +
                simp_3v.tb_e3[4] + "," +
                simp_3v.tb_e1[5] + "," +
                simp_3v.tb_e2[5] + "," +
                simp_3v.tb_e3[5] + "," +
                simp_3v.tb_Z[3] + "," +
                +simp_3v.tb_Z[4] + ","
                + simp_3v.tb_Z[5] + ","
                + simp_3v.tb_Z[6] + ", ' ',' ',' ') ";

        return r;
    }

    private String formatterRequeteUpdate(String id, String table) {

        String r = "UPDATE " + table + " set pivot='" + simp_3v.position_pivot + "', v_entrante='" + simp_3v.position_v_entrante + "', v_sortante='" + simp_3v.position_v_sortante + "'  WHERE id=" + id;
        return r;
    }


}