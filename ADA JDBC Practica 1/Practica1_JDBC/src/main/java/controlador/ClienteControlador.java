package controlador;

import model.Cliente;
import vista.ClienteVista;
import dao.ClienteDAO;

public class ClienteControlador {

    // Atributos.
    private ClienteDAO clienteDAO;
    private ClienteVista clienteVista;

    // Constructor.
    public ClienteControlador(ClienteDAO clienteDAO, ClienteVista clienteVista) {
        this.clienteDAO = clienteDAO;
        this.clienteVista = clienteVista;
    }

    // Métodos.
    public void mostrarClientes() {
        clienteVista.mostrarClientes(clienteDAO.obtenerClientes());
    }

    public void insertarCliente(Cliente cliente) {
        clienteDAO.insertarCliente(cliente);
    }

    public void eliminarCliente(String idCliente) {
        clienteDAO.eliminarCliente(idCliente);
    }
   
    public void actualizarCliente(String idCliente, Cliente cliente) {
        clienteDAO.actualizarCliente(idCliente, cliente);
    }
}
