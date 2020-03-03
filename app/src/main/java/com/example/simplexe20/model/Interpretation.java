package com.example.simplexe20.model;


public class  Interpretation{
 private int id;
 private String ZMAX;
 private String X2;
 private String X3;
 private String X1;
 private String E1;
 private String E2;
 private String E3;






 public int getId() {
  return id ;
 }


 public String getX1() {
  return X1 ;
 }

 public String getX2() {
  return X2 ;
 }

 public String getX3() {
  return X3 ;
 }

 public String getE1() {
  return E1 ;
 }

 public String getE2() {
  return E2 ;
 }

 public String getE3() {
  return E3 ;
 }

 public String getZMAX() {
  return ZMAX ;
 }






 //SETTERS


 public void setZMAX(String ZMAX) {
  this.ZMAX = ZMAX;
 }

 public void setX2(String x2) {
  X2 = x2;
 }

 public void setX3(String x3) {
  X3 = x3;
 }

 public void setX1(String x1) {
  X1 = x1;
 }

 public void setE1(String e1) {
  E1 = e1;
 }

 public void setE2(String e2) {
  E2 = e2;
 }

 public void setE3(String e3) {
  E3 = e3;
 }

 public void setId (int id){
  this.id = id ; }





 public Interpretation ()  {}
}