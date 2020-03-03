package com.example.simplexe20.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProgrameLineaire {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String zEquation;
    private float[] zEquationValues;
    private String equation1;
    private float[] equation1Values;
    private String equation2;
    private float[] equation2Values;
    private String equation3;
    private float[] equation3Values;
    private SimplexeType type;
    private List<Float> allEquationValues = new ArrayList<>();
    private List<Float> equationValuesWithoutPValues = new ArrayList<>();
    private List<ColonneTableau> colList = new ArrayList<>();
    int nb_tableau = 0;

    public int getNb_tableau() {
        return nb_tableau;
    }

    public void setNb_tableau(int nb_tableau) {
        this.nb_tableau = nb_tableau;
    }

    public List<ColonneTableau> getColList() {
        return colList;
    }

    public void setColList(List<ColonneTableau> colList) {
        this.colList = colList;
    }


    public void setColList() {
        List<ColonneTableau> list = new ArrayList<>();
        String[] vdbList = {"e1","e2","e3","Z"};
        String[] vhbList = {"x1","x2","x3","B"};
        int vdbPos = 0;
        int vhbPos = 0;
        int dataIndex = 0;
        for (String vdb : vdbList) {
            vhbPos = 0;
            for (String vhb : vhbList) {
                if (dataIndex<15){
                    Log.i("getColumnsOfTable", dataIndex+"");
                    int id = dataIndex;

                    KeyVal vhbKeyVal = new KeyVal(vhbPos, vhb);
                    KeyVal vdbKeyVal = new KeyVal(vdbPos, vdb);

                    Log.i("getColumnsOfTable vhb", vhb+"");
                    Log.i("getColumnsOfTable vdb", vdb+"");
                    ColonneTableau col = SimplexeUtils.addColumnWithData(vhbKeyVal, vdbKeyVal, id, equationValuesWithoutPValues.get(dataIndex));
                    list.add(col);
                    vhbPos++;
                    dataIndex++;
                }
            }
            vdbPos++;
        }

    }



    public SimplexeType getType() {
        return type;
    }

    public void setType(SimplexeType type) {
        this.type = type;
    }

    public void setType(String type) {

    }


    public List<Float> getAllEquationValues() {
        return allEquationValues;
    }

    public void setAllEquationValues(List<Float> allEquationValues) {
        this.allEquationValues = allEquationValues;
    }

    public void pushEquationValues(float[] values) {
        for (float v : values) {
            allEquationValues.add(v);
        }
    }

    public float[] getzEquationValues() {
        return zEquationValues;
    }

    public void setzEquationValues(float[] zEquationValues) {
        this.zEquationValues = zEquationValues;
    }

    public float[] getEquation1Values() {
        return equation1Values;
    }

    public void setEquation1Values(float[] equation1Values) {
        this.equation1Values = equation1Values;
    }

    public float[] getEquation2Values() {
        return equation2Values;
    }

    public void setEquation2Values(float[] equation2Values) {
        this.equation2Values = equation2Values;
    }

    public float[] getEquation3Values() {
        return equation3Values;
    }

    public void setEquation3Values(float[] equation3Values) {
        this.equation3Values = equation3Values;
    }

    public ProgrameLineaire(SimplexeType type) {
        this.type = type;
    }

    public ProgrameLineaire() {
    }


    public String getzEquation() {
        return zEquation;
    }

    public List<Float> getEquationValuesWithoutPValues() {
        return equationValuesWithoutPValues;
    }

    public void setEquationValuesWithoutPValues(List<Float> equationValuesWithoutPValues) {
        this.equationValuesWithoutPValues = equationValuesWithoutPValues;
    }

    public void pushEquationValuesWithoutPValues(float[] values) {
        for (float v : values) {
            equationValuesWithoutPValues.add(v);
        }
    }



    private String getSign() {
        //Definir les signes si >= ou <=
        if (type == SimplexeType.MAXI_TROIS_VARIABLES || type == SimplexeType.MAXI_DEUX_VARIABLES) {
            return ">=";
        } else {
            return "<=";
        }

    }

    private String getInconnuVariable(String number) {
        //Definir les signes si >= ou <=
        if (type == SimplexeType.MAXI_TROIS_VARIABLES || type == SimplexeType.MAXI_DEUX_VARIABLES) {
            return "x"+number;
        } else {
            return "y"+number;
        }

    }

    private float[] getVals(String[] inputs, float[] others) {
        float[] outputs = null;
        int a = 0;
        for (String v : inputs) {
            outputs[a] = Float.parseFloat(v.replace("x" + (a + 1), ""));
        }

        for (float v : others) {
            outputs[a] = v;
        }

        return outputs;
    }

    public String getEquation1() {
        return equation1;
    }
    public String getEquation2() {
        return equation2;
    }
    public String getEquation3() {
        return equation3;
    }
    public void setEquation1(float x1, float x2, float x3, float b) {
        float[] r = {x1, x2, x3, 1, 0, 0, b, 0};
        float[] r2 = {x1, x2, x3, b};
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(r2);
        equation1Values = r;
        this.equation1 = x1 + getInconnuVariable("1") + " + "+x2 + getInconnuVariable("2") + " + "+x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }


    public void setEquation2(float x1, float x2, float x3, float b) {
        float[] r = {x1, x2, x3, 0, 1, 0, b, 0};
        float[] r2 = {x1, x2, x3, b};
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(r2);
        equation2Values = r;
        this.equation2  = x1 + getInconnuVariable("1") + " + "+x2 + getInconnuVariable("2") + " + "+x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }



    public void setEquation3(float x1, float x2, float x3, float b) {
        float[] r = {x1, x2, x3, 0, 0, 1, b, 0};
        float[] r2 = {x1, x2, x3, b};
        equation3Values = r;
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(r2);
        this.equation3  = x1 + getInconnuVariable("1") + " + "+x2 + getInconnuVariable("2") + " + "+x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }

    public void setzEquation(float x1, float x2, float x3) {
        float[] r = {x1, x2, x3, 0, 0, 0, 0, 0};
        float[] r2 = {x1, x2, x3};
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(r2);
        zEquationValues = r;
        this.zEquation =  x1 + getInconnuVariable("1") + " + "+x2 + getInconnuVariable("2") + " + "+x3 + getInconnuVariable("3");
    }


    public int getColumnsNumberPerLine() {
        int res = 0;
        if (type == SimplexeType.MAXI_TROIS_VARIABLES || type == SimplexeType.MINI_TROIS_VARIABLES) {
            res = 8;
        }
        if (type == SimplexeType.MAXI_DEUX_VARIABLES || type == SimplexeType.MINI_DEUX_VARIABLES) {
            res = 7;
        }
        return res;
    }

    private int getNumberColumnPerTypeOfCalculation() {
        int res = 0;
        if (type == SimplexeType.MAXI_TROIS_VARIABLES || type == SimplexeType.MINI_TROIS_VARIABLES) {
            res = 31;
        }
        if (type == SimplexeType.MAXI_DEUX_VARIABLES || type == SimplexeType.MINI_DEUX_VARIABLES) {
            res = 27;
        }
        return res;
    }
}
