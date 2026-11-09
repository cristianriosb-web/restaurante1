import java.util.Scanner;
public class Main {
    public static void main (String []args){
//Menu interactivo
    Scanner sc = new Scanner(System.in);
    Restaurante restaurante= new Restaurante("Sabor & Tradición","900.123.456-8","Armenia,quindio cra 10 #10-67");
    int opcion;
    do{
        System.out.println("=====Menu Restaurante=====");

        System.out.println("-----Gestion de Productos-----");
        System.out.println("1. Registrar Producto (Plato Fuerte, Postre o Bebida)");
        System.out.println("2. Verificar calidad de Productos (Aprobado o Rechazado)");
        System.out.println("3. Consultar Productos disponibles");

        System.out.println("-----Gestion de clientes-----");
        System.out.println("4.Registrar nuevo cliente");
        System.out.println("5.consultar historial de pedidos de un cliente");

        System.out.println("-----Gestion de pedidos-----");
        System.out.println("6. Registrar nuevo pedido");
        System.out.println("7. Confirmar pedido pendiente");
        System.out.println("8. Actualizar estado de un pedido");
        System.out.println("9. Consultar estado e información de un pedido");

        System.out.println("-----Reportes y Estadisticas-----");
        System.out.println("10. Generar reporte de ingresos totales");
        System.out.println("11. ver producto más vendido y categoría con mayor ingreso");

        System.out.println("-----------------------------------------------");
        System.out.println("0. Salir");

        opcion=sc.nextInt();
        sc.nextLine();
        switch (opcion){
            case 1:
                System.out.println("-----Registrar Producto-----");
                System.out.println("Ingrese el codigo del producto");
                System.out.println("Ingrese el nombre del producto");
                System.out.println("Ingrese la descripcion del producto");
                System.out.println();
        }

    }
    }
}
