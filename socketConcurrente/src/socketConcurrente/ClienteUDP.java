/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socketConcurrente;

/**
 *
 * @author rafa
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author rafa
 */
public class ClienteUDP { 
    static String hostname = "Cliente de Rafa";
    static String webpage = "127.0.0.1";
    static int puerto = 6789;

    public static void main(String[] args) throws IOException {
        System.out.println("Arrancando Cliente de Rafa");

        // Voy a empezar pidiendo al usuario un número mediante mi función
        int numaenviar = elegirNumero();
        int numeroserver;
        // Crear el socket del cliente (UDP)
        DatagramSocket clienteSocket = new DatagramSocket();

        // Convertir el número a cadena
        String mensaje = Integer.toString(numaenviar) + "," + hostname;
        String mensajerecibido;
        // Convertir la cadena a bytes
        byte[] datos = new byte[1024];
        byte[] datosrecibidos = new byte[1024];
        datos = mensaje.getBytes();
            
        // cast a la dirección IP del servidor q es mi iploop - esto seria el dns
        
        InetAddress direccionServidor = InetAddress.getByName(webpage);
        
        DatagramPacket outToServer = new DatagramPacket(datos, datos.length, direccionServidor, puerto);

        // envio el paquete al server
        clienteSocket.send(outToServer);

        System.out.println("Mensaje enviado a la IP " + webpage + " puerto " + puerto);
        
        
        //recibo la respuesta del server
        DatagramPacket inFromServer = new DatagramPacket(datosrecibidos, datosrecibidos.length, direccionServidor, puerto);
        
        clienteSocket.receive(inFromServer);
        mensajerecibido = new String(inFromServer.getData());
        String [] partesServer = mensajerecibido.split(",");
        numeroserver = Integer.parseInt(partesServer[0]);
            
                
      
        System.out.println("Numero del servidor : "+ partesServer[0]);
        //me cogia caracteres vacios , uso trim para que salga solo servidor de rafa;
        
        //creo q solo me sale en mac 
        System.out.println("Nombre del servidor : "+ partesServer[1].trim());
        System.out.println("Numero del cliente : "+ numaenviar);
        System.out.println("server+cliente : ");
        System.out.println(Integer.parseInt(partesServer[0])+numaenviar);

        
        
        

        // cierro el socket
        //los datagrampacket no se cierran , solo tengo q cerrar el socket, no es como las datainputstream
        
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

