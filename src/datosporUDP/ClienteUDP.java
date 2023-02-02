package datosporUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClienteUDP {

    static void envioMensajes(DatagramSocket socket, InetAddress ip) {
        //Creamos la variable que vamos a enviar
        String mensaje;

        //Creamos el array de bytes a enviar
        byte[] bufferSalida;

        //Creamos el datagrama
        DatagramPacket packet;
        try {
        //Enviamos el mensaje 10000 veces
        for (int i=0;  i<10000; i++) {

            //Generamos el mensaje
            mensaje = "Mensaje: " + i;
            bufferSalida = mensaje.getBytes();
            packet = new DatagramPacket(bufferSalida, bufferSalida.length, ip, 41600);

            //Enviamos el paquete
            System.out.println("Enviamos el mensaje: "+mensaje);

            //Enviamos el mensaje
            socket.send(packet);
        }

            //Damos valor al ultimo mensaje
            mensaje="FIN";
            bufferSalida=mensaje.getBytes();

            packet = new DatagramPacket(bufferSalida, bufferSalida.length, ip, 41600);

            //Enviamos el paquete
            System.out.println("Enviamos el paquete");
            socket.send(packet);
        //Control de excepciones
        } catch (IOException e) {
        System.err.println("ERROR: Error en el envio del paquete");
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        try {
            //Obtener la IP local
            InetAddress ip = InetAddress.getLocalHost();

            //Creamos el socket
            System.out.println("Creación del socket.");
            DatagramSocket socket=new DatagramSocket();

            //Llamamos a la funcion
            envioMensajes(socket, ip);

            //Cerramos el socket
            socket.close();
        } catch (SocketException e) {
            System.err.println("Error en la creación del socket.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error en el envio del paquete.");
            e.printStackTrace();
        }
    }
}
