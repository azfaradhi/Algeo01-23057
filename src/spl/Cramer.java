package spl;
import matrix.Matrix;

public class Cramer{

    public static Matrix cramerSolution (Matrix m){
        int row = m.getRowEff();
        int col = m.getColEff();
        Matrix ans = new Matrix(row, 1); 
        Matrix originalMatrix = new Matrix(row, col - 1); 
        Matrix resultColumn = m.getColElmnt(m.getLastColIdx()); 
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col - 1; j++) {
                originalMatrix.setElmt(i, j, m.getElmt(i, j));
            }
        }

        double detMatrix = Determinan.detKofaktor(originalMatrix); 

        if (Math.abs(detMatrix) < 1e-10) {
            return null;
        }
        for (int i = 0; i < col - 1; i++) {
            Matrix pass = new Matrix(row, col - 1);
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col - 1; y++) {
                    pass.setElmt(x, y, originalMatrix.getElmt(x, y));
                }
            }
            for (int k = 0; k < row; k++) {
                pass.setElmt(k, i, resultColumn.getElmt(k, 0));
            }

            ans.setElmt(i, 0, Determinan.detKofaktor(pass) / detMatrix);
        }
        return ans;
    }

    public static void printMatrix(Matrix M) {
        for (int i = 0; i < M.getRowEff(); i++) {
            double x = M.getElmt(i, 0);
            System.out.println("x_" + (i + 1) + " = " + x);
        }
    }
}