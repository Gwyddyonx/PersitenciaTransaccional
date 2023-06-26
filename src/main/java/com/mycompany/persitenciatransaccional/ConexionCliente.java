
package com.mycompany.persitenciatransaccional;

import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

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
            System.out.println(nombreUsuario + "...ha ingresado al chat");
            mensajeOtrosUsuarios("Server: " + nombreUsuario + "...a ingresado al chat");

        } catch (IOException e) {
            cerrarTodo(socket, leer, escribir);
        }

    }

    public void run() {
        String mensajeCliente;

        while (socket.isConnected()) {
            try {
                mensajeCliente = leer.readLine();

                mensajeCliente = mensajeCliente.replace(nombreUsuario+": ","");
                if (mensajeCliente == null || mensajeCliente.equalsIgnoreCase("chao")) {
                    cerrarTodo(socket, leer, escribir);
                    break;
                }

                String[] partesMensaje = mensajeCliente.split(" ");
                String operacion = partesMensaje[0];

                empleadoDAO = new EmpleadoDAO();
                
                if (operacion.equalsIgnoreCase("insertar")) {
                    Empleado empleado = parsearEmpleado(partesMensaje);
                    empleadoDAO.insertarEmpleado(empleado);
                } else if (operacion.equalsIgnoreCase("actualizar")) {
                    Empleado empleado = parsearEmpleado(partesMensaje);
                    empleadoDAO.actualizarEmpleado(empleado);
                } else if (operacion.equalsIgnoreCase("consultar")) {
                    int empleadoID = Integer.parseInt(partesMensaje[1]);
                    Empleado empleado = empleadoDAO.consultarEmpleado(empleadoID);
                    mensajeCliente = empleado.toString();
                } else if (operacion.equalsIgnoreCase("borrar")) {
                    int empleadoID = Integer.parseInt(partesMensaje[1]);
                    empleadoDAO.borrarEmpleado(empleadoID);
                    mensajeOtrosUsuarios(mensajeCliente);
                }

                mensajeOtrosUsuarios(mensajeCliente);

                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Empleado parsearEmpleado(String[] partesMensaje) {
        int ID = Integer.parseInt(partesMensaje[0]);
        String primerNombre = partesMensaje[1];
        String segundoNombre = partesMensaje[2];
        String email = partesMensaje[3];
        
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Date fechaNacimiento;
        try {
            fechaNacimiento = dateFormat.parse(partesMensaje[4]);
        } catch (ParseException e) {
            fechaNacimiento = null;
            e.printStackTrace();
        }
        
        double sueldo = Double.parseDouble(partesMensaje[5]);
        int comision = Integer.parseInt(partesMensaje[6]);
        int cargoID = Integer.parseInt(partesMensaje[7]);
        int gerenteID = Integer.parseInt(partesMensaje[8]);
        int dptoID = Integer.parseInt(partesMensaje[9]);
        
        return new Empleado(ID, primerNombre, segundoNombre, email, fechaNacimiento, sueldo, comision, cargoID, gerenteID, dptoID);
    }

    private void mensajeOtrosUsuarios(String mensajeEnviar) {
        for (ConexionCliente conexionCliente : conexionCliente) {
            try {
                //if (!conexionCliente.nombreUsuario.equals(nombreUsuario)) {
                    conexionCliente.escribir.write(mensajeEnviar);
                    conexionCliente.escribir.newLine();
                    conexionCliente.escribir.flush();
                //}
            } catch (IOException e) {
                cerrarTodo(socket, leer, escribir);
            }
        }
    }

    public void removerCliente() {
        conexionCliente.remove(this);
        System.out.println(nombreUsuario + "...ha salido del chat");
        mensajeOtrosUsuarios("Server: " + nombreUsuario + "...ha salido del chat");
    }

    private void cerrarTodo(Socket socket, BufferedReader leer, BufferedWriter escribir) {
        removerCliente();
        Cliente.cerrar(socket, leer, escribir);
    }

    private void horaActual() {

    }

}
