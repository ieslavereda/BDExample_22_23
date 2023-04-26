package org.example.bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Model {

    public List<Empleado> getEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        try (
                Connection connection = MyDataSource.getMySQLDataSource().getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO")
        ) {
            int id;
            String DNI, nombre, apellidos;

            while (rs.next()) {
                id = rs.getInt("idEmpleado");
                nombre = rs.getString("nombre");
                apellidos = rs.getString("apellidos");
                DNI = rs.getString("DNI");

                empleados.add(new Empleado(id, DNI, nombre, apellidos));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return empleados;
    }

    public Empleado getEmpleado(int id) {

        Empleado empleado = null;

        try (
                Connection connection = MyDataSource.getMySQLDataSource().getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO WHERE idEmpleado=" + id)
        ) {

            String DNI, nombre, apellidos;

            if (rs.next()) {

                nombre = rs.getString("nombre");
                apellidos = rs.getString("apellidos");
                DNI = rs.getString("DNI");

                empleado = new Empleado(id, DNI, nombre, apellidos);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empleado;
    }

    public int updateEmpleado(Empleado e) {
        String sql = "UPDATE EMPLEADO SET DNI='" + e.getDNI() + "',nombre='" + e.getNombre() + "',apellidos='" + e.getApellidos() + "' WHERE idEmpleado=" + e.getIdEmpleado();
        return executeStatement(sql);
    }

    public int deleteEmpleado(String dni) {
        String sql = "DELETE FROM EMPLEADO WHERE DNI LIKE '" + dni + "'";
        return executeStatement(sql);
    }

    private int executeStatement(String sql) {
        try (
                Connection connection = MyDataSource.getMySQLDataSource().getConnection();
                Statement stmnt = connection.createStatement();
        ) {

            return stmnt.executeUpdate(sql);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
