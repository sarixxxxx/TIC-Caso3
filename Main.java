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

        BuzonEntrada buzonEntrada = new BuzonEntrada(tam1, nu);
        GeneradorAleatorio generadorAleatorio = new GeneradorAleatorio(procesadores, memoria*1024*1024, almacenamiento*1024*1024);
        ZonaEspera ze = new ZonaEspera(
                procesadores,
                memoria,
                pagina,
                almacenamiento,
                bloque,
                tam2
        );

        for (int usuario = 1; usuario<nu+1;usuario ++) {
            new Usuario(
                buzonEntrada,
                generadorAleatorio,
                base,
                usuario).start();
        }

        for (int gestor =0; gestor<ng; gestor++) {
            new GestorSolicitud(buzonEntrada, ze).start();
        }

    }
}
