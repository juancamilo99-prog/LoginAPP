package org.example.loginapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.loginapp.MainController;
import org.example.loginapp.model.Employed;
import org.example.loginapp.model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployedController implements Initializable {
    @FXML
    private Button btnfichar;

    @FXML
    private Label btninfo;

    @FXML
    private Text employedname;
    @FXML
    private Label texthour;
    @FXML
    private Button btnclearseson;

    GestorEmployed gestorEmployed = new GestorEmployed();
    private Employed employed;
    private Person person;
    MainController  mainController = new MainController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnfichar.setOnAction(e -> setBtnfichar());
        btnclearseson.setOnAction(e -> closeSesion());
    }


    public void setEmployed(Employed employed) {
        this.employed = employed;
        employedname.setText(employed.getName().toUpperCase());
    }

    public void setBtnfichar(){
        gestorEmployed.registerSigning(employed);
        btninfo.setText("Fichaje realizado correctamente");
        texthour.setText("ultima hora de fichaje: "+employed.fichar());
        btninfo.setTextFill(Color.GREEN);
        texthour.setTextFill(Color.GREEN);
    }

    public void closeSesion(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/loginapp/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnclearseson.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
