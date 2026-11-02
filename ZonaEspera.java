import java.util.ArrayList;
import java.util.Set;

import controladores.ControladorAlmacenamiento;
import controladores.ControladorMemoria;
import controladores.ControladorProcesadores;

public class ZonaEspera {
    private int umbral;
    private boolean limpiezaZona = false;
    private ArrayList<MaquinaVirtual> enEspera = new ArrayList<>();

    private ControladorProcesadores cp;
    private ControladorMemoria cm;
    private ControladorAlmacenamiento ca;

    public ZonaEspera(
            int nProc,
            int capacidadMem,
            int tamPag,
            int capacidadAlm,
            int tamBloq

    ) {
        cp = new ControladorProcesadores(nProc);
        cm = new ControladorMemoria(capacidadMem, tamPag);
        ca = new ControladorAlmacenamiento(capacidadAlm, tamBloq);
    }


    public synchronized boolean reservarRecurso(MaquinaVirtual mv) throws InterruptedException {
        Set<Integer> proc = cp.reservarProcesadores(mv.getIdMV(), mv.getS().getNumProcesadores());
        if (proc != null) {
            Set<Integer> mem = cm.reservarMemoria(mv.getIdMV(), mv.getS().getMemEspacio());
            if (mem != null) {
                Set<Integer> alm = ca.reservarBloque(mv.getIdMV(), mv.getS().getEspacioDisco());
                if (alm != null) {
                    // coronó, ahora tiene que correr la máquina virtual
                    mv.setProcAsignados(proc);
                    mv.setPagsAsignadas(mem);
                    mv.setBloquesAsignados(alm);
                    mv.start();
                    return true;
                } else {
                    cm.rollbackPaginas(mem);
                    cp.rollbackProcesadores(proc);
                }
            } else {
                cp.rollbackProcesadores(proc);
            }
        }
        wait();
        // no se que tiene que retornar por ahora
        // TODO
        return false;
    }

    public synchronized void finalizarReservaRecurso(MaquinaVirtual mv) {
        cp.rollbackProcesadores(mv.getProcAsignados());
        cm.rollbackPaginas(mv.getPagsAsignadas());
        ca.rollbackBloques(mv.getBloquesAsignados());
        notify();
        // ¿debería
        //despertar una sola o todas? ¿por qué? -> no lo SEEE
    }

    
    
}
