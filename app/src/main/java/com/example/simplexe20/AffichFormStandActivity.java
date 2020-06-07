package com.example.simplexe20;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model_v1.Donnees3v;

public class AffichFormStandActivity extends AppCompatActivity {
    Toolbar toolbar;
    final DataBaseWrapper db = new DataBaseWrapper(this);
    String id_enreg;
    TextView ligne1;
    TextView ligne2;
    TextView ligne3;
    TextView ligne4;
    TextView ligne5;
    TextView ident;
    TextView nb_tb;
    TextView type_simplexe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affich__form_stand);
        setTitle("Forme standard du DUAL");

        Intent intent = getIntent();
        id_enreg = intent.getStringExtra("id");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        AffichFormStandActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        ligne1 = findViewById(R.id.ligne1);
        ligne2 = findViewById(R.id.ligne2);
        ligne3 = findViewById(R.id.ligne3);
        ligne4 = findViewById(R.id.ligne4);
        ligne5 = findViewById(R.id.ligne5);
        nb_tb = findViewById(R.id.nb_tb);
        type_simplexe = findViewById(R.id.type_simplexe);
        ident = findViewById(R.id.identifiant1);
        ecrireProgLineaire();
    }


    private void ecrireProgLineaire() {
        Donnees3v d = db.getProgramLineaire(String.valueOf(id_enreg));
        ident.setText("" + d.getId());
        nb_tb.setText("" + d.getNB_TABLEAU());

        switch (d.getTYPE_SIMPLEXE()) {
            case "MAXI_TROIS_VARIABLES":
                type_simplexe.setText("de type maximisation à trois variables");
                ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
                ligne2.setText(d.getE1X1() + " x1 + " + d.getE1X2() + " x2 + " + d.getE1X3() + "x3 + 1e1 + 0e2 + 0e3 = " + d.getBE1());
                ligne3.setText(d.getE2X1() + " x1 + " + d.getE2X2() + " x2 + " + d.getE2X3() + "x3 + 0e1 + 1e2 + 0e3 = " + d.getBE2());
                ligne4.setText(d.getE3X1() + " x1 + " + d.getE3X2() + " x2 + " + d.getE3X3() + "x3 + 0e1 + 0e2 + 1e3 = " + d.getBE3());
                ligne5.setText("max Z = " + d.getZX1() + " x1 + " + d.getZX2() + " x2 +" + d.getZX3() + "x3 +0e1 + 0e2 + 0e3");

                break;
            case "MAXI_DEUX_VARIABLES":
                type_simplexe.setText("de type maximisation à deux variables");
                ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
                ligne2.setText(d.getE1X1() + " x1 + " + d.getE1X2() + " x2 + 1e1 + 0e2 + 0e3 = " + d.getBE1());
                ligne3.setText(d.getE2X1() + " x1 + " + d.getE2X2() + " x2 + 0e1 + 1e2 + 0e3 = " + d.getBE2());
                ligne4.setText(d.getE3X1() + " x1 + " + d.getE3X2() + " x2 + 0e1 + 0e2 + 1e3 = " + d.getBE3());
                ligne5.setText("max Z = " + d.getZX1() + " x1 + " + d.getZX2() + " x2 +" + d.getZX3() + " 0e1 + 0e2 + 0e3");
                break;

            case "MINI_TROIS_VARIABLES":
                type_simplexe.setText("de type minimisation à trois variables");
                ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
                ligne2.setText(d.getE1X1() + " x1 + " + d.getE2X1() + " x2 + " + d.getE3X1() + "x3 + 1e1 + 0e2 + 0e3 = " + d.getZX1());
                ligne3.setText(d.getE1X2() + " x1 + " + d.getE2X2() + " x2 + " + d.getE3X2() + "x3 + 0e1 + 1e2 + 0e3 = " + d.getZX2());
                ligne4.setText(d.getE1X3() + " x1 + " + d.getE2X3() + " x2 + " + d.getE3X3() + "x3 + 0e1 + 0e2 + 1e3 = " + d.getZX3());
                ligne5.setText("max Z' = " + d.getBE1() + " x1 + " + d.getBE2() + " x2 +" + d.getBE3() + "x3 +0e1 + 0e2 + 0e3");
                break;
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
}