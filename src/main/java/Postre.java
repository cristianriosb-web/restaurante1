public class Postre extends Producto {
    private double pesoGramos;
    private double temperaturaAlmacenamiento;
    private boolean requiereRefrigeracion;

    public Postre(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, double temperaturaAlmacenamiento, boolean requiereRefrigeracion) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public double getTemperaturaAlmacenamiento() {
        return temperaturaAlmacenamiento;
    }

    public void setTemperaturaAlmacenamiento(double temperaturaAlmacenamiento) {
        this.temperaturaAlmacenamiento = temperaturaAlmacenamiento;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }
    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase()*1.15;
        if (requiereRefrigeracion) {
            precio+=(getPrecioBase()*1.05);
        }
        return precio;
    }
    @Override
    public boolean cumpleCalidad() {
        if(pesoGramos<=500 && pesoGramos>=80 && ((requiereRefrigeracion && temperaturaAlmacenamiento<=6 && temperaturaAlmacenamiento>=0)|| (!requiereRefrigeracion && (temperaturaAlmacenamiento>=15 && temperaturaAlmacenamiento<=30)))) {
            return true;
        }else{
            return false;
        }
    }
}
