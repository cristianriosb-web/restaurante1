public class PlatoFuerte extends Producto{
    private double pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    public PlatoFuerte(double pesoGramos, TipoPreparacion tipoPreparacion, boolean coccionAprobada, double precioBase, int tiempoPreparacion, String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean isCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprovada) {
        this.coccionAprobada = coccionAprovada;
    }
    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.2;
    }
    @Override
    public boolean cumpleCalidad() {
       if(pesoGramos<=900 && pesoGramos>=200&& getTiempoPreparacion()>=10 && getTiempoPreparacion()<=60 && coccionAprobada) {
           return true;
       }else{
           return false;
       }
    }
}
