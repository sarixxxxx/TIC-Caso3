package controladores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControladorAlmacenamiento {
    private final String[] bloques;
    private final int tamBloqueKiB;
    private final int totalGiB;

    public ControladorAlmacenamiento(
        int capacidad,
        int tamanioBloque
    ) {
        totalGiB = capacidad;
        tamBloqueKiB = tamanioBloque;
        bloques = new String[totalGiB*(1024*1024)/(tamBloqueKiB)];
        Arrays.fill(bloques, "libre");
    }
    public synchronized Set<Integer> reservarBloque(
        String idMV,
        int n
    ) {
        int i = 0;
        int bloquesNecesarios = (int) Math.ceil((float) n*(1024*1024)/tamBloqueKiB);
        Set<Integer> bloquesReservados = new HashSet<>();
        while (i<bloques.length && bloquesReservados.size()<bloquesNecesarios) {
            if (Objects.equals(bloques[i], "libre")) {
                bloques[i] = idMV;
                bloquesReservados.add(i);
            }
            i++;
        }
        if (bloquesReservados.size()==bloquesNecesarios) {
            return bloquesReservados;
        } else {
            rollbackBloques(bloquesReservados);
            return null;
        }
    }
    public synchronized void rollbackBloques(Set<Integer> liberar) {
        for (int i :liberar) {
            bloques[i] = "libre";
        }
    }

}
