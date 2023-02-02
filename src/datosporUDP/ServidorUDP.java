package datosporUDP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP {

    /**
     * Funcion para leer los mensajes enviados por el cliente y guardarlo en un fichero
     */
    static void leerMensajes(DatagramSocket socket) {

        //Creamos el buffer de entrada
        System.out.println("Creación del array de bytes");
        byte[] bufferEntrada;

        //Creamos el datagrama
        System.out.println("Creación del datagrama");
        DatagramPacket packetEntrada;

        String nombre="";

        try {
            while(!nombre.equals("FIN")) {
                //Inicializamos el buffer de entrada
                bufferEntrada= new byte[32];

                //Reiniciamos el datagrama
                packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                //Recibimos el datagrama
                socket.receive(packetEntrada);

                //Leemos el mensaje y lo transformamos a String
                nombre = new String(packetEntrada.getData());

                //Escribimos el string en el fichero
                BufferedWriter bw=new BufferedWriter(new FileWriter("mensajes.txt"));

                //
                bw.append(nombre);
            }

        } catch (IOException e) {
            System.err.println("ERROR: Algo se ha interrumpido");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }




    }

    public static void main(String[] args) {
        try {
            System.out.println("Creación del socket");
            DatagramSocket socket = new DatagramSocket(41600);

            while (true) {
                leerMensajes(socket);
            }

        } catch (SocketException e) {
            System.err.println("Error en la creacion del socket.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error en la recepcion del paquete.");
            e.printStackTrace();
        }
    }
}

