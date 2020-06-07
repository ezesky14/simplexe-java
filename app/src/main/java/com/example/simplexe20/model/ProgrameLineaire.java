package com.example.simplexe20.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProgrameLineaire {
    private String id;
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
    private int nbTableau = 0;
    private int roundNumber = -1;

    public ProgrameLineaire(SimplexeType type, int roundNumber) {
        this.roundNumber = roundNumber;
        this.type = type;
    }

    public ProgrameLineaire(SimplexeType type) {
        this.type = type;
    }

    public ProgrameLineaire() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNbTableau() {
        return nbTableau;
    }

    public void setNbTableau(int nbTableau) {
        this.nbTableau = nbTableau;
    }

    public List<ColonneTableau> getColList() {
        return colList;
    }

    public void setColList(List<ColonneTableau> colList) {
        this.colList = colList;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setColList() {
        Log.i("equationVWithoutPValues", String.valueOf(equationValuesWithoutPValues.size()));
        Log.i("equationPValues", String.valueOf(getAllEquationValues().size()));
        List<ColonneTableau> list = new ArrayList<>();
        String[] vdbList = {"e1", "e2", "e3", "Z"};
        String[] vhbList = {"x1", "x2", "x3", "B"};

        if (type == SimplexeType.MAXI_DEUX_VARIABLES) {
            vhbList = new String[]{"x1", "x2", "B"};
        }

        int vdbPos = 0;
        int vhbPos;
        int dataIndex = 0;
        for (String vdb : vdbList) {
            vhbPos = 0;
            for (String vhb : vhbList) {
                if (!(vhb.equals("B") && vdb.equals("Z"))) {
                    int id = dataIndex;
                    KeyVal vhbKeyVal = new KeyVal(vhbPos, vhb);
                    KeyVal vdbKeyVal = new KeyVal(vdbPos, vdb);
                    ColonneTableau col = SimplexeUtils.addColumnWithData(vhbKeyVal, vdbKeyVal, id, equationValuesWithoutPValues.get(dataIndex));
                    list.add(col);
                    vhbPos++;
                    dataIndex++;
                }
            }
            vdbPos++;
        }
        this.colList = list;
    }


    public SimplexeType getType() {
        return type;
    }

    public void setType(SimplexeType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = SimplexeType.valueOf(type);
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

    public String getzEquation() {
        return zEquation;
    }

    public List<Float> getEquationValuesWithoutPValues() {
        return equationValuesWithoutPValues;
    }

    public void setEquationValuesWithoutPValues(List<Float> equationValuesWithoutPValues) {
        this.equationValuesWithoutPValues = equationValuesWithoutPValues;
    }

    public void pushEquationValuesWithoutPValues(ArrayList<Float> values) {
        equationValuesWithoutPValues.addAll(values);
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
            return "x" + number;
        } else {
            return "y" + number;
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

    private float[] getPValues(int vdbIndex) {
        float[] values = new float[3];
        for (int a = 0; a < 3; a++) {
            if (a == vdbIndex) {
                values[a] = 1;
            } else {
                values[a] = 0;
            }
        }

        return values;
    }

    private float[] addOtherValues(ArrayList<Float> vhbValues, float[] pValues, float b) {
        // float[] r = {x1, x2, x3, 0, 0, 0, 0, 0};
        float[] init = new float[getColumnsNumberPerLine()];
        int a = 0;
        for (float v : vhbValues) {
            init[a] = v;
            a++;
        }
        for (float v : pValues) {
            init[a] = v;
            a++;
        }

        //Pour Z
        if (b == -1) {
            init[a] = 0;
            init[a + 1] = 0;
        } else {
            init[a] = b;
        }

        return init;
    }

    private ArrayList<Float> getVDBValues(float x1, float x2, float x3) {
        ArrayList<Float> v = new ArrayList<>();
        v.add(x1);
        v.add(x2);
        if (type == SimplexeType.MAXI_TROIS_VARIABLES) {
            v.add(x3);
        }
        return v;
    }

    public void setEquation1(float x1, float x2, float x3, float b) {
        ArrayList<Float> init = getVDBValues(x1, x2, x3);
        float[] pValues = getPValues(0);
        float[] r = addOtherValues(init, pValues, b);
        init.add(b);
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(init);
        equation1Values = r;
        //this.equation1 = x1 + getInconnuVariable("1") + " + " + x2 + getInconnuVariable("2") + " + " + x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }

    public void setEquation2(float x1, float x2, float x3, float b) {
        ArrayList<Float> init = getVDBValues(x1, x2, x3);
        float[] pValues = getPValues(1);
        float[] r = addOtherValues(init, pValues, b);
        init.add(b);
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(init);
        equation2Values = r;
        //this.equation2 = x1 + getInconnuVariable("1") + " + " + x2 + getInconnuVariable("2") + " + " + x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }

    public void setEquation3(float x1, float x2, float x3, float b) {
        ArrayList<Float> init = getVDBValues(x1, x2, x3);
        float[] pValues = getPValues(2);
        float[] r = addOtherValues(init, pValues, b);
        init.add(b);
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(init);
        equation3Values = r;
        //this.equation3 = x1 + getInconnuVariable("1") + " + " + x2 + getInconnuVariable("2") + " + " + x3 + getInconnuVariable("3") + " " + getSign() + " " + b;
    }

    public void setzEquation(float x1, float x2, float x3) {
        ArrayList<Float> init = getVDBValues(x1, x2, x3);
        float[] pValues = getPValues(-1);
        float[] r = addOtherValues(init, pValues, -1);
        pushEquationValues(r);
        pushEquationValuesWithoutPValues(init);
        zEquationValues = r;
        //this.zEquation = x1 + getInconnuVariable("1") + " + " + x2 + getInconnuVariable("2") + " + " + x3 + getInconnuVariable("3");
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
