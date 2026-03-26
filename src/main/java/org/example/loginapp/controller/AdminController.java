package org.example.loginapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.loginapp.model.Admnistrator;
import org.example.loginapp.model.Employed;
import org.example.loginapp.model.Person;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private Text adminName;

    @FXML
    private Button btnAlta;

    @FXML
    private Button btnclean;

    @FXML
    private Button btnload;

    @FXML
    private Button btnremove;

    @FXML
    private Button btnsearchdni;

    @FXML
    private Button btnupdatepassword;

    @FXML
    private CheckBox checkadmin;

    @FXML
    private TextField newpassword;

    @FXML
    private TextField removedni;

    @FXML
    private TextField searchdni;

    @FXML
    private TableColumn<Person, String> tableCargo;

    @FXML
    private TableColumn<Person, String> tableEmail;

    @FXML
    private TableColumn<Person, String> tablePassword;

    @FXML
    private TableColumn<Person, String> tabledni;

    @FXML
    private TableColumn<Person, String> tablename;

    @FXML
    private TableView<Person> tablesearchemployed;

    @FXML
    private TableColumn<Person, String> tablesecondname;

    @FXML
    private TextField tfdni;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfname;

    @FXML
    private TextField tfpassword;

    @FXML
    private TextField tfsecondnaem;

    @FXML
    private TextArea txtregistros;

    @FXML
    private TextField updatedni;

    @FXML
    private Text txtError;
    GestorAdministrator gestorAdministrator = new GestorAdministrator();
    private Person person;
    private ObservableList<Person> employedListobservable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAlta.setOnAction(actionEvent -> addRegisterusuarios());
        btnremove.setOnAction(actionEvent -> removeRegisterusuarios());
        btnupdatepassword.setOnAction(actionEvent -> updatePasswordUser());
        btnsearchdni.setOnAction(actionEvent -> setBtnbuscar());
        btnclean.setOnAction(actionEvent -> setBtnclean());
        btnload.setOnAction(actionEvent -> setbBtnLoadFichero());
        showEmployed();
    }

    public void setAdmin(Person person) {
        this.person = person;
        adminName.setText(person.getName().toUpperCase());
        configTable();
        showEmployed();
    }

    public void addRegisterusuarios(){
        String name_employed = tfname.getText();
        String secondname_employed = tfsecondnaem.getText();
        String email_employed = tfemail.getText();
        String password_employed = tfpassword.getText();
        String dni = tfdni.getText();
        boolean check_admin = checkadmin.isSelected();
        if (name_employed.isEmpty() ||  secondname_employed.isEmpty() || email_employed.isEmpty() || password_employed.isEmpty() || dni.isEmpty()){
            txtError.setText("Debe rellene todos los campos");
            txtError.setFill(Color.RED);
            clearWindow();
            return;
        }
        try{
            if(check_admin){
                gestorAdministrator.addAdmnistrator(new Admnistrator(name_employed,secondname_employed,dni, email_employed,password_employed));
                System.out.println("admnistrator added");
                txtError.setText("User Added Successfully");
                txtError.setFill(Color.GREEN);
                showEmployed();
                clearWindow();
            }else {
                gestorAdministrator.addEmployed(new Employed(name_employed,secondname_employed,dni,email_employed,password_employed));
                System.out.println("employed added");
                txtError.setText("User Added Successfully");
                txtError.setFill(Color.GREEN);
                showEmployed();
                clearWindow();
            }
        }catch (Exception e){
            clearWindow();
            txtError.setText("Error al grabar usuario.");
            txtError.setFill(Color.RED);
        }
    }

    public void removeRegisterusuarios(){
        String dni_remove = removedni.getText();
        if (dni_remove.isEmpty()){
            txtError.setText("Debe rellenar el campo");
            txtError.setFill(Color.RED);
            return;
        }

        try {
            boolean removeEmployed = gestorAdministrator.removeEmpleado(dni_remove);
            if (removeEmployed){
                txtError.setText("User Removed Successfully");
                txtError.setFill(Color.GREEN);
                removedni.setText("");
                showEmployed();
            }else  {
                txtError.setText("User no found");
                txtError.setFill(Color.RED);
                removedni.setText("");
            }
        }catch (Exception e){
            txtError.setText("Error al borrar usuario.");
            txtError.setFill(Color.RED);
        }
    }

    public void updatePasswordUser(){
        try{
            if (updatedni.getText().isEmpty() || newpassword.getText().isEmpty()){
                txtError.setText("Debe rellenar el campo");
                txtError.setFill(Color.RED);
                return;
            }
            boolean updateEmployed = gestorAdministrator.updatePassword(updatedni.getText(),newpassword.getText());
            if (updateEmployed){
                txtError.setText("User Updated Successfully");
                txtError.setFill(Color.GREEN);
                showEmployed();
                updatedni.clear();
                newpassword.clear();
            }else   {
                txtError.setText("User no found");
                txtError.setFill(Color.RED);
                updatedni.clear();
                newpassword.clear();
            }
        }catch (Exception e){
            txtError.setText("se ha producido un error");
            txtError.setFill(Color.RED);
        }
    }

    public void configTable(){
        tabledni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tablename.setCellValueFactory(new PropertyValueFactory<>("name"));
        tablesecondname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tableEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tablePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableCargo.setCellValueFactory(data -> {
            Person person = data.getValue();
            String cargo = (person instanceof Admnistrator) ? "Admnistrator" : "Employed";
            return new SimpleStringProperty(cargo);
        });
    }

    public void showEmployed(){
        tablesearchemployed.setItems((ObservableList<Person>) gestorAdministrator.showRegisterEmployed());
    }

    public void setBtnbuscar(){
        List<Person> viewEmployed = gestorAdministrator.ListEmployed(searchdni.getText());
        employedListobservable = FXCollections.observableArrayList(viewEmployed);
        FilteredList<Person> filteredData = new FilteredList<>(employedListobservable,personEncontrada -> true);
        searchdni.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(personaEncontrada -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filtro =  newValue.toLowerCase();
                return personaEncontrada.getName().toLowerCase().contains(filtro);
            });
        });
        configTable();
        tablesearchemployed.setItems(filteredData);
    }

    public void setbBtnLoadFichero(){
        String load = gestorAdministrator.loadFileHoursEmployed();
        txtregistros.setText(load);
    }

    public void setBtnclean(){
        searchdni.clear();
        showEmployed();
    }

    public void clearWindow(){
        tfname.clear();
        tfsecondnaem.clear();
        tfemail.clear();
        tfpassword.clear();
        tfdni.clear();
        checkadmin.setSelected(false);
    }
}
