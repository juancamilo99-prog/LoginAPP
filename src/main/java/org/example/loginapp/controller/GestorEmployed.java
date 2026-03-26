package org.example.loginapp.controller;

import org.example.loginapp.model.Employed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorEmployed {

    public void registerSigning(Employed employed) {
        String registro = employed.getEmail() + "-" + employed.fichar();

        File file = new File("src/main/resources/org/example/loginapp/registerEmployed/horas.txt");
        //si no existe o no hay nada
        if (file.getParentFile() != null){
            //lo crea
            file.getParentFile().mkdir();
        }
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                //si no existe o esta vacio creamos el archivo.
                if (file.length() == 0) {
                    bufferedWriter.write("Email - Date and Hour"); // -> creamos una linea con el correo, y horas
                    bufferedWriter.newLine();
                }
                //registro del trabajador
                String hora = employed.fichar();
                employed.setHoraFichaje(hora);
                bufferedWriter.write(registro);
                bufferedWriter.newLine();
            System.out.println("Fichaje registro correctamente");
        } catch (IOException e) {
            System.out.println("Error al registrar el fichero" + e.getMessage());
        }
    }
}
