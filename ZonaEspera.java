import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controladores.ControladorAlmacenamiento;
import controladores.ControladorMemoria;
import controladores.ControladorProcesadores;

public class ZonaEspera {
    private final int umbral;
    private boolean limpiezaZona = false;
    private final HashMap<String, MaquinaVirtual> enEspera = new HashMap<String, MaquinaVirtual>();

    private ControladorProcesadores cp;
    private ControladorMemoria cm;
    private ControladorAlmacenamiento ca;

    public ZonaEspera(
            int nProc,
            int capacidadMem,
            int tamPag,
            int capacidadAlm,
            int tamBloq,
            int umbral

    ) {
        cp = new ControladorProcesadores(nProc);
        cm = new ControladorMemoria(capacidadMem, tamPag);
        ca = new ControladorAlmacenamiento(capacidadAlm, tamBloq);
        this.umbral = umbral;
    }


    public synchronized boolean reservarRecurso(MaquinaVirtual mv) throws InterruptedException {
        enEspera.put(mv.getIdMV(), mv);
        while (true) {
            Set<Integer> proc = cp.reservarProcesadores(mv.getIdMV(), mv.getS().getNumProcesadores());
            if (proc != null) {
                Set<Integer> mem = cm.reservarMemoria(mv.getIdMV(), mv.getS().getMemEspacio());
                if (mem != null) {
                    Set<Integer> alm = ca.reservarBloque(mv.getIdMV(), mv.getS().getEspacioDisco());
                    if (alm != null) {
                        // coronó, ahora tiene que correr la máquina virtual
                        enEspera.remove(mv.getIdMV());
                        if (enEspera.size()==0){
                            this.limpiezaZona = false;
                        }
                        mv.setProcAsignados(proc);
                        mv.setPagsAsignadas(mem);
                        mv.setBloquesAsignados(alm);
                        if ( enEspera.size()>= umbral) {
                            this.limpiezaZona = true;
                        }
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
            if (enEspera.size()>=umbral){
                this.limpiezaZona = true;
            }
            wait();

        }
        // no se que tiene que retornar por ahora
        // TODO

    }

    public synchronized void finalizarReservaRecurso(MaquinaVirtual mv) {
        cp.rollbackProcesadores(mv.getProcAsignados());
        cm.rollbackPaginas(mv.getPagsAsignadas());
        ca.rollbackBloques(mv.getBloquesAsignados());
        notifyAll();
        // ¿debería
        //despertar una sola o todas? ¿por qué? -> no lo SEEE
    }

    public boolean esEsperaLimpieza(){
        return limpiezaZona;
    }

    
    
}
