import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nombre;
    private String documento;
    private String telefono;
    private String correo;
    private String direccion;
    private ArrayList<Pedido> historial;

    public Cliente(String nombre, String documento, String telefono, String correo, String direccion) {
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.historial = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido != null && !historial.contains(pedido)) {
            historial.add(pedido);
        }
    }

    public List<Pedido> consultarHistorial() {
        return historial;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return String.format("%s - %s | Tel: %s | %s | %s | Pedidos: %d",
                documento, nombre, telefono, correo, direccion, historial.size());
    }
}
