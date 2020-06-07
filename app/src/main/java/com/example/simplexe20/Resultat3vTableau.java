package com.example.simplexe20;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model.ProgrameLineaire;
import com.example.simplexe20.model.SimplexeV2;
import com.example.simplexe20.model.TableauV2;

import java.util.ArrayList;

public class Resultat3vTableau extends AppCompatActivity {

    Toolbar toolbar;
    String id_enreg;
    String type_simplexe = "";

    //LABEL DU TABLEAU
    TextView lab_e1;
    TextView lab_e2;
    TextView lab_e3;
    TextView lab_p1;
    TextView lab_p2;
    TextView lab_p3;
    TextView lab_x1;
    TextView lab_x2;
    TextView lab_x3;


    //CHAMPS DES RESULTATS
    TextView txt_e1x1;
    TextView txt_e1x2;
    TextView txt_e1x3;

    TextView txt_e2x1;
    TextView txt_e2x2;
    TextView txt_e2x3;

    TextView txt_e3x1;
    TextView txt_e3x2;
    TextView txt_e3x3;

    TextView txt_zx1;
    TextView txt_zx2;
    TextView txt_zx3;

    TextView txt_p1e1;
    TextView txt_p1e2;
    TextView txt_p1e3;


    TextView txt_be1;
    TextView txt_be2;
    TextView txt_be3;


    TextView txt_p2e1;
    TextView txt_p2e2;
    TextView txt_p2e3;
    TextView txt_p3e1;
    TextView txt_p3e2;
    TextView txt_p3e3;
    TextView txt_p1z;
    TextView txt_p2z;
    TextView txt_p3z;
    TextView txt_bz;
    TextView txt_re1;
    TextView txt_re2;
    TextView txt_re3;

    TextView v_entrant;
    TextView v_sortant;
    TextView pivot;


    //LES CHAMPS POUR L INTERPRETATION
    TextView txt_x1;
    TextView txt_x2;
    TextView txt_x3;
    TextView txt_e1;
    TextView txt_e2;
    TextView txt_e3;
    TextView txt_zmax;

