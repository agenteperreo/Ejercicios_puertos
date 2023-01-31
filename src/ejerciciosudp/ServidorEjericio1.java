package ejerciciosudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServidorEjericio1 {

    public static void main(String[] args) {
        try {
            System.out.println("Creación del socket");
            DatagramSocket socket =new DatagramSocket(41600);

            while(true) {
                System.out.println("Cración del array de bytes");
                byte[] bufferEntrada = new byte[64];

                System.out.println("Creación del datagrama");
                DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                System.out.println("A la espera de recibir datagrama");
                socket.receive(packetEntrada);

                System.out.println("Leemos el mensaje");
                String nombre = new String(packetEntrada.getData());

                //Generamos el mensaje
                String mensaje="Hola "+nombre;

                byte[] bufferSalida= mensaje.getBytes();
                DatagramPacket packetSalida=new DatagramPacket(bufferSalida, bufferSalida.length, packetEntrada.getAddress(), packetEntrada.getPort());

                System.out.println("Enviamos el paquete");
                socket.send(packetSalida);
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
