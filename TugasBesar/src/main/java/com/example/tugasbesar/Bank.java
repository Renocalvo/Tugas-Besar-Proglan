package com.example.tugasbesar;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;

public class Bank extends Application {
    /**parameter dalam program
     */
    private double saldo;
    private TextField saldoTextField;

    //Main method :
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start Method :
     * Method untuk mengeksekusi program agar langsung berjalan
     */
    @Override
    public void start(Stage primaryStage) {
        //Judul aplikasi
        primaryStage.setTitle("Aplikasi Bank");

        // Membuat GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER); // Menengahkan GridPane

        // Label Selamat Datang
        Label welcomeLabel = new Label("Selamat Datang di");
        welcomeLabel.setFont(Font.font("Times New Roman", 16));
        welcomeLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(welcomeLabel, 0, 0, 2, 1); // atas, kanan, bawah , kiri
        GridPane.setHalignment(welcomeLabel, HPos.CENTER);

        // Label  ATM Java GUI
        Label appNameLabel = new Label(" ATM Java GUI");
        appNameLabel.setFont(Font.font("Times New Roman", 16));
        appNameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(appNameLabel, 0, 1, 2, 1); // atas, kanan, bawah , kiri
        GridPane.setHalignment(appNameLabel, HPos.CENTER);

        // Label dan TextField untuk Saldo
        Label saldoLabel = new Label("Saldo:");
        GridPane.setConstraints(saldoLabel, 0, 2, 2, 1);
        saldoTextField = new TextField();
        saldoTextField.setEditable(false);
        GridPane.setConstraints(saldoTextField, 0, 3, 2, 1);
        GridPane.setHalignment(saldoTextField, HPos.CENTER);

        // Button Cek Saldo
        Button cekSaldoButton = new Button("Cek Saldo");
        GridPane.setConstraints(cekSaldoButton, 0, 4, 1, 1);
        cekSaldoButton.setOnAction(e -> cekSaldo());
        GridPane.setHalignment(cekSaldoButton, HPos.CENTER);

        // Button Simpan
        Button simpanButton = new Button("Simpan");
        GridPane.setConstraints(simpanButton, 1, 4, 1, 1);
        simpanButton.setOnAction(e -> simpan());
        GridPane.setHalignment(simpanButton, HPos.CENTER);

        // Button Transfer
        Button transferButton = new Button("Transfer");
        GridPane.setConstraints(transferButton, 0, 5, 1, 1);
        transferButton.setOnAction(e -> transfer());
        GridPane.setHalignment(transferButton, HPos.CENTER);

        // Button Ambil
        Button ambilButton = new Button("Ambil");
        GridPane.setConstraints(ambilButton, 1, 5, 1, 1);
        ambilButton.setOnAction(e -> ambil());
        GridPane.setHalignment(ambilButton, HPos.CENTER);

        // Button Clear
        Button clearButton = new Button("Clear");
        GridPane.setConstraints(clearButton, 0, 6, 1, 1);
        clearButton.setOnAction(e -> clear());
        GridPane.setHalignment(clearButton, HPos.CENTER);

        // Button Keluar
        Button keluarButton = new Button("Keluar");
        GridPane.setConstraints(keluarButton, 1, 6, 1, 1);
        keluarButton.setOnAction(e -> keluar(primaryStage));
        GridPane.setHalignment(keluarButton, HPos.CENTER);

        // Menambahkan elemen-elemen ke GridPane
        gridPane.getChildren().addAll(
                welcomeLabel, appNameLabel,
                saldoLabel, saldoTextField,
                cekSaldoButton, simpanButton,
                transferButton, ambilButton,
                clearButton, keluarButton
        );

        // Membuat scene dan menetapkannya ke primaryStage
        Scene scene = new Scene(gridPane, 300, 400); // Menambahkan sedikit tinggi agar muat
        primaryStage.setScene(scene);

        // Menampilkan primaryStage
        primaryStage.show();
    }

    //Harus membuat Pengimplementasian JAVA I/O