    final DataBaseWrapper db = new DataBaseWrapper(this);
    SimplexeV2 simplexeV2;
    ArrayList<TableauV2> tableauList;
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tb_1_3v);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Resultat3vTableau.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        initTableFields();
        initOthersFields();
        initInterpretationFields();
        getAllTables();

        if (this.tableauList.size() > 0) {
            setTableau(0);
        }

    }

    private void getAllTables() {
        Intent intent = getIntent();
        id_enreg = intent.getStringExtra("id");
        setTitle("Résultats Pour l'ID no " + id_enreg);
        ProgrameLineaire prog = db.getProgrammeLineaire(id_enreg);
        simplexeV2 = new SimplexeV2(prog);
        type_simplexe = prog.getType().toString();
        tableauList = db.getAllTableau(id_enreg);
        simplexeV2.setInterpretation(db.getInterPretationProgLineaire(id_enreg));
    }

    private void initTableFields() {
        lab_x1 = findViewById(R.id.lab_x1);
        lab_x2 = findViewById(R.id.lab_x2);
        lab_x3 = findViewById(R.id.lab_x3);
        lab_e1 = findViewById(R.id.e1);
        lab_e2 = findViewById(R.id.e2);
        lab_e3 = findViewById(R.id.e3);
        lab_p1 = findViewById(R.id.lab_p1);
        lab_p2 = findViewById(R.id.lab_p2);
        lab_p3 = findViewById(R.id.lab_p3);

        txt_e1x1 = findViewById(R.id.e1x1);
        txt_e1x2 = findViewById(R.id.e1x2);
        txt_e1x3 = findViewById(R.id.e1x3);
        txt_e2x1 = findViewById(R.id.e2x1);
        txt_e2x2 = findViewById(R.id.e2x2);
        txt_e2x3 = findViewById(R.id.e2x3);
        txt_e3x1 = findViewById(R.id.e3x1);
        txt_e3x2 = findViewById(R.id.e3x2);
        txt_e3x3 = findViewById(R.id.e3x3);
        txt_zx1 = findViewById(R.id.zx1);
        txt_zx2 = findViewById(R.id.zx2);
        txt_zx3 = findViewById(R.id.zx3);
        txt_p1e1 = findViewById(R.id.p1e1);
        txt_p1e2 = findViewById(R.id.p1e2);
        txt_p1e3 = findViewById(R.id.p1e3);
        txt_p2e1 = findViewById(R.id.p2e1);
        txt_p2e2 = findViewById(R.id.p2e2);
        txt_p2e3 = findViewById(R.id.p2e3);
        txt_p3e1 = findViewById(R.id.p3e1);
        txt_p3e2 = findViewById(R.id.p3e2);
        txt_p3e3 = findViewById(R.id.p3e3);
        txt_p1z = findViewById(R.id.p1z);
        txt_p2z = findViewById(R.id.p2z);
        txt_p3z = findViewById(R.id.p3z);
        txt_bz = findViewById(R.id.bz);
        txt_be1 = findViewById(R.id.be1);
        txt_be2 = findViewById(R.id.be2);
        txt_be3 = findViewById(R.id.be3);
        txt_re1 = findViewById(R.id.re1);
        txt_re2 = findViewById(R.id.re2);
        txt_re3 = findViewById(R.id.re3);
    }

    private void initInterpretationFields() {
        txt_x1 = findViewById(R.id.txt_x1);
        txt_x2 = findViewById(R.id.txt_x2);
        txt_x3 = findViewById(R.id.txt_x3);
        txt_e1 = findViewById(R.id.txt_e1);
        txt_e2 = findViewById(R.id.txt_e2);
        txt_e3 = findViewById(R.id.txt_e3);
        txt_zmax = findViewById(R.id.txt_zmax_min);
    }

    private void initOthersFields() {
        v_entrant = findViewById(R.id.v_entrante);
        v_sortant = findViewById(R.id.v_sortante);
        pivot = findViewById(R.id.pivot);
    }

    private void setTableau(int position) {
        TableauV2 tb = tableauList.get(position);
        resetValuesOfTableFields();
        txt_e1x1.setText(tb.getValue("e1", 0) + "");
        txt_e1x2.setText(tb.getValue("e1", 1) + "");
        txt_e1x3.setText(tb.getValue("e1", 2) + "");
        txt_e2x1.setText(tb.getValue("e2", 0) + "");
        txt_e2x2.setText(tb.getValue("e2", 1) + "");
        txt_e2x3.setText(tb.getValue("e2", 2) + "");
        txt_e3x1.setText(tb.getValue("e3", 0) + "");
        txt_e3x2.setText(tb.getValue("e3", 1) + "");
        txt_e3x3.setText(tb.getValue("e3", 2) + "");
        txt_zx1.setText(tb.getValue("Z", 0) + "");
        txt_zx2.setText(tb.getValue("Z", 1) + "");
        txt_zx3.setText(tb.getValue("Z", 2) + "");
        txt_be1.setText(tb.getValue("e1", 6) + "");
        txt_be2.setText(tb.getValue("e2", 6) + "");
        txt_be3.setText(tb.getValue("e3", 6) + "");
        txt_p1e1.setText(tb.getValue("e1", 3) + "");
        txt_p2e1.setText(tb.getValue("e1", 4) + "");
        txt_p3e1.setText(tb.getValue("e1", 5) + "");

        txt_p1e2.setText(tb.getValue("e2", 3) + "");
        txt_p2e2.setText(tb.getValue("e2", 4) + "");
        txt_p3e2.setText(tb.getValue("e2", 5) + "");

        txt_p1e3.setText(tb.getValue("e3", 3) + "");
        txt_p2e3.setText(tb.getValue("e3", 4) + "");
        txt_p3e3.setText(tb.getValue("e3", 5) + "");

        txt_p1z.setText(tb.getValue("Z", 3) + "");
        txt_p2z.setText(tb.getValue("Z", 4) + "");
        txt_p3z.setText(tb.getValue("Z", 5) + "");
        txt_bz.setText(tb.getValue("Z", 6) + "");

        pivot.setText(tb.getPositionPivot());
        v_entrant.setText(tb.getPositionVEntrante());
        v_sortant.setText(tb.getPositionVSortante());

        txt_re1.setText(tb.getValue("e1", 7) + "");
        txt_re2.setText(tb.getValue("e2", 7) + "");
        txt_re3.setText(tb.getValue("e3", 7) + "");


        setLabelTexts(tb.getVhbLabels(), tb.getVdbLabels());
        if (!tb.isLast()) {
            subrillanceChamp(tb.getPositionVEntrante(), tb.getPositionVSortante(), tb.getPositionPivot());
            v_entrant.setText(tb.getPositionVEntrante());
            v_sortant.setText(tb.getPositionVSortante());
            pivot.setText(tb.getPositionPivot());
        } else {
            interpreteSolu(simplexeV2.getInterpretation());
            String endMessage = "";
            v_entrant.setText(endMessage);
            v_sortant.setText(endMessage);
            pivot.setText(endMessage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void next(View v) {
        if (pos < tableauList.size() - 1) {
            pos += 1;
            setTableau(pos);
        }
    }

    public void previous(View v) {
        if (pos > 0) {
            pos -= 1;
            setTableau(pos);
        }
    }

    private void resetColors() {
        txt_zx1.setBackgroundResource(R.color.md_orange_500_50);
        txt_zx2.setBackgroundResource(R.color.md_orange_500_50);
        txt_zx3.setBackgroundResource(R.color.md_orange_500_50);
        txt_p1z.setBackgroundResource(R.color.md_orange_500_50);
        txt_p2z.setBackgroundResource(R.color.md_orange_500_50);
        txt_p3z.setBackgroundResource(R.color.md_orange_500_50);
        txt_re1.setBackgroundResource(R.color.md_orange_500_50);
        txt_re2.setBackgroundResource(R.color.md_orange_500_50);
        txt_re3.setBackgroundResource(R.color.md_orange_500_50);
        txt_e1x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e1x2.setBackgroundResource(R.color.md_orange_500_50);
        txt_e1x3.setBackgroundResource(R.color.md_orange_500_50);
        txt_e2x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e2x2.setBackgroundResource(R.color.md_orange_500_50);
        txt_e2x3.setBackgroundResource(R.color.md_orange_500_50);
        txt_e3x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e3x2.setBackgroundResource(R.color.md_orange_500_50);
        txt_e3x3.setBackgroundResource(R.color.md_orange_500_50);
    }

    private void subrillanceChamp(String vEntrant, String vSortant, String pivot) {
        resetColors();
        /*LES COULEURS CHOISIES SONT :
            @color/md_blue_500_25 pour la valeur entrante
            @color/md_green_300" pour la valeur sortante
            @color/md_yellow_500_50 pur le pivot
            par defaut /md_orange_500_50
        */
        //VALEUR ENTRANTE
        if (vEntrant.toLowerCase().equals("zx1")) {
            txt_zx1.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (vEntrant.toLowerCase().equals("zx2")) {
            txt_zx2.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (vEntrant.toLowerCase().equals("zx3")) {
            txt_zx3.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (vEntrant.toLowerCase().equals("p1z")) {
            txt_p1z.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (vEntrant.toLowerCase().equals("p2z")) {
            txt_p2z.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (vEntrant.toLowerCase().equals("p3z")) {
            txt_p3z.setBackgroundResource(R.color.md_blue_500_25);
        }


        //VALEUR SORTANTE
        if (vSortant.toLowerCase().equals("re1")) {
            txt_re1.setBackgroundResource(R.color.md_green_300);
        }
        if (vSortant.toLowerCase().equals("re2")) {
            txt_re2.setBackgroundResource(R.color.md_green_300);
        }
        if (vSortant.toLowerCase().equals("re3")) {
            txt_re3.setBackgroundResource(R.color.md_green_300);
        }


        //PIVOT
        if (pivot.toLowerCase().equals("e1x1")) {
            txt_e1x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e1x2")) {
            txt_e1x2.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e1x3")) {
            txt_e1x3.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e2x1")) {
            txt_e2x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e2x2")) {
            txt_e2x2.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e2x3")) {
            txt_e2x3.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e3x1")) {
            txt_e3x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e3x2")) {
            txt_e3x2.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.toLowerCase().equals("e3x3")) {
            txt_e3x3.setBackgroundResource(R.color.md_yellow_500_50);
        }
    }


    private void resetValuesOfRFields() {
        txt_re1.setText("");
        txt_re2.setText("");
        txt_re3.setText("");
    }


    private void resetValuesOfTableFields() {
        resetLabelTexts();
        txt_e1x1.setText("");
        txt_e1x2.setText("");
        txt_e1x3.setText("");

        txt_e2x1.setText("");
        txt_e2x2.setText("");
        txt_e2x3.setText("");

        txt_e3x1.setText("");
        txt_e3x2.setText("");
        txt_e3x3.setText("");

        txt_e1x3.setText("");
        txt_e2x3.setText("");
        txt_e3x3.setText("");

        txt_zx1.setText("");
        txt_zx2.setText("");
        txt_zx3.setText("");

        txt_be1.setText("");
        txt_be2.setText("");
        txt_be3.setText("");

        txt_p1e1.setText("");
        txt_p2e1.setText("");
        txt_p3e1.setText("");

        txt_p1e2.setText("");
        txt_p2e2.setText("");
        txt_p3e2.setText("");

        txt_p1e3.setText("");
        txt_p2e3.setText("");
        txt_p3e3.setText("");

        txt_p1z.setText("");
        txt_p2z.setText("");
        txt_p3z.setText("");

/*
        txt_x1.setText("");
        txt_x2.setText("");
        txt_x3.setText("");
        txt_e1.setText("");
        txt_e2.setText("");
        txt_e3.setText("");
        txt_zmax.setText("");
        txt_interpretation.setText("");

*/
    }

    private void resetLabelTexts() {
        lab_e1.setText("e1");
        lab_e2.setText("e2");
        lab_e3.setText("e3");
        lab_x1.setText("x1");
        lab_x2.setText("x2");
        lab_x3.setText("x3");
        lab_p1.setText(".");
        lab_p2.setText(".");
        lab_p3.setText(".");
    }

    private void setLabelTexts(String[] VhbLabels, String[] VdbLabels) {
        resetLabelTexts();
        lab_e1.setText(VdbLabels[0]);
        lab_e2.setText(VdbLabels[1]);
        lab_e3.setText(VdbLabels[2]);

        lab_x1.setText(VhbLabels[0]);
        lab_x2.setText(VhbLabels[1]);
        lab_x3.setText(VhbLabels[2]);
        lab_p1.setText(VhbLabels[3]);
        lab_p2.setText(VhbLabels[4]);
        lab_p3.setText(VhbLabels[5]);
    }


    //Fonction d'interpretation de la solution optimale
    public void interpreteSolu(com.example.simplexe20.model.Interpretation interpretation) {
        txt_e1.setText(interpretation.getE1());
        txt_e2.setText(interpretation.getE2());
        txt_e3.setText(interpretation.getE3());
        txt_x1.setText(interpretation.getX1());
        txt_x2.setText(interpretation.getX2());
        txt_x3.setText(interpretation.getX3());
        txt_zmax.setText(interpretation.getZMAX());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
