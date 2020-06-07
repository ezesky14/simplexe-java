package com.example.simplexe20.model;

import android.util.Log;

/*
 * Exemple :
 * e1x1 a comme :
 * id=>
 * vhbPosition => 0
 * vdbPosition => 0
 * vhbValue => x1
 * vdbValue => e1
 * positionValue =>e1x1
 * value=> */
@SuppressWarnings("unused")
public class ColonneTableau {
    private int id;
    private String vhbValue;
    private String vdbValue;
    private int vhbPosition;
    private int vdbPosition;
    private String positionValue;
    private float value = 0;
    private int lignePosition;
    private int roundNumber = -1;


    public ColonneTableau() {

    }

    public ColonneTableau(float value, int roundNumber) {
        this.value = value;
        //this.roundNumber = roundNumber;
    }

    public ColonneTableau(int roundNumber) {
        //this.roundNumber = roundNumber;
    }


    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        //this.roundNumber = roundNumber;
    }

    public int getLignePosition() {
        return lignePosition;
    }

    public void setLignePosition(int lignePosition) {
        this.lignePosition = lignePosition;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }

    private boolean isCalculated = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVhbValue() {
        return vhbValue;
    }

    public void setVhbValue(String vhbValue) {
        this.vhbValue = vhbValue;
    }

    public String getVdbValue() {
        return vdbValue;
    }

    public void setVdbValue(String vdbValue) {
        this.vdbValue = vdbValue;
    }

    public int getVhbPosition() {
        return vhbPosition;
    }

    public void setVhbPosition(int vhbPosition) {
        this.vhbPosition = vhbPosition;
    }

    public int getVdbPosition() {
        return vdbPosition;
    }

    public void setVdbPosition(int vdbPosition) {
        this.vdbPosition = vdbPosition;
    }

    public String getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(String positionValue) {
        this.positionValue = positionValue;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        Log.i("value", value + "");
        Log.i("roundNumber", roundNumber + "");
        if (roundNumber != -1) {
            String s = String.format("%." + roundNumber + "f", value);
            Log.i("value formatted", s + "");
            this.value = Float.parseFloat(s);
        } else {
            this.value = value;
        }

    }

    public static int math(float f) {
        int c = (int) ((f) + 0.5f);
        float n = f + 0.5f;
        return (n - c) % 2 == 0 ? (int) f : c;
    }
}
