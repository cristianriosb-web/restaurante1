public class RapidoExpress {
    private double tarifaBase;
    private double valorPorKm;
    private double distanciaMaxima;

    public RapidoExpress(double tarifaBase, double valorPorKm, double distanciaMaxima) {
        this.tarifaBase = tarifaBase;
        this.valorPorKm = valorPorKm;
        this.distanciaMaxima = distanciaMaxima;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getValorPorKm() {
        return valorPorKm;
    }

    public void setValorPorKm(double valorPorKm) {
        this.valorPorKm = valorPorKm;
    }

    public double getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public void setDistanciaMaxima(double distanciaMaxima) {
        this.distanciaMaxima = distanciaMaxima;
    }
    public boolean aceptarServicio(double distancia) {
        return distancia <= distanciaMaxima;
    }
    public double calcularCosto(double distancia){
        return tarifaBase+valorPorKm*distancia;
    }
}
