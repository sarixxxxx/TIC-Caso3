import java.util.ArrayList;

public class BuzonEntrada {
    private ArrayList<Solicitud> buff;
    private int n;
    private int usuariosActivos; 

    public BuzonEntrada(
        int n,
        int usuarios
    ) {
        this.n = n;
        usuariosActivos = usuarios;
        buff = new ArrayList<Solicitud>();
    }

    public synchronized void crearSolicitud( Solicitud s) throws InterruptedException {
        while (buff.size() == n){
            wait();
        }
        buff.add(s);
        notify();
        // mirar si si es con all o no
    }


    public Solicitud gestionarSolicitud() {
        Solicitud s = obtenerSolicitud();
        while (s == null) {
            Thread.yield();
            s = obtenerSolicitud();
        }
        return s;
    }
    public synchronized Solicitud obtenerSolicitud(){

        if (buff.isEmpty()) {
            return null;
        }
        Solicitud solicitud = buff.remove(0);
        notifyAll(); 
        return solicitud;
    }

    public synchronized void reportarTerminacion(){
        usuariosActivos--;
    }
    public synchronized boolean hayUsuariosActivos(){
        return usuariosActivos>0;
    }
}
