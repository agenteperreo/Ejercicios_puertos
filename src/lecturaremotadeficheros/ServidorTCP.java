package lecturaremotadeficheros;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServidorTCP {

    public static void leerFichero(String ruta, Socket peticion) {
        BufferedWriter bw=null;
        String linea="";

        try {
            OutputStream os = peticion.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            bw = new BufferedWriter(osw);
            File archivo=new File(ruta);
            FileReader fr = new FileReader(archivo);
            BufferedReader brr=new BufferedReader(fr);

            while(linea!=null) {
                linea= brr.readLine();
                bw.write(linea);
                bw.newLine();
                bw.flush();
            }
        } catch (FileNotFoundException e) {
            //bw.write("El archivo no se encuentra.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

                //Guardo el numero enviado en una variable
                String ruta=br.readLine();

                //Mostramos el numero enviado por el cliente
                System.out.println("Mensaje enviado por el cliente: " + ruta);




                // 5 - Cerrar flujos de lectura, escritura y conexion
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
