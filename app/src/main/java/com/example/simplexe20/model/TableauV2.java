package com.example.simplexe20.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableauV2 {
    private String id;
    private float min;
    private float max;
    private float pivot;
    private String positionPivot;
    private String positionVEntrante;
    private ColonneTableau colvEntrante;
    private ColonneTableau colvSortante;
    private ArrayList<ColonneTableau> colonnes;
    private String positionVSortante;
    private boolean isLast = false;
    private List<ColonneTableau> pivotColonnes;
    private String programmeLineaireId;
    private int realPosZ;
    private String indice;
    private String[] vhbLabels;
    private String[] vdbLabels;
    private ColonneTableau colPivot;
    private int roundNumber = -1;
    private int numberTotalcolPerLine;

    public TableauV2(ProgrameLineaire programeLineaire) {
        this.roundNumber = programeLineaire.getRoundNumber();
        this.numberTotalcolPerLine = programeLineaire.getColumnsNumberPerLine();
        initLabels(numberTotalcolPerLine);
        colonnes = initTableau(programeLineaire.getAllEquationValues());
    }

    public TableauV2(List<Float> data, int numberTotalcolPerLine) {
        this.numberTotalcolPerLine = numberTotalcolPerLine;
        initLabels(numberTotalcolPerLine);
        colonnes = initTableau(data);
    }

    public String[] getVdbLabels() {
        return vdbLabels;
    }

    public void setVdbLabels(String[] vdbLabels) {
        this.vdbLabels = vdbLabels;
    }


    public ColonneTableau getColPivot() {
        return colPivot;
    }

    public void setColPivot(ColonneTableau colPivot) {
        this.colPivot = colPivot;
    }

    public String[] getVhbLabels() {
        return vhbLabels;
    }

    public String getLabelsToString(String[] labels, String regex) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (String vhbLabel : labels) {
            if (i < labels.length - 1) {
                res.append(vhbLabel).append(regex);
            } else {
                res.append(vhbLabel);
            }

        }
        return res.toString();
    }

    public void setVhbLabels(String[] vhbLabels) {
        this.vhbLabels = vhbLabels;
    }

    private void initLabels(int numberTotalcolPerLine) {
        String[] vdbLabels = {"e1", "e2", "e3"};
        setVdbLabels(vdbLabels);
        String[] vhbLabels = {"x1", "x2", "x3", ".", ".", "."};
        if (numberTotalcolPerLine == 7) {
            vhbLabels = new String[]{"x1", "x2", ".", ".", "."};
        }
        setVhbLabels(vhbLabels);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ColonneTableau getColvEntrante() {
        return colvEntrante;
    }

    public void setColvEntrante(ColonneTableau colvEntrante) {
        this.colvEntrante = colvEntrante;
    }

    public ColonneTableau getColvSortante() {
        return colvSortante;
    }

    public void setColvSortante(ColonneTableau colvSortante) {
        this.colvSortante = colvSortante;
    }

    public String getProgrammeLineaireId() {
        return programmeLineaireId;
    }

    public void setProgrammeLineaireId(String programmeLineaireId) {
        this.programmeLineaireId = programmeLineaireId;
    }

    public int getNumberTotalcolPerLine() {
        return numberTotalcolPerLine;
    }

    public void setNumberTotalcolPerLine(int numberTotalcolPerLine) {
        this.numberTotalcolPerLine = numberTotalcolPerLine;
    }



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

    public String getPositionPivot() {
        return positionPivot;
    }

    public void setPositionPivot(String positionPivot) {
        this.positionPivot = positionPivot;
    }

    public String getPositionVEntrante() {
        return positionVEntrante;
    }

    public void setPositionVEntrante(String positionVEntrante) {
        this.positionVEntrante = positionVEntrante;
    }

    public String getPositionVSortante() {
        return positionVSortante;
    }

    public void setPositionVSortante(String positionVSortante) {
        this.positionVSortante = positionVSortante;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    /* e1, e2, e3, z
       x1,x2,x3 ,point1, point2, point3 , B , R
     */
    private Map<String, Integer> vdbList2() {
        String[] res = {"e1", "e2", "e3", "Z"};
        return convertToMap(res);
    }

    private Map<String, Integer> vhbList2() {
        String[] res = {"x1", "x2", "x3", "p1", "p2", "p3", "B", "R"};
        return convertToMap(res);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> convertToMap(String[] list) {
        Map<String, Integer> myMap = new HashMap();
        int a = 0;
        for (String l : list) {
            myMap.put(l, a);
            a++;
        }
        return myMap;
    }

    private String[] vdbList() {
        return new String[]{"e1", "e2", "e3", "Z"};
    }

    private String[] vhbList() {
        String[] res = {"x1", "x2", "x3", "p1", "p2", "p3", "B", "R"};

        if (numberTotalcolPerLine == 7) {
            res = new String[]{"x1", "x2", "p1", "p2", "p3", "B", "R"};
        }
        return res;
    }

    public ColonneTableau addColumn(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id) {
        ColonneTableau col = new ColonneTableau(roundNumber);
        col.setId(id);
        col.setVhbPosition(vhbKeyVal.getKey());
        col.setVhbValue(vhbKeyVal.getValue());
        col.setVdbPosition(vdbKeyVal.getKey());
        col.setVdbValue(vdbKeyVal.getValue());
        col.setPositionValue(vdbKeyVal.getValue() + "" + vhbKeyVal.getValue());
        col.setCalculated(true);
        return col;
    }

    public ColonneTableau addColumnWithData(KeyVal vhbKeyVal, KeyVal vdbKeyVal, int id, float data) {
        ColonneTableau col = addColumn(vhbKeyVal, vdbKeyVal, id);
        col.setValue(data);
        return col;
    }

    private ArrayList<ColonneTableau> getColumnsOfTable() {
        return getColumnsOfTable(null);
    }

    /**
     * Base class for activities that use the
     * <a href="{@docRoot}tools/extras/support-library.html">support library</a> action bar features.
     *
     * <p>You can add an {@link androidx.appcompat.app.ActionBar} to your activity when running on API level 7 or higher
     * by extending this class for your activity and setting the activity theme to
     * {@link androidx.appcompat.R.style#Theme_AppCompat Theme.AppCompat} or a similar theme.
     *
     * <div class="special reference">
     * <h3>Developer Guides</h3>
     *
     * <p>For informatsion about how to use the action bar, including how to add action items, navigation
     * modes and more, read the <a href="{@docRoot}guide/topics/ui/actionbar.html">Action
     * Bar</a> API guide.</p>
     * </div>
     */
    private ArrayList<ColonneTableau> getColumnsOfTable(List<Float> data) {
        ArrayList<ColonneTableau> list = new ArrayList<>();
        int vdbPos = 0;
        int dataIndex = 0;

        for (String vdb : vdbList()) {
            int vhbPos = 0;
            for (String vhb : vhbList()) {
                ColonneTableau col;
                int id = dataIndex;
                KeyVal vhbKeyVal = new KeyVal(vhbPos, vhb);
                KeyVal vdbKeyVal = new KeyVal(vdbPos, vdb);


                if (data != null) {
                    col = addColumnWithData(vhbKeyVal, vdbKeyVal, id, data.get(dataIndex));
                } else {
                    col = addColumn(vhbKeyVal, vdbKeyVal, id);
                }

                list.add(col);
                vhbPos++;
                dataIndex++;
            }
            vdbPos++;
        }
        return list;
    }

    private ArrayList<ColonneTableau> initTableau() {
        ArrayList<ColonneTableau> tableList = getColumnsOfTable();
        //La derniere colonne n'est pas calculable
        tableList.get(tableList.size() - 1).setCalculated(false);
        return tableList;
    }

    public ArrayList<ColonneTableau> initTableau(List<Float> data) {
        ArrayList<ColonneTableau> tableList = getColumnsOfTable(data);
        //La derniere colonne n'est pas calculable
        tableList.get(tableList.size() - 1).setCalculated(false);
        return tableList;
    }

    public ColonneTableau getCol(String vdbValue, int lignePosition) {
        int vdbPosition = vdbList2().get(vdbValue);
        int res = (numberTotalcolPerLine * (vdbPosition)) + lignePosition;
        return colonnes.get(res);
    }

    public ColonneTableau getCol(int vdbPosition, int lignePosition) {
        int res = (numberTotalcolPerLine * (vdbPosition)) + lignePosition;
        return colonnes.get(res);
    }

    public float getValue(String vdbValue, int lignePosition) {
        int vdbPosition = vdbList2().get(vdbValue);
        return getCol(vdbPosition, lignePosition).getValue();
    }

    public ArrayList<ColonneTableau> getColonnes(Map<Integer, Integer> queries) {
        ArrayList<ColonneTableau> colonnes = new ArrayList<>();
        for (int vdbPosition : queries.keySet()) {
            int lignePosition = queries.get(vdbPosition);
            colonnes.add(getCol(vdbPosition, lignePosition));
        }
        return colonnes;

    }

    public List<ColonneTableau> getLine(String vdbValue) {
        // return tb_Z[0], tb_Z[1], tb_Z[2], tb_Z[3], tb_Z[4], tb_Z[5]
        int index = numberTotalcolPerLine - 1;
        int vdbPosition = vdbList2().get(vdbValue);
        int start = (index * vdbPosition) + vdbPosition;
        return colonnes.subList(start, start + index + 1);
    }

    public void setLine(List<ColonneTableau> list, String vdbValue) {
        int index = numberTotalcolPerLine - 1;
        int vdbPosition = vdbList2().get(vdbValue);
        int start = (index * vdbPosition) + vdbPosition;
        int b = 0;
        for (int a = start; a <= start + index; a++) {
            colonnes.set(a, list.get(b));
            b++;
        }
    }

    public float[] getLineValues(String vdbValue) {
        int index = numberTotalcolPerLine - 1;
        int vdbPosition = vdbList2().get(vdbValue);
        int start = (index * vdbPosition) + vdbPosition;
        return toFloat(colonnes.subList(start, start + index + 1));
    }

    public void verifyTable() {
        int nbPositif = 0;
        for (int i = 0; i < numberTotalcolPerLine; i++) {
            float val = getValue("Z", i);
            if (val > 0) {
                nbPositif++;
            }
        }
        isLast = (nbPositif == 0);
    }

    private float[] toFloat(List<ColonneTableau> list) {
        float[] res = new float[list.size()];
        int a = 0;
        for (ColonneTableau tb : list) {
            res[a] = tb.getValue();
            a++;
        }
        return res;
    }

    public boolean isPValue(String value) {
        String pList = "p1,p2,p3";
        return pList.contains(value);
    }
}
