package controladores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControladorMemoria {
    private final String[] paginas;
    private final int tamPagKiB;
    private final int totalGiB;

    public ControladorMemoria(
        int capacidad,
        int tamanioPaginaKiB
    ) {
        totalGiB = capacidad;
        tamPagKiB = tamanioPaginaKiB;
        paginas = new String[totalGiB*(1024*1024)/(tamPagKiB)];
        Arrays.fill(paginas, "libre");
    }

    public synchronized Set<Integer> reservarMemoria(
        String idMV,
        int n
    ) {
        int i = 0;
        Set<Integer> pagsReservadas = new HashSet<>();
        int paginasNecesarias = (int) Math.ceil((float) n*(1024*1024)/tamPagKiB);
        while (i<paginas.length && pagsReservadas.size()<paginasNecesarias) {
            if (Objects.equals(paginas[i], "libre")) {
                paginas[i] = idMV;
                pagsReservadas.add(i);
            }
            i++;
        }
        if (pagsReservadas.size()==paginasNecesarias) {
            return pagsReservadas;
        } else {
            rollbackPaginas(pagsReservadas);
            return null;
        }
    }

    public synchronized void rollbackPaginas(Set<Integer> liberar) {
        for (int i :liberar) {
            paginas[i] = "libre";
        }
    }
}
