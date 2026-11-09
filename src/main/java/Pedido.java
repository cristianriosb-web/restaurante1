
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private Cliente cliente;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private ArrayList<DetallePedido> detalles;
    private ServicioDomicilio servicioDomicilio; // null cuando es en restaurante
    private double distanciaKm;

    public Pedido(String codigo, LocalDate fecha, Cliente cliente, ModalidadEntrega modalidad,
                  EstadoPedido estado, ServicioDomicilio servicioDomicilio, double distanciaKm) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = estado;
        this.detalles = new ArrayList<>();
        this.servicioDomicilio = servicioDomicilio;
        this.distanciaKm = distanciaKm;
    }

    public void agregarDetalle(Producto producto, int cantidad) {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden agregar productos a un pedido PENDIENTE");
        }
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularValorProductos() {
        double total = 0;
        for (DetallePedido d : detalles) {
            total += d.calcularSubtotal();
        }
        return total;
    }

    public boolean domicilioAceptado() {
        return modalidad == ModalidadEntrega.DOMICILIO
                && servicioDomicilio != null
                && servicioDomicilio.aceptaServicio(distanciaKm);
    }

    public double calcularCostoDomicilio() {
        return domicilioAceptado() ? servicioDomicilio.calcularCosto(distanciaKm) : 0;
    }

    public double calcularValorTotal() {
        return calcularValorProductos() + calcularCostoDomicilio();
    }

    public void actualizarEstado(EstadoPedido nuevoEstado) {
        boolean valido = switch (estado) {
            case PENDIENTE -> nuevoEstado == EstadoPedido.CONFIRMADO || nuevoEstado == EstadoPedido.CANCELADO;
            case CONFIRMADO -> nuevoEstado == EstadoPedido.ENTREGADO || nuevoEstado == EstadoPedido.CANCELADO;
            default -> false;
        };
        if (!valido) {
            throw new IllegalStateException("No se puede pasar de " + estado + " a " + nuevoEstado);
        }
        estado = nuevoEstado;
    }

    public boolean esVentaValida() {
        return estado == EstadoPedido.CONFIRMADO || estado == EstadoPedido.ENTREGADO;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ModalidadEntrega getModalidad() {
        return modalidad;
    }
    public void setModalidad(ModalidadEntrega modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public ServicioDomicilio getServicioDomicilio() {
        return servicioDomicilio;
    }
    public void setServicioDomicilio(ServicioDomicilio servicioDomicilio) {
        this.servicioDomicilio = servicioDomicilio;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }
    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido %s | %s | Cliente: %s | %s | Estado: %s%n",
                codigo, fecha, cliente != null ? cliente.getNombre() : "-", modalidad, estado));
        for (DetallePedido d : detalles) {
            sb.append("   - ").append(d).append(System.lineSeparator());
        }
        sb.append(String.format("   Productos: $%,.0f", calcularValorProductos()));
        if (modalidad == ModalidadEntrega.DOMICILIO) {
            String empresa = servicioDomicilio != null ? servicioDomicilio.getNombre() : "sin empresa";
            sb.append(String.format(" | Domicilio (%s, %.1f km): %s", empresa, distanciaKm,
                    domicilioAceptado() ? String.format("$%,.0f", calcularCostoDomicilio()) : "NO ACEPTADO"));
        }
        sb.append(String.format(" | TOTAL: $%,.0f", calcularValorTotal()));
        return sb.toString();
    }
}
