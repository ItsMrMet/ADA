package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cliente;

public class ClienteDAO {

    // Atributos.
    private Connection conexion;

    // Constructor.
    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Métodos.

    // Método para ver si existe un cliente determinado.
    public boolean existeCliente(String idCliente) {
        try {
            String consulta = "SELECT idCliente FROM cliente WHERE idCliente = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setString(1, idCliente);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar si el cliente existe: " + e.getMessage());
            return false;
        }
    }

    // Método para ver si existe al menos un cliente en la base de datos.
    public boolean existenClientes() {
        try {
            String consulta = "SELECT * FROM cliente";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar si existen clientes: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener cliente específico.
    public Cliente obtenerCliente(String idCliente) {
        Cliente cliente = null;
        
        String consulta = "SELECT * FROM cliente WHERE idCliente = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                int limitCredito = rs.getInt("limiteCredito");
                int descuento = rs.getInt("descuento");

                cliente = new Cliente(idCliente, saldo, limitCredito, descuento);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente: " + idCliente + e.getMessage());
        }

        return cliente;
    }

    // Método para obtener todos los clientes de la base de datos.
    public ArrayList<Cliente> obtenerClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM cliente";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idCliente = rs.getString("idCliente");
                double saldo = rs.getDouble("saldo");
                int limitCredito = rs.getInt("limiteCredito");
                int descuento = rs.getInt("descuento");

                Cliente cliente = new Cliente(idCliente, saldo, limitCredito, descuento);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("ERROR, no se ha podido obtener datos de clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Método para insertar un cliente en la base de datos.
    public void insertarCliente(Cliente cliente) {

        if (cliente.getLimitCredito() > 18000) {
            System.out.println("El limite de credito no debe superar los 18.000€.");
            return;
        }

        if (cliente.getDescuento() > 100) {
            System.out.println("El descuento no puede ser superior al 100%.");
            return;
        }
        
        if (!existeCliente(cliente.getIdCliente())) {
            try {
                String consulta = "INSERT INTO cliente (idCliente, saldo, limiteCredito, descuento) VALUES (?,?,?,?)";
                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, cliente.getIdCliente());
                ps.setDouble(2, cliente.getSaldo());
                ps.setInt(3, cliente.getLimitCredito());
                ps.setInt(4, cliente.getDescuento());
                ps.executeUpdate();

                System.out.println("Cliente insertado correctamente.");
            } catch (SQLException e) {
                System.out.println("No se ha podido insertar el cliente: " + e.getMessage());
            }
        } else {
            System.out.println("El cliente con ID " + cliente.getIdCliente() + " ya existe.");
        }
    }

    // Método para eliminar un cliente determinado de la base de datos.
    public void eliminarCliente(String idCliente) {
        try {
            if (existeCliente(idCliente)) {
                String consulta = "DELETE FROM cliente WHERE idCliente = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setString(1, idCliente);
                ps.executeUpdate();
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("El cliente con ID " + idCliente + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar el cliente: " + e.getMessage());
        }
    }

    // Método para actualizar un cliente determinado de la base de datos.
    public void actualizarCliente(String idCliente, Cliente cliente) {
        try {
            if (existeCliente(idCliente)) {
                String consulta = "UPDATE cliente SET saldo = ?, limiteCredito = ?, descuento = ? WHERE idCliente = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setDouble(1, cliente.getSaldo());
                ps.setInt(2, cliente.getLimitCredito());
                ps.setInt(3, cliente.getDescuento());
                ps.setString(4, idCliente);
                ps.executeUpdate();
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("El cliente con ID " + idCliente + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido actualizar el cliente: " + e.getMessage());
        }
    }
}
