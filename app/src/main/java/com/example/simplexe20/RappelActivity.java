package com.example.simplexe20;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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
import com.example.simplexe20.model_v1.Donnees3v;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class RappelActivity extends AppCompatActivity {
    ListView listview_rappel;
    final DataBaseWrapper db = new DataBaseWrapper(this);
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    public List<Donnees3v> result = new ArrayList<>();
    final NavigationViewModel nva = new NavigationViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel);
        listview_rappel = findViewById(R.id.listView_rappel);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            nva.setupNavigationDrawerContent(navigationView, RappelActivity.this, drawerLayout, R.id.item_rappel);
        }

        nva.setupNavigationDrawerContent(navigationView, RappelActivity.this, drawerLayout, R.id.item_rappel);
        result = db.recupAllProgLineaire();
        populateListViewFromDB();
        registerListClickCallback();
    }


    private void registerListClickCallback() {
        final ListView myList = (ListView) findViewById(R.id.listView_rappel);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long idInDB) {
                Donnees3v b = result.get(position);
                String type = db.getType_simplexe(String.valueOf(b.getId()));
                if (type.equals("MAXI_TROIS_VARIABLES") || type.equals("MINI_TROIS_VARIABLES")) {
                    Intent intent = new Intent(RappelActivity.this, Resultat3vTableau.class);
                    intent.putExtra("id", b.getId());
                    Log.i("id sended", b.getId());
                    startActivity(intent);

                } else if (type.equals("MAXI_DEUX_VARIABLES")) {
                    Intent intent = new Intent(RappelActivity.this, Resultat2vTableau.class);
                    intent.putExtra("id", b.getId());
                    Log.i("id sended", b.getId());
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
                        getMenuInflater().inflate(R.menu.menu_rappel, menu);
                        Donnees3v b = result.get(position);
                        menu.setHeaderTitle("Options de l'ID " + b.getId());
                        menu.setHeaderIcon(R.drawable.icone_small);

                        //SUPPRESSION DU PROGRAMME LINEAIRE
                        menu.findItem(R.id.delete_donnees).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(RappelActivity.this, "deleting  ....", Toast.LENGTH_SHORT).show();
                                Donnees3v b = result.get(position);
                                //   db.delete_donnees("DELETE FROM donnees_3v WHERE id ="+String.valueOf(b.getId()));
                                //  int nb_tableau=Integer.parseInt(db.Nb_tableaux(complete.getText().toString()));
                                int ind = 1;
                                while (ind <= 4) {
                                    //    db.delete_donnees("DELETE FROM tableau"+ind+" WHERE id ="+String.valueOf(b.getId()));
                                    ind++;
                                }
                                Toast.makeText(RappelActivity.this, "Suppression de l' identifiant " + b.getId() + " éfectuée", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        });

                        menu.findItem(R.id.affich_donnees).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Donnees3v b = result.get(position);
                                Intent intent = new Intent(RappelActivity.this, AffichProgActivity.class);
                                Log.i("id sended", b.getId());
                                intent.putExtra("id", b.getId());
                                startActivity(intent);
                                return false;
                            }
                        });

                        //AFFICHER LA FORME STANDARD
                        menu.findItem(R.id.affich_standard).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Donnees3v b = result.get(position);
                                Intent intent = new Intent(RappelActivity.this, AffichFormStandActivity.class);
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
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateListViewFromDB() {
        ArrayAdapter<Donnees3v> adapter = new MyListAdapter();
        ListView myList = findViewById(R.id.listView_rappel);
        myList.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Donnees3v> {
        public MyListAdapter() {
            super(RappelActivity.this, R.layout.detail_simplexe, result);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.detail_simplexe, parent, false);
            }
            Donnees3v donnees3v = result.get(position);
            TextView identifiant = (TextView) itemView.findViewById(R.id.identifiant1);
            TextView type_simplexe = (TextView) itemView.findViewById(R.id.type_simplexe);
            TextView nb_tb = (TextView) itemView.findViewById(R.id.nb_tb);
            identifiant.setText("" + donnees3v.getId());
            type_simplexe.setText(donnees3v.getTYPE_SIMPLEXE());
            nb_tb.setText("" + donnees3v.getNB_TABLEAU());
            return itemView;
        }
    }
}
