/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socketConcurrente;

/**
 *
 * @author rafa
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;
import java.net.*;

/**
 *
 * @author rafa
 */
public class ClienteTCP{
    static String hostname = "Cliente de Rafa";
     static String webpage = "127.0.0.1";
    static int puerto = 6789;
    
    
    public static void main(String[] args) throws IOException {
        System.out.println("Arrancando "+ hostname);
        
     // voy a empezar pidiendo al usuario un numero mediante mi funcion
        int numaenviar = elegirNumero();
        
        // Crear el socket del cliente
        Socket clienteSocket = new Socket(webpage, puerto);

        // tengo el flujo de salida del socket en outtoserver
        DataOutputStream outToServer = new DataOutputStream(clienteSocket.getOutputStream());

        // cast de int a string del numero ya controlado
        String mensaje = Integer.toString(numaenviar);
        
        mensaje = mensaje +","+ hostname;
        //System.out.println(mensaje);
        
        outToServer.writeUTF(mensaje);
        
        //no me deja usar lo s metodos de enviar de las diapositivas , me salen tachados, pruebo a enviar con utf;
        
        //con utf funciona ok en teoria . 
        
        System.out.println("mensaje enviado a la ip "+webpage+ " puerto "+ puerto);
        
        
        
        
        DataInputStream inFromServer = new DataInputStream(clienteSocket.getInputStream());
        String mensajedeServidor = inFromServer.readUTF();
        String [] partesServer = mensajedeServidor.split(",");
        System.out.println("Numero del servidor : "+ partesServer[0]);
        System.out.println("Nombre del servidor : "+ partesServer[1]);
        System.out.println("Numero del cliente : "+ numaenviar);
        System.out.println("server+cliente : ");
        System.out.println(Integer.parseInt(partesServer[0])+numaenviar);
       
        
        
      
        
        
        // Cerrar el flujo de salida y el socket
        inFromServer.close();
        outToServer.close();
        clienteSocket.close();
    }

    public static int elegirNumero(){
        // variable ahora controlada en server
        //cada vez q variable no cumple , server peta 
        // por tanto comento el control y ya 
          int a=0;
        while (a==0){
        System.out.println("Introduce un entre  1 y  100 \n");
        
            Scanner s = new Scanner(System.in);
            int control;
            control= s.nextInt();
        //if(control>=1&&control<=100){
            a=control;
            
        //}
       
        }
        return a;    
    }



}

