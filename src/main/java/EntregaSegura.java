public class EntregaSegura {
    private double tarifaBase;
    private double valorPorKm;
    private double distanciaMaxima;
    private double recargoAdicional;
    private double umbralRecargoKm;

    public EntregaSegura(double tarifaBase, double valorPorKm, double distanciaMaxima, double recargoAdicional, double umbralRecargoKm) {
        this.tarifaBase = tarifaBase;
        this.valorPorKm = valorPorKm;
        this.distanciaMaxima = distanciaMaxima;
        this.recargoAdicional = recargoAdicional;
        this.umbralRecargoKm = umbralRecargoKm;
    }

    public double getUmbralRecargoKm() {
        return umbralRecargoKm;
    }

    public void setUmbralRecargoKm(double umbralRecargoKm) {
        this.umbralRecargoKm = umbralRecargoKm;
    }

    public double getRecargoAdicional() {
        return recargoAdicional;
    }

    public void setRecargoAdicional(double recargoAdicional) {
        this.recargoAdicional = recargoAdicional;
    }

    public double getDistanciaMaxima() {
        return distanciaMaxima;
    }

    public void setDistanciaMaxima(double distanciaMaxima) {
        this.distanciaMaxima = distanciaMaxima;
    }

    public double getValorPorKm() {
        return valorPorKm;
    }

    public void setValorPorKm(double valorPorKm) {
        this.valorPorKm = valorPorKm;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    public boolean aceptarServicio(double distancia){
        return distancia<=distanciaMaxima;
    }
    public double calcularCosto(double distancia){
        double precio=tarifaBase;
        if(aceptarServicio(distancia)){
            precio+=valorPorKm*distancia;
        }
        return precio;
    }
}

