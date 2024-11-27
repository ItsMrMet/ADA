package controlador;

import model.Direccion;
import vista.DireccionVista;
import dao.DireccionDAO;

public class DireccionControlador {

    // Atributos.
    private DireccionDAO direccionDAO;
    private DireccionVista direccionVista;

    // Constructor.
    public DireccionControlador(DireccionDAO direccionDAO, DireccionVista direccionVista) {
        this.direccionDAO = direccionDAO;
        this.direccionVista = direccionVista;
    }

    // Métodos.
    public void mostrarDirecciones() {
        direccionVista.mostrarDirecciones(direccionDAO.obtenerDirecciones());
    }

    public void insertarDireccion(Direccion direccion) {
        direccionDAO.insertarDireccion(direccion);
    }

    public void eliminarDireccion(int idDireccion) {
        direccionDAO.eliminarDireccion(idDireccion);
    }

    public void actualizarDireccion(int idDireccion, Direccion direccion) {
        direccionDAO.actualizarDireccion(idDireccion, direccion);
    }
}
