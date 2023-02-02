package lecturaremotadeficheros;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServidorTCP {
    static String contenidos[]=new String[20];

    private static void leerArchivo(String rutaFichero) throws IOException {
        //Creamos la variable que va a almacenar lo que se le enviara al cliente
        String contenido = "";
        int contador=0;

        //Creamos un tipo File
        File archivo = new File(rutaFichero);

        //Si el archivo existe
        if (archivo.exists()){
            //Leemos el archivo y lo guardamos en una variable
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String brRL=br.readLine();
            while(brRL !=null) {
                contenidos[contador] = brRL;
                contador++;
                brRL=br.readLine();
            }

            br.close();
        //Si no
        }else {
            //Guardamos en la variable que no existe
            contenido = "El fichero dado no existe";
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

                //Llamamos a la funcion
                leerArchivo(ruta);

                //Mensaje enviado al cliente
                System.out.println("(Servidor): Envíamos el contenido del archivo al cliente");

                //Enviamos el mensaje al cliente
                for(int i=0; i< contenidos.length; i++) {
                    if(contenidos[i]==null) {
                        contenidos[i]="";
                    }
                    bw.write(contenidos[i].trim());
                    bw.newLine();
                }

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
