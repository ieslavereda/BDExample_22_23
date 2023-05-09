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

        m.conectar();

    }

}