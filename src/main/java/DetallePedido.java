public class DetallePedido {

    private Producto producto;
    private int cantidad;

    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.calcularPrecioVenta() * cantidad;
    }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return String.format("%d x %s ($%,.0f c/u) = $%,.0f",
                cantidad, producto.getNombre(), producto.calcularPrecioVenta(), calcularSubtotal());
    }
}
