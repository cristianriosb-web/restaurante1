import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Restaurante restaurante= new Restaurante("Sabor & Tradición","900.123.456-8","Armenia,quindio cra 10 #10-67");
        int opcion;

        do {
            System.out.println("===== MENÚ RESTAURANTE =====");
            System.out.println("----- Gestión de productos -----");
            System.out.println("1. Registrar producto");
            System.out.println("2. Verificar calidad de productos");
            System.out.println("3. Consultar productos disponibles");

            System.out.println("----- Gestión de clientes -----");
            System.out.println("4. Registrar cliente");
            System.out.println("5. Consultar historial de un cliente");

            System.out.println("----- Gestión de pedidos -----");
            System.out.println("6. Registrar pedido");
            System.out.println("7. Confirmar pedido");
            System.out.println("8. Actualizar estado de pedido");
            System.out.println("9. Consultar pedido");

            System.out.println("----- Reportes -----");
            System.out.println("10. Consultar ingresos");
            System.out.println("11. Producto más vendido y categoría con mayor ingreso");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    System.out.println("----- Registrar producto -----");
                    System.out.println("1. Plato fuerte");
                    System.out.println("2. Bebida");
                    System.out.println("3. Postre");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    if (tipo < 1 || tipo > 3) {
                        System.out.println("Tipo de producto no válido.");
                        break;
                    }

                    System.out.print("Código: ");
                    String codigo = sc.nextLine();

                    if (restaurante.buscarProducto(codigo) != null) {
                        System.out.println("Ya existe un producto con ese código.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();

                    System.out.print("Precio base: ");
                    double precioBase = sc.nextDouble();

                    System.out.print("Tiempo de preparación en minutos: ");
                    int tiempo = sc.nextInt();
                    sc.nextLine();

                    if (precioBase < 0 || tiempo < 0) {
                        System.out.println("El precio y el tiempo no pueden ser negativos.");
                        break;
                    }

                    Producto producto = null;

                    if (tipo == 1) {
                        System.out.print("Peso en gramos: ");
                        double peso = sc.nextDouble();

                        System.out.println("Tipo de preparación:");
                        System.out.println("1. Asado");
                        System.out.println("2. Frito");
                        System.out.println("3. Horneado");
                        System.out.println("4. A la plancha");
                        int preparacion = sc.nextInt();
                        sc.nextLine();

                        TipoPreparacion tipoPreparacion;

                        if (preparacion == 1) {
                            tipoPreparacion = TipoPreparacion.ASADO;
                        } else if (preparacion == 2) {
                            tipoPreparacion = TipoPreparacion.FRITO;
                        } else if (preparacion == 3) {
                            tipoPreparacion = TipoPreparacion.HORNEADO;
                        } else if (preparacion == 4) {
                            tipoPreparacion = TipoPreparacion.PLANCHA;
                        } else {
                            System.out.println("Preparación no válida.");
                            break;
                        }

                        System.out.print("¿Cocción aprobada? (s/n): ");
                        boolean coccion = sc.nextLine().equalsIgnoreCase("s");

                        producto = new PlatoFuerte(
                                peso, tipoPreparacion, coccion,
                                precioBase, tiempo, codigo, nombre, descripcion);

                    } else if (tipo == 2) {
                        System.out.print("Volumen en mililitros: ");
                        double volumen = sc.nextDouble();

                        System.out.print("Temperatura de servicio: ");
                        double temperatura = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("¿Es fría? (s/n): ");
                        boolean fria = sc.nextLine().equalsIgnoreCase("s");

                        System.out.print("¿Tiene preparación especial?(s/n): ");
                        boolean especial = sc.nextLine().equalsIgnoreCase("s");

                        producto = new Bebida(
                                codigo, nombre, descripcion, precioBase, tiempo,
                                volumen, temperatura, fria, especial);

                    } else {
                        System.out.print("Peso en gramos: ");
                        double peso = sc.nextDouble();

                        System.out.print("Temperatura de almacenamiento: ");
                        double temperatura = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("¿Requiere refrigeración? (s/n): ");
                        boolean refrigeracion = sc.nextLine().equalsIgnoreCase("s");

                        producto = new Postre(
                                codigo, nombre, descripcion, precioBase, tiempo,
                                peso, temperatura, refrigeracion);
                    }

                    if (restaurante.registrarProducto(producto)) {
                        System.out.println("Producto registrado.");
                        System.out.println("Precio de venta: " + producto.calcularPrecioVenta());

                        if (producto.cumpleCalidad()) {
                            System.out.println("Calidad: aprobado.");
                        } else {
                            System.out.println("Calidad: rechazado.");
                        }
                    } else {
                        System.out.println("No se pudo registrar el producto.");
                    }
                    break;
                }

                case 2: {
                    System.out.println("----- Productos aprobados -----");
                    for (Producto producto : restaurante.consultarProductosDisponibles()) {
                        System.out.println(producto);
                    }

                    System.out.println("----- Productos rechazados -----");
                    for (Producto producto : restaurante.consultarProductosRechazados()) {
                        System.out.println(producto);
                    }
                    break;
                }

                case 3: {
                    System.out.println("----- Productos disponibles -----");
                    for (Producto producto : restaurante.consultarProductosDisponibles()) {
                        System.out.println(producto);
                    }
                    break;
                }

                case 4: {
                    System.out.println("----- Registrar cliente -----");

                    System.out.print("Documento: ");
                    String documento = sc.nextLine();

                    System.out.print("Nombre completo: ");
                    String nombre = sc.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();

                    System.out.print("Correo: ");
                    String correo = sc.nextLine();

                    System.out.print("Dirección: ");
                    String direccion = sc.nextLine();

                    Cliente cliente = new Cliente(
                            nombre, documento, telefono, correo, direccion);

                    if (restaurante.registrarCliente(cliente)) {
                        System.out.println("Cliente registrado.");
                    } else {
                        System.out.println("Ya existe un cliente con ese documento.");
                    }
                    break;
                }

                case 5: {
                    System.out.print("Documento del cliente: ");
                    String documento = sc.nextLine();

                    Cliente cliente = restaurante.buscarCliente(documento);

                    if (cliente == null) {
                        System.out.println("Cliente no encontrado.");
                    } else if (cliente.consultarHistorial().isEmpty()) {
                        System.out.println("El cliente no tiene pedidos en su historial.");
                    } else {
                        for (Pedido pedido : restaurante.consultarPedidosCliente(documento)) {
                            System.out.println(pedido);
                        }
                    }
                    break;
                }

                case 6: {
                    System.out.println("----- Registrar pedido -----");

                    System.out.print("Código del pedido: ");
                    String codigo = sc.nextLine();

                    if (restaurante.buscarPedido(codigo) != null) {
                        System.out.println("Ya existe un pedido con ese código.");
                        break;
                    }

                    System.out.print("Documento del cliente: ");
                    String documento = sc.nextLine();

                    Cliente cliente = restaurante.buscarCliente(documento);

                    if (cliente == null) {
                        System.out.println("El cliente no está registrado.");
                        break;
                    }

                    System.out.println("Modalidad:");
                    System.out.println("1. Consumo en restaurante");
                    System.out.println("2. Domicilio");
                    int opcionModalidad = sc.nextInt();
                    sc.nextLine();

                    ModalidadEntrega modalidad;
                    ServicioDomicilio servicio = null;
                    double distancia = 0;

                    if (opcionModalidad == 1) {
                        modalidad = ModalidadEntrega.RESTAURANTE;
                    } else if (opcionModalidad == 2) {
                        modalidad = ModalidadEntrega.DOMICILIO;

                        System.out.println("Empresa de domicilio:");
                        System.out.println("1. RápidoExpress");
                        System.out.println("2. EntregaSegura");
                        int empresa = sc.nextInt();
                        sc.nextLine();

                        if (empresa == 1) {
                            servicio = new RapidoExpress(5000, 1500, 12);
                        } else if (empresa == 2) {
                            servicio = new EntregaSegura(7000, 1000, 20, 3000, 5);
                        } else {
                            System.out.println("Empresa no válida.");
                            break;
                        }

                        System.out.print("Distancia en kilómetros: ");
                        distancia = sc.nextDouble();
                        sc.nextLine();

                        if (!servicio.aceptaServicio(distancia)) {
                            System.out.println("La empresa no acepta esa distancia.");
                            break;
                        }

                        System.out.println("Costo del domicilio: "
                                + servicio.calcularCosto(distancia));
                    } else {
                        System.out.println("Modalidad no válida.");
                        break;
                    }

                    Pedido pedido = new Pedido(
                            codigo, LocalDate.now(), cliente, modalidad,
                            EstadoPedido.PENDIENTE, servicio, distancia);

                    System.out.println("----- Productos disponibles -----");
                    for (Producto producto : restaurante.consultarProductosDisponibles()) {
                        System.out.println(producto);
                    }

                    String codigoProducto;

                    do {
                        System.out.print("Código del producto, o FIN para terminar: ");
                        codigoProducto = sc.nextLine();

                        if (!codigoProducto.equalsIgnoreCase("FIN")) {
                            Producto producto = restaurante.buscarProducto(codigoProducto);

                            if (producto == null) {
                                System.out.println("Producto no encontrado.");
                            } else if (!producto.cumpleCalidad()) {
                                System.out.println("El producto no cumple calidad.");
                            } else {
                                System.out.print("Cantidad: ");
                                int cantidad = sc.nextInt();
                                sc.nextLine();

                                if (cantidad > 0) {
                                    pedido.agregarDetalle(producto, cantidad);
                                    System.out.println("Producto agregado.");
                                } else {
                                    System.out.println("La cantidad debe ser mayor que cero.");
                                }
                            }
                        }
                    } while (!codigoProducto.equalsIgnoreCase("FIN"));

                    if (pedido.getDetalles().isEmpty()) {
                        System.out.println("No se registró el pedido porque está vacío.");
                    } else {
                        restaurante.registrarPedido(pedido);
                        System.out.println("Pedido registrado como pendiente.");
                        System.out.println(pedido);
                    }
                    break;
                }

                case 7: {
                    System.out.print("Código del pedido: ");
                    String codigo = sc.nextLine();

                    String error = restaurante.verificarPedido(codigo);

                    if (error != null) {
                        System.out.println("No se puede confirmar: " + error);
                    } else if (restaurante.confirmarPedido(codigo)) {
                        System.out.println("Pedido confirmado.");
                    }
                    break;
                }

                case 8: {
                    System.out.print("Código del pedido: ");
                    String codigo = sc.nextLine();

                    Pedido pedido = restaurante.buscarPedido(codigo);

                    if (pedido == null) {
                        System.out.println("Pedido no encontrado.");
                        break;
                    }

                    System.out.println("Estado actual: " + pedido.getEstado());
                    System.out.println("1. Marcar como entregado");
                    System.out.println("2. Cancelar");
                    int nuevoEstado = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (nuevoEstado == 1) {
                            pedido.actualizarEstado(EstadoPedido.ENTREGADO);
                            System.out.println("Pedido entregado.");
                        } else if (nuevoEstado == 2) {
                            pedido.actualizarEstado(EstadoPedido.CANCELADO);
                            System.out.println("Pedido cancelado.");
                        } else {
                            System.out.println("Opción no válida.");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 9: {
                    System.out.print("Código del pedido: ");
                    String codigo = sc.nextLine();

                    Pedido pedido = restaurante.buscarPedido(codigo);

                    if (pedido == null) {
                        System.out.println("Pedido no encontrado.");
                    } else {
                        System.out.println(pedido);
                    }
                    break;
                }

                case 10: {
                    System.out.println("Ingresos del restaurante: "
                            + restaurante.calcularIngresosRestaurante());
                    System.out.println("Valores cobrados por domicilios: "
                            + restaurante.calcularIngresosDomicilios());
                    break;
                }

                case 11: {
                    Producto masVendido = restaurante.obtenerProductoMasVendido();

                    if (masVendido == null) {
                        System.out.println("Todavía no hay ventas.");
                    } else {
                        System.out.println("Producto más vendido: " + masVendido.getNombre());
                        System.out.println("Unidades vendidas: "
                                + restaurante.calcularUnidadesVendidas(masVendido.getCodigo()));
                        System.out.println("Categoría con mayor ingreso: "
                                + restaurante.obtenerCategoriaMayorIngreso());
                    }
                    break;
                }

                case 0:
                    System.out.println("Saliendo del programa.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}