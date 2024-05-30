module com.example.gardenproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gardenproject to javafx.fxml;
    exports com.example.gardenproject;
}