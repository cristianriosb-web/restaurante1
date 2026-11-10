import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    private Bebida bebida;

    @BeforeEach
    void setUp() {
        bebida = new Bebida("B01", "Limonada", "Natural", 5000, 5, 500, 5, true, false);
    }

    @Test
    void precioVentaIncrementa10Porciento() {
        assertEquals(5500, bebida.calcularPrecioVenta(), 0.01);
    }

    @Test
    void precioVentaConPreparacionEspecialSuma3000() {
        bebida.setPreparacionEspecial(true);
        assertEquals(8500, bebida.calcularPrecioVenta(), 0.01);
    }

    @Test
    void bebidaFriaDe500mlA5GradosCumple() {
        assertTrue(bebida.cumpleCalidad());
    }

    @Test
    void bebidaCalienteA40GradosNoCumple() {
        bebida.setEsFria(false);
        bebida.setTemperaturaServicio(40);
        assertFalse(bebida.cumpleCalidad());
    }

    @Test
    void bebidaDe2000mlNoCumple() {
        bebida.setVolumenMl(2000);
        assertFalse(bebida.cumpleCalidad());
    }
}
