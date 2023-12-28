package com.example.tugasbesar;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFX extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Login Form");

        VBox vboxRoot = new VBox(10);
        vboxRoot.setStyle("-fx-background-color: #ffff; -fx-padding: 20;");
        Scene scene = new Scene(vboxRoot, 300, 250);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-prompt-text-fill: #17202A; -fx-text-fill: black;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-prompt-text-fill: #17202A; -fx-text-fill: black;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0288d1; -fx-text-fill: white;");

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;");

        vboxRoot.getChildren().addAll(usernameField, passwordField, loginButton, cancelButton);

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        cancelButton.setOnAction(e -> primaryStage.close());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
