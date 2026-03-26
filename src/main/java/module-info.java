module org.example.loginapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jdk.compiler;


    opens org.example.loginapp to javafx.fxml;
    exports org.example.loginapp;

    opens org.example.loginapp.model to javafx.fxml, javafx.base;
    opens org.example.loginapp.controller to javafx.fxml;
}