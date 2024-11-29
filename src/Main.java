import matrix.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import spl.BalikanMatrix;
import spl.Determinan;
import spl.Cramer;
import spl.Gauss;
import spl.GaussJordan;
import bicubic.BicubicSpline;
import interpolasi.interPolim;
import regresiBerganda.RegresiBerganda;
import savetofile.Save;

public class Main {

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            displayExit();
        }));
        @SuppressWarnings("resource")
        Scanner userInput = new Scanner(System.in);
        displayWelcome();

        while (true) {
            displayMenu();
            int choice = getValidInput(userInput, 1, 8);
            System.out.println();

            if (choice == 8) {
                displayExit();
                System.out.println("Keluar dari program.");
                break;
            }

            String namafile;
            Matrix M = null;

            switch (choice) {
                case 1:
                    handleSPL(userInput);
                    break;
                case 2:
                    // Implementasi untuk menghitung Determinan
                    handleDeterminan(userInput);
                    break;
                case 3:
                    // Implementasi untuk Matriks Balikan
                    handleInvers(userInput);
                    break;
                case 4:
                    // Implementasi untuk Interpolasi Polinom
                    handleInterpol(userInput);
                    break;
                case 5:
                    handleRegresi(userInput);
                    // Implementasi untuk Regresi Linear Berganda
                    break;
                case 6:
                    handleBicubic(userInput);
                    // Implementasi untuk Interpolasi Bicubic Spline
                    break;
                case 7:
                    System.err.println("Masih dalam Maintance");
                    // Implementasi untuk Resize Image
                    break;
            }
        }
        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void displayWelcome(){
        System.out.println("                                                                                                                                                              ");
        System.out.println("   SSSSSSSSSSSSSSS HHHHHHHHH     HHHHHHHHH               AAA               ZZZZZZZZZZZZZZZZZZZ               AAA               MMMMMMMM               MMMMMMMM");
        System.out.println(" SS:::::::::::::::SH:::::::H     H:::::::H              A:::A              Z:::::::::::::::::Z              A:::A              M:::::::M             M:::::::M");
        System.out.println("S:::::SSSSSS::::::SH:::::::H     H:::::::H             A:::::A             Z:::::::::::::::::Z             A:::::A             M::::::::M           M::::::::M");
        System.out.println("S:::::S     SSSSSSSHH::::::H     H::::::HH            A:::::::A            Z:::ZZZZZZZZ:::::Z             A:::::::A            M:::::::::M         M:::::::::M");
        System.out.println("S:::::S              H:::::H     H:::::H             A:::::::::A           ZZZZZ     Z:::::Z             A:::::::::A           M::::::::::M       M::::::::::M");
        System.out.println("S:::::S              H:::::H     H:::::H            A:::::A:::::A                  Z:::::Z              A:::::A:::::A          M:::::::::::M     M:::::::::::M");
        System.out.println(" S::::SSSS           H::::::HHHHH::::::H           A:::::A A:::::A                Z:::::Z              A:::::A A:::::A         M:::::::M::::M   M::::M:::::::M");
        System.out.println("  SS::::::SSSSS      H:::::::::::::::::H          A:::::A   A:::::A              Z:::::Z              A:::::A   A:::::A        M::::::M M::::M M::::M M::::::M");
        System.out.println("    SSS::::::::SS    H:::::::::::::::::H         A:::::A     A:::::A            Z:::::Z              A:::::A     A:::::A       M::::::M  M::::M::::M  M::::::M");
        System.out.println("       SSSSSS::::S   H::::::HHHHH::::::H        A:::::AAAAAAAAA:::::A          Z:::::Z              A:::::AAAAAAAAA:::::A      M::::::M   M:::::::M   M::::::M");
        System.out.println("            S:::::S  H:::::H     H:::::H       A:::::::::::::::::::::A        Z:::::Z              A:::::::::::::::::::::A     M::::::M    M:::::M    M::::::M");
        System.out.println("            S:::::S  H:::::H     H:::::H      A:::::AAAAAAAAAAAAA:::::A    ZZZ:::::Z     ZZZZZ    A:::::AAAAAAAAAAAAA:::::A    M::::::M     MMMMM     M::::::M");
        System.out.println("SSSSSSS     S:::::SHH::::::H     H::::::HH   A:::::A             A:::::A   Z::::::ZZZZZZZZ:::Z   A:::::A             A:::::A   M::::::M               M::::::M");
        System.out.println("S::::::SSSSSS:::::SH:::::::H     H:::::::H  A:::::A               A:::::A  Z:::::::::::::::::Z  A:::::A               A:::::A  M::::::M               M::::::M");
        System.out.println("S:::::::::::::::SS H:::::::H     H:::::::H A:::::A                 A:::::A Z:::::::::::::::::Z A:::::A                 A:::::A M::::::M               M::::::M");
        System.out.println(" SSSSSSSSSSSSSSS   HHHHHHHHH     HHHHHHHHHAAAAAAA                   AAAAAAAZZZZZZZZZZZZZZZZZZZAAAAAAA                   AAAAAAAMMMMMMMM               MMMMMMMM");
        System.out.println("                                                                                                                                                              ");
        System.out.println("==============================================================================================================================================================");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("---------------------------------------------------------------------SELAMAT DATANG-------------------------------------------------------------------------- ");
        System.out.println("-------------------------------------------------------------------CALCULATOR MATRIKS------------------------------------------------------------------------ ");
    }
    private static void displayExit(){
        System.out.println("                                                                                                                                                              ");
        System.out.println("   SSSSSSSSSSSSSSS HHHHHHHHH     HHHHHHHHH               AAA               ZZZZZZZZZZZZZZZZZZZ               AAA               MMMMMMMM               MMMMMMMM");
        System.out.println(" SS:::::::::::::::SH:::::::H     H:::::::H              A:::A              Z:::::::::::::::::Z              A:::A              M:::::::M             M:::::::M");
        System.out.println("S:::::SSSSSS::::::SH:::::::H     H:::::::H             A:::::A             Z:::::::::::::::::Z             A:::::A             M::::::::M           M::::::::M");
        System.out.println("S:::::S     SSSSSSSHH::::::H     H::::::HH            A:::::::A            Z:::ZZZZZZZZ:::::Z             A:::::::A            M:::::::::M         M:::::::::M");
        System.out.println("S:::::S              H:::::H     H:::::H             A:::::::::A           ZZZZZ     Z:::::Z             A:::::::::A           M::::::::::M       M::::::::::M");
        System.out.println("S:::::S              H:::::H     H:::::H            A:::::A:::::A                  Z:::::Z              A:::::A:::::A          M:::::::::::M     M:::::::::::M");
        System.out.println(" S::::SSSS           H::::::HHHHH::::::H           A:::::A A:::::A                Z:::::Z              A:::::A A:::::A         M:::::::M::::M   M::::M:::::::M");
        System.out.println("  SS::::::SSSSS      H:::::::::::::::::H          A:::::A   A:::::A              Z:::::Z              A:::::A   A:::::A        M::::::M M::::M M::::M M::::::M");
        System.out.println("    SSS::::::::SS    H:::::::::::::::::H         A:::::A     A:::::A            Z:::::Z              A:::::A     A:::::A       M::::::M  M::::M::::M  M::::::M");
        System.out.println("       SSSSSS::::S   H::::::HHHHH::::::H        A:::::AAAAAAAAA:::::A          Z:::::Z              A:::::AAAAAAAAA:::::A      M::::::M   M:::::::M   M::::::M");
        System.out.println("            S:::::S  H:::::H     H:::::H       A:::::::::::::::::::::A        Z:::::Z              A:::::::::::::::::::::A     M::::::M    M:::::M    M::::::M");
        System.out.println("            S:::::S  H:::::H     H:::::H      A:::::AAAAAAAAAAAAA:::::A    ZZZ:::::Z     ZZZZZ    A:::::AAAAAAAAAAAAA:::::A    M::::::M     MMMMM     M::::::M");
        System.out.println("SSSSSSS     S:::::SHH::::::H     H::::::HH   A:::::A             A:::::A   Z::::::ZZZZZZZZ:::Z   A:::::A             A:::::A   M::::::M               M::::::M");
        System.out.println("S::::::SSSSSS:::::SH:::::::H     H:::::::H  A:::::A               A:::::A  Z:::::::::::::::::Z  A:::::A               A:::::A  M::::::M               M::::::M");
        System.out.println("S:::::::::::::::SS H:::::::H     H:::::::H A:::::A                 A:::::A Z:::::::::::::::::Z A:::::A                 A:::::A M::::::M               M::::::M");
        System.out.println(" SSSSSSSSSSSSSSS   HHHHHHHHH     HHHHHHHHHAAAAAAA                   AAAAAAAZZZZZZZZZZZZZZZZZZZAAAAAAA                   AAAAAAAMMMMMMMM               MMMMMMMM");
        System.out.println("                                                                                                                                                              ");
        System.out.println("==============================================================================================================================================================");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("----------------------------------------------------------------------TERIMA KASIH--------------------------------------------------------------------------- ");
        System.out.println("-----------------------------------------------------------------SALAM PENGHUNI TAMFEST---------------------------------------------------------------------- ");
        System.out.println("-----------------------------------------------------------13523115-----13523057-----13523079---------------------------------------------------------------- ");
    }
    private static void displayMenu() {
        
        System.out.println("Menu:");
        System.out.println("1. Sistem Persamaan Linear");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Regresi Linear Berganda");
        System.out.println("6. Interpolasi Bicubic Spline");
        System.out.println("7. Resize Image");
        System.out.println("8. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void handleSPL(Scanner userInput) throws IOException {
        StringBuilder output = new StringBuilder(); 
        System.out.println("Pilih metode penyelesaian:");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss Jordan");
        System.out.println("3. Cramer");
        System.out.println("4. Matriks balikan");
        int metode = getValidInput(userInput, 1, 4);
        System.out.println();

        System.out.println("Pilih metode input:");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input dari file");
        int input = getValidInput(userInput, 1, 2);
        userInput.nextLine();

        Matrix M = null;
        if (input == 1) {
            M = InputOutput.readMatrixFromKeyboard();
        } else if (input == 2) {
            String path;
            path = getPath(userInput);
            if (path == null) return;
            M = InputOutput.readMatrixFromFile(path);
        }

        switch (metode) {
            case 1:
                M = Gauss.gaussSolution(M);
                M = Gauss.printSolutionGauss(M);
                output.append("Hasil SPL menggunakan Gauss:\n"); // Tambahkan ke output
                printMatrixOutput(M, output); // Cetak matriks ke output
                break;
            case 2:
                M = GaussJordan.gaussJordanSolution(M);
                Gauss.printSolutionGauss(M);
                output.append("Hasil SPL menggunakan Gauss Jordan:\n");
                printMatrixOutput(M, output);
                break;
            case 3:
                M = Cramer.cramerSolution(M);
                InputOutput.printMatrix(M);
                if (M!=null){
                    output.append("Hasil SPL menggunakan Kaidah Cramer:\n");
                     printMatrixOutput(M, output);
                }
                break;
            case 4:
                M = BalikanMatrix.gaussWithInverse(M);
                M = Gauss.printSolutionGauss(M);
                output.append("Hasil SPL menggunakan Balikan Matriks:\n");
                printMatrixOutput(M, output);
                break;
        }

        // Simpan output ke file
        while(true){
            System.out.println("Simpan hasil ke dalam file (y/n)?");
            String pilihan =userInput.nextLine().trim().toLowerCase();
            if (pilihan.equals("y")) {
                // System.out.println("Mau disimpan dalam folder apa?");
                // String namafolder = userInput.nextLine();
                System.out.println("Mau disimpan dengan nama file apa?");
                String namafile = userInput.nextLine();
                String hasilSPL=InputOutput.matrixToString(M);
                Save.saveResultToFile("Hasil SPL: \n" + hasilSPL, "output",namafile);
                System.out.println("Berhasil menyimpan hasil.");
            }else if (pilihan.equals("n")) {
                System.out.println("Hasil tidak disimpan.");
                break;
            }else{
                System.out.println("Masukkan invalid");
            } 
        }
        // String namafolder = "output";
        // String namaFile = "output_SPL";
        // //InputOutput.saveOutputToFile(output.toString()); // Simpan output
        // Save.saveResultToFile(output.toString(), namafolder, namaFile); // Simpan hasil ke file
    }
    private static void handleDeterminan(Scanner userInput) throws IOException{
         // Memilih metode perhitungan determinan
        System.out.println("Pilih metode perhitungan determinan:");
        System.out.println("1. Reduksi Baris");
        System.out.println("2. Ekspansi Kofaktor");
        int metode = getValidInput(userInput, 1, 2);
        System.out.println();

        // Memilih metode input matriks
        System.out.println("Pilih metode input:");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input dari file");
        int input = getValidInput(userInput, 1, 2);
        userInput.nextLine(); // Membersihkan buffer input

        Matrix M = null;
        if (input == 1) {
            M = InputOutput.readMatrixFromKeyboard();
        } else if (input == 2) {
            String path;
            path = getPath(userInput);
            if(path==null)return ;
            M = InputOutput.readMatrixFromFile(path);
        }

        // Menghitung determinan berdasarkan metode yang dipilih
        double determinan = 0;
        switch (metode) {
            case 1:
                determinan = Determinan.detOBE(M);
                System.out.println("Determinant (Reduksi Baris): " + determinan);
                break;
            case 2:
                determinan = Determinan.detKofaktor(M);
                System.out.println("Determinant (Ekspansi Kofaktor): " + determinan);
                break;
        }

        // Menyimpan hasil ke dalam file
        while(true){
            System.out.println("Simpan hasil ke dalam file (y/n)?");
            String pilihan =userInput.nextLine().trim().toLowerCase();
            if (pilihan.equals("y")) {
                // System.out.println("Mau disimpan dalam folder apa?");
                // String namafolder = userInput.nextLine();
                System.out.println("Mau disimpan dengan nama file apa?");
                String namafile = userInput.nextLine();
                Save.saveResultToFile("Determinant: " + determinan, "output",namafile);
                System.out.println("Berhasil menyimpan hasil.");
            }else if (pilihan.equals("n")) {
                System.out.println("Hasil tidak disimpan.");
                break;
            }else{
                System.out.println("Masukkan invalid");
            } 
        }
    }
    private static void handleInvers(Scanner userInput) throws IOException{
        System.out.println("Pilih metode perhitungan matriks balik:");
        System.out.println("1. Adjoin (Kofaktor)");
        System.out.println("2. OBE (Operasi Baris Elementer)");
        int metode = getValidInput(userInput, 1, 2);
        System.out.println();

        // Memilih metode input matriks
        System.out.println("Pilih metode input:");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input dari file");
        int input = getValidInput(userInput, 1, 2);
        userInput.nextLine(); // Membersihkan buffer input

        Matrix M = null;
        if (input == 1) {
            M = InputOutput.readMatrixFromKeyboard();
        } 
        else if (input == 2) {
            String path;
            path = getPath(userInput);
            if(path==null)return ;
            M = InputOutput.readMatrixFromFile(path);
        }

        Matrix invertedMatrix = null;
        switch (metode) {
            case 1:
                invertedMatrix = BalikanMatrix.balikanMatrixKofaktorSolution(M);
                System.out.println("Matriks Balik (Adjoin):");
                InputOutput.printMatrix(invertedMatrix);
                break;
            case 2:
                invertedMatrix = BalikanMatrix.balikanMatrixOBESolution(M);
                System.out.println("Matriks Balik (OBE):");
                InputOutput.printMatrix(invertedMatrix);
                break;
        }

        // Menyimpan hasil ke dalam file
        while(true){
            System.out.println("Simpan hasil ke dalam file (y/n)?");
            String pilihan = userInput.nextLine().trim().toLowerCase();
            if (pilihan.equals("y")) {
                // System.out.println("Mau disimpan dalam folder apa?");
                // String namafolder = userInput.nextLine();
                String hasil = InputOutput.matrixToString(invertedMatrix);
                System.out.println("Mau disimpan dengan nama file apa?");
                String namafile = userInput.nextLine();
                Save.saveResultToFile(hasil, "output",namafile);
                System.out.println("Berhasil menyimpan hasil.");
            }else if (pilihan.equals("n")) {
                System.out.println("Hasil tidak disimpan.");
                break;
            }else{
                System.out.println("Masukkan invalid");
            } 
        }
        
    }
    private static void handleInterpol(Scanner userInput) throws IOException{
        System.out.println("Pilih metode input:");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input dari file");
        int input = getValidInput(userInput, 1, 2);
        userInput.nextLine();
        if (input == 1){
            Matrix m = InputOutput.readInterpolasi();
            interPolim.interPolinom(m);
        }
        else if (input == 2){
            String path;
            path = getPath(userInput);
            if(path==null)return ;
            Matrix m = InputOutput.readMatrixFromFile(path);
            interPolim.interPolinomFile(m,path);
        }
        
    }
    private static void handleBicubic(Scanner userInput) throws IOException{
        
        String path ;
        path = getPath(userInput);
        Object[] hasil = InputOutput.bacaFileKeMatriks(path);
        Matrix bicubic = (Matrix) hasil[0];
        double a = (double) hasil[1];
        double b = (double) hasil[2];
        BicubicSpline.interpolasiBicubics(bicubic,a,b);
    }





    private static void handleRegresi(Scanner userInput) throws IOException {

        Matrix M = null;
        System.out.println("Pilih metode perhitungan regresi:");
        System.out.println("1. Regresi Linier Berganda");
        System.out.println("2. Regresi Kuadratik Berganda");
        
        int metode = getValidInput(userInput, 1, 2);
        System.out.println("Metode yang dipilih: " + metode);
        
        System.out.println("Pilih metode input:");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input dari file");
        
        int input = getValidInput(userInput, 1, 2);
        userInput.nextLine(); 
        String output="";
        Matrix predict=null;
        if (input == 1) {
            System.out.println("Input metode: Keyboard");
            M = InputOutput.readRegresiKeyboard();
            predict = new Matrix(M.getRowEff(), 1);
            System.out.println("Masukkan koefisien yang akan diprediksa: ");
            for (int i = 0; i<M.getColEff()-1; i++){
                System.out.printf("Masukkan peubah x" + (i+1) + ": " );
                double temp = userInput.nextDouble();
                predict.setElmt(i, 0, temp);
            }
            System.out.println("");
            } 
        else if (input == 2) {
            System.out.println("Input metode: File");
            String path = getPath(userInput);
            if (path == null) return;
            Matrix Mtemp = new Matrix(0, 0);
            Mtemp = InputOutput.readMatrixFromFile(path);
            predict = new Matrix(Mtemp.getColEff(),1 );
            M = new Matrix(Mtemp.getRowEff()-1,Mtemp.getColEff());
            
            // System.out.println("Masukkan koefisien yang akan diprediksa: ");
            for (int i = 0; i<Mtemp.getRowEff()-1; i++){
                for(int j=0;j<Mtemp.getColEff();j++){
                    M.setElmt(i, j, Mtemp.getElmt(i, j));
                }
            }

            for (int i = 0; i<Mtemp.getColEff(); i++){
                // System.out.printf("Masukkan peubah x" + (i+1) + ": " );
                double temp = Mtemp.getElmt(Mtemp.getRowEff()-1, i);
                predict.setElmt(i, 0, temp);
            }
            System.out.println("");
            // InputOutput.printMatrix(Mtemp);
            // System.out.println("done\n");
            // InputOutput.printMatrix(M);
            // System.out.println("done\n");
            // InputOutput.printMatrix(predict);
             
        }
        
        if (metode == 1) {
            output+="Hasil dari regresi linear :\n";
            M = RegresiBerganda.regresiLinear(M);
            output+=InputOutput.matrixToString(M);
            output+="\n";
            RegresiBerganda.estimateY(M, predict,output);;
        } 
        else if (metode == 2) {
            output+="Hasil dari regresi kuadratik :\n";
            M = RegresiBerganda.regresiKuadratik(M);
            output+=InputOutput.matrixToString(M);
            output+="\n";
            RegresiBerganda.printRegresiKuadrat(M, predict,output);
        }
    }


    public static void printMatrixOutput(Matrix matrix, StringBuilder output) {
        for (int i = 0; i < matrix.getRowEff(); i++) {
            for (int j = 0; j < matrix.getColEff(); j++) {
                output.append(matrix.getElmt(i, j)).append(" ");
            }
            output.append("\n");
        }
    }


    // Metode untuk mendapatkan input yang valid
    private static int getValidInput(Scanner userInput, int min, int max) {
        int input;
        do {
            System.out.print("Masukkan pilihan (" + min + "-" + max + "): ");
            while (!userInput.hasNextInt()) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                userInput.next(); // membersihkan input yang tidak valid
            }
            input = userInput.nextInt();   
        } while (input < min || input > max);
        return input;
    }




    private static String getPath(Scanner userInput) {
    String path = "";
    boolean fileFound = false;
        while (!fileFound) {
            System.out.print("Masukkan nama file: ");
            String file = userInput.next();
            path = "test/Input/" + file;
            System.out.println(path);
            
            File filee = new File(path);
            if (!filee.exists()) {
                System.out.println("File tidak ditemukan: " + path);
                System.out.print("Apakah Anda ingin mencoba lagi? (y/n): ");
                String choice = userInput.next().toLowerCase();
                if (!choice.equals("y")) {
                    System.out.println("Pengguna memilih untuk tidak mencari file lagi.");
                    return null; 
                }
            } else {
                System.out.println("File ditemukan: " + path);
                fileFound = true; 
            }
        }
    return path; 
    }


}