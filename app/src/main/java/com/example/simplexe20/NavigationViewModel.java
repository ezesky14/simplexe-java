package com.example.simplexe20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * Created by EZE on 28/04/2016.
 */
public class NavigationViewModel {
    ArrayList<Integer> items;
    ArrayList<Class<?>> classes_destination;


    public NavigationViewModel(){

        //NB: Les items en meme temps que les activity referants à leur intent doivent avoir le meme index . Lajout doit se faire de facon syncro
        /*EXEMPLe: item 1 a un index =0 son activité referante MainActivity.class a un index =0

         */
        //Ajout des items
        items=new ArrayList<>();
        items.add(R.id.item_max_3v);
        items.add(R.id.item_max_2v);
        items.add(R.id.item_min_3v);
        items.add(R.id.item_rappel);


        items.add(0);



        items.add(R.id.item_parametres);
        items.add(R.id.item_apropos);

        //Ajout des classes
         classes_destination=new ArrayList<>();
        classes_destination.add(MainActivity.class);
        classes_destination.add(Maxi2vActivity.class);
        classes_destination.add(Mini3vActivity.class);
        classes_destination.add(RappelActivity.class);

        //Optionnel
        classes_destination.add(RappelActivity.class);


        classes_destination.add(SettingsActivity.class);
        classes_destination.add(AboutActivity.class);
    }




public void Generer_Mini_Drawer(final Activity context, final Toolbar toolbar, Bundle savedInstanceState, int pos_item){

}




    private void Gerer_Click_Item(int pos){



    }


    public void setupNavigationDrawerContent(final NavigationView navigationView, final Context context , final DrawerLayout drawerLayout , int pos_item) {

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
                        int position_item=0;
                       for (int i=0 ;i<items.size();i++){
                           if (items.get(i).equals(menuItem.getItemId())){
                               position_item=i;
                           }
                       }

                        String cl=classes_destination.get(position_item).getSimpleName();

                        if (!cl.equals(context.getClass().getSimpleName())){
                            Intent i1 = new Intent(context,classes_destination.get(position_item));
                            context.startActivity(i1);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            return true;

                        }else{
                            Toast.makeText(context, "Vous y etes dejà .", Toast.LENGTH_SHORT).show();
                            drawerLayout.closeDrawer(GravityCompat.START);

                        }
                        return true;
                    }
                });
    }

}
