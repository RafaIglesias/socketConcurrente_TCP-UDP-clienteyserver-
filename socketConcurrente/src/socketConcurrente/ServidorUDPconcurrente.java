/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socketConcurrente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafa
 */
public class ServidorUDPconcurrente {
 

    

    static String hostname = "Servidor de Rafa";
    static int puerto = 6789;

    public static void main(String[] args) throws IOException {
        
        int numeroserver = elegirNumero();
        
        System.out.println("Arrancando " + hostname + "\n");
        DatagramSocket socketServidor = new DatagramSocket(puerto);
        System.out.println("Servidor UDP arrancado, escucha en puerto " + puerto + "\n");
        System.out.println("----------------------");
        System.out.println("Esperando datagrama...\n");

        while (true) {
            
            
            byte[] datosrecibidos = new byte[1024];
            
                 
                 DatagramPacket paquete = new DatagramPacket(datosrecibidos, datosrecibidos.length);
                 socketServidor.receive(paquete);
                 
                 //CREAR 1 HILO POR CADA DATAGRAMA , SI 0 DATAGRAMA 0 HILO
                 
                 Thread servidorConcurrente = new Thread(new hiloConcurrencia(socketServidor, numeroserver,hostname,paquete) );
                 servidorConcurrente.start();
                 
                 
            
            


            
            
        }
        
         // cierro el socket
        //los datagrampacket no se cierran , solo tengo q cerrar el socket, no es como las datainputstream
        
        
    }
    public static int elegirNumero(){
        
          int a=0;
        while (a==0){
        System.out.println("Introduce un entre  1 y  100 \n");
        
            Scanner s = new Scanner(System.in);
            int control;
            control= s.nextInt();
        if(control>=1&&control<=100){
            a=control;
            
        }
       
        }
        return a;    
    }
    
    static class hiloConcurrencia implements Runnable{
         DatagramSocket socket;
        int numeroserver;
        String hostname;
        DatagramPacket paquete;

        
          public hiloConcurrencia(DatagramSocket socket, int numeroserver,String hostname,DatagramPacket paquete){
              this.numeroserver=numeroserver;
              this.socket=socket;
              this.hostname=hostname;
              this.paquete = paquete;
              
          }

        @Override
        public void run() {
            
            
             try {
                 //para ver mejor las conexiones aqui voy a poner la linea antes , de forma que me divida las conexiones mejor
            
                 
                 byte[] datos = new byte[1024];
                 
                 
                 System.out.println("HILO NUEVO ");
               
                 
                 // Obtener los datos del paquete
                 String mensaje = new String(paquete.getData(), 0, paquete.getLength());
                 String[] partes = mensaje.split(",");
                 
                 // Parte 0: número, Parte 1: hostname
                 System.out.println("----------------------");
                 // Cliente de Rafa
                 System.out.println(partes[1]);
                 // numero recibido
                 
                 System.out.println("Numero del cliente ");
                 System.out.println(partes[0]);
                 // numeroserver
                 System.out.println("Numero del servidor ");
                 System.out.println(numeroserver);
                 //suma
                 System.out.println("Suma server+cliente");
                 System.out.println(numeroserver+Integer.parseInt(partes[0]));
                 
                 System.out.println("Datagrama recibido de " + partes[1]);
                 
                 
                 if(Integer.parseInt(partes[0])<1||Integer.parseInt(partes[0])>100){
                     //si numero diferente de parametros
                     System.out.println("----------------------");
                     System.out.println("Numero no cumple los parametros de control");
                     System.out.println("--Server crash --");
                     
                     socket.close();
                     return;
                     
                 }
                 //guardo la ip y el puerto con referencia de lo recibido para hacer la respuesta .
                 InetAddress direccionCliente = paquete.getAddress();
                 int puertoCliente = paquete.getPort();
                 
                 String mensajeServer = Integer.toString(numeroserver);
                 
                 mensajeServer = mensajeServer +","+ hostname;
                 datos = mensajeServer.getBytes();
                 DatagramPacket outToClient = new DatagramPacket(datos, datos.length, direccionCliente, puertoCliente);
                 
                 socket.send(outToClient);
                
                 
             } catch (IOException ex) {
                 Logger.getLogger(ServidorUDPconcurrente.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        
        
    }
    
   
}
