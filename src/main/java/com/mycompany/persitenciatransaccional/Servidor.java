
package com.mycompany.persitenciatransaccional;

import java.io.IOException;
import java.net.*;

public class Servidor {

    private ServerSocket servidorSocket;

    public Servidor(ServerSocket servidorSocket) {
        this.servidorSocket = servidorSocket;
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Esperando conexion de cliente");
        ServerSocket servidorSocket = new ServerSocket(5000);
        Servidor servidor = new Servidor(servidorSocket);
        servidor.iniciarServidor();
    }

    public void iniciarServidor() {

        try {
            while (!servidorSocket.isClosed()) {
                Socket socket = servidorSocket.accept();
                ConexionCliente conexionCliente = new ConexionCliente(socket);
                Thread thread = new Thread(conexionCliente);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("El cliente finalizo la conexión");
        }
    }
}
