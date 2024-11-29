package spl;
import matrix.InputOutput;
import matrix.Matrix;
import regresiBerganda.RegresiBerganda;

public class BalikanMatrix{

    private static final double EPSILON = 1e-10;

    public static Matrix balikanMatrixOBESolution(Matrix M){
        int n = M.getRowEff();
        Matrix gabungan = new Matrix(n,n*2);
        for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (i == j){
					gabungan.setElmt(i, j+n, 1);
				}
				else{
					gabungan.setElmt(i, j+n, 0);
				}
				gabungan.setElmt(i, j, M.getElmt(i,j));
			}
		}
        gabungan = GaussJordan.gaussJordanSolution(gabungan);
        Matrix hasil = new Matrix(n, n);
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                hasil.setElmt(i, j, gabungan.getElmt(i, j+n));
            }
        }
        return hasil;

    }

    public static double getCofactor(Matrix M, int i, int j){
        Matrix sub = Matrix.subMatrix(M, i, j);
        double detSub = Determinan.detKofaktor(sub);
        return ((i + j) % 2 == 0 ? 1 : -1) * detSub;
    }

    public static Matrix mAdjoin(Matrix M){
        int row = M.getRowEff();
        int col = M.getColEff();
        if (M.isSquare()){
            Matrix mkofaktor = new Matrix(row, col);
            for(int i=0; i<row; i++){
                for(int j=0; j<col; j++){
                    mkofaktor.setElmt(i, j, getCofactor(M, i, j));
                }
            }
            return mkofaktor.transposeMatrix();
        }
        return null;
    }
    public static Matrix balikanMatrixKofaktorSolution(Matrix M){
        if (!M.isSquare()){
            return null;
        }
        double det = Determinan.detKofaktor(M);
        if (Math.abs(det) < EPSILON){
            return null;
        }
        Matrix adjoin = mAdjoin(M);
        Matrix mInverse = new Matrix(M.getRowEff(), M.getColEff());
        for (int i=0; i<M.getRowEff(); i++){
            for (int j=0; j<M.getColEff(); j++){
                mInverse.setElmt(i, j, adjoin.getElmt(i, j) / det);
            }
        }
        return mInverse;
    }


    public static Matrix gaussWithInverse(Matrix M){
        Matrix A = new Matrix(M.getRowEff(), M.getColEff()-1);
        Matrix B = new Matrix(M.getRowEff(),1);

        for (int i=0; i<A.getRowEff(); i++){
            for (int j=0; j<A.getColEff(); j++){
                A.setElmt(i, j, M.getElmt(i, j));
            }
        }

        for (int i = 0; i<B.getRowEff(); i++){
            B.setElmt(i, 0, M.getElmt(i, M.getLastColIdx()));
        }

        Matrix hasil = BalikanMatrix.balikanMatrixOBESolution(A);
        hasil = RegresiBerganda.multiplyMatrices(hasil, B);
        return hasil;
    }

}