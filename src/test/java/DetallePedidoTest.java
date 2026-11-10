import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    private PlatoFuerte plato;
    private Bebida bebida;
    private Postre postre;

    @BeforeEach
    void setUp() {
        plato = new PlatoFuerte("P01", "Bandeja", "Típica", 20000, 30, 500, TipoPreparacion.FRITO, true);
        bebida = new Bebida("B01", "Limonada", "Natural", 5000, 5, 500, 5, true, false);
        postre = new Postre("D01", "Natas", "Tradicional", 8000, 15, 200, 4, true);
    }

    @Test
    void subtotalDosPlatos() {
        assertEquals(48000, new DetallePedido(plato, 2).calcularSubtotal(), 0.01);
    }

    @Test
    void subtotalTresBebidas() {
        assertEquals(16500, new DetallePedido(bebida, 3).calcularSubtotal(), 0.01);
    }

    @Test
    void subtotalUnPostreRefrigerado() {
        assertEquals(9600, new DetallePedido(postre, 1).calcularSubtotal(), 0.01);
    }

    @Test
    void guardaLaCantidad() {
        assertEquals(4, new DetallePedido(plato, 4).getCantidad());
    }

    @Test
    void guardaElProducto() {
        assertSame(bebida, new DetallePedido(bebida, 1).getProducto());
    }
}
