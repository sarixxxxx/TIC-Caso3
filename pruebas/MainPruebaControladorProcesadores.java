package pruebas;

import java.util.Set;

import controladores.ControladorProcesadores;

public class MainPruebaControladorProcesadores {
    public static void main(String[] args){
        ControladorProcesadores cp = new ControladorProcesadores(10);
        // desde máquina "a" intentamos reservar 5
        Set<Integer> reservadosA = cp.reservarProcesadores("a", 5);
        System.out.println("Reserva 5 procesos desde a, asignados: "+reservadosA);

        Set<Integer> reservadosB = cp.reservarProcesadores("b", 3);
        System.out.println("Reserva 3 procesos desde b, asignados: "+reservadosB);

        Set<Integer> reservadosC = cp.reservarProcesadores("c", 3);
        System.out.println("Reserva 5 procesos desde c, asignados: "+reservadosC);
        // se espera que no haya ninguno reservado por c, ya que no hay suficientes de los que necesita

        System.out.println("libero los de A");
        cp.rollbackProcesadores(reservadosA);

        //ahora si debería dejar, porque el proceso de limpieza libera adecuadamente los recursos
        Set<Integer> reservadosC2 = cp.reservarProcesadores("c", 3);
        System.out.println("Reserva 3 procesos desde c, asignados en segundo intento: "+reservadosC2);
    }
}
