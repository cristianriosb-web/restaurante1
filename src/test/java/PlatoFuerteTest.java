import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    private PlatoFuerte plato;

    @BeforeEach
    void setUp() {
        plato = new PlatoFuerte(0.90, "Bandeja paisa", "Típica", 20000, 30, 500,
                TipoPreparacion.FRITO, true);
    }

    @Test
    void precioVentaIncrementa20Porciento() {
        assertEquals(24000, plato.calcularPrecioVenta(), 0.01);
    }

    @Test
    void cumpleCalidadConValoresValidos() {
        assertTrue(plato.cumpleCalidad());
    }

    @Test
    void noCumpleCalidadConPesoMenorA200() {
        plato.setPesoGramos(150);
        assertFalse(plato.cumpleCalidad());
    }

    @Test
    void noCumpleCalidadConTiempoMayorA60() {
        plato.setTiempoPreparacion(70);
        assertFalse(plato.cumpleCalidad());
    }

    @Test
    void noCumpleCalidadSinCoccionAprobada() {
        plato.setCoccionAprobada(false);
        assertFalse(plato.cumpleCalidad());
    }
}
