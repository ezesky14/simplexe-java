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
import com.example.simplexe20.model.Donnees_3v;

public class Affich_FormStandActivity extends AppCompatActivity {
    Toolbar toolbar;
    DataBaseWrapper db=new DataBaseWrapper(this);
    int id_enreg;

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
        id_enreg=Integer.parseInt(intent.getStringExtra("id"));



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Affich_FormStandActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        ligne1=(TextView)findViewById(R.id.ligne1);
        ligne2=(TextView)findViewById(R.id.ligne2);
        ligne3=(TextView)findViewById(R.id.ligne3);
        ligne4=(TextView)findViewById(R.id.ligne4);
        ligne5=(TextView)findViewById(R.id.ligne5);
        nb_tb=(TextView)findViewById(R.id.nb_tb);
        type_simplexe=(TextView)findViewById(R.id.type_simplexe);
        ident=(TextView)findViewById(R.id.identifiant1);

        Ecrire_ProgLineaire();

    }




    private void Ecrire_ProgLineaire() {


        Donnees_3v d = db.Search_Donnees(String.valueOf(id_enreg));

        ident.setText("" + d.getId());
        nb_tb.setText("" + d.getNB_TABLEAU());

        if (d.getTYPE_SIMPLEXE().equals("maxi_3v")) {
            type_simplexe.setText("de type maximisation à trois variables");

            ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
            ligne2.setText(d.getE1X1() + " x1 + " + d.getE1X2() + " x2 + " + d.getE1X3() + "x3 + 1e1 + 0e2 + 0e3 = " + d.getBE1());
            ligne3.setText(d.getE2X1() + " x1 + " + d.getE2X2() + " x2 + " + d.getE2X3() + "x3 + 0e1 + 1e2 + 0e3 = " + d.getBE2());
            ligne4.setText(d.getE3X1() + " x1 + " + d.getE3X2() + " x2 + " + d.getE3X3() + "x3 + 0e1 + 0e2 + 1e3 = " + d.getBE3());
            ligne5.setText("max Z = " + d.getZX1() + " x1 + " + d.getZX2() + " x2 +" + d.getZX3() + "x3 +0e1 + 0e2 + 0e3");

        } else if (d.getTYPE_SIMPLEXE().equals("maxi_2v")) {
            type_simplexe.setText("de type maximisation à deux variables");

            ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
            ligne2.setText(d.getE1X1() + " x1 + " + d.getE1X2() + " x2 + 1e1 + 0e2 + 0e3 = " + d.getBE1());
            ligne3.setText(d.getE2X1() + " x1 + " + d.getE2X2() + " x2 + 0e1 + 1e2 + 0e3 = " + d.getBE2());
            ligne4.setText(d.getE3X1() + " x1 + " + d.getE3X2() + " x2 + 0e1 + 0e2 + 1e3 = " + d.getBE3());
            ligne5.setText("max Z = " + d.getZX1() + " x1 + " + d.getZX2() + " x2 +" + d.getZX3() + " 0e1 + 0e2 + 0e3");


        } else if (d.getTYPE_SIMPLEXE().equals("mini_3v")) {
            type_simplexe.setText("de type minimisation à trois variables");
            ligne1.setText("(x1,x2,x3,e1,e2,e3) >=0");
            ligne2.setText(d.getE1X1() + " x1 + " + d.getE2X1() + " x2 + " + d.getE3X1() + "x3 + 1e1 + 0e2 + 0e3 = " + d.getZX1());
            ligne3.setText(d.getE1X2() + " x1 + " + d.getE2X2() + " x2 + " + d.getE3X2() + "x3 + 0e1 + 1e2 + 0e3 = " + d.getZX2());
            ligne4.setText(d.getE1X3() + " x1 + " + d.getE2X3() + " x2 + " + d.getE3X3() + "x3 + 0e1 + 0e2 + 1e3 = " + d.getZX3());
            ligne5.setText("max Z' = " + d.getBE1() + " x1 + " + d.getBE2() + " x2 +" + d.getBE3() + "x3 +0e1 + 0e2 + 0e3");

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }
