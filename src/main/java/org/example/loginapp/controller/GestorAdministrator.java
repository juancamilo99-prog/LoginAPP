package org.example.loginapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.loginapp.model.Admnistrator;
import org.example.loginapp.model.Employed;
import org.example.loginapp.model.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GestorAdministrator {

    private ArrayList<Employed> employedList;
    private ArrayList<Admnistrator> admnistratorList;
    private ObservableList<Person> personList;
    private final String RUTA_USER = "src/main/resources/org/example/loginapp/registerEmployed/usuarios.txt";
    public GestorAdministrator() {
        employedList = new ArrayList<>();
        admnistratorList = new ArrayList<>();
        personList = FXCollections.observableArrayList();
        final Admnistrator admin = new Admnistrator("camilo", "montero", "234232", "montero@gmail.com", "admin123");
        System.out.println("admin email: " + admin.getEmail());
        System.out.println("admin password: " + admin.getPassword());
    }

    //devolvemos una persona
    private <T extends Person> T addPerson(T person, List<T> list_person) {
        boolean exists = list_person.stream()
                .anyMatch(person1-> person1.getDni().equals(person.getDni()));

        if (exists) {
            System.out.println("Person already exists");
            return null;
        }
        list_person.add(person);
        personList.add(person);
        loadEmployedList(list_person);
        System.out.println("Person succesfully added");
        return person;
    }

    //devolvemos un empleado
    public Employed addEmployed(Employed employed){
        return addPerson(employed, employedList);
    }
    //devolvemos un administrador
    public Admnistrator addAdmnistrator(Admnistrator admnistrator){
        return addPerson(admnistrator, admnistratorList);
    }

    //eliminar empleado buscado por su dni
    public boolean removeEmpleado(String dni){
        boolean remove = personList.removeIf(employed -> employed.getDni().equalsIgnoreCase(dni));
        if (remove){
            System.out.println("Employed successfully removed");
        }else {
            System.out.println("Employed not succesfully removed");
        }
        return remove;
    }

    //actualizar la clave del usuario
    public boolean updatePassword(String dni, String newpassword){
       Optional<Employed> user = employedList.stream()
                .filter(employed -> employed.getDni().equalsIgnoreCase(dni))
               .findFirst();
       if (user.isPresent()){
           user.get().setPassword(newpassword);
           return true;
       }
       return false;
    }

    //importar fichero de empleados fichados
    public ArrayList<Employed> DocumentImportEmployedSigning(){
        File file = new File("src/main/resources/org/example/loginapp/registerEmployed/horas.txt");
        ArrayList<Employed> employedList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null){
                String[] items = line.split("-");
                if (items.length == 6){
                    Employed employed = new Employed(items[0], items[1], items[2], items[3], items[4], items[5]);
                    employedList.add(employed);
                    System.out.println("Employed successfully added");
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("URL not found "+ e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file "+ e.getMessage());
        }
        return employedList;
    }

    //exportar fichero de empleados agregados
    private void loadEmployedList(List<? extends Person> globalUserList){
        File file = new File(RUTA_USER);
        file.getParentFile().mkdir();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))){
            for (Person person : globalUserList){
                String type = (person instanceof Admnistrator) ? "ADMIN" : "EMPLOYED";
                String linea = String.format("%s,%s,%s,%s,%s,%s", type, person.getName(),person.getLastname(),person.getDni(), person.getEmail(), person.getPassword());
                bufferedWriter.write(linea);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo "+ e.getMessage());
        }
    }

    //importar fichero horas empleados
    public String loadFileHoursEmployed(){
        File file = new File("src/main/resources/org/example/loginapp/registerEmployed/horas.txt");
        if (!file.exists()){
            return "no existen registro de jornada";
        }

        StringBuilder contenido = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String linea = "";
            while ((linea = reader.readLine()) != null){
                contenido.append(linea).append("\n");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contenido.toString();
    }

    //mostrar los empleados fichados
    public ArrayList<Employed> getEmployedSigning(){
        ArrayList<Employed> employedListsigning = DocumentImportEmployedSigning();
        for (Employed employed : employedList){
            if (employed.getHoraFichaje() != null){
                System.out.println("Empleados fichados");
                employedListsigning.add(employed);
            }
        }
        //recorremos la lista y mostramos los datos de los empleados fichados
        for (Employed employed : employedListsigning){
            employed.mostrarDatos();
        }
        return employedListsigning;
    }

    //listar empleado buscado por su dni
    public ObservableList<Person> ListEmployed(String dni){
       return personList.stream()
               .filter(person -> person.getDni().equalsIgnoreCase(dni))
               .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    //listar todos los empleados
    public List<Person> showRegisterEmployed(){
        return personList;
    }
}
