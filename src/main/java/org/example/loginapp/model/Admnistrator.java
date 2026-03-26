package org.example.loginapp.model;

import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.loginapp.MainController;
import org.example.loginapp.controller.AdminController;

import java.io.IOException;

@Setter
@Getter
public class Admnistrator extends Person {

    //constructor vacio
    public Admnistrator() {
        super();
    }

    public Admnistrator(String name, String lastname, String dni, String email, String password) {

        super(name, lastname, dni, email, password);
    }

    public Admnistrator(String name, String lastname, String dni, String email) {
        super(name, lastname, dni, email);
    }

    @Override
    public void EmployeedRoll(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("adminvisual.fxml"));
        Parent root = loader.load();
        AdminController controller = loader.getController();
        controller.setAdmin(this);
        stage.setScene(new Scene(root));
        stage.setTitle("Menu Administrador");
        stage.show();
        System.out.println("Usuario administrador");
    }

}
