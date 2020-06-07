package com.example.simplexe20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
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
import androidx.preference.PreferenceManager;

import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model.ProgrameLineaire;
import com.example.simplexe20.model.SimplexeType;
import com.example.simplexe20.model.SimplexeV2;
import com.example.simplexe20.model.TableauV2;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Maxi2vActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    String idEnreg;
    TextView lab_e1x1;
    EditText txt_e1x1;

    TextView lab_e1x2;
    EditText txt_e1x2;
    TextView lab_e2x1;
    EditText txt_e2x1;
    TextView lab_e2x2;
    EditText txt_e2x2;
    TextView lab_e3x1;
    EditText txt_e3x1;
    TextView lab_e3x2;
    EditText txt_e3x2;
    TextView lab_zx1;
    EditText txt_zx1;
    TextView lab_zx2;
    EditText txt_zx2;
    EditText txt_be1;
    EditText txt_be2;
    EditText txt_be3;

    TextView i1;
    TextView i2;
    TextView i3;

    final DataBaseWrapper db = new DataBaseWrapper(this);
    final NavigationViewModel nva = new NavigationViewModel();
    SimplexeV2 simplexeV2;
    ArrayList<TableauV2> tableauList;
    final Context CONTEXT = Maxi2vActivity.this;
    private int roundNumber;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxi2v);
        setTitle("Maximisation à deux variables");

        //DECLARATION DES CHAMPS  POUR LE TABLEAU A 3VARIABLES
        lab_e1x1 = findViewById(R.id.e1x1_);
        txt_e1x1 = findViewById(R.id.txt_e1x1);

        lab_e1x2 = findViewById(R.id.e1x2_);
        txt_e1x2 = findViewById(R.id.txt_e1x2);

        lab_e2x1 = findViewById(R.id.e2x1_);
        txt_e2x1 = findViewById(R.id.txt_e2x1);

        lab_e2x2 = findViewById(R.id.e2x2_);
        txt_e2x2 = findViewById(R.id.txt_e2x2);

        lab_e3x1 = findViewById(R.id.e3x1_);
        txt_e3x1 = findViewById(R.id.txt_e3x1);

        lab_e3x2 = findViewById(R.id.e3x2_);
        txt_e3x2 = findViewById(R.id.txt_e3x2);

        lab_zx1 = findViewById(R.id.x1_);
        txt_zx1 = findViewById(R.id.txt_zx1);
        lab_zx2 = findViewById(R.id.x2_);
        txt_zx2 = findViewById(R.id.txt_zx2);

        txt_be1 = findViewById(R.id.txt_be1);
        txt_be2 = findViewById(R.id.txt_be2);
        txt_be3 = findViewById(R.id.txt_be3);

        i1 = findViewById(R.id.i1);
        i2 = findViewById(R.id.i2);
        i3 = findViewById(R.id.i3);

        i1.setText("<=");
        i2.setText("<=");
        i3.setText("<=");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        if (navigationView != null) {
            nva.setupNavigationDrawerContent(navigationView, Maxi2vActivity.this, drawerLayout, R.id.item_max_2v);
        }
        nva.setupNavigationDrawerContent(navigationView, Maxi2vActivity.this, drawerLayout, R.id.item_max_2v);

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(CONTEXT);
        roundNumber = Integer.parseInt(sharedPreferences.getString("roundNumber", "-1"));

        Log.i("roundNumber", roundNumber + "");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void Valider_2v_maxi(View v) {
        calculerSimplexeV2();
    }


    private void calculerSimplexeV2() {
        if (txt_e1x1.getText().toString().isEmpty() || txt_e1x2.getText().toString().isEmpty()
                || txt_e2x1.getText().toString().isEmpty() || txt_e2x2.getText().toString().isEmpty()
                || txt_e3x1.getText().toString().isEmpty() || txt_e3x2.getText().toString().isEmpty()
                || txt_zx1.getText().toString().isEmpty() || txt_zx2.getText().toString().isEmpty()) {
            Toast.makeText(CONTEXT, "Remplissez bien les champs SVP", Toast.LENGTH_SHORT).show();

        } else {
            try {
                //Ajout des donnees dans le programme lineaire
                ProgrameLineaire programeLineaire = new ProgrameLineaire(SimplexeType.MAXI_DEUX_VARIABLES, roundNumber);
                programeLineaire.setEquation1(
                        Float.parseFloat(txt_e1x1.getText().toString()),
                        Float.parseFloat(txt_e1x2.getText().toString()),
                        0,
                        Float.parseFloat(txt_be1.getText().toString())
                );
                programeLineaire.setEquation2(
                        Float.parseFloat(txt_e2x1.getText().toString()),
                        Float.parseFloat(txt_e2x2.getText().toString()),
                        0,
                        Float.parseFloat(txt_be2.getText().toString())
                );
                programeLineaire.setEquation3(
                        Float.parseFloat(txt_e3x1.getText().toString()),
                        Float.parseFloat(txt_e3x2.getText().toString()),
                        0,
                        Float.parseFloat(txt_be3.getText().toString())
                );
                programeLineaire.setzEquation(
                        Float.parseFloat(txt_zx1.getText().toString()),
                        Float.parseFloat(txt_zx2.getText().toString()),
                        0
                );

                programeLineaire.setColList();
                simplexeV2 = new SimplexeV2(programeLineaire);
                this.tableauList = simplexeV2.genTables();
                programeLineaire.setNbTableau(this.tableauList.size());

                //Enregistrement dans la base de donnees
                idEnreg = db.insertProgLineaire(programeLineaire, SimplexeType.MAXI_TROIS_VARIABLES.toString());
                db.insertAllTableau(this.tableauList, idEnreg);
                db.insertInterPretation(simplexeV2.getInterpretation(), idEnreg);

                //Redirection vers les resultats
                AlertDialog.Builder al = new AlertDialog.Builder(CONTEXT);
                al.setTitle("Enregistrement");
                al.setMessage("Enregistrement éffectué .");

                al.setPositiveButton("résultat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CONTEXT, Resultat2vTableau.class);
                        intent.putExtra("id", "" + idEnreg);
                        startActivity(intent);
                    }
                });
            } catch (SQLException ex) {
                Toast.makeText(CONTEXT, "Erreur détectée", Toast.LENGTH_LONG).show();
            }
        }
    }
}
