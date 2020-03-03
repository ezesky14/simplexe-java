package com.example.simplexe20;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MiniDrawerActivity extends AppCompatActivity {
NavigationViewModel nv=new NavigationViewModel();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_drawer);


        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("kk");
        nv.Generer_Mini_Drawer(MiniDrawerActivity.this,toolbar,savedInstanceState,3);
    }




}
