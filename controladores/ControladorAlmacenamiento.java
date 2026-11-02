package controladores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControladorAlmacenamiento {
    private final String[] bloques;
    private final int tamBloque;
    private final int totalGiB;

    public ControladorAlmacenamiento(
        int capacidad,
        int tamanioBloque
    ) {
        totalGiB = capacidad;
        tamBloque = tamanioBloque;
        bloques = new String[totalGiB/tamBloque];
        Arrays.fill(bloques, "libre");
    }
    public synchronized Set<Integer> reservarBloque(
        String idMV,
        int n
    ) {
        int i = 0;
        int bloquesNecesarios = (int) Math.ceil((float) n/tamBloque);
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
