
package model;


public class Articulo {
    
    // Atributos
    private int idArticulo;
    private String descripcion;
    private double precio;
    private int existencias;
    private int idFabrica;
    
    // Constructor
    public Articulo(String descripcion) {
        this.descripcion = descripcion;
    }

    public Articulo(String descripcion, double precio, int existencias, int idFabrica) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
        this.idFabrica = idFabrica;
    }

    // Setters y Getters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setIdFabrica(int idFabrica) {
        this.idFabrica = idFabrica;
    }

    public int getIdFabrica() {
        return idFabrica;
    }
}
