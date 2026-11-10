
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Restaurante restaurante =
            new Restaurante("Sabor & Tradición", "900123456-7", "Cra. 14 #20-30, Armenia, Quindío");

    private static final ServicioDomicilio RAPIDO_EXPRESS = new RapidoExpress(5000, 1500, 12);
    private static final ServicioDomicilio ENTREGA_SEGURA = new EntregaSegura(7000, 1000, 20, 3000, 5);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> registrarCliente();
                case 3 -> verMenu();
                case 4 -> verCalidad();
                case 5 -> crearPedido();
                case 6 -> cotizarDomicilio();
                case 7 -> confirmarPedido();
                case 8 -> verPedidosCliente();
                case 9 -> cambiarEstado();
                case 10 -> reportes();
                case 11 -> cargarDatosEjemplo();
                case 0 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n========== " + restaurante.getNombre().toUpperCase() + " ==========");
        System.out.println(" 1. Registrar producto");
        System.out.println(" 2. Registrar cliente");
        System.out.println(" 3. Ver menú (información y precio de venta)");
        System.out.println(" 4. Ver productos aprobados / rechazados por calidad");
        System.out.println(" 5. Crear pedido (queda PENDIENTE)");
        System.out.println(" 6. Cotizar domicilio (RápidoExpress vs EntregaSegura)");
        System.out.println(" 7. Confirmar pedido");
        System.out.println(" 8. Ver pedidos de un cliente");
        System.out.println(" 9. Consultar / cambiar estado de un pedido");
        System.out.println("10. Reportes de ventas");
        System.out.println("11. Cargar datos de ejemplo");
        System.out.println(" 0. Salir");
    }

    private static void registrarProducto() {
        System.out.println("Tipo de producto: 1. Plato fuerte  2. Bebida  3. Postre");
        int tipo = leerEntero("Tipo: ");
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo no válido.");
            return;
        }
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        double precioBase = leerDouble("Precio base: ");
        int tiempo = leerEntero("Tiempo de preparación (min): ");

        Producto producto;
        switch (tipo) {
            case 1 -> {
                double peso = leerDouble("Peso (g): ");
                TipoPreparacion tp = leerTipoPreparacion();
                boolean coccion = leerSiNo("¿Cocción aprobada? (s/n): ");
                producto = new PlatoFuerte(codigo, nombre, descripcion, precioBase, tiempo, peso, tp, coccion);
            }
            case 2 -> {
                double volumen = leerDouble("Volumen (ml): ");
                double temp = leerDouble("Temperatura de servicio (°C): ");
                boolean fria = leerSiNo("¿Es fría? (s/n): ");
                boolean especial = leerSiNo("¿Preparación especial? (s/n): ");
                producto = new Bebida(codigo, nombre, descripcion, precioBase, tiempo, volumen, temp, fria, especial);
            }
            default -> {
                double peso = leerDouble("Peso (g): ");
                double temp = leerDouble("Temperatura de almacenamiento (°C): ");
                boolean refri = leerSiNo("¿Requiere refrigeración? (s/n): ");
                producto = new Postre(codigo, nombre, descripcion, precioBase, tiempo, peso, temp, refri);
            }
        }
        if (restaurante.registrarProducto(producto)) {
            System.out.println("Producto registrado: " + producto);
        } else {
            System.out.println("No se registró: ya existe un producto con el código " + codigo);
        }
    }

    private static void registrarCliente() {
        String documento = leerTexto("Documento: ");
        String nombre = leerTexto("Nombre completo: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        String direccion = leerTexto("Dirección: ");
        Cliente c = new Cliente(nombre, documento, telefono, correo, direccion);
        if (restaurante.registrarCliente(c)) {
            System.out.println("Cliente registrado: " + c);
        } else {
            System.out.println("No se registró: ya existe un cliente con el documento " + documento);
        }
    }

    private static void verMenu() {
        if (restaurante.getMenu().isEmpty()) {
            System.out.println("El menú está vacío.");
            return;
        }
        System.out.println("--- MENÚ ---");
        for (Producto p : restaurante.getMenu()) {
            System.out.println(p);
        }
    }

    private static void verCalidad() {
        System.out.println("--- PRODUCTOS DISPONIBLES (cumplen calidad) ---");
        List<Producto> disponibles = restaurante.consultarProductosDisponibles();
        if (disponibles.isEmpty()) System.out.println("(ninguno)");
        disponibles.forEach(p -> System.out.printf("[%s] %s - $%,.0f%n",
                p.getCodigo(), p.getNombre(), p.calcularPrecioVenta()));

        System.out.println("--- PRODUCTOS RECHAZADOS ---");
        List<Producto> rechazados = restaurante.consultarProductosRechazados();
        if (rechazados.isEmpty()) System.out.println("(ninguno)");
        rechazados.forEach(System.out::println);
    }

    private static void crearPedido() {
        String codigo = leerTexto("Código del pedido: ");
        if (restaurante.buscarPedido(codigo) != null) {
            System.out.println("Ya existe un pedido con ese código.");
            return;
        }
        Cliente cliente = restaurante.buscarCliente(leerTexto("Documento del cliente: "));
        if (cliente == null) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        System.out.println("Modalidad: 1. Restaurante  2. Domicilio");
        ModalidadEntrega modalidad = leerEntero("Modalidad: ") == 2
                ? ModalidadEntrega.DOMICILIO : ModalidadEntrega.RESTAURANTE;

        ServicioDomicilio servicio = null;
        double km = 0;
        if (modalidad == ModalidadEntrega.DOMICILIO) {
            km = leerDouble("Distancia del recorrido (km): ");
            servicio = elegirEmpresa(km);
        }

        Pedido pedido = new Pedido(codigo, LocalDate.now(), cliente, modalidad,
                EstadoPedido.PENDIENTE, servicio, km);

        System.out.println("Agregue productos (escriba FIN en el código para terminar).");
        verCalidad();
        while (true) {
            String cod = leerTexto("Código del producto: ");
            if (cod.equalsIgnoreCase("FIN")) break;
            Producto p = restaurante.buscarProducto(cod);
            if (p == null) {
                System.out.println("Ese producto no pertenece al menú.");
                continue;
            }
            int cantidad = leerEntero("Cantidad: ");
            pedido.agregarDetalle(p, cantidad);
            if (cantidad <= 0) {
                System.out.println("Advertencia: cantidad no válida, el pedido no podrá confirmarse.");
            }
            if (!p.cumpleCalidad()) {
                System.out.println("Advertencia: el producto no cumple calidad, el pedido no podrá confirmarse.");
            }
        }
        restaurante.registrarPedido(pedido);
        System.out.println("Pedido registrado en estado PENDIENTE:");
        System.out.println(pedido);
    }

    private static ServicioDomicilio elegirEmpresa(double km) {
        mostrarCotizacion(km);
        System.out.println("Empresa: 1. RápidoExpress  2. EntregaSegura");
        ServicioDomicilio servicio = leerEntero("Empresa: ") == 2 ? ENTREGA_SEGURA : RAPIDO_EXPRESS;
        if (servicio.aceptaServicio(km)) {
            System.out.printf("%s aceptó el servicio. Costo: $%,.0f%n", servicio.getNombre(), servicio.calcularCosto(km));
        } else {
            System.out.println(servicio.getNombre() + " NO acepta ese recorrido; el pedido no podrá confirmarse.");
        }
        return servicio;
    }

    private static void cotizarDomicilio() {
        double km = leerDouble("Distancia del recorrido (km): ");
        mostrarCotizacion(km);
    }

    private static void mostrarCotizacion(double km) {
        for (ServicioDomicilio s : new ServicioDomicilio[]{RAPIDO_EXPRESS, ENTREGA_SEGURA}) {
            if (s.aceptaServicio(km)) {
                System.out.printf("  %-14s acepta  -> $%,.0f%n", s.getNombre(), s.calcularCosto(km));
            } else {
                System.out.printf("  %-14s NO acepta %.1f km%n", s.getNombre(), km);
            }
        }
    }

    private static void confirmarPedido() {
        String codigo = leerTexto("Código del pedido: ");
        String motivo = restaurante.verificarPedido(codigo);
        if (restaurante.confirmarPedido(codigo)) {
            System.out.println("Pedido CONFIRMADO:");
            System.out.println(restaurante.buscarPedido(codigo));
        } else {
            System.out.println("No se pudo confirmar: " + motivo);
        }
    }

    private static void verPedidosCliente() {
        String doc = leerTexto("Documento del cliente: ");
        Cliente c = restaurante.buscarCliente(doc);
        if (c == null) {
            System.out.println("El cliente no está registrado.");
            return;
        }
        List<Pedido> historial = restaurante.consultarPedidosCliente(doc);
        System.out.println("Historial de " + c.getNombre() + ":");
        if (historial.isEmpty()) {
            System.out.println("(sin pedidos confirmados)");
        }
        historial.forEach(System.out::println);

        // También se muestran los pedidos pendientes del cliente que aún no están en el historial
        for (Pedido p : restaurante.getPedido()) {
            if (p.getCliente() == c && !historial.contains(p)) {
                System.out.println("(no confirmado) " + p);
            }
        }
    }

    private static void cambiarEstado() {
        Pedido p = restaurante.buscarPedido(leerTexto("Código del pedido: "));
        if (p == null) {
            System.out.println("El pedido no existe.");
            return;
        }
        System.out.println("Estado actual: " + p.getEstado());
        System.out.println("Nuevo estado: 1. Entregado  2. Cancelado  0. Volver");
        System.out.println("(para confirmar use la opción 7, que valida el pedido)");
        int op = leerEntero("Opción: ");
        if (op != 1 && op != 2) return;
        try {
            p.actualizarEstado(op == 1 ? EstadoPedido.ENTREGADO : EstadoPedido.CANCELADO);
            System.out.println("Estado actualizado a " + p.getEstado());
        } catch (IllegalStateException e) {
            System.out.println("No permitido: " + e.getMessage());
        }
    }

    private static void reportes() {
        System.out.println("--- REPORTE DE VENTAS (pedidos confirmados y entregados) ---");
        System.out.printf("Ingresos del restaurante:        $%,.0f%n", restaurante.calcularIngresosRestaurante());
        System.out.printf("Cobros por domicilios (aparte): $%,.0f%n", restaurante.calcularIngresosDomicilios());
        Producto top = restaurante.obtenerProductoMasVendido();
        if (top == null) {
            System.out.println("Producto más vendido: (sin ventas)");
        } else {
            System.out.printf("Producto más vendido: %s (%d unidades)%n",
                    top.getNombre(), restaurante.calcularUnidadesVendidas(top.getCodigo()));
        }
        String categoria = restaurante.obtenerCategoriaMayorIngreso();
        System.out.println("Categoría con mayores ingresos: " + (categoria == null ? "(sin ventas)" : categoria));
    }

    private static void cargarDatosEjemplo() {
        restaurante.registrarProducto(new PlatoFuerte("P01", "Bandeja paisa", "Frijoles, arroz, chicharrón",
                25000, 30, 800, TipoPreparacion.FRITO, true));
        restaurante.registrarProducto(new PlatoFuerte("P02", "Trucha a la plancha", "Trucha con patacón",
                28000, 25, 450, TipoPreparacion.PLANCHA, true));
        restaurante.registrarProducto(new PlatoFuerte("P03", "Lechona", "Porción de lechona",
                22000, 90, 950, TipoPreparacion.HORNEADO, false)); // rechazada
        restaurante.registrarProducto(new Bebida("B01", "Limonada de coco", "Limonada cremosa",
                7000, 5, 400, 4, true, true));
        restaurante.registrarProducto(new Bebida("B02", "Café del Quindío", "Café de origen",
                4000, 3, 250, 70, false, false));
        restaurante.registrarProducto(new Postre("D01", "Postre de natas", "Tradicional",
                8000, 15, 200, 4, true));
        restaurante.registrarProducto(new Postre("D02", "Brownie", "Brownie de chocolate",
                6000, 10, 600, 20, false)); // rechazado
        restaurante.registrarCliente(new Cliente("Ana Gómez", "1001", "3001234567",
                "ana@correo.com", "Calle 10 #5-20"));
        restaurante.registrarCliente(new Cliente("Luis Pérez", "1002", "3109876543",
                "luis@correo.com", "Barrio La Castellana"));
        System.out.println("Datos de ejemplo cargados: 7 productos (2 rechazados) y 2 clientes (1001, 1002).");
    }

    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
        } while (texto.isEmpty());
        return texto;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static boolean leerSiNo(String mensaje) {
        while (true) {
            String r = leerTexto(mensaje).toLowerCase();
            if (r.startsWith("s")) return true;
            if (r.startsWith("n")) return false;
            System.out.println("Responda s o n.");
        }
    }

    private static TipoPreparacion leerTipoPreparacion() {
        TipoPreparacion[] tipos = TipoPreparacion.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        int op;
        do {
            op = leerEntero("Tipo de preparación: ");
        } while (op < 1 || op > tipos.length);
        return tipos[op - 1];
    }
}
