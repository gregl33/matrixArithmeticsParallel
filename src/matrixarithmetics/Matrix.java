/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrixarithmetics;

/**
 *
 * @author greg
 */
public class Matrix extends Thread {
    
    int me ;
    double matrix1[][];
    double matrix2[][]; 
    double matrix[][];
    int N= 0;
    int B = 0;
   
    public Matrix(int me,int n,int p,double m1[][] ,double m2[][],double res[][]) {
        B = n/p;
        this.me = me ;
        matrix1 = m1;
        matrix2 = m2;
        N = n;
        matrix = res;
    }
    
    
       public void run() {

        int begin = me * B ;
        int end = begin + B ;
  
        for (int i = begin; i < end; i++) {
           for (int j = 0; j < N; j++) {
               for (int k = 0; k < N; k++) {
                   matrix[i][j] = matrix[i][j] + matrix1[i][k] * matrix2[k][j];
               }
           }
        }
       }
}
