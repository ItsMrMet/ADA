package main;

import model.*;
import vista.*;
import dao.*;
import controlador.*;
import java.sql.Connection;
import java.util.*;
import static main.Conexion.conectarMySQL;
import static main.Conexion.desconectarMySQL;

public class Main {
    
    // Enum entidades
    public static enum entidad{
        ARTICULO, CLIENTE, DIRECCION, FABRICA, PEDIDO;
    }

    // Conexión a la base de datos.
    private static final Connection conexion = conectarMySQL();

    // Scanner.
    private static final Scanner entrada = new Scanner(System.in);

    // Entidad actual.
    private static entidad entidadActual;

    // Controladores.
    private static final ArticuloControlador articuloControlador = new ArticuloControlador(new ArticuloDAO(conexion), new ArticuloVista());
    private static final ClienteControlador clienteControlador = new ClienteControlador(new ClienteDAO(conexion), new ClienteVista());
    private static final DireccionControlador direccionControlador = new DireccionControlador(new DireccionDAO(conexion), new DireccionVista());
    private static final FabricaControlador fabricaControlador = new FabricaControlador(new FabricaDAO(conexion), new FabricaVista());
    private static final PedidoControlador pedidoControlador = new PedidoControlador(new PedidoDAO(conexion), new PedidoVista());
    private static final FuncionesEspecialesControlador funcionesEspecialesControlador = new FuncionesEspecialesControlador(new FuncionesEspecialesDAO(conexion), new PedidoVista(), new ClienteVista(), new ClienteDAO(conexion));

    // Métodos para obtener datos del usuario.
    
    // Método para obtener un número entero del usuario.
    public static int obtenerInt(String mensaje) {
        int num = 0;
        boolean validado = false;
        while(!validado) {
            System.out.print(mensaje);
            try {
                num = entrada.nextInt();
                validado = true;
            } catch (Exception e) {
                System.out.println("No se ha introducido un número entero.");
                entrada.nextLine();
            }
        }
        entrada.nextLine();
        return num;
    }
    
    // Método para obtener un número decimal del usuario.
    public static Double obtenerDouble(String mensaje) {
        while(true) {
            System.out.print(mensaje);
            try {
                return entrada.nextDouble();
            } catch (Exception e) {
                System.out.println("No se ha introducido un número válido.");
            }
        }
    }
    
