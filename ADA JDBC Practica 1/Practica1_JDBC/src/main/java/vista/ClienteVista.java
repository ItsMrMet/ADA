package vista;

import java.util.ArrayList;

import model.Cliente;

public class ClienteVista {

    // Método para mostrar información de un cliente en la consola.
    public void mostrarCliente(Cliente cliente) {
        System.out.println("");
        System.out.println("DNI: " + cliente.getIdCliente());
        System.out.println("Saldo: " + cliente.getSaldo());
        System.out.println("Límite de crédito: " + cliente.getLimitCredito());
        System.out.println("Descuento: " + cliente.getDescuento() + "%");
    }

    // Método para mostrar información de varios clientes en la consola.
    public void mostrarClientes(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No se han encontrado clientes.");
        }
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println("");
            System.out.println("----------------------------------------");
            System.out.println("Cliente " + (i + 1) + ":");
            mostrarCliente(clientes.get(i));
        }
    }
}
