
package com.mycompany.persitenciatransaccional;

import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ConexionCliente implements Runnable {

    public static ArrayList<ConexionCliente> conexionCliente = new ArrayList<>();
    private Socket socket;
    private BufferedReader leer;
    private BufferedWriter escribir;
    private String nombreUsuario;
    private EmpleadoDAO empleadoDAO;

    public ConexionCliente(Socket socket) {
        try {
            this.socket = socket;
            this.leer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.escribir = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nombreUsuario = leer.readLine();
            conexionCliente.add(this);
            System.out.println(nombreUsuario + " - Se ha conectado al servidor");
            mensajeOtrosUsuarios(nombreUsuario + " - Se ha conectado al servidor");

            String mensajeExplicacion = " GESTION DE EMPLEADOS, ACCIONES PERMITIDAS: insertar, actualizar, consultar, borrar";
            mensajeOtrosUsuarios(mensajeExplicacion);

        } catch (IOException e) {
            cerrarTodo(socket, leer, escribir);
        }

    }

    public void run() {
        String mensajeCliente;

        while (socket.isConnected()) {
            try {
                mensajeCliente = leer.readLine();

                mensajeCliente = mensajeCliente.replace(nombreUsuario + ": ", "");
                if (mensajeCliente == null || mensajeCliente.equalsIgnoreCase("chao")) {
                    cerrarTodo(socket, leer, escribir);
                    break;
                }

                String[] partesMensaje = mensajeCliente.split(" ");
                String operacion = partesMensaje[0];

                String json = "";

                empleadoDAO = new EmpleadoDAO();

                if (operacion.equalsIgnoreCase("insertar")) {
                    json = mensajeCliente.substring(operacion.length());
                    Empleado empleado = parsearEmpleado(json);
                    if (empleadoDAO.insertarEmpleado(empleado)) {
                        mensajeCliente = "insertando correctamente";
                    } else {
                        mensajeCliente = "error al insertar";
                    }
                } else if (operacion.equalsIgnoreCase("actualizar")) {
                    json = mensajeCliente.substring(operacion.length());
                    Empleado empleado = parsearEmpleado(json);
                    if (empleadoDAO.actualizarEmpleado(empleado)) {
                        mensajeCliente = "actualizado correctamente";
                    } else {
                        mensajeCliente = "error al actualizar";
                    }
                } else if (operacion.equalsIgnoreCase("consultar")) {
                    int empleadoID = Integer.parseInt(partesMensaje[1]);
                    Empleado empleado = empleadoDAO.consultarEmpleado(empleadoID);
                    if (empleado != null) {
                        JSONObject jsonc = new JSONObject(empleado);
                        mensajeCliente = jsonc.toString();
                    } else {
                        mensajeCliente = "el empleado no existe";
                    }

                } else if (operacion.equalsIgnoreCase("borrar")) {
                    int empleadoID = Integer.parseInt(partesMensaje[1]);
                    if (empleadoDAO.borrarEmpleado(empleadoID)) {
                        mensajeCliente = "borrado correctamente";
                    } else {
                        mensajeCliente = "error al borrar";
                    }
                } else {
                    mensajeCliente = "Comando inválido";
                }

                mensajeOtrosUsuarios(mensajeCliente);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Empleado parsearEmpleado(String empleado) {
        try {
            JSONObject jsonObject = new JSONObject(empleado);

            Empleado empleadoObjeto = new Empleado();

            empleadoObjeto.setPrimerNombre(jsonObject.getString("primerNombre"));
            empleadoObjeto.setID(jsonObject.has("ID") ? jsonObject.getInt("ID") : 0);
            empleadoObjeto.setSegundoNombre(jsonObject.getString("segundoNombre"));
            empleadoObjeto.setEmail(jsonObject.getString("email"));
            String fechaNacimientoString = jsonObject.getString("fechaNacimiento");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = dateFormat.parse(fechaNacimientoString);
            empleadoObjeto.setFechaNacimiento(fechaNacimiento);

            empleadoObjeto.setSueldo(jsonObject.getDouble("sueldo"));
            empleadoObjeto.setComision(jsonObject.getInt("comision"));
            empleadoObjeto.setCargoID(jsonObject.getInt("cargoID"));
            empleadoObjeto.setGerenteID(jsonObject.getInt("gerenteID"));
            empleadoObjeto.setDptoID(jsonObject.getInt("dptoID"));

            return empleadoObjeto;

        } catch (JSONException | ParseException err) {
            return null;
        }

    }

    private void mensajeOtrosUsuarios(String mensajeEnviar) {
        for (ConexionCliente conexionCliente : conexionCliente) {
            try {
                if (conexionCliente.nombreUsuario.equals(nombreUsuario)) {
                    conexionCliente.escribir.write(mensajeEnviar);
                    conexionCliente.escribir.newLine();
                    conexionCliente.escribir.flush();
                }
            } catch (IOException e) {
                cerrarTodo(socket, leer, escribir);
            }
        }
    }

    public void removerCliente() {
        conexionCliente.remove(this);
        System.out.println(nombreUsuario + " - Se ha desconectado");
        // mensajeOtrosUsuarios("Server: " + nombreUsuario + " - Se ha desconectado");
    }

    private void cerrarTodo(Socket socket, BufferedReader leer, BufferedWriter escribir) {
        removerCliente();
        Cliente.cerrar(socket, leer, escribir);
    }

}
