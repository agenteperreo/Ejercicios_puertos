package ejercicio1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    static boolean esPrimo(int num) {
        //Creamos un variables que nos dira si el numero es primo o no
        boolean esPrimo = true;

        if (num==0 || num==1) {
            esPrimo=false;
        } else {
            for (int i=2; i<=num/2; i++) {
                if(num % i == 0) {
                    esPrimo=false;
                }
            }
        }
        return esPrimo;
    }

    public static void main(String[] args) {

        boolean positivo=false;

        try {
            // 1 - Crear socket de tipo servidor y le indicamos el puerto
            ServerSocket servidor = new ServerSocket(49200);

            // 2 - Queda a la espera de peticiones y las acepta cuando las recibe
            while(true) {
                System.out.println("Servidor se encuentra a la escucha...");
                Socket peticion = servidor.accept();

                // 3 - Abrir flujos de lectura y escritura de datos
                InputStream is = peticion.getInputStream();
                OutputStream os = peticion.getOutputStream();

                // 4 - Intercambiar datos con el cliente
                // Leer mensaje enviado por el cliente e imprimirlo por consola
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);

                System.out.println("Mensaje enviado por el cliente: " + br.read());

                if (esPrimo(Integer.parseInt(br.readLine()))){
                    System.out.println("Servidor envía al cliente el mensaje");
                    bw.write("Es un numero primo");
                    bw.newLine();
                    bw.flush();
                } else {
                    System.out.println("Servidor envía al cliente el mensaje");
                    bw.write("Es un numero primo");
                    bw.newLine();
                    bw.flush();
                }
                // Enviarle mensaje al cliente
                System.out.println("Servidor envía al cliente el mensaje");

                bw.write("mamawebo tu");
                bw.newLine();
                bw.flush();

                // 5 - Cerrar flujos de lectura y escritura
                br.close();
                isr.close();
                bw.close();
                osw.close();
                is.close();
                os.close();
                peticion.close();
            }

            // 6 - Cerra la conexión
            /**System.out.println("Cierre de conexiones");
             peticion.close();
             servidor.close();**/

        } catch (IOException e) {
            System.err.println("Ha habido algún error en la creación del Socket Servidor");
            e.printStackTrace();
        }
    }

}
