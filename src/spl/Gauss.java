package spl;
import matrix.Matrix;

public class Gauss {
    private static final double EPSILON = 1e-10;

    public static void multiplyRow(Matrix M, double coef, int row) {
        for (int i = 0; i < M.getColEff(); i++) {
            double temp = M.getElmt(row, i) * coef;
            if (Math.abs(temp) < EPSILON) {
                temp = 0.0;
            }
            M.setElmt(row, i, temp);
        }
    }

    public static void plusRow(Matrix M, int rowTarget, int rowAsal, double coef) {
        for (int j = 0; j < M.getColEff(); j++) {
            double temp = M.getElmt(rowTarget, j) + M.getElmt(rowAsal, j) * coef;
            if (Math.abs(temp) < EPSILON) {
                temp = 0.0;
            }
            M.setElmt(rowTarget, j, temp);
        }
    }

    public static void sortZero(Matrix M, int col) {
        for (int i = col; i < M.getRowEff(); i++) {
            if (Math.abs(M.getElmt(i, col)) >= EPSILON) {
                M.rowSwap(M, col, i);
                return;
            }
        }
    }

    public static Matrix gaussSolution(Matrix M) {
        int colNotZero = 0;
    
        for (int i = 0; i < M.getRowEff(); i++) {
            Gauss.sortZero(M, colNotZero);
    
            if (colNotZero >= M.getColEff()) {
                break;
            }
    
            if (Math.abs(M.getElmt(i, colNotZero)) >= EPSILON) {
                Gauss.multiplyRow(M, 1 / M.getElmt(i, colNotZero), i);
    
                for (int j = i + 1; j < M.getRowEff(); j++) {
                    Gauss.plusRow(M, j, i, -M.getElmt(j, colNotZero));
                }
            }
            colNotZero++;
        }
        return M;
    }
    
    public static Matrix printSolutionGauss(Matrix M) {
        int row = M.getRowEff();
        int col = M.getColEff() - 1; 
        
        boolean[] free = new boolean[col];
        double[] solusi = new double[col]; 
        boolean hasSolution = true;
        
        for (int i = 0; i < col; i++) {
            free[i] = true;
        }
    
        for (int i = row - 1; i >= 0; i--) {
            int pivot = -1;
            
            for (int j = 0; j < col; j++) {
                if (Math.abs(M.getElmt(i, j)) > EPSILON) {
                    pivot = j;
                    break;
                }
            }
    
            if (pivot == -1 && Math.abs(M.getElmt(i, col)) > EPSILON) {
                hasSolution = false;
                break;
            }
    
            if (pivot == -1) {
                continue;
            }
    
            free[pivot] = false;
            
            solusi[pivot] = M.getElmt(i, col);
            
            for (int j = pivot + 1; j < col; j++) {
                solusi[pivot] -= M.getElmt(i, j) * solusi[j];
            }
            
            solusi[pivot] /= M.getElmt(i, pivot);
        }
    
        if (!hasSolution) {
            System.out.println("SPL tidak memiliki solusi");
        } else {
            boolean takhingga = false;
            
            for (int i = 0; i < col; i++) {
                if (free[i]) { 
                    takhingga = true;
                    System.out.println("x" + (i + 1) + " = t" + (i + 1) + " (variabel bebas)");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("x" + (i + 1) + " = " + String.format("%.4f", solusi[i]));
            
                    for (int j = i + 1; j < col; j++) {
                        double coef = M.getElmt(i, j);
                    
                        if (Math.abs(coef) > EPSILON) {
                            if (coef > 0) {
                                sb.append(" - (" + String.format("%.4f", coef) + " * t" + (j + 1) + ")");
                            } else {
                                sb.append(" + (" + String.format("%.4f", Math.abs(coef)) + " * t" + (j + 1) + ")");
                            }
                        }
                    }
                    
                    
                    System.out.println(sb.toString());
                }
            }
    
            // Menyatakan apakah sistem memiliki tak hingga solusi atau solusi unik
            if (takhingga) {
                System.out.println("SPL memiliki tak hingga solusi");
            } else {
                System.out.println("SPL ini memiliki solusi unik");
            }
        }
    
        return M;
    }
    
    
}