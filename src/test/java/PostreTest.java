import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    private Postre postre;

    @BeforeEach
    void setUp() {
        postre = new Postre("D01", "Natas", "Tradicional", 8000, 15, 200, 4, true);
    }

    @Test
    void precioVentaSinRefrigeracionIncrementa15Porciento() {
        postre.setRequiereRefrigeracion(false);
        assertEquals(9200, postre.calcularPrecioVenta(), 0.01);
    }

    @Test
    void precioVentaRefrigeradoIncrementa20Porciento() {
        assertEquals(9600, postre.calcularPrecioVenta(), 0.01);
    }

    @Test
    void refrigeradoDe200gA4GradosCumple() {
        assertTrue(postre.cumpleCalidad());
    }

    @Test
    void refrigeradoA10GradosNoCumple() {
        postre.setTemperaturaAlmacenamiento(10);
        assertFalse(postre.cumpleCalidad());
    }

    @Test
    void postreDe600gNoCumple() {
        postre.setPesoGramos(600);
        assertFalse(postre.cumpleCalidad());
    }
}
