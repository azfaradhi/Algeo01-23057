package regresiBerganda;
import spl.BalikanMatrix;
import matrix.Matrix;
public class RegresiBerganda {

    public static Matrix multiplyMatrices(Matrix A, Matrix B) {

        int rowsA = A.getRowEff();
        int colsA = A.getColEff(); 
        int colsB = B.getColEff();  
    
        Matrix result = new Matrix(rowsA, colsB);
    
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = 0;
                for (int k = 0; k < colsA; k++) {
                    sum += A.getElmt(i, k) * B.getElmt(k, j);
                }
                result.setElmt(i, j, sum);
            }
        }
    
        return result;
    }

    public static Matrix regresiLinear(Matrix M){

        int m = M.getRowEff();
        int n = M.getColEff() -1 ;

        Matrix X = new Matrix(m,n+1);
        Matrix Y = new Matrix(m,1);
        for (int i = 0; i<m; i++){
            X.setElmt(i, 0, 1);
            for (int j = 1; j<=n; j++){
                X.setElmt(i, j, M.getElmt(i,j-1));
            }
        }
        for (int k = 0; k<m; k++){
            Y.setElmt(k, 0, M.getElmt(k, n));
        }

        Matrix trans = X.transposeMatrix();
        Matrix koefisien = multiplyMatrices(trans, X);
        koefisien = BalikanMatrix.balikanMatrixOBESolution(koefisien);
        koefisien = multiplyMatrices(koefisien, trans);
        koefisien = multiplyMatrices(koefisien, Y);
        return koefisien;
    }
    
    public static void estimateY(Matrix koefisien, Matrix XP, String output) {
        StringBuilder outt=new StringBuilder(); 
        int n = koefisien.getRowEff();
        double sum = koefisien.getElmt(0, 0);
        for (int i = 1; i< n; i++){
            sum += (koefisien.getElmt(i, 0)*XP.getElmt(i-1, 0));
        }
        System.out.printf("Persamaan regresi linear adalah: f(x) = %.4f", koefisien.getElmt(0, 0)); 
        outt.append(String.format("Persamaan regresi linear adalah: f(x) = %.4f", koefisien.getElmt(0, 0)));
        for (int i=1; i<n;i++){
            double coef = koefisien.getElmt(i, 0);
            if (Math.abs(coef) < 1e-10) coef = 0.0;
            System.out.printf(" + (%.4f{x%d})", coef, i);
            outt.append(String.format(" + (%.4f{x%d})", coef, i));
        }

        System.out.println();
        System.out.printf("Predikisi nilai Y adalah: %.4f\n",sum); 
        outt.append(String.format("\nPredikisi nilai Y adalah: %.4f\n",sum)); 
        output+=outt.toString();
    }

    public static Matrix regresiKuadratik(Matrix M){
        int m = M.getRowEff();
        int n = M.getColEff() - 1;

        Matrix X = new Matrix(m, 1 + 2*n + (n*(n-1)/2));
        Matrix Y = new Matrix(m, 1);

        for (int i=0; i< m; i++){
            int index = 0;

            X.setElmt(i,  index++,1);
            for (int j = 0; j<n; j++){
                X.setElmt(i, index++, M.getElmt(i, j));
            }
            for (int j = 0; j < n; j++){
                X.setElmt(i,index++, M.getElmt(i, j) * M.getElmt(i, j));
            }
            for (int j=0; j<n; j++){
                for (int k=j+1; k<n;k++){
                    X.setElmt(i, index++, M.getElmt(i, j) * M.getElmt(i, k));
                }
            }
            Y.setElmt(i, 0, M.getElmt(i, n));
        }
        Matrix trans = X.transposeMatrix();
        Matrix koefisien = multiplyMatrices(trans, X);
        koefisien = BalikanMatrix.balikanMatrixOBESolution(koefisien);
        koefisien = multiplyMatrices(koefisien, trans);
        koefisien = multiplyMatrices(koefisien, Y);
        return koefisien;   
    }


    public static void printRegresiKuadrat(Matrix koefisien, Matrix XP, String output) {
        StringBuilder eq = new StringBuilder("y = ");
        int n = koefisien.getRowEff();
        Double predict = koefisien.getElmt(0, 0);

        System.out.printf("Persamaan regresi linear adalah: ");
        eq.append(String.format("%.4f", koefisien.getElmt(0, 0)));

        int index = 1;

        for (int i = 0; i < n / 3; i++) {
            predict += koefisien.getElmt(index, 0) * XP.getElmt(i, 0);
            double coef = koefisien.getElmt(index++, 0);
            if (Math.abs(coef) < 1e-10) coef = 0.0;
            eq.append(String.format(" + (%.4f{x%d})", coef, i));
        }
        for (int i = 0; i < n / 3; i++) {
            predict += koefisien.getElmt(index, 0) * XP.getElmt(i, 0) * XP.getElmt(i, 0);
            double coef = koefisien.getElmt(index++, 0);
            if (Math.abs(coef) < 1e-10) coef = 0.0;
            eq.append(String.format(" + (%.4f{x%d^2})", coef, i));        
        }
        for (int i = 0; i < n / 3; i++) {
            for (int j = i + 1; j < n / 3; j++) {
                predict += koefisien.getElmt(index, 0) * XP.getElmt(i, 0) * XP.getElmt(j, 0);
                double coef = koefisien.getElmt(index++, 0);
                if (Math.abs(coef) < 1e-10) coef = 0.0;
                eq.append(String.format(" + (%.4f{x%d}{x%d})", coef, i, j));
            }
        }

        System.out.println(eq.toString());
        System.out.println("Prediksi nilai Y adalah: " + predict);
        eq.append(String.format("Prediksi nilai Y adalah: " + predict));
        output+=eq.toString();


    }
}