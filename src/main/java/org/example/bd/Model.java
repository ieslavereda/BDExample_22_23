package org.example.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    public void conectar(){
        try(Connection con = MyDataSource.getOracleDataSource().getConnection()){
            System.out.println("OK");
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        }
    }

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

    public Empleado crearEmpleado(Empleado e){
        String sql = "INSERT INTO EMPLEADO (DNI,nombre,apellidos) VALUES (?,?,?)";
        try(Connection c = MyDataSource.getMySQLDataSource().getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            int pos = 0;

            pstmt.setString(++pos,e.getDNI());
            pstmt.setString(++pos,e.getNombre());
            pstmt.setString(++pos,e.getApellidos());

            if(pstmt.executeUpdate()==0)
                throw new SQLException("No se ha podido crear el empleado");

            try(ResultSet rs = pstmt.getGeneratedKeys()){

                if(rs.next())
                    e.setIdEmpleado(rs.getInt(1));
                else
                    throw new SQLException("No se ha podido obtener el id asignado");


//                List<Empleado> listado = new ArrayList<>();
//                Empleado empleado = new Empleado(1,"","","");
//                listado.get(listado.indexOf(empleado));
//                Empleado e2 = listado.stream()
//                        .filter(e3->e3.getIdEmpleado()==1)
//                        .findFirst()
//                        .get();
                return e;
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public boolean guardarEmpleado(Empleado e){
        String sql = "INSERT INTO EMPLEADO SET " +
                "DNI='"+e.getDNI()+"', "+
                "nombre='"+e.getNombre()+"',  "+
                "apellidos='"+e.getApellidos()+"'";

        return executeStatement(sql)==1;
    }

    public int deleteEmpleado(String dni){

        String sql = "DELETE FROM EMPLEADO WHERE DNI LIKE ?";

        try( Connection c = MyDataSource.getMySQLDataSource().getConnection();
             PreparedStatement pstmt = c.prepareStatement(sql)
        ) {

            int pos = 0;

            pstmt.setString(++pos,dni);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int updateEmpleado(Empleado e) {
        String sql = "UPDATE EMPLEADO SET DNI='" + e.getDNI() + "',nombre='" + e.getNombre() + "',apellidos='" + e.getApellidos() + "' WHERE idEmpleado=" + e.getIdEmpleado();
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
