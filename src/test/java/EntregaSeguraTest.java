import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    private EntregaSegura entrega;

    @BeforeEach
    void setUp() {
        entrega = new EntregaSegura(7000, 1000, 20, 3000, 5);
    }

    @Test
    void costoPara5KmSinRecargo() {
        assertEquals(12000, entrega.calcularCosto(5), 0.01);
    }

    @Test
    void costoPara6KmConRecargo() {
        assertEquals(16000, entrega.calcularCosto(6), 0.01);
    }

    @Test
    void aceptaRecorridoDe20Km() {
        assertTrue(entrega.aceptaServicio(20));
    }

    @Test
    void noAceptaRecorridoDe21Km() {
        assertFalse(entrega.aceptaServicio(21));
    }

    @Test
    void costoPara20Km() {
        assertEquals(30000, entrega.calcularCosto(20), 0.01);
    }
}
