package controladores;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ControladorMemoria {
    private final String[] paginas;
    private final int tamPag;
    private final int totalGiB;

    public ControladorMemoria(
        int capacidad,
        int tamanioPagina
    ) {
        totalGiB = capacidad;
        tamPag = tamanioPagina;
        paginas = new String[totalGiB/tamPag];
        Arrays.fill(paginas, "libre");
    }

    public synchronized Set<Integer> reservarMemoria(
        String idMV,
        int n
    ) {
        int i = 0;
        Set<Integer> pagsReservadas = new HashSet<>();
        int paginasNecesarias = (int) Math.ceil((float) n/tamPag);
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
