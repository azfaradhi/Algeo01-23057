package interpolasi;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import matrix.Matrix;
import spl.GaussJordan;
import matrix.InputOutput;

public class interPolim {
    private static Scanner scanInterpol = new Scanner(System.in);
    public static void interPolinom(Matrix A) {
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.println("\nMenginterpolasi Titik");
        int row = A.getRowEff();
        Matrix result = SPLInterpol(A);
        // InputOutput.printMatrix(result);
        String output = "Polinom interpolasi: p" + (row - 1) + "(x) = ";
        System.out.print("p" + (row - 1) + "(x) = ");
        boolean firstTerm = true; // Menandakan apakah ini adalah term pertama
        for (int i = 0; i < result.getRowEff(); i++) {
            double coef = result.getElmt(i, result.getColEff() - 1);
            String formattedCoef = df.format(coef);
            if(coef<0)formattedCoef="("+formattedCoef+")";

            if (coef != 0) {
                String term;
                if (i == 0) {
                    term = formattedCoef; // Untuk konstanta
                } else if (i == 1) {
                    term = formattedCoef + "(x)"; // Untuk x
                } else {
                    term = formattedCoef + "(x^" + i + ")"; // Untuk x^n
                }
                
                // Jika bukan term pertama dan ada term sebelumnya yang non-zero, tambahkan "+"
                if (!firstTerm) {
                    System.out.print(" + ");
                    output += " + ";
                }

                // Cetak term baru
                output += term;
                System.out.print(term);
                firstTerm = false; // Set ke false setelah term pertama
            }
        }

        // Jika tidak ada term yang dicetak, berarti polinom adalah 0
        if (firstTerm) {
            output += "0"; // Menangani kasus di mana semua koefisien adalah nol
            System.out.print("0");
        }

        output += "\n";

        boolean next = true;
        while (next) {
            System.out.printf("\nPrediksi nilai titik Ya (Y) atau Tidak (T) > ");
            String ujiInterpol = scanInterpol.next().toUpperCase();

            while (!ujiInterpol.equals("Y") && !ujiInterpol.equals("T")) {
                output += "Masukan TIDAK VALID\n";
                System.out.println("Masukan TIDAK VALID\n");
                System.out.printf("\nPrediksi nilai titik Ya (Y) atau Tidak (T) > ");
                ujiInterpol = scanInterpol.next().toUpperCase();
            }

            if (ujiInterpol.equals("Y")) {
                double x;
                System.out.print("\nMasukkan titik (x) yang ingin diestimasi: ");
                x = scanInterpol.nextDouble();
                double est = calculateEstimation(result, x);
                String formattedEst = df.format(est);
                output += "p" + String.format("%d", row - 1) + "(" + x + ") = " + formattedEst + "\n";
                System.out.println("p" + (row - 1) + "(" + x + ") = " + formattedEst);
            } else {
                break;
            }
        }

        InputOutput.saveOutputToFile(output);
    }

