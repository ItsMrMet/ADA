package controlador;

import dao.FabricaDAO;
import model.Fabrica;
import vista.FabricaVista;

public class FabricaControlador {

    // Atributos.
    private FabricaDAO fabricaDAO;
    private FabricaVista fabricaVista;

    // Constructor.
    public FabricaControlador(FabricaDAO fabricaDAO, FabricaVista fabricaVista) {
        this.fabricaDAO = fabricaDAO;
        this.fabricaVista = fabricaVista;
    }

    // Métodos.
    public void mostrarFabricas() {
        fabricaVista.mostrarFabricas(fabricaDAO.obtenerFabricas());
    }

    public void insertarFabrica(Fabrica fabrica) {
        fabricaDAO.insertarFabrica(fabrica);
    }

    public void eliminarFabrica(int idFabrica) {
        fabricaDAO.eliminarFabrica(idFabrica);
    }

    public void actualizarFabrica(int idFabrica, Fabrica fabrica) {
        fabricaDAO.actualizarFabrica(idFabrica, fabrica);
    }
}
