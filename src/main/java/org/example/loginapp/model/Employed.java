package org.example.loginapp.model;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.loginapp.MainController;
import org.example.loginapp.controller.EmployedController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Setter
@Getter
public class Employed extends Person {

    private String horaFichaje;
    //contructor vacio
    public Employed() {

    }

    public Employed(String name, String lastname, String dni, String email, String password, String horaFichaje) {
        super(name, lastname, dni, email, password);
        this.horaFichaje = horaFichaje;
    }

    public Employed(String name, String lastname, String dni, String email, String password) {
        super(name, lastname, dni, email, password);
    }

    public String fichar(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return now.format(dtf);
    }

    public void mostrarDatos(){
        System.out.printf("Nombre: %s\n"+
                        "Apellido: %s\n" +
                        "DNI: %s\n" +
                        "Correo: %s\n" +
                        "Contraseña: %s\n" +
                "hora fichada: %s\n", getName(), getLastname(), getDni(),getEmail(),getPassword(),getHoraFichaje());
    }

    @Override
    public String toString() {
        return String.format("%s",getEmail());
    }

    @Override
    public void EmployeedRoll(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("employedvisual.fxml"));
        Parent root = loader.load();
        EmployedController controller = loader.getController();
        controller.setEmployed(this);
        stage.setScene(new Scene(root));
        stage.setTitle("Menu Employed");
        stage.show();
        System.out.println("Usuario empleado");
    }
}
