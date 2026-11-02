public class Solicitud {
    private String id;
    private int numProcesadores;
    private int memEspacio; // en GiB
    private int espacioDisco;

    public String getId() {
        return id;
    }

    public int getNumProcesadores() {
        return numProcesadores;
    }

    public int getMemEspacio() {
        return memEspacio;
    }

    public int getEspacioDisco() {
        return espacioDisco;
    }

    public Solicitud(
        String id,
        int np,
        int me,
        int ed
    ) {
        this.id = id;
        numProcesadores = np;
        memEspacio = me;
        espacioDisco = ed;
    }

}
