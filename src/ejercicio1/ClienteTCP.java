package ejercicio1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTCP {

    static boolean esPositivo(int num) {
        boolean esPositivo=true;

        if(num < 0) {
            esPositivo=false;
        }

        return esPositivo;
    }

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

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
            System.out.println("El servidor te pide un numero positivo: ");
            int num=sc.nextInt();
            while(!esPositivo(num)) {
                System.out.println("El numero no es positivo");
                num= sc.nextInt();
            }
            bw.write(num);
            bw.newLine();
            bw.flush();

            // Leo mensajes que me envía el servidor
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println("El servidor me envia el mensajer: "+br.readLine());

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

        } catch (UnknownHostException e) {
            System.err.println("No se encuentra el host especificado.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error en la conexión con el servidor.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}