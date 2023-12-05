/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socketConcurrente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafa
 */
public class ServidorTCPconcurrente {
    
    static String hostname = "Servidor de Rafa";
    static int puerto = 6789;
    
    public static void main(String[] args) throws IOException  {
        int numeroserver = elegirNumero();
       
        System.out.println("Arrancando "+ hostname+"\n");
        ServerSocket socketServidor = new ServerSocket(puerto);
        System.out.println("Servidor TCP arrancado , escucha en puerto"+ puerto +"\n");
                

        while(true){
            System.out.println("Esperando conexion\n");
            System.out.println("----------------------");
           
            // Esperar a que llegue una conexión de un cliente
            Socket connectionSocket = socketServidor.accept();
            System.out.println("Conexion entrante");
            
            Thread servidorConcurrente = new Thread(new hiloConcurrencia(connectionSocket, numeroserver,hostname) );
            servidorConcurrente.start();

            
            
            
        }
        
     
            //cerrar socket tcp
            
            //socketServidor.close();
        
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
    
        Socket socket;
        int numeroserver;
        String hostname;

        
          public hiloConcurrencia(Socket socket, int numeroserver,String hostname){
              this.numeroserver=numeroserver;
              this.socket=socket;
              this.hostname=hostname;
              
          }
        @Override
        public void run() {
            
            
            
            DataInputStream inFromClient = null;
            try {
                // Obtener el flujo de entrada del socket
                inFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                // Leer el número enviado por el cliente
                String mensaje = inFromClient.readUTF();
                String [] partes = mensaje.split(",");
                //parte 0 numero -- parte 1 hostname
                // cliente de rafa
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
                //
                
                if(Integer.parseInt(partes[0])<1||Integer.parseInt(partes[0])>100){
                    //si numero diferente de parametros
                    System.out.println("----------------------");
                    System.out.println("Numero no cumple los parametros de control");
                    System.out.println("--Server crash --");
                    //cierro todo
                    outToClient.close();
                    inFromClient.close();
                    socket.close();
                    
                    // no consigo hacer q mi servidor cierre el socket e impida futuraws conexiones, aunq no se si seria lo optimo,
                    //segun esta actualmente implementado no crashea de forma global , sino que esa conexion se acaba y permite a otra conexiones seguir utilizando el socket.
                    
                    //quizas esta manera es la optima.
                    
                    //opciones  -- join() no tiene mucho sentido ya que la comunicacion es un proceso asincrono.
                    //       gpt variable global   -- chatgpt me propone utilizar una variable de control superior la cual modificar en caso de q el cliente de un parametro fuera de los valores de control
                    // creo que esto no sería viable puesto mi socket crashearia y no se como afectaria a los clientes, ya que al poder haber varios clientes corriendo produciría efectos que no contemplo.
                    
                    
                    return;
                    
                    
                    
                }   String mensajeServer = Integer.toString(numeroserver);
                mensajeServer = mensajeServer +","+ hostname;
                outToClient.writeUTF(mensajeServer);
                System.out.println("Conexion cerrada con "+ partes[1]);
                // Cerrar todo menos el socket tcp
                outToClient.close();
                inFromClient.close();
                socket.close();

            } catch (IOException ex) {
                Logger.getLogger(ServidorTCPconcurrente.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    inFromClient.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServidorTCPconcurrente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
    
}
}

