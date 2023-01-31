package ejerciciosudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class ServidorEjercicio2 {

    static String mensajes[]=new String[2];
    public static void main(String[] args) {
        try {
            System.out.println("Creación del socket");
            DatagramSocket socket =new DatagramSocket(41600);

            while(true) {

                //Creamos el buffer de entrada
                System.out.println("Cración del array de bytes");
                byte[] bufferEntrada = new byte[64];

                //Creamos el datagrama
                System.out.println("Creación del datagrama");
                DatagramPacket packetEntrada1 = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                DatagramPacket packetEntrada2 = new DatagramPacket(bufferEntrada, bufferEntrada.length);

                /**
                 * Primera cadena
                 */
                //Esperamos el mensajer del cliente
                System.out.println("A la espera de recibir datagrama");
                socket.receive(packetEntrada1);

                //Lo pasamos a String para no coger la direccion de memoria
                String mensaje1Entrada = new String(packetEntrada1.getData());

                //Guardamos el mensaje en el array
                mensajes[0]=mensaje1Entrada;
                /**
                 * Segunda cadena
                 */
                //Vaciamos el buffer de entrada
                bufferEntrada=new byte[64];

                //Esperamos el mensajer del cliente
                System.out.println("A la espera de recibir datagrama");
                socket.receive(packetEntrada2);

                //Lo pasamos a String para no coger la direccion de memoria
                String mensaje2Entrada = new String(packetEntrada2.getData());

                //Guardamos el mensaje en el array
                mensajes[1]=mensaje2Entrada;

                //Ordenamos el array
                Arrays.sort(mensajes);


                byte[] bufferSalida= mensaje1Entrada.getBytes();
                DatagramPacket packetSalida1=new DatagramPacket(bufferSalida, bufferSalida.length, packetEntrada1.getAddress(), packetEntrada1.getPort());

                //enviamos el paquete
                System.out.println("Enviamos el paquete");
                socket.send(packetSalida1);

                bufferSalida=new byte[32];
                bufferSalida=mensaje2Entrada.getBytes();
                DatagramPacket packetSalida2=new DatagramPacket(bufferSalida, bufferSalida.length, packetEntrada2.getAddress(), packetEntrada2.getPort());

                System.out.println("Enviamos el paquete");
                socket.send(packetSalida2);

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
