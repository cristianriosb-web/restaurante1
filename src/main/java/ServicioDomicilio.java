public interface ServicioDomicilio {

    String getNombre();

    boolean aceptaServicio(double km);

    double calcularCosto(double km);
}
