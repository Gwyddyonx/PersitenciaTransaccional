package com.mycompany.persitenciatransaccional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJdbcExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@(description=(retry_count=1)(retry_delay=1)(address=(protocol=tcps)(port=1522)(host=adb.sa-santiago-1.oraclecloud.com))(connect_data=(service_name=g5ba5f1a88b3f4d_poli_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
        String username = "POLI";
        String password = "xSK3t5j9Q7NEAMn";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT SYSDATE FROM DUAL");
            if (resultSet.next()) {
                java.sql.Date sysdate = resultSet.getDate(1);
                System.out.println("SYSDATE: " + sysdate);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
