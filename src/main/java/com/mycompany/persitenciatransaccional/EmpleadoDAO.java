package com.mycompany.persitenciatransaccional;

import java.sql.*;

public class EmpleadoDAO {
    private static final String JDBC_URL = "jdbc:oracle:thin:@(description=(retry_count=1)(retry_delay=1)(address=(protocol=tcps)(port=1522)(host=adb.sa-santiago-1.oraclecloud.com))(connect_data=(service_name=g5ba5f1a88b3f4d_poli_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
    private static final String USERNAME = "POLI";
    private static final String PASSWORD = "xSK3t5j9Q7NEAMn";

    public void insertarEmpleado(Empleado empleado) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO empleados (empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, empl_sueldo, empl_comision, empl_cargo_ID, empl_gerente_ID, empl_dpto_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, empleado.getPrimerNombre());
            statement.setString(2, empleado.getSegundoNombre());
            statement.setString(3, empleado.getEmail());
            statement.setDate(4, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            statement.setDouble(5, empleado.getSueldo());
            statement.setInt(6, empleado.getComision());
            statement.setInt(7, empleado.getCargoID());
            statement.setInt(8, empleado.getGerenteID());
            statement.setInt(9, empleado.getDptoID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEmpleado(Empleado empleado) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "UPDATE empleados SET empl_primer_nombre = ?, empl_segundo_nombre = ?, empl_email = ?, empl_fecha_nac = ?, empl_sueldo = ?, empl_comision = ?, empl_cargo_ID = ?, empl_gerente_ID = ?, empl_dpto_ID = ? " +
                    "WHERE empl_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, empleado.getPrimerNombre());
            statement.setString(2, empleado.getSegundoNombre());
            statement.setString(3, empleado.getEmail());
            statement.setDate(4, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            statement.setDouble(5, empleado.getSueldo());
            statement.setInt(6, empleado.getComision());
            statement.setInt(7, empleado.getCargoID());
            statement.setInt(8, empleado.getGerenteID());
            statement.setInt(9, empleado.getDptoID());
            statement.setInt(10, empleado.getID());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Empleado consultarEmpleado(int empleadoID) {
        Empleado empleado = null;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM empleados WHERE empl_ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, empleadoID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                empleado = new Empleado();
                empleado.setID(resultSet.getInt("empl_ID"));
                empleado.setPrimerNombre(resultSet.getString("empl_primer_nombre"));
                empleado.setSegundoNombre(resultSet.getString("empl_segundo_nombre"));
                empleado.setEmail(resultSet.getString("empl_email"));
                empleado.setFechaNacimiento(resultSet.getDate("empl_fecha_nac"));
                empleado.setSueldo(resultSet.getDouble("empl_sueldo"));
                empleado.setComision(resultSet.getInt("empl_comision"));
                empleado.setCargoID(resultSet.getInt("empl_cargo_ID"));
                empleado.setGerenteID(resultSet.getInt("empl_gerente_ID"));
                empleado.setDptoID(resultSet.getInt("empl_dpto_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }

    public void borrarEmpleado(int empleadoID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String borrarQuery = "DELETE FROM empleados WHERE empl_ID = ?";
            PreparedStatement borrarStatement = connection.prepareStatement(borrarQuery);
            borrarStatement.setInt(1, empleadoID);
            borrarStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
