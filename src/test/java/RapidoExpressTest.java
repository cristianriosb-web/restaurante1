import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    private RapidoExpress rapido;

    @BeforeEach
    void setUp() {
        rapido = new RapidoExpress(5000, 1500, 12);
    }

    @Test
    void costoPara10Km() {
        assertEquals(20000, rapido.calcularCosto(10), 0.01);
    }

    @Test
    void aceptaRecorridoDe12Km() {
        assertTrue(rapido.aceptaServicio(12));
    }

    @Test
    void noAceptaRecorridoDe13Km() {
        assertFalse(rapido.aceptaServicio(13));
    }

    @Test
    void noAceptaRecorridoDe0Km() {
        assertFalse(rapido.aceptaServicio(0));
    }

    @Test
    void costoPara1Km() {
        assertEquals(6500, rapido.calcularCosto(1), 0.01);
    }
}
