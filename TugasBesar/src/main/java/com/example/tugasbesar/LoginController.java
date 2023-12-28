package com.example.tugasbesar;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Bank bank; // Objek Bank yang akan digunakan

    public void initialize() {
        // Inisialisasi logika kontroler (jika diperlukan)
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Logika login di sini

        if (bank != null) {
            // Gunakan objek Bank
            boolean loginSuccess = bank.login(username, password);
            if (loginSuccess) {
                System.out.println("Login successful!");
            } else {
                // Jika login gagal, tampilkan pesan kesalahan
                System.out.println("Login failed. Please try again.");
            }
        }
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
