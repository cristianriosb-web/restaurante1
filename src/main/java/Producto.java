public abstract class Producto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int tiempoPreparacion; // minutos

    public Producto(String codigo, String nombre, String descripcion,
                    double precioBase, int tiempoPreparacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public Producto(double pesoGramos, String bandeja, String típica, int precioBase, int tiempoPreparacion, int i, TipoPreparacion tipoPreparacion, boolean b) {
    }

    public abstract double calcularPrecioVenta();

    public abstract boolean cumpleCalidad();

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }

    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(int tiempoPreparacion) { this.tiempoPreparacion = tiempoPreparacion; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s | Base: $%,.0f | Venta: $%,.0f | %d min | Calidad: %s",
                codigo, nombre, getClass().getSimpleName(), descripcion, precioBase,
                calcularPrecioVenta(), tiempoPreparacion, cumpleCalidad() ? "CUMPLE" : "RECHAZADO");
    }
}
