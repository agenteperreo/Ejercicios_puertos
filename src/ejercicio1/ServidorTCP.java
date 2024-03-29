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
                    break;
                }
            }
        }
        return esPrimo;
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
                int numEnviado=br.read();

                //Mostramos el numero enviado por el cliente
                System.out.println("Mensaje enviado por el cliente: " + numEnviado);

                //Creamos una variable para saber si es primo o no llamando a la funcion esPrimo
                boolean primo=esPrimo(numEnviado);

                //Si la variable es es true
                if (primo){
                    //Enviamos que es un numero primo
                    System.out.println("El servidor le envía la respuesta al cliente");
                    bw.write("Es un numero primo");
                    bw.newLine();
                    bw.flush();
                //Si es false
                } else {
                    //Enviamos que no es un numero primo
                    System.out.println("El servidor le envía la respuesta al cliente");
                    bw.write("No es un numero primo");
                    bw.newLine();
                    bw.flush();
                }


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
