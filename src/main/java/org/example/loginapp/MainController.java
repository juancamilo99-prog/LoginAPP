package org.example.loginapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.loginapp.controller.GestorLogin;
import org.example.loginapp.model.Admnistrator;
import org.example.loginapp.model.Employed;
import org.example.loginapp.model.Person;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button btnIngresar;

    @FXML
    private TextField password;

    @FXML
    private Text textError;

    @FXML
    private TextField username;

    GestorLogin gestorLogin = new GestorLogin();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnIngresar.setOnAction(event -> searchLogin());
    }

    public void searchLogin(){
        String email = username.getText();
        String pass = password.getText();
        try{
            Person persona = gestorLogin.logIn(email.toLowerCase().trim(), pass.trim());
            cleanWindow();
            if (email.isEmpty() || pass.isEmpty()) {
                textError.setText("Rellene los campos");
                textError.setFill(Color.RED);
                return;
            }

            Stage stage = (Stage) username.getScene().getWindow();
            if (persona instanceof Admnistrator admnistrator) {
                textError.setText("Usuario encontrado");
                textError.setFill(Color.GREEN);
                admnistrator.EmployeedRoll(stage);
            }else if (persona instanceof Employed employed) {
                textError.setText("Usuario empleado encontrado");
                textError.setFill(Color.GREEN);
                employed.EmployeedRoll(stage);
            } else {
                textError.setText("Usuario no encontrado");
                textError.setFill(Color.RED);
            }

        }catch (Exception e){
            cleanWindow();
            textError.setText("El usuario no existe");
            textError.setFill(Color.RED);
        }
    }

    public void cleanWindow(){
        username.clear();
        password.clear();
        textError.setText("");
    }
}
