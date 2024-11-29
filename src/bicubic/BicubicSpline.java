package bicubic;
import matrix.Matrix;
import spl.BalikanMatrix;
import regresiBerganda.*;
import savetofile.Save;


public class BicubicSpline {


    public static Matrix fungsiF() {
        Matrix f;
        f = new Matrix(4, 16);
        int i, j, row, col;
        int x, y;
        for (row = 0; row < 4; row++) {
            if (row == 0) {
                x = 0;
                y = 0;
            } else if (row == 1) {
                x = 1;
                y = 0;
            } else if (row == 2) {
                x = 0;
                y = 1;
            } else {
                x = 1;
                y = 1;
            }
            i = 0;
            j = 0;
            for (col = 0; col < 16; col++) {
                f.setElmt(row, col, Math.pow(x, i) * Math.pow(y, j));
                j++;
                if (j > 3) {
                    j = 0;
                    i++;
                }
            }
        }
        return f;
    }

    public static Matrix fungsiFx() {
        Matrix fx;
        fx = new Matrix(4, 16);
        int i, j, row, col;
        int x, y;
        for (row = 0; row < 4; row++) {
            if (row == 0) {
                x = 0;
                y = 0;
            } else if (row == 1) {
                x = 1;
                y = 0;
            } else if (row == 2) {
                x = 0;
                y = 1;
            } else {
                x = 1;
                y = 1;
            }
            i = 0;
            j = 0;
            for (col = 0; col < 16; col++) {
                if (i == 0) {
                    fx.setElmt(row, col, 0);
                } else {
                    fx.setElmt(row, col, i *Math.pow(x, i - 1) * Math.pow(y, j));
                }
                j++;
                if (j > 3) {
                    j = 0;
                    i++;
                }
            }
        }
        return fx;
    }

    public static Matrix fungsiFy() {
        Matrix fy;
        fy = new Matrix(4, 16);
        int i, j, row, col;
        int x, y;
        for (row = 0; row < 4; row++) {
            if (row == 0) {
                x = 0;
                y = 0;
            } else if (row == 1) {
                x = 1;
                y = 0;
            } else if (row == 2) {
                x = 0;
                y = 1;
            } else {
                x = 1;
                y = 1;
            }
            i = 0;
            j = 0;
            for (col = 0; col < 16; col++) {
                if (j == 0) {
                    fy.setElmt(row, col, 0);
                } else {
                    fy.setElmt(row, col, j*Math.pow(x, i) * Math.pow(y, j - 1));
                }
                j++;
                if (j > 3) {
                    j = 0;
                    i++;
                }
            }
        }
        return fy;
    }

    public static Matrix fungsiFxy() {
        Matrix fxy;
        fxy = new Matrix(4, 16);
        int i, j, row, col;
        int x, y;
        for (row = 0; row < 4; row++) {
            if (row == 0) {
                x = 0;
                y = 0;
            } else if (row == 1) {
                x = 1;
                y = 0;
            } else if (row == 2) {
                x = 0;
                y = 1;
            } else {
                x = 1;
                y = 1;
            }
            i = 0;
            j = 0;
            for (col = 0; col < 16; col++) {
                if (i == 0 || j == 0) {
                    fxy.setElmt(row, col, 0);
                } else {
                    fxy.setElmt(row, col, i*j*Math.pow(x, i - 1) * Math.pow(y, j - 1));
                }
                j++;
                if (j > 3) {
                    j = 0;
                    i++;
                }
            }
        }
        return fxy;
    }

    public static Matrix matrixX() {
        Matrix M = new Matrix(16, 16);
        int i, j;
        for (i = 0; i < 16; i++) {
            for (j = 0; j < 16; j++) {
                if (i <= 3) {
                    M.setElmt(i, j, fungsiF().getElmt(i, j));
                } else if (i <= 7) {
                    M.setElmt(i, j, fungsiFx().getElmt(i - 4, j));
                } else if (i <= 11) {
                    M.setElmt(i, j, fungsiFy().getElmt(i - 8, j));
                } else {
                    M.setElmt(i, j, fungsiFxy().getElmt(i - 12, j));
                }
            }
        }
        return M;
    }
    
    public static void interpolasiBicubics(Matrix M,double a, double b){
        int i, j;
        System.out.println(a);
        System.out.println(b);
        Matrix m1, mY;

        // input matriks 4x4 berisi nilsi fungsi dan turunan berarah di sekitarnya
        m1 = new Matrix(4,4);
        for(i=0; i<4; i++){
            for(j=0; j<4; j++){
                m1.setElmt(i, j, M.getElmt(i, j));
            }
        }
        
        mY = new Matrix(16,1);
        for(i=0; i<16; i++){
            if(i<4){
                mY.setElmt(i, 0, m1.getElmt(0, i));
            } else if (i<8){
                mY.setElmt(i, 0, m1.getElmt(1,i-4));
            } else if (i<12){
                mY.setElmt(i, 0, m1.getElmt(2, i-8));
            } else{
                mY.setElmt(i, 0, m1.getElmt(3, i-12));
            }
        }

        // Y = XA maka A = (invers)X * Y
        Matrix A, X;
        X = matrixX();

        Matrix inversX = BalikanMatrix.balikanMatrixOBESolution(X);
        A = RegresiBerganda.multiplyMatrices(inversX, mY);


        //Jumlahkan hasil perkalian A dengan a pangkat i dan b pangkat j
        Double hasil = 0.0;
        int indeks = 0;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++){
                hasil += A.getElmt(indeks, 0) * Math.pow(a, i) * Math.pow(b, j);
                indeks++;
            }
        }

        System.out.printf("Hasil interpolasi bicubic: %.4f\n", hasil);
        String mauDiSave = Double.toString(hasil);
        Save.saveResultToFile(mauDiSave, "test", "result_interpolasi_bicubic.txt");
        
    } 

}
