
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurante {

    private String nombre;
    private String nit;
    private String direccion;
    private ArrayList<Producto> menu;
    private ArrayList<Cliente> cliente;
    private ArrayList<Pedido> pedido;

    public Restaurante(String nombre, String nit, String direccion) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.menu = new ArrayList<>();
        this.cliente = new ArrayList<>();
        this.pedido = new ArrayList<>();
    }

    public boolean registrarProducto(Producto producto) {
        if (producto == null || buscarProducto(producto.getCodigo()) != null) {
            return false;
        }
        menu.add(producto);
        return true;
    }

    public boolean registrarCliente(Cliente nuevo) {
        if (nuevo == null || buscarCliente(nuevo.getDocumento()) != null) {
            return false;
        }
        cliente.add(nuevo);
        return true;
    }

    public List<Producto> consultarProductosDisponibles() {
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : menu) {
            if (p.cumpleCalidad()) {
                disponibles.add(p);
            }
        }
        return disponibles;
    }

    public List<Producto> consultarProductosRechazados() {
        List<Producto> rechazados = new ArrayList<>();
        for (Producto p : menu) {
            if (!p.cumpleCalidad()) {
                rechazados.add(p);
            }
        }
        return rechazados;
    }

    public void registrarPedido(Pedido nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        if (buscarPedido(nuevo.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un pedido con el código " + nuevo.getCodigo());
        }
        pedido.add(nuevo);
    }

    public String verificarPedido(String codigoPedido) {
        Pedido p = buscarPedido(codigoPedido);
        if (p == null) {
            return "El pedido no existe";
        }
        if (p.getEstado() != EstadoPedido.PENDIENTE) {
            return "El pedido ya está " + p.getEstado() + " (solo se confirman pedidos PENDIENTES)";
        }
        if (p.getCliente() == null || buscarCliente(p.getCliente().getDocumento()) == null) {
            return "El cliente no está registrado";
        }
        if (p.getDetalles().isEmpty()) {
            return "El pedido no tiene productos";
        }
        for (DetallePedido d : p.getDetalles()) {
            if (!menu.contains(d.getProducto())) {
                return "El producto " + d.getProducto().getNombre() + " no pertenece al menú";
            }
            if (d.getCantidad() <= 0) {
                return "La cantidad de " + d.getProducto().getNombre() + " debe ser mayor que cero";
            }
            if (!d.getProducto().cumpleCalidad()) {
                return "El producto " + d.getProducto().getNombre() + " no cumple las condiciones de calidad";
            }
        }
        if (p.getModalidad() == ModalidadEntrega.DOMICILIO && !p.domicilioAceptado()) {
            return "Ninguna empresa de domicilio aceptó el servicio";
        }
        return null;
    }

    public boolean confirmarPedido(String codigoPedido) {
        if (verificarPedido(codigoPedido) != null) {
            return false;
        }
        Pedido p = buscarPedido(codigoPedido);
        p.actualizarEstado(EstadoPedido.CONFIRMADO);
        p.getCliente().agregarPedido(p);
        return true;
    }

    public List<Pedido> consultarPedidosCliente(String documento) {
        Cliente c = buscarCliente(documento);
        return c == null ? new ArrayList<>() : c.consultarHistorial();
    }

    public double calcularIngresosRestaurante() {
        double total = 0;
        for (Pedido p : pedido) {
            if (p.esVentaValida()) {
                total += p.calcularValorProductos();
            }
        }
        return total;
    }

    public double calcularIngresosDomicilios() {
        double total = 0;
        for (Pedido p : pedido) {
            if (p.esVentaValida()) {
                total += p.calcularCostoDomicilio();
            }
        }
        return total;
    }

    public int calcularUnidadesVendidas(String codigoProducto) {
        int unidades = 0;
        for (Pedido p : pedido) {
            if (p.esVentaValida()) {
                for (DetallePedido d : p.getDetalles()) {
                    if (d.getProducto().getCodigo().equals(codigoProducto)) {
                        unidades += d.getCantidad();
                    }
                }
            }
        }
        return unidades;
    }

    public Producto obtenerProductoMasVendido() {
        Map<Producto, Integer> unidades = new HashMap<>();
        for (Pedido p : pedido) {
            if (p.esVentaValida()) {
                for (DetallePedido d : p.getDetalles()) {
                    unidades.merge(d.getProducto(), d.getCantidad(), Integer::sum);
                }
            }
        }
        Producto masVendido = null;
        int max = 0;
        for (Map.Entry<Producto, Integer> e : unidades.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                masVendido = e.getKey();
            }
        }
        return masVendido;
    }

    public String obtenerCategoriaMayorIngreso() {
        Map<String, Double> ingresos = new HashMap<>();
        for (Pedido p : pedido) {
            if (p.esVentaValida()) {
                for (DetallePedido d : p.getDetalles()) {
                    String categoria = d.getProducto().getClass().getSimpleName();
                    ingresos.merge(categoria, d.calcularSubtotal(), Double::sum);
                }
            }
        }
        String mayor = null;
        double max = 0;
        for (Map.Entry<String, Double> e : ingresos.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                mayor = e.getKey();
            }
        }
        return mayor;
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : menu) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String documento) {
        for (Cliente c : cliente) {
            if (c.getDocumento().equals(documento)) {
                return c;
            }
        }
        return null;
    }

    public Pedido buscarPedido(String codigo) {
        for (Pedido p : pedido) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Producto> getMenu() { return menu; }
    public List<Cliente> getCliente() { return cliente; }
    public List<Pedido> getPedido() { return pedido; }

    @Override
    public String toString() {
        return nombre + " | NIT: " + nit + " | " + direccion;
    }
}
