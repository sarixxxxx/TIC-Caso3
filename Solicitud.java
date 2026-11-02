public class Solicitud {
    private String id;
    private int numProcesadores;
    private int memEspacio; // en GiB
    private int espacioDisco;

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
