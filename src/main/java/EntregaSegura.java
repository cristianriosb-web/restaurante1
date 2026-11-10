public class EntregaSegura implements ServicioDomicilio {

    private double tarifaBase;
    private double valorPorKm;
    private double distanciaMaxima;
    private double recargoAdicional;
    private double umbralRecargoKm;

    public EntregaSegura(double tarifaBase, double valorPorKm, double distanciaMaxima,
                         double recargoAdicional, double umbralRecargoKm) {
        this.tarifaBase = tarifaBase;
        this.valorPorKm = valorPorKm;
        this.distanciaMaxima = distanciaMaxima;
        this.recargoAdicional = recargoAdicional;
        this.umbralRecargoKm = umbralRecargoKm;
    }

    @Override
    public String getNombre() {
        return "EntregaSegura";
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
        double costo = tarifaBase + valorPorKm * km;
        if (km > umbralRecargoKm) {
            costo += recargoAdicional;
        }
        return costo;
    }

    public double getTarifaBase() { return tarifaBase; }
    public void setTarifaBase(double tarifaBase) { this.tarifaBase = tarifaBase; }

    public double getValorPorKm() { return valorPorKm; }
    public void setValorPorKm(double valorPorKm) { this.valorPorKm = valorPorKm; }

    public double getDistanciaMaxima() { return distanciaMaxima; }
    public void setDistanciaMaxima(double distanciaMaxima) { this.distanciaMaxima = distanciaMaxima; }

    public double getRecargoAdicional() { return recargoAdicional; }
    public void setRecargoAdicional(double recargoAdicional) { this.recargoAdicional = recargoAdicional; }

    public double getUmbralRecargoKm() { return umbralRecargoKm; }
    public void setUmbralRecargoKm(double umbralRecargoKm) { this.umbralRecargoKm = umbralRecargoKm; }

    @Override
    public String toString() {
        return getNombre() + " (base $" + String.format("%,.0f", tarifaBase)
                + " + $" + String.format("%,.0f", valorPorKm) + "/km, +$"
                + String.format("%,.0f", recargoAdicional) + " si supera " + umbralRecargoKm
                + " km, máx. " + distanciaMaxima + " km)";
    }
}
