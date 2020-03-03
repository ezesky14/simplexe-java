package com.example.simplexe20;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.simplexe20.model.DataBaseWrapper;
import com.example.simplexe20.model.Donnees_3v;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class RappelActivity extends AppCompatActivity {


    ListView listview_rappel;
    DataBaseWrapper db = new DataBaseWrapper(this);

    final String id="id";
    final String lieu="lieu";

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    public List<Donnees_3v> result=new ArrayList<>();

    Menu men;
    NavigationViewModel nva=new NavigationViewModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel);
        listview_rappel=(ListView)findViewById(R.id.listView_rappel);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {

            nva.setupNavigationDrawerContent(navigationView,RappelActivity.this,drawerLayout,R.id.item_rappel);
        }

        nva.setupNavigationDrawerContent(navigationView,RappelActivity.this,drawerLayout,R.id.item_rappel);

      result=db.Recup_all_id();


/*
        final Button search_button=(Button) findViewById(R.id.Delete_button);
        complete=(AutoCompleteTextView) findViewById(R.id.id_edit);
        complete.setThreshold(0);



        // ASSOCIATION DE NOTRE LISTE DE LIGNE A UN ARRAY ADAPTER
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,android.R.layout.simple_dropdown_item_1line,  db.Recup_all_id()) ;
        // … puis on indique que notre AutoCompleteTextView utilise cet adaptateur
        complete.setAdapter(adapter) ;

*/
/*
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( this,android.R.layout.simple_list_item_1, db.Recup_all_id()) ;
        listview_rappel.setAdapter(adapter2);

*/





        populateListViewFromDB();
        registerListClickCallback();

    }









    private void registerListClickCallback() {
        final ListView myList = (ListView) findViewById(R.id.listView_rappel);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


                Donnees_3v b=result.get(position);

                      System.out.println("ID = "+b.getId());

                String type=db.Recup_type_simplexe(String.valueOf(b.getId()));
                System.out.println("type = "+type);
                if (type.equals("maxi_3v") || type.equals("mini_3v")) {
                    Intent intent = new Intent(RappelActivity.this, Resultat3v_Tableau.class);
                    intent.putExtra("id", String.valueOf(b.getId()));

                    startActivity(intent);

                }else if(type.equals("maxi_2v") ){
                    Intent intent = new Intent(RappelActivity.this, Resultat2v_Tableau.class);
                    intent.putExtra("id", String.valueOf(b.getId()));

                    startActivity(intent);

                }
            }
        });




        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {




                myList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        getMenuInflater().inflate(R.menu.menu_rappel,menu);
                        Donnees_3v b=result.get(position);
                        menu.setHeaderTitle("Options de l'ID "+b.getId());
                        menu.setHeaderIcon(R.drawable.icone_small);


                       //SUPPRESSION DU PROGRAMME LINEAIRE
                        menu.findItem(R.id.delete_donnees).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(RappelActivity.this,"deleting  ....",Toast.LENGTH_SHORT).show();


                                Donnees_3v b=result.get(position);

                             //   db.delete_donnees("DELETE FROM donnees_3v WHERE id ="+String.valueOf(b.getId()));


                                //  int nb_tableau=Integer.parseInt(db.Nb_tableaux(complete.getText().toString()));
                                int ind=1;
                                while (ind<=4){
                                //    db.delete_donnees("DELETE FROM tableau"+ind+" WHERE id ="+String.valueOf(b.getId()));
                                    ind++;
                                }

                                Toast.makeText(RappelActivity.this,"Suppression de l' identifiant "+String.valueOf(b.getId())+" éfectuée",Toast.LENGTH_LONG).show();



                                return false;
                            }
                        });




                        //AFFICHER PROGRAMME LINEAIRE
                        menu.findItem(R.id.affich_donnees).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                Donnees_3v b=result.get(position);
                                Intent intent = new Intent(RappelActivity.this, AffichProgActivity.class);
                                intent.putExtra("id", String.valueOf(b.getId()));

                                startActivity(intent);
                                return false;
                            }
                        });



                        //AFFICHER LA FORME STANDARD
                        menu.findItem(R.id.affich_standard).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Donnees_3v b=result.get(position);
                                Intent intent = new Intent(RappelActivity.this, Affich_FormStandActivity.class);
                                intent.putExtra("id", String.valueOf(b.getId()));

                                startActivity(intent);
                                return false;
                            }
                        });

                    }
                });



                return false;
            }
        });
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







    private void populateListViewFromDB(  ) {
        ArrayAdapter <Donnees_3v> adapter =new MyListAdapter( );

        // Set the adapter for the list view
        ListView myList = (ListView) findViewById(R.id.listView_rappel);
        myList.setAdapter(adapter);











    }











    private class MyListAdapter  extends ArrayAdapter<Donnees_3v> {

        public MyListAdapter(){

            super(RappelActivity.this,R.layout.detail_simplexe,result);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.detail_simplexe, parent, false);
            }
            Donnees_3v currentBus = result.get(position);



            TextView identifiant = (TextView) itemView.findViewById(R.id.identifiant1);
            TextView type_simplexe = (TextView) itemView.findViewById(R.id.type_simplexe);
            TextView nb_tb = (TextView) itemView.findViewById(R.id.nb_tb);


            identifiant.setText(""+currentBus.getId());
            type_simplexe.setText(currentBus.getTYPE_SIMPLEXE());
            nb_tb.setText(""+currentBus.getNB_TABLEAU());



            return itemView;
        }


    }
/*
    private void registerListClickCallback() {
        ListView myList = (ListView) findViewById(R.id.listView_result);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {


                Bus b=result.get(position);
                Intent intent = new Intent(TestResultActivity.this, ResultActivity.class);
                intent.putExtra("id",b.getID_BUS());

                startActivity(intent);
            }
        });
    }

    */








}
