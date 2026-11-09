public class Bebida extends Producto {
    private double volumenMl;
    private double temperaturaServicio;
    private boolean esFria;
    private boolean preparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double volumenMl, double temperaturaServicio, boolean esFria, boolean preparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.esFria = esFria;
        this.preparacionEspecial = preparacionEspecial;
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(double volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public boolean isEsFria() {
        return esFria;
    }

    public void setEsFria(boolean esFria) {
        this.esFria = esFria;
    }

    public boolean isPreparacionEspecial() {
        return preparacionEspecial;
    }

    public void setPreparacionEspecial(boolean preparacionEspecial) {
        this.preparacionEspecial = preparacionEspecial;
    }

    @Override
    public double calcularPrecioVenta() {
      double precio= getPrecioBase()*1.1;
      if(preparacionEspecial){
          precio+=3000;
      }
      return precio;
    }

    @Override
    public boolean cumpleCalidad() {
        if((volumenMl>=150 && volumenMl<=1500) && ((esFria && temperaturaServicio<=10 && temperaturaServicio>=0)||(!esFria && (temperaturaServicio>=55 && temperaturaServicio<=85)))) {
            return true;
        }else{
            return false;
        }
    }
}