    // Fungsi untuk interpolasi dari file
    public static void interPolinomFile(Matrix A, String path) {
        // InputOutput.printMatrix(A);
        DecimalFormat df = new DecimalFormat("#.####");
        System.out.println("\nMenginterpolasi Titik dari File");
        int row = A.getRowEff();
        Matrix result = SPLInterpolFile(A);
        // InputOutput.printMatrix(result);

        String output = "Polinom interpolasi: p" + (row - 1) + "(x) = ";
        System.out.print("p" + (row - 1) + "(x) = ");
        boolean firstTerm = true; // Menandakan apakah ini adalah term pertama
        for (int i = 0; i < result.getRowEff(); i++) {
            double coef = result.getElmt(i, result.getColEff() - 1);
            String formattedCoef = df.format(coef);
            if(coef<0)formattedCoef="("+formattedCoef+")";

            if (coef != 0) {
                String term;
                if (i == 0) {
                    term = formattedCoef; // Untuk konstanta
                } else if (i == 1) {
                    term = formattedCoef + "(x)"; // Untuk x
                } else {
                    term = formattedCoef + "(x^" + i + ")"; // Untuk x^n
                }
                
                // Jika bukan term pertama dan ada term sebelumnya yang non-zero, tambahkan "+"
                if (!firstTerm) {
                    System.out.print(" + ");
                    output += " + ";
                }

                // Cetak term baru
                output += term;
                System.out.print(term);
                firstTerm = false; // Set ke false setelah term pertama
            }
        }

        // Jika tidak ada term yang dicetak, berarti polinom adalah 0
        if (firstTerm) {
            output += "0"; // Menangani kasus di mana semua koefisien adalah nol
            System.out.print("0");
        }

        output += "\n";
        
        int nUji = countTest(path);
        System.out.println("");
        // System.err.println("\n "+countTest(path));
        if (nUji != 0) {
            double uji = readInterpolTestFromFile( path);
                double est = calculateEstimation(result, uji);
                String formattedEst = df.format(est);
                output += "p" + String.format("%d", row - 1) + "(" + uji + ") = " + formattedEst + "\n";
                System.out.println("p" + (row - 1) + "(" + uji + ") = " + formattedEst);
        }
        InputOutput.saveOutputToFile(output);
    }

    // Fungsi bantu untuk menghitung estimasi
    private static double calculateEstimation(Matrix result, double x) {
        double est = 0.0;
        for (int i = 0; i < result.getRowEff(); i++) {
            est += result.getElmt(i, result.getColEff() - 1) * (Math.pow(x, i));
        }
        return est;
    }

    // Fungsi untuk menyimpan output ke file
    
    private static Matrix SPLInterpolFile(Matrix A) {
        int n = A.getRowEff()-1;
        Matrix coeffs = new Matrix(n, n + 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                coeffs.setElmt(i, j, Math.pow(A.getElmt(i, 0), j)); 
            }
            coeffs.setElmt(i, n, A.getElmt(i, 1));
        }
        // System.out.println("Matriks Koefisien setelah dibangun:");
        // InputOutput.printMatrix(coeffs);
        Matrix result = GaussJordan.gaussJordanSolution(coeffs);
        // System.out.println("hasil gaussJordan");
        // InputOutput.printMatrix(result);
        return result;
    }

    private static Matrix SPLInterpol(Matrix A) {
        int n = A.getRowEff();
        Matrix coeffs = new Matrix(n, n + 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                coeffs.setElmt(i, j, Math.pow(A.getElmt(i, 0), j)); 
            }
            coeffs.setElmt(i, n, A.getElmt(i, 1));
        }
        // System.out.println("Matriks Koefisien setelah dibangun:");
        // InputOutput.printMatrix(coeffs);
        Matrix result = GaussJordan.gaussJordanSolution(coeffs);
        // System.out.println("hasil gaussJordan");
        // InputOutput.printMatrix(result);
        return result;
    }

    private static int countTest(String pathToFile) {
        try {
            int nUji = 0;
            File file1 = new File(pathToFile);
            Scanner scanner1 = new Scanner(file1);
            while (scanner1.hasNextLine()) {
                String data = scanner1.nextLine();
                String[] array = data.split("\\s+");
                if (array.length == 1) {
                    nUji++;
                }
            }
            scanner1.close();

            return nUji;
        } catch (Exception e) {
            if (e.toString().contains("FileNotFoundException")) {
                System.out.println("File tidak ditemukan");
            } else {
                System.out.println("Input titik tidak valid");
            }
            return 0;
        }
    }

    private static double readInterpolTestFromFile(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            double uji = 0.0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!scanner.hasNextLine()) { // Baris terakhir
                    uji = Double.parseDouble(line.trim());
                }
            }
            scanner.close();
            return uji;
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
            return 0;
        } catch (Exception e) {
            System.out.println("Error membaca file: " + e.getMessage());
            return 0;
        }


    }

    // Tambahkan kelas Matrix dan InputOutput jika belum ada
}
