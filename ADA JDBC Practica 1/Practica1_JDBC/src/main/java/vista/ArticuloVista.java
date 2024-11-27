package vista;

import java.util.ArrayList;

import model.Articulo;

public class ArticuloVista {
    
    // Método para mostrar información de un artículo en la consola.
    public void mostrarArticulo(Articulo articulo) {
        System.out.println("");
        System.out.println("Id: " + articulo.getIdArticulo());
        System.out.println("Descripción: " + articulo.getDescripcion());
        System.out.println("Precio: " + articulo.getPrecio());
        System.out.println("Existencias: " + articulo.getExistencias());
        System.out.println("Fábrica: " + articulo.getIdFabrica());
    }

    // Método para mostrar información de varios artículos en la consola.
    public void mostrarArticulos(ArrayList<Articulo> articulos) {
        if (articulos.isEmpty()) {
            System.out.println("No se han encontrado artículos.");
        }
        for (int i = 0; i < articulos.size(); i++) {
            System.out.println("");
            System.out.println("----------------------------------------");
            System.out.println("Artículo " + (i + 1) + ":");
            mostrarArticulo(articulos.get(i));
        }
    }
}
