package vista;

import model.Direccion;
import java.util.ArrayList;

public class DireccionVista {

    // Método para mostrar información de una dirección en la consola.
    public void mostrarDireccion(Direccion direccion) {
        System.out.println("");
        System.out.println("DNI del cliente: " + direccion.getIdCliente());
        System.out.println("Calle: " + direccion.getCalle());
        System.out.println("Número: " + direccion.getNumero());
        System.out.println("Ciudad: " + direccion.getCiudad());
        System.out.println("Código postal: " + direccion.getCodPostal());
    }

    // Método para mostrar información de varias direcciones en la consola.
    public void mostrarDirecciones(ArrayList<Direccion> direcciones) {
        if (direcciones.isEmpty()) {
            System.out.println("No se han encontrado direcciones.");
        }
        for (int i = 0; i < direcciones.size(); i++) {
            System.out.println("");
            System.out.println("----------------------------------------");
            System.out.println("Dirección " + (i + 1) + ":");
            mostrarDireccion(direcciones.get(i));
        }
    }
}
