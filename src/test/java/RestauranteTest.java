import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    private Restaurante restaurante;
    private Cliente cliente;
    private PlatoFuerte plato;
    private Bebida bebida;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Sabor & Tradición", "900123456-7", "Armenia");
        cliente = new Cliente("Ana", "1001", "300", "ana@correo.com", "Calle 1");
        plato = new PlatoFuerte("P01", "Bandeja", "Típica", 20000, 30, 500, TipoPreparacion.FRITO, true);
        bebida = new Bebida("B01", "Limonada", "Natural", 5000, 5, 500, 5, true, false);
        restaurante.registrarCliente(cliente);
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(bebida);
    }

    @Test
    void productoConCodigoRepetidoNoSeRegistra() {
        Bebida otra = new Bebida("P01", "Jugo", "Mora", 4000, 5, 300, 5, true, false);
        assertFalse(restaurante.registrarProducto(otra));
        assertEquals(2, restaurante.getMenu().size());
    }

    @Test
    void clienteConDocumentoRepetidoNoSeRegistra() {
        assertFalse(restaurante.registrarCliente(new Cliente("Otro", "1001", "1", "o@c.com", "X")));
    }

    @Test
    void noConfirmaPedidoConProductoRechazado() {
        PlatoFuerte malo = new PlatoFuerte("P02", "Lechona", "x", 20000, 90, 950, TipoPreparacion.HORNEADO, false);
        restaurante.registrarProducto(malo);
        Pedido p = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE,
                EstadoPedido.PENDIENTE, null, 0);
        p.agregarDetalle(malo, 1);
        restaurante.registrarPedido(p);
        assertFalse(restaurante.confirmarPedido("PED1"));
        assertEquals(EstadoPedido.PENDIENTE, p.getEstado());
    }

    @Test
    void ingresosExcluyenDomicilio() {
        Pedido p = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.DOMICILIO,
                EstadoPedido.PENDIENTE, new RapidoExpress(5000, 1500, 12), 4);
        p.agregarDetalle(plato, 2);   // 48.000
        p.agregarDetalle(bebida, 3);  // 16.500
        restaurante.registrarPedido(p);
        assertTrue(restaurante.confirmarPedido("PED1"));
        assertEquals(64500, restaurante.calcularIngresosRestaurante(), 0.01);
        assertEquals(11000, restaurante.calcularIngresosDomicilios(), 0.01);
    }

    @Test
    void productoMasVendidoEsElDeMasUnidades() {
        Pedido p = new Pedido("PED1", LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE,
                EstadoPedido.PENDIENTE, null, 0);
        p.agregarDetalle(plato, 1);
        p.agregarDetalle(bebida, 5);
        restaurante.registrarPedido(p);
        restaurante.confirmarPedido("PED1");
        assertSame(bebida, restaurante.obtenerProductoMasVendido());
        assertEquals("Bebida", restaurante.obtenerCategoriaMayorIngreso()); // 27.500 > 24.000
    }
}
