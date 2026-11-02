import java.util.Random;

/**
 * GeneradorAleatorio
 */
public class GeneradorAleatorio {
    private int maxProcesadores;
    private int maxMemoria;
    private int maxAlmacenamiento;
    private Random r= new Random();

    public GeneradorAleatorio(
        int mp,
        int mm,
        int ma
    ) {
        maxProcesadores = mp;
        maxMemoria = mm;
        maxAlmacenamiento = ma;
    }

    public int[] generar() {
        int[] respuesta = new int[3];
        respuesta[0] = r.nextInt(Math.round((float) maxProcesadores*3/4)) + 1; // procesadores
        int memoriaRaw = r.nextInt(Math.round((float) maxMemoria*3/4)) + 1;
        int potencia2= (int) Math.floor((float) Math.log(memoriaRaw)/Math.log(2));
        respuesta [1] = r.nextInt((int) Math.pow(2, potencia2));

        int almacenamientoRaw =  r.nextInt(Math.round((float) maxAlmacenamiento*3/4)) + 1;
        int multiplo2 = almacenamientoRaw /2 ;
        respuesta[2] = multiplo2*2;
        return respuesta;
    }

}
