package matrix;

import java.text.DecimalFormat;

public class Matrix {
    private int row;
    private int col;
    private double[][] matrix;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        matrix = new double[row][col];
    }
    public Matrix (double contents [][] , int row, int col) {
        this.matrix = contents;
        this.row = row;
        this.col = col;
    }

    public int getRowEff() {
        return this.row;
    }

    public int getColEff() {
        return this.col;
    }

    public int getLastRowIdx() {
        return this.row - 1;
    }

    public int getLastColIdx() {
        return this.col - 1;
    }

    public boolean isSquare() {
        return this.row == this.col;
    }

    public double getElmt(int i, int j) {
        if (i < 0 || i >= row || j < 0 || j >= col) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return this.matrix[i][j];
    }

    public void setElmt(int i, int j, double elmt) {
        if (i < 0 || i >= row || j < 0 || j >= col) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        this.matrix[i][j] = elmt;
    }

    public Matrix getRowElmnt(int row) {
        Matrix rowElmnt = new Matrix(1, this.col);
        for (int i = 0; i < this.col; i++) {
            rowElmnt.matrix[0][i] = this.matrix[row][i];
        }
        return rowElmnt;
    }

    public Matrix getColElmnt(int col) {
        Matrix colElmnt = new Matrix(this.row, 1);
        for (int i = 0; i < this.row; i++) {
            colElmnt.matrix[i][0] = this.matrix[i][col];
        }
        return colElmnt;
    }

    public void rowSwap(Matrix m, int rows1, int rows2) {
        if (rows1 < 0 || rows1 >= row || rows2 < 0 || rows2 >= row) {
            throw new IndexOutOfBoundsException("Row index out of bounds");
        }
        for (int i = 0; i < m.col; i++) {
            double temp = m.getElmt(rows1, i);
            m.setElmt(rows1, i, m.getElmt(rows2, i));
            m.setElmt(rows2, i, temp);
        }
    }

    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                copy.matrix[i][j] = this.matrix[i][j];
            }
        }
        return copy;
    }

    public Matrix transposeMatrix() {
        Matrix m2 = new Matrix(this.col, this.row);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                m2.matrix[j][i] = this.matrix[i][j];
            }
        }
        return m2;
    }



    public static Matrix subMatrix(Matrix m, int rowCoret, int colCoret) {
        if (!m.isSquare()) {
            throw new IllegalArgumentException("Matrix harus persegi");
        }
        Matrix submatrix = new Matrix(m.getRowEff() - 1, m.getColEff() - 1);
        for (int i = 0; i < submatrix.getRowEff(); i++) {
            for (int j = 0; j < submatrix.getColEff(); j++) {
                if (i < rowCoret && j < colCoret) {
                    submatrix.setElmt(i, j, m.getElmt(i, j));
                } else if (i < rowCoret && j >= colCoret) {
                    submatrix.setElmt(i, j, m.getElmt(i, j+1));
                } else if (i >= rowCoret && j < colCoret) {
                    submatrix.setElmt(i, j, m.getElmt(i+1, j));
                } else {
                    submatrix.setElmt(i, j, m.getElmt(i + 1, j + 1));
                }
            }
        }
        return submatrix;
    }

    public Matrix getColumn(int colIndex) {
        if (colIndex < 0 || colIndex >= this.getColEff()) {
            throw new IndexOutOfBoundsException("Index kolom di luar batas.");
        }
        Matrix column = new Matrix(this.getRowEff(), 1);
        for (int i = 0; i < this.getRowEff(); i++) {
            column.setElmt(i, 0, this.getElmt(i, colIndex));
        }
        return column;
    }
    public void displayMatrix() {
        DecimalFormat df = new DecimalFormat("#.####");
        for (int i = 0; i < this.row; i++) {
            System.out.print("[");
            for (int j = 0; j < this.col; j++) {
                System.out.print(Double.parseDouble(df.format(matrix[i][j])));
                if (j != this.col - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
    public static Matrix addRow(Matrix m, double[] newRow){
        Matrix mTemp;
        double[][] matrix;
        int i, j;

        matrix = new double[m.row + 1][m.col];
        for(i = 0; i < m.row; i++){
            for(j = 0; j < m.col; j++){
                matrix[i][j] = m.matrix[i][j];
            }
        }
        for(i = 0; i < m.col; i++){
            matrix[m.row][i] = newRow[i];
        }
        mTemp = new Matrix(matrix, m.row+1, m.col);
        return mTemp;
    }
}
