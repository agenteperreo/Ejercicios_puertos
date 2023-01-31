package ejercicio3;

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

        //Declaración de variables
        int num;
        //Abrimos el escaner
        Scanner sc = new Scanner(System.in);

        do {
            try {
                // 1 - Crear un socket de tipo cliente indicando IP y puerto del servidor
                System.out.println("Estableciendo conexión con el servidor");
                //Tomamos nuestra propia ip y puerto
                Socket cliente = new Socket(InetAddress.getLocalHost(), 2200);

                // 2 - Abrir flujos de lectura y escritura
                InputStream is = cliente.getInputStream();
                OutputStream os = cliente.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                System.out.println("El servidor te pide un numero positivo: ");
                num = sc.nextInt();
                while (!esPositivo(num)) {
                    System.out.println("El numero no es positivo");
                    num = sc.nextInt();
                }
                bw.write(num);
                bw.newLine();
                bw.flush();

                System.out.println("El servidor me envia el mensaje: " + br.read());

                // 4 - Cerrar flujos de lectura y escritura
                bw.close();
                osw.close();
                br.close();
                isr.close();
                is.close();
                os.close();


            } catch (UnknownHostException e) {

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }while(true);
    }
}
