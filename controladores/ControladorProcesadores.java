package controladores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControladorProcesadores {
    private final String[] cuposProcesadores;
    public ControladorProcesadores(
        int nProc
    ) {
        cuposProcesadores = new String[nProc];
        Arrays.fill(cuposProcesadores, "libre");
    }

    public synchronized Set<Integer> reservarProcesadores(
        String idMV,
        int n
    ) {
        int i = 0;
        Set<Integer> reservados = new HashSet<>();
        while (i<cuposProcesadores.length && reservados.size()<n){
            if (Objects.equals(cuposProcesadores[i], "libre")) {
                cuposProcesadores[i] = idMV;
                reservados.add(i);
            }
            i++;
        }

        if (reservados.size()==n) {
            return reservados;
        } else {
            rollbackProcesadores(reservados);
            return null;
        }
    }

    public synchronized void rollbackProcesadores (
        Set<Integer> liberar
    ) {
        for (int i: liberar) {
            cuposProcesadores[i] ="libre";
        }
    }
    
}
