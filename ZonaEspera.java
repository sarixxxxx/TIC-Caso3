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
                        mv.setProcAsignados(proc);
                        mv.setPagsAsignadas(mem);
                        mv.setBloquesAsignados(alm);
                        System.out.println("[ ZONA ESPERA ] : Se consiguieron todos los recursos para la máquina "+ mv.getIdMV()+ " id. Procesadores: "+mv.getProcAsignados()+", paginas "+mv.getPagsAsignadas().size()+", bloques "+ mv.getBloquesAsignados().size() );
                        enEspera.remove(mv.getIdMV());
                        if (enEspera.size()==0){
                            if(limpiezaZona) System.out.println("[ ZONA ESPERA ] : Acaba tiempo de espera limpieza con id mv "+ mv.getIdMV());
                            this.limpiezaZona = false;
                        }
                        if ( enEspera.size()>= umbral && !limpiezaZona) {
                            System.out.println("Empieza tiempo de limpieza con máquina virtual " +mv.getIdMV());
                            this.limpiezaZona = true;
                        }
                        mv.start();

                        return true;
                    } else {
                        System.out.println("[ ZONA ESPERA ] : Faltaron recursos para la máquina (bloques en disco) para id mv "+ mv.getIdMV());
                        cm.rollbackPaginas(mem);
                        cp.rollbackProcesadores(proc);
                    }
                } else {
                    System.out.println("[ ZONA ESPERA ] : Faltaron recursos para la máquina (bloques en disco y páginas) para id mv "+ mv.getIdMV());
                    cp.rollbackProcesadores(proc);
                }
            }
            System.out.println("[ ZONA ESPERA ] : Faltaron recursos para la máquina (todo faltó) para id mv "+ mv.getIdMV());
            if (enEspera.size()>=umbral && !limpiezaZona){
                System.out.println("[ ZONA ESPERA ]: Empieza tiempo de limpieza con máquina virtual " +mv.getIdMV());
                this.limpiezaZona = true;
            }
            wait();

        }
        // no se que tiene que retornar por ahora
        // TODO

    }

    public synchronized void finalizarReservaRecurso(MaquinaVirtual mv) {
        System.out.println("[ ZONA ESPERA ] : Acabó reserva recurso máquina virtual  " + mv.getIdMV());

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
