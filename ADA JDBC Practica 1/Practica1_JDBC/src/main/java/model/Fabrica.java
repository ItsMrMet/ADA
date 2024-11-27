package model;

public class Fabrica {
    
    // Atributos
    private int idFabrica;
    private String telefono;

    // Constructor
    public Fabrica(String telefono) {
        this.telefono = telefono;
    }

    // Setters y Getters
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdFabrica() {
        return idFabrica;
    }

    public void setIdFabrica(int idFabrica) {
        this.idFabrica = idFabrica;
    }
}
