import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Ana Gómez", "1001", "3001234567", "ana@correo.com", "Calle 10");
    }

    private Pedido nuevoPedido(String codigo) {
        return new Pedido(codigo, LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE,
                EstadoPedido.PENDIENTE, null, 0);
    }

    @Test
    void guardaLosDatos() {
        assertEquals("Ana Gómez", cliente.getNombre());
        assertEquals("1001", cliente.getDocumento());
        assertEquals("ana@correo.com", cliente.getCorreo());
    }

    @Test
    void historialIniciaVacio() {
        assertTrue(cliente.consultarHistorial().isEmpty());
    }

    @Test
    void agregarPedidoAumentaHistorial() {
        cliente.agregarPedido(nuevoPedido("PED1"));
        assertEquals(1, cliente.consultarHistorial().size());
    }

    @Test
    void historialContieneElPedidoAgregado() {
        Pedido p = nuevoPedido("PED1");
        cliente.agregarPedido(p);
        assertTrue(cliente.consultarHistorial().contains(p));
    }

    @Test
    void dosPedidosTamanoDos() {
        cliente.agregarPedido(nuevoPedido("PED1"));
        cliente.agregarPedido(nuevoPedido("PED2"));
        assertEquals(2, cliente.consultarHistorial().size());
    }
}
