
package com.mycompany.persitenciatransaccional;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class Cliente {

    private Socket socket;
    private BufferedReader leer;
    private BufferedWriter escribir;
    private String nombreUsuario;

    public Cliente(Socket socket, String nombreUsuario) {
        try {
            this.socket = socket;
            this.leer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.escribir = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nombreUsuario = nombreUsuario;
        } catch (IOException e) {
            cerrarTodo(socket, leer, escribir);
        }
    }

    static void cerrar(Socket socket, BufferedReader leer, BufferedWriter escribir) {
        try {
            if (leer != null) {
                leer.close();
            }
            if (escribir != null) {
                escribir.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            Scanner escaner = new Scanner(System.in);
            System.out.println("Usuario");
            String nombreUsuario = escaner.nextLine();
            System.out.println("IP de usuario");
            String ipUsuario = escaner.nextLine();
            System.out.println("Puerto");
            int puerto = escaner.nextInt();
            Socket socket = new Socket(ipUsuario, puerto);
            Cliente cliente = new Cliente(socket, nombreUsuario);
            System.out.println("Te haz unido al chat");
            cliente.canalEnviarYRecibir();
            cliente.enviarMensaje();

        } catch (IOException e) {
            System.out.println("Conexion no pudo establecerse..... vuelve a iniciar el programa");
        }
    }

    public void enviarMensaje() {
        try {
            escribir.write(nombreUsuario);
            escribir.newLine();
            escribir.flush();

            Scanner escaner = new Scanner(System.in);
            while (socket.isConnected()) {
                String mensajeEnviar = escaner.nextLine();
                escribir.write(nombreUsuario + ": " + mensajeEnviar);
                escribir.newLine();
                escribir.flush();
            }
        } catch (IOException e) {
            cerrarTodo(socket, leer, escribir);
        }
    }

    public void canalEnviarYRecibir() {

        new Thread(new Runnable() {
            @Override

            public void run() {

                String mensajeChat;
                while (socket.isConnected()) {
                    try {
                        mensajeChat = leer.readLine();
                        if (mensajeChat == null || mensajeChat.equalsIgnoreCase(nombreUsuario + " chao")) {
                            cerrarTodo(socket, leer, escribir);
                            System.out.println("Has abandonado el chat");
                            exit(0);
                        }
                        System.out.println(mensajeChat);

                    } catch (IOException e) {
                        cerrarTodo(socket, leer, escribir);
                    }
                }

            }
        }).start();

    }

    private void cerrarTodo(Socket socket, BufferedReader leer, BufferedWriter escribir) {
        cerrar(socket, leer, escribir);
    }
}
