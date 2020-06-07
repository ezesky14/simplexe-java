package com.example.simplexe20;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
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
import com.example.simplexe20.model.TableauV2;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class Mini3vActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    String idEnreg;
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

    final DataBaseWrapper db = new DataBaseWrapper(this);
    final NavigationViewModel nva = new NavigationViewModel();
    SimplexeV2 simplexeV2;
    ArrayList<TableauV2> tableauList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini3v);
        setTitle("Minimisation à trois variables");

        //DECLARATION DES CHAMPS  POUR LE TABLEAU A 3VARIABLES
        initialiserChamps();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        if (navigationView != null) {
            nva.setupNavigationDrawerContent(navigationView, Mini3vActivity.this, drawerLayout, R.id.item_min_3v);
        }

        nva.setupNavigationDrawerContent(navigationView, Mini3vActivity.this, drawerLayout, R.id.item_min_3v);
    }

    private void initialiserChamps() {
        lab_e1x1 = findViewById(R.id.e1y1_);
        txt_e1x1 = findViewById(R.id.txt_e1y1);

        lab_e1x2 = findViewById(R.id.e1y2_);
        txt_e1x2 = findViewById(R.id.txt_e1y2);

        lab_e1x3 = findViewById(R.id.e1y3_);
        txt_e1x3 = findViewById(R.id.txt_e1y3);

        lab_e2x1 = findViewById(R.id.e2y1_);
        txt_e2x1 = findViewById(R.id.txt_e2y1);

        lab_e2x2 = findViewById(R.id.e2y2_);
        txt_e2x2 = findViewById(R.id.txt_e2y2);

        lab_e2x3 = findViewById(R.id.e2y3_);
        txt_e2x3 = findViewById(R.id.txt_e2y3);

        lab_e3x1 = findViewById(R.id.e3y1_);
        txt_e3x1 = findViewById(R.id.txt_e3y1);

        lab_e3x2 = findViewById(R.id.e3y2_);
        txt_e3x2 = findViewById(R.id.txt_e3y2);

        lab_e3x3 = findViewById(R.id.e3y3_);
        txt_e3x3 = findViewById(R.id.txt_e3y3);

        lab_zx1 = findViewById(R.id.y1_);
        txt_zx1 = findViewById(R.id.txt_zy1);
        lab_zx2 = findViewById(R.id.y2_);
        txt_zx2 = findViewById(R.id.txt_zy2);


        txt_be1 = findViewById(R.id.txt_be1);
        txt_be2 = findViewById(R.id.txt_be2);
        txt_be3 = findViewById(R.id.txt_be3);
        txt_zx3 = findViewById(R.id.txt_zy3);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void procederAuCalcul() {
        //PROCEDER AU CALCUL
        calculerSimplexeV2();
    }

    private void calculerSimplexeV2() {
        //On verifie si tout les champs ont été renseigner
        //dans le cas contraire on affiche un message afin de forcer l'utilisateur à remplir tout les champs
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty() || txt_e1x3.getText().toString().isEmpty()
                || txt_e2x1.getText().toString().isEmpty() || txt_e2x2.getText().toString().isEmpty() || txt_e2x3.getText().toString().isEmpty()
                || txt_e3x1.getText().toString().isEmpty() || txt_e3x2.getText().toString().isEmpty() || txt_e3x3.getText().toString().isEmpty()
                || txt_zx1.getText().toString().isEmpty() || txt_zx2.getText().toString().isEmpty() || txt_zx3.getText().toString().isEmpty()) {
            Toast.makeText(Mini3vActivity.this, "Remplissez bien les champs SVP", Toast.LENGTH_SHORT).show();

        } else {
            try {

                //Ajout des donnees dans le programme lineaire
                ProgrameLineaire programeLineaire = new ProgrameLineaire(SimplexeType.MINI_TROIS_VARIABLES);
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
                programeLineaire.setNbTableau(tableauList.size());

                //Enregistrement dans la base de donnees
                idEnreg = db.insertProgLineaire(programeLineaire, SimplexeType.MINI_TROIS_VARIABLES.toString());
                db.insertAllTableau(tableauList, idEnreg);
                db.insertInterPretation(simplexeV2.getInterpretation(), idEnreg);

                //Redirection vers les resultats
                AlertDialog.Builder al = new AlertDialog.Builder(Mini3vActivity.this);
                al.setTitle("Enregistrement");
                al.setMessage("Enregistrement éffectué .");

                al.setPositiveButton("résultat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Mini3vActivity.this, Resultat3vTableau.class);
                        intent.putExtra("id", "" + idEnreg);
                        startActivity(intent);
                    }
                });
            } catch (SQLException ex) {
                Toast.makeText(Mini3vActivity.this, "Erreur détectée", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void Valider3vMin(View v) {
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty() || txt_e1x3.getText().toString().isEmpty() || txt_zx1.getText().toString().isEmpty()
                || txt_e2x1.getText().toString().isEmpty() || txt_e2x2.getText().toString().isEmpty() || txt_e2x3.getText().toString().isEmpty() || txt_zx2.getText().toString().isEmpty()
                || txt_e3x1.getText().toString().isEmpty() || txt_e3x2.getText().toString().isEmpty() || txt_e3x3.getText().toString().isEmpty() || txt_zx3.getText().toString().isEmpty()
                || txt_be1.getText().toString().isEmpty() || txt_be2.getText().toString().isEmpty() || txt_be3.getText().toString().isEmpty()) {

            Toast.makeText(this, "Veillez remplir les champs SVP", Toast.LENGTH_LONG).show();
        } else {
            procederAuCalcul();
        }
    }


}
