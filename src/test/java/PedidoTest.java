import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private PlatoFuerte plato;
    private Bebida bebida;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("Ana", "1001", "300", "ana@correo.com", "Calle 1");
        pedido = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE,
                EstadoPedido.PENDIENTE, null, 0);
        plato = new PlatoFuerte("P01", "Bandeja", "Típica", 20000, 30, 500, TipoPreparacion.FRITO, true);
        bebida = new Bebida("B01", "Limonada", "Natural", 5000, 5, 500, 5, true, false);
    }

    @Test
    void estadoInicialEsPendiente() {
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
    }

    @Test
    void valorProductosDosPlatosYTresBebidas() {
        pedido.agregarDetalle(plato, 2);
        pedido.agregarDetalle(bebida, 3);
        assertEquals(64500, pedido.calcularValorProductos(), 0.01);
    }

    @Test
    void totalIncluyeDomicilioRapidoExpress4Km() {
        pedido.setModalidad(ModalidadEntrega.DOMICILIO);
        pedido.setServicioDomicilio(new RapidoExpress(5000, 1500, 12));
        pedido.setDistanciaKm(4);
        pedido.agregarDetalle(plato, 2);
        pedido.agregarDetalle(bebida, 3);
        assertEquals(11000, pedido.calcularCostoDomicilio(), 0.01);
        assertEquals(75500, pedido.calcularValorTotal(), 0.01);
    }

    @Test
    void noSePuedeConfirmarDosVeces() {
        pedido.actualizarEstado(EstadoPedido.CONFIRMADO);
        assertThrows(IllegalStateException.class,
                () -> pedido.actualizarEstado(EstadoPedido.CONFIRMADO));
    }

    @Test
    void pedidoCanceladoNoSePuedeEntregar() {
        pedido.actualizarEstado(EstadoPedido.CANCELADO);
        assertThrows(IllegalStateException.class,
                () -> pedido.actualizarEstado(EstadoPedido.ENTREGADO));
        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
    }
}
