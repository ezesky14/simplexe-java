package com.example.simplexe20.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TableauList extends ArrayList<TableauV2> implements Parcelable
{
    public TableauList()
    {

    }

    public TableauList(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public TableauList createFromParcel(Parcel in)
        {
            return new TableauList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            TableauV2 tableauV2 = this.get(i); //On vient lire chaque objet personne
            dest.writeString(tableauV2.getPosition_pivot());
            dest.writeString(tableauV2.getPosition_v_entrante());
        }
    }

    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            TableauV2 tableauV2  = new TableauV2();
            tableauV2.setPosition_v_entrante(in.readString());
            tableauV2.setPosition_pivot(in.readString());
            this.add(tableauV2);
        }

    }
}
