package model;


public class Direccion {
    
    // Atributos
    private int idDireccion;
    private String idCliente;
    private String calle;
    private int numero;
    private String ciudad;
    private String codPostal;
    
    // Constructor
    public Direccion(String idCliente, String calle, int numero, String ciudad, String codPostal) {
        this.idCliente = idCliente;
        this.numero = numero;
        this.calle = calle;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
    }

    // Constructor
    public Direccion(String idCliente, int idDireccion, String calle, int numero, String ciudad, String codPostal) {
        this.idCliente = idCliente;
        this.idDireccion = idDireccion;
        this.numero = numero;
        this.calle = calle;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
    }

    public Direccion() {
        
    }

    // Setters y Getters
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }
    
    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Direccion{" + ", numero=" + numero + ", calle=" + calle + ", codPostal=" + codPostal + ", ciudad=" + ciudad + '}';
    }
}
