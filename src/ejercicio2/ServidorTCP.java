package ejercicio2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {

    static int factorial(int num) {
        //Declaración de variables
        int total=0;

        for(int i=num; i>0; i++) {
            total*=i;
        }

        return total;
    }

    public static void main(String[] args) {

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

                // Enviarle mensaje al cliente
                System.out.println("Servidor envía al cliente el mensaje");

                int total=factorial(Integer.parseInt(br.readLine()));
                bw.write(total);
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
