package org.example.bd;

import com.mysql.cj.jdbc.MysqlDataSource;
import oracle.jdbc.datasource.impl.OracleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class MyDataSource {

    public static DataSource getMySQLDataSource() {

        // Propiedades donde tenemos los datos de acceso a la BD
        Properties props = new Properties();

        // Objeto DataSource que devolveremos
        MysqlDataSource mysqlDS = null;

        try (FileInputStream fis = new FileInputStream("db.properties");) {

            // Cargamos las propiedades
            props.load(fis);


            // Generamos el DataSource con los datos URL, user y passwd necesarios
            mysqlDS = new MysqlDataSource();

            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));

            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mysqlDS;

    }

    public static DataSource getOracleDataSource() {

        // Propiedades donde tenemos los datos de acceso a la BD
        Properties props = new Properties();

        // Objeto DataSource que devolveremos
        OracleDataSource oracleDS = null;

        try(FileInputStream fis = new FileInputStream("db.properties");) {
            // Cargamos las propiedades
            props.load(fis);
            // Generamos el DataSource con los datos URL, user y passwd necesarios
            oracleDS = new OracleDataSource();
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oracleDS;
    }
}
