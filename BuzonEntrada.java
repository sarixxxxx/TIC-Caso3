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
            System.out.println("[ BUZON ENTRADA ] Espera solicitud por buffer lleno "+ s.getId());
            wait();
        }
        buff.add(s);
        System.out.println("[ BUZON ENTRADA ] Solicitud recibida al buffer "+ s.getId());
        notify();
        // mirar si si es con all o no
    }


    public Solicitud gestionarSolicitud() {
        Solicitud s = obtenerSolicitud();
        while (s == null) {
            Thread.yield();
            s = obtenerSolicitud();
        }
        System.out.println("[ BUZON ENTRADA - GESTOR ] Procesamiento de solicituddd "+ s.getId());
        return s;
    }
    public synchronized Solicitud obtenerSolicitud(){

        if (buff.isEmpty()) {
            return null;
        }
        Solicitud solicitud = buff.removeFirst();
        notifyAll(); 
        return solicitud;
    }

    public synchronized void reportarTerminacion(){
        usuariosActivos--;
    }
    public synchronized boolean hayUsuariosActivos(){
        return usuariosActivos>0;
    }

    public synchronized boolean haySolicitudes(){ return !buff.isEmpty();}
}
