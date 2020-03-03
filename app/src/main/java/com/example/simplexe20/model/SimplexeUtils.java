package com.example.simplexe20.model;

public class SimplexeUtils {

    public static ColonneTableau addColumnWithData(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id, float data) {
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

    public static ColonneTableau addColumn(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id) {
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
}
