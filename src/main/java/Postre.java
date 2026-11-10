public class Postre extends Producto {

    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion, double precioBase,
                  int tiempoPreparacion, double pesoGramos,
                  double temperaturaAlmacenamiento, boolean requiereRefrigeracion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.15;
        if (requiereRefrigeracion) {
            precio += getPrecioBase() * 0.05;
        }
        return precio;
    }

    @Override
    public boolean cumpleCalidad() {
        boolean pesoOk = pesoGramos >= 80 && pesoGramos <= 500;
        boolean temperaturaOk = requiereRefrigeracion
                ? temperaturaAlmacenamiento >= 0 && temperaturaAlmacenamiento <= 6
                : temperaturaAlmacenamiento >= 15 && temperaturaAlmacenamiento <= 30;
        return pesoOk && temperaturaOk;
    }

    public double getPesoGramos() { return pesoGramos; }
    public void setPesoGramos(double pesoGramos) { this.pesoGramos = pesoGramos; }

    public double getTemperaturaAlmacenamiento() { return temperaturaAlmacenamiento; }
    public void setTemperaturaAlmacenamiento(double t) { this.temperaturaAlmacenamiento = t; }

    public boolean isRequiereRefrigeracion() { return requiereRefrigeracion; }
    public void setRequiereRefrigeracion(boolean r) { this.requiereRefrigeracion = r; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %.0f g, %.1f °C, %s",
                pesoGramos, temperaturaAlmacenamiento,
                requiereRefrigeracion ? "refrigerado" : "sin refrigeración");
    }
}
