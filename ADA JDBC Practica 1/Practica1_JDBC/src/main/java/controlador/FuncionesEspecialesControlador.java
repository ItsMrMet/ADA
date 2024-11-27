package controlador;

import dao.ClienteDAO;
import dao.FuncionesEspecialesDAO;
import model.*;
import vista.*;

public class FuncionesEspecialesControlador {

    // Atributos.
    private FuncionesEspecialesDAO funcionesEspecialesDAO;
    private PedidoVista pedidoVista;
    private ClienteVista clienteVista;
    private ClienteDAO clienteDAO;
    
    // Constructor.
    public FuncionesEspecialesControlador(FuncionesEspecialesDAO funcionesEspecialesDAO, PedidoVista pedidoVista, ClienteVista clienteVista, ClienteDAO clienteDAO) {
        this.funcionesEspecialesDAO = funcionesEspecialesDAO;
        this.pedidoVista = pedidoVista;
        this.clienteVista = clienteVista;
        this.clienteDAO = clienteDAO;
    }

    // Métodos.
    public void primeraConsulta(String idCliente) {
        System.out.println(funcionesEspecialesDAO.listarPedidosPorCliente(idCliente));
    }

    public void segundaConsulta() {
        funcionesEspecialesDAO.eliminarFabricasSinPedidos();
    }

    public void terceraConsulta(int anio) {
        System.out.println("Total de artículos incluidos en todos los pedidos en el año " + anio + ": " + funcionesEspecialesDAO.cantidadTotalArticulosPorAnio(anio));
    }

    public void cuartaConsulta() {
        clienteVista.mostrarCliente(clienteDAO.obtenerCliente(funcionesEspecialesDAO.obtenerClienteConMasPedidos()));
    }

    public void quintaConsulta(int idArticulo, Articulo articulo) {
        
    }
}
