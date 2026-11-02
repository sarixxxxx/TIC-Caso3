import java.util.logging.Logger;

public class GestorSolicitud extends Thread {
    // este sería el consumidor
    private static final Logger logger = Logger.getLogger(GestorSolicitud.class.getName());
    private final BuzonEntrada buff;
    private final ZonaEspera ze;

    public GestorSolicitud(
        BuzonEntrada b,
        ZonaEspera ze
    ) {
        buff = b;
        this.ze = ze;
    }

    @Override 
    public void run(){
        while (buff.hayUsuariosActivos()|| buff.haySolicitudes()){
            Solicitud s = buff.gestionarSolicitud();
            while  (ze.esEsperaLimpieza()){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            MaquinaVirtual mv = new MaquinaVirtual(s, ze);
            try {
                ze.reservarRecurso(mv);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("Finaliza gestor solicitud :)");

    }
}
