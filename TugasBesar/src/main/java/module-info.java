module com.example.tugasbesar {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tugasbesar to javafx.fxml;
    exports com.example.tugasbesar;
}