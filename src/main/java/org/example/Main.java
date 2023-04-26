package org.example;

import org.example.bd.Empleado;
import org.example.bd.Model;
import org.example.bd.MyDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Model m = new Model();

//        System.out.println(m.getEmpleados());
//        System.out.println(m.getEmpleado(3));

        Empleado empleado3 = m.getEmpleado(3);
        empleado3.setNombre("Nombre actualizado");

        System.out.println(m.deleteEmpleado("A' OR '1' LIKE '1"));

    }

}