package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionesEspecialesDAO {

    // Atributo
    private Connection conexion;

    // Constructor
    public FuncionesEspecialesDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método i: Listar todas las comandes de un cliente y el total de importes en descuentos
    public List<String> listarPedidosPorCliente(String idCliente) {
        List<String> comandes = new ArrayList<>();
        double totalDescuentos = 0.0;

        String consulta = "SELECT p.idPedido, SUM(i.cantidad) as totalProductos, " +
                          "COALESCE(c.descuento, 0) as descuento " +
                          "FROM pedido p " +
                          "JOIN incluye i ON p.idPedido = i.idPedido " +
                          "JOIN cliente c ON p.idCliente = c.idCliente " +
                          "WHERE p.idCliente = ? " +
                          "GROUP BY p.idPedido";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idPedido = rs.getString("idPedido");
                int totalProductos = rs.getInt("totalProductos");
                double descuento = rs.getDouble("descuento");
                totalDescuentos += descuento;

                comandes.add("Pedido ID: " + idPedido + ", Total productos: " + totalProductos);
            }
            comandes.add("Total descuentos: " + totalDescuentos);
        } catch (SQLException e) {
            System.out.println("Error al listar comandes: " + e.getMessage());
        }
        return comandes;
    }

    // Método ii: Eliminar fábricas sin artículos en pedidos
    public void eliminarFabricasSinPedidos() {
        String consulta = "DELETE FROM fabrica WHERE idFabrica NOT IN (SELECT DISTINCT idFabrica FROM fabricar)";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            int filasEliminadas = ps.executeUpdate();
            System.out.println("Fábricas eliminadas: " + filasEliminadas);
        } catch (SQLException e) {
            System.out.println("Error al eliminar fábricas: " + e.getMessage());
        }
    }

    // Método iii: Calcular cantidad total de artículos en pedidos de un año dado
    public int cantidadTotalArticulosPorAnio(int anio) {
        int totalArticulos = 0;

        String consulta = "SELECT SUM(i.cantidad) as totalArticulos " +
                          "FROM incluye i " +
                          "JOIN pedido p ON i.idPedido = p.idPedido " +
                          "WHERE YEAR(p.fecha) = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, anio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalArticulos = rs.getInt("totalArticulos");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular la cantidad total de artículos: " + e.getMessage());
        }
        return totalArticulos;
    }

    // Método iv: Obtener el cliente con más pedidos
    public String obtenerClienteConMasPedidos() {
        String idCliente = null;

        String consulta = "SELECT p.idCliente, COUNT(p.idPedido) as totalPedidos " +
                          "FROM pedido p " +
                          "GROUP BY p.idCliente " +
                          "ORDER BY totalPedidos DESC LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(consulta);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                idCliente = rs.getString("idCliente");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente con más pedidos: " + e.getMessage());
        }
        return idCliente;
    }

    // Método v: Obtener comanda con más productos y el precio medio entre dos fechas
    public Map<String, Object> obtenerComandaMasProductosYPrecioMedio(String fechaInicio, String fechaFin) {
        Map<String, Object> resultado = new HashMap<>();

        String consulta = "SELECT p.idPedido, SUM(i.cantidad) as totalProductos, AVG(i.cantidad * f.precio) as precioMedio " +
                        "FROM pedido p " +
                        "JOIN incluye i ON p.idPedido = i.idPedido " +
                        "JOIN fabricar f ON i.idArticulo = f.idArticulo " +
                        "WHERE p.fecha BETWEEN ? AND ? " +
                        "GROUP BY p.idPedido " +
                        "ORDER BY totalProductos DESC LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String idPedido = rs.getString("idPedido");
                double precioMedio = rs.getDouble("precioMedio");
                resultado.put("Pedido ID", idPedido);
                resultado.put("Precio Medio", precioMedio);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener comanda con más productos: " + e.getMessage());
        }
        return resultado;
    }

}
