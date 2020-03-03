package com.example.simplexe20.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableauV2 implements Parcelable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private float min;
    private float max;
    private float pivot;
    private String position_pivot;
    private String position_v_entrante;
    private ColonneTableau ColvEntrante;
    private ColonneTableau ColvSortante;

    public ColonneTableau getColvEntrante() {
        return ColvEntrante;
    }

    public void setColvEntrante(ColonneTableau colvEntrante) {
        ColvEntrante = colvEntrante;
    }

    public ColonneTableau getColvSortante() {
        return ColvSortante;
    }

    public void setColvSortante(ColonneTableau colvSortante) {
        ColvSortante = colvSortante;
    }

    private String position_v_sortante;
    private boolean isLast = false;
    private List<ColonneTableau> pivotColonnes;
    private String programme_lineaireId;
    public String getProgramme_lineaireId() {
        return programme_lineaireId;
    }

    public void setProgramme_lineaireId(String programme_lineaireId) {
        this.programme_lineaireId = programme_lineaireId;
    }

    int nb_negatif=0;
    int nb_positif=0;
    int realPosZ;
    String indice;

    public int getNumberTotalcolPerLine() {
        return numberTotalcolPerLine;
    }

    public void setNumberTotalcolPerLine(int numberTotalcolPerLine) {
        this.numberTotalcolPerLine = numberTotalcolPerLine;
    }

    private int numberTotalcolPerLine;

    public int getRealPosZ() {
        return realPosZ;
    }

    public void setRealPosZ(int realPosZ) {
        this.realPosZ = realPosZ;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public List<ColonneTableau> getPivotColonnes() {
        return pivotColonnes;
    }
    public float[] getLignePivotValues() {
        return toFloat(pivotColonnes);
    }

    public void setPivotColonnes(List<ColonneTableau> pivotColonnes) {
        this.pivotColonnes = pivotColonnes;
    }

    public ArrayList<ColonneTableau> getColonnes() {
        return colonnes;
    }

    public void setColonnes(ArrayList<ColonneTableau> colonnes) {
        this.colonnes = colonnes;
    }

    ArrayList<ColonneTableau> colonnes;

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getPivot() {
        return pivot;
    }

    public void setPivot(float pivot) {
        this.pivot = pivot;
    }

    public String getPosition_pivot() {
        return position_pivot;
    }

    public void setPosition_pivot(String position_pivot) {
        this.position_pivot = position_pivot;
    }

    public String getPosition_v_entrante() {
        return position_v_entrante;
    }

    public void setPosition_v_entrante(String position_v_entrante) {
        this.position_v_entrante = position_v_entrante;
    }

    public String getPosition_v_sortante() {
        return position_v_sortante;
    }

    public void setPosition_v_sortante(String position_v_sortante) {
        this.position_v_sortante = position_v_sortante;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    /*
         e1,e2,e3,z
         x1,x2,x3 ,point1, point2, point3 , B , R
         */
    public TableauV2() {
        colonnes = initTableau();
    }
    public TableauV2(List<Float> data, int numberTotalcolPerLine) {
        this.numberTotalcolPerLine = numberTotalcolPerLine;
        colonnes = initTableau(data);
    }

    private Map<String,Integer> vdbList2() {
        String[] res = {"e1","e2","e3","Z"};
        return convertToMap(res);
    }
    private  Map<String,Integer> convertToMap(String[] list){
        Map<String, Integer> myMap = new HashMap();
        int a =0;
        for (String l: list){
            myMap.put(l, a);
            a++;
        }
        return myMap;
    }

    private Map<String,Integer> vhbList2() {
        String[] res = {"x1","x2","x3","p1","p2","p3","B","R"};
        return convertToMap(res);
    }

    private String[] vdbList() {
        String[] res = {"e1","e2","e3","Z"};
        return res;
    }

    private String[] vhbList() {
        String[] res = {"x1","x2","x3","p1","p2","p" +
                "" +
                "3","B","R"};
        return res;
    }

   public ColonneTableau addColumn(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id) {
       ColonneTableau col = new ColonneTableau();
       col.setId(id);
       col.setVhbPosition(vhbKeyVal.getKey());
       col.setVhbValue(vhbKeyVal.getValue());
       col.setVdbPosition(vdbKeyVal.getKey());
       col.setVdbValue(vdbKeyVal.getValue());
       col.setPositionValue(vdbKeyVal.getValue()+""+vhbKeyVal.getValue());
       col.setCalculated(true);

       return col;
   }

    public ColonneTableau addColumnWithData(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id, float data) {
        ColonneTableau col = new ColonneTableau();
        col.setId(id);
        col.setVhbPosition(vhbKeyVal.getKey());
        col.setVhbValue(vhbKeyVal.getValue());
        col.setVdbPosition(vdbKeyVal.getKey());
        col.setVdbValue(vdbKeyVal.getValue());
        col.setPositionValue(vdbKeyVal.getValue()+""+vhbKeyVal.getValue());
        col.setCalculated(true);
        col.setValue(data);

        return col;

    }
    private ArrayList<ColonneTableau> getColumnsOfTable(){
        //Map<String, Integer> vdbList = vdbList();
        //Map<String, Integer> vhbList = vhbList();
        ArrayList<ColonneTableau> list = new ArrayList<>();
        int vdbPos = 0;
        int vhbPos = 0;
        int id = 0;
        for (String vdb : vdbList()) {
            vhbPos = 0;
            for (String vhb : vhbList()) {
                KeyVal vhbKeyVal = new KeyVal(vhbPos, vhb);
                KeyVal vdbKeyVal = new KeyVal(vdbPos, vdb);
                ColonneTableau col = SimplexeUtils.addColumn(vhbKeyVal, vdbKeyVal, id);
                list.add(col);
                vhbPos++;
                id++;
            }
            vdbPos++;
        }
       return list;
    }

    private ArrayList<ColonneTableau> getColumnsOfTable(List<Float> data){
        //Map<String, Integer> vdbList = vdbList();
        //Map<String, Integer> vhbList = vhbList();
        ArrayList<ColonneTableau> list = new ArrayList<>();
        int vdbPos = 0;
        int vhbPos = 0;
        int dataIndex = 0;
        for (String vdb : vdbList()) {
            vhbPos = 0;
            for (String vhb : vhbList()) {
                Log.i("getColumnsOfTable", dataIndex+"");
                int id = dataIndex;

                KeyVal vhbKeyVal = new KeyVal(vhbPos, vhb);
                KeyVal vdbKeyVal = new KeyVal(vdbPos, vdb);

                Log.i("getColumnsOfTable vhb", vhb+"");
                Log.i("getColumnsOfTable vdb", vdb+"");
                ColonneTableau col = SimplexeUtils.addColumnWithData(vhbKeyVal, vdbKeyVal, id, data.get(dataIndex));
                list.add(col);
                vhbPos++;
                dataIndex++;
            }
            vdbPos++;
        }
        return list;
    }



    private ArrayList <ColonneTableau> initTableau() {
        ArrayList <ColonneTableau> tableList = getColumnsOfTable();
        //La derniere colonne n'est pas calculable
        tableList.get(tableList.size()-1).setCalculated(false);
        return tableList;
    }
    public ArrayList <ColonneTableau> initTableau(List<Float> data) {
       int i =0;
       for (float v:data){
           Log.w("index "+i,v+"");
           i++;
       }
        ArrayList <ColonneTableau> tableList = getColumnsOfTable(data);
        //La derniere colonne n'est pas calculable
        tableList.get(tableList.size()-1).setCalculated(false);
        return tableList;
    }

    public ColonneTableau getCol(int vdbPosition, int lignePosition) {
        int res = ( numberTotalcolPerLine * (vdbPosition)) + lignePosition;
        return colonnes.get(res);
    }

    public float getVal(String vdbValue, int lignePosition) {
       /* on a e3  on souhaite retrouver la position vdb affectee a ca
       *
       *  */
       int vdbPosition = vdbList2().get(vdbValue);
       return getCol(vdbPosition, lignePosition).getValue();
    }

    public List<ColonneTableau> getLine(String vdbValue) {
        // return tb_Z[0], tb_Z[1], tb_Z[2], tb_Z[3], tb_Z[4], tb_Z[5]
        int vdbPosition = vdbList2().get(vdbValue);
        int start = (7 * vdbPosition) + vdbPosition;
        return colonnes.subList(start, start+7+1);
    }
    public void setLine(List<ColonneTableau> list, String vdbValue) {
        int vdbPosition = vdbList2().get(vdbValue);
        int start =(7 * vdbPosition)  + vdbPosition;
        Collection<ColonneTableau> c = colonnes.subList(start, start+7+1);
        int b =0;
        for (int a = start; a<=start+7;a++){
            colonnes.set(a, list.get(b));
            b++;
        }
       // colonnes.removeAll(c);
        //colonnes.addAll(start, list);
    }
    public float[] getLineValues(String vdbValue) {
        int vdbPosition = vdbList2().get(vdbValue);
        int start = (7 * vdbPosition) + vdbPosition;
        return toFloat(colonnes.subList(start, start+7+1));
    }

    public void verifTableau(){
        nb_positif = 0;
        for (int i=0;i<numberTotalcolPerLine;i++){
            if (getVal("Z", i) > 0){
                nb_positif++;
            }
        }
        isLast = false;
        if (nb_positif == 0){
            isLast = true;
        }
    }

    private float[] toFloat (List<ColonneTableau> list) {
       float [] res = new float[list.size()];
       int a =0;
       for (ColonneTableau tb : list) {
           res[a] = tb.getValue();
           a++;
       }
       Log.w("size  = ", res.length+"");
      return res;
    }

    public static final Parcelable.Creator<TableauV2> CREATOR
            = new Parcelable.Creator<TableauV2>() {
        public TableauV2 createFromParcel(Parcel in) {
            return new TableauV2(in);
        }

        public TableauV2[] newArray(int size) {
            return new TableauV2[size];
        }
    };

    private TableauV2(Parcel in) {
        min = in.readFloat();
        max = in.readFloat();
        pivot = in.readFloat();
        position_pivot = in.readString();
        position_v_entrante = in.readString();
        position_v_sortante = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isLast = in.readBoolean();
        }else{
            isLast = in.readByte() != 0;
        }
        pivotColonnes = in.readArrayList(TableauV2.class.getClassLoader());

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.position_pivot);
        dest.writeString(this.position_v_entrante);
        dest.writeString(this.position_v_sortante);
        dest.writeFloat(this.min);
        dest.writeFloat(this.max);
        dest.writeFloat(this.pivot);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(this.isLast);
        }else{
            dest.writeByte((byte) (this.isLast ? 1 : 0));
        }
        dest.writeList(this.pivotColonnes);
    }
}
