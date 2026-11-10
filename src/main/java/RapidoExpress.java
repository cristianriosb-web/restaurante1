public class RapidoExpress implements ServicioDomicilio {

    private double tarifaBase;
    private double valorPorKm;
    private double distanciaMaxima;

    public RapidoExpress(double tarifaBase, double valorPorKm, double distanciaMaxima) {
        this.tarifaBase = tarifaBase;
        this.valorPorKm = valorPorKm;
        this.distanciaMaxima = distanciaMaxima;
    }

    @Override
    public String getNombre() {
        return "RápidoExpress";
    }

    @Override
    public boolean aceptaServicio(double km) {
        return km > 0 && km <= distanciaMaxima;
    }

    @Override
    public double calcularCosto(double km) {
        if (!aceptaServicio(km)) {
            return 0;
        }
        return tarifaBase + valorPorKm * km;
    }

    public double getTarifaBase() { return tarifaBase; }
    public void setTarifaBase(double tarifaBase) { this.tarifaBase = tarifaBase; }

    public double getValorPorKm() { return valorPorKm; }
    public void setValorPorKm(double valorPorKm) { this.valorPorKm = valorPorKm; }

    public double getDistanciaMaxima() { return distanciaMaxima; }
    public void setDistanciaMaxima(double distanciaMaxima) { this.distanciaMaxima = distanciaMaxima; }

    @Override
    public String toString() {
        return getNombre() + " (base $" + String.format("%,.0f", tarifaBase)
                + " + $" + String.format("%,.0f", valorPorKm) + "/km, máx. "
                + distanciaMaxima + " km)";
    }
}
