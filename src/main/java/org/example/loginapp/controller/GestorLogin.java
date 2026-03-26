package org.example.loginapp.controller;

import javafx.stage.Stage;
import org.example.loginapp.model.Admnistrator;
import org.example.loginapp.model.Employed;
import org.example.loginapp.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestorLogin {

    private List<Person> userList;

    public GestorLogin() {
        userList = new ArrayList<>();
        loadDefaultData();
    }

    public Person logIn(String email, String password) throws IOException {
        Person person_user = buscarUsuario(email,password);

        if (person_user == null) {
            System.out.println("Usuario o contraseña incorrectos");
            return person_user;
        }
        return person_user;
    }

    public Person buscarUsuario(String email, String password) {
        for (Person person : userList) {
            if (email.equalsIgnoreCase(person.getEmail()) && password.equals(person.getPassword())) {
                return person;
            }
        }
        return null;
    }

    public void loadDefaultData(){
        userList.add(new Admnistrator("camilo", "montero", "234232", "montero@gmail.com", "admin123"));
        userList.add(new Employed("carlos", "martin", "1234", "carloslopez@gmail.com", "4321"));
    }
}
