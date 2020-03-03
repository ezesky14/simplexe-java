package com.example.simplexe20;

/**
 * Created by ezesky on 27/01/2016.
 */
public class Simplexe_2v {



    float tb_e1[]=new float[7];
    float tb_e1prim[]=new float[7];

    float tb_e2[]=new float[7];
    float tb_e2prim[]=new float[7];

    float tb_e3[]=new float[7];
    float tb_e3prim[]=new float[7];

    float tb_Z[]=new float[7];
    float tb_Zprim[]=new float[7];

    float tb_libelle[]=new float[7];
    float tb_ligne_pivot[]=new float[7];

    float V_entrant;
    float V_sortant;
    float max;
    float min;
    float pivot;

    int nb_negatif=0;
    int nb_positif=0;
    String fin_tableau="";

    String position_pivot;
    String position_v_entrante;
    String position_v_sortante;


   /* * - Les fonctions a creer
       Les fonctions serviront à alleger le code , eviter les repetitions et permmettons une bonne transparence de l'algorithme
       Pour cela nous creerons plusieurs fonctions telles que :
         - recupLignePivot(): Recupere les elements du tableau ou se trouve le pivot et les insere dans un tableau
         - calculLigneProchainTableau() : Applique la formule du pivot de GAUSS L'=L  - k * Lp / P  tels que P =Pivot  , Lp=Ligne pivot
         - determinerPivot() : Cette fonction determinera la variable entrante VE , la variable sortante VS et le pivot puis affectera la ligne pivot au tableau prevu à cet effet .
         - Apli_Regle_Pivot() : fera les calculs pour appliquer les regles de pivotage pour le tableau suivant .
         - verifTableau() : Verifiera que le tableau Z contient seulement que des valeurs negatives ou nuls
         - Interprete_solu() : Interpretera la solution optimale si seulement si les coefficients des variables hors base sont negatives ou nuls.


         */











    public void Calcul_Ligne (int indice ,String e){

        if ("e1".equals(e)){
            for (int i=0;i<7;i++)
            {

                tb_e1prim[i] =  tb_ligne_pivot[i] /pivot ;
                tb_e2prim[i] = tb_e2[i] - (tb_e2[indice] * tb_ligne_pivot[i] /pivot)  ;
                tb_e3prim[i] = tb_e3[i] - (tb_e3[indice] * tb_ligne_pivot[i] /pivot) ;
                tb_Zprim[i] = tb_Z[i] - (tb_Z[indice] * tb_ligne_pivot[i] /pivot) ;

            }
        }else if ("e2".equals(e)){
            for (int i=0;i<7;i++)
            {

                tb_e1prim[i] = tb_e1[i] - (tb_e1[indice] * tb_ligne_pivot[i] /pivot) ;
                tb_e2prim[i] =  tb_ligne_pivot[i] /pivot  ;
                tb_e3prim[i] = tb_e3[i] - (tb_e3[indice] * tb_ligne_pivot[i] /pivot) ;
                tb_Zprim[i] = tb_Z[i] - (tb_Z[indice] * tb_ligne_pivot[i] /pivot) ;

            }
        }else if ("e3".equals(e)){
            for (int i=0;i<7;i++)
            {

                tb_e1prim[i] = tb_e1[i] - (tb_e1[indice] * tb_ligne_pivot[i] /pivot) ;
                tb_e2prim[i] = tb_e2[i] - (tb_e2[indice] * tb_ligne_pivot[i] /pivot)  ;
                tb_e3prim[i] = tb_ligne_pivot[i] /pivot;
                tb_Zprim[i] = tb_Z[i] - (tb_Z[indice] * tb_ligne_pivot[i] /pivot) ;

            }
        }


    }






    public void Verif_Tableau(){
        nb_positif=0;

        for (int i=0;i<7;i++){

            if (tb_Zprim[i]<=0){
            } else {
                nb_positif++;
            }


        }
        fin_tableau="non";



        if (nb_positif==0){
            fin_tableau="oui";
        }





    }






