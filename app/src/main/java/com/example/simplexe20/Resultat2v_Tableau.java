package com.example.simplexe20;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model.Tableau;

public class Resultat2v_Tableau extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    Tableau tableau;
    Tableau tableau2;
    Tableau tableau3;
    Tableau tableau4;

    int id_enreg;
    int tb_indice = 0;
    String pivot1 = "";
    String pivot2 = "";
    String pivot3 = "";
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


    //CHAMPS DES RESULTATS
    TextView txt_e1x1;
    TextView txt_e1x2;


    TextView txt_e2x1;
    TextView txt_e2x2;


    TextView txt_e3x1;
    TextView txt_e3x2;


    TextView txt_zx1;
    TextView txt_zx2;


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

    Button tb1;
    Button tb2;
    Button tb3;
    Button tb4;
    Button Interpretation;

    TextView v_entrant;
    TextView v_sortant;
    TextView pivot;

    DataBaseWrapper db = new DataBaseWrapper(this);


    //LES CHAMPS POUR L INTERPRETATION
    TextView txt_x1;
    TextView txt_x2;
    TextView txt_e1;
    TextView txt_e2;
    TextView txt_e3;
    TextView txt_zmax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id_enreg = Integer.parseInt(intent.getStringExtra("id"));
        setContentView(R.layout.tb_1_2v);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        setTitle("Résultats Pour l'ID no "+id_enreg );


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TypedValue typedValueColorPrimaryDark = new TypedValue();
        Resultat2v_Tableau.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }


        //DECLARATION DES CHAMPS  POUR LE TABLEAU A 3VARIABLES
        lab_x1 = (TextView) findViewById(R.id.lab_x1);
        lab_x2 = (TextView) findViewById(R.id.lab_x2);

        lab_e1 = (TextView) findViewById(R.id.e1);
        lab_e2 = (TextView) findViewById(R.id.e2);
        lab_e3 = (TextView) findViewById(R.id.e3);
        lab_p1 = (TextView) findViewById(R.id.lab_p1);
        lab_p2 = (TextView) findViewById(R.id.lab_p2);
        lab_p3 = (TextView) findViewById(R.id.lab_p3);

        txt_e1x1 = (TextView) findViewById(R.id.e1x1);
        txt_e1x2 = (TextView) findViewById(R.id.e1x2);

        txt_e2x1 = (TextView) findViewById(R.id.e2x1);
        txt_e2x2 = (TextView) findViewById(R.id.e2x2);

        txt_e3x1 = (TextView) findViewById(R.id.e3x1);
        txt_e3x2 = (TextView) findViewById(R.id.e3x2);

        txt_zx1 = (TextView) findViewById(R.id.zx1);
        txt_zx2 = (TextView) findViewById(R.id.zx2);

        txt_p1e1 = (TextView) findViewById(R.id.p1e1);
        txt_p1e2 = (TextView) findViewById(R.id.p1e2);
        txt_p1e3 = (TextView) findViewById(R.id.p1e3);
        txt_p2e1 = (TextView) findViewById(R.id.p2e1);
        txt_p2e2 = (TextView) findViewById(R.id.p2e2);
        txt_p2e3 = (TextView) findViewById(R.id.p2e3);
        txt_p3e1 = (TextView) findViewById(R.id.p3e1);
        txt_p3e2 = (TextView) findViewById(R.id.p3e2);
        txt_p3e3 = (TextView) findViewById(R.id.p3e3);
        txt_p1z = (TextView) findViewById(R.id.p1z);
        txt_p2z = (TextView) findViewById(R.id.p2z);
        txt_p3z = (TextView) findViewById(R.id.p3z);
        txt_bz = (TextView) findViewById(R.id.bz);
        txt_be1 = (TextView) findViewById(R.id.be1);
        txt_be2 = (TextView) findViewById(R.id.be2);
        txt_be3 = (TextView) findViewById(R.id.be3);
        txt_re1 = (TextView) findViewById(R.id.re1);
        txt_re2 = (TextView) findViewById(R.id.re2);
        txt_re3 = (TextView) findViewById(R.id.re3);


        v_entrant = (TextView) findViewById(R.id.v_entrante);
        v_sortant = (TextView) findViewById(R.id.v_sortante);
        pivot = (TextView) findViewById(R.id.pivot);


       /* pivot1 = db.Recuperer_Pivot(id_enreg + "", "tableau");
        pivot2 = db.Recuperer_Pivot(id_enreg + "", "tableau2");
        pivot3 = db.Recuperer_Pivot(id_enreg + "", "tableau3");
*/
        type_simplexe = db.recuperer(id_enreg + "");


