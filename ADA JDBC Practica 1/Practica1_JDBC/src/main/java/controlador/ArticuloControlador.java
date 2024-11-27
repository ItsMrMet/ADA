package controlador;

import dao.ArticuloDAO;
import model.Articulo;
import vista.ArticuloVista;

public class ArticuloControlador {

    // Atributos.
    private ArticuloDAO articuloDAO;
    private ArticuloVista articuloVista;

    // Constructor.
    public ArticuloControlador(ArticuloDAO articuloDAO, ArticuloVista articuloVista) {
        this.articuloDAO = articuloDAO;
        this.articuloVista = articuloVista;
    }

    // Métodos.
    public void mostrarArticulos() {
        articuloVista.mostrarArticulos(articuloDAO.obtenerArticulos());
    }

    public void insertarArticulo(Articulo articulo) {
        articuloDAO.insertarArticulo(articulo);
    }

    public void eliminarArticulo(int idArticulo) {
        articuloDAO.eliminarArticulo(idArticulo);
    }

    public void actualizarArticulo(int idArticulo, Articulo articulo) {
        articuloDAO.actualizarArticulo(idArticulo, articulo);
    }
}
