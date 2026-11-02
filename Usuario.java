public class Usuario extends Thread {
    private BuzonEntrada buff;
    private GeneradorAleatorio g;
    private int nSolicitudes;

    public Usuario(
        BuzonEntrada b,
        GeneradorAleatorio g,
        int n
    ) {
        buff = b;
        this.g = g;
        nSolicitudes = n;
    }  

    @Override
    public void run(){
        for (int i = 0; i<nSolicitudes; i++){
            Solicitud s = new Solicitud(i, i, i, i);
            try {
                buff.crearSolicitud(s);
            } catch (InterruptedException e) {
                System.out.println("error :(");
                e.printStackTrace();
            }
        }
    }
}