//POUR L INTERPRETATION


        txt_x1 = (TextView) findViewById(R.id.txt_x1);
        txt_x2 = (TextView) findViewById(R.id.txt_x2);
        txt_e1 = (TextView) findViewById(R.id.txt_e1);
        txt_e2 = (TextView) findViewById(R.id.txt_e2);
        txt_e3 = (TextView) findViewById(R.id.txt_e3);
        txt_zmax = (TextView) findViewById(R.id.txt_zmax_min);


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


    //LES EVENEMENTS LORS DU CLICK SUR LES BOUTONS

    //pour afFICHER LE TABLEAU 1
    public void tb1_Click(View v) {
        tableau = db.getTable(id_enreg + "");
        delete_champ();
        //AFICHAGE DES CHAPS TROUVEEs
        txt_e1x1.setText(tableau.getE1X1());
        txt_e1x2.setText(tableau.getE1X2());

        txt_e2x1.setText(tableau.getE2X1());
        txt_e2x2.setText(tableau.getE2X2());

        txt_e3x1.setText(tableau.getE3X1());
        txt_e3x2.setText(tableau.getE3X2());

        txt_zx1.setText(tableau.getZX1());
        txt_zx2.setText(tableau.getZX2());

        txt_be1.setText(tableau.getBE1());
        txt_be2.setText(tableau.getBE2());
        txt_be3.setText(tableau.getBE3());
        txt_p1e1.setText(tableau.getP1E1());
        txt_p1e2.setText(tableau.getP1E2());
        txt_p1e3.setText(tableau.getP1E3());
        txt_p2e1.setText(tableau.getP2E1());
        txt_p2e2.setText(tableau.getP2E2());
        txt_p2e3.setText(tableau.getP2E3());
        txt_p3e1.setText(tableau.getP3E1());
        txt_p3e2.setText(tableau.getP3E2());
        txt_p3e3.setText(tableau.getP3E3());
        txt_p1z.setText(tableau.getP1Z());
        txt_p2z.setText(tableau.getP2Z());
        txt_p3z.setText(tableau.getP3Z());

        txt_bz.setText(tableau.getBZ());


        pivot.setText(tableau.getPIVOT());
        v_entrant.setText(tableau.getV_ENTRANT());
        v_sortant.setText(tableau.getV_SORTANT());
        afiche_re1(v_entrant.getText().toString());





            Subrillance_Champ(v_entrant.getText().toString(), v_sortant.getText().toString(), pivot.getText().toString());


    }








    private void ReinitialiserCouleur(){
        txt_zx1.setBackgroundResource(R.color.md_orange_500_50);
        txt_zx2.setBackgroundResource(R.color.md_orange_500_50);

        txt_p1z.setBackgroundResource(R.color.md_orange_500_50);
        txt_p2z.setBackgroundResource(R.color.md_orange_500_50);
        txt_p3z.setBackgroundResource(R.color.md_orange_500_50);
        txt_re1.setBackgroundResource(R.color.md_orange_500_50);
        txt_re2.setBackgroundResource(R.color.md_orange_500_50);
        txt_re3.setBackgroundResource(R.color.md_orange_500_50);
        txt_e1x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e1x2.setBackgroundResource(R.color.md_orange_500_50);

        txt_e2x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e2x2.setBackgroundResource(R.color.md_orange_500_50);
        txt_e3x1.setBackgroundResource(R.color.md_orange_500_50);
        txt_e3x2.setBackgroundResource(R.color.md_orange_500_50);
        txt_p1e1.setBackgroundResource(R.color.md_orange_500_50);
        txt_p1e2.setBackgroundResource(R.color.md_orange_500_50);
        txt_p1e3.setBackgroundResource(R.color.md_orange_500_50);
        txt_p2e1.setBackgroundResource(R.color.md_orange_500_50);
        txt_p2e2.setBackgroundResource(R.color.md_orange_500_50);
        txt_p2e3.setBackgroundResource(R.color.md_orange_500_50);
        txt_p3e1.setBackgroundResource(R.color.md_orange_500_50);
        txt_p3e2.setBackgroundResource(R.color.md_orange_500_50);
        txt_p3e3.setBackgroundResource(R.color.md_orange_500_50);



    }

    private void Subrillance_Champ(String v_entrant,String v_sortant,String pivot){

        ReinitialiserCouleur();
    /*LES COULEURS CHOISIES SONT :
     @color/md_blue_500_25 pour la valeur entrante
     @color/md_green_300" pour la valeur sortante
      @color/md_yellow_500_50 pur le pivot

      par defaut /md_orange_500_50

    */

        //VALEUR ENTRANTE
        if (v_entrant.equals("zx1")  ){
            txt_zx1.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (v_entrant.equals("zx2")  ){
            txt_zx2.setBackgroundResource(R.color.md_blue_500_25);
        }



        if (v_entrant.equals("p1z")  ){
            txt_p1z.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (v_entrant.equals("p2z")  ){
            txt_p2z.setBackgroundResource(R.color.md_blue_500_25);
        }

        if (v_entrant.equals("p3z")  ){
            txt_p3z.setBackgroundResource(R.color.md_blue_500_25);
        }


        //VALEUR SORTANTE
        if (v_sortant.equals("re1")  ){
            txt_re1.setBackgroundResource(R.color.md_green_300);
        }
        if (v_sortant.equals("re2")  ){
            txt_re2.setBackgroundResource(R.color.md_green_300);
        }
        if (v_sortant.equals("re3")  ){
            txt_re3.setBackgroundResource(R.color.md_green_300);
        }






        //PIVOT
        if (pivot.equals("e1x1")  ){
            txt_e1x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.equals("e1x2")  ){
            txt_e1x2.setBackgroundResource(R.color.md_yellow_500_50);
        }



        if (pivot.equals("e2x1")  ){
            txt_e2x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.equals("e2x2")  ){
            txt_e2x2.setBackgroundResource(R.color.md_yellow_500_50);
        }



        if (pivot.equals("e3x1")  ){
            txt_e3x1.setBackgroundResource(R.color.md_yellow_500_50);
        }

        if (pivot.equals("e3x2")  ){
            txt_e3x2.setBackgroundResource(R.color.md_yellow_500_50);
        }



        if (pivot.equals("p1e1")  ){
            txt_p1e1.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p1e2")  ){
            txt_p1e2.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p1e3")  ){
            txt_p1e3.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p2e1")  ){
            txt_p2e1.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p2e2")  ){
            txt_p2e2.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p2e3")  ){
            txt_p2e3.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p3e1")  ){
            txt_e3x2.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p3e2")  ){
            txt_p3e2.setBackgroundResource(R.color.md_yellow_500_50);
        }
        if (pivot.equals("p3e3")  ){
            txt_p3e3.setBackgroundResource(R.color.md_yellow_500_50);
        }



    }





    //Reinitialisation des R
    private void effacer_re1() {
        txt_re1.setText("");
        txt_re2.setText("");
        txt_re3.setText("");
    }


    private void delete_champ() {
        Reinit();

        txt_e1x1.setText("");
        txt_e1x2.setText("");


        txt_e2x1.setText("");
        txt_e2x2.setText("");


        txt_e3x1.setText("");
        txt_e3x2.setText("");


        txt_zx1.setText("");
        txt_zx2.setText("");


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

    private void Reinit() {
        lab_e1.setText("e1");
        lab_e2.setText("e2");
        lab_e3.setText("e3");
        lab_x1.setText("x1");
        lab_x2.setText("x2");

        lab_p1.setText(".");
        lab_p2.setText(".");
        lab_p3.setText(".");

    }


    public void Apli_Regle_Pivot(String position_pivot1, String position_pivot2, String position_pivot3) {


        Reinit();
        if ("e1x1".equals(position_pivot1) || "e1x1".equals(position_pivot2)) {

            lab_e1.setText("x1");
            lab_p1.setText("e1");
            lab_x1.setText(".");
        }


        if ("e1x2".equals(position_pivot1) || "e1x2".equals(position_pivot2)) {
            lab_e1.setText("x2");
            lab_p1.setText("e1");
            lab_x2.setText(".");
        }


        if ("e2x1".equals(position_pivot1) || "e2x1".equals(position_pivot2)) {
            lab_e2.setText("x1");
            lab_p2.setText("e2");
            lab_x1.setText(".");
        }


        if ("e2x2".equals(position_pivot1) || "e2x2".equals(position_pivot2)) {
            lab_e2.setText("x2");
            lab_p2.setText("e2");
            lab_x2.setText(".");

        }


        if ("e3x1".equals(position_pivot1) || "e3x1".equals(position_pivot2)) {
            lab_e3.setText("x1");
            lab_p3.setText("e3");
            lab_x1.setText(".");

        }

        if ("e3x2".equals(position_pivot1) || "e3x2".equals(position_pivot2)) {
            lab_e3.setText("x2");
            lab_p3.setText("e3");
            lab_x2.setText(".");
        }

        if ("p3e1".equals(position_pivot3)) {
            lab_e1.setText(lab_p3.getText());
            lab_p3.setText(".");
            lab_p1.setText("e1");

        }
        if ("p3e2".equals(position_pivot3)) {
            lab_e2.setText(lab_p3.getText());
            lab_p3.setText(".");
            lab_p2.setText("e2");

        }
        if ("p1e2".equals(position_pivot3)) {
            lab_e2.setText(lab_p1.getText());
            lab_p2.setText("e2");
            lab_p1.setText(".");

        }
        if ("p1e3".equals(position_pivot3)) {
            lab_e3.setText(lab_p1.getText());
            lab_p3.setText("e3");
            lab_p1.setText(".");

        }
        if ("p2e1".equals(position_pivot3)) {
            lab_e1.setText(lab_p2.getText());
            lab_p2.setText(".");
            lab_p1.setText("e1");

        }
        if ("p2e3".equals(position_pivot3)) {
            lab_e1.setText(lab_p2.getText());
            lab_p2.setText(".");
            lab_p1.setText("e3");

        }


    }


    public void afiche_re1(String v) {
        effacer_re1();
        if ("zx1".equals(v)) {
            float re1 = Float.parseFloat(txt_be1.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e1x1.getText().toString().replace(",", "."));
            float re2 = Float.parseFloat(txt_be2.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e2x1.getText().toString().replace(",", "."));
            float re3 = Float.parseFloat(txt_be3.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e3x1.getText().toString().replace(",", "."));
            txt_re1.setText("" + re1);
            txt_re2.setText("" + re2);
            txt_re3.setText("" + re3);
        }
        if ("zx2".equals(v)) {
            float re1 = Float.parseFloat(txt_be1.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e1x2.getText().toString().replace(",", "."));
            float re2 = Float.parseFloat(txt_be2.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e2x2.getText().toString().replace(",", "."));
            float re3 = Float.parseFloat(txt_be3.getText().toString().replace(",", ".")) / Float.parseFloat(txt_e3x2.getText().toString().replace(",", "."));
            txt_re1.setText("" + re1);
            txt_re2.setText("" + re2);
            txt_re3.setText("" + re3);
        }

        if ("p1z".equals(v)) {
            float re1 = Float.parseFloat(txt_be1.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p1e1.getText().toString().replace(",", "."));
            float re2 = Float.parseFloat(txt_be2.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p1e2.getText().toString().replace(",", "."));
            float re3 = Float.parseFloat(txt_be3.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p1e3.getText().toString().replace(",", "."));
            txt_re1.setText("" + re1);
            txt_re2.setText("" + re2);
            txt_re3.setText("" + re3);
        }
        if ("p2z".equals(v)) {
            float re1 = Float.parseFloat(txt_be1.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p2e1.getText().toString().replace(",", "."));
            float re2 = Float.parseFloat(txt_be2.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p2e2.getText().toString().replace(",", "."));
            float re3 = Float.parseFloat(txt_be3.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p2e3.getText().toString().replace(",", "."));
            txt_re1.setText("" + re1);
            txt_re2.setText("" + re2);
            txt_re3.setText("" + re3);
        }
        if ("p3z".equals(v)) {
            float re1 = Float.parseFloat(txt_be1.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p3e1.getText().toString().replace(",", "."));
            float re2 = Float.parseFloat(txt_be2.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p3e2.getText().toString().replace(",", "."));
            float re3 = Float.parseFloat(txt_be3.getText().toString().replace(",", ".")) / Float.parseFloat(txt_p3e3.getText().toString().replace(",", "."));
            txt_re1.setText("" + re1);
            txt_re2.setText("" + re2);
            txt_re3.setText("" + re3);
        }
    }


    public void Interprete_solu(String fin_tableau) {
        switch (fin_tableau) {
            case "oui":
                Double a = Double.parseDouble(txt_bz.getText().toString()) * -1;
                txt_zmax.setText(a.toString());

                if (lab_e1.getText() != "e1" || lab_e2.getText() != "e1" || lab_e3.getText() != "e1") {
                    txt_e1.setText("0");
                    if (lab_e1.getText() != "e2" || lab_e2.getText() != "e2" || lab_e3.getText() != "e2") {
                        txt_e2.setText("0");
                        if (lab_e1.getText() != "e3" || lab_e2.getText() != "e3" || lab_e3.getText() != "e3") {
                            txt_e3.setText("0");
                        }

                    }

                }


                if ("e3".equals(lab_e1.getText()) && "x2".equals(lab_e2.getText()) && "x1".equals(lab_e3.getText())) {
                    txt_e3.setText(txt_be1.getText());
                    txt_x2.setText(txt_be2.getText());
                    txt_x1.setText(txt_be3.getText());

                }


                if ("e3".equals(lab_e1.getText()) && "x1".equals(lab_e2.getText()) && "x2".equals(lab_e3.getText())) {
                    txt_e3.setText(txt_be1.getText());
                    txt_x1.setText(txt_be2.getText());
                    txt_x2.setText(txt_be3.getText());

                }


                if ("x1".equals(lab_e1.getText()) && "e3".equals(lab_e2.getText()) && "x2".equals(lab_e3.getText())) {
                    txt_x1.setText(txt_be1.getText());
                    txt_e2.setText(txt_be2.getText());
                    txt_x2.setText(txt_be3.getText());

                }


                if ("x1".equals(lab_e1.getText()) && "x2".equals(lab_e2.getText()) && "e1".equals(lab_e3.getText())) {
                    txt_x1.setText(txt_be1.getText());
                    txt_x2.setText(txt_be2.getText());
                    txt_e1.setText(txt_be3.getText());

                }


                if ("x2".equals(lab_e1.getText()) && "x1".equals(lab_e2.getText()) && "e1".equals(lab_e3.getText())) {
                    txt_x2.setText(txt_be1.getText());
                    txt_x1.setText(txt_be2.getText());
                    txt_e1.setText(txt_be3.getText());

                }


                if ("x1".equals(lab_e1.getText()) && "x2".equals(lab_e2.getText()) && "e2".equals(lab_e3.getText())) {
                    txt_x1.setText(txt_be1.getText());
                    txt_x2.setText(txt_be2.getText());
                    txt_e2.setText(txt_be3.getText());

                }

                if ("x2".equals(lab_e1.getText()) && "x1".equals(lab_e2.getText()) && "e2".equals(lab_e3.getText())) {
                    txt_x2.setText(txt_be1.getText());
                    txt_x1.setText(txt_be2.getText());
                    txt_e2.setText(txt_be3.getText());

                }


                if ("x2".equals(lab_e1.getText()) && "e3".equals(lab_e2.getText()) && "x1".equals(lab_e3.getText())) {
                    txt_x2.setText(txt_be1.getText());
                    txt_e3.setText(txt_be2.getText());
                    txt_x1.setText(txt_be3.getText());

                }

                if ("x1".equals(lab_e1.getText()) && "e1".equals(lab_e2.getText()) && "x2".equals(lab_e3.getText())) {
                    txt_x1.setText(txt_be1.getText());
                    txt_e1.setText(txt_be2.getText());
                    txt_x2.setText(txt_be3.getText());

                }


                if ("x2".equals(lab_e1.getText()) && "e1".equals(lab_e2.getText()) && "x1".equals(lab_e3.getText())) {
                    txt_x2.setText(txt_be1.getText());
                    txt_e1.setText(txt_be2.getText());
                    txt_x1.setText(txt_be3.getText());
                }


                if ("e2".equals(lab_e1.getText()) && "x2".equals(lab_e2.getText()) && "x1".equals(lab_e3.getText())) {
                    txt_e2.setText(txt_be1.getText());
                    txt_x2.setText(txt_be2.getText());
                    txt_x1.setText(txt_be3.getText());

                }


                if ("e2".equals(lab_e1.getText()) && "x1".equals(lab_e2.getText()) && "x2".equals(lab_e3.getText())) {
                    txt_e2.setText(txt_be1.getText());
                    txt_x1.setText(txt_be2.getText());
                    txt_x2.setText(txt_be3.getText());

                }


        }
    }

}