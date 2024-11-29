package spl;

import matrix.Matrix;

public class GaussJordan {
    private static final double EPSILON = 1e-10;

    private static int whereIsNoneZero(Matrix M, int row) {
        for (int j = 0; j < M.getColEff(); j++) {
            if (Math.abs(M.getElmt(row, j)) > EPSILON) {
                return j;
            }
        }
        return -1;
    }

    public static Matrix gaussJordanSolution(Matrix M) {
        Gauss.gaussSolution(M);
        for (int i = M.getRowEff() - 1; i >= 0; i--) {
            int leadingCol = whereIsNoneZero(M, i);
            if (leadingCol == -1) continue;

            double depan = M.getElmt(i, leadingCol);
            if (Math.abs(depan) > EPSILON) {
                Gauss.multiplyRow(M, 1 / depan, i);
            }
            for (int j = i - 1; j >= 0; j--) {
                double coef = -M.getElmt(j, leadingCol);
                Gauss.plusRow(M, j, i, coef);
            }
        }
        return M;
    }

    // public static Matrix printSolutionGaussJordan(Matrix M) {
    //     int row = M.getRowEff();
    //     int col = M.getColEff() - 1;

    //     boolean[] free = new boolean[col];
    //     double[] solusi = new double[col];
    //     boolean hasSolution = true;

    //     for (int i = 0; i < col; i++) {
    //         free[i] = true;
    //     }

    //     for (int i = 0; i < row; i++) {
    //         int pivot = whereIsNoneZero(M, i);
    //         if (pivot == -1) {
    //             if (Math.abs(M.getElmt(i, col)) > EPSILON) {
    //                 hasSolution = false;
    //             }
    //             continue;
    //         }

    //         free[pivot] = false;
    //         solusi[pivot] = M.getElmt(i, col);

    //         for (int j = pivot + 1; j < col; j++) {
    //             solusi[pivot] -= M.getElmt(i, j) * solusi[j];
    //         }
    //         solusi[pivot] /= M.getElmt(i, pivot);
    //     }

    //     if (!hasSolution) {
    //         System.out.println("SPL tidak memiliki solusi");
    //     } else {
    //         boolean takhingga = false;
    //         for (int i = 0; i < col; i++) {
    //             if (free[i]) {
    //                 takhingga = true;
    //                 System.out.println("x" + (i + 1) + " = t" + (i + 1) + " (variabel bebas)");
    //             } else {
    //                 System.out.printf("x%d = %.4f\n", i + 1, solusi[i]);
    //             }
    //         }

    //         if (takhingga) {
    //             System.out.println("SPL memiliki tak hingga solusi");
    //         } else {
    //             System.out.println("SPL ini memiliki solusi unik");
    //         }
    //     }
    //     return M;
    //}
}
