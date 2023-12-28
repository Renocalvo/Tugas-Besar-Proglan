package com.example.tugasbesar;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.*;
import java.util.*;

public class BankNew extends Application {
    private double saldo;
    private TextField saldoTextField;
    private Stage primaryStage;
    private Stage loginStage;

    //Testing : private String noRekLoggedIn;
    private User[] users;

    private int loggedInUserIndex = -1;


    //Class yang menjadi Buffer untuk menyimpan nilai No. Rekening, No. PIN, dan Saldo selama program berjalan
    private class User{
        String noRek;
        String noPIN;
        double tabungan;

        //inisialisasi variabel
        User(String noRek, String noPIN, double tabungan){
            this.noRek = noRek;
            this.noPIN = noPIN;
            this.tabungan = tabungan;
        }

    }

    /** readUserFromFile
     * digunakan untuk membaca file dengan list menjadi Userlist
     * membuat user dan mengconvert nilai dari user ke dalam User[array]
     * prekondisi : teredapat file sisa_Saldo.txt
     * postkondisi : isi file menjadi noRek, noPIN, dan saldo
     */
    private void readUsersFromFile(){
        String filepath = "C:\\Users\\Reno\\IdeaProjects\\TugasBesar\\src\\main\\java\\com\\example\\tugasbesar\\Database.txt";

        //API JAVA.IO = filepath
        try (Scanner scanner = new Scanner(new File(filepath))) {
            //API JAVA.util = List
            List<User> userList = new ArrayList<>();

            while (scanner.hasNext()) {
                String noRek = scanner.nextLine().trim();
                String noPIN = scanner.nextLine().trim();
                double tabungan = Double.parseDouble(scanner.nextLine().trim());

                // Create a new User and add it to the list
                User user = new User(noRek, noPIN, tabungan);
                userList.add(user);

                // Skip the empty line
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }

            // Convert the list to an array
            users = userList.toArray(new User[0]);

        } catch (FileNotFoundException e) {
            showAlert("File not found: " + filepath);
        } catch (NumberFormatException e) {
            showAlert("Error parsing tabungan value in the file: " + filepath);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //API JAVA.array = User[0]
        users = new User[0];
        readUsersFromFile();
        this.primaryStage = primaryStage;
        tampilkanFormLogin();
    }

    private void tampilkanFormLogin() {
        // Membuat form login
        //API JAVA.FX -> Stage
        loginStage = new Stage();
        loginStage.setTitle(" Login ATM Java GUI");

        VBox vboxRoot = new VBox(10);
        //API Style GUI -> setStyle
        vboxRoot.setStyle("-fx-background-color: #ffff; -fx-padding: 20;");
        //API JAVA.FX -> Scene
        Scene scene = new Scene(vboxRoot, 300, 250);

        //API JAVA.FX -> Label
        Label appNameLabel = new Label(" ATM Java GUI");
        appNameLabel.setStyle("-fx-font-size: 17; -fx-font-weight: 700; -fx-text-fill: #333333;");

        //API JAVA.FX -> TextField
        TextField usernameField = new TextField();
        usernameField.setPromptText("No.Rekening");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("PIN");

        //API JAVA.FX -> Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0288d1; -fx-text-fill: white;");

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;");

        // Membuat HBox untuk tombol "Login" dan "Cancel"
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(loginButton, cancelButton);

        vboxRoot.getChildren().addAll(appNameLabel, usernameField, passwordField, buttonBox);
        vboxRoot.setAlignment(Pos.CENTER);
        vboxRoot.setSpacing(10);
        vboxRoot.setPadding(new Insets(20));

        // Menengahkan HBox dengan tombol "Login" dan "Cancel"
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), loginStage));
        cancelButton.setOnAction(e -> loginStage.close());
        loginStage.setScene(scene);
        loginStage.show();
    }

    //Method untuk tombol login berfungsi untuk mencek No.rekening dan No.Pin
    //Pembuatan User
    private void handleLogin(String noRek, String noPIN, Stage loginStage) {
        try {
            boolean isValid = false;
            for (int i = 0; i < users.length; i++) {
                User user = users[i];
                if (user.noRek.equals(noRek) && user.noPIN.equals(noPIN)) {
                    validateInput(noRek, noPIN);
                    isValid = true;
                    saldo = user.tabungan;
                    //Testing : noRekLoggedIn = user.noRek;
                    loggedInUserIndex = i;
                    break;
                }
            }

            if (isValid) {
                System.out.println("Login successful!");
                loginStage.close();
                tampilkanPrimaryStage();
            } else {
                System.out.println("Login failed. Please try again.");
                showAlert("Login failed Incorect No Rekening or PIN. Please try again.");
            }
        }catch (IllegalArgumentException e){
            showAlert(e.getMessage());
        }

    }

    //Method validasi Regex agar no rek harus berupa Integer dan harus berjumlah 4
    private void validateInput(String noRek, String noPIN) throws IllegalArgumentException{
        //validator inputan string harus berupa regex dalam integer
        if (!noRek.matches("\\d+")){
            throw new IllegalArgumentException("Invalid Rekening Number. Must contain only digits.");
        }
        if (!noPIN.matches("\\d+")){
            throw new IllegalArgumentException("Invalid PIN Number. Must contain digits.");
        }
    }

    private void tampilkanPrimaryStage() {
        //Judul aplikasi
        primaryStage.setTitle("Aplikasi ATM Java GUI");

        // Membuat GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER); // Menengahkan GridPane

        // Menyesuaikan lebar kolom
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true); // Mengizinkan kolom untuk mengisi lebar yang tersedia
        columnConstraints.setHgrow(Priority.ALWAYS); // Memberikan prioritas untuk berkembang ke sebelah

        // Menambahkan kolomConstraints ke GridPane
        gridPane.getColumnConstraints().add(columnConstraints);



        // Label dan TextField untuk Saldo
        Label mainMenu = new Label("Selamat Datang, Nasabah Tercinta");
        mainMenu.setStyle("-fx-font-size: 15; -fx-font-weight: 700; -fx-text-fill: #000;");
        mainMenu.setWrapText(true);
        GridPane.setConstraints(mainMenu,0,0,2,1);
        Label saldoLabel = new Label("Saldo:");
        GridPane.setConstraints(saldoLabel, 0, 2, 2, 1);
        saldoTextField = new TextField();
        saldoTextField.setEditable(false);
        saldoTextField.setText(String.format("Rp.%,.2f", saldo));
        GridPane.setConstraints(saldoTextField, 0, 3, 2, 1);
        GridPane.setHalignment(saldoTextField, HPos.CENTER);

        // Button Cek Saldo
        Button cekSaldoButton = new Button("Cek Saldo");
        GridPane.setConstraints(cekSaldoButton, 0, 4, 1, 1);
        cekSaldoButton.setOnAction(e -> cekSaldo());
        cekSaldoButton.setPrefWidth(100);
        GridPane.setHalignment(cekSaldoButton, HPos.CENTER);
        GridPane.setValignment(cekSaldoButton, VPos.CENTER);
        cekSaldoButton.getStyleClass().add("centered-button");

        // Button Simpan
        Button simpanButton = new Button("Simpan");
        GridPane.setConstraints(simpanButton, 1, 4, 1, 1);
        simpanButton.setOnAction(e -> simpan());
        simpanButton.setPrefWidth(100);
        GridPane.setHalignment(simpanButton, HPos.CENTER);
        GridPane.setValignment(simpanButton, VPos.CENTER);
        simpanButton.getStyleClass().add("centered-button");

        // Button Transfer
        Button transferButton = new Button("Transfer");
        GridPane.setConstraints(transferButton, 0, 5, 1, 1);
        transferButton.setOnAction(e -> transfer());
        transferButton.setPrefWidth(100);
        GridPane.setHalignment(transferButton, HPos.CENTER);
        GridPane.setValignment(transferButton, VPos.CENTER);
        transferButton.getStyleClass().add("centered-button");

        // Button Ambil
        Button ambilButton = new Button("Ambil");
        GridPane.setConstraints(ambilButton, 1, 5, 1, 1);
        ambilButton.setOnAction(e -> ambil());
        ambilButton.setPrefWidth(100);
        GridPane.setHalignment(ambilButton, HPos.CENTER);
        GridPane.setValignment(ambilButton, VPos.CENTER);
        ambilButton.getStyleClass().add("centered-button");


        // Button Keluar
        Button keluarButton = new Button("Keluar");
        GridPane.setConstraints(keluarButton, 1, 6, 1, 1);
        keluarButton.setOnAction(e -> keluar(primaryStage));
        keluarButton.setPrefWidth(100);
        GridPane.setHalignment(keluarButton, HPos.CENTER);

        // Menambahkan elemen-elemen ke GridPane
        gridPane.getChildren().addAll(
                saldoLabel, saldoTextField,
                cekSaldoButton, simpanButton,
                transferButton, ambilButton,
                mainMenu, keluarButton
        );


        // Membuat scene dan menetapkannya ke primaryStage
        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setScene(scene);

        // Menampilkan primaryStage
        primaryStage.show();
    }


    /**
     * Pre kondisi : Saldo harus berupa angka Integer >= 0
     * post kondisi : return nilai saldo berupa Integer
     */
    private void cekSaldo() {
        showAlert("Saldo Anda saat ini adalah:" + saldo);
    }

    /**
     * pre kondisi 1 : Inputan harus berupa angka Integer >= 0, dengan kelipatan %d 50.000
     * pre kondisi 2 : Inputan maksimal 1.500.000
     * Post Konsidi : Nilai saldo akan di simpan didalam Buffer
     */
    private void simpan() {
        try {
            String depositAmountInput = showAlertWithTextInput("Masukkan jumlah yang disimpan(kelipatan Rp.50.000:");
            double masukan = Double.parseDouble(depositAmountInput);

            if (masukan % 50000 != 0) {
                throw new IllegalArgumentException("Masukkan Nominal yang valid (harus kelipatan Rp.50.000)");
            }

            if (masukan <= 0) {
                throw new IllegalArgumentException("Nominal yang anda masukkan tidak valid");
            }

            if (masukan > 1500000) {
                throw new IllegalArgumentException("Maaf Nominal Yang Anda Masukan Melebihi Ketentuan");
            }

            saldo += masukan;
            updateSaldoText();
        } catch (NumberFormatException e) {
            showAlert("Input harus berupa angka");
        } catch (IllegalArgumentException e) {
            showAlert(e.getMessage());
        }
    }


    /**
     * Pre kondisi 1 : Pengguna menginputkan No.Rekening Sesuai dengan Buffer, dengan Regex tertentu
     * Post kondisi : Saldo - masukan == saldo akhir
     * Post kondisi : Masukan += tabungan
     */
    private void transfer() {
        //Kodingan harus diganti dengan No rekening yang punya Regex dengan mengimplementasikan I/O
        try {
            String namaPenerima = showAlertWithTextInput("Masukkan nomor rekening:");
            String transferAmountInput = showAlertWithTextInput("Masukkan Nominal Transfer:");
            double masukan = Double.parseDouble(transferAmountInput);

            if (namaPenerima.trim().isEmpty()) {
                throw new IllegalArgumentException("Nomor rekening penerima tidak boleh kosong");
            }

            if(masukan <= 15000 ){
                throw new IllegalArgumentException("Nominal yang anda masukkan tidak valid");
            }

            if (masukan > saldo){
                throw new IllegalArgumentException("Saldo anda tidak mencukupi");
            }

            saldo-=masukan;
            String noRekPenerima = findNorekByNama(namaPenerima);
            transferSaldo(noRekPenerima,masukan);

            showAlert("Transfer berhasil kepada"+namaPenerima+" sebesar Rp."+masukan);
            updateSaldoText();

        } catch (NumberFormatException e) {
            showAlert("!Operasi Failed");
        } catch (IllegalArgumentException | ArithmeticException e) {
            showAlert(e.getMessage());
        }
    }

    //Method yang digunakan untuk menemukan no Rekening tujuan pada file
    private String findNorekByNama(String namaPenerima) {
        for (User user : users) {
            if (namaPenerima.equals(user.noRek)) {
                if (!user.noRek.equals(users[loggedInUserIndex].noRek)) {
                    return user.noRek;
                } else {
                    throw new IllegalArgumentException("Anda tidak dapat mentransfer pada diri anda");
                }
            }
        }

        // Jika nomor rekening penerima tidak ditemukan
        throw new IllegalArgumentException("Nomor Rekening Tidak ditemukan");
    }


    //method yang digunakan untuk mengurangi nilai saldo dan menambah tabungan tujuan
    private void transferSaldo(String noRekPenerima, double masukan){
        try {
            for (User user : users){
                if (noRekPenerima.equals(user.noRek)){
                    user.tabungan += masukan;
                    updateBalance();
                    writeToFile("Database.txt", getUsersContent());
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //method yang digunakan untuk mengambil nilai yang tersedia
    private String getUsersContent() {
        StringBuilder content = new StringBuilder();
        for (User user : users) {
            content.append(user.noRek).append("\n");
            content.append(user.noPIN).append("\n");
            content.append(user.tabungan).append("\n");
            content.append("\n");
        }
        return content.toString();
    }
    /**
     * pre kondisi 1 : Saldo >= 0, jika saldo kurang dari masukan maka tidak dapat melakukan penarikan
     * pre kondisi 2 : masukan >= 0 , berupa integer
     * pre kondisi 3 : masukan <= 1.500.000
     * post kondisi : Saldo - masukan = saldo akhir
     */
    private void ambil() {
        try {
            String ambilAmountInput = showAlertWithTextInput("Masukkan Nominal yang Ditarik (Nominal Kelipatan Rp. 50.000):");
            double masukan = Double.parseDouble(ambilAmountInput);

            if (masukan % 50000 != 0) {
                throw new IllegalArgumentException("Masukkan Nominal yang valid (harus kelipatan Rp.50.000)");
            }
            if(masukan <= 0){
                throw new IllegalArgumentException("Nominal yang anda masukkan tidak valid");
            }

            if (masukan > 1500000) {
                throw new IllegalArgumentException("Maaf Nominal Yang Anda Masukan Melebihi Ketentuan");
            }

            if (saldo < masukan) {
                throw new ArithmeticException("Maaf, Saldo Anda tidak mencukupi untuk penarikan ini");
            } else {
                saldo -= masukan;
                showAlert("Penarikan berhasil sebesar " + masukan);
                updateSaldoText();
            }
        } catch (NumberFormatException e) {
            showAlert("Input harus berupa angka");
        } catch (IllegalArgumentException | ArithmeticException e) {
            showAlert(e.getMessage());
        }
    }


    /**
     * pre kondisi : program telah dijalankan
     * post kondisi : menghentikan eksekusi program yang berjalan
     * post kondisi terbaru : me-write file sisa_Saldo.txt
     */
    private void keluar(Stage primaryStage) {
        try {

            //menulis informasi saldo ke dalam file text
            StringBuilder content = new StringBuilder();
            for (User user : users){
                updateBalance();
                content.append(user.noRek).append("\n");
                content.append(user.noPIN).append("\n");
                content.append(user.tabungan).append("\n");
                content.append("\n");

            }

            //API JAVA.FORMATING -> String.format
            writeToFile("Database.txt", content.toString());

            // reset data login
            loggedInUserIndex = -1;
            saldo = 0;
            //Testing : noRekLoggedIn = null;

            System.out.println("Writing the File");
            primaryStage.close();
            tampilkanFormLogin();
        }catch (IOException e){
            showAlert("Error while writing to file : ");
        }

    }

    private void updateBalance() {
        if (loggedInUserIndex != -1){
            users[loggedInUserIndex].tabungan = saldo;
        }
    }

    //Method yang digunakan untuk menulis/write pada file sisa_Saldo.txt
    //Tujuannya agar user mempunyai berkas sisa saldonya
    private void writeToFile(String filename, String content) throws IOException{
        String filePath = "C:\\Users\\Reno\\IdeaProjects\\TugasBesar\\src\\main\\java\\com\\example\\tugasbesar\\"+filename;

        //API JAVA.IO -> PrintWriter, FileWriter
        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(content);
        }
    }


    // Pre-kondisi: Pesan tidak kosong.
    // Post-kondisi: Menampilkan dialog input dan mengembalikan nilai yang dimasukkan oleh pengguna.
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String showAlertWithTextInput(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        //API JAVA.UTILITY -> Optional
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    // Pre-kondisi: -
    // Post-kondisi: Memperbarui teks saldo dalam saldoTextField.
    private void updateSaldoText() {
        saldoTextField.setText(String.format("Rp %,.2f", saldo));
    }


}