package spl;

import matrix.Matrix;
import java.lang.Math;

public class Determinan {
    public static double detOBE(Matrix m){
        int i,j,k;
        int row, col;
        double det = 1; //inisiasi nilai determinan
        row = m.getRowEff(); 
        col = m.getColEff();
        if (m.isSquare()){
            if (row == 1) {
                return (m.getElmt(0, 0));
            } else {
                for(i=0; i<row; i++){
                    if(m.getElmt(i,i) == 0){
                        j = i+1; //lihat baris bawahnya
                        while(j<row && m.getElmt(j, i)==0){
                            j+=1;
                        }
                        //tukar baris
                        if(j<row){
                            m.rowSwap(m, i, j);
                            det*=-1;
                        }
                        else{
                            return 0;
                        }
                    }
                    // buat 0 pada segitiga bawah
                    for(j=i+1; j<row; j++){
                        double coef = m.getElmt(j, i)/m.getElmt(i, i);
                        for(k=i; k<col; k++){
                            m.setElmt(j, k, m.getElmt(j, k) - (m.getElmt(i, k)*coef));
                        }
                    }
                    det*=m.getElmt(i, i);
                }
                return det;
            }
        } 
        else {
            throw new IllegalArgumentException("Matrix harus persegi");
        }
    }

    public static double detKofaktor(Matrix M) {
        int row, col;
        double det = 0;
        row = M.getRowEff();
        col = M.getColEff();

        if (M.isSquare()) {
            if (row == 1) {
                return M.getElmt(0, 0);
            } else {
                for (int i = 0; i < col; i++) {
                    det += Math.pow(-1, i) * M.getElmt(0, i) * detKofaktor(matrix.Matrix.subMatrix(M, 0, i));
                }
                return det;
            }
        } else {
            throw new IllegalArgumentException("Matrix harus persegi");
        }
    }

}