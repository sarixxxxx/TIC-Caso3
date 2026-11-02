package pruebas;

import java.util.Set;

import controladores.ControladorAlmacenamiento;
import controladores.ControladorMemoria;

public class MainPruebasControladorAlmacenamiento {
    public static void main(String[] args){
        ControladorAlmacenamiento cm = new ControladorAlmacenamiento(1, 4);
        // para el ejemplo, son 262144 páginas

        // como se pidió toda la totalidad, debería dar todas las páginas y no debería poderse reservar
        // más, a menos de que sea 0 de memoria solicitada
        Set<Integer> memA = cm.reservarBloque("a", 1048576);
        System.out.println("N páginas solicitadas por a "+ memA.size());

        Set<Integer> memB = cm.reservarBloque("b", 4);
        System.out.println("N páginas solicitadas por b "+ memB);

        cm.rollbackBloques(memA);
        memA = cm.reservarBloque("a", 5);
        System.out.println("N páginas solicitadas por a "+ memA);

        memB = cm.reservarBloque("b", 4);
        System.out.println("N páginas solicitadas por b "+ memB);


    }
}