    // Método para obtener una cadena de texto del usuario.
    public static String obtenerString(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    // Métodos para los menús de la aplicación.
    
    // Método para mostrar el menú de las entidades.
    public static int menuEntidades() {
        System.out.println("");
        System.out.println("¿Con qué entidad deseas trabajar?");
        System.out.println("1. ARTÍCULO.");
        System.out.println("2. CLIENTE.");
        System.out.println("3. DIRECCIÓN.");
        System.out.println("4. FÁBRICA.");
        System.out.println("5. PEDIDO.");
        System.out.println("6. FUNCIONES ESPECIALES.");
        System.out.println("7. Salir.");
        System.out.println("");
        return obtenerInt("Elige una opción: ");
    }
    
    // Método para mostrar el menú de los CRUD.
    public static int menuCRUD() {
        System.out.println("");
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. SELECT.");
        System.out.println("2. INSERT.");
        System.out.println("3. UPDATE.");
        System.out.println("4. DELETE.");
        System.out.println("5. Volver.");
        System.out.println("");
        return obtenerInt("Elige una opción: ");
    }
    
    // Método para mostrar las funcionalidades específicas.
    public static int menuFuncionesEspeciales() {
        System.out.println("");
        System.out.println("1. Pedidos totales realizados por un cliente y el importe en descuentos.");
        System.out.println("2. Eliminar fábricas de las que nunca se ha pedido ni un artículo.");
        System.out.println("3. Cantidad total de artículos incluidos en todos los pedidos de un año determinado.");
        System.out.println("4. Cliente con más pedidos realizados.");
        System.out.println("5. Pedido con más artículos y precio medio de los pedidos comprendidos entre dos fechas.");
        System.out.println("6. Volver.");
        System.out.println("");
        return obtenerInt("Elige una opción: ");
    }

    // Switches.

    // Método para el switch del menú entidades.
    public static void switchMenuEntidades(int opcion) {
        switch(opcion) {
            case 1 -> {
                entidadActual = entidad.ARTICULO;
                switchSubmenu(menuCRUD());
            }
            case 2 -> {
                entidadActual = entidad.CLIENTE;
                switchSubmenu(menuCRUD());
            }
            case 3 -> {
                entidadActual = entidad.DIRECCION;
                switchSubmenu(menuCRUD());
            }
            case 4 -> {
                entidadActual = entidad.FABRICA;
                switchSubmenu(menuCRUD());
            }
            case 5 -> {
                entidadActual = entidad.PEDIDO;
                switchSubmenu(menuCRUD());
            }
            case 6 -> {
                switchFuncionesEspeciales(menuFuncionesEspeciales());
            }
            case 7 -> {
                System.out.println("");
                desconectarMySQL();
                System.out.println("¡Gracias por usar mi aplicación!");
                System.exit(0);
            }
            default -> System.out.println("Opción elegida inválida.");
        }
    }

    // Método para el switch del submenú.
    public static void switchSubmenu(int opcion) {
        switch(opcion) {
            // SELECT
            case 1 -> {
                switch (entidadActual) {
                    case ARTICULO -> {
                        System.out.println("");
                        articuloControlador.mostrarArticulos();
                        }
                    case CLIENTE -> {
                        System.out.println("");
                        clienteControlador.mostrarClientes();
                        }
                    case DIRECCION -> {
                        System.out.println("");
                        direccionControlador.mostrarDirecciones();
                        }
                    case FABRICA -> {
                        System.out.println("");
                        fabricaControlador.mostrarFabricas();
                        }
                    case PEDIDO -> {
                        System.out.println("");
                        pedidoControlador.mostrarPedidos();
                        }
                    default -> {
                        System.out.println("Algo ha fallado.");
                        }
                }
            }
            // INSERT
            case 2 -> {
                switch (entidadActual) {
                    case ARTICULO -> {
                        System.out.println("");
                        String descripcion = obtenerString("Introduce la descripción del artículo: ");
                        double precio = obtenerDouble("Introduce el precio del artículo: ");
                        int existencias = obtenerInt("Introduce las existencias del artículo: ");
                        int idFabrica = obtenerInt("Introduce el id de la fábrica: ");
                        
                        Articulo articulo = new Articulo(descripcion, precio, existencias, idFabrica);
                        articuloControlador.insertarArticulo(articulo);
                        }
                    case CLIENTE -> {
                        System.out.println("");
                        String idCliente = obtenerString("Introduce el DNI del cliente: ");
                        double saldo = obtenerDouble("Introduce el saldo del cliente: ");
                        int limitCredito = obtenerInt("Introduce límite de crédito: ");
                        int descuento = obtenerInt("Introduce el descuento del cliente (en %): ");
                        
                        Cliente cliente = new Cliente(idCliente, saldo, limitCredito, descuento);
                        clienteControlador.insertarCliente(cliente);
                        }
                    case DIRECCION -> {
                        System.out.println("");
                        String idCliente = obtenerString("Introduce el DNI del cliente: ");
                        String calle = obtenerString("Introduce la calle: ");
                        int numero = obtenerInt("Introduce el número: ");
                        String ciudad = obtenerString("Introduce la ciudad: ");
                        String codPostal = obtenerString("Introduce el código postal: ");
                        
                        Direccion direccion = new Direccion(idCliente, calle, numero, ciudad, codPostal);
                        direccionControlador.insertarDireccion(direccion);
                        }
                    case FABRICA -> {
                        System.out.println("");
                        String telefono = obtenerString("Introduce el teléfono de la fábrica: ");

                        Fabrica fabrica = new Fabrica(telefono);
                        fabricaControlador.insertarFabrica(fabrica);
                        }
                    case PEDIDO -> {
                        boolean pedir = true;
                        System.out.println("");
                        Date fecha = new Date();
                        String idCliente = obtenerString("Introduce el DNI del cliente: ");
                        int idDireccion = obtenerInt("Introduce el id de la dirección del cliente: ");
                        ArrayList<Integer> articulos = new ArrayList<>();
                        ArrayList<Integer> cantidades = new ArrayList<>();

                        while (pedir) {
                            articulos.add(obtenerInt("Introduce el id de un artículo: "));
                            cantidades.add(obtenerInt("Introduce la cantidad: "));  

                            int respuesta = obtenerInt("¿Quieres seguir añadiendo artículos? (0 No / 1 Sí): ");
                            if (respuesta == 0) {
                                pedir = false;
                            }
                        }

                        Pedido pedido = new Pedido(fecha, idCliente, idDireccion, articulos, cantidades);
                        pedidoControlador.insertarPedido(pedido);
                    }
                    default -> {
                        System.out.println("Algo ha fallado.");
                        }
                }
            }
            // UPDATE
            case 3 -> {
                switch (entidadActual) {
                    case ARTICULO -> {
                        System.out.println("");
                        int idArticulo = obtenerInt("Introduce el ID del articulo que deseas actualizar: ");
                        String descripcion = obtenerString("Introduce una descripción del articulo: ");
                        double precio = obtenerDouble("Introduce un precio: ");
                        int existencias = obtenerInt("Introduce las existencias del articulo: ");
                        int idFabrica = obtenerInt("Introduce el ID de la fábrica: ");
                        
                        Articulo articulo = new Articulo(descripcion, precio, existencias, idFabrica);
                        articuloControlador.actualizarArticulo(idArticulo, articulo);
                        }
                    case CLIENTE -> {
                        System.out.println("");
                        String idCliente = obtenerString("Introduce el DNI del cliente que deseas actualizar: ");
                        double saldo = obtenerDouble("Introduce el saldo del cliente: ");
                        int limitCredito = obtenerInt("Introduce tu limite de credito: ");
                        int descuento = obtenerInt("Introduce el descuento del cliente (en %): ");
                        
                        Cliente cliente = new Cliente(idCliente, saldo, limitCredito, descuento);
                        clienteControlador.actualizarCliente(idCliente, cliente);
                        }
                    case DIRECCION -> {
                        System.out.println("");
                        int idDireccion = obtenerInt("Introduce el id de la dirección que deseas actualizar: ");
                        String idCliente = obtenerString("Introduce el DNI del cliente: ");
                        String calle = obtenerString("Introduce la calle: ");
                        int numero = obtenerInt("Introduce el número: ");
                        String ciudad = obtenerString("Introduce la ciudad: ");
                        String codPostal = obtenerString("Introduce el código postal: ");
                        
                        Direccion direccion = new Direccion(idCliente, calle, numero, ciudad, codPostal);
                        direccionControlador.actualizarDireccion(idDireccion, direccion);
                        }
                    case FABRICA -> {
                        System.out.println("");
                        int idFabrica = obtenerInt("Introduce el id de la fábrica que deseas actualizar: ");
                        String telefono = obtenerString("Introduce el teléfono de la fábrica: ");

                        Fabrica fabrica = new Fabrica(telefono);
                        fabricaControlador.actualizarFabrica(idFabrica, fabrica);
                    }
                    case PEDIDO -> {
                        boolean pedir = true;
                        System.out.println("");
                        int idPedido = obtenerInt("Introduce el id del pedido que quieres actualizar: ");
                        Date fecha = new Date();
                        String idCliente = obtenerString("Introduce el DNI del cliente: ");
                        int idDireccion = obtenerInt("Introduce el id de la dirección del cliente: ");
                        ArrayList<Integer> articulos = new ArrayList<>();
                        ArrayList<Integer> cantidades = new ArrayList<>();

                        while (pedir) {
                            articulos.add(obtenerInt("Introduce el id de un artículo: "));
                            cantidades.add(obtenerInt("Introduce la cantidad: "));  

                            int respuesta = obtenerInt("¿Quieres seguir añadiendo artículos? (0 No / 1 Sí): ");
                            if (respuesta == 0) {
                                pedir = false;
                            }
                        }

                        Pedido pedido = new Pedido(fecha, idCliente, idDireccion, articulos, cantidades);
                        pedidoControlador.actualizarPedido(idPedido, pedido);
                        }
                    default -> {
                        System.out.println("Algo ha fallado.");
                        }
                }
            }
            // DELETE
            case 4 -> {
                switch (entidadActual) {
                    case ARTICULO -> {
                        System.out.println("");
                        int idArticulo = obtenerInt("Introduce el id del artículo a eliminar: ");
                        articuloControlador.eliminarArticulo(idArticulo);
                        }
                    case CLIENTE -> {
                        System.out.println("");
                        String idCliente = obtenerString("Introduce el DNI del cliente a eliminar: ");
                        clienteControlador.eliminarCliente(idCliente);
                        }
                    case DIRECCION -> {
                        System.out.println("");
                        int idDireccion = obtenerInt("Introduce el id de la dirección a eliminar: ");
                        direccionControlador.eliminarDireccion(idDireccion);
                        }
                    case FABRICA -> {
                        System.out.println("");
                        int idFabrica = obtenerInt("Introduce el id de la fábrica a eliminar: ");
                        fabricaControlador.eliminarFabrica(idFabrica);
                        }
                    case PEDIDO -> {
                        System.out.println("");
                        int idPedido = obtenerInt("Introduce el id del pedido a eliminar: ");
                        pedidoControlador.eliminarPedido(idPedido);
                    }
                    default -> {
                        System.out.println("Algo ha fallado.");
                        }
                }
            }
            case 5 -> {
                System.out.println("");
                System.out.println("Volviendo atrás.");
                break;
            }
            default -> System.out.println("Opción elegida inválida.");
        }
    }

    // Método para el switch de las funciones especiales.
    public static void switchFuncionesEspeciales(int opcion) {
        switch(opcion) {
            case 1 -> {
                funcionesEspecialesControlador.primeraConsulta(obtenerString("Introduce el DNI del cliente: "));
            }
            case 2 -> {
                funcionesEspecialesControlador.segundaConsulta();
            }
            case 3 -> {
                funcionesEspecialesControlador.terceraConsulta(obtenerInt("Introduce un año: "));
            }
            case 4 -> {
                funcionesEspecialesControlador.cuartaConsulta();
            }
            case 5 -> {
                funcionesEspecialesControlador.quintaConsulta(opcion, null);
            }
            case 6 -> {
                System.out.println("");
                System.out.println("Volviendo atrás.");
                break;
            }
            default -> {
                System.out.println("Opción elegida inválida.");
            }
        }
    }

    // Main.
    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Bienvenidos a mi aplicación CRUD con JDBC.");
        System.out.println("Trabajo realizado por Thomas.");
                
        while (true) {
            switchMenuEntidades(menuEntidades());
        }
    }
}
