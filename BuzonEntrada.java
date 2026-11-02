import java.util.ArrayList;

public class BuzonEntrada {
    private ArrayList<Solicitud> buff;
    private int n;

    public BuzonEntrada(
        int n
    ) {
        this.n = n;
        buff = new ArrayList<Solicitud>();
    }

    public synchronized void crearSolicitud( Solicitud s) throws InterruptedException {
        while (buff.size() == n){
            wait();
        }
        buff.add(s);
        notifyAll();
        // mirar si si es con all o no
    }

    public synchronized Solicitud gestionarSolicitud() throws InterruptedException{
        while (buff.isEmpty()) {
                wait();
        }
        Solicitud solicitud = buff.remove(0);
        notifyAll();
        // lo mismo acá 
        return solicitud;
    }
}
