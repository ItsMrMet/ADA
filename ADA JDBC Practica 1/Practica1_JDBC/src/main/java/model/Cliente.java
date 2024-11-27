
package model;

public class Cliente {
    
    // Atributos
    private String idCliente;
    private double saldo;
    private int limitCredito;
    private int descuento;
    
    // Constructor
    public Cliente(String idCliente, double saldo, int limitCredito, int descuento) {
        this.idCliente = idCliente;
        this.saldo = saldo;
        this.limitCredito = limitCredito;
        this.descuento = descuento;
    }

    // Setters y Getters
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public double getSaldo() {
        return saldo;
    } 

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getLimitCredito() {
        return limitCredito;
    }

    public void setLimitCredito(int limitCredito) {
        this.limitCredito = limitCredito;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }
}
