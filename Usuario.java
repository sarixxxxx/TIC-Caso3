import java.util.logging.Logger;

public class Usuario extends Thread {
    private static final Logger logger = Logger.getLogger(Usuario.class.getName());
    private BuzonEntrada buff;
    private GeneradorAleatorio g;
    private int nSolicitudes;
    private int idUsuario;

    public Usuario(
        BuzonEntrada b,
        GeneradorAleatorio g,
        int n, 
        int id
    ) {
        buff = b;
        this.g = g;
        nSolicitudes = n*id ;
        idUsuario = id;
    }  

    @Override
    public void run(){
        for (int i = 0; i<nSolicitudes; i++){
            int[] recursos = g.generar();
            Solicitud s = new Solicitud(String.valueOf(idUsuario)+"-"+i,recursos[0],recursos[1] ,recursos[2]);
            try {
                logger.info("[ USUARIO ]: Creando solicitud "+ s.getId());
                buff.crearSolicitud(s);
            } catch (InterruptedException e) {
                logger.info("error :(");
            }
        }
        buff.reportarTerminacion();
        logger.info("[ USUARIO ]: Finalizada ejecución para "+idUsuario);

    }
}