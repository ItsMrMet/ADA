package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Direccion;

public class DireccionDAO {
    
    // Atributos.
    private Connection conexion;

    // Constructor.
    public DireccionDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    // Método para verificar si existe una dirección por su ID.
    public boolean existeDireccion(int idDireccion) {
        try {
            String consulta = "SELECT idDireccion FROM direccion WHERE idDireccion = ?";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ps.setInt(1, idDireccion);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Retorna true si la dirección existe.
        } catch (SQLException e) {
            System.out.println("Error al verificar si la dirección existe: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todas las direcciones.
    public ArrayList<Direccion> obtenerDirecciones() {
        ArrayList<Direccion> direcciones = new ArrayList<>();
        try {
            String consulta = "SELECT * FROM direccion";
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idCliente = rs.getString("idCliente");
                int idDireccion = rs.getInt("idDireccion");
                int numero = rs.getInt("numero");
                String calle = rs.getString("calle");
                String ciudad = rs.getString("ciudad");
                String codPostal = rs.getString("codPostal");

                // Crear objeto Dirección y agregarlo a la lista.
                Direccion direccion = new Direccion(idCliente, idDireccion, calle, numero, ciudad, codPostal);
                direcciones.add(direccion);
            }
        } catch (SQLException e) {
            System.out.println("ERROR, no se han podido obtener todas las direcciones: " + e.getMessage());
        }
        return direcciones;
    }

    // Método para insertar una nueva dirección.
    public void insertarDireccion(Direccion direccion) {
        if (!existeDireccion(direccion.getIdDireccion())) {
            try {
                String consulta = "INSERT INTO direccion (idDireccion, idCliente, numero, calle, codPostal, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setInt(1, direccion.getIdDireccion());
                ps.setString(2, direccion.getIdCliente());
                ps.setInt(3, direccion.getNumero());
                ps.setString(4, direccion.getCalle());
                ps.setString(5, direccion.getCodPostal());
                ps.setString(6, direccion.getCiudad());
                ps.executeUpdate();

                System.out.println("Se ha insertado la dirección correctamente.");
            } catch (SQLException e) {
                System.out.println("ERROR, no se ha podido insertar la dirección: " + e.getMessage());
            }
        } else {
            System.out.println("Ya existe una dirección con ese ID.");
        }
    }

    // Método para eliminar una dirección por su ID.
    public void eliminarDireccion(int idDireccion) {
        try {
            if (existeDireccion(idDireccion)) {
                String consulta = "DELETE FROM direccion WHERE idDireccion = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);
                ps.setInt(1, idDireccion);
                ps.executeUpdate();

                System.out.println("Se ha eliminado la dirección correctamente.");
            } else {
                System.out.println("La dirección con ID " + idDireccion + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR, no se ha podido eliminar la dirección: " + e.getMessage());
        }
    }

    // Método para actualizar los datos de una dirección.
    public void actualizarDireccion(int idDireccion, Direccion direccion) {
        try {
            if (existeDireccion(idDireccion)) {
                String consulta = "UPDATE direccion SET idCliente = ?, numero = ?, calle = ?, codPostal = ?, ciudad = ? WHERE idDireccion = ?";
                PreparedStatement ps = conexion.prepareStatement(consulta);

                ps.setString(1, direccion.getIdCliente());
                ps.setInt(2, direccion.getNumero());
                ps.setString(3, direccion.getCalle());
                ps.setString(4, direccion.getCodPostal());
                ps.setString(5, direccion.getCiudad());
                ps.setInt(6, idDireccion); // ID de la dirección que estamos actualizando.
                ps.executeUpdate();

                System.out.println("Se ha actualizado la dirección correctamente.");
            } else {
                System.out.println("La dirección con ID " + idDireccion + " no existe.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR, no se ha podido actualizar la dirección: " + e.getMessage());
        }
    }
}
