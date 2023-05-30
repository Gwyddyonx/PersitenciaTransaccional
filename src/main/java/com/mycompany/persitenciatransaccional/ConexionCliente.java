
package com.mycompany.persitenciatransaccional;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ConexionCliente implements Runnable{
    
    public static ArrayList<ConexionCliente> conexionCliente = new ArrayList<>();
    private Socket socket;    
    private BufferedReader leer;    
    private BufferedWriter escribir;    
    private String nombreUsuario;
    
    public ConexionCliente(Socket socket){
        try {
            this.socket = socket;
            this.leer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.escribir = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nombreUsuario = leer.readLine();
            conexionCliente.add(this);
            System.out.println(nombreUsuario + "...ha ingresado al chat");
            mensajeOtrosUsuarios("Server: " + nombreUsuario+ "...a ingresado al chat");
            
        } catch (IOException e) {
            cerrarTodo(socket, leer, escribir);
        }
        
    }
    
    public void run (){
        String mensajeCliente;
        
        while (socket.isConnected()){
            try {                
                mensajeCliente = leer.readLine();
                if(mensajeCliente == null || mensajeCliente.equalsIgnoreCase(nombreUsuario+": chao"))
                    {
                        cerrarTodo(socket, leer, escribir);
                        break;
                    }
                mensajeOtrosUsuarios(mensajeCliente);
            } catch (Exception e) {
            }
        }
    }
    
    private void mensajeOtrosUsuarios(String mensajeEnviar){
        for (ConexionCliente conexionCliente: conexionCliente){
            try {
                if(!conexionCliente.nombreUsuario.equals(nombreUsuario)){
                    conexionCliente.escribir.write(mensajeEnviar);
                    conexionCliente.escribir.newLine();
                    conexionCliente.escribir.flush();
                }
            } catch (IOException e) {
                cerrarTodo(socket, leer, escribir);
            }
        }
    }
    
    public void removerCliente(){
        conexionCliente.remove(this);
        System.out.println(nombreUsuario + "...ha salido del chat");
        mensajeOtrosUsuarios("Server: " + nombreUsuario+ "...ha salido del chat");
    }
    
    private void cerrarTodo(Socket socket, BufferedReader leer, BufferedWriter escribir){
        removerCliente();
        Cliente.cerrar(socket, leer, escribir);
    }
      
    
}
