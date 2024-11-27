package model;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {

    // Atributos
    private int idPedido;
    private Date fecha;
    private String idCliente;
    private int idDireccion;
    private ArrayList<Integer> articulos;
    private ArrayList<Integer> cantidades;
    
    // Constructor
    public Pedido (Date fecha, String idCliente, int idDireccion, ArrayList<Integer> articulos, ArrayList<Integer> cantidades) {
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idDireccion = idDireccion;
        this.articulos = articulos;
        this.cantidades = cantidades;
    }
    
    // Setters y Getters
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setArticulos(ArrayList<Integer> articulos) {
        this.articulos = articulos;
    }

    public ArrayList<Integer> getArticulos() {
        return articulos;
    }

    public void setCantidades(ArrayList<Integer> cantidades) {
        this.cantidades = cantidades;
    }

    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }
}
