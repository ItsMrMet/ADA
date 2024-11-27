package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Fabrica;

public class FabricaDAO {

    // Atributos.
    private Connection conexion;

    // Constructor.
    public FabricaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para verificar si una fábrica existe por su ID.
    public boolean existeFabrica(int idFabrica) {
        try {
            String consulta = "SELECT idFabrica FROM fabrica WHERE idFabrica = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idFabrica);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Retorna true si la fábrica existe.
        } catch (SQLException e) {
            System.out.println("Error al verificar si la fábrica existe: " + e.getMessage());
            return false;
        }
    }

    // Método para verificar si existen fábricas en la tabla.
    public boolean existenFabricas() {
        try {
            String consulta = "SELECT * FROM fabrica";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Retorna true si hay al menos una fábrica.
        } catch (SQLException e) {
            System.out.println("Error al verificar si existen fábricas: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todas las fábricas.
    public ArrayList<Fabrica> obtenerFabricas() {
        ArrayList<Fabrica> fabricas = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM fabrica";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idFabrica = rs.getInt("idFabrica");
                String telefono = rs.getString("telefono");

                // Crear un objeto Fabrica y añadirlo a la lista.
                Fabrica fabrica = new Fabrica(telefono);
                fabrica.setIdFabrica(idFabrica);
                fabricas.add(fabrica);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todas las fábricas: " + e.getMessage());
        }
        return fabricas;
    }

    // Método para insertar una nueva fábrica.
    public void insertarFabrica(Fabrica fabrica) {
        try {
            // Verificar si la fábrica ya existe.
            String consulta = "INSERT INTO fabrica (numTelefono) VALUES (?)";
            PreparedStatement ps = conexion.prepareStatement(consulta);

            ps.setString(1, fabrica.getTelefono());
            ps.executeUpdate();

            System.out.println("Fábrica insertada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la fábrica: " + e.getMessage());
        }
    }

    // Método para eliminar una fábrica por su ID.
    public void eliminarFabrica(int idFabrica) {
        try {
            if (existeFabrica(idFabrica)) {
                String consulta = "DELETE FROM fabrica WHERE idFabrica = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idFabrica);
                ps.executeUpdate();

                System.out.println("Fábrica eliminada correctamente.");
            } else {
                System.out.println("No se puede eliminar, la fábrica con ID " + idFabrica + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la fábrica: " + e.getMessage());
        }
    }

    // Método para actualizar los datos de una fábrica.
    public void actualizarFabrica(int idFabrica, Fabrica fabrica) {
        try {
            if (existeFabrica(idFabrica)) {
                String consulta = "UPDATE fabrica SET telefono = ? WHERE idFabrica = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, fabrica.getTelefono());
                ps.setInt(2, idFabrica);
                ps.executeUpdate();

                System.out.println("Fábrica actualizada correctamente.");
            } else {
                System.out.println("No se puede actualizar, la fábrica con ID " + idFabrica + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la fábrica: " + e.getMessage());
        }
    }
}