       public void Max(float nb1,float nb2,float nb3,float nb4,float nb5){

        max=nb1;
        if (nb2>max){
            max=nb2;
        }

        if (nb3>max){
            max=nb3;
        }


        if (nb4>max){
            max=nb4;
        }

        if (nb5>max){
            max=nb5;
        }






    }






    public void Min(float nb1,float nb2,float nb3, int n){
        if(n==3){
            min=nb1;
            if (nb2<min){
                min=nb2;
            }
            if (nb3<min){
                min=nb3;

            }}

        if(n==2){
            min=nb1;
            if (nb2<min){min=nb2;}

        }
    }







    public void Recup_ligne_pivot(float tb[]){
        for (int j=0;j<7;j++){
            tb_ligne_pivot[j]=tb[j];
        }}




    public void Determine_Pivot (){

        //recherche de la variable entrante
        Max(tb_Z[0],tb_Z[1],tb_Z[2],tb_Z[3],tb_Z[4]);
        V_entrant=max;

        if (V_entrant==tb_Z[0]) {
            position_v_entrante="zx1";
            if (tb_e1[0]==0) {

                tb_e1[6]=00000 ;}
            else {
                tb_e1[6]=tb_e1[5]/tb_e1[0];
            }


            if (tb_e2[0]==0) {
                tb_e2[6]=00000 ;
            }
            else{
                tb_e2[6]=tb_e2[5]/tb_e2[0];
            }



            if (tb_e3[0]==0) {
                tb_e3[6]=00000;
            }else{
                tb_e3[6]=tb_e3[5]/tb_e3[0] ;

            }


            //algo pour eviter que les nombres negatifs soient inclues dans le calcul de minimum
            if (tb_e1[6]<=0 ){
                Min(tb_e2[6],tb_e3[6],0,2);
                V_sortant=min;
                if( tb_e2[6]<=0) {
                    V_sortant=tb_e3[6];
                }
                if (tb_e3[6]<=0 ){
                    V_sortant=tb_e2[6];

                }

            }  else{
                Min(tb_e1[6],tb_e2[6],tb_e3[6],3);
                V_sortant=min;
            }


            if (tb_e2[6]<=0) {
                Min(tb_e1[6],tb_e3[6],0,2) ;
                V_sortant=min;
                if (tb_e3[6]<=0) {
                    V_sortant=tb_e1[6]  ;
                }


            }else{
                Min(tb_e1[6],tb_e2[6],tb_e3[6],3);
                V_sortant=min;
            }


            if(tb_e3[6]<=0) {
                Min(tb_e2[6],tb_e1[6],0,2) ;
                V_sortant=min;
            }





            //RECHERCHE DE LA VALEURE SORTANTE ET DU PIVOT PUIS REMPLISSAGE DU TABLEAU ligne pivot


            if (V_sortant==tb_e1[6]) {
                position_v_sortante="re1";
                position_pivot="e1x1";
                pivot=tb_e1[0]  ;

                Recup_ligne_pivot(tb_e1);
                Calcul_Ligne (0,"e1");
            }

            if (V_sortant==tb_e2[6]) {
                position_v_sortante="re2";
                position_pivot="e2x1";
                pivot=tb_e2[0]  ;
                Recup_ligne_pivot(tb_e2);
                Calcul_Ligne (0,"e2");
            }

            if (V_sortant==tb_e3[6]) {
                position_v_sortante="re3";
                position_pivot="e3x1";
                pivot=tb_e3[0]  ;
                Recup_ligne_pivot(tb_e3);
                Calcul_Ligne (0,"e3");
            }}





        if (V_entrant==tb_Z[1]) {
            position_v_entrante="zx2";

            if (tb_e1[1]==0){
                tb_e1[6]=00000  ;
            }else{
                tb_e1[6]=tb_e1[5]/tb_e1[1];

            }


            if (tb_e2[1]==0) {
                tb_e2[6]=00000 ;

            }else{

                tb_e2[6]=tb_e2[5]/tb_e2[1]   ;
            }

            if (tb_e3[1]==0){
                tb_e3[6]=00000;

            }else{

                tb_e3[6]=tb_e3[5]/tb_e3[1]   ;
            }



            //algo pour eviter que les nombres negatifs soient inclues dans le calcul de minimum
            //LE PROBLEME SE SITUE ICIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
            if (tb_e1[6]<=0) {
                Min(tb_e2[6],tb_e3[6],0,2);
                V_sortant=min;
                if (tb_e2[6]<0) {
                    V_sortant=tb_e3[6];
                }
                if (tb_e3[6]<0) {
                    V_sortant=tb_e2[6];
                }}else{
                Min(tb_e1[6],tb_e2[6],tb_e3[6],3) ;
                V_sortant=min;

            }



            if (tb_e2[6]<=0) {
                Min(tb_e1[6],tb_e3[6],0,2);
                V_sortant=min;
                if (tb_e3[6]<=0) {
                    V_sortant=tb_e1[6];
                }

            }else{
                Min(tb_e1[6],tb_e2[6],tb_e3[6],3) ;
                V_sortant=min;

            }

            if (tb_e3[6]<=0){
                Min(tb_e2[6],tb_e1[6],0,2);
                V_sortant=min;
            }



            //RECHERCHE DE LA VALEURE SORTANTE ET DU PIVOT PUIS REMPLISSAGE DU TABLEAU ligne pivot
            if (V_sortant==tb_e1[6]) {
                position_v_sortante="re1";
                position_pivot="e1x2";
                pivot=tb_e1[1] ;
                Recup_ligne_pivot(tb_e1);
                Calcul_Ligne (1,"e1");
            }

            if (V_sortant==tb_e2[6]) {
                position_v_sortante="re2";
                position_pivot="e2x2";
                pivot=tb_e2[1] ;
                Recup_ligne_pivot(tb_e2);
                Calcul_Ligne (1,"e2");
            }


            if (V_sortant==tb_e3[6]){
                position_v_sortante="re3";
                position_pivot="e3x2";
                pivot=tb_e3[1];
                Recup_ligne_pivot(tb_e3);
                Calcul_Ligne (1,"e3");
            }
        }






        if (V_entrant==tb_Z[2]) {
            position_v_entrante="p1z";
            if (tb_e1[2]==0) {
                tb_e1[6]=00000;
            }else{

                tb_e1[6]=tb_e1[5]/tb_e1[2] ;
            }
            if( tb_e2[2]==0) {
                tb_e2[6]=00000 ;

            }else{
                tb_e2[6]=tb_e2[5]/tb_e2[2];
            }

            if (tb_e3[2]==0) {
                tb_e3[6]=00000;
            }else{
                tb_e3[6]=tb_e3[5]/tb_e3[2];

            }


            if (tb_e1[6]<0) {
                Min(tb_e2[6],tb_e3[6],0,2);
                V_sortant=min;
            }
            else if (tb_e2[6]<0) {
                Min(tb_e1[6],tb_e3[6],0,2);
                V_sortant=min;
            }

            else if (tb_e3[6]<0) {
                Min(tb_e2[6],tb_e1[6],0,2);
                V_sortant=min;
            }


            else{
                Min(tb_e1[6],tb_e2[6],tb_e3[6],3) ;
                V_sortant=min;}

            if (V_sortant==tb_e3[6]){
                position_v_sortante="re1";
                position_pivot="p1e1";
                pivot=tb_e1[2];
                Recup_ligne_pivot(tb_e1);
                Calcul_Ligne (2,"e1");
            }



            if (V_sortant==tb_e2[6]) {
                position_v_sortante="re2";
                position_pivot="p1e2";
                pivot=tb_e2[2];
                Recup_ligne_pivot(tb_e2);
                Calcul_Ligne(2,"e2");
            }


            if (V_sortant==tb_e3[6]){
                position_v_sortante="re3";
                position_pivot="p1e3";
                pivot=tb_e3[2];
                Recup_ligne_pivot(tb_e3);
                Calcul_Ligne (2,"e3");

            }
        }




        if (V_entrant==tb_Z[3]) {
            position_v_entrante="p2z";
            if (tb_e1[3]==0) {
                tb_e1[6]=00000;
            }else{

                tb_e1[6]=tb_e1[5]/tb_e1[3] ;
            }
            if( tb_e2[3]==0) {
                tb_e2[6]=00000 ;

            }else{
                tb_e2[6]=tb_e2[5]/tb_e2[3];
            }

            if (tb_e3[3]==0) {
                tb_e3[6]=00000;
            }else{
                tb_e3[6]=tb_e3[5]/tb_e3[3];

            }


            if (tb_e1[6]<0) {
                Min(tb_e2[6],tb_e3[6],0,2);
                V_sortant=min;
            }
            if (tb_e2[6]<0) {
                Min(tb_e1[6],tb_e3[6],0,2);
                V_sortant=min;
            }

            if (tb_e3[6]<0) {
                Min(tb_e2[6],tb_e1[6],0,2);
                V_sortant=min;
            }

            else{

                Min(tb_e1[6],tb_e2[6],tb_e3[6],3) ;
                V_sortant=min;}

            if (V_sortant==tb_e3[6]){
                position_v_sortante="re1";
                position_pivot="p2e1";
                pivot=tb_e1[3];
                Recup_ligne_pivot(tb_e1);
                Calcul_Ligne (3,"e1");
            }



            if (V_sortant==tb_e2[6]) {
                position_v_sortante="re2";
                position_pivot="p2e2";
                pivot=tb_e2[3];
                Recup_ligne_pivot(tb_e2);
                Calcul_Ligne (3,"e2");
            }


            if (V_sortant==tb_e3[6]){
                position_v_sortante="re3";
                position_pivot="p2e3";
                pivot=tb_e3[3];
                Recup_ligne_pivot(tb_e3);
                Calcul_Ligne (3,"e3");

            }
        }







        if (V_entrant==tb_Z[4]) {
            position_v_entrante="p3z";
            if (tb_e1[4]==0) {
                tb_e1[6]=00000;
            }else{

                tb_e1[6]=tb_e1[5]/tb_e1[4] ;
            }
            if (tb_e2[4]==0) {
                tb_e2[6]=00000 ;

            }else{
                tb_e2[6]=tb_e2[5]/tb_e2[4];
            }

            if (tb_e3[4]==0) {
                tb_e3[6]=00000;
            }else{
                tb_e3[6]=tb_e3[5]/tb_e3[4];

            }


            if (tb_e1[6]<0) {
                Min(tb_e2[6],tb_e3[6],0,2);
                V_sortant=min;
            }
            else if (tb_e2[6]<0) {
                Min(tb_e1[6],tb_e3[6],0,2);
                V_sortant=min;
            }

            else if (tb_e3[6]<0) {
                Min(tb_e2[6],tb_e1[6],0,2);
                V_sortant=min;
            }else{



                Min(tb_e1[6],tb_e2[6],tb_e3[6],3) ;
                V_sortant=min;
            }


            if (V_sortant==tb_e1[6]){
                position_v_sortante="re1";
                position_pivot="p3e1";
                pivot=tb_e1[4];
                Recup_ligne_pivot(tb_e1);
                Calcul_Ligne (4,"e1");
            }



            if (V_sortant==tb_e2[6]) {
                position_v_sortante="re2";
                position_pivot="p3e2";
                pivot=tb_e2[4];
                Recup_ligne_pivot(tb_e2);
                Calcul_Ligne (4,"e2");
            }


            if (V_sortant==tb_e3[6]){
                position_v_sortante="re3";
                position_pivot="p3e3";
                pivot=tb_e3[4];
                Recup_ligne_pivot(tb_e3);
                Calcul_Ligne (4,"e3");

            }
        }














    }



}
