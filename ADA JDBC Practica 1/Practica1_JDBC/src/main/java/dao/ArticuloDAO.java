
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Articulo;

public class ArticuloDAO {

    // Atributos.
    private Connection conexion;

    // Constructor.
    public ArticuloDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para verificar si existe un artículo por su ID.
    public boolean existeArticulo(int idArticulo) {
        try {
            String consulta = "SELECT idArticulo FROM articulo WHERE idArticulo = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idArticulo);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si existe un resultado, significa que el artículo existe.
        } catch (SQLException e) {
            System.out.println("Error al verificar si el artículo existe: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar si existen artículos en la base de datos.
    public boolean existenArticulos() {
        try {
            String consulta = "SELECT * FROM articulo";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si hay al menos un artículo, retorna true.
        } catch (SQLException e) {
            System.out.println("Error al verificar si existen artículos: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todos los artículos junto con su información de la tabla fabricar.
    public ArrayList<Articulo> obtenerArticulos() {
        ArrayList<Articulo> articulos = new ArrayList<>();
        try {
            String consulta = "SELECT a.idArticulo, a.descripcion, f.precio, f.existencias, f.idFabrica "
                            + "FROM articulo a "
                            + "JOIN fabricar f ON a.idArticulo = f.idArticulo";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int existencias = rs.getInt("existencias");
                int idFabrica = rs.getInt("idFabrica");

                Articulo articulo = new Articulo(descripcion, precio, existencias, idFabrica);
                articulo.setIdArticulo(idArticulo); // Establecemos el ID después del constructor.
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            System.out.println("ERROR, no se ha podido obtener datos de artículos: " + e.getMessage());
        }
        return articulos;
    }

    // Método para insertar un artículo.
    public void insertarArticulo(Articulo articulo) {
        try {
            if (!existeArticulo(articulo.getIdArticulo())) {
                // Primero insertamos en la tabla articulo
                String consultaArticulo = "INSERT INTO articulo (descripcion) VALUES (?)";
                PreparedStatement psArticulo = conexion.prepareStatement(consultaArticulo, PreparedStatement.RETURN_GENERATED_KEYS);
                psArticulo.setString(1, articulo.getDescripcion());
                psArticulo.executeUpdate();

                // Obtener el ID del artículo recién insertado.
                ResultSet generatedKeys = psArticulo.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idArticulo = generatedKeys.getInt(1);
                    articulo.setIdArticulo(idArticulo); // Establecemos el ID del artículo.

                    // Insertar en la tabla fabricar
                    String consultaFabricar = "INSERT INTO fabricar (precio, existencias, idArticulo, idFabrica) VALUES (?, ?, ?, ?)";
                    PreparedStatement psFabricar = conexion.prepareStatement(consultaFabricar);
                    psFabricar.setDouble(1, articulo.getPrecio());
                    psFabricar.setInt(2, articulo.getExistencias());
                    psFabricar.setInt(3, idArticulo);
                    psFabricar.setInt(4, articulo.getIdFabrica());
                    psFabricar.executeUpdate();
                }

                System.out.println("Artículo insertado correctamente.");
            } else {
                System.out.println("El artículo con ID " + articulo.getIdArticulo() + " ya existe.");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido insertar el artículo: " + e.getMessage());
        }
    }

    // Método para eliminar un artículo.
    public void eliminarArticulo(int idArticulo) {
        try {
            if (existeArticulo(idArticulo)) {
                // Primero eliminar de la tabla fabricar
                String consultaEliminarFabricar = "DELETE FROM fabricar WHERE idArticulo = ?";
                PreparedStatement psEliminarFabricar = conexion.prepareStatement(consultaEliminarFabricar);
                psEliminarFabricar.setInt(1, idArticulo);
                psEliminarFabricar.executeUpdate();

                // Ahora eliminar de la tabla articulo
                String consultaEliminarArticulo = "DELETE FROM articulo WHERE idArticulo = ?";
                PreparedStatement psEliminarArticulo = conexion.prepareStatement(consultaEliminarArticulo);
                psEliminarArticulo.setInt(1, idArticulo);
                psEliminarArticulo.executeUpdate();
                System.out.println("Artículo eliminado correctamente.");
            } else {
                System.out.println("El artículo con ID " + idArticulo + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido eliminar el artículo: " + e.getMessage());
        }
    }

    // Método para actualizar un artículo.
    public void actualizarArticulo(int idArticulo, Articulo articulo) {
        try {
            if (existeArticulo(idArticulo)) {
                String consultaArticulo = "UPDATE articulo SET descripcion = ? WHERE idArticulo = ?";
                PreparedStatement psArticulo = conexion.prepareStatement(consultaArticulo);
                psArticulo.setString(1, articulo.getDescripcion());
                psArticulo.setInt(2, idArticulo); // ID del artículo que estamos actualizando.
                psArticulo.executeUpdate();

                // Ahora actualizamos en la tabla fabricar
                String consultaFabricar = "UPDATE fabricar SET precio = ?, existencias = ?, idFabrica = ? WHERE idArticulo = ?";
                PreparedStatement psFabricar = conexion.prepareStatement(consultaFabricar);
                psFabricar.setDouble(1, articulo.getPrecio());
                psFabricar.setInt(2, articulo.getExistencias());
                psFabricar.setInt(3, articulo.getIdFabrica());
                psFabricar.setInt(4, idArticulo); // ID del artículo que estamos actualizando.
                psFabricar.executeUpdate();

                System.out.println("Artículo actualizado correctamente.");
            } else {
                System.out.println("El artículo con ID " + idArticulo + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("No se ha podido actualizar el artículo: " + e.getMessage());
        }
    }
}
