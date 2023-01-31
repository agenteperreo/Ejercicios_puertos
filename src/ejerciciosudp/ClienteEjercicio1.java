package ejerciciosudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClienteEjercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombre;
        try {
            //Obtener la IP local
            InetAddress ip = InetAddress.getLocalHost();

            //
            System.out.println("Creación del socket.");
            DatagramSocket socket=new DatagramSocket();

            //Preguntamos el nombre al usuario
            System.out.print("¿Cual es tu nombre?: ");
            nombre=sc.nextLine();

            //Generamos el mensaje
            byte[] bufferSalida= nombre.getBytes();
            DatagramPacket packet=new DatagramPacket(bufferSalida, bufferSalida.length, ip, 41600);

            System.out.println("Enviamos el paquete");
            socket.send(packet);

            byte[] bufferEntrada = new byte[64];

            System.out.println("Creación del datagrama");
            DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);

            System.out.println("A la espera de recibir datagrama");
            socket.receive(packetEntrada);

            //Leemos el mensaje
            String mensaje=new String(packetEntrada.getData());

            //Mostramos el mensaje
            System.out.println(mensaje.trim());

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
