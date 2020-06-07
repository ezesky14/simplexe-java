package com.example.simplexe20;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

/**
 * Created by EZE on 28/04/2016.
 */
public class NavigationViewModel {

    final HashMap<Integer, Class<?>> items = new HashMap<>();

    public NavigationViewModel() {
        //NB: Les items en meme temps que les activity referants à leur intent doivent avoir le meme index . L'ajout doit se faire de facon syncro
        /*Exemple: item 1 a un index egal a 0 son activité referante MainActivity.class a un index egal a 0

         */
        items.put(R.id.item_max_3v, MainActivity.class);
        items.put(R.id.item_max_2v, Maxi2vActivity.class);
        items.put(R.id.item_min_3v, Mini3vActivity.class);
        items.put(R.id.item_rappel, RappelActivity.class);
        items.put(0, null);
        items.put(R.id.item_parametres, SettingsActivity.class);
        items.put(R.id.item_apropos, AboutActivity.class);
    }

    public void setupNavigationDrawerContent(final NavigationView navigationView, final Context context, final DrawerLayout drawerLayout, int pos_item) {
        //L'affichage du NavigationView se fera en fonction de l'activité ou l'on se trouve
        // On procedera comme suit
        /*1) On verifie si la classe renvoyée a travers "nom_classe" est la meme que la classe de destination (celle qu' on ouvrira lors du clic )
             Si true alors on n'appelle pas d'intent
             Si false on appelle un intent et on le configure
         */

        navigationView.getMenu().findItem(pos_item).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Class classe = items.get(menuItem.getItemId());
                        String cl = classe.getSimpleName();
                        if (!cl.equals(context.getClass().getSimpleName())) {
                            Intent i1 = new Intent(context, classe);
                            context.startActivity(i1);
                        } else {
                            Toast.makeText(context, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

}
