import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);

        logger.info(" Bienvenido al programa, ingrese los siguientes parámetros :");
        logger.info(" Número de usuarios (nu) :");
        int nu = sc.nextInt();
        
        logger.info(" Número base de eventos (base) :");
        int base = sc.nextInt();
        
        logger.info(" Número de gestores (ng) :");
        int ng = sc.nextInt();
        
        logger.info(" Capacidad del buzón de entrada (tam1) :");
        int tam1 = sc.nextInt();
        
        logger.info(" Capacidad de la zona de espera (tam2) :");
        int tam2 = sc.nextInt();
        
        logger.info(" Número de procesadores en el sistema (procesadores) :");
        int procesadores = sc.nextInt();
        
        logger.info(" Capacidad en memoria (en GiB) :");
        int memoria = sc.nextInt();
        
        logger.info(" Tamaño de página (en KiB) :");
        int pagina = sc.nextInt();
        
        logger.info(" Capacidad en almacenamiento (en GiB) :");
        int almacenamiento = sc.nextInt();
        
        logger.info(" Tamaño de bloque (en KiB) :");
        int bloque = sc.nextInt();

        BuzonEntrada buzonEntrada = new BuzonEntrada(base);
        GeneradorAleatorio generadorAleatorio = new GeneradorAleatorio();

        for (int usuario = 0; usuario<nu;usuario ++) {
            new Usuario(
                buzonEntrada, 
                generadorAleatorio, 
                usuario).start();
        }
    }
}
