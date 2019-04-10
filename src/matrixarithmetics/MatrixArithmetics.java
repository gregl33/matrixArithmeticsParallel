package matrixarithmetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MatrixArithmetics {
    
    static int N = 2;
    final static int min_randomNumberScale = 1;     
    final static int randomNumberScale = 100;  
    final static int max_N = 1024;
    

    static int count = 0;

    static ArrayList<Integer> runs = new ArrayList<Integer>(Arrays.asList(2,4,8,16,32));
    static double[][] matrix1;
    static double[][] matrix2;
    static double[][] Sequential_matrix;
    static double sequential_time = 0;
    

    static ArrayList<Double> time = new ArrayList<Double>();
    static ArrayList<Double> speedup = new ArrayList<Double>();

        
    public static void main(String[] args) throws Exception{

        calculateSequential();
        
    }
    
    
     public static void calculateSequential() throws InterruptedException{

        N = N * 2;

        if(N <= max_N){
            System.out.println("__________________________________________________________________");
            System.out.println("");
            System.out.println("*******************************************************************");
            System.out.println("Starting Calculation for N = " + N); 
            System.out.println("*******************************************************************");
            System.out.println("");

            matrix1 = matrixGenerator(N,N);
            matrix2 = matrixGenerator(N,N);

            double startTime = System.nanoTime();

            Sequential_matrix = matrixMultiplication(matrix1, matrix2);
    
            double endTime = System.nanoTime();
            sequential_time =  (endTime - startTime)/1000000;

            System.out.println("*******************************************************************");
            System.out.println("Sequential Calculation Time " + sequential_time+ " milliseconds");

            
            calculateParallel(runs.get(count));
        }
     }

    
        public static void calculateParallel(int no_threads) throws InterruptedException{
            System.out.println("*******************************************************************");
            System.out.println("Using " + runs.get(count) + " threads:");
            
           double matrix[][] = new double[N][N];
           
           double startTime = System.nanoTime();

               Matrix [] threads = new Matrix[no_threads];
               for(int me = 0 ; me < no_threads ; me++) {
                   threads [me] = new Matrix(me,N,no_threads,matrix1,matrix2,matrix) ;
                   threads [me].start() ;
               }

               for(int me = 0 ; me < no_threads ; me++) {
                   threads [me].join();
               }


           double endTime = System.nanoTime();

           double parallel_time = 0;

           if(Arrays.deepEquals(Sequential_matrix,matrix)){
                parallel_time = (endTime - startTime)/1000000;
                System.out.println("    Parallel Calculation time: " + parallel_time + " milliseconds");
           }else{
                System.out.println("Parallel Matrix NOT equal to Sequential Matrix");
           }
          
           count++;           
           if(count <= runs.size()-1){
            calculateParallel(runs.get(count));
           }else{
               count = 0;
               calculateSequential();
           }
          
        }
        
        
    public static double[][] matrixGenerator(int rowsNo, int colNo) {
        
        double newMatrix[][] = new double[rowsNo][colNo];
        
        for (int i=0; i<newMatrix.length; i++) {
            for (int j=0; j<newMatrix[i].length; j++) {
                Random random = new Random();
                int x = random.nextInt(randomNumberScale) + min_randomNumberScale;
                newMatrix[i][j] = x;
            } 
        }
        return newMatrix;
    }
    
    
      public static double isOverflow( double a, double b) 
    { 
        if (a == 0 || b == 0) {
            return 0; 
        }
        
        double result = a * b; 
        
        if (a == result / b) {
            return result; 
        }else{
            return 0; 
        }
    } 
      
      
    public static double[][] matrixMultiplication(double[][] matrix1, double[][] matrix2) {
        
        int matrix1Row = matrix1.length;
        int matrix1Col = matrix1[0].length;
        int matrix2Col = matrix2[0].length;
        
        double matrix[][]=new double[matrix1Row][matrix2Col];
        
        for (int i = 0; i < matrix1Row; i++) {
           for (int j = 0; j < matrix2Col; j++) {
               for (int k = 0; k < matrix1Col; k++) {
                   matrix[i][j] += isOverflow(matrix1[i][k], matrix2[k][j]);
               }
           }
        }
        
        return matrix;
    }
        
        
        
    public static double[][] matrixSum(double[][] matrix1, double[][] matrix2) { 
       
        int rows = matrix1.length;
        int columns = matrix2[0].length;
        
        double matrix[][]=new double[rows][columns];
      
        for (int i = 0; i < rows; i++) {
           for (int j = 0; j < columns; j++) {
               matrix[i][j] = matrix1[i][j] + matrix2[i][j];
           }
        }
        
        return matrix;
    }

}
