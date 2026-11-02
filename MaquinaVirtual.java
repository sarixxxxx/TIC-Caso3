import java.util.Random;
import java.util.Set;

public class MaquinaVirtual extends Thread {
    private String idMV;
    private Set<Integer> procAsignados = null;

    private Set<Integer> pagsAsignadas = null;


    private Set<Integer> bloquesAsignados = null;

    private final Solicitud s;
    private final ZonaEspera ze;

    public MaquinaVirtual(
        Solicitud s,
        ZonaEspera ze
    ) {
        this.s = s;
        idMV = s.getId();
        this.ze = ze;
    }

    @Override
    public void run() {
        Random r = new Random();
        int minMs = 4000;
        int maxMs = 40000;
        try {
            Thread.sleep(r.nextInt(minMs,maxMs+1 ));
            ze.finalizarReservaRecurso(this);
            System.out.println("Finaliza máquina con id: "+s.getId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public String getIdMV() {
        return idMV;
    }

    public void setIdMV(String idMV) {
        this.idMV = idMV;
    }

    public Set<Integer> getProcAsignados() {
        return procAsignados;
    }

    public void setProcAsignados(Set<Integer> procAsignados) {
        this.procAsignados = procAsignados;
    }

    public Set<Integer> getPagsAsignadas() {
        return pagsAsignadas;
    }

    public void setPagsAsignadas(Set<Integer> pagsAsignadas) {
        this.pagsAsignadas = pagsAsignadas;
    }

    public Set<Integer> getBloquesAsignados() {
        return bloquesAsignados;
    }

    public void setBloquesAsignados(Set<Integer> bloquesAsignados) {
        this.bloquesAsignados = bloquesAsignados;
    }

    public Solicitud getS() {
        return s;
    }

}
