import java.util.logging.Logger;

public class GestorSolicitud extends Thread {
    // este sería el consumidor
    private static final Logger logger = Logger.getLogger(GestorSolicitud.class.getName());
    private BuzonEntrada buff;

    public GestorSolicitud(
        BuzonEntrada b
    ) {
        buff = b;
    }

    @Override 
    public void run(){
        while (buff.hayUsuariosActivos()){
            Solicitud s = buff.gestionarSolicitud();
        }
        logger.info("Finaliza gestor solicitud :)");

    }
}
