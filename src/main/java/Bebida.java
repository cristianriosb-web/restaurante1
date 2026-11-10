public class Bebida extends Producto {

    public static final double RECARGO_PREPARACION_ESPECIAL = 3000;

    private double volumenMl;
    private double temperaturaServicio;
    private boolean esFria;
    private boolean preparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase,
                  int tiempoPreparacion, double volumenMl, double temperaturaServicio,
                  boolean esFria, boolean preparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.esFria = esFria;
        this.preparacionEspecial = preparacionEspecial;
    }

    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.10;
        if (preparacionEspecial) {
            precio += RECARGO_PREPARACION_ESPECIAL;
        }
        return precio;
    }

    @Override
    public boolean cumpleCalidad() {
        boolean volumenOk = volumenMl >= 150 && volumenMl <= 1500;
        boolean temperaturaOk = esFria
                ? temperaturaServicio >= 0 && temperaturaServicio <= 10
                : temperaturaServicio >= 55 && temperaturaServicio <= 85;
        return volumenOk && temperaturaOk;
    }

    public double getVolumenMl() { return volumenMl; }
    public void setVolumenMl(double volumenMl) { this.volumenMl = volumenMl; }

    public double getTemperaturaServicio() { return temperaturaServicio; }
    public void setTemperaturaServicio(double temperaturaServicio) { this.temperaturaServicio = temperaturaServicio; }

    public boolean isEsFria() { return esFria; }
    public void setEsFria(boolean esFria) { this.esFria = esFria; }

    public boolean isPreparacionEspecial() { return preparacionEspecial; }
    public void setPreparacionEspecial(boolean preparacionEspecial) { this.preparacionEspecial = preparacionEspecial; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | %.0f ml, %.1f °C, %s%s",
                volumenMl, temperaturaServicio, esFria ? "fría" : "caliente",
                preparacionEspecial ? ", preparación especial" : "");
    }
}
