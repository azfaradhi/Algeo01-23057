package matrix;
import java.util.Scanner;
import java.io.*;

public class InputOutput{

	public static Scanner input = new Scanner(System.in);

	public static Matrix readMatrixFromKeyboard(){
		
		System.out.println("Masukkan jumlah baris: ");
		int row = input.nextInt();
		System.out.println("Masukkan jumlah kolom: ");
		int col = input.nextInt();

		Matrix M = new Matrix(row, col);
        System.out.println("Masukkan elemen matriks: ");

		for (int i=0; i<row; i++){
			for (int j = 0; j<col; j++){
				double temp = input.nextDouble();
				M.setElmt(i, j, temp);
			}
		}
		return M;
	}

	public static Matrix readMatrixSquareFromKeyboard(){

		System.out.println("Masukkan dimensi matrix: ");
		int n = input.nextInt();
		Matrix M = new Matrix(n, n);

        System.out.println("Masukkan elemen matriks: ");

		for (int i=0; i< n; i++){
			for (int j = 0; j<n; j++){
				double temp = input.nextDouble();
				M.setElmt(i, j, temp);
			}
		}
		return M;
	}

	public static Matrix readMatrixFromFile(String path) throws IOException{
		int i;
        Matrix matrix;
    
        // Input Nama File
        

        // Mencari File
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String s;
            String[] x;
            double[] y;
            double[][] mTemp;

            // Membaca Baris 1
            s = br.readLine();
            
            // Mengubah Baris 1 Menjadi Array of String
            x = s.split("\\s+");
            y = new double[x.length];

            // Mengubah Array menjadi Array of Double
            for(i = 0; i < y.length; i++){
                y[i] = Double.parseDouble(x[i]);
            }

            // Memindahkan Array of Double ke Matriks
            mTemp = new double[1][x.length];
            for(i = 0; i < y.length; i++){
                mTemp[0][i] = y[i];
            }
            // Membuat matriks 
            matrix = new Matrix(mTemp, 1, y.length);

            while((s = br.readLine()) != null){
                x = s.split("\\s+");
                y = new double [x.length];
                for(i = 0; i < x.length; i++){
                    y[i] = Double.parseDouble(x[i]);
                }
                if(x.length < matrix.getColEff()){
                    double[] z = new double[matrix.getColEff()];
                    for(i = 0; i < matrix.getColEff();i++){
                        if(i >= y.length){
                            z[i] = 0;
                        }else{
                            z[i] = y[i];
                        }
                    }
                    matrix = Matrix.addRow(matrix, z);
                }else{
                    matrix = Matrix.addRow(matrix, y);
                }
            }
            br.close();
            return matrix;
            
        // Ketika File Tidak Ditemukan
        }catch(Exception ex){
            System.out.println("File not found");
            System.out.println("Returning a matrix with no value");
            matrix = new Matrix(1, 1);
            return matrix;
        }
	}

	public static Matrix readInterpolasi() {
        System.out.println("Masukkan banyak titik: ");
        int n = input.nextInt();

        Matrix M = new Matrix(n , 2);
        System.out.println("Masukkan titik x dan y secara berurut: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                M.setElmt(i, j, input.nextDouble());
            }
        }
        return M;
    }

    public static Matrix readRegresiKeyboard(){

        System.out.print("Masukkan jumlah peubah x (n): ");
        int n = input.nextInt();
        System.out.print("Masukkan jumlah sampel (m): ");
        int m = input.nextInt();

        Matrix M = new Matrix(m,n+1);
        System.out.println("Masukkan titik x dan y: ");
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n + 1; j++){
                double temp = input.nextDouble();
                M.setElmt(i,j,temp);
            }
        }
        return M;
    }


    public static Object[] bacaFileKeMatriks(String filePath) {
        Matrix M = new Matrix(4, 4);
        double a = 0.0;
        double b = 0.0;
    
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int row = 0;
    
            while ((line = reader.readLine()) != null && row < 4) {
                String[] values = line.split(" ");
                if (values.length != 4) {
                    throw new IOException("Invalid matrix format: row " + row + " does not have 4 elements.");
                }
                for (int col = 0; col < 4; col++) {
                    M.setElmt(row, col, Double.parseDouble(values[col]));
                }
                row++;
            }
            if ((line = reader.readLine()) != null){
                String[] abValues = line.split(" ");
                if (abValues.length != 2) {
                    throw new IOException("Invalid format for a and b: expected 2 values.");
                }
                a = Double.parseDouble(abValues[0]);
                b = Double.parseDouble(abValues[1]);
            } else {
                throw new IOException("Missing line for a and b values.");
            }
    
            reader.close();
    
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    
        return new Object[]{M, a, b};
    }
    
    
	

	public static void printMatrix(Matrix m){

		int row=m.getRowEff();
		int col=m.getColEff();
		for (int i=0; i<row;i++){
			for (int j=0; j<col; j++){
				System.out.println(m.getElmt(i, j) +" ");
			}
			System.out.println();
		}
	}

    public static String matrixToString(Matrix M) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M.getRowEff(); i++) {
            for (int j = 0; j < M.getColEff(); j++) {
                sb.append(M.getElmt(i, j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

	public static void saveOutputToFile(String output) {
        System.out.println("\nSimpan hasil ke dalam file? (Y/N)");
        String save = input.next().trim().toUpperCase();
        if (save.equals("Y")) {
            System.out.print("Masukkan nama file: ");
            String pathToFile = input.next();
            try (FileWriter myWriter = new FileWriter("test/output/"+ pathToFile)) {
                myWriter.write(output);
                myWriter.close();
                System.out.println("Berhasil menyimpan ke dalam file test/Output/" + pathToFile);
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menyimpan ke file: " + e.getMessage());
            }
        }
    }
    public static int lineCount(String path){
        try{
            File file =  new File(path);
            Scanner isiFile = new Scanner(file);
            int row = 0, col =0;
            while(isiFile.hasNextLine()){
                col = (isiFile.nextLine()).split(" ").length;
                row ++;
            }
            isiFile.close();
            return row;
        }
        catch (FileNotFoundException e) {
            return 0;
        }
        catch(Exception e){
            return 0;
        }        
    }

    public static int columnCount(String path){
        try{
            File file =  new File(path);
            Scanner isiFile = new Scanner(file);
            int row = 0, col =0;
            col = (isiFile.nextLine()).split(" ").length;
            isiFile.close();
            return col;
        }
        catch (FileNotFoundException e) {
            return 0;
        }
        catch(Exception e){
            return 0;
        }        
    }
	

}