package vista;

import java.util.ArrayList;

import model.Fabrica;

public class FabricaVista {
    
    // Método para mostrar información de un fabrica en la consola.
    public void mostrarFabrica(Fabrica fabrica) {
        System.out.println("");
        System.out.println("Id: " + fabrica.getIdFabrica());
        System.out.println("Teléfono: " + fabrica.getTelefono());
    }

    // Método para mostrar información de varios fabricas en la consola.
    public void mostrarFabricas(ArrayList<Fabrica> fabricas) {
        if (fabricas.isEmpty()) {
            System.out.println("No se han encontrado fabricas.");
        }
        for (int i = 0; i < fabricas.size(); i++) {
            System.out.println("");
            System.out.println("----------------------------------------");
            System.out.println("Fabrica " + (i + 1) + ":");
            mostrarFabrica(fabricas.get(i));
        }
    }
}
