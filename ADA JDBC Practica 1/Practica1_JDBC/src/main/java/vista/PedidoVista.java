package vista;

import java.util.ArrayList;
import model.Pedido;

public class PedidoVista {

    // Método para mostrar información de un pedido en la consola.
    public void mostrarPedido(Pedido pedido) {
        System.out.println("");
        System.out.println("----------------------------------------");
        System.out.println("Cabezal:");
        System.out.println("Fecha: " + pedido.getFecha());
        System.out.println("Dirección: " + pedido.getIdDireccion());
        System.out.println("Cliente: " + pedido.getIdCliente());
        System.out.println("----------------------------------------");
        System.out.println("Cuerpo:");
        for (int i = 0; i < pedido.getArticulos().size(); i++) {
            System.out.println("Artículo: " + pedido.getArticulos().get(i)); 
            System.out.println("Cantidad: " + pedido.getCantidades().get(i));
        }
        System.out.println("----------------------------------------");
    }

    // Método para mostrar información de varios pedidos en la consola.
    public void mostrarPedidos(ArrayList<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("No se han encontrado pedidos.");
        }
        for (int i = 0; i < pedidos.size(); i++) {
            System.out.println("");
            System.out.println("----------------------------------------");
            System.out.println("Pedido " + (i + 1) + ":");
            mostrarPedido(pedidos.get(i));
        }
    }
}
