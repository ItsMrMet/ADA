package controlador;

import dao.PedidoDAO;
import model.Pedido;
import vista.PedidoVista;

public class PedidoControlador {

    // Atributos.
    private PedidoDAO pedidoDAO;
    private PedidoVista pedidoVista;

    // Constructor.
    public PedidoControlador(PedidoDAO pedidoDAO, PedidoVista pedidoVista) {
        this.pedidoDAO = pedidoDAO;
        this.pedidoVista = pedidoVista;
    }

    // Métodos.
    public void mostrarPedidos() {
        pedidoVista.mostrarPedidos(pedidoDAO.obtenerPedidos());
    }

    public void insertarPedido(Pedido pedido) {
        pedidoDAO.insertarPedido(pedido);
    }

    public void eliminarPedido(int idPedido) {
        pedidoDAO.eliminarPedido(idPedido);
    }

    public void actualizarPedido(int idPedido, Pedido pedido) {
        pedidoDAO.actualizarPedido(idPedido, pedido);
    }
}