    /**
     * Pre kondisi : Saldo harus berupa angka Integer >= 0
     * post kondisi : return nilai saldo berupa Integer
     */
    private void cekSaldo() {
        showAlert("Saldo Anda saat ini adalah: " + saldo);
    }

    /**
     * pre kondisi 1 : Inputan harus berupa angka Integer >= 0, dengan kelipatan %d 50.000
     * pre kondisi 2 : Inputan maksimal 1.500.000
     * Post Konsidi : Nilai saldo akan di simpan didalam Buffer
     */
    private void simpan() {
        try {
            String depositAmountInput = showAlertWithTextInput("Masukkan jumlah yang disimpan(kelipatan Rp.50.0000:");
            double masukan = Double.parseDouble(depositAmountInput);

            if (masukan <= 0 && masukan % 50000 != 0 ) {
                showAlert("Masukkan Nominal yang valid (harus kelipatan Rp.50.000)");
                return;
            }

            if (masukan > 1500000){
                showAlert("Maaf Nominal Yang Anda Masukan Melebihi Ketentuan");
            }

            saldo += masukan;
            updateSaldoText();
        } catch (NumberFormatException e) {
            showAlert("Input harus berupa angka");
        }
    }

    /**
     * Pre kondisi 1 : Pengguna menginputkan No.Rekening Sesuai dengan Buffer, dengan Regex tertentu
     * Pre Kondisi 2 : Inputan saldo yang di tranfer >= 0 && %50.0000
     * Post kondisi : Saldo - masukan == saldo akhir
     */
    private void transfer() {
        //Kodingan harus diganti dengan No rekening yang punya Regex dengan mengimplementasikan I/O
        try {
            String namaPenerima = showAlertWithTextInput("Masukkan nama penerima:");
            String transferAmountInput = showAlertWithTextInput("Masukkan Nominal Transfer:");
            double masukan = Double.parseDouble(transferAmountInput);

            if (masukan <= 0 && masukan % 50000 != 0) {
                showAlert("Masukkan jumlah transfer yang valid (Nominal Kelipatan Rp, 50.000)");
                return;
            }

            if (saldo < masukan) {
                showAlert("Maaf, Saldo Anda tidak mencukupi untuk transfer ini");
            } else {
                saldo -= masukan;
                showAlert("Transfer berhasil kepada " + namaPenerima + " sebesar " + masukan);
                updateSaldoText();
            }
        } catch (NumberFormatException e) {
            showAlert("Input harus berupa angka");
        }
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

            if (masukan <= 0 && masukan % 50000 != 0) {
                showAlert("Masukkan Nominal penarikan yang valid (harus kelipatan Rp. 50.000)");
                return;
            }

            if (masukan >1500000){
                showAlert("Nominal Yang Anda Tarik Melebihi Batas Jumlah Penarikan");
            }

            if (saldo < masukan) {
                showAlert("Maaf, Saldo Anda tidak mencukupi untuk penarikan ini");
            } else {
                saldo -= masukan;
                showAlert("Penarikan berhasil sebesar " + masukan);
                updateSaldoText();
            }
        } catch (NumberFormatException e) {
            showAlert("Input harus berupa angka");
        }
    }

    /**
     * pre kondisi : Terdapat Output/isi pada text field pada method cek saldo
     * post kondisi : Menghapus isi text field
     */
    private void clear() {
        saldoTextField.clear();
    }

    /**
     * pre kondisi : program telah dijalankan
     * post kondisi : menghentikan eksekusi program yang berjalan
     */
    private void keluar(Stage primaryStage) {
        primaryStage.close();
    }

    public boolean login(String username, String password) {
        // Logika login di sini
        return "admin".equals(username) && "admin".equals(password);
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

        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    // Pre-kondisi: -
    // Post-kondisi: Memperbarui teks saldo dalam saldoTextField.
    private void updateSaldoText() {
        saldoTextField.setText(String.format("Rp %.2f", saldo));
    }



}