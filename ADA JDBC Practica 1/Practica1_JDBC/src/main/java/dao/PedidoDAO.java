package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import model.Pedido;

public class PedidoDAO {

    // Atributos.
    private Connection conexion;

    // Constructor.
    public PedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener todos los pedidos de la base de datos.
    public ArrayList<Pedido> obtenerPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM pedido";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                Date fecha = rs.getDate("fecha");
                String idCliente = rs.getString("idCliente");
                int idDireccion = rs.getInt("idDireccion");

                // Obtener los artículos y cantidades relacionados con el pedido desde la tabla `incluye`.
                ArrayList<Integer> articulos = obtenerArticulosPorPedido(idPedido);
                ArrayList<Integer> cantidades = obtenerCantidadesPorPedido(idPedido);

                // Crear un objeto Pedido y agregarlo a la lista.
                Pedido pedido = new Pedido(fecha, idCliente, idDireccion, articulos, cantidades);
                pedido.setIdPedido(idPedido);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    // Método para insertar un nuevo pedido en la base de datos.
    public void insertarPedido(Pedido pedido) {
        try {
            // Insertar en la tabla pedido.
            String consulta = "INSERT INTO pedido (fecha, idCliente, idDireccion) VALUES (?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setDate(1, new Date(pedido.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setString(2, pedido.getIdCliente());
            ps.setInt(3, pedido.getIdDireccion());
            ps.executeUpdate();

            // Obtener el ID del pedido recién insertado.
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idPedido = generatedKeys.getInt(1);
                pedido.setIdPedido(idPedido);

                // Insertar los artículos y sus cantidades asociados al pedido en la tabla `incluye`.
                insertarArticulosPedido(idPedido, pedido.getArticulos(), pedido.getCantidades());
            }

            System.out.println("Pedido insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el pedido: " + e.getMessage());
        }
    }

    // Método para eliminar un pedido por su ID.
    public void eliminarPedido(int idPedido) {
        try {
            // Eliminar los artículos asociados al pedido antes de eliminar el pedido.
            eliminarArticulosPedido(idPedido);

            // Eliminar el pedido de la tabla.
            String consulta = "DELETE FROM pedido WHERE idPedido = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idPedido);
            ps.executeUpdate();

            System.out.println("Pedido eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el pedido: " + e.getMessage());
        }
    }

    // Método para actualizar un pedido por su ID.
    public void actualizarPedido(int idPedido, Pedido pedido) {
        try {
            // Actualizar la información del pedido en la tabla pedido.
            String consulta = "UPDATE pedido SET fecha = ?, idCliente = ?, idDireccion = ? WHERE idPedido = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setDate(1, new Date(pedido.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            ps.setString(2, pedido.getIdCliente());
            ps.setInt(3, pedido.getIdDireccion());
            ps.setInt(4, idPedido);
            ps.executeUpdate();

            // Actualizar los artículos y sus cantidades asociados al pedido en la tabla `incluye`.
            eliminarArticulosPedido(idPedido); // Primero eliminar los antiguos.
            insertarArticulosPedido(idPedido, pedido.getArticulos(), pedido.getCantidades()); // Insertar los nuevos.

            System.out.println("Pedido actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el pedido: " + e.getMessage());
        }
    }

    // Método auxiliar para obtener los artículos de un pedido desde la tabla `incluye`.
    private ArrayList<Integer> obtenerArticulosPorPedido(int idPedido) {
        ArrayList<Integer> articulos = new ArrayList<>();
        try {
            String consulta = "SELECT idArticulo FROM incluye WHERE idPedido = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                articulos.add(rs.getInt("idArticulo"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los artículos del pedido: " + e.getMessage());
        }
        return articulos;
    }

    // Método auxiliar para obtener las cantidades de los artículos de un pedido desde la tabla `incluye`.
    private ArrayList<Integer> obtenerCantidadesPorPedido(int idPedido) {
        ArrayList<Integer> cantidades = new ArrayList<>();
        try {
            String consulta = "SELECT cantidad FROM incluye WHERE idPedido = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cantidades.add(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las cantidades del pedido: " + e.getMessage());
        }
        return cantidades;
    }

    // Método auxiliar para insertar los artículos y sus cantidades en la tabla intermedia `incluye`.
    private void insertarArticulosPedido(int idPedido, ArrayList<Integer> articulos, ArrayList<Integer> cantidades) {
        try {
            String consulta = "INSERT INTO incluye (idPedido, idArticulo, cantidad) VALUES (?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(consulta);

            for (int i = 0; i < articulos.size(); i++) {
                ps.setInt(1, idPedido);
                ps.setInt(2, articulos.get(i));
                ps.setInt(3, cantidades.get(i));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error al insertar los artículos del pedido: " + e.getMessage());
        }
    }

    // Método auxiliar para eliminar los artículos asociados a un pedido desde la tabla `incluye`.
    private void eliminarArticulosPedido(int idPedido) {
        try {
            String consulta = "DELETE FROM inlcuye WHERE idPedido = ?"; // Cuidado con el nombre de la tabla inlcuye
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idPedido);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar los artículos del pedido: " + e.getMessage());
        }
    }
}
