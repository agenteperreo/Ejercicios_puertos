package lecturaremotadeficheros;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTCP {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // 1 - Crear un socket de tipo cliente indicando IP y puerto del servidor
            System.out.println("Estableciendo conexión con el servidor");
            //Tomamos nuestra propia ip y puerto
            Socket cliente = new Socket(InetAddress.getLocalHost(), 49200);

            // 2 - Abrir flujos de lectura y escritura
            InputStream is = cliente.getInputStream();
            OutputStream os = cliente.getOutputStream();

            // 3 - Intercambiar datos con el servidor
            // Le envío mensaje al servidor
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            // Leo mensajes que me envía el servidor
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            System.out.println("El servidor te pide la ruta del archivo: ");

            String ruta=sc.next();

            bw.write(ruta);
            bw.newLine();
            bw.flush();


            System.out.println("El servidor me envia el mensajer: " + br.readLine());

            // 4 - Cerrar flujos de lectura y escritura
            bw.close();
            osw.close();
            br.close();
            isr.close();
            is.close();
            os.close();

            // 5 - Cerrar la conexión
            System.out.println("Se cierra la conexión del cliente");
            cliente.close();

        } catch(UnknownHostException e){
            System.err.println("No se encuentra el host especificado.");
            e.printStackTrace();
        } catch(IOException e){
            System.err.println("Se ha producido un error en la conexión con el servidor.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
