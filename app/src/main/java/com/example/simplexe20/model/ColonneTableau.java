package com.example.simplexe20.model;

public class ColonneTableau {
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
    private int id;
    private String vhbValue;
    private String vdbValue;
    private int vhbPosition;
    private int vdbPosition;
    private String positionValue;
    private float value = 0;

    public int getLignePosition() {
        return lignePosition;
    }

    public void setLignePosition(int lignePosition) {
        this.lignePosition = lignePosition;
    }

    private int lignePosition;

    public boolean isCalculated() {
        return isCalculated;
    }

    public void setCalculated(boolean calculated) {
        isCalculated = calculated;
    }

    private boolean isCalculated = false;

    public ColonneTableau(){

    }
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
        this.value = value;
    }
}
